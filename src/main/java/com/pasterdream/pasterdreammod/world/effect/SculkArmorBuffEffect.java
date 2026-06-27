package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class SculkArmorBuffEffect extends MobEffect {

    public SculkArmorBuffEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x1A4D5E);
        this.addAttributeModifier(Attributes.MAX_HEALTH, "4a5d1e2c-3f6b-4a8d-9c7e-2b3f5d6a8e1c",
                4.0, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 身处深层时获得抗性提升
        if (entity.getY() < 0) {
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 25, 0,
                    true, false, true));
        }
        // 受到黑暗效果时：移除黑暗，给予加速和抗性
        if (entity.hasEffect(MobEffects.DARKNESS)) {
            entity.removeEffect(MobEffects.DARKNESS);
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1,
                    true, false, true));
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 1,
                    true, false, true));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // 每 tick 检查
    }
}
