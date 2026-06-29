package com.pasterdream.pasterdreammod.worldgen;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import com.pasterdream.pasterdreammod.world.block.cropblock.PasterDreamCropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;

public class ModConfiguredFeatures {

    // ===== 染梦平原 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_TREE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_ICE_PILLAR =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_ice_pillar"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_ICESTONE_BLOBS =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_icestone_blobs"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> STEM_GRASS_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "stem_grass_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_STEM_GRASS_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "tall_stem_grass_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_COROLLA_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_corolla_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIGHT_BALL_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "light_ball_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUD_CROP_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "cloud_crop_patch"));
    // ===== 染梦维度矿石 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> TITANIUM_ORE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> AMBER_CANDY_ORE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "amber_candy_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_DUST_ORE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_dust_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_QUARTZ_ORE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_quartz_ore"));
    // ===== 花卉 / 方解石笋 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_LILY_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_lily_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> DREAMING_LOTUS_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dreaming_lotus_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_STALICRIPE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "calcite_stalicripe"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_CALCITE_STALICRIPE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "small_calcite_stalicripe"));

    private static Holder<PlacedFeature> simpleBlockInAir(BlockStateProvider provider) {
        return PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(provider),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.AIR))
        );
    }

    /** 方解石替换目标（单一目标） */
    private static final RuleTest TARGET_CALCITE = new BlockMatchTest(Blocks.CALCITE);
    /** 方解石 + 石头变种替换目标 */
    private static final List<RuleTest> TARGET_CALCITE_AND_STONES = List.of(
            new BlockMatchTest(Blocks.CALCITE),
            new BlockMatchTest(Blocks.STONE),
            new BlockMatchTest(Blocks.GRANITE),
            new BlockMatchTest(Blocks.DIORITE),
            new BlockMatchTest(Blocks.ANDESITE)
    );

    private static List<OreConfiguration.TargetBlockState> oreTargets(Block oreBlock, List<RuleTest> ruleTests) {
        return ruleTests.stream()
                .<OreConfiguration.TargetBlockState>map(rt -> OreConfiguration.target(rt, oreBlock.defaultBlockState()))
                .toList();
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(DYEDREAM_TREE, new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.DYEDREAM_LOG.get()),
                        new ForkingTrunkPlacer(7, 2, 2),
                        BlockStateProvider.simple(ModBlocks.DYEDREAM_LEAVES.get()),
                        new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 2))
                        .ignoreVines()
                        .build()));

        context.register(DYEDREAM_ICE_PILLAR, new ConfiguredFeature<>(Feature.BLOCK_COLUMN,
                new BlockColumnConfiguration(
                        List.of(new BlockColumnConfiguration.Layer(
                                UniformInt.of(4, 11),
                                BlockStateProvider.simple(ModBlocks.DYEDREAM_ICE.get())
                        )),
                        Direction.UP,
                        BlockPredicate.matchesBlocks(Blocks.AIR),
                        false
                )));

        context.register(DYEDREAM_ICESTONE_BLOBS, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(
                        List.of(OreConfiguration.target(
                                new BlockMatchTest(Blocks.CALCITE),
                                ModBlocks.ICE_STONE.get().defaultBlockState()
                        )),
                        9,
                        0f
                )));

        // 茎草 — 原作 grass_3（分散生成）
        // 三个参数是：尝试生成次数，水平扩散半径，垂直扩散半径
        context.register(STEM_GRASS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(32, 16, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.STEM_GRASS.get())))));

        // 高茎草 — 原作 grass_4（分散生成）
        context.register(TALL_STEM_GRASS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(24, 16, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.TALL_STEM_GRASS.get())))));

        // 野生梦染茶花 — 原作 crop_0a（团簇生成）
        context.register(DYEDREAM_COROLLA_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(20, 5, 5,
                        simpleBlockInAir(BlockStateProvider.simple(
                        ModBlocks.DYEDREAM_COROLLA_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1))))));

        // 野生流明堇 — 原作 crop_2a（团簇生成）
        context.register(LIGHT_BALL_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(20, 5, 5,
                        simpleBlockInAir(BlockStateProvider.simple(
                        ModBlocks.LIGHT_BALL_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1))))));

        // 野生玲云花 — 原作 crop_3a（团簇生成）
        context.register(CLOUD_CROP_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(20, 5, 5,
                        simpleBlockInAir(BlockStateProvider.simple(
                        ModBlocks.CLOUD_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1))))));
        // 染梦铃兰 - 原作 flower_13
        context.register(DYEDREAM_LILY_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(20, 5, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get())))));
        // 冶梦莲 - 原作 flower_11
        context.register(DREAMING_LOTUS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(25, 5, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.DREAMING_LOTUS.get())))));


        // ===== 染梦维度矿石 =====
        // 钛矿 — 原作 size=3, 钻石分布
        context.register(TITANIUM_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.TITANIUM_ORE.get(), List.of(TARGET_CALCITE)), 8, 0f)));
        // 琥珀糖矿 — size=24
        context.register(AMBER_CANDY_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.AMBER_CANDY_ORE.get(), List.of(TARGET_CALCITE)), 24, 0f)));
        // 染梦尘矿 — size=16
        context.register(DYEDREAM_DUST_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.DYEDREAM_DUST_ORE.get(), TARGET_CALCITE_AND_STONES), 16, 0f)));
        // 染梦石英矿 — size=28
        context.register(DYEDREAM_QUARTZ_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.DYEDREAM_QUARTZ_ORE.get(), TARGET_CALCITE_AND_STONES), 28, 0f)));

        // ===== 方解石笋 =====
        context.register(CALCITE_STALICRIPE, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(16, 4, 4,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.POLISHED_CALCITE_STALICRIPE.get())))));
        context.register(SMALL_CALCITE_STALICRIPE, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(16, 4, 4,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.SMALL_POLISHED_CALCITE_STALICRIPE.get())))));
    }
}
