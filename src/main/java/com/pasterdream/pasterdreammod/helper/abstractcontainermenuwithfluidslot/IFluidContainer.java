package com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidContainer
{
    int getFluidContainerSize();
    FluidStack getFluid(int index);
    void setFluid(int index, FluidStack stack);
    default int getMaxFluidCapacity(int index)
    {
        return 1000;
    }
    // 方法名禁止与原版 Container 的方法重名（setChanged/stillValid/startOpen/stopOpen 等）。
    // 实现类若同时 implements net.minecraft.world.Container，重混淆会把同名实现改成 SRG 名
    // （如 m_6596_），而本接口的声明不会跟着改，正式环境经接口调用即 AbstractMethodError。
    void setFluidChanged();
    default boolean canPlaceFluid(int index, FluidStack stack)
    {
        return true;
    }
    default boolean canTakeFluid(int index, Player player)
    {
        return true;
    }
}
