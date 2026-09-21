package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.entity.LivingEntity;

/**
 * 「不可被外部清除」的状态效果标记接口（如暗影窥视、回避）。
 * <p>
 * 实现该接口的效果无法被外部清除逻辑移除（牛奶、totem、/effect clear、其它 mod 的清除），
 * 由 {@code ProtectedEffectRemovalMixin} 拦截。仅当通过效果自身的授权方法
 * （如 {@code allowRemoval(LivingEntity)}）显式授权后，正当的移除才能生效；
 * 效果自身计时到期走 {@code iterator.remove()} 路径，不受影响。
 */
public interface ProtectedRemovalEffect {

    /** 是否已授权本次移除（只读，不消费授权）。 */
    boolean hasRemovalAllowance(LivingEntity entity);

    /** 消费一次授权：已授权返回 true（允许移除），否则返回 false（阻止移除）。 */
    boolean consumeRemovalAllowance(LivingEntity entity);

    /**
     * 是否强制可被施加：为 true 时 {@code LivingEntity#canBeAffected} 恒返回 true，
     * 直接跳过 Forge 的 {@code MobEffectEvent.Applicable}，避免被其它模组以 DENY 阻止。
     */
    default boolean forceApplicableWhenAffected() {
        return false;
    }
}
