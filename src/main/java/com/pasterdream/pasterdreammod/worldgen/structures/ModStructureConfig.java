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

        return STRUCTURES;
    }
}
