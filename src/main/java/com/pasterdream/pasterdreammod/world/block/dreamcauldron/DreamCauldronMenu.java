package com.pasterdream.pasterdreammod.world.block.dreamcauldron;

import com.pasterdream.pasterdreammod.init.ModMenus;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class DreamCauldronMenu extends AbstractContainerMenu
{
    private final DreamCauldronBlockEntity blockEntity;

    public DreamCauldronMenu(int id, Inventory inventory, DreamCauldronBlockEntity blockEntity, ServerPlayer serverPlayer)
    {
        super(ModMenus.DREAM_CAULDRON.get(), id);
        this.blockEntity = blockEntity;
        IItemHandler handler = blockEntity.getItemHandler();

        for (int i = 0; i < 3; i++)
        {
            addSlot(new SlotItemHandler(handler, i, 54 + i * 28, 15));
        }

        addSlot(new SlotItemHandler(handler, 3, 82, 46)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return false;
            }
        });

        //玩家物品栏
        for (int i = 0; i < 9; i++)
        {
            addSlot(new Slot(inventory, i, 10 + i * 18, 168));
        }

        for (int i = 0; i < 27; i++)
        {
            addSlot(new Slot(inventory, i + 9, 10 + (i % 9) * 18, 110 + (i / 9) * 18));
        }
    }

    public DreamCauldronMenu(int id, Inventory inventory, DreamCauldronBlockEntity blockEntity)
    {
        this(id, inventory, blockEntity, null);
    }

    public DreamCauldronBlockEntity getBlockEntity()
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

        //输出槽移出到背包
        if (index == 3)
        {
            if (!this.moveItemStackTo(stack, 4, 40, true))
            {
                return ItemStack.EMPTY;
            }
        }
        else    //输入槽移出到背包
            if (index >= 0 && index < 3)
            {
                if (!this.moveItemStackTo(stack, 4, 40, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else    //背包移入输入槽
                if (index >= 4 && index < 40)
                {
                    if (!this.moveItemStackTo(stack, 0, 3, false))
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
