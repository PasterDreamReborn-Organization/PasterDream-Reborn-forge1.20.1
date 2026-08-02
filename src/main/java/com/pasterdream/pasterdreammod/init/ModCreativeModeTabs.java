package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.itemwithnbt.blueprintwithnbt.BluePrintWithNBT;
import com.pasterdream.pasterdreammod.helper.itemwithnbt.dreamnoteswithnbt.DreamNotesWithNBT;
import com.pasterdream.pasterdreammod.helper.itemwithnbt.lootgeneratorwithnbt.LootGeneratorWithNBT;
import com.pasterdream.pasterdreammod.world.block.meltdreamcrystalchest.MeltDreamCrystalChestLootTableNBT;
import com.pasterdream.pasterdreammod.world.item.DeepTreasureItem;
import com.pasterdream.pasterdreammod.world.item.SandofTimeItem;
import com.pasterdream.pasterdreammod.world.item.blueprints.AddBluePrintContentListTag;
import com.pasterdream.pasterdreammod.world.item.curio.RedDewRingItem;
import com.pasterdream.pasterdreammod.world.item.curio.StrikeRingItem;
import com.pasterdream.pasterdreammod.world.item.dreamnotes.NBTBookRegisterToCreativeModTab;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookWithNBTToCreativeModeTab;
import com.pasterdream.pasterdreammod.world.item.prophecycard.ProphecyCardItem;
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
                        output.accept(ModItems.GALAXY_JELLY.get());
                        output.accept(ModItems.FORTUNE_JELLY.get());
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
                        output.accept(ModItems.JUNGLE_SPORE.get());
                        output.accept(ModItems.BUBBLE_TEA.get());
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
                        output.accept(ModItems.MELT_DREAM_COIN.get());
                        output.accept(ModItems.MELT_DREAM_COIN_PILE.get());
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
                        output.accept(ModItems.DEEP_SEA_TREASURE.get());
                        output.accept(DeepTreasureItem.createSuper(ModItems.DEEP_SEA_TREASURE.get()));
                        output.accept(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get());
                        output.accept(DeepTreasureItem.createSuper(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get()));
                        output.accept(ModItems.SHADOW_DEEP_SEA_TREASURE.get());
                        output.accept(DeepTreasureItem.createSuper(ModItems.SHADOW_DEEP_SEA_TREASURE.get()));
                        output.accept(ModItems.SOUL_ESSENCE.get());
                        output.accept(ModItems.MAGIC_STONE.get());
                        output.accept(ModItems.PINK_SLIMEBALL.get());
                        output.accept(ModItems.DREAM_FERTILIZER.get());
                        output.accept(ModItems.PINK_EGG.get());
                        output.accept(ModItems.LIGHT_BALL.get());
                        output.accept(ModItems.DYEDREAM_COROLLA.get());
                        output.accept(ModItems.WHITE_COROLLA.get());
                        output.accept(ModItems.WHITE_CRYSTAL.get());
                        output.accept(ModItems.BLUE_HEART_OF_THE_SEA.get());
                        output.accept(ModItems.ELDER_GUARDIAN_SCALE.get());
                        output.accept(ModItems.CHARGED_AMETHYST.get());
                        output.accept(ModItems.PROTECT_DECK.get());
                        output.accept(ModItems.COTTON.get());
                        output.accept(ModItems.SPOOL.get());
                        output.accept(ModItems.FABRIC.get());
                        output.accept(ModItems.PERGAMYN.get());
                        output.accept(ModItems.REED_ROD.get());
                        output.accept(ModItems.RYE_SEED.get());
                        output.accept(ModItems.FLOUR.get());
                        output.accept(ModItems.SALT.get());
                        output.accept(ModItems.COARSE_SALT.get());
                        output.accept(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get());
                        output.accept(ModItems.MELT_DREAM_LIQUID_BUCKET.get());
                        output.accept(ModItems.SHADOW_LIQUID_BUCKET.get());
                        output.accept(ModItems.GLASS_JAR_OF_INK.get());
                        output.accept(ModItems.SCULK_HEART.get());
                        output.accept(ModItems.BLACK_STICK.get());
                        output.accept(ModItems.BROKEN_NOTE.get());
                        output.accept(ModItems.UNKNOWN_NOTE.get());
                        output.accept(ModItems.BLACK_METAL_INGOT.get());
                        output.accept(ModItems.BLACK_METAL_GRAIN.get());
                        output.accept(ModItems.RUST_BLACK_METAL_GRAIN.get());
                        output.accept(ModItems.NIGHTMARE_FUEL.get());
                        output.accept(ModItems.SHADOW_DUNGEON_KEY.get());
                        output.accept(ModItems.PALE_BONENEEDLE.get());
                        output.accept(ModItems.ROOTS_PALE_BONENEEDLE.get());
                        output.accept(ModItems.BLACK_BEETLE_CARAPACE.get());
                        output.accept(ModItems.BLACK_BEETLE_VOCALCORD.get());
                        output.accept(ModItems.PEBBLE.get());
                        // 饰品
                        output.accept(ModItems.EMBRYO_CHARM.get());
                        output.accept(ModItems.EMBRYO_NECKLACE.get());
                        output.accept(ModItems.EMBRYO_RING.get());
                        output.accept(ModItems.EMBRYO_BELT.get());
                        // 音乐唱片
                        output.accept(ModItems.SWEET_DREAM_MUSIC_DISC.get());
                        output.accept(ModItems.SNOWFALL_DREAM_MUSIC_DISC.get());
                        // 预言卡
                        output.accept(ModItems.EMPTY_PROPHECY_CARD.get());
                        output.accept(ProphecyCardItem.createCard(ModItems.PROPHECY_CARD.get(), ProphecyCardItem.TYPE_BALANCE));
                        output.accept(ProphecyCardItem.createCard(ModItems.PROPHECY_CARD.get(), ProphecyCardItem.TYPE_CHAOS));
                        output.accept(ProphecyCardItem.createCard(ModItems.PROPHECY_CARD.get(), ProphecyCardItem.TYPE_CONFLICT));
                        output.accept(ProphecyCardItem.createCard(ModItems.PROPHECY_CARD.get(), ProphecyCardItem.TYPE_GRAVEYARD));
                        output.accept(ProphecyCardItem.createCard(ModItems.PROPHECY_CARD.get(), ProphecyCardItem.TYPE_GUARD));
                        output.accept(ProphecyCardItem.createCard(ModItems.PROPHECY_CARD.get(), ProphecyCardItem.TYPE_HOLY_GRAIL));
                        output.accept(ProphecyCardItem.createCard(ModItems.PROPHECY_CARD.get(), ProphecyCardItem.TYPE_SIN));
                        output.accept(ProphecyCardItem.createCard(ModItems.PROPHECY_CARD.get(), ProphecyCardItem.TYPE_SPRINT));
                        output.accept(ProphecyCardItem.createCard(ModItems.PROPHECY_CARD.get(), ProphecyCardItem.TYPE_WIELDING_SWORD));
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
                        output.accept(ModItems.ATTACK_ENHANCE_STONE.get());
                        output.accept(ModItems.LUCK_ENHANCE_STONE.get());
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
                        // 钛金装备
                        output.accept(ModItems.TITANIUM_HELMET.get());
                        output.accept(ModItems.TITANIUM_CHESTPLATE.get());
                        output.accept(ModItems.TITANIUM_LEGGINGS.get());
                        output.accept(ModItems.TITANIUM_BOOTS.get());
                        // 染梦合金工具
                        output.accept(ModItems.DYEDREAM_SWORD.get());
                        output.accept(ModItems.SHARP_MELT_DREAM_SWORD.get());
                        output.accept(ModItems.DYEDREAM_PICKAXE.get());
                        output.accept(ModItems.DYEDREAM_AXE.get());
                        output.accept(ModItems.DYEDREAM_SHOVEL.get());
                        output.accept(ModItems.DYEDREAM_HOE.get());
                        output.accept(ModItems.DYEDREAM_HAMMER.get());
                        // 染梦合金装备
                        output.accept(ModItems.DYEDREAM_HELMET.get());
                        output.accept(ModItems.DYEDREAM_CHESTPLATE.get());
                        output.accept(ModItems.DYEDREAM_LEGGINGS.get());
                        output.accept(ModItems.DYEDREAM_BOOTS.get());
                        // 融梦水晶工具
                        output.accept(ModItems.MELT_DREAM_PICKAXE.get());
                        output.accept(ModItems.MELT_DREAM_AXE.get());
                        output.accept(ModItems.MELT_DREAM_SHOVEL.get());
                        output.accept(ModItems.MELT_DREAM_HOE.get());
                        // 钓竿
                        output.accept(ModItems.STAR_WISH_ROD.get());
                        // 回响装备
                        output.accept(ModItems.SCULK_HELMET.get());
                        output.accept(ModItems.SCULK_CHESTPLATE.get());
                        output.accept(ModItems.SCULK_LEGGINGS.get());
                        output.accept(ModItems.SCULK_BOOTS.get());
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
                        // 独立武器
                        output.accept(ModItems.TIDE_SWORD.get());
                        output.accept(ModItems.BEIHAI_RUO_TIDE_SWORD.get());
                        output.accept(ModItems.KUSANAGI.get());
                        output.accept(ModItems.MURAKUMO_KUSANAGI.get());
                        output.accept(ModItems.SWORD_EMBRYO.get());
                        output.accept(ModItems.DESERT_SWORD.get());
                        output.accept(ModItems.CHENJINGMEN_DESERT_SWORD.get());
                        output.accept(ModItems.BROKEN_HERO_SWORD.get());
                        output.accept(ModItems.WHITE_SWORD.get());
                        // 特殊道具
                        output.accept(ModItems.DREAM_HARP_OF_WANDERER.get());
                        output.accept(ModItems.QYM_CAT_EARS.get());
                        output.accept(ModItems.QYM_WIND_SHIRT.get());
                        output.accept(ModItems.QYM_SWAYING_SKIRT.get());
                        output.accept(ModItems.QYM_CLOUD_BOOTS.get());
                        // 机械光翼
                        output.accept(ModItems.MACHINE_LIGHT_WING.get());
                        // 饰品
                        output.accept(ModItems.GOLD_CHARM.get());
                        output.accept(ModItems.ENDEYE_CHARM.get());
                        output.accept(ModItems.SEA_CHARM.get());
                        output.accept(ModItems.CARAPAX_CHARM.get());
                        output.accept(ModItems.WORLDTREE_SEEDPOD.get());
                        output.accept(ModItems.GARLAND.get());
                        output.accept(ModItems.FOURLEAF_CLOVER_CURIO.get());
                        output.accept(ModItems.SNOW_VOW_HEAD.get());
                        output.accept(ModItems.QYM_BUTTERFLY_STAR_HAIRPIN.get());
                        output.accept(ModItems.LIGHT_BUTTERFLY_CURIO.get());
                        output.accept(ModItems.BRIGHT_BUTTERFLY_CURIO.get());
                        output.accept(ModItems.BOBOJI_CURIO.get());
                        output.accept(ModItems.RABBIT_FOOT_NECKLACE.get());
                        output.accept(ModItems.FEATHER_NECKLACE.get());
                        output.accept(ModItems.HEALTH_NECKLACE.get());
                        output.accept(ModItems.FIRE_NECKLACE.get());
                        output.accept(ModItems.CROSS_NECKLACE.get());
                        output.accept(ModItems.NATURE_BELT.get());
                        output.accept(ModItems.TRAVELER_BELT.get());
                        output.accept(ModItems.DREAM_TRAVELER_BELT.get());
                        output.accept(RedDewRingItem.createWithLv(ModItems.RED_DEW_RING.get(), 1));
                        output.accept(RedDewRingItem.createWithLv(ModItems.RED_DEW_RING.get(), 2));
                        output.accept(RedDewRingItem.createWithLv(ModItems.RED_DEW_RING.get(), 3));
                        output.accept(RedDewRingItem.createWithLv(ModItems.RED_DEW_RING.get(), 4));
                        output.accept(StrikeRingItem.createWithLv(ModItems.STRIKE_RING.get(), 1));
                        output.accept(StrikeRingItem.createWithLv(ModItems.STRIKE_RING.get(), 2));
                        output.accept(ModItems.COUNTER_RING.get());
                        output.accept(ModItems.MELT_DREAM_ENERGY_RING.get());
                        output.accept(ModItems.STORAGE_BAG.get());
                        output.accept(ModItems.LARGE_STORAGE_BAG.get());
                        output.accept(ModItems.WAR_FLAG.get());
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
                        output.accept(ModItems.FOX_SCULPTURE.get());
                    })
                    .build());

    // ===== 灯影之下 =====
    public static final RegistryObject<CreativeModeTab> PASTERDREAM_SHADOW_TAB = CREATIVE_MODE_TABS.register("pasterdream_shadow_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_shadow_tab"))
                    .icon(() -> new ItemStack(ModBlocks.SHADOW.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.SHADOW.get());
                        output.accept(ModItems.THICK_SHADOW.get());
                        output.accept(ModItems.SHADOW_STONE.get());
                        output.accept(ModItems.SHADOW_STONE_BRICK.get());
                        output.accept(ModItems.SHADOW_STONE_BRICK_STAIRS.get());
                        output.accept(ModItems.SHADOW_STONE_BRICK_SLAB.get());
                        output.accept(ModItems.SHADOW_STONE_BRICK_WALL.get());
                        output.accept(ModItems.NARROW_SHADOW_STONE_BRICK.get());
                        output.accept(ModItems.NARROW_SHADOW_STONE_BRICK_STAIRS.get());
                        output.accept(ModItems.NARROW_SHADOW_STONE_BRICK_SLAB.get());
                        output.accept(ModItems.NARROW_SHADOW_STONE_BRICK_WALL.get());
                        output.accept(ModItems.SHADOW_STONE_TILES.get());
                        output.accept(ModItems.SHADOW_STONE_TILES_STAIRS.get());
                        output.accept(ModItems.SHADOW_STONE_TILES_SLAB.get());
                        output.accept(ModItems.SHADOW_STONE_TILES_WALL.get());
                        output.accept(ModItems.CRACKED_SHADOW_STONE_BRICK.get());
                        output.accept(ModItems.CHISELED_SHADOW_STONE_BRICK.get());
                        output.accept(ModItems.SHADOW_DUNGEON_STONE.get());
                        output.accept(ModItems.CHISELED_SHADOW_DUNGEON_BRICKS.get());
                        output.accept(ModItems.SHADOW_DUNGEON_BRICKS.get());
                        output.accept(ModItems.CRACKED_SHADOW_DUNGEON_BRICKS.get());
                        output.accept(ModItems.FRACTURED_SHADOW_DUNGEON_BRICKS.get());
                        output.accept(ModItems.SHADOW_DUNGEON_BRICK_STAIRS.get());
                        output.accept(ModItems.SHADOW_DUNGEON_BRICK_SLAB.get());
                        output.accept(ModItems.SHATTERED_SHADOW_DUNGEON_BRICKS.get());
                        output.accept(ModItems.SHADOW_NYLIUM.get());
                        output.accept(ModItems.SHADOW_LIGHT.get());
                        output.accept(ModItems.SHADOW_SHROOMLIGHT.get());
                        output.accept(ModItems.SHADOW_CANDLE.get());
                        output.accept(ModItems.SHADOW_WART_BLOCK.get());
                        output.accept(ModItems.SHADOW_STEM.get());
                        output.accept(ModItems.SHADOW_HYPHAE.get());
                        output.accept(ModItems.STRIPPED_SHADOW_STEM.get());
                        output.accept(ModItems.STRIPPED_SHADOW_HYPHAE.get());
                        output.accept(ModItems.SHADOW_PLANKS.get());
                        output.accept(ModItems.SHADOW_STAIRS.get());
                        output.accept(ModItems.SHADOW_SLAB.get());
                        output.accept(ModItems.SHADOW_FENCE.get());
                        output.accept(ModItems.SHADOW_FENCE_GATE.get());
                        output.accept(ModItems.SHADOW_PANE.get());
                        output.accept(ModItems.SHADOW_DOOR.get());
                        output.accept(ModItems.SHADOW_TRAPDOOR.get());
                        output.accept(ModItems.SHADOW_PRESSURE_PLATE.get());
                        output.accept(ModItems.SHADOW_BUTTON.get());
                        output.accept(ModItems.BLACK_METAL_BLOCK.get());
                        output.accept(ModItems.RUST_BLACK_METAL_BLOCK.get());
                        output.accept(ModItems.RUST_BLACK_METAL_WALL.get());
                        output.accept(ModItems.RUST_BLACK_METAL_BARS.get());
                        output.accept(ModItems.SHADOW_BOOKSHELF.get());
                        output.accept(ModItems.WORN_SHADOW_BOOKSHELF.get());
                        output.accept(ModItems.COBWEB_SHADOW_BOOKSHELF.get());
                        output.accept(ModItems.KEY_SHADOW_BOOKSHELF.get());
                        output.accept(ModItems.SHADOW_ARENA_BLOCK.get());
                        output.accept(ModItems.SHADOW_FISSURE_0.get());
                        output.accept(ModItems.SHADOW_FISSURE_1.get());
                        output.accept(ModItems.SHADOW_FISSURE_2.get());
                        output.accept(ModItems.SHADOW_FISSURE_3.get());
                        output.accept(ModItems.SHADOW_FISSURE_4.get());
                        output.accept(ModItems.SHADOW_FISSURE_5.get());
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
                        output.accept(ModItems.JUNGLE_SPORANGIUM.get());
                        output.accept(ModItems.MALVA_SINENSIS_CAVAN.get());
                        output.accept(ModItems.REED.get());
                        output.accept(ModItems.RYE.get());
                        output.accept(ModItems.OATS.get());
                        output.accept(ModItems.GOLDENROD.get());
                        output.accept(ModItems.FOURLEAF_CLOVER.get());
                        output.accept(ModItems.BLAZE_FLOWER.get());
                        output.accept(ModItems.CRIMSON_THORNS.get());
                        output.accept(ModItems.SHADOW_SHORT_ROOTS.get());
                        output.accept(ModItems.SHADOW_ROOTS.get());
                        output.accept(ModItems.SHADOW_STEM_FERN.get());
                        output.accept(ModItems.SHADOW_SPROUTS.get());
                        output.accept(ModItems.SHADOW_FERN.get());
                        output.accept(ModItems.SHADOW_FUNGUS.get());


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
                        output.accept(ModBlocks.CLAY_POT.get());
                        output.accept(ModBlocks.DREAM_CAULDRON.get());
                        output.accept(ModItems.MORTAR.get());
                        output.accept(ModItems.PLIERS.get());
                        output.accept(ModBlocks.DYEDREAM_DESK.get());
                        output.accept(ModBlocks.SHADOW_DESK.get());
                        output.accept(ModBlocks.PICNIC_BASKET.get());
                        output.accept(ModBlocks.SHADOW_CHEST.get());
                        output.accept(ModBlocks.WIND_MOOR_CRATE.get());
                        output.accept(ModBlocks.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get());
                        output.accept(ModItems.FEATHER_PEN.get());
                        output.accept(ModBlocks.RESEARCH_TABLE.get());
                        output.accept(ModBlocks.LOST_SWORD_TOMB.get());
                        output.accept(SandofTimeItem.createReady(ModItems.SAND_OF_TIME.get()));
                        output.accept(ModItems.LIFE_CRYSTAL.get());
                        output.accept(ModItems.GOLDEN_FOX_SCULPTURE.get());
                        output.accept(ModItems.DESERT_HERO_TOMB.get());
                        output.accept(ModItems.MELT_DREAM_CRYSTAL_CHEST.get());
                        output.accept(ModItems.OPENED_MELT_DREAM_CRYSTAL_CHEST.get());
                        output.accept(ModItems.DREAM_ACCUMULATOR.get());
                        output.accept(ModItems.DREAM_TRAIN_STRUCTURE.get());
                        output.accept(ModItems.SORBENT.get());
                        output.accept(ModItems.WEAPON_WORKSHOP_CORE.get());
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

                        // 剧情进度引导物品
                        output.accept(ModItems.DREAM_NOTES_STORY_GUIDE.get());

                        output.accept(DreamNotesWithNBT.dreamNotesWithNBT(ModItems.DREAM_NOTES_WIND_JOURNEY_WORLD.get(), "content", "notHaveWingsBirdHaveSpreadWingsDreamToo"));

                        output.accept(BluePrintWithNBT.bluePrintWithNBT(ModItems.BLUE_PRINT.get(), "content", "wraponWorkshop", AddBluePrintContentListTag.weaponWorkshop()));

                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("使用说明"));

                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("花园解密:迷梦冶梦莲"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("花园解密:凛冽雪绒花"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("花园解密:九尾狐"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("染梦游记 其一"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("染梦游记 其二"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("染梦游记 其三"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("染梦教堂 其一"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("染梦教堂 其二"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("染梦教堂 其三"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("染梦水晶球"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("祈愿树"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("温暖的“寒风”"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("粉顶蘑菇屋"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("融梦涌泉井"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("浮空岛日记"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("落叶归根 裂荚归冠"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("梦境漂泊"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("气泡生态球"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("染梦实验室"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("来往于梦"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("染梦世界树"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("代达罗斯之翼与浮空岛"));

                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("探求秘辛"));

                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("研究笔记:黑金属"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("灯影游记 其二"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("黑暗之地"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("阴影小木屋"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("阴影地下工作室"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT(">被阴影浸染的字迹潦草的笔记<"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("关于黑暗之手的随笔"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("与黑色双手的决战和败北"));

                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("破风的骑士"));
                        output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT("清晨的新风"));

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

    public static final RegistryObject<CreativeModeTab> PASTERDREAM_DREAM_DEBUG_TAB = CREATIVE_MODE_TABS.register("pasterdream_dream_debug_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_dream_debug_tab"))
                    .icon(() -> new ItemStack(ModItems.DEBUG_SWORD.get()))
                    .displayItems((parameters, output) ->
                    {
                        output.accept(ModItems.DEBUG_SWORD.get());
                        output.accept(ModItems.LOOT_GENERATOR.get());
                        output.accept(LootGeneratorWithNBT.create(ModItems.LOOT_GENERATOR.get(), "pasterdream:chests/picnic_basket"));
                        output.accept(LootGeneratorWithNBT.create(ModItems.LOOT_GENERATOR.get(), "pasterdream:chests/dyedream_relic_chest_loot_common"));
                        output.accept(LootGeneratorWithNBT.create(ModItems.LOOT_GENERATOR.get(), "pasterdream:chests/dyedream_relic_chest_loot_rare"));
                        output.accept(LootGeneratorWithNBT.create(ModItems.LOOT_GENERATOR.get(), "pasterdream:chests/fisherman_hut_chest"));
                        output.accept(LootGeneratorWithNBT.create(ModItems.LOOT_GENERATOR.get(), "pasterdream:chests/ecosystem_bubble_chest"));
                        output.accept(LootGeneratorWithNBT.create(ModItems.LOOT_GENERATOR.get(), "pasterdream:chests/melt_dream_crystal_chest_dyedream_world_normal"));
                        output.accept(LootGeneratorWithNBT.create(ModItems.LOOT_GENERATOR.get(), "pasterdream:chests/melt_dream_crystal_chest_dyedream_world_rare"));
                        output.accept(LootGeneratorWithNBT.create(ModItems.LOOT_GENERATOR.get(), "pasterdream:chests/melt_dream_crystal_chest_dyedream_world_legend"));
                        output.accept(ModItems.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_0.get());
                        output.accept(ModItems.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_1.get());
                        output.accept(ModItems.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2.get());
                        output.accept(ModItems.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_3.get());
                        output.accept(MeltDreamCrystalChestLootTableNBT.meltDreamCrystalChestDyedreamWorldNBT(ModItems.MELT_DREAM_CRYSTAL_CHEST.get()));
                        output.accept(MeltDreamCrystalChestLootTableNBT.meltDreamCrystalChestWorldTreeNBT(ModItems.MELT_DREAM_CRYSTAL_CHEST.get()));
                        output.accept(MeltDreamCrystalChestLootTableNBT.onlyNormal(ModItems.MELT_DREAM_CRYSTAL_CHEST.get()));
                        output.accept(MeltDreamCrystalChestLootTableNBT.onlyRare(ModItems.MELT_DREAM_CRYSTAL_CHEST.get()));
                        output.accept(MeltDreamCrystalChestLootTableNBT.onlyLegend(ModItems.MELT_DREAM_CRYSTAL_CHEST.get()));
                        output.accept(ModItems.MELT_DREAM_CRYSTAL_CHEST_RESET_TOOL.get());
                        output.accept(MeltDreamCrystalChestLootTableNBT.meltDreamCrystalChestDyedreamWorldNBT(ModItems.MELT_DREAM_CRYSTAL_CHEST_RESET_TOOL.get()));
                        output.accept(MeltDreamCrystalChestLootTableNBT.meltDreamCrystalChestWorldTreeNBT(ModItems.MELT_DREAM_CRYSTAL_CHEST_RESET_TOOL.get()));
                        output.accept(MeltDreamCrystalChestLootTableNBT.onlyNormal(ModItems.MELT_DREAM_CRYSTAL_CHEST_RESET_TOOL.get()));
                        output.accept(MeltDreamCrystalChestLootTableNBT.onlyRare(ModItems.MELT_DREAM_CRYSTAL_CHEST_RESET_TOOL.get()));
                        output.accept(MeltDreamCrystalChestLootTableNBT.onlyLegend(ModItems.MELT_DREAM_CRYSTAL_CHEST_RESET_TOOL.get()));
                        output.accept(ModItems.PINK_CHICKEN_SPAWN_EGG.get());
                        output.accept(ModItems.PINK_SLIME_SPAWN_EGG.get());
                        output.accept(ModItems.GOLDEN_FOX_SPAWN_EGG.get());
                        output.accept(ModItems.SHADOW_GOLEM_SPAWN_EGG.get());
                        output.accept(ModItems.TERRORBEAK_SPAWN_EGG.get());
                        output.accept(ModItems.CRAZY_TERRORBEAK_SPAWN_EGG.get());
                        output.accept(ModItems.WEAKENESS_TERRORBEAK_SPAWN_EGG.get());
                        output.accept(ModItems.SHADOW_HAND_SPAWN_EGG.get());
                        output.accept(ModItems.SHADOW_GHOST_SPAWN_EGG.get());
                        output.accept(ModItems.SHADOW_SQUEAL_GHOST_SPAWN_EGG.get());
                        output.accept(ModItems.WAILING_SHADOW_GHOST_SPAWN_EGG.get());
                        output.accept(ModItems.FRIENDLY_SHADOW_GHOST_SPAWN_EGG.get());
                        output.accept(ModItems.BLACK_BEETLE_SPAWN_EGG.get());
                        output.accept(ModItems.BLACK_BEETLE_MOTHER_SPAWN_EGG.get());
                    }).build());

    // ===== 拓展 =====
    // 来源: 旧 paster_tab_9
    public static final RegistryObject<CreativeModeTab> PASTERDREAM_EXPANSION_TAB = CREATIVE_MODE_TABS.register("pasterdream_expansion_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_expansion_tab"))
                    .icon(() -> new ItemStack(ModItems.HIYORI_BUTTERFLY_HAIRPIN.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.DUKE_COIN_CURIO.get());
                        output.accept(ModItems.HIYORI_BUTTERFLY_HAIRPIN.get());
                        output.accept(ModItems.ALLKINDS_RING.get());
                        output.accept(ModItems.KAICHU_OMAMORI.get());
                        output.accept(ModItems.THERMAL_DAGGER.get());
                        output.accept(ModItems.MELT_DREAM_AURORIAN_STEEL.get());
                        output.accept(ModItems.TERRA_BLADE.get());
                        output.accept(ModItems.TERRA_FLOATING_ISLAND.get());
                        output.accept(ModItems.FADED_BLESSING_OF_CECILIA.get());
                        output.accept(ModItems.BLESSING_OF_CECILIA.get());
                        output.accept(ModItems.STRAWBERRY_HEART.get());
                        output.accept(ModItems.ANGEL_WING.get());
                        output.accept(ModItems.FORSAKENS_WING.get());
                        output.accept(ModItems.SLIVER_BELL.get());
                    })
                    .build());

    // ===== 追忆 =====
    // 来源: 旧 paster_tab_7
    public static final RegistryObject<CreativeModeTab> PASTERDREAM_MEMENTO_TAB = CREATIVE_MODE_TABS.register("pasterdream_memento_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_memento_tab"))
                    .icon(() -> new ItemStack(ModItems.MEMORY_GEM.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.MEMORY_GEM.get());
                        output.accept(ModItems.EMPTY_VESSEL.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
