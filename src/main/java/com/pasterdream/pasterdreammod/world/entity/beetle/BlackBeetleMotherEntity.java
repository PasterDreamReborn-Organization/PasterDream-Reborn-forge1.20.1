package com.pasterdream.pasterdreammod.world.entity.beetle;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Comparator;
import java.util.List;

public class BlackBeetleMotherEntity extends Monster implements GeoEntity {
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(BlackBeetleMotherEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(BlackBeetleMotherEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> SKILL_COOLDOWN = SynchedEntityData.defineId(BlackBeetleMotherEntity.class, EntityDataSerializers.INT);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private long lastSwing;
    public String animationprocedure = "empty";
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.PINK, ServerBossEvent.BossBarOverlay.PROGRESS);
    private int skillPhase; // 0=idle, 2=triggered, 1=summon next tick, then buff

    public BlackBeetleMotherEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.BLACK_BEETLE_MOTHER.get(), world);
    }

    public BlackBeetleMotherEntity(EntityType<BlackBeetleMotherEntity> type, Level world) {
        super(type, world);
        xpReward = 10;
        setNoAi(false);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "black_beetle_mother");
        this.entityData.define(SKILL_COOLDOWN, 0);
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
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ModSounds.BEETLE_ATTACK.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return ModSounds.BEETLE_ATTACK.get();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        triggerSkill();
        if (source.is(DamageTypes.FALL))
            return false;
        return super.hurt(source, amount);
    }

    private void triggerSkill() {
        if (this.level().isClientSide()) return;
        if (this.entityData.get(SKILL_COOLDOWN) > 0) return;
        if (this.getHealth() <= 1) return;

        this.entityData.set(SKILL_COOLDOWN, 200);
        this.skillPhase = 2;

        this.setAnimation("empty");
        this.setAnimation("skill");

        this.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 80, 0, false, false));
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 80, 1, false, false));
        this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 80, 4, false, false));
        this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1, false, false));

        this.level().playSound(null, BlockPos.containing(this.getX(), this.getY(), this.getZ()),
                ModSounds.BEETLE_SKILL.get(), SoundSource.MASTER, 2, 1);
    }

    private void executeSkillPhase() {
        if (this.level().isClientSide()) return;
        if (!this.isAlive()) return;

        if (skillPhase == 1) {
            // Summon 4 beetles
            if (this.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 4; i++) {
                    ModEntities.BLACK_BEETLE.get().spawn(serverLevel,
                            BlockPos.containing(this.getX(), this.getY(), this.getZ()),
                            MobSpawnType.MOB_SUMMONED);
                }
            }
        } else if (skillPhase == 0) {
            // Buff nearby beetles
            final Vec3 center = new Vec3(this.getX(), this.getY(), this.getZ());
            List<Entity> entities = this.level().getEntitiesOfClass(Entity.class,
                    new AABB(center, center).inflate(12), e -> true);
            Player nearestPlayer = this.level().getEntitiesOfClass(Player.class,
                            new AABB(center, center).inflate(32), e -> true).stream()
                    .min(Comparator.comparingDouble(e -> e.distanceToSqr(center)))
                    .orElse(null);

            for (Entity e : entities) {
                if (e instanceof BlackBeetleEntity beetle) {
                    beetle.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 240, 1));
                    beetle.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 240, 1));
                    beetle.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 240, 0));
                    if (nearestPlayer != null) {
                        ((Mob) beetle).setTarget(nearestPlayer);
                    }
                    beetle.setHealth(beetle.getHealth() + 1);
                }
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
        compound.putInt("SkillCooldown", this.entityData.get(SKILL_COOLDOWN));
        compound.putInt("SkillPhase", this.skillPhase);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture"))
            this.entityData.set(TEXTURE, compound.getString("Texture"));
        if (compound.contains("SkillCooldown"))
            this.entityData.set(SKILL_COOLDOWN, compound.getInt("SkillCooldown"));
        if (compound.contains("SkillPhase"))
            this.skillPhase = compound.getInt("SkillPhase");
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.refreshDimensions();
        if (!this.level().isClientSide()) {
            int cd = this.entityData.get(SKILL_COOLDOWN);
            if (cd > 0) {
                this.entityData.set(SKILL_COOLDOWN, cd - 1);
            }
            if (skillPhase > 0) {
                skillPhase--;
                executeSkillPhase();
            }
        }
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(1f);
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.MAX_HEALTH, 100)
                .add(Attributes.ARMOR, 10)
                .add(Attributes.ATTACK_DAMAGE, 10)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1);
    }

    private PlayState movementPredicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.STOP;
    }

    private PlayState attackingPredicate(AnimationState event) {
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
        if (this.deathTime == 20) {
            this.remove(RemovalReason.KILLED);
            this.dropExperience();
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
