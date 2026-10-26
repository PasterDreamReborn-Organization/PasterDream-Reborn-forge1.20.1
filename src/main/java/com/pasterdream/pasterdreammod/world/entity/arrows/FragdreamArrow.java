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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.PlayMessages;

/** 碎梦箭——命中后发出紫水晶音效；命中物块或实体时对半径 3 格内实体造成范围伤害，击中物块后消失。 */
public class FragdreamArrow extends Arrow {

    public FragdreamArrow(EntityType<? extends FragdreamArrow> type, Level level) {
        super(type, level);
    }

    public FragdreamArrow(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.FRAGDREAM_ARROW.get(), level);
    }

    public FragdreamArrow(Level level, double x, double y, double z) {
        this(ModEntities.FRAGDREAM_ARROW.get(), level);
        this.setPos(x, y, z);
    }

    public FragdreamArrow(Level level, LivingEntity shooter) {
        this(ModEntities.FRAGDREAM_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        ArrowBehaviors.FRAGDREAM.onShoot(this, velocity);
        super.shoot(x, y, z, velocity, inaccuracy);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        ArrowBehaviors.FRAGDREAM.onHitEntityPost(this, result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        ArrowBehaviors.FRAGDREAM.onHitBlock(this, result);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.FRAGDREAM_ARROW.get());
    }
}
