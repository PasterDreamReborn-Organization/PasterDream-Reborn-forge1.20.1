package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
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

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class FoxFireEntity extends Entity implements GeoEntity {

    private int lifeTicks = 0;
    @Nullable
    private Player owner;
    @Nullable
    private UUID ownerUUID;

    public FoxFireEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.FOX_FIRE.get(), world);
    }

    public FoxFireEntity(EntityType<FoxFireEntity> type, Level world) {
        super(type, world);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
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
    public void push(Entity entity) {
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public void push(double x, double y, double z) {
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
    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.hasUUID("Owner")) {
            this.ownerUUID = compound.getUUID("Owner");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        if (ownerUUID != null) {
            compound.putUUID("Owner", ownerUUID);
        }
    }

    @Override
    public void tick() {
        super.tick();
        Level level = this.level();
        if (level.isClientSide()) return;

        lifeTicks++;

        // Spawn particles
        if (level instanceof ServerLevel serverLevel) {
            double radius = 6.0;
            double height = 2.0;
            for (int i = 0; i < 8; i++) {
                double angle = level.random.nextDouble() * Math.PI * 2;
                double dist = level.random.nextDouble() * radius;
                double x = this.getX() + Math.cos(angle) * dist;
                double z = this.getZ() + Math.sin(angle) * dist;
                double y = this.getY() + level.random.nextDouble() * height;
                serverLevel.sendParticles(ModParticleTypes.FOX_FIRE_0_PARTICLE.get(),
                        x, y, z, 1, 0.1, 0.1, 0.1, 0.02);
                serverLevel.sendParticles(ModParticleTypes.FOX_FIRE_1_PARTICLE.get(),
                        x, y, z, 1, 0.1, 0.1, 0.1, 0.01);
            }
        }

        // Apply effects to entities in range
        double effectRadius = 6.0;
        Vec3 center = this.position();
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class,
                new AABB(center, center).inflate(effectRadius));

        for (LivingEntity living : targets) {
            if (living instanceof Player) {
                living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 1, false, false, true));
            } else if (!this.isOwnerOrOwnedPet(living)) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 4, false, false, true));
                living.getPersistentData().putBoolean("pasterdream:fox_fire_vulnerable", true);
            }
        }

        if (lifeTicks >= Config.FoxFireLifetimeSeconds * 20) {
            this.playSound(SoundEvents.FIRE_EXTINGUISH, 0.5f, 1.0f);
            this.discard();
        }
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        double r = 6.0;
        return this.getBoundingBox().inflate(r);
    }

    public static void init() {
    }

    public void setOwner(Player player) {
        this.owner = player;
        this.ownerUUID = player.getUUID();
    }

    @Nullable
    public Player resolveOwner() {
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

    private boolean isOwnerOrOwnedPet(LivingEntity living) {
        Player player = this.resolveOwner();
        if (player == null) {
            return false;
        }
        if (living == player) {
            return true;
        }
        return living instanceof TamableAnimal tamable && tamable.isOwnedBy(player);
    }

    // ===== GeoLib =====

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle", 0, this::idleController));
    }

    private PlayState idleController(AnimationState<FoxFireEntity> state) {
        state.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
        return PlayState.CONTINUE;
    }
}
