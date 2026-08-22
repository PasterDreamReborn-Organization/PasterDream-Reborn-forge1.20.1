package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.renderhelper.CustomRendererBakedModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelEvent;

public class ModItemModels
{
    public static void register(ModelEvent.ModifyBakingResult event)
    {
        ModelResourceLocation modelLocation = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "elixir_bottle"), "inventory");
        BakedModel original = event.getModels().get(modelLocation);
        if (original != null && !(original instanceof CustomRendererBakedModel))
        {
            event.getModels().put(modelLocation, new CustomRendererBakedModel(original));
        }
    }

    private static BakedModel elixirBottleModel;

    public static void getBakedModel(ModelEvent.BakingCompleted event)
    {
        ModelResourceLocation location = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "elixir_bottle"), "inventory");
        elixirBottleModel = event.getModels().get(location);
        if (elixirBottleModel == null)
        {
            elixirBottleModel = event.getModelManager().getMissingModel();
        }
    }

    public static BakedModel getElixirBottleModel()
    {
        return elixirBottleModel;
    }
}
