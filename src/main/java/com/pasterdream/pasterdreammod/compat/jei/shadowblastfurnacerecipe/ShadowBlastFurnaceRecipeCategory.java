package com.pasterdream.pasterdreammod.compat.jei.shadowblastfurnacerecipe;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class ShadowBlastFurnaceRecipeCategory implements IRecipeCategory<ShadowBlastFurnaceJEIRecipe>
{
    public static final RecipeType<ShadowBlastFurnaceJEIRecipe> SHADOW_BLAST_FURNACE_RECIPE_TYPE = new RecipeType<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_blast_furnace"), ShadowBlastFurnaceJEIRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public ShadowBlastFurnaceRecipeCategory(IGuiHelper helper)
    {
        background = helper.drawableBuilder(GUIBackGroundRender.SHADOW_BLAST_FURNACE_GUI, 0, 0, 126, 103).setTextureSize(126, 103).build();
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SHADOW_BLAST_FURNACE.get()));
    }

    @Override
    public RecipeType<ShadowBlastFurnaceJEIRecipe> getRecipeType()
    {
        return SHADOW_BLAST_FURNACE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".shadow_blast_furnace");
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public int getWidth()
    {
        return 126;
    }

    @Override
    public int getHeight()
    {
        return 111;
    }

    @Override
    public void draw(ShadowBlastFurnaceJEIRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        int time = recipe.getProcessingTime();
        background.draw(guiGraphics);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, String.format("%02d", (time / 72000)) + "h" + String.format("%02d", ((time % 72000) / 1200)) + "m" + String.format("%02d", ((time % 1200) / 20)) + "s" + String.format("%02d", (time % 20)) + "tick", 63, 104, 0xFFFFFFFF);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ShadowBlastFurnaceJEIRecipe recipe, IFocusGroup focuses)
    {
        IRecipeSlotBuilder inputFluidSlot = builder.addSlot(RecipeIngredientRole.INPUT, 109, 5).setFluidRenderer(1000, false, 16, 16);
        FluidIngredient inputFluidIngredient = recipe.getInputFluidIngredients().get(0);
        if (inputFluidIngredient.getFluid() != null)
        {
            inputFluidSlot.addFluidStack(inputFluidIngredient.getFluid(), inputFluidIngredient.getAmount());
        }
        else
            if (inputFluidIngredient.getTag() != null)
            {
                var tag = ForgeRegistries.FLUIDS.tags().getTag(inputFluidIngredient.getTag());
                if (tag != null)
                {
                    for (Fluid fluid : tag)
                    {
                        inputFluidSlot.addFluidStack(fluid, inputFluidIngredient.getAmount());
                    }
                }
            }

        int index = 0;
        for (ItemIngredient inputItemIngredient : recipe.getInputItemIngredients())
        {
            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.INPUT, 1, 5 + index * 45);
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
        for (ItemIngredient outputItemIngredient : recipe.getOutputItemIngredients())
        {
            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.OUTPUT, 37 + (index * 36), 86);
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
}
