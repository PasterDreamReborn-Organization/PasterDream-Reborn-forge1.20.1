package com.pasterdream.pasterdreammod.world.block.shadowdungeonportal;

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

public class ShadowDungeonPortalTileEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private boolean cd;
    private int time;
    private boolean exit;
    private int layer1;
    private int layer2;
    private int layer3;
    private int layer4;
    private int layer5;

    public ShadowDungeonPortalTileEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHADOW_DUNGEON_PORTAL.get(), pos, state);
    }

    // --- accessors ---

    public boolean isCd() { return cd; }
    public void setCd(boolean cd) { this.cd = cd; setChanged(); }
    public int getTime() { return time; }
    public void setTime(int time) { this.time = time; setChanged(); }
    public boolean isExit() { return exit; }
    public void setExit(boolean exit) { this.exit = exit; setChanged(); }
    public int getLayer1() { return layer1; }
    public void setLayer1(int layer1) { this.layer1 = layer1; setChanged(); }
    public int getLayer2() { return layer2; }
    public void setLayer2(int layer2) { this.layer2 = layer2; setChanged(); }
    public int getLayer3() { return layer3; }
    public void setLayer3(int layer3) { this.layer3 = layer3; setChanged(); }
    public int getLayer4() { return layer4; }
    public void setLayer4(int layer4) { this.layer4 = layer4; setChanged(); }
    public int getLayer5() { return layer5; }
    public void setLayer5(int layer5) { this.layer5 = layer5; setChanged(); }

    // --- animation ---

    private int getAnimationValue() {
        if (level == null) return 0;
        BlockState bs = getBlockState();
        if (bs.hasProperty(ShadowDungeonPortalBlock.ANIMATION))
            return bs.getValue(ShadowDungeonPortalBlock.ANIMATION);
        return 0;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "controller", 0, this::predicate));
        data.add(new AnimationController<>(this, "procedurecontroller", 0, this::procedurePredicate));
    }

    private PlayState predicate(AnimationState<ShadowDungeonPortalTileEntity> event) {
        if (getAnimationValue() == 0) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(AnimationState<ShadowDungeonPortalTileEntity> event) {
        int anim = getAnimationValue();
        if (anim != 0 && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay(String.valueOf(anim)));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                if (getBlockState().hasProperty(ShadowDungeonPortalBlock.ANIMATION))
                    level.setBlock(worldPosition, getBlockState().setValue(ShadowDungeonPortalBlock.ANIMATION, 0), 3);
                event.getController().forceAnimationReset();
            }
        } else if (anim == 0) {
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    // --- NBT ---

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("Cd", cd);
        tag.putInt("Time", time);
        tag.putBoolean("Exit", exit);
        tag.putInt("Layer1", layer1);
        tag.putInt("Layer2", layer2);
        tag.putInt("Layer3", layer3);
        tag.putInt("Layer4", layer4);
        tag.putInt("Layer5", layer5);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        cd = tag.getBoolean("Cd");
        time = tag.getInt("Time");
        exit = tag.getBoolean("Exit");
        layer1 = tag.getInt("Layer1");
        layer2 = tag.getInt("Layer2");
        layer3 = tag.getInt("Layer3");
        layer4 = tag.getInt("Layer4");
        layer5 = tag.getInt("Layer5");
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
