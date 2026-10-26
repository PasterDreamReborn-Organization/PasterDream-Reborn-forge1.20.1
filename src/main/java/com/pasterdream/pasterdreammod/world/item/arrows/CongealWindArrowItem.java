package com.pasterdream.pasterdreammod.world.item.arrows;

import com.pasterdream.pasterdreammod.world.entity.arrows.CongealWindArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehavior;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviorProvider;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviors;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/** 凝风箭物品。作为弓 / 弩的弹药，射出 {@link CongealWindArrow}。 */
public class CongealWindArrowItem extends ArrowItem implements ArrowBehaviorProvider {

    public CongealWindArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        return new CongealWindArrow(level, shooter);
    }

    @Override
    public ArrowBehavior arrowBehavior() {
        return ArrowBehaviors.CONGEAL_WIND;
    }
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.congeal_wind_arrow.tooltip1"));
        list.add(Component.translatable("tooltip.pasterdream.congeal_wind_arrow.tooltip2"));
    }
}
