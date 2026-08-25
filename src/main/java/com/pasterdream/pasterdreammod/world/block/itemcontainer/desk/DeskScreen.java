package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk;

import com.pasterdream.pasterdreammod.world.block.itemcontainer.ItemContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public abstract class DeskScreen<T extends DeskMenu<?>> extends ItemContainerScreen<T>
{
    public DeskScreen(T menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

    public int setScreenWidth()
    {
        return 170;
    }

    public int setScreenHeight()
    {
        return 145;
    }

    public int setInventoryLeftPosition()
    {
        return leftPos;
    }

    public int setInventoryTopPosition()
    {
        return topPos + 60;
    }
}
