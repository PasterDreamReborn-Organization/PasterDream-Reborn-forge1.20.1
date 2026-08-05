package com.pasterdream.pasterdreammod.helper;

import com.pasterdream.pasterdreammod.Config;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

/**
 * BOSS 二层限伤系统：单发上限 + 距离衰减。
 */
public class BossDamageLimiter {
    private final float baseDamageCap;
    private final double rangeCap;

    public BossDamageLimiter(float baseDamageCap, double rangeCap) {
        this.baseDamageCap = baseDamageCap;
        this.rangeCap = rangeCap;
    }

    /**
     * 对传入伤害应用限伤。
     * @return 修改后的伤害值，-1 表示免疫（距离过远）
     */
    public float limit(LivingEntity self, DamageSource source, float amount) {
        // 穿甲伤害绕过限伤
        if (source.is(DamageTypeTags.BYPASSES_ARMOR))
            return amount;

        float effectiveCap = getEffectiveCap(self);

        // 单发上限
        amount = Math.min(amount, effectiveCap);

        // 距离衰减
        if (source.getEntity() != null) {
            double distSqr = self.distanceToSqr(source.getEntity());
            double limitSqr = rangeCap * rangeCap;
            double maxLimit = rangeCap * 1.5;
            double maxLimitSqr = maxLimit * maxLimit;

            if (distSqr >= maxLimitSqr)
                return -1;
            if (distSqr > limitSqr) {
                double distance = Math.sqrt(distSqr);
                float multiplier = (float) ((maxLimit - distance) / (maxLimit - rangeCap));
                amount *= multiplier;
                if (amount <= 0)
                    return -1;
            }
        }

        return amount;
    }

    private float getEffectiveCap(LivingEntity self) {
        if (Config.bossShadowDifficultyAffectsDamageCap) {
            int tier = ShadowDifficultyHelper.getDifficultyContext(self);
            double atkMult = ShadowDifficultyHelper.getAttackMultiplier(tier);
            if (atkMult > 0)
                return (float) (baseDamageCap / atkMult);
        }
        return baseDamageCap;
    }
}
