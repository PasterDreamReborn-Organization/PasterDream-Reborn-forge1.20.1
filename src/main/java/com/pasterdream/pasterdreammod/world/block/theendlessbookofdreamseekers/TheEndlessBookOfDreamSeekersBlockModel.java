package com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TheEndlessBookOfDreamSeekersBlockModel extends GeoModel<TheEndlessBookOfDreamSeekersBlockEntity>
{
    @Override
    public ResourceLocation getModelResource(TheEndlessBookOfDreamSeekersBlockEntity animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/the_endless_book_of_dream_seekers.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TheEndlessBookOfDreamSeekersBlockEntity animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/the_endless_book_of_dream_seekers.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TheEndlessBookOfDreamSeekersBlockEntity animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/the_endless_book_of_dream_seekers.animation.json");
    }
}
