package com.pasterdream.pasterdreammod.world.fluid;

import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraftforge.fluids.FluidType;

public class DyedreamFlowerTeaFluid extends PasterDreamBaseFluid
{
    @Override
    public FluidType getFluidType()
    {
        return ModFluids.DYEDREAM_FLOWER_TEA_TYPE.get();
    }
}
