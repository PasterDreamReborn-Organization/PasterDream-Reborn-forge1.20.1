package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/**
 * 风雷之佑 —— 萦风合金盔甲的套装效果标记。
 * <p>
 * 本身不做任何数值结算：拉弓/弩装填 -25%、免疫雷电、天气翻倍、
 * 雨天/雷雨减伤、弹射物弹开、缓降均由 {@code FluffyWindAlloyArmorHandler}
 * 在事件中按「满套」条件结算，该效果仅用于向玩家展示套装已激活
 * （图标使用头盔物品纹理）。
 */
public class WindThunderBoonEffect extends MobEffect implements ProtectedRemovalEffect {

    public WindThunderBoonEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x9BE0FF);
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
