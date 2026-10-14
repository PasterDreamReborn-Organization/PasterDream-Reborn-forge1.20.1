package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;

/**
 * 萦风箭 —— 萦风弓「萦风之箭」强化射击专用的箭实体。
 * <p>
 * 与原版 {@link net.minecraft.world.entity.projectile.Arrow} 行为一致（含药水箭效果、可拾取），区别有三：
 * <ul>
 *   <li>不受重力影响（{@link #setNoGravity(boolean)}），沿射出方向直线飞行；</li>
 *   <li>命中生物时使其飘浮 1s；额外雷电伤害（按最终伤害 30%）由 {@code FluffyWindBowHandler} 结算；</li>
 *   <li>客户端记录拖尾采样点，由 {@code ArrowTrailRenderer} 绘制青绿色渐变三棱柱拖尾
 *       （采样与颜色机制见基类 {@link TrailArrowEntity}）。</li>
 * </ul>
 * 因不受重力，箭矢可能长期不落地，故额外以飞行 tick 计数兜底清除，避免实体泄漏。
 */
public class FluffyWindArrowEntity extends TrailArrowEntity {

    /** 拖尾头端颜色（ARGB，青绿不透明） */
    private static final int HEAD_COLOR = 0xFF3FE8C4;
    /** 拖尾尾端颜色（ARGB，青绿透明） */
    private static final int TAIL_COLOR = 0x0032C79E;

    /** 命中施加的飘浮时长（tick），1 秒 */
    private static final int LEVITATION_DURATION = 20;
    /** 无重力下的最大飞行 tick 兜底，超时清除（不落地也不会永久残留） */
    private static final int MAX_FLIGHT_TICKS = 200;

    /** 持久数据键：本箭是否启用「引雷」落雷（附魔引雷时由弓在生成时写入） */
    private static final String NBT_LIGHTNING = "pasterdream:fluffy_wind_lightning";
    /** 持久数据键：火矢附魔等级（用于引雷落雷范围点燃） */
    private static final String NBT_FLAME = "pasterdream:fluffy_wind_flame";

    /** 飞行 tick 计数（仅服务端维护） */
    private int flightTicks;

    public FluffyWindArrowEntity(EntityType<? extends FluffyWindArrowEntity> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    public FluffyWindArrowEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.FLUFFY_WIND_ARROW.get(), level);
    }

    public FluffyWindArrowEntity(Level level, LivingEntity shooter) {
        this(ModEntities.FLUFFY_WIND_ARROW.get(), level);
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

    /** 生成时由萦风弓调用：开启「引雷」落雷，并记录火矢附魔等级（用于范围点燃）。 */
    public void enableChannelingLightning(int flameLevel) {
        CompoundTag tag = this.getPersistentData();
        tag.putBoolean(NBT_LIGHTNING, true);
        tag.putInt(NBT_FLAME, Math.max(0, flameLevel));
    }

    /** 本箭是否启用「引雷」落雷（由弓在生成时按附魔写入）。 */
    public boolean hasChannelingLightning() {
        return this.getPersistentData().getBoolean(NBT_LIGHTNING);
    }

    /** 火矢附魔等级（0 表示无）。 */
    public int channelingFlameLevel() {
        return this.getPersistentData().getInt(NBT_FLAME);
    }

    @Override
    public void tick() {
        super.tick();
        // 无重力箭矢若长期不命中就不会落地，原版落地计时（tickDespawn）不会触发，故按飞行时长兜底清除
        if (!this.level().isClientSide && !this.inGround && ++this.flightTicks > MAX_FLIGHT_TICKS) {
            this.discard();
        }
    }

    /** 命中生物后使其飘浮 1s；额外雷电伤害由 {@code FluffyWindBowHandler} 按最终伤害结算。 */
    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        if (this.level().isClientSide) {
            return;
        }
        target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, LEVITATION_DURATION, 0, false, true));
    }
}
