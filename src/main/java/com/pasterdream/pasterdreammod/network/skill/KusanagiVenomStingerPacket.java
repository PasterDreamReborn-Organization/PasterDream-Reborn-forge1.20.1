package com.pasterdream.pasterdreammod.network.skill;

import com.pasterdream.pasterdreammod.world.item.grasstool.MurakumoKusanagiItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 客户端左键（空挥 / 破坏方块 / 攻击实体）→ 服务端，用于触发『天丛云』草薙的毒刺发射。
 * 与 {@code TerraBladeSwingPacket} 同构：避免用 {@code ServerPlayer#swing} 钩子（Q 丢物品会误触发）。
 */
public class KusanagiVenomStingerPacket {

    public KusanagiVenomStingerPacket() {}

    public static void encode(KusanagiVenomStingerPacket message, FriendlyByteBuf buffer) {}

    public static KusanagiVenomStingerPacket decode(FriendlyByteBuf buffer) {
        return new KusanagiVenomStingerPacket();
    }

    public static void handle(KusanagiVenomStingerPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            if (player != null) {
                MurakumoKusanagiItem.onSwing(player);
            }
        });
        context.get().setPacketHandled(true);
    }
}
