package com.pasterdream.pasterdreammod.world.item.debugtool.generichandler;

import net.minecraftforge.fluids.capability.IFluidHandler;

public record FluidHandlerLaunchData(IFluidHandler handler, Runnable onChanged){}
