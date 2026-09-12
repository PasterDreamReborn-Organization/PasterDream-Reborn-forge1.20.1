package com.pasterdream.pasterdreammod.helper.renderhelper;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public class CustomRenderTypes
{
    public static final RenderType TRANSLUCENT_NO_DEPTH_WRITE = createEntityTranslucent("translucent_no_depth_write", false);

    public static final RenderType TRANSLUCENT = createEntityTranslucent("translucent", true);

    private static RenderType createEntityTranslucent(String name, boolean depthWrite)
    {
        return RenderType.create(
                name,
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                2097152,
                true,
                false,
                RenderType.CompositeState.builder()
                        .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
                        .setTextureState(RenderStateShard.BLOCK_SHEET_MIPPED)
                        .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                        .setLightmapState(RenderStateShard.LIGHTMAP)
                        .setOverlayState(RenderStateShard.OVERLAY)
                        .setWriteMaskState(depthWrite ? RenderStateShard.COLOR_DEPTH_WRITE : RenderStateShard.COLOR_WRITE)
                        .createCompositeState(true)
        );
    }
}
