package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TranceEffect extends MobEffect {
    private static final String UUID = "d9839357-ed78-40fc-8c2f-5e877116d9f9";
    public TranceEffect() {
        super(MobEffectCategory.HARMFUL, -4427155);
        this.addAttributeModifier(ModAttributes.BLINK_CD.get(), UUID, 1, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, UUID, -0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, UUID, -0.2, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID, -1, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(ModAttributes.SKILL_COOLDOWN_RATE.get(), UUID, 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
    @Override
    public String getDescriptionId() {
        return "effect.pasterdream.trance";
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
