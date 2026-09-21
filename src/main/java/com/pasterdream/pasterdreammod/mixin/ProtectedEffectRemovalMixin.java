package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.world.effect.ProtectedRemovalEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

/**
 * 「不可清除」状态效果保护（{@link ProtectedRemovalEffect}，如暗影窥视、回避）。
 * <ul>
 *   <li>给予侧：{@link ProtectedRemovalEffect#forceApplicableWhenAffected()} 为 true 的效果，
 *       拦截 {@code LivingEntity#canBeAffected} 强制可施加，直接跳过 Forge 的
 *       {@code MobEffectEvent.Applicable}，避免被其它 mod（如神秘遗物的非欧立方）以 DENY 阻止
 *       （无需依赖事件监听器优先级）。</li>
 *   <li>移除侧：防止被外部清除逻辑移除（牛奶、totem、{@code /effect clear}、其它 mod 的清除）。
 *       只有经 {@link ProtectedRemovalEffect#consumeRemovalAllowance} 返回 true 的授权后，
 *       入侵完成 / 竞技场结束 / 成功回避等正当移除才能生效；效果自身计时到期不受影响。</li>
 * </ul>
 */
@Mixin(LivingEntity.class)
public class ProtectedEffectRemovalMixin {

    @Inject(method = "canBeAffected", at = @At("HEAD"), cancellable = true, remap = true)
    private void pasterdream$forceCanBeAffected(MobEffectInstance effectInstance, CallbackInfoReturnable<Boolean> cir) {
        if (effectInstance != null
                && effectInstance.getEffect() instanceof ProtectedRemovalEffect protectedEffect
                && protectedEffect.forceApplicableWhenAffected()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "removeEffect", at = @At("HEAD"), cancellable = true, remap = true)
    private void pasterdream$protectRemoveEffect(MobEffect effect, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (effect instanceof ProtectedRemovalEffect protectedEffect
                && !protectedEffect.consumeRemovalAllowance(self)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "removeAllEffects", at = @At("HEAD"), cancellable = true, remap = true)
    private void pasterdream$protectRemoveAllEffects(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.level().isClientSide()) {
            return;
        }
        List<MobEffect> effects = new ArrayList<>(self.getActiveEffectsMap().keySet());
        boolean hasProtected = false;
        for (MobEffect effect : effects) {
            if (effect instanceof ProtectedRemovalEffect) {
                hasProtected = true;
                break;
            }
        }
        if (!hasProtected) {
            return; // 无受保护效果：交给原方法
        }
        boolean removed = false;
        for (MobEffect effect : effects) {
            if (effect instanceof ProtectedRemovalEffect protectedEffect
                    && !protectedEffect.hasRemovalAllowance(self)) {
                continue; // 未授权：保留受保护效果
            }
            if (self.removeEffect(effect)) {
                removed = true;
            }
        }
        cir.setReturnValue(removed);
    }
}
