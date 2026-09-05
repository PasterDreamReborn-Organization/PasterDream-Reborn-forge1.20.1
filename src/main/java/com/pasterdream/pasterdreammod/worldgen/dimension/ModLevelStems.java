package com.pasterdream.pasterdreammod.worldgen.dimension;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.worldgen.biome.ModBiomes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.lang.reflect.Constructor;
import java.util.List;

public class ModLevelStems {

    //六维参数区间必须满足：每个群系的参数区间不能都是其他群系参数区间的子集，否则该群系永远无法出现。
    //满足上述条件的情况下如果某群系仍然不生成，则需进一步满足：六个参数区间中至少有一个与其他所有群系的对应区间交集为空。
    public static final ResourceKey<LevelStem> DYEDREAM_WORLD =
            ResourceKey.create(Registries.LEVEL_STEM,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world"));

    public static final ResourceKey<LevelStem> LAMP_SHADOW_WORLD =
            ResourceKey.create(Registries.LEVEL_STEM,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"));

    public static final ResourceKey<LevelStem> WIND_JOURNEY_WORLD =
            ResourceKey.create(Registries.LEVEL_STEM,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey_world"));

    public static final ResourceKey<LevelStem> AARONCOS_ARENA_WORLD =
            ResourceKey.create(Registries.LEVEL_STEM,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_arena_world"));

    @SuppressWarnings("unchecked")
    private static MultiNoiseBiomeSource createMultiNoiseSource(
            Climate.ParameterList<Holder<Biome>> params) {
        try {
            Constructor<MultiNoiseBiomeSource> ctor = MultiNoiseBiomeSource.class
                    .getDeclaredConstructor(Either.class);
            ctor.setAccessible(true);
            return ctor.newInstance(Either.left(params));
        } catch (Exception e) {
            throw new RuntimeException("Failed to create MultiNoiseBiomeSource", e);
        }
    }

    public static void bootstrap(BootstapContext<LevelStem> context) {
        // 获取依赖注册表的引用
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);

        // 引用染梦维度群系
        Holder<Biome> dyedreamPlains = biomes.getOrThrow(ModBiomes.DYEDREAM_PLAINS);
        Holder<Biome> dyedreamMushroomMountains = biomes.getOrThrow(ModBiomes.DYEDREAM_MUSHROOM_MOUNTAINS);
        Holder<Biome> dyedreamSnowyPlains = biomes.getOrThrow(ModBiomes.DYEDREAM_SNOWY_PLAINS);
        Holder<Biome> dyedreamFrozenOcean = biomes.getOrThrow(ModBiomes.DYEDREAM_FROZEN_OCEAN);
        Holder<Biome> dyedreamOcean = biomes.getOrThrow(ModBiomes.DYEDREAM_OCEAN);
        // 引用自定义的维度类型和噪声设置
        Holder<DimensionType> dimType = dimensionTypes.getOrThrow(ModDimensionTypes.DYEDREAM_WORLD);
        Holder<NoiseGeneratorSettings> dimNoise = noiseSettings.getOrThrow(ModNoiseSettings.DYEDREAM_WORLD);

        // 旧 多噪声群系源 — 参数值域[-2,2]，大陆性C是海洋/陆地的唯一区分维度
        //  群系     温度          湿度          大陆性         侵蚀          怪异度
        //  冻洋  T[-2.0,2.0]  H[-2.0,2.0]  C[-2.0,-0.19]  E[-2.0,2.0]  W[-2.0,-0.5]
        //  暖洋  T[-2.0,2.0]  H[-2.0,2.0]  C[-2.0,-0.19]  E[-2.0,2.0]  W[-0.5, 2.0]
        //  雪原  T[-2.0,-0.1]  H[-2.0,2.0] C[-0.19,2.0]  E[-2.0,2.0]  W[-2.0,2.0]
        //  平原  T[ 0.0, 0.9]  H[-2.0,2.0] C[-0.19,2.0]  E[-2.0,2.0]  W[-2.0,2.0]
        //  菇山  T[ 1.0,2.0]  H[-0.5,2.0] C[-0.19,2.0]  E[-0.5,2.0]  W[-2.0,2.0]
        //  估算占比: 冻洋~18% 暖洋~31% 雪原~27% 平原~19% 菇山~5%

        // 修改说明 通过公式计算并用AI验证后：
        //   冻洋温度从 [-2.0, -0.2] 收窄为 [-2.0, -0.4]（让出寒冷内陆给雪原）
        //   暖洋温度保持 [0.0, 2.0]
        //   雪原温度从 [-2.0, -0.2] 扩回 [-2.0, -0.1]（扩大寒冷范围）
        //   雪原湿度从 [-2.0, -0.1] 扩大为 [-2.0, 0.5]（允许更多湿度 增加出现机会）
        //   平原温度从 [0.0, 0.7] 收窄为 [0.0, 0.5]（让出温暖区给菇山）
        //   平原湿度从 [-2.0, 2.0] 收窄为 [-0.5, 0.5]（不占用干湿区域）
        //   菇山温度从 [0.3, 2.0] 略扩为 [0.25, 2.0]
        //   菇山侵蚀保持全范围 [-2.0, 2.0]

        // 新 多噪声群系源 — 参数值域[-2,2]，大陆性C是海洋/陆地的唯一区分维度
        //  群系          温度             湿度             大陆性             侵蚀            怪异度
        //  冻洋    T[-2.0, -0.4]   H[-2.0, 2.0]    C[-2.0, -0.19]   E[-2.0, 2.0]    W[-2.0, -0.5]
        //  暖洋    T[0.0, 2.0]     H[-2.0, 2.0]    C[-2.0, -0.19]   E[-2.0, 2.0]    W[-0.5, 2.0]
        //  雪原    T[-2.0, -0.1]   H[-2.0, 0.5]    C[-0.19, 2.0]    E[-2.0, 2.0]    W[-2.0, 2.0]
        //  平原    T[0.0, 0.5]     H[-0.5, 0.5]    C[-0.19, 2.0]    E[-2.0, 2.0]    W[-2.0, 2.0]
        //  菇山    T[0.25, 2.0]    H[-0.5, 2.0]    C[-0.19, 2.0]    E[-2.0, 2.0]    W[-2.0, 2.0]

        Climate.ParameterList<Holder<Biome>> biomeParams = new Climate.ParameterList<>(List.of(
                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(-2.0F, -0.4F),      // 温度
                                Climate.Parameter.span(-2.0F, 2.0F),
                                Climate.Parameter.span(-2.0F, -0.19F),
                                Climate.Parameter.span(-2.0F, 2.0F),
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.span(-2.0F, -0.5F),
                                0L
                        ),
                        dyedreamFrozenOcean
                ),
                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(0.0F, 2.0F),
                                Climate.Parameter.span(-2.0F, 2.0F),
                                Climate.Parameter.span(-2.0F, -0.19F),
                                Climate.Parameter.span(-2.0F, 2.0F),
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.span(-0.5F, 2.0F),
                                0L
                        ),
                        dyedreamOcean
                ),
                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(-2.0F, -0.1F),      // 温度：扩大
                                Climate.Parameter.span(-2.0F, 0.5F),       // 湿度：扩大
                                Climate.Parameter.span(-0.19F, 2.0F),
                                Climate.Parameter.span(-2.0F, 2.0F),
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.span(-2.0F, 2.0F),
                                0L
                        ),
                        dyedreamSnowyPlains
                ),
                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(0.0F, 0.5F),        // 温度：收窄
                                Climate.Parameter.span(-0.5F, 0.5F),       // 湿度：收窄
                                Climate.Parameter.span(-0.19F, 2.0F),
                                Climate.Parameter.span(-2.0F, 2.0F),
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.span(-2.0F, 2.0F),
                                0L
                        ),
                        dyedreamPlains
                ),

                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(0.25F, 2.0F),       // 温度：略扩
                                Climate.Parameter.span(-0.5F, 2.0F),
                                Climate.Parameter.span(-0.19F, 2.0F),
                                Climate.Parameter.span(-2.0F, 2.0F),       // 侵蚀：全
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.span(-2.0F, 2.0F),
                                0L
                        ),
                        dyedreamMushroomMountains
                )
        ));
        MultiNoiseBiomeSource biomeSource = createMultiNoiseSource(biomeParams);
        ChunkGenerator chunkGenerator = new NoiseBasedChunkGenerator(biomeSource, dimNoise);

        context.register(DYEDREAM_WORLD, new LevelStem(dimType, chunkGenerator));

        // ===== 灯影之下维度 =====
        Holder<Biome> shadowNyliumWastes = biomes.getOrThrow(ModBiomes.SHADOW_NYLIUM_WASTES);
        Holder<Biome> shadowForest = biomes.getOrThrow(ModBiomes.SHADOW_FOREST);
        Holder<Biome> shadowRuins = biomes.getOrThrow(ModBiomes.SHADOW_RUINS);
        Holder<Biome> shadowOcean = biomes.getOrThrow(ModBiomes.SHADOW_OCEAN);
        Holder<DimensionType> lampShadowDimType = dimensionTypes.getOrThrow(ModDimensionTypes.LAMP_SHADOW_WORLD);
        Holder<NoiseGeneratorSettings> lampShadowNoise = noiseSettings.getOrThrow(ModNoiseSettings.LAMP_SHADOW_WORLD);

        Climate.ParameterList<Holder<Biome>> lampShadowBiomeParams = new Climate.ParameterList<>(List.of(
                // shadow_ocean（阴影之海）：全温度/湿度，C[-2, -0.19]海洋大陆性，约占25%
                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(-2F, 2F),
                                Climate.Parameter.span(-2F, 2F),
                                Climate.Parameter.span(-2F, -0.19F),
                                Climate.Parameter.span(-2F, 2F),
                                Climate.Parameter.span(-2F, 2F),
                                Climate.Parameter.span(-2F, 2F),
                                0L
                        ),
                        shadowOcean
                ),
                // shadow_ruins（阴影古迹）：寒冷干燥 T[-2, 0] H[-2, 0] C[-0.19, 2]，约占18.75%
                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(-2F, 0F),
                                Climate.Parameter.span(-2F, 0F),
                                Climate.Parameter.span(-0.19F, 2F),
                                Climate.Parameter.span(-2F, 2F),
                                Climate.Parameter.span(-2F, 2F),
                                Climate.Parameter.span(-2F, 2F),
                                0L
                        ),
                        shadowRuins
                ),
                // shadow_forest（阴影森林）：寒冷潮湿 T[-2, 0] H[0, 2] C[-0.19, 2]，约占18.75%
                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(-2F, 0F),
                                Climate.Parameter.span(0F, 2F),
                                Climate.Parameter.span(-0.19F, 2F),
                                Climate.Parameter.span(-2F, 2F),
                                Climate.Parameter.span(-2F, 2F),
                                Climate.Parameter.span(-2F, 2F),
                                0L
                        ),
                        shadowForest
                ),
                // shadow_nylium_wastes（菌索荒原）：温暖 T[0, 2] H[-2, 2] C[-0.19, 2]，约占37.5%
                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(0F, 2F),
                                Climate.Parameter.span(-2F, 2F),
                                Climate.Parameter.span(-0.19F, 2F),
                                Climate.Parameter.span(-2F, 2F),
                                Climate.Parameter.span(-2F, 2F),
                                Climate.Parameter.span(-2F, 2F),
                                0L
                        ),
                        shadowNyliumWastes
                )
        ));
        MultiNoiseBiomeSource lampShadowBiomeSource = createMultiNoiseSource(lampShadowBiomeParams);
        ChunkGenerator lampShadowChunkGenerator = new NoiseBasedChunkGenerator(lampShadowBiomeSource, lampShadowNoise);

        context.register(LAMP_SHADOW_WORLD, new LevelStem(lampShadowDimType, lampShadowChunkGenerator));

        // ===== 风之旅途维度 =====
        Holder<Biome> windMoorArchipelago = biomes.getOrThrow(ModBiomes.WIND_MOOR_ARCHIPELAGO);
        Holder<Biome> mistyDreamCloudLayer = biomes.getOrThrow(ModBiomes.MISTY_DREAM_CLOUD_LAYER);
        Holder<DimensionType> windJourneyDimType = dimensionTypes.getOrThrow(ModDimensionTypes.WIND_JOURNEY_WORLD);
        Holder<NoiseGeneratorSettings> windJourneyNoise = noiseSettings.getOrThrow(ModNoiseSettings.WIND_JOURNEY_WORLD);

        // 双群系（原作 multi_noise 参数）：
        //  风泊群岛 T[0,1] H[0,1] C[-0.3,1] E[0,1]
        //  迷梦云层 T[-1,0] H[-1,0] C[-1,-0.3] E[-1,0]
        Climate.ParameterList<Holder<Biome>> windJourneyBiomeParams = new Climate.ParameterList<>(List.of(
                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(0F, 1F),
                                Climate.Parameter.span(0F, 1F),
                                Climate.Parameter.span(-0.3F, 1F),
                                Climate.Parameter.span(0F, 1F),
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.span(-1F, 1F),
                                0L
                        ),
                        windMoorArchipelago
                ),
                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(-1F, 0F),
                                Climate.Parameter.span(-1F, 0F),
                                Climate.Parameter.span(-1F, -0.3F),
                                Climate.Parameter.span(-1F, 0F),
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.span(-1F, 1F),
                                0L
                        ),
                        mistyDreamCloudLayer
                )
        ));
        MultiNoiseBiomeSource windJourneyBiomeSource = createMultiNoiseSource(windJourneyBiomeParams);
        ChunkGenerator windJourneyChunkGenerator = new NoiseBasedChunkGenerator(windJourneyBiomeSource, windJourneyNoise);

        context.register(WIND_JOURNEY_WORLD, new LevelStem(windJourneyDimType, windJourneyChunkGenerator));

        // ===== 亚伦柯斯竞技场维度 =====
        Holder<Biome> aaroncosArenaBiome = biomes.getOrThrow(ModBiomes.AARONCOS_ARENA);
        Holder<DimensionType> aaroncosArenaDimType = dimensionTypes.getOrThrow(ModDimensionTypes.AARONCOS_ARENA_WORLD);
        Holder<NoiseGeneratorSettings> aaroncosArenaNoise = noiseSettings.getOrThrow(ModNoiseSettings.AARONCOS_ARENA_WORLD);

        // 单群系（原作 multi_noise 参数全 0；单群系 R-tree 恒返回唯一群系，虚空维度无覆盖问题）
        Climate.ParameterList<Holder<Biome>> aaroncosArenaBiomeParams = new Climate.ParameterList<>(List.of(

                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.point(0.0F),
                                0L
                        ),
                        aaroncosArenaBiome
                )
        ));
        MultiNoiseBiomeSource aaroncosArenaBiomeSource = createMultiNoiseSource(aaroncosArenaBiomeParams);
        ChunkGenerator aaroncosArenaChunkGenerator = new NoiseBasedChunkGenerator(aaroncosArenaBiomeSource, aaroncosArenaNoise);

        context.register(AARONCOS_ARENA_WORLD, new LevelStem(aaroncosArenaDimType, aaroncosArenaChunkGenerator));
    }
}