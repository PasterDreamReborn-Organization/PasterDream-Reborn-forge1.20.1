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

public class ModStructureSetProvider implements DataProvider
{
    private final PackOutput output;
    private final List<StructureGenerationConfig> configs;

    public ModStructureSetProvider(PackOutput output, List<StructureGenerationConfig> configs)
    {
        this.output = output;
        this.configs = configs;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache)
    {
        CompletableFuture<?>[] futures = configs.stream().map(config ->
        {
            JsonObject set = new JsonObject();

            JsonArray structures = new JsonArray();
            JsonObject entry = new JsonObject();
            entry.addProperty("structure", config.name());
            entry.addProperty("weight", config.structureSetWeight());
            structures.add(entry);
            set.add("structures", structures);

            JsonObject placement = new JsonObject();
            placement.addProperty("type", "minecraft:random_spread");
            placement.addProperty("salt", config.salt());
            placement.addProperty("separation", config.separation());
            placement.addProperty("spacing", config.spacing());
            set.add("placement", placement);

            Path path = output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(config.modId()).resolve("worldgen/structure_set/" + config.path() + ".json");

            return DataProvider.saveStable(cache, set, path);
        }).toArray(CompletableFuture[]::new);

        return CompletableFuture.allOf(futures);
    }

    @Override
    public String getName()
    {
        return "Structure Sets: multiple";
    }
}
