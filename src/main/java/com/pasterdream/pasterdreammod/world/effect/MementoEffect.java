package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class MementoEffect extends MobEffect {

    private static final String UUID = "d568a4ce-22ef-47b7-a351-f4a6e62bff3e";

    public MementoEffect() {
        super(MobEffectCategory.BENEFICIAL, -8970);
        this.addAttributeModifier(Attributes.LUCK, UUID, 10.0, AttributeModifier.Operation.ADDITION);
    }
}
