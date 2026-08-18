package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.ElixirBottleOfPotionItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;

/**
 * 空灵药瓶的流体容器能力：既可注入通用「药水」流体（把流体 NBT 中的 "Potion" 键拷到成品灵药瓶，
 * 实现双向填装），也可按 {@link FluidContainerRegistry} 注入狂暴/融梦等已注册流体。
 */
public class ElixirBottleFluidHandler implements IFluidHandlerItem
{
    private static final int CAPACITY = ElixirBottleOfPotionItem.CAPACITY;

    private ItemStack container;
    private final GenericContainerFluidHandler registryDelegate;

    public ElixirBottleFluidHandler(ItemStack container)
    {
        this.container = container;
        this.registryDelegate = new GenericContainerFluidHandler(container);
    }

    @Override
    public int getTanks()
    {
        return 1;
    }

    @Override
    public FluidStack getFluidInTank(int tank)
    {
        return FluidStack.EMPTY;
    }

    @Override
    public int getTankCapacity(int tank)
    {
        return CAPACITY;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack)
    {
        if (stack.getFluid() == ModFluids.POTION.get())
        {
            return true;
        }
        return registryDelegate.isFluidValid(tank, stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action)
    {
        if (resource.isEmpty())
        {
            return 0;
        }
        if (resource.getFluid() == ModFluids.POTION.get())
        {
            // 每 250mB = 1 次可饮用次数，向下取整到整次（不足一次的部分留在流体槽）
            int amount = (resource.getAmount() / ElixirBottleOfPotionItem.FLUID_AMOUNT_PER_USE)
                    * ElixirBottleOfPotionItem.FLUID_AMOUNT_PER_USE;
            amount = Math.min(amount, CAPACITY);
            if (amount <= 0)
            {
                return 0;
            }
            if (action.execute())
            {
                ItemStack filled = new ItemStack(ModItems.ELIXIR_BOTTLE_OF_POTION.get());
                FluidTank tank = ElixirBottleOfPotionItem.getFluidTank(filled);
                tank.fill(new FluidStack(resource.getFluid(), amount, resource.getTag()), FluidAction.EXECUTE);
                ElixirBottleOfPotionItem.saveFluidTank(filled, tank);
                container = filled;
            }
            return amount;
        }
        // 其余流体（狂暴/融梦等）交给通用容器关系处理
        int filled = registryDelegate.fill(resource, action);
        if (action.execute())
        {
            container = registryDelegate.getContainer();
        }
        return filled;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action)
    {
        return FluidStack.EMPTY;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action)
    {
        return FluidStack.EMPTY;
    }

    @Override
    public ItemStack getContainer()
    {
        return container;
    }
}
