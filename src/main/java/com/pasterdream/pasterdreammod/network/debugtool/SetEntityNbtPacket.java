package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolEntityEditorMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class SetEntityNbtPacket
{
    private final int entityId;
    @Nullable private final CompoundTag NBT;

    public SetEntityNbtPacket(int entityId, @Nullable CompoundTag NBT)
    {
        this.entityId = entityId;
        this.NBT = NBT;
    }

    public SetEntityNbtPacket(FriendlyByteBuf buffer)
    {
        this.entityId = buffer.readVarInt();
        this.NBT = buffer.readNbt();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(entityId);
        buffer.writeNbt(NBT);
    }

    public static void handle(SetEntityNbtPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if (player != null && player.containerMenu instanceof DebugToolEntityEditorMenu)
            {
                ServerLevel level = player.serverLevel();
                Entity entity = level.getEntity(packet.entityId);

                if (entity != null)
                {
                    CompoundTag incoming = (packet.NBT == null ? new CompoundTag() : packet.NBT.copy());
                    entity.load(incoming);
                    level.sendBlockUpdated(entity.blockPosition(), level.getBlockState(entity.blockPosition()), level.getBlockState(entity.blockPosition()), 3);
                    ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SyncEntityNbtPacket(incoming));
                }
            }

        });
        context.get().setPacketHandled(true);
    }
}
