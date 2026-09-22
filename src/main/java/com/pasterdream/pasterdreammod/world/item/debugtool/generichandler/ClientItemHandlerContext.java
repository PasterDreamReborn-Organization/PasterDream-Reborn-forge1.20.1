package com.pasterdream.pasterdreammod.world.item.debugtool.generichandler;

import net.minecraftforge.items.IItemHandler;

public class ClientItemHandlerContext
{
    private static IItemHandler pending;
    private ClientItemHandlerContext()
    {

    }

    public static void set(IItemHandler itemHandler)
    {
        pending = itemHandler;
    }

    public static IItemHandler consume()
    {
        IItemHandler itemHandler = pending;
        pending = null;
        return itemHandler;
    }
}
