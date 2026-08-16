package com.pasterdream.pasterdreammod.capability.san;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.network.san.IsSanEnableSyncPacket;
import com.pasterdream.pasterdreammod.network.san.MaxSanSyncPacket;
import com.pasterdream.pasterdreammod.network.san.SanSyncPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SanHelper
{
    public static void setPlayerSanAndSync(ServerPlayer player, double sanValue)
    {
        player.getCapability(ModCapabilities.SAN).ifPresent(capability ->
        {
            capability.setSanValue(sanValue);
            SanSyncPacket.sendToPlayer(player, capability);
        });
    }

    public static void addPlayerSanAndSync(ServerPlayer player, double sanValue)
    {
        player.getCapability(ModCapabilities.SAN).ifPresent(capability ->
        {
            capability.addSanValue(sanValue);
            SanSyncPacket.sendToPlayer(player, capability);
        });
    }

    public static double getPlayerSan(ServerPlayer player)
    {
        AtomicReference<Double> sanValue = new AtomicReference<>(0.0);
        player.getCapability(ModCapabilities.SAN).ifPresent(capability ->
        {
            sanValue.set(capability.getSanValue());
        });
        return sanValue.get();
    }

    public static void setIsSanEnabled(ServerPlayer player, boolean isEnabled)
    {
        player.getCapability(ModCapabilities.SAN).ifPresent(capability ->
        {
            capability.setIsSanEnable(isEnabled);
            IsSanEnableSyncPacket.sendToPlayer(player, capability);
        });
    }

    public static boolean getIsSanEnabled(ServerPlayer player)
    {
        AtomicReference<Boolean> isEnabled = new AtomicReference<>(false);
        player.getCapability(ModCapabilities.SAN).ifPresent(capability ->
        {
            isEnabled.set(capability.getIsSanEnabled());
        });
        return isEnabled.get();
    }

    public static void setPlayerMaxSanAndSync(ServerPlayer player, double maxSanValue)
    {
        player.getCapability(ModCapabilities.SAN).ifPresent(capability ->
        {
            capability.setMaxSanValue(maxSanValue);
            MaxSanSyncPacket.sendToPlayer(player, capability);
        });
    }

    public static void addPlayerMaxSanAndSync(ServerPlayer player, double maxSanValue)
    {
        player.getCapability(ModCapabilities.SAN).ifPresent(capability ->
        {
            capability.addMaxSanValue(maxSanValue);
            MaxSanSyncPacket.sendToPlayer(player, capability);
        });
    }

    public static double getPlayerMaxSan(ServerPlayer player)
    {
        AtomicReference<Double> maxSanValue = new AtomicReference<>(0.0);
        player.getCapability(ModCapabilities.SAN).ifPresent(capability ->
        {
            maxSanValue.set(capability.getMaxSanValue());
        });
        return maxSanValue.get();
    }

    /**
     * 收集玩家已装备的 SAN 修正器（护甲槽 + Curios 饰品中实现 {@link ISanModifier} 的物品）。
     */
    public static List<ISanModifier> getEquippedSanModifiers(Player player)
    {
        List<ISanModifier> modifiers = new ArrayList<>();
        for (ItemStack stack : player.getArmorSlots())
        {
            if (stack.getItem() instanceof ISanModifier modifier)
            {
                modifiers.add(modifier);
            }
        }
        CuriosApi.getCuriosInventory(player).ifPresent(handler ->
                handler.findCurios(stack -> stack.getItem() instanceof ISanModifier)
                        .forEach(slotResult -> modifiers.add((ISanModifier) slotResult.stack().getItem())));
        return modifiers;
    }
}
