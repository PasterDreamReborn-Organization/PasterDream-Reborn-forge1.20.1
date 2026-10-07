package com.pasterdream.pasterdreammod.world.item.shadowalloytool;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 暗影合金工具攻击特性：
 * 1. 低 SAN 时按档位提升攻击伤害（作用于最终伤害，含暴击/附魔）；
 * 2. 每次命中扣减使用者 SAN（下限 20%）。
 * <p>
 * 攻击加成不能用 getAttributeModifiers（SAN 变化不会刷新属性缓存），
 * 因此在 LivingHurtEvent 里动态结算。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class ShadowAlloyAttackHandler {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        if (!(player instanceof ServerPlayer sp)) return;
        if (!(player.getMainHandItem().getItem() instanceof ShadowAlloyTool)) return;
        if (event.getSource().getDirectEntity() != player) return;

        float multiplier = ShadowAlloyToolHelper.getLowSanMultiplier(sp);
        if (multiplier > 1.0F) {
            event.setAmount(event.getAmount() * multiplier);
        }
        ShadowAlloyToolHelper.drainSanWithFloor(sp, ShadowAlloyToolHelper.ATTACK_DRAIN_AMOUNT);
    }
}
