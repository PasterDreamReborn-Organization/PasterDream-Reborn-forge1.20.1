package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.itemwithnbt.blueprintwithnbt.BluePrintWithNBT;
import com.pasterdream.pasterdreammod.helper.itemwithnbt.dreamnoteswithnbt.DreamNotesWithNBT;
import com.pasterdream.pasterdreammod.world.item.blueprints.AddBluePrintContentListTag;
import com.pasterdream.pasterdreammod.world.item.dreamnotes.NBTBookRegisterToCreativeModTab;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PasterDreamMod.MOD_ID);

    // ===== 饮食 =====
    // 来源: 旧 tab_0 食材部分 + 旧 paser_tab_4
    public static final RegistryObject<CreativeModeTab> PASTERDREAM_FOOD_TAB = CREATIVE_MODE_TABS.register("pasterdream_food_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_food_tab"))
                    .icon(() -> new ItemStack(ModItems.DYEDREAM_FRUIT.get()))
                    .displayItems((parameters, output) -> {
                        // 饮品/食物 (来自旧 paser_tab_4, 按原顺序)
                        output.accept(ModItems.GLASS_JAR.get());
                        output.accept(ModItems.GLASS_JAR_OF_WATER.get());
                        output.accept(ModItems.GLASS_JAR_OF_MILK.get());
                        output.accept(ModItems.GLASS_JAR_OF_YEAST.get());
                        output.accept(ModItems.GLASS_JAR_OF_GUIDING_DRUG.get());
                        output.accept(ModItems.DOUGH.get());
                        output.accept(ModItems.DOUGH_WITH_EGG.get());
                        output.accept(ModItems.BREAD_SLICE.get());
                        output.accept(ModItems.CAKE_BASE.get());
                        output.accept(ModItems.WAFER_BISCUIT.get());
                        output.accept(ModItems.DYEDREAM_FRUIT.get());
                        output.accept(ModItems.FIG.get());
                        output.accept(ModItems.GLASS_JAR_OF_DREAM_JUICE.get());
                        output.accept(ModItems.AMBER_CANDY.get());
                        output.accept(ModItems.QUEER_SOUP.get());
                        output.accept(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get());
                        output.accept(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get());
                        output.accept(ModItems.GLASS_CUP.get());
                        output.accept(ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get());
                        output.accept(ModItems.GLASS_CUP_OF_APPLE_JUICE.get());
                        output.accept(ModItems.GLASS_CUP_OF_HONEY_JUICE.get());
                        output.accept(ModItems.GLASS_CUP_OF_WATERMELON_JUICE.get());
                        output.accept(ModItems.DYEDREAM_POPSICLE.get());
                        output.accept(ModItems.FRIED_EGG.get());
                        output.accept(ModItems.BACON_AND_EGG.get());
                        output.accept(ModItems.RICE_CAKE.get());
                        output.accept(ModItems.SANDWICH.get());
                        output.accept(ModItems.CHOCOLATE.get());
                        output.accept(ModItems.POPPING_CANDY.get());
                        output.accept(ModItems.CHOCOLATE_MATCHA_CAKE.get());
                        output.accept(ModItems.SWISS_ROLL.get());
                        output.accept(ModItems.STUFFED_WAFER_COOKIES.get());
                        output.accept(ModItems.CREAM_BUN_CAKE.get());
                        output.accept(ModItems.BERRY_BUN_CAKE.get());
                        output.accept(ModItems.TUBER_BUN_CAKE.get());
                        output.accept(ModItems.WATERMELON_BUN_CAKE.get());
                        output.accept(ModItems.PUMPKIN_BUN_CAKE.get());
                        output.accept(ModItems.GLOW_BERRY_BUN_CAKE.get());
                        output.accept(ModItems.DYEDREAM_FRUIT_BUN_CAKE.get());
                        output.accept(ModItems.HEART_CHOCOLATE.get());
                        output.accept(ModItems.WHITE_HEART_CHOCOLATE.get());
                        output.accept(ModItems.PINK_HEART_CHOCOLATE.get());
                        output.accept(ModItems.GINGERBREAD_MAN.get());
                        output.accept(ModItems.CANDY_CANE.get());
                        output.accept(ModItems.BUBBLE_GUM.get());
                        output.accept(ModItems.MELT_DREAM_COTTON_CANDY.get());
                        output.accept(ModItems.YINHUL_COTTON_CANDY.get());
                        output.accept(ModItems.JELLYFISH_MUD.get());
                        output.accept(ModItems.JELLYFISH_JELLO.get());
                        output.accept(ModItems.ELIXIR_BOTTLE.get());
                        output.accept(ModItems.ELIXIR_BOTTLE_OF_MELT_DREAM.get());
                        output.accept(ModItems.ELIXIR_BOTTLE_OF_RAGE_ELIXIR.get());
                        output.accept(ModItems.PINEAPPLE_LOVE_SEA.get());
                        output.accept(ModItems.RED_DEW.get());
                        output.accept(ModItems.BLUE_DEW.get());
                        output.accept(ModItems.GLASS_JAR_OF_GOLDENROD_TEA.get());
                        output.accept(ModItems.LEGENDARY_DRAGON_HORN_ICE_CREAM.get());
                        output.accept(ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME.get());
                        output.accept(ModItems.LIGHT_ORGAN.get());
                        output.accept(ModItems.ODD_BACON_AND_EGG.get());

                    })
                    .build());

    // ===== 物品 =====
    // 来源: 旧 paster_tab_0 (材料/杂项)
    public static final RegistryObject<CreativeModeTab> PASTERDREAM_ITEMS_TAB = CREATIVE_MODE_TABS.register("pasterdream_items_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_items_tab"))
                    .icon(() -> new ItemStack(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get());
                        output.accept(ModItems.DYEDREAM_DUST.get());
                        output.accept(ModItems.DYEDREAM_DUST_PIECE.get());
                        output.accept(ModItems.DYEDREAM_DYE.get());
                        output.accept(ModItems.DYEDREAM_BUD_NUGGET.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ.get());
                        output.accept(ModItems.RAW_TITANIUM.get());
                        output.accept(ModItems.RAW_MOLTEN_GOLD.get());
                        output.accept(ModItems.TITANIUM_INGOT.get());
                        output.accept(ModItems.MOLTEN_GOLD_INGOT.get());
                        output.accept(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get());
                        output.accept(ModItems.DYEDREAM_ALLOY_INGOT.get());
                        output.accept(ModItems.TITANIUM_NUGGET.get());
                        output.accept(ModItems.MOLTEN_GOLD_NUGGET.get());
                        output.accept(ModItems.DYEDREAM_ALLOY_NUGGET.get());
                        output.accept(ModItems.SOUL_DUST.get());
                        output.accept(ModItems.SOUL_ESSENCE.get());
                        output.accept(ModItems.MAGIC_STONE.get());
                        output.accept(ModItems.PINK_SLIMEBALL.get());
                        output.accept(ModItems.DREAM_FERTILIZER.get());
                        output.accept(ModItems.PINK_EGG.get());
                        output.accept(ModItems.LIGHT_BALL.get());
                        output.accept(ModItems.DYEDREAM_COROLLA.get());
                        output.accept(ModItems.WHITE_COROLLA.get());
                        output.accept(ModItems.COTTON.get());
                        output.accept(ModItems.CHARGED_AMETHYST.get());
                        output.accept(ModItems.RYE_SEED.get());
                        output.accept(ModItems.FLOUR.get());
                        output.accept(ModItems.SALT.get());
                        output.accept(ModItems.COARSE_SALT.get());
                        output.accept(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get());
                        output.accept(ModItems.MELT_DREAM_LIQUID_BUCKET.get());
                        output.accept(ModItems.SHADOW_LIQUID_BUCKET.get());
                        output.accept(ModItems.SCULK_HEART.get());
                        output.accept(ModItems.BLACK_STICK.get());
                        output.accept(ModItems.SPOOL.get());
                        output.accept(ModItems.FABRIC.get());
                        output.accept(ModItems.PROTECT_DECK.get());
                        output.accept(ModItems.REED_ROD.get());
                        output.accept(ModItems.BLUE_HEART_OF_THE_SEA.get());
                        output.accept(ModItems.ELDER_GUARDIAN_SCALE.get());
                        output.accept(ModItems.MELT_DREAM_COIN.get());
                        output.accept(ModItems.MELT_DREAM_COIN_PILE.get());
                        output.accept(ModItems.JUNGLE_SPORE.get());
                    })
                    .build());

    // ===== 装备 =====
    // 升级套件、武器、工具、防具、饰品
    public static final RegistryObject<CreativeModeTab> PASTERDREAM_GEAR_TAB = CREATIVE_MODE_TABS.register("pasterdream_gear_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_gear_tab"))
                    .icon(() -> new ItemStack(ModItems.SHARP_MELT_DREAM_SWORD.get()))
                    .displayItems((parameters, output) -> {
                        // 升级套件
                        output.accept(ModItems.TITANIUM_UPGRADE.get());
                        output.accept(ModItems.SCULK_UPGRADE.get());
                        output.accept(ModItems.DYEDREAM_UPGRADE.get());
                        // 铜工具
                        output.accept(ModItems.COPPER_SWORD.get());
                        output.accept(ModItems.COPPER_PICKAXE.get());
                        output.accept(ModItems.COPPER_AXE.get());
                        output.accept(ModItems.COPPER_SHOVEL.get());
                        output.accept(ModItems.COPPER_HOE.get());
                        // 铜装备
                        output.accept(ModItems.COPPER_HELMET.get());
                        output.accept(ModItems.COPPER_CHESTPLATE.get());
                        output.accept(ModItems.COPPER_LEGGINGS.get());
                        output.accept(ModItems.COPPER_BOOTS.get());
                        // 钛金工具
                        output.accept(ModItems.TITANIUM_SWORD.get());
                        output.accept(ModItems.TITANIUM_PICKAXE.get());
                        output.accept(ModItems.TITANIUM_AXE.get());
                        output.accept(ModItems.TITANIUM_SHOVEL.get());
                        output.accept(ModItems.TITANIUM_HOE.get());
                        // 染梦合金工具
                        output.accept(ModItems.DYEDREAM_SWORD.get());
                        output.accept(ModItems.SHARP_MELT_DREAM_SWORD.get());
                        output.accept(ModItems.DYEDREAM_PICKAXE.get());
                        output.accept(ModItems.DYEDREAM_AXE.get());
                        output.accept(ModItems.DYEDREAM_SHOVEL.get());
                        output.accept(ModItems.DYEDREAM_HOE.get());
                        output.accept(ModItems.DYEDREAM_HAMMER.get());
                        // 钛金装备
                        output.accept(ModItems.TITANIUM_HELMET.get());
                        output.accept(ModItems.TITANIUM_CHESTPLATE.get());
                        output.accept(ModItems.TITANIUM_LEGGINGS.get());
                        output.accept(ModItems.TITANIUM_BOOTS.get());
                        // 回响装备
                        output.accept(ModItems.SCULK_HELMET.get());
                        output.accept(ModItems.SCULK_CHESTPLATE.get());
                        output.accept(ModItems.SCULK_LEGGINGS.get());
                        output.accept(ModItems.SCULK_BOOTS.get());
                        // 染梦合金装备
                        output.accept(ModItems.DYEDREAM_HELMET.get());
                        output.accept(ModItems.DYEDREAM_CHESTPLATE.get());
                        output.accept(ModItems.DYEDREAM_LEGGINGS.get());
                        output.accept(ModItems.DYEDREAM_BOOTS.get());
                        // 炙焰金工具
                        output.accept(ModItems.MOLTEN_GOLD_SWORD.get());
                        output.accept(ModItems.MOLTEN_GOLD_PICKAXE.get());
                        output.accept(ModItems.MOLTEN_GOLD_AXE.get());
                        output.accept(ModItems.MOLTEN_GOLD_SHOVEL.get());
                        output.accept(ModItems.MOLTEN_GOLD_HOE.get());
                        // 狱炎工具
                        output.accept(ModItems.HELLFIRE_SWORD.get());
                        output.accept(ModItems.HELLFIRE_PICKAXE.get());
                        output.accept(ModItems.INFERNO_SWORD.get());
                        // 融梦水晶工具
                        output.accept(ModItems.MELT_DREAM_PICKAXE.get());
                        output.accept(ModItems.MELT_DREAM_AXE.get());
                        output.accept(ModItems.MELT_DREAM_SHOVEL.get());
                        output.accept(ModItems.MELT_DREAM_HOE.get());
                        // 独立武器
                        output.accept(ModItems.TIDE_SWORD.get());
                        output.accept(ModItems.BEIHAI_RUO_TIDE_SWORD.get());
                    })
                    .build());

    // ===== 方块 =====
    // 来源: 旧 paster_tab_1 (建筑方块), 按原顺序
    public static final RegistryObject<CreativeModeTab> PASTERDREAM_BLOCKS_TAB = CREATIVE_MODE_TABS.register("pasterdream_blocks_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_blocks_tab"))
                    .icon(() -> new ItemStack(ModBlocks.DYEDREAM_GRASS_BLOCK.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.DYEDREAM_GRASS_BLOCK.get());
                        output.accept(ModItems.DYEDREAM_FARMLAND.get());
                        output.accept(ModItems.DYEDREAM_DIRT.get());
                        output.accept(ModItems.DYEDREAM_SAND.get());
                        output.accept(ModItems.DYEDREAM_LOG.get());
                        output.accept(ModItems.DYEDREAM_WOOD.get());
                        output.accept(ModItems.DYEDREAM_LEAVES.get());
                        output.accept(ModItems.DYEDREAM_WORLDTREE_LEAVES.get());
                        output.accept(ModItems.DYEDREAM_PLANKS.get());
                        output.accept(ModItems.DYEDREAM_STAIRS.get());
                        output.accept(ModItems.DYEDREAM_SLAB.get());
                        output.accept(ModItems.DYEDREAM_FENCE.get());
                        output.accept(ModItems.DYEDREAM_FENCE_GATE.get());
                        output.accept(ModItems.DYEDREAM_PANE.get());
                        output.accept(ModItems.DYEDREAM_DOOR.get());
                        output.accept(ModItems.DYEDREAM_TRAPDOOR.get());
                        output.accept(ModItems.DYEDREAM_PRESSURE_PLATE.get());
                        output.accept(ModItems.DYEDREAM_BUTTON.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_ORE.get());
                        output.accept(ModItems.DYEDREAM_DUST_ORE.get());
                        output.accept(ModItems.AMBER_CANDY_ORE.get());
                        output.accept(ModItems.DYEDREAM_ALLOY_BLOCK.get());
                        output.accept(ModItems.PINK_SLIME_BLOCK.get());
                        output.accept(ModItems.PINK_MUSHROOM_BLOCK.get());
                        output.accept(ModItems.PINK_MUSHROOM_STEM.get());
                        output.accept(ModItems.PINK_MUSHROOM_PORES.get());
                        output.accept(ModItems.PINK_SHROOMLIGHT.get());
                        output.accept(ModItems.DYEDREAM_ICE.get());
                        output.accept(ModItems.DYEDREAM_PACKED_ICE.get());
                        output.accept(ModItems.BIG_BUBBLE.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.BRICKS_DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.PILLAR_DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.CHISELED_DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_BLOCK_STAIRS.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_BLOCK_SLAB.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_BLOCK_WALL.get());
                        output.accept(ModItems.DYEDREAM_CRYSTAL_LANTERN.get());
                        output.accept(ModItems.DYEDREAM_LANTERN.get());
                        output.accept(ModItems.DYEDREAM_GLASS.get());
                        output.accept(ModItems.DYEDREAM_GLASS_PANE.get());
                        output.accept(ModItems.CARVE_DYEDREAM_GLASS.get());
                        output.accept(ModItems.CARVE_DYEDREAM_GLASS_PANE.get());
                        output.accept(ModItems.GOLD_CARVE_DYEDREAM_GLASS.get());
                        output.accept(ModItems.GOLD_CARVE_DYEDREAM_GLASS_PANE.get());
                        output.accept(ModItems.LARGE_DYEDREAM_BUD.get());
                        output.accept(ModItems.MEDIUM_DYEDREAM_BUD.get());
                        output.accept(ModItems.SMALL_DYEDREAM_BUD.get());
                        output.accept(ModItems.DYEDREAM_BUD_BLOCK.get());
                        output.accept(ModItems.DYEDREAM_BUD_STAIRS.get());
                        output.accept(ModItems.DYEDREAM_BUD_SLAB.get());
                        output.accept(ModItems.DYEDREAM_BUD_WALL.get());
                        output.accept(ModItems.ICE_STONE.get());
                        output.accept(ModItems.ICE_BUD.get());
                        output.accept(ModItems.CLOUD.get());
                        output.accept(ModItems.DARK_CLOUD.get());
                        output.accept(ModItems.POLISHED_CALCITE.get());
                        output.accept(ModItems.POLISHED_CALCITE_STAIRS.get());
                        output.accept(ModItems.POLISHED_CALCITE_SLAB.get());
                        output.accept(ModItems.POLISHED_CALCITE_WALL.get());
                        output.accept(ModItems.CALCITE_TILES.get());
                        output.accept(ModItems.CALCITE_TILES_STAIRS.get());
                        output.accept(ModItems.CALCITE_TILES_SLAB.get());
                        output.accept(ModItems.CALCITE_TILES_WALL.get());
                        output.accept(ModItems.TITANIUM_ORE.get());
                        output.accept(ModItems.DEEPSLATE_TITANIUM_ORE.get());
                        output.accept(ModItems.MOLTEN_GOLD_ORE.get());
                        output.accept(ModItems.SOUL_ORE.get());
                        output.accept(ModItems.RAW_TITANIUM_BLOCK.get());
                        output.accept(ModItems.SALT_BLOCK.get());
                        output.accept(ModItems.TITANIUM_BLOCK.get());
                        output.accept(ModItems.MOLTEN_GOLD_BLOCK.get());
                        output.accept(ModItems.CHARGED_AMETHYST_BLOCK.get());
                    })
                    .build());

    // ===== 植物 =====
    // 来源: 旧 paster_tab_2, 按原顺序
    public static final RegistryObject<CreativeModeTab> PASTERDREAM_PLANTS_TAB = CREATIVE_MODE_TABS.register("pasterdream_plants_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_plants_tab"))
                    .icon(() -> new ItemStack(ModBlocks.DYEDREAM_SAPLING.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.DYEDREAM_SAPLING.get());
                        output.accept(ModItems.DYEDREAM_COROLLA_CROP_AGE_0.get());
                        output.accept(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get());
                        output.accept(ModItems.LIGHT_BALL_CROP_AGE_0.get());
                        output.accept(ModItems.LIGHT_BALL_CROP_AGE_1.get());
                        output.accept(ModItems.CLOUD_CROP_AGE_0.get());
                        output.accept(ModItems.CLOUD_CROP_AGE_1.get());
                        output.accept(ModItems.STEM_GRASS.get());
                        output.accept(ModItems.TALL_STEM_GRASS.get());
                        output.accept(ModItems.PINK_MUSHROOM.get());
                        output.accept(ModItems.TALL_PINK_MUSHROOM.get());
                        output.accept(ModItems.POLISHED_CALCITE_STALICRIPE.get());
                        output.accept(ModItems.SMALL_POLISHED_CALCITE_STALICRIPE.get());
                        output.accept(ModItems.DYEDREAM_MOSS.get());
                        output.accept(ModItems.LINHT_FLOWER.get());
                        output.accept(ModItems.DYEDREAM_LILY_OF_THE_VALLEY.get());
                        output.accept(ModItems.SINGULARITY_FERN.get());
                        output.accept(ModItems.DYEDREAM_VINE.get());
                        output.accept(ModItems.DYEDREAM_LILY_PAD.get());
                        output.accept(ModItems.DYEDREAM_SEAGRASS.get());
                        output.accept(ModItems.DYEDREAM_LOTUS.get());
                        output.accept(ModItems.DREAMING_LOTUS.get());
                        output.accept(ModItems.MISTY_DREAMING_LOTUS.get());
                        output.accept(ModItems.EDELWEISS.get());
                        output.accept(ModItems.NIPPY_EDELWEISS.get());
                        output.accept(ModItems.WHITE_COROLLA_CROP_AGE_0.get());
                        output.accept(ModItems.WHITE_COROLLA_CROP_AGE_1.get());
                        output.accept(ModItems.COTTON_CROP_AGE_0.get());
                        output.accept(ModItems.COTTON_CROP_AGE_1.get());
                        output.accept(ModItems.FERRARIA_CRISPA.get());
                        output.accept(ModItems.EUSTOMA.get());

                        output.accept(ModItems.MALVA_SINENSIS_CAVAN.get());
                        output.accept(ModItems.REED.get());
                        output.accept(ModItems.RYE.get());
                        output.accept(ModItems.OATS.get());
                        output.accept(ModItems.GOLDENROD.get());

                        output.accept(ModItems.BLAZE_FLOWER.get());
                        output.accept(ModItems.CRIMSON_THORNS.get());



                        output.accept(ModItems.WHITE_ORCHID_FLOWER.get());
                    })
                    .build());

    // ===== 设备 =====
    // 来源: 旧 paster_tab_5 (功能方块), 按原顺序
    public static final RegistryObject<CreativeModeTab> PASTERDREAM_EQUIPMENT_TAB = CREATIVE_MODE_TABS.register("pasterdream_equipment_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_equipment_tab"))
                    .icon(() -> new ItemStack(ModBlocks.QYM_DOLL.get()))
                    .displayItems((parameters, output) ->
                    {
                        output.accept(ModBlocks.QYM_DOLL.get());
                        output.accept(ModBlocks.UUZ_DOLL.get());
                        output.accept(ModBlocks.DYEDREAM_CRACK.get());
                        output.accept(ModBlocks.CLAYPAN.get());
                        output.accept(ModBlocks.DREAM_CAULDRON.get());
                        output.accept(ModItems.MORTAR.get());
                        output.accept(ModBlocks.DYEDREAM_DESK.get());
                        output.accept(ModBlocks.SHADOW_DESK.get());
                        output.accept(ModBlocks.PICNIC_BASKET.get());
                        output.accept(ModBlocks.SHADOW_CHEST.get());
                        output.accept(ModBlocks.WIND_MOOR_CRATE.get());
                    })
                    .build());

    //寻梦者笔记
    public static final RegistryObject<CreativeModeTab> PASTERDREAM_DREAM_NOTES_TAB = CREATIVE_MODE_TABS.register("pasterdream_dream_notes_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_dream_notes_tab"))
                    .icon(() -> new ItemStack(ModItems.DREAM_NOTES_DYEDREAM_WORLD.get()))
                    .displayItems((parameters, output) ->
                    {
                        output.accept(DreamNotesWithNBT.dreamNotesWithNBT(ModItems.DREAM_NOTES_DYEDREAM_WORLD.get(), "content", "developerNameList"));
                        output.accept(DreamNotesWithNBT.dreamNotesWithNBT(ModItems.DREAM_NOTES_DYEDREAM_WORLD.get(), "content", "dyedreamCreak"));
                        output.accept(DreamNotesWithNBT.dreamNotesWithNBT(ModItems.DREAM_NOTES_DYEDREAM_WORLD.get(), "content", "dyedreamWorld"));
                        output.accept(DreamNotesWithNBT.dreamNotesWithNBT(ModItems.DREAM_NOTES_DYEDREAM_WORLD.get(), "content", "pinkSlime"));
                        output.accept(DreamNotesWithNBT.dreamNotesWithNBT(ModItems.DREAM_NOTES_DYEDREAM_WORLD.get(), "content", "whiteCorolla"));
                        output.accept(DreamNotesWithNBT.dreamNotesWithNBT(ModItems.DREAM_NOTES_DYEDREAM_WORLD.get(), "content", "paleBoneNeedle"));
                        output.accept(DreamNotesWithNBT.dreamNotesWithNBT(ModItems.DREAM_NOTES_DYEDREAM_WORLD.get(), "content", "dreamFertilizer"));
                        output.accept(DreamNotesWithNBT.dreamNotesWithNBT(ModItems.DREAM_NOTES_DYEDREAM_WORLD.get(), "content", "dreamAccumulator"));

                        output.accept(DreamNotesWithNBT.dreamNotesWithNBT(ModItems.DREAM_NOTES_LAMP_SHADOW_WORLD.get(), "content", "theLurkersInTheShadow"));
                        output.accept(DreamNotesWithNBT.dreamNotesWithNBT(ModItems.DREAM_NOTES_LAMP_SHADOW_WORLD.get(), "content", "infestedChurch"));
                        output.accept(DreamNotesWithNBT.dreamNotesWithNBT(ModItems.DREAM_NOTES_LAMP_SHADOW_WORLD.get(), "content", "depositionShadow"));
                        output.accept(DreamNotesWithNBT.dreamNotesWithNBT(ModItems.DREAM_NOTES_LAMP_SHADOW_WORLD.get(), "content", "shadowTravelogue"));
                        output.accept(DreamNotesWithNBT.dreamNotesWithNBT(ModItems.DREAM_NOTES_LAMP_SHADOW_WORLD.get(), "content", "shadowDungeon"));
                        output.accept(DreamNotesWithNBT.dreamNotesWithNBT(ModItems.DREAM_NOTES_LAMP_SHADOW_WORLD.get(), "content", "scare"));

                        output.accept(DreamNotesWithNBT.dreamNotesWithNBT(ModItems.DREAM_NOTES_WIND_JOURNEY_WORLD.get(), "content", "notHaveWingsBirdHaveSpreadWingsDreamToo"));

                        output.accept(BluePrintWithNBT.bluePrintWithNBT(ModItems.BLUE_PRINT.get(), "content", "wraponWorkshop", AddBluePrintContentListTag.weaponWorkshop()));

                        output.accept(NBTBookRegisterToCreativeModTab.GardenDecryption.MistyDreamingLotus());
                        output.accept(NBTBookRegisterToCreativeModTab.GardenDecryption.PiercingEdelweiss());
                        output.accept(NBTBookRegisterToCreativeModTab.GardenDecryption.GoldenFox());
                        output.accept(NBTBookRegisterToCreativeModTab.DyedreamTravelogue.DyedreamTravelogue_0());
                        output.accept(NBTBookRegisterToCreativeModTab.DyedreamTravelogue.DyedreamTravelogue_1());
                        output.accept(NBTBookRegisterToCreativeModTab.DyedreamTravelogue.DyedreamTravelogue_2());
                        output.accept(NBTBookRegisterToCreativeModTab.DreamChurch.DreamChurch_0());
                        output.accept(NBTBookRegisterToCreativeModTab.DreamChurch.DreamChurch_1());
                        output.accept(NBTBookRegisterToCreativeModTab.DreamChurch.DreamChurch_2());
                        output.accept(NBTBookRegisterToCreativeModTab.DyedreamWorld.CrystalBall());
                        output.accept(NBTBookRegisterToCreativeModTab.DyedreamWorld.DreamWishingTree());
                        output.accept(NBTBookRegisterToCreativeModTab.DyedreamWorld.WarmColdWind());
                        output.accept(NBTBookRegisterToCreativeModTab.DyedreamWorld.PinkAgalicHouse());
                        output.accept(NBTBookRegisterToCreativeModTab.DyedreamWorld.MeltDreamLiquidWell());
                        output.accept(NBTBookRegisterToCreativeModTab.DyedreamWorld.FloatingIslandDiary());
                        output.accept(NBTBookRegisterToCreativeModTab.DyedreamWorld.FallingLeavesReturnToTheirRoots_CrackedPodsReturnToTheirCrowns());
                        output.accept(NBTBookRegisterToCreativeModTab.DyedreamWorld.DreamFloating());
                        output.accept(NBTBookRegisterToCreativeModTab.DyedreamWorld.BigBubble());
                        output.accept(NBTBookRegisterToCreativeModTab.DyedreamWorld.DreamLaboratory());
                        output.accept(NBTBookRegisterToCreativeModTab.DyedreamWorld.InteractingWithDream());
                        output.accept(NBTBookRegisterToCreativeModTab.DyedreamWorld.DyedreamWorldTree());
                        output.accept(NBTBookRegisterToCreativeModTab.DyedreamWorld.WingsOfDaiDaLuoSiAndFloatingIsland());
                        output.accept(NBTBookRegisterToCreativeModTab.OverWorld.DesertCottage());
                        output.accept(NBTBookRegisterToCreativeModTab.lampShadowWorld.ResearchNotes_BlackMetal());
                        output.accept(NBTBookRegisterToCreativeModTab.lampShadowWorld.LampShadowTravelogue_1());
                        output.accept(NBTBookRegisterToCreativeModTab.lampShadowWorld.ShadowPlace());
                        output.accept(NBTBookRegisterToCreativeModTab.lampShadowWorld.ShadowFungusHouse());
                        output.accept(NBTBookRegisterToCreativeModTab.lampShadowWorld.ShadowUndergroundWorkroom());
                        output.accept(NBTBookRegisterToCreativeModTab.lampShadowWorld.ShadowDyedSloppyHandwritingNotes());
                        output.accept(NBTBookRegisterToCreativeModTab.lampShadowWorld.RegardingShadowHandsEssay());
                        output.accept(NBTBookRegisterToCreativeModTab.lampShadowWorld.DecisiveBattleWithShadowHandsAndFail());
                        output.accept(NBTBookRegisterToCreativeModTab.WindJourneyWorld.WindBreakKnight());
                        output.accept(NBTBookRegisterToCreativeModTab.WindJourneyWorld.MorningNewWind());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
