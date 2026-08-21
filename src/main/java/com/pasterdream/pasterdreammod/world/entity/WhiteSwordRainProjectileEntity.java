package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class WhiteSwordRainProjectileEntity extends Entity {

    private static final TagKey<EntityType<?>> SHADOW_MOB = TagKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath("pasterdream", "shadow_mob"));
    private static final int MAX_LIFE = 30; // 弹射物最大存活时间(tick)
    private static final double HOMING_SPEED = 1.5; // 追踪目标时的速度
    private static final double SHARPNESS_DAMAGE_MULTIPLIER = 0.5; // 锋利附魔每级伤害加成
    private static final double SMITE_DAMAGE_MULTIPLIER = 2.5; // 亡灵杀手每级伤害加成
    private static final double BANE_DAMAGE_MULTIPLIER = 2.5; // 节肢杀手每级伤害加成
    private static final double HITBOX_BASE = 0.3; // 基础碰撞箱扩展
    private static final double HITBOX_SWEEPING_BONUS = 0.3; // 横扫之刃每级碰撞箱加成
    private static final float SHADOW_DAMAGE_BONUS = 0.5f; // 对影魔额外伤害倍率
    private static final float BROOCH_DAMAGE_BONUS = 0.5f; // 白兰花胸针额外伤害倍率

    @Nullable
    private UUID ownerUUID;
    @Nullable
    private UUID targetUUID;
    private float damage;
    private final Set<UUID> reflectedProjectiles = new HashSet<>();

    public WhiteSwordRainProjectileEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public WhiteSwordRainProjectileEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.WHITE_SWORD_RAIN_PROJECTILE.get(), level);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.hasUUID("Owner")) {
            this.ownerUUID = tag.getUUID("Owner");
        }
        if (tag.hasUUID("Target")) {
            this.targetUUID = tag.getUUID("Target");
        }
        this.damage = tag.getFloat("Damage");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if (this.ownerUUID != null) {
            tag.putUUID("Owner", this.ownerUUID);
        }
        if (this.targetUUID != null) {
            tag.putUUID("Target", this.targetUUID);
        }
        tag.putFloat("Damage", this.damage);
    }

    public void setOwner(LivingEntity owner) {
        this.ownerUUID = owner.getUUID();
    }

    public void setTarget(@Nullable LivingEntity target) {
        this.targetUUID = target != null ? target.getUUID() : null;
    }

    @Nullable
    private LivingEntity resolveOwner() {
        if (this.ownerUUID != null && this.level() instanceof net.minecraft.server.level.ServerLevel sl) {
            Entity e = sl.getEntity(this.ownerUUID);
            if (e instanceof LivingEntity le) return le;
        }
        return null;
    }

    @Nullable
    private LivingEntity resolveTarget() {
        if (this.targetUUID != null && this.level() instanceof net.minecraft.server.level.ServerLevel sl) {
            Entity e = sl.getEntity(this.targetUUID);
            if (e instanceof LivingEntity le && le.isAlive()) return le;
        }
        return null;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();

        // Lifespan
        if (!this.level().isClientSide && this.tickCount >= MAX_LIFE) {
            this.discard();
            return;
        }

        // Homing: steer toward target if set
        if (!this.level().isClientSide && this.targetUUID != null) {
            LivingEntity target = resolveTarget();
            if (target != null) {
                Vec3 toTarget = target.getEyePosition().subtract(this.position()).normalize();
                this.setDeltaMovement(toTarget.scale(HOMING_SPEED));
            }
        }

        // Movement
        Vec3 delta = this.getDeltaMovement();
        this.setPos(this.getX() + delta.x, this.getY() + delta.y, this.getZ() + delta.z);

        // Entity collision (server only)
        if (!this.level().isClientSide && this.isAlive()) {
            checkEntityCollision();
            reflectProjectiles();
        }

        // Particles (client only)
        if (this.level().isClientSide) {
            Vec3 motion = this.getDeltaMovement().normalize();
            this.level().addParticle((SimpleParticleType) ModParticleTypes.WHITE_SWORD_SPARK_PARTICLE.get(),
                    this.getX(), this.getY(), this.getZ(),
                    motion.x * 0.2, motion.y * 0.2, motion.z * 0.2);
            this.level().addParticle((SimpleParticleType) ModParticleTypes.DUST_0_PARTICLE.get(),
                    this.getX(), this.getY(), this.getZ(), motion.x * 0.1, motion.y * 0.1, motion.z * 0.1);
        }
    }

    private void checkEntityCollision() {
        CompoundTag projectileData = this.getPersistentData();
        int sweepingEdge = projectileData.getInt("paster_sweeping_edge");
        int knockback = projectileData.getInt("paster_knockback");

        double hitboxSize = HITBOX_BASE + sweepingEdge * HITBOX_SWEEPING_BONUS;
        AABB aabb = this.getBoundingBox().inflate(hitboxSize);
        LivingEntity owner = resolveOwner();
        List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, aabb,
                e -> e.isAlive() && e != owner && !isOwnedBy(e, owner));

        if (targets.isEmpty()) return;
        LivingEntity target = targets.get(0);

        boolean hasBrooch = owner instanceof Player player
                && CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.BROOCH_OF_WHITE_ORCHID.get()).isPresent())
                .orElse(false);

        // ShadowSilence 10s on shadow_mob entities — apply BEFORE damage to suppress on-hurt skills
        if (target.getType().is(SHADOW_MOB)) {
            target.addEffect(new MobEffectInstance(ModEffects.SHADOW_SILENCE.get(), 200, 0));
            target.getPersistentData().putBoolean("pasterdream:white_sword_boosted", true);
        }

        // Enchantment bonuses
        this.damage += projectileData.getInt("paster_sharpness") * SHARPNESS_DAMAGE_MULTIPLIER;
        if (projectileData.getInt("paster_smite") > 0 && target.getMobType() == MobType.UNDEAD) {
            this.damage += projectileData.getInt("paster_smite") * SMITE_DAMAGE_MULTIPLIER;
        }
        if (projectileData.getInt("paster_bane") > 0 && target.getMobType() == MobType.ARTHROPOD) {
            this.damage += projectileData.getInt("paster_bane") * BANE_DAMAGE_MULTIPLIER;
        }

        // Shadow/Brooch damage multiplier
        float rainMultiplier = 1.0f;
        if (target.getType().is(SHADOW_MOB)) {
            rainMultiplier += SHADOW_DAMAGE_BONUS;
        }
        if (hasBrooch) {
            rainMultiplier += BROOCH_DAMAGE_BONUS;
        }
        this.damage *= rainMultiplier;

        // Fire Aspect
        int fireAspect = projectileData.getInt("paster_fire_aspect");
        if (fireAspect > 0) {
            target.setSecondsOnFire(fireAspect * 4);
        }

        // Bind 6s
        target.addEffect(new MobEffectInstance(ModEffects.BIND.get(), 120, 0));

        // Melee damage
        if (owner instanceof Player player) {
            float finalDamage = this.damage * SkillCooldownHelper.getSkillDamageMultiplier(player);
            target.getPersistentData().putBoolean("pasterdream:rain_damage", true);
            target.hurt(this.damageSources().indirectMagic(this, player), finalDamage);
            target.getPersistentData().remove("pasterdream:rain_damage");
            target.invulnerableTime = hasBrooch ? 0 : 9;

            // Knockback
            if (knockback > 0) {
                Vec3 kb = target.position().subtract(this.position()).normalize().scale(knockback * 0.6);
                target.push(kb.x, 0.2, kb.z);
            }
        }

        this.discard();
    }

    private void reflectProjectiles() {
        double radius = 1.5;
        AABB aabb = this.getBoundingBox().inflate(radius);
        LivingEntity owner = resolveOwner();
        List<Projectile> projectiles = this.level().getEntitiesOfClass(Projectile.class, aabb,
                p -> !reflectedProjectiles.contains(p.getUUID()));
        for (Projectile projectile : projectiles) {
            Entity projOwner = projectile.getOwner();
            if (owner != null && projOwner != null && projOwner.getUUID().equals(owner.getUUID())) {
                continue;
            }
            reflectedProjectiles.add(projectile.getUUID());
            projectile.setDeltaMovement(projectile.getDeltaMovement().reverse());
            if (owner != null) {
                projectile.setOwner(owner);
            }
            if (projectile instanceof AbstractArrow arrow) {
                arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
            }
            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.CRIT,
                        projectile.getX(), projectile.getY() + projectile.getBbHeight() / 2, projectile.getZ(),
                        8, 0.3, 0.3, 0.3, 0.1);
            }
        }
    }

    private static boolean isOwnedBy(LivingEntity target, @Nullable LivingEntity owner) {
        if (owner == null) return false;
        if (target instanceof OwnableEntity ownable) {
            return ownable.getOwner() == owner;
        }
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }
}
