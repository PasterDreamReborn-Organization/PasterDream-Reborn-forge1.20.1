package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(LightTexture.class)
public class LightTextureGammaMixin {

    private static final double GAMMA_OVERRIDE_VALUE = 10.0;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Redirect(
        method = "updateLightTexture",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;"
        )
    )
    private Object redirectOptionGet(OptionInstance instance) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && instance == mc.options.gamma()) {
            boolean hasCurio = CuriosApi.getCuriosInventory(mc.player)
                    .map(h -> h.findFirstCurio(ModItems.BRIGHT_BUTTERFLY_CURIO.get()).isPresent())
                    .orElse(false);
            if (hasCurio) {
                return GAMMA_OVERRIDE_VALUE;
            }
        }
        return ((IMixinOptionInstance) (Object) instance).pasterdream_getValue();
    }
}
