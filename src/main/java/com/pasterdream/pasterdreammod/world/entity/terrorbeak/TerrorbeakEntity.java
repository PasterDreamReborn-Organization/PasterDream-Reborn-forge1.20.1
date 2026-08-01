package com.pasterdream.pasterdreammod.world.entity.terrorbeak;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.helper.ShadowDifficultyHelper;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import com.pasterdream.pasterdreammod.world.entity.ghost.ITextureVariant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
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

public class TerrorbeakEntity extends Monster implements GeoEntity, ITextureVariant {

    public enum Variant {
        NORMAL("terrorbeak", 40, 16, 0.3, 1.8, 12,
                true, false, 9, -2, 7, -0.02, true),
        CRAZY("crazy_terrorbeak", 60, 20, 0.31, 1.8, 10,
                true, true, 10, -3, 30, -0.2, false),
        WEAKENESS("weakness_terrorbeak", 30, 12, 0.3, 1.6, 4,
                false, false, 0, 0, 7, -0.02, true);

        final String texture;
        final double maxHealth, attackDamage, movementSpeed, meleeSpeed;
        final int xpReward;
        final boolean hasRoar, roarOnlyNonImmune;
        final int roarRange, roarSanPenalty, dieSanReward;
        final double touchSanPenalty;
        final boolean touchSanGt0;

        Variant(String texture, double maxHealth, double attackDamage, double movementSpeed,
                double meleeSpeed, int xpReward, boolean hasRoar, boolean roarOnlyNonImmune,
                int roarRange, int roarSanPenalty, int dieSanReward,
                double touchSanPenalty, boolean touchSanGt0) {
            this.texture = texture;
            this.maxHealth = maxHealth;
            this.attackDamage = attackDamage;
            this.movementSpeed = movementSpeed;
            this.meleeSpeed = meleeSpeed;
            this.xpReward = xpReward;
            this.hasRoar = hasRoar;
            this.roarOnlyNonImmune = roarOnlyNonImmune;
            this.roarRange = roarRange;
            this.roarSanPenalty = roarSanPenalty;
            this.dieSanReward = dieSanReward;
            this.touchSanPenalty = touchSanPenalty;
            this.touchSanGt0 = touchSanGt0;
        }
    }

