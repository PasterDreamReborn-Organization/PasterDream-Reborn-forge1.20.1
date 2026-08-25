package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * 萦风引雷的落雷载体实体 —— 由萦风合金剑（雷模式）生成。
 * 每 10 tick 在目标头顶降下一道纯视觉落雷（setVisualOnly），
 * 并造成 攻击力×1.5 的 4×3×4 范围 AOE 雷电伤害，共 5 道后消散。
 * 通过每 tick 重新读取目标位置实现"追踪"。
 */
public class WindAlloyLightningEntity extends Entity {

    private static final int STRIKES = 5;            // 落雷数量
    private static final int STRIKE_INTERVAL = 10;   // 每道落雷之间的间隔(tick)
    private static final double AOE_XZ = 2.0;        // AOE 半宽/半深（总宽4格）
    private static final double AOE_Y = 1.5;         // AOE 半高（总高3格）
    private static final float SMITE_BANE_DAMAGE = 2.5f;         // 亡灵杀手/节肢杀手每级额外伤害
    private static final int FIRE_ASPECT_TICK_MULTIPLIER = 4;    // 火焰附加秒数乘数

    private int ticks = 0;
    private int strikeIndex = 0;
    private float attackDamage;
    private int smite;
    private int baneOfArthropods;
    private int fireAspect;
    private UUID ownerUUID;
    private transient LivingEntity cachedOwner;
    private UUID targetUUID;
    private transient LivingEntity cachedTarget;
    private Vec3 fallbackPos;
    private Vec3 lastStrikePos;

