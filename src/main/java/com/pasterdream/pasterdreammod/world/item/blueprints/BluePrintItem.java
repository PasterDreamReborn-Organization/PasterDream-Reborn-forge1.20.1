package com.pasterdream.pasterdreammod.world.item.blueprints;

import com.pasterdream.pasterdreammod.helper.localnbtreader.LocalNBTReader;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BluePrintItem extends Item
{
    public BluePrintItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public Component getName(ItemStack itemStack)
    {
        CompoundTag compoundTag = itemStack.getTag();
        BluePrintInfo bluePrintInfo = (compoundTag != null && compoundTag.contains("content")) ? BluePrintRegistry.getInfo(compoundTag.getString("content")) : null;
        return Component.translatable("item.pasterdream.blue_print").copy().append(" - ").append(bluePrintInfo != null ? bluePrintInfo.title() : Component.literal("null"));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.右键打开GUI以查看蓝图结构"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide)
        {
            CompoundTag compoundTag = stack.getTag();
            CompoundTag NBT = new CompoundTag();
            if (compoundTag != null && compoundTag.contains("content"))
            {
                BluePrintInfo bluePrintInfo = BluePrintRegistry.getInfo(compoundTag.getString("content"));
                if(bluePrintInfo != null)
                {
                    NBT = LocalNBTReader.getCompoundTag(bluePrintInfo.materialNBT());
                }

                Minecraft.getInstance().setScreen(new BluePrintScreen(NBT));
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
