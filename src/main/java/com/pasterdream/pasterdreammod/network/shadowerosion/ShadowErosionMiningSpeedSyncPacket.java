package com.pasterdream.pasterdreammod.network.shadowerosion;

import com.pasterdream.pasterdreammod.init.ModNetwork;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 服务端 → 客户端：同步影蚀工具的挖掘速度倍率。
 * <p>
 * 服务端用权威亮度算出倍率后发给客户端，客户端用它做裂纹动画，
 * 从而保证两端挖掘速度一致，避免“方块挖碎后回弹”。
 */
public class ShadowErosionMiningSpeedSyncPacket {

    // 客户端缓存的最新倍率，默认无加成
    private static float clientMultiplier = 1.0F;

    private final float multiplier;

    public ShadowErosionMiningSpeedSyncPacket(float multiplier) {
        this.multiplier = multiplier;
    }

    public static void encode(ShadowErosionMiningSpeedSyncPacket packet, FriendlyByteBuf buffer) {
        buffer.writeFloat(packet.multiplier);
    }

    public static ShadowErosionMiningSpeedSyncPacket decode(FriendlyByteBuf buffer) {
        return new ShadowErosionMiningSpeedSyncPacket(buffer.readFloat());
    }

    public static void handle(ShadowErosionMiningSpeedSyncPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> clientMultiplier = packet.multiplier);
        context.get().setPacketHandled(true);
    }

    public static float getClientMultiplier() {
        return clientMultiplier;
    }

    public static void sendToPlayer(float multiplier, ServerPlayer player) {
        ModNetwork.sendShadowErosionMiningSpeedSyncPacketToPlayer(new ShadowErosionMiningSpeedSyncPacket(multiplier), player);
    }
}
