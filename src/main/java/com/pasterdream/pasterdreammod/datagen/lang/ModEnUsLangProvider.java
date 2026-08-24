package com.pasterdream.pasterdreammod.datagen.lang;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.PotionBottleRegistry;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnUsLangProvider extends LanguageProvider {
    public ModEnUsLangProvider(PackOutput output) {
        super(output, PasterDreamMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModBlocks.PEBBLE.get(), "Pebble");
        add(ModBlocks.SMALL_STONE_SPIRIT_BLOCK.get(), "Small Stone Spirit Block");
        add("tooltip.pasterdream.pebble.throw", "§7Right-click to throw");
        add("tooltip.pasterdream.pebble.place", "§7Sneak + Right-click to place");
        add(ModItems.LIFE_CRYSTAL.get(), "Life Crystal");
        add("message.pasterdream.life_crystal.already_used", "You have already absorbed a Life Crystal!");
        add("message.pasterdream.life_crystal.already_absorbing", "You are already absorbing another Life Crystal!");
        add("message.pasterdream.life_crystal.absorbed", "The Life Crystal shatters and flows into you §aMax Health +2");
        add("death.pasterdream.lamp_shadow_world", "%s melted into the whispers of the shadows");
        add(ModItems.DYEDREAM_QUARTZ.get(), "Dye Dream Quartz");
        add(ModItems.DYEDREAM_DUST.get(), "Dye Dream Dust");
        add(ModItems.DYEDREAM_DUST_PIECE.get(), "Dye Dream Dust Piece");
        add(ModItems.AMBER_CANDY.get(), "Amber Candy");
        add(ModItems.PINK_SLIMEBALL.get(), "Pink Slimeball");
        add(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get(), "Raw Dye Dream Alloy Ingot");
        add(ModItems.DYEDREAM_ALLOY_INGOT.get(), "Dye Dream Alloy Ingot");
        add(ModItems.TITANIUM_INGOT.get(), "Titanium Ingot");
        add(ModItems.TITANIUM_NUGGET.get(), "Titanium Nugget");
        add(ModItems.RAW_TITANIUM.get(), "Raw Titanium");
        add(ModItems.DYEDREAM_ALLOY_NUGGET.get(), "Dye Dream Alloy Nugget");
        add(ModItems.MORTAR.get(), "Mortar");
        add(ModItems.PLIERS.get(), "Pliers");
        add(ModItems.DYEDREAM_DYE.get(), "Dye Dream Dye");
        add(ModItems.DYEDREAM_BUD_NUGGET.get(), "Dye Dream Bud Nugget");
        add(ModItems.GLASS_JAR.get(), "Glass Jar");
        add(ModItems.GLASS_JAR_OF_WATER.get(), "Water Jar");
        add(ModItems.GLASS_JAR_OF_MILK.get(), "Milk Jar");
        add(ModItems.GLASS_JAR_OF_YEAST.get(), "Glass Jar of Yeast");
        add(ModItems.GLASS_JAR_OF_GUIDING_DRUG.get(), "Glass Jar of Guiding Drug");
        add(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get(), "Glass Jar of Wind Plant Extract");
        add(ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME.get(), "Glass Jar of Dyedream Perfume");
        add(ModItems.GLASS_JAR_OF_DREAM_JUICE.get(), "Glass Jar of Dream Juice");
        add("tooltip.pasterdreammod.glass_jar_of_dream_juice", "§7§oA sweet and dreamy taste that melds you into the dream");
        add(ModItems.GLASS_JAR_OF_GOLDENROD_TEA.get(), "Glass Jar of Goldenrod Tea");
        add(ModItems.GLASS_JAR_OF_INK.get(), "Glass Jar of Ink");
        add(ModItems.FEATHER_PEN.get(), "Feather Pen");
        add(ModItems.MELT_DREAM_LIQUID_BUCKET.get(), "Meltdream Liquid Bucket");
        add(ModItems.SHADOW_LIQUID_BUCKET.get(), "Shadow Liquid Bucket");
        add(ModItems.GLASS_CUP.get(), "Glass Cup");
        add(ModItems.GLASS_CUP_OF_APPLE_JUICE.get(), "Glass Cup of Apple Juice");
        add(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get(), "Glass Cup of Cooked Dyedream Flower Tea");
        add(ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get(), "Glass Cup of Dyedream Juice");
        add(ModItems.GLASS_CUP_OF_HONEY_JUICE.get(), "Glass Cup of Honey Juice");
        add(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get(), "Glass Cup of Uncooked Dyedream Flower Tea");
        add(ModItems.GLASS_CUP_OF_WATERMELON_JUICE.get(), "Glass Cup of Watermelon Juice");
        add(ModItems.FLOUR.get(), "Flour");
        add(ModItems.DOUGH.get(), "Dough");
        add(ModItems.RYE_SEED.get(), "Rye Seed");
        add(ModItems.DOUGH_WITH_EGG.get(), "Dough with Egg");
        add(ModItems.COARSE_SALT.get(), "Coarse Salt");
        add(ModItems.SALT.get(), "Salt");
        add(ModItems.PINK_EGG.get(), "Pink Egg");
        add(ModItems.DYEDREAM_FRUIT.get(), "Dyedream Fruit");
        add(ModItems.FIG.get(), "Fig");
        add(ModItems.CHOCOLATE.get(), "Chocolate");
        add(ModItems.CAKE_BASE.get(), "Cake Base");
        add(ModItems.CREAM_BUN_CAKE.get(), "Cream Bun Cake");
        add(ModItems.BERRY_BUN_CAKE.get(), "Berry Bun Cake");
        add(ModItems.TUBER_BUN_CAKE.get(), "Tuber Bun Cake");
        add(ModItems.WATERMELON_BUN_CAKE.get(), "Watermelon Bun Cake");
        add(ModItems.PUMPKIN_BUN_CAKE.get(), "Pumpkin Bun Cake");
        add(ModItems.GLOW_BERRY_BUN_CAKE.get(), "Glow Berry Bun Cake");
        add(ModItems.DYEDREAM_FRUIT_BUN_CAKE.get(), "Dyedream Fruit Bun Cake");
        add(ModItems.CHOCOLATE_MATCHA_CAKE.get(), "Chocolate Matcha Cake");
        add(ModItems.MAGIC_STONE.get(), "Magic Stone");
        add(ModItems.RICE_CAKE.get(), "Rice Cake");
        add("item.pasterdream.rice_cake.describe", "A lingering spirit of desert might be interested in this...");
        add(ModItems.DYEDREAM_POPSICLE.get(), "Dyedream Popsicle");
        add(ModItems.FRIED_EGG.get(), "Fried Egg");
        add(ModItems.BACON_AND_EGG.get(), "Bacon and Egg");
        add(ModItems.ODD_BACON_AND_EGG.get(), "Odd Bacon and Egg");
        add(ModItems.HEART_CHOCOLATE.get(), "Heart Chocolate");
        add(ModItems.WHITE_HEART_CHOCOLATE.get(), "White Heart Chocolate");
        add(ModItems.PINK_HEART_CHOCOLATE.get(), "Pink Heart Chocolate");
        add(ModItems.BREAD_SLICE.get(), "Bread Slice");
        add(ModItems.SWISS_ROLL.get(), "Swiss Roll");
        add(ModItems.SANDWICH.get(), "Sandwich");
        add(ModItems.WAFER_BISCUIT.get(), "Wafer Biscuit");
        add(ModItems.STUFFED_WAFER_COOKIES.get(), "Stuffed Wafer Cookies");
        add(ModItems.GINGERBREAD_MAN.get(), "Gingerbread Man");
        add(ModItems.CANDY_CANE.get(), "Candy Cane");
        add(ModItems.POPPING_CANDY.get(), "Popping Candy");
        add(ModItems.YINHUL_COTTON_CANDY.get(), "Yinhul Cotton Candy");
        add(ModItems.MELT_DREAM_COTTON_CANDY.get(), "Melt Dream Cotton Candy");
        add(ModItems.BUBBLE_GUM.get(), "Bubble Gum");
        add(ModItems.GALAXY_JELLY.get(), "Galaxy Jelly");
        add("tooltip.pasterdreammod.galaxy_jelly", "§9After consumption, throw the player into the air and gains a 7-second slow falling.");
        add("tooltip.pasterdreammod.galaxy_jelly.flavor", "§7§O --Holding the galaxy in my hands.");
        add(ModItems.MILKY_WAY_JELLY.get(), "Milky Way Jelly");
        add("tooltip.pasterdreammod.milky_way_jelly", "§9After consumption, teleports the player to the dimension's build height and grants 30 seconds of slow falling.");
        add("tooltip.pasterdreammod.milky_way_jelly.flavor", "§7§O --The Milky Way knows no bounds, an ocean of stars without end.");
        add(ModItems.FORTUNE_JELLY.get(), "Fortune Jelly");
        add("tooltip.pasterdreammod.fortune_jelly", "§9After eating, gain Luck I (1:00)");
        add("item.minecraft.potion.effect.luck", "Potion of Luck");
        add("item.minecraft.splash_potion.effect.luck", "Splash Potion of Luck");
        add("item.minecraft.lingering_potion.effect.luck", "Lingering Potion of Luck");
        add(ModItems.LIGHT_ORGAN.get(), "Light Organ");
        add(ModItems.JELLYFISH_MUD.get(), "Jellyfish Mud");
        add(ModItems.JELLYFISH_JELLO.get(), "Jellfish Jello");
        add(ModItems.QUEER_SOUP.get(), "Queer Soup");
        add(ModItems.LEGENDARY_DRAGON_HORN_ICE_CREAM.get(), "Legendary Dragon Horn Ice Cream");
        add("item.pasterdream.legendary_dragon_horn_ice_cream.describe.0", "§9Permanently +10 Luck (until death)");
        add("item.pasterdream.legendary_dragon_horn_ice_cream.describe.1", "§9Effect does not stack");
        add("item.pasterdream.legendary_dragon_horn_ice_cream.client.success", "§eThe taste feels familiar, as if it came from Snow Tears Cold... wait, what?");
        add("item.pasterdream.legendary_dragon_horn_ice_cream.client.fail", "§7You have already tasted this ice cream flavor");
        add(ModItems.ELIXIR_BOTTLE.get(), "Elixir Bottle");
        add(ModItems.ELIXIR_BOTTLE_OF_POTION.get(), "Elixir Bottle of Potion");
        add("tooltip.pasterdreammod.elixir_bottle_of_potion.uses", "§7Remaining uses: %s");
        add(ModItems.ELIXIR_BOTTLE_OF_MELT_DREAM.get(), "Elixir Bottle of Melt Dream");
        add(ModItems.ELIXIR_BOTTLE_OF_RAGE_ELIXIR.get(), "Elixir Bottle of Rage Elixir");
        add("tooltip.pasterdreammod.elixir_bottle_of_rage_elixir.1", "§7Effects after drinking:");
        add("tooltip.pasterdreammod.elixir_bottle_of_rage_elixir.2", "§7▪ §9-15% Skill cooldown, +20% Attack damage (until death)");
        add("item.pasterdream.elixir_bottle_of_rage_elixir.client.success", "§5You feel a surge of power coursing through your body...");
        add("item.pasterdream.elixir_bottle_of_rage_elixir.client.fail", "§7You have already drunk this potion.");
        add(ModItems.BUBBLE_TEA.get(), "Bubble Tea");
        add(ModItems.SWEET_DREAM_MUSIC_DISC.get(), "Music Disc");
        add(ModItems.SWEET_DREAM_MUSIC_DISC.get().getDescriptionId() + ".desc", "PasterDream - Sweet Dream");
        add(ModItems.SNOWFALL_DREAM_MUSIC_DISC.get(), "Music Disc");
        add(ModItems.SNOWFALL_DREAM_MUSIC_DISC.get().getDescriptionId() + ".desc", "PasterDream - Snowfall Dream");
        add(ModItems.WIND_JOURNEY_MUSIC_DISC.get(), "Music Disc");
        add(ModItems.WIND_JOURNEY_MUSIC_DISC.get().getDescriptionId() + ".desc", "§dPasterDream§7 - Wind Journey");
        add(ModItems.PINEAPPLE_LOVE_SEA.get(), "Pineapple Love Sea");
        add(ModItems.BLUE_HEART_OF_THE_SEA.get(), "Blue Heart Of The Sea");
        add(ModItems.ELDER_GUARDIAN_SCALE.get(), "Elder Guardian Scale");
        add(ModItems.RED_DEW.get(), "Red Dew");
        add(ModItems.BLUE_DEW.get(), "Blue Dew");
        add(ModItems.DYEDREAM_COROLLA.get(), "Dyedream Corolla");
        add(ModItems.WHITE_COROLLA.get(), "White Corolla");
        add(ModItems.WHITE_CRYSTAL.get(), "White Crystal");
        add(ModItems.SHADOW_HILT.get(), "Condensed Shadow Hilt");
        add(ModItems.TALENT_LIGHT.get(), "Faith in Light");
        add(ModItems.TALENT_SHADOW.get(), "Shadow Servant");
        add("tooltip.pasterdream.shadow_hilt", "§7A core material for crafting shadow blades");
        add(ModItems.CONGEAL_WIND.get(), "Congeal Wind");
        add(ModItems.WIND_RUNNER_CRYSTAL.get(), "Windrunner Crystal");
        add(ModItems.PULSE_WIND_RUNNER_CRYSTAL.get(), "Pulse Windrunner Crystal");
        add(ModItems.CONGEAL_WIND_IRON_INGOT.get(), "Congeal Wind Iron Ingot");
        add("tooltip.pasterdream.white_crystal", "§7Core material for crafting the White Calamity Sword");
        add(ModItems.LIGHT_BALL.get(), "Light Ball");
        add(ModItems.COTTON.get(), "Cotton");
        add(ModItems.SCULK_HEART.get(), "Sculk Heart");
        add(ModItems.BLACK_STICK.get(), "Black Stick");
        add(ModItems.NIGHTMARE_FUEL.get(), "Nightmare Fuel");
        add("tooltip.pasterdreammod.nightmare_fuel", "§7This is the stuff of nightmares!");
        add(ModItems.RUST_BLACK_METAL_GRAIN.get(), "Rust Black Metal Grain");
        add(ModItems.BLACK_METAL_INGOT.get(), "Black Metal Ingot");
        add(ModItems.BLACK_METAL_GRAIN.get(), "Black Metal Grain");
        add(ModItems.SPOOL.get(), "Spool");
        add(ModItems.FABRIC.get(), "Fabric");
        add(ModItems.PROTECT_DECK.get(), "Protect Deck");
        add(ModItems.TITANIUM_UPGRADE.get(), "Titanium Upgrade");
        add(ModItems.SCULK_UPGRADE.get(), "Sculk Upgrade");
        add(ModItems.DYEDREAM_UPGRADE.get(), "Dyedream Upgrade");
        add(ModItems.DEEP_SEA_TREASURE.get(), "Deep Sea Treasure");
        add(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get(), "Dyedream Deep Treasure");
        add(ModItems.SHADOW_DEEP_SEA_TREASURE.get(), "Shadow Deep Sea Treasure");
        add(ModItems.ATTACK_ENHANCE_STONE.get(), "Attack Enhance Stone");
        add(ModItems.LUCK_ENHANCE_STONE.get(), "Luck Enhance Stone");
        add(ModItems.COPPER_SWORD.get(), "Copper Sword");
        add(ModItems.COPPER_PICKAXE.get(), "Copper Pickaxe");
        add(ModItems.COPPER_AXE.get(), "Copper Axe");
        add(ModItems.COPPER_SHOVEL.get(), "Copper Shovel");
        add(ModItems.COPPER_HOE.get(), "Copper Hoe");
        add(ModItems.COPPER_HELMET.get(), "Copper Helmet");
        add(ModItems.COPPER_CHESTPLATE.get(), "Copper Chestplate");
        add(ModItems.COPPER_LEGGINGS.get(), "Copper Leggings");
        add(ModItems.COPPER_BOOTS.get(), "Copper Boots");
        add(ModItems.TITANIUM_SWORD.get(), "Titanium Sword");
        add(ModItems.TITANIUM_PICKAXE.get(), "Titanium Pickaxe");
        add(ModItems.TITANIUM_AXE.get(), "Titanium Axe");
        add(ModItems.TITANIUM_SHOVEL.get(), "Titanium Shovel");
        add(ModItems.TITANIUM_HOE.get(), "Titanium Hoe");
        add(ModItems.MOLTEN_GOLD_SWORD.get(), "Molten Gold Sword");
        add(ModItems.MOLTEN_GOLD_PICKAXE.get(), "Molten Gold Pickaxe");
        add(ModItems.MOLTEN_GOLD_AXE.get(), "Molten Gold Axe");
        add(ModItems.MOLTEN_GOLD_SHOVEL.get(), "Molten Gold Shovel");
        add(ModItems.MOLTEN_GOLD_HOE.get(), "Molten Gold Hoe");
        add(ModItems.HELLFIRE_SWORD.get(), "Hellfire Sword");
        add(ModItems.HELLFIRE_PICKAXE.get(), "Hellfire Pickaxe");
        add(ModItems.MELT_DREAM_PICKAXE.get(), "Melt Dream Pickaxe");
        add(ModItems.MELT_DREAM_AXE.get(), "Melt Dream Axe");
        add(ModItems.MELT_DREAM_SHOVEL.get(), "Melt Dream Shovel");
        add(ModItems.MELT_DREAM_HOE.get(), "Melt Dream Hoe");
        add(ModItems.INFERNO_SWORD.get(), "Inferno Sword");
        add(ModItems.DYEDREAM_SWORD.get(), "Dyedream Sword");
        add(ModItems.DYEDREAM_PICKAXE.get(), "Dyedream Pickaxe");
        add(ModItems.DYEDREAM_AXE.get(), "Dyedream Axe");
        add(ModItems.DYEDREAM_SHOVEL.get(), "Dyedream Shovel");
        add(ModItems.DYEDREAM_HOE.get(), "Dyedream Hoe");
        add(ModItems.DYEDREAM_HAMMER.get(), "Dyedream Hammer");
        add(ModItems.SHARP_MELT_DREAM_SWORD.get(), "Dyedream Sharp Sword");
        add(ModItems.TIDE_SWORD.get(), "Tide Sword");
        add(ModItems.BEIHAI_RUO_TIDE_SWORD.get(), "Beihai Ruo Tide Sword");
        add(ModItems.KUSANAGI.get(), "Kusanagi");
        add(ModItems.MURAKUMO_KUSANAGI.get(), "Murakumo Kusanagi");
        add(ModItems.SWORD_EMBRYO.get(), "Sword Embryo");
        add(ModItems.ICE_SHADOW_HAMMER.get(), "Ice Shadow Hammer");
        add(ModItems.SHADOW_EROSION_DAGGER.get(), "Shadow Erosion Dagger");
        add(ModItems.SHADOW_EROSION_PICKAXE.get(), "Shadow Erosion Pickaxe");
        add(ModItems.SHADOW_EROSION_AXE.get(), "Shadow Erosion Axe");
        add(ModItems.SHADOW_EROSION_SHOVEL.get(), "Shadow Erosion Shovel");
        add(ModItems.SHADOW_EROSION_HOE.get(), "Shadow Erosion Hoe");
        add(ModItems.THERMAL_DAGGER.get(), "Thermal Dagger");
        add(ModItems.DESERT_SWORD.get(), "Desert Greatsword");
        add(ModItems.CHENJINGMEN_DESERT_SWORD.get(), "Chenjingmen Desert");
        add(ModItems.BROKEN_HERO_SWORD.get(), "Broken Hero Sword");
        add(ModItems.TITANIUM_HELMET.get(), "Titanium Helmet");
        add(ModItems.TITANIUM_CHESTPLATE.get(), "Titanium Chestplate");
        add(ModItems.TITANIUM_LEGGINGS.get(), "Titanium Leggings");
        add(ModItems.TITANIUM_BOOTS.get(), "Titanium Boots");
        add(ModItems.SCULK_HELMET.get(), "Sculk Helmet");
        add(ModItems.SCULK_CHESTPLATE.get(), "Sculk Chestplate");
        add(ModItems.SCULK_LEGGINGS.get(), "Sculk Leggings");
        add(ModItems.SCULK_BOOTS.get(), "Sculk Boots");
        add(ModItems.DYEDREAM_HELMET.get(), "Dyedream Helmet");
        add(ModItems.DYEDREAM_CHESTPLATE.get(), "Dyedream Chestplate");
        add(ModItems.DYEDREAM_LEGGINGS.get(), "Dyedream Leggings");
        add(ModItems.DYEDREAM_BOOTS.get(), "Dyedream Boots");
        add(ModItems.MELT_DREAM_HELMET.get(), "Melt Dream Crystal Helmet");
        add(ModItems.MELT_DREAM_CHESTPLATE.get(), "Melt Dream Crystal Chestplate");
        add(ModItems.MELT_DREAM_LEGGINGS.get(), "Melt Dream Crystal Leggings");
        add(ModItems.MELT_DREAM_BOOTS.get(), "Melt Dream Crystal Boots");
        add(ModItems.MACHINE_LIGHT_WING.get(), "Machine Light Wing");
        add(ModItems.ANGEL_WING.get(), "Angel Wing");
        add(ModItems.FORSAKENS_WING.get(), "Forsakens Wing");
        add(ModItems.DREAM_FERTILIZER.get(), "Dream Fertilizer");
        add(ModItems.DREAM_NOTES_DYEDREAM_WORLD.get(), "Dream Notes - Dyedream World");
        add(ModItems.DREAM_NOTES_LAMP_SHADOW_WORLD.get(), "Dream Notes - Lamp Shadow World");
        add(ModItems.DREAM_NOTES_WIND_JOURNEY_WORLD.get(), "Dream Notes - Wind Journey World");
        add(ModItems.DREAM_NOTES_STORY_GUIDE.get(), "Resolved Notes");
        add(ModItems.BLUE_PRINT.get(), "Blue Print");
        add(ModItems.REED_ROD.get(), "Reed Rod");
        add(ModItems.MELT_DREAM_COIN.get(), "Melt Dream Coin");
        add(ModItems.MELT_DREAM_COIN_PILE.get(), "Melt Dream Coin Pile");
        add(ModItems.JUNGLE_SPORE.get(), "Jungle Spore");
        add(ModItems.PERGAMYN.get(), "Pergamyn");
        add("block.pasterdream.shadow_liquid", "Shadow Liquid");
        add("block.pasterdream.melt_dream_liquid", "Melt Dream Liquid");

        add(ModBlocks.DYEDREAM_DIRT.get(), "Dye Dream Dirt");
        add(ModBlocks.DYEDREAM_GRASS_BLOCK.get(), "Dye Dream Grass Block");
        add(ModBlocks.DYEDREAM_FARMLAND.get(), "Dye Dream Farmland");
        add(ModBlocks.DYEDREAM_LOG.get(), "Dye Dream Log");
        add(ModBlocks.DYEDREAM_WOOD.get(), "Dye Dream Wood");
        add(ModBlocks.DYEDREAM_LEAVES.get(), "Dye Dream Leaves");
        add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get(), "Dye Dream Worldtree Leaves");
        add(ModBlocks.DYEDREAM_SAPLING.get(), "Dye Dream Sapling");
        add(ModBlocks.DYEDREAM_QUARTZ_ORE.get(), "Dye Dream Quartz Ore");
        add(ModBlocks.DYEDREAM_DUST_ORE.get(), "Dye Dream Dust Ore");
        add(ModBlocks.AMBER_CANDY_ORE.get(), "Amber Candy Ore");
        add(ModBlocks.TITANIUM_ORE.get(), "Titanium Ore");
        add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(), "Deepslate Titanium Ore");
        add(ModBlocks.MOLTEN_GOLD_ORE.get(), "Molten Gold Ore");
        add(ModBlocks.SOUL_ORE.get(), "Soul Ore");
        add(ModBlocks.CONGEAL_WIND_ORE.get(), "Congeal Wind Ore");
        add(ModBlocks.WIND_RUNNER_CRYSTAL_ORE.get(), "Windrunner Crystal Ore");
        add(ModBlocks.CONGEAL_WIND_BLOCK.get(), "Congeal Wind Block");
        add(ModBlocks.WIND_RUNNER_CRYSTAL_BLOCK.get(), "Windrunner Crystal Block");
        add(ModBlocks.CONGEAL_WIND_IRON_BLOCK.get(), "Congeal Wind Iron Block");
        add(ModBlocks.CONGEAL_WIND_IRON_BARS.get(), "Congeal Wind Iron Bars");
        add(ModBlocks.EJECTION_PRESSURE_PLATE.get(), "Ejection Pressure Plate");
        add(ModBlocks.EJECTION_PRESSURE_BLOCK.get(), "Ejection Pressure Block");
        add(ModBlocks.RAW_TITANIUM_BLOCK.get(), "Raw Titanium Block");
        add(ModBlocks.SALT_BLOCK.get(), "Salt Block");
        add(ModBlocks.TITANIUM_BLOCK.get(), "Titanium Block");
        add(ModBlocks.MOLTEN_GOLD_BLOCK.get(), "Molten Gold Block");
        add(ModBlocks.CHARGED_AMETHYST_BLOCK.get(), "Charged Amethyst Block");
        add(ModBlocks.RUST_BLACK_METAL_BLOCK.get(), "Rust Black Metal Block");
        add(ModBlocks.BLACK_METAL_BLOCK.get(), "Black Metal Block");
        add(ModBlocks.RUST_BLACK_METAL_WALL.get(), "Rust Black Metal Wall");
        add(ModBlocks.RUST_BLACK_METAL_BARS.get(), "Rust Black Metal Bars");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get(), "Dye Dream Quartz Block");
        add(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get(), "Smooth Dye Dream Quartz Block");
        add(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get(), "Dye Dream Quartz Bricks");
        add(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get(), "Dye Dream Quartz Pillar");
        add(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get(), "Chiseled Dye Dream Quartz Block");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get(), "Dye Dream Quartz Stairs");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), "Dye Dream Quartz Slab");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get(), "Dye Dream Quartz Wall");
        add(ModBlocks.DYEDREAM_PLANKS.get(), "Dye Dream Planks");
        add(ModBlocks.DYEDREAM_STAIRS.get(), "Dye Dream Stairs");
        add(ModBlocks.DYEDREAM_SLAB.get(), "Dye Dream Slab");
        add(ModBlocks.DYEDREAM_FENCE.get(), "Dye Dream Fence");
        add(ModBlocks.DYEDREAM_FENCE_GATE.get(), "Dye Dream Fence Gate");
        add(ModBlocks.DYEDREAM_PANE.get(), "Dye Dream Pane");
        add(ModBlocks.DYEDREAM_DOOR.get(), "Dye Dream Door");
        add(ModBlocks.DYEDREAM_TRAPDOOR.get(), "Dye Dream Trapdoor");
        add(ModBlocks.DYEDREAM_PRESSURE_PLATE.get(), "Dye Dream Pressure Plate");
        add(ModBlocks.DYEDREAM_BUTTON.get(), "Dye Dream Button");
        add(ModBlocks.PINK_SLIME_BLOCK.get(), "Pink Slime Block");
        add(ModBlocks.PINK_MUSHROOM_BLOCK.get(), "Pink Mushroom Cap");
        add(ModBlocks.PINK_MUSHROOM_STEM.get(), "Pink Mushroom Stem");
        add(ModBlocks.PINK_MUSHROOM_PORES.get(), "Pink Mushroom Pores");
        add(ModBlocks.PINK_SHROOMLIGHT.get(), "Pink Shroomlight");
        add(ModBlocks.PINK_MUSHROOM.get(), "Pink Mushroom");
        add(ModBlocks.TALL_PINK_MUSHROOM.get(), "Tall Pink Mushroom");
        add(ModBlocks.DYEDREAM_SAND.get(), "Dye Dream Sand");
        add(ModBlocks.DYEDREAM_GLASS.get(), "Dye Dream Glass");
        add(ModBlocks.DYEDREAM_GLASS_PANE.get(), "Dye Dream Glass Pane");
        add(ModBlocks.CARVE_DYEDREAM_GLASS.get(), "Carved Dye Dream Glass");
        add(ModBlocks.CARVE_DYEDREAM_GLASS_PANE.get(), "Carved Dye Dream Glass Pane");
        add(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get(), "Gilded Carved Dye Dream Glass");
        add(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS_PANE.get(), "Gilded Carved Dye Dream Glass Pane");
        add(ModBlocks.CLARITY_GLASS.get(), "Clarity Glass");
        add(ModBlocks.CLARITY_GLASS_PANE.get(), "Clarity Glass Pane");
        add(ModBlocks.CARVE_CLARITY_GLASS.get(), "Carved Clarity Glass");
        add(ModBlocks.CARVE_CLARITY_GLASS_PANE.get(), "Carved Clarity Glass Pane");
        add(ModBlocks.FRAME_CLARITY_GLASS.get(), "Framed Clarity Glass");
        add(ModBlocks.FRAME_CLARITY_GLASS_PANE.get(), "Framed Clarity Glass Pane");
        add(ModBlocks.BREAK_WIND_CURTAIN.get(), "Break Wind Curtain");
        add(ModBlocks.CHRISTMAS_LIGHTS.get(), "Christmas Lights");
        add(ModBlocks.DYEDREAM_BUDDING_BLOCK.get(), "Budding Dye Dream");
        add(ModBlocks.SMALL_DYEDREAM_BUD.get(), "Small Dye Dream Bud");
        add(ModBlocks.MEDIUM_DYEDREAM_BUD.get(), "Medium Dye Dream Bud");
        add(ModBlocks.LARGE_DYEDREAM_BUD.get(), "Large Dye Dream Bud");
        add(ModBlocks.DYEDREAM_BUD_BLOCK.get(), "Dye Dream Bud Block");
        add(ModBlocks.DYEDREAM_BUD_BRICKS.get(), "Dye Dream Bud Bricks");
        add(ModBlocks.DYEDREAM_BUD_STAIRS.get(), "Dye Dream Bud Stairs");
        add(ModBlocks.DYEDREAM_BUD_SLAB.get(), "Dye Dream Bud Slab");
        add(ModBlocks.DYEDREAM_BUD_WALL.get(), "Dye Dream Bud Wall");
        add(ModBlocks.DYEDREAM_ICE.get(), "Dye Dream Ice");
        add(ModBlocks.DYEDREAM_PACKED_ICE.get(), "Dye Dream Packed Ice");
        add(ModBlocks.CLOUD.get(), "Cloud");
        add(ModBlocks.DARK_CLOUD.get(), "Dark Cloud");
        add(ModBlocks.WHITE_SAND.get(), "White Sand");
        add(ModBlocks.THICK_CLOUD.get(), "Thick Cloud");
        add(ModBlocks.SHADOW.get(), "Shadow");
        add(ModBlocks.THICK_SHADOW.get(), "Thick Shadow");
        add(ModBlocks.SHADOW_STONE.get(), "Shadow Stone");
        add(ModBlocks.SHADOW_STONE_BRICK.get(), "Shadow Stone Brick");
        add(ModBlocks.SHADOW_STONE_BRICK_STAIRS.get(), "Shadow Stone Brick Stairs");
        add(ModBlocks.SHADOW_STONE_BRICK_SLAB.get(), "Shadow Stone Brick Slab");
        add(ModBlocks.SHADOW_STONE_BRICK_WALL.get(), "Shadow Stone Brick Wall");
        add(ModBlocks.NARROW_SHADOW_STONE_BRICK.get(), "Narrow Shadow Stone Brick");
        add(ModBlocks.NARROW_SHADOW_STONE_BRICK_STAIRS.get(), "Narrow Shadow Stone Brick Stairs");
        add(ModBlocks.NARROW_SHADOW_STONE_BRICK_SLAB.get(), "Narrow Shadow Stone Brick Slab");
        add(ModBlocks.NARROW_SHADOW_STONE_BRICK_WALL.get(), "Narrow Shadow Stone Brick Wall");
        add(ModBlocks.SHADOW_STONE_TILES.get(), "Shadow Stone Tiles");
        add(ModBlocks.SHADOW_STONE_TILES_STAIRS.get(), "Shadow Stone Tiles Stairs");
        add(ModBlocks.SHADOW_STONE_TILES_SLAB.get(), "Shadow Stone Tiles Slab");
        add(ModBlocks.SHADOW_STONE_TILES_WALL.get(), "Shadow Stone Tiles Wall");
        add(ModBlocks.CRACKED_SHADOW_STONE_BRICK.get(), "Cracked Shadow Stone Brick");
        add(ModBlocks.CHISELED_SHADOW_STONE_BRICK.get(), "Chiseled Shadow Stone Brick");
        add(ModBlocks.SHADOW_STONE_CAGE_RUNE.get(), "Shadow Stone Cage Rune");
        add(ModBlocks.SHADOW_STONE_HOLY_GRAIL_RUNE.get(), "Shadow Stone Holy Grail Rune");
        add(ModBlocks.SHADOW_STONE_OBLATION_RUNE.get(), "Shadow Stone Oblation Rune");
        add(ModBlocks.SHADOW_STONE_TRIPOD_CAULDRON_RUNE.get(), "Shadow Stone Tripod Cauldron Rune");
        // ===== Shadow Dungeon Blocks =====
        add(ModBlocks.SHADOW_DUNGEON_STONE.get(), "Shadow Dungeon Stone");
        add(ModBlocks.CHISELED_SHADOW_DUNGEON_BRICKS.get(), "Chiseled Shadow Dungeon Bricks");
        add(ModBlocks.SHADOW_DUNGEON_BRICKS.get(), "Shadow Dungeon Bricks");
        add(ModBlocks.CRACKED_SHADOW_DUNGEON_BRICKS.get(), "Cracked Shadow Dungeon Bricks");
        add(ModBlocks.FRACTURED_SHADOW_DUNGEON_BRICKS.get(), "Fractured Shadow Dungeon Bricks");
        add(ModBlocks.SHADOW_DUNGEON_BRICK_STAIRS.get(), "Shadow Dungeon Brick Stairs");
        add(ModBlocks.SHADOW_DUNGEON_BRICK_SLAB.get(), "Shadow Dungeon Brick Slab");
        add(ModBlocks.SHATTERED_SHADOW_DUNGEON_BRICKS.get(), "Shattered Shadow Dungeon Bricks");
        add(ModBlocks.SHADOW_DUNGEON_GATE.get(), "Shadow Dungeon Gate");
        add(ModBlocks.SHADOW_DUNGEON_BARRIER.get(), "Shadow Dungeon Barrier");
        add(ModBlocks.SHADOW_DUNGEON_WALL_KEY.get(), "Shadow Dungeon Key (Wall)");
        add(ModBlocks.SHADOW_DUNGEON_FLOOR_KEY.get(), "Shadow Dungeon Key (Floor)");
        add(ModBlocks.DREAM_SPAWNER.get(), "Dream Spawner");
        add(ModBlocks.FADED_DREAM_SPAWNER.get(), "Faded Dream Spawner");
        add(ModBlocks.SHADOW_DUNGEON_PORTAL.get(), "Shadow Dungeon Portal Core");
        add(ModBlocks.BROKEN_SHADOW_DUNGEON_PORTAL.get(), "Broken Shadow Dungeon Portal Core");
        add(ModBlocks.SHADOW_ARENA_BLOCK.get(), "Shadow Arena Block");
        add(ModBlocks.SHADOW_FISSURE_0.get(), "Shadow Fissure");
        add(ModBlocks.SHADOW_FISSURE_1.get(), "Shadow Fissure");
        add(ModBlocks.SHADOW_FISSURE_2.get(), "Shadow Fissure");
        add(ModBlocks.SHADOW_FISSURE_3.get(), "Shadow Fissure");
        add(ModBlocks.SHADOW_FISSURE_4.get(), "Shadow Fissure");
        add(ModBlocks.SHADOW_FISSURE_5.get(), "Shadow Fissure");
        add(ModBlocks.SHADOW_NYLIUM.get(), "Shadow Nylium");
        add(ModBlocks.SHADOW_LIGHT.get(), "Shadow Light");
        add(ModBlocks.SHADOW_SHROOMLIGHT.get(), "Shadow Shroomlight");
        add(ModBlocks.SHADOW_CANDLE.get(), "Shadow Candle");
        add(ModBlocks.TWILIGHT_LANTERN.get(), "Twilight Lantern");
        add(ModBlocks.SHADOW_BED.get(), "Shadow Bed");
        add(ModBlocks.SHADOW_WART_BLOCK.get(), "Shadow Wart Block");
        add(ModBlocks.SHADOW_STEM.get(), "Shadow Stem");
        add(ModBlocks.SHADOW_HYPHAE.get(), "Shadow Hyphae");
        add(ModBlocks.STRIPPED_SHADOW_STEM.get(), "Stripped Shadow Stem");
        add(ModBlocks.STRIPPED_SHADOW_HYPHAE.get(), "Stripped Shadow Hyphae");
        add(ModBlocks.SHADOW_PLANKS.get(), "Shadow Planks");
        add(ModBlocks.SHADOW_STAIRS.get(), "Shadow Stairs");
        add(ModBlocks.SHADOW_SLAB.get(), "Shadow Slab");
        add(ModBlocks.SHADOW_FENCE.get(), "Shadow Fence");
        add(ModBlocks.SHADOW_FENCE_GATE.get(), "Shadow Fence Gate");
        add(ModBlocks.SHADOW_PANE.get(), "Shadow Pane");
        add(ModBlocks.SHADOW_DOOR.get(), "Shadow Door");
        add(ModBlocks.SHADOW_TRAPDOOR.get(), "Shadow Trapdoor");
        add(ModBlocks.SHADOW_PRESSURE_PLATE.get(), "Shadow Pressure Plate");
        add(ModBlocks.SHADOW_BUTTON.get(), "Shadow Button");
        add(ModBlocks.SHADOW_BOOKSHELF.get(), "Shadow Bookshelf");
        add(ModBlocks.WORN_SHADOW_BOOKSHELF.get(), "Worn Shadow Bookshelf");
        add(ModBlocks.COBWEB_SHADOW_BOOKSHELF.get(), "Cobweb Shadow Bookshelf");
        add(ModBlocks.KEY_SHADOW_BOOKSHELF.get(), "Key Shadow Bookshelf");
        add(ModItems.SHADOW_DUNGEON_KEY.get(), "Shadow Dungeon Key");
        add("tooltip.pasterdream.shadow_dungeon_key", "§7Used to open the door to the lower level of the Shadow Dungeon");
        add(ModBlocks.BIG_BUBBLE.get(), "Big Bubble");
        add(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get(), "Dyedream Crystal Lantern");
        add(ModBlocks.DYEDREAM_LANTERN.get(), "Dye Dream Lantern");
        add(ModBlocks.DYEDREAM_ALLOY_BLOCK.get(), "Dye Dream Alloy Block");
        add(ModBlocks.POLISHED_CALCITE.get(), "Polished Calcite");
        add(ModBlocks.POLISHED_CALCITE_STAIRS.get(), "Polished Calcite Stairs");
        add(ModBlocks.POLISHED_CALCITE_SLAB.get(), "Polished Calcite Slab");
        add(ModBlocks.POLISHED_CALCITE_WALL.get(), "Polished Calcite Wall");
        add(ModBlocks.CALCITE_TILES.get(), "Calcite Tiles");
        add(ModBlocks.CALCITE_TILES_STAIRS.get(), "Calcite Tiles Stairs");
        add(ModBlocks.CALCITE_TILES_SLAB.get(), "Calcite Tiles Slab");
        add(ModBlocks.CALCITE_TILES_WALL.get(), "Calcite Tiles Wall");
        add(ModBlocks.ICE_STONE.get(), "Icestone");
        add(ModBlocks.ICE_BUD.get(), "Ice Bud");
        add(ModBlocks.CYAN_STONE.get(), "Cyan Stone");
        add(ModBlocks.CYAN_MOSS_STONE.get(), "Cyan Moss Stone");
        add(ModBlocks.CYAN_STONE_BRICKS.get(), "Cyan Stone Bricks");
        add(ModBlocks.CYAN_STONE_BRICK_STAIRS.get(), "Cyan Stone Brick Stairs");
        add(ModBlocks.CYAN_STONE_BRICK_SLAB.get(), "Cyan Stone Brick Slab");
        add(ModBlocks.CYAN_STONE_BRICK_WALL.get(), "Cyan Stone Brick Wall");
        add(ModBlocks.CYAN_STONE_PRESSURE_PLATE.get(), "Cyan Stone Pressure Plate");
        add(ModBlocks.CYAN_STONE_BUTTON.get(), "Cyan Stone Button");
        add(ModBlocks.MOSSY_CYAN_STONE_BRICKS.get(), "Mossy Cyan Stone Bricks");
        add(ModBlocks.MOSSY_CYAN_STONE_BRICK_STAIRS.get(), "Mossy Cyan Stone Brick Stairs");
        add(ModBlocks.MOSSY_CYAN_STONE_BRICK_SLAB.get(), "Mossy Cyan Stone Brick Slab");
        add(ModBlocks.MOSSY_CYAN_STONE_BRICK_WALL.get(), "Mossy Cyan Stone Brick Wall");
        add(ModBlocks.CHISELED_CYAN_STONE_BRICKS.get(), "Chiseled Cyan Stone Bricks");
        add(ModBlocks.CYAN_STONE_PILLAR.get(), "Cyan Stone Pillar");
        add(ModBlocks.QYM_DOLL.get(), "琴雨梦Doll");
        add(ModBlocks.UUZ_DOLL.get(), "幼幼紫Doll");
        add(ModBlocks.DYEDREAM_CRACK.get(), "Dyedream Crack");
        add(ModBlocks.CLAYPAN.get(), "Claypan");
        add(ModBlocks.CLAY_POT.get(), "Clay Pot");
        add(ModBlocks.SHADOW_CLAY_POT.get(), "Shadow Clay Pot");
        add(ModBlocks.DREAM_CAULDRON.get(), "Dream Cauldron");
        add(ModBlocks.DYEDREAM_DESK.get(), "Dyedream Desk");
        add(ModBlocks.SHADOW_DESK.get(), "Shadow Desk");
        add(ModBlocks.PICNIC_BASKET.get(), "Picnic Basket");
        add(ModBlocks.SHADOW_CHEST.get(), "Shadow Chest");
        add(ModBlocks.WIND_MOOR_CRATE.get(), "Wind Moor Crate");
        add(ModBlocks.WIND_MOOR_LOG.get(), "Wind Moor Log");
        add(ModBlocks.WIND_MOOR_WOOD.get(), "Wind Moor Wood");
        add(ModBlocks.STRIPPED_WIND_MOOR_LOG.get(), "Stripped Wind Moor Log");
        add(ModBlocks.STRIPPED_WIND_MOOR_WOOD.get(), "Stripped Wind Moor Wood");
        add(ModBlocks.WIND_MOOR_LEAVES_0.get(), "Wind Moor Leaves");
        add(ModBlocks.WIND_MOOR_LEAVES_1.get(), "Wind Moor Leaves");
        add(ModBlocks.WIND_MOOR_PLANKS.get(), "Wind Moor Planks");
        add(ModBlocks.WIND_MOOR_STAIRS.get(), "Wind Moor Stairs");
        add(ModBlocks.WIND_MOOR_SLAB.get(), "Wind Moor Slab");
        add(ModBlocks.WIND_MOOR_FENCE.get(), "Wind Moor Fence");
        add(ModBlocks.WIND_MOOR_FENCE_GATE.get(), "Wind Moor Fence Gate");
        add(ModBlocks.WIND_MOOR_PANE.get(), "Wind Moor Pane");
        add(ModBlocks.WIND_MOOR_DOOR.get(), "Wind Moor Door");
        add(ModBlocks.WIND_MOOR_TRAPDOOR.get(), "Wind Moor Trapdoor");
        add(ModBlocks.WIND_MOOR_PRESSURE_PLATE.get(), "Wind Moor Pressure Plate");
        add(ModBlocks.WIND_MOOR_BUTTON.get(), "Wind Moor Button");
        add(ModBlocks.FIG_VINE.get(), "Fig Vine");
        add(ModBlocks.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get(), "The Endless Book Of Dream Seekers");
        add(ModBlocks.RESEARCH_TABLE.get(), "Research Table");
        add(ModBlocks.LOST_SWORD_TOMB.get(), "Lost Sword Tomb");
        add(ModBlocks.GOLDEN_FOX_SCULPTURE.get(), "Golden Fox Sculpture");
        add(ModBlocks.WIND_KNIGHT_ALTAR.get(), "Wind Knight Altar");
        add("block.pasterdream.wind_knight_altar.need_crystal", "Embed a [Windrunner Crystal]");
        add("block.pasterdream.wind_knight_altar.need_torso", "Need [Congeal Wind Iron Ingot] to assemble the torso");
        add("block.pasterdream.wind_knight_altar.need_arms", "Need [Congeal Wind Iron Ingot] to assemble the arms");
        add("block.pasterdream.wind_knight_altar.need_head", "Need [Congeal Wind Iron Ingot] to assemble the head");
        add("block.pasterdream.wind_knight_altar.throw_lightning", "Requires a Lightning Potion Bottle: right-click the altar, or throw the bottle near the altar to awaken it");
        add("block.pasterdream.golden_fox_sculpture.no_reaction", "The sculpture does not respond...");
        add("block.pasterdream.golden_fox_sculpture.cooldown", "The sculpture is still slumbering... %s seconds remaining");
        add(ModBlocks.FOX_SCULPTURE.get(), "Fox Sculpture");
        add(ModBlocks.ECOLOGY_GLASS_JAR.get(), "Ecology Glass Jar");
        add(ModBlocks.FIREFLY_GLASS_JAR.get(), "Firefly Glass Jar");
        add(ModBlocks.FIREFLY_NEST.get(), "Firefly Nest");
        add(ModBlocks.BIRDS_NEST.get(), "Birds Nest");
        add(ModBlocks.DESERT_HERO_TOMB.get(), "Desert Hero Tomb");
        add(ModBlocks.MELT_DREAM_CRYSTAL_CHEST.get(), "Melt Dream Crystal Chest");
        add(ModBlocks.OPENED_MELT_DREAM_CRYSTAL_CHEST.get(), "Opened Melt Dream Crystal Chest");
        add(ModBlocks.DREAM_ACCUMULATOR.get(), "Dream Accumulator");
        add(ModItems.SORBENT.get(), "Sorbent");
        add(ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE.get(), "Weapon Workshop Crafting Table");
        add(ModBlocks.WEAPON_WORKSHOP_ANVIL.get(), "Weapon Workshop Anvil");
        add(ModBlocks.WEAPON_WORKSHOP_COOLER_POT.get(), "Weapon Workshop Cooler Pot");
        add(ModBlocks.WEAPON_WORKSHOP_HAMMER.get(), "Weapon Workshop Hammer");
        add(ModBlocks.WEAPON_WORKSHOP_GRIND_STONE.get(), "Weapon Workshop Grind Stone");
        add(ModBlocks.WEAPON_WORKSHOP_BLAST_FURNACE.get(), "Weapon Workshop Blast Furnace");
        add(ModBlocks.SHADOW_BLAST_FURNACE.get(), "Shadow Blast Furnace");

        add(ModBlocks.DYEDREAM_COROLLA_CROP.get(), "Dyedream Corolla");
        add(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get(), "Mature Dyedream Corolla");
        add(ModBlocks.WHITE_COROLLA_CROP.get(), "White Corolla Crop");
        add(ModItems.WHITE_COROLLA_CROP_AGE_1.get(), "Mature White Corolla Crop");
        add(ModBlocks.LIGHT_BALL_CROP.get(), "Light Ball Crop");
        add(ModItems.LIGHT_BALL_CROP_AGE_1.get(), "Mature Light Ball Crop");
        add(ModBlocks.CLOUD_CROP.get(), "Cloud Crop");
        add(ModItems.CLOUD_CROP_AGE_1.get(), "Mature Cloud Crop");
        add(ModBlocks.COTTON_CROP.get(), "Cotton Crop");
        add(ModItems.COTTON_CROP_AGE_1.get(), "Mature Cotton Crop");

        add(ModBlocks.GOLDENROD.get(), "Goldenrod");
        add(ModBlocks.FERRARIA_CRISPA.get(), "Ferraria Crispa");
        add(ModBlocks.EUSTOMA.get(), "Eustoma");
        add(ModBlocks.MALVA_SINENSIS_CAVAN.get(), "Malva Sinensis Cavan");
        add(ModBlocks.LINHT_FLOWER.get(), "Linht Flower");
        add(ModBlocks.DREAMING_LOTUS.get(), "Dreaming Lotus");
        add(ModBlocks.MISTY_DREAMING_LOTUS.get(), "Misty Dreaming Lotus");
        add(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get(), "Dyedream Lily of the Valley");
        add(ModBlocks.BLAZE_FLOWER.get(), "Blaze Flower");
        add(ModBlocks.WHITE_ORCHID_FLOWER.get(), "White Orchid Flower");
        add(ModBlocks.EDELWEISS.get(), "Edelweiss");
        add(ModBlocks.NIPPY_EDELWEISS.get(), "Nippy Edelweiss");
        add(ModBlocks.DYEDREAM_LILY_PAD.get(), "Dye Dream Lily Pad");
        add(ModBlocks.DYEDREAM_LOTUS.get(), "Dye Dream Lotus");

        add(ModBlocks.DYEDREAM_MOSS.get(), "Dyedream Moss");
        add(ModBlocks.STEM_GRASS.get(),"Stem Grass");
        add(ModBlocks.TALL_STEM_GRASS.get(),"Tall Stem Grass");
        add(ModBlocks.SINGULARITY_FERN.get(), "Singularity Fern");
        add(ModBlocks.CRIMSON_THORNS.get(), "Crimson Thorns");
        add(ModBlocks.OATS.get(), "oats");
        add(ModBlocks.RYE.get(), "Rye");
        add(ModBlocks.POLISHED_CALCITE_STALICRIPE.get(), "Polished Calcite Stalicripe");
        add(ModBlocks.SMALL_POLISHED_CALCITE_STALICRIPE.get(), "Small Polished Calcite Stalicripe");
        add(ModBlocks.CALCITE_CONE.get(), "Calcite Cone");
        add(ModBlocks.DYEDREAM_SEAGRASS.get(), "Dyedream Seagrass");
        add(ModBlocks.REED.get(), "Reed");
        add(ModBlocks.DYEDREAM_VINE.get(), "Dyedream Vine");
        add(ModBlocks.JUNGLE_SPORANGIUM.get(), "Jungle Sporangium");
        add(ModBlocks.FOURLEAF_CLOVER.get(),"fourleaf clover");
        add(ModBlocks.HAIRY_MOSS.get(), "Hairy Moss");
        add(ModBlocks.WIND_CLEAVING_GRASS.get(), "Wind Cleaving Grass");
        add(ModBlocks.WIND_FEATHER_GRASS.get(), "Wind Feather Grass");
        add(ModBlocks.WIND_ISLAND_REED.get(), "Wind Island Reed");

        add(ModBlocks.SHADOW_SHORT_ROOTS.get(), "Shadow Short Roots");
        add(ModBlocks.SHADOW_ROOTS.get(), "Shadow Roots");
        add(ModBlocks.SHADOW_STEM_FERN.get(), "Shadow Stem Fern");
        add(ModBlocks.SHADOW_SPROUTS.get(), "Shadow Sprouts");
        add(ModBlocks.SHADOW_FERN.get(), "Shadow Fern");
        add(ModBlocks.SHADOW_FUNGUS.get(), "Shadow Fungus");

        add(ModBlocks.POTTED_STEM_GRASS.get(), "Potted Stem Grass");
        add(ModBlocks.POTTED_PINK_MUSHROOM.get(), "Potted Pink Mushroom");
        add(ModBlocks.POTTED_DYEDREAM_MOSS.get(), "Potted Dyedream Moss");
        add(ModBlocks.POTTED_LINHT_FLOWER.get(), "Potted Linht Flower");
        add(ModBlocks.POTTED_DYEDREAM_LILY_OF_THE_VALLEY.get(), "Potted Dyedream Lily of the Valley");
        add(ModBlocks.POTTED_SINGULARITY_FERN.get(), "Potted Singularity Fern");
        add(ModBlocks.POTTED_FERRARIA_CRISPA.get(), "Potted Ferraria Crispa");
        add(ModBlocks.POTTED_EUSTOMA.get(), "Potted Eustoma");
        add(ModBlocks.POTTED_JUNGLE_SPORANGIUM.get(), "Potted Jungle Sporangium");
        add(ModBlocks.POTTED_MALVA_SINENSIS_CAVAN.get(), "Potted Malva Sinensis Cavan");
        add(ModBlocks.POTTED_GOLDENROD.get(), "Potted Goldenrod");
        add(ModBlocks.POTTED_FOURLEAF_CLOVER.get(), "Potted Fourleaf Clover");
        add(ModBlocks.POTTED_BLAZE_FLOWER.get(), "Potted Blaze Flower");
        add(ModBlocks.POTTED_WHITE_ORCHID_FLOWER.get(), "Potted White Orchid Flower");
        add(ModBlocks.POTTED_SHADOW_SHORT_ROOTS.get(), "Potted Shadow Short Roots");
        add(ModBlocks.POTTED_SHADOW_ROOTS.get(), "Potted Shadow Roots");
        add(ModBlocks.POTTED_SHADOW_SPROUTS.get(), "Potted Shadow Sprouts");
        add(ModBlocks.POTTED_SHADOW_FERN.get(), "Potted Shadow Fern");
        add(ModBlocks.POTTED_SHADOW_FUNGUS.get(), "Potted Shadow Fungus");
        add(ModBlocks.POTTED_EDELWEISS.get(), "Potted Edelweiss");
        add(ModBlocks.POTTED_NIPPY_EDELWEISS.get(), "Potted Nippy Edelweiss");
        add(ModBlocks.POTTED_DYEDREAM_SAPLING.get(), "Potted Dyedream Sapling");
        add(ModBlocks.POTTED_REED.get(), "Potted Reed");
        add(ModBlocks.POTTED_RYE.get(), "Potted Rye");
        add(ModBlocks.POTTED_OATS.get(), "Potted Oats");
        add(ModBlocks.POTTED_DYEDREAM_COROLLA_CROP.get(), "Potted Dyedream Corolla");
        add(ModBlocks.POTTED_WHITE_COROLLA_CROP.get(), "Potted White Corolla");
        add(ModBlocks.POTTED_LIGHT_BALL_CROP.get(), "Potted Light Ball");
        add(ModBlocks.POTTED_CLOUD_CROP.get(), "Potted Cloud");
        add(ModBlocks.POTTED_COTTON_CROP.get(), "Potted Cotton");
        add(ModBlocks.POTTED_HAIRY_MOSS.get(), "Potted Hairy Moss");
        add(ModBlocks.POTTED_WIND_CLEAVING_GRASS.get(), "Potted Wind Cleaving Grass");

        add(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get(), "Melt Dream Crystal Fragment");
        add(ModItems.MELT_DREAM_AURORIAN_STEEL.get(), "Melt Dream Aurorian Steel");
        add("tooltip.pasterdreammod.melt_dream_aurorian_steel", "§7§oKids, did you ask?");


        add(ModItems.DEBUG_SWORD.get(), "Debug Sword");
        add(ModItems.LOOT_GENERATOR.get(), "Loot Generator");
        add(ModItems.MELT_DREAM_CRYSTAL_CHEST_RESET_TOOL.get(), "Melt Dream Crystal Chest Reset Tool");
        add(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_0.get(), "Model Break Particle Provider Block 0");
        add(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_1.get(), "Model Break Particle Provider Block 1");
        add(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2.get(), "Model Break Particle Provider Block 2");
        add(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_3.get(), "Model Break Particle Provider Block 3");
        add(ModBlocks.DREAM_TRAIN_STRUCTURE.get(), "Dream Train Structure");

        add("fluid.pasterdream.melt_dream_liquid","Melt Dream Liquid");
        add("fluid.pasterdream.shadow_liquid","Shadow Liquid");

        add("fluid.pasterdream.apple_juice","Apple Juice(fluid)");
        add("fluid.pasterdream.dream_juice","Dream Juice(fluid)");
        add("fluid.pasterdream.dyedream_flower_tea","Dyedream Flower Tea(fluid)");
        add("fluid.pasterdream.dyedream_juice","Dyedream Juice(fluid)");
        add("fluid.pasterdream.dyedream_perfume","Dyedream Perfume(fluid)");
        add("fluid.pasterdream.goldenrod_tea","Goldenrod Tea(fluid)");
        add("fluid.pasterdream.guiding_drug","Guiding Drug(fluid)");
        add("fluid.pasterdream.honey_juice","Honey Juice(fluid)");
        add("fluid.pasterdream.rage_elixir","Rage Elixir(fluid)");
        add("fluid.pasterdream.potion","Potion(fluid)");
        add("fluid.pasterdream.potion.with_effect","Potion (%s)");
        add("fluid.pasterdream.uncooked_dyedream_flower_tea","Uncooked Dyedream Flower Tea(fluid)");
        add("fluid.pasterdream.watermelon_juice","Watermelon Juice(fluid)");
        add("fluid.pasterdream.wind_plant_extract","Wind Plant Extract(fluid)");
        add("fluid.pasterdream.yeast","Yeast(fluid)");
        add("fluid.pasterdream.ink","Ink(fluid)");

        add("itemGroup.pasterdream.pasterdream_food_tab", "Paster Dream | Food & Drinks");
        add("itemGroup.pasterdream.pasterdream_items_tab", "Paster Dream | Items");
        add("itemGroup.pasterdream.pasterdream_gear_tab", "Paster Dream | Gear");
        add("itemGroup.pasterdream.pasterdream_blocks_tab", "Dye Dream World");
        add("itemGroup.pasterdream.pasterdream_shadow_tab", "Lamp Shadow World");
        add("itemGroup.pasterdream.pasterdream_plants_tab", "Paster Dream | Plants");
        add("itemGroup.pasterdream.pasterdream_equipment_tab", "Paster Dream | Equipment");
        add("itemGroup.pasterdream.pasterdream_dream_notes_tab", "Paster Dream | Dream Notes");
        add("itemGroup.pasterdream.pasterdream_dream_debug_tab", "Paster Dream | Debug");
        add("itemGroup.pasterdream.pasterdream_expansion_tab", "Paster Dream | Expansion");
        add("itemGroup.pasterdream.pasterdream_memento_tab", "Paster Dream | Mementos");
        add("itemGroup.pasterdream.wind_journey_world", "Wind Journey World");

        add("item.pasterdream.duke_coin_curio", "Duke Coin Curio");
        add("tooltip.pasterdream.duke_coin_curio.effect.luck", "§7▪ §9+7 Luck");
        add("tooltip.pasterdream.duke_coin_curio.effect.blink_cd", "§7▪ §c+7 Blink Cooldown");
        add("tooltip.pasterdream.duke_coin_curio.flavor", "§7§o-- 7 Days to Die");

        add("button.pasterdream.mortarbutton", "grinding");
        add("button.pasterdream.copy_button", "Copy");
        add("button.pasterdream.research_button", "Research");

        add("tooltip.pasterdreammod.magic_stone", "§7§oWhat kind of mage doesn't carry a few magic stones?");
        add("tooltip.pasterdreammod.goldenrod_tea", "§dContinuously removes Hunger and Nausea");
        add("tooltip.pasterdreammod.glass_cup_of_honey_juice", "§dClears all negative effects upon drinking");
        add("tooltip.pasterdreammod.dyedream_perfume", "§dClears insomnia and prevents phantom attacks");
        add("tooltip.pasterdreammod.dyedream_perfume.flavor", "§7§oWhen you're wondering why perfume is meant to be drunk,\nmaybe you should first consider: are you dreaming?");
        add("tooltip.pasterdreammod.melt_dream_crystal_fragment", "§7All creatures with souls in this world have dreams\nThose fleeting memories that cannot be recalled upon waking\nShatter and melt, buried across the world, condensing into crystals\nWaiting to be discovered by new souls");
        add("tooltip.pasterdreammod.upgrade_kit", "§7Used at the smithing table to upgrade equipment Can preserve the original enhancement properties of the equipment");
        add("tooltip.pasterdream.enhance_stone.usage", "§7Used to enhance synthesis embryos at the Weapon Workshop");
        add("tooltip.pasterdream.thermal_dagger.swim", "§7▪ §9+0.5 Swim Speed when in main hand");
        add("tooltip.pasterdream.thermal_dagger", "§7§o-- Subnautica");
        add("tooltip.pasterdream.deep_treasure", "§7Splashing waves push us into the distance");
        add("tooltip.pasterdream.shadow_deep_treasure", "§7Shadows still seem to flow across its surface");
        add("tooltip.pasterdream.attack_enhance_stone.effect", "§7▪ §9Attack Damage +0.1~+0.5");
        add("tooltip.pasterdream.luck_enhance_stone.effect", "§7▪ §9Luck +1~+2");
        add("tooltip.pasterdream.右键打开GUI", "§7Press right mouse button to open GUI");
        add("tooltip.pasterdream.此方块仅用于提供粒子效果，生存模式无法获取", "§7This block only use to provide particle effect, Can't get on Survival mode");
        add("tooltip.pasterdream.左键实体：直接删除此实体", "§7Left Button Press Entity: Delete This Entity");
        add("tooltip.pasterdream.左键方块：模拟破坏此方块", "§7Left Button Press Block: Simulate Destroy This Block");
        add("tooltip.pasterdream.右键实体：在聊天框打印此实体NBT", "§7Right Button Press Entity: Print This Entity's NBT In The Chat Box");
        add("tooltip.pasterdream.右键方块：在聊天框打印此方块BlockState和NBT", "§7Right Button Press Block: Print This Entity's BlockState And NBT In The Chat Box");
        add("tooltip.pasterdream.用于生成战利品，对着箱子点击右键以生成", "§7Used to generate loot, right-click on the chest to generate it");
        add("tooltip.pasterdream.当前设置战利品表：", "§7Current loot table settings:");
        add("tooltip.pasterdream.loot_table.not_set", "§7No set");
        add("tooltip.pasterdream.loot_generator.usage.shift", "§7Use Shift + Right Click on the container to generate loot in the container");

        add("message.pasterdream.loot_generator.no_loot_table", "§cLoot generator has no loot table set");
        add("message.pasterdream.loot_generator.not_container", "§cTarget block is not a container");
        add("message.pasterdream.loot_generator.loot_table_not_found", "§cLoot table %s does not exist");
        add("message.pasterdream.loot_generator.loot_table_set", "§aContainer cleared and loot table set: %s, will generate when opened");

        add("message.pasterdream.需要在本层寻找暗影地牢钥匙以打开大门", "Need to find Shadow Dungeon Key in this floor to open the gate");

        add("message.pasterdream.大门紧闭不开", "The gate is tightly closed");

        add("option.pasterdream.Q: 模拟无工具破坏", "Q: Simulate Break Without Tool");
        add("option.pasterdream.W: 模拟下界合金镐破坏", "W: Simulate Break With Netherite Pickaxe");
        add("option.pasterdream.E: 模拟下界合金镐时运III破坏", "E: Simulate Break With Fortune III Netherite Pickaxe");
        add("option.pasterdream.A: 模拟下界合金镐精准采集破坏", "A: Simulate Break With Silk Touch Netherite Pickaxe");
        add("option.pasterdream.S: 获取对应的BlockItem并掉落，并将方块设置为空气", "S: Get Corresponding BlockItem And Drop, And Set Block To Air");
        add("option.pasterdream.D: 将方块设置为空气的同时不触发方块更新", "D: Set The Block To Air Without NeighborChange");

        add("tooltip.pasterdream.normal_loot_table", "§3Normal Loot Table：%s，Weight: %d，Luck Multiplier Increase: %f");
        add("tooltip.pasterdream.rare_loot_table", "§aRare Loot Table：%s，Weight: %d，Luck Multiplier Increase: %f");
        add("tooltip.pasterdream.legend_loot_table", "§dLegend Loot Table：%s，Weight: %d，Luck Multiplier Increase: %f");
        add("tooltip.pasterdream.not_have_loot_table", "§cNot Have Loot Table");
        add("tooltip.pasterdream.按住Shift+右键点击打开的融梦水晶箱可复位（写入此物品NBT中包含的战利品列表）", "§7Press Shift And Right Mouse Button Click Opened Melt Dream Crystal Chest Can Reset(Write LootTable From NBT Which In This Item)");
        add("tooltip.pasterdream.reset_cost_melt_dream_energy", "Reset Cost %f Melt Dream Energy");
        add("tooltip.pasterdream.右键打开GUI以查看蓝图结构", "Right Mouse Click To Open GUI And View The Blue Print Structure.");

        add("tooltip.pasterdream.空", "Empty");
        add("tooltip.pasterdream.总容量:", "Total Capacity:");

        add("message.pasterdream.融梦水晶箱重置工具只能重置打开的融梦水晶箱", "Melt Dream Crystal Chest Reset Tool Only Can Reset Opened Melt Dream Crystal Chest");
        add("message.pasterdream.融梦水晶箱战利品品质应为1，2或3，但是此时为", "Melt Dream Crystal Chest Loot Table Level Should Be 1 or 2 or 3, But Now Is%d");
        add("message.pasterdream.融梦能量不足", "Melt Dream Energy Not Enough");

        add("message.pasterdream.已放置蓝图", "Blue Print Placed");
        add("message.pasterdream.取消放置蓝图", "Blue Print Place Cancel");
        add("message.pasterdream.蓝图放置失败", "Blue Print Place Failed");
        add("message.pasterdream.材料不足", "Material Not Enough");
        add("button.pasterdream.blue_print_button", "Place");

        add("effect.pasterdream.sculk_armor", "Sculk Echo");
        add("effect.pasterdream.dyedream_armor", "Dyedream Protection");
        add("effect.pasterdream.titanium_armor", "Titanium Protection");
        add("effect.pasterdream.dyedream_up", "Dyedream Tool Boost");
        add("tooltip.pasterdream.machine_light_wing.flight", "§7▪ §9Enables flight");
        add("tooltip.pasterdream.machine_light_wing.energy", "§7▪ §4Melt Dream Energy Cost: 1.2/min");
        add("tooltip.pasterdream.angel_wing.flight", "§7▪ §9Grants flight and fall damage immunity");
        add("tooltip.pasterdream.angel_wing.flavor", "§7§o-- Based on Angel Player, developed on my dorm PC");
        add("tooltip.pasterdream.forsakens_wing.flight", "§7▪ §9Grants flight and fall damage immunity");
        add("tooltip.pasterdream.forsakens_wing.immune", "§7▪ §9Grants slow and wither immunity");
        add("tooltip.pasterdream.forsakens_wing.flavor", "§7§o-- See you at the Nexus");
        add("tooltip.pasterdreammod.titanium_armor.1", "§7Set Bonus: §eTitanium Protection");
        add("tooltip.pasterdreammod.titanium_armor.2", "§7▪ §9Gain 2 Absorption hearts every 30s");
        add("tooltip.pasterdreammod.sculk_armor.1", "§7Set Bonus: §2Sculk Echo");
        add("tooltip.pasterdreammod.sculk_armor.2", "§7▪ §9Max Health +4");
        add("tooltip.pasterdreammod.sculk_armor.3", "§7▪ §9Enhanced when below Y=0");
        add("tooltip.pasterdreammod.sculk_armor.4", "§7▪ §9Immune to Sculk's Darkness");
        add("tooltip.pasterdreammod.sculk_armor.5", "§7▪ §9Gain Speed and Resistance when hit by Darkness");
        add("tooltip.pasterdreammod.sculk_armor.6", "§7▪ §9Immunity to Blindness");
        add("tooltip.pasterdreammod.sculk_armor.7", "§7▪ §9Wardens will not attack you");
        add("tooltip.pasterdreammod.dyedream_hammer", "§7▪ §9Area Mining 3×3×3");
        add("tooltip.pasterdreammod.molten_gold_sword.1", "Inlaid: §7None");
        add("tooltip.pasterdreammod.molten_gold_sword.2", "§7▪ §9Sets target on fire for 3 seconds");
        add("tooltip.pasterdreammod.molten_gold_tool.1", "Inlaid: §7None");
        add("tooltip.pasterdreammod.molten_gold_tool.2", "§7▪ §9Right-click to consume hunger for Haste");
        add("tooltip.pasterdreammod.molten_gold_tool.3", "§7▪ §9Cooldown: 10s");
        add("tooltip.pasterdreammod.hellfire_sword.1", "Inlaid: §7None");
        add("tooltip.pasterdreammod.hellfire_sword.2", "§7▪ §9Ignites target for 4 seconds");
        add("tooltip.pasterdreammod.hellfire_sword.3", "§7▪ §9If already burning, adds 2 seconds (stackable)");
        add("tooltip.pasterdreammod.hellfire_tool.1", "Inlaid: §7None");
        add("tooltip.pasterdreammod.hellfire_tool.2", "§7▪ §9Right-click to consume hunger for Haste");
        add("tooltip.pasterdreammod.hellfire_tool.3", "§7▪ §9Cooldown: 8s");
        add("tooltip.pasterdreammod.inferno_sword.1", "Inlaid: §bNether Star");
        add("tooltip.pasterdreammod.inferno_sword.2", "Combat Art: §6Bone-melting Slash");
        add("tooltip.pasterdreammod.inferno_sword.3", "§7▪ §9Converts damage type to Lava");
        add("tooltip.pasterdreammod.inferno_sword.4", "§7▪ §9Deals 2+ATK+FireSeconds*0.6 bonus damage");
        add("tooltip.pasterdreammod.inferno_sword.5", "§7▪ §9If fire >10s, reduces target speed by 30%");
        add("tooltip.pasterdreammod.inferno_sword.6", "§7▪ §9Clears target's fire effect");
        add("tooltip.pasterdreammod.inferno_sword.7", "§7▪ §9Cooldown: 10s");
        add("tooltip.pasterdreammod.inferno_sword.8", "§7Passive:");
        add("tooltip.pasterdreammod.inferno_sword.9", "§7▪ §9Ignites 4s; if burning, adds 3s");
        add("tooltip.pasterdreammod.melt_dream_tool.1", "While held");
        add("tooltip.pasterdreammod.melt_dream_tool.2", "§7▪ §bMelt-Repair: 0.01E/1 Durability");
        add("tooltip.pasterdreammod.dyedream_armor.1", "§7Set Bonus: §dDyedream Protection");
        add("tooltip.pasterdreammod.dyedream_armor.2", "§7▪ §9Max Health +4");
        add("tooltip.pasterdreammod.dyedream_armor.3", "§7▪ §9Gain 4 Absorption hearts every 30s");
        add("tooltip.pasterdreammod.dyedream_armor.4", "§7▪ §9Enhances wielded Dyedream tools");
        add("tooltip.pasterdreammod.melt_dream_armor.1", "§7▪ §9Max Health +2");
        add("tooltip.pasterdreammod.melt_dream_armor.2", "§7▪ §9Melt Dream Energy +0.2/min, Max +5");
        add("tooltip.pasterdreammod.melt_dream_armor.3", "§7▪ §bMelt-Repair: 0.01E/1 Durability");
        add("tooltip.pasterdreammod.melt_dream_armor.4", "§7Set Bonus: §dMelt Dream Protection");
        add("tooltip.pasterdreammod.melt_dream_armor.5", "§7▪ §bMelt-Repair cost reduced by 50%");
        add("tooltip.pasterdreammod.melt_dream_armor.6", "§7▪ §9Gain Regeneration II");
        add("tooltip.pasterdreammod.melt_dream_armor.7", "§7▪ §9Gain Dyedream tool enhancement");
        add("tooltip.pasterdreammod.melt_dream_armor.8", "§7▪ §9San Aura +1.2 when Melt Dream Energy is above 50%, otherwise +0.6");
        add("tooltip.pasterdreammod.sharp_melt_dream_sword.1", "Inlaid: §dMelt Dream Crystal Fragment");
        add("tooltip.pasterdreammod.sharp_melt_dream_sword.2", "Combat Art: §bSora-giri");
        add("tooltip.pasterdreammod.sharp_melt_dream_sword.3", "§7▪ §9Slash and launch enemies upward, deals 2+ATK*1.5 bonus damage");
        add("tooltip.pasterdreammod.sharp_melt_dream_sword.4", "§7▪ §9Cooldown: 5s");
        add("tooltip.pasterdreammod.sharp_melt_dream_sword.5", "§7▪ §9Cooldown reduced to 3s while Dyedream Protection is active");
        add("tooltip.pasterdreammod.tide_sword.1", "Inlaid: §7None");
        add("tooltip.pasterdreammod.tide_sword.2", "§7▪ §9Grants Water Breathing while held");
        add("tooltip.pasterdreammod.beihai_ruo_tide_sword.1", "Inlaid: §3Heart of the Sea");
        add("tooltip.pasterdreammod.beihai_ruo_tide_sword.2", "Combat Art: §3Surging Current");
        add("tooltip.pasterdreammod.beihai_ruo_tide_sword.3", "§7▪ §9Dash forward in water, 80% damage reduction during dash");
        add("tooltip.pasterdreammod.beihai_ruo_tide_sword.4", "§7▪ §9Triggers on-hit when target is in water");
        add("tooltip.pasterdreammod.beihai_ruo_tide_sword.5", "§7▪ §9Deals 3+ATK*1.2 bonus damage");
        add("tooltip.pasterdreammod.beihai_ruo_tide_sword.6", "§7▪ §9Cooldown: 2.5s");
        add("tooltip.pasterdreammod.beihai_ruo_tide_sword.7", "§7Passive:");
        add("tooltip.pasterdreammod.beihai_ruo_tide_sword.8", "§7▪ §9Grants Water Breathing while held");
        add("tooltip.pasterdreammod.kusanagi.1", "Souls: §7%d/%d");
        add("tooltip.pasterdreammod.kusanagi.2", "§7▪ §9Applies Poison on hit");
        add("tooltip.pasterdreammod.kusanagi.evolve", "The weapon has grown to a new stage");
        add("tooltip.pasterdreammod.murakumo_kusanagi.1", "Inlaid: §7%d Souls");
        add("tooltip.pasterdreammod.murakumo_kusanagi.2", "Combat Art: §bExtreme Sharpness");
        add("tooltip.pasterdreammod.murakumo_kusanagi.3", "§7▪ §9Triggers on next attack after activation");
        add("tooltip.pasterdreammod.murakumo_kusanagi.4", "§7▪ §9Deals 7+Sharpness*BaseATK/2 bonus damage");
        add("tooltip.pasterdreammod.murakumo_kusanagi.5", "§7▪ §9Cooldown: 4s");
        add("tooltip.pasterdreammod.murakumo_kusanagi.6", "§7Passive:");
        add("tooltip.pasterdreammod.murakumo_kusanagi.7", "§7▪ §9Applies longer Poison on hit");
        add("tooltip.pasterdreammod.desert_sword.1", "Inlaid: §7None");
        add("tooltip.pasterdreammod.desert_sword.2", "§7▪ §9Move Speed -15% and Resistance +20% while held");
        add("tooltip.pasterdreammod.chenjingmen_desert_sword.1", "Inlaid: §eLonely Family Letter");
        add("tooltip.pasterdreammod.chenjingmen_desert_sword.2", "Combat Art: §eDesperate Counterattack");
        add("tooltip.pasterdreammod.chenjingmen_desert_sword.3", "§7▪ §9Grants brief Absorption when skill is activated");
        add("tooltip.pasterdreammod.chenjingmen_desert_sword.4", "§7▪ §9Deals 5+(LostHP%*2+1)*ATK bonus damage on hit");
        add("tooltip.pasterdreammod.chenjingmen_desert_sword.5", "§7▪ §9Cooldown: 10s");
        add("tooltip.pasterdreammod.chenjingmen_desert_sword.6", "Passive:");
        add("tooltip.pasterdreammod.chenjingmen_desert_sword.7", "§7▪ §9Move Speed -15% and Resistance +20% while held");
        add("tooltip.pasterdreammod.san_value", "SAN: ");
        add("tooltip.pasterdreammod.melt_dream_energy", "Melt Dream Energy: ");
        add("tooltip.pasterdreammod.shadow_erosion_tool.1","§7▪ §9The digging speed will increase as the brightness decreases.");
        add("tooltip.pasterdreammod.shadow_erosion_tool.2","§7▪ §9Provides a mining speed boost for shadow-type, deepslate-type, and sculk-type blocks");

        add("tooltip.pasterdream.developerNameList", "§dDeveloper Name List");
        add("tooltip.pasterdream.dyedreamCreak", "§dDyedream Creak");
        add("tooltip.pasterdream.dyedreamWorld", "§dDyedream World");
        add("tooltip.pasterdream.pinkSlime", "§dPink Slime");
        add("tooltip.pasterdream.whiteCorolla", "§dWhite Corolla");
        add("tooltip.pasterdream.paleBoneNeedle", "§dPale Bone Needle");
        add("tooltip.pasterdream.dreamFertilizer", "§dDream Fertilizer");
        add("tooltip.pasterdream.dreamAccumulator", "§dDream Accumulator");

        add("tooltip.pasterdream.theLurkersInTheShadow", "§8The Lurkers In The Shadow");
        add("tooltip.pasterdream.infestedChurch", "§8Infested Church");
        add("tooltip.pasterdream.depositionShadow", "§8Deposition Shadow");
        add("tooltip.pasterdream.lampShadowTravelogue1", "§8Lamp Shadow Travelogue 1");
        add("tooltip.pasterdream.shadowDungeon", "§8Shadow Dungeon");
        add("tooltip.pasterdream.scare", "§8Scare");

        add("tooltip.pasterdream.notHaveWingsBirdHaveSpreadWingsDreamToo", "§aNot Have Wings Bird Have Spread Wings Dream Too");

        add("biome.pasterdream.dyedream_plains","Dyedream Plains");
        add("biome.pasterdream.dyedream_mushroom_mountains","Dyedream Mushroom Mountains");
        add("biome.pasterdream.dyedream_snowy_plains","Dyedream Snowy Plains");
        add("biome.pasterdream.dyedream_frozen_ocean","Dyedream Frozen Ocean");
        add("biome.pasterdream.dyedream_ocean","Dyedream Ocean");
        add("biome.pasterdream.shadow_nylium_wastes","Shadow Nylium Wastes");
        add("biome.pasterdream.shadow_forest","Shadow Forest");
        add("biome.pasterdream.shadow_ruins","Shadow Ruins");
        add("biome.pasterdream.shadow_ocean","Shadow Ocean");
        add("biome.pasterdream.wind_moor_archipelago","Wind Moor Archipelago");
        add("biome.pasterdream.misty_dream_cloud_layer","Misty Dream Cloud Layer");
        add("biome.pasterdream.aaroncos_arena","Aaroncos Arena");

        add("recipe.pasterdream.copy", "Copy");
        add("recipe.pasterdream.research", "Research");

        add("book.pasterdream.title.使用说明", "Use Guide");

        add("book.pasterdream.title.花园解密:迷梦冶梦莲", "Garden Decode: Misty Dreaming Lotus");
        add("book.pasterdream.title.花园解密:凛冽雪绒花", "Garden Decode: Piercing Edel Weiss");
        add("book.pasterdream.title.花园解密:九尾狐", "Garden Decode: Golden Fox");
        add("book.pasterdream.title.染梦游记 其一", "Dyedream Travelogue 1");
        add("book.pasterdream.title.染梦游记 其二", "Dyedream Travelogue 2");
        add("book.pasterdream.title.染梦游记 其三", "Dyedream Travelogue 3");
        add("book.pasterdream.title.染梦教堂 其一", "Dream Church 1");
        add("book.pasterdream.title.染梦教堂 其二", "Dream Church 2");
        add("book.pasterdream.title.染梦教堂 其三", "Dream Church 3");
        add("book.pasterdream.title.染梦水晶球", "Dyedream Crystal Ball");
        add("book.pasterdream.title.祈愿树", "Dream Wishing Tree");
        add("book.pasterdream.title.温暖的“寒风”", "Warm Cold Wind");
        add("book.pasterdream.title.粉顶蘑菇屋", "Pink Agalic House");
        add("book.pasterdream.title.融梦涌泉井", "Melt Dream Liquid Well");
        add("book.pasterdream.title.浮空岛日记", "Floating Island Diary");
        add("book.pasterdream.title.落叶归根 裂荚归冠", "Falling Leaves Return To Their Roots, Cracked Pods Return To Their Crowns");
        add("book.pasterdream.title.梦境漂泊", "Dream Floating");
        add("book.pasterdream.title.气泡生态球", "Big Bubble");
        add("book.pasterdream.title.染梦实验室", "Dream Laboratory");
        add("book.pasterdream.title.来往于梦", "Interacting With Dream");
        add("book.pasterdream.title.染梦世界树", "Dyedream World Tree");
        add("book.pasterdream.title.代达罗斯之翼与浮空岛", "Wings Of DaiDaLuoSi And Floating Island");
        add("book.pasterdream.title.星河果冻和浮空岛", "Galaxy Jelly and Floating Island");

        add("book.pasterdream.title.探求秘辛", "Desert Cottage");

        add("book.pasterdream.title.研究笔记:黑金属", "Research Notes: Black Metal");
        add("book.pasterdream.title.灯影游记 其二", "Lamp Shadow Travelogue 2");
        add("book.pasterdream.title.黑暗之地", "Shadow Place");
        add("book.pasterdream.title.阴影小木屋", "Shadow Fungus House");
        add("book.pasterdream.title.阴影地下工作室", "Shadow Underground Workroom");
        add("book.pasterdream.title.-被阴影浸染的字迹潦草的笔记-", "-Shadow Dyed Sloppy Handwriting Notes-");
        add("book.pasterdream.title.关于黑暗之手的随笔", "Regarding Shadow Hands Essay");
        add("book.pasterdream.title.与黑色双手的决战和败北", "Decisive Battle With Shadow Hands And Fail");
        add("book.pasterdream.title.侵染教堂-黑面", "Infested Church - Black Face");
        add("book.pasterdream.title.侵染教堂-亮面", "Infested Church - Bright Face");
        add("book.pasterdream.title.暮影之笼", "Twilight Cage");
        add("book.pasterdream.title.暗影图书馆", "Shadow Library");
        add("book.pasterdream.title.沉淀阴影", "Deposition of Shadow");
        add("book.pasterdream.title.灯影游记 其一", "Lamp Shadow Travelogue 1");
        add("book.pasterdream.title.暗影地牢", "Shadow Dungeon");
        add("book.pasterdream.title.暗影地牢入口", "Shadow Dungeon Entrance");
        add("book.pasterdream.title.欺诈", "Deception");
        add("book.pasterdream.title.交易", "Bargain");
        add("book.pasterdream.title.破碎", "Shattered");

        add("book.pasterdream.title.破风的骑士", "Wind Break Knight");
        add("book.pasterdream.title.清晨的新风", "Morning New Wind");

        add("book.pasterdream.title.精铸工坊", "Weapon Workshop");
        add("book.pasterdream.title.暗影高炉", "Shadow Blast Furnace");

        // Dew Tooltip
        add("tooltip.pasterdream.drink_effect", "§7When consumed:");
        add("tooltip.pasterdream.red_dew.effect", "§7▪ §9Instant Health I");
        add("tooltip.pasterdream.blue_dew.effect", "§7▪ §9Water Breathing I (1:00)");

        add("tooltip.pasterdream.melt_dream_coin", "§7Right-click with 10+ to combine into a coin pile");
        add("tooltip.pasterdream.melt_dream_coin_pile", "§7Right-click to split into 10 Melt Dream Coins");
        add("tooltip.pasterdream.melt_dream_coin.value", "§7▪ §6Face Value : ");

        add("tooltip.pasterdream.pliers.1", "§7▪ §9Can harvest crops and plants");
        add("tooltip.pasterdream.pliers.2", "§7▪ §9Sneak + right-click to directly harvest Pasterdream plants");

        // Broken Note
        add(ModItems.BROKEN_NOTE.get(), "Broken Note");
        add(ModItems.UNKNOWN_NOTE.get(), "Unknown Note");
        add(ModItems.PALE_BONENEEDLE.get(), "Pale Boneneedle");
        add(ModItems.ROOTS_PALE_BONENEEDLE.get(), "Roots Pale Boneneedle");
        add("tooltip.pasterdream.broken_note", "§7We still need to learn more...");
        add("tooltip.pasterdream.unknown_note.1", "Unknown Content");
        add("tooltip.pasterdream.unknown_note.2", "§7Needs to be analyzed at a §eResearch Table");

        // Pale Boneneedle
        add("tooltip.pasterdream.pale_boneneedle", "§7Awakens you from dreams...");
        add("tooltip.pasterdream.pale_boneneedle.use", "§7Left-click on other players | Right-click on yourself");
        add("tooltip.pasterdream.roots_pale_boneneedle.1", "§7Awakens you from dreams... and returns to the marked location");
        add("tooltip.pasterdream.roots_pale_boneneedle.2", "§7Sneak + right-click to set a waypoint");
        add("tooltip.pasterdream.roots_pale_boneneedle.3", "§7If no waypoint is set, returns to spawn by default");
        add("tooltip.pasterdream.roots_pale_boneneedle.4", "§7Left-click on other players | Right-click on yourself");
        add("message.pasterdream.roots_pale_boneneedle.waypoint_set", "Waypoint recorded");

        //Other Items
        add(ModItems.SAND_OF_TIME.get(), "Time of Sand");
        add("message.pasterdream.sand_of_time.cooldown", "This Sand of Time is resting after a hard day of work and needs %s seconds to recover");
        add("tooltip.pasterdream.sand_of_time.1", "§7Right click to change through the world's day and night");
        add("tooltip.pasterdream.sand_of_time.2", "§7This item will go into cooldown for %s §7seconds after use");
        add("tooltip.pasterdream.sand_of_time.3", "§7§o-- The hourglass will remember the time we forgot");
        add("tooltip.pasterdream.sand_of_time.4", "%s seconds remaining");
        add("tooltip.pasterdream.sand_of_time.5", "§aReady to use");
        add(ModItems.STORAGE_BAG.get(), "Storage Bag");
        add(ModItems.LARGE_STORAGE_BAG.get(), "Large Storage Bag");
        add(ModItems.DREAM_HARP_OF_WANDERER.get(), "DreamHarp of Wanderer");
        add(ModItems.SLIVER_BELL.get(),"Sliver Bell");
        add("tooltip.pasterdream.sliver_bell", "§7§O --Sheyann Meow!");
        add("message.pasterdream.dream_harp_of_wanderer.no_energy", "Not enough Melt Dream Energy");
        add("tooltip.pasterdream.dream_harp_of_wanderer.line1", "§7Plays to provide effects to players within 10 blocks (1:00):");
        add("tooltip.pasterdream.dream_harp_of_wanderer.line2", "§7▪ §9Max HP+4, restore 4 HP, Speed+10%");
        add("tooltip.pasterdream.dream_harp_of_wanderer.line3", "§7▪ §9+4.8 SAN Aura, immediately restore 5 SAN");
        add("tooltip.pasterdream.dream_harp_of_wanderer.line4", "§7▪ §9Cooldown: 30s");
        add("tooltip.pasterdream.dream_harp_of_wanderer.line5", "§7▪ §4Melt Dream Energy Cost: 3");
        add("tooltip.pasterdream.storage_bag.tooltip", "§7A regular bag with 9 storage slots");
        add("tooltip.pasterdream.large_storage_bag.tooltip1", "§7Use Ender powers to expand the bag's storage, giving it 25 slots");
        add("tooltip.pasterdream.large_storage_bag.tooltip2", "§7Shift right-click can capture some kinds of creature, and shift right-click again to release it (this feature: %s)");
        add("tooltip.pasterdream.large_storage_bag.catch_enabled", "§aActive");
        add("tooltip.pasterdream.large_storage_bag.catch_disabled", "§cInactive");
        add("tooltip.pasterdream.large_storage_bag.tooltip3", "§7Contains creature: %s");


        // Custom Rarity
        add("rarity.pasterdream.common", "Common");
        add("rarity.pasterdream.excellent", "Excellent");
        add("rarity.pasterdream.superior", "Superior");
        add("rarity.pasterdream.master", "Master");
        add("rarity.pasterdream.ancient", "Ancient");
        add("rarity.pasterdream.epic", "Epic");
        add("rarity.pasterdream.legendary", "Legendary");
        add("rarity.pasterdream.miracle", "Miracle");
        add("tooltip.pasterdream.quality", "Quality: %s %s");

        // Charms
        add(ModItems.EMBRYO_CHARM.get(), "Embryo Charm");
        add(ModItems.GOLD_CHARM.get(), "Gold Charm");
        add("tooltip.pasterdream.gold_charm.piglin", "§7▪ §9Makes piglins neutral");
        add(ModItems.ENDEYE_CHARM.get(), "Endeye Charm");
        add("tooltip.pasterdream.endeye_charm.enderman", "§7▪ §9Makes endermen neutral");
        add(ModItems.SEA_CHARM.get(), "Sea Charm");
        add(ModItems.CARAPAX_CHARM.get(), "Carapax Charm");
        add(ModItems.WORLDTREE_SEEDPOD.get(), "Worldtree Seedpod");
        add(ModItems.GARLAND.get(), "Garland");
        add(ModItems.REST_ICON.get(), "Rest Icon");
        add(ModItems.CHEER_UP_ICON.get(), "Cheer Up Icon");
        add(ModItems.LETHARGY_ICON.get(), "Lethargy Icon");
        add(ModItems.TRANCE_ICON.get(), "Trance Icon");
        add(ModItems.INSANE_ICON.get(), "Madness Icon");
        add(ModItems.CHAOS_ICON.get(), "Chaos Icon");
        add(ModItems.DREAM_WISH_ICON.get(), "Dream Wish Icon");
        add(ModItems.COOK_ICON.get(), "Cuisine Icon");
        add(ModItems.SHADOW_SILENCE_ICON.get(), "Shadow Silence Icon");
        add(ModItems.BIND_ICON.get(), "Bind Icon");
        add(ModItems.BLESSING_OF_CECILIA.get(), "Blessing of Cecilia");
        add("tooltip.pasterdream.blessing_of_cecilia.effect0", "§7▪ §9Passive: Forced 20% DMG Reduction");
        add("tooltip.pasterdream.blessing_of_cecilia.effect1", "§7 Upon receiving fatal damage:");
        add("tooltip.pasterdream.blessing_of_cecilia.effect2", "§7▪ §9Grants 5s of damage immunity");
        add("tooltip.pasterdream.blessing_of_cecilia.effect3", "§7▪ §9And grants 10s of Resistance V, Regeneration X, Absorption V and Speed II");
        add("tooltip.pasterdream.blessing_of_cecilia.lore", "§7§o--I will protect you, forever and ever...");
        add(ModItems.FADED_BLESSING_OF_CECILIA.get(), "Faded Blessing of Cecilia");
        add("tooltip.pasterdream.faded_blessing_of_cecilia.effect0", "§7▪ §9Passive: Resistance I");
        add("tooltip.pasterdream.faded_blessing_of_cecilia.hint", "§7Perhaps we should submerge it in Melt Dream Spring");
        add(ModItems.PALE_BONE_NEEDLE_TALISMAN.get(), "Pale Bone Needle Talisman");
        add("tooltip.pasterdream.pale_bone_needle_talisman", "§7Negates a fatal blow in dream dimensions and teleports you to your respawn point");
        add(ModItems.FOURLEAF_CLOVER_CURIO.get(), "Fourleaf Clover");
        add("tooltip.pasterdream.fourleaf_clover_curio.flavor", "§7§oWhich leaf represents luck?");
        add(ModItems.SNOW_VOW_HEAD.get(), "Snow Vow");
        add("tooltip.pasterdream.snow_vow_head.effect.area", "§7▪ §9Grants nearby players within 7 blocks an effect");
        add(ModItems.QYM_BUTTERFLY_STAR_HAIRPIN.get(), "QYM's Butterfly Star Hairpin");
        add("tooltip.pasterdream.qym_butterfly_star_hairpin.effect", "§7▪ §9All mobs will not actively attack you");
        add("tooltip.pasterdream.qym_butterfly_star_hairpin.lore", "§7§oIf you aim for the stars in the night sky, even if you get lost, you are still among them.");
        add(ModItems.HIYORI_BUTTERFLY_HAIRPIN.get(), "Hiyori Butterfly Hairpin");
        add("tooltip.pasterdream.hiyori_butterfly_hairpin.lore", "§7§o-- Hamidashi, therefore Creative");
        add(ModItems.ALLKINDS_RING.get(), "Allkinds Ring");
        add("tooltip.pasterdream.allkinds_ring.flavor", "§7§oGather the power of all things, forge this ring");
        add("tooltip.pasterdream.allkinds_ring.lore", "§7§o-- Szuperlina Deonne");
        add("tooltip.pasterdream.allkinds_ring.skill_cd", "§7▪ §9-5% Skill Cooldown");
        add("tooltip.pasterdream.allkinds_ring.skill_dmg", "§7▪ §9+5% Skill Damage");
        add("tooltip.pasterdream.allkinds_ring.blink_cd", "§7▪ §9-5% Blink Cooldown");
        add(ModItems.LIGHT_BUTTERFLY_CURIO.get(), "Light Butterfly Curio");
        add("tooltip.pasterdream.light_butterfly_curio.effect", "§7▪ §9Grants Night Vision in low light");
        add("tooltip.pasterdream.light_butterfly_curio.energy", "§7▪ §4Melt Dream Energy cost: 0.24/min");
        add(ModItems.BRIGHT_BUTTERFLY_CURIO.get(), "Bright Butterfly Curio");
        add("tooltip.pasterdream.bright_butterfly_curio.effect.gamma", "§7▪ §9Grants Night Vision effect");
        add("tooltip.pasterdream.bright_butterfly_curio.effect.brightness", "§7▪ §9Greatly boosts screen brightness");
        add("tooltip.pasterdream.bright_butterfly_curio.effect.darkness_immune", "§7▪ §9Immune to Darkness and Blindness");
        add(ModItems.ICE_SHADOW_CURIO.get(), "Ice Shadow Curio");
        add("tooltip.pasterdream.ice_shadow_curio.0", "§7▪ §9Ice Shadow Hammer's skill spawns 2 extra Shaking Crystals");
        add("tooltip.pasterdream.ice_shadow_curio.1", "§7▪ §9Crystal spacing is affected by cast distance");
        add(ModItems.BOBOJI_CURIO.get(), "Boboji's Gorgeous Feather");
        add("tooltip.pasterdream.boboji_curio.stat.speed", "§7▪ §9+5% Speed");
        add("tooltip.pasterdream.boboji_curio.stat.range", "§7▪ §9+0.1 Blink Range");
        add("tooltip.pasterdream.boboji_curio.stat.cd", "§7▪ §9-0.2 Blink Cooldown");
        add("tooltip.pasterdream.boboji_curio.stat.consume", "§7▪ §9-0.4 Blink Consume");
        add("tooltip.pasterdream.boboji_curio.effect.elytra", "§7▪ §9No longer extends Blink cooldown when wearing Elytra");
        add("tooltip.pasterdream.boboji_curio.effect.evasion", "§7▪ §9Evade one instance of damage within 0.25s after Blink");
        add("tooltip.pasterdream.boboji_curio.effect.share", "§7▪ §9Evasion time doubles and is shared with nearby allies");
        add("tooltip.pasterdream.boboji_curio.effect.flavor", "§7▪ §eAdds sound effects and particle trails to Blink");
        add("tooltip.pasterdream.boboji_curio.lore", "§7§oDedicated to the one who once accompanied me");
        add("tooltip.pasterdream.boboji_curio.dedication", "§7§o-- A yellow-green budgerigar named Boboji");
        add(ModItems.SHADOW_BREATH.get(), "Shadow Breath");
        add("tooltip.pasterdream.shadow_breath.0", "§7▪ §9When SAN ≥ 50%:");
        add("tooltip.pasterdream.shadow_breath.1", "§9  Every +10% SAN");
        add("tooltip.pasterdream.shadow_breath.2", "§9  Attack Damage +4% (max +20%)");
        add("tooltip.pasterdream.shadow_breath.3", "§9  Magic Damage +4% (max +20%)");
        add("tooltip.pasterdream.shadow_breath.4", "§7▪ §9When SAN < 50%:");
        add("tooltip.pasterdream.shadow_breath.5", "§9  (Gain Regeneration I when SAN ≤ 40%)");
        add("tooltip.pasterdream.shadow_breath.6", "§9  Every -10% SAN");
        add("tooltip.pasterdream.shadow_breath.7", "§9  Regeneration level +1 (max III)");
        add("tooltip.pasterdream.shadow_breath.8", "§9  Armor +2 (max +10)");
        add("tooltip.pasterdream.shadow_breath.hint", "§7Hold [§rShift§7] to view current bonuses");
        add("tooltip.pasterdream.shadow_breath.current.header", "§7▪ §9Current bonuses:");
        add("tooltip.pasterdream.shadow_breath.current.attack", "§9  Attack Damage +%s%%, Magic Damage +%s%%");
        add("tooltip.pasterdream.shadow_breath.current.armor", "§9  Armor +%s");
        add("tooltip.pasterdream.shadow_breath.current.regen", "§9  Regeneration %s");
        add("tooltip.pasterdream.shadow_breath.current.none", "§9  No current bonuses");
        add("tooltip.pasterdream.shadow_breath.current.disabled", "§9  SAN system disabled, curio is inactive");
        add(ModItems.MOSS_PHANTOM_MEMBRANE.get(), "Moss Phantom Membrane");
        add("tooltip.pasterdream.moss_phantom_membrane.effect", "§7▪ §9Slowly repairs the equipped Elytra in bright light");
        add(ModItems.LIGHT_MOSS_PHANTOM_MEMBRANE.get(), "Light Moss Phantom Membrane");
        add("tooltip.pasterdream.light_moss_phantom_membrane.effect", "§7▪ §9Repairs the equipped Elytra");
        add("tooltip.pasterdream.light_moss_phantom_membrane.effect.dark", "§7▪ §9Repairs slower in darkness");

        add(ModItems.KAICHU_OMAMORI.get(), "Kaichu Omamori");
        add(ModItems.BROOCH_OF_WHITE_ORCHID.get(), "Brooch of White Orchid");
        add("tooltip.pasterdream.brooch_of_white_orchid.effect", "§7▪ §9No longer affected by environmental SAN reduction");
        add("tooltip.pasterdream.brooch_of_white_orchid.effect2", "§7▪ §9White Sword Rain +50% damage to all creatures");
        add("tooltip.pasterdream.brooch_of_white_orchid.effect3", "§7▪ §9White Sword Rain ignores invulnerability frames");
        add("message.pasterdream.brooch_of_white_orchid.rejected", "This ornament rejects you");
        add("tooltip.pasterdream.brooch_of_white_orchid.flavor", "§o§7 -- I fade into formlessness, now enjoy the glory you deserve");
        add("tooltip.pasterdream.kaichu_omamori.effect.foxfire", "§7▪ §9Generates a Foxfire field in a 12×12 area");
        add("tooltip.pasterdream.kaichu_omamori.effect.vulnerable", "§7▪ §9Non-player creatures take 20% more damage and get Slowness V effect");
        add("tooltip.pasterdream.kaichu_omamori.effect.regen", "§7▪ §9Grants the player Regeneration");
        add("tooltip.pasterdream.kaichu_omamori.effect.duration", "§7▪ §9Duration: %s seconds");
        add("tooltip.pasterdream.kaichu_omamori.effect.energy_cost", "§f▪ §4Melt Dream Energy -5");
        add("tooltip.pasterdream.kaichu_omamori.effect.cooldown", "§7▪ §9Cooldown: %s seconds");
        add("tooltip.pasterdream.kaichu_omamori.flavor", "§o§7 -- Alirea custom item");
        add("key.pasterdream.kaichu_omamori", "Activate Kaichu Omamori");
        add("tooltip.pasterdream.kaichu_omamori.equip", "§7When equip:");
        add("tooltip.pasterdream.kaichu_omamori.luck", "§7▪ §9+5Luck");
        add("tooltip.pasterdream.kaichu_omamori.hotkey", "§7▪ §9Press §e%s §9to release Foxfire field");

        add("tooltip.pasterdream.snow_vow_head.effect.bonus", "§7▪ §9Luck +3  Immune to burning and freezing");
        add("effect.pasterdream.snow_vow", "Snow Vow");
        add("tooltip.pasterdream.fourleaf_clover_curio.effect.health", "§7▪ §9+1 Max Health");
        add("tooltip.pasterdream.fourleaf_clover_curio.effect.luck", "§7▪ §9+6 Luck");
        add("tooltip.pasterdream.worldtree_seedpod.condition", "§7▪ §9In Dyedream World, open sky, Y>160, daytime, standing on Worldtree Leaves");
        add("tooltip.pasterdream.worldtree_seedpod.hunger", "§7▪ §4Constantly increases exhaustion");
        add("tooltip.pasterdream.worldtree_seedpod.energy", "§7▪ §9Melt Dream Energy +360/h");
        add("tooltip.pasterdream.worldtree_seedpod.flavor", "§7§o-- Leaves fall to root, pods burst to crown");
        //Necklaces
        add(ModItems.EMBRYO_NECKLACE.get(), "Embryo Necklace");
        add(ModItems.RABBIT_FOOT_NECKLACE.get(), "Rabbit Foot Necklace");
        add("tooltip.pasterdream.rabbit_foot_necklace.effect", "§7▪ §9Grants Jump Boost II & +1 Luck");
        add(ModItems.FEATHER_NECKLACE.get(), "Feather Necklace");
        add("tooltip.pasterdream.feather_necklace.effect1", "§7▪ §9-0.05 Blink Consumption");
        add("tooltip.pasterdream.feather_necklace.effect2", "§7▪ §9+0.2 Blink Range");
        add(ModItems.HEALTH_NECKLACE.get(), "Health Necklace");
        add("tooltip.pasterdream.health_necklace.effect1", "§7▪ §9+2 Max Health");
        add("tooltip.pasterdream.health_necklace.effect2", "§7▪ §9Grants Regeneration I");
        add(ModItems.FIRE_NECKLACE.get(), "Fire Necklace");
        add("tooltip.pasterdream.fire_necklace.effect1", "§7▪ §9Leaves a trail of fire while walking");
        add("tooltip.pasterdream.fire_necklace.effect2", "§7▪ §9Grants Haste II while on fire");
        add("tooltip.pasterdream.fire_necklace.effect3", "§7▪ §9Grants Fire Resistance");
        add(ModItems.CROSS_NECKLACE.get(), "Cross Necklace");
        add("tooltip.pasterdream.cross_necklace.effect", "§7▪ §9Extends Blink evasion duration to 0.5s");
        //Rings
        add(ModItems.EMBRYO_RING.get(), "Embryo Ring");
        add(ModItems.RED_DEW_RING.get(), "Red Dew Ring");
        add(ModItems.STRIKE_RING.get(), "Strike Ring");
        add("tooltip.pasterdream.red_dew_ring.effect", "§7▪ §9+%sMax Health");
        add("tooltip.pasterdream.strike_ring.effect", "§7▪ §9+%s Attack Damage");

        //Belts
        add(ModItems.EMBRYO_BELT.get(), "Embryo Belt");
        add(ModItems.NATURE_BELT.get(), "Nature Belt");
        add(ModItems.TRAVELER_BELT.get(), "Traveler Belt");
        add("tooltip.pasterdream.traveler_belt.effect", "§7▪ §9-0.5 Blink Consumption");
        add(ModItems.DREAM_TRAVELER_BELT.get(), "Dream Traveler Belt");
        add("tooltip.pasterdream.dream_traveler_belt.effect1", "§7▪ §9+3% Movement Speed");
        add("tooltip.pasterdream.dream_traveler_belt.effect2", "§7▪ §9-0.1 Blink Cooldown");
        //Embryo
        add("tooltip.pasterdream.introduction.tooltip", "§f▪ §7Introduction");
        add("tooltip.pasterdream.embryo.tooltip1", "§7A curios embryo made from titanium and soul essence");
        add("tooltip.pasterdream.embryo.tooltip2", "§7Using titanium's life affinity and the spiritual connection of soul essence");
        add("tooltip.pasterdream.embryo.tooltip3", "§7Allow you to strengthen yourself through accessories made from physical materials");
        //curios tooltips
        add("tooltip.pasterdream.lv", "§7Level: §bLv.%s");
        add("tooltip.pasterdream.only_one.tooltip", "§7Only one of this curios can be equipped in the curios slot.");
        add("tooltip.pasterdream.hold_shift", "§7Hold [§rShift§7] to view the description");

        // Blink Skill
        add("key.pasterdream.blink", "Blink");
        add("key.categories.pasterdream", "PasterDream");
        add("effect.pasterdream.cheer_up", "Cheer Up");
        add("effect.pasterdream.lethargy", "Lethargy");
        add("effect.pasterdream.trance", "Trance");
        add("effect.pasterdream.confusion", "Confusion");
        add("effect.pasterdream.insane", "Insanity");
        add("effect.pasterdream.evasion", "Evasion");
        add("effect.pasterdream.blink_cooldown", "Blink Cooldown");
        add("effect.pasterdream.dyedream_perfume", "Dyedream Perfume");
        add("effect.pasterdream.goldenrod_tea", "Goldenrod Tea");
        add("effect.pasterdream.cook", "Cook");
        add("effect.pasterdream.dream_wish", "Dream Wish");
        add("effect.pasterdream.cecilia_blessing", "Blessing of Cecilia");
        add("effect.pasterdream.rest", "Rest");
        add("effect.pasterdream.dream_harp_of_wanderer", "DreamHarp of Wanderer");
        add("effect.pasterdream.guard", "Guard");
        add("effect.pasterdream.holy_grail", "Holy Grail");
        add("effect.pasterdream.flare_up", "Flare up");
        add("effect.pasterdream.conflict_mark", "Conflict Mark");
        add("attribute.pasterdream.blink_cd", "Blink Cooldown");
        add("attribute.pasterdream.blink_consume", "Blink Hunger Cost");
        add("attribute.pasterdream.blink_range", "Blink Distance");
        add("attribute.pasterdream.san_variability", "Sanity Variability");
        add("attribute.pasterdream.skill_cooldown_rate", "Skill Cooldown Rate");
        add("attribute.pasterdream.skill_damage_rate", "Skill Damage Rate");
        add("attribute.pasterdream.max_san_extra", "Max Sanity Bonus");
        add("attribute.pasterdream.max_melt_dream_energy_extra", "Max Melt Dream Energy Bonus");
        add("attribute.pasterdream.melt_dream_variability", "Melt Dream Aura");
        add("subtitles.pasterdream.evasion", "Evasion");

        // Low San effect commands
        add("command.pasterdream.lowsan.overlay", "Low San screen overlay");
        add("command.pasterdream.lowsan.overlay.set", "Low San screen overlay set to %s");
        add("command.pasterdream.lowsan.overlay.get", "%s: %s");
        add("command.pasterdream.lowsan.jitter", "Low San screen jitter");
        add("command.pasterdream.lowsan.jitter.set", "Low San screen jitter set to %s");
        add("command.pasterdream.lowsan.jitter.get", "%s: %s");
        add("command.pasterdream.lowsan.sound", "Low San insanity sound");
        add("command.pasterdream.lowsan.sound.set", "Low San insanity sound set to %s");
        add("command.pasterdream.lowsan.sound.get", "%s: %s");
        add("item.pasterdream.star_wish_rod", "Star's Wish Rod");
        add("tooltip.pasterdream.star_wish_rod.desc", "§7▪ §9Grants an extra Super deep-sea treasure when fishing");
        add("tooltip.pasterdream.star_wish_rod", "§7§O --Fish as numerous as stars");
        add("command.pasterdream.lowsan.enabled", "enabled");
        add("command.pasterdream.lowsan.disabled", "disabled");
        add("gamerule.category.pasterdream", "PasterDream");
        add("gamerule.shadowDifficulty", "World Shadow Difficulty");
        add("gamerule.playerShadowDifficulty", "Default Player Shadow Difficulty");
        // Shadow difficulty command
        add("command.pasterdream.shadowDifficulty.tier.0", "Very Easy");
        add("command.pasterdream.shadowDifficulty.tier.1", "Easy");
        add("command.pasterdream.shadowDifficulty.tier.2", "Normal");
        add("command.pasterdream.shadowDifficulty.tier.3", "Hard");
        add("command.pasterdream.shadowDifficulty.set.playerDefault", "Player shadow difficulty set to %s");
        add("command.pasterdream.shadowDifficulty.set.world", "World shadow difficulty set to %s");
        add("command.pasterdream.shadowDifficulty.set.forPlayer", "Set %s's shadow difficulty to %s");
        add("command.pasterdream.shadowDifficulty.source.personal", "personal override");
        add("command.pasterdream.shadowDifficulty.source.playerDefault", "player default");
        add("command.pasterdream.shadowDifficulty.get.playerEffective", "Player %s: %s [%s]");
        add("command.pasterdream.shadowDifficulty.get.gameruleSummary", "shadowDifficulty (world): %s | playerShadowDifficulty (player default): %s");

        // Terra Blade
        add(ModItems.TERRA_BLADE.get(), "Terra Blade");
        add("tooltip.pasterdream.terra_blade.skill_name", "Combat Art: §aTerra Sword Skill");
        add("tooltip.pasterdream.terra_blade.desc1", "§7▪ §9Shift+Right-click to toggle sword wave mode");
        add("tooltip.pasterdream.terra_blade.desc2", "§7▪ §9When active, each swing releases a sword wave");
        add("tooltip.pasterdream.terra_blade.desc3", "§7▪ §9Each wave deals §c2+ATK §9damage");
        add("tooltip.pasterdream.terra_blade.desc4", "§7▪ §9Each wave costs §c0.5 §9Melt Dream Energy");
        add("tooltip.pasterdream.terra_blade.desc5", "§7▪ §9Auto-deactivates when energy is depleted");
        add("tooltip.pasterdream.terra_blade.skill_on", "§aSword Wave: ON");
        add("tooltip.pasterdream.terra_blade.skill_off", "§7Sword Wave: OFF");
        add("tooltip.pasterdream.terra_blade.no_energy", "Not enough Melt Dream Energy, sword wave deactivated");
        add("entity.pasterdream.terrasword_wave", "Terra Sword Wave");
        add("entity.pasterdream.shaking_crystal", "Shaking Crystal");

        // White Sword
        add(ModItems.WHITE_SWORD.get(), "White Sword");
        add("tooltip.pasterdream.white_sword.skill_name", "Combat Art: §fWhite Sword Rain");
        add("tooltip.pasterdream.white_sword.desc1", "§7▪ §9Summons sword rain from behind and above, firing toward the crosshair");
        add("tooltip.pasterdream.white_sword.desc2", "§7▪ §9Deals §dmagic damage§9, ignores armor and binds the target");
        add("tooltip.pasterdream.white_sword.desc3", "§7▪ §9Each hit deals §cATK*0.01 §9damage");
        add("tooltip.pasterdream.white_sword.desc4", "§7▪ §9Silences shadow mobs for 10s on hit");
        add("tooltip.pasterdream.white_sword.desc5", "§7▪ §9Cooldown: §c2.0s");
        add("tooltip.pasterdream.white_sword.desc6", "§7▪ §9Melt Dream Energy Cost: §c1.5");
        add("tooltip.pasterdream.white_sword.desc7", "§7▪ §9§c+50% §9damage to shadow mobs");
        add("tooltip.pasterdream.white_sword.skill_passive_name", "Passive: §6Light Chaser");
        add("tooltip.pasterdream.white_sword.desc8", "§7▪ §9§c50% §9chance on melee attack to fire homing sword rain at the target");
        add("message.pasterdream.white_sword.no_energy", "Not enough Melt Dream Energy");
        add("message.pasterdream.white_sword.no_talent", "You haven't chosen <Light> to use this sword");
        add("entity.pasterdream.white_sword_rain_projectile", "White Sword Rain");

        // Shadow Sword
        add(ModItems.SHADOW_SWORD.get(), "Shadow Sword");
        add("tooltip.pasterdream.shadow_sword.skill_name", "Skill: §5Nightmare Slash");
        add("tooltip.pasterdream.shadow_sword.skill_desc1", "§7▪ §9Right-click consumes §c5 SAN §9(or §c5 HP §9if insufficient, non-lethal)");
        add("tooltip.pasterdream.shadow_sword.skill_desc2", "§7▪ §9Next attack becomes §dMagic Damage§9, dealing §cATK×(2.5-SAN%)§9");
        add("tooltip.pasterdream.shadow_sword.skill_desc3", "§7▪ §9Cooldown: §c2.0s");
        add("tooltip.pasterdream.shadow_sword.passive_name", "Passive: §5Shadow");
        add("tooltip.pasterdream.shadow_sword.desc1", "§7▪ §9The lower the wielder's sanity, the higher the damage");
        add("tooltip.pasterdream.shadow_sword.desc2", "§7▪ §9Max §c+75% ATK§9, §c+50% §9attack speed");
        add("tooltip.pasterdream.shadow_sword.desc3", "§7▪ §4Swings consume your own life when sanity is depleted");
        add("tooltip.pasterdream.shadow_sword.flavor", "§7§o——The nightmare sharpened into this blade");
        add("message.pasterdream.shadow_sword.san_disabled", "§cThe Shadow Sword rejects you");
        add("message.pasterdream.shadow_sword.no_talent", "You haven't chosen <Shadow> to use this sword");

        // Ice Shadow Hammer
        add("tooltip.pasterdream.ice_shadow_hammer.skill_name", "Battle Art: §3Eno's Earthshaker");
        add("tooltip.pasterdream.ice_shadow_hammer.0", "§7▪ §9Launches you upward and summons a §3Shaking Crystal");
        add("tooltip.pasterdream.ice_shadow_hammer.1", "§7▪ §9Crystal continuously §dConfuses §9and §bFreezes §9nearby enemies");
        add("tooltip.pasterdream.ice_shadow_hammer.2", "§7▪ §9Deals 3 waves of damage: §cATK×0.4§9, §cATK×0.6§9, §cATK×1.0§9(explosion)");
        add("tooltip.pasterdream.ice_shadow_hammer.3", "§7▪ §9Grants §eAbsorption I §9to the caster after explosion");
        add("tooltip.pasterdream.ice_shadow_hammer.4", "§7Right-click on ground to unleash");
        add("tooltip.pasterdream.ice_shadow_hammer.5", "§7▪ §9Cooldown: §c4s");
        add("tooltip.pasterdream.ice_shadow_hammer.cost", "§7▪ §4Melt Dream Energy Cost: §c0.5");
        add("message.pasterdream.ice_shadow_hammer.no_energy", "§cNot enough Melt Dream Energy");

        // Seal of the Fallen
        add(ModItems.SEAL_OF_THE_CORRUPTED.get(), "Seal of the Corrupted");
        add("tooltip.pasterdream.seal_of_the_corrupted.effect1", "§7▪ §9Immune to negative effects from low sanity");
        add("tooltip.pasterdream.seal_of_the_corrupted.effect2", "§7▪ §9Shadow mobs become neutral to you");
        add("tooltip.pasterdream.seal_of_the_corrupted.effect3", "§7▪ §9Shadow mobs spawned by low sanity fight for you");
        add("tooltip.pasterdream.seal_of_the_corrupted.flavor", "§7§o——I will become your shadow and share your suffering");
        add("message.pasterdream.seal_of_the_corrupted.rejected", "This ornament rejects you");

        // Terra Floating Island
        add(ModItems.TERRA_FLOATING_ISLAND.get(), "Terra Floating Island");
        add("tooltip.pasterdream.terra_floating_island.desc1", "§7▪ §9Terra Sword wave cost reduced to §c0.4");
        add("tooltip.pasterdream.terra_floating_island.desc2", "§7▪ §9Terra Sword wave damage +30%");
        add("tooltip.pasterdream.terra_floating_island.desc3", "§7▪ §9Terra Sword wave ignores invulnerability frames");

        // Strawberry Heart
        add(ModItems.STRAWBERRY_HEART.get(), "Strawberry Heart");
        add("tooltip.pasterdream.strawberry_heart.desc1", "§7▪ §9Right-click to perform §4Costs 1 Melt Dream Energy");
        add("tooltip.pasterdream.strawberry_heart.desc2", "§7▪ §9Heals nearby players by 4 HP and grants brief Regen, Strength & Speed");
        add("tooltip.pasterdream.strawberry_heart.desc3", "§7▪ §7Cooldown: 12s §7| §6Radius: 8 blocks");
        add("tooltip.pasterdream.strawberry_heart.desc4", "§7▪ §dInstantly restores 2 SAN §dand grants 8s 3.6 SAN aura, immune to lethargy during this period");
        add("tooltip.pasterdream.strawberry_heart.no_energy", "Not enough Melt Dream Energy");

        // Memory Gem
        add(ModItems.MEMORY_GEM.get(), "Memory Gem");

        // Memento Item
        add(ModItems.EMPTY_VESSEL.get(), "Empty Vessel");
        add("tooltip.pasterdream.empty_vessel.1", "§7▪ §9Grants Luck +10 for 3 minutes");
        add("tooltip.pasterdream.empty_vessel.2", "§7▪ §999%+20%SAN §c1%-100%SAN");
        add("tooltip.pasterdream.empty_vessel.3", "§7Exclusive memento of GQ2529");
        add("tooltip.pasterdream.empty_vessel.4", "§6PasterDream Developer");

        // Rebirth Dream Crystal
        add(ModItems.REBIRTH_DREAM_CRYSTAL.get(), "Rebirth Dream Crystal");
        add("tooltip.pasterdream.rebirth_dream_crystal.1", "§7\"The old dream has woken, let us dream anew.\"");
        add("tooltip.pasterdream.rebirth_dream_crystal.2", "§7\"Welcome back, Dreamseeker.\"");
        add("tooltip.pasterdream.rebirth_dream_crystal.3", "§7▪ §9+12 Sanity Aura");
        add("tooltip.pasterdream.rebirth_dream_crystal.4", "§7▪ §9Gains 12s Evasion every 10s");
        add("tooltip.pasterdream.rebirth_dream_crystal.5", "§7Exclusive memento of ShiLiuYinYu.");
        add("tooltip.pasterdream.rebirth_dream_crystal.6", "§7Shift right-click to destroy itself and release the soul within.");
        add("tooltip.pasterdream.rebirth_dream_crystal.7", "§6PasterDream: Reborn Developer");

        add(ModItems.SOUL_GEM_OF_AKIZUKI_AYANE.get(), "Soul Gem of Akizuki Ayane");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.1", "§7\"I bestow upon you my legacy magic, to walk beside you and witness the dreams you see\"");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.2", "§7An experimental creation by a great mage to protect her shattered soul. Once her soul was restored, this materialized soul lost its meaning.");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.3", "§7▪ §9Restores 1.5 Melt Dream Energy per second");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.4", "§7▪ §9+40% Skill Damage");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.5", "§7▪ §9+50% Magic Damage");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.6", "§f▪ §4Melt Dream Energy consumption doubled");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.7", "§f▪ §4Damage taken is doubled when Melt Dream Energy is below 30");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.8", "§7Exclusive memento of Ayane.");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.9", "§7Shift right-click in main hand to fully restore Melt Dream Energy, disable its consumption and gain +60% Skill Damage and Magic Damage for 2 minutes (4.5 min cooldown)");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.10", "§6PasterDream: Reborn Copywriter");
        add("tooltip.pasterdream.soul_gem_of_akizuki_ayane.cooldown", "§cThe soul gem's power is still cooling down");

        add(ModItems.MAGNIFYING_GLASS_OF_SHERRY.get(), "Detective's Magnifying Glass");
        add("tooltip.pasterdream.magnifying_glass_of_sherry.1", "§7\"My super intelligence tells me it's time to use my super strength!\"");
        add("tooltip.pasterdream.magnifying_glass_of_sherry.2", "§7Actually, Bei_xu didn't know what relic to make at the time, but he really liked Tachibana Sherry, so this is what happened.");
        add("tooltip.pasterdream.magnifying_glass_of_sherry.3", "§7▪ §9+50% Mining Speed");
        add("tooltip.pasterdream.magnifying_glass_of_sherry.4", "§7▪ §9+100% Melee Damage");
        add("tooltip.pasterdream.magnifying_glass_of_sherry.5", "§7▪ §9+200% Damage when bare-handed");
        add("tooltip.pasterdream.magnifying_glass_of_sherry.6", "§f▪ §4Weapon skills cannot be released");
        add("message.pasterdream.skill_locked", "§cSkill locked");
        add("tooltip.pasterdream.magnifying_glass_of_sherry.7", "§7Exclusive memento of bei_xu.");
        add("tooltip.pasterdream.magnifying_glass_of_sherry.8", "§6PasterDream: Reborn Copywriter");

        // Qym Gear
        add(ModItems.QYM_CAT_EARS.get(), "Qym's Cat Ears");
        add("tooltip.pasterdream.qym_cat_ears.san", "§7▪ §9SAN always at maximum");
        add("tooltip.pasterdream.qym_cat_ears.set_bonus", "§7Set Bonus: §dSlumbering Rainbow Maiden");
        add("tooltip.pasterdream.qym_cat_ears.damage_reduce", "§f▪ §9Gain 80% Damage Reduction");
        add("tooltip.pasterdream.qym_cat_ears.magic_damage", "§f▪ §9Attacks deal bonus magic damage (5% target current HP)");
        add("tooltip.pasterdream.qym_cat_ears.dream_evasion", "§f▪ §9Immune to all damage in dream dimensions");
        add(ModItems.QYM_WIND_SHIRT.get(), "Qym's Wind Shirt");
        add("tooltip.pasterdream.qym_wind_shirt.flight", "§7▪ §9Gain flight ability");
        add("tooltip.pasterdream.qym_wind_shirt.fall_immune", "§7▪ §9Immune to fall damage");
        add(ModItems.QYM_SWAYING_SKIRT.get(), "Qym's Swaying Skirt");
        add("tooltip.pasterdream.qym_swaying_skirt.energy", "§7▪ §9Melt Dream Energy always at maximum");
        add(ModItems.QYM_CLOUD_BOOTS.get(), "Qym's Cloud Boots");
        add("tooltip.pasterdream.qym_cloud_boots.blink", "§7▪ §9Blink has no cooldown");
        add("tooltip.pasterdream.qym_cloud_boots.skill_cd", "§7▪ §9Skills have no cooldown");

        add("message.pasterdream.lost_sword_tomb.lack_strength", "Your strength is not enough to pull out this sword");
        add("tooltip.pasterdream.lost_sword_tomb", "§7If you can't pull it out, just use it with the stone!");

        // Enchantments
        add("enchantment.pasterdream.swift_strike", "Swift Strike");
        add("enchantment.pasterdream.shelter", "Shelter");
        add("enchantment.pasterdream.swift_strike.desc", "Increases attack speed.");
        add("enchantment.pasterdream.shelter.desc", "Reduce the damage taken.");

        // Counter Ring
        add(ModItems.COUNTER_RING.get(), "Counter Ring");
        add(ModItems.MELT_DREAM_ENERGY_RING.get(), "Melt Dream Energy Ring");
        add("tooltip.pasterdream.counter_ring.effect1", "§7▪ §9On successful dodge: gain Counterattack I (0:10)");
        add("tooltip.pasterdream.counter_ring.effect2", "§7▪ §9Next attack: Attack +3, Skill DMG ×+50%, then buff ends");
        add("tooltip.pasterdream.melt_dream_energy_ring.effect1", "§7▪ §9Melt Dream Energy +0.3/min");
        add("effect.pasterdream.counter_attack", "Counterattack");
        add("effect.pasterdream.memento", "Dream Seeker's Prayer");

        // War Flag
        add(ModItems.WAR_FLAG.get(), "War Flag");
        add("tooltip.pasterdream.war_flag.effect1", "§7▪ §9 Give a battle intention effect when killing enemies. Each kill increases it by 1 level, up to a maximum of 3 levels.");
        add("tooltip.pasterdream.war_flag.effect2", "§7▪ §9Increase attack damage and increase healing speed while the effect lasts.");
        add("tooltip.pasterdream.war_flag.effect3", "§7▪ §9This effect can be shared with nearby players.");
        add("tooltip.pasterdream.war_flag.description", "§7§O --The shadows flowing on this flag seem to be telling stories of a distant past...");
        add("effect.pasterdream.war_flag", "Battle Intention");
        add("effect.pasterdream.bind", "Bind");
        add("effect.pasterdream.shadow_silence", "Shadow Silence");
        add("effect.pasterdream.restrainmove_block", "Movement Restraint");
        add("effect.pasterdream.oppression", "Oppression");
        add("effect.pasterdream.shadow_spyon", "Shadow Gaze");
        add("message.pasterdream.shadow_intrude.start_1", "§5You feel a chill, your vision veiled by a layer of black fog");
        add("message.pasterdream.shadow_intrude.start_2", "§5The nearby shadows begin to stir");
        add("message.pasterdream.shadow_intrude.end", "§5The shadows fall silent...");
        add("message.pasterdream.shadow_intrude.end_1", "§7You feel puzzled that these shadow creatures have come here");
        add("message.pasterdream.shadow_intrude.end_2", "§7Perhaps we should go find Nameless again...");

        // Shadow choice screen
        add("gui.pasterdream.shadow_select_end.label_choose", "Follow your first instinct to make your choice");
        add("gui.pasterdream.shadow_select_end.label_outcome", "This will lead your exploration to a different future");

        // Nameless dialogue: first dialogue
        add("dialogue.pasterdream.nameless.first_1", "???: Hmm...?");
        add("dialogue.pasterdream.nameless.first_2", "???: ...How did you get here?");
        add("dialogue.pasterdream.nameless.first_3", "???: It has been a long time since anyone came here...");
        add("dialogue.pasterdream.nameless.first_4", "???: You may be curious about my past, but... I have been through too much, and I would rather not recall it.");
        add("dialogue.pasterdream.nameless.first_5", "???: As for my name... too much time has passed, and I have lost my former \"self.\" Just call me \"Nameless.\"");
        add("dialogue.pasterdream.nameless.first_6", "Nameless: It is dangerous here. Every time you enter this shadow dungeon, it changes — more threats or more treasure... I cannot tell.");
        add("dialogue.pasterdream.nameless.first_7", "Nameless: Please do not risk your life exploring here. Leave as soon as you can.");
        add("dialogue.pasterdream.nameless.first_8", "Nameless: Why am I still here? Because I cannot escape, and I no longer want to try. Go back while you still can.");
        add("dialogue.pasterdream.nameless.first_9", "Nameless: ......");
        add("dialogue.pasterdream.nameless.first_10", "Nameless: Why are you still here...");
        add("dialogue.pasterdream.nameless.first_11", "Nameless: Staying here will only erode your mind... I cannot let you go deeper. This thirst for the unknown will only make it easier for the shadows to control you... It is for your own good.");
        add("dialogue.pasterdream.nameless.first_12", "Nameless: I cannot let you stay here any longer... Please leave.");

        // Nameless dialogue: second dialogue
        add("dialogue.pasterdream.nameless.second_1", "Nameless: You have seen them appear in your world?");
        add("dialogue.pasterdream.nameless.second_2", "Nameless: Why would they appear in a world beyond the lamp shadow...");
        add("dialogue.pasterdream.nameless.second_3", "Nameless: ...Sorry, I drifted off. You asked why I stopped you?");
        add("dialogue.pasterdream.nameless.second_4", "Nameless: As far as I have seen, few people survive facing those monsters. You seem different from the ordinary.");
        add("dialogue.pasterdream.nameless.second_5", "Nameless: I can feel your extraordinary mental strength... You came into this shadow of your own will, did you not? In that case, you truly have the 'aptitude' to wield that power.");
        add("dialogue.pasterdream.nameless.second_6", "Nameless: Indeed, you do have the 'aptitude.'");
        add("dialogue.pasterdream.nameless.second_7", "Nameless: Beneath this floor lies the grave of someone long departed. I exist here to keep a promise — the promise to 'guard his grave.'");
        add("dialogue.pasterdream.nameless.second_8", "Nameless: If it is you, perhaps you can earn his approval.");
        add("dialogue.pasterdream.nameless.second_9", "Nameless: If you insist on fighting... then touch the door below once more. If you are truly acknowledged, the door will open.");
        add("dialogue.pasterdream.nameless.second_10", "Nameless: Go touch the Twilight Long Bed. Do not worry about the outcome — just follow your first instinct.");

        // Nameless dialogue: choice aftermath (light)
        add("dialogue.pasterdream.nameless.light_1", "Nameless: It seems you have chosen to embrace the light, to become a ray of light here.");
        add("dialogue.pasterdream.nameless.light_2", "Nameless: As one of the 'lamp,' you now have the strength to stop Him, to defeat Him.");
        add("dialogue.pasterdream.nameless.light_3", "Nameless: Then set out and search — search for the eyes of Aaroncos.");
        add("dialogue.pasterdream.nameless.light_4", "Nameless: May fortune favor you, and may you remain a lamp that lights the dark even after this battle.");

        // Nameless dialogue: choice aftermath (shadow)
        add("dialogue.pasterdream.nameless.shadow_1", "Nameless: It seems you have chosen to melt into the darkness, to become a wisp of shadow here.");
        add("dialogue.pasterdream.nameless.shadow_2", "Nameless: As one of the 'shadow,' you now seem qualified to accept Him, to merge with Him.");
        add("dialogue.pasterdream.nameless.shadow_3", "Nameless: Then set out and search — search for the eyes of Aaroncos.");
        add("dialogue.pasterdream.nameless.shadow_4", "Nameless: May fortune favor you, and may you keep your human clarity even after this battle.");

        // Nameless dialogue: waiting state
        add("dialogue.pasterdream.nameless.wait", "Nameless: ......");
        add("item.pasterdream.shadow_magicball_spawn_egg", "Shadow Magicball Spawn Egg");
        add("item.pasterdream.shadow_tune_totem_spawn_egg", "Shadow Tune Totem Spawn Egg");
        add("item.pasterdream.aaroncos_left_hand_spawn_egg", "Aaroncos's Left Hand Spawn Egg");
        add("item.pasterdream.aaroncos_right_hand_spawn_egg", "Aaroncos's Right Hand Spawn Egg");
        add("entity.pasterdream.aaroncos_left_hand", "Aaroncos's Left Hand");
        add("entity.pasterdream.aaroncos_right_hand", "Aaroncos's Right Hand");
        add("block.pasterdream.aaroncos_eye", "Aaroncos Eye");
        add("block.pasterdream.aaroncos_hand_chest", "Aaroncos Hand Chest");
        add("block.pasterdream.aaroncos_arena_portals", "Aaroncos Arena Portals");
        add("item.pasterdream.aaroncos_arena_create", "Aaroncos Arena Create");
        add("tooltip.pasterdream.aaroncos_arena_create", "§4Creative Mode Item");
        add("item.pasterdream.aaroncos_music_disc", "Aaroncos Music Disc");
        add("item.pasterdream.aaroncos_music_disc.desc", "§dPasterDream§7 - Aaroncos's Touch");
        add("item.pasterdream.pure_horror", "Pure Horror");
        add("entity.pasterdream.shadow_magicball", "Shadow Magicball");
        add("entity.pasterdream.shadow_tune_totem", "Shadow Tune Totem");
        add("message.pasterdream.shadow_tune_totem.charging", "The Shadow Tune Totem is charging energy");
        add("message.pasterdream.shadow_tune_totem.about_to_explode", "The Shadow Tune Totem is about to explode");
        add("block.pasterdream.shadow_vortex", "Shadow Vortex");
        add("block.pasterdream.shadow_hand_trap", "Shadow Hand Trap");
        add("block.pasterdream.shadow_brazier", "Shadow Brazier");
        add("block.pasterdream.shadow_blast_furnace_core", "Shadow Blast Furnace Core");
        add("tooltip.pasterdream.shadow_hand_trap", "§7--Why would you dig this up?");
        add("tooltip.pasterdream.shadow_blast_furnace_core.1", "When the multi-block structure is complete");
        add("tooltip.pasterdream.shadow_blast_furnace_core.2", "Right-click this core with the corresponding blueprint to build");
        add("message.pasterdream.shadow_brazier.need_candle", "You need a Shadow Candle to light the brazier");
        add("message.pasterdream.shadow_brazier.lit", "The brazier ignites, but brings no light...");
        add("message.pasterdream.shadow_brazier.shadow_spread", "Shadow spreads from all around");
        add("message.pasterdream.shadow_brazier.extinguished", "The brazier burns out and shatters on the ground");
        add("message.pasterdream.shadow_brazier.key_dropped", "A key drops from the crevice of the brazier");

        // Broken Portal Messages
        add("message.pasterdream.broken_portal.too_low", "Shadow Dungeon Portal's Y-axis height is too low, Shadow Dungeon Structure Can't Spawn.");
        add("message.pasterdream.broken_portal.creative_repaired", "Creative mode: Core repaired unconditionally");
        add("message.pasterdream.broken_portal.need_materials", "Hold §eBlack Metal Ingot §fand §eShadow Light §fin both hands to repair the core");
        add("message.pasterdream.broken_portal.repaired", "Core repaired");
        add("message.pasterdream.broken_portal.lack_knowledge", "You don't yet understand how to repair this core");

        // Calais Spice Bottle
        add(ModItems.CALAIS_SPICE_BOTTLE.get(), "Calais Spice Bottle");
        add("tooltip.pasterdream.calais_spice_bottle.effect1", "§7▪ §9+40% eating speed");
        add("tooltip.pasterdream.calais_spice_bottle.effect2", "§7▪ §9Gain Calais Spice X buff when equipped. Each attack consumes a stack, and after chopping until it disappears, you need to eat to restore it. Eating accumulates stacks (1 stack per 3 hunger, up to level X).");
        add("tooltip.pasterdream.calais_spice_bottle.effect3", "§7▪ §9Each stack consumed grants a random effect");
        add("effect.pasterdream.calais_spice_bottle", "Calais Spice");

        add(ModItems.GHOST_FACE.get(), "Ghost Face");
        add("tooltip.pasterdream.ghost_face.effect.1", "§7▪ §9When using ranged weapons, fire an extra shot, with a 20% chance to fire another one.");
        add("tooltip.pasterdream.ghost_face.effect.2", "§7▪ §9Ranged weapon projectiles ignore invincibility frames.");
        add("tooltip.pasterdream.ghost_face.cooldown", "§7▪ §9Cooldown: %s seconds");

        // Advancements - Story
        add("advancements.pasterdream.story.root.title", "PasterDream");
        add("advancements.pasterdream.story.root.description", "Begins with a wish from the heart");
        add("advancements.pasterdream.story.pure_and_flawless.title", "Pure and Flawless");
        add("advancements.pasterdream.story.pure_and_flawless.description", "Obtain a Pale Snow Lotus");
        add("advancements.pasterdream.story.use_pale_boneneedle.title", "Ouch, it's hurt!");
        add("advancements.pasterdream.story.use_pale_boneneedle.description", "Use the Pale Boneneedle to Wake You up from the Dream");
        add("advancements.pasterdream.story.human_falls_out_of_dream.title", "Human Falls out of Dream");
        add("advancements.pasterdream.story.human_falls_out_of_dream.description", "You can’t tell for a moment whether your pain comes from falling or from being pierced by a bone needle.");
        add("advancements.pasterdream.story.dyedream_crack.title", "Dyedream Crack");
        add("advancements.pasterdream.story.dyedream_crack.description", "Wake up in the next dream, the crack will resonate with your dream.");
        add("advancements.pasterdream.story.dyedream_world.title", "Gothenburg Lullaby");
        add("advancements.pasterdream.story.dyedream_world.description", "Visit the Dyedream World");
        add("advancements.pasterdream.story.dream_fertilizer.title", "Inedible Jelly");
        add("advancements.pasterdream.story.dream_fertilizer.description", "Craft and use Dream Fertilizer to spread on the dream land");
        add("advancements.pasterdream.story.dyedream_dust.title", "Holding the Dream in Your Palm!");
        add("advancements.pasterdream.story.dyedream_dust.description", "Seek its traces from this world");
        add("advancements.pasterdream.story.melt_dream_crystal_fragment.title", "Melted in Dream");
        add("advancements.pasterdream.story.melt_dream_crystal_fragment.description", "Collect a Melt Dream Crystal Fragment");
        add("advancements.pasterdream.story.glass_jar_of_dream_juice.title", "Where Lies the Sweet Dream");
        add("advancements.pasterdream.story.glass_jar_of_dream_juice.description", "Craft and drink Dream Juice, then journey to the Dyedream World in your sleep");
        add("advancements.pasterdream.story.dream_accumulator.title", "The Dust Has Settled");
        add("advancements.pasterdream.story.dream_accumulator.description", "Use the Dream Accumulator to collect Dyedream Dust");
        add("advancements.pasterdream.story.melt_dream_liquid_bucket.title", "Mixed Emotions");
        add("advancements.pasterdream.story.melt_dream_liquid_bucket.description", "Obtain a bucket of Melt Dream Spring");
        add("advancements.pasterdream.story.create_pliers.title", "太陽とレインボ一");
        add("advancements.pasterdream.story.create_pliers.description", "Make a pair of pliers");
        add("advancements.pasterdream.story.create_research_table.title", "Afternoon Tea Time");
        add("advancements.pasterdream.story.create_research_table.description", "Craft a research table to carry out research.");
        add("advancements.pasterdream.story.galaxy_jelly.title", "Star Gel");
        add("advancements.pasterdream.story.galaxy_jelly.description", "Get the Galaxy Jelly, a jelly that can be found in the deep-sea treasures and chests in the dream, and it can make you soar to the clouds.");
        add("advancements.pasterdream.story.eat_galaxy_jelly_on_high_height.title", "Space Professional Voice Actor");
        add("advancements.pasterdream.story.eat_galaxy_jelly_on_high_height.description", "Eat the Galaxy Jelly at the building height limit... Kaz finally has company now...");
        add("advancements.pasterdream.story.look_at_pink_sheep.title", "Do Block People Dream of Pink Sheep?");
        add("advancements.pasterdream.story.look_at_pink_sheep.description", "Block people only dream of block sheep, of course.");

        // Advancements - Adventure Expansion
        add("advancements.pasterdream.adventure.forgotten_sword_tomb.title", "The Forgotten Sword Tomb");
        add("advancements.pasterdream.adventure.forgotten_sword_tomb.description", "Discovered the lost sword tomb, a sword tomb hidden deep in the jungle.");
        add("advancements.pasterdream.story.get_the_lost_sword.title", "Sword of Oblivion");
        add("advancements.pasterdream.story.get_the_lost_sword.description", "Got the Sword Embryo, it seems to resonate with the power of the jungle…");
        add("advancements.pasterdream.story.lamp_shadow_root.title", "Infested Church");
        add("advancements.pasterdream.story.lamp_shadow_root.description", "Read the Dream Seeker's Notes \"Infested Church - Black Face\"");
        add("advancements.pasterdream.story.bastion_guard.title", "Bastion Guard");
        add("advancements.pasterdream.story.bastion_guard.description", "Complete the Twilight Lantern bastion guard event");
        add("advancements.pasterdream.story.enter_lamp_shadow_world.title", "Lamp Shadow World");
        add("advancements.pasterdream.story.enter_lamp_shadow_world.description", "Delve into the darkest side of the world");
        add("advancements.pasterdream.story.shadow_choice.title", "Light and Shadow");
        add("advancements.pasterdream.story.shadow_choice.description", "Make your choice between light and shadow");
        add("advancements.pasterdream.story.talent_light.title", "Faith in Light");
        add("advancements.pasterdream.story.talent_light.description", "Choose the light in the choice between light and shadow");
        add("advancements.pasterdream.story.talent_shadow.title", "Shadow Servant");
        add("advancements.pasterdream.story.talent_shadow.description", "Choose the shadow in the choice between light and shadow");
        add("advancements.pasterdream.story.defeat_aaroncos.title", "Shadow and Dust");
        add("advancements.pasterdream.story.defeat_aaroncos.description", "Defeat Aaroncos's Touch");
        add("message.pasterdream.aaroncos_arena.need_progress", "You haven't completed the prerequisite progress yet");
        add("message.pasterdream.aaroncos_arena.battle_in_progress", "A player is challenging Aaroncos, entry is not allowed for now");
        add("advancements.pasterdream.story.shadow_intrude_complete.title", "Shadow Intrusion");
        add("advancements.pasterdream.story.shadow_intrude_complete.description", "Complete the shadow intrusion event");
        add("advancements.pasterdream.story.dig_up_a_tomb.title", "Sweet Sixteen, with the Strength of a Titan");
        add("advancements.pasterdream.story.dig_up_a_tomb.description", "You... what exactly did you dig up???");
        add("advancements.pasterdream.new_standard_sword_drawing.title", "New Standard Sword Drawing");
        add("advancements.pasterdream.new_standard_sword_drawing.description", "Because you couldn't pull out the sword, you ended up using your enemy as a tool to break stones!");
        add("advancements.pasterdream.craft_kusanagi.title", "Power of the Jungle");
        add("advancements.pasterdream.craft_kusanagi.description", "Craft Kusanagi, a sword imbued with the power of the jungle. It is hungrily seeking lost souls...");
        add("advancements.pasterdream.get_murakumo_kusanagi.title", "Lost Souls Under the Sword");
        add("advancements.pasterdream.get_murakumo_kusanagi.description", "Let Kusanagi further evolve by killing enemies.");
        add("advancements.pasterdream.adventure.find_desert_fortress.title", "Desert Heroic Spirit");
        add("advancements.pasterdream.adventure.find_desert_fortress.description", "Discover a desert fortress, where a heroic spirit who hasn't completed his aspiration. Maybe you should think about killing the nearby enemies first…");
        add("advancements.pasterdream.adventure.get_desert_sword.title", "Sword of the Heroic Spirit");
        add("advancements.pasterdream.adventure.get_desert_sword.description", "Get the Desert Sword from the hero and fulfill his last wish to let this weapon evolve.");
        add("advancements.pasterdream.adventure.get_chenjingmen_desert_sword.title", "The wish has come true");
        add("advancements.pasterdream.adventure.get_chenjingmen_desert_sword.description", "Fulfilling the heroic spirit's last wish allowed Desert Sword to evolve into ChenJingmen Desert Sword. The former hero can finally rest in peace...");

        // Advancements - Nether Expansion
        add("advancements.pasterdream.get_molten_gold_ingot.title", "Real Gold Does Not Fear Fire");
        add("advancements.pasterdream.get_molten_gold_ingot.description", "Obtain Molten Gold Ingot, smelted from a kind of hot gold ore from the Nether.");
        add("advancements.pasterdream.craft_hellfire_sword.title", "Blazing Sword");
        add("advancements.pasterdream.craft_hellfire_sword.description", "Craft a Hellfire Sword, it resonates with the drop of a terrifying undead creature.");
        add("advancements.pasterdream.craft_inferno_sword.title", "Inferno Fire");
        add("advancements.pasterdream.craft_inferno_sword.description", "Inlay a Nether Star into the Hellfire Sword to let it reach its final evolution.");

        // Advancements - Husbandry Expansion
        add("advancements.pasterdream.get_deep_sea_treasure.title", "Gift from the Sea");
        add("advancements.pasterdream.get_deep_sea_treasure.description", "Get a treasure of the ocean, coming from the deep sea and the dyedream frozen ocean.");
        add("advancements.pasterdream.get_super_deep_sea_treasure.title", "To wish upon a satellite...");
        add("advancements.pasterdream.get_super_deep_sea_treasure.description", "To get higher-level marine treasures, you should find a more powerful fishing rod for it...");
        add("advancements.pasterdream.get_blue_dew.title", "Tears of the Ocean");
        add("advancements.pasterdream.get_blue_dew.description", "Get the blue dew, it comes from that enchanting ocean treasure glowing with magic light.");
        add("advancements.pasterdream.get_blue_heart_of_the_sea.title", "Heart of Deep Blue");
        add("advancements.pasterdream.get_blue_heart_of_the_sea.description", "Once you get the Blue Heart of the Sea, you'll know which weapon needs it...");
        add("advancements.pasterdream.get_beihairuo_tide_sword.title", "God of Tides");
        add("advancements.pasterdream.get_beihairuo_tide_sword.description", "Get the BeiHairuo Tide Sword; it holds the power of surging waves and riptide...");

        // Advancements - Dyedream Treasure
        add("advancements.pasterdream.root_dyedream_treasure.title", "Dyedream Collection");
        add("advancements.pasterdream.root_dyedream_treasure.description", "What kind of treasures could there be in a pink world? ");
        add("advancements.pasterdream.get_sand_of_time.title", "Your Time is really Valuable");
        add("advancements.pasterdream.get_sand_of_time.description", "Get the Sand of Time, a sandglass that can switch day and night, but it takes a break after working...");
        add("advancements.pasterdream.get_broken_hero_sword.title", "Solar Eclipse Relic");
        add("advancements.pasterdream.get_broken_hero_sword.description", "Got the Broken Hero Sword. Wait a minute? Isn't this thing drop from a big flappy moth?");
        add("advancements.pasterdream.get_terra_sword.title", "Racing with a Turtle");
        add("advancements.pasterdream.get_terra_sword.description", "Get the Terra Blade. You should consider whether you might get crushed by a turtle falling from the sky.");
        add("advancements.pasterdream.get_boboji_curios.title", "Boboji's Dream");
        add("advancements.pasterdream.get_boboji_curios.description", "Get the Boboji Curios.");
        add("advancements.pasterdream.get_allkinds_ring.title", "Who taught you to add attribute like this?");
        add("advancements.pasterdream.get_allkinds_ring.description", "Get the Allkinds Ring, a ring that boosts a little bit of every attribute.");
        add("advancements.pasterdream.get_hiyori_butterfly_hairpin.title", "Hanging out with my Real Sister");
        add("advancements.pasterdream.get_hiyori_butterfly_hairpin.description", "Get the Hiyori Butterfly Hairpin.");
        add("advancements.pasterdream.get_snow_vow_head.title", "Permafrost Snowflake");
        add("advancements.pasterdream.get_snow_vow_head.description", "Get The Snow Vow Head.");
        add("advancements.pasterdream.get_star_wish_rod.title", "Fish are like Countless Stars in the Vast Sky");
        add("advancements.pasterdream.get_star_wish_rod.description", "Get the Star Wish Rod. Look for more tempting treasures in the sea.");
        add("advancements.pasterdream.get_blessing_of_cecilia.title", "I hate you like a block of wood");
        add("advancements.pasterdream.get_blessing_of_cecilia.description", "Getting Blessing of Cecilia. It is an even stronger life-saving curios than the Undying Totem.");
        add("advancements.pasterdream.get_light_butterfly_curio.title", "Night Butterfly");
        add("advancements.pasterdream.get_light_butterfly_curio.description", "Get the night-glow butterfly, a special butterfly that provides night vision. Maybe you should look for something to upgrade it in the deepest darkness.");
        add("advancements.pasterdream.get_qym_doll.title", "Cuteness is Justice");
        add("advancements.pasterdream.get_qym_doll.description", "Get the QYM doll, be nice to her.");
        add("advancements.pasterdream.get_terra_floating_island.title", "Why not give Terraria a try?");
        add("advancements.pasterdream.get_terra_floating_island.description", "Get the Terra Floating Island.");
        add("advancements.pasterdream.get_dream_harp_of_wanderer.title", "A Gift from the Wandering Traveler");
        add("advancements.pasterdream.get_dream_harp_of_wanderer.description", "Get the Dream Harp of the Wandering, a gift left behind by the Train-wandering Traveler.");
        add("advancements.pasterdream.get_worldtree_seedpod.title", "Fallen leaves return to their roots, split pods return to their crown");
        add("advancements.pasterdream.get_worldtree_seedpod.description", "Get the Worldtree Seedpod.");
        add("advancements.pasterdream.get_sliver_bell.title", "Sheyann Meow!");
        add("advancements.pasterdream.get_sliver_bell.description", "Get the silver bell, and it will only show its true form when you combine it with the light you found in the deep darkness.");
        add("advancements.pasterdream.get_kaichu_omamori.title", "The Golden Fox's Wish");
        add("advancements.pasterdream.get_kaichu_omamori.description", "Get the Kaichu Omamori. When you actually make the sleeping golden fox's dream come true, she will give you this special keepsake.");

        // Patchouli book

        // Categories

        // Meltdream Energy

        // Sanity

        // Evasion (Blink)

        // Fishing

        // Foretold Dream

        // Titanium

        // Molten Gold

        // Soul Dust

        // Sculk Upgrade

        // Inferno Sword

        // Kusanagi

        // Tide Sword

        // Desert Sword

        // Deep Sea Treasure

        // Thermal Dagger

        // Fortune Jelly

        // Dyedream Sky Island

        // Lost Sword Tomb

        // Fisherman's Hut

        // Desert Ruins

        // The Dream Foretold - New Buff Effects









        // Story





        // Entities
        add(ModEntities.PINK_CHICKEN.get(), "Pink Chicken");
        add(ModItems.PINK_CHICKEN_SPAWN_EGG.get(), "Pink Chicken Spawn Egg");
        add(ModEntities.PINK_SLIME.get(), "Pink Slime");
        add(ModItems.PINK_SLIME_SPAWN_EGG.get(), "Pink Slime Spawn Egg");
        add(ModEntities.GOLDEN_FOX.get(), "Golden Fox");
        add(ModItems.GOLDEN_FOX_SPAWN_EGG.get(), "Golden Fox Spawn Egg");
        add(ModEntities.NAMELESS.get(), "Nameless");
        add(ModItems.NAMELESS_SPAWN_EGG.get(), "Nameless Spawn Egg");
        add(ModEntities.FIREFLY.get(), "Firefly");
        add(ModItems.FIREFLY_SPAWN_EGG.get(), "Firefly Spawn Egg");
        add(ModEntities.WIND_KNIGHT.get(), "Wind Knight");
        add(ModItems.WIND_KNIGHT_SPAWN_EGG.get(), "Wind Knight Spawn Egg");
        add(ModEntities.THUNDERCLOUD.get(), "Thundercloud");
        add(ModItems.THUNDERCLOUD_SPAWN_EGG.get(), "Thundercloud Spawn Egg");
        add(ModEntities.HIGHVOLTAGE_THUNDERCLOUD.get(), "Highvoltage Thundercloud");
        add(ModItems.HIGHVOLTAGE_THUNDERCLOUD_SPAWN_EGG.get(), "Highvoltage Thundercloud Spawn Egg");
        add(ModEntities.BONE_WING.get(), "Bone Wing");
        add(ModItems.BONE_WING_SPAWN_EGG.get(), "Bone Wing Spawn Egg");
        add(ModEntities.ASH_BONE_WING.get(), "Ash Bone Wing");
        add(ModItems.ASH_BONE_WING_SPAWN_EGG.get(), "Ash Bone Wing Spawn Egg");
        add(ModEntities.JELLYFISH.get(), "Jellyfish");
        add(ModItems.JELLYFISH_SPAWN_EGG.get(), "Jellyfish Spawn Egg");
        add(ModEntities.SMALL_STONE_SPIRIT.get(), "Small Stone Spirit");
        add(ModItems.SMALL_STONE_SPIRIT_SPAWN_EGG.get(), "Small Stone Spirit Spawn Egg");
        add(ModEntities.LIGHTNING_PROJECTILE.get(), "Thundercloud");
        add(ModEntities.MELT_DREAM_CRYSTAL_ENTITY.get(), "Melt Dream Crystal Entity");
        add(ModEntities.FOX_FIRE.get(), "Fox Fire");
        add(ModEntities.SHADOW_GOLEM.get(), "Shadow Golem");
        add(ModItems.SHADOW_GOLEM_SPAWN_EGG.get(), "Shadow Golem Spawn Egg");
        add(ModEntities.TERRORBEAK.get(), "Terrorbeak");
        add(ModItems.TERRORBEAK_SPAWN_EGG.get(), "Terrorbeak Spawn Egg");
        add(ModEntities.CRAZY_TERRORBEAK.get(), "Crazy Terrorbeak");
        add(ModItems.CRAZY_TERRORBEAK_SPAWN_EGG.get(), "Crazy Terrorbeak Spawn Egg");
        add(ModEntities.WEAKENESS_TERRORBEAK.get(), "Weakness Terrorbeak");
        add(ModItems.WEAKENESS_TERRORBEAK_SPAWN_EGG.get(), "Weakness Terrorbeak Spawn Egg");
        add(ModEntities.SHADOW_HAND.get(), "Shadow Hand");
        add(ModItems.SHADOW_HAND_SPAWN_EGG.get(), "Shadow Hand Spawn Egg");
        add(ModEntities.SHADOW_GHOST.get(), "Shadow Ghost");
        add(ModItems.SHADOW_GHOST_SPAWN_EGG.get(), "Shadow Ghost Spawn Egg");
        add(ModEntities.SHADOW_SQUEAL_GHOST.get(), "Squeal Shadow Ghost");
        add(ModItems.SHADOW_SQUEAL_GHOST_SPAWN_EGG.get(), "Squeal Shadow Ghost Spawn Egg");
        add(ModEntities.WAILING_SHADOW_GHOST.get(), "Wailing Shadow Ghost");
        add(ModItems.WAILING_SHADOW_GHOST_SPAWN_EGG.get(), "Wailing Shadow Ghost Spawn Egg");
        add(ModEntities.FRIENDLY_SHADOW_GHOST.get(), "Friendly Shadow Ghost");
        add(ModItems.FRIENDLY_SHADOW_GHOST_SPAWN_EGG.get(), "Friendly Shadow Ghost Spawn Egg");
        add(ModEntities.BLACK_BEETLE.get(), "Black Beetle");
        add(ModItems.BLACK_BEETLE_SPAWN_EGG.get(), "Black Beetle Spawn Egg");
        add(ModEntities.BLACK_BEETLE_MOTHER.get(), "Black Beetle Mother");
        add(ModItems.BLACK_BEETLE_MOTHER_SPAWN_EGG.get(), "Black Beetle Mother Spawn Egg");
        add(ModItems.BLACK_BEETLE_CARAPACE.get(), "Black Beetle Carapace");
        add(ModItems.BLACK_BEETLE_VOCALCORD.get(), "Black Beetle Vocalcord");
        add("tooltip.pasterdream.black_beetle_vocalcord", "§7You can still faintly feel its vibration");
        add("message.pasterdream.black_beetle.easter_egg_1", "Come on, Xiao Liang, show us what you got!");
        add("message.pasterdream.black_beetle.easter_egg_2", "Grass, walk, ignore! ጿ ኈ ቼ ዽ ጿ");
        add("entity.pasterdream.golden_fox.vanish", "The golden fox vanished after fulfilling your wish...");

        //jeed Compatible
        add("effect.pasterdream.rest.description", "Touch the QYM doll or get it after sleeping, and you'll get a san aura while the effect lasts.");
        add("effect.pasterdream.cook.description", "Obtained after eating some kind of food, grants a san aura while the effect lasts.");
        add("effect.pasterdream.cheer_up.description", "Get it when san is above 90% to gain some positive effects.");
        add("effect.pasterdream.lethargy.description", "You gain some negative effects when san is between 40% and 60%.");
        add("effect.pasterdream.trance.description", "You gain worse negative effects when san is between 20% and 40%.");
        add("effect.pasterdream.insane.description", "When sanity falls below 20%, you get it and experience extremely severe negative effects. Your vision will be covered with special visual effects. When san is between 1% and 10%, you get Insane II, with looping sound effects and camera shakes. When sanity drops below 1%, you get Insane III, and the aforementioned negative effects are intensified. (Visual effects, shaking, and sound effects can be turned off with commands or config)");
        add("effect.pasterdream.snow_vow.description", "During the effect period, grants 3 Luck, immunity to burn and freeze effects, provided by Snow Vow Head curios within range.");
        add("effect.pasterdream.goldenrod_tea.description", "During the duration of the effect, it provides immunity to hunger and nausea.");
        add("effect.pasterdream.sculk_armor.description", "Obtained when wearing the full Sculk set, increases maximum health, grants resistance buff when in the depths and removes darkness debuff while giving speed and resistance effects when affected by darkness debuff.");
        add("effect.pasterdream.dyedream_armor.description", "Obtained when wearing the full Dyedream Alloy set, increases maximum health and grants Absorption II every 30 seconds.");
        add("effect.pasterdream.titanium_armor.description", "Obtained when wearing the full Titanium set, grants Absorption I every 30 seconds.");
        add("effect.pasterdream.dyedream_up.description", "Obtained when wearing the full Dyedream Alloy set, deals 50% more damage when holding Dyedream (Dyedream Alloy and Melt Dream Crystal) tools.");
        add("effect.pasterdream.melt_dream_crystal_armor", "Melt Dream Protection");
        add("effect.pasterdream.melt_dream_crystal_armor.description", "Obtained when wearing the full Melt Dream Crystal set, reduces Melt-Repair cost by 50%, grants Regeneration II and Dyedream tool enhancement, grants a San Aura of +1.2 when Melt Dream Energy is above 50%, otherwise +0.6.");
        add("effect.pasterdream.dream_wish.description", "Drink the dream fruit juice to get it, and having this effect lets you enter the Dyedream World when you sleep at night.");
        add("effect.pasterdream.dyedream_perfume.description", "Obtained by drinking the Dyedream Perfume, Phantom don't attack you for the duration of its effect.");
        add("effect.pasterdream.counter_attack.description", "Increase skill damage and disappear after the next attack.");
        add("effect.pasterdream.dream_harp_of_wanderer.description", "Obtained after using the Dream Harp of Wanderer, the Dream Harp of Wanderer can be acquired from the Dyedream Train.");
        add("effect.pasterdream.cecilia_blessing.description", "Obtained after takes a fatal hit when equips Blessing of Cecilia, making you immune to damage while the effect lasts.");
        add("effect.pasterdream.guard.description", "Obtained by using the Guardian Prophecy Card, while you have this buff, damage above a certain percentage of your health will be reduced (can be changed in the Config).");
        add("effect.pasterdream.flare_up.description", "Obtained using the Wielding-Sword Prophecy card. While you have this buff, it increases attack power and attack speed, boosts skill damage multiplier, and reduces skill cooldowns.");
        add("effect.pasterdream.conflict_mark.description", "After marking another entity with the Conflict Prophecy card, that entity gains the buff. While it has this buff, it will become the target of attacks from other entities.");
        add("effect.pasterdream.war_flag.description", "After equipping the war flag, you gain it by killing enemies, which increases your attack power, and leveling up by 1 for each enemy you kill.");
        add("effect.pasterdream.confusion.description", "When applied to a player, their view will shake; when applied to other creatures, it will make them unable to move.");
        add("effect.pasterdream.calais_spice_bottle.description", "After equipping the Calais Spice Bottle, you gain it. Each time you hit an enemy, it loses 1 level and grants a random effect. Eating the required food will restore the levels.");
        add("effect.pasterdream.shadow_silence.description", "After getting this buff, shadow creatures will lose the ability to use their skills.");
        add("effect.pasterdream.bind.description", "Obtained after being hit by the White Sword's sword rain. After getting this buff, you won't be able to move.");
        add("effect.pasterdream.restrainmove_block.description", "Remove jump boosts and force-disable flying while the effect lasts.");

        // Desert Hero Tomb - Quest Dialogue
        // Dyedream Crack Messages
        add("message.pasterdream.dyedream_crack.first_contact.1", "§5Your body passes through this strange hole, but nothing happens.");
        add("message.pasterdream.dyedream_crack.first_contact.2", "§5You can feel that this crack-like thing interacts with this world and another place. The different environments nearby might have been born because of it.");
        add("message.pasterdream.dyedream_crack.first_contact.3", "§5Perhaps it's not the time yet. Let's leave the answer to the time that flows between day and night.");
        add("message.pasterdream.dyedream_crack.first_contact.4", "§5Maybe I should flip through the Seniors Dream... there might be records about this kind of crack.");
        add("message.pasterdream.sleep.dream_of_crack.1", "§5You wake up startled from your sleep, with cold sweat on your back. You recall dreaming of the strange crack you encountered during your past explorations, slowly approaching and staring at you.");
        add("message.pasterdream.sleep.dream_of_crack.2", "§5That crack is likely suspicious. Perhaps you should return there to investigate.");
        add("message.pasterdream.sleep.dream_of_crack.3", "§5I must find out what happened...");
        add("message.pasterdream.sleep.dream_of_crack.4", "A Dream Seeker's note appeared in your pocket");
        add("message.pasterdream.dyedream_world.found_note", "You found a Dream Seeker's note and tucked it into your backpack");
        add("message.pasterdream.story.pure_and_flawless.found_note", "You found a Dream Seeker's note and tucked it into your backpack");
        add("message.pasterdream.story.dream_fertilizer.found_note", "You found a Dream Seeker's note and tucked it into your backpack");
        add("message.pasterdream.dream_accumulator.found_note", "You found a Dream Seeker's note and tucked it into your backpack");
        add("message.pasterdream.story_guide.not_entered_lamp_shadow", "You have not yet set foot in the Lamp Shadow World");
        add("message.pasterdream.story_guide.all_done", "All Lamp Shadow World notes have been resolved");

        add("message.pasterdream.dream_train.train_pass", "A Dream Train rumbles past...");
        add("message.pasterdream.dream_train.location_info", "The train revealed a location... §aX：%s §aZ：%s");

        add("message.pasterdream.desert_hero_tomb.line1", "At long last, a visitor has come.");
        add("message.pasterdream.desert_hero_tomb.line2", "I have no regrets, only one final request.");
        add("message.pasterdream.desert_hero_tomb.line3", "As you can see, this is all that remains of me.");
        add("message.pasterdream.desert_hero_tomb.line4", "I bestow this sword upon you. Will you help me drive out the invaders?");
        add("message.pasterdream.desert_hero_tomb.line5", "Well done, my gratitude.");
        add("message.pasterdream.desert_hero_tomb.line6", "You have proven your worth. I shall grant you the true sword.");
        add("message.pasterdream.desert_hero_tomb.line7", "Its name is: §e'Chenjingmen' Desert Blade");
        add("message.pasterdream.desert_hero_tomb.task_clear_threats", "[Clear the nearby Pillagers and Husks]");
        add("message.pasterdream.desert_hero_tomb.threats_remaining", "The threats nearby have not been cleared. He will not respond.");
        add("message.pasterdream.desert_hero_tomb.quest1", "1. Bring me 10 Rice Cakes");
        add("message.pasterdream.desert_hero_tomb.quest2", "2. I want a horse by my side");
        add("message.pasterdream.desert_hero_tomb.quest3", "3. Gain the recognition of an entire village as their hero");
        add("message.pasterdream.desert_hero_tomb.task1_complete", "Task 1 Complete");
        add("message.pasterdream.desert_hero_tomb.task2_complete", "Task 2 Complete");
        add("message.pasterdream.desert_hero_tomb.task3_complete", "Task 3 Complete");
        add("message.pasterdream.desert_hero_tomb.all_tasks_done", "My wishes have been fulfilled. Bring me the Desert Sword.");
        add("message.pasterdream.desert_hero_tomb.receive_sword", "Please accept this: its name is 'Chenjingmen' Desert Blade");
        add("message.pasterdream.desert_hero_tomb.bring_sword", "My wishes have been fulfilled. Bring me the Desert Sword.");
        add("message.pasterdream.desert_hero_tomb.already_completed", "Someone has already fulfilled all his wishes. He no longer responds.");

        // Prophecy Cards
        add(ModItems.EMPTY_PROPHECY_CARD.get(), "Empty Prophecy Card");
        add("item.pasterdream.prophecy_card", "Prophecy Card");
        add("item.pasterdream.prophecy_card.balance", "Prophecy Card of Balance");
        add("item.pasterdream.prophecy_card.chaos", "Prophecy Card of Chaos");
        add("item.pasterdream.prophecy_card.conflict", "Prophecy Card of Conflict");
        add("item.pasterdream.prophecy_card.graveyard", "Prophecy Card of Graveyard");
        add("item.pasterdream.prophecy_card.guard", "Prophecy Card of Guard");
        add("item.pasterdream.prophecy_card.holy_grail", "Prophecy Card of Holy Grail");
        add("item.pasterdream.prophecy_card.sin", "Prophecy Card of Sin");
        add("item.pasterdream.prophecy_card.sprint", "Prophecy Card of Sprint");
        add("item.pasterdream.prophecy_card.wielding_sword", "Prophecy Card of Wielding Sword");
        add("item.pasterdream.prophecy_card.unknown", "Error Prophecy Card (%s)");
        add("tooltip.pasterdream.prophecy_card.empty", "§7An empty prophecy card, yet to be assigned a type");
        add("tooltip.pasterdream.prophecy_card.type.balance", "§bType: Balance");
        add("tooltip.pasterdream.prophecy_card.type.chaos", "§bType: Chaos");
        add("tooltip.pasterdream.prophecy_card.type.conflict", "§bType: Conflict");
        add("tooltip.pasterdream.prophecy_card.type.graveyard", "§bType: Graveyard");
        add("tooltip.pasterdream.prophecy_card.type.guard", "§bType: Guard");
        add("tooltip.pasterdream.prophecy_card.type.holy_grail", "§bType: Holy Grail");
        add("tooltip.pasterdream.prophecy_card.type.sin", "§bType: Sin");
        add("tooltip.pasterdream.prophecy_card.type.sprint", "§bType: Sprint");
        add("tooltip.pasterdream.prophecy_card.type.wielding_sword", "§bType: Wielding Sword");
        add("tooltip.pasterdream.prophecy_card.unknown", "§cType: Error! (%s)");

        add("tooltip.pasterdream.prophecy_card.balance.description", "§9When using a card, the levels of some potion effects you currently have are doubled, but their duration is halved.");
        add("tooltip.pasterdream.prophecy_card.conflict.description.1", "§9When using a card, the entity corresponding to the cursor will be marked. The marked entity will become a target for attacks from other entities for 120 seconds.");
        add("tooltip.pasterdream.prophecy_card.conflict.description.2", "§7§O --At least you don’t have to worry about being stabbed into glass shards by a girl in white after using this card.");
        add("message.pasterdream.prophecy_card.conflict.marked", "Let's dance!");
        add("message.pasterdream.prophecy_card.conflict.no_target", "No target selected.");
        add("tooltip.pasterdream.prophecy_card.graveyard.description", "§9When using the card, deal %.1f damage to all enemies within a 7*7 range centered on yourself. This damage is not reduced by defense. (This effect does not apply to players)");
        add("tooltip.pasterdream.prophecy_card.sprint.description", "§9When using the card, you gain Speed III, Jump Boost II, and Rapid Reaction effects for 120 seconds. During this time, your step height is increased, Blink cooldown is reduced by 30%, and you are immune to fall damage.");
        add("tooltip.pasterdream.prophecy_card.guard.description.1", "§9When using the card, gain 120 seconds of Damage Absorption III and 60 seconds of Guardian effect.");
        add("tooltip.pasterdream.prophecy_card.guard.description.2", "§9If a player takes any damage that exceeds %.1f%% of their maximum health, the excess part will be reduced by %.1f%%. This damage reduction is applied before the armor's damage reduction.");
        add("tooltip.pasterdream.prophecy_card.holy_grail.description", "§9When using the card, you gain the Holy Grail effect for 120 seconds. During this time, you get 15 maximum HP, are immune to some negative statuses, and naturally regenerate health—the lower your HP, the faster the regeneration, reaching the maximum speed when below 33%.");
        add("tooltip.pasterdream.prophecy_card.chaos.description.1", "§9When using the card, all enemies within a 7*7 area centered on yourself will become confused and unable to act for 10 seconds. (This effect does not apply to players)");
        add("tooltip.pasterdream.prophecy_card.chaos.description.2", "§7§O --THE TRUE AND NEO CHAOS");
        add("tooltip.pasterdream.prophecy_card.sin.description.1", "§9When using the card, it ignites all undead mobs and Illagers within a 19*19 area centered on yourself for 15 seconds, dealing 25 points damage.");
        add("tooltip.pasterdream.prophecy_card.sin.description.2", "§9If this entity is a zombie villager, it won't take damage and will turn into a villager.");
        add("tooltip.pasterdream.prophecy_card.sin.description.3", "§9As for those extremely annoying creatures, just ERASE them. As for what counts as extremely annoying, trust the prophecy card's judgment.");
        add("tooltip.pasterdream.prophecy_card.sin.description.4", "§5§O --FACE YOUR SIN.");
        add("tooltip.pasterdream.prophecy_card.wielding_sword.description", "§9When using the card, you gain the Flare up effect for 120 seconds. During this effect, attack damage +3, attack speed +0.2, skill damage multiplier +30%, and skill cooldown -20%.");

        add("tooltip.pasterdream.prophecy_card.unknown.description.1", "§9How did you get this card? ");
        add("tooltip.pasterdream.prophecy_card.unknown.description.2", "§9Please send what you did during game to GitHub or mcmod so we can pinpoint the issue, instead of just sending this error screenshot.");
        add("message.pasterdream.prophecy_card.invalid", "§cProphecy Card data corrupted (%s), right-click disabled!");

        add(PotionBottleRegistry.POTION_BOTTLE.get(), "Potion Bottle");
        add("item.pasterdream.potion_bottle.berserk", "Potion Bottle of Berserk");
        add("item.pasterdream.potion_bottle.frozen", "Potion Bottle of Frozen");
        add("item.pasterdream.potion_bottle.highly_toxic", "Potion Bottle of Highly Toxic");
        add("item.pasterdream.potion_bottle.lightning", "Potion Bottle of Lightning");
        add("item.pasterdream.potion_bottle.rejuvenation", "Potion Bottle of Rejuvenation");
        add("effect.pasterdream.highly_toxic", "Highly Toxic");
        add("tooltip.pasterdream.potion_bottle.highly_toxic.description","§9After Smashing, release a highly toxic cloud, inflicting Highly Toxic, Slowness, and Weakness buff on all creatures in a 6x6 area.");
        add("tooltip.pasterdream.potion_bottle.lightning.description.1","§9After Smashing, release a dark cloud at the landing spot, and after a short time, randomly strikes lightning 4 times within a 5x5 area.");
        add("tooltip.pasterdream.potion_bottle.lightning.description.2","§7§O --Bottled Lightning");
        add("tooltip.pasterdream.potion_bottle.rejuvenation.description","§9After Smashing, it releases a 5x5 area of healing mist for 20 seconds, restoring 5% of max health to players or peaceful creatures within the mist.");
        add("tooltip.pasterdream.potion_bottle.frozen.description.1","§9After smashing, it releases a 7*7 freezing mist at the landing spot, and any entities inside will be unable to move and lose their combat ability.");
        add("tooltip.pasterdream.potion_bottle.frozen.description.2","§7§O --Snowgrave");
        add("tooltip.pasterdream.potion_bottle.berserk.description.1","§9After smashing, release an 8*8 frenzy mist at the landing spot.");
        add("tooltip.pasterdream.potion_bottle.berserk.description.2","§9Players in it +20% attack damage, +10% movement speed, +50% attack speed, -30% skill cooldown, -30% blink cooldown, and +30% skill damage.");
        add("tooltip.pasterdream.potion_bottle.inferno.description.1","§9After smashing, create a blazing fire in a 6x6 area, dealing magic damage over time to all enemies within the range and igniting them.");
        add("tooltip.pasterdream.potion_bottle.inferno.description.2","§9Enemies hit will get a vulnerability debuff, up to 3 stacks.");
        add("effect.pasterdream.berserk", "Frenzy");
        add("effect.pasterdream.frozen", "Flash Freeze");
        add("effect.pasterdream.vulnerability", "Vulnerability");


        // Shadow Hand Lantern
        add(ModItems.SHADOW_HAND_LANTERN.get(), "Shadow Hand Lantern");
        add("tooltip.pasterdream.shadow_hand_lantern.description.1", "§7▪ §9When holding a lantern: 1.2 Sanity aura/min");
        add("tooltip.pasterdream.shadow_hand_lantern.description.2", "§7Use with right-click");
        add("tooltip.pasterdream.shadow_hand_lantern.description.3", "§7▪ §9Shadow creatures within a 15-square diameter take 20% increased damage for 15 seconds.");
        add("tooltip.pasterdream.shadow_hand_lantern.description.4", "§7▪ §9Cooldown: 8 seconds");
        add("tooltip.pasterdream.shadow_hand_lantern.description.5", "§7▪ §4San cost: 1");

        // Structure translations
        add("structure.pasterdream.oak_fisherman_hut", "Oak Fisherman Hut");
        add("structure.pasterdream.spruce_fisherman_hut", "Spruce Fisherman Hut");
        add("structure.pasterdream.dyedream_crack", "Dyedream Crack");
        add("structure.pasterdream.campsite_overworld", "Campsite");
        add("structure.pasterdream.the_lost_sword_tomb", "The Lost Sword Tomb");
        add("structure.pasterdream.dyedream_church_0", "Dream Church");
        add("structure.pasterdream.dyedream_church_2", "Dream Church");
        add("structure.pasterdream.dyedream_church_4", "Dream Church");
        add("structure.pasterdream.dyedream_church_6", "Dream Church");
        add("structure.pasterdream.dyedream_crystal_ball", "Dyedream Crystal Ball");
        add("structure.pasterdream.garden_decryption_misty_dreaming_lotus", "Garden Decryption: Misty Dreaming Lotus");
        add("structure.pasterdream.garden_decryption_nippy_edelweiss", "Garden Decryption: Nippy Edelweiss");
        add("structure.pasterdream.garden_decryption_nine_tailed_fox", "Garden Decryption: Nine-Tailed Fox");
        add("structure.pasterdream.traveler_house", "Traveler House");
        add("structure.pasterdream.dyedream_pavilion_plain", "Dyedream Pavilion");
        add("structure.pasterdream.dyedream_pavilion_snowy_plain", "Snowy Plain Dyedream Pavilion");
        add("structure.pasterdream.dyedream_worldtree", "Dyedream World Tree");
        add("structure.pasterdream.dyedream_wishing_tree", "Dyedream Wishing Tree");
        add("structure.pasterdream.dyedream_floating_temple", "Floating Temple");
        add("structure.pasterdream.dyedream_tavern", "Dyedream Tavern");
        add("structure.pasterdream.dyedream_campsite", "Dyedream Campsite");
        add("structure.pasterdream.dyedream_ecosystem_bubble", "Dyedream Ecosystem Bubble");
        add("structure.pasterdream.ecosystem_bubble", "Ecosystem Bubble");
        add("structure.pasterdream.pinkagaric_house", "Pinkagaric House");
        add("structure.pasterdream.dyedream_tower_0", "Dyedream Cloud-Piercing Tower 0");
        add("structure.pasterdream.dyedream_tower_1", "Dyedream Cloud-Piercing Tower 1");
        add("structure.pasterdream.big_bubbles_0", "Big Bubble 0");
        add("structure.pasterdream.big_bubbles_1", "Big Bubble 1");
        add("structure.pasterdream.big_bubbles_2", "Big Bubble 2");
        add("structure.pasterdream.desert_fortress", "Desert Fortress");
        add("structure.pasterdream.dream_train", "Dream Train");
        add("structure.pasterdream.dyedream_laboratory", "Dyedream Laboratory");
        add("structure.pasterdream.lifecrystal_cave", "Life Crystal Cave");
        add("structure.pasterdream.melt_dream_liquid_well", "Melt Dream Liquid Well");
        add("structure.pasterdream.dyedream_sky_island", "Dyedream Sky Island");
        add("structure.pasterdream.shadownote_ruin_0", "Shadow Note Ruin");
        add("structure.pasterdream.shadownote_ruin_1", "Shadow Note Ruin");
        add("structure.pasterdream.shadownote_ruin_2", "Shadow Note Ruin");
        add("structure.pasterdream.shadow_shelter", "Shadow Shelter");
        add("structure.pasterdream.shadow_nest", "Shadow Nest");
        add("structure.pasterdream.shadow_fungus_house", "Shadow Fungus House");
        add("structure.pasterdream.shadow_foundry", "Shadow Foundry");
        add("structure.pasterdream.invaded_church", "Infested Church");
        add("structure.pasterdream.twilight_lantern", "Twilight Lantern");
        add("structure.pasterdream.wind_island", "Wind Island");
        add("structure.pasterdream.windmill_lodge", "Windmill Lodge");
        add("structure.pasterdream.lost_windknight_ruins", "Lost Windknight Ruins");
        add("structure.pasterdream.windmoor_tree", "Windmoor Tree");
        add("structure.pasterdream.wind_infested_stone_0", "Wind-infested Stone");
        add("structure.pasterdream.wind_infested_stone_1", "Wind-infested Stone");
        add("structure.pasterdream.wind_pond", "Wind Pond");
        add("structure.pasterdream.big_bubbles_6", "Big Bubble");
        add("structure.pasterdream.big_bubbles_7", "Big Bubble");
        add("structure.pasterdream.bocchi_0", "Bocchi");
        add("structure.pasterdream.bocchi_1", "Bocchi");
        add("structure.pasterdream.breakwing_curtain", "Breakwing Curtain");
        add("structure.pasterdream.hakurei_reimu", "Hakurei Reimu");
        add("structure.pasterdream.hot_air_balloon_0", "Hot Air Balloon");
        add("structure.pasterdream.hot_air_balloon_1", "Hot Air Balloon");
        add("structure.pasterdream.hot_air_balloon_2", "Hot Air Balloon");
        add("structure.pasterdream.hot_air_balloon_3", "Hot Air Balloon");
        add("structure.pasterdream.hot_air_balloon_4", "Hot Air Balloon");
        add("structure.pasterdream.hot_air_balloon_5", "Hot Air Balloon");
        add("structure.pasterdream.hot_air_balloon_6", "Hot Air Balloon");
        add("structure.pasterdream.hot_air_balloon_7", "Hot Air Balloon");
        add("structure.pasterdream.small_ballon_0", "Small Balloon");
        add("structure.pasterdream.small_ballon_1", "Small Balloon");
        add("structure.pasterdream.small_ballon_2", "Small Balloon");
        add("structure.pasterdream.small_ballon_3", "Small Balloon");
        add("structure.pasterdream.small_ballon_4", "Small Balloon");
        add("structure.pasterdream.small_ballon_5", "Small Balloon");
        add("structure.pasterdream.small_ballon_6", "Small Balloon");
        add("structure.pasterdream.small_ballon_7", "Small Balloon");
        add("structure.pasterdream.small_ballon_8", "Small Balloon");
        add("structure.pasterdream.small_ballon_9", "Small Balloon");
        add("structure.pasterdream.small_ballon_10", "Small Balloon");
        add("structure.pasterdream.christmas_tree", "Christmas Tree");
        add("structure.pasterdream.fluffy_wind_church", "Fluffy Wind Church");
        add("structure.pasterdream.aaroncos_arena_portal", "Aaroncos Arena Portal");
        add("structure.pasterdream.shadow_underground_workroom", "Shadow Underground Workroom");
        add("message.pasterdream.twilight_lantern.activate_fail_no_knowledge", "You do not yet know how to activate the shadow lantern");
        add("message.pasterdream.twilight_lantern.activate_fail_no_crystal", "You need to light the shadow lantern with a Melt Dream Crystal Fragment");
        add("message.pasterdream.twilight_lantern.event_start", "§8The eerie lantern suddenly shakes violently, black shadows flow out from the wick.");
        add("message.pasterdream.twilight_lantern.event_mid", "§8The shadows materialize into physical form, hollow echoes resound from all around, followed by a shriek loud enough to tear eardrums.");
        add("message.pasterdream.twilight_lantern.event_voice", "§8You seem to hear a strange voice.");
        add("message.pasterdream.twilight_lantern.event_end", "§8Shadows no longer surge outward, the Twilight Lantern gradually returns to calm, a rift slowly appears in the sky above.");
        add("message.pasterdream.twilight_lantern.event_end_3", "§5\"Do not resist, surrender to your heart, together in eternal life\"");
        add("message.pasterdream.twilight_lantern.event_end_4", "§5\"Submit! Submit!! Submit!!!\"");
        add("message.pasterdream.twilight_lantern.event_end_5", "§5\"Come, come~ Come! ...Embrace us, sleep with us, merge with us, here is your only home\"");
        add("message.pasterdream.twilight_lantern.event_end_2", "§8The voices grow clearer, the repetitive words begin to irritate you.");
        add("message.pasterdream.twilight_lantern.event_ready", "§5You can now resonate with the Twilight Lantern, interact with the True Shadow Bed");
        add("message.pasterdream.twilight_lantern.data_reset", "Block data reset");
        add("message.pasterdream.twilight_lantern_location", "The end of the note records coordinates in the upper Nether: X: %s Z: %s");
        // ===== Wind Journey: wind direction mechanic =====
        add(ModItems.WIND_VANE.get(), "Wind Vane");
        add(ModItems.WIND_KNIGHT_FLAG.get(), "Wind Knight Flag");
        add(ModItems.PAPER_PLANE.get(), "Paper Plane");
        add("effect.pasterdream.tailwind", "Tailwind");
        add("effect.pasterdream.deadwind", "Deadwind");
        add("effect.pasterdream.windproof", "Windproof");
        add("effect.pasterdream.misty_dream", "Misty Dream");
        add("effect.pasterdream.cloud_mist", "Cloud Mist");
        add("tooltip.pasterdream.wind_vane.desc", "§7Detects the current wind direction and the player's angle");
        add("message.pasterdream.wind_vane.angle", "Angle: %s Direction: %s");
        add("tooltip.pasterdream.wind_knight_flag.effect1", "§7▪ §9Treats deadwind as tailwind");
        add("tooltip.pasterdream.paper_plane.effect1", "§7▪ §9Amplifies the wind's effect on yourself");
        add("tooltip.pasterdream.paper_plane.description", "§7§o-- The childhood we left behind");
        add("message.pasterdream.wind_vane.direction.0", "Current wind: §aNorth Wind");
        add("message.pasterdream.wind_vane.direction.1", "Current wind: §aNortheast Wind");
        add("message.pasterdream.wind_vane.direction.2", "Current wind: §aEast Wind");
        add("message.pasterdream.wind_vane.direction.3", "Current wind: §aSoutheast Wind");
        add("message.pasterdream.wind_vane.direction.4", "Current wind: §aSouth Wind");
        add("message.pasterdream.wind_vane.direction.5", "Current wind: §aSouthwest Wind");
        add("message.pasterdream.wind_vane.direction.6", "Current wind: §aWest Wind");
        add("message.pasterdream.wind_vane.direction.7", "Current wind: §aNorthwest Wind");
        add("message.pasterdream.wind_direction.announce.0", "§7§oThe sun rises... The howling wind blows toward §aSouth");
        add("message.pasterdream.wind_direction.announce.1", "§7§oThe sun rises... The howling wind blows toward §aSouthwest");
        add("message.pasterdream.wind_direction.announce.2", "§7§oThe sun rises... The howling wind blows toward §aWest");
        add("message.pasterdream.wind_direction.announce.3", "§7§oThe sun rises... The howling wind blows toward §aNorthwest");
        add("message.pasterdream.wind_direction.announce.4", "§7§oThe sun rises... The howling wind blows toward §aNorth");
        add("message.pasterdream.wind_direction.announce.5", "§7§oThe sun rises... The howling wind blows toward §aNortheast");
        add("message.pasterdream.wind_direction.announce.6", "§7§oThe sun rises... The howling wind blows toward §aEast");
        add("message.pasterdream.wind_direction.announce.7", "§7§oThe sun rises... The howling wind blows toward §aSoutheast");
    }
}
