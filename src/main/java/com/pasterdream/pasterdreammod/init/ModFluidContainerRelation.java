package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.helper.FluidContainerCapability.FluidContainerRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFluidContainerRelation
{
    public static void registerFluidContainerRelation()
    {
        FluidContainerRegistry.register(ModItems.GLASS_JAR.get(), Fluids.WATER, 125, ModItems.GLASS_JAR_OF_WATER.get());
        FluidContainerRegistry.register(ModItems.GLASS_JAR.get(), ForgeMod.MILK.get(), 125, ModItems.GLASS_JAR_OF_MILK.get());
        FluidContainerRegistry.register(ModItems.GLASS_JAR.get(), ModFluids.DREAM_JUICE.get(), 125, ModItems.GLASS_JAR_OF_DREAM_JUICE.get());
        FluidContainerRegistry.register(ModItems.GLASS_JAR.get(), ModFluids.GOLDENROD_TEA.get(), 125, ModItems.GLASS_JAR_OF_GOLDENROD_TEA.get());
        FluidContainerRegistry.register(ModItems.GLASS_JAR.get(), ModFluids.GUIDING_DRUG.get(), 125, ModItems.GLASS_JAR_OF_GUIDING_DRUG.get());
        FluidContainerRegistry.register(ModItems.GLASS_JAR.get(), ModFluids.WIND_PLANT_EXTRACT.get(), 125, ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get());
        FluidContainerRegistry.register(ModItems.GLASS_JAR.get(), ModFluids.YEAST.get(), 125, ModItems.GLASS_JAR_OF_YEAST.get());

        FluidContainerRegistry.register(ModItems.GLASS_CUP.get(), ModFluids.APPLE_JUICE.get(), 250, ModItems.GLASS_CUP_OF_APPLE_JUICE.get());
        FluidContainerRegistry.register(ModItems.GLASS_CUP.get(), ModFluids.UNCOOKED_DYEDREAM_FLOWER_TEA.get(), 250, ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get());
        FluidContainerRegistry.register(ModItems.GLASS_CUP.get(), ModFluids.DYEDREAM_FLOWER_TEA.get(), 250, ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get());
        FluidContainerRegistry.register(ModItems.GLASS_CUP.get(), ModFluids.DYEDREAM_JUICE.get(), 250, ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get());
        FluidContainerRegistry.register(ModItems.GLASS_CUP.get(), ModFluids.HONEY_JUICE.get(), 250, ModItems.GLASS_CUP_OF_HONEY_JUICE.get());
        FluidContainerRegistry.register(ModItems.GLASS_CUP.get(), ModFluids.WATERMELON_JUICE.get(), 250, ModItems.GLASS_CUP_OF_WATERMELON_JUICE.get());
    }
}
