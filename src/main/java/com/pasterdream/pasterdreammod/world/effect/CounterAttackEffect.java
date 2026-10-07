package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * 反击 buff：成功闪避后获得，下一次造成的伤害在减伤结算后 +50%（攻击力+3 由同时施加的 DAMAGE_BOOST 提供）。
 * 触发与移除逻辑见 PlayerEvents#onLivingDamage。
 */
public class CounterAttackEffect extends MobEffect {

    public CounterAttackEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF6B35);
    }
}
