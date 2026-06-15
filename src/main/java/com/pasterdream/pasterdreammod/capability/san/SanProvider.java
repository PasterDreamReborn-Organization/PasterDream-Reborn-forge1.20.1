package com.pasterdream.pasterdreammod.capability.san;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class SanProvider implements ICapabilitySerializable<CompoundTag>
{
    private final ISan instance = new San();
    private final LazyOptional<ISan> lazyOptional = LazyOptional.of(() -> instance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side)
    {
        return ModCapabilities.SAN.orEmpty(capability, lazyOptional);
    }

    @Override
    public CompoundTag serializeNBT()
    {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("san_value", instance.getSanValue());
        tag.putBoolean("is_enabled", instance.getIsSanEnabled());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag)
    {
        instance.setSanValue(tag.getDouble("san_value"));
        instance.setIsSanEnable(tag.getBoolean("is_enabled"));
    }
}
