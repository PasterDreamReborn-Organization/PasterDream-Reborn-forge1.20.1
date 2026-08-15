package com.pasterdream.pasterdreammod.world.block.fireflyglassjar;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FireflyGlassJarBlockEntityRenderer extends GeoBlockRenderer<FireflyGlassJarBlockEntity> {
    public FireflyGlassJarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new FireflyGlassJarBlockModel());
    }

    @Override
    public RenderType getRenderType(FireflyGlassJarBlockEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
