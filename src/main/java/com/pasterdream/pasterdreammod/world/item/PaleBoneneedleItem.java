package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.helper.DreamDimensionHelper;
import com.pasterdream.pasterdreammod.helper.GameModeHelper;
import com.pasterdream.pasterdreammod.init.ModCriteriaTriggers;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PaleBoneneedleItem extends Item {

    /** 传送前延迟（tick），给音效/粒子留出播放时间 */
    static final int TELEPORT_DELAY = 20;

    // ===== 简易延迟任务调度器 =====

    private static final Queue<DelayedTask> TASKS = new ConcurrentLinkedQueue<>();
    private static boolean registered = false;

    static void scheduleDelayed(Runnable task) {
        if (!registered) {
            registered = true;
            MinecraftForge.EVENT_BUS.addListener(PaleBoneneedleItem::onServerTick);
        }
        TASKS.add(new DelayedTask(TELEPORT_DELAY, task));
    }

    private static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Iterator<DelayedTask> it = TASKS.iterator();
        while (it.hasNext()) {
            DelayedTask task = it.next();
            task.ticks--;
            if (task.ticks <= 0) {
                task.runnable.run();
                it.remove();
            }
        }
    }

    private static class DelayedTask {
        int ticks;
        final Runnable runnable;
        DelayedTask(int ticks, Runnable runnable) {
            this.ticks = ticks;
            this.runnable = runnable;
        }
    }

    // ===== 物品逻辑 =====

    public PaleBoneneedleItem() {
        super(new Item.Properties().durability(1).rarity(Rarity.COMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.pale_boneneedle"));
        tooltip.add(Component.translatable("tooltip.pasterdream.pale_boneneedle.use"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.swing(hand, true);
        player.hurt(player.level().damageSources().generic(), 1.0f);

        if (DreamDimensionHelper.isDreamDimension(level) && level instanceof ServerLevel serverLevel) {
            boolean wasFalling = player.fallDistance > 10;

            if (player instanceof ServerPlayer sp) {
                // 授予进度：使用苍白骨针（"哦，疼！"）
                ModCriteriaTriggers.USE_BONE_NEEDLE.trigger(sp, false);
            }
            serverLevel.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(),
                    player.getX(), player.getY(), player.getZ(),
                    64, 0.1, 1, 0.1, 0.2);
            level.playSound(null, BlockPos.containing(player.getX(), player.getY(), player.getZ()),
                    ModSounds.DREAM0.get(), SoundSource.NEUTRAL, 0.5f, 1.0f);

            scheduleDelayed(() -> {
                teleportToOverworldAndSpawn(serverLevel, player);
                // 挑战进度：回主世界后授予（梦境中跌落>10格使用骨针 —— "人类坠出梦境"）
                if (wasFalling && player instanceof ServerPlayer sp) {
                    ModCriteriaTriggers.USE_BONE_NEEDLE.trigger(sp, true);
                }
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 100, 0));
                player.getCooldowns().addCooldown(this, 100);
            });
        }

        itemstack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    static void teleportToOverworldAndSpawn(ServerLevel serverLevel, Player player) {
        if (player instanceof ServerPlayer sp) {
            ResourceKey<Level> destKey = Level.OVERWORLD;
            if (sp.level().dimension() != destKey) {
                ServerLevel overworld = sp.server.getLevel(destKey);
                if (overworld != null) {
                    sp.teleportTo(overworld, sp.getX(), sp.getY(), sp.getZ(), sp.getYRot(), sp.getXRot());
                }
            }
            double spawnX, spawnY, spawnZ;
            if (sp.getRespawnDimension().equals(sp.level().dimension()) && sp.getRespawnPosition() != null) {
                BlockPos respawn = sp.getRespawnPosition();
                spawnX = respawn.getX();
                spawnY = respawn.getY();
                spawnZ = respawn.getZ();
            } else {
                spawnX = sp.level().getLevelData().getXSpawn();
                spawnY = sp.level().getLevelData().getYSpawn();
                spawnZ = sp.level().getLevelData().getZSpawn();
            }
            sp.teleportTo(spawnX, spawnY, spawnZ);
            sp.fallDistance = 0;
            // 离开梦境维度后恢复进入前的游戏模式（防止卡在冒险模式）
            GameModeHelper.restorePreDreamGameMode(sp);
        }
    }

    @Mod.EventBusSubscriber(modid = "pasterdream", bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class AttackHandler {
        @SubscribeEvent
        public static void onAttackEntity(AttackEntityEvent event) {
            Player attacker = event.getEntity();
            if (attacker.level().isClientSide()) return;

            ItemStack stack = attacker.getMainHandItem();
            Item item = stack.getItem();

            if (!(item instanceof PaleBoneneedleItem) && !(item instanceof RootsPaleBoneneedleItem)) return;
            if (!(event.getTarget() instanceof Player target)) return;

            // RootsPaleBoneneedleItem: only works if waypoint is set
            if (item instanceof RootsPaleBoneneedleItem && !stack.getOrCreateTag().getBoolean("switch")) return;

            event.setCanceled(true);

            Level level = attacker.level();
            target.hurt(level.damageSources().generic(), 1.0f);

            if (DreamDimensionHelper.isDreamDimension(level) && level instanceof ServerLevel serverLevel) {
                boolean wasFalling = target.fallDistance > 10;

                serverLevel.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(),
                        target.getX(), target.getY(), target.getZ(),
                        64, 0.1, 1, 0.1, 0.2);
                level.playSound(null, BlockPos.containing(target.getX(), target.getY(), target.getZ()),
                        ModSounds.DREAM0.get(), SoundSource.NEUTRAL, 0.5f, 1.0f);

                scheduleDelayed(() -> {
                    teleportToOverworldAndSpawn(serverLevel, target);
                    if (item instanceof RootsPaleBoneneedleItem) {
                        RootsPaleBoneneedleItem.teleportToWaypoint(stack, target);
                    }
                    if (target instanceof ServerPlayer sp) {
                        ModCriteriaTriggers.USE_BONE_NEEDLE.trigger(sp, false);
                    }
                    if (wasFalling && target instanceof ServerPlayer sp) {
                        ModCriteriaTriggers.USE_BONE_NEEDLE.trigger(sp, true);
                    }
                    target.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 100, 0));
                    attacker.getCooldowns().addCooldown(item, 100);
                });
            }

            if (item instanceof PaleBoneneedleItem) {
                stack.hurtAndBreak(1, attacker, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            }
        }
    }
}
