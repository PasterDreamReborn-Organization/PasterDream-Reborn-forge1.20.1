package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.entity.arrows.BlackstoneArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/** 黑石箭渲染器：用独立贴图替代原版箭贴图。 */
public class BlackstoneArrowRenderer extends ArrowRenderer<BlackstoneArrow> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/blackstone_arrow.png");

    public BlackstoneArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(BlackstoneArrow entity) {
        return TEXTURE;
    }
}
