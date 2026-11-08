package com.pasterdream.pasterdreammod.world.block;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.worldgen.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
            } else {
                scatterShroomlights(level, random, pos);
            }
        }
    }

    /** Scatters PINK_SHROOMLIGHT blocks around the stem inside the huge mushroom cap. */
    private void scatterShroomlights(ServerLevel level, RandomSource random, BlockPos center) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        BlockState shroomlight = ModBlocks.PINK_SHROOMLIGHT.get().defaultBlockState();
        BlockPos stemSurface = findStemSurface(level, center);
        int stemBottom = stemSurface != null ? stemSurface.getY() : center.getY();

        for (int y = stemBottom + 2; y <= stemBottom + 7; y++) {
            for (int dx = -2; dx <= 2; dx++) {
                for (int dz = -2; dz <= 2; dz++) {
                    // only inside the cap radius (≈3) and not right at the stem core
                    int dist2 = dx * dx + dz * dz;
                    if (dist2 < 2 || dist2 > 9) continue;

                    if (random.nextFloat() > 0.3f) continue;

                    cursor.set(center.getX() + dx, y, center.getZ() + dz);
                    if (level.getBlockState(cursor).isAir()
                            && hasAdjacentStemOrCap(level, cursor)) {
                        level.setBlock(cursor, shroomlight, 3);
                    }
                }
            }
        }
    }

    /** Finds the highest stem block near {@code center} to guess the mushroom base. */
    private BlockPos findStemSurface(ServerLevel level, BlockPos center) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int y = center.getY() + 10; y >= center.getY(); y--) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    cursor.set(center.getX() + dx, y, center.getZ() + dz);
                    if (level.getBlockState(cursor).is(ModBlocks.PINK_MUSHROOM_STEM.get())) {
                        // walk down to the base of this stem column
                        while (y > center.getY()
                                && level.getBlockState(cursor.set(cursor.getX(), cursor.getY() - 1, cursor.getZ()))
                                        .is(ModBlocks.PINK_MUSHROOM_STEM.get())) {
                            y--;
                        }
                        return cursor.immutable();
                    }
                }
            }
        }
        return null;
    }

    private boolean hasAdjacentStemOrCap(ServerLevel level, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockState neighbor = level.getBlockState(pos.relative(dir));
            if (neighbor.is(ModBlocks.PINK_MUSHROOM_STEM.get())
                    || neighbor.is(ModBlocks.PINK_MUSHROOM_BLOCK.get())) {
                return true;
            }
        }
        return false;
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
