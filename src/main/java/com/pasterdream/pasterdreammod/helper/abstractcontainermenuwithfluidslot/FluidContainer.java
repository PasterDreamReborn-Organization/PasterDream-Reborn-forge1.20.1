package com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class FluidContainer
{
    public static class GenericFluidContainer implements IFluidContainer
    {
        public final FluidTank[] tanks;

        public GenericFluidContainer(FluidTank[] tanks)
        {
            this.tanks = tanks;
        }

        @Override
        public int getFluidContainerSize()
        {
            return tanks.length;
        }

        @Override
        public FluidStack getFluid(int index)
        {
            return tanks[index].getFluid();
        }

        @Override
        public void setFluid(int index, FluidStack stack)
        {
            tanks[index].setFluid(stack);
        }

        @Override
        public int getMaxFluidCapacity(int index)
        {
            return tanks[index].getCapacity();
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
}
