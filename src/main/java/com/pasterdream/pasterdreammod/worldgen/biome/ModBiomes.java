package com.pasterdream.pasterdreammod.worldgen.biome;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Carvers;
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
    public static final ResourceKey<Biome> DYEDREAM_OCEAN =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_ocean"));

    private static final ResourceKey<PlacedFeature> FREEZE_TOP_LAYER =
            ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.withDefaultNamespace("freeze_top_layer"));

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(DYEDREAM_PLAINS, dyedreamPlains(placedFeatures, carvers));
        context.register(DYEDREAM_MUSHROOM_MOUNTAINS, dyedreamMushroomMountains(placedFeatures, carvers));
        context.register(DYEDREAM_SNOWY_PLAINS, dyedreamSnowyPlains(placedFeatures, carvers));
        context.register(DYEDREAM_FROZEN_OCEAN, dyedreamFrozenOcean(placedFeatures, carvers));
        context.register(DYEDREAM_OCEAN, dyedreamOcean(placedFeatures, carvers));
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

    /** 所有群系共享的 6 种矿石 */
    private static void addCommonOres(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.TITANIUM_ORE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.AMBER_CANDY_ORE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.DYEDREAM_DUST_ORE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.DYEDREAM_QUARTZ_ORE);
    }

    /** 所有群系共享的 3 种晶芽 */
    private static void addCommonBuds(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModPlacedFeatures.SMALL_DYEDREAM_BUD_PATCH)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModPlacedFeatures.MEDIUM_DYEDREAM_BUD_PATCH)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModPlacedFeatures.LARGE_DYEDREAM_BUD_PATCH);
    }

    /** 所有群系共享的 6 种植被装饰 */
    private static void addCommonVegetation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.DYEDREAM_MOSS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.CORAL_TREE_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.CORAL_CLAW_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.CORAL_MUSHROOM_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SEA_PICKLE_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.CALCITE_BOULDER);
    }

    /** 陆地群系共享：洞穴 + 海草 + 3 种花朵 */
    private static void addLandSharedFeatures(BiomeGenerationSettings.Builder builder) {
        builder.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.DYEDREAM_SEAGRASS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.DREAMING_LOTUS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.LINHT_FLOWER_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SINGULARITY_FERN_PATCH);
    }

    /** 温暖群系共享的冰 blob */
    private static void addWarmIceFeatures(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.DYEDREAM_ICE_BLOBS)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.DYEDREAM_PACKED_ICE_BLOBS);
    }

    /** 寒冷群系共享的冰矿石 + 冰晶芽 */
    private static void addColdOresAndBuds(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.DYEDREAM_ICE_STONE_BLOBS)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.VANILLA_ICE_BLOBS)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.VANILLA_PACKED_ICE_BLOBS)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModPlacedFeatures.ICE_BUD_PATCH);
    }

    /** 统一收尾：添加共享的矿石、晶芽、植被 */
    private static void finishGeneration(BiomeGenerationSettings.Builder builder) {
        addCommonVegetation(builder);
        addCommonOres(builder);
        addCommonBuds(builder);
    }

    // ==================== 各群系构建方法 ====================

    private static Biome dyedreamPlains(HolderGetter<PlacedFeature> placedFeatures,
                                         HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFDC6F2)
                .grassColorOverride(0xFFFDC6F2)
                .ambientParticle(new AmbientParticleSettings(ModParticleTypes.LEAVES_PARTICLE.get(), 0.005f));

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        addLandSharedFeatures(gen);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.DYEDREAM_TREE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.STEM_GRASS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.TALL_STEM_GRASS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.DYEDREAM_COROLLA_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.LIGHT_BALL_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.CLOUD_CROP_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.DYEDREAM_LILY_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.CALCITE_STALICRIPE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SMALL_CALCITE_STALICRIPE);
        addWarmIceFeatures(gen);
        finishGeneration(gen);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(1.0f)
                .downfall(0.35f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 20, 1, 2))
                        // TODO: pink_slime (weight 40, 2-4), pink_chicken (weight 10, 1-1)
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamMushroomMountains(HolderGetter<PlacedFeature> placedFeatures,
                                                    HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFCB3ED)
                .grassColorOverride(0xFFFFABEE)
                .ambientParticle(new AmbientParticleSettings(ModParticleTypes.LEAVES_PARTICLE.get(), 0.005f));

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        addLandSharedFeatures(gen);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.PINK_MUSHROOM_TREE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.PINK_HUGE_MUSHROOM)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.PINK_MUSHROOM_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.TALL_PINK_MUSHROOM_PATCH);
        addWarmIceFeatures(gen);
        finishGeneration(gen);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(1.6f)
                .downfall(0.2f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 10, 4, 4))
                        // TODO: pink_chicken (weight 40, 4-4), pink_slime (weight 20, 4-4)
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamSnowyPlains(HolderGetter<PlacedFeature> placedFeatures,
                                              HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects();
        applyColdFoliage(effects);
        effects.ambientParticle(new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.01f));

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        addLandSharedFeatures(gen);
        gen.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.SNOWY_WATER_POOL)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.DYEDREAM_TREE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.DYEDREAM_ICE_PILLAR)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.DYEDREAM_PACKED_ICE_PILLAR)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.DYEDREAM_LILY_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.EDELWEISS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.CLOUD_PILLAR_SMALL)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.CLOUD_PILLAR_LARGE);
        addColdOresAndBuds(gen);
        gen.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, FREEZE_TOP_LAYER);
        finishGeneration(gen);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.0f)
                .downfall(0.7f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 15, 1, 2))
                        // TODO: pink_slime (weight 20, 1-3)
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamFrozenOcean(HolderGetter<PlacedFeature> placedFeatures,
                                              HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects();
        applyColdFoliage(effects);
        effects.ambientParticle(new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.005f));

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.DYEDREAM_SEAGRASS_PATCH)
                .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ModPlacedFeatures.DYEDREAM_ICEBERG_PACKED)
                .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ModPlacedFeatures.DYEDREAM_ICEBERG_BLUE);
        addColdOresAndBuds(gen);
        finishGeneration(gen);

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
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamOcean(HolderGetter<PlacedFeature> placedFeatures,
                                        HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFDC6F2)
                .grassColorOverride(0xFFFDC6F2)
                .ambientParticle(new AmbientParticleSettings(ModParticleTypes.LEAVES_PARTICLE.get(), 0.003f));

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.DYEDREAM_SEAGRASS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.DYEDREAM_KELP_PATCH);
        addWarmIceFeatures(gen);
        finishGeneration(gen);

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
                        .build())
                .generationSettings(gen.build())
                .build();
    }
}
