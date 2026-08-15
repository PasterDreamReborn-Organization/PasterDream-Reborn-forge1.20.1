package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagsProvider extends FluidTagsProvider
{
    public ModFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {

        tag(TagKey.create(BuiltInRegistries.FLUID.key(), ResourceLocation.fromNamespaceAndPath("forge", "tea")))
                .add(ModFluids.DYEDREAM_FLOWER_TEA.get())
                .add(ModFluids.GOLDENROD_TEA.get())
                .add(ModFluids.UNCOOKED_DYEDREAM_FLOWER_TEA.get());

        tag(TagKey.create(BuiltInRegistries.FLUID.key(), ResourceLocation.fromNamespaceAndPath("forge", "juice")))
                .add(ModFluids.APPLE_JUICE.get())
                .add(ModFluids.DREAM_JUICE.get())
                .add(ModFluids.DYEDREAM_JUICE.get())
                .add(ModFluids.HONEY_JUICE.get())
                .add(ModFluids.WATERMELON_JUICE.get());

        tag(TagKey.create(BuiltInRegistries.FLUID.key(), ResourceLocation.fromNamespaceAndPath("forge", "apple_juice")))
                .add(ModFluids.APPLE_JUICE.get());

        tag(TagKey.create(BuiltInRegistries.FLUID.key(), ResourceLocation.fromNamespaceAndPath("forge", "melon_juice")))
                .add(ModFluids.WATERMELON_JUICE.get());

        tag(TagKey.create(BuiltInRegistries.FLUID.key(), ResourceLocation.fromNamespaceAndPath("forge", "honey")))
                .add(ModFluids.HONEY_JUICE.get());

        tag(TagKey.create(BuiltInRegistries.FLUID.key(), ResourceLocation.fromNamespaceAndPath("forge", "ink")))
                .add(ModFluids.INK.get());
    }
}
