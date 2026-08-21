package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class LethargyEffect extends MobEffect {
    private static final String UUID = "c4cde0ad-53a3-46cc-bf26-eec88418a387";
    public LethargyEffect() {
        super(MobEffectCategory.HARMFUL, -2907486);
        this.addAttributeModifier(ModAttributes.BLINK_CD.get(), UUID, 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, UUID, -0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, UUID, -0.1, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(ModAttributes.SKILL_COOLDOWN_RATE.get(), UUID, 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
    @Override
    public String getDescriptionId() {
        return "effect.pasterdream.lethargy";
    }


    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
