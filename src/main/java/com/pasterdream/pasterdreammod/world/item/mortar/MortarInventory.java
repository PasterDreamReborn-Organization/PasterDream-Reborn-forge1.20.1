package com.pasterdream.pasterdreammod.world.item.mortar;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;

public class MortarInventory implements Container, FluidContainer
{
    private final MortarDataHandler dataHandler;
    private final FluidTank[] fluidTanks;
    private final ItemStackHandler itemHandler;

    private boolean suppressSave;   //抑制初始化期间的全部写入

    public MortarInventory(MortarDataHandler dataHandler)
    {
        this.dataHandler = dataHandler;
        suppressSave = true;   //开启抑制

        fluidTanks = new FluidTank[6];
        for (int i = 0; i < fluidTanks.length; i++)
        {
            final int slotIndex = i;
            fluidTanks[i] = new FluidTank(1000)
            {
                @Override
                public void setFluid(FluidStack stack)
                {
                    super.setFluid(stack);
                    this.onContentsChanged();   //自动触发回调
                }

                @Override
                protected void onContentsChanged()
                {
                    if (!suppressSave)
                    {
                        if (slotIndex < 4)
                        {
                            dataHandler.setInputFluid(slotIndex, this.getFluid());
                        }
                            else
                            {
                                dataHandler.setOutputFluid(slotIndex - 4, this.getFluid());
                            }
                    }
                }
            };
        }

        itemHandler = new ItemStackHandler(12)
        {
            @Override
            protected void onContentsChanged(int slotIndex)
            {
                if (!suppressSave)
                {
                    if (slotIndex < 8)
                    {
                        dataHandler.setInputItem(slotIndex, getStackInSlot(slotIndex));
                    }
                        else
                        {
                            dataHandler.setOutputItem(slotIndex - 8, getStackInSlot(slotIndex));
                        }
                }
            }
        };

        loadFromDataHandler();
        suppressSave = false;
    }

    //从NBT中读取
    private void loadFromDataHandler()
    {
        for (int i = 0; i < 4; i++)
        {
            fluidTanks[i].setFluid(dataHandler.getInputFluid(i));
        }
        for (int i = 0; i < 2; i++)
        {
            fluidTanks[4 + i].setFluid(dataHandler.getOutputFluid(i));
        }
        for (int i = 0; i < 8; i++)
        {
            itemHandler.setStackInSlot(i, dataHandler.getInputItem(i));
        }
        for (int i = 0; i < 4; i++)
        {
            itemHandler.setStackInSlot(8 + i, dataHandler.getOutputItem(i));
        }
    }

    //写入NBT
    private void saveToDataHandler()
    {
        for (int i = 0; i < 4; i++)
        {
            dataHandler.setInputFluid(i, fluidTanks[i].getFluid());
        }
        for (int i = 0; i < 2; i++)
        {
            dataHandler.setOutputFluid(i, fluidTanks[4 + i].getFluid());
        }
        for (int i = 0; i < 8; i++)
        {
            dataHandler.setInputItem(i, itemHandler.getStackInSlot(i));
        }
        for (int i = 0; i < 4; i++)
        {
            dataHandler.setOutputItem(i, itemHandler.getStackInSlot(8 + i));
        }
    }

    @Override
    public int getContainerSize()
    {
        return 12;
    }

    @Override
    public int getFluidContainerSize()
    {
        return 6;
    }

    @Override
    public boolean isEmpty()
    {
        for (int i = 0; i < itemHandler.getSlots(); i++)
        {
            if (!itemHandler.getStackInSlot(i).isEmpty())
            {
                return false;
            }
        }
        for (FluidTank tank : fluidTanks)
        {
            if (!tank.getFluid().isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot)
    {
        if (slot >= 0 && slot < 12)
        {
            return itemHandler.getStackInSlot(slot);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack)
    {
        if (slot >= 0 && slot < 12)
        {
            itemHandler.setStackInSlot(slot, stack);
        }
    }

    @Override
    public ItemStack removeItem(int slot, int amount)
    {
        if (slot >= 0 && slot < 12)
        {
            return itemHandler.extractItem(slot, amount, false);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot)
    {
        ItemStack stack = getItem(slot);
        setItem(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public FluidStack getFluid(int index)
    {
        if (index >= 0 && index < 6)
        {
            return fluidTanks[index].getFluid();
        }
        return FluidStack.EMPTY;
    }

    @Override
    public void setFluid(int index, FluidStack stack)
    {
        if (index >= 0 && index < 6)
        {
            fluidTanks[index].setFluid(stack);
        }
    }

    @Override
    public void setChanged()
    {
        saveToDataHandler();
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    @Override
    public void clearContent()
    {
        for (FluidTank tank : fluidTanks)
        {
            tank.setFluid(FluidStack.EMPTY);
        }
        for (int i = 0; i < itemHandler.getSlots(); i++)
        {
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
        setChanged();
    }

    public ItemStackHandler getItemHandler()
    {
        return itemHandler;
    }

    public FluidTank[] getFluidTanks()
    {
        return fluidTanks;
    }

    @Override
    public void startOpen(Player player) {}

    @Override
    public void stopOpen(Player player) {}
}
