package com.pasterdream.pasterdreammod.datagen.lang;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.level.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModZhCnLangProvider extends LanguageProvider {
    public ModZhCnLangProvider(PackOutput output) {
        super(output, PasterDreamMod.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(ModBlocks.DYEDREAM_DIRT.get(), "染梦泥土");
        add(ModBlocks.DYEDREAM_GRASS_BLOCK.get(), "染梦草方块");
        add(ModBlocks.DYEDREAM_LOG.get(), "染梦原木");
        add(ModBlocks.DYEDREAM_WOOD.get(), "染梦木头");
        add(ModBlocks.DYEDREAM_LEAVES.get(), "染梦树叶");
        add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get(), "染梦世界树树叶");
        add(ModBlocks.DYEDREAM_SAPLING.get(), "染梦树苗");
        add(ModBlocks.DYEDREAM_PLANKS.get(), "染梦木板");
        add(ModBlocks.DYEDREAM_PLANKS_STAIRS.get(), "染梦木板楼梯");
        add(ModBlocks.DYEDREAM_PLANKS_SLAB.get(), "染梦木板台阶");
        add(ModBlocks.DYEDREAM_PLANKS_FENCE.get(), "染梦木板栅栏");
        add(ModBlocks.DYEDREAM_PLANKS_FENCEGATE.get(), "染梦木板栅栏门");
        add(ModBlocks.DYEDREAM_PLANKS_PANE.get(), "染梦木窗格");
        add(ModBlocks.DYEDREAM_PLANKS_DOOR.get(), "染梦木门");
        add(ModBlocks.DYEDREAM_PLANKS_TRAPDOOR.get(), "染梦木板活板门");
        add(ModBlocks.DYEDREAM_PLANKS_PRESSURE_PLATE.get(), "染梦木板压力板");
        add(ModBlocks.DYEDREAM_PLANKS_BUTTON.get(), "染梦木板按钮");
        add("itemGroup.pasterdream.pasterdream_tab", "Paster Dream");
    }
}
