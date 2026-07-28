package com.pasterdream.pasterdreammod.helper.structuregenerate;

import net.minecraft.resources.ResourceLocation;

public record StructureGenerationConfig(
        String name,                //结构名（同时也是NBT名、模板池名）
        String biomeTag,            //允许生成的群系标签，例如"pasterdream:can_fisherman_hut_spawn_biome"
        String step,                //生成阶段，如 "surface_structures"
        String terrainAdaptation,   //地形适应，"none"：直接硬摆放，"beard_thin"：将结构最下层方块向下延伸，"beard_box"：形成地基，"bury"：埋入地下
        String heightType,          //高度类型，"absolute"：固定高度，其他如 "very_biased_to_bottom"：范围高度
        int minHeight,              //最小高度（范围高度时使用）
        int maxHeight,              //最大高度（范围高度时使用）
        String heightmap,           //高度图类型，如 "WORLD_SURFACE_WG"
        int maxDistanceFromCenter,  //最大中心距离（结构拼图最大距离）
        int size,                   //jigsaw大小（结构拼图数量）
        boolean useExpansionHack,   //是否使用扩展 hack
        String projection,          //模板投影，"rigid"：不投影，"terrain_matching"：地形投影
        String processors,          //处理器，替换、随机化方块等，如"minecraft:empty"，"minecraft:fossil_rot"
        int poolElementWeight,      //该模板在木板池中的元素权重
        int spacing,                //结构在世界中生成的平均距离间隔（区块）
        int separation,             //结构在世界中生成的最小距离间隔（区块）
        int salt,                   //随机种子盐
        int structureSetWeight,     //结构集内该结构的权重
        String groupSetId,          //若不为null，结构归入此共享结构集（如"dyedream_structures"），不再生成独立结构集
        boolean generateStructureFiles //是否由datagen生成structure/pool JSON（手工结构已有静态文件时设为false）
)
{
    /** 兼容旧构造函数：固定绝对高度，独立结构集 */
    public StructureGenerationConfig(
            String name, String biomeTag, String step, String terrainAdaptation,
            int startHeight, String heightmap, int maxDistanceFromCenter, int size,
            boolean useExpansionHack, String projection, String processors,
            int poolElementWeight, int spacing, int separation, int salt, int structureSetWeight)
    {
        this(name, biomeTag, step, terrainAdaptation,
                "absolute", startHeight, startHeight,
                heightmap, maxDistanceFromCenter, size,
                useExpansionHack, projection, processors,
                poolElementWeight, spacing, separation, salt, structureSetWeight,
                null, true);
    }

    /** 固定绝对高度 + 共享结构集分组 */
    public StructureGenerationConfig(
            String name, String biomeTag, String step, String terrainAdaptation,
            int startHeight, String heightmap, int maxDistanceFromCenter, int size,
            boolean useExpansionHack, String projection, String processors,
            int poolElementWeight, int spacing, int separation, int salt, int structureSetWeight,
            String groupSetId)
    {
        this(name, biomeTag, step, terrainAdaptation,
                "absolute", startHeight, startHeight,
                heightmap, maxDistanceFromCenter, size,
                useExpansionHack, projection, processors,
                poolElementWeight, spacing, separation, salt, structureSetWeight,
                groupSetId, true);
    }

    /** 固定绝对高度 + 共享结构集分组 + 控制是否生成结构文件 */
    public StructureGenerationConfig(
            String name, String biomeTag, String step, String terrainAdaptation,
            int startHeight, String heightmap, int maxDistanceFromCenter, int size,
            boolean useExpansionHack, String projection, String processors,
            int poolElementWeight, int spacing, int separation, int salt, int structureSetWeight,
            String groupSetId, boolean generateStructureFiles)
    {
        this(name, biomeTag, step, terrainAdaptation,
                "absolute", startHeight, startHeight,
                heightmap, maxDistanceFromCenter, size,
                useExpansionHack, projection, processors,
                poolElementWeight, spacing, separation, salt, structureSetWeight,
                groupSetId, generateStructureFiles);
    }

    public String modId()
    {
        return ResourceLocation.parse(name).getNamespace();
    }

    public String path()
    {
        return ResourceLocation.parse(name).getPath();
    }
}