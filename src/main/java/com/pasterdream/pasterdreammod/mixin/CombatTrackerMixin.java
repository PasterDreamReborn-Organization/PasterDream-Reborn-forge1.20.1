package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.world.dimension.LampShadowDimension;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 玩家在灯影之下维度死亡时，用自定义信息替换原版死亡播报。
 * getDeathMessage() 同时被死亡界面（ClientboundPlayerCombatKillPacket）与
 * 全服广播（ServerPlayer#die 中的 broadcastSystemMessage）复用，在此处替换即可同时生效。
 */
@Mixin(CombatTracker.class)
public class CombatTrackerMixin {

    @Shadow @Final private LivingEntity mob;

    @Inject(method = "getDeathMessage", at = @At("HEAD"), cancellable = true)
    private void pasterdream$customLampShadowDeathMessage(CallbackInfoReturnable<Component> cir) {
        if (!(this.mob instanceof Player)) return;
        if (!this.mob.level().dimension().equals(LampShadowDimension.LAMP_SHADOW_WORLD)) return;
        cir.setReturnValue(Component.translatable("death.pasterdream.lamp_shadow_world", this.mob.getDisplayName()));
    }
}
