package com.pasterdream.pasterdreammod.worldgen.biome;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
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
    public static final ResourceKey<Biome> DYEDREAM_OCEAN =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_ocean"));
    public static final ResourceKey<Biome> DYEDREAM_COLD_OCEAN =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_cold_ocean"));
    public static final ResourceKey<Biome> DYEDREAM_BEACH =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_beach"));
    public static final ResourceKey<Biome> DYEDREAM_RIVER =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_river"));
    public static final ResourceKey<Biome> DYEDREAM_FROZEN_RIVER =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_frozen_river"));
    public static final ResourceKey<Biome> DYEDREAM_SNOWY_PEAKS =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_snowy_peaks"));
    public static final ResourceKey<Biome> DYEDREAM_CHERRY_GROVE =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_cherry_grove"));
    public static final ResourceKey<Biome> DYEDREAM_FOREST =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_forest"));
    public static final ResourceKey<Biome> DYEDREAM_FLOWER_FIELD =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_flower_field"));
    public static final ResourceKey<Biome> DYEDREAM_CAVES =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_caves"));
    public static final ResourceKey<Biome> DYEDREAM_LUSH_CAVES =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_lush_caves"));
    public static final ResourceKey<Biome> DYEDREAM_DRIPSTONE_CAVES =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_dripstone_caves"));

    // ===== 灯影之下维度群系 =====
    public static final ResourceKey<Biome> SHADOW_NYLIUM_WASTES =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_nylium_wastes"));
    public static final ResourceKey<Biome> SHADOW_FOREST =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_forest"));
    public static final ResourceKey<Biome> SHADOW_RUINS =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ruins"));
    public static final ResourceKey<Biome> SHADOW_OCEAN =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ocean"));

    // ===== 风之旅途维度群系 =====
    public static final ResourceKey<Biome> WIND_MOOR_ARCHIPELAGO =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_moor_archipelago"));
    public static final ResourceKey<Biome> MISTY_DREAM_CLOUD_LAYER =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "misty_dream_cloud_layer"));

    // ===== 亚伦柯斯竞技场维度群系 =====
    public static final ResourceKey<Biome> AARONCOS_ARENA =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_arena"));

    private static final ResourceKey<SoundEvent> SWEET_DREAM_MUSIC_KEY =
            ResourceKey.create(Registries.SOUND_EVENT,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sweet_dream_music"));
    private static final ResourceKey<SoundEvent> SNOWFALL_DREAM_MUSIC_KEY =
            ResourceKey.create(Registries.SOUND_EVENT,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "snowfall_dream_music"));
    private static final ResourceKey<SoundEvent> BREEZE_WIND_KEY =
            ResourceKey.create(Registries.SOUND_EVENT,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "breeze_wind"));
    private static final ResourceKey<SoundEvent> WIND_JOURNEY_MUSIC_KEY =
            ResourceKey.create(Registries.SOUND_EVENT,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey"));
    private static final ResourceKey<SoundEvent> SHADOW_BIOME_KEY =
            ResourceKey.create(Registries.SOUND_EVENT,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_biome"));
    private static final int MUSIC_MIN_DELAY = 12000;
    private static final int MUSIC_MAX_DELAY = 24000;

    private static Music warmMusic() {
        return new Music(BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(SWEET_DREAM_MUSIC_KEY),
                MUSIC_MIN_DELAY, MUSIC_MAX_DELAY, false);
    }

    private static Music coldMusic() {
        return new Music(BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(SNOWFALL_DREAM_MUSIC_KEY),
                MUSIC_MIN_DELAY, MUSIC_MAX_DELAY, false);
    }

    private static Music windJourneyMusic() {
        return new Music(BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(WIND_JOURNEY_MUSIC_KEY),
                MUSIC_MIN_DELAY, MUSIC_MAX_DELAY, true);
    }

    private static final ResourceKey<PlacedFeature> FREEZE_TOP_LAYER =
            ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath("minecraft", "freeze_top_layer"));
    private static final ResourceKey<PlacedFeature> BAMBOO_LIGHT =
            ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath("minecraft", "bamboo_light"));

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(DYEDREAM_PLAINS, dyedreamPlains(placedFeatures, carvers));
        context.register(DYEDREAM_MUSHROOM_MOUNTAINS, dyedreamMushroomMountains(placedFeatures, carvers));
        context.register(DYEDREAM_SNOWY_PLAINS, dyedreamSnowyPlains(placedFeatures, carvers));
        context.register(DYEDREAM_FROZEN_OCEAN, dyedreamFrozenOcean(placedFeatures, carvers));
        context.register(DYEDREAM_COLD_OCEAN, dyedreamColdOcean(placedFeatures, carvers));
        context.register(DYEDREAM_OCEAN, dyedreamOcean(placedFeatures, carvers));
        context.register(DYEDREAM_BEACH, dyedreamBeach(placedFeatures, carvers));
        context.register(DYEDREAM_RIVER, dyedreamRiver(placedFeatures, carvers));
        context.register(DYEDREAM_FROZEN_RIVER, dyedreamFrozenRiver(placedFeatures, carvers));
        context.register(DYEDREAM_SNOWY_PEAKS, dyedreamSnowyPeaks(placedFeatures, carvers));
        context.register(DYEDREAM_CHERRY_GROVE, dyedreamCherryGrove(placedFeatures, carvers));
        context.register(DYEDREAM_FOREST, dyedreamForest(placedFeatures, carvers));
        context.register(DYEDREAM_FLOWER_FIELD, dyedreamFlowerField(placedFeatures, carvers));
        context.register(DYEDREAM_CAVES, dyedreamCaves(placedFeatures, carvers));
        context.register(DYEDREAM_LUSH_CAVES, dyedreamLushCaves(placedFeatures, carvers));
        context.register(DYEDREAM_DRIPSTONE_CAVES, dyedreamDripstoneCaves(placedFeatures, carvers));

        // 灯影之下占位群系（待后续细化）
        context.register(SHADOW_NYLIUM_WASTES, shadowNyliumWastes(placedFeatures, carvers));
        context.register(SHADOW_FOREST, shadowForest(placedFeatures, carvers));
        context.register(SHADOW_RUINS, shadowRuins(placedFeatures, carvers));
        context.register(SHADOW_OCEAN, shadowOcean(placedFeatures, carvers));

        // 风之旅途
        context.register(WIND_MOOR_ARCHIPELAGO, windMoorArchipelago(placedFeatures, carvers));
        context.register(MISTY_DREAM_CLOUD_LAYER, mistyDreamCloudLayer(placedFeatures, carvers));

        // 亚伦柯斯竞技场（虚空占位群系：无地物 / 无生成 / 无雕刻器）
        context.register(AARONCOS_ARENA, aaroncosArenaBiome(placedFeatures, carvers));
    }

    // ==================== 共享辅助方法 ====================

    /** sky / fog / water 颜色：所有染梦群系完全相同 */
    private static BiomeSpecialEffects.Builder commonEffects() {
        return new BiomeSpecialEffects.Builder()
                .skyColor(0x79A6FF)
                .fogColor(0xC0D8FF)
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533);
    }

    /** 寒冷群系的 foliage / grass 颜色 */
    private static void applyColdFoliage(BiomeSpecialEffects.Builder effects) {
        effects.foliageColorOverride(0xFFFFA9ED)
                .grassColorOverride(0xFFFFABEE);
    }

    // ==================== 各群系构建方法 ====================

    private static Biome dyedreamPlains(HolderGetter<PlacedFeature> placedFeatures,
                                         HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFDC6F2)
                .grassColorOverride(0xFFFDC6F2)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.005f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(1.0f)
                .downfall(0.35f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 20, 1, 2))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(ModEntities.PINK_SLIME.get(), 40, 2, 4))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(ModEntities.PINK_CHICKEN.get(), 10, 1, 1))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamMushroomMountains(HolderGetter<PlacedFeature> placedFeatures,
                                                    HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFCB3ED)
                .grassColorOverride(0xFFFFABEE)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.005f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(1.6f)
                .downfall(0.2f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 10, 4, 4))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(ModEntities.PINK_CHICKEN.get(), 40, 4, 4))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(ModEntities.PINK_SLIME.get(), 20, 4, 4))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamSnowyPlains(HolderGetter<PlacedFeature> placedFeatures,
                                              HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects();
        applyColdFoliage(effects);
        effects.ambientParticle(new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.01f))
                .backgroundMusic(coldMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.0f)
                .downfall(0.7f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 15, 1, 2))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(ModEntities.PINK_SLIME.get(), 20, 1, 3))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamFrozenOcean(HolderGetter<PlacedFeature> placedFeatures,
                                              HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects();
        applyColdFoliage(effects);
        effects.ambientParticle(new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.005f))
                .backgroundMusic(coldMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.7f)
                .downfall(0.25f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 30, 1, 2))
                        .addSpawn(MobCategory.WATER_CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 20, 1, 3))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.COD, 15, 1, 5))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.SALMON, 15, 1, 5))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamOcean(HolderGetter<PlacedFeature> placedFeatures,
                                        HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFDC6F2)
                .grassColorOverride(0xFFFDC6F2)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.003f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(1.2f)
                .downfall(0.5f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 20, 1, 2))
                        .addSpawn(MobCategory.WATER_CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 15, 1, 3))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.COD, 15, 1, 5))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.SALMON, 15, 1, 5))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.PUFFERFISH, 1, 1, 3))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 8, 8))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamColdOcean(HolderGetter<PlacedFeature> placedFeatures,
                                            HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFDC6F2)
                .grassColorOverride(0xFFFDC6F2)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.003f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.4f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 20, 1, 2))
                        .addSpawn(MobCategory.WATER_CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 15, 1, 3))
                        .addSpawn(MobCategory.WATER_CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.SQUID, 1, 1, 4))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.COD, 15, 1, 5))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.SALMON, 15, 1, 5))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamBeach(HolderGetter<PlacedFeature> placedFeatures,
                                        HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFDC6F2)
                .grassColorOverride(0xFFFDC6F2)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.01f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.8f)
                .downfall(0.4f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 20, 1, 2))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(ModEntities.PINK_SLIME.get(), 10, 1, 2))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamRiver(HolderGetter<PlacedFeature> placedFeatures,
                                        HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFDC6F2)
                .grassColorOverride(0xFFFDC6F2)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.01f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.5f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 20, 1, 2))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.SALMON, 5, 1, 5))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamFrozenRiver(HolderGetter<PlacedFeature> placedFeatures,
                                              HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects();
        applyColdFoliage(effects);
        effects.ambientParticle(new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.005f))
                .backgroundMusic(coldMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.0f)
                .downfall(0.5f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 15, 1, 2))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.SALMON, 5, 1, 5))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamSnowyPeaks(HolderGetter<PlacedFeature> placedFeatures,
                                             HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects();
        applyColdFoliage(effects);
        effects.ambientParticle(new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.01f))
                .backgroundMusic(coldMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(-0.7f)
                .downfall(0.9f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 15, 1, 2))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(ModEntities.PINK_SLIME.get(), 10, 1, 3))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamCherryGrove(HolderGetter<PlacedFeature> placedFeatures,
                                              HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects();
        applyColdFoliage(effects);
        effects.ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.01f))
                .backgroundMusic(coldMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(-0.2f)
                .downfall(0.8f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 15, 1, 2))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(ModEntities.PINK_SLIME.get(), 10, 1, 3))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamForest(HolderGetter<PlacedFeature> placedFeatures,
                                         HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFDC6F2)
                .grassColorOverride(0xFFFDC6F2)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.01f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.7f)
                .downfall(0.8f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 15, 1, 2))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 10, 4, 4))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(ModEntities.PINK_SLIME.get(), 20, 2, 4))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(ModEntities.PINK_CHICKEN.get(), 10, 1, 1))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamFlowerField(HolderGetter<PlacedFeature> placedFeatures,
                                              HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFDC6F2)
                .grassColorOverride(0xFFFDC6F2)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.01f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(1.0f)
                .downfall(0.35f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 20, 1, 2))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(ModEntities.PINK_SLIME.get(), 40, 2, 4))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(ModEntities.PINK_CHICKEN.get(), 10, 1, 1))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamCaves(HolderGetter<PlacedFeature> placedFeatures,
                                        HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .ambientParticle(new AmbientParticleSettings(ModParticleTypes.LEAVES_PARTICLE.get(), 0.005f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.8f)
                .downfall(0.4f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamLushCaves(HolderGetter<PlacedFeature> placedFeatures,
                                            HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .ambientParticle(new AmbientParticleSettings(ModParticleTypes.LEAVES_PARTICLE.get(), 0.01f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.5f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamDripstoneCaves(HolderGetter<PlacedFeature> placedFeatures,
                                                 HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .ambientParticle(new AmbientParticleSettings(ModParticleTypes.LEAVES_PARTICLE.get(), 0.005f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.8f)
                .downfall(0.4f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .generationSettings(gen.build())
                .build();
    }

    // ==================== 灯影之下 ====================

    /** 灯影群系共享的环境音（暗影低语，原作 shadow_biome_0，tick_chance 0.0111） */
    private static AmbientAdditionsSettings shadowAmbient() {
        return new AmbientAdditionsSettings(
                BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(SHADOW_BIOME_KEY), 0.0111);
    }

    /** 灯影群系背景音乐（原作 shadow_biome_0，replace_current_music = true） */
    private static Music shadowBiomeMusic() {
        return new Music(BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(SHADOW_BIOME_KEY),
                MUSIC_MIN_DELAY, MUSIC_MAX_DELAY, true);
    }

    private static BiomeSpecialEffects.Builder shadowEffects() {
        return new BiomeSpecialEffects.Builder()
                .skyColor(0x000000)
                .fogColor(0x000000)
                .waterColor(0x404040)
                .waterFogColor(0x202020)
                .ambientAdditionsSound(shadowAmbient())
                .backgroundMusic(shadowBiomeMusic());
    }

    /** 灯影群系共享：洞穴 + 峡谷 + 锁链柱 */
    private static void addShadowLandFeatures(BiomeGenerationSettings.Builder builder) {
        builder.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CANYON)
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.SHADOW_CHAIN_PILLAR);
    }

    /** 全部灯影群系共享的地表植被（影芽、影蕨） */
    private static void addShadowVegetation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SHADOW_SPROUTS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SHADOW_FERN_PATCH);
    }

    /** 菌索荒原特有（植被） */
    private static void addShadowNyliumWastesVegetation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SHADOW_SHORT_ROOTS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SHADOW_ROOTS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SHADOW_FUNGUS_PATCH);
    }

    /** 阴影森林特有植被（四种 + 巨型蘑菇树） */
    private static void addShadowForestVegetation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SHADOW_SHORT_ROOTS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SHADOW_ROOTS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SHADOW_FUNGUS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.WHITE_ORCHID_FLOWER_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SHADOW_FUNGUS_TREE);
    }

    /** 阴影古迹特有植被（影茎蕨、白厄花）+ 残垣断壁 + 古墓 */
    private static void addShadowRuinsVegetation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.SHADOW_RUIN_WALL)
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.SHADOW_TOMB)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SHADOW_STEM_FERN_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.WHITE_ORCHID_FLOWER_PATCH);
    }

    private static Biome shadowNyliumWastes(HolderGetter<PlacedFeature> placedFeatures,
                                       HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        addShadowLandFeatures(gen);
        addShadowVegetation(gen);
        addShadowNyliumWastesVegetation(gen);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.5f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(shadowEffects().build())
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .generationSettings(gen.build())
                .build();
    }

    private static Biome shadowForest(HolderGetter<PlacedFeature> placedFeatures,
                                       HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        addShadowLandFeatures(gen);
        addShadowVegetation(gen);
        addShadowForestVegetation(gen);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.2f)
                .downfall(0.3f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(shadowEffects().build())
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .generationSettings(gen.build())
                .build();
    }

    private static Biome shadowRuins(HolderGetter<PlacedFeature> placedFeatures,
                                       HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        addShadowLandFeatures(gen);
        addShadowVegetation(gen);
        addShadowRuinsVegetation(gen);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.4f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(shadowEffects().build())
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .generationSettings(gen.build())
                .build();
    }

    private static Biome shadowOcean(HolderGetter<PlacedFeature> placedFeatures,
                                      HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        addShadowLandFeatures(gen);
        gen.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.SHADOW_CHAIN_PILLAR_OCEAN);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.5f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(shadowEffects().build())
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .generationSettings(gen.build())
                .build();
    }

    // ==================== 风之旅途 ====================

    /** 风之旅途群系共享的环境音（微风） */
    private static AmbientAdditionsSettings windBreeze() {
        return new AmbientAdditionsSettings(
                BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(BREEZE_WIND_KEY), 0.0111);
    }

    private static Biome windMoorArchipelago(HolderGetter<PlacedFeature> placedFeatures,
                                            HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
                .skyColor(-1376258)
                .fogColor(-1376258)
                .waterColor(-11475496)
                .waterFogColor(-9705764)
                .foliageColorOverride(-11149912)
                .grassColorOverride(-14107774)
                .ambientParticle(new AmbientParticleSettings(ModParticleTypes.FIREFLY_PARTICLE.get(), 0.003f))
                .ambientAdditionsSound(windBreeze())
                .backgroundMusic(windJourneyMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.CONGEAL_WIND_ORE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.WIND_RUNNER_CRYSTAL_ORE)
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.WIND_JOURNEY_CLOUD_PATCH)
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.WIND_JOURNEY_WATER_POOL)
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.WIND_JOURNEY_PEBBLE_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.WIND_JOURNEY_FIREFLY_NEST)
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.WIND_JOURNEY_CYAN_MOSS_STONE_BLOB)
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.WIND_JOURNEY_SMALL_STONE_SPIRIT)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BAMBOO_LIGHT)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.WIND_JOURNEY_HAIRY_MOSS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.WIND_JOURNEY_CLEAVING_GRASS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.WIND_JOURNEY_FEATHER_GRASS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.WIND_JOURNEY_REED_PATCH);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(1.0f)
                .downfall(0.5f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        // 原作 monster: 骨翼 weight6(1~2)、灰骨翼 weight1(1~1)、萤火虫 weight10(3~4)、雷云 weight5(1~2)、高压雷云 weight1(1~1)；密度减半
                        .addSpawn(MobCategory.MONSTER,
                                new MobSpawnSettings.SpawnerData(ModEntities.BONE_WING.get(), 3, 1, 2))
                        .addSpawn(MobCategory.MONSTER,
                                new MobSpawnSettings.SpawnerData(ModEntities.ASH_BONE_WING.get(), 1, 1, 1))
                        .addSpawn(MobCategory.MONSTER,
                                new MobSpawnSettings.SpawnerData(ModEntities.FIREFLY.get(), 5, 2, 3))
                        .addSpawn(MobCategory.MONSTER,
                                new MobSpawnSettings.SpawnerData(ModEntities.THUNDERCLOUD.get(), 2, 1, 1))
                        .addSpawn(MobCategory.MONSTER,
                                new MobSpawnSettings.SpawnerData(ModEntities.HIGHVOLTAGE_THUNDERCLOUD.get(), 1, 1, 1))
                        // 原作 creature: 水母 weight10(1~2)；小石精原作 creature weight12(1~2)，但实体为 Monster（暗处生成），按 monster 接入否则永不在群系生成；小石精密度减半
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(ModEntities.JELLYFISH.get(), 10, 1, 2))
                        .addSpawn(MobCategory.MONSTER,
                                new MobSpawnSettings.SpawnerData(ModEntities.SMALL_STONE_SPIRIT.get(), 6, 1, 2))
                        // addMobCharge 生成预算：每次生成尝试需 charge × Σ(已聚集charge/距离) ≤ energyBudget，直接限制这 6 种怪在玩家周围的同时存在数量
                        //（charge 越大越稀疏，energyBudget 越小越稀疏；骨翼/余烬骨翼能量预算提高至 3.0，允许同区域同时存在的数量约为原来的两倍(原1.5)）
                        .addMobCharge(ModEntities.BONE_WING.get(), 2.0, 3.0)
                        .addMobCharge(ModEntities.ASH_BONE_WING.get(), 2.0, 3.0)
                        .addMobCharge(ModEntities.FIREFLY.get(), 2.0, 2.0)
                        .addMobCharge(ModEntities.THUNDERCLOUD.get(), 2.0, 2.0)
                        .addMobCharge(ModEntities.HIGHVOLTAGE_THUNDERCLOUD.get(), 2.0, 2.0)
                        .addMobCharge(ModEntities.SMALL_STONE_SPIRIT.get(), 2.0, 3)
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome mistyDreamCloudLayer(HolderGetter<PlacedFeature> placedFeatures,
                                            HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
                .skyColor(-1114369)
                .fogColor(-1114369)
                .waterColor(-11475496)
                .waterFogColor(-5639444)
                .foliageColorOverride(-11149912)
                .grassColorOverride(-14107774)
                .ambientAdditionsSound(windBreeze())
                .backgroundMusic(windJourneyMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.WIND_JOURNEY_CLOUD_PATCH_LOW);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.4f)
                .downfall(0.5f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .generationSettings(gen.build())
                .build();
    }

    // ==================== 亚伦柯斯竞技场 ====================

    private static Biome aaroncosArenaBiome(HolderGetter<PlacedFeature> placedFeatures,
                                            HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
                .skyColor(0x79A6FF)
                .fogColor(0xC0D8FF)
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .foliageColorOverride(0x9E814D)
                .grassColorOverride(0x90814D);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.5f)
                .downfall(0.0f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, carvers).build())
                .build();
    }
}
