package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

/**
 * 暗影窥视 —— 使玩家成为暗影的「窥视」目标，每 tick 触发一次暗影入侵。
 * 效果无法被牛奶解除，由暗影入侵完成或击败亚伦柯斯之触时移除。
 */
public class ShadowSpyonBuffEffect extends MobEffect {

    public ShadowSpyonBuffEffect() {
        super(MobEffectCategory.HARMFUL, -13421773);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        ShadowIntrudeHandler.tick(entity);
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
