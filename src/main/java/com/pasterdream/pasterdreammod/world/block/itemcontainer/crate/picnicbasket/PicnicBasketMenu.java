package com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.picnicbasket;

import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.CrateMenu;
import net.minecraft.world.entity.player.Inventory;

public class PicnicBasketMenu extends CrateMenu<PicnicBasketBlockEntity>
{
    public PicnicBasketMenu(int id, Inventory inventory, PicnicBasketBlockEntity blockEntity)
    {
        super(ModMenus.PICNIC_BASKET.get(), id, inventory, blockEntity);
    }
}
