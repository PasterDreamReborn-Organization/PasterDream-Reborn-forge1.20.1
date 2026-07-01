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
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.lang.reflect.Constructor;
import java.util.List;

public class ModLevelStems {

    public static final ResourceKey<LevelStem> DYEDREAM_WORLD =
            ResourceKey.create(Registries.LEVEL_STEM,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world"));

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

        // 多噪声群系源（按温度/湿度/大陆性/侵蚀/怪异度分布群系）
        // 格式: [temperature, humidity, continentalness, erosion, weirdness], depth, offset
        Climate.ParameterList<Holder<Biome>> biomeParams = new Climate.ParameterList<>(List.of(
                // 染梦平原 — 原作: T[-0.35,0.1] H[-0.1,0.3] C[-0.11,0.5] E[-0.19,0.3] W[-0.85,0.2]
                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(-0.35F, 0.1F),
                                Climate.Parameter.span(-0.0999F, 0.3F),
                                Climate.Parameter.span(-0.11F, 0.5F),
                                Climate.Parameter.span(-0.19F, 0.3F),
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.span(-0.85F, 0.2F),
                                0L
                        ),
                        dyedreamPlains
                ),
                // 粉顶菇山地 — 原作: T[0.1,0.5] H[0.3,1.2] C[0.5,1.2] E[0.25,0.7] W[0.2,0.6]
                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(0.1F, 0.5F),
                                Climate.Parameter.span(0.3F, 1.2F),
                                Climate.Parameter.span(0.5F, 1.2F),
                                Climate.Parameter.span(0.25F, 0.7F),
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.span(0.2F, 0.6F),
                                0L
                        ),
                        dyedreamMushroomMountains
                ),
                // 染梦雪原 — 原作: T[-1.5,-0.4] H[-0.35,-0.1] C[0.2,0.5] E[0.7,1.0] W[0.2,1.0]
                // 注：已适当扩大范围避免群系过于零碎
                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(-1.2F, -0.15F),
                                Climate.Parameter.span(-0.4F, 0.2F),
                                Climate.Parameter.span(0.05F, 0.55F),
                                Climate.Parameter.span(0.35F, 1.0F),
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.span(0.0F, 1.0F),
                                0L
                        ),
                        dyedreamSnowyPlains
                ),
                // 染梦冻洋 — 原作: T[0.5,1.0] H[-1,1] C[-1.05,-0.11] E[-1,1] W[-1,-0.85]
                // 注：continentalness 限定海洋范围[-1.05,-0.19]，不再与平原海岸线重叠
                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(0.5F, 1.0F),
                                Climate.Parameter.span(-1.0F, 1.0F),
                                Climate.Parameter.span(-1.05F, -0.19F),
                                Climate.Parameter.span(-1.0F, 1.0F),
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.span(-1.0F, -0.85F),
                                0L
                        ),
                        dyedreamFrozenOcean
                ),
                // 染梦海洋 — 与冻洋争夺海洋空间，weirdness 相邻区间
                Pair.of(
                        new Climate.ParameterPoint(
                                Climate.Parameter.span(0.5F, 1.0F),
                                Climate.Parameter.span(-1.0F, 1.0F),
                                Climate.Parameter.span(-1.05F, -0.19F),
                                Climate.Parameter.span(-1.0F, 1.0F),
                                Climate.Parameter.point(0.0F),
                                Climate.Parameter.span(-0.45F, 0.0F),
                                0L
                        ),
                        dyedreamOcean
                )
        ));
        MultiNoiseBiomeSource biomeSource = createMultiNoiseSource(biomeParams);
        ChunkGenerator chunkGenerator = new NoiseBasedChunkGenerator(biomeSource, dimNoise);

        context.register(DYEDREAM_WORLD, new LevelStem(dimType, chunkGenerator));
    }
}
