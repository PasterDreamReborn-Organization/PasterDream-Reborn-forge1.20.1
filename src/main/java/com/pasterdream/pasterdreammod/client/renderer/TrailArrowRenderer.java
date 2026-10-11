package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.entity.TrailArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/**
 * 带拖尾箭实体的箭头模型渲染器基类。
 * <p>
 * 仅负责绘制箭本体（普通箭 / 药水箭贴图）；三棱柱管状拖尾由
 * {@code ArrowTrailRenderer} 在 {@code RenderLevelStageEvent.AFTER_PARTICLES} 阶段统一绘制。
 */
public abstract class TrailArrowRenderer<T extends TrailArrowEntity> extends ArrowRenderer<T> {

    private static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/entity/projectiles/arrow.png");
    private static final ResourceLocation TIPPED_ARROW_TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/entity/projectiles/tipped_arrow.png");

    protected TrailArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return entity.getColor() != -1 ? TIPPED_ARROW_TEXTURE : ARROW_TEXTURE;
    }
}
