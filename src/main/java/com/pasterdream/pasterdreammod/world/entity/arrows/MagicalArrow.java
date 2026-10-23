package com.pasterdream.pasterdreammod.world.entity.arrows;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.entity.TrailArrowEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;

/**
 * 魔法箭——飞行时追踪附近目标，带蓝色拖尾。
 * <p>
 * 追踪行为由弹药物品关联的 {@code ArrowBehaviors.MAGICAL} 提供，经 {@link TrailArrowEntity} 的通用钩子结算，
 * 因此被强化到自制武器的拖尾箭上时追踪仍然生效。
 */
public class MagicalArrow extends TrailArrowEntity {

    /** 拖尾头端颜色（ARGB，蓝色不透明）。 */
    private static final int HEAD_COLOR = 0xFF4D9FFF;
    /** 拖尾尾端颜色（ARGB，蓝色透明）。 */
    private static final int TAIL_COLOR = 0x004D9FFF;

    public MagicalArrow(EntityType<? extends MagicalArrow> type, Level level) {
        super(type, level);
    }

    public MagicalArrow(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.MAGICAL_ARROW.get(), level);
    }

    public MagicalArrow(Level level, LivingEntity shooter) {
        this(ModEntities.MAGICAL_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    @Override
    public int trailHeadColor() {
        return HEAD_COLOR;
    }

    @Override
    public int trailTailColor() {
        return TAIL_COLOR;
    }

    @Override
    protected ItemStack getPickupItem() {
        ItemStack ammo = this.getAmmoItem();
        return ammo.isEmpty() ? new ItemStack(ModItems.MAGICAL_ARROW.get()) : ammo.copy();
    }
}
