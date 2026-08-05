package com.pasterdream.pasterdreammod.world.block.twilightlantern;

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

public class TwilightLanternBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean eventSwitch;
    private boolean key;
    private double number;
    private int eventTick;

    public TwilightLanternBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TWILIGHT_LANTERN.get(), pos, state);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::idleController));
        controllers.add(new AnimationController<>(this, "procedurecontroller", 0, this::procedureController));
    }

    private PlayState idleController(AnimationState<TwilightLanternBlockEntity> state) {
        int anim = getBlockState().getValue(TwilightLanternBlock.ANIMATION);
        if (anim == 0) {
            state.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private PlayState procedureController(AnimationState<TwilightLanternBlockEntity> state) {
        int anim = getBlockState().getValue(TwilightLanternBlock.ANIMATION);
        if (anim != 0 && state.getController().getAnimationState() == AnimationController.State.STOPPED) {
            state.getController().setAnimation(RawAnimation.begin().thenPlay(String.valueOf(anim)));
            if (state.getController().getAnimationState() == AnimationController.State.STOPPED) {
                if (level != null) {
                    level.setBlock(getBlockPos(), getBlockState().setValue(TwilightLanternBlock.ANIMATION, 0), 3);
                }
                state.getController().forceAnimationReset();
            }
        } else if (anim == 0) {
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }

    public boolean isEventSwitch() {
        return eventSwitch;
    }

    public void setEventSwitch(boolean eventSwitch) {
        this.eventSwitch = eventSwitch;
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public boolean isKey() {
        return key;
    }

    public void setKey(boolean key) {
        this.key = key;
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
        setChanged();
    }

    public int getEventTick() {
        return eventTick;
    }

    public void setEventTick(int eventTick) {
        this.eventTick = eventTick;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("switch", eventSwitch);
        tag.putBoolean("key", key);
        tag.putDouble("number", number);
        tag.putInt("eventTick", eventTick);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        eventSwitch = tag.getBoolean("switch");
        key = tag.getBoolean("key");
        number = tag.getDouble("number");
        eventTick = tag.getInt("eventTick");
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putBoolean("switch", eventSwitch);
        tag.putBoolean("key", key);
        tag.putDouble("number", number);
        tag.putInt("eventTick", eventTick);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
