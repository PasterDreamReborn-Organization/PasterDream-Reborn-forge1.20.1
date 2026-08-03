package com.pasterdream.pasterdreammod.world.item.grasstool;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.List;

public class MurakumoKusanagiItem extends SwordItem {

    private static final int COOLDOWN_TICKS = 80;
    private final float baseAttackDamage;

    public MurakumoKusanagiItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
        this.baseAttackDamage = damage + tier.getAttackDamageBonus();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.getOrCreateTag().getBoolean("skill")) {
            stack.getOrCreateTag().putBoolean("skill", false);
            if (attacker instanceof Player player) {
                int sharpnessLevel = stack.getEnchantmentLevel(Enchantments.SHARPNESS);
                float extraDamage = (7.0f + sharpnessLevel * this.baseAttackDamage / 2.0f)
                        * SkillCooldownHelper.getSkillDamageMultiplier(player);
                target.invulnerableTime = 0;
                target.hurt(player.damageSources().playerAttack(player), extraDamage);
            }
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    ModSounds.SKILL1.get(), target.getSoundSource(), 1.5f, 1.0f);
            if (target.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(), target.getX(), target.getY(), target.getZ(), 64, 1.5, 1.8, 1.5, 0.2);
                serverLevel.sendParticles(ModParticleTypes.SPORE_PARTICLE.get(), target.getX(), target.getY(), target.getZ(), 64, 1.5, 1.8, 1.5, 0.2);
            }
        } else {
            if (!target.level().isClientSide()) {
                target.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hand == InteractionHand.OFF_HAND) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && !stack.getOrCreateTag().getBoolean("skill")) {
            stack.getOrCreateTag().putBoolean("skill", true);
            SkillCooldownHelper.applySharedCooldown(player, COOLDOWN_TICKS);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.SWORD1.get(), SoundSource.PLAYERS, 0.8f, 1.0f);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ModParticleTypes.BUFF_0_PARTICLE.get(), player.getX(), player.getY() - 0.5, player.getZ(), 20, 0.5, 1, 0.5, 1);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.murakumo_kusanagi.1", Config.TheNumberofKillEnemytoEvolve));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.murakumo_kusanagi.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.murakumo_kusanagi.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.murakumo_kusanagi.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.murakumo_kusanagi.5"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.murakumo_kusanagi.6"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.murakumo_kusanagi.7"));
    }
}
