package com.pasterdream.pasterdreammod.worldgen.structures;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.helper.structuregenerate.StructureGenerationConfig;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModTemplatePoolProvider implements DataProvider
{
    private final PackOutput output;
    private final List<StructureGenerationConfig> configs;

    public ModTemplatePoolProvider(PackOutput output, List<StructureGenerationConfig> configs)
    {
        this.output = output;
        this.configs = configs;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache)
    {
        CompletableFuture<?>[] futures = configs.stream().map(config ->
        {
            JsonObject pool = new JsonObject();
            pool.addProperty("fallback", "minecraft:empty");

            JsonArray elements = new JsonArray();
            JsonObject element = new JsonObject();
            element.addProperty("weight", config.poolElementWeight());

            JsonObject inner = new JsonObject();
            inner.addProperty("element_type", "minecraft:single_pool_element");
            inner.addProperty("location", config.name());
            inner.addProperty("projection", config.projection());
            inner.addProperty("processors", config.processors());

            element.add("element", inner);
            elements.add(element);
            pool.add("elements", elements);

            Path path = output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(config.modId()).resolve("worldgen/template_pool/" + config.path() + "_pool.json");

            return DataProvider.saveStable(cache, pool, path);
        }).toArray(CompletableFuture[]::new);

        return CompletableFuture.allOf(futures);
    }

    @Override
    public String getName()
    {
        return "Template Pools: multiple";
    }
}
