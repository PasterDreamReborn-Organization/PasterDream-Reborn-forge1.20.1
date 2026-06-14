package com.pasterdream.pasterdreammod.datagen;

import com.pasterdream.pasterdreammod.world.level.block.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
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
        add(ModBlocks.DYEDREAM_GRASS_BLOCK.get(),
                block -> createSilkTouchDispatchTable(block,
                        LootItem.lootTableItem(ModBlocks.DYEDREAM_DIRT.get())));
        dropSelf(ModBlocks.DYEDREAM_LOG.get());
        dropSelf(ModBlocks.DYEDREAM_WOOD.get());
        add(ModBlocks.DYEDREAM_LEAVES.get(),
                block -> createLeavesDrops(block, ModBlocks.DYEDREAM_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get(),
                block -> createLeavesDrops(block, ModBlocks.DYEDREAM_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        dropSelf(ModBlocks.DYEDREAM_SAPLING.get());

        dropSelf(ModBlocks.DYEDREAM_PLANKS.get());
        dropSelf(ModBlocks.DYEDREAM_PLANKS_STAIRS.get());
        add(ModBlocks.DYEDREAM_PLANKS_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.DYEDREAM_PLANKS_SLAB.get()));
        dropSelf(ModBlocks.DYEDREAM_PLANKS_FENCE.get());
        dropSelf(ModBlocks.DYEDREAM_PLANKS_FENCEGATE.get());
        dropSelf(ModBlocks.DYEDREAM_PLANKS_PANE.get());
        add(ModBlocks.DYEDREAM_PLANKS_DOOR.get(), createDoorTable(ModBlocks.DYEDREAM_PLANKS_DOOR.get()));
        dropSelf(ModBlocks.DYEDREAM_PLANKS_TRAPDOOR.get());
        dropSelf(ModBlocks.DYEDREAM_PLANKS_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.DYEDREAM_PLANKS_BUTTON.get());
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
