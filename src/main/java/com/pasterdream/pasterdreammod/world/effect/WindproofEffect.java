package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * 防风 buff：存在期间免疫风之旅途维度的顺风/逆风影响，仅作标记，无属性修正。
 */
public class WindproofEffect extends MobEffect {

    public WindproofEffect() {
        super(MobEffectCategory.BENEFICIAL, -4396554);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
