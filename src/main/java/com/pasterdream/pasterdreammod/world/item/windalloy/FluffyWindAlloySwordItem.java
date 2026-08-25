package com.pasterdream.pasterdreammod.world.item.windalloy;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillLockHelper;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.entity.FoxFireEntity;
import com.pasterdream.pasterdreammod.world.entity.WindAlloyLightningEntity;
import com.pasterdream.pasterdreammod.world.item.IndestructibleItemEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * 萦风合金剑 —— 风雷双模式战技 + 通用被动。
 *
 * 风 · 疾风突进：右键向准心突进，对沿途敌人造成 10×移动速度×攻击力 伤害，冷却 2s。
 * 雷 · 萦风引雷：右键向目标头顶降下 5 道追踪落雷（间隔 10tick），每道造成 攻击力×1.5
 *               的 4×3×4 范围雷电伤害，消耗融梦能量 1.0，冷却 5s。
 * 被动 · 雷随疾风：移动速度越高伤害越高，伤害=(1+移动速度)×攻击力，并附带攻击力×0.1 雷电伤害。
 *
 * 说明：伤害公式中的"移动速度"= max(移动速度属性, 进行攻击时的瞬时移动速度)。
 */
public class FluffyWindAlloySwordItem extends SwordItem {

    private static final String MODE_TAG = "pasterdream:fluffy_wind_alloy_mode";
    private static final String MODE_WIND = "wind";
    private static final String MODE_THUNDER = "thunder";

    /** 施加战技/被动自算伤害期间置位，防止被动对自身伤害再次套用形成递归 */
    private static final String APPLYING_TAG = "pasterdream:fluffy_wind_alloy_applying";

    // ===== 风 · 疾风突进 =====
    private static final int WIND_COOLDOWN_TICKS = 40;            // 冷却 2s
    private static final int WIND_FLIGHT_COOLDOWN_TICKS = 200;    // 鞘翅突进冷却 10s
    private static final double WIND_DASH_DISTANCE = 5.0;         // 突进距离(格)
    private static final double WIND_DASH_SPEED = 1.2;            // 突进速度(方块/tick)
    private static final double WIND_FLIGHT_DASH_SPEED = 2.5;     // 鞘翅飞行突进速度(方块/tick, 三叉戟式)
    private static final double WIND_FLIGHT_DASH_DISTANCE = 12.0; // 鞘翅飞行突进横扫距离(格)
    private static final int WIND_FLIGHT_SPIN_TICKS = 20;         // 飞行突进自旋时长(tick)
    private static final double WIND_DAMAGE_SPEED_FACTOR = 2.5;   // 2.5×移动速度
    private static final double WIND_FLIGHT_DAMAGE_FACTOR = 1.0;  // 鞘翅突进伤害倍率（不额外折扣）
    private static final double WIND_SWEEP_XZ = 1.2;              // 沿途横扫范围(XZ)
    private static final double WIND_SWEEP_Y = 1.5;               // 沿途横扫范围(Y)
    private static final double WIND_KNOCKBACK = 0.8;             // 沿途击退系数
    private static final double WIND_SWEEP_ENCHANT_BONUS = 0.5;   // 横扫之刃每级横扫范围加成
    private static final double WIND_KNOCKBACK_ENCHANT_BONUS = 0.4; // 击退附魔每级击退加成

    // ===== 雷 · 萦风引雷 =====
    private static final int THUNDER_COOLDOWN_TICKS = 100;        // 冷却 5s
    private static final double THUNDER_ENERGY_COST = 1.0;        // 融梦能量消耗
    private static final double THUNDER_DAMAGE_MULTIPLIER = 1.0;  // 攻击力×1.0
    private static final int THUNDER_TARGET_RANGE = 24;           // 索敌/落雷距离

    // ===== 被动 · 雷随疾风 =====
    private static final double PASSIVE_LIGHTNING_RATIO = 0.1;    // 附带攻击力×0.1 雷电伤害

