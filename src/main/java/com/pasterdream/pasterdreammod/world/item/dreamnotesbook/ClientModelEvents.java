package com.pasterdream.pasterdreammod.world.item.dreamnotesbook;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "pasterdream", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModelEvents
{
    @SubscribeEvent
    public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event)
    {
        ModelResourceLocation base = new ModelResourceLocation("pasterdream", "dream_notes_book", "inventory");
        BakedModel original = event.getModels().get(base);
        if (original != null)
        {
            event.getModels().put(base, new DreamNotesBookModel(original));
        }
    }

    @SubscribeEvent
    public static void onRegisterAdditionalModels(ModelEvent.RegisterAdditional event)
    {
        for (ModelResourceLocation modelResourceLocation : DreamNotesBookRegistry.getModelLocations())
        {
            event.register(modelResourceLocation);
        }
    }
}
