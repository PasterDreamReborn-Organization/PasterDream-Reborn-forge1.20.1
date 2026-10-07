package com.pasterdream.pasterdreammod.world.item.meltdreamtool;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.network.meltdreamenergy.MeltDreamEnergySyncPacket;
import com.pasterdream.pasterdreammod.world.dimension.DyedreamDimension;
import com.pasterdream.pasterdreammod.world.entity.MeltDreamArrowEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
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
 * 融梦弓 —— 融梦水晶系列远程武器。
 * <p>
 * 被动「融梦箭」：拥有融梦能量时射出的一箭消耗 0.2 融梦能量，本次箭矢伤害 +30%；
 * 能量不足时照常射出但不触发。处于染梦世界时，普通箭矢不消耗箭矢/耐久/融梦能量，
 * 且依旧享受 +30% 加成。
 * <p>
 * 与融梦水晶工具一样自带「融梦修补」（在背包中消耗融梦能量修复耐久）。
 */
public class MeltDreamBowItem extends BowItem {

    /** 单次强化射击消耗的融梦能量 */
    private static final double ENERGY_COST = 0.2;
    /** 强化射击伤害倍率 */
    private static final float DAMAGE_MULTIPLIER = 1.3F;

    public MeltDreamBowItem(Properties properties) {
        super(properties);
    }

    /* 覆盖原版逻辑：插入融梦箭强化 / 染梦世界免耗逻辑，其余（附魔、拾取、音效）保持一致。 */
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
        // 染梦世界 + 普通箭矢：不消耗箭矢/耐久/融梦能量，仍享受强化
        boolean dyedreamFree = level.dimension().equals(DyedreamDimension.DYEDREAM_WORLD)
                && ammo.is(Items.ARROW);

        boolean empowered = false;
        if (!level.isClientSide) {
            empowered = dyedreamFree || tryConsumeEnergy(player);
        }

        if (!level.isClientSide) {
            AbstractArrow arrow;
            if (empowered && !ammo.is(Items.SPECTRAL_ARROW)) {
                // 强化射击改用带粉色拖尾的融梦箭实体（光谱箭保留原版发光行为）
                MeltDreamArrowEntity trailArrow = new MeltDreamArrowEntity(level, player);
                trailArrow.setEffectsFromItem(ammo);
                arrow = trailArrow;
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
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                arrow.setSecondsOnFire(100);
            }

            if (empowered) {
                arrow.setBaseDamage(arrow.getBaseDamage() * DAMAGE_MULTIPLIER);
            }

            if (!dyedreamFree) {
                stack.hurtAndBreak(1, player,
                        brokenPlayer -> brokenPlayer.broadcastBreakEvent(player.getUsedItemHand()));
            }
            if (infiniteArrow || (player.getAbilities().instabuild
                    && (ammo.is(Items.SPECTRAL_ARROW) || ammo.is(Items.TIPPED_ARROW)))) {
                arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
            level.addFreshEntity(arrow);
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F,
                1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F);

        if (!infiniteArrow && !dyedreamFree) {
            ammo.shrink(1);
            if (ammo.isEmpty()) {
                player.getInventory().removeItem(ammo);
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
    }

    /**
     * 尝试消耗 0.2 融梦能量换取强化。创造模式或「无需消耗」状态下不扣能量但仍视为强化。
     *
     * @return 是否触发强化
     */
    private boolean tryConsumeEnergy(Player player) {
        AtomicBoolean empowered = new AtomicBoolean(false);
        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(energy -> {
            boolean free = !energy.getIsOrNotNeedConsumeDreamEnergy() || player.isCreative();
            if (free) {
                empowered.set(true);
            } else {
                // 消耗翻倍减益下按实际消耗判定，避免能量被扣成负数
                double effectiveCost = energy.isConsumeDoubled() ? ENERGY_COST * 2.0 : ENERGY_COST;
                if (energy.getMeltDreamEnergy() >= effectiveCost) {
                    energy.addMeltDreamEnergy(-ENERGY_COST);
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
     * 允许抢夺附魔（原版抢夺仅限剑）。Forge 将 {@link Enchantment#canEnchant} 重定向到本方法，
     * 因此附魔台 / 铁砧（附魔书）/ {@code /enchant} 均生效；掉落加成走原版逻辑自动读取击杀者主手。
     */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == Enchantments.MOB_LOOTING || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get())
                || super.isValidRepairItem(stack, repairCandidate);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        MeltDreamToolHelper.onInventoryTick(stack, level, entity, slot, selected);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_bow.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_bow.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_bow.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_bow.4"));
        MeltDreamToolHelper.appendHoverText(stack, level, tooltip, flag);
    }
}
