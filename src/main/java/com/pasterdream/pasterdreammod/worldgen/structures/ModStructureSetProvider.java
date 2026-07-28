package com.pasterdream.pasterdreammod.worldgen.structures;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.helper.structuregenerate.StructureGenerationConfig;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModStructureSetProvider implements DataProvider
{
    private final PackOutput output;
    private final List<StructureGenerationConfig> configs;

    /** 统一结构集的网格间距（区块），世界每SHARED_SPACING区块尝试生成一个结构 */
    private static final int SHARED_SPACING = 18;
    /** 统一结构集中两个结构之间的最小距离（区块） */
    private static final int SHARED_SEPARATION = 6;

    public ModStructureSetProvider(PackOutput output, List<StructureGenerationConfig> configs)
    {
        this.output = output;
        this.configs = configs;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache)
    {
        List<StructureGenerationConfig> individualConfigs = new ArrayList<>();
        Map<String, List<StructureGenerationConfig>> groups = new LinkedHashMap<>();

        for (var config : configs)
        {
            if (config.groupSetId() != null)
            {
                groups.computeIfAbsent(config.groupSetId(), k -> new ArrayList<>()).add(config);
            }
            else
            {
                individualConfigs.add(config);
            }
        }

        List<CompletableFuture<?>> futures = new ArrayList<>();

        // 独立结构集（非分组结构）
        for (var config : individualConfigs)
        {
            futures.add(generateIndividualSet(cache, config));
        }

        // 共享结构集（分组结构）
        for (var entry : groups.entrySet())
        {
            String groupId = entry.getKey();
            List<StructureGenerationConfig> groupConfigs = entry.getValue();
            futures.add(generateSharedSet(cache, groupId, groupConfigs));
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    private CompletableFuture<?> generateIndividualSet(CachedOutput cache, StructureGenerationConfig config)
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

        Path path = output.getOutputFolder(PackOutput.Target.DATA_PACK)
                .resolve(config.modId())
                .resolve("worldgen/structure_set/" + config.path() + ".json");

        return DataProvider.saveStable(cache, set, path);
    }

    private CompletableFuture<?> generateSharedSet(CachedOutput cache, String groupId, List<StructureGenerationConfig> groupConfigs)
    {
        if (groupConfigs.isEmpty()) return CompletableFuture.completedFuture(null);

        StructureGenerationConfig first = groupConfigs.get(0);

        JsonObject set = new JsonObject();

        JsonArray structures = new JsonArray();
        for (var config : groupConfigs)
        {
            JsonObject entry = new JsonObject();
            entry.addProperty("structure", config.name());
            entry.addProperty("weight", config.structureSetWeight());
            structures.add(entry);
        }
        set.add("structures", structures);

        JsonObject placement = new JsonObject();
        placement.addProperty("type", "minecraft:random_spread");
        placement.addProperty("salt", 987654321);
        placement.addProperty("separation", SHARED_SEPARATION);
        placement.addProperty("spacing", SHARED_SPACING);

        set.add("placement", placement);

        Path path = output.getOutputFolder(PackOutput.Target.DATA_PACK)
                .resolve(first.modId())
                .resolve("worldgen/structure_set/" + groupId + ".json");

        return DataProvider.saveStable(cache, set, path);
    }

    @Override
    public String getName()
    {
        return "Structure Sets: multiple";
    }
}
