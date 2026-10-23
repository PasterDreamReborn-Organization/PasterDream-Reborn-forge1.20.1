package com.pasterdream.pasterdreammod.world.entity.arrows.behavior;

import com.pasterdream.pasterdreammod.helper.MinionFilterHelper;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 自定义箭矢行为注册表与通用工具。
 * <p>
 * 行为实例为无状态单例，任何依赖箭矢个体的状态（蓄力系数）都写入箭矢持久数据，
 * 因此同一个实例可同时服务于箭矢自身的实体类与武器强化拖尾箭。
 */
public final class ArrowBehaviors {

    /** 无额外行为的普通箭矢。 */
    public static final ArrowBehavior NONE = new ArrowBehavior() {
    };

    public static final ArrowBehavior BLACKSTONE = new BlackstoneBehavior();
    public static final ArrowBehavior TOXIC_SPORE = new ToxicSporeBehavior();
    public static final ArrowBehavior FRAGDREAM = new FragdreamBehavior();
    public static final ArrowBehavior MAGICAL = new MagicalBehavior();
    public static final ArrowBehavior CHARGED = new ChargedBehavior();

    /** 持久数据键：射出初速反推的蓄力系数（0.1 ~ 1.0）。 */
    private static final String TAG_CHARGE_FACTOR = "pasterdream:arrow_charge_factor";
    /** 满蓄力射速（弓为 power * 3.0，弩为 3.15）。 */
    private static final float FULL_CHARGE_VELOCITY = 3.0F;

    private ArrowBehaviors() {
    }

    /** 按弹药物品解析行为；非自定义箭矢返回 {@link #NONE}。 */
    public static ArrowBehavior get(ItemStack stack) {
        return stack.getItem() instanceof ArrowBehaviorProvider provider
                ? provider.arrowBehavior()
                : NONE;
    }

    /** 武器创建强化拖尾箭时复制弹药的基础属性。 */
    public static void applyBaseStats(AbstractArrow arrow, ItemStack ammo) {
        get(ammo).applyBaseStats(arrow);
    }

    /** 记录蓄力系数：初速 / 3.0，夹取到 [0.1, 1.0]。 */
    public static void markChargeFactor(AbstractArrow arrow, float velocity) {
        arrow.getPersistentData().putDouble(TAG_CHARGE_FACTOR,
                Mth.clamp(velocity / FULL_CHARGE_VELOCITY, 0.1F, 1.0F));
    }

    /** 读取蓄力系数；未记录时视为满蓄力。 */
    public static double chargeFactor(AbstractArrow arrow) {
        CompoundTag tag = arrow.getPersistentData();
        return tag.contains(TAG_CHARGE_FACTOR) ? tag.getDouble(TAG_CHARGE_FACTOR) : 1.0D;
    }

    /* ============================ 黑石箭 ============================ */

    /** 黑石箭：基础伤害为普通箭的 1.2 倍。 */
    private static final class BlackstoneBehavior implements ArrowBehavior {

        private static final double DAMAGE_MULTIPLIER = 1.2;

        @Override
        public void applyBaseStats(AbstractArrow arrow) {
            arrow.setBaseDamage(arrow.getBaseDamage() * DAMAGE_MULTIPLIER);
        }
    }

    /* ============================ 毒孢箭 ============================ */

    /** 毒孢箭：飞行时孢子粒子拖尾，命中施加剧毒。 */
    private static final class ToxicSporeBehavior implements ArrowBehavior {

        private static final int POISON_DURATION = 100;
        private static final int POISON_AMPLIFIER = 1;

        @Override
        public void onTick(AbstractArrow arrow, boolean inGround) {
            if (!arrow.level().isClientSide || inGround) {
                return;
            }
            if (arrow.tickCount % 2 != 0) {
                return;
            }
            Vec3 motion = arrow.getDeltaMovement();
            arrow.level().addParticle(ModParticleTypes.SPORE_PARTICLE.get(),
                    arrow.getX(), arrow.getY(), arrow.getZ(),
                    motion.x * 0.1, motion.y * 0.1, motion.z * 0.1);
        }

