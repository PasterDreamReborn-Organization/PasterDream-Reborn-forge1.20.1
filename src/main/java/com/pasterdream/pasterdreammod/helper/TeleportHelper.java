package com.pasterdream.pasterdreammod.helper;

import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.Set;

/**
 * 跨维度传送辅助。
 * <p>
 * {@code ServerPlayer.teleportTo(ServerLevel, ...)} 只发送 {@code ClientboundRespawnPacket}，
 * 客户端会重建 {@code LocalPlayer} 并丢失所有效果实例；原版 {@code changeDimension} 会在重生包后
 * 逐条补发效果包，这里提供同样的补发逻辑，避免出现「效果还在但图标不显示」。
 * <p>
 * 另外，重生包与位置包在同 tick 连发时，客户端处理重生期间可能忽略位置包，停留在传送门坐标并继续
 * 上报移动；服务端确认窗口（20 tick）超时后会接受旧坐标，把玩家拖回入口并反复触发传送（高延迟
 * 服务器上尤其明显，表现为卡加载界面 + ping 飙升）。因此传送后需在后续 3 tick 再强制同步一次落点。
 */
public final class TeleportHelper {

    private TeleportHelper() {}

    /** 跨维度传送玩家：效果补发 + 后续 3 tick 强制同步落点，避免客户端被旧坐标拉回。 */
    public static void teleportToDimension(ServerPlayer player, ServerLevel target,
                                           double x, double y, double z, float yRot, float xRot) {
        // 带 Set 的重载会为落点区块添加 POST_TELEPORT 票，确保目标区块加载并开始向客户端发送区块。
        player.teleportTo(target, x, y, z, Set.of(), yRot, xRot);
        resendActiveEffects(player);
        for (int delay = 1; delay <= 3; delay++) {
            player.server.tell(new TickTask(delay,
                    () -> forcePosition(player, target, x, y, z, yRot, xRot)));
        }
    }

    /** 服务端 setPos + 客户端位置包（设置 awaiting 位置，期间拒绝旧坐标上报）。 */
    private static void forcePosition(ServerPlayer player, ServerLevel target,
                                      double x, double y, double z, float yRot, float xRot) {
        if (player == null || player.level() != target) {
            return;
        }
        player.setPos(x, y, z);
        player.connection.teleport(x, y, z, yRot, xRot);
    }

    public static void resendActiveEffects(ServerPlayer player) {
        for (MobEffectInstance effect : player.getActiveEffects()) {
            player.connection.send(new ClientboundUpdateMobEffectPacket(player.getId(), effect));
        }
    }
}
