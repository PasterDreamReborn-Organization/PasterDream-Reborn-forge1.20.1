package com.pasterdream.pasterdreammod.helper.drinkandfoodproperties;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class FluidDrinkPropertiesRegistry
{
    private static final Map<ResourceLocation, GenericFluidDrinkProperties> PROPERTIES = new HashMap<>();

    public static void register(Fluid fluid, GenericFluidDrinkProperties properties)
    {
        ResourceLocation id = ForgeRegistries.FLUIDS.getKey(fluid);
        if (id != null)
        {
            PROPERTIES.put(id, properties);
        }
    }

    public static GenericFluidDrinkProperties getProperties(Fluid fluid)
    {
        ResourceLocation id = ForgeRegistries.FLUIDS.getKey(fluid);
        return id != null ? PROPERTIES.get(id) : null;
    }
}
