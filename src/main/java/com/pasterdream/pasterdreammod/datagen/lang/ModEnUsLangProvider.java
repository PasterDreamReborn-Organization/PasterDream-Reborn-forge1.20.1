package com.pasterdream.pasterdreammod.datagen.lang;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.ModItems;
import com.pasterdream.pasterdreammod.world.level.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnUsLangProvider extends LanguageProvider {
    public ModEnUsLangProvider(PackOutput output) {
        super(output, PasterDreamMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModItems.DYEDREAM_QUARTZ.get(), "Dye Dream Quartz");
        add(ModItems.DYEDREAM_DUST.get(), "Dye Dream Dust");
        add(ModItems.DYEDREAM_DUST_PIECE.get(), "Dye Dream Dust Piece");

        add(ModBlocks.DYEDREAM_DIRT.get(), "Dye Dream Dirt");
        add(ModBlocks.DYEDREAM_GRASS_BLOCK.get(), "Dye Dream Grass Block");
        add(ModBlocks.DYEDREAM_LOG.get(), "Dye Dream Log");
        add(ModBlocks.DYEDREAM_WOOD.get(), "Dye Dream Wood");
        add(ModBlocks.DYEDREAM_LEAVES.get(), "Dye Dream Leaves");
        add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get(), "Dye Dream Worldtree Leaves");
        add(ModBlocks.DYEDREAM_SAPLING.get(), "Dye Dream Sapling");
        add(ModBlocks.DYEDREAM_QUARTZ_ORE.get(), "Dye Dream Quartz Ore");
        add(ModBlocks.DYEDREAM_DUST_ORE.get(), "Dye Dream Dust Ore");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get(), "Dye Dream Quartz Block");
        add(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get(), "Smooth Dye Dream Quartz Block");
        add(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get(), "Dye Dream Quartz Bricks");
        add(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get(), "Dye Dream Quartz Pillar");
        add(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get(), "Chiseled Dye Dream Quartz Block");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get(), "Dye Dream Quartz Stairs");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), "Dye Dream Quartz Slab");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get(), "Dye Dream Quartz Wall");
        add(ModBlocks.DYEDREAM_PLANKS.get(), "Dye Dream Planks");
        add(ModBlocks.DYEDREAM_PLANKS_STAIRS.get(), "Dye Dream Planks Stairs");
        add(ModBlocks.DYEDREAM_PLANKS_SLAB.get(), "Dye Dream Planks Slab");
        add(ModBlocks.DYEDREAM_PLANKS_FENCE.get(), "Dye Dream Planks Fence");
        add(ModBlocks.DYEDREAM_PLANKS_FENCEGATE.get(), "Dye Dream Planks Fence Gate");
        add(ModBlocks.DYEDREAM_PLANKS_PANE.get(), "Dye Dream Planks Pane");
        add(ModBlocks.DYEDREAM_PLANKS_DOOR.get(), "Dye Dream Planks Door");
        add(ModBlocks.DYEDREAM_PLANKS_TRAPDOOR.get(), "Dye Dream Planks Trapdoor");
        add(ModBlocks.DYEDREAM_PLANKS_PRESSURE_PLATE.get(), "Dye Dream Planks Pressure Plate");
        add(ModBlocks.DYEDREAM_PLANKS_BUTTON.get(), "Dye Dream Planks Button");
        add("itemGroup.pasterdream.pasterdream_tab", "Paster Dream");
        add("itemGroup.pasterdream.pasterdream_materials_tab", "Paster Dream Materials");
    }
}
