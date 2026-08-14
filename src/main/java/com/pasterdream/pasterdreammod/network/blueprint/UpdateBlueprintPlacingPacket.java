package com.pasterdream.pasterdreammod.network.blueprint;

import com.pasterdream.pasterdreammod.world.item.blueprints.BluePrintItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateBlueprintPlacingPacket
{
    private final boolean placing;

    public UpdateBlueprintPlacingPacket(boolean placing)
    {
        this.placing = placing;
    }

    public static void encode(UpdateBlueprintPlacingPacket message, FriendlyByteBuf buffer)
    {
        buffer.writeBoolean(message.placing);
    }

    public static UpdateBlueprintPlacingPacket decode(FriendlyByteBuf buffer)
    {
        return new UpdateBlueprintPlacingPacket(buffer.readBoolean());
    }

    public static void handle(UpdateBlueprintPlacingPacket message, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if (player != null)
            {
                ItemStack held = player.getMainHandItem();
                if (held.getItem() instanceof BluePrintItem)
                {
                    held.getOrCreateTag().putBoolean("isPlacing", message.placing);
                    player.inventoryMenu.broadcastChanges();
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
