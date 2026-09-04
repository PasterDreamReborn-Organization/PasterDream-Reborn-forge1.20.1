package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 纷争标记效果：使被标记实体渲染发光轮廓（类似原版发光效果）。
 * 在 LivingEntity#isCurrentlyGlowing 判定中追加 CONFLICT_MARK 效果检查，
 * 复用原版发光状态的同步（updateGlowingStatus -> 共享标志位 FLAG_GLOWING）与描边渲染链路。
 */
@Mixin(LivingEntity.class)
public class ConflictMarkGlowMixin {

    @Inject(method = "isCurrentlyGlowing", at = @At("RETURN"), cancellable = true)
    private void pd$conflictMarkIsGlowing(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            return;
        }
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.hasEffect(ModEffects.CONFLICT_MARK.get())) {
            cir.setReturnValue(true);
        }
    }
}
