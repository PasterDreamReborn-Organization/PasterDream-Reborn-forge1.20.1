package com.pasterdream.pasterdreammod.world.item.deserttool;

import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.List;

public class ChenjingmenDesertSwordItem extends SwordItem {

    private static final int COOLDOWN_TICKS = 200; // 技能冷却时间(tick)
    private static final double SHARPNESS_DAMAGE_BONUS = 0.5; // 锋利附魔每级伤害加成
    private static final double SKILL_BASE_DAMAGE = 5.0; // 技能基础伤害
    private static final double LOST_HP_RATIO_MULTIPLIER = 2.0; // 已损生命比例系数
    private static final double LOST_HP_RATIO_OFFSET = 1.0; // 已损生命比例偏移

    public ChenjingmenDesertSwordItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (selected && entity instanceof LivingEntity living && !level.isClientSide()) {
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 0, false, false));
            living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2, 0, false, false));
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.getOrCreateTag().getBoolean("skill")) {
            stack.getOrCreateTag().putBoolean("skill", false);
            if (attacker instanceof Player player) {
                double pasterAtk = player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                        + stack.getEnchantmentLevel(Enchantments.SHARPNESS) * SHARPNESS_DAMAGE_BONUS;
                double lostHpRatio = (player.getMaxHealth() - player.getHealth()) / player.getMaxHealth();
                float bonusDamage = (float) (SKILL_BASE_DAMAGE + (lostHpRatio * LOST_HP_RATIO_MULTIPLIER + LOST_HP_RATIO_OFFSET) * pasterAtk)
                        * SkillCooldownHelper.getSkillDamageMultiplier(player);
                target.invulnerableTime = 0;
                target.hurt(player.damageSources().playerAttack(player), bonusDamage);
            }
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    ModSounds.SKILL1.get(), target.getSoundSource(), 1.5f, 1.0f);
            if (target.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, target.getX(), target.getY(), target.getZ(), 64, 1.5, 1.8, 1.5, 0.2);
                serverLevel.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(), target.getX(), target.getY(), target.getZ(), 64, 1.5, 1.8, 1.5, 0.2);
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
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 60, 0, false, false));
            SkillCooldownHelper.applySharedCooldown(player, COOLDOWN_TICKS);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.SWORD1.get(), player.getSoundSource(), 0.8f, 1.0f);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ModParticleTypes.BUFF_0_PARTICLE.get(), player.getX(), player.getY() - 0.5, player.getZ(), 20, 0.5, 1, 0.5, 1);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.chenjingmen_desert_sword.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.chenjingmen_desert_sword.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.chenjingmen_desert_sword.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.chenjingmen_desert_sword.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.chenjingmen_desert_sword.5"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.chenjingmen_desert_sword.6"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.chenjingmen_desert_sword.7"));
    }
}
