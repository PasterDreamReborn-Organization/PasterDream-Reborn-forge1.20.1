package com.pasterdream.pasterdreammod.network.blueprint;

import com.pasterdream.pasterdreammod.helper.blueprint.BlueprintPlacementManager;
import com.pasterdream.pasterdreammod.world.item.blueprints.BluePrintItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StartBlueprintPlacementPacket
{
    private final CompoundTag materialNBT;
    private final CompoundTag resultNBT;

    public StartBlueprintPlacementPacket(CompoundTag materialNBT, CompoundTag resultNBT)
    {
        this.materialNBT = materialNBT;
        this.resultNBT = resultNBT;
    }

    public static void encode(StartBlueprintPlacementPacket message, FriendlyByteBuf buffer)
    {
        buffer.writeNbt(message.materialNBT);
        buffer.writeNbt(message.resultNBT);
    }

    public static StartBlueprintPlacementPacket decode(FriendlyByteBuf buffer)
    {
        return new StartBlueprintPlacementPacket(buffer.readNbt(), buffer.readNbt());
    }

    public static void handle(StartBlueprintPlacementPacket message, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if (player != null)
            {
                ItemStack heldItem = player.getMainHandItem();
                if (heldItem.getItem() instanceof BluePrintItem)
                {
                    heldItem.getOrCreateTag().putBoolean("isPlacing", true);
                    player.inventoryMenu.broadcastChanges();
                }
                BlueprintPlacementManager.startPlacement(player, message.materialNBT, message.resultNBT);
            }
        });
        context.get().setPacketHandled(true);
    }
}