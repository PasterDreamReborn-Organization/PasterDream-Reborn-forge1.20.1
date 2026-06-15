package com.pasterdream.pasterdreammod.compat.jei;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModFluids;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.registration.IExtraIngredientRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class ModJEIPlugin implements IModPlugin
{
    @Override
    public ResourceLocation getPluginUid()
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "jei_plugin");
    }

    //将流体添加至JEI物品列表
    @Override
    public void registerExtraIngredients(IExtraIngredientRegistration registration)
    {
        List<FluidStack> fluidStacks = new ArrayList<>();

        fluidStacks.add(new FluidStack(ModFluids.FLOWING_MELT_DREAM_LIQUID.get(), 1000));
        fluidStacks.add(new FluidStack(ModFluids.FLOWING_SHADOW_LIQUID.get(), 1000));

        fluidStacks.add(new FluidStack(ModFluids.APPLE_JUICE.get(), 1000));
        fluidStacks.add(new FluidStack(ModFluids.DREAM_JUICE.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.DYEDREAM_FLOWER_TEA.get(), 1000));
        fluidStacks.add(new FluidStack(ModFluids.DYEDREAM_JUICE.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.DYEDREAM_PERFUME.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.GOLDENROD_TEA.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.GUIDING_DRUG.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.HONEY_JUICE.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.RAGE_ELIXIR.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.UNCOOKED_DYEDREAM_FLOWER_TEA.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.WATERMELON_JUICE.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.WIND_PLANT_EXTRACT.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.YEAST.get(),1000));

        registration.addExtraIngredients(ForgeTypes.FLUID_STACK, fluidStacks);
    }
}
