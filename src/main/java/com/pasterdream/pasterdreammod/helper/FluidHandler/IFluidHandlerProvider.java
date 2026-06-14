package com.pasterdream.pasterdreammod.helper.FluidHandler;

import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IFluidHandlerProvider
{
    IFluidHandler getFluidHandler(int tankIndex);
}
