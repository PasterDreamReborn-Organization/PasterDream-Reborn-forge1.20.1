package com.pasterdream.pasterdreammod.world.item.shadowalloytool;

import com.pasterdream.pasterdreammod.world.entity.ShadowAlloyArrowEntity;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * 暗影合金弩 —— 暗影合金系列远程武器。
 * <p>
 * 在原版弩基础上追加：
 * <ul>
 *   <li>可附魔「无限」；装填/发射不消耗普通箭矢（类似原版弓的无限）。</li>
 *   <li>自带力量 III（相当于原版力量 III），可与附魔力量 V 叠加（等效等级相加）。</li>
 *   <li>发射的箭矢带标记，命中生物时由 {@link ShadowAlloyCrossbowHandler} 施加 10s 易伤与随机 5s 负面效果。</li>
 * </ul>
 * 与暗影合金工具一致：防火、EPIC 稀有度、暗影合金锭修复。
 */
public class ShadowAlloyCrossbowItem extends CrossbowItem {

    private static final String TAG_CHARGED_PROJECTILES = "ChargedProjectiles";
    private static final float ARROW_POWER = 3.15F;
    private static final float FIREWORK_POWER = 1.6F;
    /** 内置力量等级（相当于原版力量 III），与附魔力量等级相加 */
    public static final int BUILT_IN_POWER = 3;

    public ShadowAlloyCrossbowItem(Properties properties) {
        super(properties.fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (isCharged(stack)) {
            shootChargedProjectiles(level, player, hand, stack, computeShootingPower(stack), 1.0F);
            setCharged(stack, false);
            return InteractionResultHolder.consume(stack);
        }
        if (canCharge(player, stack)) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        int charge = this.getUseDuration(stack) - timeLeft;
        float power = computeChargePower(charge, stack);
        if (power >= 1.0F && !isCharged(stack) && loadProjectilesWithInfinity(entity, stack)) {
            setCharged(stack, true);
            SoundSource source = entity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.CROSSBOW_LOADING_END,
                    source, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == Enchantments.INFINITY_ARROWS || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(com.pasterdream.pasterdreammod.init.ModItems.SHADOW_ALLOY_INGOT.get())
                || super.isValidRepairItem(stack, repairCandidate);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_crossbow.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_crossbow.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_crossbow.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_crossbow.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_crossbow.5"));
    }

    /* ============================ 装填（无限感知） ============================ */

    private static boolean canCharge(Player player, ItemStack stack) {
        return !player.getProjectile(stack).isEmpty() || hasInfinity(player, stack);
    }

    private static boolean hasInfinity(Player player, ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
    }

    private static float computeChargePower(int time, ItemStack stack) {
        float f = (float) time / getChargeDuration(stack);
        return f > 1.0F ? 1.0F : f;
    }

    private static boolean loadProjectilesWithInfinity(LivingEntity shooter, ItemStack crossbow) {
        int multishot = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, crossbow);
        int count = multishot == 0 ? 1 : 3;
        boolean creative = shooter instanceof Player player && player.getAbilities().instabuild;
        boolean infinity = shooter instanceof Player player && hasInfinity(player, crossbow);
        ItemStack ammo = shooter.getProjectile(crossbow);
        if (ammo.isEmpty() && infinity && !creative) {
            ammo = new ItemStack(Items.ARROW);
        }
        ItemStack template = ammo.copy();
        for (int i = 0; i < count; i++) {
            if (i > 0) {
                ammo = template.copy();
            }
            if (ammo.isEmpty() && creative) {
                ammo = new ItemStack(Items.ARROW);
                template = ammo.copy();
            }
            if (!loadSingleProjectile(shooter, crossbow, ammo, i > 0, creative, infinity)) {
                return false;
            }
        }
        return true;
    }

    private static boolean loadSingleProjectile(LivingEntity shooter, ItemStack crossbow, ItemStack projectile,
                                                boolean secondary, boolean creative, boolean infinity) {
        if (projectile.isEmpty()) {
            return false;
        }
        // 无限仅作用于普通箭矢：药水箭 / 光谱箭 / 烟花照常消耗
        boolean infiniteProjectile = infinity && projectile.is(Items.ARROW);
        ItemStack loaded;
        if (creative || infiniteProjectile || secondary) {
            loaded = projectile.copy();
        } else {
            loaded = projectile.split(1);
            if (projectile.isEmpty() && shooter instanceof Player player) {
                player.getInventory().removeItem(projectile);
            }
        }
        appendChargedProjectile(crossbow, loaded);
        return true;
    }

    /* ============================ 发射 ============================ */

    private static float computeShootingPower(ItemStack stack) {
        return containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? FIREWORK_POWER : ARROW_POWER;
    }

    private static void shootChargedProjectiles(Level level, LivingEntity shooter, InteractionHand hand,
                                                ItemStack crossbow, float velocity, float inaccuracy) {
        List<ItemStack> projectiles = readChargedProjectiles(crossbow);
        float[] pitches = computeShotPitches(shooter);
        boolean creative = shooter instanceof Player player && player.getAbilities().instabuild;
        boolean infinity = shooter instanceof Player player && hasInfinity(player, crossbow);
        for (int i = 0; i < projectiles.size(); i++) {
            ItemStack projectile = projectiles.get(i);
            if (projectile.isEmpty()) {
                continue;
            }
            float angle = i == 0 ? 0.0F : (i == 1 ? -10.0F : 10.0F);
            fireProjectile(level, shooter, hand, crossbow, projectile, pitches[i % pitches.length],
                    creative || infinity, velocity, inaccuracy, angle);
        }
        afterCrossbowShot(level, shooter, crossbow);
    }

    private static void fireProjectile(Level level, LivingEntity shooter, InteractionHand hand, ItemStack crossbow,
                                        ItemStack projectile, float soundPitch, boolean creativeOnly, float velocity,
                                        float inaccuracy, float angle) {
        if (level.isClientSide) {
            return;
        }
        boolean firework = projectile.is(Items.FIREWORK_ROCKET);
        Projectile shot;
        if (firework) {
            shot = new FireworkRocketEntity(level, projectile, shooter, shooter.getX(),
                    shooter.getEyeY() - 0.15D, shooter.getZ(), true);
        } else {
            shot = createCrossbowArrow(level, shooter, crossbow, projectile);
            if (creativeOnly || angle != 0.0F) {
                ((AbstractArrow) shot).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
        }

        // 标记为暗影合金弩发射的弹射物：箭命中 / 烟花爆炸伤害时据此施加被动
        shot.getPersistentData().putBoolean(ShadowAlloyCrossbowHandler.PROJECTILE_TAG, true);

        if (shooter instanceof CrossbowAttackMob mob) {
            mob.shootCrossbowProjectile(mob.getTarget(), crossbow, shot, angle);
        } else {
            Vec3 up = shooter.getUpVector(1.0F);
            Quaternionf rotation = new Quaternionf().setAngleAxis(angle * 0.017453292F, up.x, up.y, up.z);
            Vec3 view = shooter.getViewVector(1.0F);
            Vector3f direction = view.toVector3f().rotate(rotation);
            shot.shoot(direction.x(), direction.y(), direction.z(), velocity, inaccuracy);
        }

        crossbow.hurtAndBreak(firework ? 3 : 1, shooter, entity -> entity.broadcastBreakEvent(hand));
        level.addFreshEntity(shot);
        level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT,
                SoundSource.PLAYERS, 1.0F, soundPitch);
    }

    private static AbstractArrow createCrossbowArrow(Level level, LivingEntity shooter, ItemStack crossbow, ItemStack projectile) {
        // 普通/药水箭使用带暗紫拖尾的自定义箭实体；光谱箭保留原版实体以维持发光效果
        AbstractArrow arrow;
        if (projectile.is(Items.SPECTRAL_ARROW)) {
            ArrowItem arrowItem = (ArrowItem) (projectile.getItem() instanceof ArrowItem ? projectile.getItem() : Items.ARROW);
            arrow = arrowItem.createArrow(level, projectile, shooter);
        } else {
            ShadowAlloyArrowEntity trailArrow = new ShadowAlloyArrowEntity(level, shooter);
            trailArrow.setEffectsFromItem(projectile);
            arrow = trailArrow;
        }
        if (shooter instanceof Player) {
            arrow.setCritArrow(true);
        }
        arrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        arrow.setShotFromCrossbow(true);

        int piercing = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, crossbow);
        if (piercing > 0) {
            arrow.setPierceLevel((byte) piercing);
        }

        int powerLevel = BUILT_IN_POWER + EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, crossbow);
        arrow.setBaseDamage(arrow.getBaseDamage() + (double) powerLevel * 0.5D + 0.5D);

