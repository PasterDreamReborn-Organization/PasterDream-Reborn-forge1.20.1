package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.PotionBottleItem;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ThrownPotionBottle extends ThrowableItemProjectile {

    /** 落地后的 tick 计数，-1 表示仍在飞行 */
    private int landedTicks = -1;
    /** 延迟动作的最大 tick（用于判断何时移除） */
    private int maxDelayedTick = 0;
    /** 延迟动作映射：tick → 动作 */
    private final Map<Integer, Runnable> delayedActions = new HashMap<>();

    public ThrownPotionBottle(EntityType<? extends ThrownPotionBottle> type, Level level) {
        super(type, level);
    }

    public ThrownPotionBottle(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.THROWN_POTION_BOTTLE.get(), level);
    }

    public ThrownPotionBottle(Level level, LivingEntity shooter) {
        super(ModEntities.THROWN_POTION_BOTTLE.get(), shooter, level);
    }

    public ThrownPotionBottle(Level level, double x, double y, double z) {
        super(ModEntities.THROWN_POTION_BOTTLE.get(), x, y, z, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.POTION_BOTTLE.get();
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            for (int i = 0; i < 12; i++) {
                this.level().addParticle(
                        new ItemParticleOption(ParticleTypes.ITEM, this.getItem()),
                        this.getX(), this.getY(), this.getZ(),
                        ((double) this.random.nextFloat() - 0.5D) * 0.16D,
                        ((double) this.random.nextFloat() - 0.5D) * 0.16D,
                        ((double) this.random.nextFloat() - 0.5D) * 0.16D);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        Vec3 hitPos = result.getLocation();

        // 玻璃破碎音效 + 粒子（双端可见）
        this.level().playSound(null, hitPos.x, hitPos.y, hitPos.z,
                SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, 0.8F,
                0.9F + this.random.nextFloat() * 0.2F);
        this.level().broadcastEntityEvent(this, (byte) 3);

        if (!this.level().isClientSide) {
            ItemStack stack = this.getItem();
            String type = PotionBottleItem.getPotionType(stack);

            if (!type.isEmpty()) {
                PotionBottleItem.PotionBottleEffect effect = PotionBottleItem.getEffect(type);
                if (effect != null && this.getOwner() instanceof LivingEntity thrower) {
                    // 立即效果
                    effect.onBottleBreak(stack, this.level(), thrower, hitPos);
                    // 获取延迟动作
                    Map<Integer, Runnable> delayed = effect.getDelayedActions();
                    if (!delayed.isEmpty()) {
                        delayedActions.putAll(delayed);
                        maxDelayedTick = Collections.max(delayedActions.keySet());
                        landedTicks = 0;
                    }
                }
            }
        }

        if (landedTicks < 0) {
            // 无延迟动作，立即移除
            this.discard();
        } else {
            // 有延迟动作，停在命中位置等待调度
            this.setDeltaMovement(Vec3.ZERO);
            this.setNoGravity(true);
            this.noPhysics = true;
        }
    }

    @Override
    public void tick() {
        if (landedTicks >= 0) {
            landedTicks++;
            if (!this.level().isClientSide) {
                Runnable action = delayedActions.get(landedTicks);
                if (action != null) {
                    action.run();
                }
                if (landedTicks >= maxDelayedTick) {
                    this.discard();
                    return;
                }
            }
            this.setDeltaMovement(Vec3.ZERO);
        }
        super.tick();
    }
}
