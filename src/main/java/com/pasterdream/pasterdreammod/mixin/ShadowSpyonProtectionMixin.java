package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.world.effect.ShadowSpyonEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

/**
 * 保护「暗影窥视」效果不被外部清除逻辑移除（牛奶、totem、/effect clear、其它 mod 的清除）。
 * 只有经 {@link ShadowSpyonEffect#allowRemoval(net.minecraft.world.entity.Entity)} 显式授权后，
 * 入侵完成 / 竞技场结束的正当移除才能生效；效果自身的计时到期不受影响。
 */
@Mixin(LivingEntity.class)
public class ShadowSpyonProtectionMixin {

    @Inject(method = "removeEffect", at = @At("HEAD"), cancellable = true, remap = true)
    private void pasterdream$protectRemoveEffect(MobEffect effect, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (effect == ModEffects.SHADOW_SPYON.get() && !ShadowSpyonEffect.consumeRemovalAllowance(self)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "removeAllEffects", at = @At("HEAD"), cancellable = true, remap = true)
    private void pasterdream$protectRemoveAllEffects(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.level().isClientSide()) {
            return;
        }
        MobEffect spy = ModEffects.SHADOW_SPYON.get();
        if (!self.hasEffect(spy)) {
            return;
        }
        if (ShadowSpyonEffect.consumeRemovalAllowance(self)) {
            return; // 已授权：交给原方法移除（含暗影窥视）
        }
        List<MobEffect> others = new ArrayList<>(self.getActiveEffectsMap().keySet());
        boolean removed = false;
        for (MobEffect effect : others) {
            if (effect != spy && self.removeEffect(effect)) {
                removed = true;
            }
        }
        cir.setReturnValue(removed);
    }
}
