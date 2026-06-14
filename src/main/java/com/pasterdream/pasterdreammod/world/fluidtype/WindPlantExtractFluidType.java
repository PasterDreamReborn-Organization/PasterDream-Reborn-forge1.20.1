package com.pasterdream.pasterdreammod.world.fluidtype;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;

public class WindPlantExtractFluidType extends PasterDreamBaseFluidType
{
    public static final FluidType TYPE = new WindPlantExtractFluidType();

    private WindPlantExtractFluidType()
    {
        super(Properties.create().density(1000).viscosity(1000).temperature(300).canExtinguish(true).canSwim(true).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY), ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_still"), ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_flow"), 0x7F0E9E78);
    }

    @Override
    public String getDescriptionId()
    {
        return "fluid." + PasterDreamMod.MOD_ID + ".wind_plant_extract";
    }
}
