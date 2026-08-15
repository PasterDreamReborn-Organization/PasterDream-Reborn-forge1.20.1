package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.client.model.FireflyModel;
import com.pasterdream.pasterdreammod.world.entity.FireflyEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireflyRenderer extends GeoEntityRenderer<FireflyEntity> {
    public FireflyRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FireflyModel());
        this.shadowRadius = 0.2f;
        this.addRenderLayer(new FireflyLayer(this));
    }

    @Override
    public RenderType getRenderType(FireflyEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
