package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.init.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class ShadowSelectEndMenu extends AbstractContainerMenu {

    public ShadowSelectEndMenu(int id, Inventory inventory) {
        super(ModMenus.SHADOW_SELECT_END.get(), id);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
