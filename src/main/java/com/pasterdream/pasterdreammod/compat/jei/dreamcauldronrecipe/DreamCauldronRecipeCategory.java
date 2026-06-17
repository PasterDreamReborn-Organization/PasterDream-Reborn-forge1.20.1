package com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.fluidingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class DreamCauldronRecipeCategory implements IRecipeCategory<DreamCauldronJEIRecipe>
{
    public static final RecipeType<DreamCauldronJEIRecipe> DREAM_CAULDRON_RECIPE_TYPE =   new RecipeType<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_cauldron"), DreamCauldronJEIRecipe.class);
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_cauldron/dream_cauldron.png");
    private final IDrawable background;
    private final IDrawable icon;

    public DreamCauldronRecipeCategory(IGuiHelper guiHelper)
    {
        this.background = guiHelper.drawableBuilder(TEXTURE, 0, 0, 171, 93).setTextureSize(171, 93).build();
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.DREAM_CAULDRON.get()));
    }

    @Override
    public RecipeType<DreamCauldronJEIRecipe> getRecipeType()
    {
        return DREAM_CAULDRON_RECIPE_TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".dream_cauldron");
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DreamCauldronJEIRecipe recipe, IFocusGroup focuses)
    {
        IRecipeSlotBuilder fluidSlot0 = builder.addSlot(RecipeIngredientRole.INPUT, 154, 19).setFluidRenderer(1000, false, 16, 16);
        FluidIngredient fluid0Ingredient = recipe.getFluidIngredients().get(0);
        if (fluid0Ingredient.getFluid() != null)
        {
            fluidSlot0.addFluidStack(fluid0Ingredient.getFluid(), fluid0Ingredient.getAmount());
        }
        else
            if (fluid0Ingredient.getTag() != null)
            {
                var tag = ForgeRegistries.FLUIDS.tags().getTag(fluid0Ingredient.getTag());
                if (tag != null)
                {
                    for (Fluid fluid : tag)
                    {
                        fluidSlot0.addFluidStack(fluid, fluid0Ingredient.getAmount());
                    }
                }
            }

        IRecipeSlotBuilder fluidSlot1 = builder.addSlot(RecipeIngredientRole.INPUT, 1, 46).setFluidRenderer(1000, false, 16, 16);
        FluidIngredient fluid1Ingredient = recipe.getFluidIngredients().get(1);
        if (fluid1Ingredient.getFluid() != null)
        {
            fluidSlot1.addFluidStack(fluid1Ingredient.getFluid(), fluid1Ingredient.getAmount());
        }
        else
            if (fluid1Ingredient.getTag() != null)
            {
                var tag = ForgeRegistries.FLUIDS.tags().getTag(fluid1Ingredient.getTag());
                if (tag != null)
                {
                    for (Fluid fluid : tag)
                    {
                        fluidSlot1.addFluidStack(fluid, fluid1Ingredient.getAmount());
                    }
                }
            }


        int itemX = 45, itemY = 15;
        for (Ingredient ingredient : recipe.getItemIngredients())
        {
            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.INPUT, itemX, itemY);
            for (ItemStack stack : ingredient.getItems())
            {
                slot.addItemStack(stack);
            }
            itemX += 28;
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 73, 46).addItemStack(recipe.getOutput());
    }

    @Override
    public void draw(DreamCauldronJEIRecipe recipe, IRecipeSlotsView slotsView, GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        background.draw(guiGraphics);
    }

    @Override
    public int getWidth()
    {
        return 171;
    }

    @Override
    public int getHeight()
    {
        return 93;
    }
}
