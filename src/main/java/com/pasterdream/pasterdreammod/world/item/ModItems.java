package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.level.block.ModBlocks;
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
