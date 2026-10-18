package com.pasterdream.pasterdreammod.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * 暴露 {@link LivingEntity#lastHurt}，供「不占用无敌帧」的伤害结算保存/还原状态。
 *
 * <p>用于草薙剧毒每跳伤害：结算前清空无敌帧计时器使本跳必定生效，
 * 结算后连同 {@code lastHurt} 一起还原，避免污染目标的无敌帧判定。
 */
@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {

    @Accessor("lastHurt")
    float pasterdream$getLastHurt();

    @Accessor("lastHurt")
    void pasterdream$setLastHurt(float value);
}
