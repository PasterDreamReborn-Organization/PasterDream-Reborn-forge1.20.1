package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TwilightLanternDisplayModel extends GeoModel<TwilightLanternDisplayItem> {
    @Override
    public ResourceLocation getModelResource(TwilightLanternDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/twilight_lantern.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TwilightLanternDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/twilight_lantern.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TwilightLanternDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/twilight_lantern.animation.json");
    }
}
