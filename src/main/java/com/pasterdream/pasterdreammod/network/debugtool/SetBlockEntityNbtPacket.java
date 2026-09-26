package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.init.ModNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class SetBlockEntityNbtPacket
{
    private final BlockPos blockPosition;
    private final CompoundTag NBT;

    public SetBlockEntityNbtPacket(BlockPos blockPosition, CompoundTag NBT)
    {
        this.blockPosition = blockPosition;
        this.NBT = NBT;
    }

    public SetBlockEntityNbtPacket(FriendlyByteBuf buffer)
    {
        this.blockPosition = buffer.readBlockPos();
        this.NBT = buffer.readNbt();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(blockPosition);
        buffer.writeNbt(NBT);
    }

    public static void handle(SetBlockEntityNbtPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if(player != null)
            {
                ServerLevel level = player.serverLevel();
                BlockEntity blockEntity = level.getBlockEntity(packet.blockPosition);
                if (blockEntity != null)
                {
                    CompoundTag incoming = (packet.NBT == null ? new CompoundTag() : packet.NBT.copy());
                    incoming.remove("x");
                    incoming.remove("y");
                    incoming.remove("z");
                    incoming.remove("id");

                    blockEntity.load(incoming);
                    blockEntity.setChanged();
                    level.sendBlockUpdated(packet.blockPosition, blockEntity.getBlockState(), blockEntity.getBlockState(), 3);
                    ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SyncBlockNbtPacket(blockEntity.saveWithId()));
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
