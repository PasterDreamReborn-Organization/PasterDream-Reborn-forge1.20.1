package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class CheerUpEffect extends MobEffect {

    private static final String UUID = "b57e603c-ae6f-44f3-b51e-b4e5b89171c9";

    public CheerUpEffect() {
        super(MobEffectCategory.BENEFICIAL, -33158);
        this.addAttributeModifier(ModAttributes.BLINK_CD.get(), UUID, -0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, UUID, 0.05, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, UUID, 0.05, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(ModAttributes.SKILL_COOLDOWN_RATE.get(), UUID, -0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
