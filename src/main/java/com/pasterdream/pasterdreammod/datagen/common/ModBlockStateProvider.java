package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
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

        stairsBlock((StairBlock) ModBlocks.DYEDREAM_PLANKS_STAIRS.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));
        blockItem(ModBlocks.DYEDREAM_PLANKS_STAIRS);

        slabBlock((SlabBlock) ModBlocks.DYEDREAM_PLANKS_SLAB.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));
        blockItem(ModBlocks.DYEDREAM_PLANKS_SLAB);

        fenceBlock((FenceBlock) ModBlocks.DYEDREAM_PLANKS_FENCE.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));

        fenceGateBlock((FenceGateBlock) ModBlocks.DYEDREAM_PLANKS_FENCEGATE.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));
        blockItem(ModBlocks.DYEDREAM_PLANKS_FENCEGATE);

        paneBlock((IronBarsBlock) ModBlocks.DYEDREAM_PLANKS_PANE.get(), modLoc("block/dyedream_planks_trapdoor"), modLoc("block/dyedream_planks_trapdoor"));

        doorBlockWithRenderType((DoorBlock) ModBlocks.DYEDREAM_PLANKS_DOOR.get(),
                modLoc("block/dyedream_planks_door_bottom"),
                modLoc("block/dyedream_planks_door_top"),
                "cutout");

        trapdoorBlockWithRenderType((TrapDoorBlock) ModBlocks.DYEDREAM_PLANKS_TRAPDOOR.get(),
                modLoc("block/dyedream_planks_trapdoor"),
                true,
                "cutout");
        blockItem(ModBlocks.DYEDREAM_PLANKS_TRAPDOOR, "_bottom");

        pressurePlateBlock((PressurePlateBlock) ModBlocks.DYEDREAM_PLANKS_PRESSURE_PLATE.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));
        blockItem(ModBlocks.DYEDREAM_PLANKS_PRESSURE_PLATE);

        buttonBlock((ButtonBlock) ModBlocks.DYEDREAM_PLANKS_BUTTON.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));

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

        var budTex = blockTexture(ModBlocks.DYEDREAM_BUD_BLOCK.get());
        simpleBlockWithItem(ModBlocks.DYEDREAM_BUD_BLOCK.get(), cubeAll(ModBlocks.DYEDREAM_BUD_BLOCK.get()));

        stairsBlock((StairBlock) ModBlocks.DYEDREAM_BUD_STAIRS.get(), budTex);
        blockItem(ModBlocks.DYEDREAM_BUD_STAIRS);

        slabBlock((SlabBlock) ModBlocks.DYEDREAM_BUD_SLAB.get(), budTex, budTex);
        blockItem(ModBlocks.DYEDREAM_BUD_SLAB);

        wallBlock((WallBlock) ModBlocks.DYEDREAM_BUD_WALL.get(), budTex);

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
        simpleBlockWithItem(ModBlocks.DYEDREAM_LARTERN.get(), cubeAll(ModBlocks.DYEDREAM_LARTERN.get()));

        // ===== 冰晶岩与冰凌晶芽 =====
        simpleBlockWithItem(ModBlocks.ICE_STONE.get(), cubeAll(ModBlocks.ICE_STONE.get()));

        var iceBud = models().cross(ModBlocks.ICE_BUD.getId().getPath(), blockTexture(ModBlocks.ICE_BUD.get())).renderType("cutout");
        directionalBlock(ModBlocks.ICE_BUD.get(), iceBud);

        // ===== 染梦合金块 =====
        simpleBlockWithItem(ModBlocks.DYEDREAM_ALLOY_BLOCK.get(), cubeAll(ModBlocks.DYEDREAM_ALLOY_BLOCK.get()));

        //流体方块
        simpleBlock(ModBlocks.MELTDREAM_LIQUID.get(), models().cubeAll(ModBlocks.MELTDREAM_LIQUID.getId().getPath(), modLoc("block/meltdream_liquid_flowing")));
        simpleBlock(ModBlocks.SHADOW_LIQUID.get(), models().cubeAll(ModBlocks.SHADOW_LIQUID.getId().getPath(), modLoc("block/shadow_liquid_flowing")));
    }

    private <T extends Block> void blockItem(RegistryObject<T> block) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(PasterDreamMod.MOD_ID + ":block/" + block.getId().getPath()));
    }

    private <T extends Block> void blockItem(RegistryObject<T> block, String append) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(PasterDreamMod.MOD_ID + ":block/" + block.getId().getPath() + append));
    }
}
