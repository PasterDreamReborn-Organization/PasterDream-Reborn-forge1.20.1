package com.pasterdream.pasterdreammod.helper.cooldown;

import com.pasterdream.pasterdreammod.world.item.curio.MagnifyingGlassOfSherryItem;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public final class SkillLockHelper {

    private SkillLockHelper() {}

    /**
     * 战技是否被锁定（当前唯一来源：佩戴大侦探的放大镜）。
     * <p>
     * 高频自动触发路径（命中 / 挥砍 / 被动）应使用 {@link #isSkillLockedSilent}，避免刷屏。
     */
    public static boolean isSkillLocked(Player player) {
        if (isSkillLockedSilent(player)) {
            if (player instanceof ServerPlayer sp) {
                sp.displayClientMessage(Component.translatable("message.pasterdream.skill_locked"), true);
            }
            return true;
        }
        return false;
    }

    /** 战技是否被锁定（不发送提示）。用于每次攻击 / 挥砍等高频路径。 */
    public static boolean isSkillLockedSilent(Player player) {
        return MagnifyingGlassOfSherryItem.isWearing(player);
    }
}
