package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class TitaniumArmorEffect extends MobEffect {

    public TitaniumArmorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xE0E0E0);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.tickCount % 600 == 0) {
            entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 0,
                    true, false, false));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
