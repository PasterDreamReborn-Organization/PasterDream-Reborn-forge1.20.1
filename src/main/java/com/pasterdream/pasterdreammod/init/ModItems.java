package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.AmberCandyItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PasterDreamMod.MOD_ID);

    // ===== 素材物品 =====

    public static final RegistryObject<Item> DYEDREAM_QUARTZ = ITEMS.register("dyedream_quartz",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_DUST = ITEMS.register("dyedream_dust",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_DUST_PIECE = ITEMS.register("dyedream_dust_piece",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AMBER_CANDY = ITEMS.register("amber_candy",
            AmberCandyItem::new);
    public static final RegistryObject<Item> PINK_SLIMEBALL = ITEMS.register("pink_slimeball",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MORTAR = ITEMS.register("mortar",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DYEDREAM_DYE = ITEMS.register("dyedream_dye",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DYEDREAM_BUD_NUGGET = ITEMS.register("dyedream_bud_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_DYEDREAM_ALLOY_INGOT = ITEMS.register("raw_dyedream_alloy_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DYEDREAM_ALLOY_INGOT = ITEMS.register("dyedream_alloy_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TITANIUM_NUGGET = ITEMS.register("titanium_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_TITANIUM = ITEMS.register("raw_titanium",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DYEDREAM_ALLOY_NUGGET = ITEMS.register("dyedream_alloy_nugget",
            () -> new Item(new Item.Properties()));

    // ===== 方块物品 =====

    public static final RegistryObject<Item> DYEDREAM_DIRT = ITEMS.register("dyedream_dirt",
            () -> new BlockItem(ModBlocks.DYEDREAM_DIRT.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_GRASS_BLOCK = ITEMS.register("dyedream_grass_block",
            () -> new BlockItem(ModBlocks.DYEDREAM_GRASS_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_LOG = ITEMS.register("dyedream_log",
            () -> new BlockItem(ModBlocks.DYEDREAM_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_WOOD = ITEMS.register("dyedream_wood",
            () -> new BlockItem(ModBlocks.DYEDREAM_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_LEAVES = ITEMS.register("dyedream_leaves",
            () -> new BlockItem(ModBlocks.DYEDREAM_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_WORLDTREE_LEAVES = ITEMS.register("dyedream_worldtree_leaves",
            () -> new BlockItem(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_SAPLING = ITEMS.register("dyedream_sapling",
            () -> new BlockItem(ModBlocks.DYEDREAM_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_QUARTZ_ORE = ITEMS.register("dyedream_quartz_ore",
            () -> new BlockItem(ModBlocks.DYEDREAM_QUARTZ_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_DUST_ORE = ITEMS.register("dyedream_dust_ore",
            () -> new BlockItem(ModBlocks.DYEDREAM_DUST_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> AMBER_CANDY_ORE = ITEMS.register("amber_candy_ore",
            () -> new BlockItem(ModBlocks.AMBER_CANDY_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_SLIME_BLOCK = ITEMS.register("pink_slime_block",
            () -> new BlockItem(ModBlocks.PINK_SLIME_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_MUSHROOM_BLOCK = ITEMS.register("pink_mushroom_block",
            () -> new BlockItem(ModBlocks.PINK_MUSHROOM_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_MUSHROOM_STEM = ITEMS.register("pink_mushroom_stem",
            () -> new BlockItem(ModBlocks.PINK_MUSHROOM_STEM.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_MUSHROOM_PORES = ITEMS.register("pink_mushroom_pores",
            () -> new BlockItem(ModBlocks.PINK_MUSHROOM_PORES.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_SHROOMLIGHT = ITEMS.register("pink_shroomlight",
            () -> new BlockItem(ModBlocks.PINK_SHROOMLIGHT.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_MUSHROOM = ITEMS.register("pink_mushroom",
            () -> new BlockItem(ModBlocks.PINK_MUSHROOM.get(), new Item.Properties()));
    public static final RegistryObject<Item> TALL_PINK_MUSHROOM = ITEMS.register("tall_pink_mushroom",
            () -> new BlockItem(ModBlocks.TALL_PINK_MUSHROOM.get(), new Item.Properties()));

    // ===== 染梦玻璃系列 =====

    public static final RegistryObject<Item> DYEDREAM_SAND = ITEMS.register("dyedream_sand",
            () -> new BlockItem(ModBlocks.DYEDREAM_SAND.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_GLASS = ITEMS.register("dyedream_glass",
            () -> new BlockItem(ModBlocks.DYEDREAM_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_GLASSPANE = ITEMS.register("dyedream_glasspane",
            () -> new BlockItem(ModBlocks.DYEDREAM_GLASSPANE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CARVE_DYEDREAM_GLASS = ITEMS.register("carve_dyedream_glass",
            () -> new BlockItem(ModBlocks.CARVE_DYEDREAM_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CARVE_DYEDREAM_GLASSPANE = ITEMS.register("carve_dyedream_glasspane",
            () -> new BlockItem(ModBlocks.CARVE_DYEDREAM_GLASSPANE.get(), new Item.Properties()));
    public static final RegistryObject<Item> GOLD_CARVE_DYEDREAM_GLASS = ITEMS.register("gold_carve_dyedream_glass",
            () -> new BlockItem(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GOLD_CARVE_DYEDREAM_GLASSPANE = ITEMS.register("gold_carve_dyedream_glasspane",
            () -> new BlockItem(ModBlocks.GOLD_CARVE_DYEDREAM_GLASSPANE.get(), new Item.Properties()));

    public static final RegistryObject<Item> SMALL_DYEDREAM_BUD = ITEMS.register("small_dyedream_bud",
            () -> new BlockItem(ModBlocks.SMALL_DYEDREAM_BUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> MEDIUM_DYEDREAM_BUD = ITEMS.register("medium_dyedream_bud",
            () -> new BlockItem(ModBlocks.MEDIUM_DYEDREAM_BUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> LARGE_DYEDREAM_BUD = ITEMS.register("large_dyedream_bud",
            () -> new BlockItem(ModBlocks.LARGE_DYEDREAM_BUD.get(), new Item.Properties()));

    public static final RegistryObject<Item> DYEDREAM_BUD_BLOCK = ITEMS.register("dyedream_bud_block",
            () -> new BlockItem(ModBlocks.DYEDREAM_BUD_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_BUD_STAIRS = ITEMS.register("dyedream_bud_stairs",
            () -> new BlockItem(ModBlocks.DYEDREAM_BUD_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_BUD_SLAB = ITEMS.register("dyedream_bud_slab",
            () -> new BlockItem(ModBlocks.DYEDREAM_BUD_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_BUD_WALL = ITEMS.register("dyedream_bud_wall",
            () -> new BlockItem(ModBlocks.DYEDREAM_BUD_WALL.get(), new Item.Properties()));

    public static final RegistryObject<Item> DYEDREAM_ICE = ITEMS.register("dyedream_ice",
            () -> new BlockItem(ModBlocks.DYEDREAM_ICE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_PACKED_ICE = ITEMS.register("dyedream_packed_ice",
            () -> new BlockItem(ModBlocks.DYEDREAM_PACKED_ICE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_LARTERN = ITEMS.register("dyedream_lartern",
            () -> new BlockItem(ModBlocks.DYEDREAM_LARTERN.get(), new Item.Properties()));

    // ===== 染梦石英建材系列 =====

    public static final RegistryObject<Item> DYEDREAM_QUARTZ_BLOCK = ITEMS.register("dyedream_quartz_block",
            () -> new BlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SMOOTH_DYEDREAM_QUARTZ_BLOCK = ITEMS.register("smooth_dyedream_quartz_block",
            () -> new BlockItem(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> BRICKS_DYEDREAM_QUARTZ_BLOCK = ITEMS.register("bricks_dyedream_quartz_block",
            () -> new BlockItem(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> PILLAR_DYEDREAM_QUARTZ_BLOCK = ITEMS.register("pillar_dyedream_quartz_block",
            () -> new BlockItem(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_DYEDREAM_QUARTZ_BLOCK = ITEMS.register("chiseled_dyedream_quartz_block",
            () -> new BlockItem(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_QUARTZ_BLOCK_STAIRS = ITEMS.register("dyedream_quartz_block_stairs",
            () -> new BlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_QUARTZ_BLOCK_SLAB = ITEMS.register("dyedream_quartz_block_slab",
            () -> new BlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_QUARTZ_BLOCK_WALL = ITEMS.register("dyedream_quartz_block_wall",
            () -> new BlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get(), new Item.Properties()));

    // ===== 染梦木板建材系列 =====

    public static final RegistryObject<Item> DYEDREAM_PLANKS = ITEMS.register("dyedream_planks",
            () -> new BlockItem(ModBlocks.DYEDREAM_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_PLANKS_STAIRS = ITEMS.register("dyedream_planks_stairs",
            () -> new BlockItem(ModBlocks.DYEDREAM_PLANKS_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_PLANKS_SLAB = ITEMS.register("dyedream_planks_slab",
            () -> new BlockItem(ModBlocks.DYEDREAM_PLANKS_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_PLANKS_FENCE = ITEMS.register("dyedream_planks_fence",
            () -> new BlockItem(ModBlocks.DYEDREAM_PLANKS_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_PLANKS_FENCEGATE = ITEMS.register("dyedream_planks_fencegate",
            () -> new BlockItem(ModBlocks.DYEDREAM_PLANKS_FENCEGATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_PLANKS_PANE = ITEMS.register("dyedream_planks_pane",
            () -> new BlockItem(ModBlocks.DYEDREAM_PLANKS_PANE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_PLANKS_DOOR = ITEMS.register("dyedream_planks_door",
            () -> new BlockItem(ModBlocks.DYEDREAM_PLANKS_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_PLANKS_TRAPDOOR = ITEMS.register("dyedream_planks_trapdoor",
            () -> new BlockItem(ModBlocks.DYEDREAM_PLANKS_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_PLANKS_PRESSURE_PLATE = ITEMS.register("dyedream_planks_pressure_plate",
            () -> new BlockItem(ModBlocks.DYEDREAM_PLANKS_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_PLANKS_BUTTON = ITEMS.register("dyedream_planks_button",
            () -> new BlockItem(ModBlocks.DYEDREAM_PLANKS_BUTTON.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
