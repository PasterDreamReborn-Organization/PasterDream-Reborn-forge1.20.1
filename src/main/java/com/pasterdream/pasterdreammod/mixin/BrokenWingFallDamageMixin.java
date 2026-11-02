package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * 折翼（broken_wing）debuff：每级使摔落伤害提升 50%。
 */
@Mixin(LivingEntity.class)
public abstract class BrokenWingFallDamageMixin {

    @ModifyVariable(method = "causeFallDamage", at = @At("HEAD"), argsOnly = true, index = 1)
    private float pasterdream$amplifyFallDamage(float pMultiplier) {
        LivingEntity self = (LivingEntity) (Object) this;
        MobEffectInstance brokenWing = self.getEffect(ModEffects.BROKEN_WING.get());
        if (brokenWing != null) {
            int level = brokenWing.getAmplifier() + 1;
            return pMultiplier * (1.0F + 0.5F * level);
        }
        return pMultiplier;
    }
}
