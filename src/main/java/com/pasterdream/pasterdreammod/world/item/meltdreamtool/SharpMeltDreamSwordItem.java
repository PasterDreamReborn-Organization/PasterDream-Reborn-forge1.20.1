package com.pasterdream.pasterdreammod.world.item.meltdreamtool;

import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillLockHelper;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 极锋染梦合金剑 —— 右键蓄力特殊攻击，命中时击飞敌人并造成额外伤害。
 * <p>
 * 镶嵌：融梦水晶碎片<br>
 * 战技：凌空斩 —— 冷却 5 秒，拥有染梦守护缩短为 3 秒。
 */
public class SharpMeltDreamSwordItem extends SwordItem {

    private static final String TAG_COOLDOWN = "SharpCooldown";
    private static final String TAG_CHARGED = "SharpCharged";
    private static final int COOLDOWN_WITHOUT_BUFF = 100; // 无染梦守护时冷却(tick)
    private static final int COOLDOWN_WITH_BUFF = 60; // 有染梦守护时冷却(tick)
    private static final float BASE_SKILL_ATTACK = 4.0f; // 技能基础攻击力
    private static final float SKILL_DAMAGE_OFFSET = 2.0f; // 技能附加伤害
    private static final float SKILL_DAMAGE_MULTIPLIER = 1.5f; // 技能攻击力倍率
    private static final double KNOCKBACK_Y = 0.8; // 击飞Y速度
    private static final float HIT_SOUND_VOLUME = 1.5f; // 命中音效音量

    public SharpMeltDreamSwordItem(Tier tier, Properties properties) {
        super(tier, 4, -2.4f, properties.fireResistant());
    }

    /**
     * 右键蓄力：冷却就绪时标记下次攻击为特殊攻击，播放音效。
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hand == InteractionHand.OFF_HAND) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        ItemStack stack = player.getItemInHand(hand);
        if (SkillLockHelper.isSkillLocked(player)) return InteractionResultHolder.fail(stack);
        if (!level.isClientSide) {
            long now = level.getGameTime();
            long lastUse = stack.getOrCreateTag().getLong(TAG_COOLDOWN);
            // 染梦守护 buff 缩短冷却：3 秒 vs 5 秒
            int cooldownTicks = player.hasEffect(ModEffects.DYEDREAM_ARMOR_BUFF.get()) ? COOLDOWN_WITH_BUFF : COOLDOWN_WITHOUT_BUFF;
            if (now - lastUse >= cooldownTicks) {
                stack.getOrCreateTag().putLong(TAG_COOLDOWN, now);
                stack.getOrCreateTag().putBoolean(TAG_CHARGED, true);
                SkillCooldownHelper.applySharedCooldown(player, cooldownTicks);
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        ModSounds.SWORD1.get(), player.getSoundSource(), 1.0f, 1.0f);
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ModParticleTypes.BUFF_0_PARTICLE.get(), player.getX(), player.getY() - 0.5, player.getZ(), 20, 0.5, 1, 0.5, 1);
                }
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    /**
     * 命中时若已蓄力 → 凌空斩：击飞敌人。
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.getOrCreateTag().getBoolean(TAG_CHARGED)) {
            stack.getOrCreateTag().putBoolean(TAG_CHARGED, false);
            if (attacker instanceof Player player) {
                float baseAttack = BASE_SKILL_ATTACK + this.getTier().getAttackDamageBonus();
                float extraDamage = (SKILL_DAMAGE_OFFSET + baseAttack * SKILL_DAMAGE_MULTIPLIER)
                        * SkillCooldownHelper.getSkillDamageMultiplier(player);
                target.invulnerableTime = 0;
                target.hurt(player.damageSources().playerAttack(player), extraDamage);
            }
            target.setDeltaMovement(target.getDeltaMovement().add(0, KNOCKBACK_Y, 0));
            target.hurtMarked = true;
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    ModSounds.SKILL1.get(), target.getSoundSource(), HIT_SOUND_VOLUME, 1.0f);
            // 脚下粒子爆发：40 个，位置 (x, y+0.3, z)，速度 (0.05, 0.3, 0.05)，速度随机偏移 0.4
            if (target.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ModParticleTypes.SHARP_SWORD_SLASH.get(),
                        target.getX(), target.getY() + 0.3, target.getZ(), 40,
                        0.15, 0.6, 0.15, 0.6);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        MeltDreamToolHelper.onInventoryTick(stack, level, entity, slot, selected);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.sharp_melt_dream_sword.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.sharp_melt_dream_sword.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.sharp_melt_dream_sword.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.sharp_melt_dream_sword.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.sharp_melt_dream_sword.5"));
        MeltDreamToolHelper.appendHoverText(stack, level, tooltip, flag);
    }
}
