package com.pasterdream.pasterdreammod.world.fluid;

import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraftforge.fluids.FluidType;

public class WindPlantExtractFluid extends PasterDreamBaseFluid
{
    @Override
    public FluidType getFluidType()
    {
        return ModFluids.WIND_PLANT_EXTRACT_TYPE.get();
    }
}
