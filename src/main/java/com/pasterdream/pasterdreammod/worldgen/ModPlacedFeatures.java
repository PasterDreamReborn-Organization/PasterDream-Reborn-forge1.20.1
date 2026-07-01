package com.pasterdream.pasterdreammod.worldgen;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;

import java.lang.reflect.Constructor;
import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> DYEDREAM_TREE =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_tree"));
    public static final ResourceKey<PlacedFeature> DYEDREAM_ICE_PILLAR =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_ice_pillar"));
    public static final ResourceKey<PlacedFeature> DYEDREAM_PACKED_ICE_PILLAR =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_packed_ice_pillar"));
    // 雪块底水池 — 原作 ground_feature_dyedream_1
    public static final ResourceKey<PlacedFeature> SNOWY_WATER_POOL =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "snowy_water_pool"));
    public static final ResourceKey<PlacedFeature> DYEDREAM_ICE_STONE_BLOBS =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_ice_stone_blobs"));
    public static final ResourceKey<PlacedFeature> CALCITE_BOULDER =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "calcite_boulder"));
    public static final ResourceKey<PlacedFeature> DYEDREAM_ICE_BLOBS =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_ice_blobs"));
    public static final ResourceKey<PlacedFeature> DYEDREAM_PACKED_ICE_BLOBS =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_packed_ice_blobs"));
    public static final ResourceKey<PlacedFeature> VANILLA_ICE_BLOBS =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "vanilla_ice_blobs"));
    public static final ResourceKey<PlacedFeature> VANILLA_PACKED_ICE_BLOBS =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "vanilla_packed_ice_blobs"));
    public static final ResourceKey<PlacedFeature> STEM_GRASS_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "stem_grass_patch"));
    public static final ResourceKey<PlacedFeature> TALL_STEM_GRASS_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "tall_stem_grass_patch"));
    public static final ResourceKey<PlacedFeature> PINK_MUSHROOM_TREE =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "pink_mushroom_tree"));
    public static final ResourceKey<PlacedFeature> PINK_HUGE_MUSHROOM =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "pink_huge_mushroom"));
    public static final ResourceKey<PlacedFeature> PINK_MUSHROOM_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "pink_mushroom_patch"));
    public static final ResourceKey<PlacedFeature> TALL_PINK_MUSHROOM_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "tall_pink_mushroom_patch"));
    public static final ResourceKey<PlacedFeature> DYEDREAM_COROLLA_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_corolla_patch"));
    public static final ResourceKey<PlacedFeature> LIGHT_BALL_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "light_ball_patch"));
    public static final ResourceKey<PlacedFeature> CLOUD_CROP_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "cloud_crop_patch"));
    public static final ResourceKey<PlacedFeature> CLOUD_PILLAR_SMALL =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "cloud_pillar_small"));
    public static final ResourceKey<PlacedFeature> CLOUD_PILLAR_LARGE =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "cloud_pillar_large"));
    public static final ResourceKey<PlacedFeature> CORAL_TREE_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "coral_tree_patch"));
    public static final ResourceKey<PlacedFeature> CORAL_CLAW_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "coral_claw_patch"));
    public static final ResourceKey<PlacedFeature> CORAL_MUSHROOM_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "coral_mushroom_patch"));
    public static final ResourceKey<PlacedFeature> SEA_PICKLE_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sea_pickle_patch"));
    // 染梦海草 — 原作 ground_feature_dyedream_5
    public static final ResourceKey<PlacedFeature> DYEDREAM_SEAGRASS_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_seagrass_patch"));
    public static final ResourceKey<PlacedFeature> DYEDREAM_LILY_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_lily_patch"));
    public static final ResourceKey<PlacedFeature> DREAMING_LOTUS_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dreaming_lotus_patch"));
    public static final ResourceKey<PlacedFeature> EDELWEISS_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "edelweiss_patch"));
    public static final ResourceKey<PlacedFeature> SINGULARITY_FERN_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "singularity_fern_patch"));
    public static final ResourceKey<PlacedFeature> LINHT_FLOWER_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "linht_flower_patch"));
    // ===== 洞穴晶芽 =====
    public static final ResourceKey<PlacedFeature> DYEDREAM_MOSS_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_moss_patch"));
    public static final ResourceKey<PlacedFeature> SMALL_DYEDREAM_BUD_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "small_dyedream_bud_patch"));
    public static final ResourceKey<PlacedFeature> MEDIUM_DYEDREAM_BUD_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "medium_dyedream_bud_patch"));
    public static final ResourceKey<PlacedFeature> LARGE_DYEDREAM_BUD_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "large_dyedream_bud_patch"));
    public static final ResourceKey<PlacedFeature> ICE_BUD_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "ice_bud_patch"));

    // ===== 染梦维度矿石 =====
    public static final ResourceKey<PlacedFeature> TITANIUM_ORE =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium_ore"));
    public static final ResourceKey<PlacedFeature> AMBER_CANDY_ORE =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "amber_candy_ore"));
    public static final ResourceKey<PlacedFeature> DYEDREAM_DUST_ORE =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_dust_ore"));
    public static final ResourceKey<PlacedFeature> DYEDREAM_QUARTZ_ORE =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_quartz_ore"));
    // ===== 染梦睡莲 / 染梦莲花 =====
    public static final ResourceKey<PlacedFeature> DYEDREAM_LILY_PAD_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_lily_pad"));
    public static final ResourceKey<PlacedFeature> DYEDREAM_LOTUS_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_lotus"));

    // ===== 方解石笋 =====
    public static final ResourceKey<PlacedFeature> CALCITE_STALICRIPE =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "calcite_stalicripe"));
    public static final ResourceKey<PlacedFeature> SMALL_CALCITE_STALICRIPE =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "small_calcite_stalicripe"));

    // ===== 染梦冻洋 — 自定义冰山 placed feature（比原版更高频率） =====
    public static final ResourceKey<PlacedFeature> DYEDREAM_ICEBERG_PACKED =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_iceberg_packed"));
    public static final ResourceKey<PlacedFeature> DYEDREAM_ICEBERG_BLUE =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_iceberg_blue"));

    // ===== 染梦海洋 — 海带 =====
    public static final ResourceKey<PlacedFeature> DYEDREAM_KELP_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_kelp_patch"));

    //原版维度花草
    public static final ResourceKey<PlacedFeature> GOLDENROD_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "goldenrod_patch"));
    public static final ResourceKey<PlacedFeature> FERRARIA_CRISPA_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "ferraria_crispa_patch"));
    public static final ResourceKey<PlacedFeature> MALVA_SINENSIS_CAVAN_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "malva_sinensis_cavan_patch"));
    public static final ResourceKey<PlacedFeature> EUSTOMA_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "eustoma_patch"));
    public static final ResourceKey<PlacedFeature> OATS_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "oats_patch"));
    public static final ResourceKey<PlacedFeature> RYE_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "rye_patch"));
    public static final ResourceKey<PlacedFeature> WHITE_COROLLA_CROP_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "white_corolla_crop_patch"));
    public static final ResourceKey<PlacedFeature> COTTON_CROP_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "cotton_crop_patch"));

    //原版维度矿石
    public static final ResourceKey<PlacedFeature> DEEPSLATE_TITANIUM_ORE_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "deepslate_titanium_ore_patch"));
    public static final ResourceKey<PlacedFeature> MOLTEN_GOLD_ORE_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "molten_gold_ore_patch"));
    public static final ResourceKey<PlacedFeature> SOUL_ORE_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "soul_ore_patch"));


    /** HeightmapPlacement 在 Forge 1.20.1 中构造函数为 private，通过反射创建 */
    private static PlacementModifier onHeightmap(Heightmap.Types type) {
        try {
            Constructor<HeightmapPlacement> ctor = HeightmapPlacement.class.getDeclaredConstructor(Heightmap.Types.class);
            ctor.setAccessible(true);
            return ctor.newInstance(type);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create HeightmapPlacement", e);
        }
    }

    /** 限制植物只能生成在染梦地表方块（染梦草方块/染梦泥土）上 */
    private static final PlacementModifier ON_DYEDREAM_GROUND = BlockPredicateFilter.forPredicate(
            BlockPredicate.matchesBlocks(
                    Direction.DOWN.getNormal(),
                    ModBlocks.DYEDREAM_GRASS_BLOCK.get(),
                    ModBlocks.DYEDREAM_DIRT.get()));

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> cf = context.lookup(Registries.CONFIGURED_FEATURE);

        // CountPlacement.of()是在每个区块内尝试生成多少个
        // RarityFilter.onAverageOnceEvery(16)是平均每16个区块生成1个
        // HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));
        // 表示生成的Y轴范围
        // 可以用SurfaceWaterDepthFilter.forMaxDepth(0)避免在水上生成
        
        // 染梦树 — MOTION_BLOCKING 高度图 + would_survive
        context.register(DYEDREAM_TREE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_TREE),
                List.of(CountPlacement.of(2), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                        PlacementUtils.filteredByBlockSurvival(ModBlocks.DYEDREAM_SAPLING.get()))));

        // 染梦冰柱 — MOTION_BLOCKING 高度图
        context.register(DYEDREAM_ICE_PILLAR, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_ICE_PILLAR),
                List.of(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING))));

        context.register(DYEDREAM_PACKED_ICE_PILLAR, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_PACKED_ICE_PILLAR),
                List.of(RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING))));

        // 雪块底水池 — 原作 ground_feature_dyedream_1, 每2区块平均1个
        context.register(SNOWY_WATER_POOL, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SNOWY_WATER_POOL),
                List.of(RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING))));

        // ===== 染梦海洋 — 海带 =====
        ResourceKey<ConfiguredFeature<?, ?>> VANILLA_KELP_CF =
                ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.withDefaultNamespace("kelp"));
        context.register(DYEDREAM_KELP_PATCH, new PlacedFeature(
                cf.getOrThrow(VANILLA_KELP_CF),
                List.of(CountPlacement.of(8), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR))));

        // ===== 染梦冻洋 — 自定义冰山 placed feature =====
        // 引用原版 configured feature，但使用更高的生成频率（原版 packed=1/16, blue=1/32 区块）
        ResourceKey<ConfiguredFeature<?, ?>> VANILLA_ICEBERG_PACKED_CF =
                ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.withDefaultNamespace("iceberg_packed"));
        ResourceKey<ConfiguredFeature<?, ?>> VANILLA_ICEBERG_BLUE_CF =
                ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.withDefaultNamespace("iceberg_blue"));
        // packed 冰山：每 8 个区块生成 1 个（原版为 1/16）
        context.register(DYEDREAM_ICEBERG_PACKED, new PlacedFeature(
                cf.getOrThrow(VANILLA_ICEBERG_PACKED_CF),
                List.of(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR))));
        // blue 冰山：每 16 个区块生成 1 个（原版为 1/32）
        context.register(DYEDREAM_ICEBERG_BLUE, new PlacedFeature(
                cf.getOrThrow(VANILLA_ICEBERG_BLUE_CF),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR))));

        // 冰晶岩团块 — 地下 y=-64~64
        context.register(DYEDREAM_ICE_STONE_BLOBS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_ICE_STONE_BLOBS),
                List.of(CountPlacement.of(12), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));
        // 染梦冰团块 — y=-64~64
        context.register(DYEDREAM_ICE_BLOBS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_ICE_BLOBS),
                List.of(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));
        // 染梦浮冰团块 — y=-64~64
        context.register(DYEDREAM_PACKED_ICE_BLOBS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_PACKED_ICE_BLOBS),
                List.of(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));
        // 原版冰团块 — y=-64~64
        context.register(VANILLA_ICE_BLOBS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.VANILLA_ICE_BLOBS),
                List.of(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));
        // 原版浮冰团块 — y=-64~64
        context.register(VANILLA_PACKED_ICE_BLOBS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.VANILLA_PACKED_ICE_BLOBS),
                List.of(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));
        // 方解石团块 — WORLD_SURFACE_WG + SurfaceWaterDepth 避免水上生成
        context.register(CALCITE_BOULDER, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
                        // 避免水上生成(水深不超过0)
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));


        // === 染梦维度花草 ===
        // 茎草 — WORLD_SURFACE_WG
        context.register(STEM_GRASS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.STEM_GRASS_PATCH),
                List.of(CountPlacement.of(5), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        // 高茎草 — WORLD_SURFACE_WG
        context.register(TALL_STEM_GRASS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.TALL_STEM_GRASS_PATCH),
                List.of(CountPlacement.of(3), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        // 粉顶菌巨树 — 仅生成在染梦草方块/染梦泥土上
        context.register(PINK_MUSHROOM_TREE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.PINK_MUSHROOM_TREE),
                List.of(CountPlacement.of(1), InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(
                                Direction.DOWN.getNormal(),
                                ModBlocks.DYEDREAM_GRASS_BLOCK.get(),
                                ModBlocks.DYEDREAM_DIRT.get())))));

        // 粉顶菌巨菇 — MOTION_BLOCKING 高度图，避免水上生成
        context.register(PINK_HUGE_MUSHROOM, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.PINK_HUGE_MUSHROOM),
                List.of(CountPlacement.of(2), InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                        ON_DYEDREAM_GROUND)));

        // 粉顶菌 (小型地表) — WORLD_SURFACE_WG
        context.register(PINK_MUSHROOM_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.PINK_MUSHROOM_PATCH),
                List.of(CountPlacement.of(5), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        // 高粉顶菌 (地表) — WORLD_SURFACE_WG
        context.register(TALL_PINK_MUSHROOM_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.TALL_PINK_MUSHROOM_PATCH),
                List.of(CountPlacement.of(3), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        // 野生梦染茶花 — 团簇稀疏
        context.register(DYEDREAM_COROLLA_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_COROLLA_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        // 野生流明堇 — 团簇稀疏
        context.register(LIGHT_BALL_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.LIGHT_BALL_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        // 野生玲云花 — 团簇稀疏
        context.register(CLOUD_CROP_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CLOUD_CROP_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        // 云团柱 — SP, rarity=4, MOTION_BLOCKING
        context.register(CLOUD_PILLAR_SMALL, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CLOUD_PILLAR_SMALL),
                List.of(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING))));
        context.register(CLOUD_PILLAR_LARGE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CLOUD_PILLAR_LARGE),
                List.of(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING))));

        // ===== 水下装饰（OCEAN_FLOOR 放置于海床） =====
        context.register(CORAL_TREE_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CORAL_TREE_PATCH),
                List.of(CountPlacement.of(2), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR))));
        context.register(CORAL_CLAW_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CORAL_CLAW_PATCH),
                List.of(CountPlacement.of(2), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR))));
        context.register(CORAL_MUSHROOM_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CORAL_MUSHROOM_PATCH),
                List.of(CountPlacement.of(2), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR))));
        context.register(SEA_PICKLE_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SEA_PICKLE_PATCH),
                List.of(CountPlacement.of(4), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR))));
        // 染梦海草 — 原作 ground_feature_dyedream_5: count=64, OCEAN_FLOOR_WG, y=20~59
        context.register(DYEDREAM_SEAGRASS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_SEAGRASS_PATCH),
                List.of(CountPlacement.of(64), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(20), VerticalAnchor.absolute(59)))));

        // 染梦铃兰
        context.register(DYEDREAM_LILY_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_LILY_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        //冶梦莲
        context.register(DREAMING_LOTUS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DREAMING_LOTUS_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        //雪绒花 — 原作 flower_16: count=5, rarity=32
        context.register(EDELWEISS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.EDELWEISS_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));
        //奇异蕨 — 原作 flower_14: count=2, rarity=32
        context.register(SINGULARITY_FERN_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SINGULARITY_FERN_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));
        //苓灯花 — 原作 flower_9: count=2, rarity=32
        context.register(LINHT_FLOWER_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.LINHT_FLOWER_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        //秋麒麟
        context.register(GOLDENROD_PATCH, new PlacedFeature(cf.getOrThrow(ModConfiguredFeatures.GOLDENROD_PATCH), List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        //魔星兰
        context.register(FERRARIA_CRISPA_PATCH, new PlacedFeature(cf.getOrThrow(ModConfiguredFeatures.FERRARIA_CRISPA_PATCH), List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        //锦葵
        context.register(MALVA_SINENSIS_CAVAN_PATCH, new PlacedFeature(cf.getOrThrow(ModConfiguredFeatures.MALVA_SINENSIS_CAVAN_PATCH), List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        //洋桔梗
        context.register(EUSTOMA_PATCH, new PlacedFeature(cf.getOrThrow(ModConfiguredFeatures.EUSTOMA_PATCH), List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        //洋麦
        context.register(OATS_PATCH, new PlacedFeature(cf.getOrThrow(ModConfiguredFeatures.OATS_PATCH), List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        //兰麦
        context.register(RYE_PATCH, new PlacedFeature(cf.getOrThrow(ModConfiguredFeatures.RYE_PATCH), List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        //苍白雪莲植株
        context.register(WHITE_COROLLA_CROP_PATCH, new PlacedFeature(cf.getOrThrow(ModConfiguredFeatures.WHITE_COROLLA_CROP_PATCH), List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        //棉花植株
        context.register(COTTON_CROP_PATCH, new PlacedFeature(cf.getOrThrow(ModConfiguredFeatures.COTTON_CROP_PATCH), List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        //深层钛矿石
        context.register(DEEPSLATE_TITANIUM_ORE_PATCH, new PlacedFeature(cf.getOrThrow(ModConfiguredFeatures.DEEPSLATE_TITANIUM_ORE_PATCH), List.of(CountPlacement.of(8), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0)))));

        //炙焰金矿石
        context.register(MOLTEN_GOLD_ORE_PATCH, new PlacedFeature(cf.getOrThrow(ModConfiguredFeatures.MOLTEN_GOLD_ORE_PATCH), List.of(CountPlacement.of(4), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(64)))));

        //灵魂矿土
        context.register(SOUL_ORE_PATCH, new PlacedFeature(cf.getOrThrow(ModConfiguredFeatures.SOUL_ORE_PATCH), List.of(CountPlacement.of(8), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128)))));

        // ===== 染梦维度矿石 =====
        // 钛矿 — 钻石分布: count=7, triangle aboveBottom(-80)~aboveBottom(80)
        context.register(TITANIUM_ORE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.TITANIUM_ORE),
                List.of(CountPlacement.of(7), InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

        // 琥珀糖矿 — count=10, y=-60~320（含团块层）
        context.register(AMBER_CANDY_ORE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.AMBER_CANDY_ORE),
                List.of(CountPlacement.of(10), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(320)))));

        // 染梦粉尘矿 — count=5, y=-60~320（含团块层）
        context.register(DYEDREAM_DUST_ORE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_DUST_ORE),
                List.of(CountPlacement.of(5), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(320)))));

        // 染梦石英矿 — count=18, y=-60~320（含团块层）
        context.register(DYEDREAM_QUARTZ_ORE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_QUARTZ_ORE),
                List.of(CountPlacement.of(18), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(320)))));

        // 染梦睡莲 — 原作 dyedream_lily_pad: count=7, rarity=32
        context.register(DYEDREAM_LILY_PAD_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_LILY_PAD_PATCH),
                List.of(CountPlacement.of(7), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        // 染梦莲花 — 原作 dyedream_lotus: count=6, rarity=32
        context.register(DYEDREAM_LOTUS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_LOTUS_PATCH),
                List.of(CountPlacement.of(6), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        // 方解石笋(大) — 不限制高度
        context.register(CALCITE_STALICRIPE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_STALICRIPE),
                List.of(CountPlacement.of(20), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(320)))));
        // 染梦苔藓 — 同方解石笋，不限制高度
        context.register(DYEDREAM_MOSS_PATCH, new PlacedFeature(cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_MOSS_PATCH), List.of(CountPlacement.of(20), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(320)))));
        // 方解石笋(小) — 不限制高度
        context.register(SMALL_CALCITE_STALICRIPE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SMALL_CALCITE_STALICRIPE),
                List.of(CountPlacement.of(20), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(320)))));

        // ===== 洞穴晶芽 =====
        // 染梦晶芽 x3 — 所有群系,
        context.register(SMALL_DYEDREAM_BUD_PATCH, new PlacedFeature(cf.getOrThrow(ModConfiguredFeatures.SMALL_DYEDREAM_BUD_PATCH), List.of(CountPlacement.of(6), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(320)))));
        context.register(MEDIUM_DYEDREAM_BUD_PATCH, new PlacedFeature(cf.getOrThrow(ModConfiguredFeatures.MEDIUM_DYEDREAM_BUD_PATCH), List.of(CountPlacement.of(6), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(320)))));
        context.register(LARGE_DYEDREAM_BUD_PATCH, new PlacedFeature(cf.getOrThrow(ModConfiguredFeatures.LARGE_DYEDREAM_BUD_PATCH), List.of(CountPlacement.of(6), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(320)))));
        // 冰晶芽 — SP+FO,
        context.register(ICE_BUD_PATCH, new PlacedFeature(cf.getOrThrow(ModConfiguredFeatures.ICE_BUD_PATCH), List.of(CountPlacement.of(6), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(320)))));
    }
}
