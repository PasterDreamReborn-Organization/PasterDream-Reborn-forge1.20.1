package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 风行者效果（客户端）：双击跳跃发起滑翔的启动判定
 * LocalPlayer#aiStep 中 canElytraFly 是短路求值，若此处不放行，
 * tryToStartFallFlying 不会被调用，客户端无法发起滑翔。
 */
@Mixin(LocalPlayer.class)
public class LocalPlayerElytraFlyMixin {

    @Redirect(method = "aiStep",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;canElytraFly(Lnet/minecraft/world/entity/LivingEntity;)Z"))
    private boolean pd$windRunnerCanElytraFly(ItemStack stack, LivingEntity entity) {
        return stack.canElytraFly(entity) || entity.hasEffect(ModEffects.WIND_RUNNER.get());
    }
}