        @Override
        public void doPostHurtEffects(AbstractArrow arrow, LivingEntity target) {
            if (arrow.level().isClientSide) {
                return;
            }
            target.addEffect(new MobEffectInstance(ModEffects.HIGHLY_TOXIC.get(),
                    POISON_DURATION, POISON_AMPLIFIER, false, true));
        }
    }

    /* ============================ 碎梦箭 ============================ */

    /** 碎梦箭：命中音效 + 水晶碎裂粒子 + 范围伤害，命中方块后消散。 */
    private static final class FragdreamBehavior implements ArrowBehavior {

        private static final double AOE_RADIUS = 3.0;
        private static final float AOE_DAMAGE_MULTIPLIER = 2.0F;

        @Override
        public void onShoot(AbstractArrow arrow, float velocity) {
            markChargeFactor(arrow, velocity);
        }

        @Override
        public void onHitEntityPost(AbstractArrow arrow, EntityHitResult result) {
            Vec3 hit = result.getLocation();
            this.playImpactSound(arrow, hit);
            this.spawnShatterParticles(arrow, hit);
            this.applyAreaDamage(arrow, hit, result.getEntity());
        }

        @Override
        public void onHitBlock(AbstractArrow arrow, BlockHitResult result) {
            Vec3 hit = result.getLocation();
            this.playImpactSound(arrow, hit);
            this.spawnShatterParticles(arrow, hit);
            this.applyAreaDamage(arrow, hit, null);
            arrow.discard();
        }

        /** 击中音效：紫水晶碎裂。仅在服务端播放，由服务端广播给附近客户端，避免双响。 */
        private void playImpactSound(AbstractArrow arrow, Vec3 pos) {
            if (arrow.level().isClientSide) {
                return;
            }
            arrow.level().playSound(null, pos.x, pos.y, pos.z,
                    SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.BLOCKS, 0.8F, 1.0F);
        }

        /** 染梦水晶块碎裂粒子：以染梦水晶块破碎粒子模拟整块水晶崩裂。 */
        private void spawnShatterParticles(AbstractArrow arrow, Vec3 center) {
            if (!(arrow.level() instanceof ServerLevel serverLevel)) {
                return;
            }
            BlockState state = ModBlocks.DYEDREAM_BUD_BRICKS.get().defaultBlockState();
            BlockParticleOption option = new BlockParticleOption(ParticleTypes.BLOCK, state);
            serverLevel.sendParticles(option, center.x, center.y, center.z, 48, 0.4, 0.4, 0.4, 0.4);
        }

        /** 以箭落点为圆心，对半径内所有生物造成伤害；跳过射击者、其仆从/召唤物与被直接命中的目标。 */
        private void applyAreaDamage(AbstractArrow arrow, Vec3 center, Entity directTarget) {
            if (arrow.level().isClientSide) {
                return;
            }
            Entity owner = arrow.getOwner();
            Player ownerPlayer = owner instanceof Player player ? player : null;
            double radius = AOE_RADIUS * chargeFactor(arrow);
            AABB area = new AABB(
                    center.x - radius, center.y - radius, center.z - radius,
                    center.x + radius, center.y + radius, center.z + radius);
            DamageSource source = arrow.damageSources().arrow(arrow, owner);
            float damage = (float) (arrow.getBaseDamage() * AOE_DAMAGE_MULTIPLIER * chargeFactor(arrow));
            for (LivingEntity target : arrow.level().getEntitiesOfClass(LivingEntity.class, area)) {
                if (target == owner || target == directTarget
                        || MinionFilterHelper.isOwnedMinion(target, ownerPlayer)) {
                    continue;
                }
                target.hurt(source, damage);
            }
        }
    }

    /* ============================ 魔法箭 ============================ */

