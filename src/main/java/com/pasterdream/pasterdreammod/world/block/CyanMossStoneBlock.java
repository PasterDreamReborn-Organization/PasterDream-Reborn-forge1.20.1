package com.pasterdream.pasterdreammod.world.block;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CyanMossStoneBlock extends Block {

    public CyanMossStoneBlock(Properties properties) {
        super(properties.randomTicks());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canBeMossy(state, level, pos)) {
            level.setBlockAndUpdate(pos, ModBlocks.CYAN_STONE.get().defaultBlockState());
            return;
        }
        if (level.getMaxLocalRawBrightness(pos.above()) < 9) return;

        BlockState mossyState = this.defaultBlockState();
        for (int i = 0; i < 4; i++) {
            BlockPos targetPos = pos.offset(
                    random.nextInt(3) - 1,
                    random.nextInt(5) - 3,
                    random.nextInt(3) - 1);
            BlockState targetState = level.getBlockState(targetPos);
            if (targetState.is(ModBlocks.CYAN_STONE.get()) && canPropagate(mossyState, level, targetPos)) {
                level.setBlockAndUpdate(targetPos, mossyState);
            }
        }
    }

    private static boolean canBeMossy(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);
        return !aboveState.isSolidRender(level, above) && aboveState.getFluidState().getAmount() < 8;
    }

    private static boolean canPropagate(BlockState state, LevelReader level, BlockPos pos) {
        return canBeMossy(state, level, pos) && !level.getFluidState(pos.above()).is(FluidTags.WATER);
    }
}
