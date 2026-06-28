package com.pasterdream.pasterdreammod.world.item.hellfiretool;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 狱炎镐 —— 右键消耗饱食度获得急迫（30s / 8s 冷却）。
 */
public class HellfirePickaxeItem extends PickaxeItem {

    public HellfirePickaxeItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties.fireResistant());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            player.causeFoodExhaustion(4);
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 600, 1,
                    false, false, true));
            player.getCooldowns().addCooldown(this, 160);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.hellfire_tool.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.hellfire_tool.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.hellfire_tool.3"));
    }
}