    /** 魔法箭：飞行时追踪附近目标。 */
    private static final class MagicalBehavior implements ArrowBehavior {

        private static final double HOMING_RANGE = 8.0;
        private static final double HOMING_STRENGTH = 0.25;

        @Override
        public void onTick(AbstractArrow arrow, boolean inGround) {
            if (arrow.level().isClientSide || inGround || arrow.isRemoved()) {
                return;
            }
            this.applyHoming(arrow);
        }

        /** 追踪：找到范围内最近目标，把速度方向朝它偏转，保持速度大小。 */
        private void applyHoming(AbstractArrow arrow) {
            LivingEntity target = this.findTarget(arrow);
            if (target == null) {
                return;
            }
            Vec3 velocity = arrow.getDeltaMovement();
            if (velocity.lengthSqr() < 1.0E-6) {
                return;
            }
            Vec3 toTarget = target.getBoundingBox().getCenter().subtract(arrow.position());
            if (toTarget.lengthSqr() < 1.0E-6) {
                return;
            }
            double speed = velocity.length();
            Vec3 currentDir = velocity.scale(1.0 / speed);
            Vec3 targetDir = toTarget.normalize();
            Vec3 newDir = currentDir.add(targetDir.subtract(currentDir).scale(HOMING_STRENGTH)).normalize();
            arrow.setDeltaMovement(newDir.scale(speed));
            arrow.hasImpulse = true;
        }

        /** 最近目标：排除射击者、其仆从/召唤物、创造玩家、旁观者。 */
        private LivingEntity findTarget(AbstractArrow arrow) {
            Entity owner = arrow.getOwner();
            Player ownerPlayer = owner instanceof Player player ? player : null;
            AABB area = arrow.getBoundingBox().inflate(HOMING_RANGE);
            List<LivingEntity> candidates = arrow.level().getEntitiesOfClass(LivingEntity.class, area,
                    e -> e.isAlive() && !e.isSpectator()
                            && e != owner
                            && !(e instanceof Player player && player.isCreative())
                            && !MinionFilterHelper.isOwnedMinion(e, ownerPlayer));
            LivingEntity best = null;
            double bestDist = Double.MAX_VALUE;
            for (LivingEntity candidate : candidates) {
                double dist = candidate.distanceToSqr(arrow);
                if (dist < bestDist) {
                    bestDist = dist;
                    best = candidate;
                }
            }
            return best;
        }
    }

    /* ============================ 充能箭 ============================ */

    /** 充能箭：先结算随蓄力缩放的魔法伤害，再走原版物理伤害；飞行带龙息粒子拖尾。 */
    private static final class ChargedBehavior implements ArrowBehavior {

        private static final float MAGIC_DAMAGE_MULTIPLIER = 1.5F;

        @Override
        public void onShoot(AbstractArrow arrow, float velocity) {
            markChargeFactor(arrow, velocity);
        }

        @Override
        public void onTick(AbstractArrow arrow, boolean inGround) {
            if (!arrow.level().isClientSide || inGround) {
                return;
            }
            if (arrow.tickCount % 2 != 0) {
                return;
            }
            Vec3 motion = arrow.getDeltaMovement();
            arrow.level().addParticle(ParticleTypes.DRAGON_BREATH,
                    arrow.getX(), arrow.getY(), arrow.getZ(),
                    motion.x * 0.1, motion.y * 0.1, motion.z * 0.1);
        }

        @Override
        public void onHitEntityPre(AbstractArrow arrow, EntityHitResult result) {
            if (arrow.level().isClientSide || !(result.getEntity() instanceof LivingEntity target)) {
                return;
            }
            float magicDamage = (float) (arrow.getBaseDamage() * MAGIC_DAMAGE_MULTIPLIER * chargeFactor(arrow));
            target.hurt(arrow.damageSources().indirectMagic(arrow, arrow.getOwner()), magicDamage);
            target.invulnerableTime = 0;
        }
    }
}
