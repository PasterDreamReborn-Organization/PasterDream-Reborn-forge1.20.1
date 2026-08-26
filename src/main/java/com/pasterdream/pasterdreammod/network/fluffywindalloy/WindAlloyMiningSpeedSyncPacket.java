package com.pasterdream.pasterdreammod.network.fluffywindalloy;

import com.pasterdream.pasterdreammod.init.ModNetwork;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 服务端 → 客户端：同步萦风合金工具的挖掘速度倍率（疾风过境被动）。
 * <p>
 * 服务端用权威移动速度算出倍率后发给客户端，客户端用它做裂纹动画，
 * 从而保证两端挖掘速度一致，避免"方块挖碎后回弹"。
 */
public class WindAlloyMiningSpeedSyncPacket {

    // 客户端缓存的最新倍率，默认无加成
    private static float clientMultiplier = 1.0F;

    private final float multiplier;

    public WindAlloyMiningSpeedSyncPacket(float multiplier) {
        this.multiplier = multiplier;
    }

    public static void encode(WindAlloyMiningSpeedSyncPacket packet, FriendlyByteBuf buffer) {
        buffer.writeFloat(packet.multiplier);
    }

    public static WindAlloyMiningSpeedSyncPacket decode(FriendlyByteBuf buffer) {
        return new WindAlloyMiningSpeedSyncPacket(buffer.readFloat());
    }

    public static void handle(WindAlloyMiningSpeedSyncPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> clientMultiplier = packet.multiplier);
        context.get().setPacketHandled(true);
    }

    public static float getClientMultiplier() {
        return clientMultiplier;
    }

    public static void sendToPlayer(float multiplier, ServerPlayer player) {
        ModNetwork.sendWindAlloyMiningSpeedSyncPacketToPlayer(new WindAlloyMiningSpeedSyncPacket(multiplier), player);
    }
}