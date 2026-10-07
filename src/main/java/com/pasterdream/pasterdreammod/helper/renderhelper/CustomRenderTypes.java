package com.pasterdream.pasterdreammod.helper.renderhelper;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public class CustomRenderTypes
{
    public static final RenderType TRANSLUCENT_NO_DEPTH_WRITE = createEntityTranslucent("translucent_no_depth_write", false);

    public static final RenderType TRANSLUCENT = createEntityTranslucent("translucent", true);

    /**
     * 融梦箭拖尾：纯色（POSITION_COLOR）三棱柱管，加法混合、禁剔除、不写深度。
     * 混合用加法（LIGHTNING_TRANSPARENCY），输出到主目标（MAIN_TARGET），深度测试 LEQUAL。
     */
    public static final RenderType MELT_DREAM_TRAIL = RenderType.create(
            "melt_dream_trail",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
                    .setTransparencyState(RenderStateShard.LIGHTNING_TRANSPARENCY)
                    .setOutputState(RenderStateShard.MAIN_TARGET)
                    .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                    .createCompositeState(false)
    );

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
