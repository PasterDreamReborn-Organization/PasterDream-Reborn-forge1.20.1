package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanRecipe;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanRecipeSerializer;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronRecipe;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronRecipeSerializer;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarRecipe;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarRecipeSerializer;
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

    public static final RegistryObject<RecipeType<ClaypanRecipe>> CLAYPAN = RECIPE_TYPES.register("claypan", () -> new RecipeType<>() {});
    public static final RegistryObject<RecipeType<DreamCauldronRecipe>> DREAM_CAULDRON = RECIPE_TYPES.register("dream_cauldron", () -> new RecipeType<>() {});
    public static final RegistryObject<RecipeType<MortarRecipe>> MORTAR = RECIPE_TYPES.register("mortar", () -> new RecipeType<>() {});

    public static final RegistryObject<RecipeSerializer<ClaypanRecipe>> CLAYPAN_SERIALIZER = RECIPE_SERIALIZERS.register("claypan", ClaypanRecipeSerializer::new);
    public static final RegistryObject<DreamCauldronRecipeSerializer> DREAM_CAULDRON_SERIALIZER = RECIPE_SERIALIZERS.register("dream_cauldron", DreamCauldronRecipeSerializer::new);
    public static final RegistryObject<MortarRecipeSerializer> MORTAR_SERIALIZER = RECIPE_SERIALIZERS.register("mortar", MortarRecipeSerializer::new);

    public static void register(IEventBus eventBus)
    {
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
