package com.pasterdream.pasterdreammod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pasterdream.pasterdreammod.client.model.SmallStoneSpiritModel;
import com.pasterdream.pasterdreammod.world.entity.SmallStoneSpiritEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SmallStoneSpiritRenderer extends GeoEntityRenderer<SmallStoneSpiritEntity> {
    public SmallStoneSpiritRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SmallStoneSpiritModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public RenderType getRenderType(SmallStoneSpiritEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack poseStack, SmallStoneSpiritEntity entity, BakedGeoModel model, MultiBufferSource bufferSource,
                          VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay,
                          float red, float green, float blue, float alpha) {
        float scale = (float) (entity.getPersistentData().getDouble("size") + 1);
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick,
                packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(SmallStoneSpiritEntity entity) {
        return 0.0F;
    }
}
