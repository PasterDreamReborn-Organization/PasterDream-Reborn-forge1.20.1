package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.PasterDreamDrinkAndFoodProperties;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.picnicbasket.PicnicBasketItem;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.shadowchest.ShadowChestItem;
import com.pasterdream.pasterdreammod.world.block.cropblock.MatureCropItem;
import com.pasterdream.pasterdreammod.world.block.doll.qymdoll.QYMDollItem;
import com.pasterdream.pasterdreammod.world.block.doll.uuzdoll.UUZDollItem;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronItem;
import com.pasterdream.pasterdreammod.world.item.blueprints.BluePrintItem;
import com.pasterdream.pasterdreammod.world.item.BlueDewItem;
import com.pasterdream.pasterdreammod.world.item.RedDewItem;
import com.pasterdream.pasterdreammod.world.item.dreamnotes.DreamNotesItem;
import com.pasterdream.pasterdreammod.world.item.drinkandfooditem.PasterDreamDrinkItem;
import com.pasterdream.pasterdreammod.world.item.drinkandfooditem.PasterDreamFoodItem;
import com.pasterdream.pasterdreammod.world.item.ModArmorMaterials;
import com.pasterdream.pasterdreammod.world.item.ModToolTiers;
import com.pasterdream.pasterdreammod.world.item.armoritem.DyedreamArmorItem;
import com.pasterdream.pasterdreammod.world.item.DyedreamHammerItem;
import com.pasterdream.pasterdreammod.world.item.hellfiretool.*;
import com.pasterdream.pasterdreammod.world.item.meltdreamtool.*;
import com.pasterdream.pasterdreammod.world.item.moltengoldtool.*;
import com.pasterdream.pasterdreammod.world.item.meltdreamtool.SharpMeltDreamSwordItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.SculkArmorItem;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarItem;
import com.pasterdream.pasterdreammod.world.item.MeltDreamCoinItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
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
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

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

    public static final RegistryObject<Item> MELT_DREAM_COIN = ITEMS.register("melt_dream_coin",
            () -> new MeltDreamCoinItem(new Item.Properties(), false));
    public static final RegistryObject<Item> MELT_DREAM_COIN_PILE = ITEMS.register("melt_dream_coin_pile",
            () -> new MeltDreamCoinItem(new Item.Properties(), true));

    public static final RegistryObject<Item> DREAM_FERTILIZER = ITEMS.register("dream_fertilizer", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DYEDREAM_COROLLA = ITEMS.register("dyedream_corolla", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WHITE_COROLLA = ITEMS.register("white_corolla", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_BALL = ITEMS.register("light_ball", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COTTON = ITEMS.register("cotton", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SCULK_HEART = ITEMS.register("sculk_heart",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BLACK_STICK = ITEMS.register("black_stick",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SPOOL = ITEMS.register("spool",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FABRIC = ITEMS.register("fabric",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PROTECT_DECK = ITEMS.register("protect_deck",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> REED_ROD = ITEMS.register("reed_rod",
            () -> new Item(new Item.Properties()) {
                @Override
                public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                    return 100;
                }
            });

    // ===== 升级套件 =====
    public static final RegistryObject<Item> TITANIUM_UPGRADE = ITEMS.register("titanium_upgrade",
            () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> SCULK_UPGRADE = ITEMS.register("sculk_upgrade",
            () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> DYEDREAM_UPGRADE = ITEMS.register("dyedream_upgrade",
            () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)));

    // ===== 铜工具 =====
    public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword",
            () -> new SwordItem(ModToolTiers.COPPER, 3, -2.4f, new Item.Properties()));
    public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe",
            () -> new PickaxeItem(ModToolTiers.COPPER, 1, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe",
            () -> new AxeItem(ModToolTiers.COPPER, 6.5f, -3.15f, new Item.Properties()));
    public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel",
            () -> new ShovelItem(ModToolTiers.COPPER, 1.5f, -3.0f, new Item.Properties()));
    public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe",
            () -> new HoeItem(ModToolTiers.COPPER, 0, -1.5f, new Item.Properties()));

    // ===== 铜装备 =====
    public static final RegistryObject<Item> COPPER_HELMET = ITEMS.register("copper_helmet",
            () -> new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate",
            () -> new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings",
            () -> new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> COPPER_BOOTS = ITEMS.register("copper_boots",
            () -> new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.BOOTS, new Item.Properties()));

    // ===== 钛金工具 =====
    public static final RegistryObject<Item> TITANIUM_SWORD = ITEMS.register("titanium_sword",
            () -> new SwordItem(ModToolTiers.TITANIUM, 3, -2.4f, new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_PICKAXE = ITEMS.register("titanium_pickaxe",
            () -> new PickaxeItem(ModToolTiers.TITANIUM, 1, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_AXE = ITEMS.register("titanium_axe",
            () -> new AxeItem(ModToolTiers.TITANIUM, 5.5f, -3.0f, new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_SHOVEL = ITEMS.register("titanium_shovel",
            () -> new ShovelItem(ModToolTiers.TITANIUM, 2.0f, -3.0f, new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_HOE = ITEMS.register("titanium_hoe",
            () -> new HoeItem(ModToolTiers.TITANIUM, -3, 0.0f, new Item.Properties()));

    // ===== 染梦合金工具 =====
    public static final RegistryObject<Item> DYEDREAM_SWORD = ITEMS.register("dyedream_sword",
            () -> new SwordItem(ModToolTiers.DYEDREAM, 3, -2.4f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DYEDREAM_PICKAXE = ITEMS.register("dyedream_pickaxe",
            () -> new PickaxeItem(ModToolTiers.DYEDREAM, 1, -2.8f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DYEDREAM_AXE = ITEMS.register("dyedream_axe",
            () -> new AxeItem(ModToolTiers.DYEDREAM, 5.5f, -3.0f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DYEDREAM_SHOVEL = ITEMS.register("dyedream_shovel",
            () -> new ShovelItem(ModToolTiers.DYEDREAM, 1.5f, -3.0f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DYEDREAM_HOE = ITEMS.register("dyedream_hoe",
            () -> new HoeItem(ModToolTiers.DYEDREAM, -3, 0.0f, new Item.Properties().rarity(Rarity.UNCOMMON)));

    // ===== 融梦水晶工具 =====
    public static final RegistryObject<Item> MELT_DREAM_PICKAXE = ITEMS.register("melt_dream_pickaxe",
            () -> new MeltDreamPickaxeItem(ModToolTiers.MELT_DREAM, 1, -2.8f, new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MELT_DREAM_AXE = ITEMS.register("melt_dream_axe",
            () -> new MeltDreamAxeItem(ModToolTiers.MELT_DREAM, 5.5f, -3.0f, new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MELT_DREAM_SHOVEL = ITEMS.register("melt_dream_shovel",
            () -> new MeltDreamShovelItem(ModToolTiers.MELT_DREAM, 1.5f, -3.0f, new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MELT_DREAM_HOE = ITEMS.register("melt_dream_hoe",
            () -> new MeltDreamHoeItem(ModToolTiers.MELT_DREAM, -3, 0.5f, new Item.Properties().fireResistant().rarity(Rarity.RARE)));

    // ===== 炙焰金工具 =====
    public static final RegistryObject<Item> MOLTEN_GOLD_SWORD = ITEMS.register("molten_gold_sword",
            () -> new MoltenGoldSwordItem(ModToolTiers.MOLTEN_GOLD, 3, -2.3f, new Item.Properties()));
    public static final RegistryObject<Item> MOLTEN_GOLD_PICKAXE = ITEMS.register("molten_gold_pickaxe",
            () -> new MoltenGoldPickaxeItem(ModToolTiers.MOLTEN_GOLD, 1, -2.7f, new Item.Properties()));
    public static final RegistryObject<Item> MOLTEN_GOLD_AXE = ITEMS.register("molten_gold_axe",
            () -> new MoltenGoldAxeItem(ModToolTiers.MOLTEN_GOLD, 6.0f, -3.0f, new Item.Properties()));
    public static final RegistryObject<Item> MOLTEN_GOLD_SHOVEL = ITEMS.register("molten_gold_shovel",
            () -> new MoltenGoldShovelItem(ModToolTiers.MOLTEN_GOLD, 1.5f, -2.9f, new Item.Properties()));
    public static final RegistryObject<Item> MOLTEN_GOLD_HOE = ITEMS.register("molten_gold_hoe",
            () -> new MoltenGoldHoeItem(ModToolTiers.MOLTEN_GOLD, -2, -0.5f, new Item.Properties()));

    // ===== 狱炎工具 =====
    public static final RegistryObject<Item> HELLFIRE_SWORD = ITEMS.register("hellfire_sword",
            () -> new HellfireSwordItem(ModToolTiers.HELLFIRE, 3, -2.2f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> HELLFIRE_PICKAXE = ITEMS.register("hellfire_pickaxe",
            () -> new HellfirePickaxeItem(ModToolTiers.HELLFIRE, 1, -2.6f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> INFERNO_SWORD = ITEMS.register("inferno_sword",
            () -> new InfernoSwordItem(ModToolTiers.HELLFIRE, 4, -2.15f, new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> DYEDREAM_HAMMER = ITEMS.register("dyedream_hammer",
            () -> new DyedreamHammerItem(ModToolTiers.DYEDREAM, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SHARP_MELT_DREAM_SWORD = ITEMS.register("sharp_melt_dream_sword",
            () -> new SharpMeltDreamSwordItem(ModToolTiers.MELT_DREAM, new Item.Properties().rarity(Rarity.RARE)));

    // ===== 钛金装备 =====
    public static final RegistryObject<Item> TITANIUM_HELMET = ITEMS.register("titanium_helmet",
            () -> new ArmorItem(ModArmorMaterials.TITANIUM, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_CHESTPLATE = ITEMS.register("titanium_chestplate",
            () -> new ArmorItem(ModArmorMaterials.TITANIUM, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_LEGGINGS = ITEMS.register("titanium_leggings",
            () -> new ArmorItem(ModArmorMaterials.TITANIUM, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_BOOTS = ITEMS.register("titanium_boots",
            () -> new ArmorItem(ModArmorMaterials.TITANIUM, ArmorItem.Type.BOOTS, new Item.Properties()));

    // ===== 回响装备 =====
    public static final RegistryObject<Item> SCULK_HELMET = ITEMS.register("sculk_helmet",
            () -> new SculkArmorItem(ModArmorMaterials.SCULK, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> SCULK_CHESTPLATE = ITEMS.register("sculk_chestplate",
            () -> new ArmorItem(ModArmorMaterials.SCULK, ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> SCULK_LEGGINGS = ITEMS.register("sculk_leggings",
            () -> new ArmorItem(ModArmorMaterials.SCULK, ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> SCULK_BOOTS = ITEMS.register("sculk_boots",
            () -> new ArmorItem(ModArmorMaterials.SCULK, ArmorItem.Type.BOOTS, new Item.Properties().fireResistant()));

    // ===== 染梦合金装备 =====
    public static final RegistryObject<Item> DYEDREAM_HELMET = ITEMS.register("dyedream_helmet",
            () -> new DyedreamArmorItem(ModArmorMaterials.DYEDREAM, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DYEDREAM_CHESTPLATE = ITEMS.register("dyedream_chestplate",
            () -> new ArmorItem(ModArmorMaterials.DYEDREAM, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DYEDREAM_LEGGINGS = ITEMS.register("dyedream_leggings",
            () -> new ArmorItem(ModArmorMaterials.DYEDREAM, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DYEDREAM_BOOTS = ITEMS.register("dyedream_boots",
            () -> new ArmorItem(ModArmorMaterials.DYEDREAM, ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> MELT_DREAM_CRYSTAL_FRAGMENT = ITEMS.register("melt_dream_crystal_fragment",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)) {
                @Override
                public boolean isFoil(ItemStack stack) {
                    return true;
                }
            });

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
    public static final RegistryObject<Item> GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA = ITEMS.register("glass_cup_of_cooked_dyedream_flower_tea", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.HEAL, 1, 0), 1.0f).alwaysEat().build())) {
        @Override
        public boolean isFoil(ItemStack stack) {
            return true;
        }
    });
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
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.5f).alwaysEat().build())){
                @Override
                protected void onFoodSpecial(Player player, Level level) {
                    if (player.isOnFire()) {
                        int remainingTicks = player.getRemainingFireTicks();
                        player.setRemainingFireTicks(Math.max(0, remainingTicks - 100));
                    }
                }
            });//减少燃烧时间

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
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(15).meltDreamEnergyAdd(15).rarity(Rarity.EPIC)
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
                    .food(new FoodProperties.Builder().nutrition(10).saturationMod(1.2f).alwaysEat().build())
                    .rarity(Rarity.EPIC)) {
                @Override
                protected void onFoodSpecial(Player player, Level level) {
                    if (!level.isClientSide && !player.isCreative()) {
                        ItemStack containerStack = new ItemStack(Items.BOWL);
                        if (!player.getInventory().add(containerStack)) {
                            player.drop(containerStack, false);
                        }
                    }
                }
            });

    public static final RegistryObject<Item> ELIXIR_BOTTLE = ITEMS.register("elixir_bottle",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ELIXIR_BOTTLE_OF_MELT_DREAM = ITEMS.register("elixir_bottle_of_melt_dream",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().stacksTo(1).meltDreamEnergyAdd(25).rarity(Rarity.UNCOMMON)
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(2).alwaysEat().build())));

    public static final RegistryObject<Item> ELIXIR_BOTTLE_OF_RAGE_ELIXIR = ITEMS.register("elixir_bottle_of_rage_elixir",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().stacksTo(1).rarity(Rarity.UNCOMMON)
                    .food(new FoodProperties.Builder().alwaysEat().build())));

    public static final RegistryObject<Item> PINEAPPLE_LOVE_SEA = ITEMS.register("pineapple_love_sea",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().sanAdd(15).rarity(Rarity.UNCOMMON)
                    .food(new FoodProperties.Builder().nutrition(3).saturationMod(0.5f).alwaysEat().build())));

    // ===== 露滴 =====
    public static final RegistryObject<Item> RED_DEW = ITEMS.register("red_dew",
            () -> new RedDewItem());
    public static final RegistryObject<Item> BLUE_DEW = ITEMS.register("blue_dew",
            () -> new BlueDewItem());

    // ===== 方块物品 =====

    public static final RegistryObject<Item> DYEDREAM_DIRT = ITEMS.register("dyedream_dirt",
            () -> new BlockItem(ModBlocks.DYEDREAM_DIRT.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_GRASS_BLOCK = ITEMS.register("dyedream_grass_block",
            () -> new BlockItem(ModBlocks.DYEDREAM_GRASS_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_FARMLAND = ITEMS.register("dyedream_farmland",
            () -> new BlockItem(ModBlocks.DYEDREAM_FARMLAND.get(), new Item.Properties()));
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

    public static final RegistryObject<Item> DYEDREAM_BUDDING_BLOCK = ITEMS.register("dyedream_budding_block",
            () -> new BlockItem(ModBlocks.DYEDREAM_BUDDING_BLOCK.get(), new Item.Properties()));
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
    public static final RegistryObject<Item> DYEDREAM_CRYSTAL_LANTERN = ITEMS.register("dyedream_crystal_lantern",
            () -> new BlockItem(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_LANTERN = ITEMS.register("dyedream_lantern",
            () -> new BlockItem(ModBlocks.DYEDREAM_LANTERN.get(), new Item.Properties()));

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

    // =====植物系列（花） =====
    public static final RegistryObject<Item> GOLDENROD = ITEMS.register("goldenrod",
            () -> new BlockItem(ModBlocks.GOLDENROD.get(), new Item.Properties()));

    public static final RegistryObject<Item> FERRARIA_CRISPA = ITEMS.register("ferraria_crispa",//f1
            () -> new BlockItem(ModBlocks.FERRARIA_CRISPA.get(), new Item.Properties()));

    public static final RegistryObject<Item> EUSTOMA = ITEMS.register("eustoma",//f2
            () -> new BlockItem(ModBlocks.EUSTOMA.get(), new Item.Properties()));

    public static final RegistryObject<Item> MALVA_SINENSIS_CAVAN = ITEMS.register("malva_sinensis_cavan",//f5
            () -> new BlockItem(ModBlocks.MALVA_SINENSIS_CAVAN.get(), new Item.Properties()));

    public static final RegistryObject<Item> BLAZE_FLOWER = ITEMS.register("blaze_flower",//f6
            () -> new BlockItem(ModBlocks.BLAZE_FLOWER.get(), new Item.Properties()));

    public static final RegistryObject<Item> LINHT_FLOWER = ITEMS.register("linht_flower",
            () -> new BlockItem(ModBlocks.LINHT_FLOWER.get(), new Item.Properties()));//f9

    public static final RegistryObject<Item> DREAMING_LOTUS = ITEMS.register("dreaming_lotus",
            () -> new BlockItem(ModBlocks.DREAMING_LOTUS.get(), new Item.Properties()));//f11

    public static final RegistryObject<Item> MISTY_DREAMING_LOTUS = ITEMS.register("misty_dreaming_lotus",
            () -> new BlockItem(ModBlocks.MISTY_DREAMING_LOTUS.get(), new Item.Properties()));//f12

    public static final RegistryObject<Item> DYEDREAM_LILY_OF_THE_VALLEY = ITEMS.register("dyedream_lily_of_the_valley",
            () -> new BlockItem(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get(), new Item.Properties()));//f13

    public static final RegistryObject<Item> WHITE_ORCHID_FLOWER = ITEMS.register("white_orchid_flower",
            () -> new BlockItem(ModBlocks.WHITE_ORCHID_FLOWER.get(), new Item.Properties()));//f15

    public static final RegistryObject<Item> EDELWEISS = ITEMS.register("edelweiss",
            () -> new BlockItem(ModBlocks.EDELWEISS.get(), new Item.Properties()));//f16

    public static final RegistryObject<Item> NIPPY_EDELWEISS = ITEMS.register("nippy_edelweiss",
            () -> new BlockItem(ModBlocks.NIPPY_EDELWEISS.get(), new Item.Properties()));//f17

    public static final RegistryObject<Item> DYEDREAM_LILY_PAD = ITEMS.register("dyedream_lily_pad",
            () -> new PlaceOnWaterBlockItem(ModBlocks.DYEDREAM_LILY_PAD.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_LOTUS = ITEMS.register("dyedream_lotus",
            () -> new PlaceOnWaterBlockItem(ModBlocks.DYEDREAM_LOTUS.get(), new Item.Properties()));

    // =====植物系列（草） =====
    public static final RegistryObject<Item> DYEDREAM_MOSS = ITEMS.register("dyedream_moss",
            () -> new BlockItem(ModBlocks.DYEDREAM_MOSS.get(), new Item.Properties()));

    public static final RegistryObject<Item> STEM_GRASS = ITEMS.register("stem_grass",
            () -> new BlockItem(ModBlocks.STEM_GRASS.get(), new Item.Properties()));

    public static final RegistryObject<Item> TALL_STEM_GRASS = ITEMS.register("tall_stem_grass",
            () -> new BlockItem(ModBlocks.TALL_STEM_GRASS.get(), new Item.Properties()));

    public static final RegistryObject<Item> SINGULARITY_FERN = ITEMS.register("singularity_fern",
            () -> new BlockItem(ModBlocks.SINGULARITY_FERN.get(), new Item.Properties()));//f14

    public static final RegistryObject<Item> CRIMSON_THORNS = ITEMS.register("crimson_thorns",
            () -> new BlockItem(ModBlocks.CRIMSON_THORNS.get(), new Item.Properties()));//f7

    public static final RegistryObject<Item> OATS = ITEMS.register("oats",
            () -> new BlockItem(ModBlocks.OATS.get(), new Item.Properties()));//g2

    public static final RegistryObject<Item> RYE = ITEMS.register("rye",
            () -> new BlockItem(ModBlocks.RYE.get(), new Item.Properties()));//f3

    public static final RegistryObject<Item> POLISHED_CALCITE_STALICRIPE = ITEMS.register("polished_calcite_stalicripe",
            () -> new BlockItem(ModBlocks.POLISHED_CALCITE_STALICRIPE.get(), new Item.Properties()));//g5

    public static final RegistryObject<Item> SMALL_POLISHED_CALCITE_STALICRIPE = ITEMS.register("small_polished_calcite_stalicripe",
            () -> new BlockItem(ModBlocks.SMALL_POLISHED_CALCITE_STALICRIPE.get(), new Item.Properties()));//g6

    public static final RegistryObject<Item> DYEDREAM_SEAGRASS = ITEMS.register("dyedream_seagrass",
            () -> new BlockItem(ModBlocks.DYEDREAM_SEAGRASS.get(), new Item.Properties()));

    public static final RegistryObject<Item> REED = ITEMS.register("reed",
        () -> new BlockItem(ModBlocks.REED.get(), new Item.Properties()));//g1

    public static final RegistryObject<Item> DYEDREAM_VINE = ITEMS.register("dyedream_vine",
            () -> new BlockItem(ModBlocks.DYEDREAM_VINE.get(), new Item.Properties()));
    //作物方块物品
    public static final RegistryObject<Item> DYEDREAM_COROLLA_CROP_AGE_0 = ITEMS.register("dyedream_corolla_crop_age_0", () -> new BlockItem(ModBlocks.DYEDREAM_COROLLA_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> WHITE_COROLLA_CROP_AGE_0 = ITEMS.register("white_corolla_crop_age_0", () -> new BlockItem(ModBlocks.WHITE_COROLLA_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_BALL_CROP_AGE_0 = ITEMS.register("light_ball_crop_age_0", () -> new BlockItem(ModBlocks.LIGHT_BALL_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> CLOUD_CROP_AGE_0 = ITEMS.register("cloud_crop_age_0", () -> new BlockItem(ModBlocks.CLOUD_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> COTTON_CROP_AGE_0 = ITEMS.register("cotton_crop_age_0", () -> new BlockItem(ModBlocks.COTTON_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> DYEDREAM_COROLLA_CROP_AGE_1 = ITEMS.register("dyedream_corolla_crop_age_1", () -> new MatureCropItem(new Item.Properties(), ModBlocks.DYEDREAM_COROLLA_CROP.get()));
    public static final RegistryObject<Item> WHITE_COROLLA_CROP_AGE_1 = ITEMS.register("white_corolla_crop_age_1", () -> new MatureCropItem(new Item.Properties(), ModBlocks.WHITE_COROLLA_CROP.get()));
    public static final RegistryObject<Item> LIGHT_BALL_CROP_AGE_1 = ITEMS.register("light_ball_crop_age_1", () -> new MatureCropItem(new Item.Properties(), ModBlocks.LIGHT_BALL_CROP.get()));
    public static final RegistryObject<Item> CLOUD_CROP_AGE_1 = ITEMS.register("cloud_crop_age_1", () -> new MatureCropItem(new Item.Properties(), ModBlocks.CLOUD_CROP.get()));
    public static final RegistryObject<Item> COTTON_CROP_AGE_1 = ITEMS.register("cotton_crop_age_1", () -> new MatureCropItem(new Item.Properties(), ModBlocks.COTTON_CROP.get()));




    //流体桶物品
    public static final RegistryObject<Item> MELT_DREAM_LIQUID_BUCKET = ITEMS.register("melt_dream_liquid_bucket", () -> new BucketItem(ModFluids.MELT_DREAM_LIQUID, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.RARE).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> SHADOW_LIQUID_BUCKET = ITEMS.register("shadow_liquid_bucket", () -> new BucketItem(ModFluids.SHADOW_LIQUID, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.COMMON)));

    //方块实体对应物品
    public static final RegistryObject<Item> QYM_DOLL = ITEMS.register("qym_doll", () -> new QYMDollItem(ModBlocks.QYM_DOLL.get(), new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> UUZ_DOLL = ITEMS.register("uuz_doll", () -> new UUZDollItem(ModBlocks.UUZ_DOLL.get(), new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> DYEDREAM_CRACK = ITEMS.register("dyedream_crack", () -> new BlockItem(ModBlocks.DYEDREAM_CRACK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CLAYPAN = ITEMS.register("claypan", () -> new BlockItem(ModBlocks.CLAYPAN.get(), new Item.Properties()));
    public static final RegistryObject<Item> DREAM_CAULDRON = ITEMS.register("dream_cauldron", () -> new DreamCauldronItem(ModBlocks.DREAM_CAULDRON.get(), new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_DESK = ITEMS.register("dyedream_desk", () -> new BlockItem(ModBlocks.DYEDREAM_DESK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_DESK = ITEMS.register("shadow_desk", () -> new BlockItem(ModBlocks.SHADOW_DESK.get(), new Item.Properties()));
    public static final RegistryObject<Item> PICNIC_BASKET = ITEMS.register("picnic_basket", () -> new PicnicBasketItem(ModBlocks.PICNIC_BASKET.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_CHEST = ITEMS.register("shadow_chest", () -> new ShadowChestItem(ModBlocks.SHADOW_CHEST.get(), new Item.Properties()));
    public static final RegistryObject<Item> WIND_MOOR_CRATE = ITEMS.register("wind_moor_crate", () -> new BlockItem(ModBlocks.WIND_MOOR_CRATE.get(), new Item.Properties()));

    public static final RegistryObject<Item> MORTAR = ITEMS.register("mortar", () -> new MortarItem(new Item.Properties()));

    public static final RegistryObject<Item> DREAM_NOTES_DYEDREAM_WORLD = ITEMS.register("dream_notes_dyedream_world", () -> new DreamNotesItem(new Item.Properties()));
    public static final RegistryObject<Item> DREAM_NOTES_LAMP_SHADOW_WORLD = ITEMS.register("dream_notes_lamp_shadow_world", () -> new DreamNotesItem(new Item.Properties()));
    public static final RegistryObject<Item> DREAM_NOTES_WIND_JOURNEY_WORLD = ITEMS.register("dream_notes_wind_journey_world", () -> new DreamNotesItem(new Item.Properties()));
    public static final RegistryObject<Item> BLUE_PRINT = ITEMS.register("blue_print", () -> new BluePrintItem(new Item.Properties()));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
