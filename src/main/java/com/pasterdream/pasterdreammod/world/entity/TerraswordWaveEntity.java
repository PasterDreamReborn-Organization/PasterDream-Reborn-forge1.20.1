package com.pasterdream.pasterdreammod.world.entity;

import com.mojang.logging.LogUtils;
import com.pasterdream.pasterdreammod.helper.MinionFilterHelper;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TerraswordWaveEntity extends PathfinderMob {

    private int lifeTicks = 0;
    private static final int BLOCK_HIT_LINGER_TICKS = 10;
    private int blockHitLifeTick = -1;
    public static final int MAX_LIFE_TICKS = 32; // 最大存活时间(tick)，1.6 秒
    private static final double COLLISION_RADIUS_BASE = 2.5; // 基础碰撞半径（水平）
    private static final double COLLISION_HEIGHT = 5.0; // 攻击范围高度（竖直，格）
    private static final double COLLISION_RADIUS_SWEEPING_BONUS = 0.5; // 横扫之刃每级碰撞半径加成
    private static final int MAX_SWEEPING_EDGE_LEVEL = 3; // 横扫之刃加成生效的最大等级(III)
    private static final int PARTICLE_COUNT_BASE = 8; // 基础粒子数量
    private static final int PARTICLE_COUNT_SWEEPING_BONUS = 2; // 横扫之刃每级粒子数量加成
    private static final double PARTICLE_SPREAD_BASE = 0.3; // 基础水平粒子散布
    private static final double PARTICLE_SPREAD_SWEEPING_BONUS = 0.3; // 横扫之刃每级水平粒子散布加成
    private static final double PARTICLE_VELOCITY = 0.1; // 粒子初速度
    private static final double BASE_DAMAGE_OFFSET = 2.0; // 基础伤害偏移
    private static final float SMITE_BANE_MULTIPLIER = 2.5f; // 亡灵杀手/节肢杀手每级伤害加成
    private static final double PENETRATION_DECAY = 0.75; // 每次命中后伤害乘数（累乘 25% 衰减）
    private static final float MIN_DAMAGE = 1.0f; // 衰减至 <= 该伤害后消失
    private static final int FIRE_ASPECT_TICK_MULTIPLIER = 4; // 火焰附加tick倍数
    private static final float KNOCKBACK_MULTIPLIER = 0.6f; // 击退力度系数
    private static final double KNOCKBACK_Y = 0.2; // 击退Y速度
    private static final EntityDataAccessor<Float> DATA_ROLL_ANGLE =
            SynchedEntityData.defineId(TerraswordWaveEntity.class, EntityDataSerializers.FLOAT); // 刀光平面绕飞行方向的翻滚角（度）
    /** 调试用：可视化碰撞框的基础半尺寸（不含横扫之刃加成），客户端渲染 OBB 时使用。 */
    public static final double COLLISION_HALF_WIDTH = COLLISION_RADIUS_BASE / 2d;
    public static final double COLLISION_HALF_HEIGHT = COLLISION_HEIGHT / 2d;
    public static final double COLLISION_HALF_DEPTH = COLLISION_RADIUS_BASE / 2d;
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final boolean DEBUG_LOG = false; // 调试：需要排查飞行方向时再启用
    private static final double DIRECTION_DEVIATION_WARN_DEG = 0.5; // 飞行方向偏离初速方向超过该角度才告警
    private final Set<UUID> hitEntities = new HashSet<>();
    private final Set<UUID> reflectedProjectiles = new HashSet<>();
    private int penetrationCount = 0;
    private double initialSpeed = -1.0; // 生成时的初速度，用于线性减速
    @Nullable
    private Vec3 travelDir; // 生成时的飞行方向
    @Nullable
    private Player owner;
    @Nullable
    private UUID ownerUUID;

    public TerraswordWaveEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.TERRASWORD_WAVE.get(), world);
    }

    public TerraswordWaveEntity(EntityType<TerraswordWaveEntity> type, Level world) {
        super(type, world);
        xpReward = 0;
        this.setNoGravity(true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ROLL_ANGLE, 0.0f);
    }

    /** 刀光平面绕飞行方向的翻滚角（度），0~180 随机，使 5 格长轴相对水平面随机倾斜。 */
    public float getRollAngle() {
        return this.entityData.get(DATA_ROLL_ANGLE);
    }

    public void setRollAngle(float rollAngle) {
        this.entityData.set(DATA_ROLL_ANGLE, rollAngle);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new FlyingPathNavigation(this, world);
    }

    @Override
    protected void registerGoals() {
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public boolean causeFallDamage(float l, float d, DamageSource source) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        // The tiny physical box must not limit the five-block blade to a 6.4-block draw distance.
        double range = COLLISION_HEIGHT * 64.0 * getViewScale();
        return distance < range * range;
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        // Enclose the rolled blade, including its visual forward offset, bulge and thickness.
        return this.getBoundingBox().inflate(COLLISION_HALF_HEIGHT + 3.5);
    }

    @Override
    protected void pushEntities() {
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.hasUUID("Owner")) {
            this.ownerUUID = compound.getUUID("Owner");
        }
        if (compound.contains("RollAngle")) {
            this.setRollAngle(compound.getFloat("RollAngle"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (ownerUUID != null) {
            compound.putUUID("Owner", ownerUUID);
        }
        compound.putFloat("RollAngle", this.getRollAngle());
    }

    @Override
    public void baseTick() {
        super.baseTick();
        Level level = this.level();
        if (!level.isClientSide()) {
            lifeTicks++;

            if (blockHitLifeTick >= 0 && lifeTicks - blockHitLifeTick >= BLOCK_HIT_LINGER_TICKS) {
                this.discard();
                return;
            }

            // 线性负加速度：初速度在 1.6 秒(32tick)内匀速衰减到 0。
            // 配合 3.5 格/tick 初速度，实际移动约 54.25 格，含生成偏移后约 55 格。
            if (initialSpeed < 0.0) {
                Vec3 motion = this.getDeltaMovement();
                initialSpeed = motion.length();
                travelDir = motion.lengthSqr() > 1.0E-7 ? motion.normalize() : this.getLookAngle();
            }
            double currentSpeed = Math.max(0.0, initialSpeed * (1.0 - (double) lifeTicks / MAX_LIFE_TICKS));
            if (travelDir != null) {
                this.setDeltaMovement(travelDir.scale(currentSpeed));
            }

            CompoundTag data = this.getPersistentData();
            int sweepingEdge = Math.min(data.getInt("sweeping_edge"), MAX_SWEEPING_EDGE_LEVEL);

            int particleCount = PARTICLE_COUNT_BASE + sweepingEdge * PARTICLE_COUNT_SWEEPING_BONUS;
            double particleSpread = PARTICLE_SPREAD_BASE + sweepingEdge * PARTICLE_SPREAD_SWEEPING_BONUS;
            double particleVerticalSpread = COLLISION_HEIGHT / 2;
            double radius = COLLISION_RADIUS_BASE / 2d + sweepingEdge * COLLISION_RADIUS_SWEEPING_BONUS;
            Vec3 center = new Vec3(this.getX(), this.getY(), this.getZ());

            // 朝向框架：5格高度轴垂直于飞行方向，并绕飞行方向随机翻滚（相对水平面倾角 0~180°）
            Vec3 moveDir = getMoveDirection(1.0f);
            Vec3 side = getWaveSide(moveDir);
            Vec3 heightAxis = getWaveHeightAxis(moveDir);
            final Vec3 finalSide = side;
            final Vec3 finalHeightAxis = heightAxis;
            final Vec3 finalMoveDir = moveDir;

            if (level instanceof ServerLevel serverLevel) {
                spawnOrientedParticles(serverLevel, center, finalSide, finalHeightAxis, finalMoveDir,
                        particleCount, particleSpread, particleVerticalSpread);
            }

            double pasterAtk = data.getDouble("paster_atk");
            int smite = data.getInt("smite");
            int bane = data.getInt("bane_of_arthropods");
            int fireAspect = data.getInt("fire_aspect");
            int knockback = data.getInt("knockback");

            double halfWidth = radius;
            double halfHeight = COLLISION_HEIGHT / 2;
            double halfDepth = radius;
            double searchRange = Math.max(halfWidth, Math.max(halfHeight, halfDepth));
            AABB attackBox = new AABB(center, center).inflate(searchRange);
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                    attackBox, e -> true)
                    .stream()
                    .filter(e -> isInsideOBB(e.getBoundingBox().getCenter(), center, finalSide, finalHeightAxis, finalMoveDir,
                            halfWidth, halfHeight, halfDepth))
                    .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(center)))
                    .toList();
            Player owner = resolveOwner();
            for (LivingEntity target : entities) {
                if (target != owner && !(target instanceof TerraswordWaveEntity)
                        && !MinionFilterHelper.isOwnedMinion(target, owner)
                        && !hitEntities.contains(target.getUUID())) {
                    hitEntities.add(target.getUUID());
                    float damage = (float) (BASE_DAMAGE_OFFSET + pasterAtk);
                    if (smite > 0 && target.getMobType() == MobType.UNDEAD) {
                        damage += smite * SMITE_BANE_MULTIPLIER;
                    }
                    if (bane > 0 && target.getMobType() == MobType.ARTHROPOD) {
                        damage += bane * SMITE_BANE_MULTIPLIER;
                    }
                    if (data.getBoolean("ignore_iframe")) {
                        target.invulnerableTime = 0;
                    }
                    float finalDamage = (float) (damage * Math.pow(PENETRATION_DECAY, penetrationCount));
                    if (fireAspect > 0 && !target.isOnFire()) {
                        target.setSecondsOnFire(1);
                    }
                    if (owner != null) {
                        target.hurt(new DamageSource(this.damageSources().playerAttack(owner).typeHolder(), this, owner), finalDamage);
                    }
                    penetrationCount++;
                    if (fireAspect > 0) {
                        target.setSecondsOnFire(fireAspect * FIRE_ASPECT_TICK_MULTIPLIER);
                    }
                    if (knockback > 0) {
                        Vec3 kb = target.position().subtract(center).normalize().scale(knockback * KNOCKBACK_MULTIPLIER);
                        target.push(kb.x, KNOCKBACK_Y, kb.z);
                    }
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK,
                                target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(),
                                3, 0.3, 0.3, 0.3, 0.1);
                    }
                    // 每次命中后伤害累乘 25% 衰减，衰减至 <=1 时消失（可穿透无限目标）
                    if ((float) (damage * Math.pow(PENETRATION_DECAY, penetrationCount)) <= MIN_DAMAGE) {
                        if (level instanceof ServerLevel sl) {
                            sl.sendParticles(ParticleTypes.EXPLOSION,
                                    this.getX(), this.getY(), this.getZ(),
                                    3, 0.3, 0.3, 0.3, 0.1);
                        }
                        this.discard();
                        return;
                    }
                }
            }

            // Reflect incoming projectiles
            List<Projectile> projectiles = level.getEntitiesOfClass(Projectile.class,
                    attackBox, e -> true)
                    .stream()
                    .filter(p -> !reflectedProjectiles.contains(p.getUUID())
                            && isInsideOBB(p.getBoundingBox().getCenter(), center, finalSide, finalHeightAxis, finalMoveDir,
                                    halfWidth, halfHeight, halfDepth))
                    .toList();
            for (Projectile projectile : projectiles) {
                Entity projOwner = projectile.getOwner();
                if (owner != null && projOwner != null && projOwner.getUUID().equals(owner.getUUID())) {
                    continue;
                }
                reflectedProjectiles.add(projectile.getUUID());
                projectile.setDeltaMovement(projectile.getDeltaMovement().reverse());
                if (owner != null) {
                    projectile.setOwner(owner);
                }
                if (projectile instanceof AbstractArrow arrow) {
                    arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
                }
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.CRIT,
                            projectile.getX(), projectile.getY(), projectile.getZ(),
                            8, 0.3, 0.3, 0.3, 0.1);
                }
            }

            if (lifeTicks >= MAX_LIFE_TICKS) {
                if (!hitEntities.isEmpty()) {
                    this.playSound(SoundEvents.DRAGON_FIREBALL_EXPLODE, 0.7f, 1.0f);
                }
                this.discard();
            }
        }
        this.refreshDimensions();
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(1f);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, net.minecraft.world.level.block.state.BlockState state, BlockPos pos) {
    }

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        // 朝向始终跟随飞行方向：Mob 的 LookControl 每 tick 会把 xRot 归零，这里覆盖回真实俯仰，
        // 否则 F3+B 的蓝色视线（getViewVector）不会指向前方。
        Vec3 motion = this.getDeltaMovement();
        if (motion.lengthSqr() > 1.0E-8) {
            double horizontal = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
            this.setYRot((float) (Math.atan2(motion.z, motion.x) * 180.0 / Math.PI) - 90.0F);
            this.setXRot((float) (-(Math.atan2(motion.y, horizontal) * 180.0 / Math.PI)));
        }
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
        this.yBodyRot = this.getYRot();
        this.yBodyRotO = this.yBodyRot;
        this.yHeadRot = this.getYRot();
        this.yHeadRotO = this.yHeadRot;

        if (DEBUG_LOG && !this.level().isClientSide()) {
            Vec3 moveDir = this.getMoveDirection(1.0f);
            double deviation = travelDir == null ? 0.0
                    : Math.toDegrees(Math.acos(Math.max(-1.0, Math.min(1.0, moveDir.dot(travelDir)))));
            // 只在生成首 tick 或飞行方向偏离初速方向时输出，正常飞行时保持安静
            if (lifeTicks <= 1 || deviation > DIRECTION_DEVIATION_WARN_DEG) {
                LOGGER.info("[TerraswordWave] id={} tick={} deviation={}° delta={} moveDir={} travelDir={} view={} yRot={} xRot={} roll={} heightAxis={}",
                        this.getId(), lifeTicks, fmt(deviation), fmt(motion), fmt(moveDir),
                        travelDir == null ? "null" : fmt(travelDir), fmt(this.getViewVector(1.0f)),
                        fmt(this.getYRot()), fmt(this.getXRot()), fmt(this.getRollAngle()), fmt(this.getWaveHeightAxis(moveDir)));
            }
        }
    }

    private static String fmt(Vec3 vec) {
        return String.format("(%.3f, %.3f, %.3f)", vec.x, vec.y, vec.z);
    }

    private static String fmt(float value) {
        return String.format("%.2f", value);
    }

    private static String fmt(double value) {
        return String.format("%.3f", value);
    }

    @Override
    public void travel(Vec3 input) {
        // LivingEntity.aiStep already interpolates client positions from server packets.
        // Moving again here overshoots that target and causes repeated network corrections.
        if (this.level().isClientSide()) {
            return;
        }
        // 沿初速度方向直线飞行，不做方块碰撞解算：否则 move() 会把被挡住的坐标轴速度清零，
        // 导致飞行方向（以及刀光朝向、视线）在地形边缘突然改变。
        Vec3 delta = this.getDeltaMovement();
        if (delta.lengthSqr() < 1.0E-12) {
            return;
        }
        Vec3 from = this.position();
        Vec3 to = from.add(delta);
        if (blockHitLifeTick < 0 && !this.getPersistentData().getBoolean("pierce_blocks") && hitsSolidAlong(from, to)) {
            // Keep moving for half a second after the first solid-block contact.
            blockHitLifeTick = lifeTicks;
        }
        this.setPos(to.x, to.y, to.z);
    }

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int lerpSteps, boolean teleport) {
        // 高速弹射物只用 1 tick 插值：既保留逐帧平滑移动，又不会像默认的 3 tick 插值那样
        // 让刀光明显落后于服务端的命中判定与本 tick 生成的拖尾粒子。不要直接吸附坐标，
        // 那样 xo 与当前位置相同，渲染会退化成每 tick 跳一次的卡顿。
        super.lerpTo(x, y, z, yRot, xRot, 1, teleport);
    }

    /** 沿移动线段采样，判断是否撞上实心方块（避免高速穿透）。 */
    private boolean hitsSolidAlong(Vec3 from, Vec3 to) {
        double distance = from.distanceTo(to);
        int steps = Math.max(1, (int) Math.ceil(distance / 0.5));
        for (int i = 1; i <= steps; i++) {
            Vec3 point = from.lerp(to, (double) i / steps);
            BlockPos pos = BlockPos.containing(point);
            if (!this.level().getBlockState(pos).getCollisionShape(this.level(), pos).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.5);
        builder = builder.add(Attributes.MAX_HEALTH, 1);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
        builder = builder.add(Attributes.FOLLOW_RANGE, 64);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 10);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 10);
        builder = builder.add(Attributes.FLYING_SPEED, 0.5);
        return builder;
    }

    public void setOwner(Player player) {
        this.owner = player;
        this.ownerUUID = player.getUUID();
    }

    /** 当前飞行方向：优先取速度方向，速度为 0 时退化为实体朝向。 */
    public Vec3 getMoveDirection(float partialTick) {
        Vec3 motion = this.getDeltaMovement();
        if (motion.lengthSqr() > 1.0E-8) {
            return motion.normalize();
        }
        Vec3 look = this.getViewVector(partialTick);
        return look.lengthSqr() > 1.0E-8 ? look.normalize() : new Vec3(0, 0, 1);
    }

    /** 刀光的宽度轴：垂直于飞行方向。 */
    public static Vec3 getWaveSide(Vec3 moveDir) {
        Vec3 side = moveDir.cross(new Vec3(0, 1, 0));
        if (side.lengthSqr() < 1.0E-7) {
            side = new Vec3(1, 0, 0);
        }
        return side.normalize();
    }

    /** 刀光的高度轴：垂直于飞行方向，并绕飞行方向按 {@link #getRollAngle()} 翻滚。 */
    public Vec3 getWaveHeightAxis(Vec3 moveDir) {
        Vec3 side = getWaveSide(moveDir);
        Vec3 vertical = side.cross(moveDir);
        if (vertical.lengthSqr() < 1.0E-7) {
            vertical = new Vec3(0, 1, 0);
        } else {
            vertical = vertical.normalize();
        }
        return rotateAroundAxis(vertical, moveDir, Math.toRadians(this.getRollAngle()));
    }

    /** Rodrigues 轴角旋转：把向量绕任意单位轴旋转指定弧度。 */
    public static Vec3 rotateAroundAxis(Vec3 v, Vec3 axis, double radians) {
        double sin = Math.sin(radians);
        double cos = Math.cos(radians);
        double k = 1.0 - cos;
        double a = axis.x;
        double b = axis.y;
        double c = axis.z;
        double x = v.x * (a * a * k + cos) + v.y * (a * b * k - c * sin) + v.z * (a * c * k + b * sin);
        double y = v.x * (b * a * k + c * sin) + v.y * (b * b * k + cos) + v.z * (b * c * k - a * sin);
        double z = v.x * (c * a * k - b * sin) + v.y * (c * b * k + a * sin) + v.z * (c * c * k + cos);
        return new Vec3(x, y, z);
    }

    /**
     * 判断点是否落在朝向碰撞盒（OBB）内：高度轴垂直于移动方向。
     */
    private boolean isInsideOBB(Vec3 point, Vec3 center, Vec3 side, Vec3 heightAxis, Vec3 moveDir,
                                double halfWidth, double halfHeight, double halfDepth) {
        Vec3 rel = point.subtract(center);
        return Math.abs(rel.dot(side)) <= halfWidth
                && Math.abs(rel.dot(heightAxis)) <= halfHeight
                && Math.abs(rel.dot(moveDir)) <= halfDepth;
    }

    /**
     * 在垂直于移动方向的平面内散布粒子。
     */
    private void spawnOrientedParticles(ServerLevel serverLevel, Vec3 center, Vec3 side, Vec3 heightAxis, Vec3 moveDir,
                                        int count, double spread, double verticalSpread) {
        for (int i = 0; i < count; i++) {
            double u = (serverLevel.random.nextDouble() - 0.5) * 2.0;
            // Include both ends so every burst spans the full five-block long axis.
            double v = count > 1 ? -1.0 + 2.0 * i / (count - 1) : 0.0;
            // 只向飞行方向的反侧散布：particles 在 baseTick 生成（位于上一 tick 的坐标），
            // 再向后偏移可确保拖尾始终落在刀光后方，而不是穿到刀光前面。
            double w = serverLevel.random.nextDouble();
            Vec3 offset = side.scale(u * spread)
                    .add(heightAxis.scale(v * verticalSpread))
                    .add(moveDir.scale(-w * spread));
            Vec3 pos = center.add(offset);
            serverLevel.sendParticles(ModParticleTypes.SPORE_PARTICLE.get(),
                    pos.x, pos.y, pos.z, 1, 0, 0, 0, PARTICLE_VELOCITY);
            serverLevel.sendParticles(ModParticleTypes.TERRASWORD_WAVE_PARTICLE.get(),
                    pos.x, pos.y, pos.z, 1, 0, 0, 0, PARTICLE_VELOCITY);
        }
    }

    @Nullable
    private Player resolveOwner() {
        if (owner != null && !owner.isRemoved()) {
            return owner;
        }
        if (ownerUUID != null && this.level() instanceof ServerLevel serverLevel) {
            Entity entity = serverLevel.getEntity(ownerUUID);
            if (entity instanceof Player player) {
                this.owner = player;
                return player;
            }
        }
        return null;
    }
}
