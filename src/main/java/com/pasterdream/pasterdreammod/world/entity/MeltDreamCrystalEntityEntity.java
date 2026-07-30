package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoEntity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.common.ForgeMod;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

public class MeltDreamCrystalEntityEntity extends PathfinderMob implements GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(MeltDreamCrystalEntityEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(MeltDreamCrystalEntityEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(MeltDreamCrystalEntityEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";

    public MeltDreamCrystalEntityEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.MELT_DREAM_CRYSTAL_ENTITY.get(), world);
    }

    public MeltDreamCrystalEntityEntity(EntityType<MeltDreamCrystalEntityEntity> type, Level world) {
        super(type, world);
        xpReward = 0;
        setNoAi(true);
        setPersistenceRequired();
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "meltdream_crystal_entity");
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
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        this.spawnAtLocation(new ItemStack(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()));
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.parse("block.amethyst_block.chime"));
    }

    @Override
    public boolean causeFallDamage(float l, float d, DamageSource source) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.IN_FIRE))
            return false;
        if (source.getDirectEntity() instanceof AbstractArrow)
            return false;
        if (source.getDirectEntity() instanceof Player)
            return false;
        if (source.getDirectEntity() instanceof ThrownPotion || source.getDirectEntity() instanceof AreaEffectCloud)
            return false;
        if (source.is(DamageTypes.FALL))
            return false;
        if (source.is(DamageTypes.CACTUS))
            return false;
        if (source.is(DamageTypes.DROWN))
            return false;
        if (source.is(DamageTypes.LIGHTNING_BOLT))
            return false;
        if (source.is(DamageTypes.EXPLOSION))
            return false;
        if (source.is(DamageTypes.TRIDENT))
            return false;
        if (source.is(DamageTypes.FALLING_ANVIL))
            return false;
        if (source.is(DamageTypes.DRAGON_BREATH))
            return false;
        if (source.is(DamageTypes.WITHER))
            return false;
        if (source.is(DamageTypes.WITHER_SKULL))
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
    public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
        ItemStack itemstack = sourceentity.getItemInHand(hand);
        InteractionResult retval = InteractionResult.sidedSuccess(this.level().isClientSide());
        super.mobInteract(sourceentity, hand);
        Level world = this.level();

        // MeltdreamCrystalBlockPr0Procedure logic inlined
        if (!world.isClientSide()) {
            // Play sound
            world.playSound(null, BlockPos.containing(this.getX(), this.getY(), this.getZ()),
                    ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.parse("block.amethyst_block.break")),
                    SoundSource.NEUTRAL, 0.8f, 1.0f);
        } else {
            world.playLocalSound(this.getX(), this.getY(), this.getZ(),
                    ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.parse("block.amethyst_block.break")),
                    SoundSource.NEUTRAL, 0.8f, 1.0f, false);
        }
        // Spawn particles
        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ModParticleTypes.MELTDREAM_CRYSTAL_PARTICLE.get(),
                    this.getX(), this.getY() + 1, this.getZ(), 128, 2, 2, 2, 0.5);
            serverLevel.sendParticles(ModParticleTypes.MELTDREAM_CRYSTAL_PARTICLE.get(),
                    this.getX(), this.getY() + 1, this.getZ(), 16, 0.1, 0.1, 0.1, 0.15);
            // Drop item
            ItemEntity entityToSpawn = new ItemEntity(serverLevel,
                    this.getX(), this.getY() + 0.5, this.getZ(),
                    new ItemStack(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()));
            entityToSpawn.setPickUpDelay(10);
            serverLevel.addFreshEntity(entityToSpawn);
        }
        if (!world.isClientSide())
            this.discard();

        return retval;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        // MeltdreamCrystalBlockPr1Procedure logic inlined
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ModParticleTypes.MELTDREAM_CRYSTAL_PARTICLE.get(),
                    this.getX(), this.getY() + 0.7, this.getZ(), 3, 0.7, 0.7, 0.7, 0.05);
        }
        this.refreshDimensions();
    }

    @Override
    public EntityDimensions getDimensions(Pose p_33597_) {
        return super.getDimensions(p_33597_).scale((float) 1);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader world) {
        return world.isUnobstructed(this);
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public void push(Entity entity) {
    }

    @Override
    public void push(double x, double y, double z) {
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
        this.updateSwingTime();
        this.setNoGravity(true);
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
        builder = builder.add(Attributes.MAX_HEALTH, 1314);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 0);
        builder = builder.add(Attributes.FLYING_SPEED, 0);
        builder = builder.add(ForgeMod.SWIM_SPEED.get(), 0);
        return builder;
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
        if (this.deathTime == 20) {
            this.remove(MeltDreamCrystalEntityEntity.RemovalReason.KILLED);
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
        data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
