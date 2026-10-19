package com.pasterdream.pasterdreammod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
 * 泰拉剑气弧形刀光渲染：不依赖模型/纹理，直接用 OpenGL（POSITION_COLOR + 加法混合）
 * 在飞行方向与高度轴构成的平面内画一片弯曲的刀光，平面法线垂直于飞行方向。
 * <p>
 * 刀光以「中心线 + 厚度」的方式构建：中心线是一条以 5 格长轴为高度、向飞行方向鼓起的
 * 弧线，沿面内法线向两侧铺开厚度，形成中间厚、两端收尖的月牙形。长轴（高度轴）绕飞行
 * 方向随机翻滚，故刀光在水平面内的倾角每次随机。
 */
public class TerraswordWaveRenderer extends EntityRenderer<TerraswordWaveEntity> {

    private static final int SEGMENTS = 20;               // 中心线分段数
    private static final double ARC_HALF_HEIGHT = 2.5;    // 5 格长轴的一半
    private static final double FORWARD_BULGE = 1.0;      // 弧面在飞行方向的鼓起量
    private static final double FORWARD_OFFSET = 2.5;    // 刀光前移，避免埋入粒子拖尾
    private static final double MAX_HALF_THICKNESS = 0.5; // 刀光最厚处半厚
    private static final double[] THICKNESS_OFFSETS = {-1.0, -0.5, 0.0, 0.5, 1.0}; // 厚度方向采样列
    private static final float[] EDGE_COLOR = {0.25f, 0.85f, 0.4f}; // 边缘：泰拉绿
    private static final float[] CORE_COLOR = {0.85f, 1.0f, 0.9f};  // 中心：亮白
    private static final float CORE_ALPHA = 0.95f;
    private static final float FADE_IN_TICKS = 2.0f;   // 生成淡入时长
    private static final float FADE_OUT_TICKS = 5.0f;  // 消失淡出时长

    public TerraswordWaveRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(TerraswordWaveEntity entity) {
        return null;
    }

    @Override
    public void render(TerraswordWaveEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        Vec3 moveDir = entity.getMoveDirection(partialTick);
        Vec3 side = TerraswordWaveEntity.getWaveSide(moveDir);
        Vec3 heightAxis = entity.getWaveHeightAxis(moveDir);
        float lifeFade = computeLifeFade(entity, partialTick);
        if (lifeFade <= 0.0f) {
            return;
        }

        Matrix4f matrix = poseStack.last().pose();
        VertexConsumer vc = buffer.getBuffer(CustomRenderTypes.SWORD_WAVE_ARC);

        int columns = THICKNESS_OFFSETS.length;
        Vec3[][] points = new Vec3[SEGMENTS + 1][columns];
        float[][] alpha = new float[SEGMENTS + 1][columns];
        float[][][] rgb = new float[SEGMENTS + 1][columns][3];

        for (int i = 0; i <= SEGMENTS; i++) {
            double t = -1.0 + 2.0 * i / SEGMENTS;
            double profile = Math.sqrt(Math.max(0.0, 1.0 - t * t));

            // The blade plane contains the flight direction, rather than facing it.
            Vec3 centre = heightAxis.scale(t * ARC_HALF_HEIGHT)
                    .add(moveDir.scale(FORWARD_OFFSET + FORWARD_BULGE * (1.0 - t * t)));

            // 面内切线 (x=飞行轴分量, y=高度轴分量) 旋转 90° 得到面内法线，用于铺厚度
            double tangentX = -2.0 * FORWARD_BULGE * t;
            double tangentY = ARC_HALF_HEIGHT;
            double normalLen = Math.sqrt(tangentX * tangentX + tangentY * tangentY);
            double normalX = normalLen > 1.0E-6 ? -tangentY / normalLen : -1.0;
            double normalY = normalLen > 1.0E-6 ? tangentX / normalLen : 0.0;
            Vec3 normal = moveDir.scale(normalX).add(heightAxis.scale(normalY));

            double halfThickness = MAX_HALF_THICKNESS * profile;
            for (int j = 0; j < columns; j++) {
                points[i][j] = centre.add(normal.scale(THICKNESS_OFFSETS[j] * halfThickness));

                double radial = 1.0 - Math.abs(THICKNESS_OFFSETS[j]);
                alpha[i][j] = CORE_ALPHA * (float) (profile * Math.pow(radial, 0.8)) * lifeFade;
                float mix = (float) radial;
                rgb[i][j][0] = Mth.lerp(mix, EDGE_COLOR[0], CORE_COLOR[0]);
                rgb[i][j][1] = Mth.lerp(mix, EDGE_COLOR[1], CORE_COLOR[1]);
                rgb[i][j][2] = Mth.lerp(mix, EDGE_COLOR[2], CORE_COLOR[2]);
            }
        }

        for (int i = 0; i < SEGMENTS; i++) {
            for (int j = 0; j < columns - 1; j++) {
                vertex(vc, matrix, points[i][j], rgb[i][j], alpha[i][j]);
                vertex(vc, matrix, points[i + 1][j], rgb[i + 1][j], alpha[i + 1][j]);
                vertex(vc, matrix, points[i + 1][j + 1], rgb[i + 1][j + 1], alpha[i + 1][j + 1]);
                vertex(vc, matrix, points[i][j + 1], rgb[i][j + 1], alpha[i][j + 1]);
            }
        }

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

    private static void vertex(VertexConsumer vc, Matrix4f matrix, Vec3 pos, float[] rgb, float a) {
        vc.vertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z)
                .color(rgb[0], rgb[1], rgb[2], a)
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
