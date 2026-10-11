package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.entity.MeltDreamArrowEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

/**
 * 融梦箭的箭头模型渲染器。
 * <p>
 * 仅负责绘制箭本体；粉色渐变拖尾由 {@code ArrowTrailRenderer} 在
 * {@code RenderLevelStageEvent.AFTER_PARTICLES} 阶段统一绘制三棱柱管。
 */
public class MeltDreamArrowRenderer extends TrailArrowRenderer<MeltDreamArrowEntity> {

    public MeltDreamArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
}
