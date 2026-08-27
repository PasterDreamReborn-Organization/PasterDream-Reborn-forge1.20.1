package com.pasterdream.pasterdreammod.world.block.desertherotomb;

import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;

public class DesertHeroTombHandler {

    public static void onUse(Level level, BlockPos pos, Player player) {
        if (level.isClientSide()) return;
        if (!(level.getBlockEntity(pos) instanceof DesertHeroTombBlockEntity be)) return;

        // Animation and effects play for every click (for atmosphere)
        triggerAnimation(level, pos);
        spawnSoulParticles(level, pos);
        playSkeletonSound(level, pos);

        int number = be.getQuestNumber(player);

        switch (number) {
            case 0 -> {
                be.setQuestNumber(player, 1);
                be.startDelayedMessages(player);
            }
            case 1 -> checkThreats(level, pos, player, be);
            case 2 -> checkTasks(level, pos, player, be);
            case 3 -> exchangeSword(player, be);
            case 4 -> player.displayClientMessage(
                    Component.translatable("message.pasterdream.desert_hero_tomb.already_completed"), true);
        }
    }

    /**
     * Process delayed messages during block entity tick.
     * Phase 0: initial dialogue (stage 0→1).
     * Phase 1: quest acceptance dialogue (stage 1→2).
     */
    public static void processDelayedMessages(Level level, BlockPos pos, DesertHeroTombBlockEntity be) {
        Player player = be.getInteractingPlayer(level);
        if (player == null) return;

        int tick = be.getTickCounter();
        int phase = be.getMessagePhase();

        if (phase == 0) {
            if (tick == 20) {
                player.displayClientMessage(
                        Component.translatable("message.pasterdream.desert_hero_tomb.line1"), false);
            }
            if (tick == 40) {
                player.displayClientMessage(
                        Component.translatable("message.pasterdream.desert_hero_tomb.line2"), false);
            }
            if (tick == 60) {
                player.displayClientMessage(
                        Component.translatable("message.pasterdream.desert_hero_tomb.line3"), false);
            }
            if (tick == 80) {
                player.displayClientMessage(
                        Component.translatable("message.pasterdream.desert_hero_tomb.line4"), false);
            }
            if (tick == 90) {
                ItemStack sword = new ItemStack(ModItems.DESERT_SWORD.get());
                sword.setCount(1);
                ItemHandlerHelper.giveItemToPlayer(player, sword);
                player.displayClientMessage(
                        Component.translatable("message.pasterdream.desert_hero_tomb.task_clear_threats"), false);
            }
        } else if (phase == 1) {
            if (tick == 20) {
                player.displayClientMessage(
                        Component.translatable("message.pasterdream.desert_hero_tomb.line6"), false);
            }
            if (tick == 40) {
                player.displayClientMessage(
                        Component.translatable("message.pasterdream.desert_hero_tomb.line7"), false);
                player.displayClientMessage(
                        Component.translatable("message.pasterdream.desert_hero_tomb.quest1"), false);
                player.displayClientMessage(
                        Component.translatable("message.pasterdream.desert_hero_tomb.quest2"), false);
                player.displayClientMessage(
                        Component.translatable("message.pasterdream.desert_hero_tomb.quest3"), false);
            }
        }
    }

    private static void triggerAnimation(Level level, BlockPos pos) {
        BlockState bs = level.getBlockState(pos);
        if (bs.getBlock().getStateDefinition().getProperty("animation") instanceof IntegerProperty ip
                && ip.getPossibleValues().contains(1)) {
            level.setBlock(pos, bs.setValue(ip, 1), 3);
        }
    }

