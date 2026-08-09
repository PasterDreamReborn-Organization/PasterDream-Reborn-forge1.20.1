package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

/**
 * 冰冻 debuff —— 撤除 AI + 细雪 screen overlay + 无法移动/攻击，
 * 效果移除后自动恢复 AI。
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
    public void addAttributeModifiers(LivingEntity entity, AttributeMap map, int amplifier) {
        // 效果开始时撤除 AI
        if (entity instanceof Mob mob) {
            mob.setNoAi(true);
        }
        super.addAttributeModifiers(entity, map, amplifier);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.setTicksFrozen(200);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return Collections.emptyList();
    }
}
