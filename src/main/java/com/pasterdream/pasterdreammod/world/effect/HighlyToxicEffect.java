package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/**
 * 剧毒 debuff —— 类似原版中毒，但不对亡灵生物豁免。
 * 每 25tick 造成 1 点魔法伤害（不受亡灵类型免疫影响）。
 */
public class HighlyToxicEffect extends MobEffect {

    public HighlyToxicEffect() {
        super(MobEffectCategory.HARMFUL, 0x3F8C1B); // 黄绿色
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.getHealth() > 1.0F) {
            // 与原版中毒相同的伤害逻辑，但不跳过亡灵
            entity.hurt(entity.damageSources().magic(), 1.0F);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // 与原版中毒相同的触发频率：amp0 每25tick，amp1 每12tick，以此类推
        int j = 25 >> amplifier;
        if (j > 0) {
            return duration % j == 0;
        }
        return true;
    }
}
