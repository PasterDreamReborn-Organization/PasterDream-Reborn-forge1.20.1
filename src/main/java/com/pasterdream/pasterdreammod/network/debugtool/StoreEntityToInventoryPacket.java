package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.helper.itemwithnbt.spawneggwithnbt.GetSpawnEgg;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolEntityEditorMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StoreEntityToInventoryPacket
{
    private final int entityId;

    public StoreEntityToInventoryPacket(int entityId)
    {
        this.entityId = entityId;
    }

    public StoreEntityToInventoryPacket(FriendlyByteBuf buffer)
    {
        this.entityId = buffer.readVarInt();
    }

    public void encode(FriendlyByteBuf buf)
    {
        buf.writeVarInt(entityId);
    }

    public static void handle(StoreEntityToInventoryPacket packet, Supplier<NetworkEvent.Context> context)
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
                    ItemStack itemStack = GetSpawnEgg.getSpawnEgg(player.level().getEntity(packet.entityId));
                    if (!itemStack.isEmpty())
                    {
                        if (!player.getInventory().add(itemStack))
                        {
                            player.drop(itemStack, false);
                        }
                    }
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
