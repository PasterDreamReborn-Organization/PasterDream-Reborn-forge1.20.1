package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * 风行者 buff：效果持续期间无需鞘翅即可进行鞘翅飞行。
 * 飞行判定由 PlayerElytraFlyMixin / LivingEntityElytraFlyMixin / LocalPlayerElytraFlyMixin 实现
 */
public class WindRunnerEffect extends MobEffect {

    public WindRunnerEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x87CEEB);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}