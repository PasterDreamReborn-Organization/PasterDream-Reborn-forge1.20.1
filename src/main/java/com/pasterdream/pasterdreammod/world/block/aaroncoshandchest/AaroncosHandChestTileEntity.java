package com.pasterdream.pasterdreammod.world.block.aaroncoshandchest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.AdvancementHelper;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.dimension.AaroncosArenaWorldDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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

public class AaroncosHandChestTileEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean opened;
    private int totalTicks = -1;
    private int tickCounter = 0;
    private boolean hasTalentLight;
    private boolean hasTalentShadow;

    // 原作 achievement_talent_light（信仰光明，选灯）→ 白厄花胸针 + 白水晶
    private static final ResourceLocation TALENT_LIGHT_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/talent_light");
    // 原作 achievement_talent_shadow（暗影仆从，选影）→ 堕落者之印 + 暗影剑柄
    private static final ResourceLocation TALENT_SHADOW_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/talent_shadow");

    public AaroncosHandChestTileEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AARONCOS_HAND_CHEST.get(), pos, state);
    }

    /** 开箱（一次性）：开门动画 + 音效 + 粒子 + 掉落，41t 后自毁，之后延迟传送回主世界 */
    public void onUse(Player player) {
        if (opened || level == null || level.isClientSide())
            return;
        ServerLevel sw = (ServerLevel) level;
        opened = true;
        setChanged();
        BlockPos pos = worldPosition;
        BlockState state = getBlockState();
        if (state.hasProperty(AaroncosHandChestBlock.ANIMATION))
            level.setBlock(pos, state.setValue(AaroncosHandChestBlock.ANIMATION, 1), 3);
        level.playSound(null, pos, ModSounds.SHADOW_DOOR.get(), SoundSource.NEUTRAL, 1, 1);

        hasTalentLight = player instanceof ServerPlayer sp1 && AdvancementHelper.isDone(sp1, TALENT_LIGHT_ADV);
        hasTalentShadow = player instanceof ServerPlayer sp2 && AdvancementHelper.isDone(sp2, TALENT_SHADOW_ADV);

        // 启动方块实体开箱状态机（40t 掉落，41t 自毁）
        totalTicks = 410;
        tickCounter = 0;

        // 启动竞技场离场会话（倒计时提示 + 传回主世界 + 清理非玩家实体）
        AaroncosArenaWorldDimension.startExitSession(sw, pos);
    }

    /** 开箱状态机：由方块 getTicker 注册，每 tick 驱动 */
    public static void tick(Level level, BlockPos blockPosition, BlockState blockState, AaroncosHandChestTileEntity blockEntity) {
        if (level.isClientSide || blockEntity.totalTicks <= 0)
            return;
        ServerLevel sw = (ServerLevel) level;
        BlockPos pos = blockEntity.worldPosition;
        switch (blockEntity.tickCounter) {
            case 40 -> {
                double cx = pos.getX() + 0.5, cy = pos.getY() + 0.5, cz = pos.getZ() + 0.5;
                sw.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), cx, cy, cz, 64, 1, 1, 1, 0.2);
                sw.sendParticles(ParticleTypes.END_ROD, cx, cy, cz, 16, 1, 1, 1, 0.2);
                sw.sendParticles(ParticleTypes.SMOKE, cx, cy, cz, 24, 1, 1, 1, 0.2);
                sw.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(), cx, cy, cz, 32, 1, 1, 1, 0.2);
                if (blockEntity.hasTalentLight) {
                    dropItem(sw, cx, cy, cz, ModItems.BROOCH_OF_WHITE_ORCHID.get());
                    dropItem(sw, cx, cy, cz, ModItems.WHITE_CRYSTAL.get());
                }
                if (blockEntity.hasTalentShadow) {
                    dropItem(sw, cx, cy, cz, ModItems.SEAL_OF_THE_CORRUPTED.get());
                    dropItem(sw, cx, cy, cz, ModItems.SHADOW_HILT.get());
                }
                dropItem(sw, cx, cy, cz, ModItems.PURE_HORROR.get());
            }
            case 41 -> sw.destroyBlock(pos, false);
        }
        blockEntity.tickCounter++;
    }

    private static void dropItem(ServerLevel sw, double x, double y, double z, Item item) {
        ItemEntity drop = new ItemEntity(sw, x, y, z, new ItemStack(item));
        drop.setPickUpDelay(10);
        sw.addFreshEntity(drop);
    }

    private int getAnimationValue() {
        if (level == null)
            return 0;
        BlockState bs = getBlockState();
        if (bs.hasProperty(AaroncosHandChestBlock.ANIMATION))
            return bs.getValue(AaroncosHandChestBlock.ANIMATION);
        return 0;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "controller", 0, this::predicate));
        data.add(new AnimationController<>(this, "procedurecontroller", 0, this::procedurePredicate));
    }

    private PlayState predicate(AnimationState<AaroncosHandChestTileEntity> event) {
        if (getAnimationValue() == 0) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(AnimationState<AaroncosHandChestTileEntity> event) {
        int anim = getAnimationValue();
        if (anim != 0 && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay(String.valueOf(anim)));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                if (getBlockState().hasProperty(AaroncosHandChestBlock.ANIMATION))
                    level.setBlock(worldPosition, getBlockState().setValue(AaroncosHandChestBlock.ANIMATION, 0), 3);
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
        tag.putBoolean("switch", opened);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        opened = tag.getBoolean("switch");
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
