package com.pasterdream.pasterdreammod.world.fluid;

import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraftforge.fluids.FluidType;

public class DyeDreamJuiceFluid extends PasterDreamBaseFluid
{
    @Override
    public FluidType getFluidType()
    {
        return ModFluids.DYEDREAM_JUICE_TYPE.get();
    }
}
