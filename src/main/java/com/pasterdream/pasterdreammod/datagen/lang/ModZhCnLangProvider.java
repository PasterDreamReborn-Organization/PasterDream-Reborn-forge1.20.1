package com.pasterdream.pasterdreammod.datagen.lang;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.PotionBottleRegistry;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModZhCnLangProvider extends LanguageProvider {
    public ModZhCnLangProvider(PackOutput output) {
        super(output, PasterDreamMod.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(ModBlocks.PEBBLE.get(), "小石子");
        add(ModBlocks.SMALL_STONE_SPIRIT_BLOCK.get(), "小石堆");
        add("tooltip.pasterdream.pebble.throw", "§7右键投掷");
        add("tooltip.pasterdream.pebble.place", "§7潜行+右键放置");
        add(ModItems.LIFE_CRYSTAL.get(), "生命水晶");
        add("message.pasterdream.life_crystal.already_used", "你已经吸收过生命水晶了！");
        add("message.pasterdream.life_crystal.already_absorbing", "你已经在吸收另一个生命水晶了！");
        add("message.pasterdream.life_crystal.absorbed", "生命水晶破碎并涌入你的体内 §a最大生命值+2");
        add("death.pasterdream.lamp_shadow_world", "%s融化在了影子的呢喃里");
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
        add(ModItems.PLIERS.get(), "园艺钳");
        add(ModItems.DYEDREAM_DYE.get(), "染梦染料");
        add(ModItems.DYEDREAM_BUD_NUGGET.get(), "染梦晶芽粒");
        add(ModItems.GLASS_JAR_OF_YEAST.get(), "酵母");
        add(ModItems.MOLTEN_GOLD_INGOT.get(), "炙焰金锭");
        add(ModItems.MOLTEN_GOLD_NUGGET.get(), "炙焰金粒");
        add(ModItems.RAW_MOLTEN_GOLD.get(), "粗炙焰金");
        add(ModItems.GLASS_JAR.get(), "玻璃罐");
        add(ModItems.GLASS_JAR_OF_WATER.get(), "水罐");
        add(ModItems.GLASS_JAR_OF_MILK.get(), "牛奶罐");
        add(ModItems.GLASS_JAR_OF_GUIDING_DRUG.get(), "药引瓶");
        add(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get(), "风植萃取液");
        add(ModItems.GLASS_JAR_OF_DREAM_JUICE.get(), "梦境果汁");
        add("tooltip.pasterdreammod.glass_jar_of_dream_juice", "§7§o甜美而梦幻的味道 让你交融与梦");
        add(ModItems.GLASS_JAR_OF_GOLDENROD_TEA.get(), "秋麒麟茶");
        add(ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME.get(), "染梦香水");
        add(ModItems.GLASS_JAR_OF_INK.get(), "墨水罐");
        add(ModItems.FEATHER_PEN.get(), "羽毛笔");
        add(ModItems.MELT_DREAM_LIQUID_BUCKET.get(), "融梦涌泉桶");
        add(ModItems.SHADOW_LIQUID_BUCKET.get(), "熔融阴影桶");
        add(ModItems.GLASS_CUP.get(), "玻璃杯");
        add(ModItems.GLASS_CUP_OF_APPLE_JUICE.get(), "苹果汁");
        add(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get(), "染梦花茶");
        add(ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get(), "染梦果汁");
        add(ModItems.GLASS_CUP_OF_HONEY_JUICE.get(), "蜂蜜汁");
        add(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get(), "未烹调的染梦花茶");
        add(ModItems.GLASS_CUP_OF_WATERMELON_JUICE.get(), "西瓜汁");
        add(ModItems.FLOUR.get(), "面粉");
        add(ModItems.DOUGH.get(), "面团");
        add(ModItems.RYE_SEED.get(), "野麦粒");
        add(ModItems.DOUGH_WITH_EGG.get(), "蛋液面团");
        add(ModItems.COARSE_SALT.get(), "粗盐");
        add(ModItems.SALT.get(), "盐");
        add(ModItems.PINK_EGG.get(), "粉红蛋");
        add(ModItems.CHOCOLATE.get(), "巧克力");
        add(ModItems.DYEDREAM_FRUIT.get(), "染梦果");
        add(ModItems.FIG.get(), "无花果");
        add(ModItems.CAKE_BASE.get(), "蛋糕胚");
        add(ModItems.CREAM_BUN_CAKE.get(), "奶油小蛋糕");
        add(ModItems.BERRY_BUN_CAKE.get(), "甜浆果小蛋糕");
        add(ModItems.TUBER_BUN_CAKE.get(), "块茎小蛋糕");
        add(ModItems.WATERMELON_BUN_CAKE.get(), "西瓜小蛋糕");
        add(ModItems.PUMPKIN_BUN_CAKE.get(), "南瓜小蛋糕");
        add(ModItems.GLOW_BERRY_BUN_CAKE.get(), "发光浆果小蛋糕");
        add(ModItems.DYEDREAM_FRUIT_BUN_CAKE.get(), "染梦果小蛋糕");
        add(ModItems.CHOCOLATE_MATCHA_CAKE.get(), "巧克力抹茶蛋糕");
        add(ModItems.MAGIC_STONE.get(), "魔法石");
        add(ModItems.RICE_CAKE.get(), "年糕");
        add("item.pasterdream.rice_cake.describe", "§7§o某个沙漠遗魂可能对这个感兴趣……");
        add(ModItems.DYEDREAM_POPSICLE.get(), "染梦冰棒");
        add(ModItems.FRIED_EGG.get(), "煎蛋");
        add(ModItems.BACON_AND_EGG.get(), "培根煎蛋");
        add(ModItems.ODD_BACON_AND_EGG.get(), "奇怪的培根煎蛋");
        add(ModItems.HEART_CHOCOLATE.get(), "心形巧克力");
        add(ModItems.WHITE_HEART_CHOCOLATE.get(), "白心形巧克力");
        add(ModItems.PINK_HEART_CHOCOLATE.get(), "粉心形巧克力");
        add(ModItems.BREAD_SLICE.get(), "面包片");
        add(ModItems.SWISS_ROLL.get(), "瑞士卷");
        add(ModItems.SANDWICH.get(), "三明治");
        add(ModItems.WAFER_BISCUIT.get(), "威化饼干");
        add(ModItems.STUFFED_WAFER_COOKIES.get(), "威化夹心");
        add(ModItems.GINGERBREAD_MAN.get(), "姜饼人");
        add(ModItems.CANDY_CANE.get(), "拐杖糖");
        add(ModItems.POPPING_CANDY.get(), "跳跳糖");
        add(ModItems.YINHUL_COTTON_CANDY.get(), "银狐棉花糖");
        add(ModItems.MELT_DREAM_COTTON_CANDY.get(), "融梦棉花糖");
        add(ModItems.BUBBLE_GUM.get(), "泡泡糖");
        add(ModItems.GALAXY_JELLY.get(), "星河果冻");
        add("tooltip.pasterdreammod.galaxy_jelly", "§9食用后，将玩家抛到空中并获得7秒缓降");
        add("tooltip.pasterdreammod.galaxy_jelly.flavor", "§7§O --将星河捧入手中");
        add(ModItems.MILKY_WAY_JELLY.get(), "银河果冻");
        add("tooltip.pasterdreammod.milky_way_jelly", "§9食用后，将玩家传送至维度建筑限高顶部并获得30秒缓降");
        add("tooltip.pasterdreammod.milky_way_jelly.flavor", "§7§O --银河无际，星海无涯");
        add(ModItems.FORTUNE_JELLY.get(), "福灵果冻");
        add("tooltip.pasterdreammod.fortune_jelly", "§9食用后，获得幸运I（1:00）");
        add("item.minecraft.potion.effect.luck", "幸运药水");
        add("item.minecraft.splash_potion.effect.luck", "喷溅型幸运药水");
        add("item.minecraft.lingering_potion.effect.luck", "滞留型幸运药水");
        add(ModItems.LIGHT_ORGAN.get(), "发光器官");
        add(ModItems.JELLYFISH_MUD.get(), "水母泥");
        add(ModItems.JELLYFISH_JELLO.get(), "水母果冻");
        add(ModItems.QUEER_SOUP.get(), "奇异炖菜");
        add(ModItems.LEGENDARY_DRAGON_HORN_ICE_CREAM.get(), "传说龙角冰淇淋");
        add("item.pasterdream.legendary_dragon_horn_ice_cream.describe.0", "§9幸运值永久+10（直至死亡）");
        add("item.pasterdream.legendary_dragon_horn_ice_cream.describe.1", "§9效果不叠加");
        add("item.pasterdream.legendary_dragon_horn_ice_cream.client.success", "§e味道有些熟悉，好像是来自雪泪寒……等等？");
        add("item.pasterdream.legendary_dragon_horn_ice_cream.client.fail", "§7你已经品尝过这个冰淇淋的味道了");
        add(ModItems.ELIXIR_BOTTLE.get(), "灵药瓶");
        add(ModItems.ELIXIR_BOTTLE_OF_POTION.get(), "装有药水的灵药瓶");
        add("tooltip.pasterdreammod.elixir_bottle_of_potion.uses", "§7剩余饮用次数：%s");
        add(ModItems.ELIXIR_BOTTLE_OF_MELT_DREAM.get(), "融梦灵药瓶");
        add(ModItems.ELIXIR_BOTTLE_OF_RAGE_ELIXIR.get(), "狂暴战意灵药");
        add("tooltip.pasterdreammod.elixir_bottle_of_rage_elixir.1", "§7饮用后获得效果：");
        add("tooltip.pasterdreammod.elixir_bottle_of_rage_elixir.2", "§7▪ §9技能冷却时间-15%，攻击伤害+20%（直至死亡）");
        add("item.pasterdream.elixir_bottle_of_rage_elixir.client.success", "§5你感到一股力量在身体内涌动……");
        add("item.pasterdream.elixir_bottle_of_rage_elixir.client.fail", "§7你已经饮用过这个药水了");
        add(ModItems.BUBBLE_TEA.get(), "珍珠奶茶");
        add(ModItems.SWEET_DREAM_MUSIC_DISC.get(), "音乐唱片");
        add(ModItems.SWEET_DREAM_MUSIC_DISC.get().getDescriptionId() + ".desc", "PasterDream - 甜蜜的梦");
        add(ModItems.SNOWFALL_DREAM_MUSIC_DISC.get(), "音乐唱片");
        add(ModItems.SNOWFALL_DREAM_MUSIC_DISC.get().getDescriptionId() + ".desc", "PasterDream - 落雪之梦");
        add(ModItems.PINEAPPLE_LOVE_SEA.get(), "凤梨爱琴海");
        add(ModItems.BLUE_HEART_OF_THE_SEA.get(), "蔚蓝海洋之心");
        add(ModItems.ELDER_GUARDIAN_SCALE.get(), "远古守卫者鳞片");
        add(ModItems.RED_DEW.get(), "鲜红露滴");
        add(ModItems.BLUE_DEW.get(), "蔚蓝露滴");


        add(ModItems.SOUL_DUST.get(), "灵魂粉尘");
        add(ModItems.SOUL_ESSENCE.get(), "灵魂精华");
        add(ModItems.CHARGED_AMETHYST.get(), "盈能紫水晶");
        add(ModItems.RUST_BLACK_METAL_GRAIN.get(), "锈蚀金属碎屑");
        add(ModItems.BLACK_METAL_INGOT.get(), "黑金属锭");
        add(ModItems.BLACK_METAL_GRAIN.get(), "黑金属粒");
        add(ModItems.DYEDREAM_COROLLA.get(), "梦染花冠");
        add(ModItems.WHITE_COROLLA.get(), "苍白花冠");
        add(ModItems.WHITE_CRYSTAL.get(), "白厄水晶");
        add(ModItems.CONGEAL_WIND.get(), "凝结之风");
        add(ModItems.WIND_RUNNER_CRYSTAL.get(), "风行者水晶");
        add(ModItems.PULSE_WIND_RUNNER_CRYSTAL.get(), "脉冲风行者水晶");
        add(ModItems.CONGEAL_WIND_IRON_INGOT.get(), "凝风铁锭");
        add("tooltip.pasterdream.white_crystal", "§7制作白厄剑的核心材料");
        add(ModItems.LIGHT_BALL.get(), "流明光球");
        add(ModItems.COTTON.get(), "棉花");
        add(ModItems.SCULK_HEART.get(), "回响之心");
        add(ModItems.BLACK_STICK.get(), "黑石棍");
        add(ModItems.NIGHTMARE_FUEL.get(), "噩梦燃料");
        add("tooltip.pasterdreammod.nightmare_fuel", "§7傻子和疯子使用的邪恶残渣");
        add(ModItems.SPOOL.get(), "线轴");
        add(ModItems.FABRIC.get(), "纺织布料");
        add(ModItems.PROTECT_DECK.get(), "护甲板");
        add(ModItems.TITANIUM_UPGRADE.get(), "钛金升级套件");
        add(ModItems.SCULK_UPGRADE.get(), "回响升级套件");
        add(ModItems.DYEDREAM_UPGRADE.get(), "染梦升级套件");
        add(ModItems.DEEP_SEA_TREASURE.get(), "深海秘宝");
        add(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get(), "染梦深海秘宝");
        add(ModItems.SHADOW_DEEP_SEA_TREASURE.get(), "浸影深海秘宝");
        add(ModItems.ATTACK_ENHANCE_STONE.get(), "攻击强化石");
        add(ModItems.LUCK_ENHANCE_STONE.get(), "幸运强化石");
        add(ModItems.COPPER_SWORD.get(), "铜剑");
        add(ModItems.COPPER_PICKAXE.get(), "铜镐");
        add(ModItems.COPPER_AXE.get(), "铜斧");
        add(ModItems.COPPER_SHOVEL.get(), "铜锹");
        add(ModItems.COPPER_HOE.get(), "铜锄");
        add(ModItems.COPPER_HELMET.get(), "铜头盔");
        add(ModItems.COPPER_CHESTPLATE.get(), "铜胸甲");
        add(ModItems.COPPER_LEGGINGS.get(), "铜护腿");
        add(ModItems.COPPER_BOOTS.get(), "铜靴子");
        add(ModItems.TITANIUM_SWORD.get(), "钛金剑");
        add(ModItems.TITANIUM_PICKAXE.get(), "钛金镐");
        add(ModItems.TITANIUM_AXE.get(), "钛金斧");
        add(ModItems.TITANIUM_SHOVEL.get(), "钛金锹");
        add(ModItems.TITANIUM_HOE.get(), "钛金锄");
        add(ModItems.MOLTEN_GOLD_SWORD.get(), "炙焰金剑");
        add(ModItems.MOLTEN_GOLD_PICKAXE.get(), "炙焰金镐");
        add(ModItems.MOLTEN_GOLD_AXE.get(), "炙焰金斧");
        add(ModItems.MOLTEN_GOLD_SHOVEL.get(), "炙焰金锹");
        add(ModItems.MOLTEN_GOLD_HOE.get(), "炙焰金锄");
        add(ModItems.HELLFIRE_SWORD.get(), "狱炎剑");
        add(ModItems.HELLFIRE_PICKAXE.get(), "狱炎镐");
        add(ModItems.MELT_DREAM_PICKAXE.get(), "融梦水晶镐");
        add(ModItems.MELT_DREAM_AXE.get(), "融梦水晶斧");
        add(ModItems.MELT_DREAM_SHOVEL.get(), "融梦水晶锹");
        add(ModItems.MELT_DREAM_HOE.get(), "融梦水晶锄");
        add(ModItems.INFERNO_SWORD.get(), "『融骸』狱炎剑");
        add(ModItems.DYEDREAM_SWORD.get(), "染梦合金剑");
        add(ModItems.DYEDREAM_PICKAXE.get(), "染梦合金镐");
        add(ModItems.DYEDREAM_AXE.get(), "染梦合金斧");
        add(ModItems.DYEDREAM_SHOVEL.get(), "染梦合金锹");
        add(ModItems.DYEDREAM_HOE.get(), "染梦合金锄");
        add(ModItems.DYEDREAM_HAMMER.get(), "染梦合金锤");
        add(ModItems.SHARP_MELT_DREAM_SWORD.get(), "极锋染梦合金剑");
        add(ModItems.TIDE_SWORD.get(), "引潮剑");
        add(ModItems.BEIHAI_RUO_TIDE_SWORD.get(), "『北海若』引潮");
        add(ModItems.KUSANAGI.get(), "草薙");
        add(ModItems.MURAKUMO_KUSANAGI.get(), "『天丛云』草薙");
        add(ModItems.SWORD_EMBRYO.get(), "失落之剑");
        add(ModItems.ICE_SHADOW_HAMMER.get(), "冰影战锤");
        add(ModItems.SHADOW_EROSION_DAGGER.get(), "影蚀匕首");
        add(ModItems.SHADOW_EROSION_PICKAXE.get(), "影蚀镐");
        add(ModItems.SHADOW_EROSION_AXE.get(), "影蚀斧");
        add(ModItems.SHADOW_EROSION_SHOVEL.get(), "影蚀锹");
        add(ModItems.SHADOW_EROSION_HOE.get(), "影蚀锄");
        add(ModItems.THERMAL_DAGGER.get(), "热能匕首");
        add(ModItems.DESERT_SWORD.get(), "朔漠大剑");
        add(ModItems.CHENJINGMEN_DESERT_SWORD.get(), "『沉荆门』朔漠");
        add(ModItems.BROKEN_HERO_SWORD.get(), "断裂英雄剑");
        add(ModItems.TITANIUM_HELMET.get(), "钛金头盔");
        add(ModItems.TITANIUM_CHESTPLATE.get(), "钛金胸甲");
        add(ModItems.TITANIUM_LEGGINGS.get(), "钛金护腿");
        add(ModItems.TITANIUM_BOOTS.get(), "钛金靴子");
        add(ModItems.SCULK_HELMET.get(), "回响头盔");
        add(ModItems.SCULK_CHESTPLATE.get(), "回响胸甲");
        add(ModItems.SCULK_LEGGINGS.get(), "回响护腿");
        add(ModItems.SCULK_BOOTS.get(), "回响靴子");
        add(ModItems.DYEDREAM_HELMET.get(), "染梦头盔");
        add(ModItems.DYEDREAM_CHESTPLATE.get(), "染梦胸甲");
        add(ModItems.DYEDREAM_LEGGINGS.get(), "染梦护腿");
        add(ModItems.DYEDREAM_BOOTS.get(), "染梦靴子");
        add(ModItems.MACHINE_LIGHT_WING.get(), "机械光翼");
        add(ModItems.ANGEL_WING.get(), "天使之翼");
        add(ModItems.FORSAKENS_WING.get(), "遗忘之翼");
        add(ModItems.DREAM_FERTILIZER.get(), "衍梦肥泥");
        add(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get(), "融梦水晶碎片");
        add(ModItems.MELT_DREAM_AURORIAN_STEEL.get(), "融梦极光钢锭");
        add("tooltip.pasterdreammod.melt_dream_aurorian_steel", "§7§o孩子们，问了吗？");
        add("block.pasterdream.shadow_liquid", "熔融阴影");
        add("block.pasterdream.melt_dream_liquid", "融梦涌泉");

        add(ModItems.DREAM_NOTES_DYEDREAM_WORLD.get(), "寻梦者笔记-染梦世界");
        add(ModItems.DREAM_NOTES_LAMP_SHADOW_WORLD.get(), "寻梦者笔记-灯影之下");
        add(ModItems.DREAM_NOTES_WIND_JOURNEY_WORLD.get(), "寻梦者笔记-风之旅途");
        add(ModItems.DREAM_NOTES_STORY_GUIDE.get(), "已解析的笔记");
        add(ModItems.BLUE_PRINT.get(), "蓝图");
        add(ModItems.DREAM_NOTES_BOOK.get(), "寻梦者笔记书");

        add(ModItems.REED_ROD.get(), "芦苇杆");
        add(ModItems.JUNGLE_SPORE.get(), "丛林孢子");
        add(ModItems.MELT_DREAM_COIN.get(), "融梦星币");
        add(ModItems.MELT_DREAM_COIN_PILE.get(), "融梦星币堆");
        add(ModItems.PERGAMYN.get(), "羊皮纸");

        add(ModBlocks.DYEDREAM_DIRT.get(), "染梦泥土");
        add(ModBlocks.DYEDREAM_GRASS_BLOCK.get(), "染梦草方块");
        add(ModBlocks.DYEDREAM_FARMLAND.get(), "染梦耕地");
        add(ModBlocks.DYEDREAM_LOG.get(), "染梦原木");
        add(ModBlocks.DYEDREAM_WOOD.get(), "染梦木头");
        add(ModBlocks.DYEDREAM_LEAVES.get(), "染梦树叶");
        add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get(), "染梦世界树树叶");
        add(ModBlocks.DYEDREAM_SAPLING.get(), "染梦树苗");
        add(ModBlocks.DYEDREAM_QUARTZ_ORE.get(), "染梦石英矿石");
        add(ModBlocks.DYEDREAM_DUST_ORE.get(), "染梦粉尘矿石");
        add(ModBlocks.AMBER_CANDY_ORE.get(), "琥珀糖矿石");
        add(ModBlocks.TITANIUM_ORE.get(), "钛矿石");
        add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(), "深层钛矿石");
        add(ModBlocks.MOLTEN_GOLD_ORE.get(), "炙焰金矿石");
        add(ModBlocks.SOUL_ORE.get(), "灵魂矿土");
        add(ModBlocks.CONGEAL_WIND_ORE.get(), "凝结之风云矿");
        add(ModBlocks.WIND_RUNNER_CRYSTAL_ORE.get(), "风行者水晶矿石");
        add(ModBlocks.CONGEAL_WIND_BLOCK.get(), "凝结之风块");
        add(ModBlocks.WIND_RUNNER_CRYSTAL_BLOCK.get(), "风行者水晶块");
        add(ModBlocks.CONGEAL_WIND_IRON_BLOCK.get(), "凝风铁块");
        add(ModBlocks.CONGEAL_WIND_IRON_BARS.get(), "凝风铁栏杆");
        add(ModBlocks.EJECTION_PRESSURE_PLATE.get(), "弹射压力板");
        add(ModBlocks.EJECTION_PRESSURE_BLOCK.get(), "弹射方块");
        add(ModBlocks.RAW_TITANIUM_BLOCK.get(), "粗钛金块");
        add(ModBlocks.SALT_BLOCK.get(), "盐块");
        add(ModBlocks.TITANIUM_BLOCK.get(), "钛金块");
        add(ModBlocks.MOLTEN_GOLD_BLOCK.get(), "炙焰金块");
        add(ModBlocks.CHARGED_AMETHYST_BLOCK.get(), "盈能紫水晶块");
        add(ModBlocks.RUST_BLACK_METAL_BLOCK.get(), "锈蚀金属块");
        add(ModBlocks.BLACK_METAL_BLOCK.get(), "黑金属块");
        add(ModBlocks.RUST_BLACK_METAL_WALL.get(), "锈蚀金属墙");
        add(ModBlocks.RUST_BLACK_METAL_BARS.get(), "锈蚀金属栏杆");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get(), "染梦石英块");
        add(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get(), "平滑染梦石英块");
        add(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get(), "染梦石英砖");
        add(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get(), "染梦石英柱");
        add(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get(), "錾制染梦石英块");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get(), "染梦石英楼梯");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), "染梦石英台阶");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get(), "染梦石英墙");
        add(ModBlocks.DYEDREAM_PLANKS.get(), "染梦木板");
        add(ModBlocks.DYEDREAM_STAIRS.get(), "染梦木楼梯");
        add(ModBlocks.DYEDREAM_SLAB.get(), "染梦木台阶");
        add(ModBlocks.DYEDREAM_FENCE.get(), "染梦木栅栏");
        add(ModBlocks.DYEDREAM_FENCE_GATE.get(), "染梦木栅栏门");
        add(ModBlocks.DYEDREAM_PANE.get(), "染梦木窗格");
        add(ModBlocks.DYEDREAM_DOOR.get(), "染梦木门");
        add(ModBlocks.DYEDREAM_TRAPDOOR.get(), "染梦木活板门");
        add(ModBlocks.DYEDREAM_PRESSURE_PLATE.get(), "染梦木压力板");
        add(ModBlocks.DYEDREAM_BUTTON.get(), "染梦木按钮");
        add(ModBlocks.PINK_SLIME_BLOCK.get(), "粉色粘液块");
        add(ModBlocks.PINK_MUSHROOM_BLOCK.get(), "粉顶菌菌盖");
        add(ModBlocks.PINK_MUSHROOM_STEM.get(), "粉顶菌菌柄");
        add(ModBlocks.PINK_MUSHROOM_PORES.get(), "粉顶菌菌孔块");
        add(ModBlocks.PINK_SHROOMLIGHT.get(), "粉顶菌菌光体");
        add(ModBlocks.PINK_MUSHROOM.get(), "粉顶菇");
        add(ModBlocks.TALL_PINK_MUSHROOM.get(), "高粉顶菇");
        add(ModBlocks.DYEDREAM_SAND.get(), "染梦沙");
        add(ModBlocks.DYEDREAM_GLASS.get(), "染梦玻璃");
        add(ModBlocks.DYEDREAM_GLASS_PANE.get(), "染梦玻璃板");
        add(ModBlocks.CARVE_DYEDREAM_GLASS.get(), "雕花染梦玻璃");
        add(ModBlocks.CARVE_DYEDREAM_GLASS_PANE.get(), "雕花染梦玻璃板");
        add(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get(), "镶金雕花染梦玻璃");
        add(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS_PANE.get(), "镶金雕花染梦玻璃板");
        add(ModBlocks.CLARITY_GLASS.get(), "澄澈玻璃");
        add(ModBlocks.CLARITY_GLASS_PANE.get(), "澄澈玻璃板");
        add(ModBlocks.CARVE_CLARITY_GLASS.get(), "雕纹澄澈玻璃");
        add(ModBlocks.CARVE_CLARITY_GLASS_PANE.get(), "雕纹澄澈玻璃板");
        add(ModBlocks.FRAME_CLARITY_GLASS.get(), "镶框澄澈玻璃");
        add(ModBlocks.FRAME_CLARITY_GLASS_PANE.get(), "镶框澄澈玻璃板");
        add(ModBlocks.BREAK_WIND_CURTAIN.get(), "破风幕帐");
        add(ModBlocks.CHRISTMAS_LIGHTS.get(), "圣诞彩灯");
        add(ModBlocks.DYEDREAM_BUDDING_BLOCK.get(), "染梦母岩");
        add(ModBlocks.SMALL_DYEDREAM_BUD.get(), "小型染梦晶芽");
        add(ModBlocks.MEDIUM_DYEDREAM_BUD.get(), "中型染梦晶芽");
        add(ModBlocks.LARGE_DYEDREAM_BUD.get(), "大型染梦晶芽");
        add(ModBlocks.DYEDREAM_BUD_BLOCK.get(), "染梦水晶块");
        add(ModBlocks.DYEDREAM_BUD_BRICKS.get(), "染梦水晶砖");
        add(ModBlocks.DYEDREAM_BUD_STAIRS.get(), "染梦水晶楼梯");
        add(ModBlocks.DYEDREAM_BUD_SLAB.get(), "染梦水晶台阶");
        add(ModBlocks.DYEDREAM_BUD_WALL.get(), "染梦水晶墙");
        add(ModBlocks.DYEDREAM_ICE.get(), "染梦冰");
        add(ModBlocks.DYEDREAM_PACKED_ICE.get(), "染梦浮冰");
        add(ModBlocks.CLOUD.get(), "云朵");
        add(ModBlocks.DARK_CLOUD.get(), "乌云");
        add(ModBlocks.WHITE_SAND.get(), "白沙");
        add(ModBlocks.THICK_CLOUD.get(), "厚重云朵");
        add(ModBlocks.SHADOW.get(), "阴影");
        add(ModBlocks.THICK_SHADOW.get(), "厚重阴影");
        add(ModBlocks.SHADOW_STONE.get(), "阴影石");
        add(ModBlocks.SHADOW_STONE_BRICK.get(), "阴影石砖");
        add(ModBlocks.SHADOW_STONE_BRICK_STAIRS.get(), "阴影石砖楼梯");
        add(ModBlocks.SHADOW_STONE_BRICK_SLAB.get(), "阴影石砖台阶");
        add(ModBlocks.SHADOW_STONE_BRICK_WALL.get(), "阴影石砖墙");
        add(ModBlocks.NARROW_SHADOW_STONE_BRICK.get(), "细阴影石砖");
        add(ModBlocks.NARROW_SHADOW_STONE_BRICK_STAIRS.get(), "细阴影石砖楼梯");
        add(ModBlocks.NARROW_SHADOW_STONE_BRICK_SLAB.get(), "细阴影石砖台阶");
        add(ModBlocks.NARROW_SHADOW_STONE_BRICK_WALL.get(), "细阴影石砖墙");
        add(ModBlocks.SHADOW_STONE_TILES.get(), "阴影石瓦");
        add(ModBlocks.SHADOW_STONE_TILES_STAIRS.get(), "阴影石瓦楼梯");
        add(ModBlocks.SHADOW_STONE_TILES_SLAB.get(), "阴影石瓦台阶");
        add(ModBlocks.SHADOW_STONE_TILES_WALL.get(), "阴影石瓦墙");
        add(ModBlocks.CRACKED_SHADOW_STONE_BRICK.get(), "裂阴影石砖");
        add(ModBlocks.CHISELED_SHADOW_STONE_BRICK.get(), "錾制阴影石砖");
        add(ModBlocks.SHADOW_STONE_CAGE_RUNE.get(), "阴影石圣所符文");
        add(ModBlocks.SHADOW_STONE_HOLY_GRAIL_RUNE.get(), "阴影石灵皿符文");
        add(ModBlocks.SHADOW_STONE_OBLATION_RUNE.get(), "阴影石牲礼符文");
        add(ModBlocks.SHADOW_STONE_TRIPOD_CAULDRON_RUNE.get(), "阴影石镬器符文");
        // ===== 暗影地牢方块系列 =====
        add(ModBlocks.SHADOW_DUNGEON_STONE.get(), "暗影地牢石");
        add(ModBlocks.CHISELED_SHADOW_DUNGEON_BRICKS.get(), "雕纹暗影地牢石砖");
        add(ModBlocks.SHADOW_DUNGEON_BRICKS.get(), "暗影地牢石砖");
        add(ModBlocks.CRACKED_SHADOW_DUNGEON_BRICKS.get(), "裂纹暗影地牢石砖");
        add(ModBlocks.FRACTURED_SHADOW_DUNGEON_BRICKS.get(), "开裂暗影地牢石砖");
        add(ModBlocks.SHADOW_DUNGEON_BRICK_STAIRS.get(), "暗影地牢石砖楼梯");
        add(ModBlocks.SHADOW_DUNGEON_BRICK_SLAB.get(), "暗影地牢石砖台阶");
        add(ModBlocks.SHATTERED_SHADOW_DUNGEON_BRICKS.get(), "碎裂暗影地牢石砖");
        add(ModBlocks.SHADOW_DUNGEON_GATE.get(), "暗影地牢门扉");
        add(ModBlocks.SHADOW_DUNGEON_BARRIER.get(), "暗影地牢屏障");
        add(ModBlocks.DREAM_SPAWNER.get(), "构梦刷怪笼");
        add(ModBlocks.FADED_DREAM_SPAWNER.get(), "失色构梦刷怪笼");
        add(ModBlocks.SHADOW_DUNGEON_PORTAL.get(), "暗影地牢传送门核心");
        add(ModBlocks.BROKEN_SHADOW_DUNGEON_PORTAL.get(), "破损暗影地牢传送门核心");
        add(ModBlocks.SHADOW_ARENA_BLOCK.get(), "灯影竞技场方块");
        add(ModBlocks.SHADOW_FISSURE_0.get(), "阴影裂隙");
        add(ModBlocks.SHADOW_FISSURE_1.get(), "阴影裂隙");
        add(ModBlocks.SHADOW_FISSURE_2.get(), "阴影裂隙");
        add(ModBlocks.SHADOW_FISSURE_3.get(), "阴影裂隙");
        add(ModBlocks.SHADOW_FISSURE_4.get(), "阴影裂隙");
        add(ModBlocks.SHADOW_FISSURE_5.get(), "阴影裂隙");
        add(ModBlocks.SHADOW_NYLIUM.get(), "阴影菌岩");
        add(ModBlocks.SHADOW_LIGHT.get(), "影灯");
        add(ModBlocks.SHADOW_SHROOMLIGHT.get(), "阴影菌光体");
        add(ModBlocks.SHADOW_CANDLE.get(), "阴影蜡烛");
        add(ModBlocks.TWILIGHT_LANTERN.get(), "暮影之笼");
        add(ModBlocks.SHADOW_BED.get(), "暮影长床");
        add(ModBlocks.SHADOW_WART_BLOCK.get(), "阴影疣块");
        add(ModBlocks.SHADOW_STEM.get(), "阴影菌柄");
        add(ModBlocks.SHADOW_HYPHAE.get(), "阴影菌核");
        add(ModBlocks.STRIPPED_SHADOW_STEM.get(), "去皮阴影菌柄");
        add(ModBlocks.STRIPPED_SHADOW_HYPHAE.get(), "去皮阴影菌核");
        add(ModBlocks.SHADOW_PLANKS.get(), "阴影木板");
        add(ModBlocks.SHADOW_STAIRS.get(), "阴影木楼梯");
        add(ModBlocks.SHADOW_SLAB.get(), "阴影木台阶");
        add(ModBlocks.SHADOW_FENCE.get(), "阴影木栅栏");
        add(ModBlocks.SHADOW_FENCE_GATE.get(), "阴影木栅栏门");
        add(ModBlocks.SHADOW_PANE.get(), "阴影木窗格");
        add(ModBlocks.SHADOW_DOOR.get(), "阴影木门");
        add(ModBlocks.SHADOW_TRAPDOOR.get(), "阴影木活板门");
        add(ModBlocks.SHADOW_PRESSURE_PLATE.get(), "阴影木压力板");
        add(ModBlocks.SHADOW_BUTTON.get(), "阴影木按钮");
        add(ModBlocks.SHADOW_BOOKSHELF.get(), "阴影书架");
        add(ModBlocks.WORN_SHADOW_BOOKSHELF.get(), "破旧阴影书架");
        add(ModBlocks.COBWEB_SHADOW_BOOKSHELF.get(), "蛛网阴影书架");
        add(ModBlocks.KEY_SHADOW_BOOKSHELF.get(), "钥匙阴影书架");
        add(ModItems.SHADOW_DUNGEON_KEY.get(), "暗影地牢钥匙");
        add("tooltip.pasterdream.shadow_dungeon_key", "§7用于打开暗影地牢下层的大门");
        add(ModBlocks.BIG_BUBBLE.get(), "巨型泡泡");
        add(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get(), "染梦水晶灯");
        add(ModBlocks.DYEDREAM_LANTERN.get(), "染梦灯笼");
        add(ModBlocks.DYEDREAM_ALLOY_BLOCK.get(), "染梦合金块");
        add(ModBlocks.POLISHED_CALCITE.get(), "磨制方解石");
        add(ModBlocks.POLISHED_CALCITE_STAIRS.get(), "磨制方解石楼梯");
        add(ModBlocks.POLISHED_CALCITE_SLAB.get(), "磨制方解石台阶");
        add(ModBlocks.POLISHED_CALCITE_WALL.get(), "磨制方解石墙");
        add(ModBlocks.CALCITE_TILES.get(), "方解石瓦");
        add(ModBlocks.CALCITE_TILES_STAIRS.get(), "方解石瓦楼梯");
        add(ModBlocks.CALCITE_TILES_SLAB.get(), "方解石瓦台阶");
        add(ModBlocks.CALCITE_TILES_WALL.get(), "方解石瓦墙");
        add(ModBlocks.ICE_STONE.get(), "冰晶岩");
        add(ModBlocks.ICE_BUD.get(), "冰凌晶芽");
        add(ModBlocks.CYAN_STONE.get(), "苍青岩");
        add(ModBlocks.CYAN_MOSS_STONE.get(), "苍青苔岩");
        add(ModBlocks.CYAN_STONE_BRICKS.get(), "苍青岩砖");
        add(ModBlocks.CYAN_STONE_BRICK_STAIRS.get(), "苍青岩砖楼梯");
        add(ModBlocks.CYAN_STONE_BRICK_SLAB.get(), "苍青岩砖台阶");
        add(ModBlocks.CYAN_STONE_BRICK_WALL.get(), "苍青岩砖墙");
        add(ModBlocks.CYAN_STONE_PRESSURE_PLATE.get(), "苍青岩压力板");
        add(ModBlocks.CYAN_STONE_BUTTON.get(), "苍青岩按钮");
        add(ModBlocks.MOSSY_CYAN_STONE_BRICKS.get(), "苔苍青岩砖");
        add(ModBlocks.MOSSY_CYAN_STONE_BRICK_STAIRS.get(), "苔苍青岩砖楼梯");
        add(ModBlocks.MOSSY_CYAN_STONE_BRICK_SLAB.get(), "苔苍青岩砖台阶");
        add(ModBlocks.MOSSY_CYAN_STONE_BRICK_WALL.get(), "苔苍青岩砖墙");
        add(ModBlocks.CHISELED_CYAN_STONE_BRICKS.get(), "雕文苍青岩砖");
        add(ModBlocks.CYAN_STONE_PILLAR.get(), "苍青岩柱");
        add(ModBlocks.QYM_DOLL.get(), "娇小琴雨梦玩偶");
        add(ModBlocks.UUZ_DOLL.get(), "娇小幼幼紫玩偶");
        add(ModBlocks.DYEDREAM_CRACK.get(), "染梦裂隙");
        add(ModBlocks.CLAYPAN.get(), "陶盆");
        add(ModBlocks.CLAY_POT.get(), "陶罐");
        add(ModBlocks.SHADOW_CLAY_POT.get(), "阴影陶罐");
        add(ModBlocks.DREAM_CAULDRON.get(), "法术工厂");
        add(ModBlocks.DYEDREAM_DESK.get(), "染梦书桌");
        add(ModBlocks.SHADOW_DESK.get(), "阴影书桌");
        add(ModBlocks.PICNIC_BASKET.get(), "野餐篮");
        add(ModBlocks.SHADOW_CHEST.get(), "浸影宝盒");
        add(ModBlocks.WIND_MOOR_CRATE.get(), "风泊板条筐");
        add(ModBlocks.WIND_MOOR_LOG.get(), "风泊原木");
        add(ModBlocks.WIND_MOOR_WOOD.get(), "风泊木");
        add(ModBlocks.STRIPPED_WIND_MOOR_LOG.get(), "去皮风泊原木");
        add(ModBlocks.STRIPPED_WIND_MOOR_WOOD.get(), "去皮风泊木");
        add(ModBlocks.WIND_MOOR_LEAVES_0.get(), "风泊树叶");
        add(ModBlocks.WIND_MOOR_LEAVES_1.get(), "风泊树叶");
        add(ModBlocks.WIND_MOOR_PLANKS.get(), "风泊木板");
        add(ModBlocks.WIND_MOOR_STAIRS.get(), "风泊木楼梯");
        add(ModBlocks.WIND_MOOR_SLAB.get(), "风泊木台阶");
        add(ModBlocks.WIND_MOOR_FENCE.get(), "风泊木栅栏");
        add(ModBlocks.WIND_MOOR_FENCE_GATE.get(), "风泊木栅栏门");
        add(ModBlocks.WIND_MOOR_PANE.get(), "风泊木窗格");
        add(ModBlocks.WIND_MOOR_DOOR.get(), "风泊木门");
        add(ModBlocks.WIND_MOOR_TRAPDOOR.get(), "风泊木活板门");
        add(ModBlocks.WIND_MOOR_PRESSURE_PLATE.get(), "风泊木压力板");
        add(ModBlocks.WIND_MOOR_BUTTON.get(), "风泊木按钮");
        add(ModBlocks.FIG_VINE.get(), "无花果藤");
        add(ModBlocks.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get(), "寻梦者的永恒书卷");
        add(ModBlocks.RESEARCH_TABLE.get(), "研究台");
        add(ModBlocks.LOST_SWORD_TOMB.get(), "失落剑冢");
        add(ModBlocks.GOLDEN_FOX_SCULPTURE.get(), "金狐狸雕像");
        add(ModBlocks.WIND_KNIGHT_ALTAR.get(), "破风骑士祭坛");
        add("block.pasterdream.wind_knight_altar.need_crystal", "需要嵌入 [风行者水晶]");
        add("block.pasterdream.wind_knight_altar.need_torso", "需要 [凝风铁锭] 组装躯干");
        add("block.pasterdream.wind_knight_altar.need_arms", "需要 [凝风铁锭] 组装手臂");
        add("block.pasterdream.wind_knight_altar.need_head", "需要 [凝风铁锭] 组装头颅");
        add("block.pasterdream.wind_knight_altar.throw_lightning", "将雷电药剂瓶投掷到祭坛附近以唤醒傀儡");
        add("block.pasterdream.golden_fox_sculpture.no_reaction", "雕像没有反应...");
        add("block.pasterdream.golden_fox_sculpture.cooldown", "雕像还在沉睡... 还需等待 %s 秒");
        add(ModBlocks.FOX_SCULPTURE.get(), "狐狸雕像");
        add(ModBlocks.ECOLOGY_GLASS_JAR.get(), "生态玻璃罐");
        add(ModBlocks.FIREFLY_GLASS_JAR.get(), "萤火虫玻璃罐");
        add(ModBlocks.FIREFLY_NEST.get(), "萤火虫巢");
        add(ModBlocks.BIRDS_NEST.get(), "鸟巢");
        add(ModBlocks.DESERT_HERO_TOMB.get(), "荒漠英雄之墓");
        add(ModBlocks.MELT_DREAM_CRYSTAL_CHEST.get(), "融梦水晶箱");
        add(ModBlocks.OPENED_MELT_DREAM_CRYSTAL_CHEST.get(), "打开的融梦水晶箱");
        add(ModBlocks.DREAM_ACCUMULATOR.get(), "蓄梦池");
        add(ModItems.SORBENT.get(), "吸附剂");
        add(ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE.get(), "精铸工坊工作台");
        add(ModBlocks.WEAPON_WORKSHOP_ANVIL.get(), "精铸工坊铁砧");
        add(ModBlocks.WEAPON_WORKSHOP_COOLER_POT.get(), "精铸工坊冷却盆");
        add(ModBlocks.WEAPON_WORKSHOP_HAMMER.get(), "精铸工坊锤子");
        add(ModBlocks.WEAPON_WORKSHOP_GRIND_STONE.get(), "精铸工坊砂轮");
        add(ModBlocks.WEAPON_WORKSHOP_BLAST_FURNACE.get(), "精铸工坊锻炉");
        add(ModBlocks.SHADOW_BLAST_FURNACE.get(), "暗影高炉");

        add(ModBlocks.DYEDREAM_COROLLA_CROP.get(), "梦染茶花植株");
        add(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get(), "成熟的梦染茶花植株");
        add(ModBlocks.WHITE_COROLLA_CROP.get(), "苍白雪莲植株");
        add(ModItems.WHITE_COROLLA_CROP_AGE_1.get(), "成熟的苍白雪莲植株");
        add(ModBlocks.LIGHT_BALL_CROP.get(), "流明堇植株");
        add(ModItems.LIGHT_BALL_CROP_AGE_1.get(), "成熟的流明堇植株");
        add(ModBlocks.CLOUD_CROP.get(), "玲云花植株");
        add(ModItems.CLOUD_CROP_AGE_1.get(), "成熟的玲云花植株");
        add(ModBlocks.COTTON_CROP.get(), "棉花植株");
        add(ModItems.COTTON_CROP_AGE_1.get(), "成熟的棉花植株");

        add(ModBlocks.GOLDENROD.get(), "秋麒麟");
        add(ModBlocks.FERRARIA_CRISPA.get(), "魔星兰");
        add(ModBlocks.EUSTOMA.get(), "洋桔梗");
        add(ModBlocks.MALVA_SINENSIS_CAVAN.get(), "锦葵");
        add(ModBlocks.LINHT_FLOWER.get(), "苓灯花");
        add(ModBlocks.DREAMING_LOTUS.get(), "冶梦莲");
        add(ModBlocks.MISTY_DREAMING_LOTUS.get(), "迷梦冶梦莲");
        add(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get(), "染梦铃兰");
        add(ModBlocks.BLAZE_FLOWER.get(), "烈焰花");
        add(ModBlocks.WHITE_ORCHID_FLOWER.get(), "白厄花");
        add(ModBlocks.EDELWEISS.get(), "雪绒花");
        add(ModBlocks.NIPPY_EDELWEISS.get(), "凛冽雪绒花");
        add(ModBlocks.DYEDREAM_LILY_PAD.get(), "染梦睡莲");
        add(ModBlocks.DYEDREAM_LOTUS.get(), "染梦莲花");
        add(ModBlocks.DYEDREAM_VINE.get(), "染梦藤蔓");

        add(ModBlocks.DYEDREAM_MOSS.get(), "染梦苔藓");
        add(ModBlocks.STEM_GRASS.get(),"茎草");
        add(ModBlocks.TALL_STEM_GRASS.get(),"高茎草");
        add(ModBlocks.SINGULARITY_FERN.get(), "奇异蕨");
        add(ModBlocks.CRIMSON_THORNS.get(), "赤荆棘");
        add(ModBlocks.OATS.get(), "洋麦");
        add(ModBlocks.RYE.get(), "兰麦");
        add(ModBlocks.POLISHED_CALCITE_STALICRIPE.get(), "方解石笋");
        add(ModBlocks.SMALL_POLISHED_CALCITE_STALICRIPE.get(), "小方解石笋");
        add(ModBlocks.DYEDREAM_SEAGRASS.get(), "染梦海草");
        add(ModBlocks.REED.get(), "芦苇");
        add(ModBlocks.JUNGLE_SPORANGIUM.get(), "丛林孢子囊");
        add(ModBlocks.FOURLEAF_CLOVER.get(),"四叶草");
        add(ModBlocks.HAIRY_MOSS.get(), "茸毛苔");
        add(ModBlocks.WIND_CLEAVING_GRASS.get(), "斩风草");
        add(ModBlocks.WIND_FEATHER_GRASS.get(), "风羽草");
        add(ModBlocks.WIND_ISLAND_REED.get(), "风岛芦苇");

        add(ModBlocks.SHADOW_SHORT_ROOTS.get(), "阴影矮菌索");
        add(ModBlocks.SHADOW_ROOTS.get(), "阴影菌索");
        add(ModBlocks.SHADOW_STEM_FERN.get(), "影茎蕨");
        add(ModBlocks.SHADOW_SPROUTS.get(), "影苗");
        add(ModBlocks.SHADOW_FERN.get(), "影蕨");
        add(ModBlocks.SHADOW_FUNGUS.get(), "阴影蘑菇");

        add(ModBlocks.POTTED_STEM_GRASS.get(), "盆栽茎草");
        add(ModBlocks.POTTED_PINK_MUSHROOM.get(), "盆栽粉顶菇");
        add(ModBlocks.POTTED_DYEDREAM_MOSS.get(), "盆栽染梦苔藓");
        add(ModBlocks.POTTED_LINHT_FLOWER.get(), "盆栽苓灯花");
        add(ModBlocks.POTTED_DYEDREAM_LILY_OF_THE_VALLEY.get(), "盆栽染梦铃兰");
        add(ModBlocks.POTTED_SINGULARITY_FERN.get(), "盆栽奇异蕨");
        add(ModBlocks.POTTED_FERRARIA_CRISPA.get(), "盆栽魔星兰");
        add(ModBlocks.POTTED_EUSTOMA.get(), "盆栽洋桔梗");
        add(ModBlocks.POTTED_JUNGLE_SPORANGIUM.get(), "盆栽丛林孢子囊");
        add(ModBlocks.POTTED_MALVA_SINENSIS_CAVAN.get(), "盆栽锦葵");
        add(ModBlocks.POTTED_GOLDENROD.get(), "盆栽秋麒麟");
        add(ModBlocks.POTTED_FOURLEAF_CLOVER.get(), "盆栽四叶草");
        add(ModBlocks.POTTED_BLAZE_FLOWER.get(), "盆栽烈焰花");
        add(ModBlocks.POTTED_WHITE_ORCHID_FLOWER.get(), "盆栽白厄花");
        add(ModBlocks.POTTED_SHADOW_SHORT_ROOTS.get(), "盆栽阴影矮菌索");
        add(ModBlocks.POTTED_SHADOW_ROOTS.get(), "盆栽阴影菌索");
        add(ModBlocks.POTTED_SHADOW_SPROUTS.get(), "盆栽影苗");
        add(ModBlocks.POTTED_SHADOW_FERN.get(), "盆栽影蕨");
        add(ModBlocks.POTTED_SHADOW_FUNGUS.get(), "盆栽阴影蘑菇");
        add(ModBlocks.POTTED_EDELWEISS.get(), "盆栽雪绒花");
        add(ModBlocks.POTTED_NIPPY_EDELWEISS.get(), "盆栽凛冽雪绒花");
        add(ModBlocks.POTTED_DYEDREAM_SAPLING.get(), "盆栽染梦树苗");
        add(ModBlocks.POTTED_REED.get(), "盆栽芦苇");
        add(ModBlocks.POTTED_RYE.get(), "盆栽兰麦");
        add(ModBlocks.POTTED_OATS.get(), "盆栽洋麦");
        add(ModBlocks.POTTED_DYEDREAM_COROLLA_CROP.get(), "盆栽梦染茶花");
        add(ModBlocks.POTTED_WHITE_COROLLA_CROP.get(), "盆栽苍白雪莲");
        add(ModBlocks.POTTED_LIGHT_BALL_CROP.get(), "盆栽流明堇");
        add(ModBlocks.POTTED_CLOUD_CROP.get(), "盆栽玲云花");
        add(ModBlocks.POTTED_COTTON_CROP.get(), "盆栽棉花");

        add(ModItems.DEBUG_SWORD.get(), "调试之剑");
        add(ModItems.LOOT_GENERATOR.get(), "战利品生成器");
        add(ModItems.MELT_DREAM_CRYSTAL_CHEST_RESET_TOOL.get(), "融梦水晶箱重置工具");
        add(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_0.get(), "破碎粒子提供方块0");
        add(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_1.get(), "破碎粒子提供方块1");
        add(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2.get(), "破碎粒子提供方块2");
        add(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_3.get(), "破碎粒子提供方块3");
        add(ModBlocks.DREAM_TRAIN_STRUCTURE.get(), "逐梦列车结构");

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
        add("fluid.pasterdream.potion","药水（流体）");
        add("fluid.pasterdream.potion.with_effect","药水（%s）");
        add("fluid.pasterdream.uncooked_dyedream_flower_tea","未烹调的染梦花茶（流体）");
        add("fluid.pasterdream.watermelon_juice","西瓜汁（流体）");
        add("fluid.pasterdream.wind_plant_extract","风植萃取液（流体）");
        add("fluid.pasterdream.yeast","酵母（流体）");
        add("fluid.pasterdream.ink","墨水罐（流体）");

        add("itemGroup.pasterdream.pasterdream_food_tab", "帕斯特之梦 | 饮食");
        add("itemGroup.pasterdream.pasterdream_items_tab", "帕斯特之梦 | 物品");
        add("itemGroup.pasterdream.pasterdream_gear_tab", "帕斯特之梦 | 装备");
        add("itemGroup.pasterdream.pasterdream_equipment_tab", "帕斯特之梦 | 设备");
        add("itemGroup.pasterdream.pasterdream_blocks_tab", "染梦世界");
        add("itemGroup.pasterdream.pasterdream_shadow_tab", "灯影之下");
        add("itemGroup.pasterdream.pasterdream_plants_tab", "帕斯特之梦 | 植物");
        add("itemGroup.pasterdream.pasterdream_dream_notes_tab", "帕斯特之梦 | 笔记");
        add("itemGroup.pasterdream.pasterdream_dream_debug_tab", "帕斯特之梦 | 调试栏");
        add("itemGroup.pasterdream.pasterdream_expansion_tab", "帕斯特之梦 | 拓展栏");
        add("itemGroup.pasterdream.pasterdream_memento_tab", "帕斯特之梦 | 追忆");
        add("itemGroup.pasterdream.wind_journey_world", "风之旅途");


        add("button.pasterdream.mortarbutton", "研磨");
        add("button.pasterdream.copy_button", "复制");
        add("button.pasterdream.research_button", "研究");

        add("tooltip.pasterdreammod.magic_stone", "§7§o哪个魔法师的兜里不会踹几块魔法石呢？");
        add("tooltip.pasterdreammod.goldenrod_tea", "§d持续移除饥饿与反胃效果");
        add("tooltip.pasterdreammod.glass_cup_of_honey_juice", "§d饮用后清空所有负面效果");
        add("tooltip.pasterdreammod.dyedream_perfume", "§d清空未睡眠天数，幻翼不会攻击你");
        add("tooltip.pasterdreammod.dyedream_perfume.flavor", "§7§o当你在思考为什么香水要用来喝时\n或许应该先考虑你现在正在做梦？");
        add("tooltip.pasterdreammod.melt_dream_crystal_fragment", "§7此世间一切拥有灵魂的生物皆有梦\n那些醒来时无法想起的细缕回忆\n破碎并融化被埋藏在世界各处凝结成晶\n等待着被新的灵魂发现");
        add("tooltip.pasterdreammod.upgrade_kit", "§7用于在锻造台处升级装备 可以保留装备的原有强化属性");
        add("tooltip.pasterdream.enhance_stone.usage", "§7用于在精铸工坊中强化合成原胚");
        add("tooltip.pasterdream.thermal_dagger.swim", "§7▪ §9主手时 +0.5 游泳速度");
        add("tooltip.pasterdream.thermal_dagger", "§7§o-- 深海迷航");
        add("tooltip.pasterdream.deep_treasure", "§7飞溅的浪花将我们推向远方");
        add("tooltip.pasterdream.shadow_deep_treasure", "§7表面似乎还流淌着阴影");
        add("tooltip.pasterdream.attack_enhance_stone.effect", "§7▪ §9攻击力+0.1~+0.5");
        add("tooltip.pasterdream.luck_enhance_stone.effect", "§7▪ §9幸运+1~+2");
        add("tooltip.pasterdream.右键打开GUI", "§7右键打开GUI");
        add("tooltip.pasterdream.此方块仅用于提供粒子效果，生存模式无法获取", "§7此方块仅用于提供粒子效果，生存模式无法获取");
        add("tooltip.pasterdream.左键实体：直接删除此实体", "§7左键实体：直接删除此实体");
        add("tooltip.pasterdream.左键方块：模拟破坏此方块", "§7左键方块：模拟破坏此方块");
        add("tooltip.pasterdream.右键实体：在聊天框打印此实体NBT", "§7右键实体：在聊天框打印此实体NBT");
        add("tooltip.pasterdream.右键方块：在聊天框打印此方块BlockState和NBT", "§7右键方块：在聊天框打印此方块BlockState和NBT");
        add("tooltip.pasterdream.用于生成战利品，对着箱子点击右键以生成", "§7用于生成战利品，对着箱子点击右键以生成");
        add("tooltip.pasterdream.当前设置战利品表：", "§7当前设置战利品表：");
        add("tooltip.pasterdream.loot_table.not_set", "§7未设置");
        add("tooltip.pasterdream.loot_generator.usage.shift", "§7对容器使用Shift+右键以在容器生成战利品");
        add("tooltip.pasterdream.右键打开GUI以查看蓝图结构", "右键打开GUI以查看蓝图结构");

        add("tooltip.pasterdream.normal_loot_table", "§3普通战利品：%s，权重：%d，幸运权重倍率：%f");
        add("tooltip.pasterdream.rare_loot_table", "§a稀有战利品：%s，权重：%d，幸运权重倍率：%f");
        add("tooltip.pasterdream.legend_loot_table", "§d史诗战利品：%s，权重：%d，幸运权重倍率：%f");
        add("tooltip.pasterdream.not_have_loot_table", "§c无战利品");
        add("tooltip.pasterdream.按住Shift+右键点击打开的融梦水晶箱可复位（写入此物品NBT中包含的战利品列表）", "§7按住Shift+右键点击打开的融梦水晶箱可复位（写入此物品NBT中包含的战利品列表）");
        add("tooltip.pasterdream.reset_cost_melt_dream_energy", "复位消耗%f融梦能量");

        add("message.pasterdream.融梦水晶箱重置工具只能重置打开的融梦水晶箱", "融梦水晶箱重置工具只能重置打开的融梦水晶箱");
        add("message.pasterdream.融梦水晶箱战利品品质应为1，2或3，但是此时为", "融梦水晶箱战利品品质应为1，2或3，但是此时为%d");
        add("message.pasterdream.融梦能量不足", "融梦能量不足");

        add("message.pasterdream.loot_generator.no_loot_table", "§c战利品生成器未设置战利品表");
        add("message.pasterdream.loot_generator.not_container", "§c目标方块不是容器");
        add("message.pasterdream.loot_generator.loot_table_not_found", "§c战利品表 %s 不存在");
        add("message.pasterdream.loot_generator.loot_table_set", "§a已清空容器并写入战利品表：%s，下次打开容器时生成战利品");

        add("message.pasterdream.已放置蓝图", "已放置蓝图");
        add("message.pasterdream.蓝图放置失败", "蓝图放置失败");
        add("message.pasterdream.材料不足", "材料不足");
        add("button.pasterdream.blue_print_button", "放置");

        add("message.pasterdream.需要在本层寻找暗影地牢钥匙以打开大门", "需要在本层寻找暗影地牢钥匙以打开大门");

        add("option.pasterdream.Q: 模拟无工具破坏", "Q: 模拟无工具破坏");
        add("option.pasterdream.W: 模拟下界合金镐破坏", "W: 模拟下界合金镐破坏");
        add("option.pasterdream.E: 模拟下界合金镐时运III破坏", "E: 模拟下界合金镐时运III破坏");
        add("option.pasterdream.A: 模拟下界合金镐精准采集破坏", "A: 模拟下界合金镐精准采集破坏");
        add("option.pasterdream.S: 获取对应的BlockItem并掉落，并将方块设置为空气", "S: 获取对应的BlockItem并掉落，并将方块设置为空气");
        add("option.pasterdream.D: 将方块设置为空气的同时不触发方块更新", "D: 将方块设置为空气的同时不触发方块更新");

        add("effect.pasterdream.sculk_armor_buff", "幽匿回响");
        add("effect.pasterdream.dyedream_armor_buff", "染梦守护");
        add("effect.pasterdream.titanium_armor_buff", "钛金守护");
        add("effect.pasterdream.dyedream_up_buff", "染梦工具增强");
        add("tooltip.pasterdream.machine_light_wing.flight", "§7▪ §9启用飞行能力");
        add("tooltip.pasterdream.machine_light_wing.energy", "§7▪ §4融梦能量消耗：1.2/min");
        add("tooltip.pasterdream.angel_wing.flight", "§7▪ §9获得飞行能力且免疫摔落伤害");
        add("tooltip.pasterdream.angel_wing.flavor", "§7§o-- 在宿舍电脑上基于《Angel Player》幻想开发");
        add("tooltip.pasterdream.forsakens_wing.flight", "§7▪ §9获得飞行能力且免疫摔落伤害");
        add("tooltip.pasterdream.forsakens_wing.immune", "§7▪ §9免疫缓慢与凋零效果");
        add("tooltip.pasterdream.forsakens_wing.flavor", "§7§o-- 我们时空枢纽见");
        add("tooltip.pasterdreammod.titanium_armor.1", "§7套装效果：§e钛金守护");
        add("tooltip.pasterdreammod.titanium_armor.2", "§7▪ §9每 30 秒获得 2 颗吸收之♡");
        add("tooltip.pasterdreammod.sculk_armor.1", "§7套装效果：§2幽匿回响");
        add("tooltip.pasterdreammod.sculk_armor.2", "§7▪ §9最大生命值+4");
        add("tooltip.pasterdreammod.sculk_armor.3", "§7▪ §9身处深层时效果增强");
        add("tooltip.pasterdreammod.sculk_armor.4", "§7▪ §9免疫幽匿的黑暗恐惧");
        add("tooltip.pasterdreammod.sculk_armor.5", "§7▪ §9受到黑暗效果时提高移速和免伤");
        add("tooltip.pasterdreammod.sculk_armor.6", "§7▪ §9免疫失明效果");
        add("tooltip.pasterdreammod.sculk_armor.7", "§7▪ §9监守者不会攻击你");
        add("tooltip.pasterdreammod.dyedream_hammer", "§7▪ §9范围挖掘 3×3×3");
        add("tooltip.pasterdreammod.molten_gold_sword.1", "镶嵌：§7无");
        add("tooltip.pasterdreammod.molten_gold_sword.2", "§7▪ §9攻击将使目标引燃3秒");
        add("tooltip.pasterdreammod.molten_gold_tool.1", "镶嵌：§7无");
        add("tooltip.pasterdreammod.molten_gold_tool.2", "§7▪ §9右键使用将消耗饱食度来获得短暂急迫效果");
        add("tooltip.pasterdreammod.molten_gold_tool.3", "§7▪ §9冷却时间：10秒");
        add("tooltip.pasterdreammod.hellfire_sword.1", "镶嵌：§7无");
        add("tooltip.pasterdreammod.hellfire_sword.2", "§7▪ §9攻击时将使目标引燃4秒");
        add("tooltip.pasterdreammod.hellfire_sword.3", "§7▪ §9若目标已燃烧 则叠加2秒（可重复）");
        add("tooltip.pasterdreammod.hellfire_tool.1", "镶嵌：§7无");
        add("tooltip.pasterdreammod.hellfire_tool.2", "§7▪ §9右键消耗饱食度获得急迫II效果");
        add("tooltip.pasterdreammod.hellfire_tool.3", "§7▪ §9冷却时间：8秒");
        add("tooltip.pasterdreammod.inferno_sword.1", "镶嵌：§b下界之星");
        add("tooltip.pasterdreammod.inferno_sword.2", "战技：§6融骸斩击");
        add("tooltip.pasterdreammod.inferno_sword.3", "§7▪ §9攻击伤害类型转换为熔岩");
        add("tooltip.pasterdreammod.inferno_sword.4", "§7▪ §9造成2+当前攻击力+目标引燃秒数*0.6点伤害");
        add("tooltip.pasterdreammod.inferno_sword.5", "§7▪ §9若目标引燃时长大于10秒 则减少目标移速30%");
        add("tooltip.pasterdreammod.inferno_sword.6", "§7▪ §9并清除目标的引燃效果");
        add("tooltip.pasterdreammod.inferno_sword.7", "§7▪ §9冷却时间：10秒");
        add("tooltip.pasterdreammod.inferno_sword.8", "§7被动：");
        add("tooltip.pasterdreammod.inferno_sword.9", "§7▪ §9攻击引燃4秒；已燃烧时叠加3秒");
        add("tooltip.pasterdreammod.melt_dream_tool.1", "手持工具时");
        add("tooltip.pasterdreammod.melt_dream_tool.2", "§7▪ §b融梦修补：0.01E/1耐久");
        add("tooltip.pasterdreammod.dyedream_armor.1", "§7套装效果：§d染梦守护");
        add("tooltip.pasterdreammod.dyedream_armor.2", "§7▪ §9最大生命值+4");
        add("tooltip.pasterdreammod.dyedream_armor.3", "§7▪ §9每 30 秒获得 4 颗吸收之♡");
        add("tooltip.pasterdreammod.dyedream_armor.4", "§7▪ §9增强手持的染梦工具");
        add("tooltip.pasterdreammod.sharp_melt_dream_sword.1", "镶嵌：§d融梦水晶碎片");
        add("tooltip.pasterdreammod.sharp_melt_dream_sword.2", "战技：§b凌空斩");
        add("tooltip.pasterdreammod.sharp_melt_dream_sword.3", "§7▪ §9斩击并将敌人向上击飞 造成2+当前攻击力*1.5点伤害");
        add("tooltip.pasterdreammod.sharp_melt_dream_sword.4", "§7▪ §9冷却时间：5秒");
        add("tooltip.pasterdreammod.sharp_melt_dream_sword.5", "§7▪ §9拥有染梦守护效果时 冷却时间缩短为3秒");
        add("tooltip.pasterdreammod.tide_sword.1", "镶嵌：§7无");
        add("tooltip.pasterdreammod.tide_sword.2", "§7▪ §9手持武器时获得水下呼吸效果");
        add("tooltip.pasterdreammod.beihai_ruo_tide_sword.1", "镶嵌：§3海洋之心");
        add("tooltip.pasterdreammod.beihai_ruo_tide_sword.2", "战技：§3涌流剑技");
        add("tooltip.pasterdreammod.beihai_ruo_tide_sword.3", "§7▪ §9在水中向指针方向冲刺 冲刺期间免疫80%的伤害");
        add("tooltip.pasterdreammod.beihai_ruo_tide_sword.4", "§7▪ §9攻击的目标处于水时触发");
        add("tooltip.pasterdreammod.beihai_ruo_tide_sword.5", "§7▪ §9攻击造成3+当前攻击力*1.2点伤害");
        add("tooltip.pasterdreammod.beihai_ruo_tide_sword.6", "§7▪ §9冷却时间：2.5秒");
        add("tooltip.pasterdreammod.beihai_ruo_tide_sword.7", "被动：");
        add("tooltip.pasterdreammod.beihai_ruo_tide_sword.8", "§7▪ §9手持武器时获得水下呼吸效果");
        add("tooltip.pasterdreammod.kusanagi.1", "亡魂: §7%d/%d");
        add("tooltip.pasterdreammod.kusanagi.2", "§7▪ §9攻击施加中毒效果");
        add("tooltip.pasterdreammod.kusanagi.evolve", "武器成长到了新的阶段");
        add("tooltip.pasterdreammod.murakumo_kusanagi.1", "镶嵌：§7%d亡魂");
        add("tooltip.pasterdreammod.murakumo_kusanagi.2", "战技：§b极致锋利");
        add("tooltip.pasterdreammod.murakumo_kusanagi.3", "§7▪ §9右键激活后下一次攻击触发");
        add("tooltip.pasterdreammod.murakumo_kusanagi.4", "§7▪ §9攻击造成7+武器锋利等级*武器基础面板/2点伤害");
        add("tooltip.pasterdreammod.murakumo_kusanagi.5", "§7▪ §9冷却时间：4秒");
        add("tooltip.pasterdreammod.murakumo_kusanagi.6", "被动：");
        add("tooltip.pasterdreammod.murakumo_kusanagi.7", "§7▪ §9攻击施加更长时间的中毒效果");
        add("tooltip.pasterdreammod.desert_sword.1", "镶嵌：§7无");
        add("tooltip.pasterdreammod.desert_sword.2", "§7▪ §9手持武器时移速-15% 抗性+20%");
        add("tooltip.pasterdreammod.chenjingmen_desert_sword.1", "镶嵌：§e沉寂家书");
        add("tooltip.pasterdreammod.chenjingmen_desert_sword.2", "战技：§e绝地反击");
        add("tooltip.pasterdreammod.chenjingmen_desert_sword.3", "§7▪ §9技能开启时获得短暂的生命吸收效果");
        add("tooltip.pasterdreammod.chenjingmen_desert_sword.4", "§7▪ §9攻击造成5+(已损失生命百分比*2+1)*当前攻击力点伤害");
        add("tooltip.pasterdreammod.chenjingmen_desert_sword.5", "§7▪ §9冷却时间：10秒");
        add("tooltip.pasterdreammod.chenjingmen_desert_sword.6", "被动：");
        add("tooltip.pasterdreammod.chenjingmen_desert_sword.7", "§7▪ §9手持武器时移速-15% 抗性+20%");
        add("tooltip.pasterdreammod.san_value", "SAN值: ");
        add("tooltip.pasterdreammod.melt_dream_energy", "融梦能量: ");
        add("tooltip.pasterdreammod.shadow_erosion_tool.1","§7▪ §9挖掘速率会随着亮度降低而提升");
        add("tooltip.pasterdreammod.shadow_erosion_tool.2","§7▪ §9对于暗影类，深板岩类和幽匿类方块，提供挖掘速率提升");

        add("tooltip.pasterdream.developerNameList", "§d开发者名单");
        add("tooltip.pasterdream.dyedreamCreak", "§d染梦裂隙");
        add("tooltip.pasterdream.dyedreamWorld", "§d染梦世界");
        add("tooltip.pasterdream.pinkSlime", "§d粉红史莱姆");
        add("tooltip.pasterdream.whiteCorolla", "§d苍白雪莲");
        add("tooltip.pasterdream.paleBoneNeedle", "§d苍白骨针");
        add("tooltip.pasterdream.dreamFertilizer", "§d衍梦肥泥");
        add("tooltip.pasterdream.dreamAccumulator", "§d蓄梦池");

        add("tooltip.pasterdream.theLurkersInTheShadow", "§8阴影中的潜藏者");
        add("tooltip.pasterdream.infestedChurch", "§8侵染教堂");
        add("tooltip.pasterdream.depositionShadow", "§8沉淀阴影");
        add("tooltip.pasterdream.shadowTravelogue", "§8阴影游记");
        add("tooltip.pasterdream.shadowDungeon", "§8暗影地牢");
        add("tooltip.pasterdream.scare", "§8恐惧");

        add("tooltip.pasterdream.notHaveWingsBirdHaveSpreadWingsDreamToo", "§a无翼鸟也有展翅的梦");

        add("biome.pasterdream.dyedream_plains","染梦平原");
        add("biome.pasterdream.dyedream_mushroom_mountains","粉顶菇山地");
        add("biome.pasterdream.dyedream_snowy_plains","染梦雪原");
        add("biome.pasterdream.dyedream_frozen_ocean","染梦冻洋");
        add("biome.pasterdream.dyedream_ocean","染梦海洋");
        add("biome.pasterdream.shadow_nylium_wastes","菌索荒原");
        add("biome.pasterdream.shadow_forest","阴影森林");
        add("biome.pasterdream.shadow_ruins","阴影古迹");
        add("biome.pasterdream.shadow_ocean","阴影之海");
        add("biome.pasterdream.wind_moor_archipelago","风泊群岛");
        add("biome.pasterdream.misty_dream_cloud_layer","迷梦云层");

        add("recipe.pasterdream.copy", "复制");
        add("recipe.pasterdream.research", "研究");

        add("book.pasterdream.title.使用说明", "使用说明");

        add("book.pasterdream.title.花园解密:迷梦冶梦莲", "花园解密:迷梦冶梦莲");
        add("book.pasterdream.title.花园解密:凛冽雪绒花", "花园解密:凛冽雪绒花");
        add("book.pasterdream.title.花园解密:九尾狐", "花园解密:九尾狐");
        add("book.pasterdream.title.染梦游记 其一", "染梦游记 其一");
        add("book.pasterdream.title.染梦游记 其二", "染梦游记 其二");
        add("book.pasterdream.title.染梦游记 其三", "染梦游记 其三");
        add("book.pasterdream.title.染梦教堂 其一", "染梦教堂 其一");
        add("book.pasterdream.title.染梦教堂 其二", "染梦教堂 其二");
        add("book.pasterdream.title.染梦教堂 其三", "染梦教堂 其三");
        add("book.pasterdream.title.染梦水晶球", "染梦水晶球");
        add("book.pasterdream.title.祈愿树", "祈愿树");
        add("book.pasterdream.title.温暖的“寒风”", "温暖的“寒风”");
        add("book.pasterdream.title.粉顶蘑菇屋", "粉顶蘑菇屋");
        add("book.pasterdream.title.融梦涌泉井", "融梦涌泉井");
        add("book.pasterdream.title.浮空岛日记", "浮空岛日记");
        add("book.pasterdream.title.落叶归根 裂荚归冠", "落叶归根 裂荚归冠");
        add("book.pasterdream.title.梦境漂泊", "梦境漂泊");
        add("book.pasterdream.title.气泡生态球", "气泡生态球");
        add("book.pasterdream.title.染梦实验室", "染梦实验室");
        add("book.pasterdream.title.来往于梦", "来往于梦");
        add("book.pasterdream.title.染梦世界树", "染梦世界树");
        add("book.pasterdream.title.代达罗斯之翼与浮空岛", "代达罗斯之翼与浮空岛");
        add("book.pasterdream.title.星河果冻和浮空岛", "星河果冻和浮空岛");

        add("book.pasterdream.title.探求秘辛", "探求秘辛");

        add("book.pasterdream.title.研究笔记:黑金属", "研究笔记:黑金属");
        add("book.pasterdream.title.灯影游记 其二", "灯影游记 其二");
        add("book.pasterdream.title.黑暗之地", "黑暗之地");
        add("book.pasterdream.title.阴影小木屋", "阴影小木屋");
        add("book.pasterdream.title.阴影地下工作室", "阴影地下工作室");
        add("book.pasterdream.title.-被阴影浸染的字迹潦草的笔记-", "-被阴影浸染的字迹潦草的笔记-");
        add("book.pasterdream.title.关于黑暗之手的随笔", "关于黑暗之手的随笔");
        add("book.pasterdream.title.与黑色双手的决战和败北", "与黑色双手的决战和败北");
        add("book.pasterdream.title.侵染教堂-黑面", "侵染教堂-黑面");
        add("book.pasterdream.title.侵染教堂-亮面", "侵染教堂-亮面");
        add("book.pasterdream.title.暮影之笼", "暮影之笼");

        add("book.pasterdream.title.破风的骑士", "破风的骑士");
        add("book.pasterdream.title.清晨的新风", "清晨的新风");

        add("book.pasterdream.title.精铸工坊", "精铸工坊");
        add("book.pasterdream.title.暗影高炉", "暗影高炉");

        add("book.pasterdream.content.使用说明", "    本文为此类型笔记书的相关使用说明。\n    笔记书旨在于修复原版使用MC原版written_book的相关问题，原版帕斯特之梦的书直接硬编码内容进去而没有使用翻译键，这导致没有办法使用中文以外的语种，written_book本身支持翻译键，但是还有两个问题，首先是其中的内容按页分开，这个已经按照MC原版的字分好了，在安装现代化UI等模组修改字体的时候，就会因为字体的问题（大小略微有些不同），显示到外面或者最后一行显示完后还空几个字的空间才到这一页结束，而且还需要手动处理每一页的内容，维护相当的不方便，而且再怎么说，文字换页这种东西也不应该是手动处理的，其次是中英文文字内容表达密度不一样的问题，比如相同的内容用中文写5页就写完了，但是用英文可能要写7页，这样相关的翻译就很难处理了，而且中文的最后几页会完全空白，所以此笔记书应运而生，可支持翻译键和自动文字换页，维护难度将大幅下降。\n    以下内容为代码层面上的使用方法，如果您是附属模组或整合包制作者，以下内容可能会帮助到您，添加新的内容时，需要准备好以下内容：\n    书名\n    作者名称\n    文本内容\n    书的材质及其尺寸（如果和本模组使用相同的可不用准备这个）\n    在FMLCommonSetupEvent中调用DreamNotesBookRegistry.register(String contentName, Component title, String author, Component content, ResourceLocation GUI, int GUI_X, int GUI_Y, int contentStartX, int contentStartY, int contentFinalX, int contentFinalY, ModelResourceLocation itemTexture, int color)，其中：\n    contentName表示此内容的名称，随后添加创造模式物品栏的时候会用到，需要注意的是这个不能与其他笔记书的contentName相同，否则游戏启动会报错，contentName支持中文，例如您的附属模组ModId为pasterdreamaddon，那么contentName可以写成\"PasterDreamAddon:笔记内容名称\"以和其他可能重名的区别开（甚至前面的PasterDreamAddon也可以写成中文名，例如写成\"帕斯特之梦附属模组:笔记内容名称\"）。\n    title是此笔记书的标题，为了可以翻译，请使用Component.translatable()，当然如果是纯数字/纯符号等确实不需要翻译的可以使用Component.literal()，这里面推荐填写\"book.pasterdreamaddon.title.书名\"，随后在Datagen中编写相关的翻译代码，例如add(\"book.pasterdreamaddon.title.书名\", \"标题名\");\n    author是作者名称，正常填写String类型进去就可以了，例如\"琴雨梦\"等。\n    content是书的内容，和title类似，在Component.translatable()中填写翻译键即可，推荐写成\"book.pasterdreamaddon.content.书名\"，然后在Datagen中填写add(\"book.pasterdreamaddon.title.书名\", \"书的内容\");\n    GUI是书打开后GUI的纹理文件，是一个ResourceLocation，如果需要自定义的请自备这个文件，注意文件名不要带大写字母，如果使用本模组的，那么这里填写GUIBackGroundRender.DREAM_NOTES_BOOK。\n    GUI_X和GUI_Y是这个GUI的宽度和高度，如果自备文件请根据实际值填写即可，如果使用本模组的，那么这两个值分别是140和180。\n    contentStartX和contentStartY是在这个GUI中显示文字的起始坐标，纹理中边框部分有装饰等不适合显示文字的地方，确认显示范围后，填写对应的这两个值即可，如果使用本模组的，那么这两个值分别是20和20。\n    contentFinalX和contentFinalY是在这个GUI中文字显示的终止位置，同理，如果使用自己的GUI则根据显示范围填写这两个值，如果使用本模组的，那么这两个值分别是120和170。\n    itemTexture是这个物品的图标，是一个ModelResourceLocation，可以使用本模组的或自定义，如果使用本模组的，可以通过GUIBackGroundRender.DREAM_NOTES_BOOK_DYEDREAM_WORLD来调用（还有其他几个材质的，分别是DREAM_NOTES_BOOK_OVERWORLD，DREAM_NOTES_BOOK_LAMP_SHADOW_WORLD和DREAM_NOTES_BOOK_WIND_JOURNEY_WORLD），如果需要自定义，那么应自备物品图标png文件，并放入textures\\item中，同时在models\\item中填写模型文件，假设这个名为\"pasterdream_addon_dream_notes_book_texture\"，和一般的物品类似，新建pasterdream_addon_dream_notes_book_texture.json并填写以下内容即可：\n{\n  \"parent\": \"item/generated\",\n  \"textures\":\n  {\n    \"layer0\": \"pasterdreamaddon:item/pasterdream_addon_dream_notes_book_texture\"\n  }\n}\n    在registerDreamNotesBookContentRelation中绑定时，最后一项填写new ModelResourceLocation(PasterDreamMod.MOD_ID, \"dream_notes_book_wind_journey_world\", \"inventory\")或者将其声明为ModelResourceLocation变量并调用即可。\n    color是文字颜色，是ARGB8888格式，一般写成16进制格式，写成0xXXXXXXXX，这8个X按照每2个分成4部分，分别表示：不透明度、红、绿、蓝，根据纹理背景决定即可。\n    推荐添加至创造模式物品栏，方便调试也方便玩家阅览内容，在添加创造模式物品栏的代码中（例如ModCreativeModeTabs）添加output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT(\"PasterDreamAddon:笔记内容名称\"));\n    总结一下，在FMLCommonSetupEvent中调用DreamNotesBookRegistry.register(\"内容名称\", Component.translatable(\"标题翻译键\"), \"作者名称\", Component.translatable(\"内容翻译键\"), GUI纹理路径, GUI宽度, GUI高度, 文字显示起始X坐标, 文字显示起始Y坐标, 文字显示终止X坐标, 文字显示终止Y坐标, 物品模型, 颜色);，在Datagen中编写标题和内容的翻译，在创造模式物品栏中添加即可，祝您开发顺利。");
        add("book.pasterdream.content.花园解密:迷梦冶梦莲", "西北的风吹过叮铃作响\n\n东北的云彩遮住了暖阳\n\n西南的光芒婉流明夕阳\n\n东南的亭亭小伞生其旁\n\n四周的矮草依花微风岸\n\n得以迷醉那冶梦的花莲");
        add("book.pasterdream.content.花园解密:凛冽雪绒花", "北方的大树抵挡了刺骨寒风\n\n南方的花朵染上了梦的颜色\n\n西方的灯笼代替了落日暖阳\n\n东方的草从见证了时间流淌\n\n这座花园的园丁们\n\n是由雪组成的小人\n\n和会飞的蓝色精灵");
        add("book.pasterdream.content.花园解密:九尾狐", "朝霞如梦似幻，熠熠生辉\n\n漂浮着的水晶花盆，高大的莲花燃烧着灵魂色的火焰\n\n浮空岛屿之上的水池中央\n\n一座九尾狐狸的雕像\n\n满足她的味蕾\n\n即可完成你许下的小小愿望\n\n发出金色光芒的甜蜜浆果\n\n许愿清单：\n\n给金色的苹果注入魔力\n\n让桶里充满融梦的涌泉\n\n用一堆梦境的硬币换取宝箱\n\n给她能映射梦的水晶或金色的铃兰花\n\n将正在睡觉的金色狐狸的梦\n\n变为现实...\n\n她会给予你一样特殊的纪念品");
        add("book.pasterdream.content.染梦游记 其一", "在这个世界中，有一种粉色的晶芽生长在温暖区域的地下。\n那些像史莱姆一样的生物天生具有保护色，或许可以通过它们发出的声音找到它们。\n在这里的汪洋之中也潜藏着秘宝。\n在我们的世界中，有一些宝箱都是藏起来的，这里可能也一样？\n这里也有种奇怪的花坛，会为能够解开谜题之人奉上独特的鲜花。\n有些地下洞穴里可能藏着能增加自身生命力的水晶。\n在这里，有些地方流淌着特殊的泉水。\n世界树的内部也藏着宝藏......\n运气是在这里找到宝物的关键。\n我见到的这一切，都有种似曾相识的感觉......就像经历过......这一切的存在。\nBy 琴雨梦");
        add("book.pasterdream.content.染梦游记 其二", "这里的地下浅层会有一种可以食用的矿石，还是甜味的。\n除了这种甜甜的矿石之外，地下也能发现稀有的钛金属，不过因为颜色和环境相近，我们不太容易发现它。\n地下生长的粉色石英矿物是一种不错的建筑材料！\n染梦的树叶上生长着一种果实，我们可以把这种果实做成蛋糕，吃起来味道一定很不错！\n这里有一些本土的作物，想要催熟它们需要特定的软泥肥料。\n把染梦粉尘与骨粉一同研磨可以制作出一种特殊的染料。\n某个地下研究所里有一台可以积蓄染梦粉尘的仪器。\nBy 琴雨梦");
        add("book.pasterdream.content.染梦游记 其三", "在这里探险让我感觉很轻松，即使什么都不做，也可以让我的心情好一些。\n经过这么久的探险，被我发现的群系也不少了，看景观的话，温带和寒带都有。\n但是，这里的冰冷海洋下竟然会生长出珊瑚！\n而且，这里好像会时不时有列车经过，不清楚是否是自己的幻觉......\n我还在一些遗迹的箱子里翻到了几张好听的唱片~\n我发现融梦水晶箱的振动次数越多，里面的宝藏也相对越好欸。\n有些的教堂显得与众不同，建筑的一半像是被什么侵入了一样......\nBy 琴雨梦");
        add("book.pasterdream.content.染梦教堂 其一", "记录给自己与后来者。\n我在这个所谓的“世界”里，竟然发现一座悬浮在天上的巨大建筑。 我确定我没有看错！如果未来的你也能看到这本书的话......\n我下意识地认为我正处于一场梦境之中，但我现在所见所闻的一切是那么的真实......\n当我想办法登上这座大建筑后，我看清了它的样子。这看起来应该是一座教堂？ 但里面却空无一人。（不过也不会有人住在天上吧）\n蜡烛看上去已经燃烧了许久，仿佛永不熄灭。 阳光透过教堂正对门的玻璃，照耀着教堂内的一切。\n在教堂的祭祀台之上有一块正在浮动着的美丽水晶，它在向四周发出光芒......（当时我盯了它很久）\n我慢慢靠近这块水晶，突然间，它似乎感应到了我的存在，自己落到了地上。（这种好东西我当然是要拿走了，但也许你来的时候可能会再生？也许吧......）\n我把这块水晶捧在手心里，它好像在对我说话...那虚无缥缈的声音，听起来应该是别的灵魂发自内心的祈愿。\n我面向教堂背后的大窗，闭眼许下了自己的愿望......\n哦，对了，还要再写一句，我要把这本书留在这个地方！\n至于为什么嘛......因为我想让自己的故事留在这个世界上而已啦~\nBy 琴雨梦");
        add("book.pasterdream.content.染梦教堂 其二", "就在前天，琴雨梦突然告诉了我一个秘密，她说她进到了一个类似梦境的地方，还反复强调这是真实存在的？\n我起初并不相信她的话，但是她一直坚持要我跟随她去那个神奇的地方冒险，一起去那个什么所谓的教堂许愿。\n我拒绝了她好几次，但最后实在是拗不过她，我还是决定跟随她一起去看看她口中的世界和那座神奇的教堂到底是什么样的。\n当我穿过了那个令人震惊的裂隙，看到了眼前的景色后，我还掐了下我的胳膊......这一切好像还真是真的？\n我跟随着她来到那座教堂，蓝色的灵魂灯笼和高耸的塔楼，哥特风格的西式建筑，很难想象这个地方为什么会有人类文明的痕迹。\n我决定登上承载这座教堂的浮空岛屿去一探究竟。\n这座教堂里面漂浮着点燃着的蜡烛，座椅旁摆放着闪烁着光芒的灯笼，这使得这座教堂更加充满了神秘感与不真实感。我不确定我的这些文字是否有意义，但我还是想要记录下我现在的所见所闻。\n我在教堂里漫步了许久，感受着这里的氛围。这里充满了奇妙的气息，也让人感觉到一种无法形容的神秘感。我无法解释为什么会有这种感觉，但我知道这里有着某种神奇的力量。\n因为......琴雨梦竟然慢慢飘浮起来了。\n看着我诧异的眼神，她好像没有惊讶，很自然地接受了自己飘浮起来的事实。她指着教堂书桌上悬浮着的神秘水晶，跟我说：“不知道为什么，我感觉是它就是让我能飞起来的原因。”\n琴雨梦非常熟练地轻抚水晶，然后这颗水晶就自然地跟随着她的手心飘浮。\n夜岚：“琴雨梦......”\n琴雨梦：“嗯？什么事？”\n夜岚：“啊，没什么......”\n我决定把我在这里的所有经历写成一本书，把它留在教堂里，供以后来到这个地方的人阅读。\nBy 夜岚");
        add("book.pasterdream.content.染梦教堂 其三", "“这里...是否有点太过于美好了? 依诺”\n\n自从进入这里不久后，我突然萌生出这样的想法，伊诺说我想太多了，确实，这里没有怪物，处处充满了生机和活力。\n\n但我尝试了一下占卜，三次的结果各不相同，也就是说我所看到的景象并非是真正的景象。\n\n“有光的地方就会有黑暗，光越强，黑暗便越浓厚”\n\n我在梦境不停的探索了半年之后，直到我发现了这个……半边染黑的教堂。\n\n这突兀的黑色，出现在了这完全不应该出现的地方，我努力抑制住颤抖的身体，跨进了这座教堂。\n\nBy 卡莱");
        add("book.pasterdream.content.染梦水晶球", "我小时候也曾幻想过......住在一个属于自己的小天地玻璃球里！\n有一个属于自己的小生态超酷的欸，再被包裹起来的话那就再好不过了~\n我自己不怎么喜欢那些特别大的空旷房子，在我看来，有一份属于自己的小世界比大房子有趣多了~\n就这样躺在这里面，被云朵衬托着，透过粉红色的玻璃看向透过云彩的太阳......\n嗯.......嗯~ 就这样......睡一觉吧.......\n祝我好梦~\nBy 琴雨梦");
        add("book.pasterdream.content.祈愿树", "这是一棵祈愿树！\n嘛......至于为什么我说它是祈愿树......其实我也不清楚。\n一来到这个地方，我似乎本就知道该如何称呼这里的一草一木。\n我可以非常自然地叫出它们的名字，这只需要遵从我的内心，即使这本就没有任何的正确答案。\n现在，我有了不同的愿望，除了以往生日时我会许下的愿望......我现在还想知道关于这个梦境的真相。\n不过在我许下这个愿望后，这棵树没有回应，我也感觉不到它产生了什么回应。\n所以，如果后来的你对于我的问题有答案的话......请续写这份笔记。\nBy 琴雨梦");
        add("book.pasterdream.content.温暖的“寒风”", "冰冷还是温暖？\n这个漂亮的小亭子的作用似乎并不是为了避暑。\n在这里的气温一直很舒适，说是这附近是冻洋，也只是因为周围存在着冰川，看起来觉得“冷”而已。\n毕竟那些冰川下面竟然生长着热带的珊瑚，还会有可爱的海豚。怎么看也不算是严格意义上的冻洋吧。\n嗯......这也许无法用科学理论来解释？\n或者说，这里其实是某种地理奇观？\n这些亭子的颜色也有区别，在温带的棚顶会是粉色，寒带的则是蓝色。\n（蛮合乎地域风情~）\n温带的地底和寒带的也有所不同，还会生成不同的水晶构造，或许这些水晶会对我们有用...\nBy 琴雨梦");
        add("book.pasterdream.content.粉顶蘑菇屋", "这是我和夜岚发现的一个生态群系截然不同的地方。\n虽然我已经见过全是蘑菇的岛屿，但是见到这种全是粉蘑菇的平原还是有些震撼。\n“这么巨大的蘑菇甚至可以当一座房子！”\n然后......我们真的找了一个巨大的蘑菇房。\n这很不可思议，我们站在那个精致的蘑菇房子面前呆住了好几分钟。\n夜岚:“所以以前真的有人住在蘑菇里啊?”\n琴雨梦:“因为是做梦嘛，这些都属于正常现象的啦,哈哈哈......” 我这样调侃着。\n我自己竟然会这样奇怪的接受这个事实......大脑过载了也就干脆不去想好了！\n之后就是探寻这附近的生态环境，然后在这个蘑菇屋里过了一夜。\n还是一样，我会把这本书留在这里。\n如果后来的你发现了，请不要把这个蘑菇房子吃了！我以后还会回来玩呢，也许？\nBy 琴雨梦");
        add("book.pasterdream.content.融梦涌泉井", "这都被你看到啦？那就先从头说起吧。\n在我第一次散步来到这里的时候，我发现这里的植被生长状况与其他地方有所不同。\n在比较小的一块地方有许多不同的植被类型，而且植被密度也是相对比较高的。\n和其他地方相对比的话确实比较可疑，我便低下头来研究这边的土质。\n从表层看起来，这里的土层并无什么差别，但越向下挖会发现，捧一把泥土在手里，会发现有些许发光的细小星星点从指缝里流下去。\n然后就当然是去摇人来了，我回家叫上了夜岚，顺便拿了两把趁手的铲子和她珍藏的钛金镐。\n之后，我们回到那里，开始向下挖掘，随着我们越挖越深，我们在土壤里发现越来越多细小的光点。\n我们搭好梯子，一步步下到洞底，我们看到，在一个植被茂盛的洞穴里，有一股发光的泉水在喷涌，也许这个发光的泉水就是支撑这些地下植被生命的太阳。\n或许这泉水可以一直发光来为植物提供光照？不过按照科学理论来说，不可能存在可以无限发光的东西......我们用手头的材料修了这口“井”。离开的时候，我顺手带了一瓶泉水回到了家，想进行一些实验。希望我的研究能够顺利。\n至于剩下的这些，就交给后来的你了吧。\n(取水不忘挖井人，你可要记得感谢我哦~欸嘿嘿~)\nBy 琴雨梦");
        add("book.pasterdream.content.浮空岛日记", "你说，这些浮在天空上的建筑和岛屿 是怎么产生的呢？\n是大自然的鬼斧神工？还是有比我们更先来到这里的人呢所为呢......\n那按道理，这些石头泥土也不应该会飞起来啊，大自然怎么能创造出人类文明的痕迹，而人类又怎么能脱离世界的规律来构造这一切。\n哎呀，一去认真想这些就会让我长这么大构建的世界观全部崩塌。\n不过也没必要去纠结这些就是啦~也许和我的直觉想的一样，这一切只是一场梦而已。\n幻想可是无边无际的，让这座岛屿开口说话都可以！你说对吧~\n但总会有一天，我要彻底了解这一切......\n让这趟旅程成为我此生无法忘却的回忆。\nBy 琴雨梦");
        add("book.pasterdream.content.落叶归根 裂荚归冠", "蓬勃生长的大树，枝叶作为你的孩子，在凋零时会落在你的根旁。\n其他植物在诞生之初便会抛弃自己的种荚，抛弃它们母亲留给他们最后的躯壳。\n而你，用自己最顶端的枝叶把种荚顶上高空。\n用根系和枝叶给予它营养，让它享受最顶端的光芒，笼罩这个世界最顶端的光芒。\n即使种荚早已完成它的使命，仍将这些养分用于构建自己的梦想。\n我也将自己的祈愿给予你，作为初次的见面礼。\nBy 琴雨梦");
        add("book.pasterdream.content.梦境漂泊", "在梦境的海洋，我漂泊无依。\n像是孤独的旅人，寻觅着未知的奇迹。\n星空作伴，大地为席。\n在这安眠之境，我与梦共舞。\n脚下是迷雾，前方是未知。\n我用心感受，每一段旅程的韵律。\n风中有歌声，雨中有故事。\n每一个瞬间，都是梦境的奇迹。\n我是漂泊的旅人，无畏也无惧。\n在梦境的深处，找寻生命的真谛。\n只为\n在这无尽的旅途，将我成为自己的传奇。\nBy 琴雨梦");
        add("book.pasterdream.content.气泡生态球", "正当我划着木舟横渡海洋，寻找漂浮在空中的那些遗迹时，突然，一个神奇的球体吸引了我的注意力。\n这个球体漂浮在冰冷的海水中，透明的薄膜后依稀可见游鱼的身影。我潜入水中，小心翼翼地伸出手，试图触摸这层薄膜，却在感受到微弱的阻力后直接穿了过去。\n令人难以置信的是，这个球体的内部十分温暖，与刚刚在球体外面把我浸了个透心凉的冻洋海水形成了鲜明的对比。我蹲下伸手穿过厚厚的云块摸索，果然底下的海水也很暖和。这里的热带鱼不是什么标本，刚刚在船上见到的鲜艳颜色也并非是我在长途跋涉后累得神志不清产生的错觉。\n仔细确认一下这里的内部环境，我不由得回忆起了曾经见过的生态球。这个奇妙的气泡球到底是由超越人类的存在所建造，还是这个不可思议的世界自然形成的？\n我不得而知。\n但至少这里缓慢游动的小鱼和这个世界的其他生物一样对我没有什么敌意，我可以在游回我的船上之前再坐在这里休息片刻。\n这么想着的我随意地拿木棍轻轻戳了戳边上的气泡壁，气泡壁表面却泛起了剧烈的波浪，险些崩裂，让我心悸了好一阵。果然下次还是小心为妙，这种脆弱的泡泡大概只能在平静的水域里存在，稍受扰动就有可能碎裂开来，让外面的海水倒灌而入。\n我在笔记空出的标题上写下我给这里取的名字——“气泡生态球”。\n毕竟它就像小型的生态球一样美丽且易碎，还是少惊扰为好。\nBy 千夜");
        add("book.pasterdream.content.染梦实验室", "这是一栋残破的建筑，看来我在这梦境之中发现了前人留下的痕迹。\n应该不止我一人发现了这个奇怪又充满粉色的地方，但是我大声呼喊却没人回应，这里已经人去楼空。\n昏暗的灯光笼罩着遗迹内部的空间，遗迹中央摆放了一台梯形的未知仪器。\n我确信这个地方是一位更早到这里的前辈的实验室，而这中心摆放的是他最终的研究成果。\n令人遗憾的是我没能发现前辈匆忙离开这里的原因。\n现在，我应该带着这个损坏的仪器，延续他的研究。\nBy 彗星");
        add("book.pasterdream.content.来往于梦", "致后来者：\n这份文字记录了我在这里的所见所闻和一些推测，我不知道这个世界究竟是怎样的。\n但（被划去的文字）“奇迹”，我只能用这样一个词语来形容眼前的世界。\n无处不在的粉色，永明的天空，黑暗中也不会滋生令人厌恶的邪恶，如梦境一般，不，或许它就是梦境\n虽说在唯物主义教育下长大，可面对这幅完全无法以现有知识解释的景象，我不由自主地发出了疑问：\n这世界上，会不会真的有什么神明一类的存在。（这样的神明又是唯心的“神”，还是更高级的“存在”）\n不过，这里也存在不少疑点，任何事物都具有相对性。\n既然有如此美好的世界，那有没有一个与之截然相反的地方；而且，这个世界无处不有着一种古怪的虚幻感，明明一切都那么真实却处处有种疏离感。（大概类似于清明梦？）\n这一奇迹究竟由谁创造，又由谁维持。（监管？）\n§m-大片被划去的文字-\n或许有人曾在此停留（定居？），毕竟有烟熏炉、花盆、书架这些有着生活气息的物件出现。\n不过屋主大概已经离开很久了（奇怪的是物品大多崭新如初，窗台上的花甚至还活着）\n至于这方世界，暂且称其为“梦境”。\n更多结论仍需进一步的探索才能验明，或许我需要一种更稳定的方式来往于现实和“梦境”间。\nBy 流华");
        add("book.pasterdream.content.染梦世界树", "偶然远眺风景时，我在视野的尽头发现了这棵参天巨树。走到树底仰望，树干高耸入云，甚至难以望见枝叶。\n挖掘根部周围的泥土，可以发现其根茎延伸不算很长，似乎难以解释其异常的高度。\n蜿蜒的树干形成了一条天然的阶梯，可以让人较为轻松地攀爬而上，但有些地方仍要自行徒手攀登。爬到枝干的顶端后，能看到其内部存在一个小空洞。\n闪烁着光芒的白色涌泉从侧面的树干中流出，无论哪里都显得尤为反常——但是相比于这个世界本身的神奇，这点景象已经不足以使我感到惊奇。\n回到树下后，我开始思考这棵树得以形成和正常生长的原因。对此，我做出了某种猜想：\n这个世界属于某种梦境，而其大气中存在的梦境能量的分布是不均匀的。\n就像大气现象导致天候变化一样，这种融于梦境大气中的能量——融梦能量的分布应该也是不均匀的。\n融梦能量沉积较多的区域，能量沉积下来化作了实体，形成了大陆、空岛、树木等这个世界的一切。而那些融梦能量小范围大量聚集的结果，就是融梦水晶箱——这也解释了为什么打开箱子时会有融梦能量逸散而出。\n照这个理论进行推论，在这个梦境世界的中心——融梦能量最为富集的地方，理应会形成一种体积巨大的结构；而丰富的融梦能量会化为大量的融梦水晶箱积蓄其中。\n想到这里，我回头看向了参天巨木所在的方向。它有可能正是这个梦境世界的世界树，融梦能量的富集场所，有着大量融梦水晶箱的梦幻之地。\n我理应再次登上这棵树，对其内部结构一探究竟。之前的一无所获一定是我遗漏了什么......\nBy 极星");
        add("book.pasterdream.content.代达罗斯之翼与浮空岛", "代达罗斯之翼与浮空岛\n在我从那颗参天大树中悻然离开后不久，千夜追上了我，打断了我重新探索的计划\n”看到那些悬浮在半空的岛了吗？我想上去看看那里有没有什么宝藏。”我向千夜提议\n没想到千夜直接给我泼了桶冷水，”我看你是失了智。我们又不会飞，怎么上去呢？”\n”关于这点，我有个大胆的想法……”\n在千夜难以置信的目光中，我拿出了之前路上捡到的那些粉红色的鸡掉落的羽毛。之后，我把之前捡到的蜡烛重新熔成蜡，用这些蜡把羽毛粘在了一起，费了不少力做出了两对羽翼。”\n”所以你是想当伊卡洛斯。”\n”不，我觉得我会是代达罗斯。毕竟这里的太阳可不会融化这些蜡。而且不试试怎么知道呢？毕竟……”\n事情超乎寻常的顺利——虽然按照常理来说，人是没法用这种羽翼飞翔的；但不知为何，这里的风像是托着羽翼般，使我们能毫不费力地飞行。我们很轻松地就飞到了我之前只能抬头仰望的浮空岛上。\n抵达岛上后，千夜直接冲进了那间小屋中。我也紧随其后，在这里找了个地儿坐下来，和她交谈起来。\n\n”话说回来，这个世界也太超乎常理了吧”千夜把那对羽翼卸了下来，开始观察它有没有散架。\n\n”所以我说了，这个世界应该是属于某种梦境或者幻境吧\n\n毕竟我完全搞不懂这种岛屿是怎么安然无恙地悬浮在空中，在我们踩上去后又能纹丝不动的。”\n\n”这个房子也是，虽然一看就像是人为制造的，但根本没有人类生活过的迹象。另外，我还是无法理解这玩意凭什么能运作。”千夜抖了抖手上的羽翼说。\n”别较真了，梦里啥都有可能嘛。”\nBy 极星");
        add("book.pasterdream.content.星河果冻和浮空岛", "在我从那颗参天大树中悻然离开后不久，千夜追上了我，打断了我接下来的探索计划，我认为现在我还是把我的探索计划和她说明了比较好......\n“看到那些悬浮在半空的岛了吗？我想上去看看那里有没有什么宝藏。”我向千夜提议\n没想到千夜直接给我泼了桶冷水，“我看你是失了智。我们又不会飞，怎么上去呢？”\n“关于这点，我有个大胆的想法……”\n在千夜难以置信的目光中，我拿出了之前在这个世界中的一些建筑中搜寻得到的果冻状物品\n“这是什么东西？”\n“这是我从那些建筑中搜寻到的物品，吃了它似乎会‘嗖’地一下飞到天上去，所以......”\n  她很快明白了我的想法：“所以你是想当阿姆斯特朗？”\n  “不，我觉得我顶多是万户，这只是登上浮空岛而已。好了，我们来试试吧”\n事情超乎寻常的顺利——虽然按照常理来说，现实中并不存在这种只要吃掉就会让人高高飞起的果冻，不过幸亏有这东西，我们很轻松地就飞到了我之前只能抬头仰望的浮空岛上。\n-抵达岛上后，千夜直接冲进了那间小屋中。我也紧随其后，在这里找了个地儿坐下来，和她交谈起来。\n“话说回来，这个世界也太超乎常理了吧”千夜也坐了下来，开始整理被风吹乱的头发。\n“所以我说了，这个世界应该是属于某种梦境或者幻境吧，毕竟我完全搞不懂这种岛屿是怎么安然无恙地悬浮在空中，在我们踩上去后又能纹丝不动的。”\n“这个房子也是，虽然一看就像是人为制造的，但根本没有人类生活过的迹象。另外，我还是无法理解这种果冻凭什么吃了能让我们飞起来。”\n“别较真了，梦里啥都有可能嘛。”\nBy 极星");
        add("book.pasterdream.content.探求秘辛", "为了收集一些必要的资源，我在长途跋涉后来到了一片沙漠。\n基于我要寻找的资源相当罕见这一情况，我可能不得不在此停留一段时间，因此，我设法找到了位于沙漠边缘的一座村庄，并建造了这座供我居住的小屋。\n每天日出时出门探索，日落前回到这座临时居所中；晚上或是刮起沙暴无法出门的时候，则和村中的村民攀谈或者是在家里打发时间——这就是我在这片沙漠的日常。\n转眼间半年过去了，白天的探索没有任何进展，但我和村民间的关系却日渐熟络起来。\n大人们对我在夜晚保护他们不受怪物侵扰的行动感激不尽，孩童们则醉心于我分享的游历各方时的冒险故事——渐渐的，村民们开始向我透露一些从未给其他旅者讲述过的“秘辛”：\n“我们村的铁匠几乎从不出门，所以你应该也没怎么见过他。据说他曾是某个国家的御用工匠，他的家族专门为王室锻造各式各样的精兵利器；但在王国毁灭后，他的家族就此没落，最后流浪到我们这里定居了。他好像还不太能接受自己的处境，把工坊造的离我们村子有点远，我们平时也跟他交往不深，只有工作层面的交流......但如果是你的话，应该能和他打好关系吧......”\n村民们似乎想让我去开导孤僻的没落锻造贵族（虽然我觉得他们只是指望在我跟那位打好关系后，让他们能用更便宜的价格修理工具罢了）。于是我带着我珍藏多年的陈酿，造访了铁匠的工坊。\n......\n虽然中间经历了许多波折，但我们最终成为了挚友。在一次对饮中，我向年事已高的他表示，希望他家族的精湛锻造技术能流传下去；虽然我已经在与他的相处中对他的锻造技法有所了解，但我无论如何也无法独自还原出他的技术，像他那样锻造出能力优越的装备。\n他在长时间的沉思后，掏出纸笔，为我绘制了一张设计图，并告诉我：只有严格按照他的工坊的布局，才能最大程度地将大气中的某种......物质？引导并汇聚起来，从而锻造出超凡而新颖的工具。折服于他的慷慨与信任，我将我携带的几乎所有值钱的物件全部留下作为谢礼，并带着这份设计图离开了此处。\n不知不觉，好像已经在这片沙漠停留太久了啊......\n是时候踏上新的旅途了——等我下一次在某地停留时，就试着按照这份设计图来建造一座工坊吧。\nBy 极星");
        add("book.pasterdream.content.研究笔记:黑金属", "要说起这段研究的起因，得归溯于我在这片漆黑的梦境发现的一些古怪的金属。\n就在那些倒挂的灯笼，遗迹，或者是一些陶罐宝盒里，质地看起来就像铁一样，不过锈迹却不是那种褐色的层皮，而是很多黑色的小洞的那种奇怪金属。\n但是用一般的熔炉去熔炼它不会有任何的反应，我索性把它丢进了岩浆，当然是不出意外地直接熔化，和岩浆合而为一了。\n我和夜岚为了研究这个金属，就在这附近去找了一个破旧的梯形房子。\n在一次巧合中，二楼滴落下来的流体阴影滴进了装有这些金属的罐子，然后这些阴影好像有意识一样，自己填补进了这些锈蚀的洞里。“这些还不够”，“看来这些金属可能只是一种载体，真正缺少的是那些影子”。\n随着不断地研究，我们得出了以下的配方：“高温，流动的阴影，以及那种影子怪物的身体”。\n我们从这里醒来，带上了一些材料设备，再一次躺在了前往灯影之下的床上......\n随着机器的轰鸣声，以及融合，熔化，凝结......最终一块黑色的金属应声落在了地上......\nBy 琴雨梦");
        add("book.pasterdream.content.灯影游记 其二", "我很好奇这些真菌是怎么长在这个地方的？\n这里的天空没有光，没有熟悉的太阳，没有任何一种可靠的能量来源，甚至这里的土壤也不见得能提供养料。\n但这些真菌却能把菌丝扎进这些黑色的石头里，长成比人还大的蘑菇树，甚至还长出可以发光的果实。\n同时，这些植物还孕育了一些可能是本地的小动物？这些甲虫应该是以这里的真菌为食，成群结队地在这个地方生活。\n面对黑暗的它们，似乎进化出了白色的眼睛，也许这是能让它们适应黑暗的原因。\n这些生命组合起来所谓的“生态系统”......只能说完全不能用现代科学去解释。\n不过好像从那个染上粉色的世界到这个漆黑且遍地都是怪物的地方早就不是所谓科学能解释的了，完全就是主观构想出来的虚幻产物。\n嗐，我也不是做科研的人。不过嘛，这些蘑菇......会不会很好吃？\n我觉得我应该去研究一些食谱。（还有去找一个试验品！）\nBy 琴雨梦");
        add("book.pasterdream.content.黑暗之地", "不不不，这里......我很难看清这里的一切，我感觉黑色的迷雾就在我的眼睛上！\n琴雨梦：“夜岚！你在哪,听得见吗？”\n这要说到前几天，我们在那个粉红色的梦境里探索那座被侵染的教堂。我在教堂的漂浮书卷中找到了一位名为卡莱的前辈留下的笔记，末尾那个坐标指向了下界的上层。相信在看这本书的你们也经历过那阵令人不适的低语，那会儿夜岚便领着我穿过了传送门，去寻那盏该死的灯笼。\n夜岚：“我先带上装备去看看那个暮影之笼，你在这等我一会，如果那边没问题我再叫你过来。”\n琴雨梦：“嗯。”\n然后就在夜岚触摸灯笼的瞬间，感觉有超多黑色的迷雾漫了过来，然后我什么都看不到了......\n直到迷雾散去，她已不在灯的附近。\n卡莱说得对，它真的会蛊惑人心......我脑子里全是那东西的低语，身体根本不受控制。最后那段记忆，就是我的手死死按在了那冰冷的灯罩上。之后......我就什么都不记得了，\n不过看现在这种情况，我应该是摸到灯笼之后晕倒在那个黑色的床上了，然后就成了现在这个鬼样子。\n琴雨梦：“喂！能听到我说话吗！你在......啊啊啊啊.....这什......唔唔！（放开我！）”\n夜岚：“嘘！别说话！”\n夜岚（压低声音）：“是我。”\n琴雨梦：“你干什么！很痛的，我感觉我从地上摔下来了！”\n夜岚：“你没看见外面那堆鬼东西吗？想活命就小心点。”\n琴雨梦（小声嘀咕）：“我寻思我也看不见啊。”\n夜岚：“这里是我盖的庇护所，害，其实是借别人的坟用用。”\n琴雨梦：“什......”\n夜岚：“嘘！”\n琴雨梦：“唔......”\n夜岚：“话说你竟然会跟过来，我也没给你发信号。”\n琴雨梦：“这不担心嘛，就接着跟过来了。”\n夜岚：“接着？已经过去差不多3天了。”\n琴雨梦：“啊？”\n然后，夜岚向我讲述了在这里的故事......包括这个“坟头”。\n琴雨梦：“这样啊......”\n不过想了想......最后决定先不记录这些。\n很多模糊的东西......我需要去认真的求证一下。\nBy 琴雨梦");
        add("book.pasterdream.content.阴影小木屋", "经过长时间的探索和比对，我对这个黑暗的世界的各环境做了大致的记录。\n这个世界的绝大多数地域都十分危险。\n被浓厚阴影覆盖的山地，流淌与凝结的阴影不断催生着可怖的、仅仅望见就会失神的怪物。\n堆满遗骨和断壁残垣的平原，无尽的怨魂发出凄厉的嚎叫，完全无法久留。\n唯独这里，这片长满了真菌“树”的“森林”是相对比较安全的。\n真菌的“树叶”上生长着数个发光体，在这个漆黑的世界里，这样的光芒似乎能驱散暗影，抑制阴影生物的产生。\n这里的原住民是一种温顺的甲虫，只要不向其发起攻击，就能平安无事。\n在这片森林中漫步时，我偶然发现了一座房子。\n有趣的是，这间小屋的墙壁似乎是由真菌树的菌柄做成的。\n我试着将菌柄表面附着的一层阴影剥离开后，发现其外表与房屋的墙壁别无二致——是一种类似于......去皮原木的材质？\n这间房屋似乎已经被废置很久了，人类的生活痕迹只剩下了寥寥数片地毯，连身为原住民的甲虫都在这间小屋里安家了，我不得不赶走它们才能细致观察房屋内部。\n就在我检查房屋的地板时，我发现了数片附着在地板上的不自然的阴影。\n我站在远处，用一只脚轻踩这片阴影。\n阴影底下赫然伸出一只由阴影构成的手，抓住了我的脚，试图把我拉进地下。我拼命挣扎，终于把脚从暗影之手中拔了出来，但是，当它的手心触碰到我的脚踝时，我感觉我的理智似乎被剥离了一层。\n在我试图清扫掉另一片阴影时，它在消尽前改变了形状，变成了我在阴影覆盖的山地见到的那种手型怪物，我费了一番功夫才将其斩碎。\n我有预感，我会在其他地方再次见到这种“陷阱”。\nBy 极星");
        add("book.pasterdream.content.阴影地下工作室", "虽然这片森林相对安全，但为了完全根绝阴影生物的打扰，我决定在地下修建一个工作场所。\n得益于从主世界带来的部分物资，我至少能在卧室里建造一个能冲澡的小房间——毕竟在大片的阴影中游过泳后总会想好好冲个身子。\n我把这个世界各处能找的植物中的大部分栽进了花坛里。虽然多少有点怪异，但观赏这些植物总能略微放松一下身心。\n在这些灰黑色的植物中，一种被我称作“阴影蘑菇”的植物尤为特殊——与其他植物不同，这种蘑菇似乎有着和树苗类似的性质：只要有充足的生长空间，它就能长成森林中随处可见的那种菌木。看来这片森林的形成似乎与这种蘑菇息息相关了。\n不过值得注意的是，这种蘑菇的生长必须要有充足的空间。\n之前我把它种在花坛里的时候，它突然疯长起来，把我的花坛弄得一塌糊涂。\n为了防止这种情况再次发生，我将其移出了花坛，并限制了它的生长空间。（旁边那棵已经长好的菌木则被我留了下来，供我继续研究）\n在不断地捣鼓这个世界的某些材料后，我总算是复现了我来到这个世界所用的那张黑色的长床。看样子，只要在上面躺下就能让我回到原来的世界吧。\n但这个世界中仍有许多未知等着我去探索；在将这一切究明之前，我不打算轻易回到主世界。\n啊，好像有一只甲虫爬进来了。虽然这种甲虫并没有什么攻击性，但我还是不太喜欢有生物打扰，该去赶走它了。\nBy 极星");
        add("book.pasterdream.content.-被阴影浸染的字迹潦草的笔记-", "难以置信，我竟然能从那种恐怖的生物的手中逃出来。\n那是一对掌心长着一只白色眼睛的黑色大手，仅仅是与其对视，就让我浑身战栗，根本无法涌起与其战斗的欲望。\n即便如此，我也强行鼓起勇气与其对峙，却发现它可怕的力量远超我的想象。\n仅仅是对其做出最轻微的攻击，都会引起强烈的反击。\n由于战斗时实在过于慌张，我甚至没有分清楚我究竟攻击的是哪只手。\n然而，我清楚地记得，攻击其中一只手会让其长出一根尖刺，并使其疯狂的挥动那根尖刺。\n而攻击另一只手会让其释放出一座看起来蕴含了极大能量的尖塔，看起来似乎会发生剧烈的爆炸。\n在见到这种可怕的反击后，我立即仓皇逃窜，逃到最后连我也忘记了自己是如何逃离的。\n后来者啊，希望我这丢人的经历能为你的探索做出一些贡献。\n除非你有能足以踏遍这个阴森可怕的世界而不受任何威胁的力量，否则请你千万别前往那“眼睛”的所在之处。\n-作者名字模糊不清-");
        add("book.pasterdream.content.关于黑暗之手的随笔", "在对这个世界所有群系的探索大致结束后，我对这个世界的几乎所有结构都有了相当程度上的了解......\n除了那被四根“手指”包裹的“眼睛”......\n那中心发光的传送门似乎通向一个结构简单、但并不安全的空间。\n出于保险起见，我设法将一个装有图像传输装置的坚固机器送进了这个传送门，让它代替我进行勘察。\n穿过传送门后，它缓缓落在了一个“竞技场”的中心。\n图像开始变得模糊不清起来，地面大幅震动，随即两只黑色的巨手从空气中浮现。\n那两只手在发现我投入传送门的这个“异物”后，立即一前一后地向它发起了猛攻。\n视野中，一只手突然冲撞了过来，将机器掀了个底朝天，随即在一阵黑暗与暴风中，机器的视野彻底暗转并失去了与我的联系。\n不行，这样的话变数还是太大了——仅凭获取到的这点信息的话，根本没有任何胜算。\n即便如此，我还是姑且留下这份笔记。虽然几乎没有什么有价值的信息，但应该还是能对给后来者起到一些作用的。\nBy 极星");
        add("book.pasterdream.content.侵染教堂-黑面", "我很庆幸还能活着回来，离开那永无光明的地方。但很快我就发现，那股阴森冷暗的气息依旧没有消失，直到一道裂缝出现空中，从中逐渐渗出一股黑色粘稠的液体。\n它，跟过来了……\n那股液体低落在地面，随后便开始污染周围，逐渐侵蚀教堂。我一边用泉水减缓蔓延的速度，一边回想那本书中的知识。 我不太确定书中的方法是否可以行，但目前只能赌一把了。我在教堂里面找到了所需的蜡烛，线，但还缺少一个关键物品：容器。\n我放弃了教堂中那几个被侵蚀的箱子，看着那几个破了大洞的灯笼祈祷可以用。灯笼在丢进符文中间，逐渐开始升空，黑液被一股巨大的拉力吸引，不断融入灯笼之中，直到教堂停止了侵染。灯芯忽然点燃，发出渗人的白光。\n我连忙接住空中掉落的灯笼，一股冰冷刺骨的寒意从怀中传来，低语萦绕着我，传入我的大脑。\nBy 卡莱");
        add("book.pasterdream.content.侵染教堂-亮面", "竟然是一座教堂……\n我和伊诺在并没有贸然进入，虽然之前的建筑并未有什么危险，但绕圈飞行两周半后我们才决定进入。\n\"小姐，这个教堂有点奇怪，和教会要求的并不相同，还有是内饰的东西也差很多\"\n我踹了一脚讲台，毕竟教堂的讲台下是没有箱子的。祷告日会持续大半天，讲台下的箱子里面会放一些食物的。除此之外就是，太小了，甚至没有领地内村庄的教堂大，而且里面几乎没有太多有关祷告的物件。\n在我准备离开时，伊诺发现了异常，讲台下面多了一个白金色的箱子。 我确定之前讲台之下没有任何东西，但这个箱子的出现让我感到了一丝紧张。在我犹豫是否要打开这个箱子时，熟悉的风铃声再次响起，从那个箱子处传来……\nBy 卡莱");
        add("book.pasterdream.content.暮影之笼", "尽管我把它藏在这里，我还是担心会被发现，所以留下这份书信，警告后来的旅行者，不要轻易的靠近这里，里面的东西并不是世间力量可以对抗的，你会被污染，蛊惑，借此引发祸端，将整个世界拖入黑暗。\n当然，我也并不会担心，如果你拥有超越于我的能力，便可以去探索被我封存的东西，甚至可以去完成我未曾触及之物。\nBy 卡莱");
        add("book.pasterdream.content.与黑色双手的决战和败北", "我写下这张笔记，以警示那些试图挑战它的旅者：「它」远比想象中的更为强大。\n关于那个闪光的传送门彼端的世界究竟为何，探索灯影的其他寻梦者们和我的好友留下的笔记给了我答案：门后是一个闭锁的小型世界，里面有形似一双手的恐怖阴影生物。\n为了战胜它，我用粉红梦境中能获得的最强韧的金属制作了盔甲和武器，并做好了一切我认为能做的准备后，跳入了传送门。\n然而，即使我认为我已经做好了充足的准备，这场战斗却仍然艰苦至极。\n无穷无尽的不知从何处涌出的暗影怪物、黑色双手的猛烈进攻无时无刻不在摧残着我的精神、意志和体力。\n即便如此，我也尽我所能地对他发动攻击，而换来的则是更为强烈的反击——不管怎么说，我还是认为我有对它造成有效伤害。\n就在我认为这样下去说不定有胜算的时候，可怕的事情发生了。\n一瞬间我的视野陷入了黑暗，一片漆黑之中好似无穷无尽的阴影生物蠕动而出，而我的攻击甚至无法穿透围绕那双手的厚厚一层阴影。\n被怪物包围，药水用尽、身受重创的我，在脑海中强烈祈求着这只是一场梦，拿起苍白骨针猛的往自己手臂上扎去。\n一阵刺痛之后，我的视野被强光夺去。眨眼间，我已身处熟悉的床边。但满身的冷汗、布满裂纹的盔甲和空空如也的药水瓶告诉我，刚才的经历并不是简单的一场梦。\n果然，要想击败这恐怖的生物，需要能够抑制阴影，或是能融入阴影、从内部破坏它的武器。\n果然是我还遗漏了什么要点吗。或许，等待其他援助者前来，和他们一同面对它才是更好的选择。\n在那之前，我得先想办法找到更为强大的武器......\nBy 千夜");
        add("book.pasterdream.content.破风的骑士", "古希腊风格建筑的残垣断壁，围绕成一个圆环的神庙，在这中心供奉着一个并不起眼的祭坛。\n是用来求雨？还是期望能预测风的方向？还是供奉自己的守护神？\n我靠近处于中心的那个半砖状祭坛蹲下，发现在祭坛中间留有一个黑色的洞，里面竖放着一张圈起的纸条。\n上面画着一些潦草的图案，还有数字标号的顺序。一块蓝色的水晶，还有3个类似金属制成的盔甲。\n以及这张纸最下面的一行写着\n“破风的骑士”将驱逐其守护之物以外的一切......\nBy 琴雨梦");
        add("book.pasterdream.content.清晨的新风", "这是新的一天的开始，也是新的风向的到来。\n伴随着风铃的声音，飘落的羽毛，以及从东方升起的旭光。\n风是属于这里的恩赐，顺应风的方向将是我们探索的助力，而逆风将可能使我们寸步难行。\n风不仅指引我们旅途的方向，也是一种特殊的钥匙。\n风只会在新的一天到来的时候再度吹拂，无法通过任何方式预言。\n如果错过了清晨风的指引，或是在这个梦境之外无法用身体去感受风向，那么，风向标可以跨越现实与梦来告诉你当今的风向。\n以及在这里散落的一些宝藏，将可以改变风对我们的影响。\n还有许多风的秘密......也许风会在某时某刻向我们揭晓。\nBy 琴雨梦");

        add("book.pasterdream.花园解密-迷梦冶梦莲_第1页", "         花园解密\n\n西北的风吹过叮铃作响\n\n东北的云彩遮住了暖阳\n\n西南的光芒婉流明夕阳\n\n东南的亭亭小伞生其旁\n\n四周的矮草依花微风岸\n\n得以迷醉那冶梦的花莲");
        add("book.pasterdream.花园解密-凛冽雪绒花_第1页", "         花园解密\n\n北方的大树抵挡了刺骨寒风\n\n南方的花朵染上了梦的颜色\n\n西方的灯笼代替了落日暖阳\n\n东方的草从见证了时间流淌\n\n这座花园的园丁们\n是由雪组成的小人\n和会飞的蓝色精灵\n\n");
        add("book.pasterdream.花园解密-九尾狐_第1页", "         花园解密\n\n朝霞如梦似幻，熠熠生辉\n\n漂浮着的水晶花盆，高大的莲花燃烧着灵魂色的火焰\n\n浮空岛屿之上的水池中央\n一座九尾狐狸的雕像\n\n满足她的味蕾\n即可完成你许下的小小愿望\n\n发出金色光芒的甜蜜浆果\n");
        add("book.pasterdream.花园解密-九尾狐_第2页", "许愿清单：\n\n给金色的苹果注入魔力\n\n让桶里充满融梦的涌泉\n\n用一堆梦境的硬币换取宝箱\n\n给她能映射梦的水晶或金色的铃兰花\n将正在睡觉的金色狐狸的梦\n变为现实...\n她会给予你一样特殊的纪念品");
        add("book.pasterdream.染梦游记 其一_第1页", "染梦游记 其一\n\n一种粉色的晶芽生长在温暖区域的地下\n\n那些像史莱姆一样的生物天生具有保护色，或许可以通过声音找到它们\n\n在这里的海洋也潜藏者秘宝\n\n部分宝箱都是藏起来的，这里可能也一样？");
        add("book.pasterdream.染梦游记 其一_第2页", "有种奇怪的花坛会将能够解开谜题之人奉献独特的鲜花\n\n运气是在这里找到宝物的关键\n\n地下的洞穴里可能藏着能增加自身生命力的水晶\n\n在这里有种特殊的泉水\n\n世界树的内部也藏着宝藏...以及...");
        add("book.pasterdream.染梦游记 其一_第3页", "我见到的这一切，都有种似曾相识的感觉...就像经历过..  这一切的存在\n\nBy 琴雨梦");
        add("book.pasterdream.染梦游记 其二_第1页", "染梦游记 其二\n\n这里的地下浅层会有一种可以食用的矿石，还是甜味的\n\n也能发现稀有的钛金属，不过因为颜色和环境相近，我们不太容易发现它\n\n染梦的树叶上生长着一种果实，把这种果实做成蛋糕\n\n一些本土的作物催熟需要特定的软泥肥料");
        add("book.pasterdream.染梦游记 其二_第2页", "把染梦粉尘与骨粉一同研磨可以制作出一种特殊的染料\n\n地下生长的粉色石英矿物是一种不错的建筑材料！\n\n某个地下研究所里有一台可以积蓄染梦粉尘的仪器\n\nBy 琴雨梦");
        add("book.pasterdream.染梦游记 其三_第1页", "染梦游记 其三\n\n在这里探险让我感觉很轻松，即使什么都不做，也可以让我的心情好一些\n\n经过这么久的探险，被我发现的群系总共是四个，两个温带两个寒带\n\n冰冷的海洋下竟然会生长出珊瑚\n\n");
        add("book.pasterdream.染梦游记 其三_第2页", "这里好像会时不时有列车经过，不清楚是否是自己的幻觉\n\n我还在一些遗迹的箱子里翻到了几张好听的唱片\n\n我发现融梦水晶箱的振动次数越多，里面的宝藏也相对越好\n\n有些的教堂显得与众不同，建筑的一半像是被什么侵入了一样");
        add("book.pasterdream.染梦教堂 其一_第1页", "染梦教堂 其一\n记录给自己与后来者\n\n我在这个所谓的“世界”里，竟然找到一座悬浮在天上的巨大建筑。 我确定我没有看错！如果未来的你也能看到这本书的话...\n\n我潜意识的认为我正处于一场梦境之中，但这观感的一切却又如此真实。\n\n当我爬上这座大建筑时，这");
        add("book.pasterdream.染梦教堂 其一_第2页", "看起来应该是一座教堂？ 但里面却空无一人（不过也不会有人住在天上吧）\n\n蜡烛还在燃烧，仿佛永不熄灭。 阳光透过教堂正对门的玻璃，充盈教堂内的一切。\n\n在教堂的祭祀台之上有一块正在浮动着的美丽水晶，它在向四周发出光芒...（当时我盯了它很久）");
        add("book.pasterdream.染梦教堂 其一_第3页", "我用手靠近这块水晶，它突然好像感应到了我的存在，水晶紧贴闭合落到了地上。（这种好东西我当然是要拿走了，但也许你来的时候可能会再生？也许吧...）\n\n我把这块水晶捧在手心里，它好像在对我说话...是别的灵魂发自内心的祈愿。\n\n我面向教堂背后的大窗，闭眼许下了自己的愿望...");
        add("book.pasterdream.染梦教堂 其一_第4页", "偶对了还要再写一句，我要把这本书留在这个地方！\n\n至于为什么嘛...想让自己的故事永远留在这个世界上而已啦~\n\nBy：琴雨梦");
        add("book.pasterdream.染梦教堂 其二_第1页", "就在前天，琴雨梦突然告诉了我一个秘密，她说她进到了一个类似梦境的地方，还反复强调这是真实存在的？\n\n我起初并不相信她的话，但是她一直坚持要我跟随她去那个神奇的地方冒险，一起去那个什么所谓的教堂许愿。\n我拒绝了她好几次，但最终还是决定跟随她一起去看看她口中的世界和那座神奇的教堂到底是什么样的。");
        add("book.pasterdream.染梦教堂 其二_第2页", "当我穿过了那个令人震惊的裂隙，看到了眼前的景色后，我还掐了下我的胳膊...这一切好像还真是真的？\n\n我跟随着她来到那座教堂，蓝色的灵魂灯笼和高耸的塔楼，尖刺风格的西式建筑，很难想象这个地方为什么会有人的痕迹。\n\n我决定登上承载这座教堂的浮空岛屿去一探究竟。");
        add("book.pasterdream.染梦教堂 其二_第3页", "这座教堂里面漂浮着点燃着的蜡烛，座椅旁摆放着闪着光的灯笼，这使得这座教堂更加充满了神秘感与不真实感，我不确定这些是否有意义，但我决定将它们记录下来。\n\n我在教堂里漫步了许久，感受着这里的氛围。这里充满了神秘感和奇妙的气息。我无法解释为什么会有这种感觉，但我知道这里有着某种神奇的力量。");
        add("book.pasterdream.染梦教堂 其二_第4页", "我牵着她的手探索教堂的各个角落，突然她“哎呦”一声，“你牵着我撞墙干嘛！”，然后她突然停下来，诧异的看着我。\n\n“你...你为什么在这个墙里面？ 你还好吗？”，这里哪有什么墙？我一时没有理解她所说的什么。\n\n");
        add("book.pasterdream.染梦教堂 其二_第5页", "她突然开始拽着我沿着墙转圈，突然我也不知道撞到了什么东西，这次...是她站在墙里...\n\n她突然非常细致的向我描述这个教堂的模样，她所说的讲堂没有什么灯笼，也不是巨大的落地窗。\n\n我们互相讨论了许久，“难道我们看到的它是不一样的？而且不仅仅是视觉上？”她这么和我说。");
        add("book.pasterdream.染梦教堂 其二_第6页", "而当我看向教堂巨大落地窗下祷告桌上悬浮的那一颗神秘水晶，不知道为什么，我感觉是起因与它。\n\n琴雨梦非常熟练地轻抚水晶，然后这颗水晶就自然的跟随着她的手心漂浮。\n\n“琴雨梦...”，“嗯？什么事？”，“啊没什么...”。");
        add("book.pasterdream.染梦教堂 其二_第7页", "我决定把我在这里的所有经历写成一本书，留在教堂里供以后来到这个地方的人。\n\n这样，当我的书被发现时，也许...\n\nBy 夜岚");
        add("book.pasterdream.染梦教堂 其三_第1页", "“这里...是否有点太过于美好了? 依诺”\n\n自从进入这里不久后，我突然萌生出这样的想法，伊诺说我想太多了，确实，这里没有怪物，处处充满了生机和活力。\n\n但我尝试了一下占卜，三次的结果各不相同，也就是说我所看到的景象并非是真正的景象。");
        add("book.pasterdream.染梦教堂 其三_第2页", "“有光的地方就会有黑暗，光越强，黑暗便越浓厚”\n\n我在梦境不停的探索了半年之后，直到我发现了这个……半边染黑的教堂。\n\n这突兀的黑色，出现在了这完全不应该出现的地方，我努力抑制住颤抖的身体，跨进了这座教堂。\n\nBy 卡莱");
        add("book.pasterdream.染梦水晶球_第1页", "在我小时候也曾幻想过...住在一个属于自己的小天地玻璃球里！\n\n有一个属于自己的小生态超酷的哎，再被包裹起来的话那就再好不过了~\n\n反而我自己缺不怎么喜欢那些特别大的空旷房子，比较喜欢一份属于自己的小世界。");
        add("book.pasterdream.染梦水晶球_第2页", "就这样躺在这里面，被云朵衬托着，透过粉红色的玻璃看向透过云彩的太阳。\n\n嗯..嗯~ 就这样...睡一觉吧....\n\n祝你好梦\n\nBy 琴雨梦");
        add("book.pasterdream.祈愿树_第1页", "这是一棵祈愿树！\n\n嘛...至于为什么我说它是祈愿树...其实我也不清楚。\n\n就像来到这个地方，我好像本就知道该如何称呼这里的一草一木一样。\n\n我能叫出它们的名字，只需要遵从我的内心，即使这本就没有任何的正确答案。");
        add("book.pasterdream.祈愿树_第2页", "现在，除了以往生日时我会许下的愿望...我现在还想知道关于这个梦境的真相。\n\n但好像维独这一点，这棵树没有回应，我也感觉不到。\n\n如果后来的你有答案的话...请续写这份笔记。\n\nBy 琴雨梦");
        add("book.pasterdream.温暖的“寒风”_第1页", "冰冷还是温暖？\n这个漂亮的小亭子的作用似乎并不是为了避暑。\n\n在这里的气温一直很舒适，就算在冻洋也只是周围的冰川看起来觉得“冷”而已。\n\n因为那些冰川下面竟然生长着热带的珊瑚，还会有可爱的海豚。\n\n嗯...这也许无法用物理解释？\n还是某种地理奇观？");
        add("book.pasterdream.温暖的“寒风”_第2页", "这些亭子的颜色也有区别，在温带的棚顶会是粉色，寒带的则是蓝色。\n（蛮合乎地域风情~）\n\n温带的地下和寒带的也有所不同，还会生成不同的水晶构造，或许这些水晶会对我们有用...\n\nby 琴雨梦");
        add("book.pasterdream.粉顶蘑菇屋_第1页", "这是我第二次来到这个地方，距离上一次来到这个梦境已经过去十几天的时间。\n\n在我回到清醒的世界的时候，我把这离奇的经过告诉了她。不过很显然她不可能相信，甚至以为我吃错药了？\n\n然后我就拽着她的胳膊强行把她拽到了这里，刚开始她也是一脸惊讶...不过她的适应速度还是一如既往的快。");
        add("book.pasterdream.粉顶蘑菇屋_第2页", "这次我们走了许久，发现了一个生态群系截然不同的地方。\n\n蘑菇 蘑菇 还是蘑菇，粉色的蘑菇。\n\n“这么巨大的蘑菇甚至可以当一座房子！”\n\n然后...我们的面前就真的出现了一座巨大的蘑菇房...");
        add("book.pasterdream.粉顶蘑菇屋_第3页", "这很不可思议，我们站在那个精致的蘑菇房子面前呆住了好几分钟。\n\n卧室 厨房 办公桌 储物箱等等一应俱全，这绝对不可能是大自然的鬼斧神工。\n\n最为奇怪的是这栋房子还是凭空出现？\n\n");
        add("book.pasterdream.粉顶蘑菇屋_第4页", "“因为是做梦嘛，这些都属于正常现象？的啦 哈...哈哈...” 我这样调侃着。\n\n我也感觉自己竟然会这样奇怪的接受这个事实...大脑过载了也就干脆不去想好了！\n\n之后就是探寻这附近的生态环境，然后在这个蘑菇屋里过了一夜。\n\n还是一样，我会把这本书留在这里。");
        add("book.pasterdream.粉顶蘑菇屋_第5页", "如果后来的你发现了，请不要把这个蘑菇房子吃了！我以后还会回来玩呢，也许？\n\nBy: 琴雨梦");
        add("book.pasterdream.融梦涌泉井_第1页", "这都被你看到啦？那就先从头说起吧。\n\n在我第一次散步来到这里的时候，这里的植被生长状况与其他地方留有不同。\n\n在比较小的一块地方有许多种不同的植被，而且植被的密度也是相当的高。\n\n和其他地方相对比的话确实比较可疑，我便低下头来研究这边的土质。");
        add("book.pasterdream.融梦涌泉井_第2页", "从表层看起来并无什么差别，但越向下挖会发现，捧一捧土在手里，会有些许发光的细小星星点从指缝里流下去。\n\n然后就当然是去摇人来了，我回家叫上了夜岚，带了两把趁手的铲子和她珍藏的钛金镐。\n\n随着我们向下挖掘，逐渐能在土壤里发现许多细小的光点。");
        add("book.pasterdream.融梦涌泉井_第3页", "我们搭好梯子爬下来，有一股发光的泉水在喷涌，在一个植被茂盛的洞穴里，也许这个发光的泉水就是这些地下植被的太阳。\n\n不过按照科学来讲不可能存在无限能源的东西，我们用手头的材料修了这口“井”，我带了一瓶泉水回到了家，希望我的研究能够顺利。\n\n");
        add("book.pasterdream.融梦涌泉井_第4页", "至于剩下的这些，就交给后来的你了吧。\n\n(取水不忘挖井人，记得感谢我~哎嘿嘿)\n\nBy 琴雨梦");
        add("book.pasterdream.浮空岛日记_第1页", "你说，这些浮在天空上的建筑和岛屿 是怎么产生的呢？\n\n是大自然的鬼斧神工？还是有比我们更先来到这里的人呢...\n\n那这些石头泥土也不应该飞起来啊，自然怎么能创造出人为的痕迹，而人类又怎么能脱离现实的构造这一切。\n\n");
        add("book.pasterdream.浮空岛日记_第2页", "哎呀，一去认真想这些就会让我长这么大构建的世界观全部崩塌。\n\n不过也没必要去纠结这些就是啦，也许和我的直觉想的一样？这一切只是一场梦而已。\n\n想象可是无边无际的，让这座岛屿开口说话都可以！你说对吧~");
        add("book.pasterdream.浮空岛日记_第3页", "但总会有一天，我要彻底了解这一切...\n\n让这趟旅程成为我此生无法忘却的回忆。\n\nBy 琴雨梦");
        add("book.pasterdream.落叶归根 裂荚归冠_第1页", "蓬勃生长的大树，枝叶作为你的孩子，在凋零时会落在你的根旁\n\n其他植物在诞生之初便会抛弃自己的种荚，抛弃它们母亲留给他们最后的躯壳\n\n而你，用自己最顶端的枝叶把种荚顶上高空\n\n用根系和枝叶给予它营养，让它享受最顶端的光芒，笼罩这个世界最顶端的光芒");
        add("book.pasterdream.落叶归根 裂荚归冠_第2页", "即使种荚早已完成它的使命，但却将这些养分用于构建自己的梦想\n\n我也将自己的祈愿给予你  作为初次的见面礼\n\n\nBy 琴雨梦");
        add("book.pasterdream.梦境漂泊_第1页", "梦境漂泊\n\n在梦境的海洋，我漂泊无依\n\n像是孤独的旅人，寻觅着未知的奇迹。\n\n星空作伴，大地为席\n\n在这安眠之境，我与梦共舞\n\n脚下是迷雾，前方是未知\n\n");
        add("book.pasterdream.梦境漂泊_第2页", "我用心感受，每一段旅程的韵律。\n\n风中有歌声，雨中有故事\n\n每一个瞬间，都是梦境的奇迹。\n\n我是漂泊的旅人，无畏也无惧\n\n在梦境的深处，找寻生命的真谛。");
        add("book.pasterdream.梦境漂泊_第3页", "只为\n\n在这无尽的旅途，将我成为自己的传奇。\n\n\nBy 琴雨梦");
        add("book.pasterdream.气泡生态球_第1页", "正当我划着木舟横渡海洋，寻找漂浮在空中的那些遗迹时，休息的空当间我四下张望，偶然发现了这个神奇的球体。\n这个球体漂浮在冰冷的海水中，透明的薄膜后依稀可见游鱼的身影。我潜入水中，小心翼翼地伸出手，试图触摸这层薄膜，却在感受到微弱的阻力后直接穿了过去。\n令人难以置信的是，这个球体内十分温暖，与刚刚把我浸了个透心凉的冻洋海水形");
        add("book.pasterdream.气泡生态球_第2页", "成了鲜明的对比。蹲下伸手穿过厚厚的云块摸索，果然底下的海水也很暖和。这里的热带鱼不是什么标本，刚刚在船上见到的鲜艳颜色也并非是我被冻得神智不清产生的错觉。\n仔细确认一下这里的内部环境，我不由得回忆起了曾经见过的生态球。这个奇妙的气泡球到底是由超越人智的存在所建造，还是这个不可思议的世界自然形成的？\n我不得而知。");
        add("book.pasterdream.气泡生态球_第3页", "但至少这里缓慢游动的小鱼和这个世界的其他生物一样对我没有什么敌意，我可以在游回我的船上之前再坐在这里休息片刻。\n这么想着的我随意地拿木棍轻轻戳了戳边上的气泡壁，气泡壁表面却泛起了剧烈的波浪，险些崩裂，让我心悸了好一阵。果然下次还是小心为妙，这种脆弱的泡泡大概只能在平静的冻洋里存在，稍受戳刺就会整个消失。\n我在笔记空出的标题上写下");
        add("book.pasterdream.气泡生态球_第4页", "“气泡生态球”的名字。毕竟它就像小型的生态球一样美丽且易碎，还是少惊扰为好。");
        add("book.pasterdream.染梦实验室_第1页", "一栋残破的建筑，在这梦境之中发现了前人留下的痕迹。\n\n不止我一人发现了这个奇怪又充满粉色的地方，我大声呼喊却没人回应，这里已经人走楼空。\n\n昏暗的灯光笼罩着遗迹内部的空间，这里的中间摆放了一台梯形的未知仪器。");
        add("book.pasterdream.染梦实验室_第2页", "我确信这个地方是一位更早到这里的前辈的实验室，而这中心摆放的是他最终的心血。\n\n令人遗憾的是我没能发现前辈匆忙离开这里的原因。\n\n现在,我应该延续他的研究，带着这个损坏的仪器。\n\nBy: 彗星");
        add("book.pasterdream.来往于梦_第1页", "致后来者：\n\n这份文字记录了我在这里的所见所闻和一些推测，我不知道这个世界究竟是怎样的。\n但（被划去的文字）“奇迹”，我只能用这样一个词语来形容眼前的世界。\n\n无处不在的粉色，永明的天空，黑暗中也不会滋生令人厌恶的邪恶，如梦境一般，不，或许它就是梦境");
        add("book.pasterdream.来往于梦_第2页", "虽说在唯物主义教育下长大，可面对这幅完全无法以现有知识解释的景象，我不由自主地发出了疑问：\n\n这世界上，会不会真的有什么神明一类的存在。（这样的神明又是唯心的“神”，还是更高级的“存在”）\n\n不过，这里也存在不少疑点，任何事物都具有相对性。");
        add("book.pasterdream.来往于梦_第3页", "既然有如此美好的世界，那有没有一个与之截然相反的地方；而且，这个世界无处不有着一种古怪的虚幻感，明明一切都那么真实却处处有种疏离感（大概类似于清明梦？）\n\n这一奇迹究竟由谁创造，又由谁维持（监管？）\n\n§m>大片被划去的文字<");
        add("book.pasterdream.来往于梦_第4页", "或许有人曾在此停留（定居？），毕竟有烟熏炉、花盆、书架这些有着生活气息的物件出现。\n\n不过屋主大概已经离开很久了（奇怪的是物品大多崭新如初，窗台上的花甚至还活着）\n\n至于这方世界，暂且称其为“梦境”");
        add("book.pasterdream.来往于梦_第5页", "更多结论仍需进一步的探索才能验明，或许我需要一种更稳定的方式来往于现实和“梦境”间。\n\nBy 流华");
        add("book.pasterdream.染梦世界树_第1页", "偶然远眺风景时，我在视野的尽头发现了这棵参天巨树。走到树底仰望，树干高耸入云，甚至难以望见枝叶。\n\n挖掘根部周围的泥土，可以发现其根茎延伸不算很长，似乎难以解释其异常的高度\n\n蜿蜒的树干形成了一条天然的阶梯，但有些地方仍要自行攀爬才能登上。爬到枝干的顶端后，能看到其内部存在一个小空洞。");
        add("book.pasterdream.染梦世界树_第2页", "闪烁着光芒的白色涌泉从侧面的树干中流出，无论哪里都显得尤为反常——虽然比起这个世界本身，这点景象已经不足以使我感到惊奇。\n\n回到树下后，我开始思考这棵树得以形成和正常生长的原因。对此，我做出了某种猜想：\n\n这个世界属于某种梦境，而其大气中存在的梦境能量的分布是不均匀的。");
        add("book.pasterdream.染梦世界树_第3页", "就像大气现象导致天候变化一样，这种融于梦境大气中的能量——融梦能量的分布应该也是不均匀的。\n\n融梦能量沉积较多的区域，能量沉积下来化作了实体，形成了大陆、空岛、树木等这个世界的一切。而那些融梦能量小范围大量聚集的结果，就是融梦水晶箱——这也解释了为什么打开箱子时会有融梦能量逸散而出。\n");
        add("book.pasterdream.染梦世界树_第4页", "照这个理论进行推论，在这个梦境世界的中心——融梦能量最为富集的地方，理应会形成一种体积巨大的结构；而丰富的融梦能量会化为大量的融梦水晶箱积蓄其中。\n\n想到这里，我回头看向了参天巨木所在的方向。它有可能正是这个梦境世界的世界树，融梦能量的富集场所，有着大量融梦水晶箱的梦幻之地。");
        add("book.pasterdream.染梦世界树_第5页", "我理应再次登上这棵树，对其内部结构一探究竟。之前的一无所获一定是我遗漏了什么……\n\n\nBy 极星");
        add("book.pasterdream.代达罗斯之翼与浮空岛_第1页", "代达罗斯之翼与浮空岛\n\n在我从那颗参天大树中悻然离开后不久，千夜追上了我，打断了我重新探索的计划\n\n“看到那些悬浮在半空的岛了吗？我想上去看看那里有没有什么宝藏。”我向千夜提议\n\n没想到千夜直接给我泼了桶冷水，“我看你是失了智。我们又不会飞，怎么上去呢？”");
        add("book.pasterdream.代达罗斯之翼与浮空岛_第2页", "“关于这点，我有个大胆的想法……”\n\n在千夜难以置信的目光中，我拿出了之前路上捡到的那些粉红色的鸡掉落的羽毛。之后，我把之前捡到的蜡烛重新熔成蜡，用这些蜡把羽毛粘在了一起，费了不少力做出了两对羽翼。”\n\n“所以你是想当伊卡洛斯。”");
        add("book.pasterdream.代达罗斯之翼与浮空岛_第3页", "“不，我觉得我会是代达罗斯。毕竟这里的太阳可不会融化这些蜡。而且不试试怎么知道呢？毕竟……”\n\n事情超乎寻常的顺利——虽然按照常理来说，人是没法用这种羽翼飞翔的；但不知为何，这里的风像是托着羽翼般，使我们能毫不费力地飞行。我们很轻松地就飞到了我之前只能抬头仰望的浮空岛上。");
        add("book.pasterdream.代达罗斯之翼与浮空岛_第4页", "抵达岛上后，千夜直接冲进了那间小屋中。我也紧随其后，在这里找了个地儿坐下来，和她交谈起来。\n\n“话说回来，这个世界也太超乎常理了吧”千夜把那对羽翼卸了下来，开始观察它有没有散架。\n\n“所以我说了，这个世界应该是属于某种梦境或者幻境吧");
        add("book.pasterdream.代达罗斯之翼与浮空岛_第5页", "毕竟我完全搞不懂这种岛屿是怎么安然无恙地悬浮在空中，在我们踩上去后又能纹丝不动的。”\n\n“这个房子也是，虽然一看就像是人为制造的，但根本没有人类生活过的迹象。另外，我还是无法理解这玩意凭什么能运作。”千夜抖了抖手上的羽翼说。\n\n“别较真了，梦里啥都有可能嘛。”");
        add("book.pasterdream.代达罗斯之翼与浮空岛_第6页", "By 极星");
        add("book.pasterdream.探求秘辛_第1页", "为了收集一些必要的资源，我在长途跋涉后找到了一片沙漠。\n鉴于我要寻找的资源相当罕见，可能不得不在此停留一段时间，我设法找到了位于沙漠边缘的一座村庄，并建造了这座供我居住的小屋。\n每天日出时出门探索，日落前回到这座临时居所中；晚上或是刮起沙暴无法出门的时候，则和村中的村民攀谈以打发时间——这就是我在这片沙漠的日常。");
        add("book.pasterdream.探求秘辛_第2页", "转眼间半年过去了，白天的探索没有任何进展，但我和村民间的关系却日渐熟络起来。\n大人们对我在夜晚保护他们不受怪物侵扰的行动感激不尽，孩童们则醉心于我分享的游历各方时的冒险故事——渐渐的，村民们开始向我透露一下从未给其他旅者讲述过的“秘辛”：\n“我们村的铁匠几乎从不出门，所以你应该也没怎么见过他。据说他曾是某个国家的");
        add("book.pasterdream.探求秘辛_第3页", "御用工匠，他的家族专门为王室锻造各式各样的精兵利器；但在王国毁灭后，他的家族就此没落，最后流浪到我们这里定居了。他好像还不太能接受自己的处境，把工坊造的离我们村子有点远，我们平时也跟他交往不深，只有工作层面的交流……但如果是你的话，应该能和他打好关系吧……”\n村民们似乎想让我去开导孤僻的没落锻造贵族（虽然我觉得他们只是指望在跟那位");
        add("book.pasterdream.探求秘辛_第4页", "打好关系后能用更便宜的价格修理工具罢了）。我带着我珍藏多年的陈酿，造访了铁匠的工坊。\n……\n虽然经历了许多波折，但我们最终成为了挚友。在一次对饮中，我向年事已高的他表示，希望他家族的精湛锻造技术能流传下去；虽然我已经在与他的相处中对他的锻造技法有所了解，但我无论如何也无法像他那样锻造出能力优越的装备。");
        add("book.pasterdream.探求秘辛_第5页", "他在长时间的沉思后，掏出纸笔，为我绘制了一张设计图，并告诉我：只有严格按照他的工坊的布局，才能最大程度地将大气中的某种……物质？引导并汇聚起来，从而锻造出超凡而新颖的工具。折服于他的慷慨与信任，我将我携带的几乎所有值钱的物件全部留下作为谢礼，并带着这份设计图离开了此处。\n不知不觉，好像已经在这片沙漠停留太久了啊……");
        add("book.pasterdream.探求秘辛_第6页", "是时候踏上新的旅途了——等我下一次停留时，就试着按照这份设计图来建造一座工坊吧。");
        add("book.pasterdream.研究笔记-黑金属_第1页", "要这段研究的起因，得归溯于是我在这片漆黑的梦境发现的一些古怪的金属。\n就在那些倒挂的灯笼，遗迹，或者是一些陶罐宝盒里。\n\n质地看起来就像铁一样，不过锈迹却不是那种褐色的层皮，而是很多黑色的小洞。\n但是用一般的熔炉去熔炼它不会有任何的反应，我索性把它丢进了岩浆，当然是不出意外的直接汽化了。\n\n");
        add("book.pasterdream.研究笔记-黑金属_第2页", "我和夜岚为了研究这个金属，就在这附近去找了一个破旧的梯形房子。\n\n但当一次偶然间的巧合，二楼渗下低落的流体阴影滴进了装有这些金属的罐子，然后这些阴影好像有意识一样填补进了这些锈蚀的洞里。\n\n“这些还不够”，“看来这些金属可能只是一种载体，真正缺少的是哪些影子”");
        add("book.pasterdream.研究笔记-黑金属_第3页", "随着不断地研究，“高温，流动的阴影，以及那种影子怪物的身体”。\n\n我们从这里醒来，带着一些材料设备再一次躺在了影灯之下的床上...\n\n随着机器的轰鸣声，融合，熔化，凝结...最终一块黑色的金属应声落在了地上...");
        add("book.pasterdream.灯影游记 其二_第1页", "我很好奇这些真菌是怎么长在这个地方的？\n\n这里的天空没有光，没有熟悉的太阳，没有一种可靠的能量来源。\n\n但这些真菌缺能把根扎进这些黑色的石头里，长成比人还大的蘑菇树，甚至还有可以发光的果实。\n\n\n\n");
        add("book.pasterdream.灯影游记 其二_第2页", "同时还孕育了一些可能是本地的小动物？这些甲虫以这里的菌索为食，成群结队的在这个地方生活。\n\n面对黑暗的它们，长出了白色的眼睛，也许这是能让它们看清黑暗的原因。\n\n这些生命组合起来所谓的“生态系统”......只能说完全不能用现代科学去解释。");
        add("book.pasterdream.灯影游记 其二_第3页", "不过好像从那个染上粉色的世界到这个漆黑且遍地都是怪物的地方早就不是所谓科学能解释的了，完全就是主观构想出来的虚幻产物。\n\n害，我也不是做科研的人。不过嘛，这些蘑菇...会不会好吃？\n\n我觉得我应该去研究一些食谱(还有去找一个试验品！)");
        add("book.pasterdream.黑暗之地_第1页", "不不不，这里...  我很难看清这里的一切，我感觉黑色的迷雾就在我的眼睛上！\n\n“夜岚！你在哪,听得见吗？”\n\n这要说到前几天，她在那个粉红色的梦境里收集草药，看到了一个被黑色侵染了一半的教堂...\n相信在看这本书的你们也经历过了，她也领着我到了这里。");
        add("book.pasterdream.黑暗之地_第2页", "“我先带上装备去看看那个鬼影灯，你在这等我一会，如果那边没问题我在叫你过来” “嗯”\n\n然后就在她触摸影灯的瞬间，感觉有超多黑色的迷雾漫了过来，然后我什么都看不到了...\n\n直到迷雾散去...她以不在灯的附近。");
        add("book.pasterdream.黑暗之地_第3页", "所以我肯定是跑过去摸影灯了啊，然后就成了现在这个鬼样子。\n\n“喂！能听到我说话吗！你..在...啊啊啊啊..这什..唔唔！（放开我！）” “嘘！别说话！”\n\n“是我（很小声）” “你干什么！很痛的，我感觉我从地上摔下来了！”");
        add("book.pasterdream.黑暗之地_第4页", "“你没看见外面那堆鬼东西吗？ 想活命就小心点” “我寻思我也看不见啊（小声嘀咕）”\n\n“这里是我盖的庇护所，害 其实是借别人的坟用用” “什?!...” “嘘！” “唔...”\n\n“话说你竟然会跟过来，我也没给你发信号” “这不担心嘛，就接着跟过来了”");
        add("book.pasterdream.黑暗之地_第5页", "“接着？已经过去3天了差不多”“啊？”\n\n然后...她向我讲述了在这里的故事...包括这个“坟头”\n\n“这样啊...” 不过想了想...最后决定先不记录这些。\n\n很多模糊的东西...我需要去认真的求证一下。");
        add("book.pasterdream.黑暗之地_第6页", "（因为...在蜡烛的照射下...我看不到她的影子...）\n\nBy 琴雨梦");
        add("book.pasterdream.阴影小木屋_第1页", "经过长时间的探索和比对，我对这个黑暗的世界的各环境做了大致的记录。\n\n这个世界的绝大多数地域都十分危险。\n\n被浓厚阴影覆盖的山地，流淌与凝结的阴影不断催生着可怖的、仅仅望见就会失神的怪物；\n\n");
        add("book.pasterdream.阴影小木屋_第2页", "堆满遗骨和断壁残垣的平原，无尽的怨魂发出凄厉的嚎叫，完全无法久留；\n\n唯独这里，这片长满了真菌“树”的“森林”是相当安全的。\n\n真菌的“树叶”上生长着数个发光体，在这个漆黑的世界里，这样的光芒似乎能驱散暗影，抑制阴影生物的产生。");
        add("book.pasterdream.阴影小木屋_第3页", "这里的原住民是一种温顺的甲虫，只要不向其发起攻击，就能相安无事。\n\n在这片森林中漫步时，我偶然发现了一座房子。\n\n有趣的是，这间小屋的墙壁似乎是由真菌树的菌柄做成的。\n\n");
        add("book.pasterdream.阴影小木屋_第4页", "我试着将菌柄表面附着的一层阴影剥离开后，发现其外表与房屋的墙壁别无二致——是一种类似于……去皮原木的材质？\n\n这间房屋似乎已经被废置很久了，人的生活痕迹只剩下了寥寥数片地毯，连原住民的甲虫都在这间小屋里安家了，我不得不赶走它们才能细致观察房屋内部。");
        add("book.pasterdream.阴影小木屋_第5页", "就在我检查房屋的地板时，我发现了数片附着在地板上的不自然的阴影。\n\n我站在远处，用一只脚轻踩这片阴影。\n\n阴影底下赫然伸出一只由阴影构成的手，抓住了我的脚，试图把我拉进地下。我拼命挣扎，终于把脚从暗影之手中拔了出来。");
        add("book.pasterdream.阴影小木屋_第6页", "在我试图清扫掉另一片阴影时，它在消尽前改变了形状，变成了我在阴影覆盖的山地见到的那种手型怪物，我费了一番功夫才将其斩碎。\n\n我有预感，我会在其他地方再次见到这种“陷阱”……\n\n\n\nBy: 极星");
        add("book.pasterdream.阴影地下工作室_第1页", "虽然这片森林相对安全，但为了完全杜绝阴影生物的打扰，我决定在地下修建一个工作场所。\n得益于从主世界带来的部分物资，我至少能在卧室里建造一个能冲澡的小房间——毕竟在大片的阴影中游过泳后总会想冲个身子。\n我把这个世界各处能找的植物中的大部分栽进了花坛里。虽然多少有点怪异，但观赏这些植物总能略微放松一下身心。\n");
        add("book.pasterdream.阴影地下工作室_第2页", "在这些灰黑色的植物中，一种被我称作“阴影蘑菇”的植物尤为特殊——与其他植物不同，这种蘑菇似乎有着和树苗类似的性质：只要有充足的生长空间，它就能长成森林中随处可见的那种菌木。看来这片森林的形成就与这种蘑菇息息相关了。\n不过神奇的是，这种蘑菇的生长必须要有充足的空间。\n之前我把它种在花坛里的时候，它突然疯长起来，把我的花坛弄得一塌糊涂。\n");
        add("book.pasterdream.阴影地下工作室_第3页", "为了防止这种情况再次发生，我将其移出了花坛，并限制了它的生长空间。（旁边那棵已经长好的菌木则被我留了下来，供我继续研究）\n在不断的捣鼓这个世界的某些材料后，我总算是复现了我来到这个世界所用的那张黑色的长床。看样子，只要在上面躺下就能让我回到原来的世界吧。\n但这个世界中仍有许多未知等着我去探索；在将这一切究明之前，我不打算轻易回\n");
        add("book.pasterdream.阴影地下工作室_第4页", "到主世界。\n啊，好像有一只甲虫爬进来了。虽然这种甲虫并没有什么攻击性，但我还是不太喜欢有生物打扰，该去赶走它了。");
        add("book.pasterdream.>被阴影浸染的字迹潦草的笔记<_第1页", "难以置信，我竟然能从那种恐怖的生物的手中逃出来。\n那是一对掌心长者一只白色眼睛的黑色大手；仅仅是与其对视，就让我浑身战栗，根本无法涌起与其战斗的欲望。\n即便如此，我也强行鼓起勇气与其对峙，却发现它可怕的力量远超我的想象。\n仅仅是对其做出最轻微的攻击，都会引起其强烈的反击。\n");
        add("book.pasterdream.>被阴影浸染的字迹潦草的笔记<_第2页", "由于战斗时实在过于慌张，我甚至没有分清楚我究竟攻击的是哪只手。\n然而，我清楚地记得，攻击其中一只手会让其长出一根尖刺，并使其疯狂的挥动那根尖刺；\n而攻击另一只手会让其释放出一座看起来蕴含了极大能量的尖塔。\n在见到这种可怕的反击后，我立即仓皇逃窜，逃到最后连我也忘记了自己是如何逃离的。\n");
        add("book.pasterdream.>被阴影浸染的字迹潦草的笔记<_第3页", "后来者啊，希望我这丢人的经历能为你的探索做出一些贡献。\n除非你有能足以踏遍这个阴森可怕的世界而不受任何威胁的力量，否则请你千万别前往那“眼睛”的所在之处。");
        add("book.pasterdream.关于黑暗之手的随笔_第1页", "在对这个世界所有群系的探索大致结束后，我对这个世界的几乎所有结构都有了相当程度上的了解……\n……除了那被四根“手指”包裹的“眼睛”。\n那中心发光的传送门似乎通向一个结构简单、但并不安全的空间。\n出于保险起见，我设法将一个装有图像传输装置的坚固机器送进了这个传送门，让它代替我进行勘察。\n");
        add("book.pasterdream.关于黑暗之手的随笔_第2页", "穿过传送门后，它缓缓落在了一个“竞技场”的中心。\n图像开始变得模糊不清起来；地面大幅震动，随即两只黑色的巨手从空气中浮现。\n那两只手在发现我投入传送门的这个“异物”后，立即一前一后地向它发起了猛攻。\n视野中，一只手突然冲撞了过来，将机器掀了个底朝天；随机在一阵黑暗与爆风中，机器的视野彻底暗转并失去了与我的联系。\n");
        add("book.pasterdream.关于黑暗之手的随笔_第3页", "不行，这样的话变数还是太大了——仅凭这点信息的话没有任何胜算。\n虽然如此，我还是姑且留下这份笔记。虽然几乎没有什么有价值的信息，但应该还是能对给后来者起到一些作用的。\n");
        add("book.pasterdream.与黑色双手的决战和败北_第1页", "我写下这张笔记，以警示那些试图挑战它的旅者：「它」远比想象中的更为强大。\n关于那个闪光的传送门彼端的世界究竟为何，探索灯影的其他寻梦者们和我的好友留下的笔记给了我答案：门后是一个闭锁的小型世界，里面有形似一双手的恐怖阴影生物。\n为了战胜它，我用粉红梦境中能获得的最强韧的金属制作了盔甲和武器，并做好了一切我认为能做的准备后，");
        add("book.pasterdream.与黑色双手的决战和败北_第2页", "跳入了传送门。\n然而，即使我认为我已经做好了充足的准备，这场战斗任仍然艰苦至极。\n无穷无尽的不知从何处涌出的暗影怪物、黑色双手的猛烈进攻无时无刻不在摧残着我的精神、意志和体力。\n即便如此，我也尽我所能地试图对他发动攻击，而换来的则是更为强烈的反击——不管怎么说，我还是认为我重创了它。\n");
        add("book.pasterdream.与黑色双手的决战和败北_第3页", "就在我认为这样下去说不定有胜算的时候，可怕的事情发生了。\n一瞬间我的视野陷入了黑暗，一片漆黑之中好似无穷无尽的阴影生物蠕动而出，而我的攻击甚至无法穿透围绕那双手的厚厚一层阴影。\n被怪物包围，药水用尽、身受重创的我，在脑海中强烈祈求着这只是一场梦，拿起苍白骨针猛的往自己手臂上扎去。\n");
        add("book.pasterdream.与黑色双手的决战和败北_第4页", "一阵刺痛之后，我的视野被强光夺去。眨眼间，我已身处熟悉的床边；但满身的冷汗、布满裂纹的盔甲和空空如也的药水瓶告诉我，刚才的经历并不是简单的一场梦。\n果然，要想击败这恐怖的生物，需要能够抑制阴影，或是能融入阴影、从内部破坏它的武器。\n果然是我还遗漏了什么吗……或许，等待其他援助者前来\n，和他们一同面对它才是更");
        add("book.pasterdream.与黑色双手的决战和败北_第5页", "好的选择。\n在那之前，我得先找到更为强大的武器……");
        add("book.pasterdream.破风的骑士_第1页", "古希腊风格建筑的残垣断壁，围绕成一个圆环的神庙，在这中心供奉着一个并不起眼的祭坛。\n\n是用来求雨？还是期望能顺应风的方向？还是供奉自己的守护神？\n\n我靠近处于中心的那个半砖状祭坛蹲下，发现在祭坛中间留有一个黑色的洞，里面竖放着一张圈起的纸条。");
        add("book.pasterdream.破风的骑士_第2页", "上面画着一些潦草的图案，还有数字标号的顺序。一块蓝色的水晶，还有3个类似金属制成的盔甲。\n\n以及这张纸最下面的一行写着\n\n“破风的骑士”将驱逐其守护之物以外的一切...\n\n\nBy 琴雨梦");
        add("book.pasterdream.清晨的新风_第1页", "这是新的一天的开始，也是新的风向的到来。\n\n伴随着风铃的声音，飘落的羽毛，以及从东方升起的阳光。\n\n风是属于这里的恩赐，顺应风的方向将是我们探索的助力，而逆风将可能使我们寸步难行。\n\n风不仅指引我们旅途的方向，也是一种特殊的钥匙。");
        add("book.pasterdream.清晨的新风_第2页", "风只会在新的方向到来的时候吹拂，无法通过任何方式预言。\n\n如果错过了清晨风的指引，或是在这个梦境之外无法用身体去感受，风向标可以跨越现实与梦来告诉你当今的风向。\n\n以及在这里散落的一些宝藏，将可以改变风对我们的影响。");
        add("book.pasterdream.清晨的新风_第3页", "还有许多风的秘密... 也许风会在某时某刻向我们揭晓。\n\n\nBy 琴雨梦");

        // 露滴 Tooltip
        add("tooltip.pasterdream.drink_effect", "§7饮用后获得效果:");
        add("tooltip.pasterdream.red_dew.effect", "§7▪ §9瞬间治疗I");
        add("tooltip.pasterdream.blue_dew.effect", "§7▪ §9水下呼吸I (1:00)");

        add("tooltip.pasterdream.melt_dream_coin", "§7右键使用10个以上可合成为融梦星币堆");
        add("tooltip.pasterdream.melt_dream_coin_pile", "§7右键使用可分解为10个融梦星币");
        add("tooltip.pasterdream.melt_dream_coin.value", "§7▪ §6价值面额 : ");

        add("tooltip.pasterdream.pliers.1", "§7▪ §9可以收获作物和植物");
        add("tooltip.pasterdream.pliers.2", "§7▪ §9潜行右键可直接采集帕斯特的植物");

        // 笔记残页
        add(ModItems.BROKEN_NOTE.get(), "笔记残页");
        add(ModItems.UNKNOWN_NOTE.get(), "未解析的笔记");
        add(ModItems.PALE_BONENEEDLE.get(), "苍白骨针");
        add(ModItems.ROOTS_PALE_BONENEEDLE.get(), "溯源苍白骨针");
        add("tooltip.pasterdream.broken_note", "§7我们还需要了解更多...");
        add("tooltip.pasterdream.unknown_note.1", "未知内容");
        add("tooltip.pasterdream.unknown_note.2", "§7需要使用§e研究台§7解析笔记");

        // 苍白骨针
        add("tooltip.pasterdream.pale_boneneedle", "§7让你从梦中清醒...");
        add("tooltip.pasterdream.pale_boneneedle.use", "§7左键对其他玩家使用 | 右键对自己使用");
        add("tooltip.pasterdream.roots_pale_boneneedle.1", "§7让你从梦中清醒...并回到标记的位置");
        add("tooltip.pasterdream.roots_pale_boneneedle.2", "§7潜行右键以设置标记点");
        add("tooltip.pasterdream.roots_pale_boneneedle.3", "§7如未设置标记将默认返回重生点");
        add("tooltip.pasterdream.roots_pale_boneneedle.4", "§7左键对其他玩家使用 | 右键对自己使用");
        add("message.pasterdream.roots_pale_boneneedle.waypoint_set", "已记录坐标点");

        // 其他道具
        add(ModItems.SAND_OF_TIME.get(), "时之沙");
        add("message.pasterdream.sand_of_time.cooldown", "这个时之沙辛苦工作了一天之后，正在休息，还需要%s秒才能恢复");
        add("tooltip.pasterdream.sand_of_time.1", "§7右键使用以跳跃世界时间更替昼夜");
        add("tooltip.pasterdream.sand_of_time.2", "§7该物品在使用后，会进入%s§7秒的冷却");
        add("tooltip.pasterdream.sand_of_time.3", "§7§o-- 沙漏将会记得 我们所遗忘的时光");
        add("tooltip.pasterdream.sand_of_time.4", "还剩%s秒冷却");
        add("tooltip.pasterdream.sand_of_time.5", "§a力量已恢复，可以切换昼夜");
        add(ModItems.STORAGE_BAG.get(), "便携储物袋");
        add(ModItems.LARGE_STORAGE_BAG.get(), "大型便携储物袋");
        add(ModItems.DREAM_HARP_OF_WANDERER.get(), "漂泊旅者的染梦竖琴");
        add("message.pasterdream.dream_harp_of_wanderer.no_energy", "融梦能量不足");
        add("tooltip.pasterdream.dream_harp_of_wanderer.line1", "§7演奏后为直径10格内的玩家提供效果(1:00)：");
        add("tooltip.pasterdream.dream_harp_of_wanderer.line2", "§7▪ §9最大生命值+4 且恢复4点生命 移动速度+10%");
        add("tooltip.pasterdream.dream_harp_of_wanderer.line3", "§7▪ §9+4.8 理智光环，并立即恢复5点精神值");
        add("tooltip.pasterdream.dream_harp_of_wanderer.line4", "§7▪ §9冷却时间：30秒");
        add("tooltip.pasterdream.dream_harp_of_wanderer.line5", "§7▪ §4融梦能量消耗：3");
        add("tooltip.pasterdream.storage_bag.tooltip", "§7一个普普通通的袋子，拥有9格储物栏");
        add("tooltip.pasterdream.large_storage_bag.tooltip1", "§7使用末影力量以拓展袋子的储量，拥有25格储物栏");
        add("tooltip.pasterdream.large_storage_bag.tooltip2", "§7shift+右键以捕获部分生物，再次shift+右键以取出（该功能：%s）");
        add("tooltip.pasterdream.large_storage_bag.catch_enabled", "§a启用");
        add("tooltip.pasterdream.large_storage_bag.catch_disabled", "§c禁用");
        add("tooltip.pasterdream.large_storage_bag.tooltip3", "§7内含生物：%s");

        // 自定义稀有度
        add("rarity.pasterdream.common", "普通");
        add("rarity.pasterdream.excellent", "优秀");
        add("rarity.pasterdream.superior", "精良");
        add("rarity.pasterdream.master", "大师");
        add("rarity.pasterdream.ancient", "上古");
        add("rarity.pasterdream.epic", "史诗");
        add("rarity.pasterdream.legendary", "传说");
        add("rarity.pasterdream.miracle", "神迹");
        add("tooltip.pasterdream.quality", "品质: %s %s");

        // 护符
        add(ModItems.EMBRYO_CHARM.get(), "护身符原胚");
        add(ModItems.GOLD_CHARM.get(), "纯金护身符");
        add("tooltip.pasterdream.gold_charm.piglin", "§7▪ §9使猪灵保持中立");
        add(ModItems.ENDEYE_CHARM.get(), "邪眼护身符");
        add("tooltip.pasterdream.endeye_charm.enderman", "§7▪ §9使末影人保持中立");
        add(ModItems.SEA_CHARM.get(), "海波护身符");
        add(ModItems.CARAPAX_CHARM.get(), "龟壳护身符");
        add(ModItems.WORLDTREE_SEEDPOD.get(), "世界树种荚");
        add(ModItems.GARLAND.get(), "花环");
        add(ModItems.REST_BUFF_ICON.get(), "休憩图标");
        add(ModItems.CHEER_UP_BUFF_ICON.get(), "振奋图标");
        add(ModItems.LETHARGY_BUFF_ICON.get(), "不振图标");
        add(ModItems.TRANCE_BUFF_ICON.get(), "恍惚图标");
        add(ModItems.INSAND_BUFF_ICON.get(), "疯狂图标");
        add(ModItems.CHAOS_BUFF_ICON.get(), "混乱图标");
        add(ModItems.DREAM_WISH_BUFF_ICON.get(), "美梦祈愿图标");
        add(ModItems.COOK_BUFF_ICON.get(), "料理图标");
        add(ModItems.SHADOW_SILENCE_BUFF_ICON.get(), "暗影沉默图标");
        add(ModItems.BIND_BUFF_ICON.get(), "束缚图标");
        add(ModItems.BLESSING_OF_CECILIA.get(), "塞西莉娅的加护");
        add("tooltip.pasterdream.blessing_of_cecilia.effect0", "§7▪ §9常驻：强制20%减伤");
        add("tooltip.pasterdream.blessing_of_cecilia.effect1", "§7 受到致命伤害时：");
        add("tooltip.pasterdream.blessing_of_cecilia.effect2", "§7▪ §9获得5秒的伤害免疫");
        add("tooltip.pasterdream.blessing_of_cecilia.effect3", "§7▪ §9并在10秒内获得抗性提升V、生命恢复X、伤害吸收V与速度II");
        add("tooltip.pasterdream.blessing_of_cecilia.lore", "§7§o--我会守护你，直到永远...");
        add(ModItems.FADED_BLESSING_OF_CECILIA.get(), "失色塞西莉娅的加护");
        add("tooltip.pasterdream.faded_blessing_of_cecilia.effect0", "§7▪ §9常驻：抗性提升I");
        add("tooltip.pasterdream.faded_blessing_of_cecilia.hint", "§7或许我们应该亲手将它浸泡于融梦涌泉");
        add(ModItems.PALE_BONE_NEEDLE_TALISMAN.get(), "苍白骨针护符");
        add("tooltip.pasterdream.pale_bone_needle_talisman", "§7在梦境维度死亡时，抵消这次死亡并传送回重生点");

        add(ModItems.SNOW_VOW_HEAD.get(), "雪下的誓言");
        add("tooltip.pasterdream.snow_vow_head.effect.area", "§7▪ §9使附近直径7格内的玩家获得效果");
        add("tooltip.pasterdream.snow_vow_head.effect.bonus", "§7▪ §9+3 幸运值 免疫燃烧与冻结");
        add(ModItems.QYM_BUTTERFLY_STAR_HAIRPIN.get(), "琴雨梦的蝴蝶星发卡");
        add("tooltip.pasterdream.qym_butterfly_star_hairpin.effect", "§7▪ §9所有生物不会主动攻击你");
        add("tooltip.pasterdream.qym_butterfly_star_hairpin.lore", "§7§o如果你瞄准夜空的星星，即使迷失，也是在星辰之间。");
        add(ModItems.HIYORI_BUTTERFLY_HAIRPIN.get(), "妃爱的蝴蝶结发夹");
        add("tooltip.pasterdream.hiyori_butterfly_hairpin.lore", "§7§o-- 因为常轨脱离 所以creative");

        add(ModItems.KAICHU_OMAMORI.get(), "怀中御守");
        add(ModItems.BROOCH_OF_WHITE_ORCHID.get(), "白厄花胸针");
        add("tooltip.pasterdream.brooch_of_white_orchid.effect", "§7▪ §9不再会受到环境造成的降san影响");
        add("tooltip.pasterdream.brooch_of_white_orchid.effect2", "§7▪ §9白厄剑对所有生物增伤50%");
        add("tooltip.pasterdream.brooch_of_white_orchid.effect3", "§7▪ §9白厄剑雨无视受伤无敌帧");
        add("tooltip.pasterdream.brooch_of_white_orchid.flavor", "§o§7 -- 我消逝于无形  此刻享受你应得的荣耀");
        add("tooltip.pasterdream.kaichu_omamori.effect.foxfire", "§7▪ §9对12*12区域范围内生成狐火立场");
        add("tooltip.pasterdream.kaichu_omamori.effect.vulnerable", "§7▪ §9使除玩家外的生物受到20%的易伤和缓慢V效果");
        add("tooltip.pasterdream.kaichu_omamori.effect.regen", "§7▪ §9给予玩家生命恢复");
        add("tooltip.pasterdream.kaichu_omamori.effect.duration", "§7▪ §9持续时间 %s秒");
        add("tooltip.pasterdream.kaichu_omamori.effect.energy_cost", "§f▪ §4融梦能量 -5");
        add("tooltip.pasterdream.kaichu_omamori.effect.cooldown", "§7▪ §9冷却时间 %s 秒");
        add("tooltip.pasterdream.kaichu_omamori.equip", "§7当装备时：");
        add("tooltip.pasterdream.kaichu_omamori.luck", "§7▪ §9+5幸运");
        add("tooltip.pasterdream.kaichu_omamori.hotkey", "§7▪ §9按下 §e%s §9释放狐火立场");
        add("tooltip.pasterdream.kaichu_omamori.flavor", "§o§7 -- Alirea定制物品");
        add("key.pasterdream.kaichu_omamori", "激活怀中御守");

        add(ModItems.ALLKINDS_RING.get(), "万象神戒");
        add("tooltip.pasterdream.allkinds_ring.flavor", "§7§o聚万象之力 铸以此戒");
        add("tooltip.pasterdream.allkinds_ring.lore", "§7§o-- 斯卓贝莉·黛欧恩");
        add("tooltip.pasterdream.allkinds_ring.skill_cd", "§7▪ §9战技冷却时间-5%");
        add("tooltip.pasterdream.allkinds_ring.skill_dmg", "§7▪ §9战技伤害+5%");
        add("tooltip.pasterdream.allkinds_ring.blink_cd", "§7▪ §9瞬身术冷却时间-5%");
        add(ModItems.LIGHT_BUTTERFLY_CURIO.get(), "夜明蝶");
        add("tooltip.pasterdream.light_butterfly_curio.effect", "§7▪ §9自身处于低亮度环境下获得夜视");
        add("tooltip.pasterdream.light_butterfly_curio.energy", "§7▪ §4融梦能量消耗：0.24/min");
        add(ModItems.BRIGHT_BUTTERFLY_CURIO.get(), "光明飞蝶");
        add("tooltip.pasterdream.bright_butterfly_curio.effect.gamma", "§7▪ §9获得夜视效果");
        add("tooltip.pasterdream.bright_butterfly_curio.effect.brightness", "§7▪ §9大幅提升画面亮度");
        add("tooltip.pasterdream.bright_butterfly_curio.effect.darkness_immune", "§7▪ §9免疫黑暗和失明效果");
        add(ModItems.ICE_SHADOW_CURIO.get(), "冰影水晶");
        add("tooltip.pasterdream.ice_shadow_curio.0", "§7▪ §9冰影战锤的战技将额外释放2个撼地水晶");
        add("tooltip.pasterdream.ice_shadow_curio.1", "§7▪ §9释放的撼地水晶间隔受释放距离影响");
        add(ModItems.BOBOJI_CURIO.get(), "啵啵鸡的华丽飞羽");
        add("tooltip.pasterdream.boboji_curio.stat.speed", "§7▪ §9+5% 速度");
        add("tooltip.pasterdream.boboji_curio.stat.range", "§7▪ §9+0.1 瞬身术距离");
        add("tooltip.pasterdream.boboji_curio.stat.cd", "§7▪ §9-0.2 瞬身术冷却");
        add("tooltip.pasterdream.boboji_curio.stat.consume", "§7▪ §9-0.4 瞬身术消耗");
        add("tooltip.pasterdream.boboji_curio.effect.elytra", "§7▪ §9装备鞘翅时不再延长瞬身术冷却时间");
        add("tooltip.pasterdream.boboji_curio.effect.evasion", "§7▪ §9使用瞬身术时可以在0.25秒内回避一次伤害");
        add("tooltip.pasterdream.boboji_curio.effect.share", "§7▪ §9如附近存在玩家则回避时间将延长一倍并分享给附近队友");
        add("tooltip.pasterdream.boboji_curio.effect.flavor", "§7▪ §e瞬身术增加音效和粒子拖尾");
        add("tooltip.pasterdream.boboji_curio.lore", "§7§o谨以此献给曾陪伴我的它");
        add("tooltip.pasterdream.boboji_curio.dedication", "§7§o-- 一只名为啵啵鸡的黄绿色虎皮鹦鹉");
        add("effect.pasterdream.snow_vow_buff", "雪下的誓言");
        add(ModItems.FOURLEAF_CLOVER_CURIO.get(), "幸运四叶草");
        add("tooltip.pasterdream.fourleaf_clover_curio.flavor", "§7§o哪片叶子代表着幸运？");
        add("tooltip.pasterdream.fourleaf_clover_curio.effect.health", "§7▪ §9+1 最大生命值");
        add("tooltip.pasterdream.fourleaf_clover_curio.effect.luck", "§7▪ §9+6 幸运值");
        add("tooltip.pasterdream.worldtree_seedpod.condition", "§7▪ §9在染梦世界 露天环境 身处高度y>160 白天 脚下为染梦世界树树叶");
        add("tooltip.pasterdream.worldtree_seedpod.hunger", "§7▪ §4持续降低饱和度");
        add("tooltip.pasterdream.worldtree_seedpod.energy", "§7▪ §9融梦能量+360/h");
        add("tooltip.pasterdream.worldtree_seedpod.flavor", "§7§o-- 落叶归根 裂荚归冠");
        add("item.pasterdream.duke_coin_curio", "公爵赌场币");
        add("tooltip.pasterdream.duke_coin_curio.effect.luck", "§7▪ §9+7 幸运值");
        add("tooltip.pasterdream.duke_coin_curio.effect.blink_cd", "§7▪ §c+7 瞬身术冷却");
        add("tooltip.pasterdream.duke_coin_curio.flavor", "§7§o-- 7 Days to Die");
        // 项链
        add(ModItems.EMBRYO_NECKLACE.get(), "项链原胚");
        add(ModItems.RABBIT_FOOT_NECKLACE.get(), "雪兔脚项链");
        add("tooltip.pasterdream.rabbit_foot_necklace.effect", "§7▪ §9获得跳跃提升II与+1幸运值");
        add(ModItems.FEATHER_NECKLACE.get(), "轻羽护符");
        add("tooltip.pasterdream.feather_necklace.effect1", "§7▪ §9-0.05 瞬身术消耗");
        add("tooltip.pasterdream.feather_necklace.effect2", "§7▪ §9+0.2 瞬身术距离");
        add(ModItems.HEALTH_NECKLACE.get(), "生命项链");
        add("tooltip.pasterdream.health_necklace.effect1", "§7▪ §9+2最大生命值");
        add("tooltip.pasterdream.health_necklace.effect2", "§7▪ §9获得生命恢复I");
        add(ModItems.FIRE_NECKLACE.get(), "业火项链");
        add("tooltip.pasterdream.fire_necklace.effect1", "§7▪ §9行走会留下火焰");
        add("tooltip.pasterdream.fire_necklace.effect2", "§7▪ §9处于燃烧状态下获得急迫II效果");
        add("tooltip.pasterdream.fire_necklace.effect3", "§7▪ §9获得防火效果");
        add(ModItems.CROSS_NECKLACE.get(), "十字章项链");
        add("tooltip.pasterdream.cross_necklace.effect", "§7▪ §9瞬身术的回避时间延长至0.5秒");
        // 戒指
        add(ModItems.EMBRYO_RING.get(), "戒指原胚");
        add(ModItems.RED_DEW_RING.get(), "红露滴戒指");
        add(ModItems.STRIKE_RING.get(), "强击戒指");
        add("tooltip.pasterdream.red_dew_ring.effect", "§7▪ §9+%s最大生命值");
        add("tooltip.pasterdream.strike_ring.effect", "§7▪ §9+%s攻击伤害");

        // 腰带
        add(ModItems.EMBRYO_BELT.get(), "腰带原胚");
        add(ModItems.NATURE_BELT.get(), "自然腰带");
        add(ModItems.TRAVELER_BELT.get(), "旅者腰带");
        add("tooltip.pasterdream.traveler_belt.effect", "§7▪ §9-0.5 瞬身术消耗");
        add(ModItems.DREAM_TRAVELER_BELT.get(), "迷梦旅者腰带");
        add("tooltip.pasterdream.dream_traveler_belt.effect1", "§7▪ §9+3% 移动速度");
        add("tooltip.pasterdream.dream_traveler_belt.effect2", "§7▪ §9-0.1 瞬身术冷却");

        //原胚
        add("tooltip.pasterdream.introduction.tooltip", "§f▪ §7简介");
        add("tooltip.pasterdream.embryo.tooltip1", "§7钛金属和灵魂精华而制作的饰品原胚");
        add("tooltip.pasterdream.embryo.tooltip2", "§7利用钛的生命亲和性和灵魂精华的精神连携作用");
        add("tooltip.pasterdream.embryo.tooltip3", "§7使得可以通过用物质实体制作成的饰品来强化自身");


        //饰品工具提示
        add("tooltip.pasterdream.lv", "§7§b等级：Lv.%s");
        add("tooltip.pasterdream.only_one.tooltip", "§7该饰品在饰品栏只能装备一个");
        add("tooltip.pasterdream.hold_shift", "§7按住[§rShift§7]键查看简介");

        // 瞬身术
        add("key.pasterdream.blink", "瞬身术");
        add("key.categories.pasterdream", "帕斯特之梦");
        add("effect.pasterdream.cheer_up_buff", "振奋");
        add("effect.pasterdream.lethargy_buff", "不振");
        add("effect.pasterdream.trance_buff", "恍惚");
        add("effect.pasterdream.confusion_buff", "混乱");
        add("effect.pasterdream.insand_buff", "疯狂");
        add("effect.pasterdream.evasion_buff", "回避");
        add("effect.pasterdream.blink_cooldown", "瞬身术冷却");
        add("effect.pasterdream.dyedream_perfume_buff", "染梦香水");
        add("effect.pasterdream.goldenrod_tea_buff", "秋麒麟茶");
        add("effect.pasterdream.cook_buff", "料理");
        add("effect.pasterdream.dream_wish_buff", "美梦祈愿");
        add("effect.pasterdream.cecilia_blessing_buff", "塞西莉娅的加护");
        add("effect.pasterdream.rest_buff", "休憩");
        add("effect.pasterdream.dream_harp_of_wanderer_buff", "漂泊旅者的染梦竖琴");
        add("effect.pasterdream.guard_buff", "守护");
        add("effect.pasterdream.rapid_reaction_buff", "高速反射");
        add("effect.pasterdream.holy_grail_buff", "圣杯");
        add("effect.pasterdream.flare_up_buff", "怒气爆发");
        add("effect.pasterdream.conflict_mark", "纷争标记");
        add("attribute.pasterdream.blink_cd", "瞬身术冷却");
        add("attribute.pasterdream.blink_consume", "瞬身术消耗");
        add("attribute.pasterdream.blink_range", "瞬身术距离");
        add("attribute.pasterdream.san_variability", "理智光环");
        add("attribute.pasterdream.skill_cooldown_rate", "战技冷却倍率");
        add("attribute.pasterdream.skill_damage_rate", "战技伤害倍率");
        add("subtitles.pasterdream.evasion", "瞬身术: 回避");

        // 低San效果指令
        add("command.pasterdream.lowsan.overlay", "低San屏幕覆盖层");
        add("command.pasterdream.lowsan.overlay.set", "低San屏幕覆盖层已%s");
        add("command.pasterdream.lowsan.overlay.get", "%s: %s");
        add("command.pasterdream.lowsan.jitter", "低San画面抖动");
        add("command.pasterdream.lowsan.jitter.set", "低San画面抖动已%s");
        add("command.pasterdream.lowsan.jitter.get", "%s: %s");
        add("command.pasterdream.lowsan.sound", "低San疯狂音效");
        add("command.pasterdream.lowsan.sound.set", "低San疯狂音效已%s");
        add("command.pasterdream.lowsan.sound.get", "%s: %s");
        add("item.pasterdream.star_wish_rod", "占星者的祈愿");
        add("tooltip.pasterdream.star_wish_rod.desc", "§7▪ §9手持钓鱼时深海秘宝额外必出超级变体");
        add("tooltip.pasterdream.star_wish_rod", "§7§O --鱼儿如满天繁星");
        add("command.pasterdream.lowsan.enabled", "启用");
        add("command.pasterdream.lowsan.disabled", "禁用");
        add("gamerule.category.pasterdream", "帕斯特之梦");
        add("gamerule.shadowDifficulty", "世界暗影难度");
        add("gamerule.playerShadowDifficulty", "默认玩家暗影难度");
        // Shadow difficulty command
        add("command.pasterdream.shadowDifficulty.tier.0", "Very Easy (极简单)");
        add("command.pasterdream.shadowDifficulty.tier.1", "Easy (简单)");
        add("command.pasterdream.shadowDifficulty.tier.2", "Normal (普通)");
        add("command.pasterdream.shadowDifficulty.tier.3", "Hard (困难)");
        add("command.pasterdream.shadowDifficulty.set.playerDefault", "默认玩家暗影难度已设置为 %s");
        add("command.pasterdream.shadowDifficulty.set.world", "世界暗影难度已设置为 %s");
        add("command.pasterdream.shadowDifficulty.set.forPlayer", "已将 %s 的暗影难度设置为 %s");
        add("command.pasterdream.shadowDifficulty.source.personal", "个人覆盖");
        add("command.pasterdream.shadowDifficulty.source.playerDefault", "玩家默认");
        add("command.pasterdream.shadowDifficulty.get.playerEffective", "玩家 %s: %s [%s]");
        add("command.pasterdream.shadowDifficulty.get.gameruleSummary", "shadowDifficulty (世界): %s | playerShadowDifficulty (玩家默认): %s");

        // 大地之刃
        add(ModItems.TERRA_BLADE.get(), "大地之刃");
        add("tooltip.pasterdream.terra_blade.skill_name", "战技：§a泰拉剑技");
        add("tooltip.pasterdream.terra_blade.desc1", "§7▪ §9Shift+右键切换剑气状态");
        add("tooltip.pasterdream.terra_blade.desc2", "§7▪ §9剑气状态开启时，每次挥剑释放一道剑气");
        add("tooltip.pasterdream.terra_blade.desc3", "§7▪ §9每道剑气造成 §c2+当前攻击力 §9伤害");
        add("tooltip.pasterdream.terra_blade.desc4", "§7▪ §9每道剑气消耗 §c0.5 §9融梦能量");
        add("tooltip.pasterdream.terra_blade.desc5", "§7▪ §9能量不足时自动退出剑气状态");
        add("tooltip.pasterdream.terra_blade.skill_on", "§a剑气状态：开启");
        add("tooltip.pasterdream.terra_blade.skill_off", "§7剑气状态：关闭");
        add("tooltip.pasterdream.terra_blade.no_energy", "融梦能量不足，剑气状态已关闭");
        add("entity.pasterdream.terrasword_wave", "泰拉剑气");
        add("entity.pasterdream.shaking_crystal", "撼地水晶");

        // 白厄剑
        add(ModItems.WHITE_SWORD.get(), "白厄剑");
        add("tooltip.pasterdream.white_sword.skill_name", "战技：§f白厄剑雨");
        add("tooltip.pasterdream.white_sword.desc1", "§7▪ §9从身后上方召唤剑雨阵列向准星方向发射");
        add("tooltip.pasterdream.white_sword.desc2", "§7▪ §9剑雨造成§d魔法伤害§9，无视护甲并束缚目标");
        add("tooltip.pasterdream.white_sword.desc3", "§7▪ §9每发剑雨造成§c攻击力*0.01§9点伤害");
        add("tooltip.pasterdream.white_sword.desc4", "§7▪ §9命中暗影生物时使其沉默10秒");
        add("tooltip.pasterdream.white_sword.desc5", "§7▪ §9冷却时间：§c2.0秒");
        add("tooltip.pasterdream.white_sword.desc6", "§7▪ §9融梦能量消耗：§c1.5");
        add("tooltip.pasterdream.white_sword.desc7", "§7▪ §9对暗影生物伤害§c+50%");
        add("tooltip.pasterdream.white_sword.skill_passive_name", "被动：§6光之追逐");
        add("tooltip.pasterdream.white_sword.desc8", "§7▪ §9近战攻击时有§c50% §9概率向目标释放追踪剑雨");
        add("message.pasterdream.white_sword.no_energy", "融梦能量不足");
        add("entity.pasterdream.white_sword_rain_projectile", "白厄剑雨");

        // 影刃
        add(ModItems.SHADOW_SWORD.get(), "影刃");
        add("tooltip.pasterdream.shadow_sword.skill_name", "战技：§5夜魔斩击");
        add("tooltip.pasterdream.shadow_sword.skill_desc1", "§7▪ §9右键消耗§c5 §9精神值（不足时消耗§c5生命值§9，不致死）");
        add("tooltip.pasterdream.shadow_sword.skill_desc2", "§7▪ §9下一次斩击变为§d魔法伤害§9，造成§c攻击力×(2.5-SAN%)§9伤害");
        add("tooltip.pasterdream.shadow_sword.skill_desc3", "§7▪ §9冷却时间：§c2.0秒");
        add("tooltip.pasterdream.shadow_sword.passive_name", "被动：§5暗影");
        add("tooltip.pasterdream.shadow_sword.desc1", "§7▪ §9持剑者的精神值越低 造成的伤害越高");
        add("tooltip.pasterdream.shadow_sword.desc2", "§7▪ §9最高攻击力§c+75%§9 攻击速度§c+50%");
        add("tooltip.pasterdream.shadow_sword.desc3", "§7▪ §4当没有精神值可供消耗时 挥砍会反噬自身生命");
        add("tooltip.pasterdream.shadow_sword.flavor", "§7§o——梦魇渐渐变得尖锐，铸成了这把利剑");
        add("message.pasterdream.shadow_sword.san_disabled", "§c影刃拒绝了你");

        // Ice Shadow Hammer
        add("tooltip.pasterdream.ice_shadow_hammer.skill_name", "战技：§3依诺的撼地");
        add("tooltip.pasterdream.ice_shadow_hammer.0", "§7▪ §9使用后起跳并在目标地点召唤§3撼地水晶");
        add("tooltip.pasterdream.ice_shadow_hammer.1", "§7▪ §9水晶持续§c3秒§9，对周围敌人施加§d混乱§9与§b冻结§9效果");
        add("tooltip.pasterdream.ice_shadow_hammer.2", "§7▪ §9造成三段伤害：§c攻击力×0.4§9、§c攻击力×0.6§9、§c攻击力×1.0§9(爆炸)");
        add("tooltip.pasterdream.ice_shadow_hammer.3", "§7▪ §9爆炸后为释放者施加§e伤害吸收I");
        add("tooltip.pasterdream.ice_shadow_hammer.4", "§7右键地面以释放技能");
        add("tooltip.pasterdream.ice_shadow_hammer.5", "§7▪ §9冷却时间：§c4秒");
        add("tooltip.pasterdream.ice_shadow_hammer.cost", "§7▪ §4融梦能量消耗：§c0.5");
        add("message.pasterdream.ice_shadow_hammer.no_energy", "§c融梦能量不足");

        // 堕落者之印
        add(ModItems.SEAL_OF_THE_CORRUPTED.get(), "堕落者之印");
        add("tooltip.pasterdream.seal_of_the_corrupted.effect1", "§7▪ §9不会受到低精神值带来的负面效果");
        add("tooltip.pasterdream.seal_of_the_corrupted.effect2", "§7▪ §9暗影生物对你保持中立");
        add("tooltip.pasterdream.seal_of_the_corrupted.effect3", "§7▪ §9因低精神值诞生的暗影生物为你而战");
        add("tooltip.pasterdream.seal_of_the_corrupted.flavor", "§7§o——我将成为你的阴影 一并体会你的痛苦");

        // 泰拉浮岛模型
        add(ModItems.TERRA_FLOATING_ISLAND.get(), "泰拉浮岛模型");
        add("tooltip.pasterdream.terra_floating_island.desc1", "§7▪ §9泰拉剑气消耗减少至 §c0.4§9");
        add("tooltip.pasterdream.terra_floating_island.desc2", "§7▪ §9泰拉剑气攻击力 +30%");
        add("tooltip.pasterdream.terra_floating_island.desc3", "§7▪ §9泰拉剑气攻击无视无敌帧");

        add("message.pasterdream.lost_sword_tomb.lack_strength", "你的力气不足以拔出这柄剑");
        add("tooltip.pasterdream.lost_sword_tomb", "§7既然拔不出来，那就带着石头用吧！");

        // 草莓甜心
        add(ModItems.STRAWBERRY_HEART.get(), "草莓甜心");
        add(ModItems.SLIVER_BELL.get(),"银色铃铛");
        add("tooltip.pasterdream.sliver_bell", "§7§O --希安喵！");
        add("tooltip.pasterdream.strawberry_heart.desc1", "§7▪ §9右键演奏 §4消耗1融梦能量");
        add("tooltip.pasterdream.strawberry_heart.desc2", "§7▪ §9为范围内玩家回复4点生命 并给予短暂生命恢复，力量和速度效果");
        add("tooltip.pasterdream.strawberry_heart.desc3", "§7▪ §7冷却: 12秒 §7| §6范围: 8格");
        add("tooltip.pasterdream.strawberry_heart.desc4", "§7▪ §d立即恢复2点理智，并给予8秒3.6理智光环，期间你免疫恍惚效果");
        add("tooltip.pasterdream.strawberry_heart.no_energy", "融梦能量不足");

        // 追忆宝石
        add(ModItems.MEMORY_GEM.get(), "追忆宝石");

        // 追忆物品
        add(ModItems.EMPTY_VESSEL.get(), "空容器");
        add("tooltip.pasterdream.empty_vessel.1", "§7▪ §9使用后幸运+10持续3分钟");
        add("tooltip.pasterdream.empty_vessel.2", "§7▪ §999%+20%SAN §c1%-100%SAN");
        add("tooltip.pasterdream.empty_vessel.3", "§7GQ2529的专属遗物");
        add("tooltip.pasterdream.empty_vessel.4", "§6PasterDream开发者");

        // 重生之梦水晶
        add(ModItems.REBIRTH_DREAM_CRYSTAL.get(), "重生之梦水晶");
        add("tooltip.pasterdream.rebirth_dream_crystal.1", "§7\"旧梦已醒，我们重新入梦。\"");
        add("tooltip.pasterdream.rebirth_dream_crystal.2", "§7\"欢迎回来，寻梦者。\"");
        add("tooltip.pasterdream.rebirth_dream_crystal.3", "§7▪ §9+12 理智光环");
        add("tooltip.pasterdream.rebirth_dream_crystal.4", "§7▪ §9每10秒获得12秒回避");
        add("tooltip.pasterdream.rebirth_dream_crystal.5", "§7ShiLiuYinYu的专属遗物。");
        add("tooltip.pasterdream.rebirth_dream_crystal.6", "§7shift右键释放遗物中的灵魂。");
        add("tooltip.pasterdream.rebirth_dream_crystal.7", "§6PasterDream: Reborn开发者");

        add(ModItems.SOUL_GEM_OF_AKIZUKI_AYANE.get(), "魔法使的灵魂石");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.1", "§7\"我将我遗留的魔法赠予你，让它替我与你同行，见证你所见的梦幻\"");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.2", "§7一位大魔法使为了保护自己残破的灵魂而制作的实验品，当她的灵魂重归完整后，这份物质化的灵魂也失去了意义。");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.3", "§7▪ §9每秒恢复 1.5 融梦能量");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.4", "§7▪ §9战技伤害 +40%");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.5", "§7▪ §9魔法伤害 +50%");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.6", "§f▪ §4融梦能量消耗翻倍");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.7", "§f▪ §4融梦能量低于 30 时，受到的伤害翻倍");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.8", "§7绫苒的专属遗物。");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.9", "§7shift右键恢复全部融梦能量，2 分钟内不消耗融梦能量（冷却 3 分钟）");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.10", "§6PasterDream: Reborn 文案");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.cooldown", "§c灵魂石的力量仍在冷却中");

        add(ModItems.MAGNIFYING_GLASS_OF_SHERRY.get(), "大侦探的放大镜");
        add("tooltip.pasterdream.magnifying_glass_of_sherry.1", "§7\"我的超级智慧告诉我该使用我的超级力量了！\"");
        add("tooltip.pasterdream.magnifying_glass_of_sherry.2", "§7其实旭当时也不知道做什么遗物，不过他当时很喜欢橘雪莉，所以就这样了。");
        add("tooltip.pasterdream.magnifying_glass_of_sherry.3", "§7▪ §9挖掘速度 +50%");
        add("tooltip.pasterdream.magnifying_glass_of_sherry.4", "§7▪ §9近战伤害 +100%");
        add("tooltip.pasterdream.magnifying_glass_of_sherry.5", "§7▪ §9未使用武器时，伤害 +200%");
        add("tooltip.pasterdream.magnifying_glass_of_sherry.6", "§f▪ §4融梦能量恒定为 0");
        add("tooltip.pasterdream.magnifying_glass_of_sherry.7", "§7bei_xu的专属遗物。");
        add("tooltip.pasterdream.magnifying_glass_of_sherry.8", "§6PasterDream: Reborn 文案");

        // 琴雨梦装备
        add(ModItems.QYM_CAT_EARS.get(), "琴雨梦的猫耳发卡");
        add("tooltip.pasterdream.qym_cat_ears.san", "§7▪ §9精神值始终为精神值上限");
        add("tooltip.pasterdream.qym_cat_ears.set_bonus", "§7套装效果： §d沉眠长虹的少女");
        add("tooltip.pasterdream.qym_cat_ears.damage_reduce", "§f▪ §9获得80%伤害减免");
        add("tooltip.pasterdream.qym_cat_ears.magic_damage", "§f▪ §9攻击附带目标当前生命值5%的魔法伤害");
        add("tooltip.pasterdream.qym_cat_ears.dream_evasion", "§f▪ §9在梦境中时免疫一切伤害");
        add(ModItems.QYM_WIND_SHIRT.get(), "琴雨梦的凌风衬衫");
        add("tooltip.pasterdream.qym_wind_shirt.flight", "§7▪ §9获得飞行能力");
        add("tooltip.pasterdream.qym_wind_shirt.fall_immune", "§7▪ §9免疫摔落伤害");
        add(ModItems.QYM_SWAYING_SKIRT.get(), "琴雨梦的摇曳裙摆");
        add("tooltip.pasterdream.qym_swaying_skirt.energy", "§7▪ §9融梦能量始终为融梦能量上限");
        add(ModItems.QYM_CLOUD_BOOTS.get(), "琴雨梦的踏云之靴");
        add("tooltip.pasterdream.qym_cloud_boots.blink", "§7▪ §9瞬身术不再有冷却时间");
        add("tooltip.pasterdream.qym_cloud_boots.skill_cd", "§7▪ §9战技不再有冷却时间");

        // 附魔
        add("enchantment.pasterdream.swift_strike", "疾风连击");
        add("enchantment.pasterdream.shelter", "庇护");
        add("enchantment.pasterdream.swift_strike.desc", "增加攻击速度。");
        add("enchantment.pasterdream.shelter.desc", "降低所受伤害。");

        // 反击戒指
        add(ModItems.COUNTER_RING.get(), "反击戒指");
        add(ModItems.MELT_DREAM_ENERGY_RING.get(), "融梦光环戒指");
        add("tooltip.pasterdream.counter_ring.effect1", "§7▪ §9成功回避伤害时获得反击I (0:10)");
        add("tooltip.pasterdream.counter_ring.effect2", "§7▪ §9下一次攻击：攻击力+3 战技伤害+50%，命中后效果解除");
        add("tooltip.pasterdream.melt_dream_energy_ring.effect1", "§7▪ §9梦境内融梦能量+0.3/min");
        add("tooltip.pasterdream.melt_dream_energy_ring.effect2", "§7▪ §9梦境外融梦能量+0.15/min");
        add("effect.pasterdream.counter_attack_buff", "反击");
        add("effect.pasterdream.memento_buff", "寻梦者的祈愿");

        // 战争旗帜
        add(ModItems.WAR_FLAG.get(), "战争旗帜");
        add("tooltip.pasterdream.war_flag.effect1", "§7▪ §9击杀敌人获得战意效果，每击杀一个敌人增加1级，最高3级");
        add("tooltip.pasterdream.war_flag.effect2", "§7▪ §9效果持续期间增加攻击并提升回血速度");
        add("tooltip.pasterdream.war_flag.effect3", "§7▪ §9该效果可以共享给周围玩家");
        add("tooltip.pasterdream.war_flag.description", "§7§O --这面旗帜上流淌的暗影似乎在述说遥远的过往……");
        add("effect.pasterdream.war_flag_buff", "战意");
        add("effect.pasterdream.bind_buff", "束缚");
        add("effect.pasterdream.shadow_silence_buff", "暗影沉默");
        add("effect.pasterdream.restrainmove_block_buff", "行动抑制");
        add("effect.pasterdream.oppression_buff", "压抑");
        // TODO: shadow_spyon_buff ("暗影窥视") 待后续搬运
        add("item.pasterdream.shadow_magicball_spawn_egg", "暗影魔法球刷怪蛋");
        add("item.pasterdream.shadow_tune_totem_spawn_egg", "暗影符文塔刷怪蛋");
        add("item.pasterdream.aaroncos_left_hand_spawn_egg", "亚伦柯斯之触-左手刷怪蛋");
        add("item.pasterdream.aaroncos_right_hand_spawn_egg", "亚伦柯斯之触-右手刷怪蛋");
        add("entity.pasterdream.aaroncos_left_hand", "亚伦柯斯之触-左手");
        add("entity.pasterdream.aaroncos_right_hand", "亚伦柯斯之触-右手");
        add("entity.pasterdream.shadow_magicball", "暗影魔法球");
        add("entity.pasterdream.shadow_tune_totem", "暗影符文塔");
        add("message.pasterdream.shadow_tune_totem.charging", "暗影符文塔正在蓄能");
        add("message.pasterdream.shadow_tune_totem.about_to_explode", "暗影符文塔即将发生爆破");
        add("block.pasterdream.shadow_vortex", "暗影漩涡");
        add("block.pasterdream.shadow_hand_trap", "暗影之手陷阱");
        add("block.pasterdream.shadow_brazier", "暗影火盆");
        add("block.pasterdream.shadow_blast_furnace_core", "暗影高炉核心");
        add("tooltip.pasterdream.shadow_hand_trap", "§7--你挖下来这个做什么？");
        add("tooltip.pasterdream.shadow_blast_furnace_core.1", "在完成多方块结构时");
        add("tooltip.pasterdream.shadow_blast_furnace_core.2", "使用对应蓝图右键此核心以进行搭建");
        add("message.pasterdream.shadow_brazier.need_candle", "需要用阴影蜡烛点燃火盆");
        add("message.pasterdream.shadow_brazier.lit", "火盆被点燃，但带来的并不是光明...");
        add("message.pasterdream.shadow_brazier.shadow_spread", "阴影从四周蔓延开来");
        add("message.pasterdream.shadow_brazier.extinguished", "火盆燃尽熄灭，坠落在地上破碎");
        add("message.pasterdream.shadow_brazier.key_dropped", "在火盆的夹层里掉落出一把钥匙");

        // 破损传送门消息
        add("message.pasterdream.broken_portal.too_low", "地牢的结构被破坏，传送门没有反应");
        add("message.pasterdream.broken_portal.creative_repaired", "创造模式：核心无条件修复");
        add("message.pasterdream.broken_portal.need_materials", "双手持§e黑金属§f和§e影灯§f以修复核心");
        add("message.pasterdream.broken_portal.repaired", "核心已修复");
        add("message.pasterdream.broken_portal.lack_knowledge", "你还不知道如何修复这个核心");

        //卡莱的调料瓶
        add(ModItems.CALAIS_SPICE_BOTTLE.get(), "卡莱的调料瓶");
        add("tooltip.pasterdream.calais_spice_bottle.effect1", "§7▪ §9食用速度+40%");
        add("tooltip.pasterdream.calais_spice_bottle.effect2", "§7▪ §9装备时获得卡莱料理X增益，攻击命中消耗层数，砍至消失后需进食恢复，进食积累层数（每3饥饿度+1，上限Ⅹ级）");
        add("tooltip.pasterdream.calais_spice_bottle.effect3", "§7▪ §9每消耗1层会获得随机效果");
        add("effect.pasterdream.calais_spice_bottle_buff", "卡莱料理");


        //鬼魂之面
        add(ModItems.GHOST_FACE.get(), "鬼魂之面");
        add("tooltip.pasterdream.ghost_face.effect.1", "§7▪ §9使用远程武器时，额外发射一发射弹，20%再发射一发");
        add("tooltip.pasterdream.ghost_face.effect.2", "§7▪ §9远程武器射弹无视无敌帧");

        // 进度 - 故事
        add("advancements.pasterdream.story.root.title", "帕斯特之梦");
        add("advancements.pasterdream.story.root.description", "以心之祈愿为始");
        add("advancements.pasterdream.story.pure_and_flawless.title", "纯洁无暇");
        add("advancements.pasterdream.story.pure_and_flawless.description", "获得一朵苍白雪莲");
        add("advancements.pasterdream.story.use_pale_boneneedle.title", "哦，痛！");
        add("advancements.pasterdream.story.use_pale_boneneedle.description", "使用苍白骨针在梦境中醒来");
        add("advancements.pasterdream.story.human_falls_out_of_dream.title", "人类坠出梦境");
        add("advancements.pasterdream.story.human_falls_out_of_dream.description", "你一时分不清你的痛是摔出来的还是被骨针扎出来的");
        add("advancements.pasterdream.story.dyedream_crack.title", "染梦裂隙");
        add("advancements.pasterdream.story.dyedream_crack.description", "在下一次的梦中醒来，裂隙会与你的梦境共鸣。");
        add("advancements.pasterdream.story.dyedream_world.title", "哥德堡安眠曲");
        add("advancements.pasterdream.story.dyedream_world.description", "访问染梦之境");
        add("advancements.pasterdream.story.dream_fertilizer.title", "不可食用果冻");
        add("advancements.pasterdream.story.dream_fertilizer.description", "制作并使用衍梦肥泥挥洒梦境的土地");
        add("advancements.pasterdream.story.dyedream_dust.title", "把梦捧在手心里!");
        add("advancements.pasterdream.story.dyedream_dust.description", "从这个世界探寻它的踪迹");
        add("advancements.pasterdream.story.melt_dream_crystal_fragment.title", "熔融于梦");
        add("advancements.pasterdream.story.melt_dream_crystal_fragment.description", "收集到一颗融梦水晶碎片");
        add("advancements.pasterdream.story.glass_jar_of_dream_juice.title", "好梦在何方");
        add("advancements.pasterdream.story.glass_jar_of_dream_juice.description", "制作并饮用梦境果汁，在睡梦中前往染梦之境");
        add("advancements.pasterdream.story.dream_accumulator.title", "尘埃落定");
        add("advancements.pasterdream.story.dream_accumulator.description", "使用蓄梦池收集染梦粉尘");
        add("advancements.pasterdream.story.melt_dream_liquid_bucket.title", "百味杂陈");
        add("advancements.pasterdream.story.melt_dream_liquid_bucket.description", "获得一桶融梦涌泉");
        add("advancements.pasterdream.story.create_pliers.title", "太陽とレインボ一");
        add("advancements.pasterdream.story.create_pliers.description", "制作一把园艺钳");
        add("advancements.pasterdream.story.create_research_table.title", "下午茶时光");
        add("advancements.pasterdream.story.create_research_table.description", "制作研究台以开展研究");
        add("advancements.pasterdream.story.galaxy_jelly.title", "星辰凝胶");
        add("advancements.pasterdream.story.galaxy_jelly.description", "获得星河果冻，一种能在梦境中的深海秘宝和箱子中找到的果冻，它能让你飞至云霄。");
        add("advancements.pasterdream.story.eat_galaxy_jelly_on_high_height.title", "太空专业配音员");
        add("advancements.pasterdream.story.eat_galaxy_jelly_on_high_height.description", "在建筑高度上限吃下星河果冻……卡兹这下有伴了……");
        add("advancements.pasterdream.story.look_at_pink_sheep.title", "方块人会梦见粉红羊吗");
        add("advancements.pasterdream.story.look_at_pink_sheep.description", "方块人当然只会梦见方块羊");

        // 进度 - 冒险拓展
        add("advancements.pasterdream.adventure.forgotten_sword_tomb.title", "被遗忘的剑冢");
        add("advancements.pasterdream.adventure.forgotten_sword_tomb.description", "发现失落剑冢，深藏于丛林深处的剑冢");
        add("advancements.pasterdream.story.get_the_lost_sword.title", "遗忘之剑");
        add("advancements.pasterdream.story.get_the_lost_sword.description", "获得失落之剑，它与丛林的力量似乎共鸣……");
        add("advancements.pasterdream.story.lamp_shadow_root.title", "侵染教堂");
        add("advancements.pasterdream.story.lamp_shadow_root.description", "阅读寻梦者笔记\"侵染教堂-黑面\"");
        add("advancements.pasterdream.story.bastion_guard.title", "据点守卫");
        add("advancements.pasterdream.story.bastion_guard.description", "完成暮影之笼的据点守卫事件");
        add("advancements.pasterdream.story.enter_lamp_shadow_world.title", "灯影之下");
        add("advancements.pasterdream.story.enter_lamp_shadow_world.description", "深入世界中最黑暗的一面");
        add("advancements.pasterdream.story.dig_up_a_tomb.title", "邻家有女初长成，力拔山兮气盖世");
        add("advancements.pasterdream.story.dig_up_a_tomb.description", "你……你把什么东西挖下来了？？？");
        add("advancements.pasterdream.new_standard_sword_drawing.title", "新概念拔剑");
        add("advancements.pasterdream.new_standard_sword_drawing.description", "因为你拔不出这把剑，气急败坏之下，索性你把你的敌人当作了开石头的工具！");
        add("advancements.pasterdream.craft_kusanagi.title", "丛林之力");
        add("advancements.pasterdream.craft_kusanagi.description", "做出草雍，一把蕴含了丛林力量的剑，它饥渴地寻求着亡魂……");
        add("advancements.pasterdream.get_murakumo_kusanagi.title", "刀下亡魂");
        add("advancements.pasterdream.get_murakumo_kusanagi.description", "通过杀戮，让草雍得到进一步进化");

        add("advancements.pasterdream.adventure.find_desert_fortress.title", "沙漠英灵");
        add("advancements.pasterdream.adventure.find_desert_fortress.description", "发现沙漠遗迹，里面住着一个还没完成遗愿的英灵，或许你应当考虑先杀死附近的敌人……");
        add("advancements.pasterdream.adventure.get_desert_sword.title", "英灵之剑");
        add("advancements.pasterdream.adventure.get_desert_sword.description", "从英灵的手中得到朔漠大剑，完成他的遗愿让这把武器得以进化");
        add("advancements.pasterdream.adventure.get_chenjingmen_desert_sword.title", "遗志已成");
        add("advancements.pasterdream.adventure.get_chenjingmen_desert_sword.description", "完成英灵的遗愿让朔漠大剑得以进化为『沉荆门』朔漠，看来那位曾经的英雄最终得以安息了……");

        // 进度 - 下界拓展
        add("advancements.pasterdream.get_molten_gold_ingot.title", "真金不怕火炼");
        add("advancements.pasterdream.get_molten_gold_ingot.description", "获得炙焰金锭，熔炼自下界的一种烫手的金矿石");
        add("advancements.pasterdream.craft_hellfire_sword.title", "炙热之剑");
        add("advancements.pasterdream.craft_hellfire_sword.description", "制作一把狱炎剑，它与一种极为可怕的亡灵生物掉落物有所共鸣");
        add("advancements.pasterdream.craft_inferno_sword.title", "炼狱之火");
        add("advancements.pasterdream.craft_inferno_sword.description", "为狱炎剑镶嵌一个下界之星来让它完成最终进化");

        // 进度 - 农牧业拓展
        add("advancements.pasterdream.get_deep_sea_treasure.title", "海之馈赠");
        add("advancements.pasterdream.get_deep_sea_treasure.description", "获得一份海洋的秘宝，它来自于深海和染梦冻洋");
        add("advancements.pasterdream.get_super_deep_sea_treasure.title", "To wish upon a satellite...");
        add("advancements.pasterdream.get_super_deep_sea_treasure.description", "获得更高级的海洋秘宝，你应该为此找到一个更强大的钓竿……");
        add("advancements.pasterdream.get_blue_dew.title", "海洋之泪");
        add("advancements.pasterdream.get_blue_dew.description", "获得湛蓝露滴，它来自于深海的海洋秘宝");
        add("advancements.pasterdream.get_blue_heart_of_the_sea.title", "深蓝之心");
        add("advancements.pasterdream.get_blue_heart_of_the_sea.description", "获得蔚蓝海洋之心，你会知道什么武器需要用上它的……");
        add("advancements.pasterdream.get_beihairuo_tide_sword.title", "潮汐之神");
        add("advancements.pasterdream.get_beihairuo_tide_sword.description", "获得『北海若』引潮，它掌握着潮涌和激流的力量……");

        // 进度 - 染梦珍藏
        add("advancements.pasterdream.root_dyedream_treasure.title", "染梦珍藏");
        add("advancements.pasterdream.root_dyedream_treasure.description", "在粉红的世界又会有什么宝藏。");
        add("advancements.pasterdream.get_sand_of_time.title", "你的时间非常值钱");
        add("advancements.pasterdream.get_sand_of_time.description", "获得时之沙，一个可以转换昼夜的沙漏，不过它工作完以后会休息……");
        add("advancements.pasterdream.get_broken_hero_sword.title", "日食遗物");
        add("advancements.pasterdream.get_broken_hero_sword.description", "获得断裂英雄剑。等等？这个玩意不是打一个大扑棱蛾子掉的吗？");
        add("advancements.pasterdream.get_terra_sword.title", "和乌龟赛跑");
        add("advancements.pasterdream.get_terra_sword.description", "获得大地之刃，你应该考虑会不会被从天而降的乌龟砸死。");
        add("advancements.pasterdream.get_boboji_curios.title", "啵啵鸡之梦");
        add("advancements.pasterdream.get_boboji_curios.description", "获得啵啵鸡的华丽飞羽。");
        add("advancements.pasterdream.get_allkinds_ring.title", "谁教你这么加点的？");
        add("advancements.pasterdream.get_allkinds_ring.description", "获得万象之戒，一个什么属性都加一点的戒指。");
        add("advancements.pasterdream.get_hiyori_butterfly_hairpin.title", "实妹相伴");
        add("advancements.pasterdream.get_hiyori_butterfly_hairpin.description", "获得妃爱的蝴蝶结发夹。");
        add("advancements.pasterdream.get_snow_vow_head.title", "永冻雪花");
        add("advancements.pasterdream.get_snow_vow_head.description", "获得雪下的誓言，配合博人传食用更佳。");
        add("advancements.pasterdream.get_star_wish_rod.title", "鱼儿如浩空繁星");
        add("advancements.pasterdream.get_star_wish_rod.description", "获得占星者的祈愿，去寻求海中更诱人的宝藏吧。");
        add("advancements.pasterdream.get_blessing_of_cecilia.title", "我恨你是一块木头");
        add("advancements.pasterdream.get_blessing_of_cecilia.description", "获得塞西莉娅的加护，比不死图腾更强大的保命饰品。");
        add("advancements.pasterdream.get_light_butterfly_curio.title", "黑夜蝴蝶");
        add("advancements.pasterdream.get_light_butterfly_curio.description", "获得夜明蝶，提供夜视的特殊蝴蝶，也许，你应当从最深邃的黑暗寻求升级它的东西。");
        add("advancements.pasterdream.get_qym_doll.title", "可爱即是正义");
        add("advancements.pasterdream.get_qym_doll.description", "获得娇小琴雨梦玩偶，请对她好一点。");
        add("advancements.pasterdream.get_terra_floating_island.title", "为什么不试试泰拉瑞亚呢？");
        add("advancements.pasterdream.get_terra_floating_island.description", "获得泰拉浮岛模型。请输入文本。");
        add("advancements.pasterdream.get_dream_harp_of_wanderer.title", "漂泊旅者之赠礼");
        add("advancements.pasterdream.get_dream_harp_of_wanderer.description", "获得漂泊旅者的染梦竖琴，列车漂泊旅者留下的赠礼。");
        add("advancements.pasterdream.get_worldtree_seedpod.title", "落叶归根 裂荚归冠");
        add("advancements.pasterdream.get_worldtree_seedpod.description", "获得世界树种荚。");
        add("advancements.pasterdream.get_sliver_bell.title", "希安喵！");
        add("advancements.pasterdream.get_sliver_bell.description", "获得银色铃铛，当你把从深邃黑暗找到的光明与之结合，它方能展现它的真实形态。");
        add("advancements.pasterdream.get_kaichu_omamori.title", "金狐的愿望");
        add("advancements.pasterdream.get_kaichu_omamori.description", "获得怀中御守，当你真正将正在睡觉的金色狐狸的梦变为现实，她会给予你这个特殊的纪念品。");


        // 帕秋莉宝典
        add(ModItems.SENIORS_DREAM_BOOK.get(), "旧梦归引");
        add("pasterdream.seniors_dream", "旧梦归引");
        add("pasterdream.seniors_dream.catalyst.test", "测试");
        add("pasterdream.seniors_dream.catalyst.test.desc", "一个测试分类。");
        add("pasterdream.seniors_dream.entry.welcome", "欢迎");
        add("pasterdream.seniors_dream.entry.welcome.page0", "如果你能看到这段文字，说明宝典工作正常！");
        add("pasterdream.seniors_dream.landing_text", "欢迎来到《帕斯特之梦：重生》，此模组是《帕斯特之梦》的重制版。在保证原有内容的前提下，对游戏内容和流程进行一定优化，通过此书可以了解到缺失的引导和物品信息。那么寻梦者，愿星河入梦，枕畔有风。");

        // 分类
        add("pasterdream.seniors_dream.category.rules", "机制介绍");
        add("pasterdream.seniors_dream.category.rules.desc", "本篇介绍模组中的核心机制。");
        add("pasterdream.seniors_dream.category.rules_shadow_difficulty", "暗影难度系统");
        add("pasterdream.seniors_dream.category.rules_shadow_difficulty.desc", "随着你在疯狂的泥潭中越陷越深，阴影中的存在也开始注意到你。它们并非幻觉——当你的精神防线足够薄弱时，暗影生物便会在你周围的黑暗中显形。你的理智越是不堪一击，它们出现得就越频繁，种类也越是可怖。$(br2)--暗影侵袭");

        // 融梦能量
        add("pasterdream.seniors_dream.entry.meltdream", "融梦能量");
        add("pasterdream.seniors_dream.entry.meltdream.page0", "在屏幕的左下角，有一个能量条一般的图标，即为融梦能量。对于拥有战技的武器，右键可以消耗融梦能量来触发相应战技。");
        add("pasterdream.seniors_dream.entry.meltdream.page1", "融梦能量条界面展示。");
        add("pasterdream.seniors_dream.entry.meltdream.page2", "融梦能量的获取途径：$(br)每开启一个融梦水晶箱可以获得2点融梦能量；$(br)食用部分食物可以获得一定融梦能量；$(br)佩戴部分饰品也可以恢复融梦能量，主要为融梦光环戒指和世界树种荚。");
        add("pasterdream.seniors_dream.entry.meltdream.page3", "与融梦能量相关的部分物品展示。");

        // san值
        add("pasterdream.seniors_dream.entry.san", "san值");
        add("pasterdream.seniors_dream.entry.san.page0", "或许你也已经注意到了，在你屏幕的右下角有一个大脑一样的图标，即为san值。");
        add("pasterdream.seniors_dream.entry.san.page1", "san值界面展示。");
        add("pasterdream.seniors_dream.entry.san.page2", "san值效果影响：$(br)san>=90%% 获得振奋$(br)san<=60%% 获得不振$(br)san<=40%% 获得恍惚$(br)san<=20%% 获得疯狂I$(br)san<=10%% 获得疯狂II$(br)san<=1%%  获得疯狂III$(br2)每个buff的详细效果请自行查阅对应的章节");
        add("pasterdream.seniors_dream.entry.san.page3", "环境带来的san值变化：$(br)san的变化受到群系类型和光照强度的影响，计算公式为：$(br)总变化率 = (理智光环 / 1200) + 群系影响 + (光照等级 - 7) * 0.0001");
        add("pasterdream.seniors_dream.entry.san.page4", "群系的影响主要表现为：$(br)在深暗之域和灯影之下的群系会降低玩家san值；$(br)在染梦世界的群系会提升玩家的san值；$(br)在其他群系不会产生强干扰。$(br2)理智光环为n则代表玩家在除开其它影响（群系影响和光照等级）的条件下一分钟内会增加n点理智。");
        add("pasterdream.seniors_dream.entry.san.page5", "其他掉san的情况：$(br)处于阴影生物的疯狂光环下；$(br)手持带有疯狂光环的物品；$(br)某些物品的技能也会消耗san值。$(br2)主动恢复san值的方法：$(br)使用特定物品或食物；$(br)装备带有理智光环的饰品；$(br)拥有料理和休憩buff；$(br)让自己处于亮度较高的环境。");
        add("pasterdream.seniors_dream.entry.san.page6", "与san值相关的部分物品展示。");

        // 瞬身术
        add("pasterdream.seniors_dream.entry.evasion", "瞬身术");
        add("pasterdream.seniors_dream.entry.evasion.page0", "默认按C键释放。使用瞬身术将会消耗2点饱食度，进行一段快速移动，且重置摔落高度。使用瞬身术会有短暂的无敌帧。默认冷却时间1秒，创造模式没有冷却时间。");
        add("pasterdream.seniors_dream.entry.evasion.page1", "在陆地上使用，会向准星所指向位置进行一段瞬时移动；$(br)在陆地潜行状态下使用，会向准星指向相反的位置进行一段瞬时移动；$(br)在水中使用，则准星在竖直方向的偏移不会有影响。");

        // 钓鱼
        add("pasterdream.seniors_dream.entry.fishing", "钓鱼");
        add("pasterdream.seniors_dream.entry.fishing.page0", "在闲暇时光，我们也许会坐在水边，拿着钓竿，接受海洋的恩赐。现在在海洋附近钓鱼有可能钓出来自海洋深处的宝藏——深海秘宝，哪怕在梦境的辽阔海洋中，你也能甩出浮标，获得宝藏。你的幸运值越高，就越有可能获得深海秘宝，有的时候，你可能会获得闪耀着奇特光芒的深海秘宝……");
        add("pasterdream.seniors_dream.entry.fishing.page1", "也许，在梦境中钓出来的深海秘宝能获得一些较为独特的宝藏，比如色彩缤纷的水晶碎片，以及闪烁着光芒的一桶泉水……");

        // 暗影难度系统
        add("pasterdream.seniors_dream.entry.shadow_difficulty", "暗影难度");
        add("pasterdream.seniors_dream.entry.shadow_difficulty.page0", "暗影的难度等级由玩家和世界两部分组成。如果存在的效果与玩家的交互有关，则优先按照玩家难度决定，否则按照世界难度决定。");
        add("pasterdream.seniors_dream.entry.shadow_difficulty.page1", "暗影难度主要有四个等级：$(br)极简单：暗影侵袭几乎不会出现，即使出现也极其微弱。$(br)简单：san<20%%时出现暗影侵袭。$(br)普通：san<60%%时开始出现暗影侵袭，怪物掉落物更加丰富。$(br)困难：san<82.5%%时就会出现暗影侵袭，其生命和攻击力翻倍，战利品也会翻倍。");

        add("pasterdream.seniors_dream.entry.shadow_creature_behavior", "暗影生物特殊行为详解");
        add("pasterdream.seniors_dream.entry.shadow_creature_behavior.page0", "当暗影难度不低于“简单”这一等级时，部分暗影生物会有特殊技能：$(br)暗影傀儡将会使用冲击波，造成更高伤害。$(br)暗影之手的攻击会夺取玩家的理智。$(br)恐怖尖喙会发出吼叫，降低玩家的理智值（包括玩家现实中的理智）。$(br)悲泣尖啸鬼魂会召唤普通怨魂。");

        // 此梦先觉
        add("pasterdream.seniors_dream.category.foretold_dream", "此梦先觉");
        add("pasterdream.seniors_dream.category.foretold_dream.desc", "原版维度中的新增机制与知识。");
        add("pasterdream.seniors_dream.category.foretold_dream_items", "新增物品");
        add("pasterdream.seniors_dream.category.foretold_dream_items.desc", "原版维度中新增的物品。");
        add("pasterdream.seniors_dream.category.foretold_dream_structures", "新增结构");
        add("pasterdream.seniors_dream.category.foretold_dream_structures.desc", "原版维度中的新增结构。");

        // 钛金
        add("pasterdream.seniors_dream.entry.titanium", "钛金");
        add("pasterdream.seniors_dream.entry.titanium.page0", "在主世界的深层地下，有时会发现一种名为钛金的矿石，需要比铁更强大更坚硬的材料制作成的镐来采集，和大部分矿石一样，可以通过时运附魔获得更多粗矿。使用钛金制成的工具会比钻石制作而来的工具稍稍趁手一点，或许，你可以在钛金制品中引入来自梦境的力量……");
        add("pasterdream.seniors_dream.entry.titanium.page1", "——拥有极强耐腐蚀性的钛金，即使久经风霜仍熠熠生辉；就像那段未醒的梦，时隔许久再次回味也宛如昨日。");

        // 炙焰金
        add("pasterdream.seniors_dream.entry.molten_gold", "炙焰金");
        add("pasterdream.seniors_dream.entry.molten_gold.page0", "当你在下界的深层探索时，可以发现一种闪耀着奇特光芒，类似黄金的矿石，这种奇特的矿石只需要铁镐就能挖掘，也可以享受时运的加成。当你拿着炙焰金制作成的工具时，你可以右键消耗你的饱食度来获取迅捷效果。制成的剑和镐好像也有更强的升级……");
        add("pasterdream.seniors_dream.entry.molten_gold.page1", "——在烈火与热浪中锻成的一种金属，如果真的想要获取它的力量，或许需要身赴火海的决心。");

        // 灵魂粉尘
        add("pasterdream.seniors_dream.entry.soul_dust", "灵魂粉尘");
        add("pasterdream.seniors_dream.entry.soul_dust.page0", "下界的灵魂沙峡谷有时会出现夹杂着一些白色物质的奇怪的灵魂土，它们被称作灵魂矿土，把它们挖掉会获得灵魂粉尘。这些矿土可以空手挖掘，不过，用工具挖掘不仅更快，而且使用有时运附魔的工具还能获得更多灵魂粉尘。灵魂粉尘可以用来合成灵魂精华，而灵魂精华可以用来制作项链原胚、戒指原胚和腰带原胚，进而加工成各种饰品。");
        add("pasterdream.seniors_dream.entry.soul_dust.page1", "——愿在下界永无安宁的亡魂得到解脱，愿行于梦中的寻梦者给予逝者以生者的宁静。");

        // 回响升级套件
        add("pasterdream.seniors_dream.entry.sculk_upgrade", "回响升级套件");
        add("pasterdream.seniors_dream.entry.sculk_upgrade.page0", "覆灭的王朝无法重见天日，寄存的力量却源源不绝。现在，你已经击溃了这里最强的守卫，试着将它注入进下界合金防具之中，让这来自远古文明的禁忌力量为你所用吧。升级后的回响套装不仅更加坚实，还会给予你来自回响的庇护，让你不再受到幽匿的恐吓，哪怕面对更加深邃的黑暗，回响的力量也会给予你一点勇气。");
        add("pasterdream.seniors_dream.entry.sculk_upgrade.page1", "——穿上回响套装的时候，感受到的不只是力量，还有一股更为深邃更为古老的黑暗与恐惧缠绕在我的耳边，挥之不去，它就像噩梦一般，在呼唤我去拥抱它……");

        // 狱炎剑
        add("pasterdream.seniors_dream.entry.inferno_sword", "『融骸』狱炎剑");
        add("pasterdream.seniors_dream.entry.inferno_sword.page0", "利用远古残骸，配上烈焰粉和炙焰金块来强化炙焰金剑后，便将它升级成狱炎剑。它的锋利程度和钻石剑不相上下，而它也会给予敌人长时间的烈焰炙烤，如果敌人原本就在承受着火焰的灼烧，那么，这把剑会延长他们受到折磨的时间。");
        add("pasterdream.seniors_dream.entry.inferno_sword.page1", "强化后，这把剑获名『融骸』，它变得更加轻便，使用者可以主动释放战技，让这把剑获得「融骸斩击」，使它下一击更致命，如果敌人将会受到长时间的火焰灼烧，融骸斩击还会熄灭他们身上的火焰，让他们的行动更加迟缓。$(br2)——浴身于战火，铸炼于战砧。");

        // 草薙
        add("pasterdream.seniors_dream.entry.kusanagi", "『天丛云』草薙");
        add("pasterdream.seniors_dream.entry.kusanagi.page0", "这把名为「草薙」的剑如同钻石所制造而成的剑一般锋利，同时，这把剑也会让它所伤害的生物染上痛苦的剧毒。而当有两百个生命成为这把剑的刀下亡魂时，它便会展现出它作为神器的完全威能——草薙不仅更加锋利，而它所伤害的生命会遭受更长时间的剧毒摧残，持有者可以主动释放战技，使其获得「极致锋利」，令这把神剑接下来的一击更为致命。");
        add("pasterdream.seniors_dream.entry.kusanagi.page1", "——这把剑虽说叫天丛云剑，但是本模组没有八岐大蛇，这把剑也不能用来当作圣遗物召唤英灵，更不能解放宝具给敌对生物来个八岐怒涛。");

        // 引潮
        add("pasterdream.seniors_dream.entry.tide_sword", "『北海若』引潮");
        add("pasterdream.seniors_dream.entry.tide_sword.page0", "某一日，你感觉到深海秘宝中发现的蔚蓝露滴与你合成的引潮剑产生了共鸣，于是，你将蔚蓝露滴进一步打磨，试着镶嵌在引潮剑中。现在，这把剑被冠以了古代的海神之名，不仅更加锋利，持有者还可以主动释放「涌流剑技」，在水中快速冲刺。另外，如果你的敌人在水中和你战斗，那么你释放涌流剑技之后，下一次斩击将会更加致命。");
        add("pasterdream.seniors_dream.entry.tide_sword.page1", "——九河横流归海若，孤峰俯眺慑天吴。");

        // 朔漠
        add("pasterdream.seniors_dream.entry.desert_sword", "『沉荆门』朔漠");
        add("pasterdream.seniors_dream.entry.desert_sword.page0", "埋葬于荒漠的战士并未安息，现在，该你去完成他的遗志了。满足他的遗愿后，他会帮你释放朔漠大剑的全部力量，这把古老的英雄之剑也恢复了它的真名——『沉荆门』朔漠，它不仅恢复了以往的锋利，还可以赋予它的持有者「绝地反击」的能力。");
        add("pasterdream.seniors_dream.entry.desert_sword.page1", "启动绝地反击后，你会短暂获得更多的生命来忍受攻击，而你受的伤越多，你接下来的反击一刀将会越致命。$(br2)——回首妖氛未扫，问人间、英雄何处？");

        // 深海秘宝
        add("pasterdream.seniors_dream.entry.deep_sea_treasure", "深海秘宝");
        add("pasterdream.seniors_dream.entry.deep_sea_treasure.page0", "当你在各个维度钓鱼时，有时会发现这种精致的小箱子，打开它会有一些小宝藏，而你的幸运程度则会影响开出的宝藏稀有度。另外，有些秘宝似乎散发着光芒，看样子里面的宝藏也不一般。除了一些常规的物品外，据说还有一把来自深海的武器，以及一些源于梦中的珍藏。");
        add("pasterdream.seniors_dream.entry.deep_sea_treasure.page1", "或许，可以试试去梦里钓一钓好运？$(br2)——融梦于海，等等，什么叫开出来了一只美西螈？");

        // 热能匕首
        add("pasterdream.seniors_dream.entry.thermal_dagger", "热能匕首");
        add("pasterdream.seniors_dream.entry.thermal_dagger.page0", "当你打开深海秘宝时，有可能会获得这种奇怪的小匕首，当主手持有这把匕首时，你的游泳速度会更快。这把匕首的速度和锋利度都略胜铁剑一筹，有趣的是，你拿这把武器去攻击敌人时，会有滋滋冒油的炙烤声。");
        add("pasterdream.seniors_dream.entry.thermal_dagger.page1", "过去曾有天外来客坠落在海洋中，在迷航中寻求回家的道路，这把奇特的匕首想必就是出自于那位天外来客之手。$(br2)——温馨提示：本匕首并不能拿去单挑死神利维坦，离开海洋请自查是否感染卡拉病毒。");

        // 福灵果冻
        add("pasterdream.seniors_dream.entry.fortune_jelly", "福灵果冻");
        add("pasterdream.seniors_dream.entry.fortune_jelly.page0", "在探索这个世界的过程中，你应该已经在某些宝箱中找到了这种绿色的果冻。吃下这种QQ弹弹的小东西会让幸运女神多予你一份眷顾。");
        add("pasterdream.seniors_dream.entry.fortune_jelly.page1", "虽然没能研究明白这种果冻是如何制成的，但是貌似能利用炼药的方法提取一下其中的好运。");

        // 研钵
        add("pasterdream.seniors_dream.entry.mortar", "研钵");
        add("pasterdream.seniors_dream.entry.mortar.page0", "古代的炼金术士用研钵这种工具来辅助他们炼制药剂，帮助他们追求点石成金的第五元素。当然，无需研究炼金术的你可以用这种工具制作一些存在于梦境中的染料。$(br2)你可以在研钵里存入液体。在液体区域右键存入液体，左键取出液体。你需要把它放在工作台上来制作染梦染料和其他可以用研钵制作的东西。制作完成后，研钵可以回收，但是研钵里存储的液体会被消耗");
        add("pasterdream.seniors_dream.entry.mortar.page1", "——过去的人们用它来追寻太一之奇迹，而现在行于梦幻的人们用它来提纯梦境的神秘");

        // 精铸工坊
        add("pasterdream.seniors_dream.entry.weapon_workshop", "精铸工坊");
        add("pasterdream.seniors_dream.entry.weapon_workshop.page0", "随着工具的升级，你的工作台慢慢开始难以胜任工具制作的任务。于是，你将目光转向村庄中铁匠铺的设备，不过你似乎无法将其复现……$(br2)或许，你需要稍稍破费一下，和村庄的工具匠多交易几次，等到他成为了大师后，应该就会愿意卖给你精铸工坊的搭建蓝图了吧。$(br2)另外，通过这种工坊，似乎还可以利用你找到的强化石。");
        add("pasterdream.seniors_dream.entry.weapon_workshop.page1", "——早在中世纪就存在这种设备和技术，虽然古老，但是它们足够我用来打造更强大的工具了。");

        // 染梦空岛
        add("pasterdream.seniors_dream.entry.dyedream_sky_island", "染梦空岛");
        add("pasterdream.seniors_dream.entry.dyedream_sky_island.page0", "初来之时，你会在主世界发现这种粉红色的空岛，它的存在似乎有那么一些不合理，就像是……梦一般。登上这座岛，就能看到一道奇怪的裂隙。第一次接触没有直接反应，但会让你做一个奇怪的梦。醒来后，你发现有一份笔记神秘地出现在你的口袋里，你或许应当在阅读完这份神秘的笔记后，重新去触碰那道裂隙……$(br2)——如果说这座空岛就是梦境力量的映射，那当穿过裂隙，我究竟身处的是「现实」还是「梦境」？");
        add("pasterdream.seniors_dream.entry.dyedream_sky_island.page1", "");

        // 失落剑冢
        add("pasterdream.seniors_dream.entry.lost_sword_tomb", "失落剑冢");
        add("pasterdream.seniors_dream.entry.lost_sword_tomb.page0", "失落剑冢是一个只能在丛林群系发现的结构，中间有一把插在石头里面的石中剑，需要用大力出奇迹的方法才能拔出来，除非……$(br2)获取到的失落之剑可以赋予丛林的力量升级为草薙，不过这柄剑似乎有点嗜杀，试着满足它，也许可以引出它所没表现出来的更多强大力量……");
        add("pasterdream.seniors_dream.entry.lost_sword_tomb.page1", "");

        // 渔夫小屋
        add("pasterdream.seniors_dream.entry.fisherman_hut", "渔夫小屋");
        add("pasterdream.seniors_dream.entry.fisherman_hut.page0", "在主世界的沙滩群系，你有时会发现这种渔夫小屋，这是离开村庄，独自在海边谋生的渔夫村民的家。你可以在屋子里的宝箱找到渔夫村民在海中打捞出来的宝藏。$(br2)当然，有时候里面也会有深海秘宝，或许渔夫不知道怎么打开，所以只是珍藏着，真是买椟还珠。");
        add("pasterdream.seniors_dream.entry.fisherman_hut.page1", "——我相信他们一定有着其他理由才会独自来到海边生活，或许，这片深海里还有更深的秘密。");

        // 沙漠遗迹
        add("pasterdream.seniors_dream.entry.desert_ruins", "沙漠遗迹");
        add("pasterdream.seniors_dream.entry.desert_ruins.page0", "在主世界的沙漠里，有时会发现这种奇怪的遗迹，看来似乎已经被掠夺者和尸壳占领，或许你可以在探索之余顺手处理掉这些敌人……");
        add("pasterdream.seniors_dream.entry.desert_ruins.page1", "");
        add("pasterdream.seniors_dream.entry.desert_ruins.page2", "传说中，那里埋葬着一位未完成遗志的战士，被深埋于地下，等待着能有一位迷途的旅行者前来，来完成他的遗愿，继承他的衣钵……");
        add("pasterdream.seniors_dream.entry.desert_ruins.page3", "");

        // 暮影之笼
        add("pasterdream.seniors_dream.entry.twilight_lantern", "暮影之笼");
        add("pasterdream.seniors_dream.entry.twilight_lantern.page0", "卡莱在前往灯影之下后，使用暗影的力量离开，虽然她违背了与暗影的约定，独自将前往黑暗梦境的后手封存在了下界的最顶端，并用染梦床将其镇压。但笼子终究无法完全封锁住暗影的力量。现如今暗影已经溢出，而你也在了解到足够多的隐秘后，决定前往永暗的梦境中一探究竟。");
        add("pasterdream.seniors_dream.entry.twilight_lantern.page1", "");
        add("pasterdream.seniors_dream.entry.twilight_lantern.page2", "既然你意已决，那么就去用融梦水晶碎片点燃笼子吧，这时笼子会更溢出更多暗影力量，而当你见证这场暗影怪物的狂欢之后，于暮影长床上入眠，便可进入那流淌黑暗的梦。");
        add("pasterdream.seniors_dream.entry.twilight_lantern.page3", "这将是一场无比艰险的恶战，所以，千万要做好见证最为阴暗的梦境的准备。");

        // 此梦先觉-新增buff效果
        add("pasterdream.seniors_dream.category.foretold_dream_buffs", "新增buff效果");
        add("pasterdream.seniors_dream.category.foretold_dream_buffs.desc", "模组中新增的buff效果。");

        add("pasterdream.seniors_dream.entry.rest", "休憩");
        add("pasterdream.seniors_dream.entry.rest.page0", "休憩是一种可以为玩家逐渐恢复san值的buff效果，每级增加1.2理智光环。$(br2)通过在原版的床上躺下、身处娇小琴雨梦玩偶或者营火附近，玩家都可以获得该buff。");

        add("pasterdream.seniors_dream.entry.cheer_up", "振奋");
        add("pasterdream.seniors_dream.entry.cheer_up.page0", "当玩家san值>=90%%时获得的buff效果，具体效果为：$(br)瞬身术冷却时间-10%%；$(br)移动速度+0.05；$(br)攻击速度+0.05；$(br)技能冷却时间-10%%。");

        add("pasterdream.seniors_dream.entry.lethargy", "不振");
        add("pasterdream.seniors_dream.entry.lethargy.page0", "当玩家san值<=60%%时获得的buff效果，具体效果为：$(br)瞬身术冷却时间+50%%；$(br)移动速度-10%%；$(br)攻击速度-0.1；$(br)技能冷却时间+20%%。");

        add("pasterdream.seniors_dream.entry.trance", "恍惚");
        add("pasterdream.seniors_dream.entry.trance.page0", "当玩家san值<=40%%时获得的buff效果，具体效果为：$(br)瞬身术冷却时间+100%%；$(br)移动速度-20%%；$(br)攻击速度-0.2，攻击力-1；$(br)技能冷却时间+50%%。");

        add("pasterdream.seniors_dream.entry.insand", "疯狂");
        add("pasterdream.seniors_dream.entry.insand.page0", "当玩家san值<=20%%时获得的buff效果，具体效果为：$(br)瞬身术冷却时间+200%%；$(br)移动速度-30%%；$(br)攻击速度-0.4，攻击力-2；$(br)攻击和触及距离-0.2。");
        add("pasterdream.seniors_dream.entry.insand.page1", "持有此状态时，视野会不受控制地抖动，随着疯狂状态等级的进一步提升，玩家画面抖动幅度会增大。");
        add("pasterdream.seniors_dream.entry.insand.page2", "当暗影难度处于“简单”及以上等级的时候，疯狂III效果会使玩家持续失去生命值。");

        add("pasterdream.seniors_dream.entry.cook", "料理");
        add("pasterdream.seniors_dream.entry.cook.page0", "通过食用部分本模组内食物，以及有联动的模组食物，可以获得此buff。每级增加1.2理智光环。");

        add("pasterdream.seniors_dream.entry.chaos", "混乱");
        add("pasterdream.seniors_dream.entry.chaos.page0", "部分武器和道具对敌人施加的buff效果，具体效果为：$(br)当玩家持有混乱效果时，玩家视野会发生类似“疯狂”效果下的抖动。$(br)当其他生物获得混乱效果时，它们的朝向会发生随机性的变化，同时被按压在地上，短暂失去行动能力。");

        add("pasterdream.seniors_dream.entry.sculk_armor", "幽匿回响");
        add("pasterdream.seniors_dream.entry.sculk_armor.page0", "当玩家穿戴全套回响套装时获得此buff。具体效果为：$(br)最大生命值+4；$(br)当玩家身处Y<0的环境时，玩家周围出现绿色粒子效果，并获得抗性提升buff；");
        add("pasterdream.seniors_dream.entry.sculk_armor.page1", "免疫幽匿的黑暗恐惧；$(br)受到黑暗buff时，改为获得1分钟的速度II和抗性提升II；$(br)监守者不会攻击持有此buff的玩家。");

        add("pasterdream.seniors_dream.entry.dyedream_armor", "染梦守护");
        add("pasterdream.seniors_dream.entry.dyedream_armor.page0", "当玩家穿戴全套染梦套装时获得此buff。具体效果为：$(br)最大生命值+4；$(br)每30秒获得伤害吸收II；$(br)获得染梦工具增强效果：染梦合金工具和融梦水晶工具的伤害增加50%%。");

        add("pasterdream.seniors_dream.entry.dream_wish", "美梦祈愿");
        add("pasterdream.seniors_dream.entry.dream_wish.page0", "当玩家饮用梦境果汁后获得的buff效果。具体效果为：$(br)持有此buff睡觉时会进入染梦世界");

        add("pasterdream.seniors_dream.entry.shadow_silence", "暗影沉默");
        add("pasterdream.seniors_dream.entry.shadow_silence.page0", "一般情况是玩家赋予怪物的负面效果，可以让暗影生物的特殊技能暂时失效。在特殊情况下，某些最强大的暗影生物似乎也能被沉默？");

        add("pasterdream.seniors_dream.entry.bind", "束缚");
        add("pasterdream.seniors_dream.entry.bind.page0", "一般情况是玩家赋予怪物的负面效果。拥有此效果时，对应生物暂时无法移动。");

        // 染梦世间
        add("pasterdream.seniors_dream.category.dyedream_world", "染梦世间");
        add("pasterdream.seniors_dream.category.dyedream_world.desc", "起源之梦。当你穿过染梦裂隙时，眼前豁然开朗，映入你眼帘的是充满飞花和粉红的奇幻世界，眼前的所有景象如同一位少女最绮丽的幻想具现而成，这就是你寻梦之旅的第一站，开始你追寻梦幻的旅途吧，寻梦者。");
        add("pasterdream.seniors_dream.category.dyedream_world_items", "方块与物品");
        add("pasterdream.seniors_dream.category.dyedream_world_items.desc", "染梦维度中的方块与物品。");
        add("pasterdream.seniors_dream.category.dyedream_world_structures", "结构");
        add("pasterdream.seniors_dream.category.dyedream_world_structures.desc", "染梦维度中的结构。");

        // 染梦世间-方块与物品
        add("pasterdream.seniors_dream.entry.melt_dream_crystal_chest", "融梦水晶箱");
        add("pasterdream.seniors_dream.entry.melt_dream_crystal_chest.page0", "在你探索这个梦境的各个结构时，经常会发现这种精致的小箱子，你只需要和它互动，箱子就会自行开启。你也许会注意到，箱子震动的次数越多，开出来的战利品越好，或许你应该想办法让自己更幸运，然后来开一开箱子。$(br2)据说梦中有一种特殊的工具，可以凝聚来自你身上的染梦力量，为打开的融梦水晶箱重新填满宝藏……");
        add("pasterdream.seniors_dream.entry.melt_dream_crystal_chest.page1", "——融梦水晶箱不会变成宝箱怪，也不会有传送陷阱，放心开吧。");

        add("pasterdream.seniors_dream.entry.melt_dream_crystal_fragment", "融梦水晶碎片");
        add("pasterdream.seniors_dream.entry.melt_dream_crystal_fragment.page0", "凝聚了巨大梦境力量的水晶散落于梦境各地，比如染梦世界树，逐梦列车中。当然，某些箱子里偶尔也能发现这种碎片。$(br2)这种碎片不仅可以镶嵌在染梦合金的工具上，将后者强化为融梦工具，而且可以放置下来，变成一个水晶簇状的小实体。或许，你需要用它来触及梦境中无比深沉的黑暗……");
        add("pasterdream.seniors_dream.entry.melt_dream_crystal_fragment.page1", "——蕴含着巨大能量的水晶碎片，似乎可以用它来更好地利用梦境的力量，或者……");

        add("pasterdream.seniors_dream.entry.qym_doll", "娇小琴雨梦玩偶");
        add("pasterdream.seniors_dream.entry.qym_doll.page0", "有时你打开融梦水晶箱时，会发现这么一种软乎乎的玩偶，它参照着那位最早的寻梦少女形象而设计出来，极其可爱的外观让你能在这个玩偶周围感到轻松，从而有效缓解你的精神压力。$(br2)当然，你也可以和玩偶互动(rua)，每一次互动之后，你的精神压力都会减少一点。");
        add("pasterdream.seniors_dream.entry.qym_doll.page1", "——这是什么？可爱捏，rua一下。");

        add("pasterdream.seniors_dream.entry.galaxy_jelly", "星河果冻");
        add("pasterdream.seniors_dream.entry.galaxy_jelly.page0", "这种奇怪的小果冻会在被你食用之后让你“嗖”地一下飞起来，当然，你不用担心会摔死，它会给予你缓降效果，能保证你安全落地，总而言之，这是一个探索梦境，翻山越岭的家庭必备好帮手。$(br2)注意：严禁连续食用，否则有摔死的风险。");
        add("pasterdream.seniors_dream.entry.galaxy_jelly.page1", "——仰望天空，不禁让人感叹星河广阔神秘。我虽不是嫦娥可以一步登天，但我愿意成为万户，行走在追逐星河的路上。");

        add("pasterdream.seniors_dream.entry.life_crystal", "生命水晶");
        add("pasterdream.seniors_dream.entry.life_crystal.page0", "当你进入逐梦列车和生命水晶洞窟中，也许会注意到这种心形的水晶，和它互动可以增加你的生命上限，不过可惜的是，你只能使用它一次，好消息是，这种水晶可以挖掘后带走，当你之后再发现这种水晶后或许可以考虑一下把它拿回去做装饰品。不过，生命水晶的效果似乎会随着你的死亡而流失……");
        add("pasterdream.seniors_dream.entry.life_crystal.page1", "——Heart Breaker");

        add("pasterdream.seniors_dream.entry.machine_light_wing", "机械光翼");
        add("pasterdream.seniors_dream.entry.machine_light_wing.page0", "当你好奇地装上这双从融梦水晶箱中获得的翅膀时，它会立刻吸收你的融梦能量并让你浮空，而且它会不断让你维持飞行状态。除非你的能量耗尽或者是摘下它，否则它可不会让你安稳地踩在地面上。$(br2)虽说大家都想要一双飞行的翅膀，但是这对光翼的能量消耗速度实在太快，还是必要时再使用吧。");
        add("pasterdream.seniors_dream.entry.machine_light_wing.page1", "——三叶草：你双脚离地了，对吧？");

        add("pasterdream.seniors_dream.entry.qym_armor_set", "琴雨梦套装");
        add("pasterdream.seniors_dream.entry.qym_armor_set.page0", "这些是过去曾经属于那位追寻梦幻的少女的随身物品，穿戴上这些衣物时，一股梦幻般的感觉游走在你的身体内，你或许可以永远保持清醒，或许会获得无穷无尽的染梦能量，或许可以自由自在地在天空飞翔。");
        add("pasterdream.seniors_dream.entry.qym_armor_set.page1", "而你穿上全部套装后，还会在梦境中自行闪避所有针对你的攻击，不过，如果你以凡人之躯行于大地的话，是没有办法获得这位前辈寻梦者的随身物品的，你或许需要足以创造万物的神明之力……$(br2)——最初的寻梦少女沉眠于长虹中，静待后来的入梦之人探寻未知。");

        add("pasterdream.seniors_dream.entry.pale_boneneedle", "苍白骨针");
        add("pasterdream.seniors_dream.entry.pale_boneneedle.page0", "你在雪山上发现了一种具有强烈香气的苍白雪莲，这种香气具有明显的提神效果。已经成为寻梦者，曾经在梦中迷失的你想到一个妙计，将苍白雪莲的花冠和骨质针具绑在一起，剧烈的疼痛和香气的刺激想必能让你迅速清醒，离开梦境。$(br2)而如果把来自末地的珍珠和古城的回响融入到苍白骨针中，也许会有奇效……");
        add("pasterdream.seniors_dream.entry.pale_boneneedle.page1", "——无论梦幻是洁净的染梦还是深邃的阴影，梦醒之时，我们将会重逢。");

        add("pasterdream.seniors_dream.entry.dyedream_perfume", "染梦香水");
        add("pasterdream.seniors_dream.entry.dyedream_perfume.page0", "在染梦的梦境中，地上的植物吸引了你的注意力，那香气让你有了一种新思路：或许可以把它们收集起来，用它们制作香水……$(br2)当你饮用这独特的饮料后，一定时间内幻翼不会来打扰你，并且梦境的力量让你就像睡了一觉一样，接下来几天都不会有幻翼像苍蝇一样飞在你头顶了……");
        add("pasterdream.seniors_dream.entry.dyedream_perfume.page1", "——Q：为什么要喝香水？A：你都在做梦了就不要考虑这些小事了~");

        // 大地之刃
        add("pasterdream.seniors_dream.entry.terra_blade", "大地之刃");
        add("pasterdream.seniors_dream.entry.terra_blade.page0", "当你在现实与炼狱中获得了八把古代遗存之物所制造的剑后，你或许有一种其他办法来让它们发挥更大的作用……$(br2)这八把剑与梦境中的涌泉合而为一的神剑——大地之刃，具有极致的伤害和不菲的力量，你可以在融梦能量充足时开启剑气状态，每一次斩击都会释放出无比致命的剑气来杀死远处的敌人，当然，记得随时关注你的融梦能量……");
        add("pasterdream.seniors_dream.entry.terra_blade.page1", "——此身剑所天成，数剑合一，铸为英雄之刃。");

        // 银河果冻
        add("pasterdream.seniors_dream.entry.milky_way_jelly", "银河果冻");
        add("pasterdream.seniors_dream.entry.milky_way_jelly.page0", "或许终有一天，你需要以凡人之躯跨越某些常规办法下无法逾越的天险，登顶世界的顶层，而原本从染梦世界中获得的星河果冻性能显然不太好，这时，你将目光放到了染梦世界的云朵和现实的羽毛上……");
        add("pasterdream.seniors_dream.entry.milky_way_jelly.page1", "当你食用了这块全新的果冻后，会有一股力量将你送到世界的顶层，并赋予你缓降效果，让你慢慢落地，这样一来，某些难以跨越的屏障想必也无法阻挡你了。$(br2)——当大地不再是人类的阻碍，星海便是我们的目标");

        // 染梦教堂
        add("pasterdream.seniors_dream.entry.dyedream_church", "染梦教堂");
        add("pasterdream.seniors_dream.entry.dyedream_church.page1", "当你在染梦世界探索时，有可能会发现这种漂浮在天空中的神秘教堂，你可以在这个教堂内发现一个融梦水晶箱，讲经台上还有来自寻梦者前辈们的随记，或许你应该仔细检查一下教堂内部的边边角角，说不定能有意外之喜……");
        add("pasterdream.seniors_dream.entry.dyedream_church.page2", "——这些教堂出自何人之手，他们信奉何种宗教，有待后来的寻梦者找到答案……");

        // 逐梦列车
        add("pasterdream.seniors_dream.entry.dream_train", "逐梦列车");
        add("pasterdream.seniors_dream.entry.dream_train.page1", "在梦境中，有时你会听到列车的呼啸声，那便是逐梦列车，当你听见列车的声音时，脑海中会自然而然地闪过列车的大概方位，你可以登上这列梦幻的列车，探索它隐藏的奥秘……$(br2)如果条件允许的话，或许应该从头到尾完全探索一下列车？");
        add("pasterdream.seniors_dream.entry.dream_train.page2", "——追逐着梦幻的列车汽笛响彻地平，我们的旅途也将继续。");

        // 染梦世界树
        add("pasterdream.seniors_dream.entry.dyedream_worldtree", "染梦世界树");
        add("pasterdream.seniors_dream.entry.dyedream_worldtree.page1", "在染梦世界的平原，长着这样一种参天大树，这里是融梦能量最为密集的区域，可以见到大量的融梦水晶箱，传说世界树的种荚有着奇妙的效果，你或许应该大胆地探索，无需拘泥于小节，这样说不定可以探索到更多的隐秘……$(br2)传说在最初曾有一真一假两种世界树，不过现在仅仅剩下真世界树了，当你找不到世界树时，或许可以想一想最初的那位创造梦境之人……");
        add("pasterdream.seniors_dream.entry.dyedream_worldtree.page2", "——连通天地的世界树，支撑着整个梦境的美好。");

        // 花园解密-迷梦冶梦莲
        add("pasterdream.seniors_dream.entry.garden_misty_dreaming_lotus", "花园解密-迷梦冶梦莲");
        add("pasterdream.seniors_dream.entry.garden_misty_dreaming_lotus.page1", "你可以在染梦平原发现这种奇怪的遗迹，顶上种有一种名为「冶梦莲」的美丽花朵，在花朵下方有着来自最早的寻梦者的提示，你或许应该根据她的提示来寻找对应的植物……");
        add("pasterdream.seniors_dream.entry.garden_misty_dreaming_lotus.page2", "或许应该从琴雨梦的提示中的铃、云、流明和小伞来寻找答案？");
        add("pasterdream.seniors_dream.entry.garden_misty_dreaming_lotus.page3", "解开谜题后获得的花朵似乎可以用于前往风的梦境，也可以去寻找传说中的神狐……");

        // 花园解密-凛冽雪绒花
        add("pasterdream.seniors_dream.entry.garden_nippy_edelweiss", "花园解密-凛冽雪绒花");
        add("pasterdream.seniors_dream.entry.garden_nippy_edelweiss.page1", "在染梦世界的雪原，你可能会发现这种奇特的结构，在下方的书桌上，你会找到来自最初那位寻梦者的笔记，你需要根据她的提示来找到对应的植物和生物。");
        add("pasterdream.seniors_dream.entry.garden_nippy_edelweiss.page2", "似乎需要从梦染、灯、奇异几个字入手，去找找看吧。");
        add("pasterdream.seniors_dream.entry.garden_nippy_edelweiss.page3", "解开谜题后获得的花朵似乎有着奇特的低温触感……");

        // 花园解密-九尾狐
        add("pasterdream.seniors_dream.entry.garden_golden_fox", "花园解密-九尾狐");
        add("pasterdream.seniors_dream.entry.garden_golden_fox.page1", "在梦境中，有时你会看见这种有着狐狸样子的雕像的遗迹，上方的寻梦者永恒书卷中有着琴雨梦给予的提示，或许需要一种奇特的莲花，也许你应该先去看看其他的花园解密……");
        add("pasterdream.seniors_dream.entry.garden_golden_fox.page2", "当你唤醒九尾狐后，她会满足你一个愿望，至于满足愿望的方法，那位少女也在书中一并告诉你了……");
        add("pasterdream.seniors_dream.entry.garden_golden_fox.page3", "——那奇妙的神狐，她将满足你的愿望，或是给予你御邪的护符。");

        // 染梦实验室
        add("pasterdream.seniors_dream.entry.dream_lab", "染梦实验室");
        add("pasterdream.seniors_dream.entry.dream_lab.page1", "在染梦的平原，你会发现这种前辈的遗迹，外观上看并不起眼，可是内部别有洞天……");
        add("pasterdream.seniors_dream.entry.dream_lab.page3", "看起来，这似乎是前辈寻梦者进行某种实验所做出的尝试，那名为蓄梦池的装置似乎是前辈们尝试的最终结果，至于他们为什么离开，就不得而知了……");
        add("pasterdream.seniors_dream.entry.dream_lab.page4", "——这里是寻梦者们驾驭梦境力量的尝试，是探寻梦境真实的一大步……");

        // 融梦涌泉井
        add("pasterdream.seniors_dream.entry.melt_dream_well", "融梦涌泉井");
        add("pasterdream.seniors_dream.entry.melt_dream_well.page1", "在染梦世界的平原，有时会分布着这样一种水井，或许你应该纵身一跃，亲自到井底一探究竟……");
        add("pasterdream.seniors_dream.entry.melt_dream_well.page3", "在这种奇妙的水井中，流淌的并非一般的清水，而是一种一直在变幻色彩的神秘液体，这种液体充斥着融梦能量，你可以尝试着效仿前辈寻梦者，找到一种可以利用它的办法……");
        add("pasterdream.seniors_dream.entry.melt_dream_well.page4", "井内的一些小角落还有可能会有一些箱子，积极地寻找一下吧。");
        add("pasterdream.seniors_dream.entry.melt_dream_well.page5", "——井下静静地闪耀着流光，照耀着地下的生灵。");

        // 染梦小客栈
        add("pasterdream.seniors_dream.entry.dyedream_inn", "染梦小客栈");
        add("pasterdream.seniors_dream.entry.dyedream_inn.page1", "染梦世界存在着这种小客栈，它不知出自何人之手，却能为疲惫的梦境旅行者们提供歇脚的地方。");
        add("pasterdream.seniors_dream.entry.dyedream_inn.page2", "上层的箱子里似乎还有前辈们留下来的物品，你当然可以拿它们为你的探索做些补给。下层不仅有丰富的矿石，那书卷中还记载着两位前辈寻梦者那比肩神话的尝试……");

        // 染梦穿云塔
        add("pasterdream.seniors_dream.entry.dyedream_cloud_tower", "染梦穿云塔");
        add("pasterdream.seniors_dream.entry.dyedream_cloud_tower.page1", "在染梦的梦境中，这种高耸入云的尖塔很容易引起你的注意力。它有两种不同的建筑样式，每座塔的底部房间里都能找到先来者的游记……$(br2)说到塔，爬到顶端似乎是必备的环节，塔顶应该会有一些战利品，也许你应该仔细在塔顶寻找一下？不过，塔的样式不同，战利品的位置似乎也会有所差异……");
        add("pasterdream.seniors_dream.entry.dyedream_cloud_tower.page2", "——爬到塔顶→鸟瞰点同步→信仰之跃→bang！同步失败~");

        // 浮空神庙
        add("pasterdream.seniors_dream.entry.floating_temple", "浮空神庙");
        add("pasterdream.seniors_dream.entry.floating_temple.page1", "在染梦世界的天空中，会有这样的建筑被云朵衬托着，浮在天上。这座古典时代的神庙让所有进入这座建筑被它的神圣肃穆所打动。你会在神庙里发现一个融梦水晶箱，以及一份前辈们的书卷……");
        add("pasterdream.seniors_dream.entry.floating_temple.page2", "——当神庙飘浮于染梦的苍穹，万神也将流连于迷梦之中");

        // 侵染教堂
        add("pasterdream.seniors_dream.entry.infected_church", "侵染教堂");
        add("pasterdream.seniors_dream.entry.infected_church.page1", "");
        add("pasterdream.seniors_dream.entry.infected_church.page2", "在染梦世界中探索时，偶尔会在天空中发现这种一半被来自另一个梦境的阴影与黑暗侵蚀染黑的教堂。在这座暗影侵染的教堂中可能发现融梦水晶箱，如果在这座教堂的角落里仔细寻找，也许能够发现遗落的宝箱。");
        add("pasterdream.seniors_dream.entry.infected_church.page3", "除此之外，在这里存在着寻梦者前辈留下的笔记。根据笔记的指引前往下界，想办法穿越基岩层，或许能够找到黑暗的来源……$(br2)——为暗影所侵蚀的教堂，在其背后隐藏着不祥与疯狂。");

        // 灯影之下
        add("pasterdream.seniors_dream.category.lamp_shadow_world", "灯影之下");
        add("pasterdream.seniors_dream.category.lamp_shadow_world.desc", "阳光越是强烈的地方阴影就越是深邃。这里是另外一个世界，堕落，疯癫，同化，皆是寻梦者的归属。但废墟中的残留的文字似乎隐藏更多的未知。当你站在那片黑暗之前，你会如何抉择？");
        add("pasterdream.seniors_dream.category.lamp_shadow_world_items", "方块与物品");
        add("pasterdream.seniors_dream.category.lamp_shadow_world_items.desc", "灯影之下的一些方块与物品。");

        add("pasterdream.seniors_dream.entry.brooch_of_white_orchid", "白厄花胸针");
        add("pasterdream.seniors_dream.entry.brooch_of_white_orchid.page0", "当你在至暗的梦境中选择了光明，击败了盘踞于深邃阴影中的大手后，你能获得这么一枚奇妙的胸针。它所蕴藏的光明力量可以让你在容易致人疯狂的环境中保持清醒。如果你戴着它拿起一把光明之剑，似乎可以让那把剑更加致命……");
        add("pasterdream.seniors_dream.entry.brooch_of_white_orchid.page1", "——行于黑暗，侍奉光明。");

        add("pasterdream.seniors_dream.entry.white_sword", "白厄剑");
        add("pasterdream.seniors_dream.entry.white_sword.page0", "当你宣誓效忠于光明时，会有一块散发着光芒的水晶出现在你的口袋，利用它可以锻造出蕴含着光明力量的白厄剑。这把剑有着不菲的伤害，另外，它的光明特性让它可以特攻暗影生物，并对它们施加\"沉默\"效果。");
        add("pasterdream.seniors_dream.entry.white_sword.page1", "除此之外，你拿着这把剑时可以使用战技，释放\"白厄剑雨\"，它不仅能为敌人带来极致的毁伤，还能短暂束缚住受害者。如果你离敌人足够近并且目视敌人的话，白厄剑雨还能追踪敌人，平常的斩击也有可能释放出追踪剑雨……");
        add("pasterdream.seniors_dream.entry.white_sword.page2", "——我不仇视浸染在黑暗中的可怜虫，只是必须尽早代行光明的正义来将其抹杀，尽早才行。");

        add("pasterdream.seniors_dream.entry.seal_of_the_corrupted", "堕落者之印");
        add("pasterdream.seniors_dream.entry.seal_of_the_corrupted.page0", "当你在无尽的黑暗中选择投身于暗影，并弑杀了潜藏在阴影中的大手后，这枚印迹便归你所有。戴上它后，你的疯狂将不会带来负面影响，阴影中的怪物将会视你为同类从而对你保持中立。当你完全陷入疯狂后，产生的黑暗怪物也不会攻击你，相反，它们将会把仇恨宣泄到你的敌人身上。");
        add("pasterdream.seniors_dream.entry.seal_of_the_corrupted.page1", "——众物终将陷入深邃的疯狂，而我必须化身为阴影的使者。");

        add("pasterdream.seniors_dream.entry.shadow_sword", "影刃");
        add("pasterdream.seniors_dream.entry.shadow_sword.page0", "在决定堕落于黑暗之中后，你会获得一柄残破的剑柄，利用它可以锻造出至深至暗的影刃。这把剑会不断侵蚀你的理智。当你在疯狂的泥潭里陷得越深，它就越致命，而等到你最后的一丝理性也被蚕食，你就需要用你的生命值来哺育它了……");
        add("pasterdream.seniors_dream.entry.shadow_sword.page1", "你拿着这把剑时可以献祭理智来让这把剑的下一击变成\"夜魔斩击\"，这样，它的下一击会更加致命，它的伤害取决于你的理性。");
        add("pasterdream.seniors_dream.entry.shadow_sword.page2", "这把剑并不会效忠通过神的力量来让自己永不疯狂的(用指令关掉san值系统)背叛暗影之人，这一点还请注意。");
        add("pasterdream.seniors_dream.entry.shadow_sword.page3", "——所谓理智只是凡人的谎言，我将用疯狂来为众生带来平等的终夜。");

        // 暗影高炉
        add("pasterdream.seniors_dream.entry.shadow_blast_furnace", "暗影高炉");
        add("pasterdream.seniors_dream.entry.shadow_blast_furnace.page0", "在阴影的梦境中，你找到了琴雨梦和夜岚的实验遗存，在遗迹里留有这项装置的残骸和一份蓝图，对照着蓝图，似乎可以复现这台机器。$(br2)这台机器以梦境中的熔融阴影和噩梦为燃料，可以转化一些物品，使它们浸染上阴影。除此之外，它似乎还可以用来修复锈蚀金属……");
        add("pasterdream.seniors_dream.entry.shadow_blast_furnace.page1", "——熔炼黑暗的机器，要令万物都染上黑暗。");

        // 笔记残页
        add("pasterdream.seniors_dream.entry.broken_note", "笔记残页");
        add("pasterdream.seniors_dream.entry.broken_note.page0", "造访这片梦境的外来者不在少数，但是他们的笔记遗存更多的被撕碎，散落在梦境的各个角落。为了拼凑和解读这些笔记，我们需要一个专门的设施。$(br2)通过还原这些笔记，你能知晓更多隐秘，或许能知晓某些设施的修复办法。");
        add("pasterdream.seniors_dream.entry.broken_note.page1", "——知识就是力量，在如此危险的梦境中更是如此。");

        // 黑金属
        add("pasterdream.seniors_dream.entry.black_metal_ingot", "黑金属");
        add("pasterdream.seniors_dream.entry.black_metal_ingot.page0", "琴雨梦和夜岚对于有关灯影之下中随处可见的锈蚀金属的重新利用早就有所研究，你在阅读完她留下的笔记后，有了一种复现这项技术的想法……$(br2)依据她笔记中记载的“以阴影填补孔隙”的想法，借助暗影高炉，你成功制作出了黑金属粒。这种金属可以用于锻造影蚀工具。只是一点金属粒明显不够，你必须想办法得到可以用于锻造的金属锭……");
        add("pasterdream.seniors_dream.entry.black_metal_ingot.page1", "——流淌着阴影的金属，是寻梦者驾驭黑暗力量的一小步。");

        // 卡莱的调味瓶
        add("pasterdream.seniors_dream.entry.calais_spice_bottle", "卡莱的调味瓶");
        add("pasterdream.seniors_dream.entry.calais_spice_bottle.page0", "在灯影之下，你能找到这样一个前辈留下的调料瓶。当你佩戴它时，你吃东西的速度会变得更快，并且，它会给予你“卡莱料理”的效果，之后每次攻击都会消耗一层“卡莱料理”并随机给予一个效果。如果“卡莱料理”的效果消失了，就需要通过吃饭回复饱食度来获得“卡莱料理”了。");

        // 光明飞蝶
        add("pasterdream.seniors_dream.entry.bright_butterfly_curio", "光明飞蝶");
        add("pasterdream.seniors_dream.entry.bright_butterfly_curio.page0", "在过去，第一位进入灯影的寻梦者借助光明飞蝶掌控暗影的力量，最终成功离开了灯影之下。在掌控了光明的力量后，你或许可以尝试用白厄水晶改造一下夜明蝶……$(br2)利用白厄水晶复现了光明飞蝶，并将其佩戴后，你的眼中常驻光明，眼前的黑暗消散殆尽。任何尝试夺走你眼前光明的手段都将失效。");
        add("pasterdream.seniors_dream.entry.bright_butterfly_curio.page1", "——眼中所见皆为光明之时，黑暗自然无处遁形。");

        // 剧情
        add("pasterdream.seniors_dream.category.story", "剧情");
        add("pasterdream.seniors_dream.category.story.desc", "本篇为模组剧情整合。");

        add("pasterdream.seniors_dream.entry.deposition_shadow", "沉淀阴影");
        add("pasterdream.seniors_dream.entry.deposition_shadow.page0", "沉淀的影子，废弃的文明，以及象征死亡的生机。这里的环境让我感觉异常的压抑，我没法从这个世界中找到一丝属于它自己的颜色。还有许多...不符合常理的怪物，就像是脱离现实的梦一样。");
        add("pasterdream.seniors_dream.entry.deposition_shadow.page1", "沉淀阴影——荒寂的灯影世界。");
        add("pasterdream.seniors_dream.entry.deposition_shadow.page2", "不过让人感到奇怪的是...这些影子一样的怪物并不会攻击我，但与我随行的人就没那么幸运。还有散落在这里的一些瓶瓶罐罐，通天的锁链，遍地的骸骨...又是谁建造了这些...或者说，这些只是我的想象罢了...");

        add("pasterdream.seniors_dream.entry.shadow_travelogue", "阴影游记");
        add("pasterdream.seniors_dream.entry.shadow_travelogue.page0", "这里是一些有关在这里探索的记录：");
        add("pasterdream.seniors_dream.entry.shadow_travelogue.page1", "阴影游记——探索记录附图。");
        add("pasterdream.seniors_dream.entry.shadow_travelogue.page2", "打败黑色透明的怪物会掉落一些...会\"蠕动\"的黑色胶状物。$(br)一种瓦石地形上会有一些陶罐和坟墓，能从里面掏出来一些好东西。$(br)部分锁链和遗迹里会有些锈蚀的金属，这种金属貌似并不属于现实存在的元素。$(br)长满蘑菇的地方生活着一些甲虫，它们很喜欢吃一种本地的白色花朵，可以和它们成为朋友。$(br)液体状的\"阴影\"小湖会在沉淀阴影的表面流淌。");

        add("pasterdream.seniors_dream.entry.shadow_dungeon", "暗影地牢");
        add("pasterdream.seniors_dream.entry.shadow_dungeon.page0", "还记得之前在探索的时候发现了一种奇怪的遗迹，像是一种仪式供奉着中间的一个东西。但当时我伸手去触碰它，却毫无反应，从残破的结构来看应该是年久失修坏掉了？");
        add("pasterdream.seniors_dream.entry.shadow_dungeon.page1", "但我们最近提炼出来的黑金属和那个核心的材质很相似...还有中间那个会发光的白色灯块，我应该尝试去修复它。如果我能成功...我会把修复方法记录下来。以及...我的感觉告诉我这个遗迹不会只有地上的这部分...这个明亮的核心就像是盏灯，它的存在酝生了下面的影子。");

        add("pasterdream.seniors_dream.entry.scare", "恐惧");
        add("pasterdream.seniors_dream.entry.scare.page0", "\"这里还有感觉吗？嘿，不要睡过去！撑住！\"$(br)脸已经被摧残的血肉模糊，身体像是被蹂躏了一样，他的腰部及以下已经没有了知觉，脊柱应该是被撕裂了。$(br)\"你不会死的，把这个喝下去，这边呢？还能感觉到吗？\"$(br)一声惨叫，当我向着声音跑过去的时候看到了眼前的景象，躺在血泊里的这个人。");
        add("pasterdream.seniors_dream.entry.scare.page1", "\"能告诉我发生了什么事吗，救援正在路上\"$(br)\"不要...不要再向前了...不要...\"他艰难地说着，沙哑低沉的声音需要把耳朵凑进去才能听到。$(br)\"你还...年轻...不要再去触碰...黑暗...\"随后他的嗓子再也发不出声音，手滑落下来。");
        add("pasterdream.seniors_dream.entry.scare.page2", "他的脖头上戴着象征着探险家的坠饰，张开的手心握着自己家人的照片，和妻子重病的药费欠款清单。$(br)\"看来已经是...为了通过这个赚取药费吗...\"$(br)我握住了他那个拿着纸团的手\"这个就由我来吧...\"");
        add("pasterdream.seniors_dream.entry.scare.page3", "我低下头，闭上眼默哀。$(br)忽然间一阵耳鸣，伴随着眩晕的感觉，我合上的双眼前显现出了画面。$(br)两只巨大的像手一样的怪物，而\"我\"的视角好像就是那个探险家！$(br)其中一只手伴随着巨大的破风声将\"我\"冲撞到墙上，然后四指合十砸碎了地面和我的双腿。$(br)在这巨大的冲击之下，口袋里的骨针也刺入了\"我\"的大腿。");
        add("pasterdream.seniors_dream.entry.scare.page4", "整个梦的过程只过了数秒，但却无比真实...$(br)我喘着气，眩晕的感觉逐渐消散，但恐惧的感觉弥漫全身。$(br)因为在最后，那个手心中间的\"眼睛\"，好像...看了真正的我一眼...");


        // 实体
        add(ModEntities.PINK_CHICKEN.get(), "粉红鸡");
        add(ModItems.PINK_CHICKEN_SPAWN_EGG.get(), "粉红鸡刷怪蛋");
        add(ModEntities.PINK_SLIME.get(), "粉红史莱姆");
        add(ModItems.PINK_SLIME_SPAWN_EGG.get(), "粉红史莱姆刷怪蛋");
        add(ModEntities.GOLDEN_FOX.get(), "金狐狸");
        add(ModItems.GOLDEN_FOX_SPAWN_EGG.get(), "金狐狸刷怪蛋");
        add(ModEntities.FIREFLY.get(), "萤火虫");
        add(ModItems.FIREFLY_SPAWN_EGG.get(), "萤火虫刷怪蛋");
        add(ModEntities.WIND_KNIGHT.get(), "破风骑士");
        add(ModItems.WIND_KNIGHT_SPAWN_EGG.get(), "破风骑士刷怪蛋");
        add(ModEntities.THUNDERCLOUD.get(), "雷云");
        add(ModItems.THUNDERCLOUD_SPAWN_EGG.get(), "雷云刷怪蛋");
        add(ModEntities.HIGHVOLTAGE_THUNDERCLOUD.get(), "高压雷云");
        add(ModItems.HIGHVOLTAGE_THUNDERCLOUD_SPAWN_EGG.get(), "高压雷云刷怪蛋");
        add(ModEntities.BONE_WING.get(), "骨翼");
        add(ModItems.BONE_WING_SPAWN_EGG.get(), "骨翼刷怪蛋");
        add(ModEntities.ASH_BONE_WING.get(), "余烬骨翼");
        add(ModItems.ASH_BONE_WING_SPAWN_EGG.get(), "余烬骨翼刷怪蛋");
        add(ModEntities.JELLYFISH.get(), "水母");
        add(ModItems.JELLYFISH_SPAWN_EGG.get(), "水母刷怪蛋");
        add(ModEntities.SMALL_STONE_SPIRIT.get(), "小石精");
        add(ModItems.SMALL_STONE_SPIRIT_SPAWN_EGG.get(), "小石精刷怪蛋");
        add(ModEntities.LIGHTNING_PROJECTILE.get(), "雷云");
        add(ModEntities.MELT_DREAM_CRYSTAL_ENTITY.get(), "融梦水晶");
        add(ModEntities.FOX_FIRE.get(), "狐火");
        add(ModEntities.SHADOW_GOLEM.get(), "阴影傀儡");
        add(ModItems.SHADOW_GOLEM_SPAWN_EGG.get(), "阴影傀儡刷怪蛋");
        add(ModEntities.TERRORBEAK.get(), "恐怖尖喙");
        add(ModItems.TERRORBEAK_SPAWN_EGG.get(), "恐怖尖喙刷怪蛋");
        add(ModEntities.CRAZY_TERRORBEAK.get(), "疯狂恐怖尖喙");
        add(ModItems.CRAZY_TERRORBEAK_SPAWN_EGG.get(), "疯狂恐怖尖喙刷怪蛋");
        add(ModEntities.WEAKENESS_TERRORBEAK.get(), "虚弱恐怖尖喙");
        add(ModItems.WEAKENESS_TERRORBEAK_SPAWN_EGG.get(), "虚弱恐怖尖喙刷怪蛋");
        add(ModEntities.SHADOW_HAND.get(), "暗影之手");
        add(ModItems.SHADOW_HAND_SPAWN_EGG.get(), "暗影之手刷怪蛋");
        add(ModEntities.SHADOW_GHOST.get(), "怨魂");
        add(ModItems.SHADOW_GHOST_SPAWN_EGG.get(), "怨魂刷怪蛋");
        add(ModEntities.SHADOW_SQUEAL_GHOST.get(), "尖啸怨魂");
        add(ModItems.SHADOW_SQUEAL_GHOST_SPAWN_EGG.get(), "尖啸怨魂刷怪蛋");
        add(ModEntities.WAILING_SHADOW_GHOST.get(), "悲泣尖啸怨魂");
        add(ModItems.WAILING_SHADOW_GHOST_SPAWN_EGG.get(), "悲泣尖啸怨魂刷怪蛋");
        add(ModEntities.FRIENDLY_SHADOW_GHOST.get(), "友善怨魂");
        add(ModItems.FRIENDLY_SHADOW_GHOST_SPAWN_EGG.get(), "友善怨魂刷怪蛋");
        add(ModEntities.BLACK_BEETLE.get(), "厚翅甲虫");
        add(ModItems.BLACK_BEETLE_SPAWN_EGG.get(), "厚翅甲虫刷怪蛋");
        add(ModEntities.BLACK_BEETLE_MOTHER.get(), "厚翅甲虫之母");
        add(ModItems.BLACK_BEETLE_MOTHER_SPAWN_EGG.get(), "厚翅甲虫之母刷怪蛋");
        add(ModItems.BLACK_BEETLE_CARAPACE.get(), "厚翅甲壳");
        add(ModItems.BLACK_BEETLE_VOCALCORD.get(), "振音声膜");
        add("tooltip.pasterdream.black_beetle_vocalcord", "§7还能隐约感觉到它的震动");
        add("message.pasterdream.black_beetle.easter_egg_1", "来,小亮给大家整个活！");
        add("message.pasterdream.black_beetle.easter_egg_2", "草,走,忽略!ጿ ኈ ቼ ዽ ጿ");
        add("entity.pasterdream.golden_fox.vanish", "金色狐狸在完成了你许下的愿望之后消失了...");

        //jeed兼容
        add("effect.pasterdream.rest_buff.description", "触摸琴雨梦玩偶或睡觉后获得，效果持续期间获得理智光环。");
        add("effect.pasterdream.cook_buff.description", "食用部分食物后获得，效果持续期间获得理智光环。");
        add("effect.pasterdream.cheer_up_buff.description", "san高于90%的时候获得，获得一些正面效果。");
        add("effect.pasterdream.lethargy_buff.description", "san在40%和60%之间的时候获得，获得一些负面效果。");
        add("effect.pasterdream.trance_buff.description", "san在20%和40%之间的时候获得，获得更严重的负面效果。");
        add("effect.pasterdream.insand_buff.description", "san低于20%的时候获得，获得极为严重的负面效果，视线会被特殊的视觉效果覆盖，san在1%~10%时会获得疯狂II，循环播放音效并视角抖动，san低于1%时获得疯狂III，前面所述负面会得到加强。（视觉效果，抖动和音效可以指令或配置关闭）");
        add("effect.pasterdream.snow_vow_buff.description", "效果期间+3幸运，免疫燃烧和冻结效果，由雪下的誓言饰品在范围内给予。");
        add("effect.pasterdream.goldenrod_tea_buff.description", "效果持续期间免疫饥饿和反胃效果。");
        add("effect.pasterdream.sculk_armor_buff.description", "穿戴全套回响套装时获得，增加最大生命值，身处深层时获得抗性提升，受到黑暗效果时移除黑暗并给予速度和抗性效果。");
        add("effect.pasterdream.dyedream_armor_buff.description", "穿戴全套染梦合金套装时获得，增加最大生命值并每30秒获得伤害吸收II。");
        add("effect.pasterdream.titanium_armor_buff.description", "穿戴全套钛金套装时获得，每30秒获得伤害吸收I。");
        add("effect.pasterdream.dyedream_up_buff.description", "穿戴全套染梦合金套装时获得，持有染梦(染梦合金与融梦水晶)工具时伤害+50%");
        add("effect.pasterdream.dream_wish_buff.description", "饮用梦境果汁获得，拥有该效果在晚上睡觉以进入染梦世界。");
        add("effect.pasterdream.dyedream_perfume_buff.description", "饮用染梦香水获得，效果持续期间幻翼不会攻击你。");
        add("effect.pasterdream.counter_attack_buff.description", "增加战技伤害，下一次攻击后消失。");
        add("effect.pasterdream.dream_harp_of_wanderer_buff.description", "使用漂泊旅者的染梦竖琴后获得，漂泊旅者的染梦竖琴可以从染梦列车中得到。");
        add("effect.pasterdream.cecilia_blessing_buff.description", "戴着塞西莉娅受到致命伤害后获得，效果期间获得伤害免疫。");
        add("effect.pasterdream.guard_buff.description", "使用守护预言卡获得，拥有该buff时，超过一定生命百分比的部分伤害将会受到减伤（可在配置里修改）");
        add("effect.pasterdream.holy_grail_buff.description", "使用圣杯预言卡获得，拥有该buff时，效果持续期间+15最大生命值，并免疫一部分负面状态，获得自然生命恢复且血量越低，回血速度越快。");
        add("effect.pasterdream.rapid_reaction_buff.description", "使用疾行预言卡获得，拥有该buff时，增加上坡高度，降低瞬身术冷却并免疫摔落伤害。");
        add("effect.pasterdream.flare_up_buff.description", "使用执剑预言卡获得，拥有该buff时，增加攻击力和攻击速度，增加战技伤害倍率并降低技能冷却时间。");
        add("effect.pasterdream.conflict_mark.description", "使用纷争预言卡标记其它实体后该实体获得，拥有该buff时，将会成为其它实体攻击目标。");
        add("effect.pasterdream.war_flag_buff.description", "装备战争旗帜后击杀敌人后获得，期间增加攻击力，并每击杀一个敌人增加1级。");
        add("effect.pasterdream.confusion_buff.description", "施加给玩家后，玩家的视角将会抖动，施加给其它生物后，会使该生物失去行动能力。");
        add("effect.pasterdream.calais_spice_bottle_buff.description", "装备卡莱调料瓶后获得，每次击中敌人后减少1层等级并获得随机效果，食用符合要求食物会恢复层数。");
        add("effect.pasterdream.shadow_silence_buff.description", "暗影生物获得该buff后，会失去释放技能的能力。");
        add("effect.pasterdream.bind_buff.description", "被白厄剑的剑雨攻击后获得，获得该buff后，会无法移动。");
        add("effect.pasterdream.restrainmove_block_buff.description", "效果持续期间移除跳跃提升并强制禁用飞行。");

        // 染梦裂隙消息
        add("message.pasterdream.dyedream_crack.first_contact.1", "§5身体从这个奇怪的洞穴中穿过，但并没有什么反应。");
        add("message.pasterdream.dyedream_crack.first_contact.2", "§5你能感觉到这个类似裂隙一样的东西在与这个世界和另外一处地方交互，附近不同的环境可能正是因为此而诞生。");
        add("message.pasterdream.dyedream_crack.first_contact.3", "§5现在可能还不是时候，让我们交给在日夜轮转之间的时光中给予答案。");
        add("message.pasterdream.dyedream_crack.first_contact.4", "§5也许我该翻翻那本旧梦归引……或许其中有关于这类裂隙的记载。");
        add("message.pasterdream.sleep.dream_of_crack.1", "§5你在睡梦中惊醒，背后冒出了些许冷汗，你回忆起自己梦见了往日探索中遇见的奇怪裂隙，在慢慢靠近并凝视着你。");
        add("message.pasterdream.sleep.dream_of_crack.2", "§5那裂隙很可能有古怪，或许应该返回那里去调查一下");
        add("message.pasterdream.sleep.dream_of_crack.3", "§5我必须知道发生了什么...");
        add("message.pasterdream.sleep.dream_of_crack.4", "一张寻梦者笔记出现在了你的口袋里");
        add("message.pasterdream.dyedream_world.found_note", "你发现了一张寻梦者笔记，并收进了你的背包");
        add("message.pasterdream.story.pure_and_flawless.found_note", "你发现了一张寻梦者笔记，并收进了你的背包");
        add("message.pasterdream.story.dream_fertilizer.found_note", "你发现了一张寻梦者笔记，并收进了你的背包");
        add("message.pasterdream.dream_accumulator.found_note", "你发现了一张寻梦者笔记，并收进了你的背包");
        add("message.pasterdream.story_guide.not_entered_lamp_shadow", "你尚未踏足灯影之下");
        add("message.pasterdream.story_guide.all_done", "灯影之下的所有笔记都已解析完毕");
        add("message.pasterdream.story_guide.need_infested_church", "你需要先自行找到有关灯影之下的笔记");

        add("message.pasterdream.dream_train.train_pass", "一辆逐梦列车呼啸而过...");
        add("message.pasterdream.dream_train.location_info", "列车向我们暴露了一个位置信息... §aX：%s §aZ：%s");

        // 沙漠英雄墓 - 任务对话
        add("message.pasterdream.desert_hero_tomb.line1", "良久，竟有来者。");
        add("message.pasterdream.desert_hero_tomb.line2", "无苦心，有孥，吾有一事相求。");
        add("message.pasterdream.desert_hero_tomb.line3", "如卿所见，吾今也。");
        add("message.pasterdream.desert_hero_tomb.line4", "以剑授汝，可否助我逐外寇也？");
        add("message.pasterdream.desert_hero_tomb.line5", "为得好，子曰谢酬。");
        add("message.pasterdream.desert_hero_tomb.line6", "汝又能终吾三请，吾以真剑付汝。");
        add("message.pasterdream.desert_hero_tomb.line7", "其名为：§e『沉荆门』朔漠");
        add("message.pasterdream.desert_hero_tomb.task_clear_threats", "[清除附近的掠夺者与尸壳]");
        add("message.pasterdream.desert_hero_tomb.threats_remaining", "尚未清除附近的威胁，他不予回应");
        add("message.pasterdream.desert_hero_tomb.quest1", "1.为我带来10块年糕");
        add("message.pasterdream.desert_hero_tomb.quest2", "2.我想要一匹马在旁边陪着我");
        add("message.pasterdream.desert_hero_tomb.quest3", "3.获得一个全村庄村民的英雄认可时来见我");
        add("message.pasterdream.desert_hero_tomb.task1_complete", "任务1完成");
        add("message.pasterdream.desert_hero_tomb.task2_complete", "任务2完成");
        add("message.pasterdream.desert_hero_tomb.task3_complete", "任务3完成");
        add("message.pasterdream.desert_hero_tomb.all_tasks_done", "吾心愿已成，请携朔漠大剑见我");
        add("message.pasterdream.desert_hero_tomb.receive_sword", "请君收下吧：此名为『沉荆门』朔漠");
        add("message.pasterdream.desert_hero_tomb.bring_sword", "吾心愿已成，请携朔漠大剑见我");
        add("message.pasterdream.desert_hero_tomb.already_completed", "已有人完成他的全部愿望，他便不再回应");

        // 预言卡
        add(ModItems.EMPTY_PROPHECY_CARD.get(), "空白预言卡");
        add("item.pasterdream.prophecy_card", "预言卡");
        add("item.pasterdream.prophecy_card.balance", "预言卡-平衡");
        add("item.pasterdream.prophecy_card.chaos", "预言卡-混乱");
        add("item.pasterdream.prophecy_card.conflict", "预言卡-纷争");
        add("item.pasterdream.prophecy_card.graveyard", "预言卡-墓园");
        add("item.pasterdream.prophecy_card.guard", "预言卡-守护");
        add("item.pasterdream.prophecy_card.holy_grail", "预言卡-圣杯");
        add("item.pasterdream.prophecy_card.sin", "预言卡-罪恶");
        add("item.pasterdream.prophecy_card.sprint", "预言卡-疾行");
        add("item.pasterdream.prophecy_card.wielding_sword", "预言卡-持剑");
        add("item.pasterdream.prophecy_card.unknown", "错误的预言卡（%s）");
        add("tooltip.pasterdream.prophecy_card.empty", "§7一张空白的预言卡，似乎还未被赋予种类");
        add("tooltip.pasterdream.prophecy_card.type.balance", "§b种类：平衡");
        add("tooltip.pasterdream.prophecy_card.type.chaos", "§b种类：混乱");
        add("tooltip.pasterdream.prophecy_card.type.conflict", "§b种类：纷争");
        add("tooltip.pasterdream.prophecy_card.type.graveyard", "§b种类：墓园");
        add("tooltip.pasterdream.prophecy_card.type.guard", "§b种类：守护");
        add("tooltip.pasterdream.prophecy_card.type.holy_grail", "§b种类：圣杯");
        add("tooltip.pasterdream.prophecy_card.type.sin", "§b种类：罪恶");
        add("tooltip.pasterdream.prophecy_card.type.sprint", "§b种类：疾行");
        add("tooltip.pasterdream.prophecy_card.type.wielding_sword", "§b种类：持剑");
        add("tooltip.pasterdream.prophecy_card.unknown", "§c种类：错误！（%s）");


        add("tooltip.pasterdream.prophecy_card.balance.description", "§9使用卡牌时，当前自身拥有的部分药水效果的等级翻倍，但持续时间缩短至一半。");
        add("tooltip.pasterdream.prophecy_card.conflict.description.1", "§9使用卡牌时，将光标对应的实体标记，被标记的实体将会被成为其它实体的攻击对象，持续120秒");
        add("tooltip.pasterdream.prophecy_card.conflict.description.2", "§7§O --至少你用完这张卡不用担心被一位白衣少女捅成玻璃碴子");
        add("message.pasterdream.prophecy_card.conflict.marked", "让现场腥风血雨起来吧！");
        add("message.pasterdream.prophecy_card.conflict.no_target", "没有选中目标。");
        add("tooltip.pasterdream.prophecy_card.graveyard.description", "§9使用卡牌时，对以自身为中心，7*7范围内的所有敌人造成%.1f点伤害，该伤害不被减伤所降低。（此效果不对玩家生效）");
        add("tooltip.pasterdream.prophecy_card.sprint.description", "§9使用卡牌时，获得速度III、跳跃提升II、高速反射效果，持续120秒。效果持续期间增加上坡高度，-30%瞬身术冷却并免疫摔落伤害。");
        add("tooltip.pasterdream.prophecy_card.guard.description.1", "§9使用卡牌时，获得120秒伤害吸收III和60秒守护效果。");
        add("tooltip.pasterdream.prophecy_card.guard.description.2", "§9玩家若受到的任何超过其最大生命值%.1f%%的伤害，超出的部分都会减少%.1f%%。这种减伤是在护甲的减伤作用之前应用的。");
        add("tooltip.pasterdream.prophecy_card.holy_grail.description", "§9使用卡牌时，获得圣杯效果，持续120秒。效果持续期间+15最大生命值，并免疫一部分负面状态，获得自然生命恢复且血量越低，回血速度越快，低于33%的时候达到最快。");
        add("tooltip.pasterdream.prophecy_card.chaos.description.1", "§9使用卡牌时，对以自身为中心，7*7范围内的所有敌人陷入混乱并失去行动能力，持续10秒。（此效果不对玩家生效）");
        add("tooltip.pasterdream.prophecy_card.chaos.description.2", "§7§O --THE TRUE AND NEO CHAOS");
        add("tooltip.pasterdream.prophecy_card.sin.description.1", "§9使用卡牌时，对以自身为中心，引燃19*19范围内的所有亡灵生物和灾厄村民15秒并造成25点伤害。");
        add("tooltip.pasterdream.prophecy_card.sin.description.2", "§9如果此实体为僵尸村民，则不会受到伤害且转化为村民。");
        add("tooltip.pasterdream.prophecy_card.sin.description.3", "§9对于那些极为烦人的生物，则直接抹杀，至于什么是极为烦人的生物，相信预言卡的判断。");
        add("tooltip.pasterdream.prophecy_card.sin.description.4", "§5§O --直面汝罪");
        add("tooltip.pasterdream.prophecy_card.wielding_sword.description", "§9使用卡牌时，获得怒气爆发效果，持续120秒。效果期间，攻击力+3，攻击速度+0.2，战技伤害倍率+30%，技能冷却-20%。");


        add("tooltip.pasterdream.prophecy_card.unknown.description.1", "§9你是怎么获得这张卡的？");
        add("tooltip.pasterdream.prophecy_card.unknown.description.2", "§9请把你做了什么发送到github或者mcmod方便我们定位问题，而不是只发送这张错误的截图");
        add("message.pasterdream.prophecy_card.invalid", "§c预言卡数据异常（%s），右键无效！");

        // 药剂瓶
        add(PotionBottleRegistry.POTION_BOTTLE.get(), "药剂瓶");
        add("item.pasterdream.potion_bottle.berserk", "狂暴药剂瓶");
        add("item.pasterdream.potion_bottle.frozen", "冰冻药剂瓶");
        add("item.pasterdream.potion_bottle.highly_toxic", "剧毒药剂瓶");
        add("item.pasterdream.potion_bottle.lightning", "雷电药剂瓶");
        add("item.pasterdream.potion_bottle.rejuvenation", "回春药剂瓶");
        add("item.pasterdream.potion_bottle.inferno", "狱火药剂瓶");
        add("effect.pasterdream.highly_toxic_buff", "剧毒");
        add("tooltip.pasterdream.potion_bottle.highly_toxic.description","§9砸碎后释放剧毒云，对6*6区域范围内的生物施加剧毒、缓慢和虚弱");
        add("tooltip.pasterdream.potion_bottle.lightning.description.1","§9砸碎后在落地地点释放乌云，并在短时间后对5*5区域范围内生成4次随机落雷");
        add("tooltip.pasterdream.potion_bottle.lightning.description.2","§7§O --瓶装闪电");
        add("tooltip.pasterdream.potion_bottle.rejuvenation.description","§9砸碎后释放5*5区域的治疗雾气，持续20秒，为在雾气内的玩家或和平生物恢复最大生命值的5%的血量");
        add("tooltip.pasterdream.potion_bottle.frozen.description.1","§9砸碎后在落地地点释放7*7的冷冻雾气，在其中的实体将无法移动并失去战斗能力");
        add("tooltip.pasterdream.potion_bottle.frozen.description.2","§7§O --谁将召唤那位施雪的女孩？");
        add("tooltip.pasterdream.potion_bottle.berserk.description.1","§9砸碎后在落地地点释放8*8的狂暴雾气");
        add("tooltip.pasterdream.potion_bottle.berserk.description.2","§9在其中的玩家攻击力+20%，移动速度+10%，攻击速度+50%，战技冷却-30%，瞬身术冷却-30%，战技伤害+30%");
        add("tooltip.pasterdream.potion_bottle.inferno.description.1","§9在6x6范围制造烈火，对范围内所有敌人周期造成魔法伤害并点燃敌人");
        add("tooltip.pasterdream.potion_bottle.inferno.description.2","§9波及的敌人会被赋予易伤buff，最高3级");
        add("effect.pasterdream.berserk_buff", "狂暴");
        add("effect.pasterdream.frozen_buff", "急冻");
        add("effect.pasterdream.vulnerability_buff", "易伤");

        // 暗影提灯
        add(ModItems.SHADOW_HAND_LANTERN.get(), "暗影提灯");
        add("tooltip.pasterdream.shadow_hand_lantern.description.1", "§7▪ §9手持提灯时理智光环+1.2san/分钟");
        add("tooltip.pasterdream.shadow_hand_lantern.description.2", "§7右键使用");
        add("tooltip.pasterdream.shadow_hand_lantern.description.3", "§7▪ §9直径15格范围内的暗影生物受到20%的易伤效果持续15秒");
        add("tooltip.pasterdream.shadow_hand_lantern.description.4", "§7▪ §9冷却时间：8秒");
        add("tooltip.pasterdream.shadow_hand_lantern.description.5", "§7▪ §4精神值消耗：1");

        // 结构翻译键
        add("structure.pasterdream.oak_fisherman_hut", "橡木渔夫小屋");
        add("structure.pasterdream.spruce_fisherman_hut", "云杉渔夫小屋");
        add("structure.pasterdream.dyedream_crack", "染梦裂隙");
        add("structure.pasterdream.campsite_overworld", "主世界营地");
        add("structure.pasterdream.the_lost_sword_tomb", "失落剑冢");
        add("structure.pasterdream.dyedream_church_0", "染梦教堂");
        add("structure.pasterdream.dyedream_church_2", "染梦教堂");
        add("structure.pasterdream.dyedream_church_4", "染梦教堂");
        add("structure.pasterdream.dyedream_church_6", "染梦教堂");
        add("structure.pasterdream.dyedream_crystal_ball", "染梦水晶球");
        add("structure.pasterdream.garden_decryption_misty_dreaming_lotus", "花园解密·迷梦冶梦莲");
        add("structure.pasterdream.garden_decryption_nippy_edelweiss", "花园解密·凌冽雪绒花");
        add("structure.pasterdream.garden_decryption_nine_tailed_fox", "花园解密·九尾狐");
        add("structure.pasterdream.traveler_house", "旅者小屋");
        add("structure.pasterdream.dyedream_pavilion_plain", "染梦凉亭");
        add("structure.pasterdream.dyedream_pavilion_snowy_plain", "雪原染梦凉亭");
        add("structure.pasterdream.dyedream_worldtree", "染梦世界树");
        add("structure.pasterdream.dyedream_wishing_tree", "染梦祈愿树");
        add("structure.pasterdream.dyedream_floating_temple", "浮空神庙");
        add("structure.pasterdream.dyedream_tavern", "染梦客栈");
        add("structure.pasterdream.dyedream_campsite", "染梦营地");
        add("structure.pasterdream.dyedream_ecosystem_bubble", "染梦气泡生态球");
        add("structure.pasterdream.ecosystem_bubble", "气泡生态球");
        add("structure.pasterdream.pinkagaric_house", "粉顶蘑菇屋");
        add("structure.pasterdream.dyedream_tower_0", "染梦穿云塔_0");
        add("structure.pasterdream.dyedream_tower_1", "染梦穿云塔_1");
        add("structure.pasterdream.big_bubbles_0", "大气泡0");
        add("structure.pasterdream.big_bubbles_1", "大气泡1");
        add("structure.pasterdream.big_bubbles_2", "大气泡2");
        add("structure.pasterdream.desert_fortress", "沙漠遗迹");
        add("structure.pasterdream.dream_train", "逐梦列车");
        add("structure.pasterdream.dyedream_laboratory", "染梦实验室");
        add("structure.pasterdream.lifecrystal_cave", "生命水晶洞穴");
        add("structure.pasterdream.melt_dream_liquid_well", "融梦涌泉井");
        add("structure.pasterdream.dyedream_sky_island", "染梦空岛");
        add("structure.pasterdream.shadownote_ruin_0", "阴影笔记废墟");
        add("structure.pasterdream.shadownote_ruin_1", "阴影笔记废墟");
        add("structure.pasterdream.shadownote_ruin_2", "阴影笔记废墟");
        add("structure.pasterdream.shadow_shelter", "阴影庇护所");
        add("structure.pasterdream.shadow_nest", "阴影巢穴");
        add("structure.pasterdream.shadow_fungus_house", "阴影小木屋");
        add("structure.pasterdream.shadow_foundry", "阴影铸造厂");
        add("structure.pasterdream.invaded_church", "侵染教堂");
        add("structure.pasterdream.twilight_lantern", "暮影之笼");
        add("structure.pasterdream.wind_island", "风之岛");
        add("structure.pasterdream.windmill_lodge", "风车小屋");
        add("structure.pasterdream.lost_windknight_ruins", "失落风骑士遗迹");
        add("structure.pasterdream.windmoor_tree", "风泊树");
        add("structure.pasterdream.wind_infested_stone_0", "风侵石");
        add("structure.pasterdream.wind_infested_stone_1", "风侵石");
        add("structure.pasterdream.wind_pond", "风池");
        add("structure.pasterdream.big_bubbles_6", "大气泡");
        add("structure.pasterdream.big_bubbles_7", "大气泡");
        add("structure.pasterdream.bocchi_0", "波奇");
        add("structure.pasterdream.bocchi_1", "波奇");
        add("structure.pasterdream.breakwing_curtain", "破翼帷幕");
        add("structure.pasterdream.hakurei_reimu", "博丽灵梦");
        add("structure.pasterdream.hot_air_balloon_0", "热气球");
        add("structure.pasterdream.hot_air_balloon_1", "热气球");
        add("structure.pasterdream.hot_air_balloon_2", "热气球");
        add("structure.pasterdream.hot_air_balloon_3", "热气球");
        add("structure.pasterdream.hot_air_balloon_4", "热气球");
        add("structure.pasterdream.hot_air_balloon_5", "热气球");
        add("structure.pasterdream.hot_air_balloon_6", "热气球");
        add("structure.pasterdream.hot_air_balloon_7", "热气球");
        add("structure.pasterdream.small_ballon_0", "小气球");
        add("structure.pasterdream.small_ballon_1", "小气球");
        add("structure.pasterdream.small_ballon_2", "小气球");
        add("structure.pasterdream.small_ballon_3", "小气球");
        add("structure.pasterdream.small_ballon_4", "小气球");
        add("structure.pasterdream.small_ballon_5", "小气球");
        add("structure.pasterdream.small_ballon_6", "小气球");
        add("structure.pasterdream.small_ballon_7", "小气球");
        add("structure.pasterdream.small_ballon_8", "小气球");
        add("structure.pasterdream.small_ballon_9", "小气球");
        add("structure.pasterdream.small_ballon_10", "小气球");
        add("structure.pasterdream.christmas_tree", "圣诞树");
        add("message.pasterdream.twilight_lantern.activate_fail_no_knowledge", "你尚未知晓如何激活影灯");
        add("message.pasterdream.twilight_lantern.activate_fail_no_crystal", "需要用融梦水晶碎片点燃影灯");
        add("message.pasterdream.twilight_lantern.event_start", "§8诡异的灯笼突然剧烈抖动，黑色的阴影从灯芯里流出");
        add("message.pasterdream.twilight_lantern.event_mid", "§8阴影化为实物，四周传来空洞的回响，随后便是足以撕裂耳膜的嘶吼");
        add("message.pasterdream.twilight_lantern.event_voice", "§8你似乎从听到了怪异的声音");
        add("message.pasterdream.twilight_lantern.event_end", "§8阴影不再从四周向外涌出，暮影之笼也逐渐归为平静，上空逐渐出现一道裂缝");
        add("message.pasterdream.twilight_lantern.event_end_3", "§5不要抗拒，顺从内心，一同永生");
        add("message.pasterdream.twilight_lantern.event_end_4", "§5顺从！顺从！！顺从！！！");
        add("message.pasterdream.twilight_lantern.event_end_5", "§5来吧，来吧~ 来吧！……与我们拥抱，与我们共眠，与我们融为一体，这里是你唯一的归属");
        add("message.pasterdream.twilight_lantern.event_end_2", "§8声音逐渐清晰，不断重复的语言让你似乎有一些烦躁");
        add("message.pasterdream.twilight_lantern.event_ready", "§5可以与暮影之笼发生共鸣，与暗影长床互动");
        add("message.pasterdream.twilight_lantern.data_reset", "方块数据已重置");
        add("message.pasterdream.twilight_lantern_location", "笔记末尾记载着炼狱上层的坐标：§aX：%s §aZ：%s");
    }
}
