package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

public class RapidReactionEffect extends MobEffect {
    private static final String UUID = "ecd3ac4b-03fb-46e9-afbf-6a42d706bb18";
    public RapidReactionEffect() {
        super(MobEffectCategory.BENEFICIAL, -9315102);
        this.addAttributeModifier(ModAttributes.BLINK_CD.get(), UUID,
                -0.3, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(ForgeMod.STEP_HEIGHT_ADDITION.get(), UUID,
                0.5, AttributeModifier.Operation.ADDITION);
    }
    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        var cdAttr = entity.getAttribute(ModAttributes.BLINK_CD.get());
        if (cdAttr != null) cdAttr.removeModifier(java.util.UUID.fromString(UUID));
        var stepAttr = entity.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
        if (stepAttr != null) stepAttr.removeModifier(java.util.UUID.fromString(UUID));

    }
}
