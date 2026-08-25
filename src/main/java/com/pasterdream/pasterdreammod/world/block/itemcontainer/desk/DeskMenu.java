package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk;

import com.pasterdream.pasterdreammod.world.block.itemcontainer.ItemContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public abstract class DeskMenu<T extends DeskBlockEntity> extends ItemContainerMenu<T>
{
    public DeskMenu(MenuType<?> type, int id, Inventory playerInventory, T blockEntity)
    {
        super(type, id, playerInventory, blockEntity);
    }

    public int getPlayerInventoryX()
    {
        return 5;
    }

    public int getPlayerInventoryY()
    {
        return 65;
    }

    public void addContainerSlot(IItemHandler handler)
    {
        addSlot(new SlotItemHandler(handler, 0, 77, 5));
    }

    public int getContainerSlotCount()
    {
        return 1;
    }
}
