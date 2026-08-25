package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.shadowdesk;

import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.DeskMenu;
import net.minecraft.world.entity.player.Inventory;

public class ShadowDeskMenu extends DeskMenu<ShadowDeskBlockEntity>
{
    public ShadowDeskMenu(int id, Inventory inventory, ShadowDeskBlockEntity blockEntity)
    {
        super(ModMenus.SHADOW_DESK.get(), id, inventory, blockEntity);
    }
}
