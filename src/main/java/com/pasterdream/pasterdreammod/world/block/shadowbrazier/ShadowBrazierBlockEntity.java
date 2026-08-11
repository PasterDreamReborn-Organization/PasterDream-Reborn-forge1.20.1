package com.pasterdream.pasterdreammod.world.block.shadowbrazier;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class ShadowBrazierBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean lit;
    private int ritualTimer;

    public ShadowBrazierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHADOW_BRAZIER.get(), pos, state);
    }

    public boolean isLit() {
        return lit;
    }

    public void ignite() {
        if (lit) return;
        lit = true;
        ritualTimer = 0;
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            level.updateNeighborsAt(worldPosition, getBlockState().getBlock());
        }
    }

    public void onServerTick() {
        if (level == null || level.isClientSide() || !lit) return;

        ritualTimer++;

        int x = worldPosition.getX();
        int y = worldPosition.getY();
        int z = worldPosition.getZ();
        ServerLevel sw = (ServerLevel) level;

        // Animation: set to 1 (burning)
        BlockState bs = getBlockState();
        if (bs.hasProperty(ShadowBrazierBlock.ANIMATION) && bs.getValue(ShadowBrazierBlock.ANIMATION) != 1) {
            level.setBlock(worldPosition, bs.setValue(ShadowBrazierBlock.ANIMATION, 1), 3);
        }

        // t=1: darkness + opening sounds
        if (ritualTimer == 1) {
            Vec3 center = new Vec3(x + 0.5, y + 0.5, z + 0.5);
            List<Player> players = level.getEntitiesOfClass(Player.class,
                    new AABB(center, center).inflate(12.5));
            for (Player player : players) {
                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 0));
            }
            level.playSound(null, worldPosition, ModSounds.SHADOW0.get(), SoundSource.NEUTRAL, 1, 1);
            level.playSound(null, worldPosition, ModSounds.SHADOW_MUSIC_0.get(), SoundSource.NEUTRAL, 1, 1);
        }

        // t=6: 2 terrorbeaks
        if (ritualTimer == 6) {
            spawnEntity(sw, ModEntities.TERRORBEAK.get(), x + 7, y - 2, z);
            spawnEntity(sw, ModEntities.TERRORBEAK.get(), x - 7, y - 2, z);
        }

        // t=15: 4 shadow hands
        if (ritualTimer == 15) {
            spawnEntity(sw, ModEntities.SHADOW_HAND.get(), x, y, z + 7);
            spawnEntity(sw, ModEntities.SHADOW_HAND.get(), x, y, z - 7);
            spawnEntity(sw, ModEntities.SHADOW_HAND.get(), x + 7, y, z);
            spawnEntity(sw, ModEntities.SHADOW_HAND.get(), x - 7, y, z);
        }

        // t=40: shadow golem
        if (ritualTimer == 40) {
            spawnEntity(sw, ModEntities.SHADOW_GOLEM.get(), x, y - 2, z + 7);
        }

        // t=50: 4 shadow hands
        if (ritualTimer == 50) {
            spawnEntity(sw, ModEntities.SHADOW_HAND.get(), x, y, z + 7);
            spawnEntity(sw, ModEntities.SHADOW_HAND.get(), x, y, z - 7);
            spawnEntity(sw, ModEntities.SHADOW_HAND.get(), x + 7, y, z);
            spawnEntity(sw, ModEntities.SHADOW_HAND.get(), x - 7, y, z);
        }

        // t=65: 2 terrorbeaks
        if (ritualTimer == 65) {
            spawnEntity(sw, ModEntities.TERRORBEAK.get(), x + 7, y - 2, z);
            spawnEntity(sw, ModEntities.TERRORBEAK.get(), x - 7, y - 2, z);
        }

        // t=80: shadow golem
        if (ritualTimer == 80) {
            spawnEntity(sw, ModEntities.SHADOW_GOLEM.get(), x, y - 2, z - 7);
        }

        // t=95: 4 shadow hands
        if (ritualTimer == 95) {
            spawnEntity(sw, ModEntities.SHADOW_HAND.get(), x, y, z + 7);
            spawnEntity(sw, ModEntities.SHADOW_HAND.get(), x, y, z - 7);
            spawnEntity(sw, ModEntities.SHADOW_HAND.get(), x + 7, y, z);
            spawnEntity(sw, ModEntities.SHADOW_HAND.get(), x - 7, y, z);
        }

        // t=105: 2 terrorbeaks
        if (ritualTimer == 105) {
            spawnEntity(sw, ModEntities.TERRORBEAK.get(), x - 7, y - 2, z);
            spawnEntity(sw, ModEntities.TERRORBEAK.get(), x + 7, y - 2, z);
        }

        // t>=120: ritual complete
        if (ritualTimer >= 120) {
            Vec3 center = new Vec3(x + 0.5, y + 0.5, z + 0.5);
            List<Player> players = level.getEntitiesOfClass(Player.class,
                    new AABB(center, center).inflate(12.5));
            for (Player player : players) {
                player.displayClientMessage(
                        net.minecraft.network.chat.Component.translatable("message.pasterdream.shadow_brazier.extinguished"), false);
                player.displayClientMessage(
                        net.minecraft.network.chat.Component.translatable("message.pasterdream.shadow_brazier.key_dropped"), false);
            }

            level.destroyBlock(worldPosition, false);
            if (level instanceof ServerLevel serverLevel) {
                ItemEntity keyDrop = new ItemEntity(serverLevel, x + 0.5, y + 0.5, z + 0.5,
                        new ItemStack(ModItems.SHADOW_DUNGEON_KEY.get()));
                keyDrop.setPickUpDelay(10);
                serverLevel.addFreshEntity(keyDrop);

                ItemEntity grainDrop = new ItemEntity(serverLevel, x + 0.5, y + 0.5, z + 0.5,
                        new ItemStack(ModItems.BLACK_METAL_GRAIN.get()));
                grainDrop.setPickUpDelay(10);
                serverLevel.addFreshEntity(grainDrop);
            }
        }
    }

    private void spawnEntity(ServerLevel level, net.minecraft.world.entity.EntityType<?> type, int x, int y, int z) {
        var entity = type.spawn(level, new BlockPos(x, y, z), MobSpawnType.MOB_SUMMONED);
        if (entity != null) {
            entity.setYRot(level.getRandom().nextFloat() * 360F);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "controller", 0, this::predicate));
        data.add(new AnimationController<>(this, "procedurecontroller", 0, this::procedurePredicate));
    }

    private PlayState predicate(software.bernie.geckolib.core.animation.AnimationState<ShadowBrazierBlockEntity> event) {
        if (getAnimationValue() == 0) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(software.bernie.geckolib.core.animation.AnimationState<ShadowBrazierBlockEntity> event) {
        int anim = getAnimationValue();
        if (anim != 0 && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay(String.valueOf(anim)));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                BlockState bs = getBlockState();
                if (bs.hasProperty(ShadowBrazierBlock.ANIMATION))
                    level.setBlock(worldPosition, bs.setValue(ShadowBrazierBlock.ANIMATION, 0), 3);
                event.getController().forceAnimationReset();
            }
        } else if (anim == 0) {
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }

    private int getAnimationValue() {
        BlockState bs = getBlockState();
        if (bs.hasProperty(ShadowBrazierBlock.ANIMATION))
            return bs.getValue(ShadowBrazierBlock.ANIMATION);
        return 0;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("Lit", lit);
        tag.putInt("RitualTimer", ritualTimer);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        lit = tag.getBoolean("Lit");
        ritualTimer = tag.getInt("RitualTimer");
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
