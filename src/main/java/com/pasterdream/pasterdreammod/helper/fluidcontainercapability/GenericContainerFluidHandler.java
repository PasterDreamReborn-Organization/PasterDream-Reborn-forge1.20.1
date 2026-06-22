package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class GenericContainerFluidHandler implements IFluidHandlerItem
{
    private ItemStack inputItem;
    private Item item;
    private FluidContainerRegistry.ContainerEntry entry;
    private ItemStack outputItem = null;
    private int defaultCapacity;

    public GenericContainerFluidHandler(ItemStack input)
    {
        inputItem = input;
        item = input.getItem();
        entry = FluidContainerRegistry.getEntryForFillToEmpty(item);

        if (entry == null)
        {
            FluidContainerRegistry.ContainerEntry anyEntry = FluidContainerRegistry.getAnyEntryForEmpty(item);
            if (anyEntry != null)
            {
                defaultCapacity = anyEntry.amount;
            }
            else
            {
                defaultCapacity = 1000;
            }
        }
        else
        {
            defaultCapacity = entry.amount;
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
        if (entry != null && inputItem.getItem() == entry.filledItem)
        {
            return new FluidStack(entry.fluid, entry.amount);
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
    public boolean isFluidValid(int tank, FluidStack stack)
    {
        if (entry != null)
        {
            return false;
        }
        else
        {   //空杯：检查是否有注册关系
            return FluidContainerRegistry.getEntryForEmptyAndFluid(item, stack.getFluid()) != null;
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
        FluidContainerRegistry.ContainerEntry entry = FluidContainerRegistry.getEntryForEmptyAndFluid(item, resource.getFluid());
        if (entry == null || resource.getAmount() < entry.amount)
        {
            return 0;
        }

        if (action.execute())
        {   //创建满杯
            outputItem = new ItemStack(entry.filledItem, inputItem.getCount());
        }
        return entry.amount;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action)
    {
        if (entry == null || inputItem.getItem() != entry.filledItem || resource.isEmpty() || resource.getAmount() < entry.amount || !resource.isFluidEqual(new FluidStack(entry.fluid, entry.amount)))
        {
            return FluidStack.EMPTY;
        }
        else
        {
            return drain(entry.amount, action);
        }
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action)
    {
        if (entry == null || inputItem.getItem() != entry.filledItem || maxDrain < entry.amount)
        {
            return FluidStack.EMPTY;
        }

        FluidStack drained = new FluidStack(entry.fluid, entry.amount);
        if (action.execute())
        {
            ItemStack newStack = new ItemStack(entry.emptyItem);
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
