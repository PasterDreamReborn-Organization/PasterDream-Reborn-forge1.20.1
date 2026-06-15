package com.pasterdream.pasterdreammod.network;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.capability.san.ISan;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SanSyncPacket
{
    private final int playerId;
    private final double sanValue;
    private final boolean isEnabled;

    public SanSyncPacket(Player player, ISan capability)
    {
        playerId = player.getId();
        sanValue = capability.getSanValue();
        isEnabled = capability.getIsSanEnabled();
    }

    private SanSyncPacket(int playerId, double sanValue, boolean isEnabled)
    {
        this.playerId = playerId;
        this.sanValue = sanValue;
        this.isEnabled = isEnabled;
    }

    public static void encode(SanSyncPacket packet, FriendlyByteBuf buffer)
    {
        buffer.writeInt(packet.playerId);
        buffer.writeDouble(packet.sanValue);
        buffer.writeBoolean(packet.isEnabled);
    }

    public static SanSyncPacket decode(FriendlyByteBuf buffer)
    {
        return new SanSyncPacket(buffer.readInt(), buffer.readDouble(), buffer.readBoolean());
    }

    public static void handle(SanSyncPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            Player player = Minecraft.getInstance().player;
            if (player != null)
            {
                player.getCapability(ModCapabilities.SAN).ifPresent(capability ->
                {
                    capability.setSanValue(packet.sanValue);
                    capability.setIsSanEnable(packet.isEnabled);
                });
            }
        });
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
