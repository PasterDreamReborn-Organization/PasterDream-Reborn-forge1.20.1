package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.network.FluidSlotInteractPacket;
import com.pasterdream.pasterdreammod.network.FluidSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetwork
{
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int id = 0;

    public static void register()
    {
        CHANNEL.registerMessage(id++, FluidSlotInteractPacket.class, FluidSlotInteractPacket::encode, FluidSlotInteractPacket::decode, FluidSlotInteractPacket::handle);
        CHANNEL.registerMessage(id++, FluidSoundPacket.class, FluidSoundPacket::encode, FluidSoundPacket::decode, FluidSoundPacket::handle);
    }
}
