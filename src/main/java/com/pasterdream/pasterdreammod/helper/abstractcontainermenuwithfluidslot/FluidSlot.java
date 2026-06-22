package com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;

public class FluidSlot
{
    public final int fluidSlotIndex;
    private final FluidContainer container;
    public final int x;
    public final int y;

    public FluidSlot(FluidContainer container, int fluidSlotIndex, int x, int y)
    {
        this.container = container;
        this.fluidSlotIndex = fluidSlotIndex;
        this.x = x;
        this.y = y;
    }

    public FluidStack getFluid()
    {
        return container.getFluid(fluidSlotIndex);
    }

    public void setFluid(FluidStack fluidStack)
    {
        container.setFluid(fluidSlotIndex, fluidStack);
    }

    public int getMaxCapacity()
    {
        return container.getMaxFluidCapacity(fluidSlotIndex);
    }

    public void setChanged()
    {
        container.setChanged();
    }

    public boolean mayPlace(FluidStack stack)
    {
        return container.canPlaceFluid(fluidSlotIndex, stack);
    }

    public boolean mayPickup(Player player)
    {
        return container.canTakeFluid(fluidSlotIndex, player);
    }
}
