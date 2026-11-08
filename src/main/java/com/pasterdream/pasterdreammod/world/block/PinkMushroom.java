package com.pasterdream.pasterdreammod.world.block;

import com.pasterdream.pasterdreammod.worldgen.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PinkMushroom extends BushBlock implements BonemealableBlock {

    protected static final VoxelShape SHAPE = box(4.0, 0.0, 4.0, 12.0, 10.0, 12.0);

    public PinkMushroom(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(level, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    public boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.DIRT);
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
        var registry = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);

        BlockPos origin = find2x2Origin(level, pos);
        if (origin != null) {
            // ---- 2×2 → jungle tree ----
            BlockState[] saved = new BlockState[4];
            int idx = 0;
            for (int dx = 0; dx < 2; dx++) {
                for (int dz = 0; dz < 2; dz++) {
                    BlockPos p = origin.offset(dx, 0, dz);
                    saved[idx++] = level.getBlockState(p);
                    level.removeBlock(p, false);
                }
            }

            ConfiguredFeature<?, ?> tree = registry.get(ModConfiguredFeatures.PINK_MUSHROOM_TREE);
            if (tree == null || !tree.place(level, level.getChunkSource().getGenerator(), random, origin)) {
                idx = 0;
                for (int dx = 0; dx < 2; dx++) {
                    for (int dz = 0; dz < 2; dz++) {
                        level.setBlock(origin.offset(dx, 0, dz), saved[idx++], 3);
                    }
                }
            }
        } else {
            // ---- single → huge red-mushroom dome ----
            level.removeBlock(pos, false);

            ConfiguredFeature<?, ?> mushroom = registry.get(ModConfiguredFeatures.PINK_HUGE_MUSHROOM);
            if (mushroom == null || !mushroom.place(level, level.getChunkSource().getGenerator(), random, pos)) {
                level.setBlock(pos, state, 3);
            }
        }
    }

    // ---- 2×2 detection ----

    private BlockPos find2x2Origin(Level level, BlockPos pos) {
        BlockState self = level.getBlockState(pos);

        for (int dx = 0; dx < 2; dx++) {
            for (int dz = 0; dz < 2; dz++) {
                BlockPos candidate = pos.offset(-dx, 0, -dz);
                if (is2x2Of(level, candidate, self))
                    return candidate;
            }
        }
        return null;
    }

    private boolean is2x2Of(Level level, BlockPos origin, BlockState expected) {
        for (int dx = 0; dx < 2; dx++) {
            for (int dz = 0; dz < 2; dz++) {
                if (!level.getBlockState(origin.offset(dx, 0, dz)).is(expected.getBlock()))
                    return false;
            }
        }
        return true;
    }
}
