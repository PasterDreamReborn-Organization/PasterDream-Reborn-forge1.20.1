package com.pasterdream.pasterdreammod.helper.drinkandfoodproperties;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FluidDrinkPropertiesRegistry
{
    private static final Map<ResourceLocation, GenericFluidDrinkProperties> PROPERTIES = new ConcurrentHashMap<>();

    public static void register(Fluid fluid, GenericFluidDrinkProperties properties)
    {
        ResourceLocation id = ForgeRegistries.FLUIDS.getKey(fluid);
        if (id != null)
        {
            PROPERTIES.put(id, properties);
        }
    }

    public static GenericFluidDrinkProperties getProperties(FluidStack fluidStack)
    {
        ResourceLocation id = ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid());
        if(id != null)
        {
            return PROPERTIES.get(id);
        }
            else
            {
                return null;
            }
    }
}
