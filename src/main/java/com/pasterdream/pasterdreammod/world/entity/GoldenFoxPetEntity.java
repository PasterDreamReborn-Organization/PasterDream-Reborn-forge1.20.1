package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
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

public class GoldenFoxPetEntity extends TamableAnimal implements GeoEntity, RangedAttackMob {

    public static final EntityDataAccessor<Boolean> SLEEPING =
            SynchedEntityData.defineId(GoldenFoxPetEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SIT_IDLE =
            SynchedEntityData.defineId(GoldenFoxPetEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION =
            SynchedEntityData.defineId(GoldenFoxPetEntity.class, EntityDataSerializers.STRING);

    private static final int SLEEP_IDLE_TICKS = 10 * 20;
    private static final int FOX_FIRE_COOLDOWN_TICKS = 45 * 20;
    private static final double FOX_FIRE_RANGE = 16.0D;
    private static final double SLEEP_RANGE_SQR = 12.0D * 12.0D;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";

    private int ownerIdleTicks;
    private int foxFireCooldown;
    private int sitIdleTicks;
    private int nextSitIdleCheck;
    private boolean hasOwnerPos;
    private Vec3 lastOwnerPos = Vec3.ZERO;
    private float lastOwnerYaw;

    public GoldenFoxPetEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.GOLDEN_FOX_PET.get(), level);
    }

