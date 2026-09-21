package com.pasterdream.pasterdreammod.world.item.debugtool.slot;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ActiveStatusChangeableSlot extends SlotItemHandler
{
    private boolean active = true;

    public ActiveStatusChangeableSlot(IItemHandler handler, int index, int x, int y)
    {
        super(handler, index, x, y);
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    @Override
    public boolean isActive()
    {
        return active;
    }
}
