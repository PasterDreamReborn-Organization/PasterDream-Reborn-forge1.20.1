package com.pasterdream.pasterdreammod.compat.jei;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.compat.jei.brewingrecipe.FortuneJellyJeiBrewingRecipe;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import com.pasterdream.pasterdreammod.compat.jei.claypanrecipe.ClaypanJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.claypanrecipe.ClaypanRecipeCategory;
import com.pasterdream.pasterdreammod.compat.jei.dreamaccumulatorrecipe.DreamAccumulatorJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.dreamaccumulatorrecipe.DreamAccumulatorRecipeCategory;
import com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe.DreamCauldronJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe.DreamCauldronRecipeCategory;
import com.pasterdream.pasterdreammod.compat.jei.mortarrecipe.MortarJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.mortarrecipe.MortarRecipeCategory;
import com.pasterdream.pasterdreammod.compat.jei.researchtablecopy.ResearchTableCopyJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.researchtablecopy.ResearchTableCopyRecipeCategory;
import com.pasterdream.pasterdreammod.compat.jei.researchtableresearch.ResearchTableResearchJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.researchtableresearch.ResearchTableResearchRecipeCategory;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModPotions;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanRecipe;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanScreen;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorRecipe;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorScreen;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronRecipe;
import com.pasterdream.pasterdreammod.world.block.researchtable.ResearchTableCopyRecipe;
import com.pasterdream.pasterdreammod.world.block.researchtable.ResearchTableResearchRecipe;
import com.pasterdream.pasterdreammod.world.item.curio.RedDewRingItem;
import com.pasterdream.pasterdreammod.world.item.curio.StrikeRingItem;
import com.pasterdream.pasterdreammod.world.item.lootgenerator.LootGeneratorItem;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarRecipe;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarScreen;
import com.pasterdream.pasterdreammod.world.item.prophecycard.ProphecyCardItem;
import com.pasterdream.pasterdreammod.world.item.PotionBottleItem;
import com.pasterdream.pasterdreammod.world.item.PotionBottleRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.fluids.FluidStack;


