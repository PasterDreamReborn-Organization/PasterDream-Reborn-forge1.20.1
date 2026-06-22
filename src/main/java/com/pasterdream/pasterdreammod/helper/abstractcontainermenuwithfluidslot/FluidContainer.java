package com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;

public interface FluidContainer
{
    int getFluidContainerSize();
    FluidStack getFluid(int index);
    void setFluid(int index, FluidStack stack);
    default int getMaxFluidCapacity(int index)
    {
        return 1000;
    }
    void setChanged();
    boolean stillValid(Player player);
    default void startOpen(Player player) {}
    default void stopOpen(Player player) {}
    default boolean canPlaceFluid(int index, FluidStack stack)
    {
        return true;
    }
    default boolean canTakeFluid(int index, Player player)
    {
        return true;
    }
}
