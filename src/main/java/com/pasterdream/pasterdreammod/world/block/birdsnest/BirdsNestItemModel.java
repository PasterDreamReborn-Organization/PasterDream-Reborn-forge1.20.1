package com.pasterdream.pasterdreammod.world.block.birdsnest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BirdsNestItemModel extends GeoModel<BirdsNestItem> {
    @Override
    public ResourceLocation getModelResource(BirdsNestItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/birds_nest.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BirdsNestItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/birds_nest.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BirdsNestItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/birds_nest.animation.json");
    }
}
