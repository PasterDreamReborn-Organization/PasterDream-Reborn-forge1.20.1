package com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable;

import com.pasterdream.pasterdreammod.init.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class WeaponWorkshopCraftingTableMenu extends AbstractContainerMenu
{
    private final WeaponWorkshopCraftingTableBlockEntity blockEntity;

    public WeaponWorkshopCraftingTableMenu(int id, Inventory inventory, WeaponWorkshopCraftingTableBlockEntity blockEntity)
    {
        super(ModMenus.WEAPON_WORKSHOP_CRAFTING_TABLE.get(), id);
        this.blockEntity = blockEntity;
        IItemHandler handler = blockEntity.getItemHandler();

        for(int i = 0; i < 5; i++)
        {
            addSlot(new SlotItemHandler(handler, i, 6 + 18 * i, 8));
        }
        addSlot(new SlotItemHandler(handler, 5, 132, 8));
        addSlot(new SlotItemHandler(handler, 6, 132, 53)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return false;
            }
        });

        for (int i = 0; i < 9; i++)
        {
            addSlot(new Slot(inventory, i, 7 + i * 18, 152));
        }

        for (int i = 0; i < 27; i++)
        {
            addSlot(new Slot(inventory, i + 9, 7 + (i % 9) * 18, 94 + (i / 9) * 18));
        }
    }

    public WeaponWorkshopCraftingTableBlockEntity getBlockEntity()
    {
        return blockEntity;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return player.level().getBlockEntity(blockEntity.getBlockPos()) == blockEntity && player.distanceToSqr(blockEntity.getBlockPos().getX() + 0.5, blockEntity.getBlockPos().getY() + 0.5, blockEntity.getBlockPos().getZ() + 0.5) <= 64;
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

        //移出到背包
        if (index >= 0 && index <= 6)
        {
            if (!this.moveItemStackTo(stack, 7, 43, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else    //背包移入输入槽
            if (index >= 7 && index <= 42)
            {
                if (!this.moveItemStackTo(stack, 0, 6, false))
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

        if (stack.getCount() == copy.getCount())
        {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, stack);
        return copy;
    }
}
