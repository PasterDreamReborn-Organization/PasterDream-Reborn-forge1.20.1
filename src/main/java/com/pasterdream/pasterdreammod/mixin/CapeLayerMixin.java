package com.pasterdream.pasterdreammod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 佩戴风行者披风时隐藏原版披风，避免与自带 GEO 披风在背部重叠 / Z-fighting。
 *
 * <p>原版 {@code CapeLayer} 仅在胸甲槽为 {@code Items.ELYTRA} 时跳过披风渲染；
 * 本模组的披风是自定义胸甲，故用 {@code @Inject(HEAD, cancellable)} 短路（而非 {@code @Overwrite}），
 * 保留原方法体与其它模组的注入点。
 */
@Mixin(CapeLayer.class)
public class CapeLayerMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void pasterdream$hideCapeWhenWearingCloak(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                                                      AbstractClientPlayer player, float limbSwing, float limbSwingAmount,
                                                      float partialTick, float ageInTicks, float netHeadYaw, float headPitch,
                                                      CallbackInfo ci) {
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.WIND_RUNNER_CLOAK.get())) {
            ci.cancel();
        }
    }
}
