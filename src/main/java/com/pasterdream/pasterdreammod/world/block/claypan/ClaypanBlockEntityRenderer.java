package com.pasterdream.pasterdreammod.world.block.claypan;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Matrix4f;

public class ClaypanBlockEntityRenderer implements BlockEntityRenderer<ClaypanBlockEntity>
{
    public ClaypanBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {

    }

    @Override
    public void render(ClaypanBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay)
    {
        FluidStack fluidStack = blockEntity.getFluidTanks()[0].getFluid();
        if (fluidStack.isEmpty())
        {
            return;
        }

        //获取流体纹理
        IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = extensions.getStillTexture(fluidStack);

        if (stillTexture == null)
        {
            return;
        }

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(stillTexture);
        if (sprite == null)
        {
            return;
        }

        //获取流体颜色
        int color = extensions.getTintColor(fluidStack);
        float a = ((color >> 24) & 0xFF) / 255f;
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;

        float minX = 0.0625f;
        float maxX = 0.9375f;
        float minZ = 0.0625f;
        float maxZ = 0.9375f;
        float yLevel = 0.1875f;

        //渲染
        poseStack.pushPose();
        VertexConsumer consumer = buffer.getBuffer(RenderType.translucent());
        Matrix4f matrix = poseStack.last().pose();

        float nx = 0, ny = 1, nz = 0;

        consumer.vertex(matrix, minX, yLevel, minZ)
                .color(r, g, b, a)
                .uv(sprite.getU0(), sprite.getV0())
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(nx, ny, nz)
                .endVertex();

        consumer.vertex(matrix, minX, yLevel, maxZ)
                .color(r, g, b, a)
                .uv(sprite.getU0(), sprite.getV1())
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(nx, ny, nz)
                .endVertex();

        consumer.vertex(matrix, maxX, yLevel, maxZ)
                .color(r, g, b, a)
                .uv(sprite.getU1(), sprite.getV1())
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(nx, ny, nz)
                .endVertex();

        consumer.vertex(matrix, maxX, yLevel, minZ)
                .color(r, g, b, a)
                .uv(sprite.getU1(), sprite.getV0())
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(nx, ny, nz)
                .endVertex();

        poseStack.popPose();
    }
}
