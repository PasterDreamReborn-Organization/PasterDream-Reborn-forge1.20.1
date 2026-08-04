package com.pasterdream.pasterdreammod.world.block.lifecrystal;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class LifeCrystalBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int animationState = 0;
    @Nullable
    private UUID absorbingPlayer;

    public LifeCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LIFE_CRYSTAL.get(), pos, state);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::idleController));
        controllers.add(new AnimationController<>(this, "procedurecontroller", 0, this::procedureController));
    }

    private PlayState idleController(AnimationState<LifeCrystalBlockEntity> state) {
        if (animationState == 0) {
            state.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private PlayState procedureController(AnimationState<LifeCrystalBlockEntity> state) {
        if (animationState != 0 && state.getController().getAnimationState() == AnimationController.State.STOPPED) {
            state.getController().setAnimation(RawAnimation.begin().thenPlay(String.valueOf(animationState)));
            if (state.getController().getAnimationState() == AnimationController.State.STOPPED) {
                animationState = 0;
                state.getController().forceAnimationReset();
            }
        } else if (animationState == 0) {
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }

    public void setAnimationState(int state) {
        this.animationState = state;
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public int getAnimationState() {
        return animationState;
    }

    public void setAbsorbingPlayer(UUID uuid) {
        this.absorbingPlayer = uuid;
    }

    @Nullable
    public UUID getAbsorbingPlayer() {
        return absorbingPlayer;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("AnimationState", animationState);
        if (absorbingPlayer != null) {
            tag.putUUID("AbsorbingPlayer", absorbingPlayer);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        animationState = tag.getInt("AnimationState");
        if (tag.hasUUID("AbsorbingPlayer")) {
            absorbingPlayer = tag.getUUID("AbsorbingPlayer");
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("AnimationState", animationState);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
