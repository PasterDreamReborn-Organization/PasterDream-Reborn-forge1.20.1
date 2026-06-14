package com.pasterdream.pasterdreammod.datagen.lang;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.ModItems;
import com.pasterdream.pasterdreammod.world.level.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModZhCnLangProvider extends LanguageProvider {
    public ModZhCnLangProvider(PackOutput output) {
        super(output, PasterDreamMod.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(ModItems.DYEDREAM_QUARTZ.get(), "染梦石英");
        add(ModItems.DYEDREAM_DUST.get(), "染梦粉尘");
        add(ModItems.DYEDREAM_DUST_PIECE.get(), "小撮染梦粉尘");
        add(ModItems.AMBER_CANDY.get(), "琥珀糖");
        add(ModItems.PINK_SLIMEBALL.get(), "粉色粘液球");

        add(ModBlocks.DYEDREAM_DIRT.get(), "染梦泥土");
        add(ModBlocks.DYEDREAM_GRASS_BLOCK.get(), "染梦草方块");
        add(ModBlocks.DYEDREAM_LOG.get(), "染梦原木");
        add(ModBlocks.DYEDREAM_WOOD.get(), "染梦木头");
        add(ModBlocks.DYEDREAM_LEAVES.get(), "染梦树叶");
        add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get(), "染梦世界树树叶");
        add(ModBlocks.DYEDREAM_SAPLING.get(), "染梦树苗");
        add(ModBlocks.DYEDREAM_QUARTZ_ORE.get(), "染梦石英矿石");
        add(ModBlocks.DYEDREAM_DUST_ORE.get(), "染梦粉尘矿石");
        add(ModBlocks.AMBER_CANDY_ORE.get(), "琥珀糖矿石");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get(), "染梦石英块");
        add(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get(), "平滑染梦石英块");
        add(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get(), "染梦石英砖");
        add(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get(), "染梦石英柱");
        add(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get(), "錾制染梦石英块");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get(), "染梦石英楼梯");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), "染梦石英台阶");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get(), "染梦石英墙");
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
        add(ModBlocks.PINK_SLIME_BLOCK.get(), "粉色粘液块");
        add(ModBlocks.PINK_MUSHROOM_BLOCK.get(), "粉顶菌菌盖");
        add(ModBlocks.PINK_MUSHROOM_STEM.get(), "粉顶菌菌柄");
        add(ModBlocks.PINK_MUSHROOM_PORES.get(), "粉顶菌菌孔块");
        add(ModBlocks.PINK_SHROOMLIGHT.get(), "粉顶菌菌光体");
        add(ModBlocks.PINK_MUSHROOM.get(), "粉顶菇");
        add(ModBlocks.TALL_PINK_MUSHROOM.get(), "高粉顶菇");
        add(ModBlocks.DYEDREAM_SAND.get(), "染梦沙");
        add(ModBlocks.DYEDREAM_GLASS.get(), "染梦玻璃");
        add(ModBlocks.DYEDREAM_GLASSPANE.get(), "染梦玻璃板");
        add(ModBlocks.CARVE_DYEDREAM_GLASS.get(), "雕花染梦玻璃");
        add(ModBlocks.CARVE_DYEDREAM_GLASSPANE.get(), "雕花染梦玻璃板");
        add(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get(), "镶金雕花染梦玻璃");
        add(ModBlocks.GOLD_CARVE_DYEDREAM_GLASSPANE.get(), "镶金雕花染梦玻璃板");
        add("itemGroup.pasterdream.pasterdream_tab", "Paster Dream");
        add("itemGroup.pasterdream.pasterdream_materials_tab", "Paster Dream 材料");
    }
}
