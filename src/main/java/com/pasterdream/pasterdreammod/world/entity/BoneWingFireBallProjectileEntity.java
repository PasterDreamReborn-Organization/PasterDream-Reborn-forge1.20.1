package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class BoneWingFireBallProjectileEntity extends AbstractArrow implements ItemSupplier {
    public static final ItemStack PROJECTILE_ITEM = new ItemStack(Items.FIRE_CHARGE);

    public BoneWingFireBallProjectileEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.BONE_WING_FIRE_BALL_PROJECTILE.get(), world);
    }

    public BoneWingFireBallProjectileEntity(EntityType<? extends BoneWingFireBallProjectileEntity> type, Level world) {
        super(type, world);
    }

    public BoneWingFireBallProjectileEntity(EntityType<? extends BoneWingFireBallProjectileEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public BoneWingFireBallProjectileEntity(EntityType<? extends BoneWingFireBallProjectileEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
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
    protected ItemStack getPickupItem() {
        return PROJECTILE_ITEM;
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level().isClientSide()) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.NEUTRAL, 0.6f, 1f);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 5, 0.12, 0.12, 0.12, 0.01);
            level.sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 8, 0.15, 0.15, 0.15, 0.02);
        }
        if (this.inGround)
            this.discard();
    }

    public static BoneWingFireBallProjectileEntity shoot(Level world, LivingEntity entity, RandomSource source) {
        return shoot(world, entity, source, 0.8f, 9, 1);
    }

    public static BoneWingFireBallProjectileEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        BoneWingFireBallProjectileEntity arrow = new BoneWingFireBallProjectileEntity(ModEntities.BONE_WING_FIRE_BALL_PROJECTILE.get(), entity, world);
        arrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
        arrow.setSilent(true);
        arrow.setCritArrow(false);
        arrow.setBaseDamage(damage);
        arrow.setKnockback(knockback);
        arrow.setSecondsOnFire(100);
        world.addFreshEntity(arrow);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSounds.BONE_WING_FIRE_BALL.get(),
                SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return arrow;
    }

    public static BoneWingFireBallProjectileEntity shoot(LivingEntity entity, LivingEntity target) {
        BoneWingFireBallProjectileEntity arrow = new BoneWingFireBallProjectileEntity(ModEntities.BONE_WING_FIRE_BALL_PROJECTILE.get(), entity, entity.level());
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        arrow.shoot(dx, dy - arrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 0.8f * 2, 12.0F);
        arrow.setSilent(true);
        arrow.setBaseDamage(9);
        arrow.setKnockback(1);
        arrow.setCritArrow(false);
        arrow.setSecondsOnFire(100);
        entity.level().addFreshEntity(arrow);
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSounds.BONE_WING_FIRE_BALL.get(),
                SoundSource.PLAYERS, 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
        return arrow;
    }
}
