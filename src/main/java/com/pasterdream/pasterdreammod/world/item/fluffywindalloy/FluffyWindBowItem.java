package com.pasterdream.pasterdreammod.world.item.fluffywindalloy;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.network.meltdreamenergy.MeltDreamEnergySyncPacket;
import com.pasterdream.pasterdreammod.world.entity.FluffyWindArrowEntity;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviors;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 萦风弓 —— 萦风合金系列远程武器。
 * <p>
 * 被动「萦风之箭」：拥有融梦能量时射出的一箭消耗 0.2 融梦能量，本次箭矢改为
 * {@link FluffyWindArrowEntity}：不受重力影响、命中目标使其飘浮 1s、且带青绿色拖尾；
 * 能量不足时照常射出普通箭矢但无上述效果。
 * <p>
 * 特性：可附魔「引雷」。附魔引雷后消耗 +0.1，且强化箭命中生物时在其处落下闪电造成范围雷电伤害
 * （具体由 {@code FluffyWindBowHandler} 结算）。
 */
public class FluffyWindBowItem extends BowItem {

    /** 单次强化射击消耗的融梦能量 */
    private static final double ENERGY_COST = 0.2;
    /** 附魔「引雷」时的额外融梦能量消耗 */
    private static final double CHANNELING_EXTRA_COST = 0.1;

    public FluffyWindBowItem(Properties properties) {
        super(properties);
    }

    /* 覆盖原版逻辑：插入萦风箭强化，其余（附魔、拾取、音效）保持一致。 */
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (!(entityLiving instanceof Player player)) {
            super.releaseUsing(stack, level, entityLiving, timeLeft);
            return;
        }

        boolean infinite = player.getAbilities().instabuild
                || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
        ItemStack ammo = player.getProjectile(stack);

        int charge = this.getUseDuration(stack) - timeLeft;
        charge = ForgeEventFactory.onArrowLoose(stack, level, player, charge, !ammo.isEmpty() || infinite);
        if (charge < 0) {
            return;
        }
        if (ammo.isEmpty() && !infinite) {
            return;
        }
        if (ammo.isEmpty()) {
            ammo = new ItemStack(Items.ARROW);
        }

        float power = getPowerForTime(charge);
        if (power < 0.1F) {
            return;
        }

        boolean infiniteArrow = player.getAbilities().instabuild
                || (ammo.getItem() instanceof ArrowItem arrowItem && arrowItem.isInfinite(ammo, stack, player));

        int channelingLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.CHANNELING, stack);
        int flameLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack);

        boolean empowered = !level.isClientSide && tryConsumeEnergy(player, stack);

        if (!level.isClientSide) {
            AbstractArrow arrow;
            if (empowered && !ammo.is(Items.SPECTRAL_ARROW)) {
                // 强化射击改用不受重力的萦风箭实体（光谱箭保留原版发光行为）
                FluffyWindArrowEntity windArrow = new FluffyWindArrowEntity(level, player);
                windArrow.setEffectsFromItem(ammo);
                windArrow.setAmmoItem(ammo);
                ArrowBehaviors.applyBaseStats(windArrow, ammo);
                if (channelingLevel > 0) {
                    // 附魔引雷：命中生物时落雷，并记录火矢等级用于范围点燃
                    windArrow.enableChannelingLightning(flameLevel);
                }
                arrow = windArrow;
            } else {
                ArrowItem arrowItem = (ArrowItem) (ammo.getItem() instanceof ArrowItem ? ammo.getItem() : Items.ARROW);
                arrow = arrowItem.createArrow(level, ammo, player);
            }
            arrow = this.customArrow(arrow);
            arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, power * 3.0F, 1.0F);
            if (power == 1.0F) {
                arrow.setCritArrow(true);
            }

            int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
            if (powerLevel > 0) {
                arrow.setBaseDamage(arrow.getBaseDamage() + (double) powerLevel * 0.5D + 0.5D);
            }
            int punchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
            if (punchLevel > 0) {
                arrow.setKnockback(punchLevel);
            }
            if (flameLevel > 0) {
                arrow.setSecondsOnFire(100);
            }

            stack.hurtAndBreak(1, player,
                    brokenPlayer -> brokenPlayer.broadcastBreakEvent(player.getUsedItemHand()));
            if (infiniteArrow || (player.getAbilities().instabuild
                    && (ammo.is(Items.SPECTRAL_ARROW) || ammo.is(Items.TIPPED_ARROW)))) {
                arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
            level.addFreshEntity(arrow);
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F,
                1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F);

        if (!infiniteArrow) {
            ammo.shrink(1);
            if (ammo.isEmpty()) {
                player.getInventory().removeItem(ammo);
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
    }

    /**
     * 尝试消耗融梦能量换取强化（基础 0.2，附魔「引雷」时 +0.1）。
     * 创造模式或「无需消耗」状态下不扣能量但仍视为强化。
     *
     * @return 是否触发强化
     */
    private boolean tryConsumeEnergy(Player player, ItemStack stack) {
        double cost = ENERGY_COST;
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.CHANNELING, stack) > 0) {
            cost += CHANNELING_EXTRA_COST;
        }
        AtomicBoolean empowered = new AtomicBoolean(false);
        double finalCost = cost;
        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(energy -> {
            boolean free = !energy.getIsOrNotNeedConsumeDreamEnergy() || player.isCreative();
            if (free) {
                empowered.set(true);
            } else {
                // 消耗翻倍减益下按实际消耗判定，避免能量被扣成负数
                double effectiveCost = MeltDreamEnergyHelper.getEffectiveConsumeCost(energy, finalCost);
                if (energy.getMeltDreamEnergy() >= effectiveCost) {
                    energy.addMeltDreamEnergy(-effectiveCost);
                    MeltDreamEnergySyncPacket.sendToPlayer(player, energy);
                    empowered.set(true);
                }
            }
        });
        return empowered.get();
    }

    @Override
    public int getEnchantmentValue() {
        return 22;
    }

    /**
     * 允许在附魔台获得「引雷」（原版引雷仅限三叉戟）。
     * Forge 将 {@link Enchantment#canEnchant} 重定向到本方法，故附魔台 / 铁砧（附魔书）/ {@code /enchant} 均生效。
     */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == Enchantments.CHANNELING || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(ModItems.FLUFFY_WIND_ALLOY_INGOT.get())
                || super.isValidRepairItem(stack, repairCandidate);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.fluffy_wind_bow.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.fluffy_wind_bow.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.fluffy_wind_bow.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.fluffy_wind_bow.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.fluffy_wind_bow.5"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.fluffy_wind_bow.6"));
    }
}
