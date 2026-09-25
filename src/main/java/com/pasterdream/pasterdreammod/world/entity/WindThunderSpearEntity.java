package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.WindwreathedThunderSpearItem;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 萦风雷矛的投掷弹射物实体（虚影）。
 * <p>
 * 投掷时本体仍留在投掷者手中，此实体只是「虚影」：飞行命中生物/方块后对命中目标造成雷电伤害
 * → 触发「连锁闪电」（向最近敌人跳跃、逐跳衰减）→ 自动飞回投掷者并解除其投掷冷却。
 * 由 {@code WindwreathedThunderSpearItem} 的 shift+右键蓄力投掷生成。
 */
public class WindThunderSpearEntity extends AbstractArrow implements GeoEntity {

    // ===== 连锁闪电 =====
    private static final int CHAIN_JUMPS = 4;             // 最大跳跃次数（基础）
    private static final float CHAIN_DECAY = 0.75F;       // 每跳伤害衰减
    private static final double CHAIN_RANGE = 4.0;        // 每跳索敌半径
    private static final double CHAIN_DAMAGE_RATIO = 0.6; // 连锁伤害 = 投掷伤害 × 0.6（基础）

    // 引雷为原版 1 级，按「是否附魔」计；附魔引雷后，锋利与引雷共同提供雷伤加成（含连锁次数）
    private static final double CHANNELING_LIGHTNING_BONUS = 0.2;    // 引雷：雷伤 +20%
    private static final double SHARPNESS_LIGHTNING_PER_LEVEL = 0.1; // 锋利每级：雷伤 +10%
    private static final int CHANNELING_CHAIN_JUMPS = 1;             // 引雷：连锁次数 +1
    private static final int SHARPNESS_PER_CHAIN_JUMP = 2;           // 每 2 级锋利：连锁次数 +1

    // ===== 附魔加成 =====
    private static final float SMITE_BANE_DAMAGE = 2.5F;      // 亡灵/节肢杀手每级额外伤害
    private static final int FIRE_ASPECT_TICK_MULTIPLIER = 4; // 火焰附加秒数乘数

    private static final double RETURN_SPEED = 0.45;      // 飞回速度
    private static final double RETURN_SNAP_DISTANCE = 1.5; // 飞回吸附距离

    private ItemStack spearItem;
    private boolean returning;
    private boolean dealtDamage;
    private float attackDamage;
    private int smite;
    private int baneOfArthropods;
    private int fireAspect;
    private UUID ownerUUID;
    private transient LivingEntity cachedOwner;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public WindThunderSpearEntity(EntityType<? extends WindThunderSpearEntity> type, Level level) {
        super(type, level);
        this.spearItem = new ItemStack(ModItems.WINDWREATHED_THUNDER_SPEAR.get());
    }

    public WindThunderSpearEntity(Level level, LivingEntity owner, ItemStack stack) {
        super(ModEntities.WIND_THUNDER_SPEAR.get(), owner, level);
        this.spearItem = stack.copy();
        this.cachedOwner = owner;
        this.ownerUUID = owner.getUUID();
        this.pickup = Pickup.DISALLOWED;
    }

