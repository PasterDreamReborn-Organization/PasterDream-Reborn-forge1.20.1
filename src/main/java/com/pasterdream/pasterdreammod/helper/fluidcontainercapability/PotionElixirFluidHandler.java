package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.fluid.PotionFluidHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

/**
 * 药水灵药瓶的流体容器能力：承载 1000mB 的通用「药水」流体，
 * 具体药水及其效果记录在流体 NBT 中（见 {@link PotionFluidHelper}）。
 */
public class PotionElixirFluidHandler implements IFluidHandlerItem
{
    private static final int CAPACITY = 1000;

    private ItemStack container;

    public PotionElixirFluidHandler(ItemStack container)
    {
        this.container = container;
    }

    @Override
    public int getTanks()
    {
        return 1;
    }

    @Override
    public FluidStack getFluidInTank(int tank)
    {
        Potion potion = PotionUtils.getPotion(container);
        if (potion != Potions.EMPTY)
        {
            return PotionFluidHelper.createStack(potion, CAPACITY);
        }
        return new FluidStack(ModFluids.POTION.get(), CAPACITY);
    }

    @Override
    public int getTankCapacity(int tank)
    {
        return CAPACITY;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack)
    {
        // 已装满的瓶子不可再注入
        return false;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action)
    {
        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action)
    {
        // isFluidEqual 会同时比较流体类型与 NBT（含药水与效果），避免用别的药水抽错瓶子
        if (resource.isEmpty() || resource.getAmount() < CAPACITY || !resource.isFluidEqual(getFluidInTank(0)))
        {
            return FluidStack.EMPTY;
        }
        return drain(CAPACITY, action);
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action)
    {
        if (maxDrain < CAPACITY)
        {
            return FluidStack.EMPTY;
        }
        FluidStack drained = getFluidInTank(0);
        if (action.execute())
        {
            // 排出全部药水后返还空灵药瓶
            container = new ItemStack(ModItems.ELIXIR_BOTTLE.get());
        }
        return drained;
    }

    @Override
    public ItemStack getContainer()
    {
        return container;
    }
}
