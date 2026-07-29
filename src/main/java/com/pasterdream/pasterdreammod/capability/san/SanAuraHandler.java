package com.pasterdream.pasterdreammod.capability.san;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.helper.sanbiomeratemanager.SanBiomeRateManager;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 理智光环处理器：每 tick 评估环境修正与属性叠加，驱动 San 值变化。
 * <p>
 * 数据流：装备/效果 → SAN_VARIABILITY 属性 → 每 tick 转化率 → San Capability
 * → 根据 San 百分比施加对应的阈值效果。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class SanAuraHandler {

    /** 将"每分钟变化量"转为"每 tick 变化量"的除数 */
    private static final double TICKS_PER_MINUTE = 1200.0;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) {
            return;
        }
        if (!SanHelper.getIsSanEnabled(player)) return;
        if (player.isSpectator()) return;

        Level level = player.level();
        BlockPos pos = player.blockPosition();

        // 1. 读取属性叠加后的总变化率（每分钟变化量）
        double sanVariability = player.getAttributeValue(ModAttributes.SAN_VARIABILITY.get());
        double attributeRate = sanVariability / TICKS_PER_MINUTE;

        // 2. 群系修正（来自 data/pasterdream/san_biome_rates/）
        ResourceLocation biomeId = level.getBiome(pos).unwrapKey()
                .map(ResourceKey::location).orElse(null);
        double biomeRate = biomeId != null ? SanBiomeRateManager.getRate(biomeId) : 0.0;

        // 3. 光照修正：亮度 < 7 时 San 下降，亮度 > 7 时 San 回升
        int light = level.getMaxLocalRawBrightness(pos);
        double lightRate = (light - 7) * 0.0001;

        double totalRate = attributeRate + biomeRate + lightRate;
        if (totalRate != 0) {
            SanHelper.addPlayerSanAndSync(player, totalRate);
        }

        // 4. San 阈值效果
        player.getCapability(ModCapabilities.SAN).ifPresent(cap -> {
            double ratio = cap.getSanValue() / cap.getMaxSanValue();
            if (ratio >= Config.sanCheerUpThreshold) {
                player.addEffect(new MobEffectInstance(ModEffects.CHEER_UP_BUFF.get(), 20, 0, false, false));
            } else if (ratio < Config.sanLethargyUpperThreshold && ratio >= Config.sanLethargyLowerThreshold) {
                if (!player.getPersistentData().getBoolean("pasterdream:strawberry_san_aura")) {
                    player.addEffect(new MobEffectInstance(ModEffects.LETHARGY_BUFF.get(), 20, 0, false, false));
                }
            } else if (ratio < Config.sanLethargyLowerThreshold && ratio >= Config.sanTranceLowerThreshold) {
                player.addEffect(new MobEffectInstance(ModEffects.TRANCE_BUFF.get(), 20, 0, false, false));
            } else if (ratio < Config.sanTranceLowerThreshold) {
                int lv = ratio < Config.sanInsandLv3Threshold ? 2 : ratio < Config.sanInsandLv2Threshold ? 1 : 0;
                player.addEffect(new MobEffectInstance(ModEffects.INSAND_BUFF.get(), 20, lv, false, false));
            }
        });
    }

    /**
     * 玩家死亡重生后，继承旧 capability 数据并将 San 值恢复到当前上限。
     */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath() || !(event.getEntity() instanceof ServerPlayer newPlayer)) {
            return;
        }
        event.getOriginal().reviveCaps();
        event.getOriginal().getCapability(ModCapabilities.SAN).ifPresent(oldCap ->
            newPlayer.getCapability(ModCapabilities.SAN).ifPresent(newCap -> {
                newCap.copyValueFromOtherSan(oldCap);
                newCap.setSanValue(newCap.getMaxSanValue());
            })
        );
        event.getOriginal().invalidateCaps();
    }
}
