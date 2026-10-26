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

/** 黑石箭——伤害为普通箭的 1.2 倍。 */
public class BlackstoneArrow extends Arrow {

    public BlackstoneArrow(EntityType<? extends BlackstoneArrow> type, Level level) {
        super(type, level);
        ArrowBehaviors.BLACKSTONE.applyBaseStats(this);
    }

    public BlackstoneArrow(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.BLACKSTONE_ARROW.get(), level);
    }

    public BlackstoneArrow(Level level, double x, double y, double z) {
        this(ModEntities.BLACKSTONE_ARROW.get(), level);
        this.setPos(x, y, z);
    }

    public BlackstoneArrow(Level level, LivingEntity shooter) {
        this(ModEntities.BLACKSTONE_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.BLACKSTONE_ARROW.get());
    }
}
