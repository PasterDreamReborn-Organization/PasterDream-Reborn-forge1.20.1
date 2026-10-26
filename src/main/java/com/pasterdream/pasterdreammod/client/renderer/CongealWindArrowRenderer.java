package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.entity.arrows.CongealWindArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/** 凝风箭渲染器：用独立贴图替代原版箭贴图。 */
public class CongealWindArrowRenderer extends ArrowRenderer<CongealWindArrow> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/congeal_wind_arrow.png");

    public CongealWindArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(CongealWindArrow entity) {
        return TEXTURE;
    }
}
