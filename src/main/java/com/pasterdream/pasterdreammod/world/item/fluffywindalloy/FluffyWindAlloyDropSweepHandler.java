package com.pasterdream.pasterdreammod.world.item.fluffywindalloy;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 萦风合金被动 · 风卷：手持萦风合金工具/剑时，掉落物被风卷到玩家身边（持续生效）。
 * <p>
 * 1. 方块掉落：通过 BreakEvent 在方块真正被破坏时触发，延迟 1 tick 等待掉落物生成后再吹向玩家。
 * 2. 击杀掉落：通过 LivingDropsEvent 在生物死亡掉落生成前把掉落物吹向玩家。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class FluffyWindAlloyDropSweepHandler {

    private static boolean isWindAlloyHeld(Player player) {
        var item = player.getMainHandItem().getItem();
        return item instanceof FluffyWindAlloyTool || item instanceof FluffyWindAlloySwordItem;
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player instanceof FakePlayer) return; // 自动化假玩家不享受加成
        if (!isWindAlloyHeld(player)) return;
        if (!(event.getLevel() instanceof Level level)) return;
        FluffyWindAlloyToolHelper.sweepDropsToPlayer(level, event.getPos(), player);
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        if (player instanceof FakePlayer) return; // 自动化假玩家不享受加成
        if (!isWindAlloyHeld(player)) return;
        Vec3 target = player.getEyePosition(1.0F);
        for (ItemEntity drop : event.getDrops()) {
            if (!drop.isAlive()) continue;
            FluffyWindAlloyToolHelper.blowDropToPlayer(drop, target);
        }
    }
}