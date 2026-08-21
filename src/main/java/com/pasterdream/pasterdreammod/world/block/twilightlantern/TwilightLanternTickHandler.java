package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TwilightLanternTickHandler {

    private static final int EVENT_END_TICK = 2600;
    private static final int POST_MESSAGE_TICK = 2660;
    private static final int READY_MESSAGE_TICK = 2680;

    private static final ResourceLocation LAMP_SHADOW_ROOT_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/lamp_shadow_root");
    private static final ResourceLocation BASTION_GUARD_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/bastion_guard");
    private static final String BASTION_GUARD_CRITERION = "complete_bastion_guard";

    public static void execute(ServerLevel world, BlockPos pos, TwilightLanternBlockEntity lantern) {
        if (!lantern.isEventSwitch()) return;

        int tick = lantern.getEventTick() + 1;
        lantern.setEventTick(tick);

        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        // ---- Active event phase (tick 0 ~ 2599) ----

        // Wave counter increment (every 20 ticks)
        if (tick <= EVENT_END_TICK && tick % 20 == 0) {
            double number = lantern.getNumber() + 1;
            lantern.setNumber(number);

            if (number == 4 || number == 14 || number == 40 || number == 50 || number == 70 || number == 100 || number == 120) {
                TwilightLanternWaveSpawner.execute(world, x, y, z);
            }
        }

        // Tick 18: first ominous message + shadow0 sound
        if (tick == 18) {
            playSound(world, pos, ModSounds.SHADOW_OMINOUS.get());
            broadcastMessage(world, pos, Component.translatable("message.pasterdream.twilight_lantern.event_start"));
        }

        // Tick 30: elite wave 1 (SHADOW_GOLEM + TERRORBEAK)
        if (tick == 30) {
            spawnElite(world, x, y, z, z + 9);
        }

        // Tick 55: second message + shadow_music_0 + shadow golem roar
        if (tick == 55) {
            playSound(world, pos, ModSounds.SHADOW_MUSIC.get(), SoundSource.RECORDS);
            playSound(world, pos, ModSounds.SHADOW_ROAR.get(), SoundSource.HOSTILE);
            broadcastMessage(world, pos, Component.translatable("message.pasterdream.twilight_lantern.event_mid"));
        }

        // Tick 90: eerie voice
        if (tick == 90) {
            broadcastMessage(world, pos, Component.translatable("message.pasterdream.twilight_lantern.event_voice"));
        }

        // Tick 80: elite wave 2
        if (tick == 80) {
            spawnElite(world, x, y, z, z - 9);
        }

        // ---- Event end phase ----

        // Tick 2600: event completes, set key=true, grant bastion_guard to eligible players
        if (tick == EVENT_END_TICK) {
            boolean playerNearby = !world.getEntitiesOfClass(Player.class,
                    AABB.ofSize(new Vec3(x, y, z), 46, 46, 46), e -> true).isEmpty();

            if (playerNearby) {
                lantern.setKey(true);
                broadcastMessage(world, pos, Component.translatable("message.pasterdream.twilight_lantern.event_end"));

                // Grant bastion_guard to all nearby players who have read 侵染教堂-黑面
                for (Player player : world.players()) {
                    if (player.distanceToSqr(x, y, z) > 46 * 46) continue;
                    if (!(player instanceof ServerPlayer sp)) continue;
                    if (!isAdvancementDone(sp, LAMP_SHADOW_ROOT_ADV)) continue;
                    grantAdvancement(sp, BASTION_GUARD_ADV, BASTION_GUARD_CRITERION);
                }
            }
        }

        // Tick 2615: dark purple message 1
        if (tick == 2615) {
            broadcastMessage(world, pos, Component.translatable("message.pasterdream.twilight_lantern.event_end_3"));
        }

        // Tick 2630: dark purple message 2
        if (tick == 2630) {
            broadcastMessage(world, pos, Component.translatable("message.pasterdream.twilight_lantern.event_end_4"));
        }

        // Tick 2645: dark purple message 3
        if (tick == 2645) {
            broadcastMessage(world, pos, Component.translatable("message.pasterdream.twilight_lantern.event_end_5"));
        }

        // Tick 2660: post-event message
        if (tick == POST_MESSAGE_TICK) {
            broadcastMessage(world, pos, Component.translatable("message.pasterdream.twilight_lantern.event_end_2"));
        }

        // Tick 2680: ready message
        if (tick == READY_MESSAGE_TICK) {
            broadcastMessage(world, pos, Component.translatable("message.pasterdream.twilight_lantern.event_ready"));
        }

        // Cleanup after all post-event work is done
        if (tick >= READY_MESSAGE_TICK) {
            lantern.setEventSwitch(false);
            lantern.setEventTick(0);
            lantern.setNumber(0);
        }
    }

    private static void spawnElite(ServerLevel world, double x, double y, double z, double golemZ) {
        Entity golem = ModEntities.SHADOW_GOLEM.get().spawn(world,
                BlockPos.containing(x, y - 1, golemZ), MobSpawnType.MOB_SUMMONED);
        if (golem != null) golem.setYRot(world.getRandom().nextFloat() * 360F);

        Entity terrorbeak = ModEntities.TERRORBEAK.get().spawn(world,
                BlockPos.containing(x - 13, y - 1, z), MobSpawnType.MOB_SUMMONED);
        if (terrorbeak != null) terrorbeak.setYRot(world.getRandom().nextFloat() * 360F);
    }

    private static void broadcastMessage(ServerLevel world, BlockPos pos, Component message) {
        for (Player player : world.players()) {
            if (player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) < 54 * 54) {
                player.displayClientMessage(message, false);
            }
        }
    }

    private static void playSound(ServerLevel world, BlockPos pos, net.minecraft.sounds.SoundEvent sound) {
        playSound(world, pos, sound, SoundSource.NEUTRAL);
    }

    private static void playSound(ServerLevel world, BlockPos pos, net.minecraft.sounds.SoundEvent sound, SoundSource source) {
        world.playSound(null, pos, sound, source, 1, 1);
    }

    private static boolean isAdvancementDone(ServerPlayer player, ResourceLocation id) {
        Advancement adv = player.server.getAdvancements().getAdvancement(id);
        return adv != null && player.getAdvancements().getOrStartProgress(adv).isDone();
    }

    private static void grantAdvancement(ServerPlayer player, ResourceLocation id, String criterion) {
        Advancement adv = player.server.getAdvancements().getAdvancement(id);
        if (adv != null && !player.getAdvancements().getOrStartProgress(adv).isDone()) {
            player.getAdvancements().award(adv, criterion);
        }
    }
}
