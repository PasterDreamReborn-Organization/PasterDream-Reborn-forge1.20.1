package com.pasterdream.pasterdreammod.world.block;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 无花果藤：向下生长的藤蔓，参考原版发光浆果的 GrowingPlant 结构。
 * 用 {@link #HEAD} 状态区分底部（会生长，fig_vine 材质）与上方（不生长，风泊树叶材质）。
 * 用 {@link #TRIMMED} 状态表示被剪刀修剪：修剪后不再生长，材质固定不变。
 * 不注册方块物品，破坏时掉落无花果（fig）。
 */
public class FigVineBlock extends Block {
    public static final BooleanProperty HEAD = BooleanProperty.create("head");
    public static final BooleanProperty TRIMMED = BooleanProperty.create("trimmed");
    private static final VoxelShape SHAPE = box(1, 0, 1, 15, 16, 15);
    private static final double GROW_CHANCE = 0.1;

    public FigVineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HEAD, true).setValue(TRIMMED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HEAD, TRIMMED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // 下方已有果藤 → 作为上方身体；否则作为底部头部
        return this.defaultBlockState().setValue(HEAD, !context.getLevel().getBlockState(context.getClickedPos().below()).is(this));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);
        return aboveState.is(this)
                || aboveState.is(ModBlocks.WIND_MOOR_LEAVES_0.get())
                || aboveState.is(ModBlocks.WIND_MOOR_LEAVES_1.get())
                || aboveState.isFaceSturdy(level, above, Direction.DOWN);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(HEAD) && !state.getValue(TRIMMED);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(TRIMMED) || !state.getValue(HEAD) || !(random.nextDouble() < GROW_CHANCE)) {
            return;
        }
        BlockPos below = pos.below();
        if (level.getBlockState(below).isAir()) {
            // 向下生成新的头部，自身转为身体
            level.setBlockAndUpdate(below, this.defaultBlockState().setValue(HEAD, true));
            level.setBlockAndUpdate(pos, state.setValue(HEAD, false));
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(Items.SHEARS) && !state.getValue(TRIMMED)) {
            if (!level.isClientSide) {
                level.setBlock(pos, state.setValue(TRIMMED, true), 3);
                itemstack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                level.playSound(null, pos, SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        if (facing == Direction.UP && !state.canSurvive(level, pos)) {
            level.scheduleTick(pos, this, 1);
        }
        if (facing == Direction.DOWN && !state.getValue(TRIMMED)) {
            return state.setValue(HEAD, !facingState.is(this));
        }
        return super.updateShape(state, facing, facingState, level, pos, facingPos);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }
}
