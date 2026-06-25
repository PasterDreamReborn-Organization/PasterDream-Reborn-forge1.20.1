package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.itemwithnbt.blueprintwithnbt.BluePrintWithNBT;
import com.pasterdream.pasterdreammod.helper.itemwithnbt.dreamnoteswithnbt.DreamNotesWithNBT;
import com.pasterdream.pasterdreammod.world.item.blueprints.AddBluePrintContentListTag;
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
                    .icon(() -> new ItemStack(ModItems.DYEDREAM_ALLOY_INGOT.get()))
                    .displayItems((parameters, output) -> {
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
                        output.accept(ModItems.PINK_EGG.get());
                        output.accept(ModItems.DYEDREAM_COROLLA.get());
                        output.accept(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get());
                        output.accept(ModItems.CHARGED_AMETHYST.get());
                        output.accept(ModItems.RYE_SEED.get());
                        output.accept(ModItems.FLOUR.get());
                        output.accept(ModItems.SALT.get());
                        output.accept(ModItems.COARSE_SALT.get());
                        output.accept(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get());
                        output.accept(ModItems.MELT_DREAM_LIQUID_BUCKET.get());
                        output.accept(ModItems.SHADOW_LIQUID_BUCKET.get());
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
                        output.accept(ModItems.PINK_MUSHROOM.get());
                        output.accept(ModItems.TALL_PINK_MUSHROOM.get());
                        output.accept(ModItems.GOLDENROD.get());
                        output.accept(ModItems.DYEDREAM_MOSS.get());

                    })
                    .build());

    // ===== 设备 =====
    // 来源: 旧 paster_tab_5 (功能方块), 按原顺序
    public static final RegistryObject<CreativeModeTab> PASTERDREAM_EQUIPMENT_TAB = CREATIVE_MODE_TABS.register("pasterdream_equipment_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_equipment_tab"))
                    .icon(() -> new ItemStack(ModBlocks.CLAYPAN.get()))
                    .displayItems((parameters, output) ->
                    {
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
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
