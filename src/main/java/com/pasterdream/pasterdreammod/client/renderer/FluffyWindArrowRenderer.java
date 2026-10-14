package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.entity.FluffyWindArrowEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

/**
 * 萦风箭的箭头模型渲染器。
 * <p>
 * 仅负责绘制箭本体；青绿色渐变拖尾由 {@code ArrowTrailRenderer} 在
 * {@code RenderLevelStageEvent.AFTER_PARTICLES} 阶段统一绘制三棱柱管。
 */
public class FluffyWindArrowRenderer extends TrailArrowRenderer<FluffyWindArrowEntity> {

    public FluffyWindArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
}
