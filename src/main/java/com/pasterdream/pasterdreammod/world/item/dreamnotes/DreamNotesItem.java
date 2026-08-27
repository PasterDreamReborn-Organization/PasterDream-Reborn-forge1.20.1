package com.pasterdream.pasterdreammod.world.item.dreamnotes;

import com.pasterdream.pasterdreammod.init.ModCriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DreamNotesItem extends Item
{
    public DreamNotesItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(itemStack, level, tooltip, flag);
        CompoundTag tag = itemStack.getTag();
        if (tag != null && tag.contains("content"))
        {
            tooltip.add(Component.translatable("tooltip.pasterdream." + tag.getString("content")));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag tag = stack.getTag();
        String content = tag != null ? tag.getString("content") : "";

        if (level.isClientSide)
        {
            if (!content.isEmpty())
            {
                DreamNotesClientHelper.openDreamNotesScreen(content);
            }
        }
        else
        {
            if (!content.isEmpty() && player instanceof ServerPlayer serverPlayer)
            {
                level.playSound(null, player.blockPosition(), SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0f, 1.0f);
                ModCriteriaTriggers.READ_DREAM_NOTE.trigger(serverPlayer, content);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