    public WindAlloyLightningEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public WindAlloyLightningEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.WIND_ALLOY_LIGHTNING.get(), level);
    }

    public WindAlloyLightningEntity(Level level, double x, double y, double z) {
        this(ModEntities.WIND_ALLOY_LIGHTNING.get(), level);
        this.setPos(x, y, z);
    }

    public void init(Player owner, LivingEntity target, float attackDamage, Vec3 fallbackPos,
                     int smite, int baneOfArthropods, int fireAspect) {
        this.cachedOwner = owner;
        this.ownerUUID = owner.getUUID();
        if (target != null) {
            this.cachedTarget = target;
            this.targetUUID = target.getUUID();
        }
        this.attackDamage = attackDamage;
        this.fallbackPos = fallbackPos;
        this.smite = smite;
        this.baneOfArthropods = baneOfArthropods;
        this.fireAspect = fireAspect;
    }

    @Override
    public boolean isPushable() { return false; }

    @Override
    public boolean isPickable() { return false; }

    @Override
    public boolean hurt(net.minecraft.world.damagesource.DamageSource s, float a) { return false; }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        ticks = tag.getInt("Ticks");
        strikeIndex = tag.getInt("StrikeIndex");
        attackDamage = tag.getFloat("AttackDamage");
        smite = tag.getInt("Smite");
        baneOfArthropods = tag.getInt("BaneOfArthropods");
        fireAspect = tag.getInt("FireAspect");
        if (tag.contains("OwnerUUID")) ownerUUID = UUID.fromString(tag.getString("OwnerUUID"));
        if (tag.contains("TargetUUID")) targetUUID = UUID.fromString(tag.getString("TargetUUID"));
        if (tag.contains("FallbackX")) {
            fallbackPos = new Vec3(tag.getDouble("FallbackX"), tag.getDouble("FallbackY"), tag.getDouble("FallbackZ"));
        }
        if (tag.contains("LastX")) {
            lastStrikePos = new Vec3(tag.getDouble("LastX"), tag.getDouble("LastY"), tag.getDouble("LastZ"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Ticks", ticks);
        tag.putInt("StrikeIndex", strikeIndex);
        tag.putFloat("AttackDamage", attackDamage);
        tag.putInt("Smite", smite);
        tag.putInt("BaneOfArthropods", baneOfArthropods);
        tag.putInt("FireAspect", fireAspect);
        if (ownerUUID != null) tag.putString("OwnerUUID", ownerUUID.toString());
        if (targetUUID != null) tag.putString("TargetUUID", targetUUID.toString());
        if (fallbackPos != null) {
            tag.putDouble("FallbackX", fallbackPos.x);
            tag.putDouble("FallbackY", fallbackPos.y);
            tag.putDouble("FallbackZ", fallbackPos.z);
        }
        if (lastStrikePos != null) {
            tag.putDouble("LastX", lastStrikePos.x);
            tag.putDouble("LastY", lastStrikePos.y);
            tag.putDouble("LastZ", lastStrikePos.z);
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) return;
        if (!(this.level() instanceof ServerLevel sl)) return;

        // 追踪：每 tick 重新读取目标位置
        LivingEntity target = resolveTarget();
        if (target != null) {
            this.setPos(target.getX(), target.getY() + target.getBbHeight() * 0.9, target.getZ());
        } else if (lastStrikePos != null) {
            this.setPos(lastStrikePos.x, lastStrikePos.y, lastStrikePos.z);
        }

        if (ticks % STRIKE_INTERVAL == 0 && strikeIndex < STRIKES) {
            strike(sl, target);
            strikeIndex++;
        }
        ticks++;

        if (strikeIndex >= STRIKES && ticks > STRIKES * STRIKE_INTERVAL) {
            this.discard();
        }
    }

    private void strike(ServerLevel sl, LivingEntity target) {
        Vec3 strikePos;
        if (target != null) {
            strikePos = new Vec3(target.getX(), target.getY() + target.getBbHeight() * 0.9, target.getZ());
        } else if (lastStrikePos != null) {
            strikePos = lastStrikePos;
        } else if (fallbackPos != null) {
            strikePos = fallbackPos;
        } else {
            strikePos = this.position();
        }
        lastStrikePos = strikePos;

        // 纯视觉落雷（不造成原版伤害/火）
        LightningBolt bolt = net.minecraft.world.entity.EntityType.LIGHTNING_BOLT.create(sl);
        if (bolt != null) {
            bolt.moveTo(strikePos);
            bolt.setVisualOnly(true);
            if (cachedOwner instanceof Player pl) {
                bolt.setCause((net.minecraft.server.level.ServerPlayer) pl);
            }
            sl.addFreshEntity(bolt);
        }

        sl.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                strikePos.x, strikePos.y, strikePos.z, 24, 2.0, 0.5, 2.0, 0.0);

        // 4×3×4 AOE 雷电伤害
        AABB area = new AABB(
                strikePos.x - AOE_XZ, strikePos.y - AOE_Y, strikePos.z - AOE_XZ,
                strikePos.x + AOE_XZ, strikePos.y + AOE_Y, strikePos.z + AOE_XZ);
        Player ownerPlayer = cachedOwner instanceof Player pl ? pl : null;
        List<LivingEntity> entities = sl.getEntitiesOfClass(LivingEntity.class, area,
                e -> e != cachedOwner && e.isAlive() && !isOwnedMinion(e, ownerPlayer));
        for (LivingEntity e : entities) {
            e.invulnerableTime = 0;
            float dmg = attackDamage;
            if (smite > 0 && e.getMobType() == MobType.UNDEAD) dmg += smite * SMITE_BANE_DAMAGE;
            if (baneOfArthropods > 0 && e.getMobType() == MobType.ARTHROPOD) dmg += baneOfArthropods * SMITE_BANE_DAMAGE;
            if (ownerPlayer != null) {
                e.hurt(e.level().damageSources().playerAttack(ownerPlayer), dmg);
            } else {
                e.hurt(e.level().damageSources().lightningBolt(), dmg);
            }
            if (fireAspect > 0) e.setSecondsOnFire(fireAspect * FIRE_ASPECT_TICK_MULTIPLIER);
        }
    }

    /** 是否为玩家自己的仆从/召唤物/同队盟友（战技不应命中） */
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

    private LivingEntity resolveTarget() {
        if (cachedTarget != null && cachedTarget.isAlive()) {
            return cachedTarget;
        }
        if (targetUUID != null && this.level() instanceof ServerLevel sl) {
            Entity entity = sl.getEntity(targetUUID);
            if (entity instanceof LivingEntity le && le.isAlive()) {
                cachedTarget = le;
                return le;
            }
        }
        cachedTarget = null;
        return null;
    }
}