    private static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(TerrorbeakEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(TerrorbeakEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(TerrorbeakEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private long lastSwing;
    public String animationprocedure = "empty";
    private int roarCooldown;
    private Variant variant; // lazily resolved — must not be accessed directly during super() chain

    public TerrorbeakEntity(PlayMessages.SpawnEntity packet, Level world) {
        super((EntityType<? extends TerrorbeakEntity>)
                net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.byId(packet.getTypeId()), world);
        xpReward = getVariant().xpReward;
        setNoAi(false);
    }

    public TerrorbeakEntity(EntityType<? extends TerrorbeakEntity> type, Level world) {
        super(type, world);
        xpReward = getVariant().xpReward;
        setNoAi(false);
    }

    /** Safe to call from super() chain — resolves from EntityType without relying on the variant field. */
    private Variant getVariant() {
        if (variant == null) {
            EntityType<?> type = this.getType();
            if (type == ModEntities.CRAZY_TERRORBEAK.get()) variant = Variant.CRAZY;
            else if (type == ModEntities.WEAKENESS_TERRORBEAK.get()) variant = Variant.WEAKENESS;
            else variant = Variant.NORMAL;
        }
        return variant;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, getVariant().texture);
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
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, getVariant().meleeSpeed, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
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
    public boolean hurt(DamageSource source, float amount) {
        if (!this.level().isClientSide() && this.isAlive() && getVariant().hasRoar && roarCooldown <= 0
                && ShadowDifficultyHelper.getDifficulty(this.level()) > 0) {
            boolean shouldRoar = true;
            if (getVariant().roarOnlyNonImmune) {
                shouldRoar = !source.is(DamageTypes.IN_FIRE) && !source.is(DamageTypes.CACTUS)
                        && !source.is(DamageTypes.WITHER) && !source.is(DamageTypes.WITHER_SKULL);
            }
            if (shouldRoar) {
                this.playSound(ModSounds.TERRORBEAK_ROAR.get(), variant == Variant.CRAZY ? 0.7f : 0.6f, 1);
                Vec3 look = this.getLookAngle();
                Vec3 center = this.position().add(look.x, 0, look.z);
                AABB area = AABB.ofSize(center, getVariant().roarRange, getVariant().roarRange, getVariant().roarRange);
                List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, area,
                        e -> e != this && !(e instanceof TerrorbeakEntity)
                                && !e.getType().is(ModEntityTypeTags.SHADOW_MOB));
                for (LivingEntity target : entities) {
                    target.addEffect(new MobEffectInstance(ModEffects.CONFUSION_BUFF.get(), 30, 1));
                    target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 30, 0));
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 1));
                }
                for (ServerPlayer sp : this.level().getEntitiesOfClass(ServerPlayer.class, area, e -> true)) {
                    sp.getCapability(ModCapabilities.SAN).ifPresent(cap -> cap.addSanValue(getVariant().roarSanPenalty));
                }
                roarCooldown = 200;
            }
        }
        if (source.is(DamageTypes.IN_FIRE))
            return false;
        if (source.is(DamageTypes.CACTUS))
            return false;
        if (source.is(DamageTypes.WITHER))
            return false;
        if (source.is(DamageTypes.WITHER_SKULL))
            return false;
        return super.hurt(source, amount);
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (source.getEntity() instanceof ServerPlayer sp) {
            sp.getCapability(ModCapabilities.SAN).ifPresent(cap -> {
                if (cap.getSanValue() <= 20) {
                    cap.addSanValue(getVariant().dieSanReward);
                }
            });
        }
    }

    @Override
    public void playerTouch(Player player) {
        super.playerTouch(player);
        if (player instanceof ServerPlayer sp) {
            sp.getCapability(ModCapabilities.SAN).ifPresent(cap -> {
                if (getVariant().touchSanGt0) {
                    if (cap.getSanValue() > 0) {
                        cap.addSanValue(getVariant().touchSanPenalty);
                    }
                } else {
                    if (cap.getSanValue() <= 20) {
                        cap.addSanValue(getVariant().touchSanPenalty);
                    }
                }
            });
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
        compound.putInt("RoarCooldown", roarCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture"))
            this.setTexture(compound.getString("Texture"));
        if (compound.contains("RoarCooldown"))
            roarCooldown = compound.getInt("RoarCooldown");
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (roarCooldown > 0)
            roarCooldown--;
        this.refreshDimensions();
        if (!this.level().isClientSide() && this.level().canSeeSky(this.blockPosition()) && this.level().isDay()) {
            this.kill();
        }
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(1f);
    }

    public static void init() {
        // Spawn placements deferred
    }

    public static AttributeSupplier.Builder createTerrorbeakAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, Variant.NORMAL.movementSpeed)
                .add(Attributes.MAX_HEALTH, Variant.NORMAL.maxHealth)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, Variant.NORMAL.attackDamage)
                .add(Attributes.FOLLOW_RANGE, 24)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1);
    }

    public static AttributeSupplier.Builder createCrazyTerrorbeakAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, Variant.CRAZY.movementSpeed)
                .add(Attributes.MAX_HEALTH, Variant.CRAZY.maxHealth)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, Variant.CRAZY.attackDamage)
                .add(Attributes.FOLLOW_RANGE, 24)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1);
    }

    public static AttributeSupplier.Builder createWeakenessTerrorbeakAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, Variant.WEAKENESS.movementSpeed)
                .add(Attributes.MAX_HEALTH, Variant.WEAKENESS.maxHealth)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, Variant.WEAKENESS.attackDamage)
                .add(Attributes.FOLLOW_RANGE, 24)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1);
    }

    // ===== GeckoLib =====

    private PlayState movementPredicate(software.bernie.geckolib.core.animation.AnimationState<TerrorbeakEntity> event) {
        if (this.animationprocedure.equals("empty")) {
            if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            }
            if (this.isDeadOrDying()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.STOP;
    }

    private PlayState attackingPredicate(software.bernie.geckolib.core.animation.AnimationState<TerrorbeakEntity> event) {
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

    private PlayState procedurePredicate(software.bernie.geckolib.core.animation.AnimationState<TerrorbeakEntity> event) {
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
