package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.PlayMessages;

public class GoldenFoxFireballEntity extends AbstractHurtingProjectile {

    public GoldenFoxFireballEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.GOLDEN_FOX_FIREBALL.get(), level);
    }

    public GoldenFoxFireballEntity(EntityType<? extends GoldenFoxFireballEntity> type, Level level) {
        super(type, level);
    }

    public GoldenFoxFireballEntity(Level level, LivingEntity shooter, double dx, double dy, double dz) {
        super(ModEntities.GOLDEN_FOX_FIREBALL.get(), shooter, dx, dy, dz, level);
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.SOUL_FIRE_FLAME;
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        if (entity == this.getOwner()) {
            return false;
        }
        Entity owner = this.getOwner();
        if (owner instanceof LivingEntity livingOwner && entity instanceof TamableAnimal tamable
                && tamable.isOwnedBy(livingOwner)) {
            return false;
        }
        return super.canHitEntity(entity);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.level().isClientSide()) {
            return;
        }

        Entity entity = result.getEntity();
        Entity owner = this.getOwner();
        DamageSource source = owner instanceof LivingEntity livingOwner
                ? this.damageSources().indirectMagic(this, livingOwner)
                : this.damageSources().magic();

        if (entity instanceof LivingEntity target) {
            target.hurt(source, 10.0F);
            target.addEffect(new MobEffectInstance(ModEffects.BIND.get(), 10, 0, false, true), this);
            target.addEffect(new MobEffectInstance(ModEffects.VULNERABILITY.get(), 40, 0, false, true), this);
        }
        this.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 1.0F);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }
}
