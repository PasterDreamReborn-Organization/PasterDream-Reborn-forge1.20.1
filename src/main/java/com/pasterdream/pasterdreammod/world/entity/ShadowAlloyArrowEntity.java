package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;

/**
 * 暗影合金弩专用的箭实体。
 * <p>
 * 行为与原版 {@link net.minecraft.world.entity.projectile.Arrow} 完全一致（普通物理伤害、可拾取），
 * 仅用于让客户端可识别弩箭并绘制暗紫色拖尾（见基类 {@link TrailArrowEntity}）。
 * <p>
 * 命中效果（易伤 + 随机负面）由 {@code ShadowAlloyCrossbowHandler} 通过箭矢持久数据标记结算，
 * 与本实体类型无强绑定。
 */
public class ShadowAlloyArrowEntity extends TrailArrowEntity {

    /** 拖尾头端颜色（ARGB，暗紫不透明） */
    private static final int HEAD_COLOR = 0xFF8E24AA;
    /** 拖尾尾端颜色（ARGB，暗紫透明） */
    private static final int TAIL_COLOR = 0x008E24AA;

    public ShadowAlloyArrowEntity(EntityType<? extends ShadowAlloyArrowEntity> type, Level level) {
        super(type, level);
    }

    public ShadowAlloyArrowEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.SHADOW_ALLOY_ARROW.get(), level);
    }

    public ShadowAlloyArrowEntity(Level level, LivingEntity shooter) {
        this(ModEntities.SHADOW_ALLOY_ARROW.get(), level);
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
}
