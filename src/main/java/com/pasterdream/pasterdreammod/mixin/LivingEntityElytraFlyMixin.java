package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 风行者效果：无需鞘翅即可维持鞘翅飞行
 * 重定向 LivingEntity#updateFallFlying 中对 canElytraFly / elytraFlightTick 的判定。
 * 不覆盖 IForgeItemStack 接口默认方法，避免与 ironsspellbooks 的 IItemExtensionMixin 冲突。
 */
@Mixin(LivingEntity.class)
public class LivingEntityElytraFlyMixin {

    @Redirect(method = "updateFallFlying",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;canElytraFly(Lnet/minecraft/world/entity/LivingEntity;)Z"))
    private boolean pd$windRunnerCanElytraFly(ItemStack stack, LivingEntity entity) {
        return stack.canElytraFly(entity) || entity.hasEffect(ModEffects.WIND_RUNNER.get());
    }

    @Redirect(method = "updateFallFlying",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;elytraFlightTick(Lnet/minecraft/world/entity/LivingEntity;I)Z"))
    private boolean pd$windRunnerElytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        return stack.elytraFlightTick(entity, flightTicks) || entity.hasEffect(ModEffects.WIND_RUNNER.get());
    }
}