package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.helper.ShadowDifficultyHelper;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.PasterDreamDrinkAndFoodProperties;
import com.pasterdream.pasterdreammod.world.item.drinkandfooditem.PasterDreamFoodItem;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShadowJellyItem extends PasterDreamFoodItem {

    public ShadowJellyItem(PasterDreamDrinkAndFoodProperties properties) {
        super(properties);
    }

    @Override
    protected void onFoodSpecial(LivingEntity entity, Level level) {
        if (entity instanceof ServerPlayer player) {
            int oldTier = ShadowDifficultyHelper.getDifficulty(player);
            int newTier = Math.max(0, Math.min(3, oldTier + (player.getRandom().nextBoolean() ? 1 : -1)));
            ShadowDifficultyHelper.setPlayerDifficulty(player, newTier);
            int delta = newTier - oldTier;
            Component tierName = Component.translatable("message.pasterdream.shadow_difficulty.tier." + newTier);
            if (delta > 0) {
                player.displayClientMessage(Component.translatable("message.pasterdream.shadow_jelly.up", newTier, tierName), false);
            } else if (delta < 0) {
                player.displayClientMessage(Component.translatable("message.pasterdream.shadow_jelly.down", newTier, tierName), false);
            } else {
                player.displayClientMessage(Component.translatable("message.pasterdream.shadow_jelly.unchanged", newTier, tierName), false);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_jelly"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_jelly.flavor"));
    }
}