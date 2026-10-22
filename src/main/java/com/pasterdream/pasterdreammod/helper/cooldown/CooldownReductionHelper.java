package com.pasterdream.pasterdreammod.helper.cooldown;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

/**
 * 冷却缩减属性辅助。
 * <p>
 * 属性值语义为“缩减比例”：默认 {@code 0.0}，正数减少冷却，负数增加冷却。
 * 属性注册范围 {@code -10.0 ~ 1.0}，超过 100% 的缩减由 {@code RangedAttribute} 直接截断。
 */
public final class CooldownReductionHelper {

    public static final double MIN_REDUCTION = -10.0;
    public static final double MAX_REDUCTION = 1.0;
    public static final double MIN_COOLDOWN_FACTOR = 0.0;
    public static final double MAX_COOLDOWN_FACTOR = 10.0;

    private CooldownReductionHelper() {}

    /** 读取属性的总缩减值（已按 -10 ~ 1 钳制）。 */
    public static double getReduction(Player player, Attribute attribute) {
        AttributeInstance instance = player.getAttribute(attribute);
        return instance == null ? 0.0 : Mth.clamp(instance.getValue(), MIN_REDUCTION, MAX_REDUCTION);
    }

    /** 将属性缩减值转换为最终冷却倍率（0 = 无冷却，1 = 原时长）。 */
    public static double getCooldownFactor(Player player, Attribute attribute) {
        return Mth.clamp(1.0 - getReduction(player, attribute), MIN_COOLDOWN_FACTOR, MAX_COOLDOWN_FACTOR);
    }
}
