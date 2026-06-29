package com.pasterdream.pasterdreammod.helper.structuregenerate;

import net.minecraft.resources.ResourceLocation;

public record StructureGenerationConfig(
        String name,                //结构名（同时也是NBT名、模板池名）
        String biomeTag,            //允许生成的群系标签，例如"pasterdream:can_fisherman_hut_spawn_biome"
        String step,                //生成阶段，如 "surface_structures"
        String terrainAdaptation,   //地形适应，"none"：直接硬摆放，"beard_thin"：将结构最下层方块向下延伸，"beard_box"：形成地基，"bury"：埋入地下
        int startHeight,            //起始绝对高度
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
        int structureSetWeight      //结构集内该结构的权重
)
{
    public String modId()
    {
        return ResourceLocation.parse(name).getNamespace();
    }

    public String path()
    {
        return ResourceLocation.parse(name).getPath();
    }
}