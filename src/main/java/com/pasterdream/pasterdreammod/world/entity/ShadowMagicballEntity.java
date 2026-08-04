package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
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

public class ShadowMagicballEntity extends PathfinderMob implements GeoEntity {
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ShadowMagicballEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ShadowMagicballEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";
    private int lifeTicks = 0;
    private int explodeTick = -1;

    private static final TagKey<EntityType<?>> SHADOW_MOB = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("pasterdream", "shadow_mob"));
    private static final TagKey<EntityType<?>> SPECIAL_ENTITY = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("pasterdream", "special_entity_tag"));

    public ShadowMagicballEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.SHADOW_MAGICBALL.get(), world);
    }

    public ShadowMagicballEntity(EntityType<ShadowMagicballEntity> type, Level world) {
        super(type, world);
        xpReward = 0;
        setNoAi(true);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "shadow_magicball");
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
        if (source.is(DamageTypes.IN_FIRE))
            return false;
        if (source.getDirectEntity() instanceof ThrownPotion || source.getDirectEntity() instanceof AreaEffectCloud)
            return false;
        if (source.is(DamageTypes.FALL) || source.is(DamageTypes.CACTUS) || source.is(DamageTypes.DROWN))
            return false;
        if (source.is(DamageTypes.LIGHTNING_BOLT) || source.is(DamageTypes.FALLING_ANVIL))
            return false;
        if (source.is(DamageTypes.DRAGON_BREATH) || source.is(DamageTypes.WITHER) || source.is(DamageTypes.WITHER_SKULL))
            return false;
        return super.hurt(source, amount);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture"))
            this.setTexture(compound.getString("Texture"));
    }

    @Override
    public void baseTick() {
        super.baseTick();
        lifeTicks++;
        this.refreshDimensions();

        if (level().isClientSide())
            return;

        // Explosion phase
        if (explodeTick >= 0) {
            runExplodePhase();
            return;
        }

        // Flying phase - timeout after 30 ticks
        if (lifeTicks >= 30) {
            explode();
            return;
        }

        // Trail particles
        net.minecraft.server.level.ServerLevel sw = (net.minecraft.server.level.ServerLevel) level();
        sw.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(),
                getX(), getY(), getZ(), 4, 0.2, 0.2, 0.2, 0.1);
        sw.sendParticles(ParticleTypes.SMOKE,
                getX(), getY(), getZ(), 4, 0.2, 0.2, 0.2, 0.1);

        // Collision check
        Vec3 center = new Vec3(getX(), getY(), getZ());
        List<Entity> entities = level().getEntitiesOfClass(Entity.class,
                new AABB(center, center).inflate(1.5), e -> true);
        for (Entity target : entities) {
            if (!target.getType().is(SPECIAL_ENTITY) && !target.getType().is(SHADOW_MOB)) {
                target.hurt(new DamageSource(level().registryAccess()
                        .registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(DamageTypes.MAGIC)), skillDamage(1.33f));
                explode();
                return;
            }
        }
    }

    private void runExplodePhase() {
        net.minecraft.server.level.ServerLevel sw = (net.minecraft.server.level.ServerLevel) level();
        if (explodeTick == 0 || explodeTick == 5 || explodeTick == 10) {
            sw.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), getX(), getY(), getZ(), 64, 3, 1, 3, 0.3);
            sw.sendParticles(ParticleTypes.SMOKE, getX(), getY(), getZ(), 64, 3, 1, 3, 0.3);
        } else if (explodeTick == 15) {
            Vec3 center = new Vec3(getX(), getY(), getZ());
            List<Entity> entities = level().getEntitiesOfClass(Entity.class,
                    new AABB(center, center).inflate(3.5), e -> true);
            for (Entity target : entities) {
                if (!target.getType().is(SPECIAL_ENTITY) && !target.getType().is(SHADOW_MOB)) {
                    target.hurt(new DamageSource(level().registryAccess()
                            .registryOrThrow(Registries.DAMAGE_TYPE)
                            .getHolderOrThrow(DamageTypes.MAGIC)), skillDamage(3.33f));
                }
            }
            sw.sendParticles(ParticleTypes.EXPLOSION, getX(), getY() + 1, getZ(), 8, 1, 1, 1, 0.5);
            this.playSound(net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE, 1, 1);
        } else if (explodeTick == 20) {
            if (isAlive()) discard();
            return;
        }
        explodeTick++;
    }

    private void explode() {
        this.setAnimation("death");
        if (!level().isClientSide())
            explodeTick = 0;
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
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(1);
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
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10)
                .add(Attributes.ATTACK_KNOCKBACK, 10)
                .add(Attributes.FLYING_SPEED, 0.5);
    }

    private PlayState movementPredicate(software.bernie.geckolib.core.animation.AnimationState<ShadowMagicballEntity> event) {
        if (this.animationprocedure.equals("empty")) {
            if (this.isDeadOrDying()) {
                return event.setAndContinue(RawAnimation.begin().thenPlay("death"));
            }
            if (!this.onGround()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("fly"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(software.bernie.geckolib.core.animation.AnimationState<ShadowMagicballEntity> event) {
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

    private float skillDamage(float ratio) {
        return (float) (getAttributeValue(Attributes.ATTACK_DAMAGE) * ratio);
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            this.remove(RemovalReason.KILLED);
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
}
