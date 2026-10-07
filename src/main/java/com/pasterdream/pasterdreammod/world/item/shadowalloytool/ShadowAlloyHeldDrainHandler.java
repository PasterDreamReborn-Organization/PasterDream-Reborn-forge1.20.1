package com.pasterdream.pasterdreammod.world.item.shadowalloytool;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 暗影合金工具持有代价：工具在<b>主手</b>时，每 {@link ShadowAlloyToolHelper#HELD_DRAIN_INTERVAL_TICKS} tick
 * 缓慢扣减 SAN（下限 20%）。仅主手生效，背包/副手不扣。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class ShadowAlloyHeldDrainHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer sp)) return;
        if (sp.isSpectator()) return;
        if (!SanHelper.getIsSanEnabled(sp)) return;
        if (!(sp.getMainHandItem().getItem() instanceof ShadowAlloyTool)) return;
        if (sp.tickCount % ShadowAlloyToolHelper.HELD_DRAIN_INTERVAL_TICKS != 0) return;

        ShadowAlloyToolHelper.drainSanWithFloor(sp, ShadowAlloyToolHelper.HELD_DRAIN_AMOUNT);
    }
}
