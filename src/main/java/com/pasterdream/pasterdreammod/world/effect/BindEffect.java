package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

/**
 * 束缚 —— 大幅降低移动速度，使目标无法移动。
 * 由白厄剑雨命中施加。
 */
public class BindEffect extends MobEffect {

    private static final UUID BIND_UUID = UUID.fromString("fae1cce0-b4c7-4848-b428-62c4a21ba6a3");

    public BindEffect() {
        super(MobEffectCategory.HARMFUL, -6180680);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, BIND_UUID.toString(), -1, AttributeModifier.Operation.ADDITION);
    }
}
