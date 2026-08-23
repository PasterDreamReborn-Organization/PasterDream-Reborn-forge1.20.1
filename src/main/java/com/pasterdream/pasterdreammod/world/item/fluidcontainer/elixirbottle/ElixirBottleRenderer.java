package com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pasterdream.pasterdreammod.helper.renderhelper.RendBakedModel;
import com.pasterdream.pasterdreammod.init.ModItemModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Matrix4f;

public class ElixirBottleRenderer extends BlockEntityWithoutLevelRenderer
{
    private static ElixirBottleRenderer instance;

    public ElixirBottleRenderer(BlockEntityRenderDispatcher dispatcher)
    {
        super(dispatcher, Minecraft.getInstance().getEntityModels());
    }

    public static ElixirBottleRenderer getInstance()
    {
        if (instance == null)
        {
            instance = new ElixirBottleRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher());
        }
        return instance;
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay)
    {
        BakedModel bottleModel = ModItemModels.getElixirBottleModel();

        poseStack.pushPose();

        RendBakedModel.rend(bottleModel, poseStack, buffer, packedLight, packedOverlay);

        FluidStack fluid = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(fluidHandlerItem -> fluidHandlerItem.getFluidInTank(0)).orElse(FluidStack.EMPTY);

        if (!fluid.isEmpty())
        {
            drawFluidLayer(fluid, poseStack, buffer, packedLight, packedOverlay);
        }

        poseStack.popPose();
    }

    private void drawFluidLayer(FluidStack fluidStack, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay)
    {
        IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = extensions.getStillTexture(fluidStack);
        if (stillTexture == null)
        {
            return;
        }

        int fluidAmount = fluidStack.getAmount();

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
        if (sprite == null)
        {
            return;
        }

        int color = extensions.getTintColor(fluidStack);
        float a = ((color >> 24) & 0xFF) / 255f;
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;

        drawFluid(poseStack, buffer, sprite, packedLight, packedOverlay, 0.46875f, false, fluidAmount, r, g, b, a);
        drawFluid(poseStack, buffer, sprite, packedLight, packedOverlay, 0.53125f, true, fluidAmount, r, g, b, a);
    }

    private void drawFluid(PoseStack poseStack, MultiBufferSource buffer, TextureAtlasSprite sprite, int packedLight, int packedOverlay, float z, boolean direction, int fluidAmount, float r, float g, float b, float a)
    {
        if (fluidAmount > 0)
        {
            double proportion = Math.min(1, (double) fluidAmount / 286);
            VertexConsumer consumer = buffer.getBuffer(RenderType.translucent());
            Matrix4f matrix = poseStack.last().pose();

            addQuad(sprite, consumer, matrix, 6 / 16f, 4 / 16f, 10 / 16f, (float)((4 + 2 * proportion) / 16f), z, 6f, 12f, 10f, (float)(12 - 2 * proportion), r, g, b, a, packedLight, packedOverlay, direction);

        }

        if (fluidAmount > 286)  //1000 * 2 / 7
        {
            double proportion = Math.min(1, (double) (fluidAmount - 286) / 143);
            VertexConsumer consumer = buffer.getBuffer(RenderType.translucent());
            Matrix4f matrix = poseStack.last().pose();

            addQuad(sprite, consumer, matrix, 5 / 16f, 6 / 16f, 11 / 16f, (float)((6 + proportion) / 16f), z, 5f, 10f, 11f, (float)(10 - proportion), r, g, b, a, packedLight, packedOverlay, direction);
        }

        if (fluidAmount > 429)  //1000 * 3 / 7
        {
            double proportion = Math.min(1, (double) (fluidAmount - 429) / 429);
            VertexConsumer consumer = buffer.getBuffer(RenderType.translucent());
            Matrix4f matrix = poseStack.last().pose();

            addQuad(sprite, consumer, matrix, 4 / 16f, 7 / 16f, 12 / 16f, (float)((7 + 3 * proportion) / 16f), z, 4f, 9f, 12f, (float)(9 - 3 * proportion), r, g, b, a, packedLight, packedOverlay, direction);
        }

        if (fluidAmount > 857)  //1000 * 6 / 7
        {
            double proportion = Math.min(1, (double) (fluidAmount - 857) / 143);
            VertexConsumer consumer = buffer.getBuffer(RenderType.translucent());
            Matrix4f matrix = poseStack.last().pose();

            addQuad(sprite, consumer, matrix, 5 / 16f, 10 / 16f, 11 / 16f, (float)((10 + proportion) / 16f), z, 5f, 6f, 11f, (float)(6 - proportion), r, g, b, a, packedLight, packedOverlay, direction);
        }
    }

    private void addQuad(TextureAtlasSprite sprite, VertexConsumer consumer, Matrix4f matrix, float x1, float y1, float x2, float y2, float z, float u1, float v1, float u2, float v2, float r, float g, float b, float a, int packedLight, int packedOverlay, boolean direction)
    {
        if (direction)
        {
            consumer.vertex(matrix, x1, y2, z).color(r,g,b,a).uv(sprite.getU(u1), sprite.getV(v2)).overlayCoords(packedOverlay).uv2(packedLight).normal(0,0,1).endVertex();
            consumer.vertex(matrix, x1, y1, z).color(r,g,b,a).uv(sprite.getU(u1), sprite.getV(v1)).overlayCoords(packedOverlay).uv2(packedLight).normal(0,0,1).endVertex();
            consumer.vertex(matrix, x2, y1, z).color(r,g,b,a).uv(sprite.getU(u2), sprite.getV(v1)).overlayCoords(packedOverlay).uv2(packedLight).normal(0,0,1).endVertex();
            consumer.vertex(matrix, x2, y2, z).color(r,g,b,a).uv(sprite.getU(u2), sprite.getV(v2)).overlayCoords(packedOverlay).uv2(packedLight).normal(0,0,1).endVertex();
        }
            else
            {
                consumer.vertex(matrix, x2, y2, z).color(r,g,b,a).uv(sprite.getU(u2),sprite.getV(v2)).overlayCoords(packedOverlay).uv2(packedLight).normal(0,0,1).endVertex();
                consumer.vertex(matrix, x2, y1, z).color(r,g,b,a).uv(sprite.getU(u2),sprite.getV(v1)).overlayCoords(packedOverlay).uv2(packedLight).normal(0,0,1).endVertex();
                consumer.vertex(matrix, x1, y1, z).color(r,g,b,a).uv(sprite.getU(u1),sprite.getV(v1)).overlayCoords(packedOverlay).uv2(packedLight).normal(0,0,1).endVertex();
                consumer.vertex(matrix, x1, y2, z).color(r,g,b,a).uv(sprite.getU(u1),sprite.getV(v2)).overlayCoords(packedOverlay).uv2(packedLight).normal(0,0,1).endVertex();
            }
    }
}
