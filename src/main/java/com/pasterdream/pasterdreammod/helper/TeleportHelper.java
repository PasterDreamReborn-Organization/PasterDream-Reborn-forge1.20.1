package com.pasterdream.pasterdreammod.helper;

import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

/**
 * 跨维度传送辅助。
 * {@code ServerPlayer.teleportTo(ServerLevel, ...)} 只发送 RespawnPacket，客户端会重建 LocalPlayer
 * 并丢失所有效果实例；原版 {@code changeDimension} 会在重生包后逐条补发效果包，这里提供同样的补发逻辑，
 * 供自定义传送在跨维度 teleportTo 之后调用，避免出现「效果还在但图标不显示」。
 */
public final class TeleportHelper {

    private TeleportHelper() {}

    public static void resendActiveEffects(ServerPlayer player) {
        for (MobEffectInstance effect : player.getActiveEffects()) {
            player.connection.send(new ClientboundUpdateMobEffectPacket(player.getId(), effect));
        }
    }
}
