package com.pasterdream.pasterdreammod.world.item.mortar;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class MortarItemCapabilityProvider implements ICapabilityProvider
{
    private final LazyOptional<MortarInventory> inventoryOption;

    public MortarItemCapabilityProvider(ItemStack stack)
    {
        this.inventoryOption = LazyOptional.of(() -> new MortarInventory(new MortarDataHandler(stack)));
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.ITEM_HANDLER)
        {
            return inventoryOption.lazyMap(MortarInventory::getItemHandler).cast();
        }

        if (cap == ForgeCapabilities.FLUID_HANDLER_ITEM)
        {
            return inventoryOption.lazyMap(MortarInventory::getFluidTanks).cast();
        }
        return LazyOptional.empty();
    }
}
