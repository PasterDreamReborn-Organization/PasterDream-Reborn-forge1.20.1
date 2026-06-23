package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.PasterDreamDrinkAndFoodProperties;
import com.pasterdream.pasterdreammod.world.item.dreamnotes.DreamNotesItem;
import com.pasterdream.pasterdreammod.world.item.drinkandfooditem.PasterDreamDrinkItem;
import com.pasterdream.pasterdreammod.world.item.drinkandfooditem.PasterDreamFoodItem;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PasterDreamMod.MOD_ID);

    // ===== 素材物品 =====

    public static final RegistryObject<Item> DYEDREAM_QUARTZ = ITEMS.register("dyedream_quartz",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_DUST = ITEMS.register("dyedream_dust",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_DUST_PIECE = ITEMS.register("dyedream_dust_piece",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AMBER_CANDY = ITEMS.register("amber_candy",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.1f).alwaysEat()
                            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60, 0), 1.0f).build())
                    .useDuration(16)));
    public static final RegistryObject<Item> PINK_SLIMEBALL = ITEMS.register("pink_slimeball",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DYEDREAM_DYE = ITEMS.register("dyedream_dye",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DYEDREAM_BUD_NUGGET = ITEMS.register("dyedream_bud_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_DYEDREAM_ALLOY_INGOT = ITEMS.register("raw_dyedream_alloy_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DYEDREAM_ALLOY_INGOT = ITEMS.register("dyedream_alloy_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TITANIUM_NUGGET = ITEMS.register("titanium_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_TITANIUM = ITEMS.register("raw_titanium",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DYEDREAM_ALLOY_NUGGET = ITEMS.register("dyedream_alloy_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MOLTEN_GOLD_INGOT = ITEMS.register("molten_gold_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MOLTEN_GOLD_NUGGET = ITEMS.register("molten_gold_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_MOLTEN_GOLD = ITEMS.register("raw_molten_gold",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MAGIC_STONE = ITEMS.register("magic_stone",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SOUL_DUST = ITEMS.register("soul_dust",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOUL_ESSENCE = ITEMS.register("soul_essence",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHARGED_AMETHYST = ITEMS.register("charged_amethyst",
            () -> new Item(new Item.Properties()));

    // ===== 玻璃罐系列 =====
    public static final RegistryObject<Item> GLASS_JAR = ITEMS.register("glass_jar", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GLASS_JAR_OF_WATER = ITEMS.register("glass_jar_of_water", () -> new PasterDreamDrinkItem((PasterDreamDrinkAndFoodProperties) new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().alwaysEat().build()).useDuration(24).craftRemainder(ModItems.GLASS_JAR.get())));
    public static final RegistryObject<Item> GLASS_JAR_OF_MILK = ITEMS.register("glass_jar_of_milk", () -> new PasterDreamDrinkItem((PasterDreamDrinkAndFoodProperties) new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().alwaysEat().build()).useDuration(24).craftRemainder(ModItems.GLASS_JAR.get())));
    public static final RegistryObject<Item> GLASS_JAR_OF_YEAST = ITEMS.register("glass_jar_of_yeast", () -> new Item(new Item.Properties().craftRemainder(ModItems.GLASS_JAR.get())));
    public static final RegistryObject<Item> GLASS_JAR_OF_GUIDING_DRUG = ITEMS.register("glass_jar_of_guiding_drug", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GLASS_JAR_OF_WIND_PLANT_EXTRACT = ITEMS.register("glass_jar_of_wind_plant_extract", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GLASS_JAR_OF_DREAM_JUICE = ITEMS.register("glass_jar_of_dream_juice", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().stacksTo(8).food(new FoodProperties.Builder().alwaysEat().build()).useDuration(24))
    {
        @Override
        protected void onDrinkSpecial(Player player, Level level)
        {
            //设置玩家标记，用于床交互时传送至染梦世界
            player.getPersistentData().putBoolean("pasterdream:dream_juice_drank", true);
        }
    });
    public static final RegistryObject<Item> GLASS_JAR_OF_GOLDENROD_TEA = ITEMS.register("glass_jar_of_goldenrod_tea", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().nutrition(1)./*effect().  实现“秋麒麟茶I效果后添加在这里”*/alwaysEat().build()).useDuration(24)));
    public static final RegistryObject<Item> GLASS_JAR_OF_DYEDREAM_PERFUME = ITEMS.register("glass_jar_of_dyedream_perfume",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder()./*effect(). 实现染梦香水效果后添加*/alwaysEat().build()).useDuration(24)));
    // ===== 玻璃杯系列 =====
    public static final RegistryObject<Item> GLASS_CUP = ITEMS.register("glass_cup", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GLASS_CUP_OF_APPLE_JUICE = ITEMS.register("glass_cup_of_apple_juice", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.5f).alwaysEat().build())));
    public static final RegistryObject<Item> GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA = ITEMS.register("glass_cup_of_uncooked_dyedream_flower_tea", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA = ITEMS.register("glass_cup_of_cooked_dyedream_flower_tea", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.HEAL, 1, 0), 1.0f).alwaysEat().build())));
    public static final RegistryObject<Item> GLASS_CUP_OF_DYEDREAM_JUICE = ITEMS.register("glass_cup_of_dyedream_juice", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.5f).alwaysEat().build()).meltDreamEnergyAdd(0.2)));
    public static final RegistryObject<Item> GLASS_CUP_OF_HONEY_JUICE = ITEMS.register("glass_cup_of_honey_juice", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.5f).alwaysEat().build())));
    public static final RegistryObject<Item> GLASS_CUP_OF_WATERMELON_JUICE = ITEMS.register("glass_cup_of_watermelon_juice", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.5f).alwaysEat().build())));

    // ===== 食材系列 =====
    public static final RegistryObject<Item> FLOUR = ITEMS.register("flour",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DOUGH = ITEMS.register("dough",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.5f).build())));

    public static final RegistryObject<Item> RYE_SEED = ITEMS.register("rye_seed",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DOUGH_WITH_EGG = ITEMS.register("dough_with_egg",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COARSE_SALT = ITEMS.register("coarse_salt",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SALT = ITEMS.register("salt",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PINK_EGG = ITEMS.register("pink_egg",
            () -> new Item(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> CHOCOLATE = ITEMS.register("chocolate",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(0.375f).build())));

    public static final RegistryObject<Item> DYEDREAM_FRUIT = ITEMS.register("dyedream_fruit",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1.0f)//5秒生命回复I
                    .nutrition(3).saturationMod(0.5f).build())));

    public static final RegistryObject<Item> FIG = ITEMS.register("fig",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(3).saturationMod(0.165f).build())));


    // ===== 蛋糕系列 ===== (小蛋糕都应当给予料理I buff，料理buff制作完成后补上）
    public static final RegistryObject<Item> CAKE_BASE = ITEMS.register("cake_base",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(0.375f).build())));

    public static final RegistryObject<Item> CREAM_BUN_CAKE = ITEMS.register("cream_bun_cake",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(0.625f).build())));

    public static final RegistryObject<Item> BERRY_BUN_CAKE = ITEMS.register("berry_bun_cake",
            ()  -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0), 1.0f)
                            .nutrition(4).saturationMod(0.625f).alwaysEat().build())));

    public static final RegistryObject<Item> TUBER_BUN_CAKE = ITEMS.register("tuber_bun_cake",
            ()  -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 0), 1.0f)
                            .nutrition(4).saturationMod(0.625f).alwaysEat().build())));

    public static final RegistryObject<Item> WATERMELON_BUN_CAKE = ITEMS.register("watermelon_bun_cake",
            ()  -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 0), 1.0f)
                            .nutrition(4).saturationMod(0.625f).alwaysEat().build())));

    public static final RegistryObject<Item> PUMPKIN_BUN_CAKE = ITEMS.register("pumpkin_bun_cake",
            ()  -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.JUMP, 1200, 0), 1.0f)
                            .nutrition(4).saturationMod(0.625f).alwaysEat().build())));

    public static final RegistryObject<Item> GLOW_BERRY_BUN_CAKE = ITEMS.register("glow_berry_bun_cake",
            ()  -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0), 1.0f)
                            .nutrition(4).saturationMod(0.625f).alwaysEat().build())));

    public static final RegistryObject<Item> DYEDREAM_FRUIT_BUN_CAKE = ITEMS.register("dyedream_fruit_bun_cake",
            ()  -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1).meltDreamEnergyAdd(0.3)
                    .food(new FoodProperties.Builder().nutrition(4).alwaysEat().saturationMod(0.625f).build())));

    public static final RegistryObject<Item> CHOCOLATE_MATCHA_CAKE = ITEMS.register("chocolate_matcha_cake",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().nutrition(8).saturationMod(1).build())));

    // ===== 杂项食物系列 =====（除了染梦冰棒,泡泡糖，跳跳糖，俩棉花糖和面包片，都应当给予1分钟料理I buff，除去奇怪培根煎蛋是2分钟,料理buff制作完成后补上）
    public static final RegistryObject<Item> RICE_CAKE = ITEMS.register("rice_cake",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(6).saturationMod(0.415f).build())));

    public static final RegistryObject<Item> DYEDREAM_POPSICLE = ITEMS.register("dyedream_popsicle",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1).meltDreamEnergyAdd(0.2)
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.5f).build())));//减少燃烧时间施工中

    public static final RegistryObject<Item> FRIED_EGG = ITEMS.register("fried_egg",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationMod(0.25f).build())));

    public static final RegistryObject<Item> BACON_AND_EGG = ITEMS.register("bacon_and_egg",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().nutrition(10).saturationMod(1.2f).build())));

    public static final RegistryObject<Item> ODD_BACON_AND_EGG = ITEMS.register("odd_bacon_and_egg",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(2)
                    .food(new FoodProperties.Builder().nutrition(11).saturationMod(1.365f).build())));

    public static final RegistryObject<Item> HEART_CHOCOLATE = ITEMS.register("heart_chocolate",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(0.5)
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(0.375f).build())));

    public static final RegistryObject<Item> WHITE_HEART_CHOCOLATE = ITEMS.register("white_heart_chocolate",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(0.375f).build())));

    public static final RegistryObject<Item> PINK_HEART_CHOCOLATE = ITEMS.register("pink_heart_chocolate",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1.5).meltDreamEnergyAdd(0.2)
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(0.375f).build())));

    public static final RegistryObject<Item> BREAD_SLICE = ITEMS.register("bread_slice",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(3).saturationMod(0.5f).build())));

    public static final RegistryObject<Item> SWISS_ROLL = ITEMS.register("swiss_roll",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().nutrition(6).saturationMod(0.75f).build())));

    public static final RegistryObject<Item> SANDWICH = ITEMS.register("sandwich",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().nutrition(10).saturationMod(0.9f).build())));

    public static final RegistryObject<Item> WAFER_BISCUIT = ITEMS.register("wafer_biscuit",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(3).saturationMod(0.165f).build())));

    public static final RegistryObject<Item> STUFFED_WAFER_COOKIES = ITEMS.register("stuffed_wafer_cookies",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().nutrition(7).saturationMod(1).build())));

    public static final RegistryObject<Item> GINGERBREAD_MAN = ITEMS.register("gingerbread_man",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().nutrition(5).saturationMod(0.6f).build())));

    public static final RegistryObject<Item> CANDY_CANE = ITEMS.register("candy_cane",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().nutrition(3).saturationMod(0.5f).build())));

    public static final RegistryObject<Item> POPPING_CANDY = ITEMS.register("popping_candy",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.JUMP, 400, 3), 1.0f)
                            .nutrition(2).saturationMod(0.25f).alwaysEat().build())));

    public static final RegistryObject<Item> YINHUL_COTTON_CANDY = ITEMS.register("yinhul_cotton_candy",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(15).meltDreamEnergyAdd(15)
                    .food(new FoodProperties.Builder().nutrition(6).saturationMod(0.75f).alwaysEat().build())));

    public static final RegistryObject<Item> MELT_DREAM_COTTON_CANDY= ITEMS.register("melt_dream_cotton_candy",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().meltDreamEnergyAdd(4)
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(0.625f).alwaysEat().build())));

    public static final RegistryObject<Item> BUBBLE_GUM = ITEMS.register("bubble_gum",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(3)
                    .food(new FoodProperties.Builder().nutrition(1).alwaysEat().build()).useDuration(64)));

    public static final RegistryObject<Item> LIGHT_ORGAN = ITEMS.register("light_organ",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(-1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.GLOWING, 100, 0), 1.0f)
                            .nutrition(1).build())));

    //防风buff施工中
    public static final RegistryObject<Item> JELLYFISH_MUD = ITEMS.register("jellyfish_mud",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(-3)
                    .food(new FoodProperties.Builder().nutrition(1).build())));

    public static final RegistryObject<Item> JELLYFISH_JELLO = ITEMS.register("jellyfish_jello",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(0.5f).build())));

    public static final RegistryObject<Item> QUEER_SOUP = ITEMS.register("queer_soup",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(0.375f).alwaysEat().build()))
            {
                @Override
                protected void onFoodSpecial(Player player, Level level)
                {
                    if (!level.isClientSide&&!player.isCreative())
                    {
                            ItemStack containerStack = new ItemStack(Items.BOWL);
                            if (!player.getInventory().add(containerStack))
                            {
                                player.drop(containerStack, false);
                            }
                    }
                }
            });


    public static final RegistryObject<Item> LEGENDARY_DRAGON_HORN_ICE_CREAM = ITEMS.register("legendary_dragon_horn_ice_cream",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(10).saturationMod(1.2f).alwaysEat().build()))
            {
                @Override
                protected void onFoodSpecial(Player player, Level level)
                {
                    if (!level.isClientSide&&!player.isCreative())
                    {
                        ItemStack containerStack = new ItemStack(Items.BOWL);
                        if (!player.getInventory().add(containerStack))
                        {
                            player.drop(containerStack, false);
                        }
                    }
                }
            });

    public static final RegistryObject<Item> ELIXIR_BOTTLE = ITEMS.register("elixir_bottle",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ELIXIR_BOTTLE_OF_MELT_DREAM = ITEMS.register("elixir_bottle_of_melt_dream",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().stacksTo(1).meltDreamEnergyAdd(25)
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(2).alwaysEat().build())));

    public static final RegistryObject<Item> ELIXIR_BOTTLE_OF_RAGE_ELIXIR = ITEMS.register("elixir_bottle_of_rage_elixir",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().stacksTo(1)
                    .food(new FoodProperties.Builder().alwaysEat().build())));

    public static final RegistryObject<Item> PINEAPPLE_LOVE_SEA = ITEMS.register("pineapple_love_sea",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().sanAdd(15)
                    .food(new FoodProperties.Builder().nutrition(3).saturationMod(0.5f).alwaysEat().build())));

    // ===== 材料物品 =====
    public static final RegistryObject<Item> DYEDREAM_COROLLA = ITEMS.register("dyedream_corolla",
            () -> new Item(new Item.Properties()));

    // ===== 方块物品 =====

    public static final RegistryObject<Item> DYEDREAM_DIRT = ITEMS.register("dyedream_dirt",
            () -> new BlockItem(ModBlocks.DYEDREAM_DIRT.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_GRASS_BLOCK = ITEMS.register("dyedream_grass_block",
            () -> new BlockItem(ModBlocks.DYEDREAM_GRASS_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_LOG = ITEMS.register("dyedream_log",
            () -> new BlockItem(ModBlocks.DYEDREAM_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_WOOD = ITEMS.register("dyedream_wood",
            () -> new BlockItem(ModBlocks.DYEDREAM_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_LEAVES = ITEMS.register("dyedream_leaves",
            () -> new BlockItem(ModBlocks.DYEDREAM_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_WORLDTREE_LEAVES = ITEMS.register("dyedream_worldtree_leaves",
            () -> new BlockItem(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_SAPLING = ITEMS.register("dyedream_sapling",
            () -> new BlockItem(ModBlocks.DYEDREAM_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_QUARTZ_ORE = ITEMS.register("dyedream_quartz_ore",
            () -> new BlockItem(ModBlocks.DYEDREAM_QUARTZ_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_DUST_ORE = ITEMS.register("dyedream_dust_ore",
            () -> new BlockItem(ModBlocks.DYEDREAM_DUST_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> AMBER_CANDY_ORE = ITEMS.register("amber_candy_ore",
            () -> new BlockItem(ModBlocks.AMBER_CANDY_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_ORE = ITEMS.register("titanium_ore",
            () -> new BlockItem(ModBlocks.TITANIUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DEEPSLATE_TITANIUM_ORE = ITEMS.register("deepslate_titanium_ore",
            () -> new BlockItem(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> MOLTEN_GOLD_ORE = ITEMS.register("molten_gold_ore",
            () -> new BlockItem(ModBlocks.MOLTEN_GOLD_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SOUL_ORE = ITEMS.register("soul_ore",
            () -> new BlockItem(ModBlocks.SOUL_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAW_TITANIUM_BLOCK = ITEMS.register("raw_titanium_block",
            () -> new BlockItem(ModBlocks.RAW_TITANIUM_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SALT_BLOCK = ITEMS.register("salt_block",
            () -> new BlockItem(ModBlocks.SALT_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_BLOCK = ITEMS.register("titanium_block",
            () -> new BlockItem(ModBlocks.TITANIUM_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> MOLTEN_GOLD_BLOCK = ITEMS.register("molten_gold_block",
            () -> new BlockItem(ModBlocks.MOLTEN_GOLD_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHARGED_AMETHYST_BLOCK = ITEMS.register("charged_amethyst_block",
            () -> new BlockItem(ModBlocks.CHARGED_AMETHYST_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_SLIME_BLOCK = ITEMS.register("pink_slime_block",
            () -> new BlockItem(ModBlocks.PINK_SLIME_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_MUSHROOM_BLOCK = ITEMS.register("pink_mushroom_block",
            () -> new BlockItem(ModBlocks.PINK_MUSHROOM_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_MUSHROOM_STEM = ITEMS.register("pink_mushroom_stem",
            () -> new BlockItem(ModBlocks.PINK_MUSHROOM_STEM.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_MUSHROOM_PORES = ITEMS.register("pink_mushroom_pores",
            () -> new BlockItem(ModBlocks.PINK_MUSHROOM_PORES.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_SHROOMLIGHT = ITEMS.register("pink_shroomlight",
            () -> new BlockItem(ModBlocks.PINK_SHROOMLIGHT.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_MUSHROOM = ITEMS.register("pink_mushroom",
            () -> new BlockItem(ModBlocks.PINK_MUSHROOM.get(), new Item.Properties()));
    public static final RegistryObject<Item> TALL_PINK_MUSHROOM = ITEMS.register("tall_pink_mushroom",
            () -> new BlockItem(ModBlocks.TALL_PINK_MUSHROOM.get(), new Item.Properties()));

    // ===== 染梦玻璃系列 =====

    public static final RegistryObject<Item> DYEDREAM_SAND = ITEMS.register("dyedream_sand",
            () -> new BlockItem(ModBlocks.DYEDREAM_SAND.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_GLASS = ITEMS.register("dyedream_glass",
            () -> new BlockItem(ModBlocks.DYEDREAM_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_GLASS_PANE = ITEMS.register("dyedream_glass_pane",
            () -> new BlockItem(ModBlocks.DYEDREAM_GLASS_PANE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CARVE_DYEDREAM_GLASS = ITEMS.register("carve_dyedream_glass",
            () -> new BlockItem(ModBlocks.CARVE_DYEDREAM_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CARVE_DYEDREAM_GLASS_PANE = ITEMS.register("carve_dyedream_glass_pane",
            () -> new BlockItem(ModBlocks.CARVE_DYEDREAM_GLASS_PANE.get(), new Item.Properties()));
    public static final RegistryObject<Item> GOLD_CARVE_DYEDREAM_GLASS = ITEMS.register("gold_carve_dyedream_glass",
            () -> new BlockItem(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GOLD_CARVE_DYEDREAM_GLASS_PANE = ITEMS.register("gold_carve_dyedream_glass_pane",
            () -> new BlockItem(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS_PANE.get(), new Item.Properties()));

    public static final RegistryObject<Item> SMALL_DYEDREAM_BUD = ITEMS.register("small_dyedream_bud",
            () -> new BlockItem(ModBlocks.SMALL_DYEDREAM_BUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> MEDIUM_DYEDREAM_BUD = ITEMS.register("medium_dyedream_bud",
            () -> new BlockItem(ModBlocks.MEDIUM_DYEDREAM_BUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> LARGE_DYEDREAM_BUD = ITEMS.register("large_dyedream_bud",
            () -> new BlockItem(ModBlocks.LARGE_DYEDREAM_BUD.get(), new Item.Properties()));

    public static final RegistryObject<Item> DYEDREAM_BUD_BLOCK = ITEMS.register("dyedream_bud_block",
            () -> new BlockItem(ModBlocks.DYEDREAM_BUD_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_BUD_STAIRS = ITEMS.register("dyedream_bud_stairs",
            () -> new BlockItem(ModBlocks.DYEDREAM_BUD_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_BUD_SLAB = ITEMS.register("dyedream_bud_slab",
            () -> new BlockItem(ModBlocks.DYEDREAM_BUD_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_BUD_WALL = ITEMS.register("dyedream_bud_wall",
            () -> new BlockItem(ModBlocks.DYEDREAM_BUD_WALL.get(), new Item.Properties()));

    public static final RegistryObject<Item> DYEDREAM_ICE = ITEMS.register("dyedream_ice",
            () -> new BlockItem(ModBlocks.DYEDREAM_ICE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_PACKED_ICE = ITEMS.register("dyedream_packed_ice",
            () -> new BlockItem(ModBlocks.DYEDREAM_PACKED_ICE.get(), new Item.Properties()));
    public static final RegistryObject<Item> BIG_BUBBLE = ITEMS.register("big_bubble",
            () -> new BlockItem(ModBlocks.BIG_BUBBLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CLOUD = ITEMS.register("cloud",
            () -> new BlockItem(ModBlocks.CLOUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARK_CLOUD = ITEMS.register("dark_cloud",
            () -> new BlockItem(ModBlocks.DARK_CLOUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_SEA_LANTERN = ITEMS.register("dyedream_sea_lantern",
            () -> new BlockItem(ModBlocks.DYEDREAM_SEA_LANTERN.get(), new Item.Properties()));

    public static final RegistryObject<Item> ICE_STONE = ITEMS.register("ice_stone",
            () -> new BlockItem(ModBlocks.ICE_STONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICE_BUD = ITEMS.register("ice_bud",
            () -> new BlockItem(ModBlocks.ICE_BUD.get(), new Item.Properties()));

    // ===== 染梦石英建材系列 =====

    public static final RegistryObject<Item> DYEDREAM_QUARTZ_BLOCK = ITEMS.register("dyedream_quartz_block",
            () -> new BlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SMOOTH_DYEDREAM_QUARTZ_BLOCK = ITEMS.register("smooth_dyedream_quartz_block",
            () -> new BlockItem(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> BRICKS_DYEDREAM_QUARTZ_BLOCK = ITEMS.register("bricks_dyedream_quartz_block",
            () -> new BlockItem(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> PILLAR_DYEDREAM_QUARTZ_BLOCK = ITEMS.register("pillar_dyedream_quartz_block",
            () -> new BlockItem(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_DYEDREAM_QUARTZ_BLOCK = ITEMS.register("chiseled_dyedream_quartz_block",
            () -> new BlockItem(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_QUARTZ_BLOCK_STAIRS = ITEMS.register("dyedream_quartz_block_stairs",
            () -> new BlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_QUARTZ_BLOCK_SLAB = ITEMS.register("dyedream_quartz_block_slab",
            () -> new BlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_QUARTZ_BLOCK_WALL = ITEMS.register("dyedream_quartz_block_wall",
            () -> new BlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get(), new Item.Properties()));

    // ===== 染梦木板建材系列 =====

    public static final RegistryObject<Item> DYEDREAM_PLANKS = ITEMS.register("dyedream_planks",
            () -> new BlockItem(ModBlocks.DYEDREAM_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_STAIRS = ITEMS.register("dyedream_stairs",
            () -> new BlockItem(ModBlocks.DYEDREAM_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_SLAB = ITEMS.register("dyedream_slab",
            () -> new BlockItem(ModBlocks.DYEDREAM_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_FENCE = ITEMS.register("dyedream_fence",
            () -> new BlockItem(ModBlocks.DYEDREAM_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_FENCE_GATE = ITEMS.register("dyedream_fence_gate",
            () -> new BlockItem(ModBlocks.DYEDREAM_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_PANE = ITEMS.register("dyedream_pane",
            () -> new BlockItem(ModBlocks.DYEDREAM_PANE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_DOOR = ITEMS.register("dyedream_door",
            () -> new BlockItem(ModBlocks.DYEDREAM_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_TRAPDOOR = ITEMS.register("dyedream_trapdoor",
            () -> new BlockItem(ModBlocks.DYEDREAM_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_PRESSURE_PLATE = ITEMS.register("dyedream_pressure_plate",
            () -> new BlockItem(ModBlocks.DYEDREAM_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_BUTTON = ITEMS.register("dyedream_button",
            () -> new BlockItem(ModBlocks.DYEDREAM_BUTTON.get(), new Item.Properties()));

    // ===== 染梦合金块 =====
    public static final RegistryObject<Item> DYEDREAM_ALLOY_BLOCK = ITEMS.register("dyedream_alloy_block",
            () -> new BlockItem(ModBlocks.DYEDREAM_ALLOY_BLOCK.get(), new Item.Properties()));

    // ===== 方解石系列 =====
    public static final RegistryObject<Item> POLISHED_CALCITE = ITEMS.register("polished_calcite",
            () -> new BlockItem(ModBlocks.POLISHED_CALCITE.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_CALCITE_STAIRS = ITEMS.register("polished_calcite_stairs",
            () -> new BlockItem(ModBlocks.POLISHED_CALCITE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_CALCITE_SLAB = ITEMS.register("polished_calcite_slab",
            () -> new BlockItem(ModBlocks.POLISHED_CALCITE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_CALCITE_WALL = ITEMS.register("polished_calcite_wall",
            () -> new BlockItem(ModBlocks.POLISHED_CALCITE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> CALCITE_TILES = ITEMS.register("calcite_tiles",
            () -> new BlockItem(ModBlocks.CALCITE_TILES.get(), new Item.Properties()));
    public static final RegistryObject<Item> CALCITE_TILES_STAIRS = ITEMS.register("calcite_tiles_stairs",
            () -> new BlockItem(ModBlocks.CALCITE_TILES_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CALCITE_TILES_SLAB = ITEMS.register("calcite_tiles_slab",
            () -> new BlockItem(ModBlocks.CALCITE_TILES_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> CALCITE_TILES_WALL = ITEMS.register("calcite_tiles_wall",
            () -> new BlockItem(ModBlocks.CALCITE_TILES_WALL.get(), new Item.Properties()));

    //流体桶物品
    public static final RegistryObject<Item> MELT_DREAM_LIQUID_BUCKET = ITEMS.register("melt_dream_liquid_bucket", () -> new BucketItem(ModFluids.MELT_DREAM_LIQUID, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> SHADOW_LIQUID_BUCKET = ITEMS.register("shadow_liquid_bucket", () -> new BucketItem(ModFluids.SHADOW_LIQUID, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.COMMON)));

    //方块实体对应物品
    public static final RegistryObject<Item> CLAYPAN = ITEMS.register("claypan", () -> new BlockItem(ModBlocks.CLAYPAN.get(), new Item.Properties()));
    public static final RegistryObject<Item> DREAM_CAULDRON = ITEMS.register("dream_cauldron", () -> new BlockItem(ModBlocks.DREAM_CAULDRON.get(), new Item.Properties()));

    public static final RegistryObject<Item> MORTAR = ITEMS.register("mortar", () -> new MortarItem(new Item.Properties()));

    public static final RegistryObject<Item> DREAM_NOTES_DYEDREAM_WORLD = ITEMS.register("dream_notes_dyedream_world", () -> new DreamNotesItem(new Item.Properties()));
    public static final RegistryObject<Item> DREAM_NOTES_LAMP_SHADOW_WORLD = ITEMS.register("dream_notes_lamp_shadow_world", () -> new DreamNotesItem(new Item.Properties()));
    public static final RegistryObject<Item> DREAM_NOTES_WIND_JOURNEY_WORLD = ITEMS.register("dream_notes_wind_journey_world", () -> new DreamNotesItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
