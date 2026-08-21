package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class DreamHarpOfWandererEffect extends MobEffect {

    private static final String UUID_STRING = "d7b2bd54-dee3-47f6-b20b-b5556b221228";

    public DreamHarpOfWandererEffect() {
        super(MobEffectCategory.BENEFICIAL, -1934926);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        var sanAttr = entity.getAttribute(ModAttributes.SAN_VARIABILITY.get());
        if (sanAttr != null && sanAttr.getModifier(UUID.fromString(UUID_STRING)) == null) {
            sanAttr.addTransientModifier(new AttributeModifier(UUID.fromString(UUID_STRING),
                    "Dream harp SAN var", 4.8, AttributeModifier.Operation.ADDITION));
        }
        var hpAttr = entity.getAttribute(Attributes.MAX_HEALTH);
        if (hpAttr != null && hpAttr.getModifier(UUID.fromString(UUID_STRING)) == null) {
            hpAttr.addTransientModifier(new AttributeModifier(UUID.fromString(UUID_STRING),
                    "Dream harp health", 4.0, AttributeModifier.Operation.ADDITION));
        }
        var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr != null && speedAttr.getModifier(UUID.fromString(UUID_STRING)) == null) {
            speedAttr.addTransientModifier(new AttributeModifier(UUID.fromString(UUID_STRING),
                    "Dream harp speed", 0.01, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        entity.heal(8.0f);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        var sanAttr = entity.getAttribute(ModAttributes.SAN_VARIABILITY.get());
        if (sanAttr != null) sanAttr.removeModifier(UUID.fromString(UUID_STRING));
        var hpAttr = entity.getAttribute(Attributes.MAX_HEALTH);
        if (hpAttr != null) hpAttr.removeModifier(UUID.fromString(UUID_STRING));
        var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr != null) speedAttr.removeModifier(UUID.fromString(UUID_STRING));
    }
}
