package com.pasterdream.pasterdreammod.worldgen.structures;

import com.pasterdream.pasterdreammod.helper.structuregenerate.StructureGenerationConfig;

import java.util.ArrayList;
import java.util.List;

public class ModStructureConfig
{
    public static List<StructureGenerationConfig> getStructureConfig()
    {
        List<StructureGenerationConfig> STRUCTURES = new ArrayList<>();

        STRUCTURES.add(new StructureGenerationConfig("pasterdream:oak_fisherman_hut", "pasterdream:can_fisherman_hut_spawn_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 32, 8, 15673946, 1));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:spruce_fisherman_hut", "pasterdream:can_fisherman_hut_spawn_biome", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 32, 8, 56179235, 1));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dyedream_crack", "minecraft:is_overworld", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 16, 4, 33554432, 1));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:campsite_overworld", "minecraft:is_overworld", "surface_structures", "beard_thin", 0, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 32, 8, 72918463, 1));

        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dream_church_0", "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 32, 8, 35795416, 1));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dream_church_2", "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 32, 8, 64595175, 1));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dream_church_4", "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 32, 8, 13271459, 1));
        STRUCTURES.add(new StructureGenerationConfig("pasterdream:dream_church_6", "pasterdream:dyedream_world_biome", "surface_structures", "none", 64, "WORLD_SURFACE_WG", 1, 1, false, "rigid", "minecraft:empty", 1, 32, 8, 34549862, 1));

        return STRUCTURES;
    }
}
