package com.pasterdream.pasterdreammod.world.block.birdsnest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BirdsNestBlockModel extends GeoModel<BirdsNestBlockEntity> {
    @Override
    public ResourceLocation getModelResource(BirdsNestBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/birds_nest.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BirdsNestBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/birds_nest.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BirdsNestBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/birds_nest.animation.json");
    }
}
