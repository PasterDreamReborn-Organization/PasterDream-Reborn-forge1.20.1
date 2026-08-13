package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.PasterDreamDrinkAndFoodProperties;
import com.pasterdream.pasterdreammod.tag.ModItemTags;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.shadowchest.ShadowChestItem;
import com.pasterdream.pasterdreammod.world.block.cropblock.MatureCropItem;
import com.pasterdream.pasterdreammod.world.block.doll.qymdoll.QYMDollItem;
import com.pasterdream.pasterdreammod.world.block.doll.uuzdoll.UUZDollItem;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorItem;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceItem;
import com.pasterdream.pasterdreammod.world.block.shadowdungeongate.barrier.ShadowDungeonBarrierItem;
import com.pasterdream.pasterdreammod.world.block.shadowdungeongate.gate.ShadowDungeonGateItem;
import com.pasterdream.pasterdreammod.world.block.twilightlantern.TwilightLanternDisplayItem;
import com.pasterdream.pasterdreammod.world.block.shadowdungeonportal.BrokenShadowDungeonPortalDisplayItem;
import com.pasterdream.pasterdreammod.world.block.shadowdungeonportal.ShadowDungeonPortalDisplayItem;
import net.minecraft.world.item.BlockItem;
import com.pasterdream.pasterdreammod.world.block.shadowvortex.ShadowVortexDisplayItem;
import com.pasterdream.pasterdreammod.world.block.shadowhandtrap.ShadowHandTrapItem;
import com.pasterdream.pasterdreammod.world.block.shadowbrazier.ShadowBrazierDisplayItem;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronItem;
import com.pasterdream.pasterdreammod.world.block.meltdreamcrystalchest.MeltDreamCrystalChestItem;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.openedmeltdreamcrystalchest.OpenedMeltDreamCrystalChestItem;
import com.pasterdream.pasterdreammod.world.block.researchtable.ResearchTableItem;
import com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers.TheEndlessBookOfDreamSeekersItem;
import com.pasterdream.pasterdreammod.world.block.lifecrystal.LifeCrystalItem;
import com.pasterdream.pasterdreammod.world.block.lostswordtomb.LostSwordTombItem;
import com.pasterdream.pasterdreammod.world.block.goldenfoxsculpture.GoldenFoxSculptureItem;
import com.pasterdream.pasterdreammod.world.block.desertherotomb.DesertHeroTombItem;
import com.pasterdream.pasterdreammod.world.block.foxsculpture.FoxSculptureItem;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace.WeaponWorkshopBlastFurnaceItem;
import com.pasterdream.pasterdreammod.world.item.*;
import com.pasterdream.pasterdreammod.world.item.FireproofBlockItem;
import com.pasterdream.pasterdreammod.world.item.StorgeBagItem.StorageBagItem;
import com.pasterdream.pasterdreammod.world.item.StorgeBagItem.LargeStorageBagItem;
import com.pasterdream.pasterdreammod.world.item.blueprints.BluePrintItem;
import com.pasterdream.pasterdreammod.world.item.curio.*;
import com.pasterdream.pasterdreammod.world.item.curio.FadedBlessingOfCeciliaItem;
import com.pasterdream.pasterdreammod.world.item.curio.KaichuOmamoriItem;
import com.pasterdream.pasterdreammod.world.item.debugsword.DebugSwordItem;
import com.pasterdream.pasterdreammod.world.item.dreamnotes.DreamNotesItem;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookItem;
import com.pasterdream.pasterdreammod.world.item.StoryProgressItem;
import com.pasterdream.pasterdreammod.world.item.drinkandfooditem.PasterDreamDrinkItem;
import com.pasterdream.pasterdreammod.world.item.drinkandfooditem.PasterDreamFoodItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.DyedreamArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.TitaniumArmorItem;
import com.pasterdream.pasterdreammod.world.item.hellfiretool.*;
import com.pasterdream.pasterdreammod.world.item.lootgenerator.LootGeneratorItem;
import com.pasterdream.pasterdreammod.world.item.meltdreamcrystalchestresettool.MeltDreamCrystalChestResetToolItem;
import com.pasterdream.pasterdreammod.world.item.meltdreamtool.*;
import com.pasterdream.pasterdreammod.world.item.modelbreakparticleproviderblockitem.ModelBreakParticleProviderBlockItem;
import com.pasterdream.pasterdreammod.world.item.moltengoldtool.*;
import com.pasterdream.pasterdreammod.world.item.meltdreamtool.SharpMeltDreamSwordItem;
import com.pasterdream.pasterdreammod.world.item.prophecycard.EmptyProphecyCardItem;
import com.pasterdream.pasterdreammod.world.item.prophecycard.ProphecyCardItem;
import com.pasterdream.pasterdreammod.world.item.tidetool.BeihaiRuoTideSwordItem;
import com.pasterdream.pasterdreammod.world.item.tidetool.TideSwordItem;
import com.pasterdream.pasterdreammod.world.item.whiteswordtool.WhiteSwordItem;
import com.pasterdream.pasterdreammod.world.item.shadowswordtool.ShadowSwordItem;
import com.pasterdream.pasterdreammod.world.item.shadowerosiontool.*;
import com.pasterdream.pasterdreammod.world.item.deserttool.DesertSwordItem;
import com.pasterdream.pasterdreammod.world.item.deserttool.ChenjingmenDesertSwordItem;
import com.pasterdream.pasterdreammod.world.item.iceshadowhammertool.IceShadowHammerItem;
import com.pasterdream.pasterdreammod.world.item.TerraBladeItem;
import com.pasterdream.pasterdreammod.world.item.grasstool.KusanagiItem;
import com.pasterdream.pasterdreammod.world.item.grasstool.MurakumoKusanagiItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.SculkArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.qym.QymCatEarsItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.qym.QymWindShirtItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.qym.QymSwayingSkirtItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.qym.QymCloudBootsItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.MachineLightWingItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.AngelWingItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.ForsakensWingItem;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarItem;
import com.pasterdream.pasterdreammod.world.item.MeltDreamCoinItem;
import com.pasterdream.pasterdreammod.world.item.PaleBoneneedleItem;
import com.pasterdream.pasterdreammod.world.item.PliersItem;
import com.pasterdream.pasterdreammod.world.item.RootsPaleBoneneedleItem;
import com.pasterdream.pasterdreammod.world.item.DeepTreasureItem;
import com.pasterdream.pasterdreammod.world.item.DreamHarpOfWandererItem;
import com.pasterdream.pasterdreammod.world.item.StarWishRodItem;
import com.pasterdream.pasterdreammod.world.item.ThermalDaggerItem;
import com.pasterdream.pasterdreammod.world.entity.MeltDreamCrystalEntityEntity;
import com.pasterdream.pasterdreammod.world.entity.ThrownPinkEgg;
import com.pasterdream.pasterdreammod.world.item.PebbleItem;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.CuriosApi;

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

    public static final RegistryObject<Item> MELT_DREAM_AURORIAN_STEEL = ITEMS.register("melt_dream_aurorian_steel",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> MAGIC_STONE = ITEMS.register("magic_stone",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SOUL_DUST = ITEMS.register("soul_dust",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOUL_ESSENCE = ITEMS.register("soul_essence",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHARGED_AMETHYST = ITEMS.register("charged_amethyst",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLACK_METAL_INGOT = ITEMS.register("black_metal_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLACK_METAL_GRAIN = ITEMS.register("black_metal_grain",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUST_BLACK_METAL_GRAIN = ITEMS.register("rust_black_metal_grain",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MELT_DREAM_COIN = ITEMS.register("melt_dream_coin",
            () -> new MeltDreamCoinItem(new Item.Properties(), false));
    public static final RegistryObject<Item> MELT_DREAM_COIN_PILE = ITEMS.register("melt_dream_coin_pile",
            () -> new MeltDreamCoinItem(new Item.Properties(), true));

    public static final RegistryObject<Item> DREAM_FERTILIZER = ITEMS.register("dream_fertilizer", DreamFertilizerItem::new);

    public static final RegistryObject<Item> DYEDREAM_COROLLA = ITEMS.register("dyedream_corolla", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WHITE_COROLLA = ITEMS.register("white_corolla", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WHITE_CRYSTAL = ITEMS.register("white_crystal",
            () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> LIGHT_BALL = ITEMS.register("light_ball",
            () -> new BlockItem(ModBlocks.LIGHT_BALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> COTTON = ITEMS.register("cotton", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SCULK_HEART = ITEMS.register("sculk_heart",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BLACK_STICK = ITEMS.register("black_stick",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> NIGHTMARE_FUEL = ITEMS.register("nightmare_fuel",
            NightmareFuelItem::new);

    public static final RegistryObject<Item> PALE_BONENEEDLE = ITEMS.register("pale_boneneedle",
            PaleBoneneedleItem::new);
    public static final RegistryObject<Item> ROOTS_PALE_BONENEEDLE = ITEMS.register("roots_pale_boneneedle",
            RootsPaleBoneneedleItem::new);

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
    public static final RegistryObject<Item> JUNGLE_SPORE = ITEMS.register("jungle_spore",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0).build())));
    public static final RegistryObject<Item> PERGAMYN = ITEMS.register("pergamyn",
            () -> new Item(new Item.Properties()));

    // ===== 深海秘宝 =====
    public static final RegistryObject<Item> DEEP_SEA_TREASURE = ITEMS.register("deep_sea_treasure",
            () -> new DeepTreasureItem(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON),
                    "pasterdream:chests/deep_sea_treasure",
                    "pasterdream:chests/deep_sea_treasure_super"));
    public static final RegistryObject<Item> DYEDREAM_DEEP_SEA_TREASURE = ITEMS.register("dyedream_deep_sea_treasure",
            () -> new DeepTreasureItem(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON),
                    "pasterdream:chests/dyedream_deep_sea_treasure",
                    "pasterdream:chests/dyedream_deep_sea_treasure_super"));
    public static final RegistryObject<Item> SHADOW_DEEP_SEA_TREASURE = ITEMS.register("shadow_deep_sea_treasure",
            () -> new DeepTreasureItem(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON),
                    "pasterdream:chests/shadow_deep_sea_treasure",
                    "pasterdream:chests/shadow_deep_sea_treasure_super",
                    "tooltip.pasterdream.shadow_deep_treasure"));

    // ===== 星者祈愿钓竿 =====
    public static final RegistryObject<Item> STAR_WISH_ROD = ITEMS.register("star_wish_rod",
            StarWishRodItem::new);

    // ===== 升级套件 =====
    public static final RegistryObject<Item> TITANIUM_UPGRADE = ITEMS.register("titanium_upgrade",
            () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> SCULK_UPGRADE = ITEMS.register("sculk_upgrade",
            () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DYEDREAM_UPGRADE = ITEMS.register("dyedream_upgrade",
            () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)));

    // ===== 强化石 =====
    public static final RegistryObject<Item> ATTACK_ENHANCE_STONE = ITEMS.register("attack_enhance_stone",
            () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON)) {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
                    super.appendHoverText(stack, level, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdream.enhance_stone.usage"));
                    tooltip.add(Component.translatable("tooltip.pasterdream.attack_enhance_stone.effect"));
                }
            });
    public static final RegistryObject<Item> LUCK_ENHANCE_STONE = ITEMS.register("luck_enhance_stone",
            () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON)) {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
                    super.appendHoverText(stack, level, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdream.enhance_stone.usage"));
                    tooltip.add(Component.translatable("tooltip.pasterdream.luck_enhance_stone.effect"));
                }
            });

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

    // 冰影战锤
    public static final RegistryObject<Item> ICE_SHADOW_HAMMER = ITEMS.register("ice_shadow_hammer",
            () -> new IceShadowHammerItem(ModToolTiers.ICE_SHADOW_HAMMER, new Item.Properties()));

    // ===== 影蚀系列 =====
    public static final RegistryObject<Item> SHADOW_EROSION_DAGGER = ITEMS.register("shadow_erosion_dagger",
            () -> new ShadowErosionDaggerItem(ModToolTiers.SHADOW_EROSION_DAGGER, 3, -2.0f, new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_EROSION_PICKAXE = ITEMS.register("shadow_erosion_pickaxe",
            () -> new ShadowErosionPickaxeItem(ModToolTiers.SHADOW_EROSION_PICKAXE, 1, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_EROSION_AXE = ITEMS.register("shadow_erosion_axe",
            () -> new ShadowErosionAxeItem(ModToolTiers.SHADOW_EROSION_AXE, 1, -3.0f, new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_EROSION_SHOVEL = ITEMS.register("shadow_erosion_shovel",
            () -> new ShadowErosionShovelItem(ModToolTiers.SHADOW_EROSION_SHOVEL, 1, -3.0f, new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_EROSION_HOE = ITEMS.register("shadow_erosion_hoe",
            () -> new ShadowErosionHoeItem(ModToolTiers.SHADOW_EROSION_HOE, 0, 0.0f, new Item.Properties()));

    // ===== 独立武器 =====
    public static final RegistryObject<Item> THERMAL_DAGGER = ITEMS.register("thermal_dagger",
            () -> new ThermalDaggerItem(new Item.Properties()));
    public static final RegistryObject<Item> TIDE_SWORD = ITEMS.register("tide_sword",
            () -> new TideSwordItem(ModToolTiers.TIDE_SWORD, 3, -2.8f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEIHAI_RUO_TIDE_SWORD = ITEMS.register("beihairuo_tide_sword",
            () -> new BeihaiRuoTideSwordItem(ModToolTiers.BEIHAI_RUO_TIDE_SWORD, 3, -2.8f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> KUSANAGI = ITEMS.register("kusanagi",
            () -> new KusanagiItem(ModToolTiers.KUSANAGI, 3, -2.5f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MURAKUMO_KUSANAGI = ITEMS.register("murakumo_kusanagi",
            () -> new MurakumoKusanagiItem(ModToolTiers.MURAKUMO_KUSANAGI, 3, -2.5f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SWORD_EMBRYO = ITEMS.register("sword_embryo",
            () -> new SwordItem(Tiers.IRON, 1, -2.4f, new Item.Properties()));

    // 大地之刃
    public static final RegistryObject<Item> TERRA_BLADE = ITEMS.register("terra_blade",
            () -> new TerraBladeItem(ModToolTiers.TERRA_SWORD, 7, -2.4f));

    // ===== 朔漠系列 =====
    public static final RegistryObject<Item> DESERT_SWORD = ITEMS.register("desert_sword",
            () -> new DesertSwordItem(ModToolTiers.DESERT_SWORD, 3, -3.1f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> CHENJINGMEN_DESERT_SWORD = ITEMS.register("chenjingmen_desert_sword",
            () -> new ChenjingmenDesertSwordItem(ModToolTiers.CHENJINGMEN_DESERT_SWORD, 3, -3.1f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BROKEN_HERO_SWORD = ITEMS.register("broken_hero_sword",
            () -> new SwordItem(ModToolTiers.BROKEN_HERO_SWORD, 3, -2.4f, new Item.Properties().fireResistant()));

    // 白厄
    public static final RegistryObject<Item> WHITE_SWORD = ITEMS.register("white_sword",
            () -> new WhiteSwordItem(ModToolTiers.WHITE_SWORD, 3, -2.4f));

    // 影刃
    public static final RegistryObject<Item> SHADOW_SWORD = ITEMS.register("shadow_sword",
            () -> new ShadowSwordItem(ModToolTiers.SHADOW_SWORD, 3, -2.4f));

    // ===== 钛金装备 =====
    public static final RegistryObject<Item> TITANIUM_HELMET = ITEMS.register("titanium_helmet",
            () -> new TitaniumArmorItem(ModArmorMaterials.TITANIUM, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_CHESTPLATE = ITEMS.register("titanium_chestplate",
            () -> new ArmorItem(ModArmorMaterials.TITANIUM, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_LEGGINGS = ITEMS.register("titanium_leggings",
            () -> new ArmorItem(ModArmorMaterials.TITANIUM, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_BOOTS = ITEMS.register("titanium_boots",
            () -> new ArmorItem(ModArmorMaterials.TITANIUM, ArmorItem.Type.BOOTS, new Item.Properties()));

    // ===== 回响装备 =====
    public static final RegistryObject<Item> SCULK_HELMET = ITEMS.register("sculk_helmet",
            () -> new SculkArmorItem(ModArmorMaterials.SCULK, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SCULK_CHESTPLATE = ITEMS.register("sculk_chestplate",
            () -> new ArmorItem(ModArmorMaterials.SCULK, ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SCULK_LEGGINGS = ITEMS.register("sculk_leggings",
            () -> new ArmorItem(ModArmorMaterials.SCULK, ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SCULK_BOOTS = ITEMS.register("sculk_boots",
            () -> new ArmorItem(ModArmorMaterials.SCULK, ArmorItem.Type.BOOTS, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    // ===== 染梦合金装备 =====
    public static final RegistryObject<Item> DYEDREAM_HELMET = ITEMS.register("dyedream_helmet",
            () -> new DyedreamArmorItem(ModArmorMaterials.DYEDREAM, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DYEDREAM_CHESTPLATE = ITEMS.register("dyedream_chestplate",
            () -> new ArmorItem(ModArmorMaterials.DYEDREAM, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final RegistryObject<Item> DYEDREAM_LEGGINGS = ITEMS.register("dyedream_leggings",
            () -> new ArmorItem(ModArmorMaterials.DYEDREAM, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final RegistryObject<Item> DYEDREAM_BOOTS = ITEMS.register("dyedream_boots",
            () -> new ArmorItem(ModArmorMaterials.DYEDREAM, ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()));

    public static final RegistryObject<Item> MELT_DREAM_CRYSTAL_FRAGMENT = ITEMS.register("melt_dream_crystal_fragment",
            () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.EPIC)) {
                @Override
                public boolean isFoil(@NotNull ItemStack stack) {
                    return true;
                }

                @Override
                public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
                    Level level = context.getLevel();
                    var player = context.getPlayer();
                    if (player == null) return InteractionResult.PASS;

                    // 仅潜行时触发
                    if (!player.isShiftKeyDown()) return InteractionResult.PASS;

                    BlockPos placePos = context.getClickedPos().above();
                    double x = placePos.getX() + 0.5;
                    double y = placePos.getY();
                    double z = placePos.getZ() + 0.5;

                    if (!level.getBlockState(placePos).is(Blocks.AIR)) {
                        return InteractionResult.FAIL;
                    }

                    if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
                        context.getItemInHand().shrink(1);
                        var entity = new MeltDreamCrystalEntityEntity(
                                ModEntities.MELT_DREAM_CRYSTAL_ENTITY.get(), level);
                        entity.setPos(x, y, z);
                        entity.setYRot(level.getRandom().nextFloat() * 360F);
                        level.addFreshEntity(entity);
                    }

                    if (!level.isClientSide()) {
                        level.playSound(null, context.getClickedPos(),
                                ForgeRegistries.SOUND_EVENTS.getValue(
                                        ResourceLocation.parse("block.amethyst_block.place")),
                                SoundSource.NEUTRAL, 0.8f, 1.0f);
                    } else {
                        level.playLocalSound(context.getClickedPos().getX(),
                                context.getClickedPos().getY(), context.getClickedPos().getZ(),
                                ForgeRegistries.SOUND_EVENTS.getValue(
                                        ResourceLocation.parse("block.amethyst_block.place")),
                                SoundSource.NEUTRAL, 0.8f, 1.0f, false);
                    }

                    return InteractionResult.sidedSuccess(level.isClientSide());
                }
            });

    // ===== 玻璃罐系列 =====
    public static final RegistryObject<Item> GLASS_JAR = ITEMS.register("glass_jar", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GLASS_JAR_OF_WATER = ITEMS.register("glass_jar_of_water", () -> new PasterDreamDrinkItem((PasterDreamDrinkAndFoodProperties) new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().alwaysEat().build()).useDuration(24).craftRemainder(ModItems.GLASS_JAR.get())));
    public static final RegistryObject<Item> GLASS_JAR_OF_MILK = ITEMS.register("glass_jar_of_milk", () -> new PasterDreamDrinkItem((PasterDreamDrinkAndFoodProperties) new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().alwaysEat().build()).useDuration(24).craftRemainder(ModItems.GLASS_JAR.get())));
    public static final RegistryObject<Item> GLASS_JAR_OF_YEAST = ITEMS.register("glass_jar_of_yeast", () -> new Item(new Item.Properties().craftRemainder(ModItems.GLASS_JAR.get())));
    public static final RegistryObject<Item> GLASS_JAR_OF_GUIDING_DRUG = ITEMS.register("glass_jar_of_guiding_drug", () -> new Item(new Item.Properties().craftRemainder(ModItems.GLASS_JAR.get())));
    public static final RegistryObject<Item> GLASS_JAR_OF_WIND_PLANT_EXTRACT = ITEMS.register("glass_jar_of_wind_plant_extract", () -> new Item(new Item.Properties().craftRemainder(ModItems.GLASS_JAR.get())));
    public static final RegistryObject<Item> GLASS_JAR_OF_DREAM_JUICE = ITEMS.register("glass_jar_of_dream_juice", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().stacksTo(8).food(new FoodProperties.Builder().alwaysEat().build()).useDuration(24).craftRemainder(ModItems.GLASS_JAR.get()))
    {
        @Override
        protected void onDrinkSpecial(LivingEntity entity, Level level)
        {
            //设置玩家标记，用于床交互时传送至染梦世界
            entity.getPersistentData().putBoolean("pasterdream:dream_juice_drank", true);
            if (!level.isClientSide()) {
                entity.addEffect(new MobEffectInstance(ModEffects.DREAM_WISH_BUFF.get(), 1800, 0));
            }
        }
    });
    public static final RegistryObject<Item> GLASS_JAR_OF_GOLDENROD_TEA = ITEMS.register("glass_jar_of_goldenrod_tea",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().nutrition(1).effect(() -> new MobEffectInstance(ModEffects.GOLDENROD_TEA_BUFF.get(), 3600, 0), 1.0f).alwaysEat().build()).useDuration(24).craftRemainder(ModItems.GLASS_JAR.get())) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, level, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.goldenrod_tea"));
                }
            });
    public static final RegistryObject<Item> GLASS_JAR_OF_DYEDREAM_PERFUME = ITEMS.register("glass_jar_of_dyedream_perfume",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.DYEDREAM_PERFUME_BUFF.get(), 1200, 0), 1.0f).alwaysEat().build()).useDuration(24).craftRemainder(ModItems.GLASS_JAR.get())) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, level, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.dyedream_perfume"));
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.dyedream_perfume.flavor"));
                }

                @Override
                protected void onDrinkSpecial(LivingEntity entity, Level level) {
                    if (entity instanceof ServerPlayer serverPlayer) {
                        serverPlayer.getStats().setValue(serverPlayer, Stats.CUSTOM.get(Stats.TIME_SINCE_REST), 0);
                        AABB range = entity.getBoundingBox().inflate(64.0D);
                        for (Phantom phantom : level.getEntitiesOfClass(Phantom.class, range)) {
                            if (phantom.getTarget() == entity) {
                                phantom.setTarget(null);
                            }
                        }
                    }
                }
            });
    public static final RegistryObject<Item> GLASS_JAR_OF_INK = ITEMS.register("glass_jar_of_ink", () -> new Item(new Item.Properties().craftRemainder(ModItems.GLASS_JAR.get())));


    // ===== 玻璃杯系列 =====
    public static final RegistryObject<Item> GLASS_CUP = ITEMS.register("glass_cup", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GLASS_CUP_OF_APPLE_JUICE = ITEMS.register("glass_cup_of_apple_juice", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.5f).alwaysEat().build())));
    public static final RegistryObject<Item> GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA = ITEMS.register("glass_cup_of_uncooked_dyedream_flower_tea", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA = ITEMS.register("glass_cup_of_cooked_dyedream_flower_tea", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.HEAL, 1, 0), 1.0f).alwaysEat().build())) {
        @Override
        public boolean isFoil(@NotNull ItemStack stack) {
            return true;
        }
    });
    public static final RegistryObject<Item> GLASS_CUP_OF_DYEDREAM_JUICE = ITEMS.register("glass_cup_of_dyedream_juice", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.5f).alwaysEat().build()).meltDreamEnergyAdd(0.2)));
    public static final RegistryObject<Item> GLASS_CUP_OF_HONEY_JUICE = ITEMS.register("glass_cup_of_honey_juice", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.5f).alwaysEat().build())) {
        @Override
        protected void onDrinkSpecial(LivingEntity entity, Level level) {
            if (!level.isClientSide) {
                entity.getActiveEffects().stream()
                        .map(MobEffectInstance::getEffect)
                        .filter(effect -> !effect.isBeneficial())
                        .toList()
                        .forEach(entity::removeEffect);
            }
        }
    });
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
            () -> new Item(new Item.Properties().stacksTo(16)) {
                @Override
                public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
                    ItemStack itemstack = player.getItemInHand(hand);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
                    if (!level.isClientSide) {
                        ThrownPinkEgg thrownEgg = new ThrownPinkEgg(level, player);
                        thrownEgg.setItem(itemstack);
                        thrownEgg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                        level.addFreshEntity(thrownEgg);
                    }
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                    return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                }
            });

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
                            .effect(() -> new MobEffectInstance(ModEffects.COOK_BUFF.get(), 1200, 0), 1.0f)
                            .nutrition(7).saturationMod(0.625f).alwaysEat().build())));

    public static final RegistryObject<Item> TUBER_BUN_CAKE = ITEMS.register("tuber_bun_cake",
            ()  -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(ModEffects.COOK_BUFF.get(), 1200, 0), 1.0f)
                            .nutrition(7).saturationMod(0.625f).alwaysEat().build())));

    public static final RegistryObject<Item> WATERMELON_BUN_CAKE = ITEMS.register("watermelon_bun_cake",
            ()  -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(ModEffects.COOK_BUFF.get(), 1200, 0), 1.0f)
                            .nutrition(7).saturationMod(0.625f).alwaysEat().build())));

    public static final RegistryObject<Item> PUMPKIN_BUN_CAKE = ITEMS.register("pumpkin_bun_cake",
            ()  -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.JUMP, 1200, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(ModEffects.COOK_BUFF.get(), 1200, 0), 1.0f)
                            .nutrition(7).saturationMod(0.625f).alwaysEat().build())));

    public static final RegistryObject<Item> GLOW_BERRY_BUN_CAKE = ITEMS.register("glow_berry_bun_cake",
            ()  -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(ModEffects.COOK_BUFF.get(), 1200, 0), 1.0f)
                            .nutrition(7).saturationMod(0.625f).alwaysEat().build())));

    public static final RegistryObject<Item> DYEDREAM_FRUIT_BUN_CAKE = ITEMS.register("dyedream_fruit_bun_cake",
            ()  -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1).meltDreamEnergyAdd(0.3)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK_BUFF.get(), 1200, 0), 1.0f)
                            .nutrition(7).alwaysEat().saturationMod(0.625f).build())));

    public static final RegistryObject<Item> CHOCOLATE_MATCHA_CAKE = ITEMS.register("chocolate_matcha_cake",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK_BUFF.get(), 1200, 0), 1.0f)
                            .nutrition(8).saturationMod(1).build())));

    // ===== 杂项食物系列 =====（染梦冰棒,泡泡糖，跳跳糖，俩棉花糖和面包片除外，其余给予料理I buff：1分钟/奇怪培根煎蛋2分钟/菠萝爱海5分钟）
    public static final RegistryObject<Item> RICE_CAKE = ITEMS.register("rice_cake",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(6).saturationMod(0.415f).build())));

    public static final RegistryObject<Item> DYEDREAM_POPSICLE = ITEMS.register("dyedream_popsicle",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1).meltDreamEnergyAdd(0.2)
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.5f).alwaysEat().build())){
                @Override
                protected void onFoodSpecial(LivingEntity entity, Level level) {
                    if (entity.isOnFire()) {
                        int remainingTicks = entity.getRemainingFireTicks();
                        entity.setRemainingFireTicks(Math.max(0, remainingTicks - 100));
                    }
                }
            });//减少燃烧时间

    public static final RegistryObject<Item> FRIED_EGG = ITEMS.register("fried_egg",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationMod(0.25f).build())));

    public static final RegistryObject<Item> BACON_AND_EGG = ITEMS.register("bacon_and_egg",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK_BUFF.get(), 1200, 0), 1.0f)
                            .nutrition(10).saturationMod(1.2f).build())));

    public static final RegistryObject<Item> ODD_BACON_AND_EGG = ITEMS.register("odd_bacon_and_egg",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(2)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK_BUFF.get(), 2400, 0), 1.0f)
                            .nutrition(11).saturationMod(1.365f).build())));

    public static final RegistryObject<Item> HEART_CHOCOLATE = ITEMS.register("heart_chocolate",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(0.5)
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(0.375f).build())));

    public static final RegistryObject<Item> WHITE_HEART_CHOCOLATE = ITEMS.register("white_heart_chocolate",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(0.375f).build())));

    public static final RegistryObject<Item> PINK_HEART_CHOCOLATE = ITEMS.register("pink_heart_chocolate",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1.5).meltDreamEnergyAdd(0.9)
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(0.375f).build())));

    public static final RegistryObject<Item> BREAD_SLICE = ITEMS.register("bread_slice",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(3).saturationMod(0.5f).build())));

    public static final RegistryObject<Item> SWISS_ROLL = ITEMS.register("swiss_roll",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK_BUFF.get(), 1200, 0), 1.0f)
                            .nutrition(6).saturationMod(0.75f).build())));

    public static final RegistryObject<Item> SANDWICH = ITEMS.register("sandwich",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK_BUFF.get(), 1200, 0), 1.0f)
                            .nutrition(10).saturationMod(0.9f).build())));

    public static final RegistryObject<Item> WAFER_BISCUIT = ITEMS.register("wafer_biscuit",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(3).saturationMod(0.165f).build())));

    public static final RegistryObject<Item> STUFFED_WAFER_COOKIES = ITEMS.register("stuffed_wafer_cookies",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK_BUFF.get(), 1200, 0), 1.0f)
                            .nutrition(7).saturationMod(1).build())));

    public static final RegistryObject<Item> GINGERBREAD_MAN = ITEMS.register("gingerbread_man",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK_BUFF.get(), 1200, 0), 1.0f)
                            .nutrition(5).saturationMod(0.6f).build())));

    public static final RegistryObject<Item> CANDY_CANE = ITEMS.register("candy_cane",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK_BUFF.get(), 1200, 0), 1.0f)
                            .nutrition(3).saturationMod(0.5f).build())));

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


    public static final RegistryObject<Item> GALAXY_JELLY = ITEMS.register("galaxy_jelly",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(6).alwaysEat().saturationMod(0.415f).build()).useDuration(25)
            ){
                @Override
                protected void onFoodSpecial(LivingEntity entity, Level level) {
                    // 检测饰品栏或盔甲栏是否有 galaxy_jelly_boost 标签的物品
                    boolean hasBoost = entity.getItemBySlot(EquipmentSlot.CHEST).is(ModItemTags.GALAXY_JELLY_BOOST)
                            || CuriosApi.getCuriosInventory(entity)
                                    .map(inv -> inv.findFirstCurio(
                                            stack -> stack.is(ModItemTags.GALAXY_JELLY_BOOST)).isPresent())
                                    .orElse(false);

                    if (hasBoost) {
                        entity.setDeltaMovement(entity.getDeltaMovement().x, 2, entity.getDeltaMovement().z);
                        entity.hurtMarked = true;
                        if (entity instanceof Player player) {
                            player.getCooldowns().addCooldown(this, 80);
                        }
                        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                                SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS, 3.0F, 1.0F);
                    } else {
                        // 常规效果
                        entity.setDeltaMovement(entity.getDeltaMovement().x, 3, entity.getDeltaMovement().z);
                        entity.hurtMarked = true;
                        entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 140, 0));
                        if (entity instanceof Player player) {
                            player.getCooldowns().addCooldown(this, 40);
                        }
                        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                                SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS, 3.0F, 1.0F);
                    }

                    // 进度触发器：在建筑高度上限吃下星河果冻
                    if (entity instanceof ServerPlayer sp) {
                        ModCriteriaTriggers.EAT_GALAXY_JELLY_AT_HEIGHT.trigger(sp, sp.getY());
                    }
                }
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, level, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.galaxy_jelly"));
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.galaxy_jelly.flavor"));
                }
            });

    public static final RegistryObject<Item> MILKY_WAY_JELLY = ITEMS.register("milky_way_jelly",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(8).alwaysEat().saturationMod(0.5f).build()).useDuration(25)
            ){
                @Override
                protected void onFoodSpecial(LivingEntity entity, Level level) {
                    if (entity instanceof ServerPlayer sp) {
                        sp.teleportTo(sp.serverLevel(), sp.getX(), level.getMaxBuildHeight(), sp.getZ(), sp.getYRot(), sp.getXRot());
                    } else {
                        entity.setPos(entity.getX(), level.getMaxBuildHeight(), entity.getZ());
                    }
                    entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0));
                    if (entity instanceof Player player) {
                        player.getCooldowns().addCooldown(this, 400);
                    }
                    level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                            SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS, 3.0F, 1.0F);
                }
                @Override
                public boolean isFoil(ItemStack stack) {
                    return true;
                }
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, level, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.milky_way_jelly"));
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.milky_way_jelly.flavor"));
                }
            });

    public static final RegistryObject<Item> FORTUNE_JELLY = ITEMS.register("fortune_jelly",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(6).alwaysEat().saturationMod(0.415f).build()).useDuration(25)
            ){
                @Override
                protected void onFoodSpecial(LivingEntity entity, Level level) {

                    entity.addEffect(new MobEffectInstance(MobEffects.LUCK, 1200, 0));
                }
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, level, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.fortune_jelly"));
                }
            });

    public static final RegistryObject<Item> LIGHT_ORGAN = ITEMS.register("light_organ",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(-1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.GLOWING, 100, 0), 1.0f)
                            .nutrition(1).build())));

    //TODO:防风buff施工中
    public static final RegistryObject<Item> JELLYFISH_MUD = ITEMS.register("jellyfish_mud",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(-3)
                    .food(new FoodProperties.Builder().nutrition(1).build())));

    public static final RegistryObject<Item> JELLYFISH_JELLO = ITEMS.register("jellyfish_jello",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(0.5f).build())));

    public static final RegistryObject<Item> QUEER_SOUP = ITEMS.register("queer_soup",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().meltDreamEnergyAdd(1)
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(0.375f).alwaysEat().build()))
            {
                @Override
                protected void onFoodSpecial(LivingEntity entity, Level level)
                {
                    if (!level.isClientSide && entity instanceof Player player && !player.isCreative())
                    {
                            ItemStack containerStack = new ItemStack(Items.BOWL);
                            if (!player.getInventory().add(containerStack))
                            {
                                player.drop(containerStack, false);
                            }
                    }
                }
            });


    private static final UUID LEGENDARY_DRAGON_HORN_ICE_CREAM_LUCK_UUID = UUID.fromString("31f3e03b-4aea-45dd-8702-49d7000e170c");

    public static final RegistryObject<Item> LEGENDARY_DRAGON_HORN_ICE_CREAM = ITEMS.register("legendary_dragon_horn_ice_cream",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(10).saturationMod(1.2f).alwaysEat().build())
                    .rarity(Rarity.EPIC)) {
                @Override
                protected void onFoodSpecial(LivingEntity entity, Level level) {
                    if (!level.isClientSide) {
                        if (entity instanceof Player player && !player.isCreative()) {
                            ItemStack containerStack = new ItemStack(Items.BOWL);
                            if (!player.getInventory().add(containerStack)) {
                                player.drop(containerStack, false);
                            }
                        }

                        var luckAttr = entity.getAttribute(Attributes.LUCK);
                        if (luckAttr != null) {
                            AttributeModifier existingModifier = luckAttr.getModifier(LEGENDARY_DRAGON_HORN_ICE_CREAM_LUCK_UUID);
                            if (existingModifier == null) {
                                luckAttr.addPermanentModifier(new AttributeModifier(LEGENDARY_DRAGON_HORN_ICE_CREAM_LUCK_UUID,
                                        "legendary_dragon_horn_ice_cream", 10, AttributeModifier.Operation.ADDITION));
                                if (level instanceof ServerLevel serverLevel) {
                                    serverLevel.sendParticles(ParticleTypes.SNOWFLAKE,
                                            entity.getX(), entity.getY() + 3, entity.getZ(),
                                            128, 2, 0.5, 2, 1);
                                }
                                if (entity instanceof Player player) {
                                    player.displayClientMessage(Component.translatable("item.pasterdream.legendary_dragon_horn_ice_cream.client.success"), false);
                                }
                            } else {
                                if (entity instanceof Player player) {
                                    player.displayClientMessage(Component.translatable("item.pasterdream.legendary_dragon_horn_ice_cream.client.fail"), false);
                                }
                            }
                        }
                    }
                }
            });

    public static final RegistryObject<Item> ELIXIR_BOTTLE = ITEMS.register("elixir_bottle",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ELIXIR_BOTTLE_OF_MELT_DREAM = ITEMS.register("elixir_bottle_of_melt_dream",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().stacksTo(1).meltDreamEnergyAdd(25).rarity(Rarity.UNCOMMON)
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(2).alwaysEat().build())));

    private static final UUID ELIXIR_BOTTLE_OF_RAGE_ELIXIR_ATTACK_SPEED_UUID = UUID.fromString("78e1cdd9-d201-4e2b-8adb-0af735d2c806");
    public static final RegistryObject<Item> ELIXIR_BOTTLE_OF_RAGE_ELIXIR = ITEMS.register("elixir_bottle_of_rage_elixir",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().stacksTo(1).rarity(Rarity.UNCOMMON)
                    .food(new FoodProperties.Builder().alwaysEat().build())) {
                @Override
                protected void onDrinkSpecial(LivingEntity entity, Level level) {
                    if (!level.isClientSide) {
                        var AtkspdAttr = entity.getAttribute(Attributes.ATTACK_SPEED);
                        if (AtkspdAttr != null) {
                            AttributeModifier existingModifier = AtkspdAttr.getModifier(ELIXIR_BOTTLE_OF_RAGE_ELIXIR_ATTACK_SPEED_UUID);
                            if (existingModifier == null) {
                                AtkspdAttr.addPermanentModifier(new AttributeModifier(ELIXIR_BOTTLE_OF_RAGE_ELIXIR_ATTACK_SPEED_UUID,
                                        "elixir_bottle_of_rage_elixir", 0.1, AttributeModifier.Operation.ADDITION));
                                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                                        ModSounds.DREAM0.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                                if (entity instanceof Player player) {
                                    player.displayClientMessage(Component.translatable("item.pasterdream.elixir_bottle_of_rage_elixir.client.success"), false);
                                }
                            } else {
                                if (entity instanceof Player player) {
                                    player.displayClientMessage(Component.translatable("item.pasterdream.elixir_bottle_of_rage_elixir.client.fail"), false);
                                }
                            }
                        }
                    }
                }
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, level, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.elixir_bottle_of_rage_elixir.1"));
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.elixir_bottle_of_rage_elixir.2"));
                }
            });

    public static final RegistryObject<Item> PINEAPPLE_LOVE_SEA = ITEMS.register("pineapple_love_sea",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().sanAdd(15).rarity(Rarity.UNCOMMON)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK_BUFF.get(), 6000, 1), 1.0f)
                            .nutrition(3).saturationMod(0.5f).alwaysEat().build())));

    public static final RegistryObject<Item> BUBBLE_TEA = ITEMS.register("bubble_tea", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties()
            .food(new FoodProperties.Builder().nutrition(5).saturationMod(0.7f).alwaysEat().build())));


    // ===== 露滴 =====
    public static final RegistryObject<Item> RED_DEW = ITEMS.register("red_dew",
            RedDewItem::new);
    public static final RegistryObject<Item> BLUE_HEART_OF_THE_SEA = ITEMS.register("blue_heart_of_the_sea",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ELDER_GUARDIAN_SCALE = ITEMS.register("elder_guardian_scale",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLUE_DEW = ITEMS.register("blue_dew",
            BlueDewItem::new);

    // ===== 园艺钳 =====
    public static final RegistryObject<Item> PLIERS = ITEMS.register("pliers",
            () -> new PliersItem(new Item.Properties()));

    public static final RegistryObject<Item> BROKEN_NOTE = ITEMS.register("broken_note", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> UNKNOWN_NOTE = ITEMS.register("unknown_note", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));

    // ===== 饰品（护符） =====
    public static final RegistryObject<Item> EMBRYO_CHARM = ITEMS.register("embryo_charm",
            EmbryoCharmItem::new);
    public static final RegistryObject<Item> GOLD_CHARM = ITEMS.register("gold_charm",
            GoldCharmItem::new);
    public static final RegistryObject<Item> ENDEYE_CHARM = ITEMS.register("endeye_charm",
            EndeyeCharmItem::new);
    public static final RegistryObject<Item> SEA_CHARM = ITEMS.register("sea_charm",
            SeaCharmItem::new);
    public static final RegistryObject<Item> CARAPAX_CHARM = ITEMS.register("carapax_charm",
            CarapaxCharmItem::new);
    public static final RegistryObject<Item> DUKE_COIN_CURIO = ITEMS.register("duke_coin_curio",
            DukeCoinCurioItem::new);
    public static final RegistryObject<Item> WORLDTREE_SEEDPOD = ITEMS.register("worldtree_seedpod",
            WorldtreeSeedpodItem::new);
    public static final RegistryObject<Item> GARLAND = ITEMS.register("garland",
            GarlandItem::new);
    public static final RegistryObject<Item> FOURLEAF_CLOVER_CURIO = ITEMS.register("fourleaf_clover_curio",
            FourleafCloverCurioItem::new);
    public static final RegistryObject<Item> SNOW_VOW_HEAD = ITEMS.register("snow_vow_head",
            SnowVowHeadItem::new);
    public static final RegistryObject<Item> LIGHT_BUTTERFLY_CURIO = ITEMS.register("light_butterfly_curio",
            LightButterflyCurioItem::new);
    public static final RegistryObject<Item> BRIGHT_BUTTERFLY_CURIO = ITEMS.register("bright_butterfly_curio",
            BrightButterflyCurioItem::new);
    public static final RegistryObject<Item> ICE_SHADOW_CURIO = ITEMS.register("ice_shadow_curio",
            IceShadowCurioItem::new);
    public static final RegistryObject<Item> BOBOJI_CURIO = ITEMS.register("boboji_curio",
            BobojiCurioItem::new);
    public static final RegistryObject<Item> QYM_BUTTERFLY_STAR_HAIRPIN = ITEMS.register("qym_butterfly_star_hairpin",
            QymButterflyStarHairpinItem::new);
    public static final RegistryObject<Item> HIYORI_BUTTERFLY_HAIRPIN = ITEMS.register("hiyori_butterfly_hairpin",
            HiyoriButterflyHairpinItem::new);
    public static final RegistryObject<Item> TERRA_FLOATING_ISLAND = ITEMS.register("terra_floating_island",
            TerraFloatingIslandItem::new);
    public static final RegistryObject<Item> BLESSING_OF_CECILIA = ITEMS.register("blessing_of_cecilia",
            BlessingOfCeciliaItem::new);
    public static final RegistryObject<Item> FADED_BLESSING_OF_CECILIA = ITEMS.register("faded_blessing_of_cecilia",
            FadedBlessingOfCeciliaItem::new);
    public static final RegistryObject<Item> PALE_BONE_NEEDLE_TALISMAN = ITEMS.register("pale_bone_needle_talisman",
            PaleBoneNeedleTalismanItem::new);

    // ===== 饰品（项链） =====
    public static final RegistryObject<Item> EMBRYO_NECKLACE = ITEMS.register("embryo_necklace",
            EmbryoNecklaceItem::new);
    public static final RegistryObject<Item> RABBIT_FOOT_NECKLACE = ITEMS.register("rabbit_foot_necklace",
            RabbitFootNecklaceItem::new);
    public static final RegistryObject<Item> FEATHER_NECKLACE = ITEMS.register("feather_necklace",
            FeatherNecklaceItem::new);
    public static final RegistryObject<Item> HEALTH_NECKLACE = ITEMS.register("health_necklace",
            HealthNecklaceItem::new);
    public static final RegistryObject<Item> FIRE_NECKLACE = ITEMS.register("fire_necklace",
            FireNecklaceItem::new);
    public static final RegistryObject<Item> CROSS_NECKLACE = ITEMS.register("cross_necklace",
            CrossNecklaceItem::new);

    // ===== 饰品（戒指） =====
    public static final RegistryObject<Item> EMBRYO_RING = ITEMS.register("embryo_ring",
            EmbryoRingItem::new);
    public static final RegistryObject<Item> RED_DEW_RING = ITEMS.register("red_dew_ring",
            RedDewRingItem::new);
    public static final RegistryObject<Item> STRIKE_RING = ITEMS.register("strike_ring",
            StrikeRingItem::new);
    public static final RegistryObject<Item> ALLKINDS_RING = ITEMS.register("allkinds_ring",
            AllkindsRingItem::new);
    public static final RegistryObject<Item> COUNTER_RING = ITEMS.register("counter_ring",
            CounterRingItem::new);
    public static final RegistryObject<Item> MELT_DREAM_ENERGY_RING = ITEMS.register("melt_dream_energy_ring",
            MeltDreamEnergyRingItem::new);
    // ===== 饰品（腰带） =====
    public static final RegistryObject<Item> EMBRYO_BELT = ITEMS.register("embryo_belt",
            EmbryoBeltItem::new);
    public static final RegistryObject<Item> NATURE_BELT = ITEMS.register("nature_belt",
            NatureBeltItem::new);
    public static final RegistryObject<Item> TRAVELER_BELT = ITEMS.register("traveler_belt",
            TravelerBeltItem::new);
    public static final RegistryObject<Item> DREAM_TRAVELER_BELT = ITEMS.register("dream_traveler_belt",
            DreamTravelerBeltItem::new);

    // ===== 饰品（胸饰） =====
    public static final RegistryObject<Item> KAICHU_OMAMORI = ITEMS.register("kaichu_omamori",
            KaichuOmamoriItem::new);
    public static final RegistryObject<Item> BROOCH_OF_WHITE_ORCHID = ITEMS.register("brooch_of_white_orchid",
            BroochOfWhiteOrchidItem::new);
    public static final RegistryObject<Item> SEAL_OF_THE_CORRUPTED = ITEMS.register("seal_of_the_corrupted",
            SealOfTheCorruptedItem::new);

    // ===== 饰品（通用） =====
    public static final RegistryObject<Item> WAR_FLAG = ITEMS.register("war_flag",
            WarFlagItem::new);

    public static final RegistryObject<Item> CALAIS_SPICE_BOTTLE = ITEMS.register("calais_spice_bottle",
            CalaisSpiceBottleItem::new);

    // ===== 鬼魂之面（头饰） =====
    public static final RegistryObject<Item> GHOST_FACE = ITEMS.register("ghost_face",
            GhostFaceItem::new);

    // ===== 陶罐 =====
    public static final RegistryObject<Item> CLAY_POT = ITEMS.register("clay_pot",
            () -> new BlockItem(ModBlocks.CLAY_POT.get(), new Item.Properties()));
    // ===== 阴影陶罐 =====
    public static final RegistryObject<Item> SHADOW_CLAY_POT = ITEMS.register("shadow_clay_pot",
            () -> new BlockItem(ModBlocks.SHADOW_CLAY_POT.get(), new Item.Properties()));
    // ===== 小石子 =====
    public static final RegistryObject<Item> PEBBLE = ITEMS.register("pebble",
            () -> new PebbleItem(ModBlocks.PEBBLE.get(), new Item.Properties()));

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
    public static final RegistryObject<Item> BLACK_METAL_BLOCK = ITEMS.register("black_metal_block",
            () -> new BlockItem(ModBlocks.BLACK_METAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> RUST_BLACK_METAL_BLOCK = ITEMS.register("rust_black_metal_block",
            () -> new BlockItem(ModBlocks.RUST_BLACK_METAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> RUST_BLACK_METAL_WALL = ITEMS.register("rust_black_metal_wall",
            () -> new BlockItem(ModBlocks.RUST_BLACK_METAL_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> RUST_BLACK_METAL_BARS = ITEMS.register("rust_black_metal_bars",
            () -> new BlockItem(ModBlocks.RUST_BLACK_METAL_BARS.get(), new Item.Properties()));
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
    public static final RegistryObject<Item> WHITE_SAND = ITEMS.register("white_sand",
            () -> new BlockItem(ModBlocks.WHITE_SAND.get(), new Item.Properties()));
    public static final RegistryObject<Item> THICK_CLOUD = ITEMS.register("thick_cloud",
            () -> new BlockItem(ModBlocks.THICK_CLOUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW = ITEMS.register("shadow",
            () -> new BlockItem(ModBlocks.SHADOW.get(), new Item.Properties()));
    public static final RegistryObject<Item> THICK_SHADOW = ITEMS.register("thick_shadow",
            () -> new BlockItem(ModBlocks.THICK_SHADOW.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_STONE = ITEMS.register("shadow_stone",
            () -> new BlockItem(ModBlocks.SHADOW_STONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_STONE_BRICK = ITEMS.register("shadow_stone_brick",
            () -> new BlockItem(ModBlocks.SHADOW_STONE_BRICK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_STONE_BRICK_STAIRS = ITEMS.register("shadow_stone_brick_stairs",
            () -> new BlockItem(ModBlocks.SHADOW_STONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_STONE_BRICK_SLAB = ITEMS.register("shadow_stone_brick_slab",
            () -> new BlockItem(ModBlocks.SHADOW_STONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_STONE_BRICK_WALL = ITEMS.register("shadow_stone_brick_wall",
            () -> new BlockItem(ModBlocks.SHADOW_STONE_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> NARROW_SHADOW_STONE_BRICK = ITEMS.register("narrow_shadow_stone_brick",
            () -> new BlockItem(ModBlocks.NARROW_SHADOW_STONE_BRICK.get(), new Item.Properties()));
    public static final RegistryObject<Item> NARROW_SHADOW_STONE_BRICK_STAIRS = ITEMS.register("narrow_shadow_stone_brick_stairs",
            () -> new BlockItem(ModBlocks.NARROW_SHADOW_STONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> NARROW_SHADOW_STONE_BRICK_SLAB = ITEMS.register("narrow_shadow_stone_brick_slab",
            () -> new BlockItem(ModBlocks.NARROW_SHADOW_STONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> NARROW_SHADOW_STONE_BRICK_WALL = ITEMS.register("narrow_shadow_stone_brick_wall",
            () -> new BlockItem(ModBlocks.NARROW_SHADOW_STONE_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_STONE_TILES = ITEMS.register("shadow_stone_tiles",
            () -> new BlockItem(ModBlocks.SHADOW_STONE_TILES.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_STONE_TILES_STAIRS = ITEMS.register("shadow_stone_tiles_stairs",
            () -> new BlockItem(ModBlocks.SHADOW_STONE_TILES_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_STONE_TILES_SLAB = ITEMS.register("shadow_stone_tiles_slab",
            () -> new BlockItem(ModBlocks.SHADOW_STONE_TILES_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_STONE_TILES_WALL = ITEMS.register("shadow_stone_tiles_wall",
            () -> new BlockItem(ModBlocks.SHADOW_STONE_TILES_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRACKED_SHADOW_STONE_BRICK = ITEMS.register("cracked_shadow_stone_brick",
            () -> new BlockItem(ModBlocks.CRACKED_SHADOW_STONE_BRICK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_SHADOW_STONE_BRICK = ITEMS.register("chiseled_shadow_stone_brick",
            () -> new BlockItem(ModBlocks.CHISELED_SHADOW_STONE_BRICK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_STONE_CAGE_RUNE = ITEMS.register("shadow_stone_cage_rune",
            () -> new BlockItem(ModBlocks.SHADOW_STONE_CAGE_RUNE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_STONE_HOLY_GRAIL_RUNE = ITEMS.register("shadow_stone_holy_grail_rune",
            () -> new BlockItem(ModBlocks.SHADOW_STONE_HOLY_GRAIL_RUNE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_STONE_OBLATION_RUNE = ITEMS.register("shadow_stone_oblation_rune",
            () -> new BlockItem(ModBlocks.SHADOW_STONE_OBLATION_RUNE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_STONE_TRIPOD_CAULDRON_RUNE = ITEMS.register("shadow_stone_tripod_cauldron_rune",
            () -> new BlockItem(ModBlocks.SHADOW_STONE_TRIPOD_CAULDRON_RUNE.get(), new Item.Properties()));
    // ===== 暗影地牢方块系列 =====
    public static final RegistryObject<Item> SHADOW_DUNGEON_STONE = ITEMS.register("shadow_dungeon_stone",
            () -> new BlockItem(ModBlocks.SHADOW_DUNGEON_STONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_SHADOW_DUNGEON_BRICKS = ITEMS.register("chiseled_shadow_dungeon_bricks",
            () -> new BlockItem(ModBlocks.CHISELED_SHADOW_DUNGEON_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_DUNGEON_BRICKS = ITEMS.register("shadow_dungeon_bricks",
            () -> new BlockItem(ModBlocks.SHADOW_DUNGEON_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRACKED_SHADOW_DUNGEON_BRICKS = ITEMS.register("cracked_shadow_dungeon_bricks",
            () -> new BlockItem(ModBlocks.CRACKED_SHADOW_DUNGEON_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> FRACTURED_SHADOW_DUNGEON_BRICKS = ITEMS.register("fractured_shadow_dungeon_bricks",
            () -> new BlockItem(ModBlocks.FRACTURED_SHADOW_DUNGEON_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_DUNGEON_BRICK_STAIRS = ITEMS.register("shadow_dungeon_brick_stairs",
            () -> new BlockItem(ModBlocks.SHADOW_DUNGEON_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_DUNGEON_BRICK_SLAB = ITEMS.register("shadow_dungeon_brick_slab",
            () -> new BlockItem(ModBlocks.SHADOW_DUNGEON_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHATTERED_SHADOW_DUNGEON_BRICKS = ITEMS.register("shattered_shadow_dungeon_bricks",
            () -> new BlockItem(ModBlocks.SHATTERED_SHADOW_DUNGEON_BRICKS.get(), new Item.Properties()));
    // ===== 灯影竞技场 / 裂隙方块 =====
    public static final RegistryObject<Item> SHADOW_ARENA_BLOCK = ITEMS.register("shadow_arena_block",
            () -> new BlockItem(ModBlocks.SHADOW_ARENA_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_FISSURE_0 = ITEMS.register("shadow_fissure_0",
            () -> new BlockItem(ModBlocks.SHADOW_FISSURE_0.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_FISSURE_1 = ITEMS.register("shadow_fissure_1",
            () -> new BlockItem(ModBlocks.SHADOW_FISSURE_1.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_FISSURE_2 = ITEMS.register("shadow_fissure_2",
            () -> new BlockItem(ModBlocks.SHADOW_FISSURE_2.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_FISSURE_3 = ITEMS.register("shadow_fissure_3",
            () -> new BlockItem(ModBlocks.SHADOW_FISSURE_3.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_FISSURE_4 = ITEMS.register("shadow_fissure_4",
            () -> new BlockItem(ModBlocks.SHADOW_FISSURE_4.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_FISSURE_5 = ITEMS.register("shadow_fissure_5",
            () -> new BlockItem(ModBlocks.SHADOW_FISSURE_5.get(), new Item.Properties()));
    public static final RegistryObject<Item> DREAM_SPAWNER = ITEMS.register("dream_spawner",
            () -> new BlockItem(ModBlocks.DREAM_SPAWNER.get(), new Item.Properties()));
    public static final RegistryObject<Item> FADED_DREAM_SPAWNER = ITEMS.register("faded_dream_spawner",
            () -> new BlockItem(ModBlocks.FADED_DREAM_SPAWNER.get(), new Item.Properties()));
    public static final RegistryObject<Item> BROKEN_SHADOW_DUNGEON_PORTAL = ITEMS.register("broken_shadow_dungeon_portal",
            () -> new BrokenShadowDungeonPortalDisplayItem(ModBlocks.BROKEN_SHADOW_DUNGEON_PORTAL.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_DUNGEON_PORTAL = ITEMS.register("shadow_dungeon_portal",
            () -> new ShadowDungeonPortalDisplayItem(ModBlocks.SHADOW_DUNGEON_PORTAL.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_VORTEX = ITEMS.register("shadow_vortex",
            () -> new ShadowVortexDisplayItem(ModBlocks.SHADOW_VORTEX.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_HAND_TRAP = ITEMS.register("shadow_hand_trap",
            () -> new ShadowHandTrapItem(ModBlocks.SHADOW_HAND_TRAP.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_BRAZIER = ITEMS.register("shadow_brazier",
            () -> new ShadowBrazierDisplayItem(ModBlocks.SHADOW_BRAZIER.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_NYLIUM = ITEMS.register("shadow_nylium",
            () -> new BlockItem(ModBlocks.SHADOW_NYLIUM.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_LIGHT = ITEMS.register("shadow_light",
            () -> new BlockItem(ModBlocks.SHADOW_LIGHT.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_SHROOMLIGHT = ITEMS.register("shadow_shroomlight",
            () -> new BlockItem(ModBlocks.SHADOW_SHROOMLIGHT.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_CANDLE = ITEMS.register("shadow_candle",
            () -> new BlockItem(ModBlocks.SHADOW_CANDLE.get(), new Item.Properties()));
    // ===== 阴影巨型菌系列 =====
    public static final RegistryObject<Item> SHADOW_WART_BLOCK = ITEMS.register("shadow_wart_block",
            () -> new BlockItem(ModBlocks.SHADOW_WART_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_STEM = ITEMS.register("shadow_stem",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_STEM.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_HYPHAE = ITEMS.register("shadow_hyphae",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_HYPHAE.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_SHADOW_STEM = ITEMS.register("stripped_shadow_stem",
            () -> new FireproofBlockItem(ModBlocks.STRIPPED_SHADOW_STEM.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_SHADOW_HYPHAE = ITEMS.register("stripped_shadow_hyphae",
            () -> new FireproofBlockItem(ModBlocks.STRIPPED_SHADOW_HYPHAE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_PLANKS = ITEMS.register("shadow_planks",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_STAIRS = ITEMS.register("shadow_stairs",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_SLAB = ITEMS.register("shadow_slab",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_FENCE = ITEMS.register("shadow_fence",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_FENCE_GATE = ITEMS.register("shadow_fence_gate",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_PANE = ITEMS.register("shadow_pane",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_PANE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_DOOR = ITEMS.register("shadow_door",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_TRAPDOOR = ITEMS.register("shadow_trapdoor",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_PRESSURE_PLATE = ITEMS.register("shadow_pressure_plate",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_BUTTON = ITEMS.register("shadow_button",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_BUTTON.get(), new Item.Properties()));
    // ===== 阴影书架系列 =====
    public static final RegistryObject<Item> SHADOW_BOOKSHELF = ITEMS.register("shadow_bookshelf",
            () -> new BlockItem(ModBlocks.SHADOW_BOOKSHELF.get(), new Item.Properties()));
    public static final RegistryObject<Item> WORN_SHADOW_BOOKSHELF = ITEMS.register("worn_shadow_bookshelf",
            () -> new BlockItem(ModBlocks.WORN_SHADOW_BOOKSHELF.get(), new Item.Properties()));
    public static final RegistryObject<Item> COBWEB_SHADOW_BOOKSHELF = ITEMS.register("cobweb_shadow_bookshelf",
            () -> new BlockItem(ModBlocks.COBWEB_SHADOW_BOOKSHELF.get(), new Item.Properties()));
    public static final RegistryObject<Item> KEY_SHADOW_BOOKSHELF = ITEMS.register("key_shadow_bookshelf",
            () -> new BlockItem(ModBlocks.KEY_SHADOW_BOOKSHELF.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_DUNGEON_KEY = ITEMS.register("shadow_dungeon_key",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> SHADOW_DUNGEON_GATE = ITEMS.register("shadow_dungeon_gate",
            () -> new ShadowDungeonGateItem(ModBlocks.SHADOW_DUNGEON_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_DUNGEON_BARRIER = ITEMS.register("shadow_dungeon_barrier",
            () -> new ShadowDungeonBarrierItem(ModBlocks.SHADOW_DUNGEON_BARRIER.get(), new Item.Properties()));
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

    // ===== 风之旅世界·苍青岩系列 =====
    public static final RegistryObject<Item> CYAN_STONE = ITEMS.register("cyan_stone",
            () -> new BlockItem(ModBlocks.CYAN_STONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CYAN_MOSS_STONE = ITEMS.register("cyan_moss_stone",
            () -> new BlockItem(ModBlocks.CYAN_MOSS_STONE.get(), new Item.Properties()));

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

    public static final RegistryObject<Item> JUNGLE_SPORANGIUM = ITEMS.register("jungle_sporangium",
            () -> new BlockItem(ModBlocks.JUNGLE_SPORANGIUM.get(), new Item.Properties()));

    public static final RegistryObject<Item> FOURLEAF_CLOVER = ITEMS.register("fourleaf_clover",
            () -> new BlockItem(ModBlocks.FOURLEAF_CLOVER.get(), new Item.Properties()));

    // ===== 阴影植物系列 =====
    public static final RegistryObject<Item> SHADOW_SHORT_ROOTS = ITEMS.register("shadow_short_roots",
            () -> new BlockItem(ModBlocks.SHADOW_SHORT_ROOTS.get(), new Item.Properties()));

    public static final RegistryObject<Item> SHADOW_ROOTS = ITEMS.register("shadow_roots",
            () -> new BlockItem(ModBlocks.SHADOW_ROOTS.get(), new Item.Properties()));

    public static final RegistryObject<Item> SHADOW_STEM_FERN = ITEMS.register("shadow_stem_fern",
            () -> new BlockItem(ModBlocks.SHADOW_STEM_FERN.get(), new Item.Properties()));

    public static final RegistryObject<Item> SHADOW_SPROUTS = ITEMS.register("shadow_sprouts",
            () -> new BlockItem(ModBlocks.SHADOW_SPROUTS.get(), new Item.Properties()));

    public static final RegistryObject<Item> SHADOW_FERN = ITEMS.register("shadow_fern",
            () -> new BlockItem(ModBlocks.SHADOW_FERN.get(), new Item.Properties()));

    public static final RegistryObject<Item> SHADOW_FUNGUS = ITEMS.register("shadow_fungus",
            () -> new BlockItem(ModBlocks.SHADOW_FUNGUS.get(), new Item.Properties()));
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
    public static final RegistryObject<Item> MORTAR = ITEMS.register("mortar", () -> new MortarItem(new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_DESK = ITEMS.register("shadow_desk", () -> new BlockItem(ModBlocks.SHADOW_DESK.get(), new Item.Properties()));
    public static final RegistryObject<Item> PICNIC_BASKET = ITEMS.register("picnic_basket", () -> new BlockItem(ModBlocks.PICNIC_BASKET.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_CHEST = ITEMS.register("shadow_chest", () -> new ShadowChestItem(ModBlocks.SHADOW_CHEST.get(), new Item.Properties()));
    public static final RegistryObject<Item> WIND_MOOR_CRATE = ITEMS.register("wind_moor_crate", () -> new BlockItem(ModBlocks.WIND_MOOR_CRATE.get(), new Item.Properties()));
    // ===== 风泊木系列 =====
    public static final RegistryObject<Item> WIND_MOOR_LOG = ITEMS.register("wind_moor_log", () -> new BlockItem(ModBlocks.WIND_MOOR_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> WIND_MOOR_WOOD = ITEMS.register("wind_moor_wood", () -> new BlockItem(ModBlocks.WIND_MOOR_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_WIND_MOOR_LOG = ITEMS.register("stripped_wind_moor_log", () -> new BlockItem(ModBlocks.STRIPPED_WIND_MOOR_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_WIND_MOOR_WOOD = ITEMS.register("stripped_wind_moor_wood", () -> new BlockItem(ModBlocks.STRIPPED_WIND_MOOR_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> THE_ENDLESS_BOOK_OF_DREAM_SEEKERS = ITEMS.register("the_endless_book_of_dream_seekers", () -> new TheEndlessBookOfDreamSeekersItem(ModBlocks.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get(), new Item.Properties()));
    public static final RegistryObject<Item> FEATHER_PEN = ITEMS.register("feather_pen", () -> new Item(new Item.Properties().durability(8)));
    public static final RegistryObject<Item> RESEARCH_TABLE = ITEMS.register("research_table", () -> new ResearchTableItem(ModBlocks.RESEARCH_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> LOST_SWORD_TOMB = ITEMS.register("lost_sword_tomb", () -> new LostSwordTombItem(ModBlocks.LOST_SWORD_TOMB.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LIFE_CRYSTAL = ITEMS.register("life_crystal", () -> new LifeCrystalItem(ModBlocks.LIFE_CRYSTAL.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> GOLDEN_FOX_SCULPTURE = ITEMS.register("golden_fox_sculpture", () -> new GoldenFoxSculptureItem(ModBlocks.GOLDEN_FOX_SCULPTURE.get(), new Item.Properties()));
    public static final RegistryObject<Item> FOX_SCULPTURE = ITEMS.register("fox_sculpture", () -> new FoxSculptureItem(ModBlocks.FOX_SCULPTURE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DESERT_HERO_TOMB = ITEMS.register("desert_hero_tomb", () -> new DesertHeroTombItem(ModBlocks.DESERT_HERO_TOMB.get(), new Item.Properties()));
    public static final RegistryObject<Item> DREAM_TRAIN_STRUCTURE = ITEMS.register("dream_train_structure", () -> new BlockItem(ModBlocks.DREAM_TRAIN_STRUCTURE.get(), new Item.Properties()));
    public static final RegistryObject<Item> MELT_DREAM_CRYSTAL_CHEST = ITEMS.register("melt_dream_crystal_chest", () -> new MeltDreamCrystalChestItem(ModBlocks.MELT_DREAM_CRYSTAL_CHEST.get(), new Item.Properties()));
    public static final RegistryObject<Item> OPENED_MELT_DREAM_CRYSTAL_CHEST = ITEMS.register("opened_melt_dream_crystal_chest", () -> new OpenedMeltDreamCrystalChestItem(ModBlocks.OPENED_MELT_DREAM_CRYSTAL_CHEST.get(), new Item.Properties()));
    public static final RegistryObject<Item> DREAM_ACCUMULATOR = ITEMS.register("dream_accumulator", () -> new DreamAccumulatorItem(ModBlocks.DREAM_ACCUMULATOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> TWILIGHT_LANTERN = ITEMS.register("twilight_lantern", () -> new TwilightLanternDisplayItem(ModBlocks.TWILIGHT_LANTERN.get(), new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SHADOW_HAND_LANTERN = ITEMS.register("shadow_hand_lantern", ShadowHandLanternItem::new);
    public static final RegistryObject<Item> SHADOW_BED = ITEMS.register("shadow_bed", () -> new BlockItem(ModBlocks.SHADOW_BED.get(), new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SORBENT = ITEMS.register("sorbent", () -> new Item(new Item.Properties().durability(54000)));
    public static final RegistryObject<Item> WEAPON_WORKSHOP_CRAFTING_TABLE = ITEMS.register("weapon_workshop_crafting_table", () -> new BlockItem(ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> WEAPON_WORKSHOP_ANVIL = ITEMS.register("weapon_workshop_anvil", () -> new BlockItem(ModBlocks.WEAPON_WORKSHOP_ANVIL.get(), new Item.Properties()));
    public static final RegistryObject<Item> WEAPON_WORKSHOP_COOLER_POT = ITEMS.register("weapon_workshop_cooler_pot", () -> new BlockItem(ModBlocks.WEAPON_WORKSHOP_COOLER_POT.get(), new Item.Properties()));
    public static final RegistryObject<Item> WEAPON_WORKSHOP_HAMMER = ITEMS.register("weapon_workshop_hammer", () -> new BlockItem(ModBlocks.WEAPON_WORKSHOP_HAMMER.get(), new Item.Properties()));
    public static final RegistryObject<Item> WEAPON_WORKSHOP_GRIND_STONE = ITEMS.register("weapon_workshop_grind_stone", () -> new BlockItem(ModBlocks.WEAPON_WORKSHOP_GRIND_STONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> WEAPON_WORKSHOP_BLAST_FURNACE = ITEMS.register("weapon_workshop_blast_furnace", () -> new WeaponWorkshopBlastFurnaceItem(ModBlocks.WEAPON_WORKSHOP_BLAST_FURNACE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_BLAST_FURNACE = ITEMS.register("shadow_blast_furnace", () -> new ShadowBlastFurnaceItem(ModBlocks.SHADOW_BLAST_FURNACE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_BLAST_FURNACE_CORE = ITEMS.register("shadow_blast_furnace_core", () -> new BlockItem(ModBlocks.SHADOW_BLAST_FURNACE_CORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> DREAM_NOTES_DYEDREAM_WORLD = ITEMS.register("dream_notes_dyedream_world", () -> new DreamNotesItem(new Item.Properties()));
    public static final RegistryObject<Item> DREAM_NOTES_LAMP_SHADOW_WORLD = ITEMS.register("dream_notes_lamp_shadow_world", () -> new DreamNotesItem(new Item.Properties()));
    public static final RegistryObject<Item> DREAM_NOTES_WIND_JOURNEY_WORLD = ITEMS.register("dream_notes_wind_journey_world", () -> new DreamNotesItem(new Item.Properties()));
    public static final RegistryObject<Item> DREAM_NOTES_STORY_GUIDE = ITEMS.register("dream_notes_story_guide", () -> new StoryProgressItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BLUE_PRINT = ITEMS.register("blue_print", () -> new BluePrintItem(new Item.Properties()));
    public static final RegistryObject<Item> DREAM_NOTES_BOOK = ITEMS.register("dream_notes_book", () -> new DreamNotesBookItem(new Item.Properties()));

    // 帕秋莉宝典 - buff图标物品
    public static final RegistryObject<Item> REST_BUFF_ICON = ITEMS.register("rest_buff_icon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHEER_UP_BUFF_ICON = ITEMS.register("cheer_up_buff_icon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LETHARGY_BUFF_ICON = ITEMS.register("lethargy_buff_icon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TRANCE_BUFF_ICON = ITEMS.register("trance_buff_icon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INSAND_BUFF_ICON = ITEMS.register("insand_buff_icon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_BUFF_ICON = ITEMS.register("chaos_buff_icon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DREAM_WISH_BUFF_ICON = ITEMS.register("dream_wish_buff_icon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COOK_BUFF_ICON = ITEMS.register("cook_buff_icon",
            () -> new Item(new Item.Properties()));

    // 帕秋莉宝典
    public static final RegistryObject<Item> SENIORS_DREAM_BOOK = ITEMS.register("seniors_dream_book",
            () -> new Item(new Item.Properties().stacksTo(1)));

    // ===== 音乐唱片 =====
    public static final RegistryObject<Item> SWEET_DREAM_MUSIC_DISC = ITEMS.register("sweet_dream_music_disc",
            () -> new RecordItem(0, ModSounds.SWEET_DREAM_MUSIC, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 2220));
    public static final RegistryObject<Item> SNOWFALL_DREAM_MUSIC_DISC = ITEMS.register("snowfall_dream_music_disc",
            () -> new RecordItem(0, ModSounds.SNOWFALL_DREAM_MUSIC, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 2520));

    //调试工具
    public static final RegistryObject<Item> DEBUG_SWORD = ITEMS.register("debug_sword", DebugSwordItem::new);
    public static final RegistryObject<Item> LOOT_GENERATOR = ITEMS.register("loot_generator", LootGeneratorItem::new);
    public static final RegistryObject<Item> MELT_DREAM_CRYSTAL_CHEST_RESET_TOOL = ITEMS.register("melt_dream_crystal_chest_reset_tool", () -> new MeltDreamCrystalChestResetToolItem(new Item.Properties()));
    public static final RegistryObject<Item> MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_0 = ITEMS.register("model_break_particle_provider_block_0", () -> new ModelBreakParticleProviderBlockItem(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_0.get(), new Item.Properties()));
    public static final RegistryObject<Item> MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_1 = ITEMS.register("model_break_particle_provider_block_1", () -> new ModelBreakParticleProviderBlockItem(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_1.get(), new Item.Properties()));
    public static final RegistryObject<Item> MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2 = ITEMS.register("model_break_particle_provider_block_2", () -> new ModelBreakParticleProviderBlockItem(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2.get(), new Item.Properties()));
    public static final RegistryObject<Item> MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_3 = ITEMS.register("model_break_particle_provider_block_3", () -> new ModelBreakParticleProviderBlockItem(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_3.get(), new Item.Properties()));

    //特殊道具
    public static final RegistryObject<Item> DREAM_HARP_OF_WANDERER = ITEMS.register("dream_harp_of_wanderer",
            DreamHarpOfWandererItem::new);
    public static final RegistryObject<Item> SAND_OF_TIME = ITEMS.register("sand_of_time", SandofTimeItem::new);
    public static final RegistryObject<Item> STORAGE_BAG = ITEMS.register("storage_bag", StorageBagItem::new);
    public static final RegistryObject<Item> LARGE_STORAGE_BAG = ITEMS.register("large_storage_bag", LargeStorageBagItem::new);
    public static final RegistryObject<Item> STRAWBERRY_HEART = ITEMS.register("strawberry_heart", StrawberryHeartItem::new);
    public static final RegistryObject<Item> SLIVER_BELL = ITEMS.register("silver_bell", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
            super.appendHoverText(stack, level, tooltip, flag);
            tooltip.add(Component.translatable("tooltip.pasterdream.sliver_bell"));
        }
    });

    //预言卡
    public static final RegistryObject<Item> EMPTY_PROPHECY_CARD = ITEMS.register("empty_prophecy_card",
            EmptyProphecyCardItem::new);
    public static final RegistryObject<Item> PROPHECY_CARD = ITEMS.register("prophecy_card",
            ProphecyCardItem::new);

    // 追忆
    public static final RegistryObject<Item> MEMORY_GEM = ITEMS.register("memory_gem",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EMPTY_VESSEL = ITEMS.register("empty_vessel",
            EmptyVesselItem::new);
    public static final RegistryObject<Item> QYM_CAT_EARS = ITEMS.register("qym_cat_ears",
            () -> new QymCatEarsItem(ModArmorMaterials.QYM, ArmorItem.Type.HELMET,
                    new Item.Properties().fireResistant().rarity(ModRarities.MIRACLE)));
    public static final RegistryObject<Item> QYM_WIND_SHIRT = ITEMS.register("qym_wind_shirt",
            () -> new QymWindShirtItem(ModArmorMaterials.QYM, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().fireResistant().rarity(ModRarities.MIRACLE)));
    public static final RegistryObject<Item> QYM_SWAYING_SKIRT = ITEMS.register("qym_swaying_skirt",
            () -> new QymSwayingSkirtItem(ModArmorMaterials.QYM, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().fireResistant().rarity(ModRarities.MIRACLE)));
    public static final RegistryObject<Item> QYM_CLOUD_BOOTS = ITEMS.register("qym_cloud_boots",
            () -> new QymCloudBootsItem(ModArmorMaterials.QYM, ArmorItem.Type.BOOTS,
                    new Item.Properties().fireResistant().rarity(ModRarities.MIRACLE)));

    // 机械光翼
    public static final RegistryObject<Item> MACHINE_LIGHT_WING = ITEMS.register("machine_light_wing",
            () -> new MachineLightWingItem(ModArmorMaterials.MACHINE_LIGHT_WING, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    // 天使之翼
    public static final RegistryObject<Item> ANGEL_WING = ITEMS.register("angel_wing",
            () -> new AngelWingItem(ModArmorMaterials.ANGEL_WING, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    // 遗忘之翼
    public static final RegistryObject<Item> FORSAKENS_WING = ITEMS.register("forsakens_wing",
            () -> new ForsakensWingItem(ModArmorMaterials.FORSAKENS_WING, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    // 厚翅甲壳
    public static final RegistryObject<Item> BLACK_BEETLE_CARAPACE = ITEMS.register("black_beetle_carapace",
            () -> new Item(new Item.Properties()));

    // 振音声膜
    public static final RegistryObject<Item> BLACK_BEETLE_VOCALCORD = ITEMS.register("black_beetle_vocalcord",
            () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, Level level, java.util.List<Component> tooltipComponents, TooltipFlag isAdvanced) {
                    tooltipComponents.add(Component.translatable("tooltip.pasterdream.black_beetle_vocalcord"));
                }
            });

    // 药剂瓶物品注册已移至 PotionBottleRegistry（world/item 包）

    // 刷怪蛋
    public static final RegistryObject<Item> PINK_CHICKEN_SPAWN_EGG = ITEMS.register("pink_chicken_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.PINK_CHICKEN, 0xf3e3f2, 0xbf038f, new Item.Properties()));
    public static final RegistryObject<Item> PINK_SLIME_SPAWN_EGG = ITEMS.register("pink_slime_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.PINK_SLIME, 0xe4a8d9, 0xd46daf, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_FOX_SPAWN_EGG = ITEMS.register("golden_fox_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GOLDEN_FOX, 0xe8c13a, 0x8b6914, new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_GOLEM_SPAWN_EGG = ITEMS.register("shadow_golem_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SHADOW_GOLEM, 0x191926, 0xA7A5B1, new Item.Properties()));
    public static final RegistryObject<Item> TERRORBEAK_SPAWN_EGG = ITEMS.register("terrorbeak_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.TERRORBEAK, 0x1c1c1c, 0x3d3d3d, new Item.Properties()));
    public static final RegistryObject<Item> CRAZY_TERRORBEAK_SPAWN_EGG = ITEMS.register("crazy_terrorbeak_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CRAZY_TERRORBEAK, -16777216, -11271421, new Item.Properties()));
    public static final RegistryObject<Item> WEAKENESS_TERRORBEAK_SPAWN_EGG = ITEMS.register("weakeness_terrorbeak_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.WEAKENESS_TERRORBEAK, -16777216, -13421773, new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_HAND_SPAWN_EGG = ITEMS.register("shadow_hand_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SHADOW_HAND, -12763843, -16316665, new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_GHOST_SPAWN_EGG = ITEMS.register("shadow_ghost_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SHADOW_GHOST, -591623, -9012613, new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_SQUEAL_GHOST_SPAWN_EGG = ITEMS.register("shadow_squeal_ghost_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SHADOW_SQUEAL_GHOST, -722951, -5317392, new Item.Properties()));
    public static final RegistryObject<Item> WAILING_SHADOW_GHOST_SPAWN_EGG = ITEMS.register("wailing_shadow_ghost_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.WAILING_SHADOW_GHOST, -985609, -9851188, new Item.Properties()));
    public static final RegistryObject<Item> FRIENDLY_SHADOW_GHOST_SPAWN_EGG = ITEMS.register("friendly_shadow_ghost_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.FRIENDLY_SHADOW_GHOST, -985866, -4334102, new Item.Properties()));
    public static final RegistryObject<Item> BLACK_BEETLE_SPAWN_EGG = ITEMS.register("black_beetle_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BLACK_BEETLE, -2698583, -11316914, new Item.Properties()));
    public static final RegistryObject<Item> BLACK_BEETLE_MOTHER_SPAWN_EGG = ITEMS.register("black_beetle_mother_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BLACK_BEETLE_MOTHER, -2436181, -9876718, new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_MAGICBALL_SPAWN_EGG = ITEMS.register("shadow_magicball_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SHADOW_MAGICBALL, -16777216, -15658735, new Item.Properties()));
    public static final RegistryObject<Item> SHADOW_TUNE_TOTEM_SPAWN_EGG = ITEMS.register("shadow_tune_totem_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SHADOW_TUNE_TOTEM, -15658735, -4473925, new Item.Properties()));
    public static final RegistryObject<Item> AARONCOS_LEFT_HAND_SPAWN_EGG = ITEMS.register("aaroncos_left_hand_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.AARONCOS_LEFT_HAND, -15987700, -11246213, new Item.Properties()));
    public static final RegistryObject<Item> AARONCOS_RIGHT_HAND_SPAWN_EGG = ITEMS.register("aaroncos_right_hand_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.AARONCOS_RIGHT_HAND, -15593198, -11189920, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
