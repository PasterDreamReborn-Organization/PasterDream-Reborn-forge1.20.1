package com.pasterdream.pasterdreammod.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

/**
 * 风岛芦苇：双层芦苇类植物。
 * 原版 {@link DoublePlantBlock} 在另一半被移除时，会通过 {@code updateShape} 返回空气、
 * 由 {@code Block.updateOrDestroy} 以「空手」掉落另一半（导致另一半走无剪刀/精准分支）。
 * 这里覆写 {@link #updateShape}，在检测到另一半消失时用 {@link Block#UPDATE_SUPPRESS_DROPS}
 * 静默移除自身，使战利品表只对被破坏的那一半生效，且不破坏原版的破坏粒子与掉落流程。
 */
public class WindIslandReedBlock extends DoublePlantBlock {
    public WindIslandReedBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState,
                                  LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        DoubleBlockHalf half = state.getValue(HALF);
        Direction partnerDir = half == DoubleBlockHalf.UPPER ? Direction.DOWN : Direction.UP;
        if (facing == partnerDir && !facingState.is(this)) {
            // 另一半已被移除：静默移除自身，不掉落（避免双重掉落 / 空手分支产物）。
            if (!level.isClientSide()) {
                level.setBlock(currentPos, Blocks.AIR.defaultBlockState(),
                        Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS | Block.UPDATE_SUPPRESS_DROPS, 512);
            }
            return state;
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }
}
