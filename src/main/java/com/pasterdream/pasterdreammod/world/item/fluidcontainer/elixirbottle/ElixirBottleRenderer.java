package com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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
        FluidStack fluid = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(fluidHandlerItem -> fluidHandlerItem.getFluidInTank(0)).orElse(FluidStack.EMPTY);

        TextureAtlasSprite bottleSprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "item/elixir_bottle"));

        float z = 0f;

        if (!fluid.isEmpty())
        {
            drawFluidLayer(fluid, poseStack, buffer, packedLight, packedOverlay, 0, 0, 1, 1, z);
        }

        drawBottleLayer(bottleSprite, poseStack, buffer, packedLight, packedOverlay, 0, 0, 1, 1, z);
    }

    private void drawFluidLayer(FluidStack fluidStack, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, float minX, float maxX, float minY, float maxY, float z)
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

        if (fluidAmount > 0)
        {
            double proportion = Math.min(1, (double) fluidAmount / 286);
            VertexConsumer consumer = buffer.getBuffer(RenderType.translucent());
            Matrix4f matrix = poseStack.last().pose();

            consumer.vertex(matrix, 6 / 16f, (float)((4 + 2 * proportion) / 16f), z).color(r, g, b, a)
                    .uv(sprite.getU(6f), sprite.getV((float)(12 - 2 * proportion)))
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(0, 0, 1)
                    .endVertex();

            consumer.vertex(matrix, 6 / 16f, 4 / 16f, z).color(r, g, b, a)
                    .uv(sprite.getU(6f), sprite.getV(12f))
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(0, 0, 1)
                    .endVertex();

            consumer.vertex(matrix, 10 / 16f, 4 / 16f, z).color(r, g, b, a)
                    .uv(sprite.getU(10f), sprite.getV(12f))
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(0, 0, 1)
                    .endVertex();

            consumer.vertex(matrix, 10 / 16f, (float)((4 + 2 * proportion) / 16f), z).color(r, g, b, a)
                    .uv(sprite.getU(10f), sprite.getV((float)(12 - 2 * proportion)))
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(0, 0, 1)
                    .endVertex();
        }

        if (fluidAmount > 286)  //1000 * 2 / 7
        {
            double proportion = Math.min(1, (double) (fluidAmount - 286) / 143);
            VertexConsumer consumer = buffer.getBuffer(RenderType.translucent());
            Matrix4f matrix = poseStack.last().pose();

            consumer.vertex(matrix, 5 / 16f, (float)((6 + proportion) / 16f), z).color(r, g, b, a)
                    .uv(sprite.getU(5f), sprite.getV((float)(10 - proportion)))
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(0, 0, 1)
                    .endVertex();

            consumer.vertex(matrix, 5 / 16f, 6 / 16f, z).color(r, g, b, a)
                    .uv(sprite.getU(5), sprite.getV(10))
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(0, 0, 1)
                    .endVertex();

            consumer.vertex(matrix, 11 / 16f, 6 / 16f, z).color(r, g, b, a)
                    .uv(sprite.getU(11), sprite.getV(10))
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(0, 0, 1)
                    .endVertex();

            consumer.vertex(matrix, 11 / 16f, (float)((6 + proportion) / 16f), z).color(r, g, b, a)
                    .uv(sprite.getU(11), sprite.getV((float)(10 - proportion)))
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(0, 0, 1)
                    .endVertex();
        }

        if (fluidAmount > 429)  //1000 * 3 / 7
        {
            double proportion = Math.min(1, (double) (fluidAmount - 429) / 429);
            VertexConsumer consumer = buffer.getBuffer(RenderType.translucent());
            Matrix4f matrix = poseStack.last().pose();

            consumer.vertex(matrix, 4 / 16f, (float)((7 + 3 * proportion) / 16f), z).color(r, g, b, a)
                    .uv(sprite.getU(4), sprite.getV((float)(9 - 3 * proportion)))
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(0, 0, 1)
                    .endVertex();

            consumer.vertex(matrix, 4 / 16f, 7 / 16f, z).color(r, g, b, a)
                    .uv(sprite.getU(4), sprite.getV(9))
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(0, 0, 1)
                    .endVertex();

            consumer.vertex(matrix, 12 / 16f, 7 / 16f, z).color(r, g, b, a)
                    .uv(sprite.getU(12), sprite.getV(9))
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(0, 0, 1)
                    .endVertex();

            consumer.vertex(matrix, 12 / 16f, (float)((7 + 3 * proportion) / 16f), z).color(r, g, b, a)
                    .uv(sprite.getU(12), sprite.getV((float)(9 - 3 * proportion)))
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(0, 0, 1)
                    .endVertex();
        }

        if (fluidAmount > 857)  //1000 * 6 / 7
        {
            double proportion = Math.min(1, (double) (fluidAmount - 857) / 143);
            VertexConsumer consumer = buffer.getBuffer(RenderType.translucent());
            Matrix4f matrix = poseStack.last().pose();

            consumer.vertex(matrix, 5 / 16f, (float)((10 + proportion) / 16f), z).color(r, g, b, a)
                    .uv(sprite.getU(5), sprite.getV((float)(6 - proportion)))
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(0, 0, 1)
                    .endVertex();

            consumer.vertex(matrix, 5 / 16f, 10 / 16f, z).color(r, g, b, a)
                    .uv(sprite.getU(5), sprite.getV(6))
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(0, 0, 1)
                    .endVertex();

            consumer.vertex(matrix, 11 / 16f, 10 / 16f, z).color(r, g, b, a)
                    .uv(sprite.getU(11), sprite.getV(6))
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(0, 0, 1)
                    .endVertex();

            consumer.vertex(matrix, 11 / 16f, (float)((10 + proportion) / 16f), z).color(r, g, b, a)
                    .uv(sprite.getU(11), sprite.getV((float)(6 - proportion)))
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(0, 0, 1)
                    .endVertex();
        }
    }

    private void drawBottleLayer(TextureAtlasSprite sprite, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, float minX, float maxX, float minY, float maxY, float z)
    {
        VertexConsumer consumer = buffer.getBuffer(RenderType.translucent());
        Matrix4f matrix = poseStack.last().pose();

        consumer.vertex(matrix, 0, 0, z).color(1f, 1f, 1f, 1f)
                .uv(sprite.getU0(), sprite.getV1())
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(0, 0, 1)
                .endVertex();

        consumer.vertex(matrix, 1, 0, z).color(1f, 1f, 1f, 1f)
                .uv(sprite.getU1(), sprite.getV1())
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(0, 0, 1)
                .endVertex();

        consumer.vertex(matrix, 1, 1, z).color(1f, 1f, 1f, 1f)
                .uv(sprite.getU1(), sprite.getV0())
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(0, 0, 1)
                .endVertex();

        consumer.vertex(matrix, 0, 1, z).color(1f, 1f, 1f, 1f)
                .uv(sprite.getU0(), sprite.getV0())
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(0, 0, 1)
                .endVertex();
    }
}
