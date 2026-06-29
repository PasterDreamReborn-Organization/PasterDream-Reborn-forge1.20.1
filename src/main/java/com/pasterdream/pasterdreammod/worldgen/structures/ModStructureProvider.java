package com.pasterdream.pasterdreammod.worldgen.structures;

import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.helper.structuregenerate.StructureGenerationConfig;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModStructureProvider implements DataProvider
{
    private final PackOutput output;
    private final List<StructureGenerationConfig> configs;

    public ModStructureProvider(PackOutput output, List<StructureGenerationConfig> configs)
    {
        this.output = output;
        this.configs = configs;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache)
    {
        CompletableFuture<?>[] futures = configs.stream().map(config ->
        {
            JsonObject structure = new JsonObject();
            structure.addProperty("type", "minecraft:jigsaw");
            structure.addProperty("biomes", "#" + config.biomeTag());
            structure.addProperty("step", config.step());
            structure.add("spawn_overrides", new JsonObject());
            structure.addProperty("terrain_adaptation", config.terrainAdaptation());
            structure.addProperty("start_pool", config.name() + "_pool");
            structure.addProperty("size", config.size());

            JsonObject startHeight = new JsonObject();
            startHeight.addProperty("absolute", config.startHeight());
            structure.add("start_height", startHeight);

            structure.addProperty("project_start_to_heightmap", config.heightmap());
            structure.addProperty("max_distance_from_center", config.maxDistanceFromCenter());
            structure.addProperty("use_expansion_hack", config.useExpansionHack());

            Path path = output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(config.modId()).resolve("worldgen/structure/" + config.path() + ".json");

            return DataProvider.saveStable(cache, structure, path);
        }).toArray(CompletableFuture[]::new);

        return CompletableFuture.allOf(futures);
    }

    @Override
    public String getName()
    {
        return "Structures: multiple";
    }
}