package com.pasterdream.pasterdreammod.world.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 * 聚梦：护甲附魔。每级提升 0.25 融梦光环（{@code MELT_DREAM_VARIABILITY}，单位/分钟）。
 * <p>
 * 最高 V 级，但等级不做上限裁剪（类似锋利），超出 5 级的部分依旧生效；
 * 生存环境下受附魔台/铁砧的 {@link #getMaxLevel()} 限制无法突破 5 级。
 */
public class DreamGatheringEnchantment extends Enchantment {
    public DreamGatheringEnchantment(EquipmentSlot... slots) {
        super(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR, slots);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinCost(int level) {
        return 5 + (level - 1) * 10;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 20;
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
