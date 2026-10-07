package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.entity.MeltDreamArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/**
 * 融梦箭的箭头模型渲染器。
 * <p>
 * 仅负责绘制箭本体；粉色渐变拖尾由 {@code MeltDreamTrailRenderer} 在
 * {@code RenderLevelStageEvent.AFTER_PARTICLES} 阶段统一绘制三棱柱管。
 */
public class MeltDreamArrowRenderer extends ArrowRenderer<MeltDreamArrowEntity> {

    private static final ResourceLocation ARROW_TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/projectiles/arrow.png");
    private static final ResourceLocation TIPPED_ARROW_TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/projectiles/tipped_arrow.png");

    public MeltDreamArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(MeltDreamArrowEntity entity) {
        return entity.getColor() != -1 ? TIPPED_ARROW_TEXTURE : ARROW_TEXTURE;
    }
}
