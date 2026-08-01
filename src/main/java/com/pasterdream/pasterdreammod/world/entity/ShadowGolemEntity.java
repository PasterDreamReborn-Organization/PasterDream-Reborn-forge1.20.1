package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.helper.ShadowDifficultyHelper;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class ShadowGolemEntity extends Monster implements GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ShadowGolemEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ShadowGolemEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ShadowGolemEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private long lastSwing;
    public String animationprocedure = "empty";

    private int skillTimer;
    private int timeCounter;

    public ShadowGolemEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.SHADOW_GOLEM.get(), world);
    }

    public ShadowGolemEntity(EntityType<ShadowGolemEntity> type, Level world) {
        super(type, world);
        xpReward = 7;
        setNoAi(false);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "shadow_golem");
    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE, texture);
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.9));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 1.8;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(net.minecraft.sounds.SoundEvents.DEEPSLATE_TILES_FALL, 0.15f, 1);
    }

    @Override
    public net.minecraft.sounds.SoundEvent getHurtSound(DamageSource ds) {
        return net.minecraft.sounds.SoundEvents.DEEPSLATE_TILES_BREAK;
    }

    @Override
    public net.minecraft.sounds.SoundEvent getDeathSound() {
        return net.minecraft.sounds.SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof AbstractArrow)
            return false;
        if (source.getDirectEntity() instanceof ThrownPotion || source.getDirectEntity() instanceof net.minecraft.world.entity.AreaEffectCloud)
            return false;
        if (source.is(DamageTypes.FALL))
            return false;
        if (source.is(DamageTypes.CACTUS))
            return false;
        // When hurt, accelerate skill timer
        if (timeCounter < 189) {
            timeCounter += 10;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
        compound.putInt("SkillTimer", skillTimer);
        compound.putInt("TimeCounter", timeCounter);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture"))
            this.setTexture(compound.getString("Texture"));
        if (compound.contains("SkillTimer"))
            skillTimer = compound.getInt("SkillTimer");
        if (compound.contains("TimeCounter"))
            timeCounter = compound.getInt("TimeCounter");
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (!this.level().isClientSide()) {
            updateSkillLogic();
        }
        this.refreshDimensions();
    }

    private void updateSkillLogic() {
        Level world = this.level();
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();

        if (skillTimer > 0) {
            skillTimer--;

            // 1 tick after start: play roar sound
            if (skillTimer == 43) {
                this.playSound(ModSounds.ROAR0.get(), 1.2f, 1);
            }

            // 8 ticks after start: set skill animation
            if (skillTimer == 36) {
                this.setAnimation("skill");
            }

            // 44 ticks after start: cast skill
            if (skillTimer == 0) {
                if (world instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, x, y, z, 200, 3, 0.1, 3, 0.2);
                    serverLevel.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), x, y, z, 200, 3, 0.4, 3, 0.1);
                }
                this.playSound(net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE, 1, 1);

                // 以实体脚部为中心，上下各5格覆盖
                if (!ShadowDifficultyHelper.isSpecialSkillEnabled(world)) return;

                AABB area = AABB.ofSize(new Vec3(x, y, z), 10, 10, 10);
                List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, area,
                        e -> !e.getType().is(ModEntityTypeTags.SHADOW_MOB));
                for (LivingEntity target : entities) {
                    target.hurt(new DamageSource(world.registryAccess()
                            .registryOrThrow(Registries.DAMAGE_TYPE)
                            .getHolderOrThrow(DamageTypes.MOB_ATTACK), this),
                            ShadowDifficultyHelper.getGolemSkillDamage(world));
                    target.setDeltaMovement(new Vec3(0, 1.5, 0));
                }
            }
            return;
        }

        // Normal ticking: increment counter, check for trigger
        if (timeCounter >= 200) {
            if (!world.getEntitiesOfClass(Player.class,
                    AABB.ofSize(new Vec3(x, y, z), 10, 10, 10), e -> true).isEmpty()) {
                this.setAnimation("storage");
                skillTimer = 44;
                timeCounter = 0;
                return;
            }
            timeCounter = 0;
        } else {
            timeCounter++;
        }
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(1f);
    }

    public static void init() {
        SpawnPlacements.register(ModEntities.SHADOW_GOLEM.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) ->
                        world.getDifficulty() != net.minecraft.world.Difficulty.PEACEFUL
                                && Monster.isDarkEnoughToSpawn(world, pos, random)
                                && Mob.checkMobSpawnRules(entityType, world, reason, pos, random));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.MAX_HEALTH, 150)
                .add(Attributes.ARMOR, 8)
                .add(Attributes.ATTACK_DAMAGE, 20)
                .add(Attributes.FOLLOW_RANGE, 20)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.7)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5);
    }

    // ===== GeckoLib Animation =====

    private PlayState movementPredicate(software.bernie.geckolib.core.animation.AnimationState<ShadowGolemEntity> event) {
        if (this.getSyncedAnimation().equals("empty")) {
            if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))
                    && !this.isAggressive()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            }
            if (this.isSprinting()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            }
            if (this.isAggressive() && event.isMoving()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("attack"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.STOP;
    }

    private PlayState attackingPredicate(software.bernie.geckolib.core.animation.AnimationState<ShadowGolemEntity> event) {
        double d1 = this.getX() - this.xOld;
        double d0 = this.getZ() - this.zOld;
        if (getAttackAnim(event.getPartialTick()) > 0f && !this.swinging) {
            this.swinging = true;
            this.lastSwing = level().getGameTime();
        }
        if (this.swinging && this.lastSwing + 7L <= level().getGameTime()) {
            this.swinging = false;
        }
        if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().forceAnimationReset();
            return event.setAndContinue(RawAnimation.begin().thenPlay("attack"));
        }
        return PlayState.CONTINUE;
    }

    private PlayState procedurePredicate(software.bernie.geckolib.core.animation.AnimationState<ShadowGolemEntity> event) {
        String anim = this.getSyncedAnimation();
        if (!anim.equals("empty") && !anim.equals("undefined")
                && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay(anim));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                this.setAnimation("empty");
                event.getController().forceAnimationReset();
            }
        } else if (anim.equals("empty")) {
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            this.remove(RemovalReason.KILLED);
            this.dropExperience();
        }
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
}
