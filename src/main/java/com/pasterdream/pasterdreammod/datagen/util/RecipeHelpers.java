package com.pasterdream.pasterdreammod.datagen.util;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

/**
 * 配方生成工具方法，供 data generator 调用。
 */
public final class RecipeHelpers {

    private RecipeHelpers() {}

    /**
     * 为指定木板注册全套建材配方：工作台合成木板(4)、楼梯(4)、台阶(6)、栅栏(3)、栅栏门(1)、门(3)、活板门(2)、压力板(1)、按钮(1)。
     * 不包含窗格配方（窗格通常由玻璃板+木板合成，非原版木板派生配方）。
     *
     * @param writer        配方输出
     * @param log           原木物品
     * @param planks        木板物品
     * @param stairs        楼梯物品
     * @param slab          台阶物品
     * @param fence         栅栏物品
     * @param fenceGate     栅栏门物品
     * @param door          门物品
     * @param trapdoor      活板门物品
     * @param pressurePlate 压力板物品
     * @param button        按钮物品
     * @param modId         模组 ID，用于配方文件名前缀
     */
    public static void plankFamilyRecipes(Consumer<FinishedRecipe> writer,
                                           ItemLike log, ItemLike planks,
                                           ItemLike stairs, ItemLike slab,
                                           ItemLike fence, ItemLike fenceGate,
                                           ItemLike door, ItemLike trapdoor,
                                           ItemLike pressurePlate, ItemLike button,
                                           String modId) {
        // 原木 → 木板
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, 4)
                .requires(log)
                .unlockedBy(getHasName(log), has(log))
                .save(writer, modId + ":" + getItemName(planks) + "_from_log");

        // 楼梯
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                .pattern("a  ")
                .pattern("aa ")
                .pattern("aaa")
                .define('a', planks)
                .unlockedBy(getHasName(planks), has(planks))
                .save(writer);

        // 台阶
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .pattern("aaa")
                .define('a', planks)
                .unlockedBy(getHasName(planks), has(planks))
                .save(writer);

        // 栅栏
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, fence, 3)
                .pattern("aba")
                .pattern("aba")
                .define('a', planks)
                .define('b', Items.STICK)
                .unlockedBy(getHasName(planks), has(planks))
                .save(writer);

        // 栅栏门
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, fenceGate, 1)
                .pattern("aba")
                .pattern("aba")
                .define('a', Items.STICK)
                .define('b', planks)
                .unlockedBy(getHasName(planks), has(planks))
                .save(writer);

        // 门
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, door, 3)
                .pattern("aa")
                .pattern("aa")
                .pattern("aa")
                .define('a', planks)
                .unlockedBy(getHasName(planks), has(planks))
                .save(writer);

        // 活板门
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, trapdoor, 2)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', planks)
                .unlockedBy(getHasName(planks), has(planks))
                .save(writer);

        // 压力板
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pressurePlate, 1)
                .pattern("aa")
                .define('a', planks)
                .unlockedBy(getHasName(planks), has(planks))
                .save(writer);

        // 按钮
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, button, 1)
                .requires(planks)
                .unlockedBy(getHasName(planks), has(planks))
                .save(writer);
    }

    /**
     * 9→1 压缩配方：9×材料合成 1×压缩块（3×3 有序合成）。
     */
    public static void storageCompress(Consumer<FinishedRecipe> writer,
                                        ItemLike material, ItemLike block) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block, 1)
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a', material)
                .unlockedBy(getHasName(material), has(material))
                .save(writer);
    }

    /**
     * 1→9 分解配方：1×压缩块分解为 9×材料（无序合成）。
     */
    public static void storageDecompress(Consumer<FinishedRecipe> writer,
                                          ItemLike block, ItemLike material) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, material, 9)
                .requires(block)
                .unlockedBy(getHasName(block), has(block))
                .save(writer);
    }

    /**
     * 蛋糕类合成配方
     * @param material 合成材料，决定合成出来的蛋糕的效果
     */
    public static void cake(Consumer<FinishedRecipe> writer,
                                         ItemLike material) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, material, 1)
                .requires(ModItems.CREAM_BUN_CAKE.get())
                .requires(material)
                .unlockedBy(getHasName(ModItems.CREAM_BUN_CAKE.get()), has(ModItems.CREAM_BUN_CAKE.get()))
                .save(writer);
    }
    private static String getHasName(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }

    private static String getItemName(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }

    private static InventoryChangeTrigger.TriggerInstance has(ItemLike item) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(item);
    }
}
