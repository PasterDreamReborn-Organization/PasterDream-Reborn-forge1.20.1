package com.pasterdream.pasterdreammod.worldgen.feature;

import com.mojang.serialization.Codec;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.worldgen.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 风之旅途水池：先生成原版湖泊，再按概率在岸边生成眠椰树。
 * <p>
 * 水池本体的形状、围边与水位完全沿用 {@link Feature#LAKE}；树木只会在水位附近、
 * 水平 3 格内有水、且眠椰树苗可以存活的实心地面上生成。
 * </p>
 */
public class WindJourneyWaterPoolFeature extends Feature<WindJourneyWaterPoolConfiguration> {

    /** 湖泊固定占据 origin 起 16×16 的水平范围，向外多扫描几格以覆盖岸边。 */
    private static final int SHORE_SCAN_MARGIN = 3;
    private static final int SHORE_SCAN_UP = 2;
    private static final int SHORE_SCAN_DOWN = 6;
    private static final int WATER_SEARCH_RADIUS = 3;
    private static final int WATER_SEARCH_UP = 1;
    private static final int WATER_SEARCH_DOWN = 3;
    private static final int MIN_TREE_DISTANCE = 5;

    public WindJourneyWaterPoolFeature(Codec<WindJourneyWaterPoolConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<WindJourneyWaterPoolConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        WindJourneyWaterPoolConfiguration config = context.config();

        // 1. 原版湖泊：保留原有水池形状、水位与围边
        if (!Feature.LAKE.place(config.lake(), level, context.chunkGenerator(), random, origin)) {
            return false;
        }

        // 2. 每个水池独立掷一次概率
        if (random.nextFloat() >= config.treeChance()) {
            return true;
        }

        Holder<ConfiguredFeature<?, ?>> tree = level.registryAccess()
                .registryOrThrow(Registries.CONFIGURED_FEATURE)
                .getHolder(ModConfiguredFeatures.SLUMBER_PALM_TREE)
                .orElse(null);
        if (tree == null) {
            return true;
        }

        List<BlockPos> candidates = collectShoreCandidates(level, origin);
        shuffle(candidates, random);

        int treeCount = config.treeCount().sample(random);
        List<BlockPos> placed = new ArrayList<>(treeCount);
        for (BlockPos base : candidates) {
            if (placed.size() >= treeCount) {
                break;
            }
            if (isTooClose(base, placed)) {
                continue;
            }
            if (tree.value().place(level, context.chunkGenerator(), random, base)) {
                placed.add(base);
            }
        }
        return true;
    }

    /** 收集水池岸边可种树的位置：实心地面、上方两格空气、树苗可存活、水平 3 格内有水。 */
    private static List<BlockPos> collectShoreCandidates(WorldGenLevel level, BlockPos origin) {
        List<BlockPos> candidates = new ArrayList<>();
        BlockState sapling = ModBlocks.SLUMBER_PALM_SAPLING.get().defaultBlockState();
        for (int x = -SHORE_SCAN_MARGIN; x <= 15 + SHORE_SCAN_MARGIN; x++) {
            for (int z = -SHORE_SCAN_MARGIN; z <= 15 + SHORE_SCAN_MARGIN; z++) {
                for (int y = -SHORE_SCAN_DOWN; y <= SHORE_SCAN_UP; y++) {
                    BlockPos base = origin.offset(x, y, z);
                    if (!level.isEmptyBlock(base) || !level.isEmptyBlock(base.above())) {
                        continue;
                    }
                    BlockState ground = level.getBlockState(base.below());
                    if (!ground.isSolid() || !sapling.canSurvive(level, base)) {
                        continue;
                    }
                    if (!isNearWater(level, base)) {
                        continue;
                    }
                    candidates.add(base);
                }
            }
        }
        return candidates;
    }

    /** 树木之间保持水平间距，避免树冠互相穿插。 */
    private static boolean isTooClose(BlockPos base, List<BlockPos> placed) {
        for (BlockPos other : placed) {
            if (base.distSqr(other) < MIN_TREE_DISTANCE * MIN_TREE_DISTANCE) {
                return true;
            }
        }
        return false;
    }

    /** 水平 3 格、垂直 -3~+1 格内是否有水（水面通常比岸边低 1 格）。 */
    private static boolean isNearWater(WorldGenLevel level, BlockPos base) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int dx = -WATER_SEARCH_RADIUS; dx <= WATER_SEARCH_RADIUS; dx++) {
            for (int dz = -WATER_SEARCH_RADIUS; dz <= WATER_SEARCH_RADIUS; dz++) {
                if (dx * dx + dz * dz > WATER_SEARCH_RADIUS * WATER_SEARCH_RADIUS) {
                    continue;
                }
                for (int dy = -WATER_SEARCH_DOWN; dy <= WATER_SEARCH_UP; dy++) {
                    pos.setWithOffset(base, dx, dy, dz);
                    if (level.getFluidState(pos).is(FluidTags.WATER)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void shuffle(List<BlockPos> positions, RandomSource random) {
        for (int i = positions.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            BlockPos tmp = positions.get(i);
            positions.set(i, positions.get(j));
            positions.set(j, tmp);
        }
    }
}