import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class ModJEIPlugin implements IModPlugin
{
    @Override
    public ResourceLocation getPluginUid()
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null)
        {
            RecipeManager recipeManager = level.getRecipeManager();

            List<ClaypanRecipe> claypanRecipes = recipeManager.getAllRecipesFor(ModRecipes.CLAYPAN.get());
            registration.addRecipes(ClaypanRecipeCategory.CLAYPAN_RECIPE_TYPE, claypanRecipes.stream().map(ClaypanJEIRecipe::new).collect(Collectors.toList()));

            List<DreamCauldronRecipe> dreamCauldronRecipes = recipeManager.getAllRecipesFor(ModRecipes.DREAM_CAULDRON.get());
            registration.addRecipes(DreamCauldronRecipeCategory.DREAM_CAULDRON_RECIPE_TYPE, dreamCauldronRecipes.stream().map(DreamCauldronJEIRecipe::new).collect(Collectors.toList()));

            List<MortarRecipe> mortarRecipes = recipeManager.getAllRecipesFor(ModRecipes.MORTAR.get());
            registration.addRecipes(MortarRecipeCategory.MORTAR_RECIPE_TYPE, mortarRecipes.stream().map(MortarJEIRecipe::new).collect(Collectors.toList()));

            List<ResearchTableCopyRecipe> researchTableCopyRecipes = recipeManager.getAllRecipesFor(ModRecipes.RESEARCH_TABLE_COPY.get());
            registration.addRecipes(ResearchTableCopyRecipeCategory.RESEARCH_TABLE_COPY_RECIPE_TYPE, researchTableCopyRecipes.stream().map(ResearchTableCopyJEIRecipe::new).collect(Collectors.toList()));

            List<ResearchTableResearchRecipe> researchTableResearchRecipes = recipeManager.getAllRecipesFor(ModRecipes.RESEARCH_TABLE_RESEARCH.get());
            registration.addRecipes(ResearchTableResearchRecipeCategory.RESEARCH_TABLE_RESEARCH_RECIPE_TYPE, researchTableResearchRecipes.stream().map(ResearchTableResearchJEIRecipe::new).collect(Collectors.toList()));

            List<DreamAccumulatorRecipe> dreamAccumulatorRecipes = recipeManager.getAllRecipesFor(ModRecipes.DREAM_ACCUMULATOR.get());
            registration.addRecipes(DreamAccumulatorRecipeCategory.DREAM_ACCUMULATOR_RECIPE_TYPE, dreamAccumulatorRecipes.stream().map(DreamAccumulatorJEIRecipe::new).collect(Collectors.toList()));

            // ===== 幸运药水酿造配方（原版样式，每步独立注册）=====
            Item[] potionTypes = {Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION};
            List<IJeiBrewingRecipe> brewingRecipes = new ArrayList<>();
            for (Item type : potionTypes) {
                // 福灵果冻：粗制药水 → 幸运药水
                brewingRecipes.add(new FortuneJellyJeiBrewingRecipe(
                        List.of(PotionUtils.setPotion(new ItemStack(type), Potions.AWKWARD)),
                        List.of(new ItemStack(ModItems.FORTUNE_JELLY.get())),
                        PotionUtils.setPotion(new ItemStack(type), ModPotions.LUCK.get()),
                        1
                ));
                // 荧石粉：幸运药水 → 幸运药水 II
                brewingRecipes.add(new FortuneJellyJeiBrewingRecipe(
                        List.of(PotionUtils.setPotion(new ItemStack(type), ModPotions.LUCK.get())),
                        List.of(new ItemStack(Items.GLOWSTONE_DUST)),
                        PotionUtils.setPotion(new ItemStack(type), ModPotions.STRONG_LUCK.get()),
                        1
                ));
                // 红石粉：幸运药水 → 长效幸运药水
                brewingRecipes.add(new FortuneJellyJeiBrewingRecipe(
                        List.of(PotionUtils.setPotion(new ItemStack(type), ModPotions.LUCK.get())),
                        List.of(new ItemStack(Items.REDSTONE)),
                        PotionUtils.setPotion(new ItemStack(type), ModPotions.LONG_LUCK.get()),
                        1
                ));
            }
            registration.addRecipes(RecipeTypes.BREWING, brewingRecipes);
        }
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration)
    {
        registration.addRecipeClickArea(ClaypanScreen.class, 74, 7, 22, 15, ClaypanRecipeCategory.CLAYPAN_RECIPE_TYPE);
        registration.addRecipeClickArea(MortarScreen.class, 26, 43, 15, 22, MortarRecipeCategory.MORTAR_RECIPE_TYPE);
        registration.addRecipeClickArea(DreamAccumulatorScreen.class, 75, 27, 14, 29, DreamAccumulatorRecipeCategory.DREAM_ACCUMULATOR_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CLAYPAN.get()), ClaypanRecipeCategory.CLAYPAN_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DREAM_CAULDRON.get()), DreamCauldronRecipeCategory.DREAM_CAULDRON_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.MORTAR.get()), MortarRecipeCategory.MORTAR_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.RESEARCH_TABLE.get()), ResearchTableCopyRecipeCategory.RESEARCH_TABLE_COPY_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.RESEARCH_TABLE.get()), ResearchTableResearchRecipeCategory.RESEARCH_TABLE_RESEARCH_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DREAM_ACCUMULATOR.get()), DreamAccumulatorRecipeCategory.DREAM_ACCUMULATOR_RECIPE_TYPE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        registration.addRecipeCategories(new ClaypanRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DreamCauldronRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MortarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ResearchTableCopyRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ResearchTableResearchRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DreamAccumulatorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration)
    {
        registration.registerSubtypeInterpreter(
                ModItems.RED_DEW_RING.get(),
                (stack, context) -> String.valueOf(RedDewRingItem.getLv(stack)));

        registration.registerSubtypeInterpreter(
                ModItems.STRIKE_RING.get(),
                (stack, context) -> String.valueOf(StrikeRingItem.getLv(stack)));

        registration.registerSubtypeInterpreter(
                ModItems.LOOT_GENERATOR.get(),
                (stack, context) -> {
                    ResourceLocation table = LootGeneratorItem.getToolLootTable(stack);
                    return table != null ? table.toString() : IIngredientSubtypeInterpreter.NONE;
                });

        registration.registerSubtypeInterpreter(
                ModItems.PROPHECY_CARD.get(),
                (stack, context) -> ProphecyCardItem.getType(stack));

        registration.registerSubtypeInterpreter(
                ModItems.EMPTY_PROPHECY_CARD.get(),
                (stack, context) -> IIngredientSubtypeInterpreter.NONE);

        registration.registerSubtypeInterpreter(
                PotionBottleRegistry.POTION_BOTTLE.get(),
                (stack, context) -> PotionBottleItem.getPotionType(stack));
    }

    //将流体添加至JEI物品列表
    @Override
    public void registerExtraIngredients(IExtraIngredientRegistration registration)
    {
        List<FluidStack> fluidStacks = new ArrayList<>();

        fluidStacks.add(new FluidStack(ModFluids.FLOWING_MELT_DREAM_LIQUID.get(), 1000));
        fluidStacks.add(new FluidStack(ModFluids.FLOWING_SHADOW_LIQUID.get(), 1000));

        fluidStacks.add(new FluidStack(ModFluids.APPLE_JUICE.get(), 1000));
        fluidStacks.add(new FluidStack(ModFluids.DREAM_JUICE.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.DYEDREAM_FLOWER_TEA.get(), 1000));
        fluidStacks.add(new FluidStack(ModFluids.DYEDREAM_JUICE.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.DYEDREAM_PERFUME.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.GOLDENROD_TEA.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.GUIDING_DRUG.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.HONEY_JUICE.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.RAGE_ELIXIR.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.UNCOOKED_DYEDREAM_FLOWER_TEA.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.WATERMELON_JUICE.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.WIND_PLANT_EXTRACT.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.YEAST.get(),1000));
        fluidStacks.add(new FluidStack(ModFluids.INK.get(), 1000));

        registration.addExtraIngredients(ForgeTypes.FLUID_STACK, fluidStacks);
    }
}
