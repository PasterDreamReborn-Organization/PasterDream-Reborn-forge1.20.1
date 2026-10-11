package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;

/**
 * 融梦箭 —— 融梦弓「融梦箭」强化射击专用的箭实体。
 * <p>
 * 行为与原版 {@code Arrow} 一致（含药水箭效果、可拾取），区别有二：
 * 一是伤害类型为魔法伤害（见 {@link #damageSources()}）；
 * 二是客户端记录拖尾采样点，由 {@code ArrowTrailRenderer} 绘制粉色渐变三棱柱拖尾
 * （采样与颜色机制见基类 {@link TrailArrowEntity}）。
 * 因只在强化射击时生成，实体本身即代表「拖尾开启」，无需额外同步标记。
 */
public class MeltDreamArrowEntity extends TrailArrowEntity {

    /** 拖尾头端颜色（ARGB，亮粉不透明） */
    private static final int HEAD_COLOR = 0xFFFF8AD8;
    /** 拖尾尾端颜色（ARGB，粉透明） */
    private static final int TAIL_COLOR = 0x00FF55B0;

    /** 魔法伤害来源表（首次命中时按维度注册表构建后缓存） */
    private DamageSources magicDamageSources;

    public MeltDreamArrowEntity(EntityType<? extends MeltDreamArrowEntity> type, Level level) {
        super(type, level);
    }

    public MeltDreamArrowEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.MELT_DREAM_ARROW.get(), level);
    }

    public MeltDreamArrowEntity(Level level, LivingEntity shooter) {
        this(ModEntities.MELT_DREAM_ARROW.get(), level);
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

    /**
     * 强化箭造成的是魔法伤害。仅覆盖 {@link DamageSources#arrow} 的返回类型：
     * 有射击者时归因到射击者（{@code indirect_magic}，可享受魔法伤害加成与击杀归属），
     * 无射击者时为无源魔法伤害。命中、药水效果、可拾取、暴击、击退等其余逻辑均沿用原版箭。
     */
    @Override
    public DamageSources damageSources() {
        if (this.magicDamageSources == null) {
            this.magicDamageSources = new MagicArrowDamageSources(this.level().registryAccess());
        }
        return this.magicDamageSources;
    }

    /** 将箭矢伤害来源重定向为魔法伤害的来源表。 */
    private static final class MagicArrowDamageSources extends DamageSources {

        private MagicArrowDamageSources(RegistryAccess registryAccess) {
            super(registryAccess);
        }

        @Override
        public DamageSource arrow(AbstractArrow arrow, Entity owner) {
            return owner == null || owner == arrow
                    ? this.magic()
                    : this.indirectMagic(arrow, owner);
        }
    }
}
