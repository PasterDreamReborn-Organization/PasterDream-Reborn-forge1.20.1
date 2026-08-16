package com.pasterdream.pasterdreammod.helper.sanbiomeratemanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SanBiomeRateManager extends SimpleJsonResourceReloadListener
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SanBiomeRateManager.class);
    private static final Gson GSON = new GsonBuilder().create();
    private static final Map<ResourceLocation, Double> BIOME_RATES = new HashMap<>();

    public static final SanBiomeRateManager INSTANCE = new SanBiomeRateManager();

    private SanBiomeRateManager()
    {
        super(GSON, "san_biome_rates");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> entries, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler)
    {
        BIOME_RATES.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : entries.entrySet())
        {
            ResourceLocation fileId = entry.getKey();
            JsonElement json = entry.getValue();
            try
            {
                if (!json.isJsonObject())
                {
                    continue;
                }

                JsonObject obj = json.getAsJsonObject();
                if (!obj.has("biome") || !obj.has("rate"))
                {
                    continue;
                }

                ResourceLocation biome = ResourceLocation.parse(obj.get("biome").getAsString());
                double rate = obj.get("rate").getAsDouble();
                BIOME_RATES.put(biome, rate);
            }
            catch (Exception e)
            {
                LOGGER.warn("群系san值文件加载错误，文件名：{}", fileId, e);
            }
        }
    }

    public static double getRate(ResourceLocation biome)
    {
        return BIOME_RATES.getOrDefault(biome, 0.0);
    }
}
