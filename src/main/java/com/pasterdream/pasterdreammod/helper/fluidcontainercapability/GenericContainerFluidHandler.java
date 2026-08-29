package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class GenericContainerFluidHandler implements IFluidHandlerItem
{
    private ItemStack inputItem;
    private FluidContainerRegistry.ContainerEntry entry;
    private ItemStack outputItem = null;
    private int defaultCapacity;

    public GenericContainerFluidHandler(ItemStack itemStack)
    {
        inputItem = itemStack;
        entry = FluidContainerRegistry.getEntryForFillToEmpty(inputItem);

        if (entry == null)
        {
            FluidContainerRegistry.ContainerEntry anyEntry = FluidContainerRegistry.getAnyEntryForEmpty(inputItem.getItem());
            if (anyEntry != null)
            {
                defaultCapacity = anyEntry.fluidStack.getAmount();
            }
                else
                {
                    defaultCapacity = 1000;
                }
        }
            else
            {
                defaultCapacity = entry.fluidStack.getAmount();
            }
    }

    @Override
    public int getTanks()
    {
        return 1;
    }

    @Override
    public FluidStack getFluidInTank(int tank)
    {
        if (entry != null && inputItem.getItem() == entry.fullContainerItemStack.getItem())
        {
            return entry.fluidStack;
        }
            else
            {
                return FluidStack.EMPTY;
            }
    }

    @Override
    public int getTankCapacity(int tank)
    {
        return defaultCapacity;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack fluidStack)
    {
        if (entry != null)
        {
            return false;
        }
            else
            {   //空杯：检查是否有注册关系
                return FluidContainerRegistry.getEntryForEmptyAndFluid(inputItem, fluidStack) != null;
            }
    }

    @Override
    public int fill(FluidStack resource, FluidAction action)
    {
        if (this.entry != null || resource.isEmpty())
        {
            return 0;
        }

        //根据空物品和流体动态查找条目
        FluidContainerRegistry.ContainerEntry entry = FluidContainerRegistry.getEntryForEmptyAndFluid(inputItem, resource);
        if (entry == null || resource.getAmount() < entry.fluidStack.getAmount())
        {
            return 0;
        }

        if (action.execute())
        {   //创建满杯
            outputItem = new ItemStack(entry.fullContainerItemStack.getItem(), inputItem.getCount());
            outputItem.setTag(entry.fullContainerItemStack.getTag());
        }
        return entry.fluidStack.getAmount();
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action)
    {
        if (entry == null || inputItem.getItem() != entry.fullContainerItemStack.getItem() || resource.isEmpty() || resource.getAmount() < entry.fluidStack.getAmount() || !resource.isFluidEqual(entry.fluidStack))
        {
            return FluidStack.EMPTY;
        }
            else
            {
                return drain(entry.fluidStack.getAmount(), action);
            }
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action)
    {
        if (entry == null || inputItem.getItem() != entry.fullContainerItemStack.getItem() || maxDrain < entry.fluidStack.getAmount())
        {
            return FluidStack.EMPTY;
        }

        FluidStack drained = entry.fluidStack.copy();
        if (action.execute())
        {
            ItemStack newStack = new ItemStack(entry.emptyContainerItemStack.getItem());
            inputItem = newStack;
            outputItem = newStack;
        }
        return drained;
    }

    @Override
    public ItemStack getContainer()
    {
        if(outputItem != null)
        {
            return outputItem;
        }
            else
            {
                return inputItem;
            }
    }
}
