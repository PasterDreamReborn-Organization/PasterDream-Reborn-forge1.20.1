package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * 回避无敌帧 buff，每级可抵挡一次伤害。
 */
public class EvasionEffect extends MobEffect {

    public EvasionEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xD5F5E3);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
