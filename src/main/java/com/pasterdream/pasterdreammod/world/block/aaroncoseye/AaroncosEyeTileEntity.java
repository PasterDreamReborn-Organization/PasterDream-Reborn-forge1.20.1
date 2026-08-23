package com.pasterdream.pasterdreammod.world.block.aaroncoseye;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.dimension.AaroncosArenaWorldDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
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

public class AaroncosEyeTileEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean active;

    public AaroncosEyeTileEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AARONCOS_EYE.get(), pos, state);
    }

    /** 激活（右键）：生成左右手 + 播放音乐 + 玩家改冒险模式，随后移交战斗会话并销毁自身（眼在场 = 未开战） */
    public void onUse(Player player) {
        if (level == null || level.isClientSide() || !(level instanceof ServerLevel sw))
            return;
        if (!level.dimension().equals(AaroncosArenaWorldDimension.AARONCOS_ARENA_WORLD))
            return;
        if (active)
            return;
        active = true;
        setChanged();
        BlockPos pos = worldPosition;
        Entity left = ModEntities.AARONCOS_LEFT_HAND.get().spawn(sw, pos.offset(12, 0, 0), MobSpawnType.MOB_SUMMONED);
        if (left != null)
            left.setYRot(sw.random.nextFloat() * 360F);
        Entity right = ModEntities.AARONCOS_RIGHT_HAND.get().spawn(sw, pos.offset(-12, 0, 0), MobSpawnType.MOB_SUMMONED);
        if (right != null)
            right.setYRot(sw.random.nextFloat() * 360F);
        level.playSound(null, pos, ModSounds.AARONCOS_MUSIC.get(), SoundSource.WEATHER, 1, 1);
        for (Player p : level.players()) {
            if (p instanceof ServerPlayer sp)
                sp.setGameMode(GameType.ADVENTURE);
        }
        // 战斗时间轴移交 AaroncosArenaWorldDimension 按 tick 驱动，自身销毁
        AaroncosArenaWorldDimension.startBattle(sw, pos);
        level.destroyBlock(pos, false);
    }

    private int getAnimationValue() {
        if (level == null)
            return 0;
        BlockState bs = getBlockState();
        if (bs.hasProperty(AaroncosEyeBlock.ANIMATION))
            return bs.getValue(AaroncosEyeBlock.ANIMATION);
        return 0;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "controller", 0, this::predicate));
        data.add(new AnimationController<>(this, "procedurecontroller", 0, this::procedurePredicate));
    }

    private PlayState predicate(AnimationState<AaroncosEyeTileEntity> event) {
        if (getAnimationValue() == 0) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(AnimationState<AaroncosEyeTileEntity> event) {
        int anim = getAnimationValue();
        if (anim != 0 && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay(String.valueOf(anim)));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                if (getBlockState().hasProperty(AaroncosEyeBlock.ANIMATION))
                    level.setBlock(worldPosition, getBlockState().setValue(AaroncosEyeBlock.ANIMATION, 0), 3);
                event.getController().forceAnimationReset();
            }
        } else if (anim == 0) {
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("switch", active);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        active = tag.getBoolean("switch");
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
