package com.pasterdream.pasterdreammod.world.fluid;

import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraftforge.fluids.FluidType;

public class GoldenrodTeaFluid extends PasterDreamBaseFluid
{
    @Override
    public FluidType getFluidType()
    {
        return ModFluids.GOLDENROD_TEA_TYPE.get();
    }
}
