package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class DyedreamArmorEffect extends MobEffect {

    private static final String HP_UUID = "d1e8a3f2-4b5c-6d7e-8f9a-0b1c2d3e4f5a";

    public DyedreamArmorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF69B4);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        var attr = entity.getAttribute(Attributes.MAX_HEALTH);
        if (attr != null && attr.getModifier(UUID.fromString(HP_UUID)) == null) {
            attr.addTransientModifier(new AttributeModifier(UUID.fromString(HP_UUID),
                    "Dyedream armor health", 4.0, AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        var attr = entity.getAttribute(Attributes.MAX_HEALTH);
        if (attr != null) attr.removeModifier(UUID.fromString(HP_UUID));
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
