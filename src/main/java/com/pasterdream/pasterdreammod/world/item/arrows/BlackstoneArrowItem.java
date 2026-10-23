package com.pasterdream.pasterdreammod.world.item.arrows;

import com.pasterdream.pasterdreammod.world.entity.arrows.BlackstoneArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehavior;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviorProvider;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviors;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

/** 黑石箭物品。作为弓 / 弩的弹药，射出 {@link BlackstoneArrow}。 */
public class BlackstoneArrowItem extends ArrowItem implements ArrowBehaviorProvider {

    public BlackstoneArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        return new BlackstoneArrow(level, shooter);
    }

    @Override
    public ArrowBehavior arrowBehavior() {
        return ArrowBehaviors.BLACKSTONE;
    }

    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow) > 0;
    }
}
