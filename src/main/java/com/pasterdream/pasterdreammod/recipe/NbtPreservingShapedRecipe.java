package com.pasterdream.pasterdreammod.recipe;

import com.pasterdream.pasterdreammod.init.ModRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

/**
 * 有序配方——组装时从第一个含 NBT 的可损物品复制附魔等数据到产物。
 */
public class NbtPreservingShapedRecipe extends ShapedRecipe {

    public NbtPreservingShapedRecipe(ResourceLocation id, String group, CraftingBookCategory category,
                                      int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
        super(id, group, category, width, height, ingredients, result);
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack result = super.assemble(container, registryAccess);
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.hasTag() && stack.isDamageableItem()) {
                result.setTag(stack.getTag().copy());
                break;
            }
        }
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.NBT_PRESERVING_SHAPED_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<NbtPreservingShapedRecipe> {
        private static final ShapedRecipe.Serializer FALLBACK = new ShapedRecipe.Serializer();

        @Override
        public NbtPreservingShapedRecipe fromJson(ResourceLocation id, com.google.gson.JsonObject json) {
            ShapedRecipe vanilla = FALLBACK.fromJson(id, json);
            return new NbtPreservingShapedRecipe(vanilla.getId(), vanilla.getGroup(),
                    vanilla.category(), vanilla.getWidth(), vanilla.getHeight(),
                    vanilla.getIngredients(), vanilla.getResultItem(RegistryAccess.EMPTY));
        }

        @Override
        public NbtPreservingShapedRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            ShapedRecipe vanilla = FALLBACK.fromNetwork(id, buf);
            return new NbtPreservingShapedRecipe(vanilla.getId(), vanilla.getGroup(),
                    vanilla.category(), vanilla.getWidth(), vanilla.getHeight(),
                    vanilla.getIngredients(), vanilla.getResultItem(RegistryAccess.EMPTY));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, NbtPreservingShapedRecipe recipe) {
            FALLBACK.toNetwork(buf, recipe);
        }
    }
}
