package com.pasterdream.pasterdreammod.world.entity.arrows;

import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;

/** 毒孢箭 —— 命中后使目标染上剧毒，飞行时带孢子粒子拖尾（同草薙毒刺）。 */
public class ToxicSporeArrow extends Arrow {

    private static final int POISON_DURATION = 100;
    private static final int POISON_AMPLIFIER = 1;

    public ToxicSporeArrow(EntityType<? extends ToxicSporeArrow> type, Level level) {
        super(type, level);
    }

    public ToxicSporeArrow(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.TOXIC_SPORE_ARROW.get(), level);
    }

    public ToxicSporeArrow(Level level, LivingEntity shooter) {
        this(ModEntities.TOXIC_SPORE_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.spawnSporeTrail();
        }
    }

    /** 孢子粒子拖尾：每 2 tick 在箭位置撒一个孢子粒子（同草薙毒刺的 spawnTrail）。 */
    private void spawnSporeTrail() {
        if (this.tickCount % 2 != 0) {
            return;
        }
        Vec3 motion = this.getDeltaMovement();
        this.level().addParticle(ModParticleTypes.SPORE_PARTICLE.get(),
                this.getX(), this.getY(), this.getZ(),
                motion.x * 0.1, motion.y * 0.1, motion.z * 0.1);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        if (this.level().isClientSide) {
            return;
        }
        target.addEffect(new MobEffectInstance(ModEffects.HIGHLY_TOXIC.get(), POISON_DURATION, POISON_AMPLIFIER, false, true));
    }
}
