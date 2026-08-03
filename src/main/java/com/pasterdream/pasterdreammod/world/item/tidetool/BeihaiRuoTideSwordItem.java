package com.pasterdream.pasterdreammod.world.item.tidetool;

import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class BeihaiRuoTideSwordItem extends SwordItem {

    private static final int COOLDOWN_TICKS = 50;

    public BeihaiRuoTideSwordItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (selected && entity instanceof LivingEntity living && !level.isClientSide()) {
            living.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 20, 0, false, false));
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.getOrCreateTag().getBoolean("skill")) {
            stack.getOrCreateTag().putBoolean("skill", false);
            if (attacker instanceof Player player) {
                double pasterAtk = player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                        + stack.getEnchantmentLevel(Enchantments.SHARPNESS) * 0.5;
                float bonusDamage = target.isInWaterOrBubble()
                        ? (float) (3 + 1.2 * pasterAtk)
                        : (float) pasterAtk;
                bonusDamage *= SkillCooldownHelper.getSkillDamageMultiplier(player);
                target.invulnerableTime = 0;
                target.hurt(player.damageSources().playerAttack(player), bonusDamage);
            }
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    ModSounds.SKILL1.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundSource.NEUTRAL, 0.8f, 1.0f);
            if (target.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.BUBBLE, target.getX(), target.getY(), target.getZ(), 64, 1.5, 1.8, 1.5, 0.2);
                serverLevel.sendParticles(ParticleTypes.BUBBLE_POP, target.getX(), target.getY(), target.getZ(), 64, 1.5, 1.8, 1.5, 0.2);
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

        if (player.isInWaterOrBubble()) {
            if (!level.isClientSide()) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 18, 3, false, false));
            }
            Vec3 look = player.getLookAngle();
            player.setDeltaMovement(look.x * 2, look.y * 2, look.z * 2);
            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
            SkillCooldownHelper.applySharedCooldown(player, COOLDOWN_TICKS);
        }

        if (!level.isClientSide && !stack.getOrCreateTag().getBoolean("skill")) {
            stack.getOrCreateTag().putBoolean("skill", true);
            SkillCooldownHelper.applySharedCooldown(player, COOLDOWN_TICKS);
            level.playSound(null, player.blockPosition(), ModSounds.SWORD1.get(), SoundSource.PLAYERS, 0.8f, 1.0f);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ModParticleTypes.BUFF_0_PARTICLE.get(), player.getX(), player.getY() - 0.5, player.getZ(), 20, 0.5, 1, 0.5, 1);
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.beihai_ruo_tide_sword.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.beihai_ruo_tide_sword.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.beihai_ruo_tide_sword.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.beihai_ruo_tide_sword.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.beihai_ruo_tide_sword.5"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.beihai_ruo_tide_sword.6"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.beihai_ruo_tide_sword.7"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.beihai_ruo_tide_sword.8"));
    }
}
