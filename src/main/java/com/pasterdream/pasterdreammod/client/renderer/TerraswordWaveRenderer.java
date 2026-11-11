package com.pasterdream.pasterdreammod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.renderhelper.CustomRenderTypes;
import com.pasterdream.pasterdreammod.world.entity.TerraswordWaveEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

/**
 * 泰拉剑气刀光：将绘制好的透明月牙纹理放在飞行方向与高度轴构成的平面内。
 * 高度轴仍随实体的随机翻滚角旋转，纹理透明度随生命周期渐隐；刀光始终以最大亮度渲染。
 */
public class TerraswordWaveRenderer extends EntityRenderer<TerraswordWaveEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            PasterDreamMod.MOD_ID, "textures/entities/terrasword_wave.png");
    private static final RenderType WAVE_RENDER_TYPE = CustomRenderTypes.swordWave(TEXTURE);
    private static final double HALF_HEIGHT = 2.5;
    private static final double BACK_EDGE = -0.5;
    private static final double FRONT_EDGE = 1.5;
    private static final float EXTRA_GLOW_ALPHA = 0.35f;
    private static final float FADE_IN_TICKS = 2.0f;   // 生成淡入时长
    private static final float FADE_OUT_TICKS = 5.0f;  // 消失淡出时长

    public TerraswordWaveRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(TerraswordWaveEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(TerraswordWaveEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        Vec3 moveDir = entity.getMoveDirection(partialTick);
        Vec3 heightAxis = entity.getWaveHeightAxis(moveDir);
        Vec3 side = moveDir.cross(heightAxis).normalize();
        float lifeFade = computeLifeFade(entity, partialTick);
        if (lifeFade <= 0.0f) {
            return;
        }

        Matrix4f matrix = poseStack.last().pose();
        VertexConsumer vc = buffer.getBuffer(WAVE_RENDER_TYPE);
        Vec3 topBack = heightAxis.scale(HALF_HEIGHT).add(moveDir.scale(BACK_EDGE));
        Vec3 bottomBack = heightAxis.scale(-HALF_HEIGHT).add(moveDir.scale(BACK_EDGE));
        Vec3 bottomFront = heightAxis.scale(-HALF_HEIGHT).add(moveDir.scale(FRONT_EDGE));
        Vec3 topFront = heightAxis.scale(HALF_HEIGHT).add(moveDir.scale(FRONT_EDGE));
        renderQuad(vc, matrix, topBack, bottomBack, bottomFront, topFront, lifeFade);
        renderQuad(vc, matrix, topBack, bottomBack, bottomFront, topFront, lifeFade * EXTRA_GLOW_ALPHA);

        // F3+B 开启时，绘制实际的定向碰撞盒(OBB)与飞行方向轴，便于核对命中范围
        if (Minecraft.getInstance().getEntityRenderDispatcher().shouldRenderHitBoxes()) {
            renderDebugBox(poseStack, buffer, side, heightAxis, moveDir);
        }
    }

    /** 调试：绘制命中判定用的 OBB 线框（橙）、飞行方向（绿）、高度轴（黄）、宽度轴（青）。 */
    private void renderDebugBox(PoseStack poseStack, MultiBufferSource buffer, Vec3 side, Vec3 heightAxis, Vec3 moveDir) {
        VertexConsumer vc = buffer.getBuffer(RenderType.lines());
        Matrix4f matrix = poseStack.last().pose();
        double hw = TerraswordWaveEntity.COLLISION_HALF_WIDTH;
        double hh = TerraswordWaveEntity.COLLISION_HALF_HEIGHT;
        double hd = TerraswordWaveEntity.COLLISION_HALF_DEPTH;

        Vec3[] corners = new Vec3[8];
        int index = 0;
        for (int sx = -1; sx <= 1; sx += 2) {
            for (int sy = -1; sy <= 1; sy += 2) {
                for (int sz = -1; sz <= 1; sz += 2) {
                    corners[index++] = side.scale(sx * hw).add(heightAxis.scale(sy * hh)).add(moveDir.scale(sz * hd));
                }
            }
        }
        int[][] edges = {
                {0, 1}, {0, 2}, {0, 4}, {1, 3}, {1, 5}, {2, 3},
                {2, 6}, {3, 7}, {4, 5}, {4, 6}, {5, 7}, {6, 7}
        };
        for (int[] edge : edges) {
            line(vc, matrix, corners[edge[0]], corners[edge[1]], 1.0F, 0.55F, 0.0F);
        }
        line(vc, matrix, Vec3.ZERO, moveDir.scale(hd + 1.5), 0.0F, 1.0F, 0.0F);
        line(vc, matrix, Vec3.ZERO, heightAxis.scale(hh + 0.5), 1.0F, 1.0F, 0.0F);
        line(vc, matrix, Vec3.ZERO, side.scale(hw + 0.5), 0.0F, 1.0F, 1.0F);
    }

    private static void line(VertexConsumer vc, Matrix4f matrix, Vec3 from, Vec3 to, float r, float g, float b) {
        Vec3 normal = to.subtract(from);
        normal = normal.lengthSqr() > 1.0E-8 ? normal.normalize() : new Vec3(0.0, 1.0, 0.0);
        vc.vertex(matrix, (float) from.x, (float) from.y, (float) from.z)
                .color(r, g, b, 1.0F)
                .normal((float) normal.x, (float) normal.y, (float) normal.z)
                .endVertex();
        vc.vertex(matrix, (float) to.x, (float) to.y, (float) to.z)
                .color(r, g, b, 1.0F)
                .normal((float) normal.x, (float) normal.y, (float) normal.z)
                .endVertex();
    }

    private static void renderQuad(VertexConsumer vc, Matrix4f matrix, Vec3 topBack, Vec3 bottomBack,
                                   Vec3 bottomFront, Vec3 topFront, float alpha) {
        vertex(vc, matrix, topBack, 0.0f, 0.0f, alpha);
        vertex(vc, matrix, bottomBack, 0.0f, 1.0f, alpha);
        vertex(vc, matrix, bottomFront, 1.0f, 1.0f, alpha);
        vertex(vc, matrix, topFront, 1.0f, 0.0f, alpha);
    }

    private static void vertex(VertexConsumer vc, Matrix4f matrix, Vec3 pos, float u, float v, float alpha) {
        vc.vertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z)
                .color(1.0f, 1.0f, 1.0f, alpha)
                .uv(u, v)
                .endVertex();
    }

    /** 刀光生命周期透明度：生成淡入、结束前淡出。 */
    private static float computeLifeFade(TerraswordWaveEntity entity, float partialTick) {
        float age = entity.tickCount + partialTick;
        float fadeIn = Mth.clamp(age / FADE_IN_TICKS, 0.0f, 1.0f);
        float fadeOut = Mth.clamp((TerraswordWaveEntity.MAX_LIFE_TICKS - age) / FADE_OUT_TICKS, 0.0f, 1.0f);
        return fadeIn * fadeOut;
    }
}
