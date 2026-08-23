package com.pasterdream.pasterdreammod.world.block.shadow;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ShadowNyliumBlock extends Block implements BonemealableBlock {

    private static final int BONEMEAL_RADIUS = 3;

    public ShadowNyliumBlock(Properties properties) {
        super(properties.randomTicks());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canSurviveHere(state, level, pos)) {
            level.setBlockAndUpdate(pos, ModBlocks.SHADOW_STONE.get().defaultBlockState());
            return;
        }
        RandomSource localRandom = RandomSource.create();
        for (int i = 0; i < 4; i++) {
            BlockPos targetPos = pos.offset(
                    localRandom.nextInt(3) - 1,
                    localRandom.nextInt(5) - 3,
                    localRandom.nextInt(3) - 1);
            BlockState targetState = level.getBlockState(targetPos);
            if (targetState.is(ModBlocks.SHADOW_STONE.get()) && canSpreadTo(level, targetPos)) {
                level.setBlockAndUpdate(targetPos, this.defaultBlockState());
            }
        }
    }

    private static boolean canSurviveHere(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);
        return !aboveState.isSolidRender(level, above) && aboveState.getFluidState().getAmount() < 8;
    }

    private static boolean canSpreadTo(LevelReader level, BlockPos pos) {
        return canSurviveHere(null, level, pos);
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

            // move down to the top nylium block
            while (cursor.getY() > level.getMinBuildHeight()
                    && !level.getBlockState(cursor).is(ModBlocks.SHADOW_NYLIUM.get())) {
                cursor.move(Direction.DOWN);
            }
            if (!level.getBlockState(cursor).is(ModBlocks.SHADOW_NYLIUM.get())) continue;

            BlockPos above = cursor.above();
            if (!level.getBlockState(above).isAir()) continue;

            BlockState plant = pickShadowPlant(random);
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

    /** 阴影菌岩可催生的灯影植物 */
    private static BlockState pickShadowPlant(RandomSource random) {
        return switch (random.nextInt(6)) {
            case 0 -> ModBlocks.SHADOW_SPROUTS.get().defaultBlockState();
            case 1 -> ModBlocks.SHADOW_FERN.get().defaultBlockState();
            case 2 -> ModBlocks.SHADOW_STEM_FERN.get().defaultBlockState();
            case 3 -> ModBlocks.SHADOW_SHORT_ROOTS.get().defaultBlockState();
            case 4 -> ModBlocks.SHADOW_ROOTS.get().defaultBlockState();
            default -> ModBlocks.SHADOW_FUNGUS.get().defaultBlockState();
        };
    }
}