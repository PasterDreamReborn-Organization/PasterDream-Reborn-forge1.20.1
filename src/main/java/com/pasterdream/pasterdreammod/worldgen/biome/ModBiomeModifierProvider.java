package com.pasterdream.pasterdreammod.worldgen.biome;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModBiomeModifierProvider implements DataProvider
{
    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registries;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public ModBiomeModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        this.output = output;
        this.registries = registries;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache)
    {
        return registries.thenCompose(provider ->
        {
            Map<ResourceLocation, JsonObject> entries = new HashMap<>();
            HolderLookup.RegistryLookup<PlacedFeature> featureLookup = provider.lookupOrThrow(Registries.PLACED_FEATURE);

            // 小石子 — 原作 ground_overworld_0: surface_structures step, 主世界
            addFeature(entries, "pebble_patch", ModPlacedFeatures.PEBBLE_PATCH, featureLookup, TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_pebble_spawn_biome")), GenerationStep.Decoration.SURFACE_STRUCTURES);

            addPatch(entries, "goldenrod_patch", ModPlacedFeatures.GOLDENROD_PATCH, featureLookup, TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_goldenrod_spawn_biome")));
            addPatch(entries, "fourleaf_clover_patch", ModPlacedFeatures.FOURLEAF_CLOVER_PATCH, featureLookup, TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_fourleaf_clover_spawn_biome")));
            TagKey<Biome> flowerFieldTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_flower_field_spawn_biome"));
            addPatch(entries, "ferraria_crispa_patch", ModPlacedFeatures.FERRARIA_CRISPA_PATCH, featureLookup, flowerFieldTag);
            addPatch(entries, "malva_sinensis_cavan_patch", ModPlacedFeatures.MALVA_SINENSIS_CAVAN_PATCH, featureLookup, flowerFieldTag);
            addPatch(entries, "eustoma_patch", ModPlacedFeatures.EUSTOMA_PATCH, featureLookup, BiomeTags.IS_JUNGLE);
            addPatch(entries, "oats_patch", ModPlacedFeatures.OATS_PATCH, featureLookup, TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_oats_spawn_biome")));
            TagKey<Biome> ryeSpawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_rye_spawn_biome"));
            addPatch(entries, "rye_patch", ModPlacedFeatures.RYE_PATCH, featureLookup, ryeSpawnTag);
            addPatch(entries, "white_corolla_crop_patch", ModPlacedFeatures.WHITE_COROLLA_CROP_PATCH, featureLookup, TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_white_corolla_spawn_biome")));
            TagKey<Biome> cottonSpawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_cotton_spawn_biome"));
            addPatch(entries, "cotton_crop_patch", ModPlacedFeatures.COTTON_CROP_PATCH, featureLookup, cottonSpawnTag);

            addPatch(entries, "jungle_sporangium_patch", ModPlacedFeatures.JUNGLE_SPORANGIUM_PATCH, featureLookup, BiomeTags.IS_JUNGLE);

            TagKey<Biome> reedSpawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_reed_spawn_biome"));
            addPatch(entries, "reed_patch", ModPlacedFeatures.REED_PATCH, featureLookup, reedSpawnTag);

            TagKey<Biome> crimsonForestTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_spawn_in_crimson_forest"));
            addPatch(entries, "blaze_flower_patch", ModPlacedFeatures.BLAZE_FLOWER_PATCH, featureLookup, crimsonForestTag);
            addPatch(entries, "crimson_thorns_patch", ModPlacedFeatures.CRIMSON_THORNS_PATCH, featureLookup, crimsonForestTag);

            addPatch(entries, "deepslate_titanium_ore_patch", ModPlacedFeatures.DEEPSLATE_TITANIUM_ORE_PATCH, featureLookup, BiomeTags.IS_OVERWORLD);
            addPatch(entries, "molten_gold_ore_patch", ModPlacedFeatures.MOLTEN_GOLD_ORE_PATCH, featureLookup, BiomeTags.IS_NETHER);
            addPatch(entries, "soul_ore_patch", ModPlacedFeatures.SOUL_ORE_PATCH, featureLookup, TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_soul_ore_spawn_biome")));

            // ===== 染梦维度地物分组注入（标签见 ModBiomeTagsProvider） =====
            TagKey<Biome> tAll = dyeTag("dyedream_world_biome");
            TagKey<Biome> tWater = dyeTag("dyedream_world_water_biome");
            TagKey<Biome> tSeagrass = dyeTag("dyedream_world_seagrass_biome");
            TagKey<Biome> tRiver = dyeTag("dyedream_world_river_biome");
            TagKey<Biome> tWarmLand = dyeTag("dyedream_world_warm_land_biome");
            TagKey<Biome> tWarmIce = dyeTag("dyedream_world_warm_ice_biome");
            TagKey<Biome> tCold = dyeTag("dyedream_world_cold_biome");
            TagKey<Biome> tFreeze = dyeTag("dyedream_world_freeze_biome");
            TagKey<Biome> tSeaPickle = dyeTag("dyedream_world_sea_pickle_biome");
            TagKey<Biome> tPlains = dyeTag("dyedream_world_plains_biome");
            TagKey<Biome> tFlowerField = dyeTag("dyedream_world_flower_field_biome");
            TagKey<Biome> tStem = dyeTag("dyedream_world_stem_biome");
            TagKey<Biome> tVine = dyeTag("dyedream_world_vine_biome");
            TagKey<Biome> tForest = dyeTag("dyedream_world_forest_biome");
            TagKey<Biome> tMushroom = dyeTag("dyedream_world_mushroom_biome");
            TagKey<Biome> tSnowyIcePillar = dyeTag("dyedream_world_snowy_ice_pillar_biome");
            TagKey<Biome> tEdelweiss = dyeTag("dyedream_world_edelweiss_biome");
            TagKey<Biome> tCloudPillar = dyeTag("dyedream_world_cloud_pillar_biome");
            TagKey<Biome> tSnowyTree = dyeTag("dyedream_world_snowy_tree_biome");
            TagKey<Biome> tCherryGrove = dyeTag("dyedream_world_cherry_grove_biome");
            TagKey<Biome> tSnowyPool = dyeTag("dyedream_world_snowy_pool_biome");
            TagKey<Biome> tKelp = dyeTag("dyedream_world_kelp_biome");
            TagKey<Biome> tIceberg = dyeTag("dyedream_world_iceberg_biome");
            TagKey<Biome> tLush = dyeTag("dyedream_world_lush_biome");
            TagKey<Biome> tDripstone = dyeTag("dyedream_world_dripstone_biome");
            TagKey<Biome> tLand = dyeTag("dyedream_world_land_biome");
            TagKey<Biome> tOcean = dyeTag("dyedream_world_ocean_biome");
            ResourceKey<PlacedFeature> freezeTop = ModPlacedFeatures.FREEZE_TOP_LAYER;
            // 原版樱花群系地物（染梦樱花林直接复用）
            ResourceKey<PlacedFeature> VANILLA_TREES_CHERRY = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath("minecraft", "trees_cherry"));
            ResourceKey<PlacedFeature> VANILLA_FLOWER_CHERRY = ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath("minecraft", "flower_cherry"));

            // 全维度通用：矿石 / 晶芽 / 晶洞
            addFeature(entries, "dyedream_titanium_ore", ModPlacedFeatures.TITANIUM_ORE, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_amber_candy_ore", ModPlacedFeatures.AMBER_CANDY_ORE, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_dust_ore", ModPlacedFeatures.DYEDREAM_DUST_ORE, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_quartz_ore", ModPlacedFeatures.DYEDREAM_QUARTZ_ORE, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            // 染梦世界·方解石原版矿物（全部染梦群系）
            addFeature(entries, "dyedream_calcite_coal_ore_upper", ModPlacedFeatures.CALCITE_COAL_ORE_UPPER, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_calcite_coal_ore_lower", ModPlacedFeatures.CALCITE_COAL_ORE_LOWER, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_calcite_iron_ore_upper", ModPlacedFeatures.CALCITE_IRON_ORE_UPPER, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_calcite_iron_ore_middle", ModPlacedFeatures.CALCITE_IRON_ORE_MIDDLE, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_calcite_iron_ore_small", ModPlacedFeatures.CALCITE_IRON_ORE_SMALL, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_calcite_copper_ore", ModPlacedFeatures.CALCITE_COPPER_ORE, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_calcite_copper_ore_large", ModPlacedFeatures.CALCITE_COPPER_ORE_LARGE, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_calcite_gold_ore", ModPlacedFeatures.CALCITE_GOLD_ORE, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_calcite_gold_ore_lower", ModPlacedFeatures.CALCITE_GOLD_ORE_LOWER, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_calcite_redstone_ore", ModPlacedFeatures.CALCITE_REDSTONE_ORE, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_calcite_redstone_ore_lower", ModPlacedFeatures.CALCITE_REDSTONE_ORE_LOWER, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_calcite_lapis_ore", ModPlacedFeatures.CALCITE_LAPIS_ORE, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_calcite_lapis_ore_buried", ModPlacedFeatures.CALCITE_LAPIS_ORE_BURIED, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_calcite_diamond_ore", ModPlacedFeatures.CALCITE_DIAMOND_ORE, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_calcite_diamond_ore_large", ModPlacedFeatures.CALCITE_DIAMOND_ORE_LARGE, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_calcite_diamond_ore_buried", ModPlacedFeatures.CALCITE_DIAMOND_ORE_BURIED, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_calcite_emerald_ore", ModPlacedFeatures.CALCITE_EMERALD_ORE, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_small_bud", ModPlacedFeatures.SMALL_DYEDREAM_BUD_PATCH, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_DECORATION);
            addFeature(entries, "dyedream_medium_bud", ModPlacedFeatures.MEDIUM_DYEDREAM_BUD_PATCH, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_DECORATION);
            addFeature(entries, "dyedream_large_bud", ModPlacedFeatures.LARGE_DYEDREAM_BUD_PATCH, featureLookup, tAll, GenerationStep.Decoration.UNDERGROUND_DECORATION);
            addFeature(entries, "dyedream_geode", ModPlacedFeatures.DYEDREAM_GEODE, featureLookup, tAll, GenerationStep.Decoration.LOCAL_MODIFICATIONS);

            // 水面植物（海洋/沿岸/河流）
            addPatch(entries, "dyedream_lily_pad_biome_modifier", ModPlacedFeatures.DYEDREAM_LILY_PAD_PATCH, featureLookup, tWater);
            addPatch(entries, "dyedream_lotus_biome_modifier", ModPlacedFeatures.DYEDREAM_LOTUS_PATCH, featureLookup, tWater);

            // 海草（陆地水边 + 海洋 + 沿岸/河流）
            addFeature(entries, "dyedream_seagrass", ModPlacedFeatures.DYEDREAM_SEAGRASS_PATCH, featureLookup, tSeagrass, GenerationStep.Decoration.SURFACE_STRUCTURES);

            // 河流粘土（染梦河流/染梦冻河）— 仿原版河流 disk_clay，UNDERGROUND_ORES step
            addFeature(entries, "dyedream_river_clay", ModPlacedFeatures.DYEDREAM_RIVER_CLAY, featureLookup, tRiver, GenerationStep.Decoration.UNDERGROUND_ORES);

            // 温暖冰团（平原/菇山/森林/海洋）
            addFeature(entries, "dyedream_warm_ice_blob", ModPlacedFeatures.DYEDREAM_ICE_BLOBS, featureLookup, tWarmIce, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_warm_packed_ice_blob", ModPlacedFeatures.DYEDREAM_PACKED_ICE_BLOBS, featureLookup, tWarmIce, GenerationStep.Decoration.UNDERGROUND_ORES);

            // 寒冷冰矿石 / 冰晶芽（雪山/雪坡/雪林/雪原/雪针/冻河/冻洋）
            addFeature(entries, "dyedream_cold_ice_stone", ModPlacedFeatures.DYEDREAM_ICE_STONE_BLOBS, featureLookup, tCold, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_cold_vanilla_ice", ModPlacedFeatures.VANILLA_ICE_BLOBS, featureLookup, tCold, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_cold_vanilla_packed_ice", ModPlacedFeatures.VANILLA_PACKED_ICE_BLOBS, featureLookup, tCold, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeature(entries, "dyedream_cold_ice_bud", ModPlacedFeatures.ICE_BUD_PATCH, featureLookup, tCold, GenerationStep.Decoration.UNDERGROUND_DECORATION);

            // 雪顶（寒冷陆地 + 冻河）
            addFeature(entries, "dyedream_freeze_top", freezeTop, featureLookup, tFreeze, GenerationStep.Decoration.TOP_LAYER_MODIFICATION);

            // 温暖陆地共享花草（平原/菇山/森林）
            addPatch(entries, "dyedream_warm_lotus", ModPlacedFeatures.DREAMING_LOTUS_PATCH, featureLookup, tWarmLand);
            addPatch(entries, "dyedream_warm_linht", ModPlacedFeatures.LINHT_FLOWER_PATCH, featureLookup, tWarmLand);
            addPatch(entries, "dyedream_warm_fern", ModPlacedFeatures.SINGULARITY_FERN_PATCH, featureLookup, tWarmLand);

            // 通用植被（全维度 18 群系：苔藓/珊瑚×3/方解石巨砾；海泡菜仅非寒冷群系）
            addPatch(entries, "dyedream_common_moss", ModPlacedFeatures.DYEDREAM_MOSS_PATCH, featureLookup, tAll);
            addPatch(entries, "dyedream_common_coral_tree", ModPlacedFeatures.CORAL_TREE_PATCH, featureLookup, tAll);
            addPatch(entries, "dyedream_common_coral_claw", ModPlacedFeatures.CORAL_CLAW_PATCH, featureLookup, tAll);
            addPatch(entries, "dyedream_common_coral_mushroom", ModPlacedFeatures.CORAL_MUSHROOM_PATCH, featureLookup, tAll);
            addPatch(entries, "dyedream_common_sea_pickle", ModPlacedFeatures.SEA_PICKLE_PATCH, featureLookup, tSeaPickle);
            addPatch(entries, "dyedream_common_calcite_boulder", ModPlacedFeatures.CALCITE_BOULDER, featureLookup, tAll);

            // 平原（树 + 花田）
            addPatch(entries, "dyedream_plains_tree", ModPlacedFeatures.DYEDREAM_TREE, featureLookup, tPlains);
            addPatch(entries, "dyedream_plains_corolla", ModPlacedFeatures.DYEDREAM_COROLLA_PATCH, featureLookup, tPlains);
            addPatch(entries, "dyedream_plains_light_ball", ModPlacedFeatures.LIGHT_BALL_PATCH, featureLookup, tPlains);
            addPatch(entries, "dyedream_plains_cloud_crop", ModPlacedFeatures.CLOUD_CROP_PATCH, featureLookup, tPlains);

            // 花海（密集梦染茶花 + 茎草 + 浮空流明光球；无染梦树、无高茎草）
            addPatch(entries, "dyedream_flower_field_corolla", ModPlacedFeatures.DYEDREAM_COROLLA_PATCH_FLOWER_FIELD, featureLookup, tFlowerField);
            addPatch(entries, "dyedream_flower_field_stem_grass", ModPlacedFeatures.STEM_GRASS_PATCH, featureLookup, tFlowerField);
            addFeature(entries, "dyedream_flower_field_light_ball", ModPlacedFeatures.FLOATING_LIGHT_BALL, featureLookup, tFlowerField, GenerationStep.Decoration.VEGETAL_DECORATION);
            // 花海混合花卉（流明堇/玲云花/苓灯花/染梦铃兰 — 仿原版繁花森林，按坐标噪声错落分布）
            addPatch(entries, "dyedream_flower_field_mixed_flowers", ModPlacedFeatures.FLOWER_FIELD_FLOWERS, featureLookup, tFlowerField);

            // 平原/森林共享装饰（茎草/铃兰/藤/方解石笋）
            addPatch(entries, "dyedream_stem_grass", ModPlacedFeatures.STEM_GRASS_PATCH, featureLookup, tStem);
            addPatch(entries, "dyedream_tall_stem_grass", ModPlacedFeatures.TALL_STEM_GRASS_PATCH, featureLookup, tStem);
            addPatch(entries, "dyedream_lily", ModPlacedFeatures.DYEDREAM_LILY_PATCH, featureLookup, tStem);
            addPatch(entries, "dyedream_vine", ModPlacedFeatures.DYEDREAM_VINE_PATCH, featureLookup, tVine);
            addPatch(entries, "dyedream_calcite_stalicripe", ModPlacedFeatures.CALCITE_STALICRIPE, featureLookup, tStem);
            addPatch(entries, "dyedream_small_calcite_stalicripe", ModPlacedFeatures.SMALL_CALCITE_STALICRIPE, featureLookup, tStem);

            // 森林（稠密树 + 稀疏粉顶菇 + 萤火虫巢）
            addPatch(entries, "dyedream_forest_dense_tree", ModPlacedFeatures.DYEDREAM_TREE_DENSE, featureLookup, tForest);
            addPatch(entries, "dyedream_forest_mushroom", ModPlacedFeatures.PINK_HUGE_MUSHROOM_SPARSE, featureLookup, tForest);
            addFeature(entries, "dyedream_forest_firefly_nest", ModPlacedFeatures.DYEDREAM_FIREFLY_NEST, featureLookup, tForest, GenerationStep.Decoration.VEGETAL_DECORATION);

            // 菇山（粉顶菇系列）
            addPatch(entries, "dyedream_mushroom_tree", ModPlacedFeatures.PINK_MUSHROOM_TREE, featureLookup, tMushroom);
            addPatch(entries, "dyedream_mushroom_huge", ModPlacedFeatures.PINK_HUGE_MUSHROOM, featureLookup, tMushroom);
            addPatch(entries, "dyedream_mushroom_patch", ModPlacedFeatures.PINK_MUSHROOM_PATCH, featureLookup, tMushroom);
            addPatch(entries, "dyedream_tall_mushroom_patch", ModPlacedFeatures.TALL_PINK_MUSHROOM_PATCH, featureLookup, tMushroom);

            // 寒冷陆地冰雪装饰（冰柱/雪绒花/云柱）
            addPatch(entries, "dyedream_ice_pillar", ModPlacedFeatures.DYEDREAM_ICE_PILLAR, featureLookup, tSnowyIcePillar);
            addPatch(entries, "dyedream_packed_ice_pillar", ModPlacedFeatures.DYEDREAM_PACKED_ICE_PILLAR, featureLookup, tSnowyIcePillar);
            addPatch(entries, "dyedream_edelweiss", ModPlacedFeatures.EDELWEISS_PATCH, featureLookup, tEdelweiss);
            addPatch(entries, "dyedream_cloud_pillar_small", ModPlacedFeatures.CLOUD_PILLAR_SMALL, featureLookup, tCloudPillar);
            addPatch(entries, "dyedream_cloud_pillar_large", ModPlacedFeatures.CLOUD_PILLAR_LARGE, featureLookup, tCloudPillar);

            // 雪原：稀疏染梦树 / 雪原水池
            addPatch(entries, "dyedream_snowy_tree", ModPlacedFeatures.DYEDREAM_TREE, featureLookup, tSnowyTree);
            addFeature(entries, "dyedream_snowy_water_pool", ModPlacedFeatures.SNOWY_WATER_POOL, featureLookup, tSnowyPool, GenerationStep.Decoration.SURFACE_STRUCTURES);

            // 染梦樱花林 — 原版樱花树 + 原版粉红花瓣（独享 冷×湿×陡坡 气候区）
            addFeature(entries, "dyedream_cherry_grove_trees", VANILLA_TREES_CHERRY, featureLookup, tCherryGrove, GenerationStep.Decoration.VEGETAL_DECORATION);
            addFeature(entries, "dyedream_cherry_grove_pink_petals", VANILLA_FLOWER_CHERRY, featureLookup, tCherryGrove, GenerationStep.Decoration.VEGETAL_DECORATION);

            // 冷海/海洋海带
            addPatch(entries, "dyedream_kelp", ModPlacedFeatures.DYEDREAM_KELP_PATCH, featureLookup, tKelp);

            // 冻洋冰山
            addFeature(entries, "dyedream_iceberg_packed", ModPlacedFeatures.DYEDREAM_ICEBERG_PACKED, featureLookup, tIceberg, GenerationStep.Decoration.LOCAL_MODIFICATIONS);
            addFeature(entries, "dyedream_iceberg_blue", ModPlacedFeatures.DYEDREAM_ICEBERG_BLUE, featureLookup, tIceberg, GenerationStep.Decoration.LOCAL_MODIFICATIONS);

            // 洞穴
            addPatch(entries, "dyedream_lush_mushroom_tree", ModPlacedFeatures.LUSH_CAVE_MUSHROOM_TREE, featureLookup, tLush);
            addPatch(entries, "dyedream_lush_small_mushroom", ModPlacedFeatures.SMALL_PINK_MUSHROOM_PATCH, featureLookup, tLush);
            addPatch(entries, "dyedream_lush_mushroom_patch", ModPlacedFeatures.LUSH_CAVE_MUSHROOM_PATCH, featureLookup, tLush);
            addPatch(entries, "dyedream_lush_tall_mushroom_patch", ModPlacedFeatures.LUSH_CAVE_TALL_MUSHROOM_PATCH, featureLookup, tLush);
            addPatch(entries, "dyedream_lush_stem_grass", ModPlacedFeatures.LUSH_CAVE_STEM_GRASS_PATCH, featureLookup, tLush);
            addPatch(entries, "dyedream_lush_tall_stem_grass", ModPlacedFeatures.LUSH_CAVE_TALL_STEM_GRASS_PATCH, featureLookup, tLush);
            addPatch(entries, "dyedream_lush_fern", ModPlacedFeatures.LUSH_CAVE_FERN_PATCH, featureLookup, tLush);
            addPatch(entries, "dyedream_lush_vine", ModPlacedFeatures.LUSH_CAVE_VINE_PATCH, featureLookup, tLush);
            addPatch(entries, "dyedream_lush_shroom_block", ModPlacedFeatures.LUSH_CAVE_SHROOM_BLOCK_PATCH, featureLookup, tLush);
            addPatch(entries, "dyedream_lush_pink_mushroom_curtain", ModPlacedFeatures.LUSH_CAVE_PINK_MUSHROOM_CURTAIN, featureLookup, tLush);
            addPatch(entries, "dyedream_dripstone_cone", ModPlacedFeatures.CALCITE_CONE_PATCH, featureLookup, tDripstone);
            addFeature(entries, "dyedream_dripstone_cluster", ModPlacedFeatures.CALCITE_CONE_CLUSTER_PATCH, featureLookup, tDripstone, GenerationStep.Decoration.LOCAL_MODIFICATIONS);

            // 方解石尖锥（陆地）— 表面结构 step，自定义 Feature 生成
            addFeature(entries, "calcite_spike", ModPlacedFeatures.CALCITE_SPIKE, featureLookup, tLand, GenerationStep.Decoration.SURFACE_STRUCTURES);

            // 方解石尖锥（海洋变体）— 表面结构 step，NBT 结构放置，浮于海面
            addFeature(entries, "stone_pillar_ocean", ModPlacedFeatures.STONE_PILLAR_OCEAN, featureLookup, tOcean, GenerationStep.Decoration.SURFACE_STRUCTURES);

            // ===== 阴影群系标签 =====
            TagKey<Biome> shadowNyliumWastesSpawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_nylium_wastes_spawn_biome"));
            TagKey<Biome> shadowForestSpawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_forest_spawn_biome"));
            TagKey<Biome> shadowGhostSpawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ghost_spawn_biome"));
            TagKey<Biome> shadowRuinsSpawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ruins_spawn_biome"));

            // ===== 非阴影实体生成（染梦世界等） =====
            addSpawns(entries, "pink_chicken_biome_modifier", ModEntities.PINK_CHICKEN, tAll, 5, 4, 4);
            addSpawns(entries, "pink_slime_biome_modifier", ModEntities.PINK_SLIME, tAll, 20, 5, 6);

            // ===== 灯影之下维度实体生成（对照原作 NOT_MODIFY 中的 biome_modifier + 群系 JSON） =====
            // biome_shadow_0（菌索荒原）: terrorbeak(10,1-2), shadow_hand(15,1-3)
            addSpawns(entries, "terrorbeak_biome_modifier", ModEntities.TERRORBEAK, shadowNyliumWastesSpawnTag, 10, 1, 2);
            addSpawns(entries, "shadow_hand_biome_modifier", ModEntities.SHADOW_HAND, shadowNyliumWastesSpawnTag, 15, 1, 3);

            // ===== 灯影之下维度地物（对照原作 NOT_MODIFY 中的 structure/structure_set） =====
            // 暗影之手 — 原作 shadow_hand_0 结构: biome_shadow_0, surface_structures step
            addFeature(entries, "shadow_hand_feature", ModPlacedFeatures.SHADOW_HAND, featureLookup, shadowNyliumWastesSpawnTag, GenerationStep.Decoration.SURFACE_STRUCTURES);

            // biome_shadow_1（阴影森林）: black_beetle(20,5-7 + 20,2-4) + friendly_shadow_ghost(10,1,3)
            //   原作有两处 black_beetle 生成 — 群系 JSON monster spawn + biome_modifier add_spawns
            addSpawns(entries, "black_beetle_biome_spawn", ModEntities.BLACK_BEETLE, shadowForestSpawnTag, 20, 5, 7);
            addSpawns(entries, "black_beetle_biome_modifier", ModEntities.BLACK_BEETLE, shadowForestSpawnTag, 20, 2, 4);
            addSpawns(entries, "friendly_shadow_ghost_biome_modifier", ModEntities.FRIENDLY_SHADOW_GHOST, shadowForestSpawnTag, 10, 1, 3);

            // biome_shadow_1（阴影古迹）: 幽灵系三变体（降低密度：总权重 12，单次最多 4 只）
            addSpawns(entries, "shadow_ghost_biome_modifier", ModEntities.SHADOW_GHOST, shadowGhostSpawnTag, 6, 1, 2);
            addSpawns(entries, "shadow_squeal_ghost_biome_modifier", ModEntities.SHADOW_SQUEAL_GHOST, shadowGhostSpawnTag, 4, 1, 1);
            addSpawns(entries, "wailing_shadow_ghost_biome_modifier", ModEntities.WAILING_SHADOW_GHOST, shadowGhostSpawnTag, 2, 1, 1);

            // biome_shadow_2（阴影古迹）: shadow_golem(4,1-1) + shadow_hand(10,1-2)，后者稀释精英怪比例
            addSpawns(entries, "shadow_golem_biome_modifier", ModEntities.SHADOW_GOLEM, shadowRuinsSpawnTag, 4, 1, 1);
            addSpawns(entries, "shadow_hand_ruins_biome_modifier", ModEntities.SHADOW_HAND, shadowRuinsSpawnTag, 10, 1, 2);

            return saveAll(cache, entries);
        });
    }

    private void addPatch(Map<ResourceLocation, JsonObject> map, String name, ResourceKey<PlacedFeature> featureKey, HolderLookup.RegistryLookup<PlacedFeature> featureLookup, TagKey<Biome> biomeTag)
    {
        addFeature(map, name, featureKey, featureLookup, biomeTag, GenerationStep.Decoration.VEGETAL_DECORATION);
    }

    private TagKey<Biome> dyeTag(String name) {
        return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name));
    }

    private void addSpawns(Map<ResourceLocation, JsonObject> map, String name, net.minecraftforge.registries.RegistryObject<? extends EntityType<?>> entityType, TagKey<Biome> biomeTag, int weight, int minCount, int maxCount) {
        ResourceLocation entityId = net.minecraftforge.registries.ForgeRegistries.ENTITY_TYPES.getKey(entityType.get());
        JsonObject json = new JsonObject();
        json.addProperty("type", "forge:add_spawns");
        json.addProperty("biomes", "#" + biomeTag.location());
        JsonObject spawners = new JsonObject();
        spawners.addProperty("type", entityId.toString());
        spawners.addProperty("weight", weight);
        spawners.addProperty("minCount", minCount);
        spawners.addProperty("maxCount", maxCount);
        json.add("spawners", spawners);
        map.put(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name), json);
    }

    private void addFeature(Map<ResourceLocation, JsonObject> map, String name, ResourceKey<PlacedFeature> featureKey, HolderLookup.RegistryLookup<PlacedFeature> featureLookup, TagKey<Biome> biomeTag, GenerationStep.Decoration step)
    {
        ResourceLocation featureId = featureKey.location();
        JsonObject json = new JsonObject();
        json.addProperty("type", "forge:add_features");
        json.addProperty("biomes", "#" + biomeTag.location());
        json.addProperty("features", featureId.toString());
        json.addProperty("step", step.getName());
        map.put(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name), json);
    }

    private CompletableFuture<?> saveAll(CachedOutput cache, Map<ResourceLocation, JsonObject> entries)
    {
        return CompletableFuture.allOf(entries.entrySet().stream().map(entry ->
        {
            ResourceLocation id = entry.getKey();
            JsonObject json = entry.getValue();
            Path path = output.getOutputFolder().resolve("data/" + id.getNamespace() + "/forge/biome_modifier/" + id.getPath() + ".json");
            return DataProvider.saveStable(cache, json, path);
        }).toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName()
    {
        return "Biome Modifiers: " + PasterDreamMod.MOD_ID;
    }
}
