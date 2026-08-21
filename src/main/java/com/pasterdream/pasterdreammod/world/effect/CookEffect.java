package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class CookEffect extends MobEffect {

    private static final String SAN_MODIFIER_UUID = "de214503-66e4-4d52-84c6-0a856b297f87";

    public CookEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xEDF1C3);
        this.addAttributeModifier(ModAttributes.SAN_VARIABILITY.get(), SAN_MODIFIER_UUID,
                1.2, AttributeModifier.Operation.ADDITION);
    }
}
