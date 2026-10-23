package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.entity.arrows.ToxicSporeArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/** 毒孢子箭渲染器：用独立贴图替代原版箭贴图。 */
public class ToxicSporeArrowRenderer extends ArrowRenderer<ToxicSporeArrow> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/toxic_spore_arrow.png");

    public ToxicSporeArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ToxicSporeArrow entity) {
        return TEXTURE;
    }
}