    public GoldenFoxPetEntity(EntityType<GoldenFoxPetEntity> type, Level level) {
        super(type, level);
        this.xpReward = 0;
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SLEEPING, false);
        this.entityData.define(SIT_IDLE, false);
        this.entityData.define(ANIMATION, "undefined");
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new GoldenFoxRelaxOnOwnerGoal(this));
        this.goalSelector.addGoal(4, new RangedAttackGoal(this, 1.0D, 35, 16.0F));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.2D, 8.0F, 2.0F, false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Monster.class, true));
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null;
    }

    public boolean isSleeping() {
        return this.entityData.get(SLEEPING);
    }

    private boolean isSitIdle() {
        return this.entityData.get(SIT_IDLE);
    }

    private void setSitIdle(boolean sitIdle) {
        this.entityData.set(SIT_IDLE, sitIdle);
        if (!sitIdle) {
            this.sitIdleTicks = 0;
        }
    }

    private void setSleeping(boolean sleeping) {
        if (this.isSleeping() == sleeping) {
            return;
        }
        if (sleeping) {
            this.setSitIdle(false);
        }
        this.entityData.set(SLEEPING, sleeping);
        this.setAnimation(sleeping ? "transition2" : "transition");
        this.getNavigation().stop();
        this.setDeltaMovement(Vec3.ZERO);
    }

    public String getSyncedAnimation() {
        return this.entityData.get(ANIMATION);
    }

    public void setAnimation(String animation) {
        this.animationprocedure = animation;
        this.entityData.set(ANIMATION, animation);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (ANIMATION.equals(key)) {
            this.animationprocedure = this.entityData.get(ANIMATION);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.isTame() || !this.isOwnedBy(player)) {
            return super.mobInteract(player, hand);
        }
        if (this.level().isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        boolean sitting = !this.isOrderedToSit();
        this.setOrderedToSit(sitting);
        this.setSitIdle(false);
        this.setSleeping(sitting);
        this.setTarget(null);
        this.getNavigation().stop();
        this.jumping = false;
        player.displayClientMessage(Component.translatable(sitting
                ? "message.pasterdream.golden_fox_pet.sit"
                : "message.pasterdream.golden_fox_pet.follow"), true);
        this.playSound(SoundEvents.FOX_AMBIENT, 0.7F, 1.2F);
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON || effect.getEffect() == MobEffects.WITHER) {
            return false;
        }
        return super.canBeAffected(effect);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL)
                || source.is(DamageTypes.LIGHTNING_BOLT)
                || source.is(DamageTypes.FREEZE)
                || source.is(DamageTypes.IN_WALL)
                || source.is(DamageTypes.IN_FIRE)
                || source.is(DamageTypes.ON_FIRE)
                || source.is(DamageTypes.LAVA)
                || source.is(DamageTypes.HOT_FLOOR)
                || source.is(DamageTypes.WITHER)
                || source.is(DamageTypes.WITHER_SKULL)) {
            return false;
        }
        if (source.getEntity() instanceof Player player && this.isOwnedBy(player)) {
            return false;
        }
        boolean result = super.hurt(source, amount);
        if (result) {
            this.setSleeping(false);
        }
        return result;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        if (this.foxFireCooldown > 0) {
            this.foxFireCooldown--;
        }

        this.updateSleepState();
        if (this.isSleeping()) {
            this.setSprinting(false);
            this.getNavigation().stop();
            this.setTarget(null);
            Vec3 motion = this.getDeltaMovement();
            this.setDeltaMovement(motion.x * 0.2D, motion.y, motion.z * 0.2D);
        } else {
            this.tryCastFoxFire();
            this.updateSprinting();
            this.updateSitIdle();
        }
    }

    private void updateSitIdle() {
        if (this.isSitIdle()) {
            boolean stillIdle = this.getTarget() == null
                    && this.getNavigation().isDone()
                    && this.getDeltaMovement().horizontalDistanceSqr() < 0.01D;
            if (!stillIdle) {
                this.setSitIdle(false);
                return;
            }
            this.sitIdleTicks--;
            if (this.sitIdleTicks <= 0) {
                this.setSitIdle(false);
            }
            return;
        }
        if (this.isInSittingPose() || this.isOrderedToSit()) {
            this.nextSitIdleCheck = 0;
            return;
        }
        boolean standingStill = this.getTarget() == null
                && this.getNavigation().isDone()
                && this.getDeltaMovement().horizontalDistanceSqr() < 0.0004D;
        if (!standingStill) {
            this.nextSitIdleCheck = 0;
            return;
        }
        if (this.nextSitIdleCheck <= 0) {
            if (this.random.nextInt(20) == 0) {
                this.setSitIdle(true);
                this.sitIdleTicks = 40 + this.random.nextInt(80);
            }
            this.nextSitIdleCheck = 20;
        } else {
            this.nextSitIdleCheck--;
        }
    }

    private void updateSprinting() {
        boolean chase = this.getTarget() != null && this.getTarget().isAlive() && !this.isInSittingPose();
        boolean followFast = this.getOwner() instanceof Player owner
                && owner.isSprinting() && this.distanceToSqr(owner) > 4.0D;
        this.setSprinting(chase || followFast);
    }

    private void updateSleepState() {
        if (this.isOrderedToSit()) {
            this.setSleeping(true);
            return;
        }
        if (!this.isTame() || !(this.getOwner() instanceof Player owner)) {
            this.setSleeping(false);
            return;
        }

        double distSqr = this.distanceToSqr(owner);

        if (owner.isSleeping()) {
            // Approaching the bed and lying down is handled by GoldenFoxRelaxOnOwnerGoal (cat-like).
            return;
        }

        Vec3 ownerPos = owner.position();
        boolean moved = !this.hasOwnerPos
                || ownerPos.distanceToSqr(this.lastOwnerPos) > 0.0016D
                || Math.abs(Mth.wrapDegrees(owner.getYRot() - this.lastOwnerYaw)) > 3.0F;
        if (moved) {
            this.ownerIdleTicks = 0;
            this.lastOwnerPos = ownerPos;
            this.lastOwnerYaw = owner.getYRot();
            this.hasOwnerPos = true;
        } else {
            this.ownerIdleTicks++;
        }

        boolean ownerInCombat = owner.getLastHurtByMob() != null
                && owner.tickCount - owner.getLastHurtByMobTimestamp() < 100;
        boolean shouldSleep = !ownerInCombat
                && this.ownerIdleTicks >= SLEEP_IDLE_TICKS
                && distSqr < SLEEP_RANGE_SQR
                && this.onGround()
                && !this.isInWater()
                && this.getTarget() == null;
        this.setSleeping(shouldSleep);
    }

    private void tryCastFoxFire() {
        if (this.foxFireCooldown > 0 || this.isSleeping()) {
            return;
        }
        if (!(this.getOwner() instanceof Player owner) || !(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        if (owner.getHealth() >= owner.getMaxHealth()) {
            return;
        }
        if (this.distanceToSqr(owner) > FOX_FIRE_RANGE * FOX_FIRE_RANGE) {
            return;
        }

        FoxFireEntity field = new FoxFireEntity(ModEntities.FOX_FIRE.get(), serverLevel);
        field.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
        field.setOwner(owner);
        serverLevel.addFreshEntity(field);
        this.playSound(ModSounds.FOX_FIRE.get(), 1.0F, 1.0F);
        this.foxFireCooldown = FOX_FIRE_COOLDOWN_TICKS;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        if (this.level().isClientSide() || this.isSleeping()) {
            return;
        }
        GoldenFoxFireballEntity fireball = new GoldenFoxFireballEntity(
                this.level(),
                this,
                target.getX() - this.getX(),
                target.getY(0.5D) - this.getEyeY(),
                target.getZ() - this.getZ());
        fireball.setPos(this.getX(), this.getEyeY() - 0.3D, this.getZ());
        this.level().addFreshEntity(fireball);
        this.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.0F);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("FoxFireCooldown", this.foxFireCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.foxFireCooldown = compound.getInt("FoxFireCooldown");
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(1.0F);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.refreshDimensions();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.FOX_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.FOX_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.FOX_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.5F;
    }

    @Override
    protected void tickDeath() {
        this.deathTime++;
        if (this.deathTime == 20) {
            this.remove(RemovalReason.KILLED);
            this.dropExperience();
        }
    }

    private PlayState movementPredicate(AnimationState<GoldenFoxPetEntity> event) {
        if (!this.animationprocedure.equals("empty")) {
            return PlayState.STOP;
        }
        if (this.isSleeping()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
            if (this.isSprinting()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk2"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
        }
        if (this.isSitIdle()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("sit"));
        }
        return event.setAndContinue(RawAnimation.begin().thenLoop("idle2"));
    }

    private PlayState procedurePredicate(AnimationState<GoldenFoxPetEntity> event) {
        if (!this.animationprocedure.equals("empty")
                && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                this.animationprocedure = "empty";
                event.getController().forceAnimationReset();
            }
        } else if (this.animationprocedure.equals("empty")) {
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
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

    /**
     * Cat-like behavior: when the owner is sleeping on a bed the fox first walks over to the
     * bed and only lies down after getting close, instead of sleeping from far away.
     */
    static class GoldenFoxRelaxOnOwnerGoal extends Goal {
        private final GoldenFoxPetEntity fox;
        private Player ownerPlayer;
        private BlockPos goalPos;
        private int onBedTicks;

        GoldenFoxRelaxOnOwnerGoal(GoldenFoxPetEntity fox) {
            this.fox = fox;
        }

        @Override
        public boolean canUse() {
            if (!this.fox.isTame() || this.fox.isOrderedToSit()) {
                return false;
            }
            if (!(this.fox.getOwner() instanceof Player player)) {
                return false;
            }
            this.ownerPlayer = player;
            if (!player.isSleeping() || this.fox.distanceToSqr(player) > 100.0D) {
                return false;
            }
            BlockPos pos = player.blockPosition();
            BlockState state = this.fox.level().getBlockState(pos);
            if (!state.is(BlockTags.BEDS)) {
                return false;
            }
            this.goalPos = state.getOptionalValue(BedBlock.FACING)
                    .map(facing -> pos.relative(facing.getOpposite()))
                    .orElse(pos);
            return true;
        }

        @Override
        public boolean canContinueToUse() {
            return this.fox.isTame()
                    && !this.fox.isOrderedToSit()
                    && this.ownerPlayer != null
                    && this.ownerPlayer.isSleeping()
                    && this.goalPos != null;
        }

        @Override
        public void start() {
            this.moveToGoal();
        }

        @Override
        public void stop() {
            this.fox.setSleeping(false);
            this.onBedTicks = 0;
            this.fox.getNavigation().stop();
        }

        @Override
        public void tick() {
            if (this.ownerPlayer == null || this.goalPos == null) {
                return;
            }
            this.moveToGoal();
            if (this.fox.distanceToSqr(this.ownerPlayer) < 2.5D) {
                this.onBedTicks++;
                this.fox.getLookControl().setLookAt(this.ownerPlayer, 45.0F, 45.0F);
                if (this.onBedTicks > this.adjustedTickDelay(16)) {
                    this.fox.setSleeping(true);
                }
            } else {
                this.fox.setSleeping(false);
                this.onBedTicks = 0;
            }
        }

        private void moveToGoal() {
            if (this.goalPos != null) {
                this.fox.getNavigation().moveTo(
                        this.goalPos.getX() + 0.5D, this.goalPos.getY(),
                        this.goalPos.getZ() + 0.5D, 1.1D);
            }
        }
    }

    public static void init() {
    }
}
