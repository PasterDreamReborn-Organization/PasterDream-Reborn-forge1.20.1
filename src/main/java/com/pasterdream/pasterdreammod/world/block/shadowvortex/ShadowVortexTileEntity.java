package com.pasterdream.pasterdreammod.world.block.shadowvortex;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
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

public class ShadowVortexTileEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean activated;
    private int timer;
    private boolean friendly;

    private static final TagKey<EntityType<?>> SHADOW_MOB = TagKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath("pasterdream", "shadow_mob"));
    private static final TagKey<EntityType<?>> SPECIAL_ENTITY = TagKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath("pasterdream", "special_entity_tag"));

    public ShadowVortexTileEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHADOW_VORTEX.get(), pos, state);
    }

    public void onServerTick() {
        if (level == null || level.isClientSide())
            return;

        if (timer >= 31) {
            level.setBlock(worldPosition, Blocks.AIR.defaultBlockState(), 3);
            return;
        }
        timer++;
        if (!activated) {
            activated = true;
        }

        ServerLevel sw = (ServerLevel) level;
        double cx = worldPosition.getX() + 0.5;
        double cy = worldPosition.getY();
        double cz = worldPosition.getZ() + 0.5;

        sw.sendParticles(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), cx, cy, cz, 64, 3, 0.5, 3, 0.1);
        sw.sendParticles(ParticleTypes.SMOKE, cx, cy, cz, 64, 3, 0.5, 3, 0.1);

        Vec3 center = new Vec3(cx, cy, cz);
        List<Entity> entities = level.getEntitiesOfClass(Entity.class,
                new AABB(center, center).inflate(4.5), e -> true);

        if (friendly) {
            for (Entity target : entities) {
                if (target.getType().is(SPECIAL_ENTITY) || target instanceof Player)
                    continue;
                target.hurt(new DamageSource(level.registryAccess()
                        .registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(DamageTypes.MAGIC)), 4);
                if (target instanceof LivingEntity le) {
                    le.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 0, false, false));
                }
            }
        } else {
            for (Entity target : entities) {
                if (target.getType().is(SPECIAL_ENTITY) || target.getType().is(SHADOW_MOB))
                    continue;
                if (target instanceof LivingEntity le) {
                    le.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 20, 0));
                    le.addEffect(new MobEffectInstance(ModEffects.CONFUSION_BUFF.get(), 20, 1));
                    le.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 0));
                }
                target.hurt(new DamageSource(level.registryAccess()
                        .registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(DamageTypes.MAGIC)), 3);
            }
        }
    }

    public void setFriendly(boolean friendly) {
        this.friendly = friendly;
    }

    private int getAnimationValue() {
        if (level == null)
            return 0;
        BlockState bs = getBlockState();
        if (bs.getBlock() instanceof ShadowVortexBlock && bs.hasProperty(ShadowVortexBlock.ANIMATION))
            return bs.getValue(ShadowVortexBlock.ANIMATION);
        return 0;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "controller", 0, this::predicate));
        data.add(new AnimationController<>(this, "procedurecontroller", 0, this::procedurePredicate));
    }

    private PlayState predicate(software.bernie.geckolib.core.animation.AnimationState<ShadowVortexTileEntity> event) {
        if (getAnimationValue() == 0) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(software.bernie.geckolib.core.animation.AnimationState<ShadowVortexTileEntity> event) {
        int anim = getAnimationValue();
        if (anim != 0 && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay(String.valueOf(anim)));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                if (getBlockState().hasProperty(ShadowVortexBlock.ANIMATION))
                    level.setBlock(worldPosition, getBlockState().setValue(ShadowVortexBlock.ANIMATION, 0), 3);
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
        tag.putBoolean("Activated", activated);
        tag.putInt("Timer", timer);
        tag.putBoolean("Friendly", friendly);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        activated = tag.getBoolean("Activated");
        timer = tag.getInt("Timer");
        friendly = tag.getBoolean("Friendly");
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
