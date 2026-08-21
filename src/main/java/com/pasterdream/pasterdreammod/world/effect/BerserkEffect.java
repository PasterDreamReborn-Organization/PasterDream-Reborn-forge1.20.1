package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BerserkEffect extends MobEffect {

    private static final String UUID = "9e18bc3c-bff8-4262-9f44-c62143d0e060";

    public BerserkEffect() {
        super(MobEffectCategory.BENEFICIAL, -4827668);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID,
                0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, UUID,
                0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, UUID,
                0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(ModAttributes.SKILL_DAMAGE_RATE.get(), UUID,
                0.3, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(ModAttributes.BLINK_CD.get(), UUID,
                -0.3, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(ModAttributes.SKILL_COOLDOWN_RATE.get(), UUID,
                -0.3, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
