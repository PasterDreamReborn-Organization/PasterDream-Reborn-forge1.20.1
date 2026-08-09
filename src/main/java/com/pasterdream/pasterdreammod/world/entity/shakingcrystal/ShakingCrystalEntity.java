package com.pasterdream.pasterdreammod.world.entity.shakingcrystal;

import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
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

public class ShakingCrystalEntity extends PathfinderMob implements GeoEntity {

    private static final EntityDataAccessor<String> TEXTURE =
            SynchedEntityData.defineId(ShakingCrystalEntity.class, EntityDataSerializers.STRING);
    private static final int LIFETIME_TICKS = 60; // 最大存活时间(tick)
    private static final int WAVE1_TICK = 1; // 第一波伤害触发tick(立即)
    private static final int WAVE2_TICK = 30; // 第二波伤害触发tick(1.5s后)
    private static final double DAMAGE_RADIUS = 5.0; // 伤害半径
    private static final double CONFUSION_RADIUS = 4.5; // 混乱效果半径
    private static final double WAVE1_DAMAGE_RATIO = 0.4; // 第一波伤害比例
    private static final double WAVE2_DAMAGE_RATIO = 0.6; // 第二波伤害比例
    private static final double EXPLOSION_DAMAGE_RATIO = 1.0; // 爆炸伤害比例
    private static final float SMITE_BANE_MULTIPLIER = 2.5f; // 亡灵杀手/节肢杀手每级伤害加成
    private static final int FIRE_ASPECT_TICK_MULTIPLIER = 4; // 火焰附加tick倍数
    private static final int ABSORPTION_DURATION = 200; // 爆炸后吸收buff持续时间(tick)
    private static final int PARTICLE_INTERVAL = 10; // 常驻粒子间隔(tick)

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int lifeTicks = 0;
    private int spawnDelay = 0;
    private boolean spawnAnimStarted = false;
    @Nullable private UUID ownerUUID;
    private float attackDamage;
    private int sharpness;
    private int smite;
    private int baneOfArthropods;
    private int fireAspect;

