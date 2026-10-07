package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;

import java.util.ArrayList;
import java.util.List;

/**
 * 融梦箭 —— 融梦弓「融梦箭」强化射击专用的箭实体。
 * <p>
 * 行为与原版 {@link Arrow} 完全一致（含药水箭效果、可拾取），
 * 唯一区别是客户端会记录拖尾采样点，由 {@code MeltDreamTrailRenderer} 在
 * {@code RenderLevelStageEvent.AFTER_PARTICLES} 阶段绘制粉色渐变三棱柱拖尾。
 * 因只在强化射击时生成，实体本身即代表「拖尾开启」，无需额外同步标记。
 * <p>
 * 采样在客户端每 tick 进行（位置本身由原版实体同步给所有玩家，故所有客户端都能看到完整拖尾）。
 */
public class MeltDreamArrowEntity extends Arrow {

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

    public MeltDreamArrowEntity(EntityType<? extends MeltDreamArrowEntity> type, Level level) {
        super(type, level);
    }

    public MeltDreamArrowEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.MELT_DREAM_ARROW.get(), level);
    }

    public MeltDreamArrowEntity(Level level, LivingEntity shooter) {
        this(ModEntities.MELT_DREAM_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

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
