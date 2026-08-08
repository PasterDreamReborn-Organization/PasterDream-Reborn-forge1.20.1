package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

/**
 * 回春药剂瓶落地后生成的治疗实体 —— 不可选中、不可推动、无重力，
 * 每 tick 对 5×5 范围内被动生物和玩家恢复 5% 最大生命值，持续 400 tick。
 * （直接继承 Entity，避免 PathfinderMob 的 AttributeSupplier 注册需求）
 */
public class RejuvenationBottleEntity extends Entity {

    private int lifetime = 400;

    public RejuvenationBottleEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public RejuvenationBottleEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.REJUVENATION_BOTTLE_ENTITY.get(), level);
    }

    public RejuvenationBottleEntity(Level level, double x, double y, double z) {
        this(ModEntities.REJUVENATION_BOTTLE_ENTITY.get(), level);
        this.setPos(x, y, z);
    }

    @Override
    public boolean isPushable() { return false; }

    @Override
    public boolean isPickable() { return false; }

    @Override
    public boolean hurt(net.minecraft.world.damagesource.DamageSource s, float a) { return false; }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("RejuvLifetime")) lifetime = tag.getInt("RejuvLifetime");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("RejuvLifetime", lifetime);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        if (this.level().isClientSide) return;
        if (--lifetime <= 0) {
            this.discard();
            return;
        }
        ServerLevel sl = (ServerLevel) this.level();
        double x = this.getX(), y = this.getY(), z = this.getZ();

        // 每 tick 刷粒子
        sl.sendParticles(ModParticleTypes.REJUVENATION_PARTICLE.get(),
                x, y - 0.5, z, 3, 1.7, 0.5, 1.7, 0.05);
        sl.sendParticles(ModParticleTypes.YELLOW_SMOKE_PARTICLE.get(),
                x, y + 0.1, z, 2, 1.7, 0.5, 1.7, 0.05);

        // 5×5 范围治疗：玩家 + 被动生物
        Vec3 center = new Vec3(x, y, z);
        for (LivingEntity e : this.level().getEntitiesOfClass(LivingEntity.class,
                new AABB(center, center).inflate(2.5),
                e -> e instanceof Player || e instanceof Animal)) {
            e.heal(e.getMaxHealth() * 0.05f);
        }
    }
}
