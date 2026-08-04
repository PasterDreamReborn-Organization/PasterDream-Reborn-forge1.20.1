package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

/**
 * 行动抑制 —— 禁用跳跃和飞行，不可解除。
 * 由亚伦柯斯之触血锁机制施加。
 */
public class RestrainmoveBlockBuffEffect extends MobEffect {
    public RestrainmoveBlockBuffEffect() {
        super(MobEffectCategory.BENEFICIAL, -7469562);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.removeEffect(MobEffects.JUMP);
        if (entity instanceof Player player) {
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
        }
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
