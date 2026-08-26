package com.pasterdream.pasterdreammod.world.item.blueprints;

import com.pasterdream.pasterdreammod.helper.localnbtreader.LocalNBTReader;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.blueprint.BlueprintPlacePacket;
import com.pasterdream.pasterdreammod.network.blueprint.UpdateBlueprintPlacingPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
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
        tooltip.add(Component.translatable("tooltip.pasterdream.蓝图搭建消耗规则"));
    }

    private void handleClientInteraction(Player player, ItemStack stack, @Nullable BlockPos clickedPos)
    {
        if (isPlacing(stack))
        {
            if (clickedPos != null)
            {
                ModNetwork.CHANNEL.sendToServer(new BlueprintPlacePacket(clickedPos));
            }
                else
                {
                    ModNetwork.CHANNEL.sendToServer(new UpdateBlueprintPlacingPacket(false));
                    player.displayClientMessage(Component.translatable("message.pasterdream.取消放置蓝图"), true);
                }
            ClientBluePrintPlacement.cancel();
        }
            else
            {
                CompoundTag tag = stack.getOrCreateTag();
                if (tag.contains("content"))
                {
                    BluePrintInfo info = BluePrintRegistry.getInfo(tag.getString("content"));
                    if (info != null)
                    {
                        Minecraft.getInstance().setScreen(new BluePrintScreen(LocalNBTReader.getCompoundTag(info.materialNBT()), LocalNBTReader.getCompoundTag(info.resultNBT())));
                    }
                }
            }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide)
        {
            handleClientInteraction(player, stack, null);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (level.isClientSide && player != null)
        {
            handleClientInteraction(player, context.getItemInHand(), context.getClickedPos());
        }
        return InteractionResult.SUCCESS;
    }

    private boolean isPlacing(ItemStack stack)
    {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.getBoolean("isPlacing");
    }
}
