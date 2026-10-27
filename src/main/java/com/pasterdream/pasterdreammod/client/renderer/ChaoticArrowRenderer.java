package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.entity.arrows.ChaoticArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/** 混沌箭渲染器：用独立贴图替代原版箭贴图。 */
public class ChaoticArrowRenderer extends ArrowRenderer<ChaoticArrow> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/chaotic_arrow.png");

    public ChaoticArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ChaoticArrow entity) {
        return TEXTURE;
    }
}
