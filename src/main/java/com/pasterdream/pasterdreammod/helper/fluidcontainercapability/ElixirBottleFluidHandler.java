package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

/**
 * 空灵药瓶的流体容器能力：既可注入通用「药水」流体（把流体 NBT 中的 "Potion" 键拷到成品灵药瓶，
 * 实现双向填装），也可按 {@link FluidContainerRegistry} 注入狂暴/融梦等已注册流体。
 */
public class ElixirBottleFluidHandler implements IFluidHandlerItem
{
    private static final int CAPACITY = 1000;
    private static final String TAG_POTION = "Potion";

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
            if (resource.getAmount() < CAPACITY)
            {
                return 0;
            }
            if (action.execute())
            {
                ItemStack filled = new ItemStack(ModItems.ELIXIR_BOTTLE_OF_POTION.get());
                CompoundTag tag = resource.getTag();
                if (tag != null && tag.contains(TAG_POTION))
                {
                    filled.getOrCreateTag().putString(TAG_POTION, tag.getString(TAG_POTION));
                }
                container = filled;
            }
            return CAPACITY;
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
