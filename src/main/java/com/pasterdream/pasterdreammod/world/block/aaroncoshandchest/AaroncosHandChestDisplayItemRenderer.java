package com.pasterdream.pasterdreammod.world.block.aaroncoshandchest;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import javax.annotation.Nullable;

public class AaroncosHandChestDisplayItemRenderer extends GeoItemRenderer<AaroncosHandChestDisplayItem> {
    public AaroncosHandChestDisplayItemRenderer() {
        super(new AaroncosHandChestDisplayModel());
    }

    @Override
    public RenderType getRenderType(AaroncosHandChestDisplayItem animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
