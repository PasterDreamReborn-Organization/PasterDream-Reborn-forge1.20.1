package com.pasterdream.pasterdreammod.world.item.hellfiretool;

import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.entity.FoxFireEntity;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;

/**
 * 『融骸』狱炎剑 —— 近战命中自动引发范围熔岩爆炸，爆炸亦会施加被动引燃。
 *
 * 战技 · 融骸斩击：冷却就绪时，近战命中以命中点为中心 3 格范围（横扫之刃每级 +1，上限 6），
 *                 对其中敌人造成攻击力 50% 的熔岩伤害，冷却 2s；
 *                 爆炸伤害受亡灵杀手/节肢杀手按生物类型加成。
 * 被动 · 引燃：命中点燃目标 4 秒，已燃烧目标延长 3 秒（爆炸波及的目标同样生效）；
 *              每级火焰附加额外增加 2 秒燃烧时长。
 */
public class InfernoSwordItem extends SwordItem {

    private static final int SKILL_COOLDOWN_TICKS = 40; // 战技冷却 2s
    private static final double AOE_RADIUS = 3.0; // 爆炸基础半径(格)
    private static final double AOE_RADIUS_MAX = 6.0; // 爆炸半径上限(格)
    private static final double AOE_RADIUS_PER_SWEEPING = 1.0; // 每级横扫之刃增加的爆炸半径(格)
    private static final float AOE_DAMAGE_RATIO = 0.5f; // 攻击力转化为范围熔岩伤害的比例
    private static final int PASSIVE_FIRE_TICKS = 60; // 被动引燃延长 tick 数(3s)
    private static final int PASSIVE_IGNITE_SECONDS = 4; // 被动引燃秒数
    private static final int FIRE_ASPECT_BONUS_SECONDS = 2; // 每级火焰附加增加的被动燃烧时长(秒)
    private static final float SMITE_BANE_DAMAGE = 2.5f; // 亡灵杀手/节肢杀手每级对爆炸的额外伤害
    private static final float HIT_SOUND_VOLUME = 1.5f; // 命中音效音量
    private static final float DRAGON_SOUND_VOLUME = 1.0f; // 龙息爆炸音效音量
    private static final int BASE_PARTICLE_COUNT = 64; // 基础爆炸粒子数量
    private static final double BASE_PARTICLE_SPREAD = 1.5; // 基础爆炸粒子散布(格)

    public InfernoSwordItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties.fireResistant());
    }

    /** 命中：战技冷却就绪则自动引发范围熔岩爆炸。被动引燃于攻击命中前由 AttackEntityEvent 施加，确保致死的点燃目标掉落熟食。 */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            triggerInfernoBlast(player, stack, target);
            SkillCooldownHelper.applySharedCooldown(player, SKILL_COOLDOWN_TICKS);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    /** 范围熔岩爆炸：以命中点为中心，对范围内敌人造成攻击力 50% 的熔岩伤害并施加引燃。横扫之刃每级扩大 1 格半径。 */
    private void triggerInfernoBlast(Player player, ItemStack stack, LivingEntity center) {
        Level level = center.level();
        double radius = Math.min(AOE_RADIUS + stack.getEnchantmentLevel(Enchantments.SWEEPING_EDGE) * AOE_RADIUS_PER_SWEEPING,
                AOE_RADIUS_MAX);
        level.playSound(null, center.getX(), center.getY(), center.getZ(),
                ModSounds.SKILL_HIT_HEAVY.get(), SoundSource.NEUTRAL, HIT_SOUND_VOLUME, 1.0f);
        level.playSound(null, center.getX(), center.getY(), center.getZ(),
                Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.parse("entity.dragon_fireball.explode"))),
                SoundSource.NEUTRAL, DRAGON_SOUND_VOLUME, 1.0f);
        if (level instanceof ServerLevel serverLevel) {
            double spread = radius * (BASE_PARTICLE_SPREAD / AOE_RADIUS);
            int count = (int) Math.round(BASE_PARTICLE_COUNT * radius / AOE_RADIUS);
            serverLevel.sendParticles(ParticleTypes.LAVA, center.getX(), center.getY(), center.getZ(),
                    count, spread, spread * 1.2, spread, 0.5);
        }

        int fireAspect = stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT);
        int smite = stack.getEnchantmentLevel(Enchantments.SMITE);
        int bane = stack.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS);
        float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float damage = atk * AOE_DAMAGE_RATIO * SkillCooldownHelper.getSkillDamageMultiplier(player);
        Holder<DamageType> lavaHolder = level.registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(DamageTypes.LAVA);

        AABB area = center.getBoundingBox().inflate(radius);
        List<LivingEntity> affected = level.getEntitiesOfClass(LivingEntity.class, area,
                e -> e != player && e.isAlive() && !(e instanceof ArmorStand)
                        && !isOwnedMinion(e, player)
                        && e.distanceToSqr(center) <= radius * radius);
        for (LivingEntity entity : affected) {
            float dmg = damage;
            MobType mobType = entity.getMobType();
            if (smite > 0 && mobType == MobType.UNDEAD) dmg += smite * SMITE_BANE_DAMAGE;
            if (bane > 0 && mobType == MobType.ARTHROPOD) dmg += bane * SMITE_BANE_DAMAGE;
            if (entity != center) {
                applyIgnite(entity, fireAspect);
            }
            entity.invulnerableTime = 0;
            entity.hurt(new DamageSource(lavaHolder, player, player), dmg);
        }
    }

    /** 被动引燃：未燃烧则点燃 4s，已燃烧则延长 3s；每级火焰附加额外增加 2s。 */
    private static void applyIgnite(LivingEntity target, int fireAspectLevel) {
        int bonusSeconds = fireAspectLevel * FIRE_ASPECT_BONUS_SECONDS;
        if (target.isOnFire()) {
            target.setRemainingFireTicks(target.getRemainingFireTicks() + PASSIVE_FIRE_TICKS + bonusSeconds * 20);
        } else {
            target.setSecondsOnFire(PASSIVE_IGNITE_SECONDS + bonusSeconds);
        }
    }

    /** 是否为玩家自己的仆从/召唤物/同队盟友（爆炸不应波及）。 */
    private static boolean isOwnedMinion(LivingEntity e, Player owner) {
        if (e instanceof OwnableEntity ownable) {
            return ownable.getOwner() == owner;
        }
        if (e instanceof FoxFireEntity fe) {
            return fe.resolveOwner() == owner;
        }
        return e.isAlliedTo(owner);
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
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.10"));
    }

    /** 被动 · 引燃：在近战命中结算伤害前点燃目标，保证一击致死也能掉落熟食。 */
    @Mod.EventBusSubscriber(modid = "pasterdream", bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class InfernoPassiveHandler {
        @SubscribeEvent
        public static void onAttackEntity(AttackEntityEvent event) {
            Player player = event.getEntity();
            if (player.level().isClientSide()) return;
            ItemStack stack = player.getMainHandItem();
            if (!(stack.getItem() instanceof InfernoSwordItem)) return;
            if (!(event.getTarget() instanceof LivingEntity target)) return;
            applyIgnite(target, stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT));
        }
    }
}
