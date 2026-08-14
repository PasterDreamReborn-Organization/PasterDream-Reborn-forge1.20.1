package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import java.util.Objects;

public class ModItemModelsProvider extends ItemModelProvider {
    public ModItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.DYEDREAM_QUARTZ.get());

        basicItem(ModItems.DYEDREAM_DUST.get());
        basicItem(ModItems.DYEDREAM_DUST_PIECE.get());
        basicItem(ModItems.AMBER_CANDY.get());
        basicItem(ModItems.PINK_SLIMEBALL.get());

        basicItem(ModItems.MORTAR.get());
        basicItem(ModItems.FEATHER_PEN.get());
        basicItem(ModItems.SORBENT.get());

        handheldItem(ModItems.PLIERS);
        basicItem(ModItems.DYEDREAM_DYE.get());
        basicItem(ModItems.DYEDREAM_BUD_NUGGET.get());
        basicItem(ModItems.DYEDREAM_ALLOY_INGOT.get());
        basicItem(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get());
        basicItem(ModItems.TITANIUM_INGOT.get());
        basicItem(ModItems.TITANIUM_NUGGET.get());
        basicItem(ModItems.RAW_TITANIUM.get());
        basicItem(ModItems.DYEDREAM_ALLOY_NUGGET.get());
        basicItem(ModItems.MOLTEN_GOLD_INGOT.get());
        basicItem(ModItems.MOLTEN_GOLD_NUGGET.get());
        basicItem(ModItems.RAW_MOLTEN_GOLD.get());
        basicItem(ModItems.GLASS_JAR.get());
        basicItem(ModItems.GLASS_JAR_OF_WATER.get());
        basicItem(ModItems.GLASS_JAR_OF_MILK.get());
        basicItem(ModItems.GLASS_JAR_OF_YEAST.get());
        basicItem(ModItems.GLASS_JAR_OF_GUIDING_DRUG.get());
        basicItem(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get());
        basicItem(ModItems.GLASS_JAR_OF_DREAM_JUICE.get());
        basicItem(ModItems.GLASS_JAR_OF_GOLDENROD_TEA.get());
        basicItem(ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME.get());
        basicItem(ModItems.GLASS_JAR_OF_INK.get());
        basicItem(ModItems.GLASS_CUP.get());
        basicItem(ModItems.GLASS_CUP_OF_APPLE_JUICE.get());
        basicItem(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get());
        basicItem(ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get());
        basicItem(ModItems.GLASS_CUP_OF_HONEY_JUICE.get());
        basicItem(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get());
        basicItem(ModItems.GLASS_CUP_OF_WATERMELON_JUICE.get());
        basicItem(ModItems.BUBBLE_TEA.get());
        basicItem(ModItems.FLOUR.get());
        basicItem(ModItems.DOUGH.get());
        basicItem(ModItems.RYE_SEED.get());
        basicItem(ModItems.DOUGH_WITH_EGG.get());
        basicItem(ModItems.COARSE_SALT.get());
        basicItem(ModItems.SALT.get());
        basicItem(ModItems.PINK_EGG.get());
        basicItem(ModItems.CHOCOLATE.get());
        basicItem(ModItems.DYEDREAM_FRUIT.get());;
        basicItem(ModItems.FIG.get());
        basicItem(ModItems.CAKE_BASE.get());
        basicItem(ModItems.CREAM_BUN_CAKE.get());
        basicItem(ModItems.BERRY_BUN_CAKE.get());
        basicItem(ModItems.TUBER_BUN_CAKE.get());
        basicItem(ModItems.WATERMELON_BUN_CAKE.get());
        basicItem(ModItems.PUMPKIN_BUN_CAKE.get());
        basicItem(ModItems.GLOW_BERRY_BUN_CAKE.get());
        basicItem(ModItems.DYEDREAM_FRUIT_BUN_CAKE.get());
        basicItem(ModItems.CHOCOLATE_MATCHA_CAKE.get());
        basicItem(ModItems.MAGIC_STONE.get());
        basicItem(ModItems.RICE_CAKE.get());
        basicItem(ModItems.DYEDREAM_POPSICLE.get());
        basicItem(ModItems.FRIED_EGG.get());
        basicItem(ModItems.BACON_AND_EGG.get());
        basicItem(ModItems.ODD_BACON_AND_EGG.get());
        basicItem(ModItems.HEART_CHOCOLATE.get());
        basicItem(ModItems.WHITE_HEART_CHOCOLATE.get());
        basicItem(ModItems.PINK_HEART_CHOCOLATE.get());
        basicItem(ModItems.BREAD_SLICE.get());
        basicItem(ModItems.SWISS_ROLL.get());
        basicItem(ModItems.SANDWICH.get());
        basicItem(ModItems.WAFER_BISCUIT.get());
        basicItem(ModItems.STUFFED_WAFER_COOKIES.get());
        basicItem(ModItems.GINGERBREAD_MAN.get());
        basicItem(ModItems.CANDY_CANE.get());
        basicItem(ModItems.POPPING_CANDY.get());
        basicItem(ModItems.YINHUL_COTTON_CANDY.get());
        basicItem(ModItems.MELT_DREAM_COTTON_CANDY.get());
        basicItem(ModItems.BUBBLE_GUM.get());
        basicItem(ModItems.GALAXY_JELLY.get());
        withExistingParent(ModItems.MILKY_WAY_JELLY.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/galaxy_jelly"));
        basicItem(ModItems.FORTUNE_JELLY.get());
        basicItem(ModItems.LIGHT_ORGAN.get());
        basicItem(ModItems.JELLYFISH_MUD.get());
        basicItem(ModItems.JELLYFISH_JELLO.get());
        basicItem(ModItems.QUEER_SOUP.get());
        basicItem(ModItems.LEGENDARY_DRAGON_HORN_ICE_CREAM.get());
        basicItem(ModItems.PINEAPPLE_LOVE_SEA.get());
        basicItem(ModItems.ELIXIR_BOTTLE.get());
        basicItem(ModItems.ELIXIR_BOTTLE_OF_MELT_DREAM.get());
        basicItem(ModItems.ELIXIR_BOTTLE_OF_RAGE_ELIXIR.get());
        basicItem(ModItems.SOUL_DUST.get());
        basicItem(ModItems.SOUL_ESSENCE.get());
        basicItem(ModItems.CHARGED_AMETHYST.get());
        basicItem(ModItems.DYEDREAM_COROLLA.get());
        basicItem(ModItems.WHITE_COROLLA.get());
        basicItem(ModItems.WHITE_CRYSTAL.get());
        basicItem(ModItems.CONGEAL_WIND.get());
        basicItem(ModItems.WIND_RUNNER_CRYSTAL.get());
        basicItem(ModItems.PULSE_WIND_RUNNER_CRYSTAL.get());
        basicItem(ModItems.CONGEAL_WIND_IRON_INGOT.get());
        basicItem(ModItems.LIGHT_BALL.get());
        basicItem(ModItems.COTTON.get());
        basicItem(ModItems.SCULK_HEART.get());
        basicItem(ModItems.BLACK_STICK.get());
        basicItem(ModItems.SPOOL.get());
        basicItem(ModItems.SHADOW_DUNGEON_KEY.get());
        basicItem(ModItems.FABRIC.get());
        basicItem(ModItems.PROTECT_DECK.get());
        handheldItem(ModItems.COPPER_SWORD);
        handheldItem(ModItems.COPPER_PICKAXE);
        handheldItem(ModItems.COPPER_AXE);
        handheldItem(ModItems.COPPER_SHOVEL);
        handheldItem(ModItems.COPPER_HOE);
        basicItem(ModItems.COPPER_HELMET.get());
        basicItem(ModItems.COPPER_CHESTPLATE.get());
        basicItem(ModItems.COPPER_LEGGINGS.get());
        basicItem(ModItems.COPPER_BOOTS.get());
        basicItem(ModItems.TITANIUM_HELMET.get());
        basicItem(ModItems.TITANIUM_CHESTPLATE.get());
        basicItem(ModItems.TITANIUM_LEGGINGS.get());
        basicItem(ModItems.TITANIUM_BOOTS.get());
        basicItem(ModItems.SCULK_HELMET.get());
        basicItem(ModItems.SCULK_CHESTPLATE.get());
        basicItem(ModItems.SCULK_LEGGINGS.get());
        basicItem(ModItems.SCULK_BOOTS.get());
        basicItem(ModItems.DYEDREAM_HELMET.get());
        basicItem(ModItems.DYEDREAM_CHESTPLATE.get());
        basicItem(ModItems.DYEDREAM_LEGGINGS.get());
        basicItem(ModItems.DYEDREAM_BOOTS.get());
        basicItem(ModItems.QYM_CAT_EARS.get());
        basicItem(ModItems.QYM_WIND_SHIRT.get());
        basicItem(ModItems.QYM_SWAYING_SKIRT.get());
        basicItem(ModItems.QYM_CLOUD_BOOTS.get());
        basicItem(ModItems.MACHINE_LIGHT_WING.get());
        basicItem(ModItems.ANGEL_WING.get());
        basicItem(ModItems.FORSAKENS_WING.get());
        handheldItem(ModItems.MOLTEN_GOLD_SWORD);
        handheldItem(ModItems.MOLTEN_GOLD_PICKAXE);
        handheldItem(ModItems.MOLTEN_GOLD_AXE);
        handheldItem(ModItems.MOLTEN_GOLD_SHOVEL);
        handheldItem(ModItems.MOLTEN_GOLD_HOE);
        handheldItem(ModItems.HELLFIRE_SWORD);
        handheldItem(ModItems.HELLFIRE_PICKAXE);
        handheldItem(ModItems.INFERNO_SWORD);
        handheldItem(ModItems.MELT_DREAM_PICKAXE);
        handheldItem(ModItems.MELT_DREAM_AXE);
        handheldItem(ModItems.MELT_DREAM_SHOVEL);
        handheldItem(ModItems.MELT_DREAM_HOE);
        handheldItem(ModItems.TITANIUM_SWORD);
        handheldItem(ModItems.TITANIUM_PICKAXE);
        handheldItem(ModItems.TITANIUM_AXE);
        handheldItem(ModItems.TITANIUM_SHOVEL);
        handheldItem(ModItems.TITANIUM_HOE);
        handheldItem(ModItems.DYEDREAM_SWORD);
        handheldItem(ModItems.DYEDREAM_PICKAXE);
        handheldItem(ModItems.DYEDREAM_AXE);
        handheldItem(ModItems.DYEDREAM_SHOVEL);
        handheldItem(ModItems.DYEDREAM_HOE);
        handheldItem(ModItems.DYEDREAM_HAMMER);
        // star_wish_rod 模型为手动 JSON（含 cast 覆层），不走 datagen
        handheldItem(ModItems.SHARP_MELT_DREAM_SWORD);
        handheldItem(ModItems.TIDE_SWORD);
        handheldItem(ModItems.BEIHAI_RUO_TIDE_SWORD);
        handheldItem(ModItems.KUSANAGI);
        handheldItem(ModItems.MURAKUMO_KUSANAGI);
        handheldItem(ModItems.SWORD_EMBRYO);
        handheldItem(ModItems.ICE_SHADOW_HAMMER);
        handheldItem(ModItems.SHADOW_EROSION_DAGGER);
        handheldItem(ModItems.SHADOW_EROSION_PICKAXE);
        handheldItem(ModItems.SHADOW_EROSION_AXE);
        handheldItem(ModItems.SHADOW_EROSION_SHOVEL);
        handheldItem(ModItems.SHADOW_EROSION_HOE);
        handheldItem(ModItems.THERMAL_DAGGER);
        handheldItem(ModItems.DESERT_SWORD);
        handheldItem(ModItems.CHENJINGMEN_DESERT_SWORD);
        handheldItem(ModItems.BROKEN_HERO_SWORD);
        handheldItem(ModItems.TERRA_BLADE);
        handheldItem(ModItems.WHITE_SWORD);
        handheldItem(ModItems.SHADOW_SWORD);
        handheldItem(ModItems.DEBUG_SWORD);
        basicItem(ModItems.TITANIUM_UPGRADE.get());
        basicItem(ModItems.SCULK_UPGRADE.get());
        basicItem(ModItems.DYEDREAM_UPGRADE.get());
        basicItem(ModItems.ATTACK_ENHANCE_STONE.get());
        basicItem(ModItems.LUCK_ENHANCE_STONE.get());
        basicItem(ModItems.DEEP_SEA_TREASURE.get());
        basicItem(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get());
        basicItem(ModItems.SHADOW_DEEP_SEA_TREASURE.get());
        basicItem(ModItems.DREAM_FERTILIZER.get());
        basicItem(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get());
        basicItem(ModItems.MELT_DREAM_AURORIAN_STEEL.get());
        basicItem(ModItems.DREAM_NOTES_DYEDREAM_WORLD.get());
        basicItem(ModItems.DREAM_NOTES_LAMP_SHADOW_WORLD.get());
        basicItem(ModItems.DREAM_NOTES_WIND_JOURNEY_WORLD.get());
        withExistingParent("dream_notes_story_guide", mcLoc("item/generated"))
                .texture("layer0", modLoc("item/dream_notes_lamp_shadow_world"));
        basicItem(ModItems.BLUE_PRINT.get());
        basicItem(ModItems.DREAM_NOTES_BOOK.get());
        basicItem(ModItems.REED_ROD.get());
        basicItem(ModItems.BLUE_HEART_OF_THE_SEA.get());
        basicItem(ModItems.ELDER_GUARDIAN_SCALE.get());
        basicItem(ModItems.RED_DEW.get());
        basicItem(ModItems.BLUE_DEW.get());
        basicItem(ModItems.MELT_DREAM_COIN.get());
        basicItem(ModItems.MELT_DREAM_COIN_PILE.get());
        basicItem(ModItems.JUNGLE_SPORE.get());
        basicItem(ModItems.PERGAMYN.get());

        basicItem(ModItems.BROKEN_NOTE.get());
        basicItem(ModItems.UNKNOWN_NOTE.get());

        basicItem(ModItems.PALE_BONENEEDLE.get());
        basicItem(ModItems.ROOTS_PALE_BONENEEDLE.get());

        basicItem(ModItems.DYEDREAM_COROLLA_CROP_AGE_0.get());
        basicItem(ModItems.WHITE_COROLLA_CROP_AGE_0.get());
        basicItem(ModItems.LIGHT_BALL_CROP_AGE_0.get());
        basicItem(ModItems.CLOUD_CROP_AGE_0.get());
        basicItem(ModItems.COTTON_CROP_AGE_0.get());

        itemModelWithResource(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get(), "dyedream_corolla_crop");
        itemModelWithResource(ModItems.WHITE_COROLLA_CROP_AGE_1.get(), "white_corolla_crop");
        itemModelWithResource(ModItems.LIGHT_BALL_CROP_AGE_1.get(), "light_ball_crop");
        itemModelWithResource(ModItems.CLOUD_CROP_AGE_1.get(), "cloud_crop");
        itemModelWithResource(ModItems.COTTON_CROP_AGE_1.get(), "cotton_crop");

        basicItem(ModItems.EMBRYO_CHARM.get());
        basicItem(ModItems.GOLD_CHARM.get());
        basicItem(ModItems.ENDEYE_CHARM.get());
        basicItem(ModItems.SEA_CHARM.get());
        basicItem(ModItems.CARAPAX_CHARM.get());
        basicItem(ModItems.TERRA_FLOATING_ISLAND.get());
        basicItem(ModItems.WORLDTREE_SEEDPOD.get());
        basicItem(ModItems.GARLAND.get());
        basicItem(ModItems.REST_BUFF_ICON.get());
        basicItem(ModItems.CHEER_UP_BUFF_ICON.get());
        basicItem(ModItems.LETHARGY_BUFF_ICON.get());
        basicItem(ModItems.TRANCE_BUFF_ICON.get());
        basicItem(ModItems.INSAND_BUFF_ICON.get());
        basicItem(ModItems.CHAOS_BUFF_ICON.get());
        basicItem(ModItems.DREAM_WISH_BUFF_ICON.get());
        basicItem(ModItems.COOK_BUFF_ICON.get());
        withExistingParent(ModItems.FOURLEAF_CLOVER_CURIO.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/fourleaf_clover"));
        basicItem(ModItems.SNOW_VOW_HEAD.get());
        basicItem(ModItems.QYM_BUTTERFLY_STAR_HAIRPIN.get());
        basicItem(ModItems.HIYORI_BUTTERFLY_HAIRPIN.get());
        basicItem(ModItems.LIGHT_BUTTERFLY_CURIO.get());
        basicItem(ModItems.BRIGHT_BUTTERFLY_CURIO.get());
        basicItem(ModItems.ICE_SHADOW_CURIO.get());
        basicItem(ModItems.BOBOJI_CURIO.get());
        basicItem(ModItems.EMBRYO_NECKLACE.get());
        basicItem(ModItems.EMBRYO_RING.get());
        basicItem(ModItems.ALLKINDS_RING.get());
        basicItem(ModItems.COUNTER_RING.get());
        basicItem(ModItems.MELT_DREAM_ENERGY_RING.get());
        basicItem(ModItems.EMBRYO_BELT.get());
        basicItem(ModItems.RABBIT_FOOT_NECKLACE.get());
        basicItem(ModItems.FEATHER_NECKLACE.get());
        basicItem(ModItems.HEALTH_NECKLACE.get());
        basicItem(ModItems.FIRE_NECKLACE.get());
        basicItem(ModItems.CROSS_NECKLACE.get());
        basicItem(ModItems.NATURE_BELT.get());
        basicItem(ModItems.TRAVELER_BELT.get());
        basicItem(ModItems.DREAM_TRAVELER_BELT.get());
        basicItem(ModItems.DUKE_COIN_CURIO.get());
        basicItem(ModItems.KAICHU_OMAMORI.get());
        basicItem(ModItems.BROOCH_OF_WHITE_ORCHID.get());
        basicItem(ModItems.SEAL_OF_THE_CORRUPTED.get());
        basicItem(ModItems.BLESSING_OF_CECILIA.get());
        basicItem(ModItems.FADED_BLESSING_OF_CECILIA.get());
        basicItem(ModItems.WAR_FLAG.get());
        basicItem(ModItems.CALAIS_SPICE_BOTTLE.get());
        basicItem(ModItems.GHOST_FACE.get());
        withExistingParent(ModItems.PALE_BONE_NEEDLE_TALISMAN.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/pale_boneneedle"));

        basicItem(ModItems.DEBUG_SWORD.get());
        basicItem(ModItems.LOOT_GENERATOR.get());
        basicItem(ModItems.MELT_DREAM_CRYSTAL_CHEST_RESET_TOOL.get());

        basicItem(ModItems.SWEET_DREAM_MUSIC_DISC.get());
        basicItem(ModItems.SNOWFALL_DREAM_MUSIC_DISC.get());

        basicItem(ModItems.SAND_OF_TIME.get());
        basicItem(ModItems.MEMORY_GEM.get());
        basicItem(ModItems.EMPTY_VESSEL.get());
        handheldItem(ModItems.DREAM_HARP_OF_WANDERER);
        basicItem(ModItems.STORAGE_BAG.get());
        basicItem(ModItems.LARGE_STORAGE_BAG.get());
        basicItem(ModItems.STRAWBERRY_HEART.get());
        basicItem(ModItems.SLIVER_BELL.get());

        basicItem(ModItems.EMPTY_PROPHECY_CARD.get());

        basicItem(ModItems.BLACK_METAL_INGOT.get());
        basicItem(ModItems.BLACK_METAL_GRAIN.get());
        basicItem(ModItems.RUST_BLACK_METAL_GRAIN.get());

        withExistingParent(ModItems.PINK_MUSHROOM.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/pink_mushroom"));
        withExistingParent(ModItems.TALL_PINK_MUSHROOM.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/pink_mushroom"));

        withExistingParent(ModItems.DYEDREAM_SAPLING.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_sapling"));

        fenceItem(ModBlocks.DYEDREAM_FENCE, ModBlocks.DYEDREAM_PLANKS);
        buttonItem(ModBlocks.DYEDREAM_BUTTON, ModBlocks.DYEDREAM_PLANKS);
        basicItem(ModItems.DYEDREAM_DOOR.get());
        withExistingParent(ModItems.DYEDREAM_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_trapdoor"));

        fenceItem(ModBlocks.SHADOW_FENCE, ModBlocks.SHADOW_PLANKS);
        buttonItem(ModBlocks.SHADOW_BUTTON, ModBlocks.SHADOW_PLANKS);
        basicItem(ModItems.SHADOW_DOOR.get());
        withExistingParent(ModItems.SHADOW_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/shadow_trapdoor"));

        fenceItem(ModBlocks.WIND_MOOR_FENCE, ModBlocks.WIND_MOOR_PLANKS);
        buttonItem(ModBlocks.WIND_MOOR_BUTTON, ModBlocks.WIND_MOOR_PLANKS);
        basicItem(ModItems.WIND_MOOR_DOOR.get());
        buttonItem(ModBlocks.CYAN_STONE_BUTTON, ModBlocks.CYAN_STONE);
        withExistingParent(ModItems.WIND_MOOR_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/wind_moor_trapdoor"));

        wallItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL, ModBlocks.DYEDREAM_QUARTZ_BLOCK);

        wallItem(ModBlocks.DYEDREAM_BUD_WALL, ModBlocks.DYEDREAM_BUD_BLOCK);
        wallItem(ModBlocks.POLISHED_CALCITE_WALL, ModBlocks.POLISHED_CALCITE);
        wallItem(ModBlocks.CALCITE_TILES_WALL, ModBlocks.CALCITE_TILES);
        wallItem(ModBlocks.SHADOW_STONE_BRICK_WALL, ModBlocks.SHADOW_STONE_BRICK);
        wallItem(ModBlocks.NARROW_SHADOW_STONE_BRICK_WALL, ModBlocks.NARROW_SHADOW_STONE_BRICK);
        wallItem(ModBlocks.SHADOW_STONE_TILES_WALL, ModBlocks.SHADOW_STONE_TILES);
        wallItem(ModBlocks.CYAN_STONE_BRICK_WALL, ModBlocks.CYAN_STONE_BRICKS);
        wallItem(ModBlocks.MOSSY_CYAN_STONE_BRICK_WALL, ModBlocks.MOSSY_CYAN_STONE_BRICKS);

        wallItem(ModBlocks.RUST_BLACK_METAL_WALL, ModBlocks.RUST_BLACK_METAL_BLOCK);

        withExistingParent(ModItems.DYEDREAM_GLASS_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_glass"));
        withExistingParent(ModItems.CARVE_DYEDREAM_GLASS_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/carve_dyedream_glass"));
        withExistingParent(ModItems.GOLD_CARVE_DYEDREAM_GLASS_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/gold_carve_dyedream_glass"));

        withExistingParent(ModItems.CLARITY_GLASS_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/clarity_glass"));
        withExistingParent(ModItems.CARVE_CLARITY_GLASS_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/carve_clarity_glass"));
        withExistingParent(ModItems.FRAME_CLARITY_GLASS_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/frame_clarity_glass"));

        withExistingParent(ModItems.SMALL_DYEDREAM_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/small_dyedream_bud"));
        withExistingParent(ModItems.MEDIUM_DYEDREAM_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/medium_dyedream_bud"));
        withExistingParent(ModItems.LARGE_DYEDREAM_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/large_dyedream_bud"));

        withExistingParent(ModItems.ICE_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/ice_bud"));

        withExistingParent(ModItems.LOST_SWORD_TOMB.getId().getPath(), modLoc("block/lost_sword_tomb"));

        withExistingParent(ModItems.GOLDENROD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/goldenrod"));
        withExistingParent(ModItems.FERRARIA_CRISPA.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/ferraria_crispa"));
        withExistingParent(ModItems.EUSTOMA.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/eustoma"));
        withExistingParent(ModItems.MALVA_SINENSIS_CAVAN.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/malva_sinensis_cavan"));
        withExistingParent(ModItems.LINHT_FLOWER.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/linht_flower"));
        withExistingParent(ModItems.DREAMING_LOTUS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dreaming_lotus_top"));
        withExistingParent(ModItems.MISTY_DREAMING_LOTUS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/misty_dreaming_lotus_top"));
        withExistingParent(ModItems.DYEDREAM_LILY_OF_THE_VALLEY.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_lily_of_the_valley"));
        withExistingParent(ModItems.BLAZE_FLOWER.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/blaze_flower"));
        withExistingParent(ModItems.WHITE_ORCHID_FLOWER.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/white_orchid_flower"));
        withExistingParent(ModItems.EDELWEISS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/edelweiss"));
        withExistingParent(ModItems.NIPPY_EDELWEISS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/nippy_edelweiss"));
        withExistingParent(ModItems.LIGHT_BALL.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/light_ball"));

        withExistingParent(ModItems.DYEDREAM_MOSS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_moss"));
        withExistingParent(ModItems.STEM_GRASS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/stem_grass"));
        withExistingParent(ModItems.TALL_STEM_GRASS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/stem_grass"));
        withExistingParent(ModItems.SINGULARITY_FERN.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/singularity_fern"));
        withExistingParent(ModItems.CRIMSON_THORNS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/crimson_thorns_top"));
        withExistingParent(ModItems.OATS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/oats"));
        withExistingParent(ModItems.RYE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/rye"));
        withExistingParent(ModItems.POLISHED_CALCITE_STALICRIPE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/polished_calcite_stalicripe"));
        withExistingParent(ModItems.SMALL_POLISHED_CALCITE_STALICRIPE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/small_polished_calcite_stalicripe"));
        withExistingParent(ModItems.DYEDREAM_SEAGRASS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_seagrass"));
        withExistingParent(ModItems.REED.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/reed"));
        withExistingParent(ModItems.DYEDREAM_VINE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_vine"));
        withExistingParent(ModItems.JUNGLE_SPORANGIUM.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/jungle_sporangium"));
        withExistingParent(ModItems.FOURLEAF_CLOVER.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/fourleaf_clover"));

        // 风之植物系列
        withExistingParent(ModItems.HAIRY_MOSS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/hairy_moss"));
        withExistingParent(ModItems.WIND_CLEAVING_GRASS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/wind_cleaving_grass"));
        withExistingParent(ModItems.WIND_FEATHER_GRASS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/wind_feather_grass_top"));
        withExistingParent(ModItems.WIND_ISLAND_REED.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/wind_island_reed_top"));

        // 阴影植物系列
        withExistingParent(ModItems.SHADOW_SHORT_ROOTS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/shadow_short_roots"));
        withExistingParent(ModItems.SHADOW_ROOTS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/shadow_roots"));
        withExistingParent(ModItems.SHADOW_STEM_FERN.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/shadow_stem_fern_top"));
        withExistingParent(ModItems.SHADOW_SPROUTS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/shadow_sprouts"));
        withExistingParent(ModItems.SHADOW_FERN.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/shadow_fern"));

        withExistingParent(ModItems.SHADOW_FUNGUS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/shadow_fungus"));

        withExistingParent(ModItems.RUST_BLACK_METAL_BARS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/rust_black_metal_bars"));

        withExistingParent(ModItems.CONGEAL_WIND_IRON_BARS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/congeal_wind_iron_bars"));

        basicItem(ModItems.NIGHTMARE_FUEL.get());

        withExistingParent(ModItems.SHADOW_CANDLE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/shadow_candle"));

        // 陶罐
        withExistingParent(ModItems.CLAY_POT.getId().getPath(), modLoc("block/clay_pot"));
        // 阴影陶罐（复用陶罐模型）
        withExistingParent(ModItems.SHADOW_CLAY_POT.getId().getPath(), modLoc("block/clay_pot"));
        basicItem(ModItems.PEBBLE.get());

        // 刷怪蛋
        spawnEggItem(ModItems.PINK_CHICKEN_SPAWN_EGG);
        spawnEggItem(ModItems.PINK_SLIME_SPAWN_EGG);
        spawnEggItem(ModItems.GOLDEN_FOX_SPAWN_EGG);
        spawnEggItem(ModItems.WIND_KNIGHT_SPAWN_EGG);
        spawnEggItem(ModItems.THUNDERCLOUD_SPAWN_EGG);
        spawnEggItem(ModItems.HIGHVOLTAGE_THUNDERCLOUD_SPAWN_EGG);
        spawnEggItem(ModItems.SHADOW_GOLEM_SPAWN_EGG);
        spawnEggItem(ModItems.TERRORBEAK_SPAWN_EGG);
        spawnEggItem(ModItems.CRAZY_TERRORBEAK_SPAWN_EGG);
        spawnEggItem(ModItems.WEAKENESS_TERRORBEAK_SPAWN_EGG);
        spawnEggItem(ModItems.SHADOW_HAND_SPAWN_EGG);
        spawnEggItem(ModItems.SHADOW_GHOST_SPAWN_EGG);
        spawnEggItem(ModItems.SHADOW_SQUEAL_GHOST_SPAWN_EGG);
        spawnEggItem(ModItems.WAILING_SHADOW_GHOST_SPAWN_EGG);
        spawnEggItem(ModItems.FRIENDLY_SHADOW_GHOST_SPAWN_EGG);
        spawnEggItem(ModItems.BLACK_BEETLE_SPAWN_EGG);
        spawnEggItem(ModItems.BLACK_BEETLE_MOTHER_SPAWN_EGG);

        basicItem(ModItems.BLACK_BEETLE_CARAPACE.get());
        basicItem(ModItems.BLACK_BEETLE_VOCALCORD.get());
    }

    @Override
    public ItemModelBuilder basicItem(Item item) {
        return basicItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
    }

    @Override
    public ItemModelBuilder basicItem(ResourceLocation item) {
        String path = item.getPath();

        if (path.contains("/")) {
            path = folder + "/" + path;
        }
        return getBuilder(path)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.tryParse(item.getNamespace() + ":" + "item/" + item.getPath()));
    }

    public ItemModelBuilder itemModelWithResource(Item item, String resource)
    {
        String path = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
        if (path.contains("/"))
        {
            path = folder + "/" + path;
        }
        return getBuilder(path).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", ResourceLocation.tryParse(PasterDreamMod.MOD_ID + ":" + "item/" + resource));
    }

    private <T extends Block> void buttonItem(RegistryObject<T> block, RegistryObject<Block> base) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                        "block/" + base.getId().getPath()));
    }

    private <T extends Block> void fenceItem(RegistryObject<T> block, RegistryObject<Block> base) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                        "block/" + base.getId().getPath()));
    }

    private <T extends Block> void wallItem(RegistryObject<T> block, RegistryObject<Block> base) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                        "block/" + base.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.fromNamespaceAndPath("minecraft", "item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder spawnEggItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
    }

}
