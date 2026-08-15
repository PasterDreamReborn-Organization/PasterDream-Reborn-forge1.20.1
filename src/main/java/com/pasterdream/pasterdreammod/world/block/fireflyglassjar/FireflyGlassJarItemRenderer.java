package com.pasterdream.pasterdreammod.world.block.fireflyglassjar;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FireflyGlassJarItemRenderer extends GeoItemRenderer<FireflyGlassJarItem> {
    public FireflyGlassJarItemRenderer() {
        super(new FireflyGlassJarItemModel());
    }

    @Override
    public RenderType getRenderType(FireflyGlassJarItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
