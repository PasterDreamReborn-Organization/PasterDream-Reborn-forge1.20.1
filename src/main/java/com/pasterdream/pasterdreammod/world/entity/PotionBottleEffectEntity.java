package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.helper.MagicDamageHelper;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.world.block.windknightaltar.WindKnightAltarProcedure;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import java.util.UUID;

/**
 * 药剂瓶效果承载实体 —— 落地后生成，由自身 tick 驱动多阶段时序
 * （闪电落雷、剧毒多轮毒气波、冰冻脉冲、狱火波），替代全局延迟队列。
 */
public class PotionBottleEffectEntity extends Entity {
    public static final String TYPE_LIGHTNING = "lightning";
    public static final String TYPE_TOXIC = "toxic";
    public static final String TYPE_FROZEN = "frozen";
    public static final String TYPE_INFERNO = "inferno";

    private String effectType = TYPE_LIGHTNING;
    private int ticks = 0;
    private int waveIndex = 0;
    private int waveTick = 0;
    private UUID throwerUUID;
    private transient LivingEntity cachedThrower;

    public PotionBottleEffectEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public PotionBottleEffectEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.POTION_BOTTLE_EFFECT_ENTITY.get(), level);
    }

    public PotionBottleEffectEntity(Level level, double x, double y, double z, String effectType, LivingEntity thrower) {
        this(ModEntities.POTION_BOTTLE_EFFECT_ENTITY.get(), level);
        this.setPos(x, y, z);
        this.effectType = effectType;
        if (thrower != null) {
            this.throwerUUID = thrower.getUUID();
            this.cachedThrower = thrower;
        }
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
        if (tag.contains("EffectType")) effectType = tag.getString("EffectType");
        ticks = tag.getInt("Ticks");
        waveIndex = tag.getInt("WaveIndex");
        waveTick = tag.getInt("WaveTick");
        if (tag.contains("ThrowerUUID")) throwerUUID = UUID.fromString(tag.getString("ThrowerUUID"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putString("EffectType", effectType);
        tag.putInt("Ticks", ticks);
        tag.putInt("WaveIndex", waveIndex);
        tag.putInt("WaveTick", waveTick);
        if (throwerUUID != null) tag.putString("ThrowerUUID", throwerUUID.toString());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        if (this.level().isClientSide) return;
        if (!(this.level() instanceof ServerLevel sl)) return;

        switch (effectType) {
            case TYPE_LIGHTNING -> tickLightning(sl);
            case TYPE_TOXIC -> tickToxic(sl);
            case TYPE_FROZEN -> tickFrozen(sl);
            case TYPE_INFERNO -> tickInferno(sl);
            default -> this.discard();
        }
    }

    // ===== 闪电药剂瓶：电火花分时刷出 + 4 道落雷 + 召唤风骑士祭坛 =====

    private void tickLightning(ServerLevel sl) {
        switch (ticks) {
            case 2, 7, 12, 17, 22, 27, 32, 37, 42, 47 -> sl.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                    getX(), getY(), getZ(), (ticks == 2 || ticks == 7) ? 4 : 3, 2.0, 0.5, 2.0, 0.0);
            case 55, 65, 75, 85 -> spawnLightningBolt(sl);
            case 86 -> WindKnightAltarProcedure.trySummon(sl, position());
            default -> {}
        }
        ticks++;
        if (ticks > 86) this.discard();
    }

    private void spawnLightningBolt(ServerLevel level) {
        double x = getX() + (level.random.nextDouble() - 0.5) * 4;
        double z = getZ() + (level.random.nextDouble() - 0.5) * 4;
        LightningBolt bolt = net.minecraft.world.entity.EntityType.LIGHTNING_BOLT.create(level);
        if (bolt != null) {
            bolt.moveTo(x, getY(), z);
            level.addFreshEntity(bolt);
        }
    }

    // ===== 剧毒药剂瓶：3 轮毒气波，每轮 5 次伤害波 + 纯粒子爆发 =====

    private void tickToxic(ServerLevel sl) {
        switch (waveTick) {
            case 2 -> {
                sl.sendParticles(ModParticleTypes.POISON_GAS_PARTICLE.get(),
                        getX(), getY() + 1, getZ(), 64, 2.0, 1.0, 2.0, 0.01);
                applyToxicDebuff(sl);
            }
            case 12, 32, 52, 72 -> sl.sendParticles(ModParticleTypes.POISON_GAS_PARTICLE.get(),
                    getX(), getY() + 1, getZ(), 32, 2.0, 1.0, 2.0, 0.01);
            case 22, 42, 62, 82 -> {
                sl.sendParticles(ModParticleTypes.POISON_GAS_PARTICLE.get(),
                        getX(), getY() + 1, getZ(), 32, 2.0, 1.0, 2.0, 0.01);
                applyToxicDebuff(sl);
            }
            default -> {}
        }
        waveTick++;
        if (waveTick > 82) {
            waveTick = 0;
            waveIndex++;
            if (waveIndex >= 3) this.discard();
        }
    }

    // ===== 冰冻药剂瓶：5 波脉冲（t=10,20,30,40,50） =====

    private void tickFrozen(ServerLevel sl) {
        double x = getX() + 0.5, y = getY(), z = getZ() + 0.5;
        switch (ticks) {
            case 10, 20, 30, 40, 50 -> {
                sl.sendParticles(ParticleTypes.SNOWFLAKE, x, y + 1.5, z, 48, 2.5, 1.5, 2.5, 0.1);
                sl.sendParticles(ModParticleTypes.SNOWFLAKE_0_PARTICLE.get(), x, y + 2, z, 32, 2.5, 1.5, 2.5, 0.1);
                sl.sendParticles(ModParticleTypes.SNOWFLAKE_1_PARTICLE.get(), x, y + 2, z, 32, 2.5, 1.5, 2.5, 0.1);
                applyFrozenDebuff(sl);
            }
            default -> {}
        }
        ticks++;
        if (ticks > 50) this.discard();
    }

    // ===== 狱火药剂瓶：5 轮伤害波（t=10,30,50,70,90） =====

    private void tickInferno(ServerLevel sl) {
        switch (ticks) {
            case 10, 30, 50, 70, 90 -> {
                sl.sendParticles(ModParticleTypes.INFERNO_PARTICLE.get(),
                        getX(), getY() + 1.5, getZ(), 32, 3.0, 1.0, 3.0, 0.02);
                sl.sendParticles(ParticleTypes.FLAME,
                        getX(), getY() + 0.5, getZ(), 24, 3.0, 0.5, 3.0, 0.03);
                sl.sendParticles(ParticleTypes.LAVA,
                        getX(), getY() + 1.0, getZ(), 16, 2.0, 0.5, 2.0, 0.02);
                applyInfernoPulse(sl);
            }
            default -> {}
        }
        ticks++;
        if (ticks > 90) this.discard();
    }

    // ===== 辅助方法 =====

    private LivingEntity getThrower() {
        if (cachedThrower != null && cachedThrower.isAlive())
            return cachedThrower;
        if (throwerUUID != null && level() instanceof ServerLevel sl) {
            cachedThrower = sl.getEntity(throwerUUID) instanceof LivingEntity le ? le : null;
        }
        return cachedThrower;
    }

    /** 脉冲：对 6x6 范围敌人（投掷者除外）造成魔法伤害+点燃+易伤叠加（最高3级） */
    private void applyInfernoPulse(ServerLevel sl) {
        double r = 3.0;
        Vec3 pos = position();
        LivingEntity thrower = getThrower();
        sl.getEntitiesOfClass(LivingEntity.class,
                new AABB(pos.x - r, pos.y - r, pos.z - r, pos.x + r, pos.y + r, pos.z + r),
                e -> e != thrower)
                .forEach(e -> {
                    float magic = 3.0f;
                    if (thrower instanceof Player pl) {
                        magic *= MagicDamageHelper.getMagicDamageMultiplier(pl);
                    }
                    e.hurt(e.damageSources().magic(), magic);
                    e.setSecondsOnFire(4);
                    // 易伤叠加，每波+1级，最高3级
                    var vuln = ModEffects.VULNERABILITY.get();
                    int currentLv = e.hasEffect(vuln)
                            ? e.getEffect(vuln).getAmplifier() + 1 : 0;
                    int newLv = Math.min(currentLv, 2); // amplifier 2 = 等级3
                    e.addEffect(new MobEffectInstance(
                            vuln, 200, newLv, false, true));
                });
    }

    /** 5 格半径内所有实体（投掷者除外）施加冰冻 */
    private void applyFrozenDebuff(ServerLevel sl) {
        double r = 5.0;
        Vec3 pos = position();
        LivingEntity thrower = getThrower();
        sl.getEntitiesOfClass(LivingEntity.class,
                new AABB(pos.x - r, pos.y - r, pos.z - r, pos.x + r, pos.y + r, pos.z + r),
                e -> e != thrower)
                .forEach(e -> e.addEffect(
                        new MobEffectInstance(
                                ModEffects.FROZEN.get(), 200, 0)));
    }

    /** 7 格半径内所有实体（投掷者除外）施加 剧毒IV + 虚弱 + 减速（10秒） */
    private void applyToxicDebuff(ServerLevel sl) {
        double r = 7.0;
        Vec3 pos = position();
        LivingEntity thrower = getThrower();
        sl.getEntitiesOfClass(LivingEntity.class,
                new AABB(pos.x - r, pos.y - r, pos.z - r, pos.x + r, pos.y + r, pos.z + r),
                e -> e != thrower)
                .forEach(e -> {
                    e.addEffect(new MobEffectInstance(
                            ModEffects.HIGHLY_TOXIC.get(), 200, 3)); // 剧毒 IV
                    e.addEffect(new MobEffectInstance(
                            MobEffects.WEAKNESS, 200, 0));
                    e.addEffect(new MobEffectInstance(
                            MobEffects.MOVEMENT_SLOWDOWN, 200, 0));
                });
    }
}