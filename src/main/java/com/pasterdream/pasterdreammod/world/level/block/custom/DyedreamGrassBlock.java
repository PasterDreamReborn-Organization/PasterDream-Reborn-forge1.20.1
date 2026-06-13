package com.pasterdream.pasterdreammod.world.level.block.custom;

import com.pasterdream.pasterdreammod.world.level.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;

public class DyedreamGrassBlock extends Block {
    public DyedreamGrassBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(net.minecraft.world.level.material.MapColor.COLOR_PINK)
                .sound(SoundType.GRASS)
                .strength(0.75F, 0.5F)
                .randomTicks());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canBeGrass(state, level, pos)) {
            level.setBlockAndUpdate(pos, ModBlocks.DYEDREAM_DIRT.get().defaultBlockState());
            return;
        }
        if (level.getMaxLocalRawBrightness(pos.above()) < 9) return;

        BlockState grassState = this.defaultBlockState();
        for (int i = 0; i < 4; i++) {
            BlockPos targetPos = pos.offset(
                    random.nextInt(3) - 1,
                    random.nextInt(5) - 3,
                    random.nextInt(3) - 1);
            BlockState targetState = level.getBlockState(targetPos);
            if (targetState.is(ModBlocks.DYEDREAM_DIRT.get()) && canPropagate(grassState, level, targetPos)) {
                level.setBlockAndUpdate(targetPos, grassState);
            }
        }
    }

    private static boolean canBeGrass(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);
        if (!aboveState.isAir()) return false;
        if (aboveState.getFluidState().getAmount() == 8) return false;
        return level.getMaxLocalRawBrightness(above) >= level.getMaxLightLevel();
    }

    private static boolean canPropagate(BlockState state, LevelReader level, BlockPos pos) {
        return canBeGrass(state, level, pos) && !level.getFluidState(pos.above()).is(net.minecraft.tags.FluidTags.WATER);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction direction, IPlantable plantable) {
        return true;
    }
}
