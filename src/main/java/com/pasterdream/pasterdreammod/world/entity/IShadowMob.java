package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.entity.LivingEntity;

/**
 * 暗影生物标记接口，提供统一的技能释放判定。
 * 沉默状态下（{@link ModEffects#SHADOW_SILENCE_BUFF}）返回 false。
 */
public interface IShadowMob {

    default boolean canUseSkill() {
        return !((LivingEntity) this).hasEffect(ModEffects.SHADOW_SILENCE_BUFF.get());
    }
}
