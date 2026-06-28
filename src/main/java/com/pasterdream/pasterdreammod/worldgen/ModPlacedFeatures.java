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
    public static final ResourceKey<PlacedFeature> DYEDREAM_ICESTONE_BLOBS =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_icestone_blobs"));
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
        context.register(DYEDREAM_ICESTONE_BLOBS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_ICESTONE_BLOBS),
                List.of(CountPlacement.of(20), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));

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
    }
}
