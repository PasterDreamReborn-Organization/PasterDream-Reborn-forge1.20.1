package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.client.model.ShakingCrystalModel;
import com.pasterdream.pasterdreammod.world.entity.shakingcrystal.ShakingCrystalEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShakingCrystalRenderer extends GeoEntityRenderer<ShakingCrystalEntity> {
    public ShakingCrystalRenderer(EntityRendererProvider.Context context) {
        super(context, new ShakingCrystalModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public RenderType getRenderType(ShakingCrystalEntity animatable, ResourceLocation texture,
                                    MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack poseStack, ShakingCrystalEntity entity, BakedGeoModel model,
                          MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender,
                          float partialTick, int packedLight, int packedOverlay, float red, float green,
                          float blue, float alpha) {
        this.scaleHeight = 1f;
        this.scaleWidth = 1f;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender,
                partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(ShakingCrystalEntity entityLivingBaseIn) {
        return 0.0F;
    }
}
