package com.pasterdream.pasterdreammod.world.item.debugtool.generichandler;

import net.minecraft.world.MenuProvider;

import javax.annotation.Nullable;

public interface IDebugItemHandlerAndFluidHandlerAndEnergyStorageEditorMenu
{
    MenuProvider asMenuProvider();

    @Nullable
    ItemHandlerLaunchData provideItemHandlerLaunch();

    @Nullable
    FluidHandlerLaunchData provideFluidHandlerLaunch();

    @Nullable
    EnergyStorageLaunchData provideEnergyStorageTarget();

    @Nullable
    EnergyStorageLaunchData provideEnergyStorageExternal();
}
