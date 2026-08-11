package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanRecipe;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanRecipeSerializer;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorRecipe;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorRecipeSerializer;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronRecipe;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronRecipeSerializer;
import com.pasterdream.pasterdreammod.recipe.GoldenFoxTradeRecipe;
import com.pasterdream.pasterdreammod.recipe.GoldenFoxTradeRecipeSerializer;
import com.pasterdream.pasterdreammod.recipe.NbtPreservingShapedRecipe;
import com.pasterdream.pasterdreammod.recipe.NbtPreservingShapelessRecipe;
import com.pasterdream.pasterdreammod.world.block.researchtable.ResearchTableCopyRecipe;
import com.pasterdream.pasterdreammod.world.block.researchtable.ResearchTableCopyRecipeSerializer;
import com.pasterdream.pasterdreammod.world.block.researchtable.ResearchTableResearchRecipe;
import com.pasterdream.pasterdreammod.world.block.researchtable.ResearchTableResearchRecipeSerializer;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceRecipe;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceRecipeSerializer;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarCraftingRecipe;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarRecipe;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes
{
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PasterDreamMod.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, PasterDreamMod.MOD_ID);

    public static final RegistryObject<RecipeType<ClaypanRecipe>> CLAYPAN = RECIPE_TYPES.register("claypan", () -> new RecipeType<>() {});
    public static final RegistryObject<RecipeType<DreamCauldronRecipe>> DREAM_CAULDRON = RECIPE_TYPES.register("dream_cauldron", () -> new RecipeType<>() {});
    public static final RegistryObject<RecipeType<MortarRecipe>> MORTAR = RECIPE_TYPES.register("mortar", () -> new RecipeType<>() {});
    public static final RegistryObject<RecipeType<ResearchTableCopyRecipe>> RESEARCH_TABLE_COPY = RECIPE_TYPES.register("research_table_copy", () -> new RecipeType<>() {});
    public static final RegistryObject<RecipeType<ResearchTableResearchRecipe>> RESEARCH_TABLE_RESEARCH = RECIPE_TYPES.register("research_table_research", () -> new RecipeType<>() {});
    public static final RegistryObject<RecipeType<DreamAccumulatorRecipe>> DREAM_ACCUMULATOR = RECIPE_TYPES.register("dream_accumulator", () -> new RecipeType<>() {});
    public static final RegistryObject<RecipeType<GoldenFoxTradeRecipe>> GOLDEN_FOX_TRADE = RECIPE_TYPES.register("golden_fox_trade", () -> new RecipeType<>() {});
    public static final RegistryObject<RecipeType<ShadowBlastFurnaceRecipe>> SHADOW_BLAST_FURNACE = RECIPE_TYPES.register("shadow_blast_furnace", () -> new RecipeType<>() {});

    public static final RegistryObject<RecipeSerializer<ClaypanRecipe>> CLAYPAN_SERIALIZER = RECIPE_SERIALIZERS.register("claypan", ClaypanRecipeSerializer::new);
    public static final RegistryObject<DreamCauldronRecipeSerializer> DREAM_CAULDRON_SERIALIZER = RECIPE_SERIALIZERS.register("dream_cauldron", DreamCauldronRecipeSerializer::new);
    public static final RegistryObject<MortarRecipeSerializer> MORTAR_SERIALIZER = RECIPE_SERIALIZERS.register("mortar", MortarRecipeSerializer::new);
    public static final RegistryObject<ResearchTableCopyRecipeSerializer> RESEARCH_TABLE_COPY_SERIALIZER = RECIPE_SERIALIZERS.register("research_table_copy", ResearchTableCopyRecipeSerializer::new);
    public static final RegistryObject<ResearchTableResearchRecipeSerializer> RESEARCH_TABLE_RESEARCH_SERIALIZER = RECIPE_SERIALIZERS.register("research_table_research", ResearchTableResearchRecipeSerializer::new);
    public static final RegistryObject<DreamAccumulatorRecipeSerializer> DREAM_ACCUMULATOR_SERIALIZER = RECIPE_SERIALIZERS.register("dream_accumulator", DreamAccumulatorRecipeSerializer::new);
    public static final RegistryObject<ShadowBlastFurnaceRecipeSerializer> SHADOW_BLAST_FURNACE_SERIALIZER = RECIPE_SERIALIZERS.register("shadow_blast_furnace", ShadowBlastFurnaceRecipeSerializer::new);

    public static final RegistryObject<MortarCraftingRecipe.Serializer> MORTAR_CRAFTING_SERIALIZER = RECIPE_SERIALIZERS.register("mortar_crafting", MortarCraftingRecipe.Serializer::new);
    public static final RegistryObject<NbtPreservingShapelessRecipe.Serializer> NBT_PRESERVING_SHAPELESS_SERIALIZER = RECIPE_SERIALIZERS.register("nbt_preserving_shapeless", NbtPreservingShapelessRecipe.Serializer::new);
    public static final RegistryObject<NbtPreservingShapedRecipe.Serializer> NBT_PRESERVING_SHAPED_SERIALIZER = RECIPE_SERIALIZERS.register("nbt_preserving_shaped", NbtPreservingShapedRecipe.Serializer::new);
    public static final RegistryObject<GoldenFoxTradeRecipeSerializer> GOLDEN_FOX_TRADE_SERIALIZER = RECIPE_SERIALIZERS.register("golden_fox_trade", GoldenFoxTradeRecipeSerializer::new);

    public static void register(IEventBus eventBus)
    {
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
