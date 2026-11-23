package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.event.DyedreamWandererTrades;
import com.pasterdream.pasterdreammod.event.ModWandererTrades;
import net.minecraft.world.entity.npc.WanderingTrader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 游商交易在生成时的两处接管：
 * <ul>
 *   <li><b>HEAD + cancellable</b>：染梦世界生成的游商改用独立交易表（{@link DyedreamWandererTrades}）。
 *       原版 {@code WandererTradesEvent} 是启动时一次性构建的全局静态表，拿不到实体/维度上下文。</li>
 *   <li><b>TAIL</b>：追加模式下，在原版交易生成完后从 {@link ModWandererTrades#TRADES} 随机抽 n 条
 *       追加到 offers 末尾。注入模式不追加。</li>
 * </ul>
 * 用 {@code @Inject} 而不是 {@code @Overwrite}，避免破坏其他模组对 {@code updateTrades} 的注入。
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

    @Inject(method = "updateTrades", at = @At("TAIL"))
    private void pasterdream$appendModTrades(CallbackInfo ci) {
        WanderingTrader trader = (WanderingTrader) (Object) this;
        if (!Config.wanderingTraderTradesEnabled
                || ModWandererTrades.isInjectedToVanillaPool()
                || DyedreamWandererTrades.applies(trader)) {
            return;
        }
        ModWandererTrades.appendRandomOffers(trader.getOffers(), trader,
                ModWandererTrades.TRADES, Config.wanderingTraderTradesAppendCount);
    }
}
