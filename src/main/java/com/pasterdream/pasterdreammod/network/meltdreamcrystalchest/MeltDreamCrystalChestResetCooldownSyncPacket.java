package com.pasterdream.pasterdreammod.network.meltdreamcrystalchest;

import com.pasterdream.pasterdreammod.client.network.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MeltDreamCrystalChestResetCooldownSyncPacket
{
    private final int remainingTicks;

    public MeltDreamCrystalChestResetCooldownSyncPacket(int remainingTicks)
    {
        this.remainingTicks = remainingTicks;
    }

    public static void encode(MeltDreamCrystalChestResetCooldownSyncPacket packet, FriendlyByteBuf buffer)
    {
        buffer.writeInt(packet.remainingTicks);
    }

    public static MeltDreamCrystalChestResetCooldownSyncPacket decode(FriendlyByteBuf buffer)
    {
        return new MeltDreamCrystalChestResetCooldownSyncPacket(buffer.readInt());
    }

    public static void handle(MeltDreamCrystalChestResetCooldownSyncPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> ClientPacketHandlers.handleMeltDreamCrystalChestResetCooldown(packet.remainingTicks));
        context.get().setPacketHandled(true);
    }
}
