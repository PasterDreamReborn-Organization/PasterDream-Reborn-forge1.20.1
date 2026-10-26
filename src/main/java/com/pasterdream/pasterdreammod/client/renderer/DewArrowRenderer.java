package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.entity.arrows.DewArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/** 露滴箭渲染器：用独立贴图替代原版箭贴图。 */
public class DewArrowRenderer extends ArrowRenderer<DewArrow> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/dew_arrow.png");

    public DewArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(DewArrow entity) {
        return TEXTURE;
    }
}