    private static void spawnSoulParticles(Level level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    (SimpleParticleType) ModParticleTypes.SOUL_PARTICLE.get(),
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    7, 0.2, 3, 0.2, 0.1);
        }
    }

    private static void playSkeletonSound(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.SKELETON_AMBIENT, SoundSource.NEUTRAL, 1.0f, 1.0f);
    }

    private static void checkThreats(Level level, BlockPos pos, Player player, DesertHeroTombBlockEntity be) {
        Vec3 center = new Vec3(pos.getX(), pos.getY() + 16, pos.getZ());
        AABB area = AABB.ofSize(center, 48, 48, 48);
        boolean hasPillagers = !level.getEntitiesOfClass(Pillager.class, area, e -> true).isEmpty();
        boolean hasHusks = !level.getEntitiesOfClass(Husk.class, area, e -> true).isEmpty();

        if (hasPillagers || hasHusks) {
            player.displayClientMessage(
                    Component.translatable("message.pasterdream.desert_hero_tomb.threats_remaining"), true);
        } else {
            player.displayClientMessage(
                    Component.translatable("message.pasterdream.desert_hero_tomb.line5"), false);
            be.setQuestNumber(player, 2);
            be.startDelayedMessagesPhase1(player);
        }
    }

    private static void checkTasks(Level level, BlockPos pos, Player player, DesertHeroTombBlockEntity be) {
        // Task 1: 10 rice cakes in hand
        if (!be.isTask1Complete(player)) {
            ItemStack held = player.getMainHandItem();
            if (held.is(ModItems.RICE_CAKE.get()) && held.getCount() >= 10) {
                held.shrink(10);
                be.setTask1Complete(player, true);
                player.displayClientMessage(
                        Component.translatable("message.pasterdream.desert_hero_tomb.task1_complete"), true);
            }
        }

        // Task 2: Horse nearby
        if (!be.isTask2Complete(player)) {
            AABB area = AABB.ofSize(Vec3.atCenterOf(pos), 9, 9, 9);
            if (!level.getEntitiesOfClass(Horse.class, area, e -> true).isEmpty()) {
                be.setTask2Complete(player, true);
                player.displayClientMessage(
                        Component.translatable("message.pasterdream.desert_hero_tomb.task2_complete"), true);
            }
        }

        // Task 3: Hero of the Village
        if (!be.isTask3Complete(player)) {
            if (player.hasEffect(MobEffects.HERO_OF_THE_VILLAGE)) {
                be.setTask3Complete(player, true);
                player.displayClientMessage(
                        Component.translatable("message.pasterdream.desert_hero_tomb.task3_complete"), true);
            }
        }

        // All tasks complete → advance to stage 3
        if (be.allTasksComplete(player)) {
            be.setQuestNumber(player, 3);
            player.displayClientMessage(
                    Component.translatable("message.pasterdream.desert_hero_tomb.all_tasks_done"), false);
        } else {
            player.displayClientMessage(
                    Component.translatable("message.pasterdream.desert_hero_tomb.quest1"), false);
            player.displayClientMessage(
                    Component.translatable("message.pasterdream.desert_hero_tomb.quest2"), false);
            player.displayClientMessage(
                    Component.translatable("message.pasterdream.desert_hero_tomb.quest3"), false);
        }
    }

    private static void exchangeSword(Player player, DesertHeroTombBlockEntity be) {
        ItemStack held = player.getMainHandItem();
        if (held.is(ModItems.DESERT_SWORD.get())) {
            be.setQuestNumber(player, 4);
            player.displayClientMessage(
                    Component.translatable("message.pasterdream.desert_hero_tomb.receive_sword"), false);
            ItemStack trueSword = new ItemStack(ModItems.CHENJINGMEN_DESERT_SWORD.get());
            trueSword.setCount(1);
            if (held.hasTag()) {
                trueSword.setTag(held.getTag().copy());
            }
            player.setItemInHand(InteractionHand.MAIN_HAND, trueSword);
            player.getInventory().setChanged();
        } else {
            player.displayClientMessage(
                    Component.translatable("message.pasterdream.desert_hero_tomb.bring_sword"), false);
        }
    }
}
