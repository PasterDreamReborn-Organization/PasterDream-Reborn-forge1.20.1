package com.pasterdream.pasterdreammod.capability.meltdreamenergy;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.network.meltdreamenergy.MaxMeltDreamEnergySyncPacket;
import com.pasterdream.pasterdreammod.network.meltdreamenergy.MeltDreamEnergySyncPacket;
import net.minecraft.server.level.ServerPlayer;

import java.util.concurrent.atomic.AtomicReference;

public class MeltDreamEnergyHelper
{
    public static void setPlayerMeltDreamEnergyAndSync(ServerPlayer player, double meltDreamEnergyValue)
    {
        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability ->
        {
            capability.setMeltDreamEnergy(meltDreamEnergyValue);
            MeltDreamEnergySyncPacket.sendToPlayer(player, capability);
        });
    }

    public static void addPlayerMeltDreamEnergyAndSync(ServerPlayer player, double meltDreamEnergyValue)
    {
        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability ->
        {
            capability.addMeltDreamEnergy(meltDreamEnergyValue);
            MeltDreamEnergySyncPacket.sendToPlayer(player, capability);
        });
    }

    public static double getPlayerMeltDreamEnergy(ServerPlayer player)
    {
        AtomicReference<Double> meltDreamEnergyValue = new AtomicReference<>(null);
        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability ->
        {
            meltDreamEnergyValue.set(capability.getMeltDreamEnergy());
        });
        return meltDreamEnergyValue.get();
    }

    public static void setPlayerMeltDreamEnergyIsNeed(ServerPlayer player, boolean isNeed)
    {
        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability ->
        {
            capability.setIsOrNotNeedConsumeDreamEnergy(isNeed);
        });
    }

    public static boolean getPlayerMeltDreamEnergyIsNeed(ServerPlayer player)
    {
        AtomicReference<Boolean> isNeed = new AtomicReference<>(null);
        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability ->
        {
            isNeed.set(capability.getIsOrNotNeedConsumeDreamEnergy());
        });
        return isNeed.get();
    }

    public static void setPlayerMeltDreamEnergyConsumeDoubled(ServerPlayer player, boolean isConsumeDoubled)
    {
        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability ->
        {
            capability.setIsConsumeDoubled(isConsumeDoubled);
        });
    }

    public static void setPlayerMaxMeltDreamEnergyAndSync(ServerPlayer player, double meltDreamEnergyValue)
    {
        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability ->
        {
            capability.setMaxMeltDreamEnergy(meltDreamEnergyValue);
            MaxMeltDreamEnergySyncPacket.sendToPlayer(player, capability);
        });
    }

    public static void addPlayerMaxMeltDreamEnergyAndSync(ServerPlayer player, double meltDreamEnergyValue)
    {
        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability ->
        {
            capability.addMaxMeltDreamEnergy(meltDreamEnergyValue);
            MaxMeltDreamEnergySyncPacket.sendToPlayer(player, capability);
        });
    }

    public static double getPlayerMaxMeltDreamEnergy(ServerPlayer player)
    {
        AtomicReference<Double> maxMeltDreamEnergyValue = new AtomicReference<>(null);
        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability ->
        {
            maxMeltDreamEnergyValue.set(capability.getMaxMeltDreamEnergy());
        });
        return maxMeltDreamEnergyValue.get();
    }
}
