package com.pasterdream.pasterdreammod.capability;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.IMeltDreamEnergy;
import com.pasterdream.pasterdreammod.capability.san.ISan;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapabilities
{
    public static final Capability<IMeltDreamEnergy> MELT_DREAM_ENERGY = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<ISan> SAN = CapabilityManager.get(new CapabilityToken<>() {});
}
