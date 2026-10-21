package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends BiomeTagsProvider
{
    public ModBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider)
    {
        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_white_corolla_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "snowy_slopes"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "jagged_peaks"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "frozen_peaks"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_soul_ore_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "soul_sand_valley"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_fisherman_hut_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "river"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "beach"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "swamp"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "mangrove_swamp"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "forest"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_flower_field_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "flower_forest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "jungle"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_spawn_in_crimson_forest")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "crimson_forest"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_reed_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "swamp"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "mangrove_swamp"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_rye_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "windswept_hills"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "windswept_forest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "meadow"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_cotton_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "windswept_hills"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "windswept_forest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "meadow"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "taiga"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "dark_forest"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_goldenrod_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "jungle"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "flower_forest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "birch_forest"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_fourleaf_clover_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "plains"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_oats_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "windswept_hills"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "windswept_forest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "meadow"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_pebble_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "jungle"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "desert"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "taiga"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "savanna"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "beach"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "badlands"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "windswept_hills"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "forest"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_fishing_biomes")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_frozen_ocean"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_cold_ocean"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_ocean"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_river"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_frozen_river"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world_land_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_mushroom_mountains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_snowy_plains"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world_ocean_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_frozen_ocean"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_cold_ocean"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_ocean"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world_snowy_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_frozen_ocean"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_frozen_river"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_snowy_plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_snowy_peaks"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_cherry_grove"));

        // ===== 染梦维度地物分组标签（19 群系） =====
        tag(dyeTag("dyedream_world_biome"))
                .addOptional(dye("dyedream_plains")).addOptional(dye("dyedream_mushroom_mountains"))
                .addOptional(dye("dyedream_forest")).addOptional(dye("dyedream_flower_field"))
                .addOptional(dye("dyedream_snowy_plains"))
                .addOptional(dye("dyedream_snowy_peaks"))
                .addOptional(dye("dyedream_cherry_grove"))
                .addOptional(dye("dyedream_frozen_ocean")).addOptional(dye("dyedream_cold_ocean"))
                .addOptional(dye("dyedream_ocean")).addOptional(dye("dyedream_beach"))
                .addOptional(dye("dyedream_river")).addOptional(dye("dyedream_frozen_river"))
                .addOptional(dye("dyedream_caves")).addOptional(dye("dyedream_lush_caves"))
                .addOptional(dye("dyedream_dripstone_caves"));

        tag(dyeTag("dyedream_world_water_biome"))
                .addOptional(dye("dyedream_cold_ocean")).addOptional(dye("dyedream_ocean"))
                .addOptional(dye("dyedream_beach"))
                .addOptional(dye("dyedream_river"));

        tag(dyeTag("dyedream_world_seagrass_biome"))
                .addOptional(dye("dyedream_plains")).addOptional(dye("dyedream_mushroom_mountains"))
                .addOptional(dye("dyedream_forest")).addOptional(dye("dyedream_frozen_ocean"))
                .addOptional(dye("dyedream_cold_ocean")).addOptional(dye("dyedream_ocean"))
                .addOptional(dye("dyedream_beach")).addOptional(dye("dyedream_river"))
                .addOptional(dye("dyedream_frozen_river"));

        tag(dyeTag("dyedream_world_river_biome"))
                .addOptional(dye("dyedream_river"))
                .addOptional(dye("dyedream_frozen_river"));

        tag(dyeTag("dyedream_world_warm_land_biome"))
                .addOptional(dye("dyedream_plains")).addOptional(dye("dyedream_mushroom_mountains"))
                .addOptional(dye("dyedream_forest"));

        tag(dyeTag("dyedream_world_warm_ice_biome"))
                .addOptional(dye("dyedream_plains")).addOptional(dye("dyedream_mushroom_mountains"))
                .addOptional(dye("dyedream_forest")).addOptional(dye("dyedream_flower_field"))
                .addOptional(dye("dyedream_ocean"));

        tag(dyeTag("dyedream_world_cold_biome"))
                .addOptional(dye("dyedream_snowy_peaks"))
                .addOptional(dye("dyedream_cherry_grove"))
                .addOptional(dye("dyedream_snowy_plains"))
                .addOptional(dye("dyedream_frozen_river"))
                .addOptional(dye("dyedream_frozen_ocean"));

        tag(dyeTag("dyedream_world_freeze_biome"))
                .addOptional(dye("dyedream_snowy_peaks"))
                .addOptional(dye("dyedream_cherry_grove"))
                .addOptional(dye("dyedream_snowy_plains"))
                .addOptional(dye("dyedream_frozen_river"));

        // 海泡菜（全维度通用植被，但寒冷群系不含：雪山/雪坡/樱雪森林/雪原/雪针/冻河/冻洋/冷海）
        tag(dyeTag("dyedream_world_sea_pickle_biome"))
                .addOptional(dye("dyedream_plains")).addOptional(dye("dyedream_mushroom_mountains"))
                .addOptional(dye("dyedream_forest")).addOptional(dye("dyedream_flower_field"))
                .addOptional(dye("dyedream_ocean"))
                .addOptional(dye("dyedream_beach")).addOptional(dye("dyedream_river"))
                .addOptional(dye("dyedream_caves")).addOptional(dye("dyedream_lush_caves"))
                .addOptional(dye("dyedream_dripstone_caves"));

        tag(dyeTag("dyedream_world_plains_biome"))
                .addOptional(dye("dyedream_plains"));

        tag(dyeTag("dyedream_world_flower_field_biome"))
                .addOptional(dye("dyedream_flower_field"));

        tag(dyeTag("dyedream_world_stem_biome"))
                .addOptional(dye("dyedream_plains")).addOptional(dye("dyedream_forest"));

        tag(dyeTag("dyedream_world_vine_biome"))
                .addOptional(dye("dyedream_plains")).addOptional(dye("dyedream_mushroom_mountains"))
                .addOptional(dye("dyedream_forest"));

        tag(dyeTag("dyedream_world_forest_biome"))
                .addOptional(dye("dyedream_forest"));

        tag(dyeTag("dyedream_world_mushroom_biome"))
                .addOptional(dye("dyedream_mushroom_mountains"));

        tag(dyeTag("dyedream_world_snowy_ice_pillar_biome"))
                .addOptional(dye("dyedream_snowy_plains"))
                .addOptional(dye("dyedream_cherry_grove"))
                .addOptional(dye("dyedream_frozen_river"));

        tag(dyeTag("dyedream_world_edelweiss_biome"))
                .addOptional(dye("dyedream_snowy_plains"))
                .addOptional(dye("dyedream_cherry_grove"));

        // 染梦樱花林 — 原版樱花树 + 粉红花瓣（独享 冷×湿×陡坡 气候区）
        tag(dyeTag("dyedream_world_cherry_grove_biome"))
                .addOptional(dye("dyedream_cherry_grove"));

        tag(dyeTag("dyedream_world_cloud_pillar_biome"))
                .addOptional(dye("dyedream_snowy_plains"));

        tag(dyeTag("dyedream_world_snowy_tree_biome"))
                .addOptional(dye("dyedream_snowy_plains"));

        tag(dyeTag("dyedream_world_snowy_pool_biome"))
                .addOptional(dye("dyedream_snowy_plains"));

        tag(dyeTag("dyedream_world_kelp_biome"))
                .addOptional(dye("dyedream_cold_ocean")).addOptional(dye("dyedream_ocean"));

        tag(dyeTag("dyedream_world_iceberg_biome"))
                .addOptional(dye("dyedream_frozen_ocean"));

        tag(dyeTag("dyedream_world_lush_biome"))
                .addOptional(dye("dyedream_lush_caves"));

        tag(dyeTag("dyedream_world_dripstone_biome"))
                .addOptional(dye("dyedream_dripstone_caves"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_campsite_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "forest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "taiga"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "cherry_grove"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "savanna"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "meadow"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "flower_forest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "jungle"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "sparse_jungle"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "dark_forest"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_desert_fortress_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "desert"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_garden_decryption_nippy_edelweiss_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_snowy_plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_snowy_peaks"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_cherry_grove"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_dyedream_pavilion_snowy_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_snowy_plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_snowy_peaks"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_cherry_grove"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_ecosystem_bubble_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_frozen_ocean"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_dyedream_plains_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_plains"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_pinkagaric_house_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_mushroom_mountains"));

        tag(BiomeTags.SPAWNS_SNOW_FOXES)
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_snowy_plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_cherry_grove"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_snowy_peaks"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_mob_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_nylium_wastes"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_forest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ruins"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ghost_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ruins"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_nylium_wastes_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_nylium_wastes"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_forest_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_forest"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ruins_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ruins"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_shadownote_ruin_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ruins"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_shadow_dungeon_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_nylium_wastes"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ruins"));

        // 风之旅途维度群系（结构与地物生成用）
        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey_world_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_moor_archipelago"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "misty_dream_cloud_layer"));
    }

    private static TagKey<Biome> dyeTag(String name) {
        return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name));
    }

    private static ResourceLocation dye(String name) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name);
    }
}
