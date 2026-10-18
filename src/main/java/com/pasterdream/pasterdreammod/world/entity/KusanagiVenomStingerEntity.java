package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.helper.MinionFilterHelper;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.world.item.grasstool.KusanagiVenomHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 『天丛云』草薙的毒刺射弹。
 *
 * <p>以玩家准心朝向为椭圆长轴、近端位于玩家脑后的轨道飞行：
 * <ul>
 *     <li>长轴直径 10（半轴 5），短轴直径 5（半短轴 2.5）；</li>
 *     <li>发射点位于玩家脑后（避免遮挡视野）；</li>
 *     <li>轴面相对视野水平面倾斜角随机取 [-60°, 60°]；</li>
 *     <li>目标进入 3 格内时脱离轨道直冲目标；</li>
 *     <li>每枚射弹最多命中 2 个不同目标，命中叠加剧毒（无直接伤害）；</li>
 *     <li>绕轨道飞行固定圈数后消失。</li>
 * </ul>
 */
public class KusanagiVenomStingerEntity extends Entity {

    // ===== 数值（硬编码） =====
    private static final double SEMI_MAJOR = 5.0;          // 长半轴（长轴直径 10）
    private static final double SEMI_MINOR = 2.5;          // 短半轴（短轴直径 5）
    private static final double LAUNCH_BEHIND = 0.5;       // 发射点在玩家眼后距离（格）
    private static final double TICKS_PER_LAP = 24.0;      // 绕椭圆一周所需 tick
    private static final int LAPS = 2;                     // 飞行圈数（超时消失）
    private static final double HOMING_RANGE = 3.0;        // 追踪范围（格）
    private static final double HOMING_SPEED = 1.2;        // 追踪直冲速度（格/tick）
    private static final double HIT_RADIUS = 0.5;          // 命中判定膨胀半径
    private static final int MAX_HITS = 2;                 // 最多命中的目标数
    private static final double MAX_PLANE_TILT = 60.0;     // 轴面相对视野水平面的最大倾斜角（度）
    private static final int HARD_LIFE = (int) (TICKS_PER_LAP * LAPS) + 120;
    private static final float EXECUTE_DAMAGE = 1.0E6f;    // 斩杀伤害（必死）

    @Nullable
    private UUID ownerUUID;
    private final Set<UUID> hitTargets = new HashSet<>();
    // 发射时从武器读取的附魔
    private int fireAspectLevel = 0;
    private int lootingLevel = 0;

    // 椭圆轨道参数
    private Vec3 center = Vec3.ZERO;
    private Vec3 majorAxis = new Vec3(0, 0, 1);
    private Vec3 minorAxis = new Vec3(1, 0, 0);
    private double phi = Math.PI;
    private double direction = 1.0; // 绕行方向：+1 逆时针 / -1 顺时针（随机）
    private double orbitTicks = 0;
    private boolean charging = false;
    private boolean initialised = false;

