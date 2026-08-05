package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

public class LampShadowWorldTeleporter {

    private static final ResourceKey<Level> LAMP_SHADOW_WORLD =
            ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"));

    public static ResourceKey<Level> getLampShadowWorld() {
        return LAMP_SHADOW_WORLD;
    }

    /**
     * Teleports the entity to the lamp_shadow_world dimension.
     * Target is below the twilight_lantern in the shadow_world_spawn structure.
     * Height logic must match placeShadowWorldSpawn in ModWorldGenEvents.
     */
    public static void execute(Level world, Entity entity) {
        if (!(entity instanceof ServerPlayer player)) return;
        if (player.level().dimension() == LAMP_SHADOW_WORLD) return;

        ServerLevel destination = player.server.getLevel(LAMP_SHADOW_WORLD);
        if (destination == null) return;

        // Match the structure placement: height at (-9, -9) <= 100 → low spawn, else → high spawn
        boolean low = destination.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, -9, -9) <= 100;
        double targetY = low ? 104 : 154;

        player.teleportTo(destination, 0.5, targetY, 0.5, player.getYRot(), player.getXRot());
    }
}
