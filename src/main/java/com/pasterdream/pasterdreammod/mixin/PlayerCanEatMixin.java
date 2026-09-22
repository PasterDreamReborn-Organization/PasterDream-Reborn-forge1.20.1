package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 料理（cook）效果：拥有该效果时，满饱食度也能进食任何食物（含普通食物），
 * 不再受原版 {@code canEat} 的 {@code canAlwaysEat} 限制。
 *
 * <p>用 {@code @Inject(HEAD, cancellable)} 短路，保留原方法体与其他模组的注入，
 * 避免 {@code @Overwrite} 冲突。溢出结算见 {@code event/CookOverflowHandler}，
 * 规则详见 {@code document/rule/program/料理状态效果.md}。
 */
@Mixin(Player.class)
public class PlayerCanEatMixin
{
    @Inject(method = "canEat", at = @At("HEAD"), cancellable = true, remap = true)
    private void pasterdream$cookAlwaysEat(boolean canAlwaysEat, CallbackInfoReturnable<Boolean> cir)
    {
        Player self = (Player) (Object) this;
        if (self.hasEffect(ModEffects.COOK.get()))
        {
            cir.setReturnValue(true);
        }
    }
}
