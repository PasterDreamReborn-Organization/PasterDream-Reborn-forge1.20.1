package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.datagen.util.RecipeHelpers;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipesProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        // 染梦原木 → 染梦木头
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_WOOD.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.DYEDREAM_LOG.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_LOG.get()), has(ModItems.DYEDREAM_LOG.get()))
                .save(pWriter);

        // 染梦原木 → 染梦木板 + 全套建材配方
        RecipeHelpers.plankFamilyRecipes(pWriter,
                ModItems.DYEDREAM_LOG.get(),
                ModItems.DYEDREAM_PLANKS.get(),
                ModItems.DYEDREAM_PLANKS_STAIRS.get(),
                ModItems.DYEDREAM_PLANKS_SLAB.get(),
                ModItems.DYEDREAM_PLANKS_FENCE.get(),
                ModItems.DYEDREAM_PLANKS_FENCEGATE.get(),
                ModItems.DYEDREAM_PLANKS_DOOR.get(),
                ModItems.DYEDREAM_PLANKS_TRAPDOOR.get(),
                ModItems.DYEDREAM_PLANKS_PRESSURE_PLATE.get(),
                ModItems.DYEDREAM_PLANKS_BUTTON.get(),
                PasterDreamMod.MOD_ID);

        // 染梦木头 → 染梦木板
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_PLANKS.get(), 4)
                .requires(ModItems.DYEDREAM_WOOD.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_WOOD.get()), has(ModItems.DYEDREAM_WOOD.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_planks_from_wood");

        // 染梦木窗格 - 玻璃板 + 染梦木板
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_PLANKS_PANE.get(), 1)
                .requires(Items.GLASS_PANE)
                .requires(ModItems.DYEDREAM_PLANKS.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_PLANKS.get()), has(ModItems.DYEDREAM_PLANKS.get()))
                .save(pWriter);

        // ===== 染梦染料转化配方 =====

        dyeConversion(pWriter, Items.DIRT, ModBlocks.DYEDREAM_DIRT.get().asItem());
        dyeConversion(pWriter, Items.GRASS_BLOCK, ModBlocks.DYEDREAM_GRASS_BLOCK.get().asItem());
        dyeConversionTag(pWriter, ItemTags.LEAVES, ModBlocks.DYEDREAM_LEAVES.get().asItem());
        dyeConversionTag(pWriter, ItemTags.LOGS, ModBlocks.DYEDREAM_LOG.get().asItem());
        dyeConversionTag(pWriter, ItemTags.PLANKS, ModBlocks.DYEDREAM_PLANKS.get().asItem());
        dyeConversionTag(pWriter, ItemTags.SAPLINGS, ModBlocks.DYEDREAM_SAPLING.get().asItem());
        dyeConversion(pWriter, Items.QUARTZ, ModItems.DYEDREAM_QUARTZ.get());
        dyeConversion(pWriter, Items.QUARTZ_BLOCK, ModItems.DYEDREAM_QUARTZ_BLOCK.get());
        dyeConversion(pWriter, Items.SHROOMLIGHT, ModBlocks.PINK_SHROOMLIGHT.get().asItem());

        // ===== 工具与材料配方 =====

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

        // 染梦染料：粉尘碎片 + 骨粉 + 研钵
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DYEDREAM_DYE.get(), 7)
                .requires(ModItems.DYEDREAM_DUST_PIECE.get())
                .requires(Items.BONE_MEAL)
                .requires(ModItems.MORTAR.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DUST_PIECE.get()), has(ModItems.DYEDREAM_DUST_PIECE.get()))
                .save(pWriter);

        // 染梦染料：粉尘 + 骨块 + 研钵
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DYEDREAM_DYE.get(), 63)
                .requires(ModItems.DYEDREAM_DUST.get())
                .requires(Items.BONE_BLOCK)
                .requires(ModItems.MORTAR.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DUST.get()), has(ModItems.DYEDREAM_DUST.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_dye_from_dust");

        // ===== 染梦玻璃配方 =====

        // 8× 原版玻璃 + 染梦染料 → 8× 染梦玻璃
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_GLASS.get(), 8)
                .requires(Items.GLASS, 8)
                .requires(ModItems.DYEDREAM_DYE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DYE.get()), has(ModItems.DYEDREAM_DYE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_glass_from_dye");

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

        // ===== 染梦冰与水晶灯配方 =====

        // 8× 冰 + 染梦染料 → 8× 染梦冰
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_ICE.get(), 8)
                .pattern("aaa")
                .pattern("aba")
                .pattern("aaa")
                .define('a', Items.ICE)
                .define('b', ModItems.DYEDREAM_DYE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DYE.get()), has(ModItems.DYEDREAM_DYE.get()))
                .save(pWriter);

        // 8× 浮冰 + 染梦染料 → 8× 染梦浮冰
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_PACKED_ICE.get(), 8)
                .pattern("aaa")
                .pattern("aba")
                .pattern("aaa")
                .define('a', Items.PACKED_ICE)
                .define('b', ModItems.DYEDREAM_DYE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DYE.get()), has(ModItems.DYEDREAM_DYE.get()))
                .save(pWriter);

        // 染梦水晶灯
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_LARTERN.get(), 2)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', ModItems.DYEDREAM_QUARTZ.get())
                .define('b', ModBlocks.DYEDREAM_GLASS.get())
                .define('c', ModItems.DYEDREAM_DYE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DYE.get()), has(ModItems.DYEDREAM_DYE.get()))
                .save(pWriter);

        // ===== 染梦水晶建材配方 =====

        // 染梦玻璃 + 晶芽粒 → 4× 染梦水晶砖
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_BUD_BLOCK.get(), 4)
                .pattern("ab")
                .pattern("ba")
                .define('a', ModBlocks.DYEDREAM_GLASS.get())
                .define('b', ModItems.DYEDREAM_BUD_NUGGET.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_BUD_NUGGET.get()), has(ModItems.DYEDREAM_BUD_NUGGET.get()))
                .save(pWriter);

        // 染梦水晶砖 → 4× 楼梯
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_BUD_STAIRS.get(), 4)
                .pattern("a  ")
                .pattern("aa ")
                .pattern("aaa")
                .define('a', ModBlocks.DYEDREAM_BUD_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.DYEDREAM_BUD_BLOCK.get()), has(ModBlocks.DYEDREAM_BUD_BLOCK.get()))
                .save(pWriter);

        // 染梦水晶砖 → 6× 台阶
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_BUD_SLAB.get(), 6)
                .pattern("aaa")
                .define('a', ModBlocks.DYEDREAM_BUD_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.DYEDREAM_BUD_BLOCK.get()), has(ModBlocks.DYEDREAM_BUD_BLOCK.get()))
                .save(pWriter);

        // 染梦水晶砖 → 6× 墙
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_BUD_WALL.get(), 6)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModBlocks.DYEDREAM_BUD_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.DYEDREAM_BUD_BLOCK.get()), has(ModBlocks.DYEDREAM_BUD_BLOCK.get()))
                .save(pWriter);

        // 切石机配方
        var budBlockIngredient = Ingredient.of(ModBlocks.DYEDREAM_BUD_BLOCK.get());
        SingleItemRecipeBuilder.stonecutting(budBlockIngredient, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_BUD_STAIRS.get())
                .unlockedBy(getHasName(ModBlocks.DYEDREAM_BUD_BLOCK.get()), has(ModBlocks.DYEDREAM_BUD_BLOCK.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_bud_stairs_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(budBlockIngredient, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_BUD_SLAB.get(), 2)
                .unlockedBy(getHasName(ModBlocks.DYEDREAM_BUD_BLOCK.get()), has(ModBlocks.DYEDREAM_BUD_BLOCK.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_bud_slab_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(budBlockIngredient, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_BUD_WALL.get())
                .unlockedBy(getHasName(ModBlocks.DYEDREAM_BUD_BLOCK.get()), has(ModBlocks.DYEDREAM_BUD_BLOCK.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_bud_wall_from_stonecutting");

        // ===== 粉顶菇配方 =====

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

        // ===== 粉色粘液配方 =====

        RecipeHelpers.storageCompress(pWriter, ModItems.PINK_SLIMEBALL.get(), ModItems.PINK_SLIME_BLOCK.get());
        RecipeHelpers.storageDecompress(pWriter, ModItems.PINK_SLIME_BLOCK.get(), ModItems.PINK_SLIMEBALL.get());

        //陶盆配方
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CLAYPAN.get(), 2)
                .pattern("aba")
                .pattern("aaa")
                .define('a', ItemTags.TERRACOTTA)
                .define('b', Items.WHITE_DYE)
                .unlockedBy(getHasName(Items.WHITE_DYE), has(Items.WHITE_DYE))
                .save(pWriter);



        // ===== 染梦粉尘配方 =====

        dustRecipes(pWriter);

        // ===== 染梦石英配方 =====

        quartzRecipes(pWriter);

        // ===== 锭相关合成配方 =====

        alloySmeltingRecipes(pWriter);

        // ===== 食物相关合成配方 =====
        foodRecipes(pWriter);
    }

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

    private void foodRecipes(Consumer<FinishedRecipe> pWriter) {

        // 玻璃罐合成（支持所有木台阶tag和玻璃板tag）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GLASS_JAR.get(), 8)
                .pattern("aba")
                .pattern("a a")
                .pattern("aaa")
                .define('a',  Ingredient.of(ItemTags.create(
                        ResourceLocation.fromNamespaceAndPath("forge","glass_panes"))))
                .define('b',  ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(Items.GLASS_PANE), has(Items.GLASS_PANE))
                .save(pWriter);

        // 重做酵母合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_YEAST.get(), 4)
                .requires(ModItems.GLASS_JAR.get(),4)
                .requires(ModItems.FLOUR.get(), 1)
                .requires(ModItems.GLASS_JAR_OF_WATER.get(), 1)
                .requires(ModItems.GLASS_JAR_OF_YEAST.get(), 1)
                .requires(Items.SUGAR, 1)
                .unlockedBy(getHasName(ModItems.GLASS_JAR.get()), has(ModItems.GLASS_JAR.get()))
                .save(pWriter);

        // 水罐合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_WATER.get(), 8)
                .requires(Items.WATER_BUCKET,1)
                .requires(ModItems.GLASS_JAR.get(), 8)
                .unlockedBy(getHasName(Items.WATER_BUCKET), has(Items.WATER_BUCKET))
                .save(pWriter);

        // 牛奶罐合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_MILK.get(), 8)
                .requires(Items.MILK_BUCKET,1)
                .requires(ModItems.GLASS_JAR.get(), 8)
                .unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET))
                .save(pWriter);

        // 巧克力合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CHOCOLATE.get(), 1)
                .requires(Items.COCOA_BEANS,2)
                .requires(Items.SUGAR,1)
                .unlockedBy(getHasName(Items.SUGAR), has(Items.SUGAR))
                .save(pWriter);

        // 面团合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DOUGH.get(), 1)
                .requires(ModItems.FLOUR.get(),1)
                .requires(ModItems.GLASS_JAR_OF_WATER.get(), 2)
                .unlockedBy(getHasName(ModItems.FLOUR.get()), has(ModItems.FLOUR.get()))
                .save(pWriter);

        // 蛋液面团合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DOUGH_WITH_EGG.get(), 1)
                .requires(Ingredient.of(ItemTags.create(
                        ResourceLocation.fromNamespaceAndPath("forge","eggs"))),1)
                .requires(ModItems.DOUGH.get(), 2)
                .unlockedBy(getHasName(ModItems.DOUGH.get()), has(ModItems.DOUGH.get()))
                .save(pWriter);

        // 蛋糕胚合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CAKE_BASE.get(), 2)
                .requires(ModItems.DOUGH_WITH_EGG.get(),1)
                .requires(Items.SUGAR,1)
                .requires(ModItems.GLASS_JAR_OF_YEAST.get(), 1)
                .unlockedBy(getHasName(ModItems.DOUGH_WITH_EGG.get()), has(ModItems.DOUGH_WITH_EGG.get()))
                .save(pWriter);

        // 奶油小蛋糕合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CREAM_BUN_CAKE.get(), 1)
                .requires(ModItems.CAKE_BASE.get(),1)
                .requires(ModItems.GLASS_JAR_OF_MILK.get(),1)
                .unlockedBy(getHasName(ModItems.CAKE_BASE.get()), has(ModItems.CAKE_BASE.get()))
                .save(pWriter);

        // 六种蛋糕合成
        RecipeHelpers.cake(pWriter,Items.SWEET_BERRIES,ModItems.BERRY_BUN_CAKE.get());
        RecipeHelpers.cake(pWriter,Items.POTATO,ModItems.TUBER_BUN_CAKE.get());
        RecipeHelpers.cake(pWriter,Items.MELON_SLICE,ModItems.WATERMELON_BUN_CAKE.get());
        RecipeHelpers.cake(pWriter,Items.PUMPKIN,ModItems.PUMPKIN_BUN_CAKE.get());
        RecipeHelpers.cake(pWriter,Items.GLOW_BERRIES,ModItems.GLOW_BERRY_BUN_CAKE.get());
        RecipeHelpers.cake(pWriter,ModItems.DYEDREAM_FRUIT.get(),ModItems.DYEDREAM_FRUIT_BUN_CAKE.get());

        //巧克力抹茶蛋糕合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CHOCOLATE_MATCHA_CAKE.get(), 1)
                .requires(ModItems.CAKE_BASE.get(),1)
                .requires(Ingredient.of(ItemTags.LEAVES),1)
                .requires(ModItems.CHOCOLATE.get(),1)
                .unlockedBy(getHasName(ModItems.CAKE_BASE.get()), has(ModItems.CAKE_BASE.get()))
                .save(pWriter);

    }
    private void alloySmeltingRecipes(Consumer<FinishedRecipe> pWriter) {
        // 染梦合金锭粗胚 → 染梦合金锭（熔炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()),
                        RecipeCategory.MISC, ModItems.DYEDREAM_ALLOY_INGOT.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()), has(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_alloy_ingot_from_smelting");

        // 染梦合金锭粗胚 → 染梦合金锭（高炉）
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()),
                        RecipeCategory.MISC, ModItems.DYEDREAM_ALLOY_INGOT.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()), has(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_alloy_ingot_from_blasting");

        // 粗钛 → 钛金锭（熔炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_TITANIUM.get()),
                        RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.RAW_TITANIUM.get()), has(ModItems.RAW_TITANIUM.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_ingot_from_smelting");

        // 粗钛 → 钛金锭（高炉）
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_TITANIUM.get()),
                        RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.RAW_TITANIUM.get()), has(ModItems.RAW_TITANIUM.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_ingot_from_blasting");

        // 粗炙焰金 → 炙焰金锭（熔炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_MOLTEN_GOLD.get()),
                        RecipeCategory.MISC, ModItems.MOLTEN_GOLD_INGOT.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.RAW_MOLTEN_GOLD.get()), has(ModItems.RAW_MOLTEN_GOLD.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":moltengold_ingot_from_smelting");

        // 粗炙焰金 → 炙焰金锭（高炉）
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_MOLTEN_GOLD.get()),
                        RecipeCategory.MISC, ModItems.MOLTEN_GOLD_INGOT.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.RAW_MOLTEN_GOLD.get()), has(ModItems.RAW_MOLTEN_GOLD.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":moltengold_ingot_from_blasting");

        // 钛金锭 → 9× 钛金粒
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TITANIUM_NUGGET.get(), 9)
                .requires(ModItems.TITANIUM_INGOT.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_nugget_from_ingot");

        // 9× 钛金粒 → 钛金锭
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 1)
                .requires(ModItems.TITANIUM_NUGGET.get(), 9)
                .unlockedBy(getHasName(ModItems.TITANIUM_NUGGET.get()), has(ModItems.TITANIUM_NUGGET.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":titanium_ingot_from_nuggets");

        // 染梦合金锭 → 9× 染梦合金粒
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DYEDREAM_ALLOY_NUGGET.get(), 9)
                .requires(ModItems.DYEDREAM_ALLOY_INGOT.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_ALLOY_INGOT.get()), has(ModItems.DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_alloy_nugget_from_alloy_ingot");

        // 9× 染梦合金粒 → 染梦合金锭
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DYEDREAM_ALLOY_INGOT.get(), 1)
                .requires(ModItems.DYEDREAM_ALLOY_NUGGET.get(), 9)
                .unlockedBy(getHasName(ModItems.DYEDREAM_ALLOY_NUGGET.get()), has(ModItems.DYEDREAM_ALLOY_NUGGET.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_alloy_ingot_from_nuggets");

        // 炙焰金锭 → 9× 炙焰金粒
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MOLTEN_GOLD_NUGGET.get(), 9)
                .requires(ModItems.MOLTEN_GOLD_INGOT.get())
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_INGOT.get()), has(ModItems.MOLTEN_GOLD_INGOT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":moltengold_nugget_from_ingot");

        // 9× 炙焰金粒 → 炙焰金锭
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MOLTEN_GOLD_INGOT.get(), 1)
                .requires(ModItems.MOLTEN_GOLD_NUGGET.get(), 9)
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_NUGGET.get()), has(ModItems.MOLTEN_GOLD_NUGGET.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":moltengold_ingot_from_nuggets");
    }

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

        // ===== 切石机配方 =====

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

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    private void dyeConversion(Consumer<FinishedRecipe> writer, ItemLike material, ItemLike result) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 8)
                .requires(material, 8)
                .requires(ModItems.DYEDREAM_DYE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DYE.get()), has(ModItems.DYEDREAM_DYE.get()))
                .save(writer, PasterDreamMod.MOD_ID + ":" + getItemName(result) + "_from_dye");
    }

    private void dyeConversionTag(Consumer<FinishedRecipe> writer, TagKey<Item> tag, ItemLike result) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 8)
                .requires(Ingredient.of(tag), 8)
                .requires(ModItems.DYEDREAM_DYE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DYE.get()), has(ModItems.DYEDREAM_DYE.get()))
                .save(writer, PasterDreamMod.MOD_ID + ":" + getItemName(result) + "_from_dye");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime,
                            pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, PasterDreamMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
