package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(LivingEntity.class)
public class BrightButterflyCurioMixin {

    @Inject(method = "canBeAffected", at = @At("HEAD"), cancellable = true)
    private void onCanBeAffected(MobEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        if (effect.getEffect() != MobEffects.DARKNESS && effect.getEffect() != MobEffects.BLINDNESS) return;

        LivingEntity self = (LivingEntity) (Object) this;
        if (!(self instanceof Player player)) return;

        boolean hasCurio = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.BRIGHT_BUTTERFLY_CURIO.get()).isPresent())
                .orElse(false);

        if (hasCurio) {
            cir.setReturnValue(false);
        }
    }
}
