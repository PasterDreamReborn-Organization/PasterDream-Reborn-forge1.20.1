package com.pasterdream.pasterdreammod.world.item.arrows;

import com.pasterdream.pasterdreammod.world.entity.arrows.ToxicSporeArrow;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/** 碎梦箭物品。作为原版弓的弹药，射出 {@link ToxicSporeArrow}。 */
public class ToxicSporeArrowItem extends ArrowItem {

    public ToxicSporeArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        return new ToxicSporeArrow(level, shooter);
    }
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.toxic_spore_arrow.tooltip1"));
        list.add(Component.translatable("tooltip.pasterdream.toxic_spore_arrow.tooltip2"));

    }
}