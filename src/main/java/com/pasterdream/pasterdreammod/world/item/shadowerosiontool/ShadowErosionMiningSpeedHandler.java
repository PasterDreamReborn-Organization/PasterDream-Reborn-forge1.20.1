package com.pasterdream.pasterdreammod.world.item.shadowerosiontool;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.network.shadowerosion.ShadowErosionMiningSpeedSyncPacket;
import com.pasterdream.pasterdreammod.tag.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 影蚀工具特性：
 * 1. 暗影类方块（SHADOW_EROSION_TOOL_CAN_BOOST 标签）固定 +50% 挖掘速度；
 * 2. 亮度越低，挖掘速度越快。两项独立相乘。
 * 服务端用权威亮度计算倍率并同步给客户端，客户端用同步值做裂纹动画，
 * 保证两端挖掘速度一致，避免“方块挖碎后回弹”。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class ShadowErosionMiningSpeedHandler {

    private static final int NO_BONUS_LIGHT = 14;     // 亮度 >= 14 无加成（火把亮度约 14）
    private static final float BONUS_DIVISOR = 28.0F; // 亮度加成分母（全黑时 +0.5 倍）
    private static final float SHADOW_BLOCK_BONUS = 1.5F; // 暗影类方块固定 +50%

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (player instanceof FakePlayer) return; // 自动化假玩家不享受加成
        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof ShadowErosionTool)) return;

        float multiplier;
        if (player.level().isClientSide()) {
            // 客户端：用服务端同步过来的倍率
            multiplier = ShadowErosionMiningSpeedSyncPacket.getClientMultiplier();
        } else {
            // 服务端：暗影类方块固定加成 + 亮度加成，两者独立相乘
            BlockPos pos = event.getPosition().orElse(null);
            if (pos == null) return;
            float shadowMultiplier = event.getState().is(ModBlockTags.SHADOW_EROSION_TOOL_CAN_BOOST) ? SHADOW_BLOCK_BONUS : 1.0F;
            int light = player.level().getMaxLocalRawBrightness(pos);
            float lightBonus = (NO_BONUS_LIGHT - light) / BONUS_DIVISOR;
            float lightMultiplier = lightBonus > 0.0F ? 1.0F + lightBonus : 1.0F;
            multiplier = shadowMultiplier * lightMultiplier;
            // 同步给客户端，保证裂纹动画与服务端破坏判定一致
            ShadowErosionMiningSpeedSyncPacket.sendToPlayer(multiplier, (ServerPlayer) player);
        }

        if (multiplier > 1.0F) {
            event.setNewSpeed(event.getNewSpeed() * multiplier);
        }
    }
}
