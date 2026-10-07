package com.pasterdream.pasterdreammod.world.enchantment;

import com.pasterdream.pasterdreammod.init.ModEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

/**
 * 噬心修补：通用诅咒附魔（可附任何有耐久的物品）。物品位于玩家背包/护甲/饰品栏中时，
 * 消耗玩家理智值修复自身耐久（0.1 SAN / 1 耐久），理智不足则停止修复。
 * <p>
 * 单级，且与经验修补（{@link Enchantments#MENDING}）、融梦修补（{@link ModEnchantment#MELT_DREAM_REPAIR_ENCHANTMENT}）互斥。
 * 不可附魔台获取，可交易、可宝藏获取。
 */
public class HeartDevourRepairEnchantment extends Enchantment {
    public HeartDevourRepairEnchantment(EquipmentSlot... slots) {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, slots);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinCost(int level) {
        return level * 25;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 50;
    }

    @Override
    protected boolean checkCompatibility(Enchantment other) {
        return super.checkCompatibility(other)
                && other != Enchantments.MENDING
                && other != ModEnchantment.MELT_DREAM_REPAIR_ENCHANTMENT.get();
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isTradeable() {
        return true;
    }

    @Override
    public boolean isDiscoverable() {
        return true;
    }
}