    // ===== 附魔加成（战技吃附魔，参考泰拉剑气） =====
    private static final double SHARPNESS_DAMAGE_BONUS = 0.5;     // 锋利每级攻击力加成
    private static final float SMITE_BANE_DAMAGE = 2.5f;          // 亡灵杀手/节肢杀手每级额外伤害
    private static final int FIRE_ASPECT_TICK_MULTIPLIER = 4;     // 火焰附加秒数乘数

    public FluffyWindAlloySwordItem(Tier tier, Properties properties) {
        super(tier, 4, -1.4f, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hand == InteractionHand.OFF_HAND) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        ItemStack stack = player.getItemInHand(hand);
        if (SkillLockHelper.isSkillLocked(player)) return InteractionResultHolder.fail(stack);

        if (player.isShiftKeyDown()) {
            if (!level.isClientSide() && player instanceof ServerPlayer sp) {
                toggleMode(sp, stack);
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }

        if (!level.isClientSide() && player instanceof ServerPlayer sp) {
            if (isThunderMode(stack)) {
                tryThunderSkill(sp, stack);
            } else {
                tryWindSkill(sp, stack);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    // ==================== 模式切换 ====================

    private static boolean isThunderMode(ItemStack stack) {
        return MODE_THUNDER.equals(stack.getOrCreateTag().getString(MODE_TAG));
    }

    private static void toggleMode(ServerPlayer player, ItemStack stack) {
        boolean thunder = isThunderMode(stack);
        stack.getOrCreateTag().putString(MODE_TAG, thunder ? MODE_WIND : MODE_THUNDER);
        player.displayClientMessage(Component.translatable(thunder
                ? "message.pasterdream.fluffy_wind_alloy_sword.mode_wind"
                : "message.pasterdream.fluffy_wind_alloy_sword.mode_thunder"), true);
    }

    // ==================== 风 · 疾风突进 ====================

    private void tryWindSkill(ServerPlayer player, ItemStack stack) {
        if (player.getCooldowns().isOnCooldown(stack.getItem())) return;

        Level level = player.level();
        Vec3 start = player.position();
        Vec3 look = player.getLookAngle();

        // 鞘翅飞行时采用三叉戟激流式突进：更强冲量 + 自旋
        boolean flying = player.isFallFlying();
        double dashDistance = flying ? WIND_FLIGHT_DASH_DISTANCE : WIND_DASH_DISTANCE;
        Vec3 end = start.add(look.x * dashDistance, look.y * dashDistance, look.z * dashDistance);

        if (flying) {
            player.setDeltaMovement(new Vec3(
                    look.x * WIND_FLIGHT_DASH_SPEED, look.y * WIND_FLIGHT_DASH_SPEED, look.z * WIND_FLIGHT_DASH_SPEED));
            player.startAutoSpinAttack(WIND_FLIGHT_SPIN_TICKS);
        } else {
            player.setDeltaMovement(new Vec3(
                    look.x * WIND_DASH_SPEED, look.y * WIND_DASH_SPEED + 0.15, look.z * WIND_DASH_SPEED));
        }
        player.hurtMarked = true;

        // 进行攻击（命中）时刻的移动速度 = max(移动速度属性, 瞬时移动速度)
        double speed = Math.max(player.getAttributeValue(Attributes.MOVEMENT_SPEED),
                player.getDeltaMovement().horizontalDistance());
        float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                * SkillCooldownHelper.getSkillDamageMultiplier(player);
        atk += stack.getEnchantmentLevel(Enchantments.SHARPNESS) * (float) SHARPNESS_DAMAGE_BONUS; // 锋利加成
        double factor = WIND_DAMAGE_SPEED_FACTOR * (flying ? WIND_FLIGHT_DAMAGE_FACTOR : 1.0);
        float damage = (float) (factor * speed * atk);

        int smite = stack.getEnchantmentLevel(Enchantments.SMITE);
        int bane = stack.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS);
        int fireAspect = stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT);
        int knockback = stack.getEnchantmentLevel(Enchantments.KNOCKBACK);
        int sweeping = stack.getEnchantmentLevel(Enchantments.SWEEPING_EDGE);

        // 沿途横扫伤害（横扫之刃扩大横扫范围）
        double sweepXZ = WIND_SWEEP_XZ + Math.min(sweeping, 3) * WIND_SWEEP_ENCHANT_BONUS;
        AABB swept = new AABB(
                Math.min(start.x, end.x) - sweepXZ, Math.min(start.y, end.y) - WIND_SWEEP_Y, Math.min(start.z, end.z) - sweepXZ,
                Math.max(start.x, end.x) + sweepXZ, Math.max(start.y, end.y) + WIND_SWEEP_Y, Math.max(start.z, end.z) + sweepXZ);
        player.getPersistentData().putBoolean(APPLYING_TAG, true);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, swept,
                e -> e != player && e.isAlive() && !isOwnedMinion(e, player));
        for (LivingEntity target : targets) {
            target.invulnerableTime = 0;
            float dmg = damage;
            if (smite > 0 && target.getMobType() == MobType.UNDEAD) dmg += smite * SMITE_BANE_DAMAGE;
            if (bane > 0 && target.getMobType() == MobType.ARTHROPOD) dmg += bane * SMITE_BANE_DAMAGE;
            target.hurt(level.damageSources().playerAttack(player), dmg);
            double kbStr = WIND_KNOCKBACK + knockback * WIND_KNOCKBACK_ENCHANT_BONUS;
            Vec3 kb = look.scale(kbStr);
            target.push(kb.x, 0.3 + knockback * 0.1, kb.z);
            if (fireAspect > 0) target.setSecondsOnFire(fireAspect * FIRE_ASPECT_TICK_MULTIPLIER);
        }
        player.getPersistentData().remove(APPLYING_TAG);

        if (level instanceof ServerLevel sl) {
            sl.sendParticles(ParticleTypes.CLOUD,
                    player.getX(), player.getY() + 1, player.getZ(), 40, 0.6, 0.6, 0.6, 0.1);
            sl.sendParticles(ParticleTypes.END_ROD,
                    start.x, start.y + 1, start.z, 20, 0.4, 0.4, 0.4, 0.05);
        }
        level.playSound(null, player.blockPosition(),
                ModSounds.SWORD_SLASH.get(), SoundSource.PLAYERS, 1.0f, 1.0f);

        player.getCooldowns().addCooldown(stack.getItem(), flying ? WIND_FLIGHT_COOLDOWN_TICKS : WIND_COOLDOWN_TICKS);
    }

    // ==================== 雷 · 萦风引雷 ====================

    private void tryThunderSkill(ServerPlayer player, ItemStack stack) {
        if (player.getCooldowns().isOnCooldown(stack.getItem())) return;

        if (!player.isCreative()) {
            if (MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(player) < THUNDER_ENERGY_COST) {
                player.displayClientMessage(Component.translatable("message.pasterdream.fluffy_wind_alloy_sword.no_energy"), true);
                return;
            }
            MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(player, -THUNDER_ENERGY_COST);
        }

        LivingEntity target = findTarget(player);
        Vec3 fallback = player.getEyePosition(1.0f).add(player.getLookAngle().scale(THUNDER_TARGET_RANGE));
        float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                * SkillCooldownHelper.getSkillDamageMultiplier(player);
        atk += stack.getEnchantmentLevel(Enchantments.SHARPNESS) * (float) SHARPNESS_DAMAGE_BONUS; // 锋利加成
        float damage = atk * (float) THUNDER_DAMAGE_MULTIPLIER;

        int smite = stack.getEnchantmentLevel(Enchantments.SMITE);
        int bane = stack.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS);
        int fireAspect = stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT);

