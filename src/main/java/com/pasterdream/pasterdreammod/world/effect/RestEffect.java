package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class RestEffect extends MobEffect {

    private static final String UUID = "71614fba-528a-4ba8-a0f3-26c47629cc24";

    public RestEffect() {
        super(MobEffectCategory.BENEFICIAL, -3873025);
        this.addAttributeModifier(ModAttributes.SAN_VARIABILITY.get(), UUID,
                1.2, AttributeModifier.Operation.ADDITION);
    }
}
