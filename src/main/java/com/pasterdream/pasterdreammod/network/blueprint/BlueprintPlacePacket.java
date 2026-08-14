package com.pasterdream.pasterdreammod.network.blueprint;

import com.pasterdream.pasterdreammod.helper.blueprint.BlueprintPlacementManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BlueprintPlacePacket
{
    private final BlockPos targetPos;

    public BlueprintPlacePacket(BlockPos targetPos)
    {
        this.targetPos = targetPos;
    }

    public static void encode(BlueprintPlacePacket message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.targetPos);
    }

    public static BlueprintPlacePacket decode(FriendlyByteBuf buffer)
    {
        return new BlueprintPlacePacket(buffer.readBlockPos());
    }

    public static void handle(BlueprintPlacePacket message, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if (player != null)
            {
                BlueprintPlacementManager.tryPlace(player, message.targetPos);
            }
        });
        context.get().setPacketHandled(true);
    }
}
