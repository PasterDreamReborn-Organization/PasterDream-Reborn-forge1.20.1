package com.pasterdream.pasterdreammod.world.item.meltdreamcrystalchestresettool;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.meltdreamcrystalchest.MeltDreamCrystalChestResetCooldownSyncPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

public final class MeltDreamCrystalChestResetCooldown
{
    private static final String LAST_RESET_GAME_TIME_TAG = "pasterdream:melt_dream_crystal_chest_reset_time";

    private MeltDreamCrystalChestResetCooldown()
    {
    }

    public static long getRemainingTicks(ServerPlayer player)
    {
        long cooldownTicks = getCooldownTicks();
        if (cooldownTicks <= 0L)
        {
            return 0L;
        }

        CompoundTag data = player.getPersistentData();
        if (!data.contains(LAST_RESET_GAME_TIME_TAG))
        {
            return 0L;
        }

        long elapsedTicks = getGameTime(player) - data.getLong(LAST_RESET_GAME_TIME_TAG);
        return Math.max(0L, cooldownTicks - elapsedTicks);
    }

    public static void markReset(ServerPlayer player)
    {
        player.getPersistentData().putLong(LAST_RESET_GAME_TIME_TAG, getGameTime(player));
        syncToClient(player);
    }

    public static void clear(ServerPlayer player)
    {
        player.getPersistentData().remove(LAST_RESET_GAME_TIME_TAG);
        syncToClient(player);
    }

    public static void syncToClient(ServerPlayer player)
    {
        ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                new MeltDreamCrystalChestResetCooldownSyncPacket((int) Math.min(Integer.MAX_VALUE, getRemainingTicks(player))));
    }

    public static void copyOnPlayerClone(Player original, Player newPlayer)
    {
        CompoundTag originalData = original.getPersistentData();
        if (!originalData.contains(LAST_RESET_GAME_TIME_TAG))
        {
            return;
        }

        newPlayer.getPersistentData().putLong(LAST_RESET_GAME_TIME_TAG, originalData.getLong(LAST_RESET_GAME_TIME_TAG));
        originalData.remove(LAST_RESET_GAME_TIME_TAG);
    }

    public static String formatRemainingTicks(long ticks)
    {
        long totalSeconds = (ticks + 19L) / 20L;
        return String.format("%d:%02d", totalSeconds / 60L, totalSeconds % 60L);
    }

    private static long getCooldownTicks()
    {
        return (long) Config.meltDreamCrystalChestResetCooldownSeconds * 20L;
    }

    private static long getGameTime(ServerPlayer player)
    {
        return player.getServer().overworld().getGameTime();
    }
}
