package com.pasterdream.pasterdreammod.world.block.claypan;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerMenuWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidContainer;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidSlot;
import com.pasterdream.pasterdreammod.init.ModMenus;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.SlotItemHandler;

public class ClaypanMenu extends AbstractContainerMenuWithFluidSlot
{
    private final ClaypanBlockEntity blockEntity;
    private final ContainerData data;
    private final FluidContainer fluidContainer;

    public ClaypanMenu(int id, Inventory inventory, ClaypanBlockEntity blockEntity)
    {
        super(ModMenus.CLAYPAN.get(), id);
        this.blockEntity = blockEntity;
        this.fluidContainer = new ClaypanFluidContainer(blockEntity.getFluidTank());

        data = new SimpleContainerData(2);
        data.set(0, blockEntity.getProgress());
        data.set(1, blockEntity.getMaxProgress());
        addDataSlots(data);

        addFluidSlot(new FluidSlot(fluidContainer, 0, 49, 5));
        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 0, 104, 6)
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
            addSlot(new Slot(inventory, i, 5 + i * 18, 100));
        }

        for (int i = 0; i < 27; i++)
        {
            addSlot(new Slot(inventory, i + 9, 5 + (i % 9) * 18, 42 + (i / 9) * 18));
        }

        addDataSlots(data);
        reBuildLastFluids();
    }

    private static class ClaypanFluidContainer implements FluidContainer
    {
        private final FluidTank tank;

        public ClaypanFluidContainer(FluidTank tank)
        {
            this.tank = tank;
        }

        @Override
        public int getFluidContainerSize()
        {
            return 1;
        }

        @Override
        public FluidStack getFluid(int index)
        {
            return index == 0 ? tank.getFluid() : FluidStack.EMPTY;
        }

        @Override
        public void setFluid(int index, FluidStack stack)
        {
            if (index == 0)
            {
                tank.setFluid(stack);
            }
        }

        @Override
        public int getMaxFluidCapacity(int index)
        {
            return tank.getCapacity();
        }

        @Override
        public void setChanged()
        {

        }

        @Override
        public boolean stillValid(Player player)
        {
            return true;
        }

        @Override
        public boolean canPlaceFluid(int index, FluidStack stack)
        {
            return true;
        }

        @Override
        public boolean canTakeFluid(int index, Player player)
        {
            return true;
        }
    }

    @Override
    public FluidContainer getFluidContainer()
    {
        return fluidContainer;
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
        if (index == 1)
        {
            if (!this.moveItemStackTo(stack, 2, 38, true))
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

    public ClaypanBlockEntity getBlockEntity()
    {
        return blockEntity;
    }
}
