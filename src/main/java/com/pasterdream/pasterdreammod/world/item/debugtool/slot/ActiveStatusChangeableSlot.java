package com.pasterdream.pasterdreammod.world.item.debugtool.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

public class ActiveStatusChangeableSlot extends SlotItemHandler
{
    private boolean active = true;

    public ActiveStatusChangeableSlot(IItemHandler handler, int index, int x, int y)
    {
        super(handler, index, x, y);
    }

    @Override
    public void set(ItemStack itemStack)
    {
        int slotIndex = getSlotIndex();

        IItemHandler handler = getItemHandler();
        if (handler instanceof IItemHandlerModifiable modifiable)
        {
            modifiable.setStackInSlot(slotIndex, itemStack);
        }
            else
            {
                handler.extractItem(slotIndex, 2147483647, false);
                if (!itemStack.isEmpty())
                {
                    handler.insertItem(slotIndex, itemStack, false);
                }
            }
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
