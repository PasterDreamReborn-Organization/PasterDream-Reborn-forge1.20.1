package com.pasterdream.pasterdreammod.world.entity.arrows;

import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;

/** 黑石箭 —— 伤害为普通箭的 1.2 倍。 */
public class BlackstoneArrow extends Arrow {

    /** 伤害倍率（相对普通箭基础伤害） */
    private static final double DAMAGE_MULTIPLIER = 1.2;

    public BlackstoneArrow(EntityType<? extends BlackstoneArrow> type, Level level) {
        super(type, level);
        this.setBaseDamage(this.getBaseDamage() * DAMAGE_MULTIPLIER);
    }

    public BlackstoneArrow(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.BLACKSTONE_ARROW.get(), level);
    }

    public BlackstoneArrow(Level level, LivingEntity shooter) {
        this(ModEntities.BLACKSTONE_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }
}
