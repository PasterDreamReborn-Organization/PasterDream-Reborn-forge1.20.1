package com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.shadowchest;

import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.CrateMenu;
import net.minecraft.world.entity.player.Inventory;

public class ShadowChestMenu extends CrateMenu<ShadowChestBlockEntity>
{
    public ShadowChestMenu(int id, Inventory inventory, ShadowChestBlockEntity blockEntity)
    {
        super(ModMenus.SHADOW_CHEST.get(), id, inventory, blockEntity);
    }
}
