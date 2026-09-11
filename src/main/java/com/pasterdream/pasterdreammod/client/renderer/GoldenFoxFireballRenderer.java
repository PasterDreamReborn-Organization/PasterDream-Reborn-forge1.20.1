package com.pasterdream.pasterdreammod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.client.model.FireballProjectileModel;
import com.pasterdream.pasterdreammod.world.entity.GoldenFoxFireballEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GoldenFoxFireballRenderer extends EntityRenderer<GoldenFoxFireballEntity> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/golden_fox_fireball.png");
    private final FireballProjectileModel<GoldenFoxFireballEntity> model;

    public GoldenFoxFireballRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new FireballProjectileModel<>(context.bakeLayer(FireballProjectileModel.LAYER_LOCATION));
    }

    @Override
    public void render(GoldenFoxFireballEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        VertexConsumer vb = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entity)));
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F + Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
        this.model.renderToBuffer(poseStack, vb, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(GoldenFoxFireballEntity entity) {
        return TEXTURE;
    }
}
