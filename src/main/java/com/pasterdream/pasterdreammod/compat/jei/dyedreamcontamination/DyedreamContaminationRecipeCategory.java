package com.pasterdream.pasterdreammod.compat.jei.dyedreamcontamination;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
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

/**
 * 染梦侵染 JEI 分类，布局为：原方块 → 传送门方块图标 → 产物。
 */
public class DyedreamContaminationRecipeCategory implements IRecipeCategory<DyedreamContaminationJEIRecipe> {
    public static final RecipeType<DyedreamContaminationJEIRecipe> RECIPE_TYPE =
            new RecipeType<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_contamination"),
                    DyedreamContaminationJEIRecipe.class);

    private final IDrawable icon;
    private final IDrawable arrow;

    public DyedreamContaminationRecipeCategory(IGuiHelper helper) {
        this.icon = new BlockStateDrawable(ModBlocks.DYEDREAM_WORLD_PORTAL.get().defaultBlockState(), 1.5F);
        this.arrow = helper.getRecipeArrow();
    }

    @Override
    public RecipeType<DyedreamContaminationJEIRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.pasterdream.dyedream_contamination");
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public int getWidth() {
        return 102;
    }

    @Override
    public int getHeight() {
        return 20;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DyedreamContaminationJEIRecipe recipe, IFocusGroup focuses) {
        IRecipeSlotBuilder input = builder.addSlot(RecipeIngredientRole.INPUT, 1, 2);
        for (ItemStack stack : recipe.getInputs()) {
            input.addItemStack(stack);
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 85, 2).addItemStack(recipe.getOutput());
    }

    @Override
    public void draw(DyedreamContaminationJEIRecipe recipe, IRecipeSlotsView recipeSlotsView,
                     GuiGraphics guiGraphics, double mouseX, double mouseY) {
        arrow.draw(guiGraphics, 19, 1);
        icon.draw(guiGraphics, 43, 2);
        arrow.draw(guiGraphics, 61, 1);
    }
}
