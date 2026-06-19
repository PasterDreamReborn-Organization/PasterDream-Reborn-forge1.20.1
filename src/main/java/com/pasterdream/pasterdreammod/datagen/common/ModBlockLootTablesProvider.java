package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.datagen.util.RecipeHelpers;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
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
        dropSelf(ModBlocks.DYEDREAM_LOG.get());
        dropSelf(ModBlocks.DYEDREAM_WOOD.get());
        add(ModBlocks.DYEDREAM_LEAVES.get(), block -> RecipeHelpers.createLeavesDrops(block, ModBlocks.DYEDREAM_SAPLING.get(), ModItems.DYEDREAM_FRUIT.get()));
        add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get(), block -> RecipeHelpers.createLeavesDrops(block, ModBlocks.DYEDREAM_SAPLING.get(), ModItems.DYEDREAM_FRUIT.get()));
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

        dropSelf(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get());
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), block -> createSlabItemTable(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get()));
        dropSelf(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get());

        dropSelf(ModBlocks.DYEDREAM_PLANKS.get());
        dropSelf(ModBlocks.DYEDREAM_PLANKS_STAIRS.get());
        add(ModBlocks.DYEDREAM_PLANKS_SLAB.get(), block -> createSlabItemTable(ModBlocks.DYEDREAM_PLANKS_SLAB.get()));
        dropSelf(ModBlocks.DYEDREAM_PLANKS_FENCE.get());
        dropSelf(ModBlocks.DYEDREAM_PLANKS_FENCEGATE.get());
        dropSelf(ModBlocks.DYEDREAM_PLANKS_PANE.get());
        add(ModBlocks.DYEDREAM_PLANKS_DOOR.get(), createDoorTable(ModBlocks.DYEDREAM_PLANKS_DOOR.get()));
        dropSelf(ModBlocks.DYEDREAM_PLANKS_TRAPDOOR.get());
        dropSelf(ModBlocks.DYEDREAM_PLANKS_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.DYEDREAM_PLANKS_BUTTON.get());

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

        dropSelf(ModBlocks.BIG_BUBBLE.get());
        dropSelf(ModBlocks.DYEDREAM_LARTERN.get());

        dropSelf(ModBlocks.DYEDREAM_ALLOY_BLOCK.get());

        dropSelf(ModBlocks.DYEDREAM_BUD_BLOCK.get());
        dropSelf(ModBlocks.DYEDREAM_BUD_STAIRS.get());
        add(ModBlocks.DYEDREAM_BUD_SLAB.get(), block -> createSlabItemTable(ModBlocks.DYEDREAM_BUD_SLAB.get()));
        dropSelf(ModBlocks.DYEDREAM_BUD_WALL.get());


        dropSelf(ModBlocks.CLAYPAN.get());
        dropSelf(ModBlocks.DREAM_CAULDRON.get());

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

}
