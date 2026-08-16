package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class GenericContainerCapabilityProvider implements ICapabilityProvider
{
    private final LazyOptional<IFluidHandler> holder;

    public GenericContainerCapabilityProvider(ItemStack itemStack)
    {
        this(() -> new GenericContainerFluidHandler(itemStack));
    }

    public GenericContainerCapabilityProvider(Supplier<IFluidHandler> factory)
    {
        this.holder = LazyOptional.of(() -> factory.get());
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.FLUID_HANDLER_ITEM)
        {
            return holder.cast();
        }
        return LazyOptional.empty();
    }
}
