package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * 标记效果——美梦祈愿，用于触发染梦世界传送等机制。
 */
public class DreamWishEffect extends MobEffect {

    public DreamWishEffect() {
        super(MobEffectCategory.BENEFICIAL, -357146);
    }
}
