package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * 压抑 —— 降低SAN变化率，不可解除。
 * 由暗影符文塔施加。
 */
public class OppressionEffect extends MobEffect {
    private static final String UUID = "cd91f5ed-1634-42e7-8084-54c2108ea28b";

    public OppressionEffect() {
        super(MobEffectCategory.HARMFUL, 0x0);
        this.addAttributeModifier(ModAttributes.SAN_VARIABILITY.get(), UUID, -9.6, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }
}
