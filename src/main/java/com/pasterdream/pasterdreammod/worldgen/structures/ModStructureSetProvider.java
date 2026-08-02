package com.pasterdream.pasterdreammod.worldgen.structures;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.helper.structuregenerate.StructureGenerationConfig;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ModStructureSetProvider implements DataProvider
{
    private final PackOutput output;
    private final List<StructureGenerationConfig> configs;

    /** 默认统一结构集网格间距（区块），未在 GROUP_CONFIG 中配置的组使用此值 */
    private static final int DEFAULT_SHARED_SPACING = 16;
    /** 默认统一结构集中结构之间的最小距离（区块） */
    private static final int DEFAULT_SHARED_SEPARATION = 6;

    /** 各组独立的 spacing/separation 配置，key=groupSetId, value[0]=spacing, value[1]=separation */
    private static final Map<String, int[]> GROUP_CONFIG = Map.of(
        "dyedream_structures", new int[]{14, 6},
        "dyedream_bubbles",    new int[]{4, 2},
        "shadow_structures",   new int[]{20, 8}
    );

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

        // 独立结构集（非分组结构），使用 minecraft:random_spread
        for (var config : individualConfigs)
        {
            futures.add(generateIndividualSet(cache, config));
        }

        // 共享结构集（分组结构统一到一个 set，权重控制稀有度）
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

        int[] cfg = GROUP_CONFIG.getOrDefault(groupId, new int[]{DEFAULT_SHARED_SPACING, DEFAULT_SHARED_SEPARATION});

        JsonObject placement = new JsonObject();
        placement.addProperty("type", "minecraft:random_spread");
        placement.addProperty("salt", 987654321);
        placement.addProperty("separation", cfg[1]);
        placement.addProperty("spacing", cfg[0]);

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
