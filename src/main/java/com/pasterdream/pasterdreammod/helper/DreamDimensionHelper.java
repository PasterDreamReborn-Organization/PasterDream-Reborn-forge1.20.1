package com.pasterdream.pasterdreammod.helper;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.Set;

/**
 * 梦境维度集合：苍白骨针、琴雨梦套装、融梦能量戒指等「是否处于梦境维度」的判断统一从这里取，
 * 新增梦境维度时只需在此登记。
 */
public final class DreamDimensionHelper {
    private DreamDimensionHelper() {}

    public static final Set<ResourceKey<Level>> DREAM_DIMENSIONS = new HashSet<>();

    static {
        DREAM_DIMENSIONS.add(ResourceKey.create(Registries.DIMENSION,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world")));
        DREAM_DIMENSIONS.add(ResourceKey.create(Registries.DIMENSION,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world")));
        DREAM_DIMENSIONS.add(ResourceKey.create(Registries.DIMENSION,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey_world")));
        DREAM_DIMENSIONS.add(ResourceKey.create(Registries.DIMENSION,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_arena_world")));
    }

    /**
     * 供 KubeJS 等外部脚本在启动阶段登记额外的梦境维度（格式：modid:dimension_id）。
     * 仅在世界加载前调用是安全的；解析失败返回 false。
     */
    public static boolean register(String dimensionId) {
        ResourceLocation rl = ResourceLocation.tryParse(dimensionId);
        if (rl == null) {
            return false;
        }
        DREAM_DIMENSIONS.add(ResourceKey.create(Registries.DIMENSION, rl));
        return true;
    }

    /**
     * 从梦境维度集合中移除一个维度（格式：modid:dimension_id）。
     * 解析失败或维度本来就不在集合中时返回 false；确实删除了返回 true。
     */
    public static boolean unregister(String dimensionId) {
        ResourceLocation rl = ResourceLocation.tryParse(dimensionId);
        if (rl == null) {
            return false;
        }
        return DREAM_DIMENSIONS.remove(ResourceKey.create(Registries.DIMENSION, rl));
    }

    public static boolean isDreamDimension(Level level) {
        return DREAM_DIMENSIONS.contains(level.dimension());
    }
}
