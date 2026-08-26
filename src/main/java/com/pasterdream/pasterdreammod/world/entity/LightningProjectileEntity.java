package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

public class LightningProjectileEntity extends AbstractArrow {

    public LightningProjectileEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.LIGHTNING_PROJECTILE.get(), world);
    }

    public LightningProjectileEntity(EntityType<? extends LightningProjectileEntity> type, Level world) {
        super(type, world);
    }

    /** 命中实体：造成真正的雷电（LIGHTNING_BOLT）伤害，而非箭伤 */
    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        float speed = (float) this.getDeltaMovement().length();
        int damage = Mth.ceil(Mth.clamp(speed * this.getBaseDamage(), 0.0, 2.147483647E9));
        hitResult.getEntity().hurt(this.damageSources().lightningBolt(), damage);
        this.discard();
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.level().addParticle((SimpleParticleType) ModParticleTypes.LIGHTNING_PARTICLE.get(), this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            this.level().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
        if (this.inGround)
            this.discard();
    }
}
