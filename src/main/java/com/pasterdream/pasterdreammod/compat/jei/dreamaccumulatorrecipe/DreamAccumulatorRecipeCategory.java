package com.pasterdream.pasterdreammod.compat.jei.dreamaccumulatorrecipe;

import com.pasterdream.pasterdreammod.PasterDreamMod;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

public class DreamAccumulatorRecipeCategory implements IRecipeCategory<DreamAccumulatorJEIRecipe>
{
    public static final RecipeType<DreamAccumulatorJEIRecipe> DREAM_ACCUMULATOR_RECIPE_TYPE = new RecipeType<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_accumulator"), DreamAccumulatorJEIRecipe.class);
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_accumulator.png");
    private final IDrawable background;
    private final IDrawable icon;

    public DreamAccumulatorRecipeCategory(IGuiHelper helper)
    {
        background = helper.drawableBuilder(TEXTURE, 0, 0, 28, 81).setTextureSize(28, 81).build();
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.DREAM_ACCUMULATOR.get()));
    }

    @Override
    public RecipeType<DreamAccumulatorJEIRecipe> getRecipeType()
    {
        return DREAM_ACCUMULATOR_RECIPE_TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".dream_accumulator");
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public int getWidth()
    {
        return 168;
    }

    @Override
    public int getHeight()
    {
        return 81;
    }

    @Override
    public void draw(DreamAccumulatorJEIRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        int time = recipe.getTime();
        double catalystMultiplier = recipe.getCatalystMultiplier();
        background.draw(guiGraphics);

        Registry<Biome> biomeRegistry = Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.BIOME);
        final int[] line = {0};

        for (TagKey<Biome> tag : recipe.getBiomeTags())
        {
            biomeRegistry.getTag(tag).ifPresent(holders ->
            {
                for (Holder<Biome> holder : holders)
                {
                    holder.unwrapKey().ifPresent(resourceKey ->
                    {
                        ResourceLocation id = resourceKey.location();
                        String key = "biome." + id.getNamespace() + "." + id.getPath();
                        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable(key), 112, 10 * line[0], 0xFFFFFFFF, false);
                        line[0]++;
                    });
                }
            });
        }

        guiGraphics.drawString(Minecraft.getInstance().font, String.format("%02d", (time / 72000)) + "h" + String.format("%02d", ((time % 72000) / 1200)) + "m" + String.format("%02d", ((time % 1200) / 20)) + "s" + String.format("%02d", (time % 20)) + "tick", 28, 38, 0xFFFFFFFF, false);
        guiGraphics.drawString(Minecraft.getInstance().font, catalystMultiplier + "x", 28, 65, 0xFFFFFFFF, false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DreamAccumulatorJEIRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.OUTPUT, 6, 6).addItemStack(recipe.getOutput());

        IRecipeSlotBuilder catalystSlot = builder.addSlot(RecipeIngredientRole.INPUT, 6, 60);
        ItemIngredient catalystIngredients = recipe.getCatalystIngredients();
        if (catalystIngredients.getItem() != null)
        {
            catalystSlot.addItemStack(catalystIngredients.getItemStack());
        }
        else
            if (catalystIngredients.getTag() != null)
            {
                var tag = ForgeRegistries.ITEMS.tags().getTag(catalystIngredients.getTag());
                if (tag != null)
                {
                    for (Item item : tag)
                    {
                        catalystSlot.addItemStack(new ItemStack(item));
                    }
                }
            }
    }
}
