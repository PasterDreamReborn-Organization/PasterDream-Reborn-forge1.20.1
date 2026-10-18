package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.mixin.LivingEntityAccessor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/**
 * 剧毒 debuff —— 类似原版中毒，但不对亡灵生物豁免。
 * 每 25tick 造成 1 点魔法伤害（不受亡灵类型免疫影响）。
 *
 * <p>该伤害不占用目标的无敌帧：结算前清空无敌帧计时器使本跳必定生效，
 * 结算后还原无敌帧计时器与上次受击伤害，故既不会被其它来源的无敌帧吞掉，
 * 也不会影响目标后续受到的其它伤害。
 */
public class HighlyToxicEffect extends MobEffect {

    public HighlyToxicEffect() {
        super(MobEffectCategory.HARMFUL, 0x3F8C1B); // 黄绿色
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.getHealth() > 1.0F) {
            applyIneffaceableDamage(entity);
        }
    }

    /** 结算 1 点魔法伤害，且不读取、不占用目标的无敌帧。 */
    private static void applyIneffaceableDamage(LivingEntity entity) {
        LivingEntityAccessor accessor = (LivingEntityAccessor) entity;
        int invulnerableTime = entity.invulnerableTime;
        float lastHurt = accessor.pasterdream$getLastHurt();
        entity.invulnerableTime = 0;
        entity.hurt(entity.damageSources().magic(), 1.0F);
        entity.invulnerableTime = invulnerableTime;
        accessor.pasterdream$setLastHurt(lastHurt);
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
