package com.pasterdream.pasterdreammod.helper.renderhelper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraftforge.client.model.data.ModelData;

public class RendBakedModel
{
    public static void rend(BakedModel model, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay)
    {
        VertexConsumer sideConsumer = buffer.getBuffer(RenderType.cutout());
        VertexConsumer faceConsumer = buffer.getBuffer(CustomRenderTypes.TRANSLUCENT_NO_DEPTH_WRITE);
        RandomSource random = RandomSource.create();
        for (BakedQuad quad : model.getQuads(null, null, random, ModelData.EMPTY, null))
        {
            faceConsumer.putBulkData(poseStack.last(), quad, 1, 1, 1, light, overlay);
        }
        for (Direction dir : Direction.values())
        {
            for (BakedQuad quad : model.getQuads(null, dir, random, ModelData.EMPTY, null))
            {
                sideConsumer.putBulkData(poseStack.last(), quad, 1, 1, 1, light, overlay);
            }
        }
    }
}
