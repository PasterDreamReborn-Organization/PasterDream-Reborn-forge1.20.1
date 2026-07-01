package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends BiomeTagsProvider
{
    public ModBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider)
    {
        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_white_corolla_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "snowy_slopes"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "jagged_peaks"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "frozen_peaks"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_soul_ore_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "soul_sand_valley"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_fisherman_hut_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "river"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "beach"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "swamp"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "mangrove_swamp"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "forest"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_flower_field_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "flower_forest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "jungle"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_spawn_in_crimson_forest")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "crimson_forest"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_reed_spawn_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "swamp"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", "mangrove_swamp"));

        tag(TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world_biome")))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_mushroom_mountains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_snowy_plains"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_frozen_ocean"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_ocean"));
    }
}
