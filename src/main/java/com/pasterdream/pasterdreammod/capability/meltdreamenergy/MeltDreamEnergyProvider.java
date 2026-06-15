package com.pasterdream.pasterdreammod.capability.meltdreamenergy;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class MeltDreamEnergyProvider implements ICapabilitySerializable<CompoundTag>
{
    private final IMeltDreamEnergy instance = new MeltDreamEnergy();
    private final LazyOptional<IMeltDreamEnergy> lazyOptional = LazyOptional.of(() -> instance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side)
    {
        return ModCapabilities.MELT_DREAM_ENERGY.orEmpty(capability, lazyOptional);
    }

    @Override
    public CompoundTag serializeNBT()
    {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("melt_dream_energy_count", instance.getMeltDreamEnergy());
        tag.putBoolean("is_not_need", instance.getIsOrNotNeedConsumeDreamEnergy());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag)
    {
        instance.setMeltDreamEnergy(tag.getDouble("melt_dream_energy_count"));
        instance.setIsOrNotNeedConsumeDreamEnergy(tag.getBoolean("is_not_need"));
    }
}
