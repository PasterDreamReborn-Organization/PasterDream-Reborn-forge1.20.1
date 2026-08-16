package com.pasterdream.pasterdreammod.world.item.whiteswordtool;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.entity.WhiteSwordRainProjectileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import com.pasterdream.pasterdreammod.world.item.IndestructibleItemEntity;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillLockHelper;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

public class WhiteSwordItem extends SwordItem {

    private static final double ENERGY_COST = 1.5; // 主动技能消耗的融梦能量
    private static final int COOLDOWN_TICKS = 40; // 主动技能冷却时间(tick)
    private static final double PASSIVE_CHANCE = 0.5; // 被动触发概率
    private static final int PASSIVE_PROJECTILE_COUNT = 6; // 被动触发弹射物数量
    private static final double PASSIVE_SPREAD = 2.5; // 被动弹射物生成散布范围
    private static final double ACTIVE_PROJECTILE_SPEED = 1.5; // 主动技能弹射物速度
    private static final double PASSIVE_PROJECTILE_SPEED = 1.5; // 被动触发弹射物速度
    private static final double ACTIVE_DAMAGE_RATIO = 0.15; // 主动技能伤害系数(基于攻击力)
    private static final double PASSIVE_DAMAGE_RATIO = 0.1; // 被动触发伤害系数(基于攻击力)
    private static final double SWEEPING_EDGE_SPREAD_BONUS = 0.5; // 横扫之刃每级增加散布范围

