package com.pasterdream.pasterdreammod.world.item.hellfiretool;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 狱炎剑 —— 命中点燃 4 秒，已燃烧目标叠加 2 秒。
 */
public class HellfireSwordItem extends SwordItem {

    public HellfireSwordItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties.fireResistant());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.isOnFire()) {
            target.setRemainingFireTicks(target.getRemainingFireTicks() + 40);
        } else {
            target.setSecondsOnFire(4);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.hellfire_sword.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.hellfire_sword.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.hellfire_sword.3"));
    }
}
