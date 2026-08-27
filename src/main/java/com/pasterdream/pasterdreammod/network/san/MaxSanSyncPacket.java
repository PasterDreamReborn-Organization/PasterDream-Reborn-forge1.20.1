package com.pasterdream.pasterdreammod.network.san;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.capability.san.ISan;
import com.pasterdream.pasterdreammod.client.network.ClientPacketHandlers;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MaxSanSyncPacket
{
    private final int playerId;
    private final double maxSanValue;

    public MaxSanSyncPacket(Player player, ISan capability)
    {
        playerId = player.getId();
        maxSanValue = capability.getMaxSanValue();
    }

    private MaxSanSyncPacket(int playerId, double maxSanValue)
    {
        this.playerId = playerId;
        this.maxSanValue = maxSanValue;
    }

    public static void encode(MaxSanSyncPacket packet, FriendlyByteBuf buffer)
    {
        buffer.writeInt(packet.playerId);
        buffer.writeDouble(packet.maxSanValue);
    }

    public static MaxSanSyncPacket decode(FriendlyByteBuf buffer)
    {
        return new MaxSanSyncPacket(buffer.readInt(), buffer.readDouble());
    }

    public static void handle(MaxSanSyncPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> ClientPacketHandlers.handleMaxSanSync(packet.maxSanValue));
        context.get().setPacketHandled(true);
    }

    public static void sendToPlayer(Player player, ISan capability)
    {
        if (player instanceof ServerPlayer serverPlayer)
        {
            ModNetwork.sendMaxSanSyncPacketToPlayer(new MaxSanSyncPacket(player, capability), serverPlayer);
        }
    }
}
