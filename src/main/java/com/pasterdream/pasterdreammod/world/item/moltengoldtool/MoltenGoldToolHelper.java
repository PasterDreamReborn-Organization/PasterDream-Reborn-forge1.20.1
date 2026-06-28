package com.pasterdream.pasterdreammod.world.item.moltengoldtool;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 炙焰金工具的共享逻辑 —— 右键急迫 + Tooltip。
 */
final class MoltenGoldToolHelper {

    static InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            player.causeFoodExhaustion(4);
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 400, 0,
                    false, false, true));
            player.getCooldowns().addCooldown(item, 200);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    static void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.pasterdreammod.molten_gold_tool.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.molten_gold_tool.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.molten_gold_tool.3"));
    }
}
