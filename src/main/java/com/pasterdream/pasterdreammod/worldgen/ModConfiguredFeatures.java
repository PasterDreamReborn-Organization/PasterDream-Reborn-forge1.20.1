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

    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDENROD_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "goldenrod_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> FERRARIA_CRISPA_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "ferraria_crispa_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> MALVA_SINENSIS_CAVAN_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "malva_sinensis_cavan_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> EUSTOMA_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "eustoma_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> OATS_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "oats_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> RYE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "rye_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_COROLLA_CROP_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "white_corolla_crop_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> COTTON_CROP_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "cotton_crop_patch"));

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

        //秋麒麟
        context.register(GOLDENROD_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(12, 6, 3, simpleBlockInAir(BlockStateProvider.simple(ModBlocks.GOLDENROD.get())))));

        //魔星兰
        context.register(FERRARIA_CRISPA_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(12, 6, 3, simpleBlockInAir(BlockStateProvider.simple(ModBlocks.FERRARIA_CRISPA.get())))));

        //锦葵
        context.register(MALVA_SINENSIS_CAVAN_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(12, 6, 3, simpleBlockInAir(BlockStateProvider.simple(ModBlocks.MALVA_SINENSIS_CAVAN.get())))));

        //洋桔梗
        context.register(EUSTOMA_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(12, 6, 3, simpleBlockInAir(BlockStateProvider.simple(ModBlocks.EUSTOMA.get())))));

        //洋麦
        context.register(OATS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(12, 6, 3, simpleBlockInAir(BlockStateProvider.simple(ModBlocks.OATS.get())))));

        //兰麦
        context.register(RYE_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(12, 6, 3, simpleBlockInAir(BlockStateProvider.simple(ModBlocks.RYE.get())))));

        //苍白雪莲植株
        context.register(WHITE_COROLLA_CROP_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(8, 4, 1, simpleBlockInAir(BlockStateProvider.simple(ModBlocks.WHITE_COROLLA_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1))))));

        //棉花植株
        context.register(COTTON_CROP_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(12, 6, 3, simpleBlockInAir(BlockStateProvider.simple(ModBlocks.COTTON_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1))))));
    }
}
