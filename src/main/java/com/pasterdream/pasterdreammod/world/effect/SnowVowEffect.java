package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class SnowVowEffect extends MobEffect {
    private static final UUID LUCK_UUID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");

    public SnowVowEffect() {
        super(MobEffectCategory.BENEFICIAL, -7684397);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        var attr = entity.getAttribute(Attributes.LUCK);
        if (attr != null && attr.getModifier(LUCK_UUID) == null) {
            attr.addPermanentModifier(new AttributeModifier(LUCK_UUID, "Snow vow luck", 3.0, AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.clearFire();
        entity.setTicksFrozen(0);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        var attr = entity.getAttribute(Attributes.LUCK);
        if (attr != null) {
            attr.removeModifier(LUCK_UUID);
        }
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