    public WhiteSwordItem(Tier tier, int damage, float speed) {
        super(tier, damage, speed, new Properties().fireResistant().rarity(ModRarities.LEGENDARY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hand == InteractionHand.OFF_HAND) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        ItemStack stack = player.getItemInHand(hand);
        if (SkillLockHelper.isSkillLocked(player)) return InteractionResultHolder.fail(stack);
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            // TODO: Check for advancement achievement_talent_light once advancement system is ported
            if (player.isCreative() || checkEnergy(serverPlayer)) {
                if (!player.isCreative()) {
                    MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(serverPlayer, -ENERGY_COST);
                }
                executeSkill(level, player);
            } else {
                player.displayClientMessage(Component.translatable("message.pasterdream.white_sword.no_energy"), true);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private boolean checkEnergy(ServerPlayer player) {
        return MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(player) >= ENERGY_COST;
    }

    @Nullable
    private static LivingEntity findEntityAtCrosshair(Level level, Player player, Vec3 eye, Vec3 far) {
        AABB searchBox = new AABB(eye, far).inflate(1.0);
        List<LivingEntity> candidates = level.getEntitiesOfClass(LivingEntity.class, searchBox,
                e -> e != player && e.isAlive());

        LivingEntity closest = null;
        double closestDist = Double.MAX_VALUE;
        for (LivingEntity entity : candidates) {
            Optional<Vec3> clip = entity.getBoundingBox().inflate(0.3).clip(eye, far);
            if (clip.isPresent()) {
                double dist = eye.distanceToSqr(clip.get());
                if (dist < closestDist) {
                    closestDist = dist;
                    closest = entity;
                }
            }
        }
        return closest;
    }

    private void executeSkill(Level level, Player player) {
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.WHITE_SWORD_RAIN.get(), SoundSource.PLAYERS, 0.7f, 1.0f);
        player.invulnerableTime = 10;
        player.swing(InteractionHand.MAIN_HAND, true);
        SkillCooldownHelper.applySharedCooldown(player, COOLDOWN_TICKS);

        MinecraftServer server = level.getServer();
        if (server == null) return;

        int baseTick = server.getTickCount();

        Vec3 eye = player.getEyePosition(1f);
        Vec3 look = player.getViewVector(1f);
        Vec3 far = eye.add(look.x * 20, look.y * 20, look.z * 20);
        LivingEntity homingTarget = findEntityAtCrosshair(level, player, eye, far);

        scheduleWave(server, level, player, homingTarget, baseTick + 9, 6, 4, false);
        scheduleWave(server, level, player, homingTarget, baseTick + 12, 6, 4, false);
        scheduleWave(server, level, player, homingTarget, baseTick + 15, 6, 4, true);
        scheduleWave(server, level, player, homingTarget, baseTick + 18, 5, 4, true);
        scheduleWave(server, level, player, homingTarget, baseTick + 21, 5, 3, true);
        scheduleWave(server, level, player, homingTarget, baseTick + 24, 4, 3, true);

        double px = player.getX();
        double py = player.getY();
        double pz = player.getZ();
        server.tell(new TickTask(baseTick + 27, () -> {
            level.playSound(null, px, py, pz,
                    net.minecraft.sounds.SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.PLAYERS, 2, 1);
        }));
        server.tell(new TickTask(baseTick + 30, () -> {
            level.playSound(null, px, py, pz,
                    net.minecraft.sounds.SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.PLAYERS, 2, 1);
        }));
    }

    /** Passive: 50% chance on melee attack to release homing arrow rain toward the target. */
    public static void triggerHomingRain(Level level, Player player, LivingEntity target) {
        if (SkillLockHelper.isSkillLocked(player)) return;
        if (!(level instanceof ServerLevel serverLevel)) return;

        Vec3 look = player.getViewVector(1f);
        Vec3 right = new Vec3(-look.z, 0, look.x).normalize();
        ItemStack weapon = player.getMainHandItem();
        RandomSource random = player.getRandom();

        float damage = (float) (PASSIVE_DAMAGE_RATIO * player.getAttributeValue(Attributes.ATTACK_DAMAGE));
        int sharpness = weapon.getEnchantmentLevel(Enchantments.SHARPNESS);
        int smite = weapon.getEnchantmentLevel(Enchantments.SMITE);
        int bane = weapon.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS);
        int fireAspect = weapon.getEnchantmentLevel(Enchantments.FIRE_ASPECT);
        int sweepingEdge = weapon.getEnchantmentLevel(Enchantments.SWEEPING_EDGE);
        int looting = weapon.getEnchantmentLevel(Enchantments.MOB_LOOTING);
        double effectiveSpread = PASSIVE_SPREAD + sweepingEdge * SWEEPING_EDGE_SPREAD_BONUS;

        for (int i = 0; i < PASSIVE_PROJECTILE_COUNT; i++) {
            WhiteSwordRainProjectileEntity projectile = new WhiteSwordRainProjectileEntity(
                    ModEntities.WHITE_SWORD_RAIN_PROJECTILE.get(), level);
            projectile.setOwner(player);
            projectile.setTarget(target);
            projectile.setPos(
                    player.getX() - look.x * (2.0 + random.nextDouble())
                            + right.x * Mth.nextDouble(random, -effectiveSpread, effectiveSpread),
                    player.getY() + Mth.nextDouble(random, 1.5, 3.5),
                    player.getZ() - look.z * (2.0 + random.nextDouble())
                            + right.z * Mth.nextDouble(random, -effectiveSpread, effectiveSpread));
            projectile.setDeltaMovement(
                    look.x * PASSIVE_PROJECTILE_SPEED + Mth.nextDouble(random, -0.15, 0.15),
                    look.y * PASSIVE_PROJECTILE_SPEED + Mth.nextDouble(random, -0.1, 0.1),
                    look.z * PASSIVE_PROJECTILE_SPEED + Mth.nextDouble(random, -0.15, 0.15));
            projectile.setDamage(damage);
            projectile.getPersistentData().putInt("paster_sharpness", sharpness);
            projectile.getPersistentData().putInt("paster_smite", smite);
            projectile.getPersistentData().putInt("paster_bane", bane);
            projectile.getPersistentData().putInt("paster_fire_aspect", fireAspect);
            projectile.getPersistentData().putInt("paster_sweeping_edge", sweepingEdge);
            projectile.getPersistentData().putInt("paster_knockback", 0);
            projectile.getPersistentData().putInt("paster_looting", looting);
            serverLevel.addFreshEntity(projectile);
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.WHITE_SWORD_RAIN.get(), SoundSource.PLAYERS, 0.3f, 1.5f);
    }

    private void scheduleWave(MinecraftServer server, Level level, Player player,
                              @Nullable LivingEntity target,
                              int fireTick, int outerCount, int innerCount, boolean playSound) {
        server.tell(new TickTask(fireTick, () -> {
            spawnProjectiles(level, player, target, 3.5, outerCount);
            spawnProjectiles(level, player, target, 2.5, innerCount);
            if (playSound) {
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    net.minecraft.sounds.SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.PLAYERS, 2, 1);
            }
        }));
    }

    private void spawnProjectiles(Level level, Player player, @Nullable LivingEntity target,
                                  double spread, int count) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        RandomSource random = RandomSource.create();

        Vec3 look = player.getViewVector(1f);
        Vec3 right = new Vec3(-look.z, 0, look.x).normalize();

