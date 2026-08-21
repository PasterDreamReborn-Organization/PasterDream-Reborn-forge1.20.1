package com.pasterdream.pasterdreammod.helper;

/**
 * BOSS 限伤系统的一组配置值（单发上限 + 距离衰减 + DPS 桶）。
 */
public record BossLimitValues(
        boolean shadowDifficultyAffectsDamageCap,
        boolean damageCapEnabled,
        boolean dpsCapEnabled,
        boolean rangeCapEnabled,
        float damageCap,
        float dpsCap,
        double rangeCap) {
}
