package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.helper.BossDamageLimiter;
import com.pasterdream.pasterdreammod.helper.ShadowDifficultyHelper;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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

public class AaroncosRightHandEntity extends Monster implements GeoEntity, IShadowMob {
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(AaroncosRightHandEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(AaroncosRightHandEntity.class, EntityDataSerializers.STRING);
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

    // 可调参数
    private static final double CHASE_SPEED = 3.0;               // 追敌速度
    private static final double MAGICBALL_TRIGGER_DIST = 256;    // 魔法球触发距离（平方，16格）

    // Cooldowns & counters
    private int magicballCount = 0;
    private int magicballCd = 0;
    private int vortexCd = 0;
    private int tunetotemCd = 0;
    private boolean bloodLock = false;
    private final BossDamageLimiter damageLimiter;
    private boolean sisterNearby = true;
    private int sisterScanTick = 0;
    private static final java.util.UUID SYNERGY_SPEED_ID = java.util.UUID.fromString("d9f4b3c2-5e6f-7081-9b0a-1d2e3f4a5b6c");

    private enum SkillType { NONE, MAGICBALL, VORTEX, TUNETOTEM }

    public AaroncosRightHandEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.AARONCOS_RIGHT_HAND.get(), world);
    }

    public AaroncosRightHandEntity(EntityType<AaroncosRightHandEntity> type, Level world) {
        super(type, world);
        xpReward = 250;
        setNoAi(false);
        setPersistenceRequired();
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.bossInfo.setVisible(false);
        this.damageLimiter = new BossDamageLimiter(
                (float) Config.bossDamageCap, (float) Config.bossDpsCap, Config.bossRangeCap);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "aaroncos_right_hand");
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
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, true) {
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
                return AaroncosRightHandEntity.this.getTarget() != null && !AaroncosRightHandEntity.this.getMoveControl().hasWanted();
            }
            @Override
            public boolean canContinueToUse() {
                return AaroncosRightHandEntity.this.getMoveControl().hasWanted() && AaroncosRightHandEntity.this.getTarget() != null && AaroncosRightHandEntity.this.getTarget().isAlive();
            }
            @Override
            public void start() {
                LivingEntity livingentity = AaroncosRightHandEntity.this.getTarget();
                Vec3 vec3d = livingentity.getEyePosition(1);
                AaroncosRightHandEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, CHASE_SPEED);
            }
            @Override
            public void tick() {
                LivingEntity livingentity = AaroncosRightHandEntity.this.getTarget();
                if (AaroncosRightHandEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    AaroncosRightHandEntity.this.doHurtTarget(livingentity);
                } else {
                    double d0 = AaroncosRightHandEntity.this.distanceToSqr(livingentity);
                    if (d0 < 16) {
                        Vec3 vec3d = livingentity.getEyePosition(1);
                        AaroncosRightHandEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, CHASE_SPEED);
                    }
                }
            }
        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false,
                target -> !target.getType().is(SHADOW_MOB) && !target.getType().is(SPECIAL_ENTITY)
                        && !(target instanceof Player player && (player.isCreative() || player.isSpectator()))));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.8, 20) {
            @Override
            protected Vec3 getPosition() {
                return new Vec3(
                    AaroncosRightHandEntity.this.getX() + ((random.nextFloat() * 2 - 1) * 16),
                    AaroncosRightHandEntity.this.getY() + ((random.nextFloat() * 2 - 1) * 16),
                    AaroncosRightHandEntity.this.getZ() + ((random.nextFloat() * 2 - 1) * 16));
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
        if (!level().isClientSide() && canUseSkill()) {
            // Tunetotem skill trigger (hurt-triggered, requires target)
            if (skillLock == 0 && switchState == 1 && tunetotemCd == 0 && getHealth() > 1 && getTarget() != null) {
                startTunetotemSkill();
            }
            // Bossbar update & bloodlock check on hurt
            updateBossbarData();
            checkBloodlock();
        }
        if (source.is(DamageTypes.IN_FIRE))
            return false;

        float prevBucket = damageLimiter.getDamageBucket();
        amount = damageLimiter.limit(this, source, amount);
        if (amount < 0) return false;
        boolean result = super.hurt(source, amount);
        if (!result) damageLimiter.rollback(prevBucket);
        return result;
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
        damageLimiter.tick();
        updateSisterSynergy();

        if (!level().isClientSide()) {
            // Spawn sequence
            runSpawnSequence();

            if (switchState == 1) {
                // Tick cooldowns
                if (magicballCd > 0) magicballCd--;
                if (vortexCd > 0) vortexCd--;
                if (tunetotemCd > 0) tunetotemCd--;

                // Execute active skill
                if (activeSkill != SkillType.NONE) {
                    skillTick++;
                    runActiveSkill();
                } else if (skillLock == 0 && canUseSkill() && getTarget() != null) {
                    // Try to start skills
                    if (magicballCount >= 3 && vortexCd == 0) {
                        startVortexSkill();
                    } else if (magicballCd == 0 && magicballCount < 3 && distanceToSqr(getTarget()) <= MAGICBALL_TRIGGER_DIST) {
                        startMagicballSkill();
                    }
                }
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
            setDeltaMovement(new Vec3(0, -2, 0));
            setHealth(getMaxHealth());
            playSoundAt(ModSounds.AARONCOS_SPAWN.get(), 1, 1);
            if (level() instanceof ServerLevel sl)
                sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), getX(), getY(), getZ(), 64, 1, 1, 1, 0.2);
        }
        if (spawnTick == 20) {
            if (level() instanceof ServerLevel sl)
                sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), getX(), getY(), getZ(), 64, 1, 1, 1, 0.2);
        }
        if (spawnTick == 47) {
            safeExplode(getX(), getY() + 1, getZ(), 3, 10, 64);
        }
        if (spawnTick == 51) {
            setDeltaMovement(new Vec3(0, -0.4, 0));
        }
        if (spawnTick == 80) {
            switchState = 1;
            setHealth(getMaxHealth());
        }
        spawnTick++;
    }

    // ============ Skills ============

    private void startMagicballSkill() {
        skillLock = 1;
        activeSkill = SkillType.MAGICBALL;
        skillTick = 0;
        magicballCd = 90;
        setAnimation("skill_magicball");
    }

    private void startVortexSkill() {
        skillLock = 1;
        activeSkill = SkillType.VORTEX;
        skillTick = 0;
        vortexCd = 120;
        magicballCount = 0;
        setAnimation("skill_vortex");
        setDeltaMovement(new Vec3(0, -5, 0));
        addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 4, false, false));
    }

    private void startTunetotemSkill() {
        skillLock = 1;
        activeSkill = SkillType.TUNETOTEM;
        skillTick = 0;
        tunetotemCd = 600;
        setAnimation("skill_tunetotem");
        addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 1, false, false));
        setDeltaMovement(new Vec3(0, -2, 0));
        playSoundAt(ModSounds.STONE_BREAK.get(), 1, 1);

        // 15-block confusion to non-friendly targets
        Vec3 center = new Vec3(getX(), getY(), getZ());
        List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class,
            new AABB(center, center).inflate(7.5), e -> true);
        for (LivingEntity target : entities) {
            if (!target.getType().is(SPECIAL_ENTITY) && !target.getType().is(SHADOW_MOB)
                    && !(target instanceof Player player && (player.isCreative() || player.isSpectator()))) {
                target.addEffect(new MobEffectInstance(ModEffects.CONFUSION_BUFF.get(), 60, 1, false, false));
            }
        }
    }

    private void runActiveSkill() {
        switch (activeSkill) {
            case MAGICBALL -> runMagicballSkill();
            case VORTEX -> runVortexSkill();
            case TUNETOTEM -> runTunetotemSkill();
        }
    }

    private void runMagicballSkill() {
        if (skillTick == 5) {
            playSoundAt(ModSounds.STONE_BREAK_0.get(), 1, 1);
        }
        if (skillTick == 35) {
            // Look at nearest player
            Player nearest = getNearestPlayer(64);
            if (nearest != null) {
                lookAt(EntityAnchorArgument.Anchor.EYES, nearest.getEyePosition());
            }
            // Sound + particles
            explodeSound();
            if (level() instanceof ServerLevel sl) {
                sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), getX(), getY(), getZ(), 64, 1, 1, 1, 0.2);
                sl.sendParticles(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 16, 1, 1, 1, 0.2);
            }
            // Spawn ShadowMagicball
            if (level() instanceof ServerLevel _level) {
                Vec3 look = getLookAngle();
                Entity magicball = ModEntities.SHADOW_MAGICBALL.get().spawn(_level,
                    BlockPos.containing(getX() + look.x * 1.5, getY() + look.y, getZ() + look.z * 1.5),
                    MobSpawnType.MOB_SUMMONED);
                if (magicball != null) {
                    magicball.setYRot(getYRot());
                    magicball.setYBodyRot(getYRot());
                    magicball.setYHeadRot(getYRot());
                    magicball.setXRot(getXRot());
                    magicball.setDeltaMovement(look.x * 3, look.y * 2, look.z * 3);
                }
            }
        }
        if (skillTick == 20) {
            magicballCount++;
        }
        if (skillTick >= 40) {
            endSkill();
        }
    }

    private void runVortexSkill() {
        if (skillTick == 42) {
            if (level() instanceof ServerLevel sl) {
                sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), getX(), getY(), getZ(), 128, 2, 1, 2, 0.1);
                sl.sendParticles(ParticleTypes.SMOKE, getX(), getY(), getZ(), 64, 2, 1, 2, 0.5);
            }
            Vec3 center = new Vec3(getX(), getY(), getZ());
            List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class,
                new AABB(center, center).inflate(32), e -> true);
            int vortexCount = 0;
            for (LivingEntity target : entities) {
                if (vortexCount >= 4) break;
                if (!target.getType().is(SPECIAL_ENTITY) && !target.getType().is(SHADOW_MOB)
                    && (!(target instanceof Player player)
                        || (target == getTarget() && !player.isCreative() && !player.isSpectator()))) {
                    target.addEffect(new MobEffectInstance(ModEffects.CONFUSION_BUFF.get(), 10, 1, false, false));
                    target.hurt(new DamageSource(level().registryAccess()
                        .registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(DamageTypes.GENERIC)), skillDamage(0.22f));
                    target.setDeltaMovement(new Vec3(0, 0.2, 0));
                    level().setBlock(BlockPos.containing(target.getX(), target.getY(), target.getZ()),
                        ModBlocks.SHADOW_VORTEX.get().defaultBlockState(), 3);
                    playSoundAt(ModSounds.SHADOW_VORTEX.get(), 0.8f, 1);
                    vortexCount++;
                }
            }
            // Place vortex at own position too
            level().setBlock(BlockPos.containing(getX(), getY(), getZ()),
                ModBlocks.SHADOW_VORTEX.get().defaultBlockState(), 3);
            playSoundAt(ModSounds.SHADOW_VORTEX.get(), 0.8f, 1);
        }
        if (skillTick >= 120) {
            endSkill();
        }
    }

    private void runTunetotemSkill() {
        if (skillTick == 10) {
            addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 4, false, false));
            if (level() instanceof ServerLevel sl)
                sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), getX(), getY(), getZ(), 32, 1, 0, 1, 0.5);
        }
        if (skillTick == 15) {
            if (level() instanceof ServerLevel sl)
                sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), getX(), getY(), getZ(), 32, 1, 0, 1, 0.5);
        }
        if (skillTick == 21) {
            if (level() instanceof ServerLevel _level) {
                Entity totem = ModEntities.SHADOW_TUNE_TOTEM.get().spawn(_level,
                    BlockPos.containing(getX(), getY(), getZ()), MobSpawnType.MOB_SUMMONED);
                if (totem != null) {
                    totem.setYRot(level().getRandom().nextFloat() * 360F);
                }
            }
            Vec3 look = getLookAngle();
            setDeltaMovement(new Vec3(-look.x, 0, -look.z));
        }
        if (skillTick >= 120) {
            endSkill();
        }
    }

    private void endSkill() {
        activeSkill = SkillType.NONE;
        skillTick = 0;
        skillLock = 0;
    }

    private float skillDamage(float ratio) {
        return (float) (getAttributeValue(Attributes.ATTACK_DAMAGE) * ratio);
    }

    // ============ Sister Synergy ============

    private void updateSisterSynergy() {
        if (sisterScanTick > 0) {
            sisterScanTick--;
            return;
        }
        sisterScanTick = 20;
        boolean found = !level().getEntitiesOfClass(AaroncosLeftHandEntity.class,
                this.getBoundingBox().inflate(64), e -> e.isAlive()).isEmpty();
        if (found != sisterNearby) {
            sisterNearby = found;
            var attr = getAttribute(Attributes.ATTACK_SPEED);
            if (attr != null) {
                if (found) {
                    attr.removeModifier(SYNERGY_SPEED_ID);
                } else {
                    attr.addPermanentModifier(new AttributeModifier(SYNERGY_SPEED_ID,
                            "Sister synergy speed", 0.5, AttributeModifier.Operation.MULTIPLY_BASE));
                }
            }
        }
    }

    // ============ Bloodlock ============

    private void checkBloodlock() {
        if (bloodLock)
            return;
        if (getHealth() <= 100) {
            bloodLock = true;
            addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 3, false, false));
            removeEffect(ModEffects.SHADOW_SILENCE_BUFF.get());

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

            Vec3 center = new Vec3(getX(), getY(), getZ());
            List<Entity> entities = level().getEntitiesOfClass(Entity.class,
                new AABB(center, center).inflate(40), e -> true);
            for (Entity target : entities) {
                if (target instanceof Player player
                        && !player.isCreative() && !player.isSpectator()) {
                    player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 0));
                    player.addEffect(new MobEffectInstance(ModEffects.CONFUSION_BUFF.get(), 60, 1));
                    player.addEffect(new MobEffectInstance(ModEffects.RESTRAINMOVE_BLOCK_BUFF.get(), 60, 0));
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
            player.getPersistentData().putString("aaroncos_right_hand_name", getDisplayName().getString());
            player.getPersistentData().putDouble("aaroncos_right_hand_hp", getHealth());
            player.getPersistentData().putDouble("aaroncos_right_hand_max_hp", getMaxHealth());
            player.getPersistentData().putBoolean("aaroncos_right_hand_life", isAlive());
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

    private void playSoundAt(net.minecraft.sounds.SoundEvent sound, float volume, float pitch) {
        this.playSound(sound, volume, pitch);
    }

    private void safeExplode(double cx, double cy, double cz, double radius, float damage, int particleCount) {
        playSoundAt(net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE, 1, 1);
        if (level() instanceof ServerLevel sl) {
            sl.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), cx, cy, cz, particleCount, radius / 2, radius / 2, radius / 2, 0.3);
            sl.sendParticles(ParticleTypes.EXPLOSION, cx, cy, cz, particleCount / 4, radius / 3, radius / 3, radius / 3, 0.3);
        }
        Vec3 center = new Vec3(cx, cy, cz);
        List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class,
            new AABB(center, center).inflate(radius), e -> true);
        for (LivingEntity target : entities) {
            if (!target.getType().is(SPECIAL_ENTITY) && !target.getType().is(SHADOW_MOB)
                    && !(target instanceof Player player && (player.isCreative() || player.isSpectator()))) {
                target.hurt(new DamageSource(level().registryAccess()
                    .registryOrThrow(Registries.DAMAGE_TYPE)
                    .getHolderOrThrow(DamageTypes.EXPLOSION)), damage);
            }
        }
    }

    private void explodeSound() {
        playSoundAt(net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE, 1, 1);
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
    public boolean isPushable() {
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

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return switchState == 0
                && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                || super.isInvulnerableTo(source);
    }

    @Override
    public int getExperienceReward() {
        return (int) (xpReward * ShadowDifficultyHelper.getLootMultiplier(
                ShadowDifficultyHelper.getDifficultyContext(this)));
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MOVEMENT_SPEED, 0.6)
            .add(Attributes.MAX_HEALTH, 500)
            .add(Attributes.ARMOR, 4)
            .add(Attributes.ATTACK_DAMAGE, 18)
            .add(Attributes.FOLLOW_RANGE, 64)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1)
            .add(Attributes.FLYING_SPEED, 1.0);
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
