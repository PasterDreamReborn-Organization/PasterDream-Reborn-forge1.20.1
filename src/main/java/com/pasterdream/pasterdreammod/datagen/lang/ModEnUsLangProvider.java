package com.pasterdream.pasterdreammod.datagen.lang;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.level.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnUsLangProvider extends LanguageProvider {
    public ModEnUsLangProvider(PackOutput output) {
        super(output, PasterDreamMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModBlocks.DYEDREAM_DIRT.get(), "Dye Dream Dirt");
        add(ModBlocks.DYEDREAM_GRASS_BLOCK.get(), "Dye Dream Grass Block");
        add(ModBlocks.DYEDREAM_LOG.get(), "Dye Dream Log");
        add(ModBlocks.DYEDREAM_LEAVES.get(), "Dye Dream Leaves");
        add(ModBlocks.DYEDREAM_SAPLING.get(), "Dye Dream Sapling");
        add("itemGroup.pasterdream.pasterdream_tab", "Paster Dream");
    }
}
