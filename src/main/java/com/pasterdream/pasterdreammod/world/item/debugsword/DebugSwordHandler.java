package com.pasterdream.pasterdreammod.world.item.debugsword;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "pasterdream", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DebugSwordHandler
{
    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event)
    {
        Player player = event.getEntity();
        if (player.level().isClientSide)
        {
            return;
        }
        if (player.getMainHandItem().getItem() instanceof DebugSwordItem)
        {
            event.setCanceled(true);
            Entity target = event.getTarget();
            target.discard();
            player.sendSystemMessage(Component.translatable("已删除实体: " + target.getName().getString()));
        }
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event)
    {
        Player player = event.getEntity();
        if (!(player.getMainHandItem().getItem() instanceof DebugSwordItem))
        {
            return;
        }

        event.setCanceled(true);

        if (player.level().isClientSide)
        {
            if (Minecraft.getInstance().screen instanceof DebugBlockOptionsScreen)
            {
                return;
            }

            Minecraft.getInstance().setScreen(new DebugBlockOptionsScreen(event.getPos(), player, player.level()));
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event)
    {
        Player player = event.getEntity();
        if (player.level().isClientSide)
        {
            return;
        }
        if (player.getMainHandItem().getItem() instanceof DebugSwordItem)
        {
            event.setCanceled(true);
            Entity target = event.getTarget();
            CompoundTag tag = target.serializeNBT();
            player.sendSystemMessage(Component.translatable("实体 NBT:\n" + tag));
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        Player player = event.getEntity();
        if (player.level().isClientSide)
        {
            return;
        }
        if (player.getMainHandItem().getItem() instanceof DebugSwordItem)
        {
            event.setCanceled(true);
            Level level = event.getLevel();
            BlockPos pos = event.getPos();
            BlockState state = level.getBlockState(pos);
            BlockEntity blockEntity = level.getBlockEntity(pos);
            StringBuilder stringBuilder = new StringBuilder("BlockState:").append(state).append("\n");
            if (blockEntity != null)
            {
                stringBuilder.append("BlockEntity NBT:\n").append(blockEntity.serializeNBT());
            }
                else
                {
                    stringBuilder.append("无方块实体NBT。");
                }
            player.sendSystemMessage(Component.literal(stringBuilder.toString()));
        }
    }
}
