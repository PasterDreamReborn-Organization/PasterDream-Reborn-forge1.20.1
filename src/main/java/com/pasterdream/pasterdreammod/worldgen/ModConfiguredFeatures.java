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

    private static Holder<PlacedFeature> simpleBlockInAir(BlockStateProvider provider) {
        return PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(provider),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.AIR))
        );
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
        context.register(STEM_GRASS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(32, 16, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.STEM_GRASS.get())))));

        // 高茎草 — 原作 grass_4（分散生成）
        context.register(TALL_STEM_GRASS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(24, 16, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.TALL_STEM_GRASS.get())))));

        // 野生梦染茶花 — 原作 crop_0a（团簇生成）
        context.register(DYEDREAM_COROLLA_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(10, 4, 2,
                        simpleBlockInAir(BlockStateProvider.simple(
                        ModBlocks.DYEDREAM_COROLLA_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1))))));

        // 野生流明堇 — 原作 crop_2a（团簇生成）
        context.register(LIGHT_BALL_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(10, 4, 2,
                        simpleBlockInAir(BlockStateProvider.simple(
                        ModBlocks.LIGHT_BALL_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1))))));

        // 野生玲云花 — 原作 crop_3a（团簇生成）
        context.register(CLOUD_CROP_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(10, 4, 2,
                        simpleBlockInAir(BlockStateProvider.simple(
                        ModBlocks.CLOUD_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1))))));
    }
}
