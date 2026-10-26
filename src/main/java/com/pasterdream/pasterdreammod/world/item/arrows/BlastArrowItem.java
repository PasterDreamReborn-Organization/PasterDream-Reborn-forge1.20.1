package com.pasterdream.pasterdreammod.world.item.arrows;

import com.pasterdream.pasterdreammod.init.ModEnchantment;
import com.pasterdream.pasterdreammod.world.entity.arrows.BlastArrow;
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

/** 爆破箭物品。作为弓 / 弩的弹药，射出 {@link BlastArrow}。 */
public class BlastArrowItem extends ArrowItem implements ArrowBehaviorProvider {

    public BlastArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        return new BlastArrow(level, shooter);
    }

    @Override
    public ArrowBehavior arrowBehavior() {
        return ArrowBehaviors.BLAST;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.blast_arrow.tooltip1"));
        list.add(Component.translatable("tooltip.pasterdream.blast_arrow.tooltip2"));
        list.add(Component.translatable("tooltip.pasterdream.blast_arrow.tooltip3"));
    }
}
