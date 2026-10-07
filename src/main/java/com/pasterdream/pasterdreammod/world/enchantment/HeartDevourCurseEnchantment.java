package com.pasterdream.pasterdreammod.world.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 * 噬心诅咒：护甲附魔。玩家每次受伤时，按原始伤害扣除等量理智值，
 * 且每件附带此诅咒的护甲各自叠加（4 件 = 扣除 4 倍伤害的理智）。
 * <p>
 * 单级诅咒。不可附魔台获取，可交易、可宝藏获取。
 */
public class HeartDevourCurseEnchantment extends Enchantment {
    public HeartDevourCurseEnchantment(EquipmentSlot... slots) {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR, slots);
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
