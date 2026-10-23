package com.pasterdream.pasterdreammod.world.entity.arrows;

import com.pasterdream.pasterdreammod.helper.MinionFilterHelper;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.world.entity.TrailArrowEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;

import java.util.List;

/** 魔法箭 —— 飞行时追踪附近目标，带蓝色拖尾。 */
public class MagicalArrow extends TrailArrowEntity {

    /** 追踪范围（格）：目标进入该范围后箭开始转向 */
    private static final double HOMING_RANGE = 8.0;
    /** 追踪强度：每 tick 把速度方向朝目标方向混合的比例（0~1） */
    private static final double HOMING_STRENGTH = 0.25;

    /** 拖尾头端颜色（ARGB，蓝色不透明） */
    private static final int HEAD_COLOR = 0xFF4D9FFF;
    /** 拖尾尾端颜色（ARGB，蓝色透明） */
    private static final int TAIL_COLOR = 0x004D9FFF;

    public MagicalArrow(EntityType<? extends MagicalArrow> type, Level level) {
        super(type, level);
    }

    public MagicalArrow(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.MAGICAL_ARROW.get(), level);
    }

    public MagicalArrow(Level level, LivingEntity shooter) {
        this(ModEntities.MAGICAL_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    @Override
    public int trailHeadColor() {
        return HEAD_COLOR;
    }

    @Override
    public int trailTailColor() {
        return TAIL_COLOR;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && !this.inGround && !this.isRemoved()) {
            this.applyHoming();
        }
    }

    /** 追踪：找到范围内最近目标，把速度方向朝它偏转，保持速度大小。 */
    private void applyHoming() {
        LivingEntity target = this.findTarget();
        if (target == null) {
            return;
        }
        Vec3 velocity = this.getDeltaMovement();
        if (velocity.lengthSqr() < 1.0E-6) {
            return;
        }
        Vec3 toTarget = target.getBoundingBox().getCenter().subtract(this.position());
        if (toTarget.lengthSqr() < 1.0E-6) {
            return;
        }
        double speed = velocity.length();
        Vec3 currentDir = velocity.scale(1.0 / speed);
        Vec3 targetDir = toTarget.normalize();
        Vec3 newDir = currentDir.add(targetDir.subtract(currentDir).scale(HOMING_STRENGTH)).normalize();
        this.setDeltaMovement(newDir.scale(speed));
        this.hasImpulse = true;
    }

    /** 最近目标：排除射击者、其仆从/召唤物、创造玩家、旁观者。 */
    private LivingEntity findTarget() {
        Entity owner = this.getOwner();
        Player ownerPlayer = owner instanceof Player player ? player : null;
        AABB area = this.getBoundingBox().inflate(HOMING_RANGE);
        List<LivingEntity> candidates = this.level().getEntitiesOfClass(LivingEntity.class, area,
                e -> e.isAlive() && !e.isSpectator()
                        && e != owner
                        && !(e instanceof Player player && player.isCreative())
                        && !MinionFilterHelper.isOwnedMinion(e, ownerPlayer));
        LivingEntity best = null;
        double bestDist = Double.MAX_VALUE;
        for (LivingEntity candidate : candidates) {
            double dist = candidate.distanceToSqr(this);
            if (dist < bestDist) {
                bestDist = dist;
                best = candidate;
            }
        }
        return best;
    }
}
