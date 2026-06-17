package com.pasterdream.pasterdreammod.network;

import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DreamCauldronCraftPacket
{
    private final BlockPos blockPosition;

    public DreamCauldronCraftPacket(BlockPos blockPosition)
    {
        this.blockPosition = blockPosition;
    }

    public static void encode(DreamCauldronCraftPacket message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.blockPosition);
    }

    public static DreamCauldronCraftPacket decode(FriendlyByteBuf buffer)
    {
        return new DreamCauldronCraftPacket(buffer.readBlockPos());
    }

    public static void handle(DreamCauldronCraftPacket message, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if (player != null)
            {
                Level level = player.level();
                BlockEntity blockEntity = level.getBlockEntity(message.blockPosition);
                if (blockEntity instanceof DreamCauldronBlockEntity cauldron)
                {
                    if (cauldron.canCraft())
                    {
                        cauldron.craft();
                    }
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
