package com.pasterdream.pasterdreammod.world.block.weaponworkshop.coolerpot;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pasterdream.pasterdreammod.helper.pixelcalculator.PixelCalculator;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Matrix4f;

import java.util.List;

public class WeaponWorkshopCoolerPotBlockEntityRenderer implements BlockEntityRenderer<WeaponWorkshopCoolerPotBlockEntity>
{
    public WeaponWorkshopCoolerPotBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {

    }

    @Override
    public void render(WeaponWorkshopCoolerPotBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay)
    {
        FluidStack fluidStack = blockEntity.getFluidTank(0).getFluid();
        if (fluidStack.isEmpty())
        {
            return;
        }

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

        int color = extensions.getTintColor(fluidStack);
        float a = ((color >> 24) & 0xFF) / 255f;
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;

        Direction direction = blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);

        List<List<Float>> rendPixel = PixelCalculator.calculate(0.125f, 0.8125f, 0.25f, 0.6875f);

        float minX = switch (direction)
        {
            case EAST -> rendPixel.get(0).get(0);
            case SOUTH -> rendPixel.get(1).get(0);
            case WEST -> rendPixel.get(2).get(0);
            case NORTH -> rendPixel.get(3).get(0);
            default -> 0f;
        };
        float maxX = switch (direction)
        {
            case EAST -> rendPixel.get(0).get(1);
            case SOUTH -> rendPixel.get(1).get(1);
            case WEST -> rendPixel.get(2).get(1);
            case NORTH -> rendPixel.get(3).get(1);
            default -> 1f;
        };
        float minZ = switch (direction)
        {
            case EAST -> rendPixel.get(0).get(2);
            case SOUTH -> rendPixel.get(1).get(2);
            case WEST -> rendPixel.get(2).get(2);
            case NORTH -> rendPixel.get(3).get(2);
            default -> 0f;
        };
        float maxZ = switch (direction)
        {
            case EAST -> rendPixel.get(0).get(3);
            case SOUTH -> rendPixel.get(1).get(3);
            case WEST -> rendPixel.get(2).get(3);
            case NORTH -> rendPixel.get(3).get(3);
            default -> 1f;
        };

        float yLevel = (fluidStack.getAmount() / 1000f) * 0.6875f + 0.1875f;

        float lengthU = sprite.getU1() - sprite.getU0();
        float lengthV = sprite.getV1() - sprite.getV0();

        float minU = minX * lengthU + sprite.getU0();
        float maxU = maxX * lengthU + sprite.getU0();
        float minV = minZ * lengthV + sprite.getV0();
        float maxV = maxZ * lengthV + sprite.getV0();

        poseStack.pushPose();
        VertexConsumer consumer = buffer.getBuffer(RenderType.translucent());
        Matrix4f matrix = poseStack.last().pose();

        float nx = 0, ny = 1, nz = 0;

        consumer.vertex(matrix, minX, yLevel, minZ)
                .color(r, g, b, a)
                .uv(minU, minV)
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(nx, ny, nz)
                .endVertex();

        consumer.vertex(matrix, minX, yLevel, maxZ)
                .color(r, g, b, a)
                .uv(minU, maxV)
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(nx, ny, nz)
                .endVertex();

        consumer.vertex(matrix, maxX, yLevel, maxZ)
                .color(r, g, b, a)
                .uv(maxU, maxV)
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(nx, ny, nz)
                .endVertex();

        consumer.vertex(matrix, maxX, yLevel, minZ)
                .color(r, g, b, a)
                .uv(maxU, minV)
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(nx, ny, nz)
                .endVertex();

        poseStack.popPose();
    }
}
