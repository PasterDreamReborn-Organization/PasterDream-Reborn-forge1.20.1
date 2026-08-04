package com.pasterdream.pasterdreammod.world.entity.ghost;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class SquealWaveProjectileEntity extends AbstractArrow implements ItemSupplier {
    private static final ItemStack PROJECTILE_ITEM = new ItemStack(Items.AIR);

    public SquealWaveProjectileEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.SQUEAL_WAVE_PROJECTILE.get(), world);
    }

    public SquealWaveProjectileEntity(EntityType<? extends SquealWaveProjectileEntity> type, Level world) {
        super(type, world);
    }

    public SquealWaveProjectileEntity(EntityType<? extends SquealWaveProjectileEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public SquealWaveProjectileEntity(EntityType<? extends SquealWaveProjectileEntity> type, LivingEntity entity, Level world) {
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
    public void tick() {
        super.tick();
        this.level().addParticle((SimpleParticleType) ModParticleTypes.SQUEAL_WAVE_PARTICLE.get(),
                this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        if (this.inGround)
            this.discard();
    }

    public static void shoot(Level world, LivingEntity entity, RandomSource source) {
        shoot(world, entity, source, 1.2f, 0.5, 0);
    }

    public static void shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        SquealWaveProjectileEntity arrow = new SquealWaveProjectileEntity(ModEntities.SQUEAL_WAVE_PROJECTILE.get(), entity, world);
        arrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
        arrow.setSilent(true);
        arrow.setCritArrow(false);
        arrow.setBaseDamage(damage);
        arrow.setKnockback(knockback);
        world.addFreshEntity(arrow);
        entity.playSound(ModSounds.SQUEAL_WAVE.get(), 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
    }

    public static void shoot(LivingEntity entity, LivingEntity target) {
        SquealWaveProjectileEntity arrow = new SquealWaveProjectileEntity(ModEntities.SQUEAL_WAVE_PROJECTILE.get(), entity, entity.level());
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        arrow.shoot(dx, dy - arrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1.2f * 2, 12.0F);
        arrow.setSilent(true);
        arrow.setBaseDamage(0.5);
        arrow.setKnockback(0);
        arrow.setCritArrow(false);
        entity.level().addFreshEntity(arrow);
        entity.playSound(ModSounds.SQUEAL_WAVE.get(), 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
    }
}
