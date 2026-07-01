package com.pasterdream.pasterdreammod.worldgen;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModFeatures;
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
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import com.pasterdream.pasterdreammod.worldgen.feature.PinkShroomlightTreeDecorator;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import com.pasterdream.pasterdreammod.world.block.cropblock.PasterDreamCropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;

public class ModConfiguredFeatures {

    // ===== 染梦平原 =====
    // 染梦树
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_TREE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_tree"));
    // 粉顶菌巨树 (丛林树形态, 2×2)
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_MUSHROOM_TREE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "pink_mushroom_tree"));
    // 粉顶菌巨菇 (红蘑菇形态, 单株)
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_HUGE_MUSHROOM =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "pink_huge_mushroom"));
    // 染梦冰柱
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_ICE_PILLAR =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_ice_pillar"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_PACKED_ICE_PILLAR =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_packed_ice_pillar"));
    // 雪块底水池 — 原作 ground_feature_dyedream_1
    public static final ResourceKey<ConfiguredFeature<?, ?>> SNOWY_WATER_POOL =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "snowy_water_pool"));
    // ===== 团块 =====
    // 冰晶石团块
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_ICE_STONE_BLOBS =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_ice_stone_blobs"));
    // 方解石团块 — 原作 ground_feature_dyedream_15
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_BOULDER =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "calcite_boulder"));
    // 染梦冰团块
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_ICE_BLOBS =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_ice_blobs"));
    // 染梦浮冰团块
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_PACKED_ICE_BLOBS =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_packed_ice_blobs"));
    // 原版冰团块
    public static final ResourceKey<ConfiguredFeature<?, ?>> VANILLA_ICE_BLOBS =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "vanilla_ice_blobs"));
    // 原版浮冰团块
    public static final ResourceKey<ConfiguredFeature<?, ?>> VANILLA_PACKED_ICE_BLOBS =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "vanilla_packed_ice_blobs"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_MOSS_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_moss_patch"));
    // ===== 云团柱 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUD_PILLAR_SMALL = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "cloud_pillar_small"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUD_PILLAR_LARGE = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "cloud_pillar_large"));
    // ===== 水下装饰 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORAL_TREE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "coral_tree_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORAL_CLAW_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "coral_claw_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORAL_MUSHROOM_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "coral_mushroom_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> SEA_PICKLE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sea_pickle_patch"));
    // 染梦海草 — 原作 ground_feature_dyedream_5
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_SEAGRASS_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_seagrass_patch"));
    // ===== 洞穴晶芽 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_DYEDREAM_BUD_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "small_dyedream_bud_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEDIUM_DYEDREAM_BUD_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "medium_dyedream_bud_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_DYEDREAM_BUD_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "large_dyedream_bud_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> ICE_BUD_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "ice_bud_patch"));

    // ==== 染梦维度花草 =====
    // 茎草
    public static final ResourceKey<ConfiguredFeature<?, ?>> STEM_GRASS_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "stem_grass_patch"));
    // 高茎草
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_STEM_GRASS_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "tall_stem_grass_patch"));
    // 梦染茶花
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_COROLLA_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_corolla_patch"));
    // 流明堇
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIGHT_BALL_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "light_ball_patch"));
    // 玲云花
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUD_CROP_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "cloud_crop_patch"));
    // 染梦铃兰
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_LILY_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_lily_patch"));
    // 粉顶菌 (小型, 地表)
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_MUSHROOM_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "pink_mushroom_patch"));
    // 高粉顶菌 (高草丛形态, 地表)
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_PINK_MUSHROOM_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "tall_pink_mushroom_patch"));
    // 冶梦莲
    public static final ResourceKey<ConfiguredFeature<?, ?>> DREAMING_LOTUS_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dreaming_lotus_patch"));
    // 雪绒花
    public static final ResourceKey<ConfiguredFeature<?, ?>> EDELWEISS_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "edelweiss_patch"));
    // 奇异蕨
    public static final ResourceKey<ConfiguredFeature<?, ?>> SINGULARITY_FERN_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "singularity_fern_patch"));
    // 苓灯花
    public static final ResourceKey<ConfiguredFeature<?, ?>> LINHT_FLOWER_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "linht_flower_patch"));

    // ===== 原版维度花草 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDENROD_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "goldenrod_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> FERRARIA_CRISPA_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "ferraria_crispa_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> MALVA_SINENSIS_CAVAN_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "malva_sinensis_cavan_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> EUSTOMA_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "eustoma_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> OATS_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "oats_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> RYE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "rye_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_COROLLA_CROP_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "white_corolla_crop_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> COTTON_CROP_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "cotton_crop_patch"));

    //原版维度矿石
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_TITANIUM_ORE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "deepslate_titanium_ore_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOLTEN_GOLD_ORE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "molten_gold_ore_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> SOUL_ORE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "soul_ore_patch"));

    // ===== 染梦维度矿石 =====
    // 钛矿
    public static final ResourceKey<ConfiguredFeature<?, ?>> TITANIUM_ORE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium_ore"));
    // 琥珀糖矿
    public static final ResourceKey<ConfiguredFeature<?, ?>> AMBER_CANDY_ORE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "amber_candy_ore"));
    // 染梦粉尘矿
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_DUST_ORE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_dust_ore"));
    // 染梦石英矿
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_QUARTZ_ORE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_quartz_ore"));
    // ===== 染梦睡莲 / 染梦莲花 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_LILY_PAD_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_lily_pad"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_LOTUS_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_lotus"));

    // ===== 方解石笋 =====
    // 方解石笋
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

    private static final List<Block> DYEDREAM_BUD_GROUND = List.of(
            ModBlocks.DYEDREAM_DIRT.get(),
            ModBlocks.DYEDREAM_GRASS_BLOCK.get(),
            Blocks.CALCITE,
            ModBlocks.DYEDREAM_BUDDING_BLOCK.get()
    );

    /** 冰晶芽地面：不含母岩 */
    private static final List<Block> ICE_BUD_GROUND = List.of(
            ModBlocks.DYEDREAM_DIRT.get(),
            ModBlocks.DYEDREAM_GRASS_BLOCK.get(),
            ModBlocks.ICE_STONE.get(),
            Blocks.CALCITE
    );

    /** 晶芽专用的生成辅助：同时检查目标位置为空气、下方方块在允许列表内 */
    private static Holder<PlacedFeature> simpleBudInAir(BlockStateProvider provider, List<Block> groundBlocks) {
        return PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(provider),
                BlockPredicateFilter.forPredicate(
                        BlockPredicate.allOf(List.of(
                                BlockPredicate.matchesBlocks(Blocks.AIR),
                                BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), groundBlocks)
                        ))
                )
        );
    }

    private static Holder<PlacedFeature> simpleBlockInWater(BlockStateProvider provider) {
        return PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(provider),
                BlockPredicateFilter.forPredicate(
                        BlockPredicate.allOf(List.of(
                                BlockPredicate.matchesBlocks(Blocks.WATER),
                                BlockPredicate.solid(Direction.DOWN.getNormal())
                        ))
                )
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

    //深层钛矿石替换：深板岩，花岗岩，闪长岩，安山岩
    private static final List<RuleTest> DEEPSLATE_TITANIUM_ORE_CAN_REPLACE = List.of(new BlockMatchTest(Blocks.DEEPSLATE), new BlockMatchTest(Blocks.GRANITE), new BlockMatchTest(Blocks.DIORITE), new BlockMatchTest(Blocks.ANDESITE));

    //炙焰金矿石替换：下界岩
    private static final List<RuleTest> MOLTEN_GOLD_ORE_CAN_REPLACE = List.of(new BlockMatchTest(Blocks.NETHERRACK));

    //灵魂矿土替换：灵魂土
    private static final List<RuleTest> SOUL_ORE_CAN_REPLACE = List.of(new BlockMatchTest(Blocks.SOUL_SOIL));

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

        // 粉顶菌巨树 — 2×2 骨粉催熟，丛林树形态
        context.register(PINK_MUSHROOM_TREE, new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.PINK_MUSHROOM_STEM.get()),
                        new MegaJungleTrunkPlacer(7, 2, 12),  // 7~21 格
                        BlockStateProvider.simple(ModBlocks.PINK_MUSHROOM_BLOCK.get()),
                        new MegaJungleFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2),
                        new TwoLayersFeatureSize(1, 0, 2))
                        .decorators(List.of(PinkShroomlightTreeDecorator.INSTANCE))
                        .ignoreVines()
                        .dirt(BlockStateProvider.simple(ModBlocks.DYEDREAM_DIRT.get()))
                        .build()));

        // 粉顶菌巨菇 — 单株骨粉催熟，使用自定义特征附加菌光体
        context.register(PINK_HUGE_MUSHROOM, new ConfiguredFeature<>(ModFeatures.PINK_HUGE_MUSHROOM.get(),
                new HugeMushroomFeatureConfiguration(
                        BlockStateProvider.simple(ModBlocks.PINK_MUSHROOM_BLOCK.get()),
                        BlockStateProvider.simple(ModBlocks.PINK_MUSHROOM_STEM.get()),
                        3)));

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

        context.register(DYEDREAM_PACKED_ICE_PILLAR, new ConfiguredFeature<>(Feature.BLOCK_COLUMN,
                new BlockColumnConfiguration(
                        List.of(new BlockColumnConfiguration.Layer(
                                UniformInt.of(4, 11),
                                BlockStateProvider.simple(ModBlocks.DYEDREAM_PACKED_ICE.get())
                        )),
                        Direction.UP,
                        BlockPredicate.matchesBlocks(Blocks.AIR),
                        false
                )));

        // 雪块底水池 — 原作 ground_feature_dyedream_1
        context.register(SNOWY_WATER_POOL, new ConfiguredFeature<>(Feature.LAKE,
                new LakeFeature.Configuration(
                        BlockStateProvider.simple(Blocks.WATER),
                        BlockStateProvider.simple(Blocks.SNOW_BLOCK)
                )));


        context.register(DYEDREAM_ICE_STONE_BLOBS, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(
                        List.of(OreConfiguration.target(
                                new BlockMatchTest(Blocks.CALCITE),
                                ModBlocks.ICE_STONE.get().defaultBlockState()
                        )),
                        64,
                        0.5f
                )));
        // 染梦冰团块 — size=60, PL + MM
        context.register(DYEDREAM_ICE_BLOBS, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(
                        List.of(OreConfiguration.target(
                                new BlockMatchTest(Blocks.CALCITE),
                                ModBlocks.DYEDREAM_ICE.get().defaultBlockState()
                        )),
                        64,
                        0.5f
                )));
        // 染梦浮冰团块 — size=6, PL + MM
        context.register(DYEDREAM_PACKED_ICE_BLOBS, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(
                        List.of(OreConfiguration.target(
                                new BlockMatchTest(Blocks.CALCITE),
                                ModBlocks.DYEDREAM_PACKED_ICE.get().defaultBlockState()
                        )),
                        64,
                        0.5f
                )));
        // 原版冰团块 — size=6, SP + FO
        context.register(VANILLA_ICE_BLOBS, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(
                        List.of(OreConfiguration.target(
                                new BlockMatchTest(Blocks.CALCITE),
                                Blocks.ICE.defaultBlockState()
                        )),
                        64,
                        0.5f
                )));
        // 原版浮冰团块 — size=6, SP + FO
        context.register(VANILLA_PACKED_ICE_BLOBS, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(
                        List.of(OreConfiguration.target(
                                new BlockMatchTest(Blocks.CALCITE),
                                Blocks.PACKED_ICE.defaultBlockState()
                        )),
                        64,
                        0.5f
                )));
        // 方解石团块 — 原作 ground_feature_dyedream_15
        context.register(CALCITE_BOULDER, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(
                        List.of(
                                OreConfiguration.target(new BlockMatchTest(ModBlocks.DYEDREAM_GRASS_BLOCK.get()), Blocks.CALCITE.defaultBlockState()),
                                OreConfiguration.target(new BlockMatchTest(ModBlocks.DYEDREAM_DIRT.get()), Blocks.CALCITE.defaultBlockState()),
                                OreConfiguration.target(new BlockMatchTest(ModBlocks.DYEDREAM_SAND.get()), Blocks.CALCITE.defaultBlockState()),
                                OreConfiguration.target(new BlockMatchTest(Blocks.AIR), Blocks.CALCITE.defaultBlockState()),
                                OreConfiguration.target(new BlockMatchTest(Blocks.CAVE_AIR), Blocks.CALCITE.defaultBlockState())
                        ),
                        24,
                        0.0f
                )));

        // ===== 染梦维度花草 =====
        // 茎草 — 原作 grass_3（分散生成）
        // 三个参数是：尝试生成次数，水平扩散半径，垂直扩散半径
        context.register(STEM_GRASS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(32, 16, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.STEM_GRASS.get())))));

        // 高茎草 — 原作 grass_4（分散生成）
        context.register(TALL_STEM_GRASS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(24, 16, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.TALL_STEM_GRASS.get())))));

        // 粉顶菌 — 分散生成，类似草
        context.register(PINK_MUSHROOM_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(32, 16, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.PINK_MUSHROOM.get())))));

        // 高粉顶菌 — 分散生成，类似高草
        context.register(TALL_PINK_MUSHROOM_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(24, 16, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.TALL_PINK_MUSHROOM.get())))));

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

        //雪绒花 — 原作 flower_16
        context.register(EDELWEISS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(25, 6, 6,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.EDELWEISS.get())))));
        //奇异蕨 — 原作 flower_14
        context.register(SINGULARITY_FERN_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(20, 5, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.SINGULARITY_FERN.get())))));
        //苓灯花 — 原作 flower_9
        context.register(LINHT_FLOWER_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(20, 5, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.LINHT_FLOWER.get())))));


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

        //深层钛矿石
        context.register(DEEPSLATE_TITANIUM_ORE_PATCH, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(oreTargets(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(), DEEPSLATE_TITANIUM_ORE_CAN_REPLACE), 8, 0f)));

        //炙焰金矿石
        context.register(MOLTEN_GOLD_ORE_PATCH, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(oreTargets(ModBlocks.MOLTEN_GOLD_ORE.get(), MOLTEN_GOLD_ORE_CAN_REPLACE), 16, 0f)));

        //灵魂矿土
        context.register(SOUL_ORE_PATCH, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(oreTargets(ModBlocks.SOUL_ORE.get(), SOUL_ORE_CAN_REPLACE), 16, 0f)));

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

        // 染梦睡莲 — 原作 dyedream_lily_pad: tries=32
        context.register(DYEDREAM_LILY_PAD_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(32, 7, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.DYEDREAM_LILY_PAD.get())))));
        // 染梦莲花 — 原作 dyedream_lotus: tries=24
        context.register(DYEDREAM_LOTUS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(24, 6, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.DYEDREAM_LOTUS.get())))));

        // ===== 方解石笋（同草类分散生成） =====
        context.register(CALCITE_STALICRIPE, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 8, 8,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.POLISHED_CALCITE_STALICRIPE.get())))));
        context.register(SMALL_CALCITE_STALICRIPE, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 8, 8,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.SMALL_POLISHED_CALCITE_STALICRIPE.get())))));

        // 染梦苔藓 — 原作 grass_7，同方解石笋
        context.register(DYEDREAM_MOSS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 8, 8,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.DYEDREAM_MOSS.get())))));

        // ===== 云团柱 =====
        // 小云团柱 — 原作 ground_feature_dyedream_2: tries=64, xz=2, y=24
        context.register(CLOUD_PILLAR_SMALL, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(64, 2, 24,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.CLOUD.get())))));
        // 大云团柱 — 原作 ground_feature_dyedream_4: tries=256, xz=5, y=48
        context.register(CLOUD_PILLAR_LARGE, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(256, 5, 48,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.CLOUD.get())))));

        // ===== 水下装饰 =====
        context.register(CORAL_TREE_PATCH, new ConfiguredFeature<>(Feature.CORAL_TREE,
                NoneFeatureConfiguration.INSTANCE));
        context.register(CORAL_CLAW_PATCH, new ConfiguredFeature<>(Feature.CORAL_CLAW,
                NoneFeatureConfiguration.INSTANCE));
        context.register(CORAL_MUSHROOM_PATCH, new ConfiguredFeature<>(Feature.CORAL_MUSHROOM,
                NoneFeatureConfiguration.INSTANCE));
        context.register(SEA_PICKLE_PATCH, new ConfiguredFeature<>(Feature.SEA_PICKLE,
                new CountConfiguration(UniformInt.of(1, 4))));
        // 染梦海草 — 原作 ground_feature_dyedream_5: tries=7
        context.register(DYEDREAM_SEAGRASS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(7, 7, 3,
                        simpleBlockInWater(BlockStateProvider.simple(
                                ModBlocks.DYEDREAM_SEAGRASS.get().defaultBlockState()
                                        .setValue(BlockStateProperties.WATERLOGGED, true))))));

        // ===== 洞穴晶芽 =====
        context.register(SMALL_DYEDREAM_BUD_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 4, 4,
                        simpleBudInAir(BlockStateProvider.simple(ModBlocks.SMALL_DYEDREAM_BUD.get()), DYEDREAM_BUD_GROUND))));
        context.register(MEDIUM_DYEDREAM_BUD_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 4, 4,
                        simpleBudInAir(BlockStateProvider.simple(ModBlocks.MEDIUM_DYEDREAM_BUD.get()), DYEDREAM_BUD_GROUND))));
        context.register(LARGE_DYEDREAM_BUD_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 4, 4,
                        simpleBudInAir(BlockStateProvider.simple(ModBlocks.LARGE_DYEDREAM_BUD.get()), DYEDREAM_BUD_GROUND))));
        context.register(ICE_BUD_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(25, 5, 5,
                        simpleBudInAir(BlockStateProvider.simple(ModBlocks.ICE_BUD.get()), ICE_BUD_GROUND))));
    }
}