    public ShakingCrystalEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.SHAKING_CRYSTAL.get(), world);
    }

    public ShakingCrystalEntity(EntityType<ShakingCrystalEntity> type, Level world) {
        super(type, world);
        xpReward = 0;
        setNoAi(true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEXTURE, "shaking_crystal");
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
    }

    public void setOwner(Player player) {
        this.ownerUUID = player.getUUID();
    }

    public void setAttackDamage(float damage) {
        this.attackDamage = damage;
    }

    public void setSpawnDelay(int ticks) {
        this.spawnDelay = ticks;
    }

    public void setEnchantments(ItemStack stack) {
        this.sharpness = stack.getEnchantmentLevel(Enchantments.SHARPNESS);
        this.smite = stack.getEnchantmentLevel(Enchantments.SMITE);
        this.baneOfArthropods = stack.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS);
        this.fireAspect = stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT);
    }

    @Nullable
    private Player resolveOwner() {
        if (ownerUUID != null && this.level() instanceof ServerLevel serverLevel) {
            Entity entity = serverLevel.getEntity(ownerUUID);
            if (entity instanceof Player player) {
                return player;
            }
        }
        return null;
    }

    private boolean isOwnerOrPet(Entity entity, Player owner) {
        if (entity == owner) return true;
        if (entity instanceof TamableAnimal tamable && owner.getUUID().equals(tamable.getOwnerUUID()))
            return true;
        return false;
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
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture"))
            this.entityData.set(TEXTURE, compound.getString("Texture"));
        if (compound.hasUUID("Owner"))
            this.ownerUUID = compound.getUUID("Owner");
        this.attackDamage = compound.getFloat("AttackDamage");
        this.spawnDelay = compound.getInt("SpawnDelay");
        this.sharpness = compound.getInt("Sharpness");
        this.smite = compound.getInt("Smite");
        this.baneOfArthropods = compound.getInt("BaneOfArthropods");
        this.fireAspect = compound.getInt("FireAspect");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.entityData.get(TEXTURE));
        if (ownerUUID != null)
            compound.putUUID("Owner", ownerUUID);
        compound.putFloat("AttackDamage", attackDamage);
        compound.putInt("SpawnDelay", spawnDelay);
        compound.putInt("Sharpness", sharpness);
        compound.putInt("Smite", smite);
        compound.putInt("BaneOfArthropods", baneOfArthropods);
        compound.putInt("FireAspect", fireAspect);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) return;

        if (spawnDelay > 0) {
            spawnDelay--;
            setInvisible(true);
            return;
        }
        setInvisible(false);

        lifeTicks++;

        Player owner = resolveOwner();

        // Spawn particles
        if (level() instanceof ServerLevel sl) {
            if (lifeTicks == 1) {
                for (int i = 0; i < 7; i++)
                    sl.sendParticles(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 1, 0.7, 0.5, 0.7, 0.01);
            }
            if (lifeTicks == 7) {
                sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(),
                        getX(), getY(), getZ(), 64, 1, 0.2, 1, 0.02);
                sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(),
                        getX(), getY(), getZ(), 16, 0.5, 0.2, 0.5, 0.02);
                sl.sendParticles(ParticleTypes.SMOKE, getX(), getY(), getZ(), 64, 1, 0.5, 1, 0.01);
            }
            // Persistent particles every few ticks
            if (lifeTicks % PARTICLE_INTERVAL == 0 && lifeTicks > 0) {
                sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(),
                        getX(), getY() + 1, getZ(), 8, 0.5, 0.5, 0.5, 0.01);
            }
        }

        // Apply confusion aura every tick
        if (owner != null) {
            applyConfusion(owner);
        }

        // Damage wave 1 at 0.5s
        if (lifeTicks == WAVE1_TICK) {
            pulseDamage(owner, attackDamage * (float) WAVE1_DAMAGE_RATIO, false);
        }
        // Damage wave 2 at 1.5s
        if (lifeTicks == WAVE2_TICK) {
            pulseDamage(owner, attackDamage * (float) WAVE2_DAMAGE_RATIO, false);
        }

        // Lifetime expired — explode (wave 3)
        if (lifeTicks >= LIFETIME_TICKS) {
            explode(owner);
            this.discard();
        }
    }

    private void applyConfusion(Player owner) {
        Vec3 center = position();
        List<Entity> nearby = level().getEntitiesOfClass(Entity.class,
                new AABB(center, center).inflate(CONFUSION_RADIUS));
        for (Entity entity : nearby) {
            if (entity instanceof ShakingCrystalEntity) continue;
            if (entity.getType().is(ModEntityTypeTags.SPECIAL_ENTITY)) continue;
            if (isOwnerOrPet(entity, owner)) continue;
            if (entity instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(ModEffects.CONFUSION_BUFF.get(), 30, 1));
            }
            entity.setTicksFrozen(entity.getTicksRequiredToFreeze() * 2);
        }
    }

    private void pulseDamage(@Nullable Player owner, float damage, boolean isExplosion) {
        if (level() instanceof ServerLevel sl) {
            double scale = isExplosion ? 1.5 : 0.6;
            sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(),
                    getX(), getY(), getZ(), isExplosion ? 64 : 24, scale, scale, scale, 0.05);
            sl.sendParticles(ParticleTypes.EXPLOSION,
                    getX(), getY() + 0.5, getZ(), isExplosion ? 12 : 6, 0.5, 0.3, 0.5, 0.01);
            sl.sendParticles(ParticleTypes.SMOKE,
                    getX(), getY(), getZ(), isExplosion ? 32 : 12, scale * 0.7, 0.5, scale * 0.7, 0.02);
        }
        this.playSound(SoundEvents.GENERIC_EXPLODE, isExplosion ? 0.8f : 0.4f, isExplosion ? 0.6f : 0.8f);

        Vec3 center = position();
        List<Entity> targets = level().getEntitiesOfClass(Entity.class,
                new AABB(center, center).inflate(DAMAGE_RADIUS));
        for (Entity entity : targets) {
            if (entity instanceof ShakingCrystalEntity) continue;
            if (entity.getType().is(ModEntityTypeTags.SPECIAL_ENTITY)) continue;
            if (owner != null && isOwnerOrPet(entity, owner)) continue;

            float finalDamage = damage;
            if (entity instanceof LivingEntity living) {
                MobType mobType = living.getMobType();
                if (smite > 0 && mobType == MobType.UNDEAD)
                    finalDamage += smite * SMITE_BANE_MULTIPLIER;
                if (baneOfArthropods > 0 && mobType == MobType.ARTHROPOD)
                    finalDamage += baneOfArthropods * SMITE_BANE_MULTIPLIER;
                if (fireAspect > 0)
                    living.setSecondsOnFire(fireAspect * FIRE_ASPECT_TICK_MULTIPLIER);
            }

            entity.invulnerableTime = 0;
            entity.hurt(level().damageSources().playerAttack(owner != null ? owner : null), finalDamage);
        }
    }

    private void explode(@Nullable Player owner) {
        if (level() instanceof ServerLevel sl) {
            sl.sendParticles(ParticleTypes.EXPLOSION_EMITTER, getX(), getY(), getZ(), 1, 0, 0, 0, 0);
        }
        // Damage + visual + sound handled by pulseDamage(isExplosion=true)
        pulseDamage(owner, attackDamage * (float) EXPLOSION_DAMAGE_RATIO, true);

        if (owner != null && owner.isAlive()) {
            owner.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, ABSORPTION_DURATION, 0));
        }
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(1f);
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.KNOCKBACK_RESISTANCE, 100);
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 1) {
            this.remove(RemovalReason.KILLED);
            this.dropExperience();
        }
    }

    // ===== GeoLib =====

    private PlayState movementPredicate(AnimationState<ShakingCrystalEntity> event) {
        if (!spawnAnimStarted) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("spawn"));
            spawnAnimStarted = true;
            return PlayState.CONTINUE;
        }
        if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
