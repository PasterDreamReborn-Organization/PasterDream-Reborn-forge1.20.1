package com.pasterdream.pasterdreammod.world.block;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * 方解石锥 —— 复刻原版滴水石锥的完整行为（厚度缩放、含水、下落、滴水、生长、摔落伤害、三叉戟击碎）。
 * 单个方块只是一段柱体，上下相邻方块自动切换 tip / frustum / base / middle 厚度，堆叠后形成锥形。
 */
public class CalciteConeBlock extends Block implements Fallable, SimpleWaterloggedBlock {

    public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // tip 收窄到原版栅栏的 4px 细度
    private static final VoxelShape TIP_MERGE_SHAPE = Block.box(6, 0, 6, 10, 16, 10);
    private static final VoxelShape TIP_SHAPE_UP = Block.box(6, 0, 6, 10, 11, 10);
    private static final VoxelShape TIP_SHAPE_DOWN = Block.box(6, 5, 6, 10, 16, 10);
    private static final VoxelShape FRUSTUM_SHAPE = Block.box(4, 0, 4, 12, 16, 12);
    private static final VoxelShape MIDDLE_SHAPE = Block.box(3, 0, 3, 13, 16, 13);
    private static final VoxelShape BASE_SHAPE = Block.box(2, 0, 2, 14, 16, 14);
    private static final VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK = Block.box(6, 0, 6, 10, 16, 10);

