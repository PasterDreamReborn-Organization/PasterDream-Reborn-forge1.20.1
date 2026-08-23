package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.nbt.CompoundTag;

import java.util.UUID;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class NamelessEntity extends PathfinderMob implements GeoEntity {
    public enum DialoguePhase {
        NONE, FIRST, SECOND
    }

    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(NamelessEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(NamelessEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";

    /** 对话状态机：当前阶段、下一行行号、距下一行剩余 tick、对话玩家 */
    private DialoguePhase dialoguePhase = DialoguePhase.NONE;
    private int dialogueLine = 1;
    private int dialogueTickLeft = 0;
    private UUID dialoguePlayer;

    public NamelessEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.NAMELESS.get(), world);
    }

    public NamelessEntity(EntityType<NamelessEntity> type, Level world) {
        super(type, world);
        xpReward = 0;
        setNoAi(false);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "nameless");
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
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.GENERIC_DEATH;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.GENERIC_KILL))
            return super.hurt(source, amount);
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
        compound.putString("DialoguePhase", this.dialoguePhase.name());
        compound.putInt("DialogueLine", this.dialogueLine);
        compound.putInt("DialogueTickLeft", this.dialogueTickLeft);
        if (this.dialoguePlayer != null)
            compound.putUUID("DialoguePlayer", this.dialoguePlayer);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture"))
            this.setTexture(compound.getString("Texture"));
        if (compound.contains("DialoguePhase")) {
            try {
                this.dialoguePhase = DialoguePhase.valueOf(compound.getString("DialoguePhase"));
            } catch (IllegalArgumentException e) {
                this.dialoguePhase = DialoguePhase.NONE;
            }
        }
        if (compound.contains("DialogueLine"))
            this.dialogueLine = compound.getInt("DialogueLine");
        if (compound.contains("DialogueTickLeft"))
            this.dialogueTickLeft = compound.getInt("DialogueTickLeft");
        if (compound.hasUUID("DialoguePlayer"))
            this.dialoguePlayer = compound.getUUID("DialoguePlayer");
    }

    @Override
    public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
        InteractionResult retval = InteractionResult.sidedSuccess(this.level().isClientSide());
        super.mobInteract(sourceentity, hand);
        if (!this.level().isClientSide() && sourceentity instanceof ServerPlayer serverPlayer) {
            NamelessDialogueHandler.onInteract(this, serverPlayer);
        }
        return retval;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.refreshDimensions();
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale((float) 1);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
        if (!this.level().isClientSide() && this.dialoguePhase != DialoguePhase.NONE) {
            if (--this.dialogueTickLeft <= 0) {
                this.advanceDialogue();
            }
        }
    }

    /** 开始一段对话，下一 tick 立即发送第一行 */
    public void startDialogue(ServerPlayer player, DialoguePhase phase) {
        this.dialoguePlayer = player.getUUID();
        this.dialoguePhase = phase;
        this.dialogueLine = 1;
        this.dialogueTickLeft = 0;
    }

    private void advanceDialogue() {
        ServerPlayer player = this.getDialoguePlayer();
        DialoguePhase phase = this.dialoguePhase;
        if (player == null) {
            // 玩家已下线，中止对话
            this.dialoguePhase = DialoguePhase.NONE;
            return;
        }
        int totalLines = phase == DialoguePhase.FIRST
                ? NamelessDialogueHandler.FIRST_LINES : NamelessDialogueHandler.SECOND_LINES;
        if (this.dialogueLine <= totalLines) {
            NamelessDialogueHandler.sendLine(player, NamelessDialogueHandler.lineKey(phase, this.dialogueLine));
            this.dialogueLine++;
            this.dialogueTickLeft = NamelessDialogueHandler.LINE_INTERVAL;
        } else {
            this.dialoguePhase = DialoguePhase.NONE;
            NamelessDialogueHandler.finishDialogue(this, player, phase);
        }
    }

    private ServerPlayer getDialoguePlayer() {
        if (this.dialoguePlayer == null || this.level().getServer() == null)
            return null;
        return this.level().getServer().getPlayerList().getPlayer(this.dialoguePlayer);
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.FOLLOW_RANGE, 16);
    }

    private PlayState movementPredicate(AnimationState<NamelessEntity> event) {
        if (this.animationprocedure.equals("empty")) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(AnimationState<NamelessEntity> event) {
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
        data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
