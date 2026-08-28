package com.pasterdream.pasterdreammod.world.block.dreamaccumulator;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import javax.annotation.Nullable;

public class DreamAccumulatorItemRenderer extends GeoItemRenderer<DreamAccumulatorItem>
{
    public DreamAccumulatorItemRenderer()
    {
        super(new DreamAccumulatorItemModel());
    }

    @Override
    public RenderType getRenderType(DreamAccumulatorItem animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick)
    {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