    /**
     * 构造方解石锥方块：设定材质色、音效、硬度、含水、随机刻、动态形状等基础属性，
     * 默认状态为朝上的 tip 尖端、未含水。
     */
    public CalciteConeBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.TERRACOTTA_WHITE)
                .sound(SoundType.CALCITE)
                .forceSolidOn()
                .noOcclusion()
                .randomTicks()
                .strength(0.75f)
                .requiresCorrectToolForDrops()
                .dynamicShape()
                .offsetType(BlockBehaviour.OffsetType.XZ)
                .pushReaction(PushReaction.DESTROY));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TIP_DIRECTION, Direction.UP)
                .setValue(THICKNESS, DripstoneThickness.TIP)
                .setValue(WATERLOGGED, false));
    }

    @Override
    /*
      注册方块状态属性：尖端朝向（上/下）、滴水石厚度（tip/frustum/base/middle/tip_merge）、是否含水。
     */
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
    }

    @Override
    /*
     * 判断方块能否存活：尖端背后必须有实体面或同向锥体支撑。
     */
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return isValidPlacement(level, pos, state.getValue(TIP_DIRECTION));
    }

    @Override
    /*
     * 相邻方块变化时更新：含水则计划水流 tick；仅对上下方向重算厚度，
     * 若朝上尖端失去支撑则计划 tick 以便后续掉落/破坏。
     */
    public @NotNull BlockState updateShape(BlockState state, Direction facing, BlockState facingState,
                                           LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        if (facing != Direction.UP && facing != Direction.DOWN) {
            return state;
        }
        Direction tipDir = state.getValue(TIP_DIRECTION);
        if (tipDir == Direction.DOWN && level.getBlockTicks().hasScheduledTick(pos, this)) {
            return state;
        }
        if (facing == tipDir.getOpposite() && !this.canSurvive(state, level, pos)) {
            level.scheduleTick(pos, this, tipDir == Direction.DOWN ? 2 : 1);
            return state;
        }
        boolean isMerge = state.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
        return state.setValue(THICKNESS, calculateThickness(level, pos, tipDir, isMerge));
    }

    @Override
    /*
     * 三叉戟高速击中时击碎方块（复刻原版滴水石锥被三叉戟破锥的行为）。
     */
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        BlockPos pos = hit.getBlockPos();
        if (!level.isClientSide && projectile.mayInteract(level, pos)
                && projectile instanceof ThrownTrident && projectile.getDeltaMovement().length() > 0.6) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    /*
     * 实体落到朝上的 tip 尖端上时额外造成摔落伤害（石笋尖刺伤害），其余情况走默认摔落逻辑。
     */
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.getValue(TIP_DIRECTION) == Direction.UP && state.getValue(THICKNESS) == DripstoneThickness.TIP) {
            entity.causeFallDamage(fallDistance + 2.0F, 2.0F, level.damageSources().stalagmite());
        } else {
            super.fallOn(level, state, pos, entity, fallDistance);
        }
    }

    @Override
    /*
     * 客户端粒子：可滴水的钟乳石尖端按概率在其下方生成滴水粒子。
     */
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (canDrip(state)) {
            float f = random.nextFloat();
            if (!(f > 0.12F)) {
                getFluidAboveStalactite(level, pos, state)
                        .filter(info -> f < 0.02F || canFillCauldron(info.fluid))
                        .ifPresent(info -> spawnDripParticle(level, pos, state, info.fluid));
            }
        }
    }

    @Override
    /*
     * 计划 tick：石笋失去支撑则破坏；否则尝试生成下落中的钟乳石柱。
     */
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (isStalagmite(state) && !this.canSurvive(state, level, pos)) {
            level.destroyBlock(pos, true);
        } else {
            spawnFallingStalactite(state, level, pos);
        }
    }

    @Override
    /*
     * 随机刻：尝试从顶端水源/岩浆源向下转移流体，并按极低概率触发石笋/钟乳石生长。
     */
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        maybeTransferFluid(state, level, pos, random.nextFloat());
        if (random.nextFloat() < 0.011377778F && isStalactiteStartPos(state, level, pos)) {
            growStalactiteOrStalagmiteIfPossible(state, level, pos, random);
        }
    }

    /*
     * 尝试让钟乳石顶端的流体向下转移：找到下方尖端后，若为水流过泥土则把泥土转化为黏土。
     */
    private static void maybeTransferFluid(BlockState state, ServerLevel level, BlockPos pos, float randVal) {
        if (!(randVal > 0.17578125F) || !(randVal > 0.05859375F)) {
            if (isStalactiteStartPos(state, level, pos)) {
                Optional<FluidInfo> fluidInfo = getFluidAboveStalactite(level, pos, state);
                if (!fluidInfo.isEmpty()) {
                    Fluid fluid = fluidInfo.get().fluid;
                    float prob;
                    if (fluid == Fluids.WATER) {
                        prob = 0.17578125F;
                    } else {
                        if (fluid != Fluids.LAVA) {
                            return;
                        }
                        prob = 0.05859375F;
                    }
                    if (!(randVal >= prob)) {
                        BlockPos tip = findTip(state, level, pos, 11, false);
                        if (tip != null) {
                            if (fluidInfo.get().sourceState.is(Blocks.MUD) && fluid == Fluids.WATER) {
                                BlockState clay = Blocks.CLAY.defaultBlockState();
                                level.setBlockAndUpdate(fluidInfo.get().pos, clay);
                                Block.pushEntitiesUp(fluidInfo.get().sourceState, clay, level, fluidInfo.get().pos);
                                level.gameEvent(GameEvent.BLOCK_CHANGE, fluidInfo.get().pos, GameEvent.Context.of(clay));
                                level.levelEvent(1504, tip, 0);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 放置时计算：根据玩家视线方向与周边锥体确定尖端朝向与厚度，含水则记录含水状态。
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction dir = context.getNearestLookingVerticalDirection().getOpposite();
        Direction tipDir = calculateTipDirection(level, pos, dir);
        if (tipDir == null) {
            return null;
        }
        boolean isMerge = !context.isSecondaryUseActive();
        DripstoneThickness thickness = calculateThickness(level, pos, tipDir, isMerge);
        return this.defaultBlockState()
                .setValue(TIP_DIRECTION, tipDir)
                .setValue(THICKNESS, thickness)
                .setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
    }

    @Override
    /*
     * 含水状态返回水源，否则走默认流体状态。
     */
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    /*
     * 返回空遮挡形状：该方块不遮挡相邻方块的面。
     */
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    /*
     * 按厚度返回对应的碰撞箱，并应用放置时的水平偏移。
     */
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        DripstoneThickness thickness = state.getValue(THICKNESS);
        VoxelShape shape;
        if (thickness == DripstoneThickness.TIP_MERGE) {
            shape = TIP_MERGE_SHAPE;
        } else if (thickness == DripstoneThickness.TIP) {
            shape = state.getValue(TIP_DIRECTION) == Direction.DOWN ? TIP_SHAPE_DOWN : TIP_SHAPE_UP;
        } else if (thickness == DripstoneThickness.FRUSTUM) {
            shape = FRUSTUM_SHAPE;
        } else if (thickness == DripstoneThickness.MIDDLE) {
            shape = MIDDLE_SHAPE;
        } else {
            shape = BASE_SHAPE;
        }
        Vec3 offset = state.getOffset(level, pos);
        return shape.move(offset.x, 0.0, offset.z);
    }

    @Override
    /*
     * 碰撞箱不是完整方块。
     */
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    @Override
    /*
     * 最大水平偏移量（配合 offsetType(XZ) 实现视觉错位）。
     */
    public float getMaxHorizontalOffset() {
        return 0.125F;
    }

    @Override
    /*
     * 下落方块落地破碎时播放音效。
     */
    public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity fallingBlock) {
        if (!fallingBlock.isSilent()) {
            level.levelEvent(1045, pos, 0);
        }
    }

    @Override
    /*
     * 返回被下落钟乳石砸中的伤害来源。
     */
    public DamageSource getFallDamageSource(Entity entity) {
        return entity.damageSources().fallingStalactite(entity);
    }

    /*
     * 生成下落中的钟乳石柱：从当前位置逐段向上把所有钟乳石段变为 FallingBlockEntity，
     * 到尖端为止，并按柱高计算落地伤害。
     */
    private static void spawnFallingStalactite(BlockState state, ServerLevel level, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = pos.mutable();
        BlockState current = state;
        while (isStalactite(current)) {
            FallingBlockEntity falling = FallingBlockEntity.fall(level, mutable, current);
            if (isTip(current, true)) {
                int height = Math.max(1 + pos.getY() - mutable.getY(), 6);
                float damage = 1.0F * height;
                falling.setHurtsEntities(damage, 40);
                break;
            }
            mutable.move(Direction.DOWN);
            current = level.getBlockState(mutable);
        }
    }

    /*
     * 满足生长条件（上方为滴水石块+水源）时，随机向下生长钟乳石或在下方的尖端生长石笋。
     */
    private static void growStalactiteOrStalagmiteIfPossible(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockState above1 = level.getBlockState(pos.above(1));
        BlockState above2 = level.getBlockState(pos.above(2));
        if (canGrow(above1, above2)) {
            BlockPos tip = findTip(state, level, pos, 7, false);
            if (tip != null) {
                BlockState tipState = level.getBlockState(tip);
                if (canDrip(tipState) && canTipGrow(tipState, level, tip)) {
                    if (random.nextBoolean()) {
                        grow(level, tip, Direction.DOWN);
                    } else {
                        growStalagmiteBelow(level, tip);
                    }
                }
            }
        }
    }

    /*
     * 在钟乳石下方寻找可生长位置生成石笋：跳过流体/不可滴穿的方块，
     * 遇到已有的朝上尖端则合并生长，否则在第一个可放置位置创建石笋尖。
     */
    private static void growStalagmiteBelow(ServerLevel level, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = pos.mutable();
        for (int i = 0; i < 10; i++) {
            mutable.move(Direction.DOWN);
            BlockState state = level.getBlockState(mutable);
            if (!state.getFluidState().isEmpty()) {
                return;
            }
            if (isUnmergedTipWithDirection(state, Direction.UP) && canTipGrow(state, level, mutable)) {
                grow(level, mutable, Direction.UP);
                return;
            }
            if (isValidPlacement(level, mutable, Direction.UP) && !level.isWaterAt(mutable.below())) {
                grow(level, mutable.below(), Direction.UP);
                return;
            }
            if (!canDripThrough(level, mutable, state)) {
                return;
            }
        }
    }

    /*
     * 在指定方向生长一格：目标已是反向尖端则合并成 tip_merge，否则在空气/水中创建新尖端。
     */
    private static void grow(ServerLevel level, BlockPos pos, Direction dir) {
        BlockPos target = pos.relative(dir);
        BlockState targetState = level.getBlockState(target);
        if (isUnmergedTipWithDirection(targetState, dir.getOpposite())) {
            createMergedTips(targetState, level, target);
        } else if (targetState.isAir() || targetState.is(Blocks.WATER)) {
            createCone(level, target, dir, DripstoneThickness.TIP);
        }
    }

    /*
     * 在指定位置按朝向与厚度放置一块方解石锥。
     */
    private static void createCone(LevelAccessor level, BlockPos pos, Direction dir, DripstoneThickness thickness) {
        BlockState state = ModBlocks.CALCITE_CONE.get().defaultBlockState()
                .setValue(TIP_DIRECTION, dir)
                .setValue(THICKNESS, thickness)
                .setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
        level.setBlock(pos, state, 3);
    }

    /*
     * 把上下两个尖端合并成 tip_merge 状态（两段锥体尖端相接）。
     */
    private static void createMergedTips(BlockState state, LevelAccessor level, BlockPos pos) {
        BlockPos above;
        BlockPos below;
        if (state.getValue(TIP_DIRECTION) == Direction.UP) {
            below = pos;
            above = pos.above();
        } else {
            above = pos;
            below = pos.below();
        }
        createCone(level, above, Direction.DOWN, DripstoneThickness.TIP_MERGE);
        createCone(level, below, Direction.UP, DripstoneThickness.TIP_MERGE);
    }

    /*
     * 在钟乳石尖端下方生成滴水粒子：岩浆滴用岩浆粒子，否则用水滴粒子。
     */
    private static void spawnDripParticle(Level level, BlockPos pos, BlockState state, Fluid fluid) {
        Vec3 offset = state.getOffset(level, pos);
        double x = pos.getX() + 0.5 + offset.x;
        double y = pos.getY() + 1 - 0.6875F - 0.0625;
        double z = pos.getZ() + 0.5 + offset.z;
        Fluid dripFluid = getDripFluid(level, fluid);
        ParticleOptions particle = dripFluid.is(FluidTags.LAVA)
                ? ParticleTypes.DRIPPING_DRIPSTONE_LAVA : ParticleTypes.DRIPPING_DRIPSTONE_WATER;
        level.addParticle(particle, x, y, z, 0.0, 0.0, 0.0);
    }

    /*
     * 沿锥体朝向向上/下搜索尖端（tip，可选含 tip_merge），最多搜 maxLen 格。
     */
    @Nullable
    private static BlockPos findTip(BlockState state, LevelAccessor level, BlockPos pos, int maxLen, boolean includeMerge) {
        if (isTip(state, includeMerge)) {
            return pos;
        }
        Direction dir = state.getValue(TIP_DIRECTION);
        BiPredicate<BlockPos, BlockState> throughPredicate = (p, s) ->
                s.is(ModBlocks.CALCITE_CONE.get()) && s.getValue(TIP_DIRECTION) == dir;
        return findBlockVertical(level, pos, dir.getAxisDirection(), throughPredicate,
                s -> isTip(s, includeMerge), maxLen).orElse(null);
    }

    /*
     * 放置时确定尖端朝向：优先玩家朝向，否则反向；两者都不可依附则返回 null。
     */
    @Nullable
    private static Direction calculateTipDirection(LevelReader level, BlockPos pos, Direction dir) {
        Direction tipDir;
        if (isValidPlacement(level, pos, dir)) {
            tipDir = dir;
        } else if (isValidPlacement(level, pos, dir.getOpposite())) {
            tipDir = dir.getOpposite();
        } else {
            return null;
        }
        return tipDir;
    }

    /*
     * 根据前后相邻锥体的厚度与朝向计算本段的厚度（tip/tip_merge/frustum/base/middle）。
     */
    private static DripstoneThickness calculateThickness(LevelReader level, BlockPos pos, Direction tipDir, boolean isMerge) {
        Direction opposite = tipDir.getOpposite();
        BlockState ahead = level.getBlockState(pos.relative(tipDir));
        if (isConeWithDirection(ahead, opposite)) {
            return !isMerge && ahead.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE
                    ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
        } else if (!isConeWithDirection(ahead, tipDir)) {
            return DripstoneThickness.TIP;
        } else {
            DripstoneThickness aheadThickness = ahead.getValue(THICKNESS);
            if (aheadThickness != DripstoneThickness.TIP && aheadThickness != DripstoneThickness.TIP_MERGE) {
                BlockState behind = level.getBlockState(pos.relative(opposite));
                return !isConeWithDirection(behind, tipDir) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
            } else {
                return DripstoneThickness.FRUSTUM;
            }
        }
    }

    /*
     * 判断该钟乳石是否为可滴水的尖端（朝下、tip、未含水）。
     */
    public static boolean canDrip(BlockState state) {
        return isStalactite(state) && state.getValue(THICKNESS) == DripstoneThickness.TIP && !state.getValue(WATERLOGGED);
    }

    /*
     * 判断尖端能否沿其朝向生长：目标格无流体且为空气或反向尖端。
     */
    private static boolean canTipGrow(BlockState state, ServerLevel level, BlockPos pos) {
        Direction dir = state.getValue(TIP_DIRECTION);
        BlockPos target = pos.relative(dir);
        BlockState targetState = level.getBlockState(target);
        if (!targetState.getFluidState().isEmpty()) {
            return false;
        }
        return targetState.isAir() || isUnmergedTipWithDirection(targetState, dir.getOpposite());
    }

    /*
     * 沿尖端朝向反向向上/向下找锥体的根（第一个非方解石锥的方块）。
     */
    private static Optional<BlockPos> findRootBlock(Level level, BlockPos pos, BlockState state, int maxLen) {
        Direction dir = state.getValue(TIP_DIRECTION);
        BiPredicate<BlockPos, BlockState> throughPredicate = (p, s) ->
                s.is(ModBlocks.CALCITE_CONE.get()) && s.getValue(TIP_DIRECTION) == dir;
        return findBlockVertical(level, pos, dir.getOpposite().getAxisDirection(), throughPredicate,
                s -> !s.is(ModBlocks.CALCITE_CONE.get()), maxLen);
    }

    /*
     * 判断锥体能否以某朝向放置：背后需有实体面或同向锥体支撑。
     */
    private static boolean isValidPlacement(LevelReader level, BlockPos pos, Direction dir) {
        BlockPos behind = pos.relative(dir.getOpposite());
        BlockState behindState = level.getBlockState(behind);
        return behindState.isFaceSturdy(level, behind, dir) || isConeWithDirection(behindState, dir);
    }

    /*
     * 判断是否为尖端（tip，可选含 tip_merge）。
     */
    private static boolean isTip(BlockState state, boolean includeMerge) {
        if (!state.is(ModBlocks.CALCITE_CONE.get())) {
            return false;
        }
        DripstoneThickness thickness = state.getValue(THICKNESS);
        return thickness == DripstoneThickness.TIP || includeMerge && thickness == DripstoneThickness.TIP_MERGE;
    }

    /*
     * 判断是否为指定朝向、未合并的尖端（不含 tip_merge）。
     */
    private static boolean isUnmergedTipWithDirection(BlockState state, Direction dir) {
        return isTip(state, false) && state.getValue(TIP_DIRECTION) == dir;
    }

    /*
     * 判断是否为钟乳石（尖端朝下）。
     */
    private static boolean isStalactite(BlockState state) {
        return isConeWithDirection(state, Direction.DOWN);
    }

    /*
     * 判断是否为石笋（尖端朝上）。
     */
    private static boolean isStalagmite(BlockState state) {
        return isConeWithDirection(state, Direction.UP);
    }

    /*
     * 判断是否为钟乳石的起始段（上方不再是锥体）。
     */
    private static boolean isStalactiteStartPos(BlockState state, LevelReader level, BlockPos pos) {
        return isStalactite(state) && !level.getBlockState(pos.above()).is(ModBlocks.CALCITE_CONE.get());
    }

    @Override
    /*
     * 该方块不可被生物寻路。
     */
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    /*
     * 判断是否为指定朝向的方解石锥。
     */
    private static boolean isConeWithDirection(BlockState state, Direction dir) {
        return state.is(ModBlocks.CALCITE_CONE.get()) && state.getValue(TIP_DIRECTION) == dir;
    }

    /*
     * 获取钟乳石顶端上方流体的信息（泥土在上方视为水，灼热维度不取泥水）。
     */
    private static Optional<FluidInfo> getFluidAboveStalactite(Level level, BlockPos pos, BlockState state) {
        return !isStalactite(state) ? Optional.empty() : findRootBlock(level, pos, state, 11).map(root -> {
            BlockPos above = root.above();
            BlockState aboveState = level.getBlockState(above);
            Fluid fluid;
            if (aboveState.is(Blocks.MUD) && !level.dimensionType().ultraWarm()) {
                fluid = Fluids.WATER;
            } else {
                fluid = level.getFluidState(above).getType();
            }
            return new FluidInfo(above, fluid, aboveState);
        });
    }

    /*
     * 判断流体能否填充炼药锅（仅岩浆或水）。
     */
    private static boolean canFillCauldron(Fluid fluid) {
        return fluid == Fluids.LAVA || fluid == Fluids.WATER;
    }

    /*
     * 判断生长条件：顶部为滴水石块，其上方为水源。
     */
    private static boolean canGrow(BlockState state, BlockState above) {
        return state.is(Blocks.DRIPSTONE_BLOCK) && above.is(Blocks.WATER) && above.getFluidState().isSource();
    }

    /*
     * 确定滴液流体：为空时，灼热维度滴岩浆，否则滴水。
     */
    private static Fluid getDripFluid(Level level, Fluid fluid) {
        if (fluid.isSame(Fluids.EMPTY)) {
            return level.dimensionType().ultraWarm() ? Fluids.LAVA : Fluids.WATER;
        }
        return fluid;
    }

    /*
     * 沿垂直方向向上/下搜索满足 targetPredicate 的方块，途中每个方块都必须满足 throughPredicate。
     */
    private static Optional<BlockPos> findBlockVertical(LevelAccessor level, BlockPos pos,
                                                        Direction.AxisDirection axisDir,
                                                        BiPredicate<BlockPos, BlockState> throughPredicate,
                                                        Predicate<BlockState> targetPredicate, int maxLen) {
        Direction dir = Direction.get(axisDir, Direction.Axis.Y);
        BlockPos.MutableBlockPos mutable = pos.mutable();
        for (int i = 1; i < maxLen; i++) {
            mutable.move(dir);
            BlockState state = level.getBlockState(mutable);
            if (targetPredicate.test(state)) {
                return Optional.of(mutable.immutable());
            }
            if (level.isOutsideBuildHeight(mutable.getY()) || !throughPredicate.test(mutable, state)) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /*
     * 判断流体能否穿过某方块滴下：空气可、实体方块不可、有流体不可、非实体方块看碰撞箱是否阻挡滴水通道。
     */
    private static boolean canDripThrough(BlockGetter level, BlockPos pos, BlockState state) {
        if (state.isAir()) {
            return true;
        } else if (state.isSolidRender(level, pos)) {
            return false;
        } else if (!state.getFluidState().isEmpty()) {
            return false;
        } else {
            VoxelShape shape = state.getCollisionShape(level, pos);
            return !Shapes.joinIsNotEmpty(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, shape, BooleanOp.AND);
        }
    }

    /*
     * 记录流体来源位置、流体类型与其上方的方块状态。
     */
    record FluidInfo(BlockPos pos, Fluid fluid, BlockState sourceState) {
    }
}
