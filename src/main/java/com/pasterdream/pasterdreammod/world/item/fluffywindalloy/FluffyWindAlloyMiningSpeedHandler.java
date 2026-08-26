package com.pasterdream.pasterdreammod.world.item.fluffywindalloy;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.network.fluffywindalloy.WindAlloyMiningSpeedSyncPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 萦风合金工具被动 · 疾风过境：
 * 移动速度越快，挖掘速度越快，挖掘速度 = 基础 × (1 + 移动速度 × 4)。
 * <p>
 * 服务端用权威移动速度计算倍率并同步给客户端，客户端用同步值做裂纹动画，
 * 保证两端挖掘速度一致，避免"方块挖碎后回弹"。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class FluffyWindAlloyMiningSpeedHandler {

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (player instanceof FakePlayer) return; // 自动化假玩家不享受加成
        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof FluffyWindAlloyTool)) return;

        float multiplier;
        if (player.level().isClientSide()) {
            multiplier = WindAlloyMiningSpeedSyncPacket.getClientMultiplier();
        } else {
            multiplier = FluffyWindAlloyToolHelper.computeMiningMultiplier(player);
            WindAlloyMiningSpeedSyncPacket.sendToPlayer(multiplier, (ServerPlayer) player);
        }

        if (multiplier > 1.0F) {
            event.setNewSpeed(event.getNewSpeed() * multiplier);
        }
    }
}