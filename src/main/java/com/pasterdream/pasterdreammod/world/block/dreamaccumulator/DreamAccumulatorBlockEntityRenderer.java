package com.pasterdream.pasterdreammod.world.block.dreamaccumulator;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import javax.annotation.Nullable;

public class DreamAccumulatorBlockEntityRenderer extends GeoBlockRenderer<DreamAccumulatorBlockEntity>
{
    public DreamAccumulatorBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        super(new DreamAccumulatorBlockModel());
    }

    @Override
    public RenderType getRenderType(DreamAccumulatorBlockEntity animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick)
    {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
