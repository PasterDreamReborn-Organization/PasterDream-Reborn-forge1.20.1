package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.tag.ModBlockTags;
import com.pasterdream.pasterdreammod.util.BuildingBlockFamily;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.DIRT)
                .add(ModBlocks.DYEDREAM_DIRT.get())
                .add(ModBlocks.DYEDREAM_GRASS_BLOCK.get());

        tag(BlockTags.ANIMALS_SPAWNABLE_ON)
                .add(ModBlocks.DYEDREAM_GRASS_BLOCK.get());

        tag(BlockTags.FOXES_SPAWNABLE_ON)
                .add(ModBlocks.DYEDREAM_GRASS_BLOCK.get());

        // 可以用铲子来挖掘
        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.DYEDREAM_DIRT.get())
                .add(ModBlocks.DYEDREAM_GRASS_BLOCK.get())
                .add(ModBlocks.DYEDREAM_FARMLAND.get())
                .add(ModBlocks.SOUL_ORE.get())
                .add(ModBlocks.SALT_BLOCK.get())
                .add(ModBlocks.DYEDREAM_SAND.get())
                .add(ModBlocks.WHITE_SAND.get());


        // 可以用锄头来挖掘
        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.DYEDREAM_LEAVES.get())
                .add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get())
                .add(ModBlocks.PINK_MUSHROOM_BLOCK.get())
                .add(ModBlocks.PINK_MUSHROOM_STEM.get())
                .add(ModBlocks.PINK_MUSHROOM_PORES.get())
                .add(ModBlocks.PINK_SHROOMLIGHT.get())
                .add(ModBlocks.SHADOW_SHROOMLIGHT.get())
                .add(ModBlocks.SHADOW_WART_BLOCK.get())
                .add(ModBlocks.SHADOW_HAND_TRAP.get());

        // 可以用斧子来挖掘
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get())
                .add(ModBlocks.PINK_MUSHROOM_BLOCK.get())
                .add(ModBlocks.PINK_MUSHROOM_STEM.get())
                .add(ModBlocks.PINK_MUSHROOM_PORES.get())
                .add(ModBlocks.PINK_SHROOMLIGHT.get())
                .add(ModBlocks.DYEDREAM_DESK.get())
                .add(ModBlocks.SHADOW_DESK.get())
                .add(ModBlocks.PICNIC_BASKET.get())
                .add(ModBlocks.WIND_MOOR_CRATE.get())
                .add(ModBlocks.SHADOW_STEM.get())
                .add(ModBlocks.SHADOW_HYPHAE.get())
                .add(ModBlocks.STRIPPED_SHADOW_STEM.get())
                .add(ModBlocks.STRIPPED_SHADOW_HYPHAE.get());

        // 可以用镐来挖掘
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.DYEDREAM_QUARTZ_ORE.get())
                .add(ModBlocks.DYEDREAM_DUST_ORE.get())
                .add(ModBlocks.AMBER_CANDY_ORE.get())
                .add(ModBlocks.DYEDREAM_BUDDING_BLOCK.get())
                .add(ModBlocks.SMALL_DYEDREAM_BUD.get())
                .add(ModBlocks.MEDIUM_DYEDREAM_BUD.get())
                .add(ModBlocks.LARGE_DYEDREAM_BUD.get())
                .add(ModBlocks.TITANIUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get())
                .add(ModBlocks.MOLTEN_GOLD_ORE.get())
                .add(ModBlocks.DYEDREAM_ICE.get())
                .add(ModBlocks.DYEDREAM_PACKED_ICE.get())
                .add(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get())
                .add(ModBlocks.DYEDREAM_LANTERN.get())
                .add(ModBlocks.ICE_STONE.get())
                .add(ModBlocks.ICE_BUD.get())
                .add(ModBlocks.CYAN_STONE.get())
                .add(ModBlocks.CYAN_MOSS_STONE.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get())
                .add(ModBlocks.CLAYPAN.get())
                .add(ModBlocks.CLAY_POT.get())
                .add(ModBlocks.SHADOW_CLAY_POT.get())
                .add(ModBlocks.PEBBLE.get())
                .add(ModBlocks.DREAM_CAULDRON.get())
                .add(ModBlocks.LOST_SWORD_TOMB.get())
                .add(ModBlocks.DYEDREAM_ALLOY_BLOCK.get())
                .add(ModBlocks.RAW_TITANIUM_BLOCK.get())
                .add(ModBlocks.TITANIUM_BLOCK.get())
                .add(ModBlocks.MOLTEN_GOLD_BLOCK.get())
                .add(ModBlocks.CHARGED_AMETHYST_BLOCK.get())
                .add(ModBlocks.RUST_BLACK_METAL_BLOCK.get())
                .add(ModBlocks.RUST_BLACK_METAL_WALL.get())
                .add(ModBlocks.RUST_BLACK_METAL_BARS.get())
                .add(ModBlocks.BLACK_METAL_BLOCK.get())
                .add(ModBlocks.SHADOW_CHEST.get())
                .add(ModBlocks.SHADOW_STONE.get())
                .add(ModBlocks.CRACKED_SHADOW_STONE_BRICK.get())
                .add(ModBlocks.CHISELED_SHADOW_STONE_BRICK.get())
                .add(ModBlocks.SHADOW_DUNGEON_STONE.get())
                .add(ModBlocks.CHISELED_SHADOW_DUNGEON_BRICKS.get())
                .add(ModBlocks.SHADOW_DUNGEON_BRICKS.get())
                .add(ModBlocks.CRACKED_SHADOW_DUNGEON_BRICKS.get())
                .add(ModBlocks.FRACTURED_SHADOW_DUNGEON_BRICKS.get())
                .add(ModBlocks.SHADOW_DUNGEON_BRICK_STAIRS.get())
                .add(ModBlocks.SHADOW_DUNGEON_BRICK_SLAB.get())
                .add(ModBlocks.SHATTERED_SHADOW_DUNGEON_BRICKS.get())
                .add(ModBlocks.SHADOW_ARENA_BLOCK.get())
                .add(ModBlocks.SHADOW_FISSURE_0.get())
                .add(ModBlocks.SHADOW_FISSURE_1.get())
                .add(ModBlocks.SHADOW_FISSURE_2.get())
                .add(ModBlocks.SHADOW_FISSURE_3.get())
                .add(ModBlocks.SHADOW_FISSURE_4.get())
                .add(ModBlocks.SHADOW_FISSURE_5.get())
                .add(ModBlocks.SHADOW_NYLIUM.get())
                .add(ModBlocks.SHADOW_LIGHT.get())
                .add(ModBlocks.SHADOW_CANDLE.get())
                .add(ModBlocks.OPENED_MELT_DREAM_CRYSTAL_CHEST.get())
                .add(ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE.get())
                .add(ModBlocks.WEAPON_WORKSHOP_ANVIL.get())
                .add(ModBlocks.WEAPON_WORKSHOP_COOLER_POT.get())
                .add(ModBlocks.WEAPON_WORKSHOP_HAMMER.get())
                .add(ModBlocks.WEAPON_WORKSHOP_BLAST_FURNACE.get())
                .add(ModBlocks.SHADOW_BLAST_FURNACE.get());

        //整个树的结构（包含原木，木头，树叶，菌柄，菌核，菌疣）
        tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get())
                .add(ModBlocks.SHADOW_STEM.get())
                .add(ModBlocks.SHADOW_HYPHAE.get())
                .add(ModBlocks.STRIPPED_SHADOW_STEM.get())
                .add(ModBlocks.STRIPPED_SHADOW_HYPHAE.get());

        //可以燃烧的木头（可制成木炭，可作为燃料）
        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get());

        tag(BlockTags.LOGS)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get())
                .add(ModBlocks.SHADOW_STEM.get())
                .add(ModBlocks.SHADOW_HYPHAE.get())
                .add(ModBlocks.STRIPPED_SHADOW_STEM.get())
                .add(ModBlocks.STRIPPED_SHADOW_HYPHAE.get());

        tag(ModBlockTags.STRIPPED_LOGS)
                .add(ModBlocks.DYEDREAM_LOG.get());

        tag(ModBlockTags.STRIPPED_WOOD)
                .add(ModBlocks.DYEDREAM_WOOD.get());


        tag(BlockTags.LEAVES)
                .add(ModBlocks.DYEDREAM_LEAVES.get())
                .add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get());

        tag(BlockTags.SAPLINGS)
                .add(ModBlocks.DYEDREAM_SAPLING.get());

        tag(BlockTags.FLOWERS)
                .add(ModBlocks.GOLDENROD.get())
                .add(ModBlocks.FERRARIA_CRISPA.get())
                .add(ModBlocks.EUSTOMA.get())
                .add(ModBlocks.MALVA_SINENSIS_CAVAN.get())
                .add(ModBlocks.BLAZE_FLOWER.get())
                .add(ModBlocks.WHITE_ORCHID_FLOWER.get())
                .add(ModBlocks.EDELWEISS.get())
                .add(ModBlocks.NIPPY_EDELWEISS.get())
                .add(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get())
                .add(ModBlocks.LINHT_FLOWER.get())
                .add(ModBlocks.MISTY_DREAMING_LOTUS.get())
                .add(ModBlocks.DREAMING_LOTUS.get());

        tag(BlockTags.SMALL_FLOWERS)
                .add(ModBlocks.GOLDENROD.get())
                .add(ModBlocks.FERRARIA_CRISPA.get())
                .add(ModBlocks.EUSTOMA.get())
                .add(ModBlocks.MALVA_SINENSIS_CAVAN.get())
                .add(ModBlocks.BLAZE_FLOWER.get())
                .add(ModBlocks.WHITE_ORCHID_FLOWER.get())
                .add(ModBlocks.EDELWEISS.get())
                .add(ModBlocks.NIPPY_EDELWEISS.get())
                .add(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get())
                .add(ModBlocks.LINHT_FLOWER.get())
                .add(ModBlocks.DYEDREAM_LILY_PAD.get())
                .add(ModBlocks.DYEDREAM_LOTUS.get());

        // 需要石头工具挖掘
        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.ICE_BUD.get())
                .add(ModBlocks.CHARGED_AMETHYST_BLOCK.get())
                .add(ModBlocks.SHADOW_NYLIUM.get())
                .add(ModBlocks.SHADOW_LIGHT.get());


        // 需要铁工具挖掘
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.DYEDREAM_BUDDING_BLOCK.get())
                .add(ModBlocks.DYEDREAM_DUST_ORE.get())
                .add(ModBlocks.SMALL_DYEDREAM_BUD.get())
                .add(ModBlocks.MEDIUM_DYEDREAM_BUD.get())
                .add(ModBlocks.LARGE_DYEDREAM_BUD.get())
                .add(ModBlocks.MOLTEN_GOLD_ORE.get())
                .add(ModBlocks.MOLTEN_GOLD_BLOCK.get())
                .add(ModBlocks.DYEDREAM_BUD_BLOCK.get())
                .add(ModBlocks.DYEDREAM_BUD_STAIRS.get())
                .add(ModBlocks.DYEDREAM_BUD_SLAB.get())
                .add(ModBlocks.DYEDREAM_BUD_WALL.get())
                .add(ModBlocks.RUST_BLACK_METAL_BLOCK.get())
                .add(ModBlocks.RUST_BLACK_METAL_WALL.get())
                .add(ModBlocks.RUST_BLACK_METAL_BARS.get())
                .add(ModBlocks.SHADOW_BLAST_FURNACE_CORE.get());
        // 需要钻石工具挖掘
        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.DYEDREAM_ALLOY_BLOCK.get())
                .add(ModBlocks.TITANIUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get())
                .add(ModBlocks.RAW_TITANIUM_BLOCK.get())
                .add(ModBlocks.TITANIUM_BLOCK.get())
                .add(ModBlocks.BLACK_METAL_BLOCK.get());


        // 可作为信标基座
        tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.DYEDREAM_ALLOY_BLOCK.get())
                .add(ModBlocks.TITANIUM_BLOCK.get())
                .add(ModBlocks.MOLTEN_GOLD_BLOCK.get())
                .add(ModBlocks.BLACK_METAL_BLOCK.get());

        tag(Tags.Blocks.ORES)
                .add(ModBlocks.DYEDREAM_QUARTZ_ORE.get())
                .add(ModBlocks.DYEDREAM_DUST_ORE.get())
                .add(ModBlocks.AMBER_CANDY_ORE.get())
                .add(ModBlocks.TITANIUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get())
                .add(ModBlocks.MOLTEN_GOLD_ORE.get());


        tag(BlockTags.STAIRS)
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get());

        tag(BlockTags.SLABS)
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get());

        tag(BlockTags.WALLS)
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get())
                .add(ModBlocks.RUST_BLACK_METAL_WALL.get());

        // 建材系列批量添加
        var budFamily = new BuildingBlockFamily(ModBlocks.DYEDREAM_BUD_BLOCK, ModBlocks.DYEDREAM_BUD_STAIRS, ModBlocks.DYEDREAM_BUD_SLAB, ModBlocks.DYEDREAM_BUD_WALL);
        var polishedCalciteFamily = new BuildingBlockFamily(ModBlocks.POLISHED_CALCITE, ModBlocks.POLISHED_CALCITE_STAIRS, ModBlocks.POLISHED_CALCITE_SLAB, ModBlocks.POLISHED_CALCITE_WALL);
        var calciteTilesFamily = new BuildingBlockFamily(ModBlocks.CALCITE_TILES, ModBlocks.CALCITE_TILES_STAIRS, ModBlocks.CALCITE_TILES_SLAB, ModBlocks.CALCITE_TILES_WALL);
        var shadowStoneBrickFamily = new BuildingBlockFamily(ModBlocks.SHADOW_STONE_BRICK, ModBlocks.SHADOW_STONE_BRICK_STAIRS, ModBlocks.SHADOW_STONE_BRICK_SLAB, ModBlocks.SHADOW_STONE_BRICK_WALL);
        var narrowShadowStoneBrickFamily = new BuildingBlockFamily(ModBlocks.NARROW_SHADOW_STONE_BRICK, ModBlocks.NARROW_SHADOW_STONE_BRICK_STAIRS, ModBlocks.NARROW_SHADOW_STONE_BRICK_SLAB, ModBlocks.NARROW_SHADOW_STONE_BRICK_WALL);
        var shadowStoneTilesFamily = new BuildingBlockFamily(ModBlocks.SHADOW_STONE_TILES, ModBlocks.SHADOW_STONE_TILES_STAIRS, ModBlocks.SHADOW_STONE_TILES_SLAB, ModBlocks.SHADOW_STONE_TILES_WALL);

        addBuildingFamilyToTags(budFamily);
        addBuildingFamilyToTags(polishedCalciteFamily);
        addBuildingFamilyToTags(calciteTilesFamily);
        addBuildingFamilyToTags(shadowStoneBrickFamily);
        addBuildingFamilyToTags(narrowShadowStoneBrickFamily);
        addBuildingFamilyToTags(shadowStoneTilesFamily);

        // ===== 木板建材系列 =====

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.DYEDREAM_PLANKS.get())
                .add(ModBlocks.DYEDREAM_STAIRS.get())
                .add(ModBlocks.DYEDREAM_SLAB.get())
                .add(ModBlocks.DYEDREAM_FENCE.get())
                .add(ModBlocks.DYEDREAM_FENCE_GATE.get())
                .add(ModBlocks.DYEDREAM_PANE.get())
                .add(ModBlocks.DYEDREAM_DOOR.get())
                .add(ModBlocks.DYEDREAM_TRAPDOOR.get())
                .add(ModBlocks.DYEDREAM_PRESSURE_PLATE.get())
                .add(ModBlocks.DYEDREAM_BUTTON.get())
                .add(ModBlocks.SHADOW_PLANKS.get())
                .add(ModBlocks.SHADOW_STAIRS.get())
                .add(ModBlocks.SHADOW_SLAB.get())
                .add(ModBlocks.SHADOW_FENCE.get())
                .add(ModBlocks.SHADOW_FENCE_GATE.get())
                .add(ModBlocks.SHADOW_PANE.get())
                .add(ModBlocks.SHADOW_DOOR.get())
                .add(ModBlocks.SHADOW_TRAPDOOR.get())
                .add(ModBlocks.SHADOW_PRESSURE_PLATE.get())
                .add(ModBlocks.SHADOW_BUTTON.get())
                .add(ModBlocks.SHADOW_BOOKSHELF.get())
                .add(ModBlocks.WORN_SHADOW_BOOKSHELF.get())
                .add(ModBlocks.COBWEB_SHADOW_BOOKSHELF.get())
                .add(ModBlocks.KEY_SHADOW_BOOKSHELF.get());

        tag(BlockTags.PLANKS)
                .add(ModBlocks.DYEDREAM_PLANKS.get())
                .add(ModBlocks.SHADOW_PLANKS.get());

        tag(BlockTags.WOODEN_STAIRS)
                .add(ModBlocks.DYEDREAM_STAIRS.get())
                .add(ModBlocks.SHADOW_STAIRS.get());

        tag(BlockTags.WOODEN_SLABS)
                .add(ModBlocks.DYEDREAM_SLAB.get())
                .add(ModBlocks.SHADOW_SLAB.get());

        tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.DYEDREAM_FENCE.get())
                .add(ModBlocks.SHADOW_FENCE.get());

        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.DYEDREAM_FENCE_GATE.get())
                .add(ModBlocks.SHADOW_FENCE_GATE.get());

        tag(BlockTags.WOODEN_DOORS)
                .add(ModBlocks.DYEDREAM_DOOR.get())
                .add(ModBlocks.SHADOW_DOOR.get());

        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.DYEDREAM_TRAPDOOR.get())
                .add(ModBlocks.SHADOW_TRAPDOOR.get());

        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.DYEDREAM_PRESSURE_PLATE.get())
                .add(ModBlocks.SHADOW_PRESSURE_PLATE.get());

        tag(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.DYEDREAM_BUTTON.get())
                .add(ModBlocks.SHADOW_BUTTON.get());

        // ===== 阴影书架（forge:bookshelves 兼容标签） =====
        tag(Tags.Blocks.BOOKSHELVES)
                .add(ModBlocks.SHADOW_BOOKSHELF.get());

        // ===== 模组玻璃板 =====

        tag(ModBlockTags.MOD_GLASS_PANE)
                .add(ModBlocks.DYEDREAM_GLASS_PANE.get())
                .add(ModBlocks.CARVE_DYEDREAM_GLASS_PANE.get())
                .add(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS_PANE.get());

        tag(ModBlockTags.FORGE_GLASS_PANE)
                .addTag(ModBlockTags.MOD_GLASS_PANE);


        // 可攀爬方块（藤蔓、梯子等）
        tag(BlockTags.CLIMBABLE)
                .add(ModBlocks.DYEDREAM_VINE.get());

        tag(TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_dirt")))
                .add(ModBlocks.DYEDREAM_DIRT.get())
                .add(ModBlocks.DYEDREAM_GRASS_BLOCK.get());

        tag(TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("pasterdream", "white_corolla_can_place_on")))
                .add(Blocks.SNOW_BLOCK)
                .addTag(BlockTags.DIRT);

        tag(ModBlockTags.CRIMSON_THORNS_CAN_PLACE_ON)
                .add(Blocks.CRIMSON_NYLIUM)
                .add(Blocks.NETHERRACK)
                .add(Blocks.MAGMA_BLOCK);

        tag(ModBlockTags.BLAZE_FLOWER_CAN_PLACE_ON)
                .add(Blocks.CRIMSON_NYLIUM)
                .add(Blocks.NETHERRACK)
                .add(Blocks.MAGMA_BLOCK);

        tag(ModBlockTags.SHADOW_PLANTS_CAN_PLACE_ON)
                .add(ModBlocks.SHADOW_NYLIUM.get())
                .add(Blocks.CRIMSON_NYLIUM)
                .add(Blocks.WARPED_NYLIUM);

        tag(BlockTags.VALID_SPAWN)
                .add(ModBlocks.SHADOW_NYLIUM.get())
                .add(ModBlocks.SHADOW_WART_BLOCK.get())
                .add(ModBlocks.SHADOW_STONE.get());

        // 暂时无法自动填充到
        tag(ModBlockTags.DYEDREAM_BUD_CAN_SPAWN_ON)
                .add(ModBlocks.DYEDREAM_DIRT.get())
                .add(ModBlocks.DYEDREAM_GRASS_BLOCK.get())
                .add(Blocks.CALCITE)
                .add(ModBlocks.DYEDREAM_BUDDING_BLOCK.get());


        // 暂时无法自动填充到ON_DYEDREAM_GROUND中，需要手动添加
        tag(ModBlockTags.DYEDREAM_GROUND_PLANTS_CAN_SPAWN_ON)
                .add(ModBlocks.DYEDREAM_DIRT.get())
                .add(ModBlocks.DYEDREAM_GRASS_BLOCK.get());

        //不可被流体破坏的无碰撞箱方块
        tag(TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fluid_can_not_brush_nocollission_block")))
                .add(ModBlocks.DYEDREAM_CRACK.get())
                .add(ModBlocks.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get())
                .add(ModBlocks.BIG_BUBBLE.get())
                .add(ModBlocks.SHADOW_VORTEX.get());
      
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.LIFE_CRYSTAL.get())
                .add(ModBlocks.GOLDEN_FOX_SCULPTURE.get())
                .add(ModBlocks.FOX_SCULPTURE.get())
                .add(ModBlocks.SHADOW_BRAZIER.get())
                .add(ModBlocks.SHADOW_BLAST_FURNACE_CORE.get());

        // ===== 盆栽植物 =====
        var flowerPots = tag(BlockTags.FLOWER_POTS);
        for (var potted : ModBlocks.POTTED_PLANTS.values()) {
            flowerPots.add(potted.get());
        }

        tag(ModBlockTags.PLIER_PLANTS)
                .add(ModBlocks.STEM_GRASS.get())
                .add(ModBlocks.TALL_STEM_GRASS.get())
                .add(ModBlocks.DYEDREAM_MOSS.get())
                .add(ModBlocks.SINGULARITY_FERN.get())
                .add(ModBlocks.DYEDREAM_LILY_PAD.get())
                .add(ModBlocks.DYEDREAM_SEAGRASS.get())
                .add(ModBlocks.DYEDREAM_VINE.get())
                .add(ModBlocks.DYEDREAM_LOTUS.get())
                .add(ModBlocks.DREAMING_LOTUS.get())
                .add(ModBlocks.MISTY_DREAMING_LOTUS.get())
                .add(ModBlocks.GOLDENROD.get())
                .add(ModBlocks.FERRARIA_CRISPA.get())
                .add(ModBlocks.EUSTOMA.get())
                .add(ModBlocks.MALVA_SINENSIS_CAVAN.get())
                .add(ModBlocks.LINHT_FLOWER.get())
                .add(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get())
                .add(ModBlocks.BLAZE_FLOWER.get())
                .add(ModBlocks.WHITE_ORCHID_FLOWER.get())
                .add(ModBlocks.EDELWEISS.get())
                .add(ModBlocks.NIPPY_EDELWEISS.get())
                .add(ModBlocks.CRIMSON_THORNS.get())
                .add(ModBlocks.REED.get())
                .add(ModBlocks.OATS.get())
                .add(ModBlocks.RYE.get())
                .add(ModBlocks.JUNGLE_SPORANGIUM.get())
                .add(ModBlocks.PINK_MUSHROOM.get())
                .add(ModBlocks.TALL_PINK_MUSHROOM.get())
                .add(ModBlocks.SHADOW_SHORT_ROOTS.get())
                .add(ModBlocks.SHADOW_ROOTS.get())
                .add(ModBlocks.SHADOW_STEM_FERN.get())
                .add(ModBlocks.SHADOW_SPROUTS.get())
                .add(ModBlocks.SHADOW_FERN.get())
                .add(ModBlocks.SHADOW_FUNGUS.get());

        // ===== 影蚀工具挖掘加速的暗影类方块 =====
        tag(ModBlockTags.SHADOW_EROSION_TOOL_CAN_BOOST)
                // 基础阴影方块
                .add(ModBlocks.SHADOW.get())
                .add(ModBlocks.THICK_SHADOW.get())
                // 阴影石系列
                .add(ModBlocks.SHADOW_STONE.get())
                .add(ModBlocks.SHADOW_STONE_BRICK.get())
                .add(ModBlocks.SHADOW_STONE_BRICK_STAIRS.get())
                .add(ModBlocks.SHADOW_STONE_BRICK_SLAB.get())
                .add(ModBlocks.SHADOW_STONE_BRICK_WALL.get())
                .add(ModBlocks.NARROW_SHADOW_STONE_BRICK.get())
                .add(ModBlocks.NARROW_SHADOW_STONE_BRICK_STAIRS.get())
                .add(ModBlocks.NARROW_SHADOW_STONE_BRICK_SLAB.get())
                .add(ModBlocks.NARROW_SHADOW_STONE_BRICK_WALL.get())
                .add(ModBlocks.SHADOW_STONE_TILES.get())
                .add(ModBlocks.SHADOW_STONE_TILES_STAIRS.get())
                .add(ModBlocks.SHADOW_STONE_TILES_SLAB.get())
                .add(ModBlocks.SHADOW_STONE_TILES_WALL.get())
                .add(ModBlocks.CRACKED_SHADOW_STONE_BRICK.get())
                .add(ModBlocks.CHISELED_SHADOW_STONE_BRICK.get())
                // 碎裂暗影地牢石砖（唯一可挖掘的地牢石砖）
                .add(ModBlocks.SHATTERED_SHADOW_DUNGEON_BRICKS.get())
                // 阴影自然方块
                .add(ModBlocks.SHADOW_NYLIUM.get())
                .add(ModBlocks.SHADOW_WART_BLOCK.get())
                .add(ModBlocks.SHADOW_STEM.get())
                .add(ModBlocks.SHADOW_HYPHAE.get())
                .add(ModBlocks.STRIPPED_SHADOW_STEM.get())
                .add(ModBlocks.STRIPPED_SHADOW_HYPHAE.get())
                .add(ModBlocks.SHADOW_SHROOMLIGHT.get())
                .add(ModBlocks.SHADOW_LIGHT.get())
                .add(ModBlocks.SHADOW_CANDLE.get())
                // 阴影木板系列
                .add(ModBlocks.SHADOW_PLANKS.get())
                .add(ModBlocks.SHADOW_STAIRS.get())
                .add(ModBlocks.SHADOW_SLAB.get())
                .add(ModBlocks.SHADOW_FENCE.get())
                .add(ModBlocks.SHADOW_FENCE_GATE.get())
                .add(ModBlocks.SHADOW_PANE.get())
                .add(ModBlocks.SHADOW_DOOR.get())
                .add(ModBlocks.SHADOW_TRAPDOOR.get())
                .add(ModBlocks.SHADOW_PRESSURE_PLATE.get())
                .add(ModBlocks.SHADOW_BUTTON.get())
                // 阴影书架
                .add(ModBlocks.SHADOW_BOOKSHELF.get())
                .add(ModBlocks.WORN_SHADOW_BOOKSHELF.get())
                .add(ModBlocks.COBWEB_SHADOW_BOOKSHELF.get())
                .add(ModBlocks.KEY_SHADOW_BOOKSHELF.get())
                // 黑金属
                .add(ModBlocks.BLACK_METAL_BLOCK.get())
                .add(ModBlocks.RUST_BLACK_METAL_BLOCK.get())
                .add(ModBlocks.RUST_BLACK_METAL_WALL.get())
                .add(ModBlocks.RUST_BLACK_METAL_BARS.get())
                // 深板岩
                .add(Blocks.DEEPSLATE)
                .add(Blocks.COBBLED_DEEPSLATE)
                .add(Blocks.COBBLED_DEEPSLATE_STAIRS)
                .add(Blocks.COBBLED_DEEPSLATE_SLAB)
                .add(Blocks.COBBLED_DEEPSLATE_WALL)
                .add(Blocks.POLISHED_DEEPSLATE)
                .add(Blocks.POLISHED_DEEPSLATE_STAIRS)
                .add(Blocks.POLISHED_DEEPSLATE_SLAB)
                .add(Blocks.POLISHED_DEEPSLATE_WALL)
                .add(Blocks.DEEPSLATE_BRICKS)
                .add(Blocks.DEEPSLATE_BRICK_STAIRS)
                .add(Blocks.DEEPSLATE_BRICK_SLAB)
                .add(Blocks.DEEPSLATE_BRICK_WALL)
                .add(Blocks.DEEPSLATE_TILES)
                .add(Blocks.DEEPSLATE_TILE_STAIRS)
                .add(Blocks.DEEPSLATE_TILE_SLAB)
                .add(Blocks.DEEPSLATE_TILE_WALL)
                .add(Blocks.CHISELED_DEEPSLATE)
                .add(Blocks.CRACKED_DEEPSLATE_BRICKS)
                .add(Blocks.CRACKED_DEEPSLATE_TILES)
                // 深板岩矿石
                .addTag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE)
                // 阴影功能方块
                .add(ModBlocks.SHADOW_DESK.get())
                .add(ModBlocks.SHADOW_CHEST.get())
                .add(ModBlocks.CLAY_POT.get())
                .add(ModBlocks.SHADOW_CLAY_POT.get())
                .add(ModBlocks.SHADOW_HAND_TRAP.get())
                // 幽匿系列
                .add(Blocks.SCULK)
                .add(Blocks.SCULK_SENSOR)
                .add(Blocks.CALIBRATED_SCULK_SENSOR)
                .add(Blocks.SCULK_SHRIEKER)
                .add(Blocks.SCULK_CATALYST)
                .add(Blocks.SCULK_VEIN)
                // 暗影高炉核心
                .add(ModBlocks.SHADOW_BLAST_FURNACE_CORE.get());

    }

    private void addBuildingFamilyToTags(BuildingBlockFamily family) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(family.base().get())
                .add(family.stairs().get())
                .add(family.slab().get())
                .add(family.wall().get());
        this.tag(BlockTags.STAIRS).add(family.stairs().get());
        this.tag(BlockTags.SLABS).add(family.slab().get());
        this.tag(BlockTags.WALLS).add(family.wall().get());
    }
}
