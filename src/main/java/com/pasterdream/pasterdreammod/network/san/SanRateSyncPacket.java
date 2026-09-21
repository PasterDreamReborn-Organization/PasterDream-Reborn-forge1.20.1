package com.pasterdream.pasterdreammod.network.san;

import com.pasterdream.pasterdreammod.capability.san.ISan;
import com.pasterdream.pasterdreammod.client.network.ClientPacketHandlers;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SanRateSyncPacket
{
    private final int playerId;
    private final double sanRate;

    public SanRateSyncPacket(Player player, ISan capability)
    {
        playerId = player.getId();
        sanRate = capability.getSanRate();
    }

    private SanRateSyncPacket(int playerId, double sanRate)
    {
        this.playerId = playerId;
        this.sanRate = sanRate;
    }

    public static void encode(SanRateSyncPacket packet, FriendlyByteBuf buffer)
    {
        buffer.writeInt(packet.playerId);
        buffer.writeDouble(packet.sanRate);
    }

    public static SanRateSyncPacket decode(FriendlyByteBuf buffer)
    {
        return new SanRateSyncPacket(buffer.readInt(), buffer.readDouble());
    }

    public static void handle(SanRateSyncPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> ClientPacketHandlers.handleSanRateSync(packet.sanRate));
        context.get().setPacketHandled(true);
    }

    public static void sendToPlayer(Player player, ISan capability)
    {
        if (player instanceof ServerPlayer serverPlayer)
        {
            ModNetwork.sendSanRateSyncPacketToPlayer(new SanRateSyncPacket(player, capability), serverPlayer);
        }
    }
}
