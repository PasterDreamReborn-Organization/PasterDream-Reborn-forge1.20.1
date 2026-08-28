package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.BossDamageLimiter;
import com.pasterdream.pasterdreammod.helper.BossLimitProfile;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionHand;
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
import net.minecraft.tags.DamageTypeTags;
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
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
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
    private static final double MELEE_REACH = 6.0;              // 近战攻击距离（格，中心距）
    private static final int TARGET_INFORM_INTERVAL = 10;       // 目标通知间隔（tick）
    private static final double STROLL_SPEED = 0.8;             // 闲逛速度
    private static final float STEP_SOUND_VOLUME = 0.15f;       // 脚步声音量
    private static final float STEP_SOUND_PITCH = 1.0f;         // 脚步声音调
    private static final float DIMENSION_SCALE = 1.6f;          // 实体碰撞箱缩放
    private static final int SKILL_COOLDOWN = 180;              // 技能冷却
    private static final double SKILL_TRIGGER_DIST = 36.0;      // 技能触发距离（平方，6格）
    private static final int SKILL_DELAY = 25;                  // 技能释放延迟（tick）
    private static final int SKILL_INVULNERABLE_TICKS = 40;     // 技能释放期间无敌时长（tick，2s）
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
    private static final double ARMOR = 20;                     // 护甲属性
    private static final double ATTACK_DAMAGE = 20;             // 攻击伤害属性
    private static final double FOLLOW_RANGE = 32;              // 追踪距离属性
    private static final double KNOCKBACK_RESISTANCE = 1.0;     // 击退抗性属性
    private static final int ATTACK_ANIM_LENGTH = 20;           // 攻击动画时长（tick，attack 动画长 1s）
    private static final int ATTACK_INTERVAL = 25;              // 攻击间隔（tick），略大于动画时长避免重叠
    private static final int ATTACK_DAMAGE_DELAY = 10;          // 攻击动画播放后延迟结算伤害（tick）
    private static final int SKILL_ANIM_LENGTH_TICKS = 50;      // 技能动画时长（tick，skill_0 约 2.52s）
    private static final int DEATH_TIME = 20;                   // 死亡动画时长（tick）
    private static final double LIGHTNING_SPAWN_HEIGHT = 5.0;   // 落雷生成高度（目标头顶上方，格）
    private static final double LIGHTNING_DAMAGE = 7;           // 落雷伤害（与雷云一致）
    private static final int LIGHTNING_DELAY_TICKS = 10;        // 落雷延迟（普攻命中后延迟的 tick 数）

    // 狂暴（半血）阶段参数
    private static final float ENRAGE_HEALTH_RATIO = 0.5f;          // 狂暴触发血量比例（≤50%）
    private static final float ENRAGE_DAMAGE_MULTIPLIER = 1.5f;     // 狂暴阶段造成的伤害倍率
    private static final int ENRAGE_RESISTANCE_AMPLIFIER = 1;       // 抗性提升 II（amplifier=1）
    private static final int ENRAGE_RESISTANCE_DURATION = 60;       // 抗性提升持续时长（tick）
    private static final int ENRAGE_RESISTANCE_REFRESH = 30;        // 抗性提升刷新间隔（tick）
    private static final int ENRAGE_CLOUD_COUNT = 4;                // 召唤高压雷云数量
    private static final double ENRAGE_CLOUD_HEIGHT = 6.0;          // 高压雷云生成高度（头顶上方，格）
    private static final double ENRAGE_CLOUD_RADIUS = 2.0;          // 高压雷云环绕半径（格）
    private static final int ENRAGE_CLOUD_RESISTANCE_AMPLIFIER = 1; // 召唤雷云获得抗性提升 II
    private static final int ENRAGE_CLOUD_STRENGTH_AMPLIFIER = 1;   // 召唤雷云获得力量 II
    private static final int ENRAGE_CLOUD_EFFECT_DURATION = 1200;   // 召唤雷云增益持续时长（tick，60s）

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.GREEN, ServerBossEvent.BossBarOverlay.PROGRESS);
    public String animationprocedure = "empty";
    private int procedureTimer;
    private int skillCooldown;
    private int skillDamageCountdown;
    private int hurtSoundCountdown;
    private int skillInvulnerableCountdown;
    private int lightningCountdown;
    private LivingEntity pendingLightningTarget;
    private int attackDamageCountdown;
    private LivingEntity pendingAttackTarget;
    private double pendingAttackReachSqr;
    private final BossDamageLimiter damageLimiter;
    private boolean enrageCloudsSpawned;
    private int enrageResistanceCountdown;

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
        this.goalSelector.addGoal(1, new AnimationMeleeAttackGoal(this, MELEE_SPEED, false));
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
        // 技能释放期间无敌（可被 BYPASSES_INVULNERABILITY 穿透，如 /kill）
        if (skillInvulnerableCountdown > 0 && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
            return false;

        // 先记录攻击者，保证即使伤害被限伤减免或免伤拦截，也会反击并转向攻击者
        if (!level().isClientSide() && !source.is(DamageTypeTags.NO_ANGER)) {
            Entity attacker = source.getEntity();
            if (attacker instanceof LivingEntity living) {
                this.setLastHurtByMob(living);
            }
        }

        float prevBucket = damageLimiter.getDamageBucket();
        amount = damageLimiter.limit(this, source, amount);
        if (amount < 0) return false;
        boolean result = super.hurt(source, amount);
        if (!result) damageLimiter.rollback(prevBucket);
        // 被动：受击后按概率获得短暂无敌（概率/时长/开关由 Config 控制，取较长者避免覆盖技能无敌），触发时播放祭坛修复同款粒子
        if (result && !level().isClientSide()
                && Config.windKnightPassiveInvulnerableEnabled
                && random.nextDouble() < Config.windKnightPassiveInvulnerableChance) {
            skillInvulnerableCountdown = Math.max(skillInvulnerableCountdown, Config.windKnightPassiveInvulnerableTicks);
            if (level() instanceof ServerLevel sl)
                sl.sendParticles(ParticleTypes.SCRAPE, getX(), getY() + getBbHeight() * 0.6, getZ(), 24, 1.2, 1.0, 1.2, 0.1);
        }
        return result;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return skillInvulnerableCountdown > 0
                && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                || super.isInvulnerableTo(source);
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
        if (!level().isClientSide()) {
            enrageTick();
            if (procedureTimer > 0) {
                procedureTimer--;
                if (procedureTimer == 0)
                    setAnimation("empty");
            }
            // 延迟事件倒计时：技能伤害 / 受击音效 / 落雷 / 普攻伤害
            if (skillDamageCountdown > 0 && --skillDamageCountdown == 0)
                executeSkillDamage();
            if (hurtSoundCountdown > 0 && --hurtSoundCountdown == 0)
                executeHurtSound();
            if (skillInvulnerableCountdown > 0)
                skillInvulnerableCountdown--;
            if (lightningCountdown > 0 && --lightningCountdown == 0)
                executeLightningStrike();
            if (attackDamageCountdown > 0 && --attackDamageCountdown == 0)
                executeAttackDamage();
        }
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
            // 只有在未播放其他程序动画（普攻/技能）时才释放战技，避免动画互相覆盖
            if (target != null && target.isAlive() && distanceToSqr(target) <= SKILL_TRIGGER_DIST
                    && animationprocedure.equals("empty")) {
                startProcedureAnimation("skill_0", SKILL_ANIM_LENGTH_TICKS);
                skillDamageCountdown = SKILL_DELAY;
                hurtSoundCountdown = HURT_SOUND_DELAY;
                skillInvulnerableCountdown = SKILL_INVULNERABLE_TICKS;
                addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, SLOWDOWN_EFFECT_DURATION, SLOWDOWN_EFFECT_AMPLIFIER, false, false));
                skillCooldown = 0;
            }
        } else {
            skillCooldown++;
        }
        clearFire();
    }

    /** 技能伤害结算：由 baseTick 在 SKILL_DELAY 后调用 */
    private void executeSkillDamage() {
        // 延迟期间骑士可能死亡/被移除，此时不得再造成伤害或释放粒子
        if (this.isRemoved() || !this.isAlive())
            return;
        Vec3 center = position();
        for (Entity e : level().getEntitiesOfClass(Entity.class,
                new AABB(center, center).inflate(SKILL_RADIUS), e -> true).stream()
                .sorted(Comparator.comparingDouble(en -> en.distanceToSqr(center))).toList()) {
            if (!e.getType().is(SPECIAL_ENTITY) && !(e instanceof WindKnightEntity)
                    && !(e instanceof Player player && (player.isCreative() || player.isSpectator()))) {
                e.hurt(damageSources().mobAttack(this), SKILL_DAMAGE * (isEnraged() ? ENRAGE_DAMAGE_MULTIPLIER : 1.0f));
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
    }

    /** 受击音效：由 baseTick 在 HURT_SOUND_DELAY 后调用 */
    private void executeHurtSound() {
        if (!level().isClientSide() && !this.isRemoved() && this.isAlive())
            level().playSound(null, BlockPos.containing(position()), SoundEvents.IRON_GOLEM_HURT, SoundSource.MASTER, HURT_SOUND_VOLUME, HURT_SOUND_PITCH);
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

    /**
     * 单控制器方案：服务器权威驱动动画状态，客户端只负责播放当前动画。
     * 优先级：技能/程序动画（含普攻） > 行走/待机。
     * 通过比较当前动画名称决定是否切换，不再依赖 getAnimationState()==STOPPED，
     * 从而消除"有时无法播放战技/普攻动画"的竞态问题。
     */
    private PlayState animationPredicate(AnimationState event) {
        AnimationController<WindKnightEntity> controller = event.getController();
        String currentName = controller.getCurrentAnimation() == null
                ? "" : controller.getCurrentAnimation().animation().name();

        // 1. 技能/程序动画（最高优先级，普攻也走这里）
        if (!animationprocedure.equals("empty")) {
            if (!animationprocedure.equals(currentName)) {
                controller.setAnimation(RawAnimation.begin().thenPlay(animationprocedure));
            }
            return PlayState.CONTINUE;
        }

        // 2. 行走/待机
        boolean moving = event.isMoving()
                || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)
                || this.isSprinting();
        String target = moving ? "walk" : "idle";
        if (!target.equals(currentName)) {
            controller.setAnimation(RawAnimation.begin().thenLoop(target));
        }
        return PlayState.CONTINUE;
    }

    /**
     * 普攻命中后延迟 LIGHTNING_DELAY_TICKS tick，再按配置概率（Config.windKnightLightningChance）
     * 在目标头顶召唤落雷。落雷与雷云实体所使用的攻击一致：从天而降的 LightningProjectileEntity。
     */
    private void attemptLightningStrike(LivingEntity target) {
        pendingLightningTarget = target;
        lightningCountdown = LIGHTNING_DELAY_TICKS;
    }

    /** 落雷结算：由 baseTick 在 LIGHTNING_DELAY_TICKS 后调用 */
    private void executeLightningStrike() {
        LivingEntity target = pendingLightningTarget;
        pendingLightningTarget = null;
        // 延迟期间骑士或目标可能死亡/被移除，此时不得再召唤落雷
        if (target == null || this.isRemoved() || !this.isAlive() || target.isRemoved() || !target.isAlive())
            return;
        if (level().isClientSide() || random.nextDouble() > Config.windKnightLightningChance)
            return;
        LightningProjectileEntity proj = new LightningProjectileEntity(ModEntities.LIGHTNING_PROJECTILE.get(), level());
        proj.setBaseDamage(LIGHTNING_DAMAGE);
        proj.setKnockback(0);
        proj.setSilent(true);
        proj.setPierceLevel((byte) 1);
        proj.setPos(target.getX(), target.getY() + LIGHTNING_SPAWN_HEIGHT, target.getZ());
        proj.shoot(0, -1, 0, 1, 0);
        level().addFreshEntity(proj);
        level().playSound(null, target.getOnPos(), ModSounds.THUNDERCLOUD_ATTACK.get(), SoundSource.MASTER, 0.6f, 1f);
    }

    /** 是否处于狂暴阶段（半血及以下） */
    private boolean isEnraged() {
        return this.getHealth() <= this.getMaxHealth() * ENRAGE_HEALTH_RATIO;
    }

    /**
     * 狂暴阶段逻辑：半血以下时持续获得抗性提升 II，
     * 并在首次进入狂暴时于头顶周围召唤高压雷云。恢复半血以上后可再次触发。
     */
    private void enrageTick() {
        if (this.isRemoved() || !this.isAlive())
            return;
        if (isEnraged()) {
            if (--enrageResistanceCountdown <= 0) {
                enrageResistanceCountdown = ENRAGE_RESISTANCE_REFRESH;
                addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, ENRAGE_RESISTANCE_DURATION, ENRAGE_RESISTANCE_AMPLIFIER, false, false));
            }
            if (!enrageCloudsSpawned) {
                enrageCloudsSpawned = true;
                spawnEnrageThunderclouds();
            }
        } else {
            enrageCloudsSpawned = false;
        }
    }

    /** 在头顶周围召唤 ENRAGE_CLOUD_COUNT 朵高压雷云（跟随骑士的仇恨目标） */
    private void spawnEnrageThunderclouds() {
        if (!(level() instanceof ServerLevel sl))
            return;
        for (int i = 0; i < ENRAGE_CLOUD_COUNT; i++) {
            double angle = 2 * Math.PI * i / ENRAGE_CLOUD_COUNT;
            double x = getX() + Math.cos(angle) * ENRAGE_CLOUD_RADIUS;
            double z = getZ() + Math.sin(angle) * ENRAGE_CLOUD_RADIUS;
            HighvoltageThundercloudEntity cloud = new HighvoltageThundercloudEntity(ModEntities.HIGHVOLTAGE_THUNDERCLOUD.get(), level());
            // 向上找最近的空气格，避免雷云卡进天花板/墙壁
            double cloudY = getY() + ENRAGE_CLOUD_HEIGHT;
            while (cloudY < level().getMaxBuildHeight() - 1
                    && !level().isEmptyBlock(BlockPos.containing(x, cloudY, z))) {
                cloudY++;
            }
            cloud.setPos(x, cloudY, z);
            cloud.setSummoningKnight(this);
            cloud.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, ENRAGE_CLOUD_EFFECT_DURATION, ENRAGE_CLOUD_RESISTANCE_AMPLIFIER, false, false));
            cloud.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, ENRAGE_CLOUD_EFFECT_DURATION, ENRAGE_CLOUD_STRENGTH_AMPLIFIER, false, false));
            sl.addFreshEntity(cloud);
            LivingEntity target = getTarget();
            if (target != null && target.isAlive() && !target.isRemoved()) {
                cloud.setTarget(target);
            }
        }
    }

    /** 近战伤害：狂暴（半血以下）阶段造成的伤害 ×1.5 */
    @Override
    public boolean doHurtTarget(Entity target) {
        if (!isEnraged())
            return super.doHurtTarget(target);
        AttributeInstance attack = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attack == null)
            return super.doHurtTarget(target);
        double base = attack.getBaseValue();
        attack.setBaseValue(base * ENRAGE_DAMAGE_MULTIPLIER);
        boolean result = super.doHurtTarget(target);
        attack.setBaseValue(base);
        return result;
    }

    /** 记录待结算的普攻伤害：由攻击 Goal 触发，baseTick 在 ATTACK_DAMAGE_DELAY 后结算 */
    public void scheduleAttackDamage(LivingEntity enemy, double reachSqr) {
        pendingAttackTarget = enemy;
        pendingAttackReachSqr = reachSqr;
        attackDamageCountdown = ATTACK_DAMAGE_DELAY;
    }

    /** 普攻伤害结算：由 baseTick 在 ATTACK_DAMAGE_DELAY 后调用 */
    private void executeAttackDamage() {
        LivingEntity enemy = pendingAttackTarget;
        pendingAttackTarget = null;
        if (enemy == null)
            return;
        if (!this.isRemoved() && this.isAlive()
                && enemy.isAlive()
                && this.distanceToSqr(enemy) <= pendingAttackReachSqr) {
            this.swing(InteractionHand.MAIN_HAND);
            this.doHurtTarget(enemy);
            if (enemy.isAlive()) {
                this.attemptLightningStrike(enemy);
            }
        }
    }

    /**
     * 近战攻击：普攻通过 startProcedureAnimation 走程序动画，由服务器驱动，
     * 先播动画再延迟结算伤害。
     */
    private static class AnimationMeleeAttackGoal extends MeleeAttackGoal {
        private final WindKnightEntity mob;
        private long lastAttackTick = -ATTACK_INTERVAL;

        public AnimationMeleeAttackGoal(WindKnightEntity mob, double speed, boolean followingTargetEvenIfNotSeen) {
            super(mob, speed, followingTargetEvenIfNotSeen);
            this.mob = mob;
        }

        @Override
        protected double getAttackReachSqr(LivingEntity entity) {
            return this.mob.getBbWidth() + entity.getBbWidth() + MELEE_REACH * MELEE_REACH;
        }

        @Override
        protected int getAttackInterval() {
            return this.adjustedTickDelay(ATTACK_INTERVAL);
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double reach = getAttackReachSqr(enemy);
            // 未在播放其他程序动画（战技/普攻）时才普攻，与战技互斥，避免动画互相覆盖
            if (distToEnemySqr <= reach && this.getTicksUntilNextAttack() <= 0
                    && mob.animationprocedure.equals("empty")
                    && mob.level().getGameTime() >= lastAttackTick + ATTACK_INTERVAL) {
                this.resetAttackCooldown();
                this.lastAttackTick = mob.level().getGameTime();
                // 先播放攻击动画，延迟 ATTACK_DAMAGE_DELAY tick 后再结算伤害
                this.mob.startProcedureAnimation("attack", ATTACK_ANIM_LENGTH);
                this.mob.scheduleAttackDamage(enemy, reach);
            }
        }
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

    /**
     * 启动技能/程序动画（普攻也走这里）。先清空再设置，强制触发 SynchedEntityData 的值变化，
     * 确保客户端即使在连续/重叠攻击时也能收到同步并重新播放动画。
     * 服务器端记录时长，到期后由 baseTick 自动清空。
     */
    public void startProcedureAnimation(String animation, int durationTicks) {
        this.setAnimation("empty");
        this.setAnimation(animation);
        this.procedureTimer = durationTicks;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "wind_knight", 4, this::animationPredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
