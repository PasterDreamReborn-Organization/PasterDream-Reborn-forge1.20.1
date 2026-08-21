package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffect.class)
public class GoldenrodTeaBuffMixin {

    @Inject(method = "isDurationEffectTick", at = @At("HEAD"), cancellable = true, remap = true)
    private void onIsDurationEffectTick(int duration, int amplifier, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this == ModEffects.GOLDENROD_TEA.get()) {
            cir.setReturnValue(duration % 10 == 0);
        }
    }

    @Inject(method = "applyEffectTick", at = @At("HEAD"), remap = true)
    private void onApplyEffectTick(LivingEntity entity, int amplifier, CallbackInfo ci) {
        if ((Object) this == ModEffects.GOLDENROD_TEA.get()) {
            entity.removeEffect(MobEffects.HUNGER);
            entity.removeEffect(MobEffects.CONFUSION);
        }
    }
}
