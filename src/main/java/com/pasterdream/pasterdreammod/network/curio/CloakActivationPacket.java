package com.pasterdream.pasterdreammod.network.curio;

import com.pasterdream.pasterdreammod.world.item.curio.EvasionCloakItem;
import com.pasterdream.pasterdreammod.world.item.curio.TurnbackCloakItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CloakActivationPacket {

    public CloakActivationPacket() {}

    public static void encode(CloakActivationPacket message, FriendlyByteBuf buffer) {}

    public static CloakActivationPacket decode(FriendlyByteBuf buffer) {
        return new CloakActivationPacket();
    }

    public static void handle(CloakActivationPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            if (player != null) {
                TurnbackCloakItem.tryActivate(player);
                EvasionCloakItem.tryActivate(player);
            }
        });
        context.get().setPacketHandled(true);
    }
}