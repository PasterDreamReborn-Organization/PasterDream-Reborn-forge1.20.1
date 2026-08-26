package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Comparator;

/**
 * 雷云基类：普通雷云与高压雷云的公共逻辑。
 * 包含：对攻击者产生仇恨（HurtByTargetGoal + hurt 锁目标）、
 * 有仇恨目标时在目标头顶盘旋、被破风骑士召唤时跟随骑士的仇恨目标。
 */
public abstract class AbstractThundercloudEntity extends Monster implements GeoEntity {
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(AbstractThundercloudEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(AbstractThundercloudEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";

    // 飞行参数（直接速度控制，不依赖 move control/寻路，保证雷云一定会移动）
    private static final double ORBIT_RADIUS = 3.0;          // 环绕半径（格）
    private static final double ORBIT_HEIGHT = 8.0;          // 环绕高度（目标脚底上方，格）
    private static final double ORBIT_ANGLE_SPEED = 0.1;     // 环绕角速度（弧度/tick）
    private static final double ORBIT_SPEED = 0.35;          // 并入轨道时的飞行速度（格/tick）
    private static final double IDLE_SPEED = 0.15;           // 无目标时飘动速度（格/tick）
    private static final double IDLE_RANGE = 4.0;            // 无目标时飘动水平范围（格）
    private static final double IDLE_VERTICAL = 3.0;         // 无目标时飘动垂直范围（格）
    private static final int IDLE_REPICK = 200;              // 飘动目标未到达时的重新选取间隔（tick）
    private static final int LIGHTNING_AGGRO_RANGE = 32;     // 仇恨目标落雷最大距离（格）

    private WindKnightEntity summoningKnight;
    private double orbitAngle;
    private Vec3 driftTarget;
    private int driftRepick;

    public AbstractThundercloudEntity(EntityType<? extends AbstractThundercloudEntity> type, Level world) {
        super(type, world);
        setNoAi(false);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, getDefaultTexture());
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level world) {
        return new FlyingPathNavigation(this, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        // 被攻击后记住攻击者作为仇恨目标（落雷会优先打它）
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.GENERIC_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.CANDLE_EXTINGUISH;
    }

    @Override
    public boolean causeFallDamage(float l, float d, DamageSource source) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!level().isClientSide()) {
            // 被生物攻击时将其设为仇恨目标（完全免疫的伤害类型不产生仇恨）
            if (!source.is(DamageTypes.LIGHTNING_BOLT) && !(isFireImmune() && source.is(DamageTypes.IN_FIRE))
                    && source.getEntity() instanceof LivingEntity living && living.isAlive()) {
                this.setTarget(living);
            }
            if (random.nextDouble() <= 0.5) {
                attackWithLightning();
            }
        }
        if (source.is(DamageTypes.LIGHTNING_BOLT) || (isFireImmune() && source.is(DamageTypes.IN_FIRE)))
            return false;
        return super.hurt(source, amount);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture"))
            this.setTexture(compound.getString("Texture"));
    }

