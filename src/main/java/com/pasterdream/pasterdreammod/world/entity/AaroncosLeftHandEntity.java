package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import com.pasterdream.pasterdreammod.init.ModSounds;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class AaroncosLeftHandEntity extends Monster implements GeoEntity {
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(AaroncosLeftHandEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(AaroncosLeftHandEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private long lastSwing;
    public String animationprocedure = "empty";
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.PINK, ServerBossEvent.BossBarOverlay.PROGRESS);

    private static final TagKey<EntityType<?>> SHADOW_MOB = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("pasterdream", "shadow_mob"));
    private static final TagKey<EntityType<?>> SPECIAL_ENTITY = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("pasterdream", "special_entity_tag"));

    // Skill state machine (replaces Scoreboard)
    private int spawnTick = 0;
    private int switchState = 0;
    private int skillLock = 0;
    private int skillTick = 0;
    private SkillType activeSkill = SkillType.NONE;

    // Cooldowns & counters
    private int sprintCount = 0;
    private int sprintCd = 0;
    private int hitCd = 0;
    private int swordCd = 0;
    private boolean bloodLock = false;

    private enum SkillType { NONE, SPRINT, HIT, SWORD }

    public AaroncosLeftHandEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.AARONCOS_LEFT_HAND.get(), world);
    }

    public AaroncosLeftHandEntity(EntityType<AaroncosLeftHandEntity> type, Level world) {
        super(type, world);
        xpReward = 100;
        setNoAi(false);
        setPersistenceRequired();
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "aaroncos_left_hand");
    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE, texture);
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new FlyingPathNavigation(this, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, true) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(2, new Goal() {
            {
                this.setFlags(EnumSet.of(Goal.Flag.MOVE));
            }
            public boolean canUse() {
                return AaroncosLeftHandEntity.this.getTarget() != null && !AaroncosLeftHandEntity.this.getMoveControl().hasWanted();
            }
            @Override
            public boolean canContinueToUse() {
                return AaroncosLeftHandEntity.this.getMoveControl().hasWanted() && AaroncosLeftHandEntity.this.getTarget() != null && AaroncosLeftHandEntity.this.getTarget().isAlive();
            }
            @Override
            public void start() {
                LivingEntity livingentity = AaroncosLeftHandEntity.this.getTarget();
                Vec3 vec3d = livingentity.getEyePosition(1);
                AaroncosLeftHandEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1);
            }
            @Override
            public void tick() {
                LivingEntity livingentity = AaroncosLeftHandEntity.this.getTarget();
                if (AaroncosLeftHandEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    AaroncosLeftHandEntity.this.doHurtTarget(livingentity);
                } else {
                    double d0 = AaroncosLeftHandEntity.this.distanceToSqr(livingentity);
                    if (d0 < 16) {
                        Vec3 vec3d = livingentity.getEyePosition(1);
                        AaroncosLeftHandEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1);
                    }
                }
            }
        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, false, false));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.8, 20) {
            @Override
            protected Vec3 getPosition() {
                return new Vec3(
                    AaroncosLeftHandEntity.this.getX() + ((random.nextFloat() * 2 - 1) * 16),
                    AaroncosLeftHandEntity.this.getY() + ((random.nextFloat() * 2 - 1) * 16),
                    AaroncosLeftHandEntity.this.getZ() + ((random.nextFloat() * 2 - 1) * 16));
            }
        });
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean causeFallDamage(float l, float d, DamageSource source) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!level().isClientSide() && !hasEffect(ModEffects.SHADOW_SILENCE_BUFF.get())) {
            // Sword skill trigger (hurt-triggered)
            if (skillLock == 0 && switchState == 1 && swordCd == 0 && getHealth() > 1) {
                startSwordSkill();
            }
            // Bossbar update & bloodlock check on hurt
            updateBossbarData();
            checkBloodlock();
        }
        if (source.is(DamageTypes.IN_FIRE))
            return false;
        return super.hurt(source, amount);
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        // Death animation trigger
        if (!level().isClientSide()) {
            setAnimation("death");
            level().playSound(null, BlockPos.containing(getX(), getY(), getZ()),
                net.minecraft.sounds.SoundEvents.WITHER_DEATH,
                SoundSource.NEUTRAL, 1, 1);
            if (level() instanceof ServerLevel _level) {
                _level.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(),
                    getX(), getY(), getZ(), 64, 2, 2, 2, 0.5);
            }
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, net.minecraft.world.DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        spawnTick = 0;
        return retval;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture"))
            this.setTexture(compound.getString("Texture"));
    }

    @Override
    public void baseTick() {
        super.baseTick();

        if (!level().isClientSide()) {
            // Spawn sequence
            runSpawnSequence();

            if (switchState == 1) {
                // Tick cooldowns
                if (sprintCd > 0) sprintCd--;
                if (hitCd > 0) hitCd--;
                if (swordCd > 0) swordCd--;

                // Execute active skill
                if (activeSkill != SkillType.NONE) {
                    skillTick++;
                    runActiveSkill();
                } else if (skillLock == 0 && !hasEffect(ModEffects.SHADOW_SILENCE_BUFF.get())) {
                    // Try to start skills
                    if (sprintCount >= 3 && hitCd == 0) {
                        startHitSkill();
                    } else if (sprintCd == 0 && sprintCount < 3) {
                        startSprintSkill();
                    }
                }

                // Bossbar update (left hand updates from baseTick too)
                updateBossbarData();
                checkBloodlock();
            }
        }

        this.refreshDimensions();
    }

    // ============ Spawn Sequence ============

    private void runSpawnSequence() {
        if (spawnTick == 0) {
            setAnimation("spawn");
            addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 4, false, false));
            addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 4, false, false));
            addEffect(new MobEffectInstance(MobEffects.HEAL, 100, 1, false, false));
            setDeltaMovement(new Vec3(0, -2, 0));
            setHealth(1);
            playSoundAt(ModSounds.AARONCOS_SPAWN.get(), 1, 1);
            if (level() instanceof ServerLevel sl)
                sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), getX(), getY(), getZ(), 64, 1, 1, 1, 0.2);
        }
        if (spawnTick == 30) {
            if (level() instanceof ServerLevel sl)
                sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), getX(), getY(), getZ(), 64, 1, 1, 1, 0.2);
        }
        if (spawnTick == 70) {
            safeExplode(getX(), getY() + 2, getZ(), 3, 10, 64);
        }
        if (spawnTick == 75) {
            setDeltaMovement(new Vec3(0, -0.4, 0));
        }
        if (spawnTick == 100) {
            switchState = 1;
        }
        spawnTick++;
    }

    // ============ Skills ============

    private void startSprintSkill() {
        skillLock = 1;
        activeSkill = SkillType.SPRINT;
        skillTick = 0;
        sprintCd = 120;
        setAnimation("skill_sprint");
    }

    private void startHitSkill() {
        skillLock = 1;
        activeSkill = SkillType.HIT;
        skillTick = 0;
        hitCd = 100;
        sprintCount = 0;
        setAnimation("skill_hit");
    }

    private void startSwordSkill() {
        skillLock = 1;
        activeSkill = SkillType.SWORD;
        skillTick = 0;
        swordCd = 420;
        setAnimation("skill_sword");
        addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 120, 1, false, false));
        setDeltaMovement(new Vec3(0, -2, 0));
        playSoundAt(ModSounds.STONE_BREAK.get(), 1, 1);

        // 30-block confusion to non-friendly targets
        Vec3 center = new Vec3(getX(), getY(), getZ());
        List<Entity> entities = level().getEntitiesOfClass(Entity.class,
            new AABB(center, center).inflate(15), e -> true);
        for (Entity target : entities) {
            if (!target.getType().is(SPECIAL_ENTITY) && !target.getType().is(SHADOW_MOB)) {
                if (target instanceof LivingEntity le) {
                    le.addEffect(new MobEffectInstance(ModEffects.CONFUSION_BUFF.get(), 60, 1, false, false));
                }
            }
        }
    }

    private void runActiveSkill() {
        switch (activeSkill) {
            case SPRINT -> runSprintSkill();
            case HIT -> runHitSkill();
            case SWORD -> runSwordSkill();
        }
    }

    private void runSprintSkill() {
        if (skillTick == 5) {
            playSoundAt(ModSounds.STONE_BREAK_0.get(), 1, 1);
        }
        if (skillTick == 16) {
            // Look at nearest player and dash
            Player nearest = getNearestPlayer(64);
            if (nearest != null) {
                lookAt(EntityAnchorArgument.Anchor.EYES, nearest.getEyePosition());
            }
            Vec3 look = getLookAngle();
            setDeltaMovement(new Vec3(look.x * 2.8, look.y - 0.2, look.z * 2.8));
        }
        if (skillTick == 17 || skillTick == 24) {
            explodeSound();
            if (level() instanceof ServerLevel sl) {
                sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), getX(), getY(), getZ(), 64, 1, 1, 1, 0.2);
                sl.sendParticles(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 16, 1, 1, 1, 0.2);
            }
            damageAOE(3, 7, DamageTypes.GENERIC);
        }
        if (skillTick == 20) {
            sprintCount++;
        }
        if (skillTick >= 40) {
            endSkill();
        }
    }

    private void runHitSkill() {
        // Three slam waves
        if (skillTick == 10) {
            setDeltaMovement(new Vec3(0, 2, 0));
        }
        if (skillTick == 15) {
            setDeltaMovement(new Vec3(0, -10, 0));
        }
        if (skillTick == 19) {
            doHitSlam(128, 2, 1, 2, 64, ParticleTypes.SMOKE, 12, ParticleTypes.EXPLOSION,
                7.5, 6, 0.5, DamageTypes.GENERIC, 1.0f);
        }
        if (skillTick == 21) {
            setDeltaMovement(new Vec3(0, 3, 0));
        }
        if (skillTick == 27) {
            setDeltaMovement(new Vec3(0, -10, 0));
        }
        if (skillTick == 30) {
            doHitSlam(256, 3, 1, 3, 128, ParticleTypes.SMOKE, 16, ParticleTypes.EXPLOSION,
                9.5, 7, 1.0, DamageTypes.GENERIC, 1.1f);
        }
        if (skillTick == 42) {
            setDeltaMovement(new Vec3(0, 4, 0));
        }
        if (skillTick == 48) {
            setDeltaMovement(new Vec3(0, -10, 0));
        }
        if (skillTick == 53) {
            doHitSlam(512, 4, 1, 4, 192, ParticleTypes.SMOKE, 24, ParticleTypes.EXPLOSION,
                11.5, 8, 1.5, DamageTypes.GENERIC, 1.2f);
        }
        if (skillTick >= 100) {
            endSkill();
        }
    }

    private void doHitSlam(int stoneCount, double sx, double sy, double sz,
                           int smokeCount, net.minecraft.core.particles.ParticleOptions smokeType,
                           int explodeCount, net.minecraft.core.particles.ParticleOptions explodeType,
                           double radius, float damage, double knockup, net.minecraft.resources.ResourceKey<DamageType> damageType, float volume) {
        if (level() instanceof ServerLevel sl) {
            sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), getX(), getY(), getZ(), stoneCount, sx, sy, sz, 0.1);
            sl.sendParticles(smokeType, getX(), getY(), getZ(), smokeCount, sx, sy, sz, 0.5);
            sl.sendParticles(explodeType, getX(), getY(), getZ(), explodeCount, sx / 2, sy, sz / 2, 0.5);
        }
        explodeSoundAtVolume(volume);
        Vec3 center = new Vec3(getX(), getY() - 5, getZ());
        List<Entity> entities = level().getEntitiesOfClass(Entity.class,
            new AABB(center, center).inflate(radius), e -> true);
        for (Entity target : entities) {
            if (!target.getType().is(SPECIAL_ENTITY) && !target.getType().is(SHADOW_MOB)) {
                if (target instanceof LivingEntity le) {
                    le.addEffect(new MobEffectInstance(ModEffects.CONFUSION_BUFF.get(), 10, 1, false, false));
                }
                target.setDeltaMovement(new Vec3(0, knockup, 0));
                target.hurt(new DamageSource(level().registryAccess()
                    .registryOrThrow(Registries.DAMAGE_TYPE)
                    .getHolderOrThrow(damageType)), damage);
            }
        }
    }

    private void runSwordSkill() {
        if (skillTick == 15) {
            addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 4, false, false));
            if (level() instanceof ServerLevel sl)
                sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), getX(), getY(), getZ(), 128, 1, 2, 1, 0.5);
        }
        if (skillTick == 25) {
            if (level() instanceof ServerLevel sl)
                sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), getX(), getY(), getZ(), 128, 1, 2, 1, 0.5);
        }
        // 7 waves of sword strikes at ticks 57, 70, 83, 88, 95, 105, 112
        int[] waveTicks = {57, 70, 83, 88, 95, 105, 112};
        for (int t : waveTicks) {
            if (skillTick == t) {
                executeSwordWave();
            }
        }
        if (skillTick >= 140) {
            endSkill();
        }
    }

    private void executeSwordWave() {
        setDeltaMovement(new Vec3(0, -0.1, 0));
        playSoundAt(ModSounds.SWORD_WAVE.get(), 1.2f, 1);
        explodeSound();
        if (level() instanceof ServerLevel sl) {
            sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), getX(), getY(), getZ(), 128, 5, 3, 5, 1);
            sl.sendParticles(ParticleTypes.SMOKE, getX(), getY(), getZ(), 128, 5, 3, 5, 1);
        }
        Vec3 center = new Vec3(getX(), getY(), getZ());
        List<Entity> entities = level().getEntitiesOfClass(Entity.class,
            new AABB(center, center).inflate(8), e -> true);
        for (Entity target : entities) {
            if (!target.getType().is(SPECIAL_ENTITY) && !target.getType().is(SHADOW_MOB)) {
                target.hurt(new DamageSource(level().registryAccess()
                    .registryOrThrow(Registries.DAMAGE_TYPE)
                    .getHolderOrThrow(DamageTypes.GENERIC)), 8);
                if (target instanceof Player) {
                    if (target instanceof LivingEntity le) {
                        le.addEffect(new MobEffectInstance(ModEffects.CONFUSION_BUFF.get(), 20, 1, false, false));
                    }
                }
            }
        }
    }

    private void endSkill() {
        activeSkill = SkillType.NONE;
        skillTick = 0;
        skillLock = 0;
    }

    // ============ Bloodlock ============

    private void checkBloodlock() {
        if (bloodLock)
            return;
        if (getHealth() <= 100) {
            bloodLock = true;
            addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 3, false, false));
            removeEffect(ModEffects.SHADOW_SILENCE_BUFF.get());

            // Spawn 4 ShadowHands
            if (level() instanceof ServerLevel _level) {
                for (int i = 0; i < 4; i++) {
                    Entity shadowHand = ModEntities.SHADOW_HAND.get().spawn(_level,
                        BlockPos.containing(
                            getX() + (random.nextDouble() * 6 - 3),
                            getY() + (random.nextDouble() * 6 - 3),
                            getZ() + (random.nextDouble() * 6 - 3)),
                        MobSpawnType.MOB_SUMMONED);
                    if (shadowHand != null) {
                        shadowHand.setYRot(random.nextFloat() * 360F);
                    }
                }
            }

            // Debuff players within 80 blocks
            Vec3 center = new Vec3(getX(), getY(), getZ());
            List<Entity> entities = level().getEntitiesOfClass(Entity.class,
                new AABB(center, center).inflate(40), e -> true);
            for (Entity target : entities) {
                if (target instanceof Player) {
                    if (target instanceof LivingEntity le) {
                        le.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 0));
                        le.addEffect(new MobEffectInstance(ModEffects.CONFUSION_BUFF.get(), 60, 1));
                        le.addEffect(new MobEffectInstance(ModEffects.RESTRAINMOVE_BLOCK_BUFF.get(), 60, 0));
                    }
                }
            }
            playSoundAt(ModSounds.AARONCOS_SPAWN.get(), 1, 1);
        }
    }

    // ============ Bossbar data ============

    private void updateBossbarData() {
        Vec3 center = new Vec3(getX(), getY(), getZ());
        List<Player> players = level().getEntitiesOfClass(Player.class,
            new AABB(center, center).inflate(32), e -> true);
        for (Player player : players) {
            player.getPersistentData().putString("aaroncos_left_hand_name", getDisplayName().getString());
            player.getPersistentData().putDouble("aaroncos_left_hand_hp", getHealth());
            player.getPersistentData().putBoolean("aaroncos_left_hand_life", isAlive());
        }
    }

    // ============ Helpers ============

    @Nullable
    private Player getNearestPlayer(double range) {
        Vec3 center = new Vec3(getX(), getY(), getZ());
        return level().getEntitiesOfClass(Player.class,
            new AABB(center, center).inflate(range), e -> true)
            .stream().min(Comparator.comparingDouble(e -> e.distanceToSqr(this)))
            .orElse(null);
    }

    private void damageAOE(double radius, float damage, net.minecraft.resources.ResourceKey<DamageType> damageType) {
        Vec3 center = new Vec3(getX(), getY(), getZ());
        List<Entity> entities = level().getEntitiesOfClass(Entity.class,
            new AABB(center, center).inflate(radius), e -> true);
        for (Entity target : entities) {
            if (!target.getType().is(SPECIAL_ENTITY) && !target.getType().is(SHADOW_MOB)) {
                target.hurt(new DamageSource(level().registryAccess()
                    .registryOrThrow(Registries.DAMAGE_TYPE)
                    .getHolderOrThrow(damageType)), damage);
            }
        }
    }

    private void playSoundAt(net.minecraft.sounds.SoundEvent sound, float volume, float pitch) {
        if (!level().isClientSide()) {
            level().playSound(null, BlockPos.containing(getX(), getY(), getZ()),
                sound, SoundSource.MASTER, volume, pitch);
        } else {
            level().playLocalSound(getX(), getY(), getZ(),
                sound, SoundSource.MASTER, volume, pitch, false);
        }
    }

    private void safeExplode(double cx, double cy, double cz, double radius, float damage, int particleCount) {
        playSoundAt(net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE, 1, 1);
        if (level() instanceof ServerLevel sl) {
            sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), cx, cy, cz, particleCount, radius / 2, radius / 2, radius / 2, 0.3);
            sl.sendParticles(ParticleTypes.EXPLOSION, cx, cy, cz, particleCount / 4, radius / 3, radius / 3, radius / 3, 0.3);
        }
        Vec3 center = new Vec3(cx, cy, cz);
        List<Entity> entities = level().getEntitiesOfClass(Entity.class,
            new AABB(center, center).inflate(radius), e -> true);
        for (Entity target : entities) {
            if (!target.getType().is(SPECIAL_ENTITY) && !target.getType().is(SHADOW_MOB)) {
                target.hurt(new DamageSource(level().registryAccess()
                    .registryOrThrow(Registries.DAMAGE_TYPE)
                    .getHolderOrThrow(DamageTypes.EXPLOSION)), damage);
            }
        }
    }

    private void explodeSound() {
        playSoundAt(net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE, 1, 1);
    }

    private void explodeSoundAtVolume(float volume) {
        playSoundAt(net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE, volume, 1);
    }

    // ============ Standard overrides ============

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(1);
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.setNoGravity(true);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (ANIMATION.equals(key)) {
            this.animationprocedure = this.entityData.get(ANIMATION);
        }
    }

    public void setAnimation(String animation) {
        this.animationprocedure = animation;
        this.entityData.set(ANIMATION, animation);
    }

    public String getSyncedAnimation() {
        return this.entityData.get(ANIMATION);
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MOVEMENT_SPEED, 0.25)
            .add(Attributes.MAX_HEALTH, 500)
            .add(Attributes.ARMOR, 10)
            .add(Attributes.ATTACK_DAMAGE, 20)
            .add(Attributes.FOLLOW_RANGE, 32)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1)
            .add(Attributes.FLYING_SPEED, 0.25);
    }

    // ============ Animation ============

    private PlayState movementPredicate(software.bernie.geckolib.core.animation.AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) && this.onGround()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            }
            if (this.isDeadOrDying()) {
                return event.setAndContinue(RawAnimation.begin().thenPlay("death"));
            }
            if (!this.onGround()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("fly"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.STOP;
    }

    private PlayState attackingPredicate(software.bernie.geckolib.core.animation.AnimationState event) {
        double d1 = this.getX() - this.xOld;
        double d0 = this.getZ() - this.zOld;
        float velocity = (float) Math.sqrt(d1 * d1 + d0 * d0);
        if (getAttackAnim(event.getPartialTick()) > 0f && !this.swinging) {
            this.swinging = true;
            this.lastSwing = level().getGameTime();
        }
        if (this.swinging && this.lastSwing + 7L <= level().getGameTime()) {
            this.swinging = false;
        }
        if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().forceAnimationReset();
            return event.setAndContinue(RawAnimation.begin().thenPlay("attack"));
        }
        return PlayState.CONTINUE;
    }

    private PlayState procedurePredicate(software.bernie.geckolib.core.animation.AnimationState event) {
        if (!animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                this.animationprocedure = "empty";
                event.getController().forceAnimationReset();
            }
        } else if (animationprocedure.equals("empty")) {
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 40) {
            this.remove(RemovalReason.KILLED);
            this.dropExperience();
            safeExplode(getX(), getY(), getZ(), 4, 15, 64);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
        data.add(new AnimationController<>(this, "attacking", 4, this::attackingPredicate));
        data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
