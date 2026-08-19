package com.pasterdream.pasterdreammod.world.block.aaroncoseye;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.AdvancementHelper;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.dimension.AaroncosArenaWorldDimension;
import com.pasterdream.pasterdreammod.world.entity.AaroncosLeftHandEntity;
import com.pasterdream.pasterdreammod.world.entity.AaroncosRightHandEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
    private double time0;

    // 原作 achievement_shadow_e_0（吹影镂尘），击败亚伦柯斯时授予
    private static final ResourceLocation DEFEAT_AARONCOS_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/defeat_aaroncos");

    public AaroncosEyeTileEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AARONCOS_EYE.get(), pos, state);
    }

    /** 激活（右键）：生成左右手 + 播放音乐 + 玩家改冒险模式 */
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
    }

    /** 战斗时间轴：time0 推进 + 波次生成 Terrorbeak + 双手死亡后放奖励箱并销毁自身 */
    public void onServerTick() {
        if (level == null || level.isClientSide() || !(level instanceof ServerLevel sw))
            return;
        if (!active)
            return;
        BlockPos pos = worldPosition;

        // time0 推进（每 20tick 自增 1），≥150 复位并循环音乐
        if (time0 >= 150) {
            time0 = 0;
            level.playSound(null, pos, ModSounds.AARONCOS_MUSIC.get(), SoundSource.WEATHER, 1, 1);
        } else {
            time0 += 1;
        }
        setChanged();

        // 50/100/150 各生成 2 只 Terrorbeak（z±12）
        if (time0 == 50 || time0 == 100 || time0 == 150) {
            spawnTerrorbeak(sw, pos.offset(0, 0, 12));
            spawnTerrorbeak(sw, pos.offset(0, 0, -12));
        }

        // 双手死亡（99 格范围内空）→ 放奖励箱 + 销毁自身（传送逻辑移至开箱后）
        AABB box = AABB.ofSize(Vec3.atCenterOf(pos), 99, 99, 99);
        boolean leftAlive = !level.getEntitiesOfClass(AaroncosLeftHandEntity.class, box, e -> true).isEmpty();
        boolean rightAlive = !level.getEntitiesOfClass(AaroncosRightHandEntity.class, box, e -> true).isEmpty();
        if (!leftAlive && !rightAlive) {
            level.setBlock(pos.offset(0, -1, 0), ModBlocks.AARONCOS_HAND_CHEST.get().defaultBlockState(), 3);
            level.destroyBlock(pos, false);
            for (Player p : level.players()) {
                if (p instanceof ServerPlayer sp)
                    AdvancementHelper.grant(sp, DEFEAT_AARONCOS_ADV, "defeat_aaroncos");
                p.removeEffect(ModEffects.SHADOW_SPYON_BUFF.get());
            }
        }
    }

    private static void spawnTerrorbeak(ServerLevel sw, BlockPos pos) {
        Entity beak = ModEntities.TERRORBEAK.get().spawn(sw, pos, MobSpawnType.MOB_SUMMONED);
        if (beak != null)
            beak.setYRot(sw.random.nextFloat() * 360F);
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
        tag.putDouble("time0", time0);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        active = tag.getBoolean("switch");
        time0 = tag.getDouble("time0");
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
