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

            TagKey<Biome> dyedreamWorldTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world_biome"));
            addPatch(entries, "dyedream_lily_pad_biome_modifier", ModPlacedFeatures.DYEDREAM_LILY_PAD_PATCH, featureLookup, dyedreamWorldTag);
            addPatch(entries, "dyedream_lotus_biome_modifier", ModPlacedFeatures.DYEDREAM_LOTUS_PATCH, featureLookup, dyedreamWorldTag);

            TagKey<Biome> dyedreamWorldLandTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world_land_biome"));

            // 方解石尖锥（陆地）— 表面结构 step，自定义 Feature 生成
            addFeature(entries, "calcite_spike", ModPlacedFeatures.CALCITE_SPIKE, featureLookup, dyedreamWorldLandTag, GenerationStep.Decoration.SURFACE_STRUCTURES);

            // 方解石尖锥（海洋变体）— 表面结构 step，NBT 结构放置，浮于海面
            TagKey<Biome> dyedreamWorldOceanTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world_ocean_biome"));
            addFeature(entries, "stone_pillar_ocean", ModPlacedFeatures.STONE_PILLAR_OCEAN, featureLookup, dyedreamWorldOceanTag, GenerationStep.Decoration.SURFACE_STRUCTURES);

            // ===== 阴影群系标签 =====
            TagKey<Biome> shadowNyliumWastesSpawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_nylium_wastes_spawn_biome"));
            TagKey<Biome> shadowForestSpawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_forest_spawn_biome"));
            TagKey<Biome> shadowGhostSpawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ghost_spawn_biome"));
            TagKey<Biome> shadowRuinsSpawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ruins_spawn_biome"));

            // ===== 非阴影实体生成（染梦世界等） =====
            addSpawns(entries, "pink_chicken_biome_modifier", ModEntities.PINK_CHICKEN, dyedreamWorldTag, 5, 4, 4);
            addSpawns(entries, "pink_slime_biome_modifier", ModEntities.PINK_SLIME, dyedreamWorldTag, 20, 5, 6);

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
