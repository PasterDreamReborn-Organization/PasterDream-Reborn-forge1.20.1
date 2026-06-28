package com.pasterdream.pasterdreammod.world.item.moltengoldtool;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 炙焰金剑 —— 命中时点燃目标 3 秒。
 */
public class MoltenGoldSwordItem extends SwordItem {

    public MoltenGoldSwordItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties.fireResistant());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setSecondsOnFire(3);
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.molten_gold_sword.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.molten_gold_sword.2"));
    }
}
