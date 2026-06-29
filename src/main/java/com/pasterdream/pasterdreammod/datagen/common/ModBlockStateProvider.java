package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.util.BuildingBlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PasterDreamMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(ModBlocks.DYEDREAM_DIRT.get(), cubeAll(ModBlocks.DYEDREAM_DIRT.get()));

        farmlandBlock(ModBlocks.DYEDREAM_FARMLAND.get());

        var grassBlock = models().cubeBottomTop(
                ModBlocks.DYEDREAM_GRASS_BLOCK.getId().getPath(),
                modLoc("block/dyedream_grass_block_side"),
                modLoc("block/dyedream_dirt"),
                modLoc("block/dyedream_grass_block_top"));
        simpleBlock(ModBlocks.DYEDREAM_GRASS_BLOCK.get(), grassBlock);
        simpleBlockItem(ModBlocks.DYEDREAM_GRASS_BLOCK.get(), grassBlock);

        logBlock((RotatedPillarBlock) ModBlocks.DYEDREAM_LOG.get());
        blockItem(ModBlocks.DYEDREAM_LOG);
        var logTex = blockTexture(ModBlocks.DYEDREAM_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.DYEDREAM_WOOD.get(), logTex, logTex);
        blockItem(ModBlocks.DYEDREAM_WOOD);
        simpleBlockWithItem(ModBlocks.DYEDREAM_LEAVES.get(), cubeAll(ModBlocks.DYEDREAM_LEAVES.get()));

        simpleBlockWithItem(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get(), cubeAll(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get()));

        simpleBlockWithItem(ModBlocks.DYEDREAM_QUARTZ_ORE.get(), cubeAll(ModBlocks.DYEDREAM_QUARTZ_ORE.get()));

        simpleBlockWithItem(ModBlocks.DYEDREAM_DUST_ORE.get(), cubeAll(ModBlocks.DYEDREAM_DUST_ORE.get()));

        simpleBlockWithItem(ModBlocks.AMBER_CANDY_ORE.get(), cubeAll(ModBlocks.AMBER_CANDY_ORE.get()));

        simpleBlockWithItem(ModBlocks.TITANIUM_ORE.get(), cubeAll(ModBlocks.TITANIUM_ORE.get()));
        simpleBlockWithItem(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(), cubeAll(ModBlocks.DEEPSLATE_TITANIUM_ORE.get()));
        simpleBlockWithItem(ModBlocks.MOLTEN_GOLD_ORE.get(), cubeAll(ModBlocks.MOLTEN_GOLD_ORE.get()));
        simpleBlockWithItem(ModBlocks.SOUL_ORE.get(), cubeAll(ModBlocks.SOUL_ORE.get()));
        simpleBlockWithItem(ModBlocks.RAW_TITANIUM_BLOCK.get(), cubeAll(ModBlocks.RAW_TITANIUM_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.SALT_BLOCK.get(), cubeAll(ModBlocks.SALT_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.TITANIUM_BLOCK.get(), cubeAll(ModBlocks.TITANIUM_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.MOLTEN_GOLD_BLOCK.get(), cubeAll(ModBlocks.MOLTEN_GOLD_BLOCK.get()));

        var chargedAmethystModel = models().cubeAll(ModBlocks.CHARGED_AMETHYST_BLOCK.getId().getPath(), blockTexture(ModBlocks.CHARGED_AMETHYST_BLOCK.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.CHARGED_AMETHYST_BLOCK.get(), chargedAmethystModel);

        // ===== 染梦石英建材系列 =====

        var quartzTex = blockTexture(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get());
        simpleBlockWithItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get(), cubeAll(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get(), cubeAll(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get(), cubeAll(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get()));

        logBlock((RotatedPillarBlock) ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get());
        blockItem(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK);

        var chiseledQuartz = models().cubeBottomTop(
                ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.getId().getPath(),
                modLoc("block/chiseled_dyedream_quartz_block"),
                modLoc("block/chiseled_dyedream_quartz_block_top"),
                modLoc("block/chiseled_dyedream_quartz_block_top"));
        simpleBlockWithItem(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get(), chiseledQuartz);

        stairsBlock((StairBlock) ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get(), quartzTex);
        blockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS);

        slabBlock((SlabBlock) ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), quartzTex, quartzTex);
        blockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB);

        wallBlock((WallBlock) ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get(), quartzTex);
        // wall inventory model handled in ModItemModelsProvider

        simpleBlock(ModBlocks.DYEDREAM_SAPLING.get(),
                models().cross(ModBlocks.DYEDREAM_SAPLING.getId().getPath(),
                        blockTexture(ModBlocks.DYEDREAM_SAPLING.get())).renderType("cutout"));

        // ===== 染梦木板建材系列 =====

        simpleBlockWithItem(ModBlocks.DYEDREAM_PLANKS.get(), cubeAll(ModBlocks.DYEDREAM_PLANKS.get()));

        stairsBlock((StairBlock) ModBlocks.DYEDREAM_STAIRS.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));
        blockItem(ModBlocks.DYEDREAM_STAIRS);

        slabBlock((SlabBlock) ModBlocks.DYEDREAM_SLAB.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));
        blockItem(ModBlocks.DYEDREAM_SLAB);

        fenceBlock((FenceBlock) ModBlocks.DYEDREAM_FENCE.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));

        fenceGateBlock((FenceGateBlock) ModBlocks.DYEDREAM_FENCE_GATE.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));
        blockItem(ModBlocks.DYEDREAM_FENCE_GATE);

        paneBlock((IronBarsBlock) ModBlocks.DYEDREAM_PANE.get(), modLoc("block/dyedream_trapdoor"), modLoc("block/dyedream_trapdoor"));

        doorBlockWithRenderType((DoorBlock) ModBlocks.DYEDREAM_DOOR.get(),
                modLoc("block/dyedream_door_bottom"),
                modLoc("block/dyedream_door_top"),
                "cutout");

        trapdoorBlockWithRenderType((TrapDoorBlock) ModBlocks.DYEDREAM_TRAPDOOR.get(),
                modLoc("block/dyedream_trapdoor"),
                true,
                "cutout");
        blockItem(ModBlocks.DYEDREAM_TRAPDOOR, "_bottom");

        pressurePlateBlock((PressurePlateBlock) ModBlocks.DYEDREAM_PRESSURE_PLATE.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));
        blockItem(ModBlocks.DYEDREAM_PRESSURE_PLATE);

        buttonBlock((ButtonBlock) ModBlocks.DYEDREAM_BUTTON.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));

        simpleBlockWithItem(ModBlocks.PINK_SLIME_BLOCK.get(), cubeAll(ModBlocks.PINK_SLIME_BLOCK.get()));

        // ===== 粉顶菌菇系列 =====

        var capTex = modLoc("block/pink_mushroom_block_cap");
        var poreTex = modLoc("block/pink_mushroom_block_pores");
        var stemTex = modLoc("block/pink_mushroom_stem");
        var stemTopTex = modLoc("block/pink_mushroom_stem_top");

        var capModel = models().cube(ModBlocks.PINK_MUSHROOM_BLOCK.getId().getPath(),
                poreTex, capTex, capTex, capTex, capTex, capTex).texture("particle", poreTex);
        simpleBlockWithItem(ModBlocks.PINK_MUSHROOM_BLOCK.get(), capModel);

        axisBlock((RotatedPillarBlock) ModBlocks.PINK_MUSHROOM_STEM.get(), stemTex, stemTopTex);
        blockItem(ModBlocks.PINK_MUSHROOM_STEM);

        simpleBlockWithItem(ModBlocks.PINK_MUSHROOM_PORES.get(), models().cubeAll(ModBlocks.PINK_MUSHROOM_PORES.getId().getPath(), poreTex));

        simpleBlockWithItem(ModBlocks.PINK_SHROOMLIGHT.get(), cubeAll(ModBlocks.PINK_SHROOMLIGHT.get()));

        var pinkMushroom = models().cross(ModBlocks.PINK_MUSHROOM.getId().getPath(), blockTexture(ModBlocks.PINK_MUSHROOM.get())).renderType("cutout");
        simpleBlock(ModBlocks.PINK_MUSHROOM.get(), pinkMushroom);

        var tallUpper = models().cross(ModBlocks.TALL_PINK_MUSHROOM.getId().getPath() + "_top", modLoc("block/pink_mushroom")).renderType("cutout");
        var tallLower = models().cross(ModBlocks.TALL_PINK_MUSHROOM.getId().getPath() + "_bottom", modLoc("block/tall_pink_mushroom_bottom")).renderType("cutout");
        getVariantBuilder(ModBlocks.TALL_PINK_MUSHROOM.get())
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(tallLower))
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(tallUpper));

        // ===== 染梦玻璃系列 =====

        simpleBlockWithItem(ModBlocks.DYEDREAM_SAND.get(), cubeAll(ModBlocks.DYEDREAM_SAND.get()));

        simpleBlockWithItem(ModBlocks.DYEDREAM_GLASS.get(), cubeAll(ModBlocks.DYEDREAM_GLASS.get()));
        var glassTex = modLoc("block/dyedream_glass");
        paneBlock((IronBarsBlock) ModBlocks.DYEDREAM_GLASS_PANE.get(), glassTex, glassTex);

        simpleBlockWithItem(ModBlocks.CARVE_DYEDREAM_GLASS.get(), cubeAll(ModBlocks.CARVE_DYEDREAM_GLASS.get()));
        var carveTex = modLoc("block/carve_dyedream_glass");
        paneBlock((IronBarsBlock) ModBlocks.CARVE_DYEDREAM_GLASS_PANE.get(), carveTex, carveTex);

        simpleBlockWithItem(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get(), cubeAll(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get()));
        var goldCarveTex = modLoc("block/gold_carve_dyedream_glass");
        paneBlock((IronBarsBlock) ModBlocks.GOLD_CARVE_DYEDREAM_GLASS_PANE.get(), goldCarveTex, goldCarveTex);

        // ===== 染梦晶芽系列 =====

        var smallBud = models().cross(ModBlocks.SMALL_DYEDREAM_BUD.getId().getPath(), blockTexture(ModBlocks.SMALL_DYEDREAM_BUD.get())).renderType("cutout");
        directionalBlock(ModBlocks.SMALL_DYEDREAM_BUD.get(), smallBud);

        var mediumBud = models().cross(ModBlocks.MEDIUM_DYEDREAM_BUD.getId().getPath(), blockTexture(ModBlocks.MEDIUM_DYEDREAM_BUD.get())).renderType("cutout");
        directionalBlock(ModBlocks.MEDIUM_DYEDREAM_BUD.get(), mediumBud);

        var largeBud = models().cross(ModBlocks.LARGE_DYEDREAM_BUD.getId().getPath(), blockTexture(ModBlocks.LARGE_DYEDREAM_BUD.get())).renderType("cutout");
        directionalBlock(ModBlocks.LARGE_DYEDREAM_BUD.get(), largeBud);

        // ===== 染梦水晶建材系列 =====
        simpleBuildingFamily(new BuildingBlockFamily(ModBlocks.DYEDREAM_BUD_BLOCK, ModBlocks.DYEDREAM_BUD_STAIRS, ModBlocks.DYEDREAM_BUD_SLAB, ModBlocks.DYEDREAM_BUD_WALL));

        // ===== 云朵 =====
        var cloudModel = models().cubeAll(ModBlocks.CLOUD.getId().getPath(), blockTexture(ModBlocks.CLOUD.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.CLOUD.get(), cloudModel);
        var darkCloudModel = models().cubeAll(ModBlocks.DARK_CLOUD.getId().getPath(), blockTexture(ModBlocks.DARK_CLOUD.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.DARK_CLOUD.get(), darkCloudModel);

        // ===== 巨型泡泡 =====
        var bubbleModel = models().cubeAll(ModBlocks.BIG_BUBBLE.getId().getPath(), modLoc("block/big_bubble")).renderType("translucent");
        simpleBlockWithItem(ModBlocks.BIG_BUBBLE.get(), bubbleModel);

        // ===== 染梦冰与水晶灯 =====

        simpleBlockWithItem(ModBlocks.DYEDREAM_ICE.get(), cubeAll(ModBlocks.DYEDREAM_ICE.get()));
        simpleBlockWithItem(ModBlocks.DYEDREAM_PACKED_ICE.get(), cubeAll(ModBlocks.DYEDREAM_PACKED_ICE.get()));
        simpleBlockWithItem(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get(), cubeAll(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get()));
        // 染梦灯笼
        var lanternStanding = models().withExistingParent("dyedream_lantern", mcLoc("block/template_lantern"))
                .texture("lantern", modLoc("block/dyedream_lantern")).renderType("cutout");
        var lanternHanging = models().withExistingParent("dyedream_lantern_hanging", mcLoc("block/template_hanging_lantern"))
                .texture("lantern", modLoc("block/dyedream_lantern")).renderType("cutout");
        getVariantBuilder(ModBlocks.DYEDREAM_LANTERN.get())
                .partialState().with(LanternBlock.HANGING, false).modelForState().modelFile(lanternStanding).addModel()
                .partialState().with(LanternBlock.HANGING, true).modelForState().modelFile(lanternHanging).addModel();
        simpleBlockItem(ModBlocks.DYEDREAM_LANTERN.get(), lanternStanding);

        // ===== 冰晶岩与冰凌晶芽 =====
        simpleBlockWithItem(ModBlocks.ICE_STONE.get(), cubeAll(ModBlocks.ICE_STONE.get()));

        var iceBud = models().cross(ModBlocks.ICE_BUD.getId().getPath(), blockTexture(ModBlocks.ICE_BUD.get())).renderType("cutout");
        directionalBlock(ModBlocks.ICE_BUD.get(), iceBud);

        // ===== 染梦合金块 =====
        simpleBlockWithItem(ModBlocks.DYEDREAM_ALLOY_BLOCK.get(), cubeAll(ModBlocks.DYEDREAM_ALLOY_BLOCK.get()));

        // ===== 方解石系列 =====
        simpleBuildingFamily(new BuildingBlockFamily(ModBlocks.POLISHED_CALCITE, ModBlocks.POLISHED_CALCITE_STAIRS, ModBlocks.POLISHED_CALCITE_SLAB, ModBlocks.POLISHED_CALCITE_WALL));
        simpleBuildingFamily(new BuildingBlockFamily(ModBlocks.CALCITE_TILES, ModBlocks.CALCITE_TILES_STAIRS, ModBlocks.CALCITE_TILES_SLAB, ModBlocks.CALCITE_TILES_WALL));

        // ===== 植物系列 =====
        var goldenrod = models().cross(ModBlocks.GOLDENROD.getId().getPath(), blockTexture(ModBlocks.GOLDENROD.get())).renderType("cutout");
        simpleBlock(ModBlocks.GOLDENROD.get(), goldenrod);
        var ferraria_crispa = models().cross(ModBlocks.FERRARIA_CRISPA.getId().getPath(), blockTexture(ModBlocks.FERRARIA_CRISPA.get())).renderType("cutout");
        simpleBlock(ModBlocks.FERRARIA_CRISPA.get(), ferraria_crispa);
        var eustoma = models().cross(ModBlocks.EUSTOMA.getId().getPath(), blockTexture(ModBlocks.EUSTOMA.get())).renderType("cutout");
        simpleBlock(ModBlocks.EUSTOMA.get(), eustoma);
        var dreaming_lotus_Upper = models().cross(ModBlocks.DREAMING_LOTUS.getId().getPath(),
                modLoc("block/dreaming_lotus_top")).renderType("cutout");
        var dreaming_lotus_Lower = models().cross(ModBlocks.DREAMING_LOTUS.getId().getPath() + "_bottom",
                modLoc("block/dreaming_lotus")).renderType("cutout");
        var misty_dreaming_lotus_Upper = models().cross(ModBlocks.MISTY_DREAMING_LOTUS.getId().getPath(),
                modLoc("block/misty_dreaming_lotus_top")).renderType("cutout");
        var misty_dreaming_lotus_Lower = models().cross(ModBlocks.MISTY_DREAMING_LOTUS.getId().getPath() + "_bottom",
                modLoc("block/dreaming_lotus")).renderType("cutout");
        getVariantBuilder(ModBlocks.DREAMING_LOTUS.get())
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(dreaming_lotus_Lower))
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(dreaming_lotus_Upper));
        getVariantBuilder(ModBlocks.MISTY_DREAMING_LOTUS.get())
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(misty_dreaming_lotus_Lower))
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(misty_dreaming_lotus_Upper));
        var linht_flower = models().cross(ModBlocks.LINHT_FLOWER.getId().getPath(), blockTexture(ModBlocks.LINHT_FLOWER.get())).renderType("cutout");
        simpleBlock(ModBlocks.LINHT_FLOWER.get(), linht_flower);
        var blaze_flower = models().cross(ModBlocks.BLAZE_FLOWER.getId().getPath(), blockTexture(ModBlocks.BLAZE_FLOWER.get())).renderType("cutout");
        simpleBlock(ModBlocks.BLAZE_FLOWER.get(), blaze_flower);
        var dyedream_lily_of_the_valley = models().cross(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get())).renderType("cutout");
        simpleBlock(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get(), dyedream_lily_of_the_valley);

        var white_orchid_flower = models().cross(ModBlocks.WHITE_ORCHID_FLOWER.getId().getPath(), blockTexture(ModBlocks.WHITE_ORCHID_FLOWER.get())).renderType("cutout");
        simpleBlock(ModBlocks.WHITE_ORCHID_FLOWER.get(), white_orchid_flower);
        var edelweiss = models().cross(ModBlocks.EDELWEISS.getId().getPath(), blockTexture(ModBlocks.EDELWEISS.get())).renderType("cutout");
        simpleBlock(ModBlocks.EDELWEISS.get(), edelweiss);
        var nippy_edelweiss = models().cross(ModBlocks.NIPPY_EDELWEISS.getId().getPath(), blockTexture(ModBlocks.NIPPY_EDELWEISS.get())).renderType("cutout");
        simpleBlock(ModBlocks.NIPPY_EDELWEISS.get(), nippy_edelweiss);


        var malva_sinensis_cavan = models().cross(ModBlocks.MALVA_SINENSIS_CAVAN.getId().getPath(), blockTexture(ModBlocks.MALVA_SINENSIS_CAVAN.get())).renderType("cutout");
        simpleBlock(ModBlocks.MALVA_SINENSIS_CAVAN.get(), malva_sinensis_cavan);
        var GrassTallUpper = models().cross(ModBlocks.TALL_STEM_GRASS.getId().getPath() + "_top", modLoc("block/stem_grass")).renderType("cutout");
        var GrassTallLower = models().cross(ModBlocks.TALL_STEM_GRASS.getId().getPath() + "_bottom", modLoc("block/tall_stem_grass")).renderType("cutout");
        getVariantBuilder(ModBlocks.TALL_STEM_GRASS.get())
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(GrassTallLower))
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(GrassTallUpper));

        var crimson_thorns_Upper = models().cross(ModBlocks.CRIMSON_THORNS.getId().getPath(), modLoc("block/crimson_thorns_top")).renderType("cutout");
        var crimson_thorns_Lower = models().cross(ModBlocks.CRIMSON_THORNS.getId().getPath() + "_bottom", modLoc("block/crimson_thorns")).renderType("cutout");
        getVariantBuilder(ModBlocks.CRIMSON_THORNS.get())
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(crimson_thorns_Lower))
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(crimson_thorns_Upper));
        var oats = models().cross(ModBlocks.OATS.getId().getPath(), blockTexture(ModBlocks.OATS.get())).renderType("cutout");
        simpleBlock(ModBlocks.OATS.get(), oats);
        var rye = models().cross(ModBlocks.RYE.getId().getPath(), blockTexture(ModBlocks.RYE.get())).renderType("cutout");
        simpleBlock(ModBlocks.RYE.get(), rye);

        var stalicripe = models().cross(ModBlocks.POLISHED_CALCITE_STALICRIPE.getId().getPath(), blockTexture(ModBlocks.POLISHED_CALCITE_STALICRIPE.get())).renderType("cutout");
        simpleBlock(ModBlocks.POLISHED_CALCITE_STALICRIPE.get(), stalicripe);
        var small_stalicripe = models().cross(ModBlocks.SMALL_POLISHED_CALCITE_STALICRIPE.getId().getPath(), blockTexture(ModBlocks.SMALL_POLISHED_CALCITE_STALICRIPE.get())).renderType("cutout");
        simpleBlock(ModBlocks.SMALL_POLISHED_CALCITE_STALICRIPE.get(), small_stalicripe);


        //植株方块
        generateTwoStageCrop(ModBlocks.DYEDREAM_COROLLA_CROP.get(), "dyedream_corolla_crop");
        generateTwoStageCrop(ModBlocks.WHITE_COROLLA_CROP.get(), "white_corolla_crop");
        generateTwoStageCrop(ModBlocks.LIGHT_BALL_CROP.get(), "light_ball_crop");
        generateTwoStageCrop(ModBlocks.CLOUD_CROP.get(), "cloud_crop");
        generateTwoStageCrop(ModBlocks.COTTON_CROP.get(), "cotton_crop");

        generateTwoStageCropBlockModel("dyedream_corolla_crop");
        generateTwoStageCropBlockModel("white_corolla_crop");
        generateTwoStageCropBlockModel("light_ball_crop");
        generateTwoStageCropBlockModel("cloud_crop");
        generateTwoStageCropBlockModel("cotton_crop");

        var dyedreamMoss = models().cross(ModBlocks.DYEDREAM_MOSS.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_MOSS.get())).renderType("cutout");
        simpleBlock(ModBlocks.DYEDREAM_MOSS.get(), dyedreamMoss);

        var stemGrass = models().cross(ModBlocks.STEM_GRASS.getId().getPath(), blockTexture(ModBlocks.STEM_GRASS.get())).renderType("cutout");
        simpleBlock(ModBlocks.STEM_GRASS.get(), stemGrass);

        var singularity_fern = models().cross(ModBlocks.SINGULARITY_FERN.getId().getPath(), blockTexture(ModBlocks.SINGULARITY_FERN.get())).renderType("cutout");
        simpleBlock(ModBlocks.SINGULARITY_FERN.get(), singularity_fern);

        // 染梦睡莲 / 染梦莲花 (custom Blockbench models)
        simpleBlock(ModBlocks.DYEDREAM_LILY_PAD.get(), models().getExistingFile(modLoc("block/dyedream_lily_pad")));
        simpleBlock(ModBlocks.DYEDREAM_LOTUS.get(), models().getExistingFile(modLoc("block/dyedream_lotus")));

        //流体方块
        simpleBlock(ModBlocks.MELTDREAM_LIQUID.get(), models().cubeAll(ModBlocks.MELTDREAM_LIQUID.getId().getPath(), modLoc("block/meltdream_liquid_flowing")));
        simpleBlock(ModBlocks.SHADOW_LIQUID.get(), models().cubeAll(ModBlocks.SHADOW_LIQUID.getId().getPath(), modLoc("block/shadow_liquid_flowing")));
    }

    private void simpleBuildingFamily(BuildingBlockFamily family) {
        var tex = blockTexture(family.base().get());
        simpleBlockWithItem(family.base().get(), cubeAll(family.base().get()));
        stairsBlock((StairBlock) family.stairs().get(), tex);
        blockItem(family.stairs());
        slabBlock((SlabBlock) family.slab().get(), tex, tex);
        blockItem(family.slab());
        wallBlock((WallBlock) family.wall().get(), tex);
    }

    private void generateTwoStageCrop(Block block, String baseName)
    {
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.AGE_1, 0)
                .modelForState().modelFile(modelFile(baseName + "_age_0")).addModel()
                .partialState().with(BlockStateProperties.AGE_1, 1)
                .modelForState().modelFile(modelFile(baseName)).addModel();
    }

    private ModelFile modelFile(String name)
    {
        return new ModelFile.UncheckedModelFile(modLoc("block/" + name));
    }

    private void generateTwoStageCropBlockModel(String baseName)
    {
        models().withExistingParent(baseName + "_age_0", mcLoc("block/cross")).texture("cross", modLoc("block/" + baseName + "_age_0")).renderType("cutout");
        models().withExistingParent(baseName, mcLoc("block/cross")).texture("cross", modLoc("block/" + baseName)).renderType("cutout");
    }

    private <T extends Block> void blockItem(RegistryObject<T> block) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(PasterDreamMod.MOD_ID + ":block/" + block.getId().getPath()));
    }

    private <T extends Block> void blockItem(RegistryObject<T> block, String append) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(PasterDreamMod.MOD_ID + ":block/" + block.getId().getPath() + append));
    }

    private void farmlandBlock(Block block) {
        String baseName = ForgeRegistries.BLOCKS.getKey(block).getPath();
        ModelFile dry = models().getExistingFile(modLoc("block/" + baseName));
        ModelFile moist = models().getExistingFile(modLoc("block/" + baseName + "_moist"));
        getVariantBuilder(block).forAllStates(state -> {
            int moisture = state.getValue(FarmBlock.MOISTURE);
            return ConfiguredModel.builder().modelFile(moisture == 7 ? moist : dry).build();
        });
        simpleBlockItem(block, dry);
    }
}
