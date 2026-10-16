package com.pasterdream.pasterdreammod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pasterdream.pasterdreammod.world.entity.TideSwordProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;

/**
 * 『北海若』引潮 投掷弹射物的渲染器：以剑的（数据生成的物品）模型渲染，替代原版三叉戟模型。
 * 姿态对齐原版 {@code ThrownTridentRenderer}，额外补正剑物品模型的 45° 对角线朝向。
 */
public class TideSwordProjectileRenderer extends EntityRenderer<TideSwordProjectileEntity> {

    /**
     * 剑的物品模型沿「左下角剑柄 → 右上角剑头」的 45° 对角线排布，
     * 而原版三叉戟模型的尖端指向 -Y；此角度把剑头补正到飞行正前方。
     */
    private static final float SWORD_BLADE_ROLL = 135.0F;

    private final ItemRenderer itemRenderer;

    public TideSwordProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(TideSwordProjectileEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot()) + 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(SWORD_BLADE_ROLL));
        this.itemRenderer.renderStatic(entity.getDisplayItem(), ItemDisplayContext.FIXED, packedLight,
                OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(TideSwordProjectileEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
