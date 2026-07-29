package com.pasterdream.pasterdreammod.network.skill;

import com.pasterdream.pasterdreammod.world.item.TerraBladeItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TerraBladeSwingPacket {

    public TerraBladeSwingPacket() {}

    public static void encode(TerraBladeSwingPacket message, FriendlyByteBuf buffer) {}

    public static TerraBladeSwingPacket decode(FriendlyByteBuf buffer) {
        return new TerraBladeSwingPacket();
    }

    public static void handle(TerraBladeSwingPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            if (player != null) {
                ItemStack stack = player.getMainHandItem();
                if (stack.getItem() instanceof TerraBladeItem) {
                    player.swing(InteractionHand.MAIN_HAND);
                    TerraBladeItem.tryFireSwordWave(player, stack);
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
