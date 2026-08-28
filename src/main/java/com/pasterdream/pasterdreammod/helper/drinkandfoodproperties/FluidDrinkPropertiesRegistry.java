package com.pasterdream.pasterdreammod.helper.drinkandfoodproperties;

import com.pasterdream.pasterdreammod.helper.potionhelper.GenericMobEffect;
import com.pasterdream.pasterdreammod.helper.potionhelper.PotionHelper;
import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
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

    public static GenericFluidDrinkProperties getProperties(FluidStack fluidStack)
    {
        ResourceLocation id = ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid());
        if(id != null)
        {
            GenericFluidDrinkProperties properties = PROPERTIES.get(id);

            if(fluidStack.getFluid().equals(ModFluids.POTION.get()))
            {
                GenericFluidDrinkProperties copy = GenericFluidDrinkProperties.copy(properties);

                List<GenericMobEffect> effectList = PotionHelper.getEffectType(fluidStack);

                for (GenericMobEffect effect : effectList)
                {
                    copy.effect(new MobEffectInstance(effect.effectType(), effect.time(), effect.level()));
                }
                return copy;
            }
                else
                {
                    return properties;
                }
        }
            else
            {
                return null;
            }
    }
}
