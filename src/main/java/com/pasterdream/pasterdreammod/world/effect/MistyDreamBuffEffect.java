package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * 迷梦 buff：标记效果，由奇异炖菜给予，用于在主世界高空显示进入风之旅途的云霞进度。
 * 实际传送由 WindDirectionHandler 的保底判定（Y>310）触发。
 */
public class MistyDreamBuffEffect extends MobEffect {

    public MistyDreamBuffEffect() {
        super(MobEffectCategory.BENEFICIAL, -3438857);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
