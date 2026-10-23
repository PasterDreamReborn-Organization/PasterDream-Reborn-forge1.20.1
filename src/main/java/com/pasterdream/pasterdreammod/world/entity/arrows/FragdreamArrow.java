package com.pasterdream.pasterdreammod.world.entity.arrows;

import com.pasterdream.pasterdreammod.helper.MinionFilterHelper;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;

/** 碎梦箭 —— 命中后发出紫水晶音效；命中物块或实体时对半径 3 格内实体造成范围伤害，击中物块后消失。 */
public class FragdreamArrow extends Arrow {

    private static final double AOE_RADIUS = 3.0;
    private static final float AOE_DAMAGE_MULTIPLIER = 2.0F;

    /** 蓄力系数（0.1~1.0），由射出初速反推，缩放范围与伤害 */
    private double chargeFactor = 1.0;

    public FragdreamArrow(EntityType<? extends FragdreamArrow> type, Level level) {
        super(type, level);
    }

    public FragdreamArrow(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.FRAGDREAM_ARROW.get(), level);
    }

    public FragdreamArrow(Level level, LivingEntity shooter) {
        this(ModEntities.FRAGDREAM_ARROW.get(), level);
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
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Vec3 hit = result.getLocation();
        this.playImpactSound(hit);
        this.spawnShatterParticles(hit);
        this.applyAreaDamage(hit, result.getEntity());
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        Vec3 hit = result.getLocation();
        this.playImpactSound(hit);
        this.spawnShatterParticles(hit);
        this.applyAreaDamage(hit, null);
        this.discard();
    }

    /** 击中音效：紫水晶碎裂。仅在服务端播放，由服务端广播给附近客户端，避免双响。 */
    private void playImpactSound(Vec3 pos) {
        if (this.level().isClientSide) {
            return;
        }
        this.level().playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.BLOCKS, 0.8F, 1.0F);
    }

    /** 染梦水晶块碎裂粒子：以染梦水晶块破碎粒子模拟整块水晶崩裂。 */
    private void spawnShatterParticles(Vec3 center) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        BlockState state = ModBlocks.DYEDREAM_BUD_BRICKS.get().defaultBlockState();
        BlockParticleOption option = new BlockParticleOption(ParticleTypes.BLOCK, state);
        serverLevel.sendParticles(option, center.x, center.y, center.z, 48, 0.4, 0.4, 0.4, 0.4);
    }

    /** 以箭落点为圆心，对半径内所有生物造成伤害；跳过射击者、其仆从/召唤物与被直接命中的目标。 */
    private void applyAreaDamage(Vec3 center, Entity directTarget) {
        if (this.level().isClientSide) {
            return;
        }
        Entity owner = this.getOwner();
        Player ownerPlayer = owner instanceof Player player ? player : null;
        double radius = AOE_RADIUS * this.chargeFactor;
        AABB area = new AABB(
                center.x - radius, center.y - radius, center.z - radius,
                center.x + radius, center.y + radius, center.z + radius);
        DamageSource source = this.damageSources().arrow(this, owner);
        float damage = (float) (this.getBaseDamage() * AOE_DAMAGE_MULTIPLIER * this.chargeFactor);
        for (LivingEntity target : this.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (target == owner || target == directTarget
                    || MinionFilterHelper.isOwnedMinion(target, ownerPlayer)) {
                continue;
            }
            target.hurt(source, damage);
        }
    }
}
