package com.pasterdream.pasterdreammod.world.block;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.IPlantable;

public class DyedreamFarmlandBlock extends FarmBlock {

    public DyedreamFarmlandBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_PINK)
                .sound(SoundType.GRAVEL)
                .strength(0.6F)
                .randomTicks());
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction direction, IPlantable plantable) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int moisture = state.getValue(MOISTURE);
        if (!isNearWater(level, pos) && !level.isRainingAt(pos.above())) {
            if (moisture > 0) {
                level.setBlock(pos, state.setValue(MOISTURE, moisture - 1), 2);
            } else if (!isUnderCrops(level, pos)) {
                turnToDyedreamDirt(level, pos);
            }
        } else if (moisture < 7) {
            level.setBlock(pos, state.setValue(MOISTURE, 7), 2);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            turnToDyedreamDirt(level, pos);
        }
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!level.isClientSide && level.random.nextFloat() < fallDistance - 0.5F) {
            if (entity instanceof net.minecraft.world.entity.LivingEntity &&
                    (entity instanceof net.minecraft.world.entity.player.Player ||
                            net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(level, entity))) {
                turnToDyedreamDirt(level, pos);
            }
        }
    }

    private static void turnToDyedreamDirt(Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, pushEntitiesUp(
                level.getBlockState(pos),
                ModBlocks.DYEDREAM_DIRT.get().defaultBlockState(),
                level, pos));
    }

    private static boolean isNearWater(LevelReader level, BlockPos pos) {
        for (BlockPos checkPos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (level.getFluidState(checkPos).is(FluidTags.WATER)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isUnderCrops(BlockGetter level, BlockPos pos) {
        BlockState above = level.getBlockState(pos.above());
        Block block = above.getBlock();
        return block instanceof CropBlock || block instanceof StemBlock;
    }
}
