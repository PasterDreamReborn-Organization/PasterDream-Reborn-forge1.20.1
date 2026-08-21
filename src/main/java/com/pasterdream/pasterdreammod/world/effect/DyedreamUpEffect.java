package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * 标记效果——穿戴染梦套装时持续存在，供染梦工具增强系统检测。
 */
public class DyedreamUpEffect extends MobEffect {

    public DyedreamUpEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x80B2);
    }
}
