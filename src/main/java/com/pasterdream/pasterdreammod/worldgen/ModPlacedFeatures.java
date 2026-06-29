package com.pasterdream.pasterdreammod.worldgen;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
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
    public static final ResourceKey<PlacedFeature> DYEDREAM_COROLLA_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_corolla_patch"));
    public static final ResourceKey<PlacedFeature> LIGHT_BALL_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "light_ball_patch"));
    public static final ResourceKey<PlacedFeature> CLOUD_CROP_PATCH =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "cloud_crop_patch"));
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
    // ===== 方解石笋 =====
    public static final ResourceKey<PlacedFeature> CALCITE_STALICRIPE =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "calcite_stalicripe"));
    public static final ResourceKey<PlacedFeature> SMALL_CALCITE_STALICRIPE =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "small_calcite_stalicripe"));

    public static final ResourceKey<PlacedFeature> GOLDENROD_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "goldenrod_patch"));
    public static final ResourceKey<PlacedFeature> FERRARIA_CRISPA_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "ferraria_crispa_patch"));
    public static final ResourceKey<PlacedFeature> MALVA_SINENSIS_CAVAN_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "malva_sinensis_cavan_patch"));
    public static final ResourceKey<PlacedFeature> EUSTOMA_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "eustoma_patch"));
    public static final ResourceKey<PlacedFeature> OATS_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "oats_patch"));
    public static final ResourceKey<PlacedFeature> RYE_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "rye_patch"));
    public static final ResourceKey<PlacedFeature> WHITE_COROLLA_CROP_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "white_corolla_crop_patch"));
    public static final ResourceKey<PlacedFeature> COTTON_CROP_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "cotton_crop_patch"));

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

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> cf = context.lookup(Registries.CONFIGURED_FEATURE);

        // CountPlacement.of()是在每个区块内尝试生成多少个
        // HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));
        // 表示生成的Y轴范围

        // 染梦树 — MOTION_BLOCKING 高度图 + would_survive
        context.register(DYEDREAM_TREE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_TREE),
                List.of(CountPlacement.of(2), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                        PlacementUtils.filteredByBlockSurvival(ModBlocks.DYEDREAM_SAPLING.get()))));

        // 染梦冰柱 — MOTION_BLOCKING 高度图
        context.register(DYEDREAM_ICE_PILLAR, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_ICE_PILLAR),
                List.of(CountPlacement.of(1), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING))));

        // 冰晶岩团块 — 地下 y=-64~64
        context.register(DYEDREAM_ICE_STONE_BLOBS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_ICE_STONE_BLOBS),
                List.of(CountPlacement.of(12), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));
        // 染梦冰团块 — y=-64~64
        context.register(DYEDREAM_ICE_BLOBS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_ICE_BLOBS),
                List.of(CountPlacement.of(1), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));
        // 染梦浮冰团块 — y=-64~64
        context.register(DYEDREAM_PACKED_ICE_BLOBS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_PACKED_ICE_BLOBS),
                List.of(CountPlacement.of(1), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));
        // 原版冰团块 — y=-64~64
        context.register(VANILLA_ICE_BLOBS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.VANILLA_ICE_BLOBS),
                List.of(CountPlacement.of(1), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));
        // 原版浮冰团块 — y=-64~64
        context.register(VANILLA_PACKED_ICE_BLOBS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.VANILLA_PACKED_ICE_BLOBS),
                List.of(CountPlacement.of(1), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));
        // 方解石团块 — 原作 ground_feature_dyedream_15: count=2, MOTION_BLOCKING
        context.register(CALCITE_BOULDER, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_BOULDER),
                List.of(CountPlacement.of(1), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING))));


        // === 染梦维度花草 ===
        // 茎草 — WORLD_SURFACE_WG
        context.register(STEM_GRASS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.STEM_GRASS_PATCH),
                List.of(CountPlacement.of(5), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        // 高茎草 — WORLD_SURFACE_WG
        context.register(TALL_STEM_GRASS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.TALL_STEM_GRASS_PATCH),
                List.of(CountPlacement.of(3), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        // 野生梦染茶花 — 团簇稀疏
        context.register(DYEDREAM_COROLLA_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_COROLLA_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        // 野生流明堇 — 团簇稀疏
        context.register(LIGHT_BALL_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.LIGHT_BALL_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        // 野生玲云花 — 团簇稀疏
        context.register(CLOUD_CROP_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CLOUD_CROP_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        // 染梦铃兰
        context.register(DYEDREAM_LILY_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_LILY_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        //冶梦莲
        context.register(DREAMING_LOTUS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DREAMING_LOTUS_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        //雪绒花 — 原作 flower_16: count=5, rarity=32
        context.register(EDELWEISS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.EDELWEISS_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));
        //奇异蕨 — 原作 flower_14: count=2, rarity=32
        context.register(SINGULARITY_FERN_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SINGULARITY_FERN_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));
        //苓灯花 — 原作 flower_9: count=2, rarity=32
        context.register(LINHT_FLOWER_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.LINHT_FLOWER_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));


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

        // 方解石笋(大) — 不限制高度
        context.register(CALCITE_STALICRIPE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_STALICRIPE),
                List.of(CountPlacement.of(20), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(320)))));
        // 方解石笋(小) — 不限制高度
        context.register(SMALL_CALCITE_STALICRIPE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SMALL_CALCITE_STALICRIPE),
                List.of(CountPlacement.of(20), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(320)))));
    }
}
