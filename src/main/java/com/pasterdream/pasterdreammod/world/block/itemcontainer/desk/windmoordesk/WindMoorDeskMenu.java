package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.windmoordesk;

import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.DeskMenu;
import net.minecraft.world.entity.player.Inventory;

public class WindMoorDeskMenu extends DeskMenu<WindMoorDeskBlockEntity>
{
    public WindMoorDeskMenu(int id, Inventory inventory, WindMoorDeskBlockEntity blockEntity)
    {
        super(ModMenus.WIND_MOOR_DESK.get(), id, inventory, blockEntity);
    }
}
