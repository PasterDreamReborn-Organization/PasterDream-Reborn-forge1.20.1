package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class DyedreamArmorBuffEffect extends MobEffect {

    public DyedreamArmorBuffEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF69B4);
        this.addAttributeModifier(Attributes.MAX_HEALTH, "d1e8a3f2-4b5c-6d7e-8f9a-0b1c2d3e4f5a",
                4.0, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 每 30 秒获得伤害吸收 I（2 颗黄心，持续 30 秒）
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
