package com.pasterdream.pasterdreammod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.renderhelper.CustomRenderTypes;
import com.pasterdream.pasterdreammod.world.entity.MeltDreamArrowEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 融梦箭拖尾渲染：客户端快照 + 三棱柱管状拖尾。
 * <p>
 * 在 {@code RenderLevelStageEvent.AFTER_PARTICLES} 阶段遍历拖尾快照，沿轨迹绘制
 * <b>三棱柱管状</b>拖尾（非朝向相机的平面 ribbon）：每个采样点算一个三角形横截面，
 * 相邻横截面之间画 3 个侧面四边形，头/尾各封 3 个收拢到端点的四边形。
 * 顶点色由头部（亮粉不透明）渐变到尾部（粉透明），配合加法混合呈发光感。
 * <p>
 * 拖尾数据以「快照」形式保存在客户端（每 tick 从箭实体刷新）。箭命中实体/方块后，
 * 快照仍保留并继续渲染，同时从尾端向命中点逐点收缩，因此拖尾不会随箭一起瞬间消失。
 */
@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID, value = Dist.CLIENT)
public final class MeltDreamTrailRenderer {

    /** 拖尾横截面半径（格） */
    private static final double TRAIL_SIZE = 0.08;
    /** 拖尾起点相对箭中心沿飞行方向的向前偏移（格），使拖尾接到箭尖，命中目标时能接触目标 */
    private static final float HEAD_OFFSET = 0.6F;
    /** 横截面两翼相对主法线的旋转角（度） */
    private static final double CROSS_ANGLE = 100.0;
    /** 落地/被移除后拖尾收缩消失的总时长（tick） */
    private static final float DISSOLVE_TICKS = 20.0F;

    /** 头部颜色（ARGB，亮粉不透明） */
    private static final int HEAD_COLOR = 0xFFFF8AD8;
    /** 尾部颜色（ARGB，粉透明） */
    private static final int TAIL_COLOR = 0x00FF55B0;

    private static final float HEAD_R = ((HEAD_COLOR >> 16) & 0xFF) / 255.0F;
    private static final float HEAD_G = ((HEAD_COLOR >> 8) & 0xFF) / 255.0F;
    private static final float HEAD_B = (HEAD_COLOR & 0xFF) / 255.0F;
    private static final float HEAD_A = ((HEAD_COLOR >>> 24) & 0xFF) / 255.0F;
    private static final float TAIL_R = ((TAIL_COLOR >> 16) & 0xFF) / 255.0F;
    private static final float TAIL_G = ((TAIL_COLOR >> 8) & 0xFF) / 255.0F;
    private static final float TAIL_B = (TAIL_COLOR & 0xFF) / 255.0F;
    private static final float TAIL_A = ((TAIL_COLOR >>> 24) & 0xFF) / 255.0F;

    private static final Map<Integer, TrailSnapshot> SNAPSHOTS = new HashMap<>();

    private MeltDreamTrailRenderer() {
    }

