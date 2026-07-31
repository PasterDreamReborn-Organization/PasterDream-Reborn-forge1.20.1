package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SandofTimeItem extends Item {

    private static final String TAG_COOLDOWN = "TimeCooldown";
    private static final String TAG_READY = "Ready";

    public SandofTimeItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    /**
     * 创建一个带有 TAG_READY=true 的时之沙 ItemStack，用于创造模式物品栏展示
     */
    public static ItemStack createReady(Item item) {
        ItemStack stack = new ItemStack(item);
        stack.getOrCreateTag().putBoolean(TAG_READY, true);
        return stack;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean(TAG_READY);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide) {
            long now = level.getGameTime();
            long lastUse = stack.getOrCreateTag().getLong(TAG_COOLDOWN);
            int cooldownTicks = Config.timeOfSandCooldownSeconds * 20;
            boolean ready = lastUse == 0 || (now - lastUse >= cooldownTicks);
            boolean currentFlag = stack.getOrCreateTag().getBoolean(TAG_READY);
            if (ready != currentFlag) {
                stack.getOrCreateTag().putBoolean(TAG_READY, ready);
            }
        }
        super.inventoryTick(stack, level, entity, slot, selected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            long now = level.getGameTime();
            long lastUse = stack.getOrCreateTag().getLong(TAG_COOLDOWN);
            int cooldownTicks = Config.timeOfSandCooldownSeconds * 20;

            if (now - lastUse < cooldownTicks) {
                long remainingTicks = cooldownTicks - (now - lastUse);
                int remainingSeconds = (int) Math.ceil(remainingTicks / 20.0);
                player.displayClientMessage(
                        Component.translatable("message.pasterdream.sand_of_time.cooldown", remainingSeconds), true);
                return InteractionResultHolder.fail(stack);
            }

            stack.getOrCreateTag().putLong(TAG_COOLDOWN, now);
            boolean isDay = level.isDay();
            long newTime = isDay ? 18000 : 1000;
            // DerivedLevelData.setDayTime() 是空方法，必须遍历所有维度（同 /time set 行为）
            for (ServerLevel sl : serverLevel.getServer().getAllLevels()) {
                sl.setDayTime(newTime);
            }
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.EVASION.get(), SoundSource.PLAYERS, 1.0f, 1.5f);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        int cooldownTime = Config.timeOfSandCooldownSeconds;
        tooltip.add(Component.translatable("tooltip.pasterdream.sand_of_time.1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.sand_of_time.2", cooldownTime).withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("tooltip.pasterdream.sand_of_time.3"));

        // 显示剩余冷却时间
        if (level != null) {
            long now = level.getGameTime();
            long lastUse = stack.getOrCreateTag().getLong(TAG_COOLDOWN);
            int cooldownTicks = cooldownTime * 20;
            long remainingTicks = cooldownTicks - (now - lastUse);

            if (remainingTicks > 0) {
                int remainingSeconds = (int) Math.ceil(remainingTicks / 20.0);
                tooltip.add(Component.translatable("tooltip.pasterdream.sand_of_time.4", remainingSeconds)
                        .withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.pasterdream.sand_of_time.5")
                        .withStyle(ChatFormatting.GREEN));
            }
        }
    }
}
