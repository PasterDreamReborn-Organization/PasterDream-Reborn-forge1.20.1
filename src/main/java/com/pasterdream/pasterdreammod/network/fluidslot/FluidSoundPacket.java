package com.pasterdream.pasterdreammod.network.fluidslot;

import com.pasterdream.pasterdreammod.client.network.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class FluidSoundPacket
{
    private final ResourceLocation fluidId;
    private final boolean isFill;

    public FluidSoundPacket(ResourceLocation fluidId, boolean isFill)
    {
        this.fluidId = fluidId;
        this.isFill = isFill;
    }

    public static void encode(FluidSoundPacket message, FriendlyByteBuf friendlyByteBuf)
    {
        friendlyByteBuf.writeResourceLocation(message.fluidId);
        friendlyByteBuf.writeBoolean(message.isFill);
    }

    public static FluidSoundPacket decode(FriendlyByteBuf friendlyByteBuf)
    {
        return new FluidSoundPacket(friendlyByteBuf.readResourceLocation(), friendlyByteBuf.readBoolean());
    }

    public static void handle(FluidSoundPacket message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() ->
        {
            if (context.getDirection().getReceptionSide().isClient())
            {
                ClientPacketHandlers.handleFluidSound(message.fluidId, message.isFill);
            }
        });
        context.setPacketHandled(true);
    }
}
