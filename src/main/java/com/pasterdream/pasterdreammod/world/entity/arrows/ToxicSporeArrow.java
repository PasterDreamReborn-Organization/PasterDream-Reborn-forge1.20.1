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

/** 毒孢箭——命中后使目标染上剧毒，飞行时带孢子粒子拖尾（同草薙毒刺）。 */
public class ToxicSporeArrow extends Arrow {

    public ToxicSporeArrow(EntityType<? extends ToxicSporeArrow> type, Level level) {
        super(type, level);
    }

    public ToxicSporeArrow(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.TOXIC_SPORE_ARROW.get(), level);
    }

    public ToxicSporeArrow(Level level, double x, double y, double z) {
        this(ModEntities.TOXIC_SPORE_ARROW.get(), level);
        this.setPos(x, y, z);
    }

    public ToxicSporeArrow(Level level, LivingEntity shooter) {
        this(ModEntities.TOXIC_SPORE_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    @Override
    public void tick() {
        super.tick();
        ArrowBehaviors.TOXIC_SPORE.onTick(this, this.inGround);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        ArrowBehaviors.TOXIC_SPORE.doPostHurtEffects(this, target);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.TOXIC_SPORE_ARROW.get());
    }
}
