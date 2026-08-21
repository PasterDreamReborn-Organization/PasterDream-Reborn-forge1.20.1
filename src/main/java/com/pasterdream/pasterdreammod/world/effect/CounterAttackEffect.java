package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

/**
 * 反击 buff：成功闪避后获得，期间攻击力+3、战技伤害倍率+50%。
 * 攻击力加成由同时施加的 DAMAGE_BOOST (Strength I) 提供。
 */
public class CounterAttackEffect extends MobEffect {

    private static final String SKILL_DMG_UUID = "7f8c3e2d-1a5b-4f9c-8d2e-3b6a1c4f8e9d";

    public CounterAttackEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF6B35);
        this.addAttributeModifier(ModAttributes.SKILL_DAMAGE_RATE.get(), SKILL_DMG_UUID,
                0.5, AttributeModifier.Operation.ADDITION);
    }
}
