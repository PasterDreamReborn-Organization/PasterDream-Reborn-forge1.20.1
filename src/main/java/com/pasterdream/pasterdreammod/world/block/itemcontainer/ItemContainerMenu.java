package com.pasterdream.pasterdreammod.world.block.itemcontainer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public abstract class ItemContainerMenu<T extends ItemContainerBlockEntity> extends AbstractContainerMenu
{
    protected final T blockEntity;
    protected final Inventory playerInventory;

    public ItemContainerMenu(MenuType<?> type, int id, Inventory playerInventory, T blockEntity)
    {
        super(type, id);
        this.blockEntity = blockEntity;
        this.playerInventory = playerInventory;

        IItemHandler handler = blockEntity.getItemHandler();
        addContainerSlot(handler);
        addPlayerInventory(getPlayerInventoryX(), getPlayerInventoryY());
    }

    public abstract int getPlayerInventoryX();
    public abstract int getPlayerInventoryY();
    public abstract int getContainerSlotCount();
    public abstract void addContainerSlot(IItemHandler handler);

    public void addPlayerInventory(int x, int y)
    {
        for (int i = 0; i < 9; i++)
        {
            this.addSlot(new Slot(playerInventory, i, x + i * 18, y + 58));
        }

        for (int i = 0; i < 27; i++)
        {
            this.addSlot(new Slot(playerInventory, i + 9, x + (i % 9) * 18, y + (i / 9) * 18));
        }
    }

    public T getBlockEntity()
    {
        return blockEntity;
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

        int containerSlotCount = getContainerSlotCount();

        if (index >= 0 && index < containerSlotCount)
        {
            if (!this.moveItemStackTo(stack, containerSlotCount, containerSlotCount + 36, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else
            if (index >= containerSlotCount && index < containerSlotCount + 36)
            {
                if (!this.moveItemStackTo(stack, 0, containerSlotCount, false))
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

    @Override
    public boolean stillValid(Player player)
    {
        BlockPos blockPosition = blockEntity.getBlockPos();
        return player.level().getBlockEntity(blockPosition) == blockEntity && player.distanceToSqr(blockPosition.getX() + 0.5, blockPosition.getY() + 0.5, blockPosition.getZ() + 0.5) <= 64;
    }
}
