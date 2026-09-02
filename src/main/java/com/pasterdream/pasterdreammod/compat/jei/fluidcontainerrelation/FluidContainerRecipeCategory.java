package com.pasterdream.pasterdreammod.compat.jei.fluidcontainerrelation;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.fluidcontainercapability.FluidContainerRelation;
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
import net.minecraft.world.item.Items;

public class FluidContainerRecipeCategory implements IRecipeCategory<FluidContainerRelation>
{
    public static final RecipeType<FluidContainerRelation> FLUID_CONTAINER_RELATION = new RecipeType<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fluid_container_relation"), FluidContainerRelation.class);
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/fluid_container_relation.png");
    private final IDrawable background;
    private final IDrawable icon;

    public FluidContainerRecipeCategory(IGuiHelper guiHelper)
    {
        this.background = guiHelper.drawableBuilder(TEXTURE, 0, 0, 104, 32).setTextureSize(104, 32).build();
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.BUCKET));
    }

    @Override
    public RecipeType<FluidContainerRelation> getRecipeType()
    {
        return FLUID_CONTAINER_RELATION;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("jei.pasterdream.流体容器绑定关系");
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public int getWidth()
    {
        return 104;
    }

    @Override
    public int getHeight()
    {
        return 32;
    }

    @Override
    public void draw(FluidContainerRelation recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        background.draw(guiGraphics);
        if(recipe.isGenericFluidContainer())
        {
            guiGraphics.drawCenteredString(Minecraft.getInstance().font, Component.translatable("jei.pasterdream.通用流体储罐"), 52, 11, 0xFFFFFFFF);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FluidContainerRelation recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 8).addItemStack(recipe.emptyContainer());
        builder.addSlot(RecipeIngredientRole.INPUT, 44, 8).setFluidRenderer(1000, false, 16, 16).addFluidStack(recipe.fluidStack().getFluid(), recipe.fluidStack().getAmount(), recipe.fluidStack().getTag());
        if(!recipe.isGenericFluidContainer())
        {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 8).addItemStack(recipe.fullContainer());
        }
    }
}
