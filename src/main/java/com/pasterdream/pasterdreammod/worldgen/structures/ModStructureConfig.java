package com.pasterdream.pasterdreammod.worldgen.structures;

import com.pasterdream.pasterdreammod.helper.structuregenerate.StructureGenerationConfig;

import java.util.ArrayList;
import java.util.List;

public class ModStructureConfig
{
    // ============================================================
    // 关于 spacing / separation / salt 的说明：
    //
    // groupSetId == null 的独立结构（主世界结构、气泡）：
    //   spacing/separation/salt 直接写入结构集 JSON，由该结构独占使用。
    //
    // groupSetId != null 的分组结构（染梦统一集、气泡集等）：
    //   spacing/separation/salt 仅用于计算 structureSetWeight（权重），
    //   参考公式：weight = round(258 / 原spacing)
    //   实际生成间距由 ModStructureSetProvider 中的 GROUP_CONFIG 按组配置，
    //   未配置的组使用 DEFAULT_SHARED_SPACING / DEFAULT_SHARED_SEPARATION。
    //   各结构在共享集内通过权重竞争，spacing/separation 参数本身不生效。
    // ============================================================
    //
    // 统一结构集权重基准：dream_train 原 spacing=258 为最稀有结构，权重 1
    // 其余结构权重 = round(258 / 原spacing)
    // structureSetWeight 为真实生成权重，直接写入结构集 JSON

