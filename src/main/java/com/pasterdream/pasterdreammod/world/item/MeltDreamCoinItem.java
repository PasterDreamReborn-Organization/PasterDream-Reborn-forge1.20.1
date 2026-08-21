package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MeltDreamCoinItem extends Item {
    private final boolean isPile;

    public MeltDreamCoinItem(Properties properties, boolean isPile) {
        super(properties);
        this.isPile = isPile;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.success(stack);
        }

        if (isPile) {
            stack.shrink(1);
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ModItems.MELT_DREAM_COIN.get(), 10));
            level.playSound(null, player.blockPosition(), ModSounds.COIN_EXCHANGE.get(), SoundSource.PLAYERS, 1, 1);
        } else {
            if (stack.getCount() >= 10) {
                stack.shrink(10);
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ModItems.MELT_DREAM_COIN_PILE.get(), 1));
                level.playSound(null, player.blockPosition(), ModSounds.COIN_EXCHANGE.get(), SoundSource.PLAYERS, 1, 1);
            }
        }
        return InteractionResultHolder.success(stack);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack itemstack, Player entity) {
        if (!isPile && !entity.level().isClientSide) {
            entity.level().playSound(null, entity.blockPosition(), ModSounds.COIN_DROP.get(), SoundSource.PLAYERS, 1, 1);
        }
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        int faceValue = stack.getCount() * (isPile ? 10 : 1);
        if (isPile) {
            tooltip.add(Component.translatable("tooltip.pasterdream.melt_dream_coin_pile"));
        } else {
            tooltip.add(Component.translatable("tooltip.pasterdream.melt_dream_coin"));
        }
        tooltip.add(Component.translatable("tooltip.pasterdream.melt_dream_coin.value")
                .append(Component.literal(String.valueOf(faceValue)).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD)));
    }
}
