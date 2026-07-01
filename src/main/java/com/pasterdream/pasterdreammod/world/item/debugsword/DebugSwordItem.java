package com.pasterdream.pasterdreammod.world.item.debugsword;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DebugSwordItem extends Item
{
    public DebugSwordItem()
    {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.左键实体：直接删除此实体"));
        tooltip.add(Component.translatable("tooltip.pasterdream.左键方块：模拟破坏此方块"));
        tooltip.add(Component.translatable("tooltip.pasterdream.右键实体：在聊天框打印此实体NBT"));
        tooltip.add(Component.translatable("tooltip.pasterdream.右键方块：在聊天框打印此方块BlockState和NBT"));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand)
    {
        if (!player.level().isClientSide)
        {
            CompoundTag tag = target.serializeNBT();
            player.sendSystemMessage(Component.translatable("实体NBT:\n" + tag));
        }
        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Player player = context.getPlayer();
        if (player == null)
        {
            return InteractionResult.FAIL;
        }

        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (!level.isClientSide)
        {
            BlockState state = level.getBlockState(pos);
            BlockEntity blockEntity = level.getBlockEntity(pos);
            StringBuilder stringBuilder = new StringBuilder("方块BlockState: ").append(state).append("\n");
            if (blockEntity != null)
            {
                stringBuilder.append("方块NBT:\n").append(blockEntity.serializeNBT());
            }
                else
                {
                    stringBuilder.append("此方块无NBT。");
                }
            player.sendSystemMessage(Component.literal(stringBuilder.toString()));
        }
        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }
}
