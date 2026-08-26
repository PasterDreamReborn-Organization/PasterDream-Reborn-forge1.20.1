package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.world.item.PotionBottleItem;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;

public class HighvoltageThundercloudEntity extends AbstractThundercloudEntity {

    public HighvoltageThundercloudEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.HIGHVOLTAGE_THUNDERCLOUD.get(), world);
    }

    public HighvoltageThundercloudEntity(EntityType<HighvoltageThundercloudEntity> type, Level world) {
        super(type, world);
        xpReward = 22;
    }

    @Override
    protected String getDefaultTexture() {
        return "highvoltage_thundercloud";
    }

    @Override
    protected float getDimensionScale() {
        return 2.7f;
    }

    @Override
    protected float getLightningDamage() {
        return 10;
    }

    @Override
    protected double getLightningAttackChance() {
        return 0.025;
    }

    @Override
    protected boolean isFireImmune() {
        return true;
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (!level().isClientSide()) {
            var effect = PotionBottleItem.getEffect(PotionBottleItem.TYPE_LIGHTNING);
            if (effect != null) {
                effect.onBottleBreak(ItemStack.EMPTY, level(), null, new Vec3(getX(), getY() - 4, getZ()));
            }
        }
    }

    public static void init() {
        SpawnPlacements.register(ModEntities.HIGHVOLTAGE_THUNDERCLOUD.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) ->
                        world.getDifficulty() != Difficulty.PEACEFUL
                                && Monster.isDarkEnoughToSpawn(world, pos, random)
                                && Mob.checkMobSpawnRules(entityType, world, reason, pos, random));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(Attributes.FLYING_SPEED, 0.15);
    }
}
