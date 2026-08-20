package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class MeltDreamCrystalArmorBuffEffect extends MobEffect {

    public MeltDreamCrystalArmorBuffEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xD98EFF);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 生命恢复 II
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 1,
                true, false, false));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
