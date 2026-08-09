package com.pasterdream.pasterdreammod.world.block.researchtable;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerMenuWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidContainer;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.IFluidContainer;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidSlot;
import com.pasterdream.pasterdreammod.init.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ResearchTableMenu extends AbstractContainerMenuWithFluidSlot
{
    private final ResearchTableBlockEntity blockEntity;
    private final IFluidContainer fluidContainer;

    public ResearchTableMenu(int id, Inventory inventory, ResearchTableBlockEntity blockEntity)
    {
        super(ModMenus.RESEARCH_TABLE.get(), id);
        this.blockEntity = blockEntity;
        fluidContainer = new FluidContainer.GenericFluidContainer(blockEntity.getFluidTanks());
        IItemHandler handler = blockEntity.getItemHandler();

        addFluidSlot(new FluidSlot(fluidContainer, 0, 169, 16));

        addSlot(new SlotItemHandler(handler, 0, 170, 80));
        addSlot(new SlotItemHandler(handler, 1, 35, 26));
        addSlot(new SlotItemHandler(handler, 2, 35, 44));
        addSlot(new SlotItemHandler(handler, 3, 134, 35)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return false;
            }
        });

        addSlot(new SlotItemHandler(handler, 4, 35, 80));
        addSlot(new SlotItemHandler(handler, 5, 134, 80)
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
            addSlot(new Slot(inventory, i, 19 + i * 18, 180));
        }

        for (int i = 0; i < 27; i++)
        {
            addSlot(new Slot(inventory, i + 9, 19 + (i % 9) * 18, 122 + (i / 9) * 18));
        }

        reBuildLastFluids();
    }

    public ResearchTableBlockEntity getBlockEntity()
    {
        return blockEntity;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return player.level().getBlockEntity(blockEntity.getBlockPos()) == blockEntity && player.distanceToSqr(blockEntity.getBlockPos().getX() + 0.5, blockEntity.getBlockPos().getY() + 0.5, blockEntity.getBlockPos().getZ() + 0.5) <= 64;
    }

    @Override
    public IFluidContainer getFluidContainer()
    {
        return fluidContainer;
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
        if (index >= 1 && index <= 6)
        {
            if (!this.moveItemStackTo(stack, 7, 43, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else    //背包移入输入槽
            if (index >= 7 && index <= 42)
            {
                if (!this.moveItemStackTo(stack, 1, 4, false) || !this.moveItemStackTo(stack, 5, 6, false))
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
