package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

/**
 * 冰冻 debuff / 完全冻结视觉效果 —— 持续施加细雪 screen overlay。
 */
public class FrozenBuffEffect extends MobEffect {

    private static final String UUID = "6d21bc52-22c3-4ebf-bcf6-5ea4e806c379";

    public FrozenBuffEffect() {
        super(MobEffectCategory.HARMFUL, -4657930);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID,
                -1, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, UUID,
                -1, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 每 tick 维持冰冻状态，触发原版细雪冰霜 screen overlay
        entity.setTicksFrozen(200);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // 每 tick 都刷新
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return Collections.emptyList(); // 牛奶无法清除
    }
}
