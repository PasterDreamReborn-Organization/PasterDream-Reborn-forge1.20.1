package com.pasterdream.pasterdreammod.world.item.debugtool;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolBlockEditorMenu;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolItemEditorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DebugToolItem extends Item
{
    public DebugToolItem()
    {
        super(new Properties().stacksTo(1).rarity(ModRarities.MIRACLE));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.debug_tool.我将赐予你操控万物的能力"));
        tooltip.add(Component.translatable("tooltip.pasterdream.debug_tool.拿在手上右键点击打开屏幕"));
        tooltip.add(Component.translatable("tooltip.pasterdream.debug_tool.2884omgpy的专属遗物。"));
        tooltip.add(Component.translatable("tooltip.pasterdream.debug_tool.PasterDream: Reborn 程序"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide)
        {
            NetworkHooks.openScreen((ServerPlayer) player, new MenuProvider()
            {
                @Override
                public Component getDisplayName()
                {
                    return Component.translatable("gui." + PasterDreamMod.MOD_ID + ".debug_tool_item_editor");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
                {
                    return new DebugToolItemEditorMenu(id, inventory);
                }
            });
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Player player = context.getPlayer();
        if (player == null)
        {
            return InteractionResult.PASS;
        }

        Level level = context.getLevel();
        BlockPos blockPosition = context.getClickedPos();

        if (!level.isClientSide)
        {
            NetworkHooks.openScreen((ServerPlayer) player, new MenuProvider()
            {
                @Override
                public Component getDisplayName()
                {
                    return Component.translatable("gui." + PasterDreamMod.MOD_ID + ".debug_tool_block_editor");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
                {
                    return new DebugToolBlockEditorMenu(id, inventory, blockPosition);
                }
            },friendlyByteBuf ->
            {
                friendlyByteBuf.writeBlockPos(blockPosition);

                BlockEntity blockEntity = level.getBlockEntity(blockPosition);
                friendlyByteBuf.writeBoolean(blockEntity != null);
                if (blockEntity != null)
                {
                    friendlyByteBuf.writeNbt(blockEntity.saveWithId());
                }
            });
        }

        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand)
    {
        if (!player.level().isClientSide)
        {

        }

        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }
}
