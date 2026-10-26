package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.entity.arrows.BlastArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/** 爆破箭渲染器：用独立贴图替代原版箭贴图。 */
public class BlastArrowRenderer extends ArrowRenderer<BlastArrow> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/blast_arrow.png");

    public BlastArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(BlastArrow entity) {
        return TEXTURE;
    }
}
