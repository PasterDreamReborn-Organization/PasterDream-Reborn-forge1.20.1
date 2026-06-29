package com.pasterdream.pasterdreammod.worldgen.biome;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModBiomeModifierProvider implements DataProvider
{
    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registries;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public ModBiomeModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        this.output = output;
        this.registries = registries;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache)
    {
        return registries.thenCompose(provider ->
        {
            Map<ResourceLocation, JsonObject> entries = new HashMap<>();
            HolderLookup.RegistryLookup<PlacedFeature> featureLookup = provider.lookupOrThrow(Registries.PLACED_FEATURE);

            addPatch(entries, "goldenrod_patch", ModPlacedFeatures.GOLDENROD_PATCH, featureLookup, BiomeTags.IS_OVERWORLD);
            addPatch(entries, "ferraria_crispa_patch", ModPlacedFeatures.FERRARIA_CRISPA_PATCH, featureLookup, BiomeTags.IS_OVERWORLD);
            addPatch(entries, "malva_sinensis_cavan_patch", ModPlacedFeatures.MALVA_SINENSIS_CAVAN_PATCH, featureLookup, BiomeTags.IS_OVERWORLD);
            addPatch(entries, "eustoma_patch", ModPlacedFeatures.EUSTOMA_PATCH, featureLookup, BiomeTags.IS_OVERWORLD);
            addPatch(entries, "oats_patch", ModPlacedFeatures.OATS_PATCH, featureLookup, BiomeTags.IS_OVERWORLD);
            addPatch(entries, "rye_patch", ModPlacedFeatures.RYE_PATCH, featureLookup, BiomeTags.IS_OVERWORLD);
            addPatch(entries, "white_corolla_crop_patch", ModPlacedFeatures.WHITE_COROLLA_CROP_PATCH, featureLookup, TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_white_corolla_spawn_biome")));
            addPatch(entries, "cotton_crop_patch", ModPlacedFeatures.COTTON_CROP_PATCH, featureLookup, BiomeTags.IS_OVERWORLD);

            addPatch(entries, "deepslate_titanium_ore_patch", ModPlacedFeatures.DEEPSLATE_TITANIUM_ORE_PATCH, featureLookup, BiomeTags.IS_OVERWORLD);
            addPatch(entries, "molten_gold_ore_patch", ModPlacedFeatures.MOLTEN_GOLD_ORE_PATCH, featureLookup, BiomeTags.IS_NETHER);
            addPatch(entries, "soul_ore_patch", ModPlacedFeatures.SOUL_ORE_PATCH, featureLookup, TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_soul_ore_spawn_biome")));

            return saveAll(cache, entries);
        });
    }

    private void addPatch(Map<ResourceLocation, JsonObject> map, String name, ResourceKey<PlacedFeature> featureKey, HolderLookup.RegistryLookup<PlacedFeature> featureLookup, TagKey<Biome> biomeTag)
    {
        ResourceLocation featureId = featureKey.location();
        JsonObject json = new JsonObject();
        json.addProperty("type", "forge:add_features");
        json.addProperty("biomes", "#" + biomeTag.location());
        json.addProperty("features", featureId.toString());
        json.addProperty("step", GenerationStep.Decoration.VEGETAL_DECORATION.getName());
        map.put(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name), json);
    }

    private CompletableFuture<?> saveAll(CachedOutput cache, Map<ResourceLocation, JsonObject> entries)
    {
        return CompletableFuture.allOf(entries.entrySet().stream().map(entry ->
        {
            ResourceLocation id = entry.getKey();
            JsonObject json = entry.getValue();
            Path path = output.getOutputFolder().resolve("data/" + id.getNamespace() + "/forge/biome_modifier/" + id.getPath() + ".json");
            return DataProvider.saveStable(cache, json, path);
        }).toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName()
    {
        return "Biome Modifiers: " + PasterDreamMod.MOD_ID;
    }
}
