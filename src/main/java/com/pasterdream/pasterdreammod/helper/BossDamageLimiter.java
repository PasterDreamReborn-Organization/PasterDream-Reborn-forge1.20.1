package com.pasterdream.pasterdreammod.helper;

import com.pasterdream.pasterdreammod.Config;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

/**
 * BOSS 三层限伤系统：单发上限 + 距离衰减 + DPS 桶。
 * 参考 Cataclysm 设计。
 */
public class BossDamageLimiter {
    private final float baseDamageCap;
    private final float dpsCap;
    private final double rangeCap;
    private float damageBucket;

    public BossDamageLimiter(float baseDamageCap, float dpsCap, double rangeCap) {
        this.baseDamageCap = baseDamageCap;
        this.dpsCap = dpsCap;
        this.rangeCap = rangeCap;
    }

    /** 每 tick 衰减 DPS 桶 */
    public void tick() {
        if (Config.bossDamageCapEnabled && Config.bossDpsCapEnabled && damageBucket > 0) {
            damageBucket = Math.max(0, damageBucket - dpsCap / 20f);
        }
    }

    /**
     * 对传入伤害应用三层限伤（每一层均受配置开关控制）。
     * @return 修改后的伤害值，-1 表示免疫（距离过远）
     */
    public float limit(LivingEntity self, DamageSource source, float amount) {
        // 仅 /kill 等绕过限伤
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
            return amount;

        boolean singleHitEnabled = Config.bossDamageCapEnabled;
        boolean dpsEnabled = singleHitEnabled && Config.bossDpsCapEnabled;
        boolean rangeEnabled = Config.bossRangeCapEnabled;

        float effectiveCap = getEffectiveCap(self);

        // 第一层：单发上限
        if (singleHitEnabled) {
            amount = Math.min(amount, effectiveCap);
        }

        // 第二层：距离衰减
        if (rangeEnabled && source.getEntity() != null) {
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

        // 第三层：DPS 桶
        if (dpsEnabled) {
            float projected = damageBucket + amount;
            if (projected > effectiveCap) {
                float roomLeft = effectiveCap - damageBucket;
                if (roomLeft > 0) {
                    amount = roomLeft;
                    damageBucket = effectiveCap;
                } else {
                    amount = 0.1f;
                }
            } else {
                damageBucket += amount;
            }
        }

        return amount;
    }

    /** 回滚桶状态（hurt 失败时调用） */
    public void rollback(float prevBucket) {
        this.damageBucket = prevBucket;
    }

    public float getDamageBucket() {
        return damageBucket;
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
