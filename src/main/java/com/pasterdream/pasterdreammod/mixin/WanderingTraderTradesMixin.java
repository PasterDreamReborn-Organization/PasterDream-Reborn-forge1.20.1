package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.event.DyedreamWandererTrades;
import net.minecraft.world.entity.npc.WanderingTrader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 让染梦世界生成的流浪商人改用独立交易表（{@link DyedreamWandererTrades}）。
 * <p>
 * 原版 {@code WandererTradesEvent} 是启动时一次性构建的全局静态表，拿不到实体/维度上下文，
 * 因此这里注入 {@code WanderingTrader#updateTrades}：仅当游商位于染梦世界时短路原版逻辑，
 * 从独立表填充 offers；其他维度仍走原版逻辑（全局池，含 13 条注入）。
 * <p>
 * 用 {@code @Inject(HEAD, cancellable)} 而不是 {@code @Overwrite}，避免破坏其他模组对
 * {@code updateTrades} 的注入。
 */
@Mixin(WanderingTrader.class)
public class WanderingTraderTradesMixin {

    @Inject(method = "updateTrades", at = @At("HEAD"), cancellable = true)
    private void pasterdream$useDyedreamTrades(CallbackInfo ci) {
        WanderingTrader trader = (WanderingTrader) (Object) this;
        if (!DyedreamWandererTrades.applies(trader)) {
            return;
        }
        DyedreamWandererTrades.fillOffers(trader);
        ci.cancel();
    }
}
