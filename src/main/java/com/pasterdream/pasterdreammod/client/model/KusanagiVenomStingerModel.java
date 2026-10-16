package com.pasterdream.pasterdreammod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.joml.Matrix4f;

/**
 * 天丛云毒刺射弹模型：两片正交的竖直平面构成十字（对应 Blockbench 模型）。
 * 纹理 32×32，叶片长轴朝向模型 +X（前方），tip 对应纹理 v=0。
 */
public class KusanagiVenomStingerModel<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "kusanagi_venom_stinger"), "main");

    private static final float TEX = 32.0f;

    public KusanagiVenomStingerModel(ModelPart root) {
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.scale(1.0f / 16.0f, 1.0f / 16.0f, 1.0f / 16.0f);
        Matrix4f matrix = poseStack.last().pose();

        // 平面 A：XY 平面（z = 0），法线 +Z
        quad(vertexConsumer, matrix, packedLight, packedOverlay, red, green, blue, alpha, 0.0f, 0.0f, 1.0f,
                -8.0f, -8.0f, 0.0f, 0.0f, 32.0f,
                8.0f, -8.0f, 0.0f, 0.0f, 0.0f,
                8.0f, 8.0f, 0.0f, 32.0f, 0.0f,
                -8.0f, 8.0f, 0.0f, 32.0f, 32.0f);

        // 平面 B：XZ 平面（y = 0），法线 +Y
        quad(vertexConsumer, matrix, packedLight, packedOverlay, red, green, blue, alpha, 0.0f, 1.0f, 0.0f,
                -8.0f, 0.0f, -8.0f, 0.0f, 32.0f,
                8.0f, 0.0f, -8.0f, 0.0f, 0.0f,
                8.0f, 0.0f, 8.0f, 32.0f, 0.0f,
                -8.0f, 0.0f, 8.0f, 32.0f, 32.0f);

        poseStack.popPose();
    }

    private static void quad(VertexConsumer vc, Matrix4f matrix, int light, int overlay,
                             float r, float g, float b, float a, float nx, float ny, float nz,
                             float x1, float y1, float z1, float u1, float v1,
                             float x2, float y2, float z2, float u2, float v2,
                             float x3, float y3, float z3, float u3, float v3,
                             float x4, float y4, float z4, float u4, float v4) {
        vertex(vc, matrix, x1, y1, z1, u1, v1, light, overlay, r, g, b, a, nx, ny, nz);
        vertex(vc, matrix, x2, y2, z2, u2, v2, light, overlay, r, g, b, a, nx, ny, nz);
        vertex(vc, matrix, x3, y3, z3, u3, v3, light, overlay, r, g, b, a, nx, ny, nz);
        vertex(vc, matrix, x4, y4, z4, u4, v4, light, overlay, r, g, b, a, nx, ny, nz);
    }

    private static void vertex(VertexConsumer vc, Matrix4f matrix, float x, float y, float z, float u, float v,
                               int light, int overlay, float r, float g, float b, float a,
                               float nx, float ny, float nz) {
        vc.vertex(matrix, x, y, z)
                .color(r, g, b, a)
                .uv(u / TEX, v / TEX)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(nx, ny, nz)
                .endVertex();
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
    }
}
