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

public class IsSanEnableSyncPacket
{
    private final int playerId;
    private final boolean isEnabled;

    public IsSanEnableSyncPacket(Player player, ISan capability)
    {
        playerId = player.getId();
        isEnabled = capability.getIsSanEnabled();
    }

    private IsSanEnableSyncPacket(int playerId, boolean isEnabled)
    {
        this.playerId = playerId;
        this.isEnabled = isEnabled;
    }

    public static void encode(IsSanEnableSyncPacket packet, FriendlyByteBuf buffer)
    {
        buffer.writeInt(packet.playerId);
        buffer.writeBoolean(packet.isEnabled);
    }

    public static IsSanEnableSyncPacket decode(FriendlyByteBuf buffer)
    {
        return new IsSanEnableSyncPacket(buffer.readInt(), buffer.readBoolean());
    }

    public static void handle(IsSanEnableSyncPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> ClientPacketHandlers.handleIsSanEnableSync(packet.isEnabled));
        context.get().setPacketHandled(true);
    }

    public static void sendToPlayer(Player player, ISan capability)
    {
        if (player instanceof ServerPlayer serverPlayer)
        {
            ModNetwork.sendIsSanEnableSyncPacketToPlayer(new IsSanEnableSyncPacket(player, capability), serverPlayer);
        }
    }
}
