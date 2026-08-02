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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import java.util.List;

public class WhiteSwordItem extends SwordItem {

    private static final double ENERGY_COST = 0.1;
    private static final int COOLDOWN_TICKS = 84;

    public WhiteSwordItem(Tier tier, int damage, float speed) {
        super(tier, damage, speed, new Properties().fireResistant());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
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

    private static final double TARGET_REACH = 10.0;

    private void executeSkill(Level level, Player player) {
        level.playSound(null, BlockPos.containing(player.getX(), player.getY(), player.getZ()),
                ModSounds.WHITE_SWORD_RAIN.get(), SoundSource.PLAYERS, 0.7f, 1.0f);
        player.swing(InteractionHand.MAIN_HAND, true);
        SkillCooldownHelper.applySharedCooldown(player, COOLDOWN_TICKS);

        MinecraftServer server = level.getServer();
        if (server == null) return;

        int baseTick = server.getTickCount();
        double px = player.getX();
        double py = player.getY();
        double pz = player.getZ();

        // Find target: entity at crosshair > block at crosshair > air 10 blocks ahead
        Vec3 eye = player.getEyePosition(1f);
        Vec3 look = player.getViewVector(1f);
        Vec3 far = eye.add(look.x * TARGET_REACH, look.y * TARGET_REACH, look.z * TARGET_REACH);
        Vec3 targetPos = findTargetPos(level, player, eye, far);

        // Waves at ticks 9, 12, 15, 18, 21, 24 (relative to current tick)
        scheduleWave(server, level, player, targetPos, px, py, pz, baseTick + 9, 6, 4, false);
        scheduleWave(server, level, player, targetPos, px, py, pz, baseTick + 12, 6, 4, false);
        scheduleWave(server, level, player, targetPos, px, py, pz, baseTick + 15, 6, 4, true);
        scheduleWave(server, level, player, targetPos, px, py, pz, baseTick + 18, 5, 4, true);
        scheduleWave(server, level, player, targetPos, px, py, pz, baseTick + 21, 5, 3, true);
        scheduleWave(server, level, player, targetPos, px, py, pz, baseTick + 24, 4, 3, true);

        // Closing amethyst sounds
        server.tell(new TickTask(baseTick + 27, () -> {
            level.playSound(null, BlockPos.containing(px, py, pz),
                    net.minecraft.sounds.SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.PLAYERS, 2, 1);
        }));
        server.tell(new TickTask(baseTick + 30, () -> {
            level.playSound(null, BlockPos.containing(px, py, pz),
                    net.minecraft.sounds.SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.PLAYERS, 2, 1);
        }));
    }

    /** Find target position: closest entity at crosshair → block at crosshair → air 10 blocks out */
    private Vec3 findTargetPos(Level level, Player player, Vec3 eye, Vec3 far) {
        // 1. Try to find an entity along the ray
        AABB searchBox = new AABB(eye, far).inflate(1.0);
        List<LivingEntity> candidates = level.getEntitiesOfClass(LivingEntity.class, searchBox,
                e -> e != player && e.isAlive() && !isOwnedBy(e, player));

        LivingEntity closestEntity = null;
        double closestDist = TARGET_REACH;
        for (LivingEntity entity : candidates) {
            Optional<Vec3> clip = entity.getBoundingBox().inflate(0.3).clip(eye, far);
            if (clip.isPresent()) {
                double dist = eye.distanceToSqr(clip.get());
                if (dist < closestDist * closestDist) {
                    closestDist = Math.sqrt(dist);
                    closestEntity = entity;
                }
            }
        }

        if (closestEntity != null) {
            return closestEntity.position();
        }

        // 2. No entity — try block at crosshair
        BlockHitResult blockHit = level.clip(new ClipContext(eye, far, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if (blockHit.getType() == HitResult.Type.BLOCK) {
            return blockHit.getLocation();
        }

        // 3. No entity, no block — air position 10 blocks ahead
        return far;
    }

    private static boolean isOwnedBy(LivingEntity target, Player owner) {
        if (target instanceof OwnableEntity ownable) {
            return ownable.getOwner() == owner;
        }
        return false;
    }

    private void scheduleWave(MinecraftServer server, Level level, Player player,
                              Vec3 targetPos, double playerX, double playerY, double playerZ,
                              int fireTick, int outerCount, int innerCount, boolean playSound) {
        server.tell(new TickTask(fireTick, () -> {
            spawnProjectiles(level, player, targetPos, 3.5, outerCount);
            spawnProjectiles(level, player, targetPos, 2.5, innerCount);
            if (playSound) {
                level.playSound(null, BlockPos.containing(playerX, playerY, playerZ),
                        net.minecraft.sounds.SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.PLAYERS, 2, 1);
            }
        }));
    }

    private void spawnProjectiles(Level level, Player player, Vec3 targetPos,
                                  double spread, int count) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        RandomSource random = RandomSource.create();

        float damage = (float) (3 + 0.4 * player.getAttributeValue(Attributes.ATTACK_DAMAGE));

        for (int i = 0; i < count; i++) {
            WhiteSwordRainProjectileEntity projectile = new WhiteSwordRainProjectileEntity(
                    ModEntities.WHITE_SWORD_RAIN_PROJECTILE.get(), level);
            projectile.setOwner(player);
            projectile.setPos(
                    targetPos.x + Mth.nextDouble(random, -spread, spread),
                    player.getY() + Mth.nextDouble(random, 13, 15),
                    targetPos.z + Mth.nextDouble(random, -spread, spread));
            projectile.setDeltaMovement(0, -1, 0);
            projectile.setDamage(damage);
            serverLevel.addFreshEntity(projectile);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        list.add(Component.translatable("tooltip.pasterdream.white_sword.skill_name"));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.desc1"));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.desc2"));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.desc3"));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.desc4"));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.desc5"));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.desc6"));
        list.add(Component.translatable("tooltip.pasterdream.white_sword.desc7"));
    }
}
