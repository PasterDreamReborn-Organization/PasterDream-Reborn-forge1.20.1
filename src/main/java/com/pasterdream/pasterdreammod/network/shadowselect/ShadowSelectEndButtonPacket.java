package com.pasterdream.pasterdreammod.network.shadowselect;

import com.pasterdream.pasterdreammod.world.block.twilightlantern.ShadowChoiceHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShadowSelectEndButtonPacket {

    private final int buttonId;

    public ShadowSelectEndButtonPacket(int buttonId) {
        this.buttonId = buttonId;
    }

    public static void encode(ShadowSelectEndButtonPacket message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.buttonId);
    }

    public static ShadowSelectEndButtonPacket decode(FriendlyByteBuf buffer) {
        return new ShadowSelectEndButtonPacket(buffer.readInt());
    }

    public static void handle(ShadowSelectEndButtonPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ServerPlayer player = contextSupplier.get().getSender();
            if (player != null) {
                ShadowChoiceHandler.choose(player, message.buttonId);
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
