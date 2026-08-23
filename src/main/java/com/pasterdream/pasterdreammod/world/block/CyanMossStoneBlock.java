package com.pasterdream.pasterdreammod.world.block;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CyanMossStoneBlock extends Block implements BonemealableBlock {

    private static final int BONEMEAL_RADIUS = 3;

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

    // ---- BonemealableBlock ----

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int attempt = 0; attempt < 16; attempt++) {
            int dx = random.nextInt(BONEMEAL_RADIUS * 2 + 1) - BONEMEAL_RADIUS;
            int dz = random.nextInt(BONEMEAL_RADIUS * 2 + 1) - BONEMEAL_RADIUS;
            cursor.set(pos.getX() + dx, pos.getY(), pos.getZ() + dz);

            // move down to the top mossy stone block
            while (cursor.getY() > level.getMinBuildHeight()
                    && !level.getBlockState(cursor).is(ModBlocks.CYAN_MOSS_STONE.get())) {
                cursor.move(Direction.DOWN);
            }
            if (!level.getBlockState(cursor).is(ModBlocks.CYAN_MOSS_STONE.get())) continue;

            BlockPos above = cursor.above();
            if (!level.getBlockState(above).isAir()) continue;

            BlockState plant = pickWindPlant(random);
            if (plant == null || !plant.canSurvive(level, above)) continue;

            if (plant.getBlock() instanceof DoublePlantBlock) {
                if (!level.isEmptyBlock(above.above())) continue;
                DoublePlantBlock.placeAt(level, plant, above, 3);
            } else {
                level.setBlock(above, plant, 3);
            }
            return;
        }
    }

    /** 苍青苔岩可催生的风之旅途植物 */
    private static BlockState pickWindPlant(RandomSource random) {
        return switch (random.nextInt(4)) {
            case 0 -> ModBlocks.HAIRY_MOSS.get().defaultBlockState();
            case 1 -> ModBlocks.WIND_CLEAVING_GRASS.get().defaultBlockState();
            case 2 -> ModBlocks.WIND_FEATHER_GRASS.get().defaultBlockState();
            default -> ModBlocks.WIND_ISLAND_REED.get().defaultBlockState();
        };
    }
}
