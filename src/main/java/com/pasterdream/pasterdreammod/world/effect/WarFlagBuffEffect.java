package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.List;

/**
 * 战旗 buff：杀敌后获得，每级 +2 攻击力 + 每秒回血 + 周围 6 格范围光环共享
 * 等级加成：Ⅰ=+2攻+0.5心/秒, Ⅱ=+4攻+1.0心/秒, Ⅲ=+6攻+1.5心/秒
 */
public class WarFlagBuffEffect extends MobEffect {

    private static final String UUID = "c8f3a2d1-5b4e-4a9f-8c2d-1e7b6a3f4d8e";
    private static final double AURA_RANGE = 6.0;

    public WarFlagBuffEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x0F74AE);
        // 攻击力属性加成，amplifier 自动缩放
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID,
                0.15, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 每级 0.5 心/秒回血
        if (entity.getHealth() < entity.getMaxHealth()) {
            entity.heal(0.5F * (amplifier + 1));
        }

        // 光环：给周围 6 格范围内的玩家共享战旗 buff
        if (entity instanceof Player carrier && !entity.level().isClientSide) {
            List<Player> nearby = entity.level().getEntitiesOfClass(Player.class,
                    entity.getBoundingBox().inflate(AURA_RANGE),
                    p -> p != carrier && p.distanceToSqr(carrier) <= AURA_RANGE * AURA_RANGE);

            for (Player ally : nearby) {
                MobEffectInstance existing = ally.getEffect(this);
                // 仅在队友没有 buff、或等级更低、或剩余时间不足 5 秒时刷新
                if (existing == null
                        || existing.getAmplifier() < amplifier
                        || existing.getDuration() < 100) {
                    ally.addEffect(new MobEffectInstance(this, 100, amplifier,
                            false, false, true));
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0; // 每秒触发一次
    }
}
