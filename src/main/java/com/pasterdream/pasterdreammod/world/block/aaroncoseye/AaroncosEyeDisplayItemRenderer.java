package com.pasterdream.pasterdreammod.world.block.aaroncoseye;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import javax.annotation.Nullable;

public class AaroncosEyeDisplayItemRenderer extends GeoItemRenderer<AaroncosEyeDisplayItem> {
    public AaroncosEyeDisplayItemRenderer() {
        super(new AaroncosEyeDisplayModel());
    }

    @Override
    public RenderType getRenderType(AaroncosEyeDisplayItem animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
