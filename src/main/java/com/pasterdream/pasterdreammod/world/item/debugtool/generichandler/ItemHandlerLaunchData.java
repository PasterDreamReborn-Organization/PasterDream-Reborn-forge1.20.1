package com.pasterdream.pasterdreammod.world.item.debugtool.generichandler;

import net.minecraftforge.items.IItemHandler;

public record ItemHandlerLaunchData(IItemHandler handler, Runnable onChanged){}
