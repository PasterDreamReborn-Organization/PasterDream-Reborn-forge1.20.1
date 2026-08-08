package com.pasterdream.pasterdreammod.world.item.dreamnotesbook;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

public class DreamNotesBookItemRenderer extends BlockEntityWithoutLevelRenderer
{
    public DreamNotesBookItemRenderer()
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay)
    {
        if(true)
        {
            throw new RuntimeException("Custom renderer invoked, item: " + itemStack);
        }

        CompoundTag tag = itemStack.getTag();
        DreamNotesBookInfo info = null;
        if (tag != null && tag.contains("content"))
        {
            info = DreamNotesBookRegistry.getInfo(tag.getString("content"));
        }

        if (info != null && info.itemTexture() != null)
        {
            renderWithTexture(info.itemTexture(), poseStack, bufferSource, packedLight, packedOverlay, displayContext);
        }
            else
            {
                super.renderByItem(itemStack, displayContext, poseStack, bufferSource, packedLight, packedOverlay);
            }
    }

    private void renderWithTexture(ResourceLocation textureLocation, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, ItemDisplayContext displayContext)
    {
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(textureLocation);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(sprite.atlasLocation()));

        poseStack.pushPose();
        boolean isGui = (displayContext == ItemDisplayContext.GUI || displayContext == ItemDisplayContext.FIXED);
        float size = isGui ? 0.5f : 0.25f;

        Matrix4f matrix = poseStack.last().pose();
        float minU = sprite.getU0(), maxU = sprite.getU1();
        float minV = sprite.getV0(), maxV = sprite.getV1();

        vertexConsumer.vertex(matrix, -size,  size, 0.0F).color(255,255,255,255).uv(minU, minV).overlayCoords(packedOverlay).uv2(packedLight).normal(0,0,1).endVertex();
        vertexConsumer.vertex(matrix,  size,  size, 0.0F).color(255,255,255,255).uv(maxU, minV).overlayCoords(packedOverlay).uv2(packedLight).normal(0,0,1).endVertex();
        vertexConsumer.vertex(matrix,  size, -size, 0.0F).color(255,255,255,255).uv(maxU, maxV).overlayCoords(packedOverlay).uv2(packedLight).normal(0,0,1).endVertex();
        vertexConsumer.vertex(matrix, -size, -size, 0.0F).color(255,255,255,255).uv(minU, maxV).overlayCoords(packedOverlay).uv2(packedLight).normal(0,0,1).endVertex();

        poseStack.popPose();
    }
}
