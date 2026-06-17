package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.recipe.claypan.ClaypanRecipeSerializer;
import com.pasterdream.pasterdreammod.recipe.claypan.IClaypanRecipe;
import com.pasterdream.pasterdreammod.recipe.dreamcauldron.DreamCauldronRecipeSerializer;
import com.pasterdream.pasterdreammod.recipe.dreamcauldron.IDreamCauldronRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes
{
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PasterDreamMod.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, PasterDreamMod.MOD_ID);

    public static final RegistryObject<RecipeType<IClaypanRecipe>> CLAYPAN = RECIPE_TYPES.register("claypan", () -> new RecipeType<>() {});
    public static final RegistryObject<RecipeType<IDreamCauldronRecipe>> DREAM_CAULDRON = RECIPE_TYPES.register("dream_cauldron", () -> new RecipeType<>() {});

    public static final RegistryObject<ClaypanRecipeSerializer> CLAYPAN_SERIALIZER = RECIPE_SERIALIZERS.register("claypan", ClaypanRecipeSerializer::new);
    public static final RegistryObject<DreamCauldronRecipeSerializer> DREAM_CAULDRON_SERIALIZER = RECIPE_SERIALIZERS.register("dream_cauldron", DreamCauldronRecipeSerializer::new);

    public static void register(IEventBus eventBus)
    {
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
