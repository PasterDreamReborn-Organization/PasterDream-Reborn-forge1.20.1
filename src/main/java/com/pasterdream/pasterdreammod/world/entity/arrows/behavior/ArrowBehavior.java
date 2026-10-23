package com.pasterdream.pasterdreammod.world.entity.arrows.behavior;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

/**
 * 自定义箭矢的行为钩子。
 * <p>
 * 箭矢的特殊行为（伤害倍率、命中效果、飞行粒子、追踪等）集中在此接口的实现里，
 * 由自定义箭实体与武器强化拖尾箭（{@code TrailArrowEntity} 及其子类）共同复用。
 * 这样萦风弓 / 融梦弓 / 暗影合金弩发射自制箭矢时，既能保留武器强化，也能保留箭矢自身特性。
 * <p>
 * 所有钩子默认空实现：普通箭矢（原版箭、药水箭、光谱箭）解析为 {@link ArrowBehaviors#NONE}，无额外行为。
 */
public interface ArrowBehavior {

    /** 创建实体时应用基础属性（如黑石箭的伤害倍率）。 */
    default void applyBaseStats(AbstractArrow arrow) {
    }

    /** 射出时记录与初速相关的状态（如碎梦箭 / 充能箭的蓄力系数）。 */
    default void onShoot(AbstractArrow arrow, float velocity) {
    }

    /** 每 tick 行为。{@code inGround} 由实体传入，避免访问受保护的落地字段。 */
    default void onTick(AbstractArrow arrow, boolean inGround) {
    }

    /** 原版命中结算之前调用（如充能箭先结算魔法伤害再走物理伤害）。 */
    default void onHitEntityPre(AbstractArrow arrow, EntityHitResult result) {
    }

    /** 原版命中结算之后调用（如碎梦箭的范围伤害）。 */
    default void onHitEntityPost(AbstractArrow arrow, EntityHitResult result) {
    }

    /** 命中方块之后调用（如碎梦箭命中方块后消散）。 */
    default void onHitBlock(AbstractArrow arrow, BlockHitResult result) {
    }

    /** 原版药水效果结算之后调用（如毒孢箭的剧毒）。 */
    default void doPostHurtEffects(AbstractArrow arrow, LivingEntity target) {
    }
}
