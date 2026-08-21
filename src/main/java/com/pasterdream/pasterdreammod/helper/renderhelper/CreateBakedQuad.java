package com.pasterdream.pasterdreammod.helper.renderhelper;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraftforge.client.model.pipeline.QuadBakingVertexConsumer;
import org.joml.Vector3f;

public class CreateBakedQuad
{
    public static BakedQuad createQuad(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureAtlasSprite sprite, Direction face, int tintIndex, int color)
    {
        QuadBakingVertexConsumer.Buffered bufferedConsumer = new QuadBakingVertexConsumer.Buffered();

        bufferedConsumer.setSprite(sprite);
        bufferedConsumer.setDirection(face);
        bufferedConsumer.setTintIndex(tintIndex);
        bufferedConsumer.setShade(false);
        bufferedConsumer.setHasAmbientOcclusion(true);

        addVertex(bufferedConsumer, v1, face, sprite, 0, 0, color);
        addVertex(bufferedConsumer, v2, face, sprite, 0, 16, color);
        addVertex(bufferedConsumer, v3, face, sprite, 16, 16, color);
        addVertex(bufferedConsumer, v4, face, sprite, 16, 0, color);

        return bufferedConsumer.getQuad();
    }

    private static void addVertex(QuadBakingVertexConsumer consumer, Vector3f pos, Direction face, TextureAtlasSprite sprite, float u, float v, int color)
    {
        float red = ((color >> 16) & 0xFF) / 255.0f;
        float green = ((color >> 8) & 0xFF) / 255.0f;
        float blue = (color & 0xFF) / 255.0f;
        float alpha = ((color >> 24) & 0xFF) / 255.0f;

        float uInterp = sprite.getU(u);
        float vInterp = sprite.getV(v);

        consumer.vertex(pos.x(), pos.y(), pos.z());
        consumer.color(red, green, blue, alpha);
        consumer.uv(uInterp, vInterp);
        consumer.uv2(0x00F000F0);
        consumer.normal(face.getStepX(), face.getStepY(), face.getStepZ());
        consumer.endVertex();
    }
}
