package com.pasterdream.pasterdreammod.network.meltdreamenergy;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.IMeltDreamEnergy;
import com.pasterdream.pasterdreammod.client.network.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class IsNotNeedSyncPacket
{
    private final int playerId;
    private final boolean isNotNeed;

    public IsNotNeedSyncPacket(Player player, IMeltDreamEnergy capability)
    {
        playerId = player.getId();
        isNotNeed = capability.getIsOrNotNeedConsumeDreamEnergy();
    }

    private IsNotNeedSyncPacket(int playerId, boolean isNotNeed)
    {
        this.playerId = playerId;
        this.isNotNeed = isNotNeed;
    }

    public static void encode(IsNotNeedSyncPacket packet, FriendlyByteBuf buffer)
    {
        buffer.writeInt(packet.playerId);
        buffer.writeBoolean(packet.isNotNeed);
    }

    public static IsNotNeedSyncPacket decode(FriendlyByteBuf buffer)
    {
        return new IsNotNeedSyncPacket(buffer.readInt(), buffer.readBoolean());
    }

    public static void handle(IsNotNeedSyncPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> ClientPacketHandlers.handleIsNotNeedSync(packet.isNotNeed));
        context.get().setPacketHandled(true);
    }
}
