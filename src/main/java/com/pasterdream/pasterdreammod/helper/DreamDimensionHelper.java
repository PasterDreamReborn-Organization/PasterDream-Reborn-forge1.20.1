package com.pasterdream.pasterdreammod.helper;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.Set;

/**
 * 梦境维度集合：苍白骨针、琴雨梦套装、融梦能量戒指等「是否处于梦境维度」的判断统一从这里取，
 * 新增梦境维度时只需在此登记。
 */
public final class DreamDimensionHelper {
    private DreamDimensionHelper() {}

    public static final Set<ResourceKey<Level>> DREAM_DIMENSIONS = Set.of(
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world")),
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world")),
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey_world")),
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_arena_world"))
    );

    public static boolean isDreamDimension(Level level) {
        return DREAM_DIMENSIONS.contains(level.dimension());
    }
}
