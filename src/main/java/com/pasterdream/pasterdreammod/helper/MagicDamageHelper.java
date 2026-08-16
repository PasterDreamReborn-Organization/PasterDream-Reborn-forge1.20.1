package com.pasterdream.pasterdreammod.helper;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

public final class MagicDamageHelper {

    private MagicDamageHelper() {}

    /**
     * 返回玩家的魔法伤害倍率（默认 1.0 = 无加成）。
     */
    public static float getMagicDamageMultiplier(Player player) {
        AttributeInstance inst = player.getAttribute(ModAttributes.MAGIC_DAMAGE_RATE.get());
        return inst != null ? (float) inst.getValue() : 1.0f;
    }
}
