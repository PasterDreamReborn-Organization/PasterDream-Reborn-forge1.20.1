package com.pasterdream.pasterdreammod.world.block.itemcontainer.crate;

import com.pasterdream.pasterdreammod.world.block.itemcontainer.ItemContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public abstract class CrateMenu<T extends CrateBlockEntity> extends ItemContainerMenu<T>
{
    public CrateMenu(MenuType<?> type, int id, Inventory playerInventory, T blockEntity)
    {
        super(type, id, playerInventory, blockEntity);
    }

    public int getPlayerInventoryX()
    {
        return 5;
    }

    public int getPlayerInventoryY()
    {
        return 111;
    }

    public void addContainerSlot(IItemHandler handler)
    {
        for(int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                addSlot(new SlotItemHandler(handler, i * 5 + j, 40 + j * 18, 20 + i * 18));
            }
        }
    }

    public int getContainerSlotCount()
    {
        return 15;
    }
}
