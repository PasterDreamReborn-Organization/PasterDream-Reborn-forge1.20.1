package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.util.BuildingBlockFamily;
import net.minecraft.core.Direction;
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

        // ===== 黑金属系列 =====
        simpleBlockWithItem(ModBlocks.BLACK_METAL_BLOCK.get(), cubeAll(ModBlocks.BLACK_METAL_BLOCK.get()));

        // ===== 锈蚀金属系列 =====
        simpleBlockWithItem(ModBlocks.RUST_BLACK_METAL_BLOCK.get(), cubeAll(ModBlocks.RUST_BLACK_METAL_BLOCK.get()));
        var rustMetalTex = blockTexture(ModBlocks.RUST_BLACK_METAL_BLOCK.get());
        wallBlock((WallBlock) ModBlocks.RUST_BLACK_METAL_WALL.get(), rustMetalTex);
        var barsTex = modLoc("block/rust_black_metal_bars");
        paneBlock((IronBarsBlock) ModBlocks.RUST_BLACK_METAL_BARS.get(), barsTex, barsTex);

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

        // ===== 阴影木板建材系列 =====

        simpleBlockWithItem(ModBlocks.SHADOW_PLANKS.get(), cubeAll(ModBlocks.SHADOW_PLANKS.get()));

        stairsBlock((StairBlock) ModBlocks.SHADOW_STAIRS.get(), blockTexture(ModBlocks.SHADOW_PLANKS.get()));
        blockItem(ModBlocks.SHADOW_STAIRS);

        slabBlock((SlabBlock) ModBlocks.SHADOW_SLAB.get(), blockTexture(ModBlocks.SHADOW_PLANKS.get()), blockTexture(ModBlocks.SHADOW_PLANKS.get()));
        blockItem(ModBlocks.SHADOW_SLAB);

        fenceBlock((FenceBlock) ModBlocks.SHADOW_FENCE.get(), blockTexture(ModBlocks.SHADOW_PLANKS.get()));

        fenceGateBlock((FenceGateBlock) ModBlocks.SHADOW_FENCE_GATE.get(), blockTexture(ModBlocks.SHADOW_PLANKS.get()));
        blockItem(ModBlocks.SHADOW_FENCE_GATE);

        paneBlock((IronBarsBlock) ModBlocks.SHADOW_PANE.get(), modLoc("block/shadow_trapdoor"), modLoc("block/shadow_trapdoor"));

        doorBlockWithRenderType((DoorBlock) ModBlocks.SHADOW_DOOR.get(),
                modLoc("block/shadow_door_bottom"),
                modLoc("block/shadow_door_top"),
                "cutout");

        trapdoorBlockWithRenderType((TrapDoorBlock) ModBlocks.SHADOW_TRAPDOOR.get(),
                modLoc("block/shadow_trapdoor"),
                true,
                "cutout");
        blockItem(ModBlocks.SHADOW_TRAPDOOR, "_bottom");

        pressurePlateBlock((PressurePlateBlock) ModBlocks.SHADOW_PRESSURE_PLATE.get(), blockTexture(ModBlocks.SHADOW_PLANKS.get()));
        blockItem(ModBlocks.SHADOW_PRESSURE_PLATE);

        buttonBlock((ButtonBlock) ModBlocks.SHADOW_BUTTON.get(), blockTexture(ModBlocks.SHADOW_PLANKS.get()));

        // ===== 阴影书架系列 =====
        horizontalBookshelf(ModBlocks.SHADOW_BOOKSHELF.get());
        horizontalBookshelf(ModBlocks.WORN_SHADOW_BOOKSHELF.get());
        horizontalBookshelf(ModBlocks.COBWEB_SHADOW_BOOKSHELF.get());
        horizontalBookshelfKey(ModBlocks.KEY_SHADOW_BOOKSHELF.get());

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

        var buddingModel = models().cubeAll(ModBlocks.DYEDREAM_BUDDING_BLOCK.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_BUDDING_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.DYEDREAM_BUDDING_BLOCK.get(), buddingModel);

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
        simpleBlockWithItem(ModBlocks.WHITE_SAND.get(), cubeAll(ModBlocks.WHITE_SAND.get()));
        var thickCloudModel = models().cubeAll(ModBlocks.THICK_CLOUD.getId().getPath(), blockTexture(ModBlocks.THICK_CLOUD.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.THICK_CLOUD.get(), thickCloudModel);
        var shadowModel = models().cubeAll(ModBlocks.SHADOW.getId().getPath(), blockTexture(ModBlocks.SHADOW.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.SHADOW.get(), shadowModel);
        var thickShadowModel = models().cubeAll(ModBlocks.THICK_SHADOW.getId().getPath(), blockTexture(ModBlocks.THICK_SHADOW.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.THICK_SHADOW.get(), thickShadowModel);
        simpleBlockWithItem(ModBlocks.SHADOW_STONE.get(), models().cubeAll(ModBlocks.SHADOW_STONE.getId().getPath(), blockTexture(ModBlocks.SHADOW_STONE.get())));
        // ===== 阴影石砖建材系列 =====
        simpleBuildingFamily(new BuildingBlockFamily(ModBlocks.SHADOW_STONE_BRICK, ModBlocks.SHADOW_STONE_BRICK_STAIRS, ModBlocks.SHADOW_STONE_BRICK_SLAB, ModBlocks.SHADOW_STONE_BRICK_WALL));
        // ===== 细阴影石砖建材系列 =====
        simpleBuildingFamily(new BuildingBlockFamily(ModBlocks.NARROW_SHADOW_STONE_BRICK, ModBlocks.NARROW_SHADOW_STONE_BRICK_STAIRS, ModBlocks.NARROW_SHADOW_STONE_BRICK_SLAB, ModBlocks.NARROW_SHADOW_STONE_BRICK_WALL));
        // ===== 阴影石瓦建材系列 =====
        simpleBuildingFamily(new BuildingBlockFamily(ModBlocks.SHADOW_STONE_TILES, ModBlocks.SHADOW_STONE_TILES_STAIRS, ModBlocks.SHADOW_STONE_TILES_SLAB, ModBlocks.SHADOW_STONE_TILES_WALL));
        // ===== 裂阴影石砖 / 錾制阴影石砖 =====
        simpleBlockWithItem(ModBlocks.CRACKED_SHADOW_STONE_BRICK.get(), cubeAll(ModBlocks.CRACKED_SHADOW_STONE_BRICK.get()));
        simpleBlockWithItem(ModBlocks.CHISELED_SHADOW_STONE_BRICK.get(), cubeAll(ModBlocks.CHISELED_SHADOW_STONE_BRICK.get()));
        // ===== 阴影石符文系列 =====
        var shadowStoneTopBottom = blockTexture(ModBlocks.SHADOW_STONE.get());
        var cageRuneModel = models().cubeBottomTop(ModBlocks.SHADOW_STONE_CAGE_RUNE.getId().getPath(), modLoc("block/shadow_stone_cage_rune"), shadowStoneTopBottom, shadowStoneTopBottom);
        simpleBlockWithItem(ModBlocks.SHADOW_STONE_CAGE_RUNE.get(), cageRuneModel);
        var holyGrailRuneModel = models().cubeBottomTop(ModBlocks.SHADOW_STONE_HOLY_GRAIL_RUNE.getId().getPath(), modLoc("block/shadow_stone_holy_grail_rune"), shadowStoneTopBottom, shadowStoneTopBottom);
        simpleBlockWithItem(ModBlocks.SHADOW_STONE_HOLY_GRAIL_RUNE.get(), holyGrailRuneModel);
        var oblationRuneModel = models().cubeBottomTop(ModBlocks.SHADOW_STONE_OBLATION_RUNE.getId().getPath(), modLoc("block/shadow_stone_oblation_rune"), shadowStoneTopBottom, shadowStoneTopBottom);
        simpleBlockWithItem(ModBlocks.SHADOW_STONE_OBLATION_RUNE.get(), oblationRuneModel);
        var tripodCauldronRuneModel = models().cubeBottomTop(ModBlocks.SHADOW_STONE_TRIPOD_CAULDRON_RUNE.getId().getPath(), modLoc("block/shadow_stone_tripod_cauldron_rune"), shadowStoneTopBottom, shadowStoneTopBottom);
        simpleBlockWithItem(ModBlocks.SHADOW_STONE_TRIPOD_CAULDRON_RUNE.get(), tripodCauldronRuneModel);
        // ===== 暗影地牢方块系列 =====
        simpleBlockWithItem(ModBlocks.SHADOW_DUNGEON_STONE.get(), cubeAll(ModBlocks.SHADOW_DUNGEON_STONE.get()));
        var chiseledDungeonSide = modLoc("block/chiseled_shadow_dungeon_bricks_side");
        var chiseledDungeonTop = modLoc("block/chiseled_shadow_dungeon_bricks_top");
        axisBlock((RotatedPillarBlock) ModBlocks.CHISELED_SHADOW_DUNGEON_BRICKS.get(), chiseledDungeonSide, chiseledDungeonTop);
        blockItem(ModBlocks.CHISELED_SHADOW_DUNGEON_BRICKS);
        simpleBlockWithItem(ModBlocks.SHADOW_DUNGEON_BRICKS.get(), cubeAll(ModBlocks.SHADOW_DUNGEON_BRICKS.get()));
        simpleBlockWithItem(ModBlocks.CRACKED_SHADOW_DUNGEON_BRICKS.get(), cubeAll(ModBlocks.CRACKED_SHADOW_DUNGEON_BRICKS.get()));
        simpleBlockWithItem(ModBlocks.FRACTURED_SHADOW_DUNGEON_BRICKS.get(), cubeAll(ModBlocks.FRACTURED_SHADOW_DUNGEON_BRICKS.get()));
        var dungeonBrickTex = blockTexture(ModBlocks.SHADOW_DUNGEON_BRICKS.get());
        stairsBlock((StairBlock) ModBlocks.SHADOW_DUNGEON_BRICK_STAIRS.get(), dungeonBrickTex);
        blockItem(ModBlocks.SHADOW_DUNGEON_BRICK_STAIRS);
        slabBlock((SlabBlock) ModBlocks.SHADOW_DUNGEON_BRICK_SLAB.get(), dungeonBrickTex, dungeonBrickTex);
        blockItem(ModBlocks.SHADOW_DUNGEON_BRICK_SLAB);
        simpleBlockWithItem(ModBlocks.SHATTERED_SHADOW_DUNGEON_BRICKS.get(), cubeAll(ModBlocks.SHATTERED_SHADOW_DUNGEON_BRICKS.get()));
        // ===== 灯影竞技场 / 裂隙方块 =====
        simpleBlockWithItem(ModBlocks.SHADOW_ARENA_BLOCK.get(), cubeAll(ModBlocks.SHADOW_ARENA_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.SHADOW_FISSURE_0.get(), cubeAll(ModBlocks.SHADOW_FISSURE_0.get()));
        simpleBlockWithItem(ModBlocks.SHADOW_FISSURE_1.get(), cubeAll(ModBlocks.SHADOW_FISSURE_1.get()));
        simpleBlockWithItem(ModBlocks.SHADOW_FISSURE_2.get(), cubeAll(ModBlocks.SHADOW_FISSURE_2.get()));
        simpleBlockWithItem(ModBlocks.SHADOW_FISSURE_3.get(), cubeAll(ModBlocks.SHADOW_FISSURE_3.get()));
        simpleBlockWithItem(ModBlocks.SHADOW_FISSURE_4.get(), cubeAll(ModBlocks.SHADOW_FISSURE_4.get()));
        simpleBlockWithItem(ModBlocks.SHADOW_FISSURE_5.get(), cubeAll(ModBlocks.SHADOW_FISSURE_5.get()));
        // ===== 阴影菌岩 / 影灯 / 阴影菌光体 =====
        var shadowNyliumModel = models().cubeBottomTop(
                ModBlocks.SHADOW_NYLIUM.getId().getPath(),
                modLoc("block/shadow_nylium_side"),
                modLoc("block/shadow_stone"),
                modLoc("block/shadow_nylium_top"));
        simpleBlockWithItem(ModBlocks.SHADOW_NYLIUM.get(), shadowNyliumModel);
        var shadowLightModel = models().cubeBottomTop(
                ModBlocks.SHADOW_LIGHT.getId().getPath(),
                modLoc("block/shadow_light_side"),
                modLoc("block/shadow_light_top"),
                modLoc("block/shadow_light_top"));
        simpleBlockWithItem(ModBlocks.SHADOW_LIGHT.get(), shadowLightModel);
        simpleBlockWithItem(ModBlocks.SHADOW_SHROOMLIGHT.get(), cubeAll(ModBlocks.SHADOW_SHROOMLIGHT.get()));

        // ===== 阴影巨型菌系列 =====
        simpleBlockWithItem(ModBlocks.SHADOW_WART_BLOCK.get(), cubeAll(ModBlocks.SHADOW_WART_BLOCK.get()));
        logBlock((RotatedPillarBlock) ModBlocks.SHADOW_STEM.get());
        blockItem(ModBlocks.SHADOW_STEM);
        var shadowStemTex = blockTexture(ModBlocks.SHADOW_STEM.get());
        axisBlock((RotatedPillarBlock) ModBlocks.SHADOW_HYPHAE.get(), shadowStemTex, shadowStemTex);
        blockItem(ModBlocks.SHADOW_HYPHAE);
        logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_SHADOW_STEM.get());
        blockItem(ModBlocks.STRIPPED_SHADOW_STEM);
        var strippedShadowStemTex = blockTexture(ModBlocks.STRIPPED_SHADOW_STEM.get());
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_SHADOW_HYPHAE.get(), strippedShadowStemTex, strippedShadowStemTex);
        blockItem(ModBlocks.STRIPPED_SHADOW_HYPHAE);

        // ===== 风泊木系列 =====
        logBlock((RotatedPillarBlock) ModBlocks.WIND_MOOR_LOG.get());
        blockItem(ModBlocks.WIND_MOOR_LOG);
        var windMoorLogTex = blockTexture(ModBlocks.WIND_MOOR_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.WIND_MOOR_WOOD.get(), windMoorLogTex, windMoorLogTex);
        blockItem(ModBlocks.WIND_MOOR_WOOD);
        logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_WIND_MOOR_LOG.get());
        blockItem(ModBlocks.STRIPPED_WIND_MOOR_LOG);
        var strippedWindMoorLogTex = blockTexture(ModBlocks.STRIPPED_WIND_MOOR_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_WIND_MOOR_WOOD.get(), strippedWindMoorLogTex, strippedWindMoorLogTex);
        blockItem(ModBlocks.STRIPPED_WIND_MOOR_WOOD);

        simpleBlockWithItem(ModBlocks.WIND_MOOR_LEAVES_0.get(), cubeAll(ModBlocks.WIND_MOOR_LEAVES_0.get()));
        simpleBlockWithItem(ModBlocks.WIND_MOOR_LEAVES_1.get(), cubeAll(ModBlocks.WIND_MOOR_LEAVES_1.get()));

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

        // ===== 风之旅世界·苍青岩系列 =====
        simpleBlockWithItem(ModBlocks.CYAN_STONE.get(), cubeAll(ModBlocks.CYAN_STONE.get()));
        var cyanMossStoneModel = models().cubeBottomTop(
                ModBlocks.CYAN_MOSS_STONE.getId().getPath(),
                blockTexture(ModBlocks.CYAN_MOSS_STONE.get()),
                blockTexture(ModBlocks.CYAN_STONE.get()),
                modLoc("block/cyan_moss_stone_top"));
        simpleBlockWithItem(ModBlocks.CYAN_MOSS_STONE.get(), cyanMossStoneModel);

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

        var reed = models().cross(ModBlocks.REED.getId().getPath(), blockTexture(ModBlocks.REED.get())).renderType("cutout");
        simpleBlock(ModBlocks.REED.get(), reed);
        var Dyedream_vine = models().cross(ModBlocks.DYEDREAM_VINE.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_VINE.get())).renderType("cutout");
        simpleBlock(ModBlocks.DYEDREAM_VINE.get(), Dyedream_vine);
        var Jungle_sporangium = models().cross(ModBlocks.JUNGLE_SPORANGIUM.getId().getPath(), blockTexture(ModBlocks.JUNGLE_SPORANGIUM.get())).renderType("cutout");
        simpleBlock(ModBlocks.JUNGLE_SPORANGIUM.get(),Jungle_sporangium);
        // fourleaf_clover uses manual custom model
        simpleBlock(ModBlocks.FOURLEAF_CLOVER.get(), models().getExistingFile(modLoc("block/fourleaf_clover")));
        var light_ball = models().cross(ModBlocks.LIGHT_BALL.getId().getPath(), blockTexture(ModBlocks.LIGHT_BALL.get())).renderType("cutout");
        simpleBlock(ModBlocks.LIGHT_BALL.get(),light_ball);


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

        simpleBlockWithItem(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_0.get(), cubeAll(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_0.get()));
        simpleBlockWithItem(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_1.get(), cubeAll(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_1.get()));
        var translucentModel = models().cubeAll(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2.getId().getPath(), blockTexture(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2.get(), translucentModel);
        simpleBlockWithItem(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_3.get(), cubeAll(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_3.get()));

        var dyedreamMoss = models().cross(ModBlocks.DYEDREAM_MOSS.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_MOSS.get())).renderType("cutout");
        simpleBlock(ModBlocks.DYEDREAM_MOSS.get(), dyedreamMoss);

        var stemGrass = models().cross(ModBlocks.STEM_GRASS.getId().getPath(), blockTexture(ModBlocks.STEM_GRASS.get())).renderType("cutout");
        simpleBlock(ModBlocks.STEM_GRASS.get(), stemGrass);

        var singularity_fern = models().cross(ModBlocks.SINGULARITY_FERN.getId().getPath(), blockTexture(ModBlocks.SINGULARITY_FERN.get())).renderType("cutout");
        simpleBlock(ModBlocks.SINGULARITY_FERN.get(), singularity_fern);

        // 阴影植物系列
        var shadowShortRoots = models().cross(ModBlocks.SHADOW_SHORT_ROOTS.getId().getPath(), blockTexture(ModBlocks.SHADOW_SHORT_ROOTS.get())).renderType("cutout");
        simpleBlock(ModBlocks.SHADOW_SHORT_ROOTS.get(), shadowShortRoots);

        var shadowRoots = models().cross(ModBlocks.SHADOW_ROOTS.getId().getPath(), blockTexture(ModBlocks.SHADOW_ROOTS.get())).renderType("cutout");
        simpleBlock(ModBlocks.SHADOW_ROOTS.get(), shadowRoots);

        var shadowStemFernUpper = models().cross(ModBlocks.SHADOW_STEM_FERN.getId().getPath() + "_top", modLoc("block/shadow_stem_fern_top")).renderType("cutout");
        var shadowStemFernLower = models().cross(ModBlocks.SHADOW_STEM_FERN.getId().getPath() + "_bottom", modLoc("block/shadow_stem_fern")).renderType("cutout");
        getVariantBuilder(ModBlocks.SHADOW_STEM_FERN.get())
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(shadowStemFernLower))
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(shadowStemFernUpper));

        var shadowSprouts = models().cross(ModBlocks.SHADOW_SPROUTS.getId().getPath(), blockTexture(ModBlocks.SHADOW_SPROUTS.get())).renderType("cutout");
        simpleBlock(ModBlocks.SHADOW_SPROUTS.get(), shadowSprouts);

        var shadowFern = models().cross(ModBlocks.SHADOW_FERN.getId().getPath(), blockTexture(ModBlocks.SHADOW_FERN.get())).renderType("cutout");
        simpleBlock(ModBlocks.SHADOW_FERN.get(), shadowFern);

        var shadowFungus = models().cross(ModBlocks.SHADOW_FUNGUS.getId().getPath(), blockTexture(ModBlocks.SHADOW_FUNGUS.get())).renderType("cutout");
        simpleBlock(ModBlocks.SHADOW_FUNGUS.get(), shadowFungus);

        // 染梦海草
        var dyedreamSeagrass = models().cross(ModBlocks.DYEDREAM_SEAGRASS.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_SEAGRASS.get())).renderType("cutout");
        simpleBlock(ModBlocks.DYEDREAM_SEAGRASS.get(), dyedreamSeagrass);

        // 染梦睡莲 / 染梦莲花 (custom Blockbench models)
        simpleBlock(ModBlocks.DYEDREAM_LILY_PAD.get(), models().getExistingFile(modLoc("block/dyedream_lily_pad")));
        simpleBlock(ModBlocks.DYEDREAM_LOTUS.get(), models().getExistingFile(modLoc("block/dyedream_lotus")));

        // 陶罐
        var clayPotModel = models().getExistingFile(modLoc("block/clay_pot"));
        horizontalBlock(ModBlocks.CLAY_POT.get(), clayPotModel);
        // 阴影陶罐（复用陶罐模型）
        horizontalBlock(ModBlocks.SHADOW_CLAY_POT.get(), clayPotModel);

        // 小石子
        var pebbleModel = models().getExistingFile(modLoc("block/pebble"));
        horizontalBlock(ModBlocks.PEBBLE.get(), pebbleModel);

        //流体方块
        simpleBlock(ModBlocks.MELTDREAM_LIQUID.get(), models().cubeAll(ModBlocks.MELTDREAM_LIQUID.getId().getPath(), modLoc("block/melt_dream_liquid_flowing")));
        simpleBlock(ModBlocks.SHADOW_LIQUID.get(), models().cubeAll(ModBlocks.SHADOW_LIQUID.getId().getPath(), modLoc("block/shadow_liquid_flowing")));

        // ===== 盆栽植物 =====
        for (var entry : ModBlocks.POTTED_PLANTS.entrySet()) {
            var plant = entry.getKey();
            var potted = entry.getValue();
            boolean isCrop = ModBlocks.POTTED_CROPS.contains(potted);

            if (isCrop) {
                // 两阶段生长：age=0 (幼苗) 和 age=1 (成熟)
                var plantName = plant.getId().getPath();
                var seedlingTex = modLoc("block/" + plantName + "_age_0");
                var matureTex = modLoc("block/" + plantName);

                var seedlingModel = models().withExistingParent(potted.getId().getPath() + "_age_0", mcLoc("block/flower_pot_cross"))
                        .texture("plant", seedlingTex).renderType("cutout");
                var matureModel = models().withExistingParent(potted.getId().getPath(), mcLoc("block/flower_pot_cross"))
                        .texture("plant", matureTex).renderType("cutout");

                getVariantBuilder(potted.get())
                        .partialState().with(net.minecraft.world.level.block.state.properties.BlockStateProperties.AGE_1, 0)
                        .addModels(new ConfiguredModel(seedlingModel))
                        .partialState().with(net.minecraft.world.level.block.state.properties.BlockStateProperties.AGE_1, 1)
                        .addModels(new ConfiguredModel(matureModel));
            } else {
                var plantTex = modLoc("block/" + plant.getId().getPath());
                var pottedModel = models().withExistingParent(potted.getId().getPath(), mcLoc("block/flower_pot_cross"))
                        .texture("plant", plantTex).renderType("cutout");
                simpleBlock(potted.get(), pottedModel);
            }
        }

        // 暗影高炉核心
        shadowBlastFurnaceCore();
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

    private void shadowBlastFurnaceCore() {
        var sideTex = modLoc("block/shadow_blast_furnace_core_side");
        var topTex = modLoc("block/shadow_blast_furnace_core_top");
        var model = models().cubeBottomTop("shadow_blast_furnace_core", sideTex, topTex, topTex);
        simpleBlockWithItem(ModBlocks.SHADOW_BLAST_FURNACE_CORE.get(), model);
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

    private void horizontalBookshelf(Block block) {
        String name = ForgeRegistries.BLOCKS.getKey(block).getPath();
        ModelFile model = models().cube(name,
                modLoc("block/shadow_bookshelf_top"),
                modLoc("block/shadow_bookshelf_top"),
                modLoc("block/" + name),
                modLoc("block/" + name),
                modLoc("block/" + name),
                modLoc("block/" + name))
                .texture("particle", modLoc("block/shadow_bookshelf_top"));
        horizontalBlock(block, model);
        simpleBlockItem(block, model);
    }

    private void horizontalBookshelfKey(Block block) {
        String name = ForgeRegistries.BLOCKS.getKey(block).getPath();
        ModelFile model = models().cube(name,
                modLoc("block/shadow_bookshelf_top"),
                modLoc("block/shadow_bookshelf_top"),
                modLoc("block/key_shadow_bookshelf_front"),
                modLoc("block/worn_shadow_bookshelf"),
                modLoc("block/worn_shadow_bookshelf"),
                modLoc("block/worn_shadow_bookshelf"))
                .texture("particle", modLoc("block/shadow_bookshelf_top"));
        horizontalBlock(block, model);
        simpleBlockItem(block, model);
    }
}
