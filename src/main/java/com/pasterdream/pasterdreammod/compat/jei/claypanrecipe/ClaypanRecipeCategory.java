package com.pasterdream.pasterdreammod.compat.jei.claypanrecipe;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.recipe.claypan.IClaypanRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ClaypanRecipeCategory implements IRecipeCategory<IClaypanRecipe>
{
    public static final RecipeType<IClaypanRecipe> CLAYPAN_RECIPE_TYPE = new RecipeType<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "claypan"), IClaypanRecipe.class);
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/claypan/claypan.png");
    private final IDrawable background;
    private final IDrawable icon;

    public ClaypanRecipeCategory(IGuiHelper helper)
    {
        background = helper.drawableBuilder(TEXTURE, 0, 0, 82, 28).setTextureSize(82, 28).build();
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CLAYPAN.get()));
    }

    @Override
    public RecipeType<IClaypanRecipe> getRecipeType()
    {
        return CLAYPAN_RECIPE_TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".claypan");
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public int getWidth()
    {
        return 82;
    }

    @Override
    public int getHeight()
    {
        return 28;
    }

    @Override
    public void draw(IClaypanRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        int time = recipe.getProcessingTime();
        background.draw(guiGraphics);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, String.format("%02d", (time / 72000)) + "h" + String.format("%02d", ((time % 72000) / 1200)) + "m" + String.format("%02d", ((time % 1200) / 20)) + "s" + String.format("%02d", (time % 20)) + "tick", 41, 34, 0xFFFFFFFF);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IClaypanRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 6, 6).addFluidStack(recipe.getFluidInput().getFluid(), recipe.getFluidInput().getAmount()).setFluidRenderer(recipe.getFluidInput().getAmount(), false, 16, 16);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 60, 6).addItemStack(recipe.getResultItem(null));
    }
}
