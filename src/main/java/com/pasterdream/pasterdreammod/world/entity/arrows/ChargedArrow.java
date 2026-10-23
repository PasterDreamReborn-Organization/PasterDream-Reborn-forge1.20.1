package com.pasterdream.pasterdreammod.world.entity.arrows;

import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;

/** 充能箭 —— 命中后造成两次伤害：先魔法伤害、后原版物理伤害，魔法伤害随蓄力缩放，飞行带龙息粒子拖尾。 */
public class ChargedArrow extends Arrow {

    /** 魔法伤害倍率（相对箭基础伤害） */
    private static final float MAGIC_DAMAGE_MULTIPLIER = 1.5F;

    /** 蓄力系数（0.1~1.0），由射出初速反推，缩放魔法伤害 */
    private double chargeFactor = 1.0;

    public ChargedArrow(EntityType<? extends ChargedArrow> type, Level level) {
        super(type, level);
    }

    public ChargedArrow(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.CHARGED_ARROW.get(), level);
    }

    public ChargedArrow(Level level, LivingEntity shooter) {
        this(ModEntities.CHARGED_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    @Override
    public void shootFromRotation(Entity shooter, float x, float y, float z, float velocity, float inaccuracy) {
        // 满蓄射速为 3.0，蓄力系数 = 射速 / 3.0，夹到 [0.1, 1.0]
        this.chargeFactor = Mth.clamp(velocity / 3.0F, 0.1F, 1.0F);
        super.shootFromRotation(shooter, x, y, z, velocity, inaccuracy);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.spawnDragonBreathTrail();
        }
    }

    /** 龙息粒子拖尾：每 2 tick 在箭位置撒一个龙息粒子（紫色）。 */
    private void spawnDragonBreathTrail() {
        if (this.tickCount % 2 != 0) {
            return;
        }
        Vec3 motion = this.getDeltaMovement();
        this.level().addParticle(ParticleTypes.DRAGON_BREATH,
                this.getX(), this.getY(), this.getZ(),
                motion.x * 0.1, motion.y * 0.1, motion.z * 0.1);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        // 先结算魔法伤害（随蓄力缩放），再走原版物理伤害
        if (!this.level().isClientSide && result.getEntity() instanceof LivingEntity target) {
            float magicDamage = (float) (this.getBaseDamage() * MAGIC_DAMAGE_MULTIPLIER * this.chargeFactor);
            target.hurt(this.damageSources().indirectMagic(this, this.getOwner()), magicDamage);
            target.invulnerableTime = 0;  // 重置无敌帧，让后续物理伤害正常结算
        }
        super.onHitEntity(result);
    }
}