    public WindThunderSpearEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.WIND_THUNDER_SPEAR.get(), level);
    }

    /** 由物品在投掷时调用：写入伤害与附魔加成。 */
    public void init(Player owner, float attackDamage, int smite, int baneOfArthropods, int fireAspect) {
        this.cachedOwner = owner;
        this.ownerUUID = owner.getUUID();
        this.attackDamage = attackDamage;
        this.smite = smite;
        this.baneOfArthropods = baneOfArthropods;
        this.fireAspect = fireAspect;
    }

    /** 解析投掷者（重登/跨存档后 cachedOwner 可能为空，回退到 ownerUUID），保证伤害有击杀归属。 */
    @Nullable
    private Player resolveOwner() {
        if (this.cachedOwner instanceof Player p && !p.isRemoved()) {
            return p;
        }
        if (this.getOwner() instanceof Player p) {
            this.cachedOwner = p;
            return p;
        }
        if (this.ownerUUID != null && this.level() instanceof ServerLevel sl) {
            Entity e = sl.getEntity(this.ownerUUID);
            if (e instanceof Player p) {
                this.cachedOwner = p;
                return p;
            }
        }
        return null;
    }

    @Override
    protected ItemStack getPickupItem() {
        return this.spearItem.copy();
    }

    @Override
    protected boolean tryPickup(Player player) {
        return false;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Nullable
    @Override
    protected EntityHitResult findHitEntity(Vec3 from, Vec3 to) {
        return this.dealtDamage ? null : super.findHitEntity(from, to);
    }

    /** 仆从防护：投掷物穿过自己/同队的仆从与召唤物，不造成伤害 */
    @Override
    protected boolean canHitEntity(Entity entity) {
        if (!super.canHitEntity(entity)) return false;
        if (entity instanceof LivingEntity living && this.getOwner() instanceof Player player) {
            return !isOwnedMinion(living, player);
        }
        return true;
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide && !this.returning
                && this.tickCount > WindwreathedThunderSpearItem.getThrowCooldownTicks()) {
            // 兜底：虚影长时间未命中（如投空）也主动飞回，避免残留
            this.returning = true;
        }
        if (this.returning && !this.level().isClientSide) {
            this.setNoPhysics(true);
            Entity owner = this.getOwner();
            if (owner == null || !owner.isAlive()) {
                // 本体始终留在投掷者手中，虚影丢失时直接消失即可，不掉落物品
                this.discard();
                return;
            }
            Vec3 toOwner = owner.getEyePosition().subtract(this.position());
            if (toOwner.lengthSqr() < RETURN_SNAP_DISTANCE * RETURN_SNAP_DISTANCE) {
                returnToOwner(owner);
                this.discard();
                return;
            }
            this.setDeltaMovement(this.getDeltaMovement().scale(0.8).add(toOwner.normalize().scale(RETURN_SPEED)));
        }
        super.tick();
    }

    @Override
    protected void tickDespawn() {
        if (!this.returning) {
            super.tickDespawn();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        this.dealtDamage = true;
        this.returning = true;
        if (this.level().isClientSide) {
            return;
        }
        if (!(result.getEntity() instanceof LivingEntity target)) {
            return;
        }
        Player owner = resolveOwner();
        float dmg = this.attackDamage;
        if (this.smite > 0 && target.getMobType() == MobType.UNDEAD) dmg += this.smite * SMITE_BANE_DAMAGE;
        if (this.baneOfArthropods > 0 && target.getMobType() == MobType.ARTHROPOD) dmg += this.baneOfArthropods * SMITE_BANE_DAMAGE;
        if (this.fireAspect > 0 && !target.isOnFire()) target.setSecondsOnFire(1);
        target.invulnerableTime = 0;
        target.hurt(lightningDamageSource(target, owner), dmg);
        if (this.fireAspect > 0) target.setSecondsOnFire(this.fireAspect * FIRE_ASPECT_TICK_MULTIPLIER);

        chainLightning(target, owner);
        tryChanneling(target, owner);
        this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
    }

    /** 引雷附魔：命中时在目标处召唤「萦风引雷」式效果雷（纯视觉落雷 + 范围雷伤），无天气/可见天空限制。 */
    private void tryChanneling(LivingEntity target, @Nullable Player owner) {
        if (owner == null) return;
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.CHANNELING, this.spearItem) <= 0) return;
        if (!(this.level() instanceof ServerLevel sl)) return;

        float strikeDamage = (float) (this.attackDamage * (1.0 + lightningBonus()));
        WindAlloyLightningEntity lightning = ModEntities.WIND_ALLOY_LIGHTNING.get().create(sl);
        if (lightning != null) {
            lightning.setStrikes(1); // 雷矛引雷为单道落雷
            lightning.init(owner, target, strikeDamage, target.position(),
                    this.smite, this.baneOfArthropods, this.fireAspect);
            lightning.moveTo(target.getX(), target.getY(), target.getZ());
            sl.addFreshEntity(lightning);
        }
    }

    /** 雷伤加成：仅当附魔引雷时生效，由引雷 + 锋利等级共同提供。 */
    private double lightningBonus() {
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.CHANNELING, this.spearItem) <= 0) {
            return 0.0;
        }
        int sharpness = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, this.spearItem);
        return CHANNELING_LIGHTNING_BONUS + sharpness * SHARPNESS_LIGHTNING_PER_LEVEL;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.dealtDamage = true;
        this.returning = true;
    }

    // ==================== 连锁闪电 ====================

    private void chainLightning(LivingEntity first, @Nullable Player owner) {
        if (!(this.level() instanceof ServerLevel sl)) return;
        // 引雷附魔：雷伤加成（引雷 + 锋利）与连锁次数加成
        boolean channeling = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.CHANNELING, this.spearItem) > 0;
        int sharpness = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, this.spearItem);
        int maxJumps = CHAIN_JUMPS + (channeling
                ? CHANNELING_CHAIN_JUMPS + sharpness / SHARPNESS_PER_CHAIN_JUMP : 0);
        double ratio = CHAIN_DAMAGE_RATIO + lightningBonus();
        Set<UUID> hitIds = new HashSet<>();
        hitIds.add(first.getUUID());
        LivingEntity source = first;
        float dmg = (float) (this.attackDamage * ratio);
        for (int jump = 0; jump < maxJumps; jump++) {
            LivingEntity next = findChainTarget(sl, source, hitIds, owner);
            if (next == null) break;
            hitIds.add(next.getUUID());
            next.invulnerableTime = 0;
            next.hurt(lightningDamageSource(next, owner), dmg);
            spawnChainParticles(sl, source, next);
            dmg *= CHAIN_DECAY;
            source = next;
        }
    }

    @Nullable
    private LivingEntity findChainTarget(ServerLevel sl, LivingEntity from, Set<UUID> hitIds, @Nullable Player owner) {
        List<LivingEntity> candidates = sl.getEntitiesOfClass(LivingEntity.class,
                from.getBoundingBox().inflate(CHAIN_RANGE),
                e -> e != owner && e.isAlive() && !hitIds.contains(e.getUUID()) && !isOwnedMinion(e, owner));
        LivingEntity best = null;
        double bestDist = Double.MAX_VALUE;
        for (LivingEntity candidate : candidates) {
            double dist = candidate.distanceToSqr(from);
            if (dist < bestDist) {
                bestDist = dist;
                best = candidate;
            }
        }
        return best;
    }

    private void spawnChainParticles(ServerLevel sl, LivingEntity from, LivingEntity to) {
        Vec3 a = from.position().add(0, from.getBbHeight() * 0.5, 0);
        Vec3 b = to.position().add(0, to.getBbHeight() * 0.5, 0);
        int steps = Math.max(2, (int) (a.distanceTo(b) * 3));
        for (int i = 0; i <= steps; i++) {
            Vec3 p = a.lerp(b, (double) i / steps);
            sl.sendParticles(ParticleTypes.ELECTRIC_SPARK, p.x, p.y, p.z, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    /** 构造以雷矛为直接实体、玩家为施伤者的雷电伤害源（保留击杀归属，并可被战利品处理识别为雷矛击杀） */
    private DamageSource lightningDamageSource(LivingEntity target, @Nullable Player owner) {
        Holder<DamageType> holder = target.level().registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(DamageTypes.LIGHTNING_BOLT);
        return new DamageSource(holder, this, owner);
    }

    /** 是否为玩家自己的仆从/召唤物/同队盟友（连锁不应命中） */
    private static boolean isOwnedMinion(LivingEntity e, @Nullable Player owner) {
        if (owner == null) return false;
        if (e instanceof OwnableEntity ownable) {
            return ownable.getOwner() == owner;
        }
        if (e instanceof FoxFireEntity fe) {
            return fe.resolveOwner() == owner;
        }
        return e.isAlliedTo(owner);
    }

    // ==================== 飞回 ====================

    private void returnToOwner(Entity owner) {
        if (!(owner instanceof Player player)) return;
        // 本体仍留在玩家手中：虚影回归时只需解除投掷冷却
        WindwreathedThunderSpearItem.clearThrowCooldown(player, this.spearItem);
        this.playSound(SoundEvents.TRIDENT_RETURN, 1.0F, 1.0F);
    }

    // ==================== 序列化 ====================

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("SpearItem", this.spearItem.save(new CompoundTag()));
        tag.putBoolean("Returning", this.returning);
        tag.putBoolean("DealtDamage", this.dealtDamage);
        tag.putFloat("AttackDamage", this.attackDamage);
        tag.putInt("Smite", this.smite);
        tag.putInt("BaneOfArthropods", this.baneOfArthropods);
        tag.putInt("FireAspect", this.fireAspect);
        if (this.ownerUUID != null) tag.putString("OwnerUUID", this.ownerUUID.toString());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("SpearItem", 10)) {
            this.spearItem = ItemStack.of(tag.getCompound("SpearItem"));
        }
        this.returning = tag.getBoolean("Returning");
        this.dealtDamage = tag.getBoolean("DealtDamage");
        this.attackDamage = tag.getFloat("AttackDamage");
        this.smite = tag.getInt("Smite");
        this.baneOfArthropods = tag.getInt("BaneOfArthropods");
        this.fireAspect = tag.getInt("FireAspect");
        if (tag.contains("OwnerUUID")) this.ownerUUID = UUID.fromString(tag.getString("OwnerUUID"));
    }
}
