package com.pasterdream.pasterdreammod.worldgen.dimension;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.worldgen.biome.ModBiomes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class ModNoiseSettings {

    public static final ResourceKey<NoiseGeneratorSettings> DYEDREAM_WORLD =
            ResourceKey.create(Registries.NOISE_SETTINGS,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world"));

    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> context) {
        // 复用主世界的完整噪声路由器（洞穴、含水层、矿脉、地形起伏等）
        NoiseGeneratorSettings overworld = NoiseGeneratorSettings.overworld(context, false, false);

        context.register(DYEDREAM_WORLD, new NoiseGeneratorSettings(
                overworld.noiseSettings(),                       // 噪声采样（-64~320, xz=1, y=2）
                Blocks.CALCITE.defaultBlockState(),              // 默认方块：方解石（原作）
                overworld.defaultFluid(),                        // 默认流体：水
                overworld.noiseRouter(),                         // 主世界噪声路由器
                makeSurfaceRules(),                              // 染梦平原地表规则
                overworld.spawnTarget(),                         // 无特殊生成目标
                overworld.seaLevel(),                            // 海平面 63
                overworld.disableMobGeneration(),
                overworld.aquifersEnabled(),
                overworld.oreVeinsEnabled(),
                overworld.useLegacyRandomSource()
        ));
    }

    private static SurfaceRules.RuleSource makeSurfaceRules() {
        return SurfaceRules.sequence(
                // 基岩底层：y=-64 ~ y=-59
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("minecraft:bedrock_floor",
                                VerticalAnchor.aboveBottom(0),
                                VerticalAnchor.aboveBottom(5)),
                        SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())
                ),
                // 染梦平原 / 粉顶菇山地 / 染梦雪原地表
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.DYEDREAM_PLAINS, ModBiomes.DYEDREAM_MUSHROOM_MOUNTAINS, ModBiomes.DYEDREAM_SNOWY_PLAINS),
                        SurfaceRules.ifTrue(
                                SurfaceRules.abovePreliminarySurface(),
                                SurfaceRules.sequence(
                                        // 顶层判定
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.ON_FLOOR,
                                                SurfaceRules.sequence(
                                                        // 干燥陆地 → 染梦草方块
                                                        SurfaceRules.ifTrue(
                                                                SurfaceRules.waterBlockCheck(-1, 0),
                                                                SurfaceRules.state(ModBlocks.DYEDREAM_GRASS_BLOCK.get().defaultBlockState())
                                                        ),
                                                        // 水底 → 染梦沙
                                                        SurfaceRules.state(ModBlocks.DYEDREAM_SAND.get().defaultBlockState())
                                                )
                                        ),
                                        // 下层 → 染梦土
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.UNDER_FLOOR,
                                                SurfaceRules.state(ModBlocks.DYEDREAM_DIRT.get().defaultBlockState())
                                        )
                                )
                        )
                ),
                // 染梦冻洋地表（细雪岸 + 浮冰下层）
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.DYEDREAM_FROZEN_OCEAN),
                        SurfaceRules.ifTrue(
                                SurfaceRules.abovePreliminarySurface(),
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.ON_FLOOR,
                                                SurfaceRules.sequence(
                                                        // 干燥陆地 → 细雪
                                                        SurfaceRules.ifTrue(
                                                                SurfaceRules.waterBlockCheck(-1, 0),
                                                                SurfaceRules.state(Blocks.POWDER_SNOW.defaultBlockState())
                                                        ),
                                                        // 水底 → 染梦沙
                                                        SurfaceRules.state(ModBlocks.DYEDREAM_SAND.get().defaultBlockState())
                                                )
                                        ),
                                        // 下层 → 浮冰
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.UNDER_FLOOR,
                                                SurfaceRules.state(Blocks.PACKED_ICE.defaultBlockState())
                                        )
                                )
                        )
                )
        );
    }
}
