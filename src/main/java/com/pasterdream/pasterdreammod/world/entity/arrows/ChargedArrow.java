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
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.PlayMessages;

/** 充能箭——命中后造成两次伤害：先魔法伤害、后原版物理伤害，魔法伤害随蓄力缩放，飞行带龙息粒子拖尾。 */
public class ChargedArrow extends Arrow {

    public ChargedArrow(EntityType<? extends ChargedArrow> type, Level level) {
        super(type, level);
    }

    public ChargedArrow(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.CHARGED_ARROW.get(), level);
    }

    public ChargedArrow(Level level, LivingEntity shooter) {
        this(ModEntities.CHARGED_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        ArrowBehaviors.CHARGED.onShoot(this, velocity);
        super.shoot(x, y, z, velocity, inaccuracy);
    }

    @Override
    public void tick() {
        super.tick();
        ArrowBehaviors.CHARGED.onTick(this, this.inGround);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        ArrowBehaviors.CHARGED.onHitEntityPre(this, result);
        super.onHitEntity(result);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.CHARGED_ARROW.get());
    }
}
