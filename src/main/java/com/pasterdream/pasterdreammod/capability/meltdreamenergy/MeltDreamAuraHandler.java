package com.pasterdream.pasterdreammod.capability.meltdreamenergy;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 融梦光环处理器：每 tick 读取 MELT_DREAM_VARIABILITY 属性（每分钟变化量），
 * 转换为每 tick 变化量并累加到融梦能量。与 {@code SanAuraHandler} 驱动 SAN 的方式一致。
 * <p>
 * 数据流：装备/效果 → MELT_DREAM_VARIABILITY 属性 → 每 tick 转化率 → MeltDreamEnergy Capability
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class MeltDreamAuraHandler {

    /** 将"每分钟变化量"转为"每 tick 变化量"的除数 */
    private static final double TICKS_PER_MINUTE = 1200.0;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) return;
        if (!player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).isPresent()) return;
        if (player.isSpectator()) return;

        double rate = player.getAttributeValue(ModAttributes.MELT_DREAM_VARIABILITY.get());
        if (rate == 0.0) return;

        MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(player, rate / TICKS_PER_MINUTE);
    }
}
