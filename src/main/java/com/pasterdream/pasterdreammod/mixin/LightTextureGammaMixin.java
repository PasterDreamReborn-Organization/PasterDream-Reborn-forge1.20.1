package com.pasterdream.pasterdreammod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(LightTexture.class)
public class LightTextureGammaMixin {

    @Unique
    private static final float GAMMA_OVERRIDE_VALUE = 10.0F;

    @ModifyExpressionValue(
            method = "updateLightTexture",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Double;floatValue()F",
                    ordinal = 1
            )
    )
    private float redirectOptionGet(float original) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            boolean hasCurio = CuriosApi.getCuriosInventory(mc.player)
                    .map(h -> h.findFirstCurio(ModItems.BRIGHT_BUTTERFLY_CURIO.get()).isPresent())
                    .orElse(false);
            if (hasCurio) {
                return GAMMA_OVERRIDE_VALUE;
            }
        }
        return original;
    }
}
