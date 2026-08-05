package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class ShadowTuneTotemEntity extends Monster implements GeoEntity {
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ShadowTuneTotemEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ShadowTuneTotemEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";
    private int lifeTicks = 0;

    private static final TagKey<EntityType<?>> SHADOW_MOB = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("pasterdream", "shadow_mob"));
    private static final TagKey<EntityType<?>> SPECIAL_ENTITY = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("pasterdream", "special_entity_tag"));

    public ShadowTuneTotemEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.SHADOW_TUNE_TOTEM.get(), world);
    }

    public ShadowTuneTotemEntity(EntityType<ShadowTuneTotemEntity> type, Level world) {
        super(type, world);
        xpReward = 0;
        setNoAi(true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "shadow_rune_totem");
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
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.parse("block.deepslate_bricks.break"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.parse("entity.generic.explode"));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.IN_FIRE))
            return false;
        if (source.getDirectEntity() instanceof ThrownPotion || source.getDirectEntity() instanceof AreaEffectCloud)
            return false;
        if (source.is(DamageTypes.FALL) || source.is(DamageTypes.WITHER) || source.is(DamageTypes.WITHER_SKULL))
            return false;
        return super.hurt(source, amount);
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
        lifeTicks++;
        this.refreshDimensions();

        if (!level().isClientSide()) {
            // baseTick: buff boss hands, debuff players every tick
            applyTotemAura();

            // Spawn sequence
            if (lifeTicks == 1) {
                sendMessageToNearbyPlayers("暗影符文塔正在蓄能");
            }
            if (lifeTicks == 300) {
                sendMessageToNearbyPlayers("暗影符文塔即将发生爆破");
            }
            if (lifeTicks == 400) {
                setAnimation("skill");
            }
            if (lifeTicks == 482) {
                if (isAlive()) {
                    ServerLevel sw = (ServerLevel) level();
                    Vec3 center = new Vec3(getX(), getY(), getZ());
                    List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class,
                            new AABB(center, center).inflate(49.5), e -> true);
                    for (LivingEntity target : entities) {
                        if (!target.getType().is(SPECIAL_ENTITY) && !target.getType().is(SHADOW_MOB)
                                && !(target instanceof Player player && (player.isCreative() || player.isSpectator()))) {
                            target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200, 0));
                            target.hurt(new DamageSource(level().registryAccess()
                                    .registryOrThrow(Registries.DAMAGE_TYPE)
                                    .getHolderOrThrow(DamageTypes.EXPLOSION)), skillDamage(1.0f));
                            sw.sendParticles(ParticleTypes.EXPLOSION, target.getX(), target.getY() + 1, target.getZ(), 8, 1, 1, 1, 0.5);
                        }
                    }
                    this.playSound(net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE, 1, 1);
                    sw.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), getX(), getY(), getZ(), 128, 1, 4, 1, 0.1);
                    sw.sendParticles(ParticleTypes.SMOKE, getX(), getY(), getZ(), 128, 1, 4, 1, 0.1);
                }
            }
            if (lifeTicks == 497) {
                if (isAlive()) discard();
            }
        }
    }

    private void applyTotemAura() {
        Vec3 center = new Vec3(getX(), getY(), getZ());
        List<Entity> entities = level().getEntitiesOfClass(Entity.class,
                new AABB(center, center).inflate(32), e -> true);
        for (Entity target : entities) {
            String key = EntityType.getKey(target.getType()).toString();
            if (key.equals("pasterdream:aaroncos_left_hand") || key.equals("pasterdream:aaroncos_right_hand")) {
                if (target instanceof LivingEntity le) {
                    le.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 0, false, false));
                }
            }
            if (target instanceof Player player) {
                player.addEffect(new MobEffectInstance(ModEffects.OPPRESSION_BUFF.get(), 20, 0));
                player.removeEffect(MobEffects.DAMAGE_RESISTANCE);
            }
        }
    }

    private void sendMessageToNearbyPlayers(String msg) {
        Vec3 center = new Vec3(getX(), getY(), getZ());
        List<Player> players = level().getEntitiesOfClass(Player.class,
                new AABB(center, center).inflate(32), e -> true);
        for (Player player : players) {
            player.displayClientMessage(Component.literal(msg), true);
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (ANIMATION.equals(key)) {
            this.animationprocedure = this.entityData.get(ANIMATION);
        }
    }

    private float skillDamage(float ratio) {
        return (float) (getAttributeValue(Attributes.ATTACK_DAMAGE) * ratio);
    }

    public void setAnimation(String animation) {
        this.animationprocedure = animation;
        this.entityData.set(ANIMATION, animation);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(1);
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.MAX_HEALTH, 40)
                .add(Attributes.ARMOR, 5)
                .add(Attributes.ATTACK_DAMAGE, 20)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10);
    }

    private PlayState movementPredicate(software.bernie.geckolib.core.animation.AnimationState<ShadowTuneTotemEntity> event) {
        if (this.animationprocedure.equals("empty")) {
            if (this.isDeadOrDying()) {
                return event.setAndContinue(RawAnimation.begin().thenPlay("death"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(software.bernie.geckolib.core.animation.AnimationState<ShadowTuneTotemEntity> event) {
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
        if (this.deathTime == 20) {
            this.remove(RemovalReason.KILLED);
            this.dropExperience();
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
        data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
