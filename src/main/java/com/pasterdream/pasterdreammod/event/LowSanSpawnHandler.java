package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.helper.ShadowDifficultyHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 低理智暗影生物生成处理器。
 * 独立于状态效果系统，仅依据 SAN 比率 + 配置文件决定是否刷怪。
 * SAN 系统关闭时同步停止生成。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class LowSanSpawnHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        if (!player.isAlive() || player.isSpectator()) return;
        if (!SanHelper.getIsSanEnabled(player)) return;

        player.getCapability(ModCapabilities.SAN).ifPresent(cap -> {
            double ratio = cap.getSanValue() / SanHelper.getPlayerMaxSanEffective(player);

            int tier = ShadowDifficultyHelper.getDifficulty(player);
            double highThreshold = getValue(Config.lowSanSpawnHighThresholds, tier, 0.825);
            if (ratio >= highThreshold) {
                ShadowDifficultyHelper.tryLowSanSpawn(player, "high");
            } else if (ratio >= Config.sanLethargyLowerThreshold) {
                ShadowDifficultyHelper.tryLowSanSpawn(player, "medium");
            } else if (ratio >= Config.sanTranceLowerThreshold) {
                ShadowDifficultyHelper.tryLowSanSpawn(player, "low");
            } else {
                ShadowDifficultyHelper.tryLowSanSpawn(player, "critical");
            }
        });
    }

    private static double getValue(java.util.List<? extends Double> list, int index, double fallback) {
        if (list == null || list.isEmpty()) return fallback;
        if (index < list.size()) return list.get(index);
        return list.get(list.size() - 1);
    }
}
