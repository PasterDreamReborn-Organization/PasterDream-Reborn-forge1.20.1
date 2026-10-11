package com.pasterdream.pasterdreammod.world.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

/**
 * 带客户端拖尾的箭实体基类。
 * <p>
 * 行为与原版 {@link Arrow} 一致，仅额外在客户端每 tick 记录轨迹采样点，
 * 由 {@code ArrowTrailRenderer} 在 {@code RenderLevelStageEvent.AFTER_PARTICLES}
 * 阶段绘制三棱柱管状拖尾。头/尾颜色由子类通过 {@link #trailHeadColor()} /
 * {@link #trailTailColor()} 指定（ARGB），渲染器按快照读取，故实体被移除后
 * 拖尾仍能按原色收缩消失。
 * <p>
 * 采样在客户端进行（位置由原版实体同步给所有玩家），故所有客户端都能看到完整拖尾。
 */
public abstract class TrailArrowEntity extends Arrow {

    /** 拖尾最大点数（每 tick 一点） */
    private static final int MAX_TRAIL_POINTS = 10;
    /** 低于该速度视为静止、不再放置新点 */
    private static final double MIN_TRAIL_SPEED = 0.001;
    /** 采样点去重阈值（平方距离） */
    private static final double MIN_SAMPLE_DISTANCE_SQR = 1.0E-6;
    /** 落地后经过该 tick 数清空拖尾 */
    private static final int GROUND_TRAIL_LIFETIME = 20;

    /** 客户端拖尾采样点（旧 → 新），仅用于渲染，不同步、不存档 */
    private final List<Vec3> clientTrail = new ArrayList<>();

    protected TrailArrowEntity(EntityType<? extends TrailArrowEntity> type, Level level) {
        super(type, level);
    }

    /** 拖尾头端颜色（ARGB） */
    public abstract int trailHeadColor();

    /** 拖尾尾端颜色（ARGB） */
    public abstract int trailTailColor();

    @Override
    public void tick() {
        // 先采样再移动：记录的是本 tick 起始位置（上一 tick 末），渲染时实体正朝新位置插值，
        // 这样最新采样点始终位于实体后方，头段方向不会翻转（避免头部抖动）。
        if (this.level().isClientSide) {
            this.updateClientTrail();
        }
        super.tick();
    }

    /** 每 tick 采样：落地后停止增长并延时清空，飞行中按点数上限保留。 */
    private void updateClientTrail() {
        if (this.inGround) {
            if (this.inGroundTime > GROUND_TRAIL_LIFETIME) {
                this.clientTrail.clear();
            }
            return;
        }
        if (this.getDeltaMovement().length() < MIN_TRAIL_SPEED) {
            return;
        }
        Vec3 current = this.position();
        if (this.clientTrail.isEmpty()
                || this.clientTrail.get(this.clientTrail.size() - 1).distanceToSqr(current) > MIN_SAMPLE_DISTANCE_SQR) {
            this.clientTrail.add(current);
            while (this.clientTrail.size() > MAX_TRAIL_POINTS) {
                this.clientTrail.remove(0);
            }
        }
    }

    /** 客户端拖尾采样点（旧 → 新），仅供渲染器读取。 */
    public List<Vec3> getClientTrail() {
        return this.clientTrail;
    }

    /** 是否已落地（{@code AbstractArrow#inGround} 为 protected，供渲染器读取）。 */
    public boolean isArrowInGround() {
        return this.inGround;
    }

    /** 落地时长（tick），供渲染器做淡出。 */
    public int getArrowInGroundTime() {
        return this.inGroundTime;
    }
}
