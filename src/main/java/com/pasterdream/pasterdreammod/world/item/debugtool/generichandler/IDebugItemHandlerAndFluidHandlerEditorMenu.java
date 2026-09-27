package com.pasterdream.pasterdreammod.world.item.debugtool.generichandler;

import net.minecraft.world.MenuProvider;

import javax.annotation.Nullable;

public interface IDebugItemHandlerAndFluidHandlerEditorMenu
{
    MenuProvider asMenuProvider();

    @Nullable
    ItemHandlerLaunchData provideItemHandlerLaunch();

    @Nullable
    FluidHandlerLaunchData provideFluidHandlerLaunch();
}
