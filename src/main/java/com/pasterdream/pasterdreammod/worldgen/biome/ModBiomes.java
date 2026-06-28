package com.pasterdream.pasterdreammod.worldgen.biome;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModBiomes {

    public static final ResourceKey<Biome> DYEDREAM_PLAINS =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_plains"));
    public static final ResourceKey<Biome> DYEDREAM_MUSHROOM_MOUNTAINS =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_mushroom_mountains"));
    public static final ResourceKey<Biome> DYEDREAM_SNOWY_PLAINS =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_snowy_plains"));
    public static final ResourceKey<Biome> DYEDREAM_FROZEN_OCEAN =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_frozen_ocean"));

    private static final ResourceKey<PlacedFeature> FREEZE_TOP_LAYER =
            ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.withDefaultNamespace("freeze_top_layer"));
    private static final ResourceKey<PlacedFeature> ICEBERG_PACKED =
            ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.withDefaultNamespace("iceberg_packed"));
    private static final ResourceKey<PlacedFeature> ICEBERG_BLUE =
            ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.withDefaultNamespace("iceberg_blue"));

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(DYEDREAM_PLAINS, new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(1.0f)
                .downfall(0.35f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .skyColor(0x79A6FF)
                        .fogColor(0xC0D8FF)
                        .waterColor(0x3F76E4)
                        .waterFogColor(0x050533)
                        .foliageColorOverride(0xFFFDC6F2)
                        .grassColorOverride(0xFFFDC6F2)
                        .ambientParticle(
                                new AmbientParticleSettings(ModParticleTypes.LEAVES_PARTICLE.get(), 0.005f))
                        .build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 20, 1, 2))
                        // TODO: pink_slime (weight 40, 2-4), pink_chicken (weight 10, 1-1)
                        .build())
                .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, carvers)
                        .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                        .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND)
                        .addCarver(GenerationStep.Carving.AIR, Carvers.CANYON)
                        .addFeature(GenerationStep.Decoration.LAKES,
                                MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                                ModPlacedFeatures.DYEDREAM_TREE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                                ModPlacedFeatures.STEM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                                ModPlacedFeatures.TALL_STEM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                                ModPlacedFeatures.DYEDREAM_COROLLA_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                                ModPlacedFeatures.LIGHT_BALL_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                                ModPlacedFeatures.CLOUD_CROP_PATCH)
                        .build())
                .build());

        context.register(DYEDREAM_MUSHROOM_MOUNTAINS, new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(1.6f)
                .downfall(0.2f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .skyColor(0x79A6FF)
                        .fogColor(0xC0D8FF)
                        .waterColor(0x3F76E4)
                        .waterFogColor(0x050533)
                        .foliageColorOverride(0xFFFCB3ED)
                        .grassColorOverride(0xFFFFABEE)
                        .build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 10, 4, 4))
                        // TODO: pink_chicken (weight 40, 4-4), pink_slime (weight 20, 4-4)
                        .build())
                .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, carvers)
                        .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                        .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND)
                        .addCarver(GenerationStep.Carving.AIR, Carvers.CANYON)
                        // TODO: dyedream_forest_tree feature
                        .build())
                .build());

        context.register(DYEDREAM_SNOWY_PLAINS, new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.0f)
                .downfall(0.7f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .skyColor(0x79A6FF)
                        .fogColor(0xC0D8FF)
                        .waterColor(0x3F76E4)
                        .waterFogColor(0x050533)
                        .foliageColorOverride(0xFFFFA9ED)
                        .grassColorOverride(0xFFFFABEE)
                        .ambientParticle(
                                new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.01f))
                        .build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 15, 1, 2))
                        // TODO: pink_slime (weight 20, 1-3)
                        .build())
                .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, carvers)
                        .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                        .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND)
                        .addCarver(GenerationStep.Carving.AIR, Carvers.CANYON)
                        .addFeature(GenerationStep.Decoration.LAKES,
                                MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                                ModPlacedFeatures.DYEDREAM_TREE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                                ModPlacedFeatures.DYEDREAM_ICE_PILLAR)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION,
                                ModPlacedFeatures.DYEDREAM_ICESTONE_BLOBS)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION,
                                FREEZE_TOP_LAYER)
                        .build())
                .build());

        // ========== 染梦冻洋 — 原作: biome_dyedream_3 ==========
        context.register(DYEDREAM_FROZEN_OCEAN, new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.7f)
                .downfall(0.25f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .skyColor(0x79A6FF)
                        .fogColor(0xC0D8FF)
                        .waterColor(0x3F76E4)
                        .waterFogColor(0x050533)
                        .foliageColorOverride(0xFFFFA9ED)
                        .grassColorOverride(0xFFFFABEE)
                        .ambientParticle(
                                new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.005f))
                        .build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 30, 1, 2))
                        .addSpawn(MobCategory.WATER_CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 20, 1, 3))
                        .build())
                .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, carvers)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS,
                                ICEBERG_PACKED)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS,
                                ICEBERG_BLUE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION,
                                ModPlacedFeatures.DYEDREAM_ICESTONE_BLOBS)
                        .build())
                .build());
    }
}
