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

/** 混沌箭——命中施加混乱、概率恢复理智、对暗影生物造成额外伤害。 */
public class ChaosicArrow extends Arrow {

    public ChaosicArrow(EntityType<? extends ChaosicArrow> type, Level level) {
        super(type, level);
    }

    public ChaosicArrow(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.CHAOSIC_ARROW.get(), level);
    }

    public ChaosicArrow(Level level, double x, double y, double z) {
        this(ModEntities.CHAOSIC_ARROW.get(), level);
        this.setPos(x, y, z);
    }

    public ChaosicArrow(Level level, LivingEntity shooter) {
        this(ModEntities.CHAOSIC_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        ArrowBehaviors.CHAOSIC.onShoot(this, velocity);
        super.shoot(x, y, z, velocity, inaccuracy);
    }

    @Override
    public void tick() {
        super.tick();
        ArrowBehaviors.CHAOSIC.onTick(this, this.inGround);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        ArrowBehaviors.CHAOSIC.onHitEntityPre(this, result);
        super.onHitEntity(result);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        ArrowBehaviors.CHAOSIC.doPostHurtEffects(this, target);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.CHAOSIC_ARROW.get());
    }
}
