package com.pasterdream.pasterdreammod.datagen.lang;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModBlocks;
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
        add(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get(),"染梦合金锭粗胚");
        add(ModItems.DYEDREAM_ALLOY_INGOT.get(), "染梦合金锭");
        add(ModItems.TITANIUM_INGOT.get(), "钛金锭");
        add(ModItems.TITANIUM_NUGGET.get(), "钛金粒");
        add(ModItems.RAW_TITANIUM.get(), "粗钛");
        add(ModItems.DYEDREAM_ALLOY_NUGGET.get(), "染梦合金粒");
        add(ModItems.MORTAR.get(), "研钵");
        add(ModItems.DYEDREAM_DYE.get(), "染梦染料");
        add(ModItems.DYEDREAM_BUD_NUGGET.get(), "染梦晶芽粒");
        add(ModItems.MELT_DREAM_LIQUID_BUCKET.get(), "融梦涌泉桶");
        add(ModItems.SHADOW_LIQUID_BUCKET.get(), "熔融阴影桶");

        add(ModItems.MOLTEN_GOLD_INGOT.get(), "炙焰金锭");
        add(ModItems.MOLTEN_GOLD_NUGGET.get(), "炙焰金粒");
        add(ModItems.RAW_MOLTEN_GOLD.get(), "粗炙焰金");
        add(ModItems.GLASS_JAR.get(), "玻璃罐");
        add(ModItems.GLASS_JAR_OF_WATER.get(), "水罐");
        add(ModItems.GLASS_JAR_OF_MILK.get(), "牛奶罐");

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
        add(ModBlocks.SMALL_DYEDREAM_BUD.get(), "小型染梦晶芽");
        add(ModBlocks.MEDIUM_DYEDREAM_BUD.get(), "中型染梦晶芽");
        add(ModBlocks.LARGE_DYEDREAM_BUD.get(), "大型染梦晶芽");
        add(ModBlocks.DYEDREAM_BUD_BLOCK.get(), "染梦水晶砖");
        add(ModBlocks.DYEDREAM_BUD_STAIRS.get(), "染梦水晶楼梯");
        add(ModBlocks.DYEDREAM_BUD_SLAB.get(), "染梦水晶台阶");
        add(ModBlocks.DYEDREAM_BUD_WALL.get(), "染梦水晶墙");
        add(ModBlocks.DYEDREAM_ICE.get(), "染梦冰");
        add(ModBlocks.DYEDREAM_PACKED_ICE.get(), "染梦浮冰");
        add(ModBlocks.DYEDREAM_LARTERN.get(), "染梦水晶灯");

        add("fluid.pasterdream.melt_dream_liquid","融梦涌泉");
        add("fluid.pasterdream.shadow_liquid","熔融阴影");

        add("fluid.pasterdream.apple_juice","苹果汁（流体）");
        add("fluid.pasterdream.dream_juice","梦境果汁（流体）");
        add("fluid.pasterdream.dyedream_flower_tea","染梦花茶（流体）");
        add("fluid.pasterdream.dyedream_juice","染梦果汁（流体）");
        add("fluid.pasterdream.dyedream_perfume","染梦香水（流体）");
        add("fluid.pasterdream.goldenrod_tea","秋麒麟茶（流体）");
        add("fluid.pasterdream.guiding_drug","药引瓶（流体）");
        add("fluid.pasterdream.honey_juice","蜂蜜汁（流体）");
        add("fluid.pasterdream.rage_elixir","狂暴战意灵药（流体）");
        add("fluid.pasterdream.uncooked_dyedream_flower_tea","未烹饪的染梦花茶（流体）");
        add("fluid.pasterdream.watermelon_juice","西瓜汁（流体）");
        add("fluid.pasterdream.wind_plant_extract","风植萃取液（流体）");
        add("fluid.pasterdream.yeast","酵母（流体）");

        add("itemGroup.pasterdream.pasterdream_tab", "帕斯特之梦 | 方块");
        add("itemGroup.pasterdream.pasterdream_materials_tab", "帕斯特之梦 | 物品");
    }
}
