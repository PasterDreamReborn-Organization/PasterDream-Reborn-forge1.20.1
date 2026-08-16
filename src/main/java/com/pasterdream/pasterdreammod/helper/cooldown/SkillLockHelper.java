package com.pasterdream.pasterdreammod.helper.cooldown;

import com.pasterdream.pasterdreammod.world.item.curio.MagnifyingGlassOfSherryItem;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public final class SkillLockHelper {

    private SkillLockHelper() {}

    /**
     * 战技是否被锁定（当前唯一来源：佩戴大侦探的放大镜）。
     * 未来新增锁定来源时在此处叠加判断即可，武器侧无需改动。
     */
    public static boolean isSkillLocked(Player player) {
        if (MagnifyingGlassOfSherryItem.isWearing(player)) {
            if (player instanceof ServerPlayer sp) {
                sp.displayClientMessage(Component.translatable("message.pasterdream.skill_locked"), true);
            }
            return true;
        }
        return false;
    }
}
