package com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TheEndlessBookOfDreamSeekersItemModel extends GeoModel<TheEndlessBookOfDreamSeekersItem>
{
    @Override
    public ResourceLocation getModelResource(TheEndlessBookOfDreamSeekersItem animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/the_endless_book_of_dream_seekers.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TheEndlessBookOfDreamSeekersItem animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/the_endless_book_of_dream_seekers.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TheEndlessBookOfDreamSeekersItem animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/the_endless_book_of_dream_seekers.animation.json");
    }
}
