package com.pasterdream.pasterdreammod.world.item.meltdreamtool;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.network.meltdreamenergy.MeltDreamEnergySyncPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 融梦水晶工具共享逻辑 —— 手持时消耗融梦能量修复耐久。
 */
final class MeltDreamToolHelper {

    /**
     * 手持时每 10 tick 消耗 0.01 融梦能量修复 1 点耐久。
     * 创造模式 / 无消耗标记时不扣能量。
     */
    static void onInventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        // 仅手持选中、服务端、玩家、每 10 tick、有损伤
        if (!selected || level.isClientSide || !(entity instanceof Player player)) return;
        if (player.tickCount % 10 != 0) return;
        if (stack.getDamageValue() < 1) return;

        double cost = 0.01;
        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(energy -> {
            boolean free = energy.getIsOrNotNeedConsumeDreamEnergy() || player.isCreative();
            if (free || energy.getMeltDreamEnergy() > cost) {
                if (!free) {
                    energy.addMeltDreamEnergy(-cost);
                    MeltDreamEnergySyncPacket.sendToPlayer(player, energy);
                }
                // 恢复耐久；极端情况保护
                if (stack.hurt(-1, RandomSource.create(), null)) {
                    stack.shrink(1);
                    stack.setDamageValue(0);
                }
            }
        });
    }

    static void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_tool.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_tool.2"));
    }
}
