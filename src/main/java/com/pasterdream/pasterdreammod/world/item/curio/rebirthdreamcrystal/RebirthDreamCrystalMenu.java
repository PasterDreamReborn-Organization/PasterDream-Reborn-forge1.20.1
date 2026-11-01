package com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal;

import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.playerdata.PasterDreamPlayerDreamNoteProgressData;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class RebirthDreamCrystalMenu extends AbstractContainerMenu
{
    private final Container menuContainer;
    private final Player player;

    public RebirthDreamCrystalMenu(int id, Inventory playerInventory)
    {
        super(ModMenus.REBIRTH_DREAM_CRYSTAL.get(), id);

        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(playerInventory, col, 60 + col * 18, 243));
        }

        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 60 + col * 18, 185 + row * 18));
            }
        }

        this.menuContainer = new SimpleContainer(1);
        player = playerInventory.player;
        if (!player.level().isClientSide)
        {
            ItemStack stored = PasterDreamPlayerDreamNoteProgressData.getItem(player);
            if (!stored.isEmpty())
            {
                menuContainer.setItem(0, stored.copy());
            }
        }

        addSlot(new Slot(menuContainer, 0, 160, 20)
        {
            @Override
            public void set(ItemStack itemStack)
            {
                super.set(itemStack);
                PasterDreamPlayerDreamNoteProgressData.setItem(player, itemStack);
            }
        });
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        Slot slot = slots.get(index);
        if (!slot.hasItem())
        {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if ((index == 36))
        {   //从机器移出到背包
            if (!this.moveItemStackTo(stack, 0, 36, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else
            if((index >= 0 && index <= 35))
            {   //从背包移入输入槽
                if (!(this.moveItemStackTo(stack, 36, 37, false)))
                {
                    return ItemStack.EMPTY;
                }
            }

        if (stack.isEmpty())
        {
            slot.set(ItemStack.EMPTY);
        }
        else
        {
            slot.setChanged();
        }
        return copy;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }
}
