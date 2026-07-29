package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TerraswordWaveEntity extends PathfinderMob {

    private int lifeTicks = 0;
    private static final int MAX_LIFE_TICKS = 25;
    private final Set<UUID> hitEntities = new HashSet<>();
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
    protected void pushEntities() {
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.hasUUID("Owner")) {
            this.ownerUUID = compound.getUUID("Owner");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (ownerUUID != null) {
            compound.putUUID("Owner", ownerUUID);
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();
        Level level = this.level();
        if (!level.isClientSide()) {
            lifeTicks++;

            CompoundTag data = this.getPersistentData();
            int sweepingEdge = data.getInt("sweeping_edge");

            int particleCount = 3 + sweepingEdge * 2;
            double particleSpread = 0.2 + sweepingEdge * 0.3;
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ModParticleTypes.SPORE_PARTICLE.get(),
                        this.getX(), this.getY(), this.getZ(),
                        particleCount, particleSpread, particleSpread, particleSpread, 0.1);
                serverLevel.sendParticles(ModParticleTypes.TERRASWORD_WAVE_PARTICLE.get(),
                        this.getX(), this.getY(), this.getZ(),
                        particleCount, particleSpread, particleSpread * 2, particleSpread, 0.1);
            }

            double pasterAtk = data.getDouble("paster_atk");
            int smite = data.getInt("smite");
            int bane = data.getInt("bane_of_arthropods");
            int fireAspect = data.getInt("fire_aspect");
            int knockback = data.getInt("knockback");

            double radius = 2.5 / 2d + sweepingEdge * 0.5;
            Vec3 center = new Vec3(this.getX(), this.getY(), this.getZ());
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                    new AABB(center, center).inflate(radius), e -> true)
                    .stream().sorted(Comparator.comparingDouble(e -> e.distanceToSqr(center))).toList();
            Player owner = resolveOwner();
            for (LivingEntity target : entities) {
                if (target != owner && !(target instanceof TerraswordWaveEntity)
                        && !hitEntities.contains(target.getUUID())) {
                    hitEntities.add(target.getUUID());
                    float damage = (float) (2 + pasterAtk);
                    if (smite > 0 && target.getMobType() == MobType.UNDEAD) {
                        damage += smite * 2.5f;
                    }
                    if (bane > 0 && target.getMobType() == MobType.ARTHROPOD) {
                        damage += bane * 2.5f;
                    }
                    if (data.getBoolean("ignore_iframe")) {
                        target.invulnerableTime = 0;
                    }
                    if (owner != null) {
                        target.hurt(this.damageSources().playerAttack(owner), damage);
                    } else {
                        target.hurt(this.damageSources().magic(), damage);
                    }
                    if (fireAspect > 0) {
                        target.setSecondsOnFire(fireAspect * 4);
                    }
                    if (knockback > 0) {
                        Vec3 kb = target.position().subtract(center).normalize().scale(knockback * 0.6);
                        target.push(kb.x, 0.2, kb.z);
                    }
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK,
                                target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(),
                                3, 0.3, 0.3, 0.3, 0.1);
                    }
                }
            }

            if (lifeTicks >= MAX_LIFE_TICKS) {
                if (!hitEntities.isEmpty()) {
                    level.playSound(null, BlockPos.containing(this.getX(), this.getY(), this.getZ()),
                            SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.NEUTRAL, 0.7f, 1.0f);
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
        this.yBodyRot = this.getYRot();
        this.yHeadRot = this.getYRot();
    }

    @Override
    public void travel(Vec3 input) {
        this.move(MoverType.SELF, this.getDeltaMovement());
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
