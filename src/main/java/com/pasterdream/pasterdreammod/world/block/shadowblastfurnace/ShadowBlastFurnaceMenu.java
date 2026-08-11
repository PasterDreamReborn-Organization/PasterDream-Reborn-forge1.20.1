package com.pasterdream.pasterdreammod.world.block.shadowblastfurnace;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerMenuWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidContainer;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.IFluidContainer;
import com.pasterdream.pasterdreammod.init.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ShadowBlastFurnaceMenu extends AbstractContainerMenuWithFluidSlot
{
    private final ShadowBlastFurnaceBlockEntity blockEntity;
    private final ContainerData data;
    private final IFluidContainer fluidContainer;

    public ShadowBlastFurnaceMenu(int id, Inventory inventory, ShadowBlastFurnaceBlockEntity blockEntity)
    {
        super(ModMenus.SHADOW_BLAST_FURNACE.get(), id);
        this.blockEntity = blockEntity;
        fluidContainer = new FluidContainer.GenericFluidContainer(blockEntity.getFluidTanks());
        IItemHandler handler = blockEntity.getItemHandler();

        data = new SimpleContainerData(2);
        data.set(0, blockEntity.getProgress());
        data.set(1, blockEntity.getMaxProgress());
        addDataSlots(data);

        addFluidSlot(new FluidSlot(fluidContainer, 0, 130, 4));

        addSlot(new SlotItemHandler(handler, 0, 23, 5));
        addSlot(new SlotItemHandler(handler, 1, 23, 50));
        addSlot(new SlotItemHandler(handler, 2, 59, 86)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return false;
            }
        });
        addSlot(new SlotItemHandler(handler, 3, 95, 86)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return false;
            }
        });

        for (int i = 0; i < 9; i++)
        {
            addSlot(new Slot(inventory, i, 5 + i * 18, 175));
        }

        for (int i = 0; i < 27; i++)
        {
            addSlot(new Slot(inventory, i + 9, 5 + (i % 9) * 18, 117 + (i / 9) * 18));
        }

        reBuildLastFluids();
    }


    public ShadowBlastFurnaceBlockEntity getBlockEntity()
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
        if (index >= 1 && index <= 4)
        {
            if (!this.moveItemStackTo(stack, 5, 41, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else    //背包移入输入槽
            if (index >= 5 && index <= 40)
            {
                if (!this.moveItemStackTo(stack, 1, 3, false))
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
    public void broadcastChanges()
    {
        super.broadcastChanges();
        if (!blockEntity.getLevel().isClientSide)
        {
            this.data.set(0, blockEntity.getProgress());
            this.data.set(1, blockEntity.getMaxProgress());
        }
    }

    public int getProgress()
    {
        return data.get(0);
    }

    public int getMaxProgress()
    {
        return data.get(1);
    }
}
