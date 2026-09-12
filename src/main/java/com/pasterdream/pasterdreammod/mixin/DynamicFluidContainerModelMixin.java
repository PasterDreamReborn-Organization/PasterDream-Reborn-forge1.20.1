package com.pasterdream.pasterdreammod.mixin;

import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.client.RenderTypeGroup;
import net.minecraftforge.client.model.DynamicFluidContainerModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DynamicFluidContainerModel.class, remap = false)
public class DynamicFluidContainerModelMixin
{
    @Inject(method = "getLayerRenderTypes", at = @At("RETURN"), cancellable = true, remap = false)
    private static void pasterdream$useShaderpackCompatibleTranslucent(boolean unlit, CallbackInfoReturnable<RenderTypeGroup> cir)
    {
        if (unlit)
        {
            cir.setReturnValue(new RenderTypeGroup(RenderType.translucent(), ForgeRenderTypes.ITEM_UNSORTED_TRANSLUCENT.get()));
        }
    }
}
