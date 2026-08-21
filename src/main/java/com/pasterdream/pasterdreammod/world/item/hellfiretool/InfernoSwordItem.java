package com.pasterdream.pasterdreammod.world.item.hellfiretool;

import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillLockHelper;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.resources.ResourceLocation;
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
import java.util.Objects;

/**
 * 『融骸』狱炎剑 —— 右键蓄力融骸斩击，伤害转为熔岩类型，清除目标的引燃造成额外伤害。
 */
public class InfernoSwordItem extends SwordItem {

    private static final String TAG_SKILL = "InfernoSkill";
    private static final int SKILL_COOLDOWN_TICKS = 200; // 技能冷却时间(tick)
    private static final float SKILL_BASE_DAMAGE = 2.0f; // 融骸斩基础附加伤害
    private static final float FIRE_TICK_DAMAGE_MULTIPLIER = 0.03f; // 燃烧tick转伤害系数
    private static final int SLOW_FIRE_THRESHOLD = 10; // 触发缓慢的燃烧tick阈值
    private static final int PASSIVE_FIRE_TICKS = 60; // 被动引燃增加tick数
    private static final int PASSIVE_IGNITE_SECONDS = 4; // 被动引燃秒数
    private static final float SKILL_SOUND_VOLUME = 0.8f; // 技能蓄力音效音量
    private static final float HIT_SOUND_VOLUME = 1.5f; // 命中音效音量
    private static final float DRAGON_SOUND_VOLUME = 1.0f; // 龙息音效音量

    public InfernoSwordItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties.fireResistant());
    }

    /** 右键蓄力：冷却 10 秒。 */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hand == InteractionHand.OFF_HAND) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        ItemStack stack = player.getItemInHand(hand);
        if (SkillLockHelper.isSkillLocked(player)) return InteractionResultHolder.fail(stack);
        if (!level.isClientSide && !stack.getOrCreateTag().getBoolean(TAG_SKILL)) {
            stack.getOrCreateTag().putBoolean(TAG_SKILL, true);
            SkillCooldownHelper.applySharedCooldown(player, SKILL_COOLDOWN_TICKS);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.SWORD_SLASH.get(), SoundSource.PLAYERS, SKILL_SOUND_VOLUME, 1.0f);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ModParticleTypes.BUFF_0_PARTICLE.get(), player.getX(), player.getY() - 0.5, player.getZ(), 20, 0.5, 1, 0.5, 1);
            }
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
                    ModSounds.SKILL_HIT_HEAVY.get(), SoundSource.NEUTRAL, HIT_SOUND_VOLUME, 1.0f);
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.parse("entity.dragon_fireball.explode"))), SoundSource.NEUTRAL, DRAGON_SOUND_VOLUME, 1.0f);
            // 熔岩粒子
            if (target.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.LAVA, target.getX(), target.getY(), target.getZ(),
                        64, 1.5, 1.8, 1.5, 0.5);
            }
            // 熔岩伤害：2 + 攻击力 + 已燃烧 tick * 0.03
            float atk = (float) attacker.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE);
            float mult = attacker instanceof Player pl ? SkillCooldownHelper.getSkillDamageMultiplier(pl) : 1.0f;
            float extraDamage = (SKILL_BASE_DAMAGE + atk + target.getRemainingFireTicks() * FIRE_TICK_DAMAGE_MULTIPLIER) * mult;
            target.invulnerableTime = 0;
            target.hurt(new DamageSource(target.level().registryAccess()
                    .registryOrThrow(net.minecraft.core.registries.Registries.DAMAGE_TYPE)
                    .getHolderOrThrow(DamageTypes.LAVA)), extraDamage);
            // 燃烧超过 10 tick → 缓慢
            if (target.getRemainingFireTicks() >= SLOW_FIRE_THRESHOLD) {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1,
                        false, false, true));
            }
            target.clearFire();
        } else {
            // 被动引燃（同狱炎剑）
            if (target.isOnFire()) {
                target.setRemainingFireTicks(target.getRemainingFireTicks() + PASSIVE_FIRE_TICKS);
            } else {
                target.setSecondsOnFire(PASSIVE_IGNITE_SECONDS);
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
