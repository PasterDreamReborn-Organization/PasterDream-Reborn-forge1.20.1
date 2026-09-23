package com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;

public class FluidSlot
{
    public final int fluidSlotIndex;
    private final IFluidContainer container;
    public int x;
    public int y;
    private boolean active = true;
    private Runnable onChanged;

    public FluidSlot(IFluidContainer container, int fluidSlotIndex, int x, int y)
    {
        this(container, fluidSlotIndex, x, y, null);
    }

    public FluidSlot(IFluidContainer container, int fluidSlotIndex, int x, int y, Runnable onChanged)
    {
        this.container = container;
        this.fluidSlotIndex = fluidSlotIndex;
        this.x = x;
        this.y = y;
        this.onChanged = onChanged;
    }

    public FluidStack getFluid()
    {
        return container.getFluid(fluidSlotIndex);
    }

    public void setFluid(FluidStack fluidStack)
    {
        container.setFluid(fluidSlotIndex, fluidStack);
        container.setChanged();
        if(onChanged != null)
        {
            onChanged.run();
        }
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

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public boolean isActive()
    {
        return active;
    }
}