    /** 每 tick 从存活箭实体刷新快照；消失的实体转入残影并延时移除。 */
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            SNAPSHOTS.clear();
            return;
        }

        Set<Integer> seen = new HashSet<>();
        for (Entity entity : level.entitiesForRendering()) {
            if (!(entity instanceof MeltDreamArrowEntity arrow)) {
                continue;
            }
            int id = arrow.getId();
            TrailSnapshot snapshot = SNAPSHOTS.computeIfAbsent(id, TrailSnapshot::new);
            snapshot.entity = arrow;
            snapshot.points = new ArrayList<>(arrow.getClientTrail());
            snapshot.lastOrigin = arrow.position();
            Vec3 motion = arrow.getDeltaMovement();
            snapshot.lastDirection = motion.lengthSqr() > 1.0E-8
                    ? motion.normalize()
                    : arrow.getViewVector(1.0F);
            // 飞行中不收缩；落地后按已落地时长收缩
            snapshot.dissolveTicks = arrow.isArrowInGround() ? arrow.getArrowInGroundTime() : 0;
            seen.add(id);
        }

        Iterator<TrailSnapshot> iterator = SNAPSHOTS.values().iterator();
        while (iterator.hasNext()) {
            TrailSnapshot snapshot = iterator.next();
            if (seen.contains(snapshot.entityId)) {
                continue;
            }
            snapshot.entity = null;
            // 从当前收缩进度继续（落地后被拾取不会“复生”）；飞行中命中被移除则从 0 开始
            if (++snapshot.dissolveTicks > DISSOLVE_TICKS) {
                iterator.remove();
            }
        }
    }

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || SNAPSHOTS.isEmpty()) {
            return;
        }

        Vec3 cameraPos = event.getCamera().getPosition();
        float partialTick = event.getPartialTick();
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();

        boolean renderedAnything = false;
        for (TrailSnapshot snapshot : SNAPSHOTS.values()) {
            if (snapshot.points.size() < 2) {
                continue;
            }

            Vec3 origin;
            // dissolve：0 = 完整拖尾，1 = 完全收缩消失；由快照累计的收缩时长推进，落地后被拾取也不重置
            float dissolve;
            MeltDreamArrowEntity entity = snapshot.entity;
            if (entity != null && !entity.isRemoved()) {
                origin = entity.getPosition(partialTick);
                dissolve = snapshot.dissolveTicks > 0
                        ? Mth.clamp((snapshot.dissolveTicks + partialTick) / DISSOLVE_TICKS, 0.0F, 1.0F)
                        : 0.0F;
            } else {
                origin = snapshot.lastOrigin;
                dissolve = Mth.clamp((snapshot.dissolveTicks + partialTick) / DISSOLVE_TICKS, 0.0F, 1.0F);
            }
            if (dissolve >= 1.0F) {
                continue;
            }
            float fade = 1.0F - dissolve;

            // 沿飞行方向整体平移到箭尖（拖尾从箭头发散）；避免只偏移头端导致头段轴方向每 tick 翻转
            Vec3 shift = snapshot.lastDirection.scale(HEAD_OFFSET);
            poseStack.pushPose();
            poseStack.translate(origin.x - cameraPos.x + shift.x, origin.y - cameraPos.y + shift.y,
                    origin.z - cameraPos.z + shift.z);
            drawTrail(bufferSource.getBuffer(CustomRenderTypes.MELT_DREAM_TRAIL),
                    poseStack.last().pose(), snapshot.points, origin, fade, dissolve);
            poseStack.popPose();
            renderedAnything = true;
        }

        if (renderedAnything) {
            bufferSource.endBatch(CustomRenderTypes.MELT_DREAM_TRAIL);
        }
    }

    /** @param trail 世界坐标采样点（旧 → 新） */
    private static void drawTrail(VertexConsumer consumer, Matrix4f matrix, List<Vec3> trail, Vec3 origin,
                                  float fade, float dissolve) {
        // 头 → 尾：头端直接取实体插值位置（后移量由调用方整体平移姿态实现）+ 采样点倒序（新 → 旧）
        List<Vec3> raw = new ArrayList<>(trail.size() + 1);
        raw.add(origin);
        for (int i = trail.size() - 1; i >= 0; i--) {
            raw.add(trail.get(i));
        }
        // 收缩：dissolve 增大时从尾端（旧点）向头端裁剪，使拖尾缩回命中点而非原地淡出
        List<Vec3> points = trimTail(raw, dissolve);
        int count = points.size();
        if (count < 3) {
            return;
        }

        // 转成相对 origin 的局部坐标（矩阵已平移到 origin）
        Vec3[] local = new Vec3[count];
        for (int i = 0; i < count; i++) {
            local[i] = points.get(i).subtract(origin);
        }

        // 每个点的三角形横截面（3 个偏移向量）
        Vec3[][] sections = new Vec3[count][3];
        for (int i = 0; i < count; i++) {
            Vec3 axis;
            if (i == 0) {
                axis = points.get(1).subtract(points.get(0));
            } else if (i == count - 1) {
                axis = points.get(count - 1).subtract(points.get(count - 2));
            } else {
                axis = points.get(i + 1).subtract(points.get(i - 1));
            }
            if (axis.lengthSqr() < 1.0E-8) {
                continue;
            }
            axis = axis.normalize();

            float t = (float) i / (float) (count - 1);
            double radius = TRAIL_SIZE * (1.0 - 0.85 * t);

            Vec3 normal = axis.cross(new Vec3(0.0, 1.0, 0.0));
            if (normal.lengthSqr() < 1.0E-4) {
                normal = axis.cross(new Vec3(1.0, 0.0, 0.0));
            }
            if (normal.lengthSqr() < 1.0E-8) {
                continue;
            }
            normal = normal.normalize().scale(radius);
            sections[i][0] = normal;
            sections[i][1] = rotate(normal, axis, CROSS_ANGLE);
            sections[i][2] = rotate(normal, axis, -CROSS_ANGLE);
        }

        for (int i = 0; i < count - 1; i++) {
            if (sections[i][0] == null || sections[i + 1][0] == null) {
                continue;
            }
            float[] c1 = colorAt(i, count, fade);
            float[] c2 = colorAt(i + 1, count, fade);
            sideQuad(consumer, matrix, local[i], local[i + 1], sections[i], sections[i + 1], 0, 1, c1, c2);
            sideQuad(consumer, matrix, local[i], local[i + 1], sections[i], sections[i + 1], 1, 2, c1, c2);
            sideQuad(consumer, matrix, local[i], local[i + 1], sections[i], sections[i + 1], 2, 0, c1, c2);
        }

        if (sections[0][0] != null) {
            cap(consumer, matrix, local[0], sections[0], colorAt(0, count, fade));
        }
        if (sections[count - 1][0] != null) {
            cap(consumer, matrix, local[count - 1], sections[count - 1], colorAt(count - 1, count, fade));
        }
    }

    /**
     * 从尾端（列表末端、最旧的点）向头端裁剪一定比例，使拖尾收缩回命中点。
     * <p>
     * {@code amount} 为 0 时原样返回；为 1 时全部裁掉。裁剪按点为单位、并对最后保留点做
     * 分数插值，保证收缩过程平滑无跳变。列表顺序为头 → 尾。
     */
    private static List<Vec3> trimTail(List<Vec3> points, float amount) {
        int count = points.size();
        if (count < 2 || amount <= 0.0F) {
            return points;
        }
        float scaled = Mth.clamp(amount, 0.0F, 1.0F) * (count - 1);
        int drop = (int) scaled;
        float frac = scaled - drop;
        int keep = count - drop;
        if (keep < 2) {
            return List.of();
        }
        List<Vec3> result = new ArrayList<>(keep);
        for (int i = 0; i < keep; i++) {
            result.add(points.get(i));
        }
        if (frac > 0.0F) {
            // 尾点朝前一个点收拢，保证跨刻连续
            result.set(keep - 1, result.get(keep - 1).lerp(result.get(keep - 2), frac));
        }
        return result;
    }

    private static void sideQuad(VertexConsumer consumer, Matrix4f matrix, Vec3 from, Vec3 to,
                                 Vec3[] sectionFrom, Vec3[] sectionTo, int a, int b, float[] c1, float[] c2) {
        Vec3 v1 = from.add(sectionFrom[a]);
        Vec3 v2 = to.add(sectionTo[a]);
        Vec3 v3 = to.add(sectionTo[b]);
        Vec3 v4 = from.add(sectionFrom[b]);
        vertex(consumer, matrix, v4, c1);
        vertex(consumer, matrix, v1, c1);
        vertex(consumer, matrix, v2, c2);
        vertex(consumer, matrix, v3, c2);
    }

    private static void cap(VertexConsumer consumer, Matrix4f matrix, Vec3 tip, Vec3[] section, float[] color) {
        Vec3 p0 = tip.add(section[0]);
        Vec3 p1 = tip.add(section[1]);
        Vec3 p2 = tip.add(section[2]);
        quad(consumer, matrix, p1, tip, tip, p0, color, color);
        quad(consumer, matrix, p2, tip, tip, p1, color, color);
        quad(consumer, matrix, p0, tip, tip, p2, color, color);
    }

    private static void quad(VertexConsumer consumer, Matrix4f matrix, Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4,
                             float[] c1, float[] c2) {
        vertex(consumer, matrix, v4, c1);
        vertex(consumer, matrix, v1, c1);
        vertex(consumer, matrix, v2, c2);
        vertex(consumer, matrix, v3, c2);
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix, Vec3 position, float[] color) {
        consumer.vertex(matrix, (float) position.x, (float) position.y, (float) position.z)
                .color(color[0], color[1], color[2], color[3])
                .endVertex();
    }

    /** 头部(0) → 尾部(1) 的颜色与透明度插值。 */
    private static float[] colorAt(int index, int count, float fade) {
        float t = count <= 1 ? 1.0F : (float) index / (float) (count - 1);
        float r = Mth.lerp(t, HEAD_R, TAIL_R);
        float g = Mth.lerp(t, HEAD_G, TAIL_G);
        float b = Mth.lerp(t, HEAD_B, TAIL_B);
        float a = Mth.lerp(t, HEAD_A, TAIL_A) * fade;
        return new float[]{r, g, b, a};
    }

    /** Rodrigues 轴角旋转：把向量绕任意轴旋转指定角度。 */
    private static Vec3 rotate(Vec3 v, Vec3 axis, double degrees) {
        double angle = Math.toRadians(degrees);
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double k = 1.0 - cos;
        double a = axis.x;
        double b = axis.y;
        double c = axis.z;
        double x = v.x * (a * a * k + cos) + v.y * (a * b * k - c * sin) + v.z * (a * c * k + b * sin);
        double y = v.x * (b * a * k + c * sin) + v.y * (b * b * k + cos) + v.z * (b * c * k - a * sin);
        double z = v.x * (c * a * k - b * sin) + v.y * (c * b * k + a * sin) + v.z * (c * c * k + cos);
        return new Vec3(x, y, z);
    }

    /** 单条拖尾的客户端快照：实体消失后仍保留采样点用于收缩。 */
    private static final class TrailSnapshot {
        final int entityId;
        MeltDreamArrowEntity entity;
        List<Vec3> points = List.of();
        Vec3 lastOrigin = Vec3.ZERO;
        /** 最近一次的飞行/朝向方向（落地静止时也保留，供收缩阶段定位箭尖） */
        Vec3 lastDirection = Vec3.ZERO;
        /** 收缩进度累计 tick：飞行中为 0，落地/移除后递增，跨阶段连续不重置 */
        int dissolveTicks;

        TrailSnapshot(int entityId) {
            this.entityId = entityId;
        }
    }
}
