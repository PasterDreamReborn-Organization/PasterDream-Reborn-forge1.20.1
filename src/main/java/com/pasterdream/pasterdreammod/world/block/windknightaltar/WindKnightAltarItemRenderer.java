package com.pasterdream.pasterdreammod.world.block.windknightaltar;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import javax.annotation.Nullable;

public class WindKnightAltarItemRenderer extends GeoItemRenderer<WindKnightAltarItem> {
    public WindKnightAltarItemRenderer() {
        super(new WindKnightAltarItemModel());
    }

    @Override
    public RenderType getRenderType(WindKnightAltarItem animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
