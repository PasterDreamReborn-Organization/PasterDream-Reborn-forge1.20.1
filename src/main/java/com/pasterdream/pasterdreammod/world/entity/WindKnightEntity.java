package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.BossDamageLimiter;
import com.pasterdream.pasterdreammod.helper.BossLimitProfile;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Comparator;

public class WindKnightEntity extends Monster implements GeoEntity {
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(WindKnightEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(WindKnightEntity.class, EntityDataSerializers.STRING);
    private static final TagKey<EntityType<?>> SPECIAL_ENTITY = TagKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "special_entity_tag"));

    // 可调参数
    private static final int XP_REWARD = 32;                    // 经验值
    private static final double MELEE_SPEED = 1.25;             // 近战攻击速度
    private static final double MELEE_REACH_EXTRA = 4.0;        // 近战攻击距离附加
    private static final int TARGET_INFORM_INTERVAL = 10;       // 目标通知间隔（tick）
    private static final double STROLL_SPEED = 0.8;             // 闲逛速度
    private static final float STEP_SOUND_VOLUME = 0.15f;       // 脚步声音量
    private static final float STEP_SOUND_PITCH = 1.0f;         // 脚步声音调
    private static final float DIMENSION_SCALE = 1.6f;          // 实体碰撞箱缩放
    private static final int SKILL_COOLDOWN = 100;              // 技能冷却（tick）
    private static final double SKILL_TRIGGER_DIST = 36.0;      // 技能触发距离（平方，6格）
    private static final int SKILL_DELAY = 25;                  // 技能释放延迟（tick）
    private static final double SKILL_RADIUS = 7.0;             // 技能范围（格）
    private static final float SKILL_DAMAGE = 30;               // 技能伤害
    private static final int EXPLOSION_PARTICLE_COUNT = 3;      // 爆炸粒子数量
    private static final double PARTICLE_SPREAD = 0.1;          // 粒子扩散
    private static final double CLOUD_PARTICLE_Y_OFFSET = 1.5;  // 云/暴击粒子 Y 偏移
    private static final int BIG_PARTICLE_COUNT = 80;           // 云/暴击粒子数量
    private static final double BIG_PARTICLE_SPREAD_Y = 0.5;    // 云/暴击粒子 Y 扩散
    private static final float SKILL_SOUND_VOLUME = 1.1f;       // 技能音效音量
    private static final float SKILL_SOUND_PITCH = 0.9f;        // 技能音效音调
    private static final float EXPLODE_SOUND_VOLUME = 0.7f;     // 爆炸音效音量
    private static final float EXPLODE_SOUND_PITCH = 1.0f;      // 爆炸音效音调
    private static final int SPEED_EFFECT_DURATION = 20;        // 移速效果时长（tick）
    private static final int SPEED_EFFECT_AMPLIFIER = 0;        // 移速效果等级
    private static final int HURT_SOUND_DELAY = 5;              // 受击音效延迟（tick）
    private static final int SLOWDOWN_EFFECT_DURATION = 20;     // 缓速效果时长（tick）
    private static final int SLOWDOWN_EFFECT_AMPLIFIER = 4;     // 缓速效果等级
    private static final float HURT_SOUND_VOLUME = 1.0f;        // 受击音效音量
    private static final float HURT_SOUND_PITCH = 1.1f;         // 受击音效音调
    private static final double MOVE_SPEED = 0.25;              // 移动速度属性
    private static final double MAX_HEALTH = 250;               // 最大生命属性
    private static final double ARMOR = 10;                     // 护甲属性
    private static final double ATTACK_DAMAGE = 20;             // 攻击伤害属性
    private static final double FOLLOW_RANGE = 16;              // 追踪距离属性
    private static final double KNOCKBACK_RESISTANCE = 0.4;     // 击退抗性属性
    private static final long SWING_DURATION = 7L;              // 攻击动画时长（tick）
    private static final int DEATH_TIME = 20;                   // 死亡动画时长（tick）

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.GREEN, ServerBossEvent.BossBarOverlay.PROGRESS);
    private boolean swinging;
    private long lastSwing;
    public String animationprocedure = "empty";
    private int skillCooldown;
    private final BossDamageLimiter damageLimiter;

    public WindKnightEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.WIND_KNIGHT.get(), world);
    }

    public WindKnightEntity(EntityType<WindKnightEntity> type, Level world) {
        super(type, world);
        xpReward = XP_REWARD;
        setNoAi(false);
        this.bossInfo.setVisible(false);
        this.damageLimiter = new BossDamageLimiter(BossLimitProfile.WIND_KNIGHT);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "wind_knight");
    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE, texture);
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, MELEE_SPEED, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth() + MELEE_REACH_EXTRA;
            }
        });
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, TARGET_INFORM_INTERVAL, false, false,
                target -> target instanceof Player player && !player.isCreative() && !player.isSpectator()));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, STROLL_SPEED));
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, STEP_SOUND_VOLUME, STEP_SOUND_PITCH);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.IN_FIRE) || source.is(DamageTypes.FALL) || source.is(DamageTypes.LIGHTNING_BOLT))
            return false;
        float prevBucket = damageLimiter.getDamageBucket();
        amount = damageLimiter.limit(this, source, amount);
        if (amount < 0) return false;
        boolean result = super.hurt(source, amount);
        if (!result) damageLimiter.rollback(prevBucket);
        return result;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture"))
            this.setTexture(compound.getString("Texture"));
    }

    @Override
    public void baseTick() {
        super.baseTick();
        damageLimiter.tick();
        skillTick();
        this.refreshDimensions();
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
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return super.getDimensions(pose).scale(DIMENSION_SCALE);
    }

    private void skillTick() {
        if (skillCooldown >= SKILL_COOLDOWN) {
            LivingEntity target = getTarget();
            if (target != null && target.isAlive() && distanceToSqr(target) <= SKILL_TRIGGER_DIST) {
                setAnimation("skill_0");
                PasterDreamMod.queueServerWork(SKILL_DELAY, () -> {
                    Vec3 center = position();
                    for (Entity e : level().getEntitiesOfClass(Entity.class,
                            new AABB(center, center).inflate(SKILL_RADIUS), e -> true).stream()
                            .sorted(Comparator.comparingDouble(en -> en.distanceToSqr(center))).toList()) {
                        if (!e.getType().is(SPECIAL_ENTITY) && !(e instanceof WindKnightEntity)
                                && !(e instanceof Player player && (player.isCreative() || player.isSpectator()))) {
                            e.hurt(damageSources().mobAttack(this), SKILL_DAMAGE);
                            if (level() instanceof ServerLevel sl)
                                sl.sendParticles(ParticleTypes.EXPLOSION, e.getX(), e.getY(), e.getZ(), EXPLOSION_PARTICLE_COUNT, PARTICLE_SPREAD, PARTICLE_SPREAD, PARTICLE_SPREAD, PARTICLE_SPREAD);
                        }
                    }
                    if (level() instanceof ServerLevel sl) {
                        sl.sendParticles(ParticleTypes.CLOUD, center.x, center.y + CLOUD_PARTICLE_Y_OFFSET, center.z, BIG_PARTICLE_COUNT, SKILL_RADIUS, BIG_PARTICLE_SPREAD_Y, SKILL_RADIUS, PARTICLE_SPREAD);
                        sl.sendParticles(ParticleTypes.CRIT, center.x, center.y + CLOUD_PARTICLE_Y_OFFSET, center.z, BIG_PARTICLE_COUNT, SKILL_RADIUS, BIG_PARTICLE_SPREAD_Y, SKILL_RADIUS, PARTICLE_SPREAD);
                    }
                    if (!level().isClientSide()) {
                        level().playSound(null, BlockPos.containing(center), ModSounds.WIND_KNIGHT_SKILL.get(), SoundSource.MASTER, SKILL_SOUND_VOLUME, SKILL_SOUND_PITCH);
                        level().playSound(null, BlockPos.containing(center), SoundEvents.GENERIC_EXPLODE, SoundSource.MASTER, EXPLODE_SOUND_VOLUME, EXPLODE_SOUND_PITCH);
                    }
                    addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, SPEED_EFFECT_DURATION, SPEED_EFFECT_AMPLIFIER, false, false));
                });
                addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, SLOWDOWN_EFFECT_DURATION, SLOWDOWN_EFFECT_AMPLIFIER, false, false));
                PasterDreamMod.queueServerWork(HURT_SOUND_DELAY, () -> {
                    if (!level().isClientSide())
                        level().playSound(null, BlockPos.containing(position()), SoundEvents.IRON_GOLEM_HURT, SoundSource.MASTER, HURT_SOUND_VOLUME, HURT_SOUND_PITCH);
                });
                skillCooldown = 0;
            }
        } else {
            skillCooldown++;
        }
        clearFire();
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, MOVE_SPEED)
                .add(Attributes.MAX_HEALTH, MAX_HEALTH)
                .add(Attributes.ARMOR, ARMOR)
                .add(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE)
                .add(Attributes.FOLLOW_RANGE, FOLLOW_RANGE)
                .add(Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE);
    }

    private PlayState movementPredicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F) || this.isSprinting()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.STOP;
    }

    private PlayState attackingPredicate(AnimationState event) {
        if (!this.animationprocedure.equals("empty"))
            return PlayState.STOP;
        double d1 = this.getX() - this.xOld;
        double d0 = this.getZ() - this.zOld;
        float velocity = (float) Math.sqrt(d1 * d1 + d0 * d0);
        if (getAttackAnim(event.getPartialTick()) > 0f && !this.swinging) {
            this.swinging = true;
            this.lastSwing = level().getGameTime();
        }
        if (this.swinging && this.lastSwing + SWING_DURATION <= level().getGameTime()) {
            this.swinging = false;
        }
        if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().forceAnimationReset();
            return event.setAndContinue(RawAnimation.begin().thenPlay("attack"));
        }
        return PlayState.CONTINUE;
    }

    private PlayState procedurePredicate(AnimationState event) {
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
        if (this.deathTime == DEATH_TIME) {
            this.remove(WindKnightEntity.RemovalReason.KILLED);
            this.dropExperience();
        }
    }

    public String getSyncedAnimation() {
        return this.entityData.get(ANIMATION);
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
