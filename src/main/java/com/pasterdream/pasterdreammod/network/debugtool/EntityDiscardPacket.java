package com.pasterdream.pasterdreammod.network.debugtool;


import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolEntityEditorMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EntityDiscardPacket
{
    private final int entityId;

    public EntityDiscardPacket(int entityId)
    {
        this.entityId = entityId;
    }

    public EntityDiscardPacket(FriendlyByteBuf buffer)
    {
        this.entityId = buffer.readVarInt();
    }

    public void encode(FriendlyByteBuf buf)
    {
        buf.writeVarInt(entityId);
    }

    public static void handle(EntityDiscardPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if(player != null && player.containerMenu instanceof DebugToolEntityEditorMenu)
            {
                ServerLevel level = player.serverLevel();
                Entity entity = level.getEntity(packet.entityId);
                if (entity != null)
                {
                    entity.discard();
                    player.closeContainer();
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
