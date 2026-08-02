package com.pasterdream.pasterdreammod.world.entity;

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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class WhiteSwordRainProjectileEntity extends Entity implements ItemSupplier {

    private static final ItemStack PROJECTILE_ITEM = new ItemStack(ModItems.WHITE_SWORD_RAIN.get());
    private static final TagKey<EntityType<?>> SHADOW_MOB = TagKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath("pasterdream", "shadow_mob"));
    private static final int MAX_LIFE = 25;

    @Nullable
    private UUID ownerUUID;
    private float damage;

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
        this.damage = tag.getFloat("Damage");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if (this.ownerUUID != null) {
            tag.putUUID("Owner", this.ownerUUID);
        }
        tag.putFloat("Damage", this.damage);
    }

    public void setOwner(LivingEntity owner) {
        this.ownerUUID = owner.getUUID();
    }

    @Nullable
    private LivingEntity resolveOwner() {
        if (this.ownerUUID != null && this.level() instanceof net.minecraft.server.level.ServerLevel sl) {
            Entity e = sl.getEntity(this.ownerUUID);
            if (e instanceof LivingEntity le) return le;
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
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return PROJECTILE_ITEM;
    }

    @Override
    public void tick() {
        super.tick();

        // Lifespan
        if (!this.level().isClientSide && this.tickCount >= MAX_LIFE) {
            this.discard();
            return;
        }

        // Movement
        Vec3 delta = this.getDeltaMovement();
        this.setPos(this.getX() + delta.x, this.getY() + delta.y, this.getZ() + delta.z);

        // Entity collision (server only)
        if (!this.level().isClientSide && this.isAlive()) {
            checkEntityCollision();
        }

        // Particles (client only)
        if (this.level().isClientSide) {
            this.level().addParticle(ParticleTypes.END_ROD, this.getX(), this.getY(), this.getZ(), 0, -1, 0);
            this.level().addParticle((SimpleParticleType) ModParticleTypes.DUST_0_PARTICLE.get(),
                    this.getX(), this.getY(), this.getZ(), 0.1, 0, 0.1);
        }
    }

    private void checkEntityCollision() {
        AABB aabb = this.getBoundingBox().inflate(0.3);
        LivingEntity owner = resolveOwner();
        List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, aabb,
                e -> e.isAlive() && e != owner && !isOwnedBy(e, owner));

        if (targets.isEmpty()) return;
        LivingEntity target = targets.get(0);

        // ShadowSilence 10s on shadow_mob entities — apply BEFORE damage to suppress on-hurt skills
        if (target.getType().is(SHADOW_MOB)) {
            target.addEffect(new MobEffectInstance(ModEffects.SHADOW_SILENCE_BUFF.get(), 200, 0));
        }

        // Bind 2s
        target.addEffect(new MobEffectInstance(ModEffects.BIND_BUFF.get(), 40, 0));

        // Melee damage
        if (owner instanceof Player player) {
            target.hurt(this.damageSources().playerAttack(player), this.damage);
        }
        target.invulnerableTime = 0;

        this.discard();
    }

    private static boolean isOwnedBy(LivingEntity target, @Nullable LivingEntity owner) {
        if (owner == null) return false;
        if (target instanceof OwnableEntity ownable) {
            return ownable.getOwner() == owner;
        }
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
