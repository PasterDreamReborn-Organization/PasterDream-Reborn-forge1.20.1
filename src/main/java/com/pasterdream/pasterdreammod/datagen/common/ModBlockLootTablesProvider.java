package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.datagen.util.LootHelpers;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.util.BuildingBlockFamily;
import com.pasterdream.pasterdreammod.world.block.cropblock.PasterDreamCropBlock;
import com.pasterdream.pasterdreammod.world.conditions.RealPlayerCondition;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTablesProvider extends BlockLootSubProvider {
    public ModBlockLootTablesProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.DYEDREAM_DIRT.get());
        add(ModBlocks.DYEDREAM_GRASS_BLOCK.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ModBlocks.DYEDREAM_DIRT.get())));
        dropOther(ModBlocks.DYEDREAM_FARMLAND.get(), ModItems.DYEDREAM_DIRT.get());
        dropSelf(ModBlocks.DYEDREAM_LOG.get());
        dropSelf(ModBlocks.DYEDREAM_WOOD.get());
        add(ModBlocks.DYEDREAM_LEAVES.get(), block -> LootHelpers.createLeavesDrops(block, ModBlocks.DYEDREAM_SAPLING.get(), ModItems.DYEDREAM_FRUIT.get()));
        add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get(), block -> LootHelpers.createLeavesDrops(block, ModBlocks.DYEDREAM_SAPLING.get(), ModItems.DYEDREAM_FRUIT.get()));
        dropSelf(ModBlocks.DYEDREAM_SAPLING.get());

        add(ModBlocks.DYEDREAM_QUARTZ_ORE.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.DYEDREAM_QUARTZ.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));

        add(ModBlocks.DYEDREAM_DUST_ORE.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.DYEDREAM_DUST_PIECE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));

        add(ModBlocks.AMBER_CANDY_ORE.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.AMBER_CANDY.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));

        add(ModBlocks.TITANIUM_ORE.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.RAW_TITANIUM.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.RAW_TITANIUM.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        add(ModBlocks.MOLTEN_GOLD_ORE.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.RAW_MOLTEN_GOLD.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        add(ModBlocks.SOUL_ORE.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.SOUL_DUST.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));

        dropSelf(ModBlocks.RAW_TITANIUM_BLOCK.get());
        dropSelf(ModBlocks.SALT_BLOCK.get());
        dropSelf(ModBlocks.TITANIUM_BLOCK.get());
        dropSelf(ModBlocks.MOLTEN_GOLD_BLOCK.get());
        dropSelf(ModBlocks.CHARGED_AMETHYST_BLOCK.get());
        dropSelf(ModBlocks.BLACK_METAL_BLOCK.get());

        add(ModBlocks.RUST_BLACK_METAL_BLOCK.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ModItems.RUST_BLACK_METAL_GRAIN.get())));
        add(ModBlocks.RUST_BLACK_METAL_WALL.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ModItems.RUST_BLACK_METAL_GRAIN.get())));
        add(ModBlocks.RUST_BLACK_METAL_BARS.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ModItems.RUST_BLACK_METAL_GRAIN.get())));

        dropSelf(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get());
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), block -> createSlabItemTable(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get()));
        dropSelf(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get());

        dropSelf(ModBlocks.DYEDREAM_PLANKS.get());
        dropSelf(ModBlocks.DYEDREAM_STAIRS.get());
        add(ModBlocks.DYEDREAM_SLAB.get(), block -> createSlabItemTable(ModBlocks.DYEDREAM_SLAB.get()));
        dropSelf(ModBlocks.DYEDREAM_FENCE.get());
        dropSelf(ModBlocks.DYEDREAM_FENCE_GATE.get());
        dropSelf(ModBlocks.DYEDREAM_PANE.get());
        add(ModBlocks.DYEDREAM_DOOR.get(), createDoorTable(ModBlocks.DYEDREAM_DOOR.get()));
        dropSelf(ModBlocks.DYEDREAM_TRAPDOOR.get());
        dropSelf(ModBlocks.DYEDREAM_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.DYEDREAM_BUTTON.get());

        dropSelf(ModBlocks.SHADOW_PLANKS.get());
        dropSelf(ModBlocks.SHADOW_STAIRS.get());
        add(ModBlocks.SHADOW_SLAB.get(), block -> createSlabItemTable(ModBlocks.SHADOW_SLAB.get()));
        dropSelf(ModBlocks.SHADOW_FENCE.get());
        dropSelf(ModBlocks.SHADOW_FENCE_GATE.get());
        dropSelf(ModBlocks.SHADOW_PANE.get());
        add(ModBlocks.SHADOW_DOOR.get(), createDoorTable(ModBlocks.SHADOW_DOOR.get()));
        dropSelf(ModBlocks.SHADOW_TRAPDOOR.get());
        dropSelf(ModBlocks.SHADOW_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.SHADOW_BUTTON.get());

        dropSelf(ModBlocks.PINK_SLIME_BLOCK.get());

        add(ModBlocks.PINK_MUSHROOM_BLOCK.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModBlocks.PINK_MUSHROOM.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        add(ModBlocks.PINK_MUSHROOM_STEM.get(),
                block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(net.minecraft.world.item.Items.AIR)));
        add(ModBlocks.PINK_MUSHROOM_PORES.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModBlocks.PINK_MUSHROOM.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        dropSelf(ModBlocks.PINK_SHROOMLIGHT.get());

        dropSelf(ModBlocks.PINK_MUSHROOM.get());
        add(ModBlocks.TALL_PINK_MUSHROOM.get(), block -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        dropSelf((ModBlocks.GOLDENROD.get()));
        dropSelf((ModBlocks.FERRARIA_CRISPA.get()));
        dropSelf((ModBlocks.EUSTOMA.get()));
        dropSelf((ModBlocks.MALVA_SINENSIS_CAVAN.get()));
        dropSelf(ModBlocks.LINHT_FLOWER.get());
        dropSelf(ModBlocks.DREAMING_LOTUS.get());
        dropSelf(ModBlocks.MISTY_DREAMING_LOTUS.get());
        add(ModBlocks.DREAMING_LOTUS.get(),
                block -> LootHelpers.createhighflowerDrops(ModBlocks.DREAMING_LOTUS.get()));
        add(ModBlocks.MISTY_DREAMING_LOTUS.get(),
                block -> LootHelpers.createhighflowerDrops(ModBlocks.MISTY_DREAMING_LOTUS.get()));
        dropSelf(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get());
        add(ModBlocks.BLAZE_FLOWER.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(Items.BLAZE_POWDER)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                        )
                )
        );
        dropSelf(ModBlocks.WHITE_ORCHID_FLOWER.get());
        dropSelf(ModBlocks.EDELWEISS.get());
        dropSelf(ModBlocks.NIPPY_EDELWEISS.get());
        dropSelf(ModBlocks.DYEDREAM_LILY_PAD.get());
        dropSelf(ModBlocks.DYEDREAM_LOTUS.get());

        add(ModBlocks.DYEDREAM_MOSS.get(),
                block -> LootHelpers.creategrassesDrops(ModBlocks.DYEDREAM_MOSS.get()));
        add(ModBlocks.STEM_GRASS.get(),
                block -> LootHelpers.creategrassesDrops(ModBlocks.STEM_GRASS.get()));
        add(ModBlocks.TALL_STEM_GRASS.get(),
                block -> LootHelpers.createhighgrassesDrops(ModBlocks.STEM_GRASS.get(),ModBlocks.TALL_STEM_GRASS.get()));
        add(ModBlocks.SINGULARITY_FERN.get(),
                block -> LootHelpers.creategrassesDrops(ModBlocks.SINGULARITY_FERN.get()));
        add(ModBlocks.CRIMSON_THORNS.get(),
                block -> LootHelpers.createhighgrassesDropsNeedScissor(ModBlocks.CRIMSON_THORNS.get()));
        add(ModBlocks.OATS.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.RYE_SEED.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                        )
                )
        );
        add(ModBlocks.RYE.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.RYE_SEED.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                        )
                )
        );
        add(ModBlocks.POLISHED_CALCITE_STALICRIPE.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(net.minecraft.world.item.Items.AIR)));
        add(ModBlocks.SMALL_POLISHED_CALCITE_STALICRIPE.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(net.minecraft.world.item.Items.AIR)));
        add(ModBlocks.DYEDREAM_SEAGRASS.get(),
                block -> LootHelpers.creategrassesDrops(ModBlocks.DYEDREAM_SEAGRASS.get()));
        add(ModBlocks.REED.get(),
                block -> LootHelpers.createShearsOrSilkTouchSelfElseItem(ModBlocks.REED.get(),ModItems.REED_ROD.get(),1.0F,3.0F));

        add(ModBlocks.DYEDREAM_VINE.get(),
                block -> LootHelpers.createShearsOrSilkTouchSelfElseItem(ModBlocks.DYEDREAM_VINE.get(),ModItems.DYEDREAM_FRUIT.get(),0.0F,1.0F));

        add(ModBlocks.JUNGLE_SPORANGIUM.get(),
                block -> LootHelpers.createShearsOrSilkTouchSelfElseItem(ModBlocks.JUNGLE_SPORANGIUM.get(),ModItems.JUNGLE_SPORE.get(),1.0F,1.0F));

        dropOther(ModBlocks.FOURLEAF_CLOVER.get(), ModItems.FOURLEAF_CLOVER_CURIO.get());

        // ===== 阴影植物系列 =====
        dropSelf(ModBlocks.SHADOW_SHORT_ROOTS.get());
        dropSelf(ModBlocks.SHADOW_ROOTS.get());
        add(ModBlocks.SHADOW_STEM_FERN.get(),
                block -> LootHelpers.createhighflowerDrops(ModBlocks.SHADOW_STEM_FERN.get()));
        add(ModBlocks.SHADOW_SPROUTS.get(),
                block -> LootHelpers.creategrassesDrops(ModBlocks.SHADOW_SPROUTS.get()));
        dropSelf(ModBlocks.SHADOW_FERN.get());
        dropSelf(ModBlocks.SHADOW_FUNGUS.get());

        dropSelf(ModBlocks.LIGHT_BALL.get());

        dropSelf(ModBlocks.DYEDREAM_SAND.get());
        add(ModBlocks.DYEDREAM_GLASS.get(),
                block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(net.minecraft.world.item.Items.AIR)));
        add(ModBlocks.DYEDREAM_GLASS_PANE.get(),
                block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(net.minecraft.world.item.Items.AIR)));
        add(ModBlocks.CARVE_DYEDREAM_GLASS.get(),
                block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(net.minecraft.world.item.Items.AIR)));
        add(ModBlocks.CARVE_DYEDREAM_GLASS_PANE.get(),
                block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(net.minecraft.world.item.Items.AIR)));
        add(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get(),
                block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(net.minecraft.world.item.Items.AIR)));
        add(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS_PANE.get(),
                block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(net.minecraft.world.item.Items.AIR)));

        add(ModBlocks.DYEDREAM_BUDDING_BLOCK.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModBlocks.DYEDREAM_BUDDING_BLOCK.get()))));
        add(ModBlocks.SMALL_DYEDREAM_BUD.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.DYEDREAM_BUD_NUGGET.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))))));
        add(ModBlocks.MEDIUM_DYEDREAM_BUD.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.DYEDREAM_BUD_NUGGET.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))));
        add(ModBlocks.LARGE_DYEDREAM_BUD.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.DYEDREAM_BUD_NUGGET.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))));

        add(ModBlocks.DYEDREAM_ICE.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(net.minecraft.world.item.Items.AIR)));
        add(ModBlocks.DYEDREAM_PACKED_ICE.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(net.minecraft.world.item.Items.AIR)));

        dropSelf(ModBlocks.CLOUD.get());
        dropSelf(ModBlocks.DARK_CLOUD.get());
        dropSelf(ModBlocks.SHADOW.get());
        dropSelf(ModBlocks.THICK_SHADOW.get());
        dropSelf(ModBlocks.SHADOW_STONE.get());
        dropSelf(ModBlocks.BIG_BUBBLE.get());
        dropSelf(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get());
        dropSelf(ModBlocks.DYEDREAM_LANTERN.get());

        dropSelf(ModBlocks.ICE_STONE.get());
        add(ModBlocks.ICE_BUD.get(), block -> createSilkTouchDispatchTable(block,
                applyExplosionDecay(block, LootItem.lootTableItem(ModBlocks.ICE_BUD.get()))));

        dropSelf(ModBlocks.DYEDREAM_ALLOY_BLOCK.get());

        buildingFamily(new BuildingBlockFamily(ModBlocks.POLISHED_CALCITE, ModBlocks.POLISHED_CALCITE_STAIRS, ModBlocks.POLISHED_CALCITE_SLAB, ModBlocks.POLISHED_CALCITE_WALL));
        buildingFamily(new BuildingBlockFamily(ModBlocks.CALCITE_TILES, ModBlocks.CALCITE_TILES_STAIRS, ModBlocks.CALCITE_TILES_SLAB, ModBlocks.CALCITE_TILES_WALL));
        buildingFamily(new BuildingBlockFamily(ModBlocks.DYEDREAM_BUD_BLOCK, ModBlocks.DYEDREAM_BUD_STAIRS, ModBlocks.DYEDREAM_BUD_SLAB, ModBlocks.DYEDREAM_BUD_WALL));
        buildingFamily(new BuildingBlockFamily(ModBlocks.SHADOW_STONE_BRICK, ModBlocks.SHADOW_STONE_BRICK_STAIRS, ModBlocks.SHADOW_STONE_BRICK_SLAB, ModBlocks.SHADOW_STONE_BRICK_WALL));
        buildingFamily(new BuildingBlockFamily(ModBlocks.NARROW_SHADOW_STONE_BRICK, ModBlocks.NARROW_SHADOW_STONE_BRICK_STAIRS, ModBlocks.NARROW_SHADOW_STONE_BRICK_SLAB, ModBlocks.NARROW_SHADOW_STONE_BRICK_WALL));
        buildingFamily(new BuildingBlockFamily(ModBlocks.SHADOW_STONE_TILES, ModBlocks.SHADOW_STONE_TILES_STAIRS, ModBlocks.SHADOW_STONE_TILES_SLAB, ModBlocks.SHADOW_STONE_TILES_WALL));
        dropSelf(ModBlocks.CRACKED_SHADOW_STONE_BRICK.get());
        dropSelf(ModBlocks.CHISELED_SHADOW_STONE_BRICK.get());
        // ===== 暗影地牢方块系列 =====
        dropSelf(ModBlocks.SHADOW_DUNGEON_STONE.get());
        dropSelf(ModBlocks.CHISELED_SHADOW_DUNGEON_BRICKS.get());
        dropSelf(ModBlocks.SHADOW_DUNGEON_BRICKS.get());
        dropSelf(ModBlocks.CRACKED_SHADOW_DUNGEON_BRICKS.get());
        dropSelf(ModBlocks.FRACTURED_SHADOW_DUNGEON_BRICKS.get());
        dropSelf(ModBlocks.SHADOW_DUNGEON_BRICK_STAIRS.get());
        add(ModBlocks.SHADOW_DUNGEON_BRICK_SLAB.get(), block -> createSlabItemTable(ModBlocks.SHADOW_DUNGEON_BRICK_SLAB.get()));
        add(ModBlocks.SHATTERED_SHADOW_DUNGEON_BRICKS.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(net.minecraft.world.item.Items.AIR)));
        // ===== 灯影竞技场 / 裂隙方块 =====
        dropSelf(ModBlocks.SHADOW_ARENA_BLOCK.get());
        dropSelf(ModBlocks.SHADOW_FISSURE_0.get());
        dropSelf(ModBlocks.SHADOW_FISSURE_1.get());
        dropSelf(ModBlocks.SHADOW_FISSURE_2.get());
        dropSelf(ModBlocks.SHADOW_FISSURE_3.get());
        dropSelf(ModBlocks.SHADOW_FISSURE_4.get());
        dropSelf(ModBlocks.SHADOW_FISSURE_5.get());
        add(ModBlocks.SHADOW_NYLIUM.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ModBlocks.SHADOW_STONE.get())));
        dropSelf(ModBlocks.SHADOW_LIGHT.get());
        dropSelf(ModBlocks.SHADOW_SHROOMLIGHT.get());
        dropSelf(ModBlocks.SHADOW_CANDLE.get());
        dropSelf(ModBlocks.SHADOW_WART_BLOCK.get());
        dropSelf(ModBlocks.SHADOW_STEM.get());
        dropSelf(ModBlocks.SHADOW_HYPHAE.get());
        dropSelf(ModBlocks.STRIPPED_SHADOW_STEM.get());
        dropSelf(ModBlocks.STRIPPED_SHADOW_HYPHAE.get());

        // ===== 阴影书架系列 =====
        add(ModBlocks.SHADOW_BOOKSHELF.get(), block -> LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModBlocks.SHADOW_BOOKSHELF.get()).when(HAS_SILK_TOUCH)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                        .add(LootItem.lootTableItem(ModItems.BROKEN_NOTE.get()).setWeight(1))
                        .when(HAS_SILK_TOUCH.invert())));
        add(ModBlocks.WORN_SHADOW_BOOKSHELF.get(), block -> LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModBlocks.WORN_SHADOW_BOOKSHELF.get()).when(HAS_SILK_TOUCH)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(10))
                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(20))
                        .add(LootItem.lootTableItem(ModItems.BROKEN_NOTE.get()).setWeight(1))
                        .when(HAS_SILK_TOUCH.invert())));
        add(ModBlocks.COBWEB_SHADOW_BOOKSHELF.get(), block -> LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModBlocks.COBWEB_SHADOW_BOOKSHELF.get()).when(HAS_SILK_TOUCH)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(5))
                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(10))
                        .add(LootItem.lootTableItem(Items.STRING).setWeight(10))
                        .add(LootItem.lootTableItem(ModItems.BROKEN_NOTE.get()).setWeight(2))
                        .when(HAS_SILK_TOUCH.invert())));
        add(ModBlocks.KEY_SHADOW_BOOKSHELF.get(), block -> LootTable.lootTable());

        generateCropLoot(ModBlocks.DYEDREAM_COROLLA_CROP.get(), ModItems.DYEDREAM_COROLLA.get(), 1, ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get(), ModItems.DYEDREAM_COROLLA_CROP_AGE_0.get());
        generateCropLoot(ModBlocks.WHITE_COROLLA_CROP.get(), ModItems.WHITE_COROLLA.get(), 1, ModItems.WHITE_COROLLA_CROP_AGE_1.get(), ModItems.WHITE_COROLLA_CROP_AGE_0.get());
        generateCropLoot(ModBlocks.LIGHT_BALL_CROP.get(), ModItems.LIGHT_BALL.get(), 1, ModItems.LIGHT_BALL_CROP_AGE_1.get(), ModItems.LIGHT_BALL_CROP_AGE_0.get());
        generateCropLoot(ModBlocks.CLOUD_CROP.get(), ModItems.CLOUD.get(), 5, ModItems.CLOUD_CROP_AGE_1.get(), ModItems.CLOUD_CROP_AGE_0.get());
        generateCropLoot(ModBlocks.COTTON_CROP.get(), ModItems.COTTON.get(), 1, ModItems.COTTON_CROP_AGE_1.get(), ModItems.COTTON_CROP_AGE_0.get());

        dropSelf(ModBlocks.QYM_DOLL.get());
        dropSelf(ModBlocks.UUZ_DOLL.get());
        dropSelf(ModBlocks.DYEDREAM_CRACK.get());
        dropSelf(ModBlocks.CLAYPAN.get());
        // 小石子
        dropSelf(ModBlocks.PEBBLE.get());
        // 陶罐：精准采集掉落自身，否则从战利品池随机抽取
        add(ModBlocks.CLAY_POT.get(), block -> LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModBlocks.CLAY_POT.get()).when(HAS_SILK_TOUCH)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.SOUL_DUST.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.TITANIUM_NUGGET.get()).setWeight(1))
                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(1))
                        .add(LootItem.lootTableItem(Items.QUARTZ).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.SHADOW_CANDLE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(Items.INK_SAC).setWeight(1))
                        .add(LootItem.lootTableItem(Items.COBWEB).setWeight(1))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.SHADOW_FUNGUS.get()).setWeight(1))
                        .add(LootItem.lootTableItem(Items.CHAIN).setWeight(1))
                        .add(LootItem.lootTableItem(Items.COAL).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.RUST_BLACK_METAL_GRAIN.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.BROKEN_NOTE.get()).setWeight(2))
                        .when(HAS_SILK_TOUCH.invert())));
        dropSelf(ModBlocks.DREAM_CAULDRON.get());
        dropSelf(ModBlocks.DYEDREAM_DESK.get());
        dropSelf(ModBlocks.SHADOW_DESK.get());
        dropSelf(ModBlocks.PICNIC_BASKET.get());
        dropSelf(ModBlocks.SHADOW_CHEST.get());
        dropSelf(ModBlocks.WIND_MOOR_CRATE.get());
        dropSelf(ModBlocks.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get());
        dropSelf(ModBlocks.RESEARCH_TABLE.get());
        dropSelf(ModBlocks.MELT_DREAM_CRYSTAL_CHEST.get());
        dropSelf(ModBlocks.OPENED_MELT_DREAM_CRYSTAL_CHEST.get());
        dropSelf(ModBlocks.LOST_SWORD_TOMB.get());
        dropSelf(ModBlocks.LIFE_CRYSTAL.get());
        dropSelf(ModBlocks.GOLDEN_FOX_SCULPTURE.get());
        dropSelf(ModBlocks.FOX_SCULPTURE.get());
        dropSelf(ModBlocks.DESERT_HERO_TOMB.get());
        dropSelf(ModBlocks.DREAM_ACCUMULATOR.get());
        dropNone(ModBlocks.DREAM_TRAIN_STRUCTURE.get());

        dropSelf(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_0.get());
        dropSelf(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_1.get());
        dropSelf(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2.get());
        dropSelf(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_3.get());

        // ===== 盆栽植物 =====
        for (var entry : ModBlocks.POTTED_PLANTS.entrySet()) {
            var plant = entry.getKey();
            var potted = entry.getValue();

            if (ModBlocks.POTTED_CROPS.contains(potted)) {
                // 作物盆栽：age=0 掉落幼苗，age=1 掉落成熟植株
                var items = ModBlocks.POTTED_CROP_ITEMS.get(potted);
                var matureCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(potted.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(com.pasterdream.pasterdreammod.world.block.PottedCropBlock.AGE, 1));
                add(potted.get(), LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.FLOWER_POT)))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(items.seedling().get()).when(matureCondition.invert())))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(items.mature().get()).when(matureCondition))));
            } else {
                add(potted.get(), block -> LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.FLOWER_POT)))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(plant.get()))));
            }
        }
    }

    private void buildingFamily(BuildingBlockFamily family) {
        dropSelf(family.base().get());
        dropSelf(family.stairs().get());
        add(family.slab().get(), block -> createSlabItemTable(family.slab().get()));
        dropSelf(family.wall().get());
    }

    @Override
    protected LootTable.Builder createOreDrop(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    private void generateCropLoot(Block cropBlock, Item productItem, int productCount, Item matureItem, Item immatureItem)
    {
        var matureCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(cropBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PasterDreamCropBlock.AGE, 1));
        var realPlayerCondition = RealPlayerCondition.builder();
        var immatureCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(cropBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PasterDreamCropBlock.AGE, 0));

        this.add(cropBlock, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .when(matureCondition)
                        .when(realPlayerCondition)
                        .add(LootItem.lootTableItem(matureItem)))
                .withPool(LootPool.lootPool()
                        .when(matureCondition)
                        .when(realPlayerCondition.invert())
                        .add(LootItem.lootTableItem(productItem)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(productCount)))))
                .withPool(LootPool.lootPool()
                        .when(immatureCondition)
                        .add(LootItem.lootTableItem(immatureItem))));
    }

    protected void dropNone(Block block)
    {
        this.add(block, LootTable.lootTable());
    }

}
