package com.pasterdream.pasterdreammod.world.entity.ghost;

import com.pasterdream.pasterdreammod.helper.ShadowDifficultyHelper;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.network.PlayMessages;

public class WailingShadowGhostEntity extends ShadowGhostEntity {
    private int summonTimer;
    private boolean summonGate;

    public WailingShadowGhostEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(packet, world);
    }

    public WailingShadowGhostEntity(EntityType<? extends ShadowGhostEntity> type, Level world) {
        super(type, world);
        xpReward = 5;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.setTexture("wailing_shadow_ghost");
    }

    @Override
    protected boolean isRangedVariant() {
        return true;
    }

    @Override
    protected int getRangedAttackInterval() {
        return 5;
    }

    @Override
    protected float getRangedAttackRadius() {
        return 16f;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);
        if (!this.level().isClientSide() && !summonGate && summonTimer <= 0
                && ShadowDifficultyHelper.isSpecialSkillEnabled(
                        ShadowDifficultyHelper.getDifficultyContext(this))
                && canUseSkill()) {
            summonGate = true;
            summonTimer = 44; // Total countdown
            this.playSound(ModSounds.GHOST0.get(), 1f, 1f);
            this.setAnimation("skill");
        }
        return result;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (!this.level().isClientSide() && summonTimer > 0) {
            summonTimer--;
            // t=44: start, t=36 (8 ticks): 1st shadow_ghost
            if (summonTimer == 36) {
                spawnMinion(ModEntities.SHADOW_GHOST.get());
            }
            // t=28 (16 ticks): 2nd shadow_ghost
            if (summonTimer == 28) {
                spawnMinion(ModEntities.SHADOW_GHOST.get());
            }
            // t=20 (24 ticks): shadow_squeal_ghost
            if (summonTimer == 20) {
                spawnMinion(ModEntities.SHADOW_SQUEAL_GHOST.get());
            }
            // t=14 (30 ticks): reset gate
            if (summonTimer == 14) {
                summonGate = false;
            }
        }
    }

    private void spawnMinion(EntityType<?> type) {
        if (this.level() instanceof ServerLevel serverLevel) {
            Entity entity = type.spawn(serverLevel,
                    BlockPos.containing(
                            this.getX() + Mth.nextDouble(RandomSource.create(), -1, 1),
                            this.getY(),
                            this.getZ() + Mth.nextDouble(RandomSource.create(), -1, 1)),
                    MobSpawnType.MOB_SUMMONED);
            if (entity != null) {
                entity.setYRot(this.level().getRandom().nextFloat() * 360F);
            }
        }
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(1f);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SummonTimer", summonTimer);
        compound.putBoolean("SummonGate", summonGate);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("SummonTimer"))
            summonTimer = compound.getInt("SummonTimer");
        if (compound.contains("SummonGate"))
            summonGate = compound.getBoolean("SummonGate");
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.8)
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 6)
                .add(Attributes.FOLLOW_RANGE, 20)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.4)
                .add(Attributes.FLYING_SPEED, 0.8);
    }

    public static void init() {
        SpawnPlacements.register(ModEntities.WAILING_SHADOW_GHOST.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) ->
                        world.getDifficulty() != Difficulty.PEACEFUL
                                && Monster.isDarkEnoughToSpawn(world, pos, random)
                                && Mob.checkMobSpawnRules(entityType, world, reason, pos, random));
    }
}
