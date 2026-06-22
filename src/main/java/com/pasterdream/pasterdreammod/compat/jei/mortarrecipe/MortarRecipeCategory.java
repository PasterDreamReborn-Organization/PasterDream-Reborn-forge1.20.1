package com.pasterdream.pasterdreammod.compat.jei.mortarrecipe;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.init.ModItems;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class MortarRecipeCategory implements IRecipeCategory<MortarJEIRecipe>
{
    public static final RecipeType<MortarJEIRecipe> MORTAR_RECIPE_TYPE = new RecipeType<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "mortar"), MortarJEIRecipe.class);
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/mortar/mortar.png");
    private final IDrawable background;
    private final IDrawable icon;

    public MortarRecipeCategory(IGuiHelper guiHelper)
    {
        this.background = guiHelper.drawableBuilder(TEXTURE, 0, 0, 128, 90).setTextureSize(128, 90).build();
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.MORTAR.get()));
    }

    @Override
    public RecipeType<MortarJEIRecipe> getRecipeType()
    {
        return MORTAR_RECIPE_TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("item." + PasterDreamMod.MOD_ID + ".mortar");
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MortarJEIRecipe recipe, IFocusGroup focuses)
    {
        int index = 0;
        for(FluidIngredient inputFluidIngredient : recipe.getInputFluidIngredients())
        {
            IRecipeSlotBuilder FluidSlot = builder.addSlot(RecipeIngredientRole.INPUT, 6 + (index % 2) * 18, 6 + (index / 2) * 18).setFluidRenderer(1000, false, 16, 16);
            if (inputFluidIngredient.getFluid() != null)
            {
                FluidSlot.addFluidStack(inputFluidIngredient.getFluid(), inputFluidIngredient.getAmount());
            }
            else
                if (inputFluidIngredient.getTag() != null)
                {
                    var tag = ForgeRegistries.FLUIDS.tags().getTag(inputFluidIngredient.getTag());
                    if (tag != null)
                    {
                        for (Fluid fluid : tag)
                        {
                            FluidSlot.addFluidStack(fluid, inputFluidIngredient.getAmount());
                        }
                    }
                }
            index++;
        }

        index = 0;
        for (ItemIngredient inputItemIngredient : recipe.getInputItemIngredients())
        {
            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.INPUT, 52 + (index % 4) * 18, 6 + (index / 4) * 18);
            if (inputItemIngredient.getItem() != null)
            {
                slot.addItemStack(inputItemIngredient.getItemStack());
            }
                else
                {
                    if (inputItemIngredient.getTag() != null)
                    {
                        var tag = ForgeRegistries.ITEMS.tags().getTag(inputItemIngredient.getTag());
                        if (tag != null)
                        {
                            for (Item item : tag)
                            {
                                slot.addItemStack(new ItemStack(item, inputItemIngredient.getCount(), inputItemIngredient.getNbt()));
                            }
                        }
                    }
                }
            index++;
        }

        index = 0;
        for(FluidIngredient outputFluidIngredient : recipe.getOutputFluidIngredients())
        {
            IRecipeSlotBuilder FluidSlot = builder.addSlot(RecipeIngredientRole.OUTPUT, 6 + (index * 18), 68).setFluidRenderer(1000, false, 16, 16);
            if (outputFluidIngredient.getFluid() != null)
            {
                FluidSlot.addFluidStack(outputFluidIngredient.getFluid(), outputFluidIngredient.getAmount());
            }
            else
                if (outputFluidIngredient.getTag() != null)
                {
                    var tag = ForgeRegistries.FLUIDS.tags().getTag(outputFluidIngredient.getTag());
                    if (tag != null)
                    {
                        for (Fluid fluid : tag)
                        {
                            FluidSlot.addFluidStack(fluid, outputFluidIngredient.getAmount());
                        }
                    }
                }
            index++;
        }

        index = 0;
        for (ItemIngredient outputItemIngredient : recipe.getOutputItemIngredients())
        {
            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.OUTPUT, 52 + (index * 18), 68);
            if (outputItemIngredient.getItem() != null)
            {
                slot.addItemStack(outputItemIngredient.getItemStack());
            }
                else
                {
                    if (outputItemIngredient.getTag() != null)
                    {
                        var tag = ForgeRegistries.ITEMS.tags().getTag(outputItemIngredient.getTag());
                        if (tag != null)
                        {
                            for (Item item : tag)
                            {
                                slot.addItemStack(new ItemStack(item, outputItemIngredient.getCount(), outputItemIngredient.getNbt()));
                            }
                        }
                    }
                }
            index++;
        }
    }

    @Override
    public void draw(MortarJEIRecipe recipe, IRecipeSlotsView slotsView, GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        background.draw(guiGraphics);
    }

    @Override
    public int getWidth()
    {
        return 128;
    }

    @Override
    public int getHeight()
    {
        return 90;
    }
}
