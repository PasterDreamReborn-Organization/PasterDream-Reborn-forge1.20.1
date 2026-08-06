package com.pasterdream.pasterdreammod.world.item.dreamnotesbook;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import com.pasterdream.pasterdreammod.init.ModCriteriaTriggers;

public class DreamNotesBookItem extends Item
{
    public DreamNotesBookItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public Component getName(ItemStack itemStack)
    {
        CompoundTag compoundTag = itemStack.getTag();
        DreamNotesBookInfo dreamNotesBookInfo = (compoundTag != null && compoundTag.contains("content")) ? DreamNotesBookRegistry.getInfo(compoundTag.getString("content")) : null;
        return dreamNotesBookInfo != null ? dreamNotesBookInfo.title().copy().append(" - " + dreamNotesBookInfo.author()) : Component.literal("null - null");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack itemStack = player.getItemInHand(hand);
        CompoundTag compoundTag = itemStack.getTag();
        if (level.isClientSide)
        {
            DreamNotesBookInfo dreamNotesBookInfo = (compoundTag != null && compoundTag.contains("content")) ? DreamNotesBookRegistry.getInfo(compoundTag.getString("content")) : null;
            Minecraft.getInstance().setScreen(new DreamNotesBookScreen(dreamNotesBookInfo));
        }
        else
        {
            level.playSound(null, player.blockPosition(), SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0f, 1.0f);
            if (compoundTag != null && compoundTag.contains("content") && player instanceof ServerPlayer serverPlayer)
            {
                ModCriteriaTriggers.READ_DREAM_NOTE.trigger(serverPlayer, compoundTag.getString("content"));
            }
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
    }
}
