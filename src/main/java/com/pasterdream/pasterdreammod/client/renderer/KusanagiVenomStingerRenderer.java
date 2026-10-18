package com.pasterdream.pasterdreammod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.client.model.KusanagiVenomStingerModel;
import com.pasterdream.pasterdreammod.world.entity.KusanagiVenomStingerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class KusanagiVenomStingerRenderer extends EntityRenderer<KusanagiVenomStingerEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            PasterDreamMod.MOD_ID, "textures/entities/kusanagi_venom_stinger.png");

    private final KusanagiVenomStingerModel<KusanagiVenomStingerEntity> model;

    public KusanagiVenomStingerRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new KusanagiVenomStingerModel<>(context.bakeLayer(KusanagiVenomStingerModel.LAYER_LOCATION));
    }

    @Override
    public void render(KusanagiVenomStingerEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        VertexConsumer vc = buffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(entity)));
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
        this.model.renderToBuffer(poseStack, vc, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(KusanagiVenomStingerEntity entity) {
        return TEXTURE;
    }
}
