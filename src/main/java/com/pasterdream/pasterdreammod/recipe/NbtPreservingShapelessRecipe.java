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
 * 无序配方——组装时从第一个含 NBT 的原料复制附魔等数据到产物。
 */
public class NbtPreservingShapelessRecipe extends ShapelessRecipe {

    public NbtPreservingShapelessRecipe(ResourceLocation id, String group, CraftingBookCategory category,
                                        ItemStack result, NonNullList<Ingredient> ingredients) {
        super(id, group, category, result, ingredients);
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
        return ModRecipes.NBT_PRESERVING_SHAPELESS_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<NbtPreservingShapelessRecipe> {
        private static final ShapelessRecipe.Serializer FALLBACK = new ShapelessRecipe.Serializer();

        @Override
        public NbtPreservingShapelessRecipe fromJson(ResourceLocation id, com.google.gson.JsonObject json) {
            ShapelessRecipe vanilla = FALLBACK.fromJson(id, json);
            return new NbtPreservingShapelessRecipe(vanilla.getId(), vanilla.getGroup(),
                    vanilla.category(), vanilla.getResultItem(RegistryAccess.EMPTY),
                    vanilla.getIngredients());
        }

        @Override
        public NbtPreservingShapelessRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            ShapelessRecipe vanilla = FALLBACK.fromNetwork(id, buf);
            return new NbtPreservingShapelessRecipe(vanilla.getId(), vanilla.getGroup(),
                    vanilla.category(), vanilla.getResultItem(RegistryAccess.EMPTY),
                    vanilla.getIngredients());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, NbtPreservingShapelessRecipe recipe) {
            FALLBACK.toNetwork(buf, recipe);
        }
    }
}
