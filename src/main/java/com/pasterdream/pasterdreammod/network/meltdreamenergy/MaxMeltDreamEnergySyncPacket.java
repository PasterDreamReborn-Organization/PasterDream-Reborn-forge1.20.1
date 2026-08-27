package com.pasterdream.pasterdreammod.network.meltdreamenergy;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.IMeltDreamEnergy;
import com.pasterdream.pasterdreammod.client.network.ClientPacketHandlers;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MaxMeltDreamEnergySyncPacket
{
    private final int playerId;
    private final double maxMeltDreamEnergy;

    public MaxMeltDreamEnergySyncPacket(Player player, IMeltDreamEnergy capability)
    {
        this.playerId = player.getId();
        this.maxMeltDreamEnergy = capability.getMaxMeltDreamEnergy();
    }

    private MaxMeltDreamEnergySyncPacket(int playerId, double maxMeltDreamEnergy)
    {
        this.playerId = playerId;
        this.maxMeltDreamEnergy = maxMeltDreamEnergy;
    }

    public static void encode(MaxMeltDreamEnergySyncPacket packet, FriendlyByteBuf buffer)
    {
        buffer.writeInt(packet.playerId);
        buffer.writeDouble(packet.maxMeltDreamEnergy);
    }

    public static MaxMeltDreamEnergySyncPacket decode(FriendlyByteBuf buffer)
    {
        return new MaxMeltDreamEnergySyncPacket(buffer.readInt(), buffer.readDouble());
    }

    public static void handle(MaxMeltDreamEnergySyncPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> ClientPacketHandlers.handleMaxMeltDreamEnergySync(packet.maxMeltDreamEnergy));
        context.get().setPacketHandled(true);
    }

    public static void sendToPlayer(Player player, IMeltDreamEnergy capability)
    {
        if (player instanceof ServerPlayer serverPlayer)
        {
            ModNetwork.sendMaxMeltDreamEnergySyncPacketToPlayer(new MaxMeltDreamEnergySyncPacket(player, capability), serverPlayer);
        }
    }
}
