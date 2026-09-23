package com.pasterdream.pasterdreammod.world.item.debugtool.generichandler;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.IFluidContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidHandlerContainerWrapper implements IFluidContainer
{
    private final IFluidHandler handler;
    private final Runnable onChanged;

    public FluidHandlerContainerWrapper(IFluidHandler handler, Runnable onChanged)
    {
        this.handler = handler;
        this.onChanged = onChanged;
    }

    @Override
    public int getFluidContainerSize()
    {
        return handler.getTanks();
    }

    @Override
    public FluidStack getFluid(int index)
    {
        return handler.getFluidInTank(index);
    }

    @Override
    public void setFluid(int index, FluidStack fluidStack)
    {
        // IFluidHandler 没有 setFluidInTank，用 drain + fill 模拟
        // 先把这个 tank 里的流体全部抽出
        FluidStack current = handler.getFluidInTank(index);
        if (!current.isEmpty())
        {
            handler.drain(current, IFluidHandler.FluidAction.EXECUTE);
        }
        // 再灌入新流体
        if (!fluidStack.isEmpty())
        {
            handler.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
        }
    }

    @Override
    public int getMaxFluidCapacity(int index)
    {
        return handler.getTankCapacity(index);
    }

    @Override
    public void setChanged()
    {
        if (onChanged != null)
        {
            onChanged.run();
        }
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    @Override
    public boolean canPlaceFluid(int index, FluidStack stack)
    {
        return handler.isFluidValid(index, stack);
    }

    @Override
    public boolean canTakeFluid(int index, Player player)
    {
        return true;
    }
}
