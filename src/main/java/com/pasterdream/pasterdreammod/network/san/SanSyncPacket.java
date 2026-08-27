package com.pasterdream.pasterdreammod.network.san;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.capability.san.ISan;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.client.network.ClientPacketHandlers;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SanSyncPacket
{
    private final int playerId;
    private final double sanValue;

    public SanSyncPacket(Player player, ISan capability)
    {
        playerId = player.getId();
        sanValue = capability.getSanValue();
    }

    private SanSyncPacket(int playerId, double sanValue)
    {
        this.playerId = playerId;
        this.sanValue = sanValue;
    }

    public static void encode(SanSyncPacket packet, FriendlyByteBuf buffer)
    {
        buffer.writeInt(packet.playerId);
        buffer.writeDouble(packet.sanValue);
    }

    public static SanSyncPacket decode(FriendlyByteBuf buffer)
    {
        return new SanSyncPacket(buffer.readInt(), buffer.readDouble());
    }

    public static void handle(SanSyncPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> ClientPacketHandlers.handleSanSync(packet.sanValue));
        context.get().setPacketHandled(true);
    }

    public static void sendToPlayer(Player player, ISan capability)
    {
        if (player instanceof ServerPlayer serverPlayer)
        {
            ModNetwork.sendSanSyncPacketToPlayer(new SanSyncPacket(player, capability), serverPlayer);
        }
    }
}
