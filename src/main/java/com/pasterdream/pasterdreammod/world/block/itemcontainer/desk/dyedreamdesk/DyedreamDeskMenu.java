package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.dyedreamdesk;

import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.DeskMenu;
import net.minecraft.world.entity.player.Inventory;

public class DyedreamDeskMenu extends DeskMenu<DyedreamDeskBlockEntity>
{
    public DyedreamDeskMenu(int id, Inventory inventory, DyedreamDeskBlockEntity blockEntity)
    {
        super(ModMenus.DYEDREAM_DESK.get(), id, inventory, blockEntity);
    }
}
