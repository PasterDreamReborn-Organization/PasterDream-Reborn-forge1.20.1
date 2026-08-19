package com.pasterdream.pasterdreammod.worldgen.dimension;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

public class ModDimensionTypes {

    public static final ResourceKey<DimensionType> DYEDREAM_WORLD =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world"));

    public static final ResourceKey<DimensionType> LAMP_SHADOW_WORLD =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"));

    public static final ResourceKey<DimensionType> WIND_JOURNEY_WORLD =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey_world"));

    public static final ResourceKey<DimensionType> AARONCOS_ARENA_WORLD =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_arena_world"));

    public static void bootstrap(BootstapContext<DimensionType> context) {
        context.register(DYEDREAM_WORLD, new DimensionType(
                OptionalLong.empty(),       // 无固定时间，使用日夜循环
                true,                       // 有天空光照
                false,                      // 无基岩天花板
                false,                      // 非地狱干燥环境
                true,                       // 自然维度（天气与光照行为同主世界）
                1.0,                        // 坐标缩放倍率
                true,                       // 允许睡觉
                false,                      // 不允许重生锚
                -64,                        // 最低建筑高度
                384,                        // 总高度
                384,                        // 逻辑高度（最高建筑高度 = -64+384 = 320）
                BlockTags.INFINIBURN_OVERWORLD, // 可无限燃烧的方块标签
                ResourceLocation.parse("overworld"), // 天空/雾/云效果同主世界
                0.5f,                       // 环境光照
                new DimensionType.MonsterSettings(
                        false,              // 猪灵会僵尸化
                        false,              // 禁止袭击
                        UniformInt.of(0, 0), // 怪物生成光照判定（仅天光0，配合群系空怪物列表禁止生成）
                        0                   // 怪物生成方块光照上限
                )
        ));

        context.register(LAMP_SHADOW_WORLD, new DimensionType(
                OptionalLong.of(18000),     // 固定时间 18000（午夜）
                false,                      // 无天空光照
                false,                      // 无基岩天花板
                false,                      // 非地狱干燥环境
                false,                      // 非自然维度（无天气循环）
                1.0,                        // 坐标缩放倍率
                false,                      // 不允许睡觉
                false,                      // 不允许重生锚
                -64,                        // 最低建筑高度
                384,                        // 总高度
                384,                        // 逻辑高度
                BlockTags.INFINIBURN_OVERWORLD,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"),
                0.15f,                      // 环境光照
                new DimensionType.MonsterSettings(
                        true,               // 猪灵安全
                        false,              // 禁止袭击
                        UniformInt.of(0, 7), // 怪物生成光照判定（0~7）
                        0                   // 怪物生成方块光照上限
                )
        ));

        context.register(WIND_JOURNEY_WORLD, new DimensionType(
                OptionalLong.empty(),       // 无固定时间，使用日夜循环
                true,                       // 有天空光照
                false,                      // 无基岩天花板
                false,                      // 非地狱干燥环境
                false,                      // 非自然维度（无天气循环，浮空岛）
                1.0,                        // 坐标缩放倍率
                true,                       // 允许睡觉
                false,                      // 不允许重生锚
                0,                          // 最低建筑高度
                256,                        // 总高度
                256,                        // 逻辑高度
                BlockTags.INFINIBURN_OVERWORLD,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey_world"),
                0.0f,                       // 环境光照
                new DimensionType.MonsterSettings(
                        true,               // 猪灵安全
                        false,              // 禁止袭击
                        UniformInt.of(0, 7), // 怪物生成光照判定（0~7）
                        0                   // 怪物生成方块光照上限
                )
        ));

        context.register(AARONCOS_ARENA_WORLD, new DimensionType(
                OptionalLong.empty(),       // 无固定时间，使用日夜循环
                false,                      // 无天空光照
                false,                      // 无基岩天花板
                false,                      // 非地狱干燥环境
                false,                      // 非自然维度（无天气循环）
                1.0,                        // 坐标缩放倍率
                false,                      // 不允许睡觉
                false,                      // 不允许重生锚
                0,                          // 最低建筑高度
                256,                        // 总高度
                256,                        // 逻辑高度
                BlockTags.INFINIBURN_OVERWORLD,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_arena_world"),
                0.5f,                       // 环境光照
                new DimensionType.MonsterSettings(
                        true,               // 猪灵安全
                        false,              // 禁止袭击
                        UniformInt.of(0, 7), // 怪物生成光照判定（0~7）
                        0                   // 怪物生成方块光照上限
                )
        ));
    }
}