        float damage = (float) (ACTIVE_DAMAGE_RATIO * player.getAttributeValue(Attributes.ATTACK_DAMAGE));
        ItemStack weapon = player.getMainHandItem();
        int sharpness = weapon.getEnchantmentLevel(Enchantments.SHARPNESS);
        int smite = weapon.getEnchantmentLevel(Enchantments.SMITE);
        int bane = weapon.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS);
        int fireAspect = weapon.getEnchantmentLevel(Enchantments.FIRE_ASPECT);
        int sweepingEdge = weapon.getEnchantmentLevel(Enchantments.SWEEPING_EDGE);
        int looting = weapon.getEnchantmentLevel(Enchantments.MOB_LOOTING);
        double effectiveSpread = spread + sweepingEdge * SWEEPING_EDGE_SPREAD_BONUS;

        for (int i = 0; i < count; i++) {
            WhiteSwordRainProjectileEntity projectile = new WhiteSwordRainProjectileEntity(
                    ModEntities.WHITE_SWORD_RAIN_PROJECTILE.get(), level);
            projectile.setOwner(player);
            if (target != null) {
                projectile.setTarget(target);
            }
            projectile.setPos(
                    player.getX() - look.x * (2.0 + random.nextDouble())
                            + right.x * Mth.nextDouble(random, -effectiveSpread, effectiveSpread),
                    player.getY() + Mth.nextDouble(random, 1.5, 3.5),
                    player.getZ() - look.z * (2.0 + random.nextDouble())
                            + right.z * Mth.nextDouble(random, -effectiveSpread, effectiveSpread));
            projectile.setDeltaMovement(
                    look.x * ACTIVE_PROJECTILE_SPEED + Mth.nextDouble(random, -0.2, 0.2),
                    look.y * ACTIVE_PROJECTILE_SPEED + Mth.nextDouble(random, -0.1, 0.1),
                    look.z * ACTIVE_PROJECTILE_SPEED + Mth.nextDouble(random, -0.2, 0.2));
            projectile.setDamage(damage);
            projectile.getPersistentData().putInt("paster_sharpness", sharpness);
            projectile.getPersistentData().putInt("paster_smite", smite);
            projectile.getPersistentData().putInt("paster_bane", bane);
            projectile.getPersistentData().putInt("paster_fire_aspect", fireAspect);
            projectile.getPersistentData().putInt("paster_sweeping_edge", sweepingEdge);
            projectile.getPersistentData().putInt("paster_knockback", 0);
            projectile.getPersistentData().putInt("paster_looting", looting);
            serverLevel.addFreshEntity(projectile);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        list.add(ModRarities.qualityTooltip(ModRarities.LEGENDARY));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.skill_name"));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.desc1"));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.desc2"));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.desc3"));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.desc4"));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.desc5"));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.desc6"));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.desc7"));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.skill_passive_name"));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.desc8"));
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(Level level, Entity location, ItemStack stack) {
        var entity = new IndestructibleItemEntity(level, location.getX(), location.getY(), location.getZ(), stack);
        entity.setDefaultPickUpDelay();
        entity.setDeltaMovement(location.getDeltaMovement());
        return entity;
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ShadowDamageHandler {
        private static final TagKey<EntityType<?>> SHADOW_MOB = TagKey.create(Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath("pasterdream", "shadow_mob"));

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if (event.getEntity().getPersistentData().getBoolean("pasterdream:rain_damage")) {
                return;
            }
            if (event.getSource().getEntity() instanceof Player player
                    && player.getMainHandItem().getItem() instanceof WhiteSwordItem) {
                boolean hasBrooch = CuriosApi.getCuriosInventory(player)
                        .map(inv -> inv.findFirstCurio(ModItems.BROOCH_OF_WHITE_ORCHID.get()).isPresent())
                        .orElse(false);
                float multiplier = 1.0f;
                if (event.getEntity().getType().is(SHADOW_MOB)) {
                    multiplier += 0.5f;
                }
                if (hasBrooch) {
                    multiplier += 0.5f;
                }
                if (multiplier > 1.0f) {
                    event.setAmount(event.getAmount() * multiplier);
                }

                // 50% chance passive: homing arrow rain on melee attack
                if (player.getRandom().nextDouble() < PASSIVE_CHANCE) {
                    triggerHomingRain(player.level(), player, event.getEntity());
                }
            }
        }

        @SubscribeEvent
        public static void onLivingKnockBack(LivingKnockBackEvent event) {
            if (event.getEntity().getPersistentData().getBoolean("pasterdream:rain_damage")) {
                event.setStrength(0);
            }
        }
    }
}
