package com.pasterdream.pasterdreammod.world.entity.arrows;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviors;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;

/** 凝风箭——穿透 2 个实体、速度 +30%、自带冲击 I。 */
public class CongealWindArrow extends Arrow {

    /** 速度倍率（快 30%） */
    private static final float VELOCITY_MULTIPLIER = 1.3F;

    public CongealWindArrow(EntityType<? extends CongealWindArrow> type, Level level) {
        super(type, level);
        ArrowBehaviors.CONGEAL_WIND.applyBaseStats(this);
    }

    public CongealWindArrow(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.CONGEAL_WIND_ARROW.get(), level);
    }

    public CongealWindArrow(Level level, double x, double y, double z) {
        this(ModEntities.CONGEAL_WIND_ARROW.get(), level);
        this.setPos(x, y, z);
    }

    public CongealWindArrow(Level level, LivingEntity shooter) {
        this(ModEntities.CONGEAL_WIND_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity * VELOCITY_MULTIPLIER, inaccuracy);
    }

    @Override
    public void tick() {
        super.tick();
        ArrowBehaviors.CONGEAL_WIND.onTick(this, this.inGround);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.CONGEAL_WIND_ARROW.get());
    }
}
