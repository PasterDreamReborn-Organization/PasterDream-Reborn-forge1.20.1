package com.pasterdream.pasterdreammod.compat.jei;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.compat.jei.claypanrecipe.ClaypanJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.claypanrecipe.ClaypanRecipeCategory;
import com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe.DreamCauldronJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe.DreamCauldronRecipeCategory;
import com.pasterdream.pasterdreammod.compat.jei.mortarrecipe.MortarJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.mortarrecipe.MortarRecipeCategory;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanRecipe;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanScreen;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronRecipe;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarRecipe;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
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
        }
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration)
    {
        registration.addRecipeClickArea(ClaypanScreen.class, 74, 7, 22, 15, ClaypanRecipeCategory.CLAYPAN_RECIPE_TYPE);
        registration.addRecipeClickArea(MortarScreen.class, 26, 43, 15, 22, MortarRecipeCategory.MORTAR_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CLAYPAN.get()), ClaypanRecipeCategory.CLAYPAN_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DREAM_CAULDRON.get()), DreamCauldronRecipeCategory.DREAM_CAULDRON_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.MORTAR.get()), MortarRecipeCategory.MORTAR_RECIPE_TYPE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        registration.addRecipeCategories(new ClaypanRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DreamCauldronRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MortarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
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

        registration.addExtraIngredients(ForgeTypes.FLUID_STACK, fluidStacks);
    }
}
