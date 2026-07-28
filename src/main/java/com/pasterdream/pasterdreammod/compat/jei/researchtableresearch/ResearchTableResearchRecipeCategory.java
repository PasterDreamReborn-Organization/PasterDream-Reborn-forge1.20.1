package com.pasterdream.pasterdreammod.compat.jei.researchtableresearch;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class ResearchTableResearchRecipeCategory implements IRecipeCategory<ResearchTableResearchJEIRecipe>
{
    public static final RecipeType<ResearchTableResearchJEIRecipe> RESEARCH_TABLE_RESEARCH_RECIPE_TYPE = new RecipeType<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "research_table_research"), ResearchTableResearchJEIRecipe.class);
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/research_table.png");
    private final IDrawable background;
    private final IDrawable icon;

    public ResearchTableResearchRecipeCategory(IGuiHelper guiHelper)
    {
        this.background = guiHelper.drawableBuilder(TEXTURE, 0, 0, 197, 115).setTextureSize(197, 115).build();
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.RESEARCH_TABLE.get()));
    }

    @Override
    public RecipeType<ResearchTableResearchJEIRecipe> getRecipeType()
    {
        return RESEARCH_TABLE_RESEARCH_RECIPE_TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("recipe." + PasterDreamMod.MOD_ID + ".research");
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ResearchTableResearchJEIRecipe recipe, IFocusGroup focuses)
    {
        IRecipeSlotBuilder fluidSlot = builder.addSlot(RecipeIngredientRole.INPUT, 170, 17).setFluidRenderer(250, false, 16, 16);
        FluidIngredient fluidIngredient = recipe.getInputFluidIngredients().get(0);
        if (fluidIngredient.getFluid() != null)
        {
            fluidSlot.addFluidStack(fluidIngredient.getFluid(), fluidIngredient.getAmount());
        }
        else
        if (fluidIngredient.getTag() != null)
        {
            var tag = ForgeRegistries.FLUIDS.tags().getTag(fluidIngredient.getTag());
            if (tag != null)
            {
                for (Fluid fluid : tag)
                {
                    fluidSlot.addFluidStack(fluid, fluidIngredient.getAmount());
                }
            }
        }

        IRecipeSlotBuilder penSlot = builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 170, 80);
        ItemIngredient penItemIngredient = recipe.getInputItemIngredients().get(0);
        if (penItemIngredient.getItem() != null)
        {
            penSlot.addItemStack(penItemIngredient.getItemStack());
        }
        else
        if (penItemIngredient.getTag() != null)
        {
            var tag = ForgeRegistries.ITEMS.tags().getTag(penItemIngredient.getTag());
            if (tag != null)
            {
                for (Item item : tag)
                {
                    ItemStack pen = new ItemStack(item, penItemIngredient.getCount());
                    pen.setTag(penItemIngredient.getNbt());
                    penSlot.addItemStack(pen);
                }
            }
        }

        IRecipeSlotBuilder unResolvedNoteSlot = builder.addSlot(RecipeIngredientRole.INPUT, 35, 80);
        ItemIngredient sourceBookItemIngredient = recipe.getInputItemIngredients().get(1);
        if (sourceBookItemIngredient.getItem() != null)
        {
            unResolvedNoteSlot.addItemStack(sourceBookItemIngredient.getItemStack());
        }
        else
        if (sourceBookItemIngredient.getTag() != null)
        {
            var tag = ForgeRegistries.ITEMS.tags().getTag(sourceBookItemIngredient.getTag());
            if (tag != null)
            {
                for (Item item : tag)
                {
                    unResolvedNoteSlot.addItemStack(new ItemStack(item, sourceBookItemIngredient.getCount(), sourceBookItemIngredient.getNbt()));
                }
            }
        }

        IRecipeSlotBuilder outputItemSlot = builder.addSlot(RecipeIngredientRole.OUTPUT, 134, 80);
        ItemIngredient outputItemIngredient = recipe.getOutputItemIngredients().get(0);
        if (outputItemIngredient.getItem() != null)
        {
            outputItemSlot.addItemStack(outputItemIngredient.getItemStack());
        }
        else
        if (outputItemIngredient.getTag() != null)
        {
            var tag = ForgeRegistries.ITEMS.tags().getTag(outputItemIngredient.getTag());
            if (tag != null)
            {
                for (Item item : tag)
                {
                    outputItemSlot.addItemStack(new ItemStack(item, outputItemIngredient.getCount(), outputItemIngredient.getNbt()));
                }
            }
        }
    }

    @Override
    public void draw(ResearchTableResearchJEIRecipe recipe, IRecipeSlotsView slotsView, GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        background.draw(guiGraphics);
    }

    @Override
    public int getWidth()
    {
        return 197;
    }

    @Override
    public int getHeight()
    {
        return 115;
    }
}
