package com.pasterdream.pasterdreammod.world.entity.ghost;

import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;

public class ShadowGhostEntity extends Monster implements RangedAttackMob, GeoEntity, ITextureVariant {
    private static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ShadowGhostEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ShadowGhostEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ShadowGhostEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private long lastSwing;
    private int shootAnimTimer;
    public String animationprocedure = "empty";

    public ShadowGhostEntity(PlayMessages.SpawnEntity packet, Level world) {
        super((EntityType<? extends ShadowGhostEntity>)
                net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.byId(packet.getTypeId()), world);
        xpReward = 2;
        setNoAi(false);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    public ShadowGhostEntity(EntityType<? extends ShadowGhostEntity> type, Level world) {
        super(type, world);
        xpReward = 2;
        setNoAi(false);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    protected boolean isRangedVariant() {
        return this.getType() == ModEntities.SHADOW_SQUEAL_GHOST.get();
    }

    protected int getRangedAttackInterval() {
        return 30;
    }

    protected float getRangedAttackRadius() {
        return 12f;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "shadow_ghost");
    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE, texture);
    }

    @Override
    public String getTexture() {
        return this.entityData.get(TEXTURE);
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
        super.registerGoals();
        if (isRangedVariant()) {
            this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25, getRangedAttackInterval(), getRangedAttackRadius()) {
                @Override
                public boolean canContinueToUse() {
                    return this.canUse();
                }
            });
        }
        this.goalSelector.addGoal(2, new Goal() {
            { this.setFlags(EnumSet.of(Goal.Flag.MOVE)); }
            public boolean canUse() {
                return ShadowGhostEntity.this.getTarget() != null
                        && !ShadowGhostEntity.this.getMoveControl().hasWanted();
            }
            @Override
            public boolean canContinueToUse() {
                return ShadowGhostEntity.this.getMoveControl().hasWanted()
                        && ShadowGhostEntity.this.getTarget() != null
                        && ShadowGhostEntity.this.getTarget().isAlive();
            }
            @Override
            public void start() {
                LivingEntity target = ShadowGhostEntity.this.getTarget();
                Vec3 vec = target.getEyePosition(1);
                ShadowGhostEntity.this.moveControl.setWantedPosition(vec.x, vec.y, vec.z, 0.6);
            }
            @Override
            public void tick() {
                LivingEntity target = ShadowGhostEntity.this.getTarget();
                if (ShadowGhostEntity.this.getBoundingBox().intersects(target.getBoundingBox())) {
                    ShadowGhostEntity.this.doHurtTarget(target);
                } else {
                    double d0 = ShadowGhostEntity.this.distanceToSqr(target);
                    if (d0 < 5) {
                        Vec3 vec = target.getEyePosition(1);
                        ShadowGhostEntity.this.moveControl.setWantedPosition(vec.x, vec.y, vec.z, 0.6);
                    }
                }
            }
        });
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8, 20) {
            @Override
            protected Vec3 getPosition() {
                var random = ShadowGhostEntity.this.getRandom();
                double dx = ShadowGhostEntity.this.getX() + ((random.nextFloat() * 2 - 1) * 16);
                double dy = ShadowGhostEntity.this.getY() + ((random.nextFloat() * 2 - 1) * 16);
                double dz = ShadowGhostEntity.this.getZ() + ((random.nextFloat() * 2 - 1) * 16);
                return new Vec3(dx, dy, dz);
            }
        });
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.targetSelector.addGoal(6, new HurtByTargetGoal(this));
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.GENERIC_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.GENERIC_DEATH;
    }

    @Override
    public boolean causeFallDamage(float l, float d, DamageSource source) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.IN_FIRE))
            return false;
        if (source.getDirectEntity() instanceof ThrownPotion || source.getDirectEntity() instanceof AreaEffectCloud)
            return false;
        if (source.is(DamageTypes.FALL))
            return false;
        if (source.is(DamageTypes.CACTUS))
            return false;
        if (source.is(DamageTypes.DROWN))
            return false;
        return super.hurt(source, amount);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
        compound.putInt("ShootAnimTimer", shootAnimTimer);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture"))
            this.setTexture(compound.getString("Texture"));
        if (compound.contains("ShootAnimTimer"))
            shootAnimTimer = compound.getInt("ShootAnimTimer");
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.refreshDimensions();
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(1f);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.setNoGravity(true);
        if (shootAnimTimer > 0) {
            shootAnimTimer--;
            if (shootAnimTimer == 0) {
                this.entityData.set(SHOOT, false);
            }
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float flval) {
        this.entityData.set(SHOOT, true);
        shootAnimTimer = 5;
        SquealWaveProjectileEntity.shoot(this, target);
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            this.remove(RemovalReason.KILLED);
            this.dropExperience();
        }
    }

    // ===== GeckoLib Animation =====

    public String getSyncedAnimation() {
        return this.entityData.get(ANIMATION);
    }

    public void setAnimation(String animation) {
        this.entityData.set(ANIMATION, animation);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
        data.add(new AnimationController<>(this, "attacking", 4, this::attackingPredicate));
        data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private PlayState movementPredicate(software.bernie.geckolib.core.animation.AnimationState<ShadowGhostEntity> event) {
        if (this.animationprocedure.equals("empty")) {
            if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.STOP;
    }

    protected PlayState attackingPredicate(software.bernie.geckolib.core.animation.AnimationState<ShadowGhostEntity> event) {
        if (getAttackAnim(event.getPartialTick()) > 0f && !this.swinging) {
            this.swinging = true;
            this.lastSwing = level().getGameTime();
        }
        if (this.swinging && this.lastSwing + 7L <= level().getGameTime()) {
            this.swinging = false;
        }
        boolean shouldAttack = this.swinging || (isRangedVariant() && this.entityData.get(SHOOT));
        if (shouldAttack && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().forceAnimationReset();
            return event.setAndContinue(RawAnimation.begin().thenPlay("attack"));
        }
        return PlayState.CONTINUE;
    }

    private PlayState procedurePredicate(software.bernie.geckolib.core.animation.AnimationState<ShadowGhostEntity> event) {
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

    // ===== Attributes =====

    public static AttributeSupplier.Builder createShadowGhostAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.8)
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.FOLLOW_RANGE, 20)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.FLYING_SPEED, 0.8);
    }

    public static AttributeSupplier.Builder createShadowSquealGhostAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.7)
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.FOLLOW_RANGE, 20)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.FLYING_SPEED, 0.7);
    }

    public static void init() {
        // Spawn placements deferred - natural spawning not yet configured
    }
}
