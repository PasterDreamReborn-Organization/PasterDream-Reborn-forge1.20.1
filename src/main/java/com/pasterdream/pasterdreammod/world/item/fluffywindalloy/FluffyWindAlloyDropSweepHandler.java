package com.pasterdream.pasterdreammod.world.item.fluffywindalloy;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 萦风合金工具被动 · 风卷：手持工具挖掘方块时，掉落物被风卷到玩家身边（持续生效）。
 * <p>
 * 通过 BreakEvent 在方块真正被破坏时触发，延迟 1 tick 等待掉落物生成后再吹向玩家。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class FluffyWindAlloyDropSweepHandler {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player instanceof FakePlayer) return; // 自动化假玩家不享受加成
        if (!(player.getMainHandItem().getItem() instanceof FluffyWindAlloyTool)) return;
        if (!(event.getLevel() instanceof Level level)) return;
        FluffyWindAlloyToolHelper.sweepDropsToPlayer(level, event.getPos(), player);
    }
}