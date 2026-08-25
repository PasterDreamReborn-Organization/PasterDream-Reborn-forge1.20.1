package com.pasterdream.pasterdreammod.world.block.itemcontainer.openedmeltdreamcrystalchest;

import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.ItemContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class OpenedMeltDreamCrystalChestMenu extends ItemContainerMenu<OpenedMeltDreamCrystalChestBlockEntity>
{
    public OpenedMeltDreamCrystalChestMenu(int id, Inventory inventory, OpenedMeltDreamCrystalChestBlockEntity blockEntity)
    {
        super(ModMenus.OPENED_MELT_DREAM_CRYSTAL_CHEST.get(), id, inventory, blockEntity);
    }

    public int getPlayerInventoryX()
    {
        return 5;
    }

    public int getPlayerInventoryY()
    {
        return 91;
    }

    public void addContainerSlot(IItemHandler handler)
    {
        for(int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                addSlot(new SlotItemHandler(handler, i * 3 + j, 58 + j * 18, 7 + i * 18));
            }
        }
    }

    public int getContainerSlotCount()
    {
        return 9;
    }
}
