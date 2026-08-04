package com.pasterdream.pasterdreammod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pasterdream.pasterdreammod.client.model.AaroncosRightHandModel;
import com.pasterdream.pasterdreammod.world.entity.AaroncosRightHandEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AaroncosRightHandRenderer extends GeoEntityRenderer<AaroncosRightHandEntity> {
    public AaroncosRightHandRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AaroncosRightHandModel());
        this.shadowRadius = 2.8f;
        this.addRenderLayer(new AaroncosRightHandLayer(this));
    }

    @Override
    public RenderType getRenderType(AaroncosRightHandEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack poseStack, AaroncosRightHandEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red,
            float green, float blue, float alpha) {
        float scale = 1f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(AaroncosRightHandEntity entityLivingBaseIn) {
        return 0.0F;
    }
}
