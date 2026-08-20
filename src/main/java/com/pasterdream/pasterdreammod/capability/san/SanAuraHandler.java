package com.pasterdream.pasterdreammod.capability.san;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.helper.sanbiomeratemanager.SanBiomeRateManager;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.world.item.StrawberryHeartItem;
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

import java.util.List;

/**
 * 理智光环处理器：每 tick 评估环境修正与属性叠加，驱动 San 值变化。
 * <p>
 * 数据流：装备/效果 → SAN_VARIABILITY 属性 → 每 tick 转化率 → San Capability
 * → 根据 San 百分比施加对应的阈值效果。
 * 物品对 SAN 的交互通过 {@link ISanModifier} 声明，本类不再硬编码具体物品检测。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class SanAuraHandler {

    /** 将"每分钟变化量"转为"每 tick 变化量"的除数 */
    private static final double TICKS_PER_MINUTE = 1200.0;
    /** 光照中性等级：低于此值 San 下降，高于此值 San 回升 */
    private static final int LIGHT_NEUTRAL_LEVEL = 7;
    /** 每偏离中性光照 1 级对应的每 tick SAN 变化率 */
    private static final double LIGHT_RATE_PER_LEVEL = 0.0001;
    /** 阈值效果的持续时间（tick） */
    private static final int THRESHOLD_EFFECT_DURATION = 20;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) return;
        if (!SanHelper.getIsSanEnabled(player)) return;
        if (player.isSpectator()) return;

        Level level = player.level();
        BlockPos pos = player.blockPosition();

        // 装备中的 SAN 修正器（护甲 + 饰品）
        List<ISanModifier> modifiers = SanHelper.getEquippedSanModifiers(player);
        boolean freezeSan = modifiers.stream().anyMatch(ISanModifier::freezesSan);
        boolean immuneNegative = modifiers.stream().anyMatch(ISanModifier::immuneToNegativeEffects);

        // 1. 属性变化率（每分钟变化量 → 每 tick）
        double attributeRate = player.getAttributeValue(ModAttributes.SAN_VARIABILITY.get()) / TICKS_PER_MINUTE;

        // 2. 群系修正
        ResourceLocation biomeId = level.getBiome(pos).unwrapKey().map(ResourceKey::location).orElse(null);
        double biomeRate = biomeId != null ? SanBiomeRateManager.getRate(biomeId) : 0.0;

        // 3. 光照修正
        double lightRate = (level.getMaxLocalRawBrightness(pos) - LIGHT_NEUTRAL_LEVEL) * LIGHT_RATE_PER_LEVEL;

        // 4. 环境变化率（群系 + 光照）+ 物品修正
        double envRate = biomeRate + lightRate;
        for (ISanModifier modifier : modifiers) {
            envRate = modifier.modifyEnvRate(envRate);
        }

        double totalRate = attributeRate + envRate;
        if (totalRate != 0 && !freezeSan) {
            SanHelper.addPlayerSanAndSync(player, totalRate);
        }

        // 5. SAN 阈值效果
        applyThresholdEffects(player, immuneNegative);
    }

    private static void applyThresholdEffects(ServerPlayer player, boolean immuneNegative) {
        player.getCapability(ModCapabilities.SAN).ifPresent(cap -> {
            double ratio = cap.getSanValue() / SanHelper.getPlayerMaxSanEffective(player);

            if (ratio >= Config.sanCheerUpThreshold) {
                player.addEffect(new MobEffectInstance(ModEffects.CHEER_UP_BUFF.get(),
                        THRESHOLD_EFFECT_DURATION, 0, false, false));
                return;
            }
            if (immuneNegative) return;

            if (ratio < Config.sanLethargyUpperThreshold && ratio >= Config.sanLethargyLowerThreshold) {
                if (!player.getPersistentData().getBoolean(StrawberryHeartItem.SAN_AURA_TAG)) {
                    player.addEffect(new MobEffectInstance(ModEffects.LETHARGY_BUFF.get(),
                            THRESHOLD_EFFECT_DURATION, 0, false, false));
                }
            } else if (ratio < Config.sanLethargyLowerThreshold && ratio >= Config.sanTranceLowerThreshold) {
                player.addEffect(new MobEffectInstance(ModEffects.TRANCE_BUFF.get(),
                        THRESHOLD_EFFECT_DURATION, 0, false, false));
            } else if (ratio < Config.sanTranceLowerThreshold) {
                int level = ratio < Config.sanInsandLv3Threshold ? 2 : ratio < Config.sanInsandLv2Threshold ? 1 : 0;
                player.addEffect(new MobEffectInstance(ModEffects.INSAND_BUFF.get(),
                        THRESHOLD_EFFECT_DURATION, level, false, false));
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
