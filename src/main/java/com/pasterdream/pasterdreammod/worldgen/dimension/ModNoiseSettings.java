package com.pasterdream.pasterdreammod.worldgen.dimension;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.worldgen.biome.ModBiomes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;

public class ModNoiseSettings {

    public static final ResourceKey<NoiseGeneratorSettings> DYEDREAM_WORLD =
            ResourceKey.create(Registries.NOISE_SETTINGS,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world"));

    public static final ResourceKey<NoiseGeneratorSettings> LAMP_SHADOW_WORLD =
            ResourceKey.create(Registries.NOISE_SETTINGS,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"));

    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> context) {
        // 复用主世界的完整噪声路由器（洞穴、含水层、矿脉、地形起伏等）
        NoiseGeneratorSettings overworld = NoiseGeneratorSettings.overworld(context, false, false);
        NoiseRouter originalRouter = overworld.noiseRouter();

        DensityFunction baseTerrain = originalRouter.initialDensityWithoutJaggedness();
        DensityFunction smoothTerrain = DensityFunctions.interpolated(baseTerrain);

        NoiseRouter modifiedRouter = new NoiseRouter(
                originalRouter.barrierNoise(),
                DensityFunctions.constant(-1.0D),         //含水层水量（禁用）
                DensityFunctions.constant(0.0D),          //含水层扩散（禁用）
                DensityFunctions.constant(0.0D),          //熔岩湖（禁用）
                originalRouter.temperature(),                       //温度
                originalRouter.vegetation(),                        //植被
                originalRouter.continents(),                        //大陆性
                originalRouter.erosion(),                           //侵蚀
                originalRouter.depth(),                             //深度
                originalRouter.ridges(),                            //山脊
                smoothTerrain,                                      //初始密度（平滑插值，用于群系放置）
                originalRouter.finalDensity(),                      //最终密度（原版，含地形噪声与洞穴）
                DensityFunctions.constant(1.0D),          //矿脉开关→1（禁用）
                DensityFunctions.constant(1.0D),          //矿脉脊状→1（禁用）
                DensityFunctions.constant(1.0D)           //矿脉间隙→1（禁用）
        );

        context.register(DYEDREAM_WORLD, new NoiseGeneratorSettings(
                overworld.noiseSettings(),                      //噪声采样（-64~320, xz=1, y=2）
                Blocks.CALCITE.defaultBlockState(),             //默认方块：方解石
                overworld.defaultFluid(),                       //默认流体：水
                modifiedRouter,                                 //使用修改后的噪声路由器
                makeDyedeamSurfaceRules(),                             //染梦平原地表规则
                overworld.spawnTarget(),                        //无特殊生成目标
                overworld.seaLevel(),                           //海平面 63
                overworld.disableMobGeneration(),
                overworld.aquifersEnabled(),
                false,                            //禁用矿脉生成
                overworld.useLegacyRandomSource()
        ));

        // 灯影之下维度噪声设置（基于主世界噪声，禁用含水层/熔岩湖/矿脉）
        NoiseGeneratorSettings lampShadowOverworld = NoiseGeneratorSettings.overworld(context, false, false);
        NoiseRouter lampShadowOriginalRouter = lampShadowOverworld.noiseRouter();

        DensityFunction lampShadowBaseTerrain = lampShadowOriginalRouter.initialDensityWithoutJaggedness();
        DensityFunction lampShadowSmoothTerrain = DensityFunctions.interpolated(lampShadowBaseTerrain);

        NoiseRouter lampShadowRouter = new NoiseRouter(
                lampShadowOriginalRouter.barrierNoise(),
                DensityFunctions.constant(-1.0D),          //含水层水量（禁用）
                DensityFunctions.constant(0.0D),           //含水层扩散（禁用）
                DensityFunctions.constant(0.0D),           //熔岩湖（禁用）
                lampShadowOriginalRouter.temperature(),
                lampShadowOriginalRouter.vegetation(),
                lampShadowOriginalRouter.continents(),
                lampShadowOriginalRouter.erosion(),
                lampShadowOriginalRouter.depth(),
                lampShadowOriginalRouter.ridges(),
                lampShadowSmoothTerrain,                            //初始密度（平滑，用于群系放置）
                lampShadowOriginalRouter.finalDensity(),            //最终密度（原版，含jaggedness+洞穴：补充地表起伏确保陆地高于海平面）
                DensityFunctions.constant(1.0D),           //矿脉开关→1（禁用）
                DensityFunctions.constant(1.0D),           //矿脉脊状→1（禁用）
                DensityFunctions.constant(1.0D)            //矿脉间隙→1（禁用）
        );

        context.register(LAMP_SHADOW_WORLD, new NoiseGeneratorSettings(
                lampShadowOverworld.noiseSettings(),
                ModBlocks.SHADOW_STONE.get().defaultBlockState(),   //默认方块：阴影石
                ModBlocks.SHADOW_LIQUID.get().defaultBlockState(),   //默认流体：影液
                lampShadowRouter,                                   //修改后的噪声路由
                makeLampShadowSurfaceRules(),                       //灯影之下地表规则
                lampShadowOverworld.spawnTarget(),
                63,  //海平面
                false,                                              //启用怪物生成
                false,                                              //禁用含水层
                false,                                              //禁用矿脉
                lampShadowOverworld.useLegacyRandomSource()
        ));

    }

    private static SurfaceRules.RuleSource makeDyedeamSurfaceRules() {
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
                        SurfaceRules.isBiome(ModBiomes.DYEDREAM_PLAINS, ModBiomes.DYEDREAM_MUSHROOM_MOUNTAINS, ModBiomes.DYEDREAM_SNOWY_PLAINS, ModBiomes.DYEDREAM_OCEAN),
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

    private static SurfaceRules.RuleSource makeLampShadowSurfaceRules() {
        return SurfaceRules.sequence(
                // 基岩底层：y=-64 ~ y=-59
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("minecraft:bedrock_floor",
                                VerticalAnchor.aboveBottom(0),
                                VerticalAnchor.aboveBottom(5)),
                        SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())
                ),
                // 全部灯影群系：地表统一 shadow_nylium / 水下 shadow_stone，下层 shadow_stone（群系通过地物区分）
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.SHADOW_NYLIUM_WASTES, ModBiomes.SHADOW_FOREST, ModBiomes.SHADOW_RUINS, ModBiomes.SHADOW_OCEAN),
                        SurfaceRules.ifTrue(
                                SurfaceRules.abovePreliminarySurface(),
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.ON_FLOOR,
                                                SurfaceRules.sequence(
                                                        SurfaceRules.ifTrue(
                                                                SurfaceRules.waterBlockCheck(-1, 0),
                                                                SurfaceRules.state(ModBlocks.SHADOW_NYLIUM.get().defaultBlockState())
                                                        ),
                                                        SurfaceRules.state(ModBlocks.SHADOW_STONE.get().defaultBlockState())
                                                )
                                        ),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.UNDER_FLOOR,
                                                SurfaceRules.state(ModBlocks.SHADOW_STONE.get().defaultBlockState())
                                        )
                                )
                        )
                )
        );
    }
}