        return arrow;
    }

    private static float[] computeShotPitches(LivingEntity shooter) {
        boolean flip = shooter.getRandom().nextBoolean();
        return new float[]{1.0F, computeRandomShotPitch(flip, shooter), computeRandomShotPitch(!flip, shooter)};
    }

    private static float computeRandomShotPitch(boolean low, LivingEntity shooter) {
        float base = low ? 0.63F : 0.43F;
        return 1.0F / (shooter.getRandom().nextFloat() * 0.5F + 1.8F) + base;
    }

    private static void afterCrossbowShot(Level level, LivingEntity shooter, ItemStack crossbow) {
        if (shooter instanceof ServerPlayer serverPlayer) {
            if (!level.isClientSide) {
                CriteriaTriggers.SHOT_CROSSBOW.trigger(serverPlayer, crossbow);
            }
            serverPlayer.awardStat(Stats.ITEM_USED.get(crossbow.getItem()));
        }
        removeChargedProjectiles(crossbow);
    }

    /* ============================ NBT 存取 ============================ */

    private static void appendChargedProjectile(ItemStack crossbow, ItemStack projectile) {
        CompoundTag tag = crossbow.getOrCreateTag();
        ListTag list;
        if (tag.contains(TAG_CHARGED_PROJECTILES, 9)) {
            list = tag.getList(TAG_CHARGED_PROJECTILES, 10);
        } else {
            list = new ListTag();
        }
        CompoundTag entry = new CompoundTag();
        projectile.save(entry);
        list.add(entry);
        tag.put(TAG_CHARGED_PROJECTILES, list);
    }

    private static List<ItemStack> readChargedProjectiles(ItemStack crossbow) {
        List<ItemStack> list = new ArrayList<>();
        CompoundTag tag = crossbow.getTag();
        if (tag != null && tag.contains(TAG_CHARGED_PROJECTILES, 9)) {
            ListTag projectiles = tag.getList(TAG_CHARGED_PROJECTILES, 10);
            for (int i = 0; i < projectiles.size(); i++) {
                list.add(ItemStack.of(projectiles.getCompound(i)));
            }
        }
        return list;
    }

    private static void removeChargedProjectiles(ItemStack crossbow) {
        CompoundTag tag = crossbow.getTag();
        if (tag != null) {
            ListTag list = tag.getList(TAG_CHARGED_PROJECTILES, 9);
            list.clear();
            tag.put(TAG_CHARGED_PROJECTILES, list);
        }
    }
}
