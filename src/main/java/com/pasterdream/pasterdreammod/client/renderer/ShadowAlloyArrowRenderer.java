package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.entity.ShadowAlloyArrowEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

/**
 * 暗影合金弩箭的箭头模型渲染器。
 * <p>
 * 仅负责绘制箭本体；暗紫色渐变拖尾由 {@code ArrowTrailRenderer} 在
 * {@code RenderLevelStageEvent.AFTER_PARTICLES} 阶段统一绘制三棱柱管。
 */
public class ShadowAlloyArrowRenderer extends TrailArrowRenderer<ShadowAlloyArrowEntity> {

    public ShadowAlloyArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
}