    @Override
    public void baseTick() {
        super.baseTick();
        attackTick();
        if (!level().isClientSide()) {
            syncSummonerTarget();
        }
        this.refreshDimensions();
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return super.getDimensions(pose).scale(getDimensionScale());
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, net.minecraft.core.BlockPos pos) {
    }

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.setNoGravity(true);
        if (!level().isClientSide()) {
            updateFlight();
        }
    }

    /**
     * 飞行控制：不依赖 GoalSelector / move control / 寻路，直接设置速度。
     * 有仇恨目标时在目标头顶匀速盘旋（贴轨后沿圆周切线飞行，平滑无顿挫），
     * 否则在当前位置附近缓慢飘动。
     */
    private void updateFlight() {
        LivingEntity target = getTarget();
        if (target != null && target.isAlive() && !target.isRemoved()
                && !(target instanceof Player p && (p.isCreative() || p.isSpectator()))) {
            this.orbitAngle += ORBIT_ANGLE_SPEED;
            double px = target.getX() + Math.cos(this.orbitAngle) * ORBIT_RADIUS;
            double pz = target.getZ() + Math.sin(this.orbitAngle) * ORBIT_RADIUS;
            double py = target.getY() + ORBIT_HEIGHT;
            double dx = px - getX();
            double dy = py - getY();
            double dz = pz - getZ();
            double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (dist < 0.6) {
                // 已贴近轨道点：沿圆周切线匀速绕行，避免"追点-停下-追点"的顿挫
                double arc = ORBIT_RADIUS * ORBIT_ANGLE_SPEED;
                this.setDeltaMovement(new Vec3(-Math.sin(this.orbitAngle) * arc, 0, Math.cos(this.orbitAngle) * arc));
            } else {
                this.setDeltaMovement(new Vec3(dx, dy, dz).normalize().scale(Math.min(ORBIT_SPEED, dist)));
            }
            this.getLookControl().setLookAt(target, 30.0F, 30.0F);
            return;
        }
        // 无目标：在原地附近缓慢飘动
        Vec3 wanted = pickDriftTarget();
        if (wanted == null)
            return;
        Vec3 delta = wanted.subtract(position());
        double dist = delta.length();
        if (dist < 0.5) {
            this.setDeltaMovement(Vec3.ZERO);
            return;
        }
        this.setDeltaMovement(delta.normalize().scale(Math.min(IDLE_SPEED, dist)));
    }

    /** 飘动：选当前位置附近随机的空中点（到达或超时后换新目标） */
    private Vec3 pickDriftTarget() {
        if (driftTarget != null
                && this.distanceToSqr(driftTarget.x, driftTarget.y, driftTarget.z) >= 1.0
                && ++driftRepick < IDLE_REPICK) {
            return driftTarget;
        }
        driftRepick = 0;
        for (int i = 0; i < 10; i++) {
            double x = getX() + (getRandom().nextFloat() * 2 - 1) * IDLE_RANGE;
            double y = getY() + (getRandom().nextFloat() * 2 - 1) * IDLE_VERTICAL;
            double z = getZ() + (getRandom().nextFloat() * 2 - 1) * IDLE_RANGE;
            if (level().isEmptyBlock(BlockPos.containing(x, y, z))) {
                driftTarget = new Vec3(x, y, z);
                return driftTarget;
            }
        }
        return null;
    }

    private void attackTick() {
        if (random.nextDouble() > getLightningAttackChance())
            return;
        attackWithLightning();
        if (level() instanceof ServerLevel sl) {
            sl.sendParticles(ParticleTypes.ELECTRIC_SPARK, getX(), getY(), getZ(), 1, 0.6, 0.3, 0.6, 0.004);
            sl.sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, getX(), getY(), getZ(), 1, 0.6, 0.3, 0.6, 0.004);
        }
        clearFire();
    }

    /** 选取落雷目标：优先打仇恨目标（攻击雷云的生物），否则打最近的玩家 */
    private LivingEntity resolveLightningTarget() {
        LivingEntity aggro = getTarget();
        if (aggro != null && aggro.isAlive() && !aggro.isRemoved()
                && aggro.distanceToSqr(getX(), getY() - 5, getZ()) <= LIGHTNING_AGGRO_RANGE * LIGHTNING_AGGRO_RANGE) {
            if (aggro instanceof Player p && (p.isCreative() || p.isSpectator()))
                return null;
            return aggro;
        }
        if (level().getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(getX(), getY() - 10, getZ()), 24, 24, 24), p -> !p.isCreative() && !p.isSpectator()).isEmpty())
            return null;
        return level().getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(getX(), getY() - 5, getZ()), 24, 24, 24), p -> !p.isCreative() && !p.isSpectator()).stream()
                .min(Comparator.comparingDouble(it -> it.distanceToSqr(getX(), getY() - 5, getZ()))).orElse(null);
    }

    private void attackWithLightning() {
        if (!(level() instanceof ServerLevel sl))
            return;
        LivingEntity target = resolveLightningTarget();
        if (target == null)
            return;
        float damage = getLightningDamageWithStrength();
        for (int i = 0; i < 6; i++) {
            LightningProjectileEntity proj = new LightningProjectileEntity(ModEntities.LIGHTNING_PROJECTILE.get(), level());
            proj.setBaseDamage(damage);
            proj.setKnockback(0);
            proj.setSilent(true);
            proj.setPierceLevel((byte) 1);
            proj.setPos(0.1 * Mth.nextDouble(random, -6, 6) + target.getX(), target.getY() + 5, 0.1 * Mth.nextDouble(random, -6, 6) + target.getZ());
            proj.shoot(0, -1, 0, 1, 0);
            level().addFreshEntity(proj);
        }
        level().playSound(null, target.getOnPos(), ModSounds.THUNDERCLOUD_ATTACK.get(), SoundSource.MASTER, 0.6f, 1f);
        sl.sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, target.getX(), target.getY(), target.getZ(), 16, 0.4, 0.2, 0.4, 0.004);
    }

    /** 被破风骑士召唤时记录召唤者，落雷/盘旋目标跟随骑士的仇恨目标 */
    public void setSummoningKnight(WindKnightEntity knight) {
        this.summoningKnight = knight;
    }

    /** 落雷基础伤害 × 自身力量倍率（每级 +20%，力量 II=1.4x） */
    private float getLightningDamageWithStrength() {
        float damage = getLightningDamage();
        MobEffectInstance strength = getEffect(MobEffects.DAMAGE_BOOST);
        if (strength != null) {
            damage *= 1.0F + 0.2F * (strength.getAmplifier() + 1);
        }
        return damage;
    }

    private void syncSummonerTarget() {
        if (summoningKnight == null || summoningKnight.isRemoved() || !summoningKnight.isAlive())
            return;
        LivingEntity knightTarget = summoningKnight.getTarget();
        if (knightTarget != null && knightTarget.isAlive() && !knightTarget.isRemoved()) {
            this.setTarget(knightTarget);
        }
    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE, texture);
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
    }

    public String getSyncedAnimation() {
        return this.entityData.get(ANIMATION);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (ANIMATION.equals(key)) {
            this.animationprocedure = this.entityData.get(ANIMATION);
        }
    }

    public void setAnimation(String animation) {
        this.animationprocedure = animation;
        this.entityData.set(ANIMATION, animation);
    }

    private PlayState movementPredicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(AnimationState event) {
        if (!animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                this.animationprocedure = "empty";
                event.getController().forceAnimationReset();
            }
        } else if (animationprocedure.equals("empty")) {
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 10) {
            this.remove(AbstractThundercloudEntity.RemovalReason.KILLED);
            this.dropExperience();
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
        data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    // ===== 子类差异 =====
    protected abstract String getDefaultTexture();

    protected abstract float getDimensionScale();

    protected abstract float getLightningDamage();

    protected abstract double getLightningAttackChance();

    protected abstract boolean isFireImmune();
}
