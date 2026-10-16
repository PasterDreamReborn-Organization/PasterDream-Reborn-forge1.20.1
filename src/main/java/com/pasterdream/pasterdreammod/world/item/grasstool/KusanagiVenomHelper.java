package com.pasterdream.pasterdreammod.world.item.grasstool;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

/**
 * 『天丛云』草薙武器剧毒的叠加规则。
 *
 * <p>近战命中或毒刺命中各叠加一层：等级 +1（最高 III 级），持续时间 +5s（最长 60s）。
 * 首次命中为 I 级 / 5s。
 */
public final class KusanagiVenomHelper {

    public static final int MAX_AMPLIFIER = 2;      // 最高 III 级（amplifier 从 0 起）
    public static final int DURATION_PER_HIT = 100; // 每次命中 +5s
    public static final int MAX_DURATION = 1200;    // 最长 60s

    private KusanagiVenomHelper() {
    }

    /** 对目标叠加一层武器剧毒（近战与毒刺共用）。 */
    public static void applyStacks(LivingEntity target) {
        MobEffectInstance existing = target.getEffect(ModEffects.HIGHLY_TOXIC.get());
        int amplifier;
        int duration;
        if (existing == null) {
            amplifier = 0;
            duration = DURATION_PER_HIT;
        } else {
            int current = existing.getAmplifier();
            // 保留高于上限的等级（如药剂瓶的 IV），仅按武器规则递增
            amplifier = Math.max(current, Math.min(current + 1, MAX_AMPLIFIER));
            duration = Math.min(existing.getDuration() + DURATION_PER_HIT, MAX_DURATION);
        }
        target.addEffect(new MobEffectInstance(ModEffects.HIGHLY_TOXIC.get(), duration, amplifier));
    }
}