    public static List<StructureGenerationConfig> getStructureConfig()
    {
        List<StructureGenerationConfig> STRUCTURES = new ArrayList<>();

        // === 主世界独立结构 ===
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:oak_fisherman_hut", "pasterdream:can_fisherman_hut_spawn_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 25, 8, 15673946, 1));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:spruce_fisherman_hut", "pasterdream:can_fisherman_hut_spawn_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 47, 8, 56179235, 1));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_crack", "minecraft:is_overworld", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 48, 16, 33554432, 1));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:campsite_overworld", "pasterdream:can_campsite_spawn_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 23, 8, 72918463, 1));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:the_lost_sword_tomb", "minecraft:is_jungle", "surface_structures", "beard_thin", -2, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 32, 8, 41827365, 1));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:desert_cottage", "pasterdream:can_desert_fortress_spawn_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 32, 8, 57863428, 1));
        // === 染梦世界统一结构集 (pasterdream:dyedream_structures) ===
        // 权重 = round(258 / 原spacing)，在统一集 spacing=18 下近似还原原稀有度
        // structureSetWeight为真实生成权重
        // 浮空结构
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_church_1",    "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 29, 8, 35795416,    16, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_church_2",    "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 35, 8, 64595175,    16, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:invaded_church",      "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 32, 8, 72946153,    8, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_crystal_ball", "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 24, 12, 62594864,  11, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_wishing_tree",         "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 48, 24, 15873492, 5, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:fluffy_wind_church",      "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 32, 8, 82985163,    8, "dyedream_structures"));

        // 地表结构
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:garden_decryption_misty_dreaming_lotus", "pasterdream:dyedream_world_biome", "surface_structures", "beard_box", 0, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 26, 20, 93746251, 14, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:garden_decryption_nine_tailed_fox",       "pasterdream:dyedream_world_biome", "surface_structures", "none", 40, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 48, 32, 28461573, 9, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:garden_decryption_nippy_edelweiss",       "pasterdream:can_garden_decryption_nippy_edelweiss_spawn_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 18, 7, 1889395022, 18, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:traveler_house",        "pasterdream:dyedream_world_land_biome", "surface_structures", "beard_box", 0, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 27, 8, 84729165, 14, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:lifecrystal_cave",      "pasterdream:dyedream_world_land_biome", "surface_structures", "none", -32, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 51, 25, 833118091, 9, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_pavilion_plain",       "pasterdream:dyedream_world_biome", "surface_structures", "none", -1, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 31, 12, 26381947, 12, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_pavilion_snowy_plain", "pasterdream:can_dyedream_pavilion_snowy_spawn_biome", "surface_structures", "none", -1, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 33, 8, 53901862, 12, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_floating_temple",      "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 64, 48, 47912638, 9, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_tavern",       "pasterdream:can_dyedream_plains_spawn_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 32, 16, 60483715, 14, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_campsite",     "pasterdream:can_dyedream_plains_spawn_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 28, 7, 92741583, 13, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_tower_0", "pasterdream:dyedream_world_land_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 96, 45, 524960775, 10, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_tower_1", "pasterdream:dyedream_world_land_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 98, 48, 524768400, 10, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_sky_island", "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 30, 8, 48261937, 9, "dyedream_structures"));

        // === 染梦世界手工结构（已有静态 structure/template_pool JSON，仅加入统一结构集） ===
        // 注：此处 spacing/separation/salt 仅用于权重计算，实际间距由 SHARED_SPACING 统一控制
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_worldtree",    "pasterdream:dyedream_world_biome", "surface_structures", "beard_box",  0,   "WORLD_SURFACE_WG", 96, 2, false, "rigid", "minecraft:empty", 1, 156, 87,  1208134265, 1,  "dyedream_structures", false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_laboratory",   "pasterdream:dyedream_world_biome", "surface_structures", "beard_thin", 0,   "WORLD_SURFACE_WG", 64, 2, false, "rigid", "minecraft:empty", 1, 37,  18,  946202329,  9,  "dyedream_structures", false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dream_train",           "pasterdream:dyedream_world_biome", "surface_structures", "none",      145, "WORLD_SURFACE_WG", 16, 1, false, "rigid", "minecraft:empty", 1, 258, 179, 109243324,  1,  "dyedream_structures", false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:melt_dream_liquid_well","pasterdream:dyedream_world_biome", "surface_structures", "beard_thin", 0,   "WORLD_SURFACE_WG", 64, 2, false, "rigid", "minecraft:empty", 1, 32,  8,   234876502,  10,  "dyedream_structures", false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:pinkagaric_house",      "pasterdream:can_pinkagaric_house_spawn_biome", "surface_structures", "none", 0, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 26, 8, 18365492, 14, "dyedream_structures", false));

        // === 染梦世界冻洋气泡（独立结构集 pasterdream:dyedream_bubbles，仅冻洋上空生成，互不重叠） ===
        // 生态气泡（代码生成 structure/template_pool）
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_ecosystem_bubble", "pasterdream:can_ecosystem_bubble_spawn_biome", "surface_structures", "none", "very_biased_to_bottom", 19, 25, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 25, 6, 38472910, 1, "dyedream_bubbles", true));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:ecosystem_bubble",      "pasterdream:can_ecosystem_bubble_spawn_biome", "surface_structures", "none", "very_biased_to_bottom", 19, 25, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 24, 6, 75018364, 1, "dyedream_bubbles", true));
        // 大气泡（静态 structure/template_pool，三个不同高度层）
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:big_bubbles_0", "pasterdream:can_ecosystem_bubble_spawn_biome", "surface_structures", "none", "very_biased_to_bottom", 24, 40, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 8, 3, 5740443, 4, "dyedream_bubbles", false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:big_bubbles_1", "pasterdream:can_ecosystem_bubble_spawn_biome", "surface_structures", "none", "very_biased_to_bottom", 18, 32, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 7, 3, 6317566, 4, "dyedream_bubbles", false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:big_bubbles_2", "pasterdream:can_ecosystem_bubble_spawn_biome", "surface_structures", "none", "very_biased_to_bottom", 12, 24, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 8, 4, 6125192, 4, "dyedream_bubbles", false));

        // === 灯影之下阴影古迹结构（共享结构集 pasterdream:shadow_structures） ===
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:shadownote_ruin_0", "pasterdream:can_shadownote_ruin_spawn_biome", "surface_structures", "none", -2, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 22, 8, 38472910, 2, "shadow_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:shadownote_ruin_1", "pasterdream:can_shadownote_ruin_spawn_biome", "surface_structures", "none", -2, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 22, 8, 59184736, 2, "shadow_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:shadownote_ruin_2", "pasterdream:can_shadownote_ruin_spawn_biome", "surface_structures", "none", -2, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 22, 8, 72639482, 2, "shadow_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:shadow_shelter", "pasterdream:can_shadownote_ruin_spawn_biome", "surface_structures", "none", -6, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 45, 23, 1429029018, 2, "shadow_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:shadow_nest", "pasterdream:shadow_forest_spawn_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 35, 15, 1811985292, 7, "shadow_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:shadow_fungus_house", "pasterdream:shadow_forest_spawn_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 42, 25, 486700517, 6, "shadow_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:shadow_foundry", "pasterdream:shadow_ruins_spawn_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 58, 32, 768368124, 2, "shadow_structures"));

        // === 风之旅途维度结构 ===
        // 注：这些结构使用静态 structure/template_pool JSON（generateStructureFiles=false），
        //     此处 config 仅驱动 structure_set 的 spacing/separation/salt/weight 生成。
        //     biomeTag/heightmap 等字段为占位，实际群系与高度以静态 JSON 为准。

        // --- 风泊群岛（biome_0） ---
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:wind_island", "pasterdream:wind_moor_archipelago", "top_layer_modification", "none", "very_biased_to_bottom", 62, 75, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 7, 4, 610650667, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:windmill_lodge", "pasterdream:wind_moor_archipelago", "surface_structures", "none", "very_biased_to_bottom", 100, 120, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 73, 36, 270611341, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:lost_windknight_ruins", "pasterdream:wind_moor_archipelago", "surface_structures", "none", "very_biased_to_bottom", 120, 140, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 42, 25, 1344649511, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:windmoor_tree", "pasterdream:wind_moor_archipelago", "top_layer_modification", "none", "very_biased_to_bottom", 54, 64, "", 64, 1, false, "rigid", "minecraft:empty", 1, 3, 2, 707951846, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:wind_infested_stone_0", "pasterdream:wind_moor_archipelago", "vegetal_decoration", "none", "very_biased_to_bottom", 59, 63, "", 64, 1, false, "rigid", "minecraft:empty", 1, 2, 1, 114139522, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:wind_infested_stone_1", "pasterdream:wind_moor_archipelago", "vegetal_decoration", "none", "very_biased_to_bottom", 59, 63, "", 64, 1, false, "rigid", "minecraft:empty", 1, 2, 1, 113947148, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:wind_pond", "pasterdream:wind_moor_archipelago", "top_layer_modification", "none", "very_biased_to_bottom", 59, 63, "", 64, 1, false, "rigid", "minecraft:empty", 1, 3, 2, 737290576, 1, null, false));

        // --- 迷梦云层（biome_1） ---
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:big_bubbles_6", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 42, 64, "", 64, 1, false, "rigid", "minecraft:empty", 1, 4, 2, 5355694, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:big_bubbles_7", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 43, 63, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 5, 3, 2145721488, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:bocchi_0", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 78, 140, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 32, 19, 2125868828, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:bocchi_1", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 78, 140, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 120, 34, 2118750973, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:breakwing_curtain", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 74, 116, "", 64, 1, false, "rigid", "minecraft:empty", 1, 8, 4, 133099465, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:hakurei_reimu", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 78, 140, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 33, 20, 611019746, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:hot_air_balloon_0", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 75, 124, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 24, 10, 652828939, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:hot_air_balloon_1", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 75, 124, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 24, 10, 653406063, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:hot_air_balloon_2", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 75, 124, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 24, 10, 653213688, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:hot_air_balloon_3", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 76, 126, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 23, 11, 652251816, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:hot_air_balloon_4", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 77, 125, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 23, 13, 652059442, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:hot_air_balloon_5", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 77, 125, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 43, 25, 652636565, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:hot_air_balloon_6", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 79, 127, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 37, 21, 652444191, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:hot_air_balloon_7", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 82, 129, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 49, 31, 694574195, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:small_ballon_0", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 70, 120, "", 64, 1, false, "rigid", "minecraft:empty", 1, 10, 2, 303245205, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:small_ballon_1", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 70, 120, "", 64, 1, false, "rigid", "minecraft:empty", 1, 10, 2, 302283333, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:small_ballon_2", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 70, 120, "", 64, 1, false, "rigid", "minecraft:empty", 1, 10, 2, 302090959, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:small_ballon_3", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 70, 120, "", 64, 1, false, "rigid", "minecraft:empty", 1, 10, 2, 302668082, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:small_ballon_4", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 70, 120, "", 64, 1, false, "rigid", "minecraft:empty", 1, 10, 2, 302475708, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:small_ballon_5", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 70, 120, "", 64, 1, false, "rigid", "minecraft:empty", 1, 10, 2, 301513835, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:small_ballon_6", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 70, 120, "", 64, 1, false, "rigid", "minecraft:empty", 1, 10, 2, 301321461, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:small_ballon_7", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 70, 120, "", 64, 1, false, "rigid", "minecraft:empty", 1, 10, 2, 301898584, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:small_ballon_8", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 70, 120, "", 64, 1, false, "rigid", "minecraft:empty", 1, 10, 2, 301706210, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:small_ballon_9", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 70, 120, "", 64, 1, false, "rigid", "minecraft:empty", 1, 10, 2, 306900320, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:small_ballon_10", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 70, 120, "", 64, 1, false, "rigid", "minecraft:empty", 1, 10, 2, 1855702116, 1, null, false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:christmas_tree", "pasterdream:misty_dream_cloud_layer", "surface_structures", "none", "very_biased_to_bottom", 55, 80, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 48, 24, 885566331, 1, null, false));

        return STRUCTURES;
    }
}
