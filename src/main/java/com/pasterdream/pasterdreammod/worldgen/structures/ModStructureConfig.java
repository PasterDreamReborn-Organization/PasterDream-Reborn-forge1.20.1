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

        // === 染梦世界统一结构集 (pasterdream:dyedream_structures) ===
        // 权重 = round(258 / 原spacing)，在统一集 spacing=18 下近似还原原稀有度
        // structureSetWeight为真实生成权重
        // 浮空结构
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_church_0",    "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 29, 8, 35795416,    9, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_church_2",    "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 35, 8, 64595175,    7, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_church_4",    "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 28, 8, 13271459,    9, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_church_6",    "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 36, 8, 34549862,    7, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_crystal_ball", "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 24, 12, 62594864,  11, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_wishing_tree",         "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 48, 24, 15873492, 5, "dyedream_structures"));
        // 地表结构
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:garden_decryption_misty_dreaming_lotus", "pasterdream:dyedream_world_biome", "surface_structures", "beard_box", 0, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 26, 20, 93746251, 10, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:garden_decryption_nine_tailed_fox",       "pasterdream:dyedream_world_biome", "surface_structures", "none", 40, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 48, 32, 28461573, 5, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:garden_decryption_nippy_edelweiss",       "pasterdream:can_garden_decryption_nippy_edelweiss_spawn_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 18, 7, 1889395022, 14, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:traveler_house",        "pasterdream:dyedream_world_land_biome", "surface_structures", "beard_box", 0, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 27, 8, 84729165, 10, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:lifecrystal_cave",      "pasterdream:dyedream_world_land_biome", "surface_structures", "none", -32, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 51, 25, 833118091, 5, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_pavilion_plain",       "pasterdream:dyedream_world_biome", "surface_structures", "none", -1, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 31, 12, 26381947, 8, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_pavilion_snowy_plain", "pasterdream:can_dyedream_pavilion_snowy_spawn_biome", "surface_structures", "none", -1, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 33, 8, 53901862, 8, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_floating_temple",      "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 64, 48, 47912638, 4, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_tavern",       "pasterdream:can_dyedream_plains_spawn_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 32, 16, 60483715, 8, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_campsite",     "pasterdream:can_dyedream_plains_spawn_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 28, 7, 92741583, 9, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:pinkagaric_house",      "pasterdream:can_pinkagaric_house_spawn_biome", "surface_structures", "beard_thin", -3, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 26, 8, 18365492, 10, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_tower_0", "pasterdream:dyedream_world_land_biome", "surface_structures", "beard_thin", 1, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 96, 45, 524960775, 3, "dyedream_structures"));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_tower_1", "pasterdream:dyedream_world_land_biome", "surface_structures", "beard_thin", 1, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 98, 48, 524768400, 3, "dyedream_structures"));

        // === 染梦世界手工结构（已有静态 structure/template_pool JSON，仅加入统一结构集） ===
        // 注：此处 spacing/separation/salt 仅用于权重计算，实际间距由 SHARED_SPACING 统一控制
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_worldtree",    "pasterdream:dyedream_world_biome", "surface_structures", "beard_box",  0,   "WORLD_SURFACE_WG", 96, 2, false, "rigid", "minecraft:empty", 1, 156, 87,  1208134265, 2,  "dyedream_structures", false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_laboratory",   "pasterdream:dyedream_world_biome", "surface_structures", "beard_thin", 0,   "WORLD_SURFACE_WG", 64, 2, false, "rigid", "minecraft:empty", 1, 37,  18,  946202329,  7,  "dyedream_structures", false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dream_train",           "pasterdream:dyedream_world_biome", "surface_structures", "none",      145, "WORLD_SURFACE_WG", 16, 1, false, "rigid", "minecraft:empty", 1, 258, 179, 109243324,  1,  "dyedream_structures", false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:melt_dream_liquid_well","pasterdream:dyedream_world_biome", "surface_structures", "beard_thin", 0,   "WORLD_SURFACE_WG", 64, 2, false, "rigid", "minecraft:empty", 1, 32,  8,   234876502,  8,  "dyedream_structures", false));

        // === 染梦世界冻洋气泡（独立结构集 pasterdream:dyedream_bubbles，仅冻洋上空生成，互不重叠） ===
        // 生态气泡（代码生成 structure/template_pool）
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_ecosystem_bubble", "pasterdream:can_ecosystem_bubble_spawn_biome", "surface_structures", "none", "very_biased_to_bottom", 19, 25, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 25, 6, 38472910, 1, "dyedream_bubbles", true));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:ecosystem_bubble",      "pasterdream:can_ecosystem_bubble_spawn_biome", "surface_structures", "none", "very_biased_to_bottom", 19, 25, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 24, 6, 75018364, 1, "dyedream_bubbles", true));
        // 大气泡（静态 structure/template_pool，三个不同高度层）
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:big_bubbles_0", "pasterdream:can_ecosystem_bubble_spawn_biome", "surface_structures", "none", "very_biased_to_bottom", 24, 40, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 8, 3, 5740443, 4, "dyedream_bubbles", false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:big_bubbles_1", "pasterdream:can_ecosystem_bubble_spawn_biome", "surface_structures", "none", "very_biased_to_bottom", 18, 32, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 7, 3, 6317566, 4, "dyedream_bubbles", false));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:big_bubbles_2", "pasterdream:can_ecosystem_bubble_spawn_biome", "surface_structures", "none", "very_biased_to_bottom", 12, 24, "WORLD_SURFACE_WG", 64, 1, false, "rigid", "minecraft:empty", 1, 8, 4, 6125192, 4, "dyedream_bubbles", false));

        return STRUCTURES;
    }
}
