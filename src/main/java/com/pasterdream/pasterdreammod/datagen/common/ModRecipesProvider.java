package com.pasterdream.pasterdreammod.datagen.common;

import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.datagen.util.RecipeHelpers;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider implements IConditionBuilder {

    /**
     * 将 ShapelessRecipeBuilder 产出的配方包装为自定义序列化器。
     * 用于研钵配方——研钵作为工具不消耗。
     */
    private void saveMortarCrafting(ShapelessRecipeBuilder builder, Consumer<FinishedRecipe> writer, String name) {
        builder.save(wrapped -> writer.accept(new FinishedRecipe() {
            @Override public void serializeRecipeData(JsonObject json) { wrapped.serializeRecipeData(json); }
            @Override public ResourceLocation getId() { return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name); }
            @Override public RecipeSerializer<?> getType() { return ModRecipes.MORTAR_CRAFTING_SERIALIZER.get(); }
            @Override public JsonObject serializeAdvancement() { return wrapped.serializeAdvancement(); }
            @Override public ResourceLocation getAdvancementId() { return wrapped.getAdvancementId(); }
        }), ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name));
    }

    public ModRecipesProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        woodRecipes(pWriter);
        dyeConversionRecipes(pWriter);
        blackStickRecipes(pWriter);
        materialRecipes(pWriter);
        upgradeKitRecipes(pWriter);
        copperToolRecipes(pWriter);
        copperArmorRecipes(pWriter);
        titaniumToolRecipes(pWriter);
        dyedreamToolRecipes(pWriter);
        titaniumArmorRecipes(pWriter);
        sculkArmorRecipes(pWriter);
        dyedreamArmorRecipes(pWriter);
        toolRecipes(pWriter);
        glassRecipes(pWriter);
        lanternRecipes(pWriter);
        budBuildingRecipes(pWriter);
        pinkMushroomRecipes(pWriter);
        pinkSlimeRecipes(pWriter);
        claypanRecipe(pWriter);
        dustRecipes(pWriter);
        quartzRecipes(pWriter);
        alloyRecipes(pWriter);
        miscOreRecipes(pWriter);
        calciteRecipes(pWriter);
        foodRecipes(pWriter);
        othersRecipes(pWriter);
    }

    // ===== 染梦木建材配方 =====

    private void woodRecipes(Consumer<FinishedRecipe> pWriter) {
        // 染梦原木 → 染梦木头
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_WOOD.get(), 3)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.DYEDREAM_LOG.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_LOG.get()), has(ModItems.DYEDREAM_LOG.get()))
                .save(pWriter);

        // 染梦原木 → 染梦木板 + 全套建材配方
        RecipeHelpers.plankFamilyRecipes(pWriter,
                ModItems.DYEDREAM_LOG.get(),
                ModItems.DYEDREAM_PLANKS.get(),
                ModItems.DYEDREAM_STAIRS.get(),
                ModItems.DYEDREAM_SLAB.get(),
                ModItems.DYEDREAM_FENCE.get(),
                ModItems.DYEDREAM_FENCE_GATE.get(),
                ModItems.DYEDREAM_DOOR.get(),
                ModItems.DYEDREAM_TRAPDOOR.get(),
                ModItems.DYEDREAM_PRESSURE_PLATE.get(),
                ModItems.DYEDREAM_BUTTON.get(),
                PasterDreamMod.MOD_ID);

        // 染梦木头 → 染梦木板
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_PLANKS.get(), 4)
                .requires(ModItems.DYEDREAM_WOOD.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_WOOD.get()), has(ModItems.DYEDREAM_WOOD.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_planks_from_wood");

        // 染梦木窗格 - 玻璃板 + 染梦木板
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_PANE.get(), 1)
                .requires(Items.GLASS_PANE)
                .requires(ModItems.DYEDREAM_PLANKS.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_PLANKS.get()), has(ModItems.DYEDREAM_PLANKS.get()))
                .save(pWriter);
    }

    // ===== 黑石棍配方 =====

    private void blackStickRecipes(Consumer<FinishedRecipe> pWriter) {
        // 黑石 + 黑曜石 → 黑石棍（斜向）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLACK_STICK.get(), 1)
                .pattern("  a")
                .pattern(" b ")
                .pattern("a  ")
                .define('a', Items.BLACKSTONE)
                .define('b', Items.OBSIDIAN)
                .unlockedBy(getHasName(Items.BLACKSTONE), has(Items.BLACKSTONE))
                .save(pWriter);

        // 黑石 + 黑曜石 → 黑石棍（直向）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLACK_STICK.get(), 1)
                .pattern("a")
                .pattern("b")
                .pattern("a")
                .define('a', Items.BLACKSTONE)
                .define('b', Items.OBSIDIAN)
                .unlockedBy(getHasName(Items.BLACKSTONE), has(Items.BLACKSTONE))
                .save(pWriter, PasterDreamMod.MOD_ID + ":black_stick_straight");

        // 黑石 + 哭泣黑曜石 → 黑石棍（斜向）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLACK_STICK.get(), 1)
                .pattern("  a")
                .pattern(" b ")
                .pattern("a  ")
                .define('a', Items.BLACKSTONE)
                .define('b', Items.CRYING_OBSIDIAN)
                .unlockedBy(getHasName(Items.CRYING_OBSIDIAN), has(Items.CRYING_OBSIDIAN))
                .save(pWriter, PasterDreamMod.MOD_ID + ":black_stick_from_crying_obsidian");
    }

    // ===== 铜工具配方 =====

    private void copperToolRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.COPPER_SWORD.get())
                .pattern("a")
                .pattern("a")
                .pattern("b")
                .define('a', Items.COPPER_INGOT)
                .define('b', Items.STICK)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_PICKAXE.get())
                .pattern("aaa")
                .pattern(" b ")
                .pattern(" b ")
                .define('a', Items.COPPER_INGOT)
                .define('b', Items.STICK)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_AXE.get())
                .pattern("aa")
                .pattern("ab")
                .pattern(" b")
                .define('a', Items.COPPER_INGOT)
                .define('b', Items.STICK)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_SHOVEL.get())
                .pattern("a")
                .pattern("b")
                .pattern("b")
                .define('a', Items.COPPER_INGOT)
                .define('b', Items.STICK)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_HOE.get())
                .pattern("aa")
                .pattern(" b")
                .pattern(" b")
                .define('a', Items.COPPER_INGOT)
                .define('b', Items.STICK)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
    }

    // ===== 铜装备配方 =====

    private void copperArmorRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.COPPER_HELMET.get())
                .pattern("aaa")
                .pattern("a a")
                .define('a', Items.COPPER_INGOT)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.COPPER_CHESTPLATE.get())
                .pattern("a a")
                .pattern("aaa")
                .pattern("aaa")
                .define('a', Items.COPPER_INGOT)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.COPPER_LEGGINGS.get())
                .pattern("aaa")
                .pattern("a a")
                .pattern("a a")
                .define('a', Items.COPPER_INGOT)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.COPPER_BOOTS.get())
                .pattern("a a")
                .pattern("a a")
                .define('a', Items.COPPER_INGOT)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
    }

    // ===== 钛金工具配方（升级配方） =====

    private void titaniumToolRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.TITANIUM_SWORD.get())
                .requires(Items.DIAMOND_SWORD)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.TITANIUM_PICKAXE.get())
                .requires(Items.DIAMOND_PICKAXE)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.TITANIUM_AXE.get())
                .requires(Items.DIAMOND_AXE)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.TITANIUM_SHOVEL.get())
                .requires(Items.DIAMOND_SHOVEL)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.TITANIUM_HOE.get())
                .requires(Items.DIAMOND_HOE)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter);

        // 锻造台配方：燧石 + 钻石工具 + 钛金升级套件 → 钛金工具
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.FLINT),
                        Ingredient.of(Items.DIAMOND_SWORD),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.TITANIUM_SWORD.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_sword_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.FLINT),
                        Ingredient.of(Items.DIAMOND_PICKAXE),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.TITANIUM_PICKAXE.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_pickaxe_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.FLINT),
                        Ingredient.of(Items.DIAMOND_AXE),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.TITANIUM_AXE.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_axe_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.FLINT),
                        Ingredient.of(Items.DIAMOND_SHOVEL),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.TITANIUM_SHOVEL.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_shovel_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.FLINT),
                        Ingredient.of(Items.DIAMOND_HOE),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.TITANIUM_HOE.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_hoe_smithing");
    }

    // ===== 材料配方 =====

    private void materialRecipes(Consumer<FinishedRecipe> pWriter) {
        // 线轴
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SPOOL.get(), 1)
                .pattern(" a ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', Items.STRING)
                .define('b', Items.STICK)
                .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                .save(pWriter);

        // 纺织布料
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FABRIC.get(), 1)
                .requires(Ingredient.of(ItemTags.WOOL))
                .requires(ModItems.COTTON.get())
                .requires(ModItems.SPOOL.get())
                .unlockedBy(getHasName(ModItems.SPOOL.get()), has(ModItems.SPOOL.get()))
                .save(pWriter);

        // 护甲板
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PROTECT_DECK.get(), 1)
                .pattern("aa")
                .pattern("bc")
                .define('a', Items.IRON_INGOT)
                .define('b', Items.LEATHER)
                .define('c', ModItems.FABRIC.get())
                .unlockedBy(getHasName(ModItems.FABRIC.get()), has(ModItems.FABRIC.get()))
                .save(pWriter);
    }

    // ===== 染梦合金工具配方 =====

    private void dyedreamToolRecipes(Consumer<FinishedRecipe> pWriter) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_BUD_NUGGET.get()),
                        Ingredient.of(ModItems.TITANIUM_SWORD.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.DYEDREAM_SWORD.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_sword_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_BUD_NUGGET.get()),
                        Ingredient.of(ModItems.TITANIUM_PICKAXE.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.DYEDREAM_PICKAXE.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_pickaxe_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_BUD_NUGGET.get()),
                        Ingredient.of(ModItems.TITANIUM_AXE.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.DYEDREAM_AXE.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_axe_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_BUD_NUGGET.get()),
                        Ingredient.of(ModItems.TITANIUM_SHOVEL.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.DYEDREAM_SHOVEL.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_shovel_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_BUD_NUGGET.get()),
                        Ingredient.of(ModItems.TITANIUM_HOE.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.DYEDREAM_HOE.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_hoe_smithing");

        // 染梦合金锤（工作台合成）
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DYEDREAM_HAMMER.get())
                .pattern("aaa")
                .pattern("aba")
                .pattern(" b ")
                .define('a', ModItems.DYEDREAM_ALLOY_INGOT.get())
                .define('b', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_ALLOY_INGOT.get()), has(ModItems.DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter);
    }

    // ===== 钛金装备配方 =====

    private void titaniumArmorRecipes(Consumer<FinishedRecipe> pWriter) {
        // 工作台升级：钻石装备 + 2钛金锭 + 黑石棍 → 钛金装备
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.TITANIUM_HELMET.get())
                .requires(Items.DIAMOND_HELMET)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_helmet_crafting");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.TITANIUM_CHESTPLATE.get())
                .requires(Items.DIAMOND_CHESTPLATE)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_chestplate_crafting");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.TITANIUM_LEGGINGS.get())
                .requires(Items.DIAMOND_LEGGINGS)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_leggings_crafting");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.TITANIUM_BOOTS.get())
                .requires(Items.DIAMOND_BOOTS)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_boots_crafting");

        // 工作台合成：钛金锭 + 护甲板 → 钛金装备
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TITANIUM_HELMET.get())
                .pattern("aba")
                .pattern("a a")
                .define('a', ModItems.TITANIUM_INGOT.get()).define('b', ModItems.PROTECT_DECK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_helmet_from_protect_deck");
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TITANIUM_CHESTPLATE.get())
                .pattern("a a")
                .pattern("bbb")
                .pattern("aba")
                .define('a', ModItems.PROTECT_DECK.get()).define('b', ModItems.TITANIUM_INGOT.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_chestplate_from_protect_deck");
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TITANIUM_LEGGINGS.get())
                .pattern("aba")
                .pattern("a a")
                .pattern("b b")
                .define('a', ModItems.TITANIUM_INGOT.get()).define('b', ModItems.PROTECT_DECK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_leggings_from_protect_deck");
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TITANIUM_BOOTS.get())
                .pattern("a a")
                .pattern("a a")
                .define('a', ModItems.TITANIUM_INGOT.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_boots_from_protect_deck");

        // 锻造台配方：护甲板 + 钻石装备 + 钛金升级套件 → 钛金装备
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.PROTECT_DECK.get()),
                        Ingredient.of(Items.DIAMOND_HELMET),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.TITANIUM_HELMET.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_helmet_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.PROTECT_DECK.get()),
                        Ingredient.of(Items.DIAMOND_CHESTPLATE),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.TITANIUM_CHESTPLATE.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_chestplate_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.PROTECT_DECK.get()),
                        Ingredient.of(Items.DIAMOND_LEGGINGS),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.TITANIUM_LEGGINGS.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_leggings_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.PROTECT_DECK.get()),
                        Ingredient.of(Items.DIAMOND_BOOTS),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.TITANIUM_BOOTS.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_boots_smithing");
    }

    // ===== 染梦合金装备配方 =====

    private void dyedreamArmorRecipes(Consumer<FinishedRecipe> pWriter) {
        // 工作台合成：染梦合金锭 + 护甲板 → 染梦合金装备
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DYEDREAM_HELMET.get())
                .pattern("aba")
                .pattern("a a")
                .define('a', ModItems.DYEDREAM_ALLOY_INGOT.get()).define('b', ModItems.PROTECT_DECK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_ALLOY_INGOT.get()), has(ModItems.DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_helmet_from_protect_deck");
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DYEDREAM_CHESTPLATE.get())
                .pattern("a a")
                .pattern("bbb")
                .pattern("aba")
                .define('a', ModItems.PROTECT_DECK.get()).define('b', ModItems.DYEDREAM_ALLOY_INGOT.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_ALLOY_INGOT.get()), has(ModItems.DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_chestplate_from_protect_deck");
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DYEDREAM_LEGGINGS.get())
                .pattern("aba")
                .pattern("a a")
                .pattern("b b")
                .define('a', ModItems.DYEDREAM_ALLOY_INGOT.get()).define('b', ModItems.PROTECT_DECK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_ALLOY_INGOT.get()), has(ModItems.DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_leggings_from_protect_deck");
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DYEDREAM_BOOTS.get())
                .pattern("a a")
                .pattern("a a")
                .define('a', ModItems.DYEDREAM_ALLOY_INGOT.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_ALLOY_INGOT.get()), has(ModItems.DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_boots_from_protect_deck");

        // 锻造台配方：护甲板 + 钛金装备 + 染梦升级套件 → 染梦合金装备
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.PROTECT_DECK.get()),
                        Ingredient.of(ModItems.TITANIUM_HELMET.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.DYEDREAM_HELMET.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_helmet_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.PROTECT_DECK.get()),
                        Ingredient.of(ModItems.TITANIUM_CHESTPLATE.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.DYEDREAM_CHESTPLATE.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_chestplate_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.PROTECT_DECK.get()),
                        Ingredient.of(ModItems.TITANIUM_LEGGINGS.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.DYEDREAM_LEGGINGS.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_leggings_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.PROTECT_DECK.get()),
                        Ingredient.of(ModItems.TITANIUM_BOOTS.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.DYEDREAM_BOOTS.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_boots_smithing");
    }

    // ===== 回响装备配方 =====

    private void sculkArmorRecipes(Consumer<FinishedRecipe> pWriter) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.SCULK_VEIN),
                        Ingredient.of(Items.NETHERITE_HELMET),
                        Ingredient.of(ModItems.SCULK_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.SCULK_HELMET.get())
                .unlocks("has_sculk_upgrade", has(ModItems.SCULK_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":sculk_helmet_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.SCULK_VEIN),
                        Ingredient.of(Items.NETHERITE_CHESTPLATE),
                        Ingredient.of(ModItems.SCULK_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.SCULK_CHESTPLATE.get())
                .unlocks("has_sculk_upgrade", has(ModItems.SCULK_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":sculk_chestplate_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.SCULK_VEIN),
                        Ingredient.of(Items.NETHERITE_LEGGINGS),
                        Ingredient.of(ModItems.SCULK_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.SCULK_LEGGINGS.get())
                .unlocks("has_sculk_upgrade", has(ModItems.SCULK_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":sculk_leggings_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.SCULK_VEIN),
                        Ingredient.of(Items.NETHERITE_BOOTS),
                        Ingredient.of(ModItems.SCULK_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.SCULK_BOOTS.get())
                .unlocks("has_sculk_upgrade", has(ModItems.SCULK_UPGRADE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":sculk_boots_smithing");
    }

    // ===== 升级套件配方 =====

    private void upgradeKitRecipes(Consumer<FinishedRecipe> pWriter) {
        // 钛金升级套件
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TITANIUM_UPGRADE.get(), 1)
                .pattern(" a ")
                .pattern("bcb")
                .pattern("dbd")
                .define('a', Items.IRON_INGOT)
                .define('b', ModItems.TITANIUM_INGOT.get())
                .define('c', Items.STONE_BUTTON)
                .define('d', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter);

        // 回响升级套件
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SCULK_UPGRADE.get(), 1)
                .pattern(" a ")
                .pattern("bcb")
                .pattern("dbd")
                .define('a', Items.IRON_INGOT)
                .define('b', Items.ECHO_SHARD)
                .define('c', ModItems.SCULK_HEART.get())
                .define('d', Items.SCULK_VEIN)
                .unlockedBy(getHasName(ModItems.SCULK_HEART.get()), has(ModItems.SCULK_HEART.get()))
                .save(pWriter);

        // 染梦升级套件
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DYEDREAM_UPGRADE.get(), 1)
                .pattern(" a ")
                .pattern("bcb")
                .pattern("dbd")
                .define('a', Items.IRON_INGOT)
                .define('b', ModItems.DYEDREAM_ALLOY_INGOT.get())
                .define('c', Items.GHAST_TEAR)
                .define('d', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_ALLOY_INGOT.get()), has(ModItems.DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter);
    }

    // ===== 染梦染料转化配方 =====

    private void dyeConversionRecipes(Consumer<FinishedRecipe> pWriter) {
        dyeConversion(pWriter, Items.DIRT, ModBlocks.DYEDREAM_DIRT.get().asItem());
        dyeConversion(pWriter, Items.GRASS_BLOCK, ModBlocks.DYEDREAM_GRASS_BLOCK.get().asItem());
        dyeConversionTag(pWriter, ItemTags.LEAVES, ModBlocks.DYEDREAM_LEAVES.get().asItem());
        dyeConversionTag(pWriter, ItemTags.LOGS, ModBlocks.DYEDREAM_LOG.get().asItem());
        dyeConversionTag(pWriter, ItemTags.PLANKS, ModBlocks.DYEDREAM_PLANKS.get().asItem());
        dyeConversionTag(pWriter, ItemTags.SAPLINGS, ModBlocks.DYEDREAM_SAPLING.get().asItem());
        dyeConversion(pWriter, Items.QUARTZ, ModItems.DYEDREAM_QUARTZ.get());
        dyeConversion(pWriter, Items.QUARTZ_BLOCK, ModItems.DYEDREAM_QUARTZ_BLOCK.get());
        dyeConversion(pWriter, Items.SHROOMLIGHT, ModBlocks.PINK_SHROOMLIGHT.get().asItem());
        dyeConversion(pWriter, Items.SUGAR, ModItems.AMBER_CANDY.get());
        dyeConversion(pWriter, Items.ICE, ModBlocks.DYEDREAM_ICE.get().asItem());
        dyeConversion(pWriter, Items.PACKED_ICE, ModBlocks.DYEDREAM_PACKED_ICE.get().asItem());
        dyeConversion(pWriter, Items.GLASS,ModBlocks.DYEDREAM_GLASS.get().asItem());
    }

    // ===== 工具与基础材料配方 =====

    private void toolRecipes(Consumer<FinishedRecipe> pWriter) {
        // 研钵
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MORTAR.get(), 1)
                .pattern("  a")
                .pattern("bcb")
                .pattern(" b ")
                .define('a', Items.IRON_NUGGET)
                .define('b', Items.POLISHED_DEEPSLATE)
                .define('c', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MORTAR.get(), 1)
                .requires(ModItems.MORTAR.get())
                .unlockedBy(getHasName(ModItems.MORTAR.get()), has(ModItems.MORTAR.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "mortar_from_mortar"));

        // 染梦染料：粉尘碎片 + 骨粉 + 研钵（研钵不消耗）
        saveMortarCrafting(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DYEDREAM_DYE.get(), 7)
                        .requires(ModItems.DYEDREAM_DUST_PIECE.get())
                        .requires(Items.BONE_MEAL)
                        .requires(ModItems.MORTAR.get())
                        .unlockedBy(getHasName(ModItems.DYEDREAM_DUST_PIECE.get()), has(ModItems.DYEDREAM_DUST_PIECE.get())),
                pWriter, "dyedream_dye_from_dust_piece");

        // 染梦染料：粉尘 + 骨块 + 研钵（研钵不消耗）
        saveMortarCrafting(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DYEDREAM_DYE.get(), 63)
                        .requires(ModItems.DYEDREAM_DUST.get())
                        .requires(Items.BONE_BLOCK)
                        .requires(ModItems.MORTAR.get())
                        .unlockedBy(getHasName(ModItems.DYEDREAM_DUST.get()), has(ModItems.DYEDREAM_DUST.get())),
                pWriter, "dyedream_dye_from_dust");
    }

    // ===== 染梦玻璃系列配方 =====

    private void glassRecipes(Consumer<FinishedRecipe> pWriter) {
        // 染梦沙 → 染梦玻璃（熔炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.DYEDREAM_SAND.get()),
                        RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_GLASS.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModBlocks.DYEDREAM_SAND.get()), has(ModBlocks.DYEDREAM_SAND.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_glass_from_smelting");

        // 染梦玻璃 → 16× 染梦玻璃板
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_GLASS_PANE.get(), 16)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModBlocks.DYEDREAM_GLASS.get())
                .unlockedBy(getHasName(ModBlocks.DYEDREAM_GLASS.get()), has(ModBlocks.DYEDREAM_GLASS.get()))
                .save(pWriter);

        // 4× 染梦玻璃 → 4× 雕花染梦玻璃
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CARVE_DYEDREAM_GLASS.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModBlocks.DYEDREAM_GLASS.get())
                .unlockedBy(getHasName(ModBlocks.DYEDREAM_GLASS.get()), has(ModBlocks.DYEDREAM_GLASS.get()))
                .save(pWriter);

        // 雕花染梦玻璃 → 16× 雕花玻璃板
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CARVE_DYEDREAM_GLASS_PANE.get(), 16)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModBlocks.CARVE_DYEDREAM_GLASS.get())
                .unlockedBy(getHasName(ModBlocks.CARVE_DYEDREAM_GLASS.get()), has(ModBlocks.CARVE_DYEDREAM_GLASS.get()))
                .save(pWriter);

        // 雕花染梦玻璃 + 金粒 → 镶金雕花染梦玻璃
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get(), 1)
                .pattern("ab")
                .define('a', ModBlocks.CARVE_DYEDREAM_GLASS.get())
                .define('b', Items.GOLD_NUGGET)
                .unlockedBy(getHasName(ModBlocks.CARVE_DYEDREAM_GLASS.get()), has(ModBlocks.CARVE_DYEDREAM_GLASS.get()))
                .save(pWriter);

        // 镶金雕花染梦玻璃 → 16× 镶金玻璃板
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GOLD_CARVE_DYEDREAM_GLASS_PANE.get(), 16)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get())
                .unlockedBy(getHasName(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get()), has(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get()))
                .save(pWriter);
    }



    private void lanternRecipes(Consumer<FinishedRecipe> pWriter) {
        // 染梦水晶灯
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get(), 2)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', ModItems.DYEDREAM_QUARTZ.get())
                .define('b', ModBlocks.DYEDREAM_GLASS.get())
                .define('c', ModItems.DYEDREAM_DYE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DYE.get()), has(ModItems.DYEDREAM_DYE.get()))
                .save(pWriter);

        // 染梦灯笼（铁粒围染料，仿原版灯笼配方）
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_LANTERN.get(), 1)
                .pattern("aaa")
                .pattern("aba")
                .pattern("aaa")
                .define('a', net.minecraft.world.item.Items.IRON_NUGGET)
                .define('b', ModItems.DYEDREAM_DYE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DYE.get()), has(ModItems.DYEDREAM_DYE.get()))
                .save(pWriter);
    }

    // ===== 染梦水晶建材配方 =====

    private void budBuildingRecipes(Consumer<FinishedRecipe> pWriter) {
        // 染梦玻璃 + 晶芽粒 → 4× 染梦水晶砖
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_BUD_BLOCK.get(), 4)
                .pattern("ab")
                .pattern("ba")
                .define('a', ModBlocks.DYEDREAM_GLASS.get())
                .define('b', ModItems.DYEDREAM_BUD_NUGGET.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_BUD_NUGGET.get()), has(ModItems.DYEDREAM_BUD_NUGGET.get()))
                .save(pWriter);

        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModBlocks.DYEDREAM_BUD_BLOCK.get(), ModBlocks.DYEDREAM_BUD_STAIRS.get(),
                ModBlocks.DYEDREAM_BUD_SLAB.get(), ModBlocks.DYEDREAM_BUD_WALL.get(),
                PasterDreamMod.MOD_ID);
    }

    // ===== 粉顶菇配方 =====

    private void pinkMushroomRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.PINK_DYE, 1)
                .requires(ModBlocks.PINK_MUSHROOM.get())
                .unlockedBy(getHasName(ModBlocks.PINK_MUSHROOM.get()), has(ModBlocks.PINK_MUSHROOM.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":pink_dye_from_pink_mushroom");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.PINK_DYE, 2)
                .requires(ModBlocks.TALL_PINK_MUSHROOM.get())
                .unlockedBy(getHasName(ModBlocks.TALL_PINK_MUSHROOM.get()), has(ModBlocks.TALL_PINK_MUSHROOM.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":pink_dye_from_tall_pink_mushroom");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.TALL_PINK_MUSHROOM.get()),
                        RecipeCategory.MISC, ModBlocks.PINK_MUSHROOM.get().asItem(), 2)
                .unlockedBy(getHasName(ModBlocks.TALL_PINK_MUSHROOM.get()), has(ModBlocks.TALL_PINK_MUSHROOM.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":pink_mushroom_from_tall_pink_mushroom_stonecutting");
    }

    // ===== 粉色粘液配方 =====

    private void pinkSlimeRecipes(Consumer<FinishedRecipe> pWriter) {
        RecipeHelpers.storageCompress(pWriter, ModItems.PINK_SLIMEBALL.get(), ModItems.PINK_SLIME_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.PINK_SLIME_BLOCK.get(), ModItems.PINK_SLIMEBALL.get(), PasterDreamMod.MOD_ID);
    }

    // ===== 陶盆配方 =====

    private void claypanRecipe(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CLAYPAN.get(), 2)
                .pattern("aba")
                .pattern("aaa")
                .define('a', ItemTags.TERRACOTTA)
                .define('b', Items.WHITE_DYE)
                .unlockedBy(getHasName(Items.WHITE_DYE), has(Items.WHITE_DYE))
                .save(pWriter);
    }

    // ===== 染梦粉尘配方 =====

    private void dustRecipes(Consumer<FinishedRecipe> pWriter) {
        // 烧制：染梦粉尘矿石 → 染梦粉尘碎片
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.DYEDREAM_DUST_ORE.get()),
                        RecipeCategory.MISC, ModItems.DYEDREAM_DUST_PIECE.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.DYEDREAM_DUST_ORE.get()), has(ModItems.DYEDREAM_DUST_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_dust_piece_from_smelting");

        // 染梦粉尘 → 9× 染梦粉尘碎片
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DYEDREAM_DUST_PIECE.get(), 9)
                .requires(ModItems.DYEDREAM_DUST.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DUST.get()), has(ModItems.DYEDREAM_DUST.get()))
                .save(pWriter);

        // 9× 染梦粉尘碎片 → 染梦粉尘
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DYEDREAM_DUST.get(), 1)
                .requires(ModItems.DYEDREAM_DUST_PIECE.get(), 9)
                .unlockedBy(getHasName(ModItems.DYEDREAM_DUST_PIECE.get()), has(ModItems.DYEDREAM_DUST_PIECE.get()))
                .save(pWriter);
    }

    // ===== 染梦石英配方 =====

    private void quartzRecipes(Consumer<FinishedRecipe> pWriter) {
        // 烧制：染梦石英矿石 → 染梦石英
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.DYEDREAM_QUARTZ_ORE.get()),
                        RecipeCategory.MISC, ModItems.DYEDREAM_QUARTZ.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_ORE.get()), has(ModItems.DYEDREAM_QUARTZ_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_quartz_from_smelting");

        // 烧制：染梦石英块 → 平滑染梦石英块
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.DYEDREAM_QUARTZ_BLOCK.get()),
                        RecipeCategory.MISC, ModItems.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":smooth_dyedream_quartz_block_from_smelting");

        // 4×染梦石英 → 染梦石英块
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_QUARTZ_BLOCK.get(), 1)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.DYEDREAM_QUARTZ.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ.get()), has(ModItems.DYEDREAM_QUARTZ.get()))
                .save(pWriter);

        // 4×染梦石英块 → 染梦石英砖 ×4
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.BRICKS_DYEDREAM_QUARTZ_BLOCK.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter);

        // 2×染梦石英块 → 染梦石英柱 ×2
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.PILLAR_DYEDREAM_QUARTZ_BLOCK.get(), 2)
                .pattern("a")
                .pattern("a")
                .define('a', ModItems.DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter);

        // 3×染梦石英块 → 染梦石英台阶 ×6
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), 6)
                .pattern("aaa")
                .define('a', ModItems.DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter);

        // 染梦石英块 → 染梦石英楼梯 ×4
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_QUARTZ_BLOCK_STAIRS.get(), 4)
                .pattern("a  ")
                .pattern("aa ")
                .pattern("aaa")
                .define('a', ModItems.DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter);

        // 染梦石英块 → 染梦石英墙 ×6
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_QUARTZ_BLOCK_WALL.get(), 6)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModItems.DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter);

        // 2×染梦石英台阶 → 錾制染梦石英块
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_DYEDREAM_QUARTZ_BLOCK.get(), 1)
                .pattern("a")
                .pattern("a")
                .define('a', ModItems.DYEDREAM_QUARTZ_BLOCK_SLAB.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK_SLAB.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK_SLAB.get()))
                .save(pWriter);

        // 切石机配方
        var quartzIngredient = Ingredient.of(ModItems.DYEDREAM_QUARTZ_BLOCK.get());

        SingleItemRecipeBuilder.stonecutting(quartzIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_quartz_block_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(quartzIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":smooth_dyedream_quartz_block_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(quartzIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.BRICKS_DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":bricks_dyedream_quartz_block_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(quartzIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.PILLAR_DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":pillar_dyedream_quartz_block_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(quartzIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":chiseled_dyedream_quartz_block_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(quartzIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_QUARTZ_BLOCK_STAIRS.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_quartz_block_stairs_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(quartzIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), 2)
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_quartz_block_slab_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(quartzIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_QUARTZ_BLOCK_WALL.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_quartz_block_wall_from_stonecutting");
    }

    // ===== 染梦合金配方 =====

    private void alloyRecipes(Consumer<FinishedRecipe> pWriter) {
        // 染梦合金锭粗胚：钛金锭 + 染梦粉尘 + 4×染梦晶芽粒
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_DYEDREAM_ALLOY_INGOT.get(), 1)
                .requires(ModItems.TITANIUM_INGOT.get())
                .requires(ModItems.DYEDREAM_DUST.get())
                .requires(ModItems.DYEDREAM_BUD_NUGGET.get(), 4)
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter);

        // 染梦合金锭粗胚 → 染梦合金锭（熔炉 + 高炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()),
                        RecipeCategory.MISC, ModItems.DYEDREAM_ALLOY_INGOT.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()), has(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_alloy_ingot_from_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()),
                        RecipeCategory.MISC, ModItems.DYEDREAM_ALLOY_INGOT.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()), has(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_alloy_ingot_from_blasting");

        // 染梦合金锭 ↔ 粒 ↔ 块
        RecipeHelpers.storageCompress(pWriter, ModItems.DYEDREAM_ALLOY_NUGGET.get(), ModItems.DYEDREAM_ALLOY_INGOT.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.DYEDREAM_ALLOY_INGOT.get(), ModItems.DYEDREAM_ALLOY_NUGGET.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageCompress(pWriter, ModItems.DYEDREAM_ALLOY_INGOT.get(), ModBlocks.DYEDREAM_ALLOY_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModBlocks.DYEDREAM_ALLOY_BLOCK.get(), ModItems.DYEDREAM_ALLOY_INGOT.get(), PasterDreamMod.MOD_ID);
    }

    // ===== 矿石、锭、块杂项配方 =====

    private void miscOreRecipes(Consumer<FinishedRecipe> pWriter) {
        // ===== 钛金系列 =====
        // 钛矿石 → 钛金锭（熔炉 + 高炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.TITANIUM_ORE.get()),
                        RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.TITANIUM_ORE.get()), has(ModItems.TITANIUM_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_ingot_from_titanium_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.TITANIUM_ORE.get()),
                        RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.TITANIUM_ORE.get()), has(ModItems.TITANIUM_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_ingot_from_titanium_ore_blasting");
        // 深层钛矿石 → 钛金锭（熔炉 + 高炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.DEEPSLATE_TITANIUM_ORE.get()),
                        RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.DEEPSLATE_TITANIUM_ORE.get()), has(ModItems.DEEPSLATE_TITANIUM_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_ingot_from_deepslate_titanium_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.DEEPSLATE_TITANIUM_ORE.get()),
                        RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.DEEPSLATE_TITANIUM_ORE.get()), has(ModItems.DEEPSLATE_TITANIUM_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_ingot_from_deepslate_titanium_ore_blasting");
        // 粗钛 → 钛金锭（熔炉 + 高炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_TITANIUM.get()),
                        RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.RAW_TITANIUM.get()), has(ModItems.RAW_TITANIUM.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_ingot_from_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_TITANIUM.get()),
                        RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.RAW_TITANIUM.get()), has(ModItems.RAW_TITANIUM.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_ingot_from_blasting");
        // 锭 ↔ 粒 ↔ 块
        RecipeHelpers.storageDecompress(pWriter, ModItems.TITANIUM_INGOT.get(), ModItems.TITANIUM_NUGGET.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageCompress(pWriter, ModItems.TITANIUM_NUGGET.get(), ModItems.TITANIUM_INGOT.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageCompress(pWriter, ModItems.RAW_TITANIUM.get(), ModItems.RAW_TITANIUM_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.RAW_TITANIUM_BLOCK.get(), ModItems.RAW_TITANIUM.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageCompress(pWriter, ModItems.TITANIUM_INGOT.get(), ModItems.TITANIUM_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.TITANIUM_BLOCK.get(), ModItems.TITANIUM_INGOT.get(), PasterDreamMod.MOD_ID);

        // ===== 炙焰金系列 =====
        // 炙焰金矿石 → 炙焰金锭（熔炉 + 高炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.MOLTEN_GOLD_ORE.get()),
                        RecipeCategory.MISC, ModItems.MOLTEN_GOLD_INGOT.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_ORE.get()), has(ModItems.MOLTEN_GOLD_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":molten_gold_ingot_from_molten_gold_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.MOLTEN_GOLD_ORE.get()),
                        RecipeCategory.MISC, ModItems.MOLTEN_GOLD_INGOT.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_ORE.get()), has(ModItems.MOLTEN_GOLD_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":molten_gold_ingot_from_molten_gold_ore_blasting");
        // 粗炙焰金 → 炙焰金锭（熔炉 + 高炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_MOLTEN_GOLD.get()),
                        RecipeCategory.MISC, ModItems.MOLTEN_GOLD_INGOT.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.RAW_MOLTEN_GOLD.get()), has(ModItems.RAW_MOLTEN_GOLD.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":moltengold_ingot_from_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_MOLTEN_GOLD.get()),
                        RecipeCategory.MISC, ModItems.MOLTEN_GOLD_INGOT.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.RAW_MOLTEN_GOLD.get()), has(ModItems.RAW_MOLTEN_GOLD.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":moltengold_ingot_from_blasting");
        // 锭 ↔ 粒 ↔ 块
        RecipeHelpers.storageDecompress(pWriter, ModItems.MOLTEN_GOLD_INGOT.get(), ModItems.MOLTEN_GOLD_NUGGET.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageCompress(pWriter, ModItems.MOLTEN_GOLD_NUGGET.get(), ModItems.MOLTEN_GOLD_INGOT.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageCompress(pWriter, ModItems.MOLTEN_GOLD_INGOT.get(), ModItems.MOLTEN_GOLD_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.MOLTEN_GOLD_BLOCK.get(), ModItems.MOLTEN_GOLD_INGOT.get(), PasterDreamMod.MOD_ID);

        // ===== 盈能紫水晶 =====
        // 紫水晶碎片 + 爆裂紫颂果 + 龙息 → 盈能紫水晶
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CHARGED_AMETHYST.get(), 1)
                .requires(Items.AMETHYST_SHARD)
                .requires(Items.POPPED_CHORUS_FRUIT)
                .requires(Items.DRAGON_BREATH)
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(pWriter);
        // 盈能紫水晶 ↔ 盈能紫水晶块（4:1）
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CHARGED_AMETHYST_BLOCK.get(), 1)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.CHARGED_AMETHYST.get())
                .unlockedBy(getHasName(ModItems.CHARGED_AMETHYST.get()), has(ModItems.CHARGED_AMETHYST.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.CHARGED_AMETHYST.get(), 4)
                .requires(ModItems.CHARGED_AMETHYST_BLOCK.get())
                .unlockedBy(getHasName(ModItems.CHARGED_AMETHYST_BLOCK.get()), has(ModItems.CHARGED_AMETHYST_BLOCK.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":charged_amethyst_from_charged_amethyst_block");

        // ===== 盐 =====
        RecipeHelpers.storageCompress(pWriter, ModItems.SALT.get(), ModItems.SALT_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.SALT_BLOCK.get(), ModItems.SALT.get(), PasterDreamMod.MOD_ID);
        // 盐：研钵 + 粗盐（研钵不消耗）
        saveMortarCrafting(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SALT.get(), 1)
                        .requires(ModItems.MORTAR.get())
                        .requires(ModItems.COARSE_SALT.get())
                        .unlockedBy(getHasName(ModItems.COARSE_SALT.get()), has(ModItems.COARSE_SALT.get())),
                pWriter, "salt_from_coarse_salt");

        // ===== 灵魂系列 =====
        // 灵魂矿土 → 灵魂粉尘（熔炉 + 高炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.SOUL_ORE.get()),
                        RecipeCategory.MISC, ModItems.SOUL_DUST.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.SOUL_ORE.get()), has(ModItems.SOUL_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":soul_dust_from_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.SOUL_ORE.get()),
                        RecipeCategory.MISC, ModItems.SOUL_DUST.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.SOUL_ORE.get()), has(ModItems.SOUL_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":soul_dust_from_blasting");
        // 灵魂精华：1×恶魂之泪 + 8×灵魂粉尘
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_ESSENCE.get(), 1)
                .pattern("aaa")
                .pattern("aba")
                .pattern("aaa")
                .define('a', ModItems.SOUL_DUST.get())
                .define('b', Items.GHAST_TEAR)
                .unlockedBy(getHasName(ModItems.SOUL_DUST.get()), has(ModItems.SOUL_DUST.get()))
                .save(pWriter);
        // 魔法石：1×钻石 + 8×灵魂粉尘
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MAGIC_STONE.get(), 32)
                .pattern("ab")
                .pattern("bb")
                .define('a', Items.DIAMOND)
                .define('b', ModItems.SOUL_DUST.get())
                .unlockedBy(getHasName(ModItems.SOUL_DUST.get()), has(ModItems.SOUL_DUST.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":magic_stone_from_diamond");
        // 魔法石：1×冰凌晶芽 + 8×灵魂粉尘
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MAGIC_STONE.get(), 32)
                .pattern("ab")
                .pattern("bb")
                .define('a', ModBlocks.ICE_BUD.get())
                .define('b', ModItems.SOUL_DUST.get())
                .unlockedBy(getHasName(ModItems.SOUL_DUST.get()), has(ModItems.SOUL_DUST.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":magic_stone_from_ice_bud");
    }

    // ===== 方解石系列配方 =====

    private void calciteRecipes(Consumer<FinishedRecipe> pWriter) {
        // 4× 方解石 → 4× 磨制方解石
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.POLISHED_CALCITE.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', Items.CALCITE)
                .unlockedBy(getHasName(Items.CALCITE), has(Items.CALCITE))
                .save(pWriter);

        // 磨制方解石 → 楼梯/台阶/墙 + 切石机
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.POLISHED_CALCITE.get(), ModItems.POLISHED_CALCITE_STAIRS.get(),
                ModItems.POLISHED_CALCITE_SLAB.get(), ModItems.POLISHED_CALCITE_WALL.get(),
                PasterDreamMod.MOD_ID);

        // 2×2 磨制方解石 → 4× 方解石瓦
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CALCITE_TILES.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.POLISHED_CALCITE.get())
                .unlockedBy(getHasName(ModItems.POLISHED_CALCITE.get()), has(ModItems.POLISHED_CALCITE.get()))
                .save(pWriter);

        // 方解石瓦 → 楼梯/台阶/墙 + 切石机
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.CALCITE_TILES.get(), ModItems.CALCITE_TILES_STAIRS.get(),
                ModItems.CALCITE_TILES_SLAB.get(), ModItems.CALCITE_TILES_WALL.get(),
                PasterDreamMod.MOD_ID);

        // 跨系列切石机配方
        var calciteIngredient = Ingredient.of(Items.CALCITE);
        var polishedCalciteIngredient = Ingredient.of(ModItems.POLISHED_CALCITE.get());

        SingleItemRecipeBuilder.stonecutting(calciteIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.POLISHED_CALCITE.get())
                .unlockedBy(getHasName(Items.CALCITE), has(Items.CALCITE))
                .save(pWriter, PasterDreamMod.MOD_ID + ":polished_calcite_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(polishedCalciteIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.CALCITE_TILES.get())
                .unlockedBy(getHasName(ModItems.POLISHED_CALCITE.get()), has(ModItems.POLISHED_CALCITE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":calcite_tiles_from_stonecutting");
    }

    // ===== 食物相关合成配方 =====

    private void foodRecipes(Consumer<FinishedRecipe> pWriter) {
        // 玻璃罐合成（支持所有木台阶tag和玻璃板tag）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GLASS_JAR.get(), 8)
                .pattern("aba")
                .pattern("a a")
                .pattern("aaa")
                .define('a', Ingredient.of(ItemTags.create(
                        ResourceLocation.fromNamespaceAndPath("forge", "glass_panes"))))
                .define('b', ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(Items.GLASS_PANE), has(Items.GLASS_PANE))
                .save(pWriter);

        // 玻璃杯合成配方（支持所有玻璃板tag）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GLASS_CUP.get(), 4)
                .pattern("a a")
                .pattern("a a")
                .pattern(" a ")
                .define('a', Ingredient.of(ItemTags.create(
                        ResourceLocation.fromNamespaceAndPath("forge", "glass_panes"))))
                .unlockedBy(getHasName(Items.GLASS_PANE), has(Items.GLASS_PANE))
                .save(pWriter);

        // 重做酵母合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_YEAST.get(), 4)
                .requires(ModItems.GLASS_JAR.get(), 4)
                .requires(ModItems.FLOUR.get(), 1)
                .requires(ModItems.GLASS_JAR_OF_WATER.get(), 1)
                .requires(ModItems.GLASS_JAR_OF_YEAST.get(), 1)
                .requires(Items.SUGAR, 1)
                .unlockedBy(getHasName(ModItems.GLASS_JAR.get()), has(ModItems.GLASS_JAR.get()))
                .save(pWriter);

        // 水罐合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_WATER.get(), 8)
                .requires(Items.WATER_BUCKET, 1)
                .requires(ModItems.GLASS_JAR.get(), 8)
                .unlockedBy(getHasName(Items.WATER_BUCKET), has(Items.WATER_BUCKET))
                .save(pWriter);

        // 牛奶罐合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_MILK.get(), 8)
                .requires(Items.MILK_BUCKET, 1)
                .requires(ModItems.GLASS_JAR.get(), 8)
                .unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET))
                .save(pWriter);

        // 巧克力合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CHOCOLATE.get(), 1)
                .requires(Items.COCOA_BEANS, 2)
                .requires(Items.SUGAR, 1)
                .unlockedBy(getHasName(Items.SUGAR), has(Items.SUGAR))
                .save(pWriter);

        // 面团合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DOUGH.get(), 1)
                .requires(ModItems.FLOUR.get(), 1)
                .requires(ModItems.GLASS_JAR_OF_WATER.get(), 2)
                .unlockedBy(getHasName(ModItems.FLOUR.get()), has(ModItems.FLOUR.get()))
                .save(pWriter);

        // 蛋液面团合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DOUGH_WITH_EGG.get(), 1)
                .requires(Ingredient.of(ItemTags.create(
                        ResourceLocation.fromNamespaceAndPath("forge", "eggs"))), 1)
                .requires(ModItems.DOUGH.get(), 2)
                .unlockedBy(getHasName(ModItems.DOUGH.get()), has(ModItems.DOUGH.get()))
                .save(pWriter);

        // 蛋糕胚合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CAKE_BASE.get(), 2)
                .requires(ModItems.DOUGH_WITH_EGG.get(), 1)
                .requires(Items.SUGAR, 1)
                .requires(ModItems.GLASS_JAR_OF_YEAST.get(), 1)
                .unlockedBy(getHasName(ModItems.DOUGH_WITH_EGG.get()), has(ModItems.DOUGH_WITH_EGG.get()))
                .save(pWriter);

        // 奶油小蛋糕合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CREAM_BUN_CAKE.get(), 1)
                .requires(ModItems.CAKE_BASE.get(), 1)
                .requires(ModItems.GLASS_JAR_OF_MILK.get(), 1)
                .unlockedBy(getHasName(ModItems.CAKE_BASE.get()), has(ModItems.CAKE_BASE.get()))
                .save(pWriter);

        // 六种蛋糕合成
        RecipeHelpers.cake(pWriter, Items.SWEET_BERRIES, ModItems.BERRY_BUN_CAKE.get());
        RecipeHelpers.cake(pWriter, Items.POTATO, ModItems.TUBER_BUN_CAKE.get());
        RecipeHelpers.cake(pWriter, Items.MELON_SLICE, ModItems.WATERMELON_BUN_CAKE.get());
        RecipeHelpers.cake(pWriter, Items.PUMPKIN, ModItems.PUMPKIN_BUN_CAKE.get());
        RecipeHelpers.cake(pWriter, Items.GLOW_BERRIES, ModItems.GLOW_BERRY_BUN_CAKE.get());
        RecipeHelpers.cake(pWriter, ModItems.DYEDREAM_FRUIT.get(), ModItems.DYEDREAM_FRUIT_BUN_CAKE.get());

        // 巧克力抹茶蛋糕合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CHOCOLATE_MATCHA_CAKE.get(), 1)
                .requires(ModItems.CAKE_BASE.get(), 1)
                .requires(Ingredient.of(ItemTags.LEAVES), 1)
                .requires(ModItems.CHOCOLATE.get(), 1)
                .unlockedBy(getHasName(ModItems.CAKE_BASE.get()), has(ModItems.CAKE_BASE.get()))
                .save(pWriter);

        // 三种心形巧克力合成（全部重做）
        RecipeHelpers.heart_chocolate(pWriter, Items.COCOA_BEANS, ModItems.HEART_CHOCOLATE.get());
        RecipeHelpers.heart_chocolate(pWriter, ModItems.GLASS_JAR_OF_MILK.get(), ModItems.WHITE_HEART_CHOCOLATE.get());
        RecipeHelpers.heart_chocolate(pWriter, ModItems.DYEDREAM_DUST.get(), ModItems.PINK_HEART_CHOCOLATE.get());

        // 拐杖糖合成配方
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CANDY_CANE.get(), 1)
                .pattern("ab ")
                .pattern("ba ")
                .pattern(" b ")
                .define('a', ModItems.AMBER_CANDY.get())
                .define('b', Items.SUGAR)
                .unlockedBy(getHasName(ModItems.AMBER_CANDY.get()), has(ModItems.AMBER_CANDY.get()))
                .save(pWriter);

        // 姜饼人合成配方
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GINGERBREAD_MAN.get(), 1)
                .pattern(" b ")
                .pattern("aca")
                .pattern(" d ")
                .define('a',  Items.SUGAR)
                .define('b', Items.RED_DYE)
                .define('c', ModItems.WAFER_BISCUIT.get())
                .define('d', Items.SPRUCE_LEAVES)
                .unlockedBy(getHasName(Items.SUGAR), has(Items.SUGAR))
                .save(pWriter);

        // 泡泡糖合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BUBBLE_GUM.get(), 2)
                .requires(ModItems.AMBER_CANDY.get(),2)
                .requires(ModItems.PINK_SLIMEBALL.get(), 1)
                .unlockedBy(getHasName(ModItems.AMBER_CANDY.get()), has(ModItems.AMBER_CANDY.get()))
                .save(pWriter);

        // 跳跳糖合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.POPPING_CANDY.get(), 2)
                .requires(Items.SUGAR,1)
                .requires(ModItems.AMBER_CANDY.get(),1)
                .requires(Items.RABBIT_FOOT,1)
                .requires(Items.GUNPOWDER,1)
                .unlockedBy(getHasName(ModItems.AMBER_CANDY.get()), has(ModItems.AMBER_CANDY.get()))
                .save(pWriter);

        // 染梦冰棒合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DYEDREAM_POPSICLE.get(), 1)
                .requires(Items.SNOWBALL, 1)
                .requires(ModItems.DYEDREAM_DUST_PIECE.get(),1)
                .requires(Items.STICK,1)
                //染梦铃兰暂未加入
                .unlockedBy(getHasName(ModItems.DYEDREAM_DUST_PIECE.get()), has(ModItems.DYEDREAM_DUST_PIECE.get()))
                .save(pWriter);

        // 煎蛋合成配方（熔炉，烟熏炉，篝火）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.create(
                                ResourceLocation.fromNamespaceAndPath("forge", "eggs"))),
                        RecipeCategory.MISC, ModItems.FRIED_EGG.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.PINK_EGG.get()), has(ModItems.PINK_EGG.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":fried_egg_from_pink_egg_smelting");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ItemTags.create(
                                ResourceLocation.fromNamespaceAndPath("forge", "eggs"))),
                        RecipeCategory.MISC, ModItems.FRIED_EGG.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.PINK_EGG.get()), has(ModItems.PINK_EGG.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":fried_egg_from_pink_egg_smoking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ItemTags.create(
                                ResourceLocation.fromNamespaceAndPath("forge", "eggs"))),
                        RecipeCategory.MISC, ModItems.FRIED_EGG.get(),0,600)
                .unlockedBy(getHasName(ModItems.PINK_EGG.get()), has(ModItems.PINK_EGG.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":fried_egg_from_pink_egg_campfire_cooking");

        // 面包片合成配方（熔炉，烟熏炉，篝火）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.FLOUR.get()),
                        RecipeCategory.MISC, ModItems.BREAD_SLICE.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.FLOUR.get()), has(ModItems.FLOUR.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":bread_slice_from_flour_smelting");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModItems.FLOUR.get()),
                        RecipeCategory.MISC, ModItems.BREAD_SLICE.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.FLOUR.get()), has(ModItems.FLOUR.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":bread_slice_from_flour_smoking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.FLOUR.get()),
                        RecipeCategory.MISC, ModItems.BREAD_SLICE.get(),0,600)
                .unlockedBy(getHasName(ModItems.FLOUR.get()), has(ModItems.FLOUR.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":bread_slice_from_flour_campfire_cooking");

        // 威化饼干合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WAFER_BISCUIT.get(), 2)
                .requires(ModItems.BREAD_SLICE.get(),2)
                .requires(ModItems.GLASS_JAR_OF_YEAST.get(),1)
                .unlockedBy(getHasName(ModItems.GLASS_JAR_OF_YEAST.get()), has(ModItems.GLASS_JAR_OF_YEAST.get()))
                .save(pWriter);

        // 瑞士卷合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SWISS_ROLL.get(), 1)
                .requires(ModItems.BREAD_SLICE.get(),2)
                .requires(ModItems.GLASS_JAR_OF_MILK.get(),1)
                .requires(ModItems.GLASS_JAR_OF_YEAST.get(),1)
                .unlockedBy(getHasName(ModItems.GLASS_JAR_OF_YEAST.get()), has(ModItems.GLASS_JAR_OF_YEAST.get()))
                .save(pWriter);

        // 威化夹心合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.STUFFED_WAFER_COOKIES.get(), 1)
                .requires(ModItems.WAFER_BISCUIT.get(),2)
                .requires(ModItems.GLASS_JAR_OF_MILK.get(),1)
                .requires(Items.SUGAR,1)
                .unlockedBy(getHasName(ModItems.GLASS_JAR_OF_MILK.get()), has(ModItems.GLASS_JAR_OF_MILK.get()))
                .save(pWriter);

        // 三明治合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SANDWICH.get(), 1)
                .requires(Items.BREAD,1)
                .requires(Items.CHICKEN,1)
                .requires(Ingredient.of(ItemTags.create(
                        ResourceLocation.fromNamespaceAndPath("forge", "cooked_eggs"))),1)
                .requires(Items.KELP,1)
                .unlockedBy(getHasName(Items.BREAD), has(Items.BREAD))
                .save(pWriter);

        // 培根煎蛋合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BACON_AND_EGG.get(), 1)
                .requires(Ingredient.of(ItemTags.create(
                        ResourceLocation.fromNamespaceAndPath("forge", "cooked_eggs"))),2)
                .requires(Items.COOKED_PORKCHOP,1)
                .requires(ModItems.SALT.get(),1)
                .unlockedBy(getHasName(ModItems.SALT.get()), has(ModItems.SALT.get()))
                .save(pWriter);

        // 水母果冻合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.JELLYFISH_JELLO.get(), 2)
                .requires(ModItems.JELLYFISH_MUD.get(), 2)
                .requires(Ingredient.of(ItemTags.create(
                        ResourceLocation.fromNamespaceAndPath("forge", "fruits/fig"))),1)
                .requires(Items.SUGAR,1)
                .requires(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get(),1)
                .unlockedBy(getHasName(ModItems.JELLYFISH_MUD.get()), has(ModItems.JELLYFISH_MUD.get()))
                .save(pWriter);

        // 饮料（玻璃杯类）合成配方
        RecipeHelpers.glass_cup_drink(pWriter,Items.APPLE, ModItems.GLASS_CUP_OF_APPLE_JUICE.get());
        RecipeHelpers.glass_cup_drink(pWriter,Items.HONEYCOMB, ModItems.GLASS_CUP_OF_HONEY_JUICE.get());
        RecipeHelpers.glass_cup_drink(pWriter,Items.MELON_SLICE, ModItems.GLASS_CUP_OF_WATERMELON_JUICE.get());

        // 染梦花茶（未烹调）合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get(), 1)
                .requires(ModItems.GLASS_CUP.get(),1)
                .requires(ModItems.AMBER_CANDY.get(),1)
                .requires(ModItems.DYEDREAM_LEAVES.get(),1)
                .requires(ModItems.DYEDREAM_COROLLA.get(),1)
                .unlockedBy(getHasName(ModItems.GLASS_CUP.get()), has(ModItems.GLASS_CUP.get()))
                .save(pWriter);

        // 染梦花茶合成配方
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get()),
                        RecipeCategory.MISC, ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()), has(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":glass_cup_of_cooked_dyedream_flower_tea_from_glass_cup_of_uncooked_dyedream_flower_tea_smelting");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get()),
                        RecipeCategory.MISC, ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()), has(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":glass_cup_of_cooked_dyedream_flower_tea_from_glass_cup_of_uncooked_dyedream_flower_tea_smoking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get()),
                        RecipeCategory.MISC, ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get(), 0, 600)
                .unlockedBy(getHasName(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()), has(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":glass_cup_of_cooked_dyedream_flower_tea_from_glass_cup_of_uncooked_dyedream_flower_tea_campfire_cooking");

        // 染梦果汁合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get(), 1)
                .requires(ModItems.GLASS_CUP.get(),1)
                .requires(ModItems.DYEDREAM_FRUIT.get(),1)
                .requires(ModItems.DYEDREAM_DUST_PIECE.get(),1)
                .requires(ModItems.AMBER_CANDY.get(),1)
                .unlockedBy(getHasName(ModItems.GLASS_CUP.get()), has(ModItems.GLASS_CUP.get()))
                .save(pWriter);

        // 融梦棉花糖合成配方
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MELT_DREAM_COTTON_CANDY.get(), 8)
                .pattern("bbb")
                .pattern("bab")
                .pattern("bbb")
                .define('a', ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get())
                .define('b', Items.SUGAR)
                .unlockedBy(getHasName(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()), has(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                .save(pWriter);

        // 融梦灵药瓶合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ELIXIR_BOTTLE_OF_MELT_DREAM.get(), 1)
                .requires(ModItems.ELIXIR_BOTTLE.get(),1)
                .requires(ModItems.MELT_DREAM_LIQUID_BUCKET.get(),1)
                .unlockedBy(getHasName(ModItems.MELT_DREAM_LIQUID_BUCKET.get()), has(ModItems.MELT_DREAM_LIQUID_BUCKET.get()))
                .save(pWriter);

        //秋麒麟茶合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_GOLDENROD_TEA.get(), 1)
                .requires(ModItems.GOLDENROD.get(),1)
                .requires(ModItems.GLASS_JAR_OF_WATER.get(),1)
                .requires(ModItems.GLASS_JAR.get(),1)
                .unlockedBy(getHasName(ModItems.GOLDENROD.get()), has(ModItems.GOLDENROD.get()))
                .save(pWriter);

        // 染梦香水合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME.get(), 1)
                .requires(ModItems.GLASS_JAR_OF_WATER.get(),1)
                .requires(ModItems.GLASS_JAR.get(),1)
                .requires(ModItems.DYEDREAM_DUST_PIECE.get(),1)
                .requires(Items.FERMENTED_SPIDER_EYE,1)
                .requires(ModItems.PINK_MUSHROOM.get(),1)
                .requires(ModItems.DYEDREAM_MOSS.get(),1)
                .unlockedBy(getHasName(ModItems.GLASS_JAR_OF_WATER.get()), has(ModItems.GLASS_JAR_OF_WATER.get()))
                .save(pWriter);
    }

    // ===== 其他杂项类合成配方 =====
    private void othersRecipes(Consumer<FinishedRecipe> pWriter) {

        //三种染料合成配方
        RecipeHelpers.dye(pWriter,ModItems.FERRARIA_CRISPA.get(), Items.BLACK_DYE);
        RecipeHelpers.dye(pWriter,ModItems.EUSTOMA.get(), Items.GREEN_DYE);
        RecipeHelpers.dye(pWriter,ModItems.MALVA_SINENSIS_CAVAN.get(), Items.PINK_DYE);

    }
    // ===== 配方工具方法 =====

    private void dyeConversion(Consumer<FinishedRecipe> writer, ItemLike material, ItemLike result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result, 8)
                .pattern("aaa")
                .pattern("ada")
                .pattern("aaa")
                .define('a', material)
                .define('d', ModItems.DYEDREAM_DYE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DYE.get()), has(ModItems.DYEDREAM_DYE.get()))
                .save(writer, PasterDreamMod.MOD_ID + ":" + getItemName(result) + "_from_dye");
    }

    private void dyeConversionTag(Consumer<FinishedRecipe> writer, TagKey<Item> tag, ItemLike result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result, 8)
                .pattern("aaa")
                .pattern("ada")
                .pattern("aaa")
                .define('a', Ingredient.of(tag))
                .define('d', ModItems.DYEDREAM_DYE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DYE.get()), has(ModItems.DYEDREAM_DYE.get()))
                .save(writer, PasterDreamMod.MOD_ID + ":" + getItemName(result) + "_from_dye");
    }

}
