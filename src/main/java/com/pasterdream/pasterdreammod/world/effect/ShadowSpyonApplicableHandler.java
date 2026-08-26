package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 兼容处理：保证「暗影窥视」效果总能被施加到玩家身上。
 * 神秘遗物（Enigmatic Legacy）的非欧立方（Non-Euclidean Cube）、神秘遗物、虚空珍珠等饰品
 * 会在 {@link MobEffectEvent.Applicable} 中把非增益效果的结果设为 DENY，
 * 从而阻止暗影窥视的给予。这里以 LOWEST 优先级把暗影窥视的结果改回 ALLOW，
 * 因为 Forge 事件总线会调用所有监听器、且 LOWEST 最后执行，故可覆盖其它模组的 DENY。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class ShadowSpyonApplicableHandler {

    private ShadowSpyonApplicableHandler() {
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEffectApplicable(MobEffectEvent.Applicable event) {
        MobEffect effect = event.getEffectInstance().getEffect();
        if (effect == ModEffects.SHADOW_SPYON.get()) {
            event.setResult(Event.Result.ALLOW);
        }
    }
}
