package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.entity.arrows.FragdreamArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/** 碎梦箭渲染器：用独立贴图替代原版箭贴图。 */
public class FragdreamArrowRenderer extends ArrowRenderer<FragdreamArrow> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/fragdream_arrow.png");

    public FragdreamArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(FragdreamArrow entity) {
        return TEXTURE;
    }
}
