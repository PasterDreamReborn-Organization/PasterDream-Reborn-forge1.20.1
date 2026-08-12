package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

public class TwilightLanternWaveSpawner {

    public static void execute(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof ServerLevel level)) return;

        // 4x SHADOW_GHOST
        spawn(ModEntities.SHADOW_GHOST.get(), level, x + 6, y + 2, z);
        spawn(ModEntities.SHADOW_GHOST.get(), level, x - 6, y + 2, z);
        spawn(ModEntities.SHADOW_GHOST.get(), level, x, y + 2, z + 6);
        spawn(ModEntities.SHADOW_GHOST.get(), level, x, y + 2, z - 6);

        // 4x SHADOW_SQUEAL_GHOST
        spawn(ModEntities.SHADOW_SQUEAL_GHOST.get(), level, x + 8, y + 1, z + 8);
        spawn(ModEntities.SHADOW_SQUEAL_GHOST.get(), level, x - 8, y + 1, z + 8);
        spawn(ModEntities.SHADOW_SQUEAL_GHOST.get(), level, x + 8, y + 1, z - 8);
        spawn(ModEntities.SHADOW_SQUEAL_GHOST.get(), level, x - 8, y + 1, z - 8);

        // 4x WITHER_SKELETON (vanilla)
        spawn(EntityType.WITHER_SKELETON, level, x + 7, y - 1, z + 1);
        spawn(EntityType.WITHER_SKELETON, level, x - 7, y - 1, z + 1);
        spawn(EntityType.WITHER_SKELETON, level, x, y - 1, z + 7);
        spawn(EntityType.WITHER_SKELETON, level, x, y - 1, z - 7);
    }

    private static void spawn(EntityType<?> type, ServerLevel level, double x, double y, double z) {
        Entity entity = type.spawn(level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
        if (entity != null) {
            entity.setYRot(level.getRandom().nextFloat() * 360F);
        }
    }
}
