package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/**
 * 标记效果——穿戴染梦套装时持续存在，供染梦工具增强系统检测。
 */
public class DyedreamUpEffect extends MobEffect implements ProtectedRemovalEffect {

    public DyedreamUpEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x80B2);
    }

    @Override
    public boolean hasRemovalAllowance(LivingEntity entity) {
        return false;
    }

    @Override
    public boolean consumeRemovalAllowance(LivingEntity entity) {
        return false;
    }

    @Override
    public boolean forceApplicableWhenAffected() {
        return true;
    }
}
