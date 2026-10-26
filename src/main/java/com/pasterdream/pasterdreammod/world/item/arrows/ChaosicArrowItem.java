package com.pasterdream.pasterdreammod.world.item.arrows;

import com.pasterdream.pasterdreammod.init.ModEnchantment;
import com.pasterdream.pasterdreammod.world.entity.arrows.ChaosicArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehavior;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviorProvider;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviors;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.util.List;

/** 混沌箭物品。作为弓 / 弩的弹药，射出 {@link ChaosicArrow}。 */
public class ChaosicArrowItem extends ArrowItem implements ArrowBehaviorProvider {

    public ChaosicArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        return new ChaosicArrow(level, shooter);
    }

    @Override
    public ArrowBehavior arrowBehavior() {
        return ArrowBehaviors.CHAOSIC;
    }

    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
        return EnchantmentHelper.getItemEnchantmentLevel(ModEnchantment.HEART_DEVOUR_REPAIR_ENCHANTMENT.get(), bow) > 0;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.chaosic_arrow.tooltip1"));
        list.add(Component.translatable("tooltip.pasterdream.chaosic_arrow.tooltip2"));
        list.add(Component.translatable("tooltip.pasterdream.chaosic_arrow.tooltip3"));
    }
}
