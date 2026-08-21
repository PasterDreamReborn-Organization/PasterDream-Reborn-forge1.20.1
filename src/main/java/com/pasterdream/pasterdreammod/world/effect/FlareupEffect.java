package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.fml.common.Mod;

public class FlareupEffect extends MobEffect {
    private static final String UUID = "752b5ea7-58f2-48e5-93e9-c41ee2fc6836";
    public FlareupEffect() {
        super(MobEffectCategory.BENEFICIAL, -31417);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID,
                3, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, UUID,
                0.2, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(ModAttributes.SKILL_DAMAGE_RATE.get(), UUID,
                0.3, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(ModAttributes.SKILL_COOLDOWN_RATE.get(), UUID,
                -0.20, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}

