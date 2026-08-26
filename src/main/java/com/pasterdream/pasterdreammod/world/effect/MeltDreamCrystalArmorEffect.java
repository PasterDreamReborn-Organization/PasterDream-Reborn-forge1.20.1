package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class MeltDreamCrystalArmorEffect extends MobEffect {

    public MeltDreamCrystalArmorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xD98EFF);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 每 10 秒（200 tick）给予一次生命恢复 II
        if (entity.tickCount % 200 == 0) {
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 240, 1,
                    true, false, false));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
