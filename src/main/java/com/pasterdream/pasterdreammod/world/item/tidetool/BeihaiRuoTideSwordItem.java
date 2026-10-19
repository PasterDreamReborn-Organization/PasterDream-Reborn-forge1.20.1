package com.pasterdream.pasterdreammod.world.item.tidetool;

import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillLockHelper;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.entity.TideSwordProjectileEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Set;

/**
 * 『北海若』引潮 —— 三叉戟化潮汐剑。
 *
 * 潜行+右键长按 · 原版三叉戟逻辑：
 *   无「激流」= 蓄力投掷（本体投出，忠诚飞回 / 需拾回，引雷等按原版生效）；
 *   有「激流」= 仅在水/雨中可蓄力，释放触发原版激流突进（冲量 + 自旋攻击）。
 * 直接右键 · 战技「北海潮涌」：水中/雨中向前冲刺并减免 80% 伤害，蓄势下次攻击增伤，冷却 2.5s。
 * 被动 · 潮涌能量II：手持生效。
 */
public class BeihaiRuoTideSwordItem extends SwordItem {

    // ===== 普通右键 · 原版三叉戟 =====
    private static final int THROW_CHARGE_THRESHOLD = 10; // 蓄力阈值(tick)，原版三叉戟同为 10
    private static final float THROW_SPEED = 2.5F;        // 投掷初速，原版三叉戟同为 2.5
    private static final int SPIN_TICKS = 20;             // 激流突进自旋时长(tick)，原版同为 20

    // ===== 战技 · 北海潮涌（保留独立冲刺） =====
    private static final int COOLDOWN_TICKS = 50; // 技能冷却时间(tick)
    private static final double SHARPNESS_DAMAGE_BONUS = 0.5; // 锋利附魔每级伤害加成
    private static final double WATER_BONUS_BASE = 3.0; // 水中伤害基础加成
    private static final double WATER_BONUS_MULTIPLIER = 1.2; // 水中伤害攻击力倍率
    private static final double DASH_SPEED = 2.0; // 水中/雨中冲刺速度
    private static final int RESISTANCE_DURATION = 18; // 冲刺抗性持续时间(tick)
    private static final int RESISTANCE_AMPLIFIER = 3; // 冲刺抗性等级

    private static final Set<Enchantment> TRIDENT_ENCHANTMENTS = Set.of(
            Enchantments.IMPALING, Enchantments.LOYALTY, Enchantments.RIPTIDE, Enchantments.CHANNELING);

    private static final String SKILL_TAG = "skill"; // 蓄势下次攻击增伤的 NBT 标记

    public BeihaiRuoTideSwordItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return TRIDENT_ENCHANTMENTS.contains(enchantment)
                || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    // ==================== 使用动画（原版三叉戟） ====================

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    // ==================== 被动 · 潮涌能量II ====================

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (selected && entity instanceof LivingEntity living && !level.isClientSide()
                && living.getMainHandItem().is(this)) {
            living.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 20, 1, false, false));
        }
    }

    // ==================== 右键：潜行=三叉戟 / 普通=北海潮涌 ====================

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hand == InteractionHand.OFF_HAND) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        ItemStack stack = player.getItemInHand(hand);

        // 直接右键：战技「北海潮涌」（保留独立冲刺）
        if (!player.isShiftKeyDown()) {
            return performSurgeSkill(level, player, stack);
        }

        // 潜行+右键：原版三叉戟蓄力（无激流=投掷；有激流=水/雨中突进）
        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(stack);
        }
        if (EnchantmentHelper.getRiptide(stack) > 0 && !player.isInWaterOrRain()) {
            return InteractionResultHolder.fail(stack);
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) return;
        int charge = this.getUseDuration(stack) - timeLeft;
        if (charge < THROW_CHARGE_THRESHOLD) return;

        int riptide = EnchantmentHelper.getRiptide(stack);
        if (riptide > 0 && !player.isInWaterOrRain()) return;

        if (!level.isClientSide) {
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(entity.getUsedItemHand()));
            if (riptide == 0) {
                TideSwordProjectileEntity thrown = new TideSwordProjectileEntity(level, player, stack);
                thrown.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, THROW_SPEED, 1.0F);
                if (player.getAbilities().instabuild) {
                    thrown.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }
                level.addFreshEntity(thrown);
                level.playSound(null, thrown, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!player.getAbilities().instabuild) {
                    player.getInventory().removeItem(stack);
                }
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        if (riptide > 0) {
            applyRiptideDash(level, player, riptide);
        }
    }

    /** 原版激流突进：视线方向冲量 + 自旋攻击 + 贴地起跳 + 分级音效。 */
    private void applyRiptideDash(Level level, Player player, int riptide) {
        float yRot = player.getYRot();
        float xRot = player.getXRot();
        float dx = -Mth.sin(yRot * 0.017453292F) * Mth.cos(xRot * 0.017453292F);
        float dy = -Mth.sin(xRot * 0.017453292F);
        float dz = Mth.cos(yRot * 0.017453292F) * Mth.cos(xRot * 0.017453292F);
        float len = Mth.sqrt(dx * dx + dy * dy + dz * dz);
        float power = 3.0F * ((1.0F + riptide) / 4.0F);
        dx *= power / len;
        dy *= power / len;
        dz *= power / len;
        player.push(dx, dy, dz);
        player.startAutoSpinAttack(SPIN_TICKS);
        if (player.onGround()) {
            player.move(MoverType.SELF, new Vec3(0.0, 1.1999999, 0.0));
        }
        SoundEvent sound = riptide >= 3 ? SoundEvents.TRIDENT_RIPTIDE_3
                : riptide == 2 ? SoundEvents.TRIDENT_RIPTIDE_2 : SoundEvents.TRIDENT_RIPTIDE_1;
        level.playSound(null, player, sound, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    /** 战技「北海潮涌」：水中/雨中向前冲刺（减免伤害），并蓄势下次攻击增伤。 */
    private InteractionResultHolder<ItemStack> performSurgeSkill(Level level, Player player, ItemStack stack) {
        if (SkillLockHelper.isSkillLocked(player)) return InteractionResultHolder.fail(stack);
        if (player.getCooldowns().isOnCooldown(this)) return InteractionResultHolder.fail(stack);

        if (player.isInWaterRainOrBubble()) {
            if (!level.isClientSide()) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,
                        RESISTANCE_DURATION, RESISTANCE_AMPLIFIER, false, false));
            }
            Vec3 look = player.getLookAngle();
            player.setDeltaMovement(look.x * DASH_SPEED, look.y * DASH_SPEED, look.z * DASH_SPEED);
            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
            SkillCooldownHelper.applySharedCooldown(player, COOLDOWN_TICKS);
        }

        if (!level.isClientSide && !stack.getOrCreateTag().getBoolean(SKILL_TAG)) {
            stack.getOrCreateTag().putBoolean(SKILL_TAG, true);
            SkillCooldownHelper.applySharedCooldown(player, COOLDOWN_TICKS);
            level.playSound(null, player.blockPosition(), ModSounds.SWORD_SLASH.get(), SoundSource.PLAYERS, 0.8f, 1.0f);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ModParticleTypes.BUFF_0_PARTICLE.get(),
                        player.getX(), player.getY() - 0.5, player.getZ(), 20, 0.5, 1, 0.5, 1);
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    // ==================== 战技命中 · 蓄势增伤 ====================

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.getOrCreateTag().getBoolean(SKILL_TAG)) {
            stack.getOrCreateTag().putBoolean(SKILL_TAG, false);
            if (attacker instanceof Player player) {
                double pasterAtk = player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                        + stack.getEnchantmentLevel(Enchantments.SHARPNESS) * SHARPNESS_DAMAGE_BONUS;
                float bonusDamage = target.isInWaterOrBubble()
                        ? (float) (WATER_BONUS_BASE + WATER_BONUS_MULTIPLIER * pasterAtk)
                        : (float) pasterAtk;
                bonusDamage *= SkillCooldownHelper.getSkillDamageMultiplier(player);
                target.invulnerableTime = 0;
                target.hurt(player.damageSources().playerAttack(player), bonusDamage);
            }
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    ModSounds.SKILL_HIT.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundSource.NEUTRAL, 0.8f, 1.0f);
            if (target.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.BUBBLE, target.getX(), target.getY(), target.getZ(), 64, 1.5, 1.8, 1.5, 0.2);
                serverLevel.sendParticles(ParticleTypes.BUBBLE_POP, target.getX(), target.getY(), target.getZ(), 64, 1.5, 1.8, 1.5, 0.2);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    // ==================== 提示文本 ====================

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
        tooltip.add(Component.translatable("tooltip.pasterdreammod.beihai_ruo_tide_sword.9"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.beihai_ruo_tide_sword.10"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.beihai_ruo_tide_sword.11"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.beihai_ruo_tide_sword.12"));
    }
}
