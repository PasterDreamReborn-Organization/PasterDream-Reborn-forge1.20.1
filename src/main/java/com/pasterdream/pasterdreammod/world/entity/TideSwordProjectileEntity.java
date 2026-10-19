package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import javax.annotation.Nullable;

/**
 * 『北海若』引潮 的投掷弹射物。
 * <p>
 * 复刻原版 {@code ThrownTrident} 的全部行为（忠诚飞回、引雷落雷、穿刺/附魔伤害、拾取），
 * 但以「剑的物品模型」渲染（见 {@code TideSwordProjectileRenderer}），而非三叉戟模型。
 * 由 {@code BeihaiRuoTideSwordItem} 普通右键蓄力投掷生成。
 */
public class TideSwordProjectileEntity extends AbstractArrow {

    private static final EntityDataAccessor<Byte> ID_LOYALTY =
            SynchedEntityData.defineId(TideSwordProjectileEntity.class, EntityDataSerializers.BYTE);
    /** 展示/拾取用物品栈：需同步到客户端，供渲染器与拾取逻辑取用（保留真实附魔）。 */
    private static final EntityDataAccessor<ItemStack> DATA_DISPLAY_ITEM =
            SynchedEntityData.defineId(TideSwordProjectileEntity.class, EntityDataSerializers.ITEM_STACK);

    private static final float BASE_DAMAGE = 8.0F; // 原版三叉戟投掷基础伤害

    private boolean dealtDamage;
    public int clientSideReturnTickCount;

    public TideSwordProjectileEntity(EntityType<? extends TideSwordProjectileEntity> type, Level level) {
        super(type, level);
    }

    public TideSwordProjectileEntity(Level level, LivingEntity owner, ItemStack stack) {
        super(ModEntities.TIDE_SWORD_PROJECTILE.get(), owner, level);
        this.setDisplayItem(stack);
        this.entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(stack));
    }

    public TideSwordProjectileEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(ModEntities.TIDE_SWORD_PROJECTILE.get(), level);
    }

    /** 写入展示物品栈，供渲染与拾取使用。 */
    private void setDisplayItem(ItemStack stack) {
        this.entityData.set(DATA_DISPLAY_ITEM, stack.copy());
    }

    private static ItemStack defaultDisplayItem() {
        return new ItemStack(ModItems.BEIHAI_RUO_TIDE_SWORD.get());
    }

    /** 供渲染器取用的展示物品（含真实附魔，已同步到客户端）。 */
    public ItemStack getDisplayItem() {
        return this.entityData.get(DATA_DISPLAY_ITEM);
    }

    public boolean isChanneling() {
        return EnchantmentHelper.hasChanneling(this.getDisplayItem());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_LOYALTY, (byte) 0);
        this.entityData.define(DATA_DISPLAY_ITEM, defaultDisplayItem());
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity owner = this.getOwner();
        int loyalty = this.entityData.get(ID_LOYALTY);
        if (loyalty > 0 && (this.dealtDamage || this.isNoPhysics()) && owner != null) {
            if (!this.isAcceptibleReturnOwner()) {
                if (!this.level().isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }
                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 toOwner = owner.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + toOwner.y * 0.015 * loyalty, this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }
                double speed = 0.05 * loyalty;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add(toOwner.normalize().scale(speed)));
                if (this.clientSideReturnTickCount == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }
                this.clientSideReturnTickCount++;
            }
        }

        super.tick();
    }

    private boolean isAcceptibleReturnOwner() {
        Entity owner = this.getOwner();
        if (owner == null || !owner.isAlive()) {
            return false;
        }
        return !(owner instanceof ServerPlayer) || !((ServerPlayer) owner).isSpectator();
    }

    @Override
    protected ItemStack getPickupItem() {
        return this.getDisplayItem().copy();
    }

    @Nullable
    @Override
    protected EntityHitResult findHitEntity(Vec3 from, Vec3 to) {
        return this.dealtDamage ? null : super.findHitEntity(from, to);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity hit = result.getEntity();
        float damage = BASE_DAMAGE;
        if (hit instanceof LivingEntity living) {
            damage += EnchantmentHelper.getDamageBonus(this.getDisplayItem(), living.getMobType());
        }

        Entity owner = this.getOwner();
        DamageSource source = this.damageSources().trident(this, owner == null ? this : owner);
        this.dealtDamage = true;
        SoundEvent sound = SoundEvents.TRIDENT_HIT;
        if (hit.hurt(source, damage)) {
            if (hit.getType() == EntityType.ENDERMAN) {
                // 闪避成功也会返回 true，提前退出前仍需减速，供忠诚正常回收。
                this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
                return;
            }
            if (hit instanceof LivingEntity living) {
                if (owner instanceof LivingEntity ownerLiving) {
                    EnchantmentHelper.doPostHurtEffects(living, ownerLiving);
                    EnchantmentHelper.doPostDamageEffects(ownerLiving, living);
                }
                this.doPostHurtEffects(living);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        float volume = 1.0F;
        if (this.level() instanceof ServerLevel && this.level().isThundering() && this.isChanneling()) {
            BlockPos pos = hit.blockPosition();
            if (this.level().canSeeSky(pos)) {
                LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(this.level());
                if (bolt != null) {
                    bolt.moveTo(Vec3.atBottomCenterOf(pos));
                    bolt.setCause(owner instanceof ServerPlayer sp ? sp : null);
                    this.level().addFreshEntity(bolt);
                    sound = SoundEvents.TRIDENT_THUNDER;
                    volume = 5.0F;
                }
            }
        }

        this.playSound(sound, volume, 1.0F);
    }

    @Override
    protected boolean tryPickup(Player player) {
        return super.tryPickup(player)
                || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public void playerTouch(Player player) {
        if (this.ownedBy(player) || this.getOwner() == null) {
            super.playerTouch(player);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Sword", 10)) {
            this.setDisplayItem(ItemStack.of(tag.getCompound("Sword")));
        }
        this.dealtDamage = tag.getBoolean("DealtDamage");
        this.entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(this.getDisplayItem()));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Sword", this.getDisplayItem().save(new CompoundTag()));
        tag.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    protected void tickDespawn() {
        int loyalty = this.entityData.get(ID_LOYALTY);
        if (this.pickup != AbstractArrow.Pickup.ALLOWED || loyalty <= 0) {
            super.tickDespawn();
        }
    }

    @Override
    protected float getWaterInertia() {
        return 0.99F;
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
