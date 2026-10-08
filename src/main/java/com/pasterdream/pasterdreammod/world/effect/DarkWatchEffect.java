package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/**
 * 黑暗守望 —— 暗影合金盔甲的套装效果标记。
 * <p>
 * 本身不做任何数值结算：+30% 灯影武器攻击、受击反击、对暗影生物 50% 最终减伤
 * 均由 {@code ShadowAlloyArmorHandler} 在事件中按「满套」条件结算，
 * 该效果仅用于向玩家展示套装已激活（图标使用头盔物品纹理）。
 */
public class DarkWatchEffect extends MobEffect implements ProtectedRemovalEffect {

    public DarkWatchEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x2B1B3A);
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
