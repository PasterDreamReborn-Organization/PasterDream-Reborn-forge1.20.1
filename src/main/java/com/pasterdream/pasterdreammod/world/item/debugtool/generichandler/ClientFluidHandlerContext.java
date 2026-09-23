package com.pasterdream.pasterdreammod.world.item.debugtool.generichandler;

import net.minecraftforge.fluids.capability.IFluidHandler;

public class ClientFluidHandlerContext
{
    private static IFluidHandler pending;
    private ClientFluidHandlerContext()
    {

    }

    public static void set(IFluidHandler fluidHandler)
    {
        pending = fluidHandler;
    }

    public static IFluidHandler consume()
    {
        IFluidHandler fluidHandler = pending;
        pending = null;
        return fluidHandler;
    }
}
