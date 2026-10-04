package com.pasterdream.pasterdreammod.world.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

/**
 * 融梦修补：通用附魔（可附任何有耐久的物品）。物品位于玩家背包/护甲/饰品栏中时，
 * 消耗玩家融梦能量修复自身耐久，费率同融梦水晶工具（0.01E / 1 耐久）。
 * <p>
 * 单级，且与经验修补（{@link Enchantments#MENDING}）互斥。
 */
public class MeltDreamRepairEnchantment extends Enchantment {
    public MeltDreamRepairEnchantment(EquipmentSlot... slots) {
        super(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE, slots);
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
        return super.checkCompatibility(other) && other != Enchantments.MENDING;
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