    public KusanagiVenomStingerEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public KusanagiVenomStingerEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.KUSANAGI_VENOM_STINGER.get(), level);
    }

    /** 由持有者发射，以准心为长轴构建椭圆轨道。 */
    public KusanagiVenomStingerEntity(Level level, Player owner) {
        this(ModEntities.KUSANAGI_VENOM_STINGER.get(), level);
        this.ownerUUID = owner.getUUID();
        this.setupOrbit(owner);
    }

    /** 记录发射武器上的附魔等级（火焰附加、抢夺）。 */
    public void setWeaponEnchantments(int fireAspectLevel, int lootingLevel) {
        this.fireAspectLevel = Math.max(0, fireAspectLevel);
        this.lootingLevel = Math.max(0, lootingLevel);
    }

    private void setupOrbit(Player owner) {
        Vec3 dir = owner.getViewVector(1.0f).normalize();
        // 发射点位于玩家脑后：从眼睛位置沿准心反方向后退，避免射弹在视野正前方生成
        Vec3 origin = owner.getEyePosition().subtract(dir.scale(LAUNCH_BEHIND));

        Vec3 worldUp = new Vec3(0.0, 1.0, 0.0);
        Vec3 upCam = worldUp.subtract(dir.scale(worldUp.dot(dir)));
        if (upCam.lengthSqr() < 1.0E-6) {
            Vec3 fallback = new Vec3(0.0, 0.0, -1.0);
            upCam = fallback.subtract(dir.scale(fallback.dot(dir)));
        }
        upCam = upCam.normalize();
        Vec3 right = dir.cross(upCam).normalize();

        // 轴面相对视野水平面的倾斜角随机取 [-MAX, MAX]
        double angle = Math.toRadians((this.random.nextDouble() * 2.0 - 1.0) * MAX_PLANE_TILT);
        Vec3 normal = upCam.scale(Math.cos(angle)).add(right.scale(Math.sin(angle))).normalize();

        this.majorAxis = dir;
        this.minorAxis = normal.cross(dir).normalize();
        // 发射点位于长轴近端：中心在发射点前方一个半长轴处
        this.center = origin.add(dir.scale(SEMI_MAJOR));
        this.phi = Math.PI;
        this.direction = this.random.nextBoolean() ? 1.0 : -1.0;
        this.orbitTicks = 0;
        this.charging = false;
        this.initialised = true;

        this.setPos(origin.x, origin.y, origin.z);
        this.updateRotation(this.orbitVelocity().scale(this.direction));
        // 预置首 tick 的位移，供客户端在收到首个运动同步前也能外推（并用于拖尾方向）
        this.setDeltaMovement(this.orbitVelocity().scale(this.direction).scale((Math.PI * 2.0) / TICKS_PER_LAP));
    }

    private Vec3 orbitPosition() {
        return this.center
                .add(this.majorAxis.scale(SEMI_MAJOR * Math.cos(this.phi)))
                .add(this.minorAxis.scale(SEMI_MINOR * Math.sin(this.phi)));
    }

    /** dP/dφ，乘以步进角即为每 tick 位移方向。 */
    private Vec3 orbitVelocity() {
        return this.majorAxis.scale(-SEMI_MAJOR * Math.sin(this.phi))
                .add(this.minorAxis.scale(SEMI_MINOR * Math.cos(this.phi)));
    }

    private double projectPhi() {
        Vec3 rel = this.position().subtract(this.center);
        double u = rel.dot(this.majorAxis) / SEMI_MAJOR;
        double v = rel.dot(this.minorAxis) / SEMI_MINOR;
        return Math.atan2(v, u);
    }

    private void updateRotation(Vec3 velocity) {
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
        if (velocity.lengthSqr() > 1.0E-8) {
            Vec3 v = velocity.normalize();
            float yaw = (float) (Mth.atan2(v.x, v.z) * (180.0 / Math.PI));
            float pitch = (float) (Mth.atan2(v.y, Math.sqrt(v.x * v.x + v.z * v.z)) * (180.0 / Math.PI));
            this.setYRot(yaw);
            this.setXRot(pitch);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            // 客户端外推：本实体不参与客户端 AI，位置仅靠网络同步。
            // 用同步来的 deltaMovement 自行推进一 tick，丢包/抖动的当帧也不会停顿（同箭矢）。
            Vec3 dm = this.getDeltaMovement();
            if (dm.lengthSqr() > 1.0E-9) {
                this.setPos(this.getX() + dm.x, this.getY() + dm.y, this.getZ() + dm.z);
            }
            this.spawnTrail();
            return;
        }

        if (!this.initialised || this.tickCount >= HARD_LIFE) {
            this.discard();
            return;
        }

        LivingEntity target = this.findTarget();
        if (target != null && this.getBoundingBox().inflate(HIT_RADIUS).intersects(target.getBoundingBox())) {
            if (this.applyHit(target)) {
                this.discard();
                return;
            }
            target = this.findTarget();
        }

        if (target != null) {
            // 追踪：脱离椭圆，直冲目标
            this.charging = true;
            Vec3 to = target.getBoundingBox().getCenter().subtract(this.position());
            Vec3 move = to.lengthSqr() < 1.0E-6 ? Vec3.ZERO : to.normalize().scale(HOMING_SPEED);
            this.updateRotation(move);
            this.setDeltaMovement(move);
            this.setPos(this.getX() + move.x, this.getY() + move.y, this.getZ() + move.z);
            return;
        }

        if (this.charging) {
            // 目标丢失：回到椭圆轨道（投影到最近轨道点，避免瞬移）
            this.phi = this.projectPhi();
            this.charging = false;
        }

        double step = (Math.PI * 2.0) / TICKS_PER_LAP;
        this.phi += this.direction * step;
        this.orbitTicks++;
        if (this.orbitTicks >= TICKS_PER_LAP * LAPS) {
            this.discard();
            return;
        }

        Vec3 velocity = this.orbitVelocity().scale(this.direction);
        Vec3 pos = this.orbitPosition();
        this.updateRotation(velocity);
        this.setDeltaMovement(velocity.scale(step));
        this.setPos(pos.x, pos.y, pos.z);
    }

    @Nullable
    private LivingEntity findTarget() {
        Player owner = this.resolveOwnerPlayer();
        AABB area = this.getBoundingBox().inflate(HOMING_RANGE);
        List<LivingEntity> candidates = this.level().getEntitiesOfClass(LivingEntity.class, area,
                e -> e.isAlive() && !e.isSpectator()
                        && !this.hitTargets.contains(e.getUUID())
                        && !e.getUUID().equals(this.ownerUUID)
                        && !(e instanceof Player player && player.isCreative())
                        && !MinionFilterHelper.isOwnedMinion(e, owner));
        LivingEntity best = null;
        double bestDist = Double.MAX_VALUE;
        for (LivingEntity candidate : candidates) {
            double distance = candidate.distanceToSqr(this);
            if (distance < bestDist) {
                bestDist = distance;
                best = candidate;
            }
        }
        return best;
    }

    /** 命中目标：施加火焰附加、剧毒，并在条件满足时斩杀。返回是否已达命中上限。 */
    private boolean applyHit(LivingEntity target) {
        this.hitTargets.add(target.getUUID());
        if (this.fireAspectLevel > 0) {
            target.setSecondsOnFire(this.fireAspectLevel * 4);
        }
        KusanagiVenomHelper.applyStacks(target);
        this.onHitEffects(target);
        // 斩杀：仅剩 1 点生命且带有本武器剧毒的目标
        if (target.getHealth() <= 1.0F && target.hasEffect(ModEffects.HIGHLY_TOXIC.get())) {
            this.execute(target);
        }
        return this.hitTargets.size() >= MAX_HITS;
    }

    /** 斩杀目标：由持有者结算击杀（读取武器抢夺），并不受无敌帧限制。 */
    private void execute(LivingEntity target) {
        LivingEntity owner = this.resolveOwner();
        target.getPersistentData().putInt("pasterdream:stinger_execute_looting", this.lootingLevel);
        target.invulnerableTime = 0;
        if (owner instanceof Player player) {
            target.hurt(player.damageSources().playerAttack(player), EXECUTE_DAMAGE);
        } else if (owner != null) {
            target.hurt(owner.damageSources().mobAttack(owner), EXECUTE_DAMAGE);
        } else {
            target.hurt(this.damageSources().generic(), EXECUTE_DAMAGE);
        }
        target.getPersistentData().remove("pasterdream:stinger_execute_looting");
    }

    @Nullable
    private LivingEntity resolveOwner() {
        if (this.ownerUUID != null && this.level() instanceof ServerLevel serverLevel) {
            Entity entity = serverLevel.getEntity(this.ownerUUID);
            if (entity instanceof LivingEntity living) {
                return living;
            }
        }
        return null;
    }

    @Nullable
    private Player resolveOwnerPlayer() {
        LivingEntity owner = this.resolveOwner();
        return owner instanceof Player player ? player : null;
    }

    private void onHitEffects(LivingEntity target) {
        if (this.level() instanceof ServerLevel serverLevel) {
            double cx = target.getX();
            double cy = target.getY() + target.getBbHeight() * 0.5;
            double cz = target.getZ();
            serverLevel.sendParticles(ModParticleTypes.SPORE_PARTICLE.get(), cx, cy, cz, 16, 0.4, 0.5, 0.4, 0.05);
            serverLevel.sendParticles(ModParticleTypes.POISON_GAS_PARTICLE.get(), cx, cy, cz, 12, 0.4, 0.5, 0.4, 0.02);
        }
        this.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.SLIME_ATTACK, SoundSource.PLAYERS, 0.7f, 1.4f);
    }

    private void spawnTrail() {
        if (this.tickCount % 2 != 0) {
            return;
        }
        Vec3 motion = this.getDeltaMovement();
        this.level().addParticle(ModParticleTypes.SPORE_PARTICLE.get(),
                this.getX(), this.getY(), this.getZ(),
                motion.x * 0.1, motion.y * 0.1, motion.z * 0.1);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.hasUUID("Owner")) {
            this.ownerUUID = tag.getUUID("Owner");
        }
        this.fireAspectLevel = tag.getInt("FireAspect");
        this.lootingLevel = tag.getInt("Looting");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if (this.ownerUUID != null) {
            tag.putUUID("Owner", this.ownerUUID);
        }
        tag.putInt("FireAspect", this.fireAspectLevel);
        tag.putInt("Looting", this.lootingLevel);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean hurt(net.minecraft.world.damagesource.DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }
}