        WindAlloyLightningEntity lightning = ModEntities.WIND_ALLOY_LIGHTNING.get().create(player.level());
        if (lightning != null) {
            lightning.init(player, target, damage, fallback, smite, bane, fireAspect);
            lightning.moveTo(player.getX(), player.getY(), player.getZ());
            player.level().addFreshEntity(lightning);
        }

        player.level().playSound(null, player.blockPosition(),
                ModSounds.LIGHTNING_CHARGE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        player.getCooldowns().addCooldown(stack.getItem(), THUNDER_COOLDOWN_TICKS);
    }

    /** 沿准心射线寻找第一个命中的活体目标（追踪落雷目标） */
    private LivingEntity findTarget(ServerPlayer player) {
        Vec3 eye = player.getEyePosition(1.0f);
        Vec3 look = player.getLookAngle();
        Vec3 end = eye.add(look.scale(THUNDER_TARGET_RANGE));
        Level level = player.level();
        LivingEntity best = null;
        double bestDist = Double.MAX_VALUE;
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class,
                player.getBoundingBox().inflate(THUNDER_TARGET_RANGE),
                e -> e != player && e.isAlive())) {
            Optional<Vec3> hit = e.getBoundingBox().inflate(0.3).clip(eye, end);
            if (hit.isPresent()) {
                double dist = eye.distanceToSqr(hit.get());
                if (dist < bestDist) {
                    bestDist = dist;
                    best = e;
                }
            }
        }
        return best;
    }

    /** 是否为玩家自己的仆从/召唤物/同队盟友（战技不应命中） */
    private static boolean isOwnedMinion(LivingEntity e, @Nullable Player owner) {
        if (owner == null) return false;
        if (e instanceof OwnableEntity ownable) {
            return ownable.getOwner() == owner;
        }
        if (e instanceof FoxFireEntity fe) {
            return fe.resolveOwner() == owner;
        }
        return e.isAlliedTo(owner);
    }

    // ==================== 被动 · 雷随疾风 ====================

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class WindAlloyPassiveHandler {
        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if (!(event.getSource().getEntity() instanceof Player player)) return;
            if (!(player.getMainHandItem().getItem() instanceof FluffyWindAlloySwordItem)) return;
            if (player.getPersistentData().getBoolean(APPLYING_TAG)) return;
            if (event.getSource().getDirectEntity() != player) return;

            LivingEntity target = event.getEntity();

            float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float critRatio = atk > 0 ? event.getAmount() / atk : 1.0f; // 保留暴击/附魔比例
            double speed = Math.max(player.getAttributeValue(Attributes.MOVEMENT_SPEED),
                    player.getDeltaMovement().horizontalDistance());
            float mainDamage = (float) ((1 + speed) * atk) * critRatio;
            float lightningDamage = atk * (float) PASSIVE_LIGHTNING_RATIO;

            event.setCanceled(true);
            player.getPersistentData().putBoolean(APPLYING_TAG, true);
            target.invulnerableTime = 0;
            target.hurt(target.level().damageSources().playerAttack(player), mainDamage);
            // 先造成攻击，再造成雷伤，取消无敌帧
            target.invulnerableTime = 0;
            target.hurt(target.level().damageSources().lightningBolt(), lightningDamage);
            player.getPersistentData().remove(APPLYING_TAG);
        }
    }

    // ==================== 其余 ====================

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_sword.skill_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_sword.desc1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_sword.desc2"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_sword.desc3"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_sword.desc4"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_sword.desc5"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_sword.passive_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_sword.passive1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_sword.passive2"));
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(Level level, Entity location, ItemStack stack) {
        var entity = new IndestructibleItemEntity(level, location.getX(), location.getY(), location.getZ(), stack);
        entity.setDefaultPickUpDelay();
        entity.setDeltaMovement(location.getDeltaMovement());
        return entity;
    }
}
