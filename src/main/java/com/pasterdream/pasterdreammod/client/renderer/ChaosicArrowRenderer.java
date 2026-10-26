package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.entity.arrows.ChaosicArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/** 混沌箭渲染器：用独立贴图替代原版箭贴图。 */
public class ChaosicArrowRenderer extends ArrowRenderer<ChaosicArrow> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/chaosic_arrow.png");

    public ChaosicArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ChaosicArrow entity) {
        return TEXTURE;
    }
}
