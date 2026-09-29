package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.client.network.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncBlockStatePacket
{
    private final String stateString;

    public SyncBlockStatePacket(String string)
    {
        this.stateString = string;
    }

    public SyncBlockStatePacket(FriendlyByteBuf buffer)
    {
        this.stateString = buffer.readUtf();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeUtf(stateString);
    }

    public static void handle(SyncBlockStatePacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> ClientPacketHandlers.handleSyncBlockState(packet.stateString));
        context.get().setPacketHandled(true);
    }
}
