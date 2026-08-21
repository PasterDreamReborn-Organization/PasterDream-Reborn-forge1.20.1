package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class HolyGrailBuffMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.hasEffect(ModEffects.HOLY_GRAIL.get())) {
            self.removeEffect(MobEffects.HUNGER);
            self.removeEffect(MobEffects.CONFUSION);
            self.removeEffect(MobEffects.BLINDNESS);
            self.removeEffect(MobEffects.WITHER);
            self.removeEffect(MobEffects.POISON);
            self.removeEffect(MobEffects.WEAKNESS);
        }
    }
}
