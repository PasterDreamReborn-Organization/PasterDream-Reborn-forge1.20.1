package com.pasterdream.pasterdreammod.world.item.hellfiretool;

import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 『融骸』狱炎剑 —— 右键蓄力融骸斩击，伤害转为熔岩类型，清除目标的引燃造成额外伤害。
 */
public class InfernoSwordItem extends SwordItem {

    private static final String TAG_SKILL = "InfernoSkill";

    public InfernoSwordItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties.fireResistant());
    }

    /** 右键蓄力：冷却 10 秒。 */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && !stack.getOrCreateTag().getBoolean(TAG_SKILL)) {
            stack.getOrCreateTag().putBoolean(TAG_SKILL, true);
            player.getCooldowns().addCooldown(this, 200);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.SHARP_MELT_DREAM_SWORD_USE.get(), SoundSource.PLAYERS, 0.8f, 1.0f);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    /** 命中：蓄力 → 融骸斩击；未蓄力 → 引燃。 */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.getOrCreateTag().getBoolean(TAG_SKILL)) {
            stack.getOrCreateTag().putBoolean(TAG_SKILL, false);
            // 龙息爆炸 + skill2 音效
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    ModSounds.INFERNO_SWORD_SKILL.get(), SoundSource.NEUTRAL, 1.5f, 1.0f);
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 1.0f, 1.0f);
            // 熔岩粒子
            if (target.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.LAVA, target.getX(), target.getY(), target.getZ(),
                        64, 1.5, 1.8, 1.5, 0.5);
            }
            // 熔岩伤害：2 + 攻击力 + 已燃烧 tick * 0.03
            float atk = (float) attacker.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE);
            float extraDamage = 2 + atk + target.getRemainingFireTicks() * 0.03f;
            target.hurt(new DamageSource(target.level().registryAccess()
                    .registryOrThrow(net.minecraft.core.registries.Registries.DAMAGE_TYPE)
                    .getHolderOrThrow(DamageTypes.LAVA)), extraDamage);
            // 燃烧超过 10 tick → 缓慢
            if (target.getRemainingFireTicks() >= 10) {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1,
                        false, false, true));
            }
            target.clearFire();
        } else {
            // 被动引燃（同狱炎剑）
            if (target.isOnFire()) {
                target.setRemainingFireTicks(target.getRemainingFireTicks() + 60);
            } else {
                target.setSecondsOnFire(4);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.5"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.6"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.7"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.8"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.9"));
    }
}
