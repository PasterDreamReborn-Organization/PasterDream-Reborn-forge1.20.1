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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return isValidPlacement(level, pos, state.getValue(TIP_DIRECTION));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState,
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
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        BlockPos pos = hit.getBlockPos();
        if (!level.isClientSide && projectile.mayInteract(level, pos)
                && projectile instanceof ThrownTrident && projectile.getDeltaMovement().length() > 0.6) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.getValue(TIP_DIRECTION) == Direction.UP && state.getValue(THICKNESS) == DripstoneThickness.TIP) {
            entity.causeFallDamage(fallDistance + 2.0F, 2.0F, level.damageSources().stalagmite());
        } else {
            super.fallOn(level, state, pos, entity, fallDistance);
        }
    }

    @Override
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
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (isStalagmite(state) && !this.canSurvive(state, level, pos)) {
            level.destroyBlock(pos, true);
        } else {
            spawnFallingStalactite(state, level, pos);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        maybeTransferFluid(state, level, pos, random.nextFloat());
        if (random.nextFloat() < 0.011377778F && isStalactiteStartPos(state, level, pos)) {
            growStalactiteOrStalagmiteIfPossible(state, level, pos, random);
        }
    }

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
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
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
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    @Override
    public float getMaxHorizontalOffset() {
        return 0.125F;
    }

    @Override
    public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity fallingBlock) {
        if (!fallingBlock.isSilent()) {
            level.levelEvent(1045, pos, 0);
        }
    }

    @Override
    public DamageSource getFallDamageSource(Entity entity) {
        return entity.damageSources().fallingStalactite(entity);
    }

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

    private static void grow(ServerLevel level, BlockPos pos, Direction dir) {
        BlockPos target = pos.relative(dir);
        BlockState targetState = level.getBlockState(target);
        if (isUnmergedTipWithDirection(targetState, dir.getOpposite())) {
            createMergedTips(targetState, level, target);
        } else if (targetState.isAir() || targetState.is(Blocks.WATER)) {
            createCone(level, target, dir, DripstoneThickness.TIP);
        }
    }

    private static void createCone(LevelAccessor level, BlockPos pos, Direction dir, DripstoneThickness thickness) {
        BlockState state = ModBlocks.CALCITE_CONE.get().defaultBlockState()
                .setValue(TIP_DIRECTION, dir)
                .setValue(THICKNESS, thickness)
                .setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
        level.setBlock(pos, state, 3);
    }

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

    public static boolean canDrip(BlockState state) {
        return isStalactite(state) && state.getValue(THICKNESS) == DripstoneThickness.TIP && !state.getValue(WATERLOGGED);
    }

    private static boolean canTipGrow(BlockState state, ServerLevel level, BlockPos pos) {
        Direction dir = state.getValue(TIP_DIRECTION);
        BlockPos target = pos.relative(dir);
        BlockState targetState = level.getBlockState(target);
        if (!targetState.getFluidState().isEmpty()) {
            return false;
        }
        return targetState.isAir() || isUnmergedTipWithDirection(targetState, dir.getOpposite());
    }

    private static Optional<BlockPos> findRootBlock(Level level, BlockPos pos, BlockState state, int maxLen) {
        Direction dir = state.getValue(TIP_DIRECTION);
        BiPredicate<BlockPos, BlockState> throughPredicate = (p, s) ->
                s.is(ModBlocks.CALCITE_CONE.get()) && s.getValue(TIP_DIRECTION) == dir;
        return findBlockVertical(level, pos, dir.getOpposite().getAxisDirection(), throughPredicate,
                s -> !s.is(ModBlocks.CALCITE_CONE.get()), maxLen);
    }

    private static boolean isValidPlacement(LevelReader level, BlockPos pos, Direction dir) {
        BlockPos behind = pos.relative(dir.getOpposite());
        BlockState behindState = level.getBlockState(behind);
        return behindState.isFaceSturdy(level, behind, dir) || isConeWithDirection(behindState, dir);
    }

    private static boolean isTip(BlockState state, boolean includeMerge) {
        if (!state.is(ModBlocks.CALCITE_CONE.get())) {
            return false;
        }
        DripstoneThickness thickness = state.getValue(THICKNESS);
        return thickness == DripstoneThickness.TIP || includeMerge && thickness == DripstoneThickness.TIP_MERGE;
    }

    private static boolean isUnmergedTipWithDirection(BlockState state, Direction dir) {
        return isTip(state, false) && state.getValue(TIP_DIRECTION) == dir;
    }

    private static boolean isStalactite(BlockState state) {
        return isConeWithDirection(state, Direction.DOWN);
    }

    private static boolean isStalagmite(BlockState state) {
        return isConeWithDirection(state, Direction.UP);
    }

    private static boolean isStalactiteStartPos(BlockState state, LevelReader level, BlockPos pos) {
        return isStalactite(state) && !level.getBlockState(pos.above()).is(ModBlocks.CALCITE_CONE.get());
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    private static boolean isConeWithDirection(BlockState state, Direction dir) {
        return state.is(ModBlocks.CALCITE_CONE.get()) && state.getValue(TIP_DIRECTION) == dir;
    }

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

    private static boolean canFillCauldron(Fluid fluid) {
        return fluid == Fluids.LAVA || fluid == Fluids.WATER;
    }

    private static boolean canGrow(BlockState state, BlockState above) {
        return state.is(Blocks.DRIPSTONE_BLOCK) && above.is(Blocks.WATER) && above.getFluidState().isSource();
    }

    private static Fluid getDripFluid(Level level, Fluid fluid) {
        if (fluid.isSame(Fluids.EMPTY)) {
            return level.dimensionType().ultraWarm() ? Fluids.LAVA : Fluids.WATER;
        }
        return fluid;
    }

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

    record FluidInfo(BlockPos pos, Fluid fluid, BlockState sourceState) {
    }
}
