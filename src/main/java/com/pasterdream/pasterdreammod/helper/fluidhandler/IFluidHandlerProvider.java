package com.pasterdream.pasterdreammod.helper.fluidhandler;

import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IFluidHandlerProvider
{
    IFluidHandler getFluidHandler(int tankIndex);
}
