package com.pasterdream.pasterdreammod.world.block.birdsnest;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class BirdsNestBlockEntityRenderer extends GeoBlockRenderer<BirdsNestBlockEntity> {
    public BirdsNestBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new BirdsNestBlockModel());
    }

    @Override
    public RenderType getRenderType(BirdsNestBlockEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
