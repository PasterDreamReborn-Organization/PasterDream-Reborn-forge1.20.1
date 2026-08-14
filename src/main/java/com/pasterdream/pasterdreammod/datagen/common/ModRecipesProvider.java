package com.pasterdream.pasterdreammod.datagen.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.datagen.util.RecipeHelpers;
import com.pasterdream.pasterdreammod.helper.ContainerBalanceHelper;
import com.pasterdream.pasterdreammod.world.item.DeepTreasureItem;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider implements IConditionBuilder {

    /** 自动收集的容器配方校验列表，在 buildRecipes 末尾统一执行。 */
    private final List<Runnable> containerValidations = new ArrayList<>();

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

    /**
     * 保留 NBT 的无序配方包装器：升级配方保留附魔等数据。
     */
    private void saveNbtPreserving(ShapelessRecipeBuilder builder, Consumer<FinishedRecipe> writer, String name) {
        builder.save(wrapped -> writer.accept(new FinishedRecipe() {
            @Override public void serializeRecipeData(JsonObject json) { wrapped.serializeRecipeData(json); }
            @Override public ResourceLocation getId() { return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name); }
            @Override public RecipeSerializer<?> getType() { return ModRecipes.NBT_PRESERVING_SHAPELESS_SERIALIZER.get(); }
            @Override public JsonObject serializeAdvancement() { return wrapped.serializeAdvancement(); }
            @Override public ResourceLocation getAdvancementId() { return wrapped.getAdvancementId(); }
        }), ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name));
    }

    /** 有序配方版本 */
    private void saveNbtPreservingShaped(ShapedRecipeBuilder builder, Consumer<FinishedRecipe> writer, String name) {
        builder.save(wrapped -> writer.accept(new FinishedRecipe() {
            @Override public void serializeRecipeData(JsonObject json) { wrapped.serializeRecipeData(json); }
            @Override public ResourceLocation getId() { return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name); }
            @Override public RecipeSerializer<?> getType() { return ModRecipes.NBT_PRESERVING_SHAPED_SERIALIZER.get(); }
            @Override public JsonObject serializeAdvancement() { return wrapped.serializeAdvancement(); }
            @Override public ResourceLocation getAdvancementId() { return wrapped.getAdvancementId(); }
        }), ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name));
    }

    /**
     * 保存无序配方并自动注册容器配平校验。
     * 输入参数为 (产物, 产物数量, 原料1, 数量1, 原料2, 数量2, ...)
     */
    private void saveContainerBalancedShapeless(ShapelessRecipeBuilder builder, Consumer<FinishedRecipe> writer,
                                                 String name, ItemLike result, int resultCount, Object... inputs) {
        containerValidations.add(() -> {
            ItemStack[] stacks = new ItemStack[inputs.length / 2];
            for (int i = 0; i < inputs.length; i += 2) {
                stacks[i / 2] = new ItemStack((ItemLike) inputs[i], (int) inputs[i + 1]);
            }
            ContainerBalanceHelper.validateOrThrow(name, new ItemStack(result, resultCount), stacks);
        });
        saveNbtPreserving(builder, writer, name);
    }

    /**
     * 保存有序配方并自动注册容器配平校验。
     */
    private void saveContainerBalancedShaped(ShapedRecipeBuilder builder, Consumer<FinishedRecipe> writer,
                                              String name, ItemLike result, int resultCount, Object... inputs) {
        containerValidations.add(() -> {
            ItemStack[] stacks = new ItemStack[inputs.length / 2];
            for (int i = 0; i < inputs.length; i += 2) {
                stacks[i / 2] = new ItemStack((ItemLike) inputs[i], (int) inputs[i + 1]);
            }
            ContainerBalanceHelper.validateOrThrow(name, new ItemStack(result, resultCount), stacks);
        });
        saveNbtPreservingShaped(builder, writer, name);
    }

    /**
     * 有序配方——产物带NBT+可选原料NBT匹配。
     * resultNbt为null时产物不带NBT；
     * nbtKeys传或空Map表示原料不做NBT限制。
     */
    private void saveShapedWithNbt(ShapedRecipeBuilder builder, Consumer<FinishedRecipe> writer,
                                   String name, CompoundTag resultNbt,
                                   Map<Character, CompoundTag> nbtKeys) {
        builder.save(wrapped -> writer.accept(new FinishedRecipe() {
            @Override
            public void serializeRecipeData(JsonObject json) {
                wrapped.serializeRecipeData(json);
                // 产物加NBT（兼容result为字符串或对象两种格式）
                injectResultNbt(json, resultNbt);
                // 指定key的原料改写为forge:partial_nbt格式
                injectKeyNbt(json, nbtKeys);
            }
            @Override public ResourceLocation getId() {
                return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name);
            }
            @Override public RecipeSerializer<?> getType() { return RecipeSerializer.SHAPED_RECIPE; }
            @Override public JsonObject serializeAdvancement() { return wrapped.serializeAdvancement(); }
            @Override public ResourceLocation getAdvancementId() { return wrapped.getAdvancementId(); }
        }), ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name));
    }

    /**
     * 无序配方——产物带 NBT + 可选原料 NBT 匹配。
     * resultNbt为null时产物不带NBT；
     * nbtIndices传null或空Map表示原料不做NBT限制。
     */
    private void saveShapelessWithNbt(ShapelessRecipeBuilder builder, Consumer<FinishedRecipe> writer,
                                      String name, CompoundTag resultNbt,
                                      Map<Integer, CompoundTag> nbtIndices) {
        builder.save(wrapped -> writer.accept(new FinishedRecipe() {
            @Override
            public void serializeRecipeData(JsonObject json) {
                wrapped.serializeRecipeData(json);
                // 产物加 NBT（兼容 result 为字符串或对象两种格式）
                injectResultNbt(json, resultNbt);
                // 指定位置的原料改写为 forge:partial_nbt 格式
                injectIngredientsNbt(json, nbtIndices);
            }
            @Override public ResourceLocation getId() {
                return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name);
            }
            @Override public RecipeSerializer<?> getType() { return RecipeSerializer.SHAPELESS_RECIPE; }
            @Override public JsonObject serializeAdvancement() { return wrapped.serializeAdvancement(); }
            @Override public ResourceLocation getAdvancementId() { return wrapped.getAdvancementId(); }
        }), ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name));
    }
    /** 产物加 NBT：兼容 "result": "modid:item"（字符串）和 "result": {...} */
    private static void injectResultNbt(JsonObject json, CompoundTag nbt) {
        if (nbt == null || !json.has("result")) return;
        var resultEl = json.get("result");
        if (resultEl.isJsonObject()) {
            resultEl.getAsJsonObject().addProperty("nbt", nbt.toString());
        } else if (resultEl.isJsonPrimitive()) {
            JsonObject obj = new JsonObject();
            obj.addProperty("item", resultEl.getAsString());
            obj.addProperty("nbt", nbt.toString());
            json.add("result", obj);
        }
    }

    /** 有序配方 key 原料加 NBT 匹配 */
    private static void injectKeyNbt(JsonObject json, Map<Character, CompoundTag> nbtKeys) {
        if (nbtKeys == null || nbtKeys.isEmpty()) return;
        if (!json.has("key")) return;
        JsonObject keyObj = json.getAsJsonObject("key");
        for (var entry : nbtKeys.entrySet()) {
            String key = String.valueOf(entry.getKey());
            if (keyObj.has(key)) {
                var el = keyObj.get(key);
                if (el.isJsonObject()) {
                    JsonObject ingredient = el.getAsJsonObject();
                    ingredient.addProperty("type", "forge:partial_nbt");
                    ingredient.addProperty("nbt", entry.getValue().toString());
                }
            }
        }
    }

    /** 无序配方原料按索引加 NBT 匹配 */
    private static void injectIngredientsNbt(JsonObject json, Map<Integer, CompoundTag> nbtIndices) {
        if (nbtIndices == null || nbtIndices.isEmpty()) return;
        if (!json.has("ingredients")) return;
        var ingredients = json.getAsJsonArray("ingredients");
        for (var entry : nbtIndices.entrySet()) {
            int idx = entry.getKey();
            if (idx >= 0 && idx < ingredients.size()) {
                var el = ingredients.get(idx);
                if (el.isJsonObject()) {
                    JsonObject ingredient = el.getAsJsonObject();
                    ingredient.addProperty("type", "forge:partial_nbt");
                    ingredient.addProperty("nbt", entry.getValue().toString());
                }
            }
        }
    }

    public ModRecipesProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        woodRecipes(pWriter);
        shadowBookshelfRecipes(pWriter);
        dyeConversionRecipes(pWriter);
        blackStickRecipes(pWriter);
        materialRecipes(pWriter);
        upgradeKitRecipes(pWriter);
        moltenGoldToolRecipes(pWriter);
        hellfireToolRecipes(pWriter);
        meltDreamToolRecipes(pWriter);
        tideSwordRecipes(pWriter);
        grassSwordRecipe(pWriter);
        terraBladeRecipe(pWriter);
        starWishRodRecipe(pWriter);
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
        shadowStoneRecipes(pWriter);
        cyanStoneRecipes(pWriter);
        mossyCyanStoneRecipes(pWriter);
        foodRecipes(pWriter);
        othersRecipes(pWriter);
        thickCloudRecipes(pWriter);
        curioRecipes(pWriter);


        // 校验所有涉及容器的配方是否能配平
        validateContainerBalance();
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

        // 风泊原木 → 风泊木
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.WIND_MOOR_WOOD.get(), 3)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.WIND_MOOR_LOG.get())
                .unlockedBy(getHasName(ModItems.WIND_MOOR_LOG.get()), has(ModItems.WIND_MOOR_LOG.get()))
                .save(pWriter);

        // 去皮风泊原木 → 去皮风泊木
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.STRIPPED_WIND_MOOR_WOOD.get(), 3)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.STRIPPED_WIND_MOOR_LOG.get())
                .unlockedBy(getHasName(ModItems.STRIPPED_WIND_MOOR_LOG.get()), has(ModItems.STRIPPED_WIND_MOOR_LOG.get()))
                .save(pWriter);

        // 风泊原木 → 风泊木板 + 全套建材配方
        RecipeHelpers.plankFamilyRecipes(pWriter,
                ModItems.WIND_MOOR_LOG.get(),
                ModItems.WIND_MOOR_PLANKS.get(),
                ModItems.WIND_MOOR_STAIRS.get(),
                ModItems.WIND_MOOR_SLAB.get(),
                ModItems.WIND_MOOR_FENCE.get(),
                ModItems.WIND_MOOR_FENCE_GATE.get(),
                ModItems.WIND_MOOR_DOOR.get(),
                ModItems.WIND_MOOR_TRAPDOOR.get(),
                ModItems.WIND_MOOR_PRESSURE_PLATE.get(),
                ModItems.WIND_MOOR_BUTTON.get(),
                PasterDreamMod.MOD_ID);

        // 风泊木窗格 - 玻璃板 + 风泊木板
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.WIND_MOOR_PANE.get(), 1)
                .requires(Items.GLASS_PANE)
                .requires(ModItems.WIND_MOOR_PLANKS.get())
                .unlockedBy(getHasName(ModItems.WIND_MOOR_PLANKS.get()), has(ModItems.WIND_MOOR_PLANKS.get()))
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

        // 阴影菌柄 → 阴影木板 + 全套建材配方
        RecipeHelpers.plankFamilyRecipes(pWriter,
                ModItems.SHADOW_STEM.get(),
                ModItems.SHADOW_PLANKS.get(),
                ModItems.SHADOW_STAIRS.get(),
                ModItems.SHADOW_SLAB.get(),
                ModItems.SHADOW_FENCE.get(),
                ModItems.SHADOW_FENCE_GATE.get(),
                ModItems.SHADOW_DOOR.get(),
                ModItems.SHADOW_TRAPDOOR.get(),
                ModItems.SHADOW_PRESSURE_PLATE.get(),
                ModItems.SHADOW_BUTTON.get(),
                PasterDreamMod.MOD_ID);

        // 阴影菌核 → 阴影木板
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_PLANKS.get(), 4)
                .requires(ModItems.SHADOW_HYPHAE.get())
                .unlockedBy(getHasName(ModItems.SHADOW_HYPHAE.get()), has(ModItems.SHADOW_HYPHAE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":shadow_planks_from_hyphae");

        // 去皮阴影菌柄 → 阴影木板
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_PLANKS.get(), 4)
                .requires(ModItems.STRIPPED_SHADOW_STEM.get())
                .unlockedBy(getHasName(ModItems.STRIPPED_SHADOW_STEM.get()), has(ModItems.STRIPPED_SHADOW_STEM.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":shadow_planks_from_stripped_stem");

        // 去皮阴影菌核 → 阴影木板
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_PLANKS.get(), 4)
                .requires(ModItems.STRIPPED_SHADOW_HYPHAE.get())
                .unlockedBy(getHasName(ModItems.STRIPPED_SHADOW_HYPHAE.get()), has(ModItems.STRIPPED_SHADOW_HYPHAE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":shadow_planks_from_stripped_hyphae");

        // 阴影木窗格 - 玻璃板 + 阴影木板
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_PANE.get(), 1)
                .requires(Items.GLASS_PANE)
                .requires(ModItems.SHADOW_PLANKS.get())
                .unlockedBy(getHasName(ModItems.SHADOW_PLANKS.get()), has(ModItems.SHADOW_PLANKS.get()))
                .save(pWriter);
    }

    // ===== 阴影书架配方 =====

    private void shadowBookshelfRecipes(Consumer<FinishedRecipe> pWriter) {
        // 阴影书架：上下各 3 个阴影木板，中间 3 本书
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_BOOKSHELF.get())
                .pattern("aaa")
                .pattern("bbb")
                .pattern("aaa")
                .define('a', ModItems.SHADOW_PLANKS.get())
                .define('b', Items.BOOK)
                .unlockedBy(getHasName(ModItems.SHADOW_PLANKS.get()), has(ModItems.SHADOW_PLANKS.get()))
                .save(pWriter);

        // 破旧阴影书架：左右两本书换成纸
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.WORN_SHADOW_BOOKSHELF.get())
                .pattern("aaa")
                .pattern("cbc")
                .pattern("aaa")
                .define('a', ModItems.SHADOW_PLANKS.get())
                .define('b', Items.BOOK)
                .define('c', Items.PAPER)
                .unlockedBy(getHasName(ModItems.SHADOW_PLANKS.get()), has(ModItems.SHADOW_PLANKS.get()))
                .save(pWriter);

        // 蛛网阴影书架：左右两本书换成蜘蛛网
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.COBWEB_SHADOW_BOOKSHELF.get())
                .pattern("aaa")
                .pattern("cbc")
                .pattern("aaa")
                .define('a', ModItems.SHADOW_PLANKS.get())
                .define('b', Items.BOOK)
                .define('c', Items.COBWEB)
                .unlockedBy(getHasName(ModItems.SHADOW_PLANKS.get()), has(ModItems.SHADOW_PLANKS.get()))
                .save(pWriter);

        // 钥匙阴影书架：中间的书换成暗影地牢钥匙
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.KEY_SHADOW_BOOKSHELF.get())
                .pattern("aaa")
                .pattern("bcb")
                .pattern("aaa")
                .define('a', ModItems.SHADOW_PLANKS.get())
                .define('b', Items.BOOK)
                .define('c', ModItems.SHADOW_DUNGEON_KEY.get())
                .unlockedBy(getHasName(ModItems.SHADOW_DUNGEON_KEY.get()), has(ModItems.SHADOW_DUNGEON_KEY.get()))
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

    // ===== 炙焰金工具配方 =====

    private void moltenGoldToolRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.MOLTEN_GOLD_SWORD.get())
                .pattern("a").pattern("a").pattern("b")
                .define('a', ModItems.MOLTEN_GOLD_INGOT.get()).define('b', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_INGOT.get()), has(ModItems.MOLTEN_GOLD_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.MOLTEN_GOLD_PICKAXE.get())
                .pattern("aaa").pattern(" b ").pattern(" b ")
                .define('a', ModItems.MOLTEN_GOLD_INGOT.get()).define('b', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_INGOT.get()), has(ModItems.MOLTEN_GOLD_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.MOLTEN_GOLD_AXE.get())
                .pattern("aa").pattern("ab").pattern(" b")
                .define('a', ModItems.MOLTEN_GOLD_INGOT.get()).define('b', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_INGOT.get()), has(ModItems.MOLTEN_GOLD_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.MOLTEN_GOLD_SHOVEL.get())
                .pattern("a").pattern("b").pattern("b")
                .define('a', ModItems.MOLTEN_GOLD_INGOT.get()).define('b', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_INGOT.get()), has(ModItems.MOLTEN_GOLD_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.MOLTEN_GOLD_HOE.get())
                .pattern("aa").pattern(" b").pattern(" b")
                .define('a', ModItems.MOLTEN_GOLD_INGOT.get()).define('b', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_INGOT.get()), has(ModItems.MOLTEN_GOLD_INGOT.get()))
                .save(pWriter);
    }

    // ===== 狱炎工具配方 =====

    private void hellfireToolRecipes(Consumer<FinishedRecipe> pWriter) {
        saveNbtPreservingShaped(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.HELLFIRE_SWORD.get())
                .pattern(" a ").pattern("bcb").pattern(" d ")
                .define('a', Items.BLAZE_POWDER)
                .define('b', Items.NETHERITE_SCRAP)
                .define('c', ModItems.MOLTEN_GOLD_SWORD.get())
                .define('d', ModItems.MOLTEN_GOLD_BLOCK.get())
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_SWORD.get()), has(ModItems.MOLTEN_GOLD_SWORD.get())),
                pWriter, "hellfire_sword");
        saveNbtPreservingShaped(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.HELLFIRE_PICKAXE.get())
                .pattern(" a ").pattern("bcb").pattern(" d ")
                .define('a', Items.BLAZE_POWDER)
                .define('b', Items.NETHERITE_SCRAP)
                .define('c', ModItems.MOLTEN_GOLD_PICKAXE.get())
                .define('d', ModItems.MOLTEN_GOLD_BLOCK.get())
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_PICKAXE.get()), has(ModItems.MOLTEN_GOLD_PICKAXE.get())),
                pWriter, "hellfire_pickaxe");

        // 融骸狱炎剑
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.NETHER_BRICK),
                        Ingredient.of(ModItems.HELLFIRE_SWORD.get()),
                        Ingredient.of(Items.NETHER_STAR),
                        RecipeCategory.COMBAT, ModItems.INFERNO_SWORD.get())
                .unlocks("has_nether_star", has(Items.NETHER_STAR))
                .save(pWriter, PasterDreamMod.MOD_ID + ":inferno_sword_smithing");
    }

    // ===== 融梦水晶工具配方 =====

    private void meltDreamToolRecipes(Consumer<FinishedRecipe> pWriter) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        Ingredient.of(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()),
                        Ingredient.of(ModItems.DYEDREAM_PICKAXE.get()),
                        RecipeCategory.TOOLS, ModItems.MELT_DREAM_PICKAXE.get())
                .unlocks("has_melt_dream_crystal_fragment", has(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":melt_dream_pickaxe_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        Ingredient.of(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()),
                        Ingredient.of(ModItems.DYEDREAM_AXE.get()),
                        RecipeCategory.TOOLS, ModItems.MELT_DREAM_AXE.get())
                .unlocks("has_melt_dream_crystal_fragment", has(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":melt_dream_axe_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        Ingredient.of(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()),
                        Ingredient.of(ModItems.DYEDREAM_SHOVEL.get()),
                        RecipeCategory.TOOLS, ModItems.MELT_DREAM_SHOVEL.get())
                .unlocks("has_melt_dream_crystal_fragment", has(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":melt_dream_shovel_smithing");
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        Ingredient.of(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()),
                        Ingredient.of(ModItems.DYEDREAM_HOE.get()),
                        RecipeCategory.TOOLS, ModItems.MELT_DREAM_HOE.get())
                .unlocks("has_melt_dream_crystal_fragment", has(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":melt_dream_hoe_smithing");
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
        // 工作台升级：钻石工具 + 2钛金锭 + 黑石棍 → 钛金工具（保留附魔）
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.TITANIUM_SWORD.get())
                .requires(Items.DIAMOND_SWORD)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_sword_crafting");
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.TITANIUM_PICKAXE.get())
                .requires(Items.DIAMOND_PICKAXE)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_pickaxe_crafting");
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.TITANIUM_AXE.get())
                .requires(Items.DIAMOND_AXE)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_axe_crafting");
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.TITANIUM_SHOVEL.get())
                .requires(Items.DIAMOND_SHOVEL)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_shovel_crafting");
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.TITANIUM_HOE.get())
                .requires(Items.DIAMOND_HOE)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_hoe_crafting");

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
        // 蔚蓝海洋之心
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLUE_HEART_OF_THE_SEA.get(), 1)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', Items.GLOW_INK_SAC)
                .define('b', ModItems.BLUE_DEW.get())
                .define('c', Items.HEART_OF_THE_SEA)
                .unlockedBy(getHasName(Items.HEART_OF_THE_SEA), has(Items.HEART_OF_THE_SEA))
                .save(pWriter);

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

        // ===== 黑金属配方 =====
        // 黑金属粒 → 黑金属锭
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLACK_METAL_INGOT.get(), 1)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.BLACK_METAL_GRAIN.get())
                .unlockedBy(getHasName(ModItems.BLACK_METAL_GRAIN.get()), has(ModItems.BLACK_METAL_GRAIN.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "black_metal_ingot_from_grain"));

        // 黑金属锭 → 黑金属块
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.BLACK_METAL_BLOCK.get(), 1)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.BLACK_METAL_INGOT.get())
                .unlockedBy(getHasName(ModItems.BLACK_METAL_INGOT.get()), has(ModItems.BLACK_METAL_INGOT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "black_metal_block_from_ingot"));

        // 黑金属锭 → 黑金属粒（分解）
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BLACK_METAL_GRAIN.get(), 4)
                .requires(ModItems.BLACK_METAL_INGOT.get())
                .unlockedBy(getHasName(ModItems.BLACK_METAL_INGOT.get()), has(ModItems.BLACK_METAL_INGOT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "black_metal_grain_from_ingot"));

        // 黑金属块 → 黑金属锭（分解）
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BLACK_METAL_INGOT.get(), 4)
                .requires(ModItems.BLACK_METAL_BLOCK.get())
                .unlockedBy(getHasName(ModItems.BLACK_METAL_BLOCK.get()), has(ModItems.BLACK_METAL_BLOCK.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "black_metal_ingot_from_block"));

        // 锈蚀金属块
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.RUST_BLACK_METAL_BLOCK.get(), 4)
                .pattern(" a ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', ModItems.RUST_BLACK_METAL_GRAIN.get())
                .define('b', Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.RUST_BLACK_METAL_GRAIN.get()), has(ModItems.RUST_BLACK_METAL_GRAIN.get()))
                .save(pWriter);

        // 锈蚀金属墙
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.RUST_BLACK_METAL_WALL.get(), 2)
                .pattern("aba")
                .define('a', ModItems.RUST_BLACK_METAL_GRAIN.get())
                .define('b', Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.RUST_BLACK_METAL_GRAIN.get()), has(ModItems.RUST_BLACK_METAL_GRAIN.get()))
                .save(pWriter);

        // 锈蚀金属栏杆
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.RUST_BLACK_METAL_BARS.get(), 1)
                .requires(Items.IRON_BARS)
                .requires(ModItems.RUST_BLACK_METAL_GRAIN.get())
                .unlockedBy(getHasName(ModItems.RUST_BLACK_METAL_GRAIN.get()), has(ModItems.RUST_BLACK_METAL_GRAIN.get()))
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

        // 极锋染梦合金剑
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_DUST.get()),
                        Ingredient.of(ModItems.DYEDREAM_SWORD.get()),
                        Ingredient.of(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()),
                        RecipeCategory.COMBAT, ModItems.SHARP_MELT_DREAM_SWORD.get())
                .unlocks("has_melt_dream_crystal_fragment", has(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_sharp_sword_smithing");

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
        // 工作台升级：钻石装备 + 2钛金锭 + 黑石棍 → 钛金装备（保留附魔）
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.TITANIUM_HELMET.get())
                .requires(Items.DIAMOND_HELMET)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_helmet_crafting");
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.TITANIUM_CHESTPLATE.get())
                .requires(Items.DIAMOND_CHESTPLATE)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_chestplate_crafting");
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.TITANIUM_LEGGINGS.get())
                .requires(Items.DIAMOND_LEGGINGS)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_leggings_crafting");
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.TITANIUM_BOOTS.get())
                .requires(Items.DIAMOND_BOOTS)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_boots_crafting");

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

        // 园艺钳
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.PLIERS.get(), 1)
                .pattern(" a ")
                .pattern("b a")
                .pattern("cb ")
                .define('a', Items.IRON_INGOT)
                .define('b', Items.STICK)
                .define('c', ItemTags.PLANKS)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

        // 两个储物袋合成配方
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STORAGE_BAG.get(), 1)
                .pattern(" b ")
                .pattern("a a")
                .pattern(" a ")
                .define('a', ModItems.FABRIC.get())
                .define('b', ModItems.SPOOL.get())
                .unlockedBy(getHasName(ModItems.FABRIC.get()), has(ModItems.FABRIC.get()))
                .save(pWriter);

        saveNbtPreservingShaped(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LARGE_STORAGE_BAG.get())
                        .pattern(" d ")
                        .pattern("bab")
                        .pattern(" c ")
                        .define('a', ModItems.STORAGE_BAG.get())
                        .define('b', ModItems.FABRIC.get())
                        .define('c', Items.ENDER_PEARL)
                        .define('d', Items.DIAMOND)
                        .unlockedBy(getHasName(ModItems.STORAGE_BAG.get()), has(ModItems.STORAGE_BAG.get())),
                pWriter, "large_storage_bag");
    }

    // ===== 草薙配方 =====

    private void grassSwordRecipe(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.KUSANAGI.get())
                .pattern(" ab")
                .pattern("aca")
                .pattern("da ")
                .define('a', ModItems.JUNGLE_SPORE.get())
                .define('b', Items.SPORE_BLOSSOM)
                .define('c', ModItems.SWORD_EMBRYO.get())
                .define('d', Items.EMERALD_BLOCK)
                .unlockedBy(getHasName(ModItems.JUNGLE_SPORE.get()), has(ModItems.JUNGLE_SPORE.get()))
                .save(pWriter);
    }

    // ===== 引潮剑配方 =====

    private void tideSwordRecipes(Consumer<FinishedRecipe> pWriter) {
        // 引潮剑
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TIDE_SWORD.get())
                .pattern(" ab")
                .pattern("aca")
                .pattern("da ")
                .define('a', Items.PRISMARINE_SHARD)
                .define('b', Items.PRISMARINE_CRYSTALS)
                .define('c', Items.TRIDENT)
                .define('d', ModItems.ELDER_GUARDIAN_SCALE.get())
                .unlockedBy(getHasName(ModItems.ELDER_GUARDIAN_SCALE.get()), has(ModItems.ELDER_GUARDIAN_SCALE.get()))
                .save(pWriter);

        // 『北海若』引潮 (锻造台)
        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(Items.PRISMARINE_CRYSTALS),
                Ingredient.of(ModItems.TIDE_SWORD.get()),
                Ingredient.of(ModItems.BLUE_HEART_OF_THE_SEA.get()),
                RecipeCategory.COMBAT, ModItems.BEIHAI_RUO_TIDE_SWORD.get())
                .unlocks("has_tide_sword", has(ModItems.TIDE_SWORD.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":beihairuo_tide_sword_smithing");
    }

    // ===== 大地之刃配方 =====

    private void terraBladeRecipe(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TERRA_BLADE.get())
                .pattern("abc")
                .pattern("def")
                .pattern("ghi")
                .define('a', ModItems.SHARP_MELT_DREAM_SWORD.get())
                .define('b', ModItems.INFERNO_SWORD.get())
                .define('c', ModItems.MURAKUMO_KUSANAGI.get())
                .define('d', ModItems.CHENJINGMEN_DESERT_SWORD.get())
                .define('e', ModItems.MELT_DREAM_LIQUID_BUCKET.get())
                .define('f', ModItems.BROKEN_HERO_SWORD.get())
                .define('g', ModItems.BEIHAI_RUO_TIDE_SWORD.get())
                .define('h', ModItems.THERMAL_DAGGER.get())
                .define('i', Items.NETHERITE_SWORD)
                .unlockedBy(getHasName(ModItems.BROKEN_HERO_SWORD.get()), has(ModItems.BROKEN_HERO_SWORD.get()))
                .save(pWriter);
    }

    // ===== 占星者的祈愿配方 =====

    private void starWishRodRecipe(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.STAR_WISH_ROD.get())
                .pattern("aba")
                .pattern("cde")
                .pattern("afa")
                .define('a', ModItems.TITANIUM_INGOT.get())
                .define('b', ModItems.SNOW_VOW_HEAD.get())
                .define('c', Items.POWDER_SNOW_BUCKET)
                .define('d', Items.FISHING_ROD)
                .define('e', Items.DIAMOND)
                .define('f', ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter);
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

        // 白沙 → 澄澈玻璃（熔炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.WHITE_SAND.get()),
                        RecipeCategory.BUILDING_BLOCKS, ModBlocks.CLARITY_GLASS.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModBlocks.WHITE_SAND.get()), has(ModBlocks.WHITE_SAND.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":clarity_glass_from_smelting");

        // 澄澈玻璃 → 16× 澄澈玻璃板
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CLARITY_GLASS_PANE.get(), 16)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModBlocks.CLARITY_GLASS.get())
                .unlockedBy(getHasName(ModBlocks.CLARITY_GLASS.get()), has(ModBlocks.CLARITY_GLASS.get()))
                .save(pWriter);

        // 4× 澄澈玻璃 → 4× 雕纹澄澈玻璃
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CARVE_CLARITY_GLASS.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModBlocks.CLARITY_GLASS.get())
                .unlockedBy(getHasName(ModBlocks.CLARITY_GLASS.get()), has(ModBlocks.CLARITY_GLASS.get()))
                .save(pWriter);

        // 雕纹澄澈玻璃 → 16× 雕纹澄澈玻璃板
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CARVE_CLARITY_GLASS_PANE.get(), 16)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModBlocks.CARVE_CLARITY_GLASS.get())
                .unlockedBy(getHasName(ModBlocks.CARVE_CLARITY_GLASS.get()), has(ModBlocks.CARVE_CLARITY_GLASS.get()))
                .save(pWriter);

        // 3× 澄澈玻璃 + 磨制方解石 → 4× 镶框澄澈玻璃
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.FRAME_CLARITY_GLASS.get(), 4)
                .requires(ModBlocks.CLARITY_GLASS.get())
                .requires(ModBlocks.CLARITY_GLASS.get())
                .requires(ModBlocks.CLARITY_GLASS.get())
                .requires(ModBlocks.POLISHED_CALCITE.get())
                .unlockedBy(getHasName(ModBlocks.CLARITY_GLASS.get()), has(ModBlocks.CLARITY_GLASS.get()))
                .save(pWriter);

        // 镶框澄澈玻璃 → 16× 镶框澄澈玻璃板
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.FRAME_CLARITY_GLASS_PANE.get(), 16)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModBlocks.FRAME_CLARITY_GLASS.get())
                .unlockedBy(getHasName(ModBlocks.FRAME_CLARITY_GLASS.get()), has(ModBlocks.FRAME_CLARITY_GLASS.get()))
                .save(pWriter);

        // 破风幕帐：四角+中间 5× 凝结之风，四边 4× 澄澈玻璃板 → 8× 破风幕帐
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.BREAK_WIND_CURTAIN.get(), 8)
                .pattern("aba")
                .pattern("bab")
                .pattern("aba")
                .define('a', ModItems.CONGEAL_WIND.get())
                .define('b', ModItems.CLARITY_GLASS_PANE.get())
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND.get()), has(ModItems.CONGEAL_WIND.get()))
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

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.TALL_PINK_MUSHROOM.get(), 1)
                .requires(ModBlocks.PINK_MUSHROOM.get())
                .requires(Items.BONE_MEAL)
                .unlockedBy(getHasName(ModBlocks.PINK_MUSHROOM.get()), has(ModBlocks.PINK_MUSHROOM.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":tall_pink_mushroom_from_bone_meal");
    }

    // ===== 粉色粘液配方 =====

    private void pinkSlimeRecipes(Consumer<FinishedRecipe> pWriter) {
        RecipeHelpers.storageCompress(pWriter, ModItems.PINK_SLIMEBALL.get(), ModItems.PINK_SLIME_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.PINK_SLIME_BLOCK.get(), ModItems.PINK_SLIMEBALL.get(), PasterDreamMod.MOD_ID);
    }

    // ===== 厚重云朵配方 =====

    private void thickCloudRecipes(Consumer<FinishedRecipe> pWriter) {
        RecipeHelpers.storageCompress(pWriter, ModItems.CLOUD.get(), ModItems.THICK_CLOUD.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.THICK_CLOUD.get(), ModItems.CLOUD.get(), PasterDreamMod.MOD_ID);
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

        // 小石子（圆石 + 研钵）
        saveMortarCrafting(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PEBBLE.get(), 2)
                        .requires(Items.COBBLESTONE)
                        .requires(ModItems.MORTAR.get())
                        .unlockedBy(getHasName(Items.COBBLESTONE), has(Items.COBBLESTONE)),
                pWriter, "pebble");

        // 小石子 → 圆石
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.COBBLESTONE, 1)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.PEBBLE.get())
                .unlockedBy(getHasName(ModItems.PEBBLE.get()), has(ModItems.PEBBLE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":cobblestone_from_pebble");

        // 小石子 → 燧石
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.FLINT, 1)
                .pattern("aa")
                .pattern("a ")
                .define('a', ModItems.PEBBLE.get())
                .unlockedBy(getHasName(ModItems.PEBBLE.get()), has(ModItems.PEBBLE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":flint_from_pebble");

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

        // ===== 风系列 =====
        // 凝结之风云矿 → 凝结之风（熔炉 + 高炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CONGEAL_WIND_ORE.get()),
                        RecipeCategory.MISC, ModItems.CONGEAL_WIND.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND_ORE.get()), has(ModItems.CONGEAL_WIND_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":congeal_wind_from_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.CONGEAL_WIND_ORE.get()),
                        RecipeCategory.MISC, ModItems.CONGEAL_WIND.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND_ORE.get()), has(ModItems.CONGEAL_WIND_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":congeal_wind_from_blasting");
        // 风行者水晶矿石 → 风行者水晶（熔炉 + 高炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.WIND_RUNNER_CRYSTAL_ORE.get()),
                        RecipeCategory.MISC, ModItems.WIND_RUNNER_CRYSTAL.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.WIND_RUNNER_CRYSTAL_ORE.get()), has(ModItems.WIND_RUNNER_CRYSTAL_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":wind_runner_crystal_from_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.WIND_RUNNER_CRYSTAL_ORE.get()),
                        RecipeCategory.MISC, ModItems.WIND_RUNNER_CRYSTAL.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.WIND_RUNNER_CRYSTAL_ORE.get()), has(ModItems.WIND_RUNNER_CRYSTAL_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":wind_runner_crystal_from_blasting");
        // 凝结之风块 ↔ 凝结之风
        RecipeHelpers.storageCompress(pWriter, ModItems.CONGEAL_WIND.get(), ModItems.CONGEAL_WIND_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.CONGEAL_WIND_BLOCK.get(), ModItems.CONGEAL_WIND.get(), PasterDreamMod.MOD_ID);
        // 风行者水晶块 ↔ 风行者水晶
        RecipeHelpers.storageCompress(pWriter, ModItems.WIND_RUNNER_CRYSTAL.get(), ModItems.WIND_RUNNER_CRYSTAL_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.WIND_RUNNER_CRYSTAL_BLOCK.get(), ModItems.WIND_RUNNER_CRYSTAL.get(), PasterDreamMod.MOD_ID);
        // 凝风铁锭：铁锭 + 风植萃取液 + 凝结之风×2
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CONGEAL_WIND_IRON_INGOT.get(), 1)
                .requires(Items.IRON_INGOT)
                .requires(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get())
                .requires(ModItems.CONGEAL_WIND.get())
                .requires(ModItems.CONGEAL_WIND.get())
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND.get()), has(ModItems.CONGEAL_WIND.get()))
                .save(pWriter);
        // 凝风铁块 ↔ 凝风铁锭
        RecipeHelpers.storageCompress(pWriter, ModItems.CONGEAL_WIND_IRON_INGOT.get(), ModItems.CONGEAL_WIND_IRON_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.CONGEAL_WIND_IRON_BLOCK.get(), ModItems.CONGEAL_WIND_IRON_INGOT.get(), PasterDreamMod.MOD_ID);
        // 凝风铁栏杆：6×凝风铁锭 → 16
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CONGEAL_WIND_IRON_BARS.get(), 16)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModItems.CONGEAL_WIND_IRON_INGOT.get())
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND_IRON_INGOT.get()), has(ModItems.CONGEAL_WIND_IRON_INGOT.get()))
                .save(pWriter);
        // 弹射方块：凝风铁锭×2 + 黏液球×2
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EJECTION_PRESSURE_BLOCK.get(), 1)
                .pattern("aa")
                .pattern("bb")
                .define('a', ModItems.CONGEAL_WIND_IRON_INGOT.get())
                .define('b', Items.SLIME_BALL)
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND_IRON_INGOT.get()), has(ModItems.CONGEAL_WIND_IRON_INGOT.get()))
                .save(pWriter);
        // 弹射压力板：凝风铁锭×2
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EJECTION_PRESSURE_PLATE.get(), 1)
                .pattern("aa")
                .define('a', ModItems.CONGEAL_WIND_IRON_INGOT.get())
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND_IRON_INGOT.get()), has(ModItems.CONGEAL_WIND_IRON_INGOT.get()))
                .save(pWriter);

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

    // ===== 阴影石砖系列配方 =====

    private void shadowStoneRecipes(Consumer<FinishedRecipe> pWriter) {
        // 2×2 阴影石 → 4× 阴影石砖
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_STONE_BRICK.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.SHADOW_STONE.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE.get()), has(ModItems.SHADOW_STONE.get()))
                .save(pWriter);

        // 阴影石砖 → 楼梯/台阶/墙 + 切石机
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.SHADOW_STONE_BRICK.get(), ModItems.SHADOW_STONE_BRICK_STAIRS.get(),
                ModItems.SHADOW_STONE_BRICK_SLAB.get(), ModItems.SHADOW_STONE_BRICK_WALL.get(),
                PasterDreamMod.MOD_ID);

        // 阴影石 → 阴影石砖 (切石机)
        var shadowStoneIngredient = Ingredient.of(ModItems.SHADOW_STONE.get());
        SingleItemRecipeBuilder.stonecutting(shadowStoneIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_STONE_BRICK.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE.get()), has(ModItems.SHADOW_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":shadow_stone_brick_from_stonecutting");

        // 阴影石建材 → 阴影石 (切石机，使用 forge:shadow_stones 标签)
        var shadowStonesTag = Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "shadow_stones")));
        SingleItemRecipeBuilder.stonecutting(shadowStonesTag, RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_STONE.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE.get()), has(ModItems.SHADOW_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":shadow_stone_from_stonecutting");

        // ===== 细阴影石砖系列配方 =====
        // 2×2 阴影石砖 → 4× 细阴影石砖
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.NARROW_SHADOW_STONE_BRICK.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.SHADOW_STONE_BRICK.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE_BRICK.get()), has(ModItems.SHADOW_STONE_BRICK.get()))
                .save(pWriter);

        // 细阴影石砖 → 楼梯/台阶/墙 + 切石机
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.NARROW_SHADOW_STONE_BRICK.get(), ModItems.NARROW_SHADOW_STONE_BRICK_STAIRS.get(),
                ModItems.NARROW_SHADOW_STONE_BRICK_SLAB.get(), ModItems.NARROW_SHADOW_STONE_BRICK_WALL.get(),
                PasterDreamMod.MOD_ID);

        // 阴影石建材 → 细阴影石砖 (切石机，使用 forge:shadow_stones 标签)
        SingleItemRecipeBuilder.stonecutting(shadowStonesTag, RecipeCategory.BUILDING_BLOCKS, ModItems.NARROW_SHADOW_STONE_BRICK.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE.get()), has(ModItems.SHADOW_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":narrow_shadow_stone_brick_from_stonecutting");

        // ===== 阴影石瓦系列配方 =====
        // 2×2 细阴影石砖 → 4× 阴影石瓦
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_STONE_TILES.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.NARROW_SHADOW_STONE_BRICK.get())
                .unlockedBy(getHasName(ModItems.NARROW_SHADOW_STONE_BRICK.get()), has(ModItems.NARROW_SHADOW_STONE_BRICK.get()))
                .save(pWriter);

        // 阴影石瓦 → 楼梯/台阶/墙 + 切石机
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.SHADOW_STONE_TILES.get(), ModItems.SHADOW_STONE_TILES_STAIRS.get(),
                ModItems.SHADOW_STONE_TILES_SLAB.get(), ModItems.SHADOW_STONE_TILES_WALL.get(),
                PasterDreamMod.MOD_ID);

        // 阴影石建材 → 阴影石瓦 (切石机，使用 forge:shadow_stones 标签)
        SingleItemRecipeBuilder.stonecutting(shadowStonesTag, RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_STONE_TILES.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE.get()), has(ModItems.SHADOW_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":shadow_stone_tiles_from_stonecutting");

        // 阴影石瓦 → 阴影陶罐 (切石机，打碎陶罐时解锁)
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.SHADOW_STONE_TILES.get()), RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_CLAY_POT.get())
                .unlockedBy(getHasName(ModBlocks.SHADOW_STONE_TILES.get()), has(ModBlocks.SHADOW_STONE_TILES.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":shadow_clay_pot_from_stonecutting");

        // ===== 裂阴影石砖 / 錾制阴影石砖配方 =====
        // 2× 阴影石砖台阶 → 1× 錾制阴影石砖 (工作台)
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_SHADOW_STONE_BRICK.get(), 1)
                .pattern("a")
                .pattern("a")
                .define('a', ModItems.SHADOW_STONE_BRICK_SLAB.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE_BRICK.get()), has(ModItems.SHADOW_STONE_BRICK.get()))
                .save(pWriter);

        // 阴影石建材 → 裂阴影石砖 (切石机)
        SingleItemRecipeBuilder.stonecutting(shadowStonesTag, RecipeCategory.BUILDING_BLOCKS, ModItems.CRACKED_SHADOW_STONE_BRICK.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE.get()), has(ModItems.SHADOW_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":cracked_shadow_stone_brick_from_stonecutting");

        // 阴影石建材 → 錾制阴影石砖 (切石机)
        SingleItemRecipeBuilder.stonecutting(shadowStonesTag, RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_SHADOW_STONE_BRICK.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE.get()), has(ModItems.SHADOW_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":chiseled_shadow_stone_brick_from_stonecutting");
    }

    // ===== 苍青岩砖系列配方 =====

    private void cyanStoneRecipes(Consumer<FinishedRecipe> pWriter) {
        // 2×2 苍青岩 → 4× 苍青岩砖
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CYAN_STONE_BRICKS.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.CYAN_STONE.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE.get()), has(ModItems.CYAN_STONE.get()))
                .save(pWriter);

        // 苍青岩砖 → 楼梯/台阶/墙 + 切石机
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.CYAN_STONE_BRICKS.get(), ModItems.CYAN_STONE_BRICK_STAIRS.get(),
                ModItems.CYAN_STONE_BRICK_SLAB.get(), ModItems.CYAN_STONE_BRICK_WALL.get(),
                PasterDreamMod.MOD_ID);

        // 苍青岩 → 苍青岩砖 (切石机)
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.CYAN_STONE.get()), RecipeCategory.BUILDING_BLOCKS, ModItems.CYAN_STONE_BRICKS.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE.get()), has(ModItems.CYAN_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":cyan_stone_bricks_from_stonecutting");

        // 2× 苍青岩 → 2× 苍青岩柱
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CYAN_STONE_PILLAR.get(), 2)
                .pattern("a")
                .pattern("a")
                .define('a', ModItems.CYAN_STONE.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE.get()), has(ModItems.CYAN_STONE.get()))
                .save(pWriter);

        // 苍青岩 → 苍青岩柱 (切石机)
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.CYAN_STONE.get()), RecipeCategory.BUILDING_BLOCKS, ModItems.CYAN_STONE_PILLAR.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE.get()), has(ModItems.CYAN_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":cyan_stone_pillar_from_stonecutting");

        // 2× 苍青岩砖台阶 → 雕文苍青岩砖
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_CYAN_STONE_BRICKS.get(), 1)
                .pattern("a")
                .pattern("a")
                .define('a', ModItems.CYAN_STONE_BRICK_SLAB.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE_BRICK_SLAB.get()), has(ModItems.CYAN_STONE_BRICK_SLAB.get()))
                .save(pWriter);

        // 苍青岩 → 雕文苍青岩砖 (切石机)
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.CYAN_STONE.get()), RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_CYAN_STONE_BRICKS.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE.get()), has(ModItems.CYAN_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":chiseled_cyan_stone_bricks_from_stonecutting");

        // 苍青岩 → 苍青岩压力板
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModItems.CYAN_STONE_PRESSURE_PLATE.get(), 1)
                .pattern("aa")
                .define('a', ModItems.CYAN_STONE.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE.get()), has(ModItems.CYAN_STONE.get()))
                .save(pWriter);

        // 苍青岩 → 苍青岩按钮
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ModItems.CYAN_STONE_BUTTON.get(), 1)
                .requires(ModItems.CYAN_STONE.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE.get()), has(ModItems.CYAN_STONE.get()))
                .save(pWriter);
    }

    // ===== 苔苍青岩砖系列配方 =====

    private void mossyCyanStoneRecipes(Consumer<FinishedRecipe> pWriter) {
        // 苍青岩砖 + 藤蔓 → 苔苍青岩砖
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.MOSSY_CYAN_STONE_BRICKS.get(), 1)
                .requires(ModItems.CYAN_STONE_BRICKS.get())
                .requires(Items.VINE)
                .unlockedBy(getHasName(ModItems.CYAN_STONE_BRICKS.get()), has(ModItems.CYAN_STONE_BRICKS.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":mossy_cyan_stone_bricks_from_vine");

        // 苍青岩砖 + 苔藓块 → 苔苍青岩砖
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.MOSSY_CYAN_STONE_BRICKS.get(), 1)
                .requires(ModItems.CYAN_STONE_BRICKS.get())
                .requires(Items.MOSS_BLOCK)
                .unlockedBy(getHasName(ModItems.CYAN_STONE_BRICKS.get()), has(ModItems.CYAN_STONE_BRICKS.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":mossy_cyan_stone_bricks_from_moss_block");

        // 苔苍青岩砖 → 楼梯/台阶/墙 + 切石机
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.MOSSY_CYAN_STONE_BRICKS.get(), ModItems.MOSSY_CYAN_STONE_BRICK_STAIRS.get(),
                ModItems.MOSSY_CYAN_STONE_BRICK_SLAB.get(), ModItems.MOSSY_CYAN_STONE_BRICK_WALL.get(),
                PasterDreamMod.MOD_ID);
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

        // 灵药瓶合成配方
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ELIXIR_BOTTLE.get(), 1)
                .pattern(" a ")
                .pattern("bcb")
                .pattern(" b ")
                .define('a',Items.GOLD_NUGGET)
                .define('b', ModItems.DYEDREAM_ALLOY_NUGGET.get())
                .define('c', ModItems.GLASS_JAR.get())
                .unlockedBy(getHasName(ModItems.GLASS_JAR.get()), has(ModItems.GLASS_JAR.get()))
                .save(pWriter);

        // 重做酵母合成配方（产物罐子数多于输入空罐子数，自动配平）
        saveContainerBalancedShapeless(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_YEAST.get(), 4)
                        .requires(ModItems.GLASS_JAR.get(), 2)
                        .requires(ModItems.FLOUR.get(), 1)
                        .requires(ModItems.GLASS_JAR_OF_WATER.get(), 1)
                        .requires(ModItems.GLASS_JAR_OF_YEAST.get(), 1)
                        .requires(Items.SUGAR, 1)
                        .unlockedBy(getHasName(ModItems.GLASS_JAR.get()), has(ModItems.GLASS_JAR.get())),
                pWriter, "glass_jar_of_yeast",
                ModItems.GLASS_JAR_OF_YEAST.get(), 4,
                ModItems.GLASS_JAR.get(), 2,
                ModItems.FLOUR.get(), 1,
                ModItems.GLASS_JAR_OF_WATER.get(), 1,
                ModItems.GLASS_JAR_OF_YEAST.get(), 1,
                Items.SUGAR, 1);

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
                .requires(ModItems.DYEDREAM_LILY_OF_THE_VALLEY.get(),1)
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
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()),
                        RecipeCategory.MISC, ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()), has(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":glass_cup_of_cooked_dyedream_flower_tea_from_glass_cup_of_uncooked_dyedream_flower_tea_smelting");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()),
                        RecipeCategory.MISC, ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()), has(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":glass_cup_of_cooked_dyedream_flower_tea_from_glass_cup_of_uncooked_dyedream_flower_tea_smoking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()),
                        RecipeCategory.MISC, ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get(), 0, 600)
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

        //秋麒麟茶合成配方（空罐子由水罐 remainder 配平抵消）
        saveContainerBalancedShapeless(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_GOLDENROD_TEA.get(), 1)
                        .requires(ModItems.GOLDENROD.get(),1)
                        .requires(ModItems.GLASS_JAR_OF_WATER.get(),1)
                        .unlockedBy(getHasName(ModItems.GOLDENROD.get()), has(ModItems.GOLDENROD.get())),
                pWriter, "glass_jar_of_goldenrod_tea",
                ModItems.GLASS_JAR_OF_GOLDENROD_TEA.get(), 1,
                ModItems.GOLDENROD.get(), 1,
                ModItems.GLASS_JAR_OF_WATER.get(), 1);

        // 染梦香水合成配方（空罐子由水罐 remainder 配平抵消）
        saveContainerBalancedShapeless(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME.get(), 1)
                        .requires(ModItems.GLASS_JAR_OF_WATER.get(),1)
                        .requires(ModItems.DYEDREAM_DUST_PIECE.get(),1)
                        .requires(Items.FERMENTED_SPIDER_EYE,1)
                        .requires(ModItems.PINK_MUSHROOM.get(),1)
                        .requires(ModItems.DYEDREAM_MOSS.get(),1)
                        .unlockedBy(getHasName(ModItems.GLASS_JAR_OF_WATER.get()), has(ModItems.GLASS_JAR_OF_WATER.get())),
                pWriter, "glass_jar_of_dyedream_perfume",
                ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME.get(), 1,
                ModItems.GLASS_JAR_OF_WATER.get(), 1,
                ModItems.DYEDREAM_DUST_PIECE.get(), 1,
                Items.FERMENTED_SPIDER_EYE, 1,
                ModItems.PINK_MUSHROOM.get(), 1,
                ModItems.DYEDREAM_MOSS.get(), 1);

        // 梦果汁合成配方（产物是填充罐子，自动配平容器）
        saveContainerBalancedShapeless(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_DREAM_JUICE.get(), 1)
                        .requires(ModItems.GLASS_JAR_OF_WATER.get(),1)
                        .requires(Items.BLUE_DYE,1)
                        .requires(Items.PINK_DYE,1)
                        .requires(Items.PURPLE_DYE,1)
                        .requires(ModItems.DYEDREAM_DYE.get(),1)
                        .unlockedBy(getHasName(ModItems.GLASS_JAR_OF_WATER.get()), has(ModItems.GLASS_JAR_OF_WATER.get())),
                pWriter, "glass_jar_of_dream_juice",
                ModItems.GLASS_JAR_OF_DREAM_JUICE.get(), 1,
                ModItems.GLASS_JAR_OF_WATER.get(), 1,
                Items.BLUE_DYE, 1,
                Items.PINK_DYE, 1,
                Items.PURPLE_DYE, 1,
                ModItems.DYEDREAM_DYE.get(), 1);

        // 奇异炖菜合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.QUEER_SOUP.get(), 1)
                .requires(Items.MUSHROOM_STEW,1)
                .requires(ModItems.SINGULARITY_FERN.get(),1)
                .requires(ModItems.MISTY_DREAMING_LOTUS.get(),1)
                .requires(ModItems.DYEDREAM_COROLLA.get(),1)
                .requires(Items.GHAST_TEAR,1)
                .unlockedBy(getHasName(ModItems.MISTY_DREAMING_LOTUS.get()), has(ModItems.MISTY_DREAMING_LOTUS.get()))
                .save(pWriter);

        // 银河果冻合成配方
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MILKY_WAY_JELLY.get(), 1)
                .pattern("cfc")
                .pattern("fgf")
                .pattern("cfc")
                .define('c', ModItems.CLOUD.get())
                .define('f', Items.FEATHER)
                .define('g', ModItems.GALAXY_JELLY.get())
                .unlockedBy(getHasName(ModItems.GALAXY_JELLY.get()), has(ModItems.GALAXY_JELLY.get()))
                .save(pWriter);

    }

    // ===== 其他杂项类合成配方 =====
    private void othersRecipes(Consumer<FinishedRecipe> pWriter) {

        // 厚重阴影 (4 shadow → 1 thick_shadow)
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.THICK_SHADOW.get(), 1)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.SHADOW.get())
                .unlockedBy(getHasName(ModItems.SHADOW.get()), has(ModItems.SHADOW.get()))
                .save(pWriter);

        // 阴影菌核 (2×2 shadow_stem → 3 shadow_hyphae)
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_HYPHAE.get(), 3)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.SHADOW_STEM.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STEM.get()), has(ModItems.SHADOW_STEM.get()))
                .save(pWriter);

        // 去皮阴影菌核 (2×2 stripped_shadow_stem → 3 stripped_shadow_hyphae)
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.STRIPPED_SHADOW_HYPHAE.get(), 3)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.STRIPPED_SHADOW_STEM.get())
                .unlockedBy(getHasName(ModItems.STRIPPED_SHADOW_STEM.get()), has(ModItems.STRIPPED_SHADOW_STEM.get()))
                .save(pWriter);

        //三种染料合成配方
        RecipeHelpers.dye(pWriter,ModItems.FERRARIA_CRISPA.get(), Items.BLACK_DYE);
        RecipeHelpers.dye(pWriter,ModItems.EUSTOMA.get(), Items.GREEN_DYE);
        RecipeHelpers.dye(pWriter,ModItems.MALVA_SINENSIS_CAVAN.get(), Items.PINK_DYE);

        //野餐篮合成配方
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PICNIC_BASKET.get(), 1)
                .pattern("aaa")
                .pattern("b b")
                .pattern("bbb")
                .define('a', Items.STICK)
                .define('b', ItemTags.PLANKS)
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.PAPER, 3)
                .pattern("   ")
                .pattern("aaa")
                .pattern("   ")
                .define('a', ModItems.REED_ROD.get())
                .unlockedBy(getHasName(ModItems.REED_ROD.get()), has(ModItems.REED_ROD.get()))
                .save(pWriter);

        // 鲜红露滴合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RED_DEW.get(), 1)
                .requires(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get())
                .requires(Items.GLISTERING_MELON_SLICE)
                .requires(Items.GOLDEN_APPLE)
                .requires(Items.HONEY_BOTTLE)
                .requires(Items.RED_DYE)
                .unlockedBy(getHasName(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get()),
                        has(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get()))
                .save(pWriter);

        // 羊毛 + 园艺钳 → 线
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STRING, 2)
                .requires(ItemTags.WOOL)
                .requires(ModItems.PLIERS.get())
                .unlockedBy(getHasName(ModItems.PLIERS.get()), has(ModItems.PLIERS.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "string_from_wool_and_pliers"));

        // 成熟作物 → 产物分解
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DYEDREAM_COROLLA.get(), 1)
                .requires(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get()), has(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WHITE_COROLLA.get(), 1)
                .requires(ModItems.WHITE_COROLLA_CROP_AGE_1.get())
                .unlockedBy(getHasName(ModItems.WHITE_COROLLA_CROP_AGE_1.get()), has(ModItems.WHITE_COROLLA_CROP_AGE_1.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LIGHT_BALL.get(), 1)
                .requires(ModItems.LIGHT_BALL_CROP_AGE_1.get())
                .unlockedBy(getHasName(ModItems.LIGHT_BALL_CROP_AGE_1.get()), has(ModItems.LIGHT_BALL_CROP_AGE_1.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CLOUD.get(), 5)
                .requires(ModItems.CLOUD_CROP_AGE_1.get())
                .unlockedBy(getHasName(ModItems.CLOUD_CROP_AGE_1.get()), has(ModItems.CLOUD_CROP_AGE_1.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COTTON.get(), 1)
                .requires(ModItems.COTTON_CROP_AGE_1.get())
                .unlockedBy(getHasName(ModItems.COTTON_CROP_AGE_1.get()), has(ModItems.COTTON_CROP_AGE_1.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PERGAMYN.get(), 1)
                .requires(Items.PAPER)
                .requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
                .save(pWriter);

        // 4x 笔记残页 -> 未解析的笔记
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.UNKNOWN_NOTE.get(), 1)
                .requires(ModItems.BROKEN_NOTE.get(), 4)
                .unlockedBy(getHasName(ModItems.BROKEN_NOTE.get()), has(ModItems.BROKEN_NOTE.get()))
                .save(pWriter);

        // 苍白骨针 = 骨头 + 苍白花冠
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PALE_BONENEEDLE.get(), 1)
                .requires(Items.BONE)
                .requires(ModItems.WHITE_COROLLA.get())
                .unlockedBy(getHasName(ModItems.WHITE_COROLLA.get()), has(ModItems.WHITE_COROLLA.get()))
                .save(pWriter);
        // 溯源苍白骨针 = 苍白骨针 + 末影珍珠 + 回响碎片
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ROOTS_PALE_BONENEEDLE.get(), 1)
                .requires(ModItems.PALE_BONENEEDLE.get())
                .requires(Items.ENDER_PEARL)
                .requires(Items.ECHO_SHARD)
                .unlockedBy(getHasName(ModItems.PALE_BONENEEDLE.get()), has(ModItems.PALE_BONENEEDLE.get()))
                .save(pWriter);

        // 苍白骨针护符 = 线 + 苍白骨针
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PALE_BONE_NEEDLE_TALISMAN.get(), 1)
                .pattern(" a ")
                .pattern("a a")
                .pattern(" b ")
                .define('a', Items.STRING)
                .define('b', ModItems.PALE_BONENEEDLE.get())
                .unlockedBy(getHasName(ModItems.PALE_BONENEEDLE.get()), has(ModItems.PALE_BONENEEDLE.get()))
                .save(pWriter);

        // 甜蜜的梦音乐唱片 = dyedream_dye + dyedream_alloy_nugget + soul_essence
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SWEET_DREAM_MUSIC_DISC.get(), 1)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', ModItems.DYEDREAM_DYE.get())
                .define('b', ModItems.DYEDREAM_ALLOY_NUGGET.get())
                .define('c', ModItems.SOUL_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.SOUL_ESSENCE.get()), has(ModItems.SOUL_ESSENCE.get()))
                .save(pWriter);

        // 落雪之梦音乐唱片 = snowball + dyedream_alloy_nugget + soul_essence
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SNOWFALL_DREAM_MUSIC_DISC.get(), 1)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', Items.SNOWBALL)
                .define('b', ModItems.DYEDREAM_ALLOY_NUGGET.get())
                .define('c', ModItems.SOUL_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.SOUL_ESSENCE.get()), has(ModItems.SOUL_ESSENCE.get()))
                .save(pWriter);

        // 染梦书桌
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DYEDREAM_DESK.get(), 1)
                .pattern(" a ")
                .pattern("bbb")
                .pattern(" c ")
                .define('a', Items.BOOK)
                .define('b', ModItems.DYEDREAM_SLAB.get())
                .define('c', Items.CALCITE)
                .unlockedBy(getHasName(ModItems.DYEDREAM_PLANKS.get()), has(ModItems.DYEDREAM_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DYEDREAM_DESK.get(), 1)
                .pattern("aaa")
                .pattern("aba")
                .pattern("aaa")
                .define('a', ModItems.DYEDREAM_DYE.get())
                .define('b', Items.LECTERN)
                .unlockedBy(getHasName(ModItems.DYEDREAM_DYE.get()), has(ModItems.DYEDREAM_DYE.get()))
                .save(pWriter, "dyedream_desk_from_lectern");
        // 研究台
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RESEARCH_TABLE.get(), 1)
                .pattern("abc")
                .pattern("ddd")
                .pattern("e f")
                .define('a', Ingredient.of(ItemTags.create(
                        ResourceLocation.fromNamespaceAndPath("forge", "bookshelves"))))
                .define('b', ItemTags.WOOL_CARPETS)
                .define('c', ModItems.PERGAMYN.get())
                .define('d', ItemTags.LOGS)
                .define('e', Ingredient.of(ItemTags.create(
                        ResourceLocation.fromNamespaceAndPath("forge", "chests"))))
                .define('f', Items.CARTOGRAPHY_TABLE)
                .unlockedBy(getHasName(ModItems.DREAM_NOTES_BOOK.get()), has(ModItems.DREAM_NOTES_BOOK.get()))
                .save(pWriter);

        // 旧梦归引宝典 = 书 + 染梦果（使用帕秋莉 shapeless_book_recipe）
        pWriter.accept(new FinishedRecipe() {
            @Override
            public void serializeRecipeData(JsonObject json) {
                json.addProperty("book", "pasterdream:seniors_dream");
                JsonArray ingredients = new JsonArray();
                JsonObject book = new JsonObject();
                book.addProperty("item", "minecraft:book");
                ingredients.add(book);
                JsonObject fruit = new JsonObject();
                fruit.addProperty("item", "pasterdream:dyedream_fruit");
                ingredients.add(fruit);
                json.add("ingredients", ingredients);
            }
            @Override
            public ResourceLocation getId() {
                return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, getItemName(ModItems.SENIORS_DREAM_BOOK.get()));
            }
            @Override
            public RecipeSerializer<?> getType() {
                return BuiltInRegistries.RECIPE_SERIALIZER.get(ResourceLocation.fromNamespaceAndPath("patchouli", "shapeless_book_recipe"));
            }
            @Override
            public JsonObject serializeAdvancement() { return null; }
            @Override
            public ResourceLocation getAdvancementId() { return null; }
        });

    }

    // ===== 饰品配方 =====
    private void curioRecipes(Consumer<FinishedRecipe> pWriter) {
        // 护身符原胚 = 线 + 灵魂精华 + 钛金粒
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EMBRYO_CHARM.get(), 1)
                .pattern("a")
                .pattern("b")
                .pattern("c")
                .define('a', Items.STRING)
                .define('b', ModItems.SOUL_ESSENCE.get())
                .define('c', ModItems.TITANIUM_NUGGET.get())
                .unlockedBy(getHasName(ModItems.SOUL_ESSENCE.get()), has(ModItems.SOUL_ESSENCE.get()))
                .save(pWriter);

        // 项链原胚 = 钛金粒 ×5 + 灵魂精华
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EMBRYO_NECKLACE.get(), 1)
                .pattern("aaa")
                .pattern("a a")
                .pattern(" b ")
                .define('a', ModItems.TITANIUM_NUGGET.get())
                .define('b', ModItems.SOUL_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.SOUL_ESSENCE.get()), has(ModItems.SOUL_ESSENCE.get()))
                .save(pWriter);

        // 戒指原胚 = 钛金粒 ×3 + 灵魂精华
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EMBRYO_RING.get(), 1)
                .pattern(" a ")
                .pattern("b b")
                .pattern(" b ")
                .define('a', ModItems.SOUL_ESSENCE.get())
                .define('b', ModItems.TITANIUM_NUGGET.get())
                .unlockedBy(getHasName(ModItems.SOUL_ESSENCE.get()), has(ModItems.SOUL_ESSENCE.get()))
                .save(pWriter);

        // 腰带原胚 = 钛金粒 ×5 + 灵魂精华
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EMBRYO_BELT.get(), 1)
                .pattern("aaa")
                .pattern("aba")
                .define('a', ModItems.TITANIUM_NUGGET.get())
                .define('b', ModItems.SOUL_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.SOUL_ESSENCE.get()), has(ModItems.SOUL_ESSENCE.get()))
                .save(pWriter);

        // 龟壳护身符 = 海草 + 鳞甲 + 护身符原胚
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CARAPAX_CHARM.get(), 1)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', Items.SEAGRASS)
                .define('b', Items.SCUTE)
                .define('c', ModItems.EMBRYO_CHARM.get())
                .unlockedBy(getHasName(ModItems.EMBRYO_CHARM.get()), has(ModItems.EMBRYO_CHARM.get()))
                .save(pWriter);

        // 海波护身符 = 水瓶玻璃罐 + 青金石 + 护身符原胚 + 海洋之心
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SEA_CHARM.get(), 1)
                .pattern("aba")
                .pattern("bcb")
                .pattern("ada")
                .define('a', ModItems.GLASS_JAR_OF_WATER.get())
                .define('b', Items.LAPIS_LAZULI)
                .define('c', ModItems.EMBRYO_CHARM.get())
                .define('d', Items.HEART_OF_THE_SEA)
                .unlockedBy(getHasName(ModItems.EMBRYO_CHARM.get()), has(ModItems.EMBRYO_CHARM.get()))
                .save(pWriter);

        // 邪眼护身符 = 护身符原胚 + 末影之眼 + 紫颂花
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ENDEYE_CHARM.get(), 1)
                .requires(ModItems.EMBRYO_CHARM.get())
                .requires(Items.ENDER_EYE)
                .requires(Items.CHORUS_FLOWER)
                .unlockedBy(getHasName(ModItems.EMBRYO_CHARM.get()), has(ModItems.EMBRYO_CHARM.get()))
                .save(pWriter);

        // 纯金护身符 = 护身符原胚 + 金锭
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GOLD_CHARM.get(), 1)
                .requires(ModItems.EMBRYO_CHARM.get())
                .requires(Items.GOLD_INGOT)
                .unlockedBy(getHasName(ModItems.EMBRYO_CHARM.get()), has(ModItems.EMBRYO_CHARM.get()))
                .save(pWriter);

        // 业火项链 = 项链原胚 + 炙焰金锭 + 烈焰棒 + 烈焰花
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FIRE_NECKLACE.get(), 1)
                .requires(ModItems.EMBRYO_NECKLACE.get())
                .requires(ModItems.MOLTEN_GOLD_INGOT.get())
                .requires(Items.BLAZE_ROD)
                .requires(ModItems.BLAZE_FLOWER.get())
                .unlockedBy(getHasName(ModItems.EMBRYO_NECKLACE.get()), has(ModItems.EMBRYO_NECKLACE.get()))
                .save(pWriter);

        // 生命项链 = 项链原胚 + 金锭 + 鲜红露滴 + 粉心形巧克力 + 染梦染料
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.HEALTH_NECKLACE.get(), 1)
                .requires(ModItems.EMBRYO_NECKLACE.get())
                .requires(Items.GOLD_INGOT)
                .requires(ModItems.RED_DEW.get())
                .requires(ModItems.PINK_HEART_CHOCOLATE.get())
                .requires(ModItems.DYEDREAM_DYE.get())
                .unlockedBy(getHasName(ModItems.EMBRYO_NECKLACE.get()), has(ModItems.EMBRYO_NECKLACE.get()))
                .save(pWriter);

        // 雪兔脚项链 = 项链原胚 + 兔子脚 + 细雪桶 + 钻石 + 下界疣
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RABBIT_FOOT_NECKLACE.get(), 1)
                .requires(ModItems.EMBRYO_NECKLACE.get())
                .requires(Items.RABBIT_FOOT)
                .requires(Items.POWDER_SNOW_BUCKET)
                .requires(Items.DIAMOND)
                .requires(Items.NETHER_WART)
                .unlockedBy(getHasName(ModItems.EMBRYO_NECKLACE.get()), has(ModItems.EMBRYO_NECKLACE.get()))
                .save(pWriter);

        // 花环 = 小花 ×8
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GARLAND.get(), 1)
                .pattern("aaa")
                .pattern("a a")
                .pattern("aaa")
                .define('a', Ingredient.of(ItemTags.SMALL_FLOWERS))
                .unlockedBy(getHasName(Items.POPPY), has(ItemTags.SMALL_FLOWERS))
                .save(pWriter);

        // 自然腰带 = 发光浆果 + 藤蔓
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NATURE_BELT.get(), 1)
                .pattern("aba")
                .pattern("b b")
                .pattern("aba")
                .define('a', Items.GLOW_BERRIES)
                .define('b', Items.VINE)
                .unlockedBy(getHasName(Items.GLOW_BERRIES), has(Items.GLOW_BERRIES))
                .save(pWriter);

        // 旅者腰带 = 腰带原胚 + 织物 + 皮革 + 钻石
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TRAVELER_BELT.get(), 1)
                .pattern(" a ")
                .pattern("bcb")
                .pattern(" d ")
                .define('a', ModItems.FABRIC.get())
                .define('b', Items.LEATHER)
                .define('c', ModItems.EMBRYO_BELT.get())
                .define('d', Items.DIAMOND)
                .unlockedBy(getHasName(ModItems.EMBRYO_BELT.get()), has(ModItems.EMBRYO_BELT.get()))
                .save(pWriter);

        // 迷梦旅者腰带 = 旅者腰带 + 染梦染料 + 染梦粉尘 + 迷梦睡莲
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DREAM_TRAVELER_BELT.get(), 1)
                .pattern("aba")
                .pattern("bcb")
                .pattern("ada")
                .define('a', ModItems.DYEDREAM_DYE.get())
                .define('b', ModItems.DYEDREAM_DUST.get())
                .define('c', ModItems.TRAVELER_BELT.get())
                .define('d', ModItems.MISTY_DREAMING_LOTUS.get())
                .unlockedBy(getHasName(ModItems.TRAVELER_BELT.get()), has(ModItems.TRAVELER_BELT.get()))
                .save(pWriter);

        // 光明飞蝶 = 夜明蝶 + 白厄水晶 + 灵魂精华
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRIGHT_BUTTERFLY_CURIO.get(), 1)
                .requires(ModItems.LIGHT_BUTTERFLY_CURIO.get())
                .requires(ModItems.WHITE_CRYSTAL.get())
                .requires(ModItems.SOUL_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.LIGHT_BUTTERFLY_CURIO.get()), has(ModItems.LIGHT_BUTTERFLY_CURIO.get()))
                .save(pWriter);

        //4个红露滴戒指配方
        CompoundTag lv1Nbt = new CompoundTag();
        lv1Nbt.putInt("lv", 1);
        CompoundTag lv2Nbt = new CompoundTag();
        lv2Nbt.putInt("lv", 2);
        CompoundTag lv3Nbt = new CompoundTag();
        lv3Nbt.putInt("lv", 3);
        CompoundTag lv4Nbt = new CompoundTag();
        lv4Nbt.putInt("lv", 4);

        saveShapelessWithNbt(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RED_DEW_RING.get())
                        .requires(ModItems.EMBRYO_RING.get())
                        .requires(Items.GOLD_INGOT)
                        .requires(ModItems.RED_DEW.get())
                        .unlockedBy(getHasName(ModItems.EMBRYO_RING.get()), has(ModItems.EMBRYO_RING.get())),
                pWriter, "red_dew_ring_lv1", lv1Nbt, null);

        saveShapelessWithNbt(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RED_DEW_RING.get())
                        .requires(ModItems.RED_DEW_RING.get())
                        .requires(ModItems.DYEDREAM_DUST.get())
                        .requires(ModItems.RED_DEW.get())
                        .unlockedBy(getHasName(ModItems.RED_DEW_RING.get()), has(ModItems.RED_DEW_RING.get())),
                pWriter, "red_dew_ring_lv2", lv2Nbt, Map.of(0, lv1Nbt));

        saveShapelessWithNbt(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RED_DEW_RING.get())
                        .requires(ModItems.RED_DEW_RING.get())
                        .requires(ModItems.DYEDREAM_DUST.get())
                        .requires(ModItems.RED_DEW.get())
                        .requires(ModItems.MELT_DREAM_LIQUID_BUCKET.get())
                        .unlockedBy(getHasName(ModItems.RED_DEW_RING.get()), has(ModItems.RED_DEW_RING.get())),
                pWriter, "red_dew_ring_lv3", lv3Nbt, Map.of(0, lv2Nbt));

        saveShapelessWithNbt(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RED_DEW_RING.get())
                        .requires(ModItems.RED_DEW_RING.get())
                        .requires(ModItems.DYEDREAM_DUST.get())
                        .requires(ModItems.RED_DEW.get())
                        .requires(ModItems.LIFE_CRYSTAL.get())
                        .unlockedBy(getHasName(ModItems.RED_DEW_RING.get()), has(ModItems.RED_DEW_RING.get())),
                pWriter, "red_dew_ring_lv4", lv4Nbt, Map.of(0, lv3Nbt));

        //2个强击戒指配方
        saveShapedWithNbt(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STRIKE_RING.get(), 1)
                .pattern(" c ")
                .pattern("bab")
                .pattern(" b ")
                .define('a', ModItems.EMBRYO_RING.get())
                .define('b', Items.COPPER_INGOT)
                .define('c', Items.BLAZE_POWDER)
                .unlockedBy(getHasName(ModItems.EMBRYO_RING.get()), has(ModItems.EMBRYO_RING.get())),
                pWriter, "strike_ring_lv1", lv1Nbt, null);

        saveShapedWithNbt(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STRIKE_RING.get(), 1)
                        .pattern("cbc")
                        .pattern("bab")
                        .pattern("cbc")
                        .define('a', ModItems.STRIKE_RING.get())
                        .define('b', ModItems.MOLTEN_GOLD_INGOT.get())
                        .define('c', Items.BLAZE_ROD)
                        .unlockedBy(getHasName(ModItems.STRIKE_RING.get()), has(ModItems.STRIKE_RING.get())),
                pWriter, "strike_ring_lv2", lv2Nbt, Map.of('a', lv1Nbt));

        // 融梦光环戒指 = 融梦水晶碎片 + 染梦合金粒 + 戒指原胚
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MELT_DREAM_ENERGY_RING.get(), 1)
                .pattern(" a ")
                .pattern("bcb")
                .pattern(" b ")
                .define('a', ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get())
                .define('b', ModItems.DYEDREAM_ALLOY_NUGGET.get())
                .define('c', ModItems.EMBRYO_RING.get())
                .unlockedBy(getHasName(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()),
                        has(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                        "melt_dream_energy_ring_from_embryo"));

        // ===== 金狐狸交易配方 =====
        saveGoldenFoxTrade(pWriter, Ingredient.of(Items.GOLDEN_APPLE), new ItemStack(Items.ENCHANTED_GOLDEN_APPLE),
                "golden_fox_trade_golden_apple");
        saveGoldenFoxTrade(pWriter, Ingredient.of(Items.BUCKET),
                new ItemStack(ModItems.MELT_DREAM_LIQUID_BUCKET.get()), "golden_fox_trade_bucket");
        saveGoldenFoxTrade(pWriter, Ingredient.of(ModItems.MELT_DREAM_COIN_PILE.get()),
                DeepTreasureItem.createSuper(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get(), 3),
                "golden_fox_trade_coin_pile");
        saveGoldenFoxTrade(pWriter, Ingredient.of(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()),
                new ItemStack(ModItems.KAICHU_OMAMORI.get()), "golden_fox_trade_crystal_fragment");

        // ===== 你把暗影之手扔熔炉有什么意义吗？ =====
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.SHADOW_HAND_TRAP.get()),
                        RecipeCategory.MISC, ModItems.SHADOW.get(), 0.35F, 200)
                .unlockedBy(getHasName(ModBlocks.SHADOW_HAND_TRAP.get()), has(ModBlocks.SHADOW_HAND_TRAP.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":shadow_from_shadow_hand_trap");

    }

    // ===== 容器配平校验 =====

    /**
     * 校验所有通过 {@link #saveContainerBalancedShapeless} / {@link #saveContainerBalancedShaped}
     * 注册的容器配方是否能配平。各配方在构建时已自动加入校验列表，此处统一执行。
     */
    private void validateContainerBalance() {
        for (Runnable validation : containerValidations) {
            validation.run();
        }
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

    private void saveGoldenFoxTrade(Consumer<FinishedRecipe> writer, Ingredient ingredient, ItemStack result, String name) {
        writer.accept(new FinishedRecipe() {
            @Override
            public void serializeRecipeData(JsonObject json) {
                json.add("ingredient", ingredient.toJson());
                JsonObject resultObj = new JsonObject();
                resultObj.addProperty("item", result.getItem().builtInRegistryHolder().key().location().toString());
                resultObj.addProperty("count", result.getCount());
                if (result.hasTag())
                    resultObj.addProperty("nbt", result.getTag().toString());
                json.add("result", resultObj);
            }

            @Override
            public ResourceLocation getId() {
                return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name);
            }

            @Override
            public RecipeSerializer<?> getType() {
                return ModRecipes.GOLDEN_FOX_TRADE_SERIALIZER.get();
            }

            @Override
            public JsonObject serializeAdvancement() {
                return null;
            }

            @Override
            public ResourceLocation getAdvancementId() {
                return null;
            }
        });
    }
}
