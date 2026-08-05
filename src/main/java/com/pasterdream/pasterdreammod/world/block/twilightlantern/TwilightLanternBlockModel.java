package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TwilightLanternBlockModel extends GeoModel<TwilightLanternBlockEntity> {
    @Override
    public ResourceLocation getModelResource(TwilightLanternBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/twilight_lantern.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TwilightLanternBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/twilight_lantern.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TwilightLanternBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/twilight_lantern.animation.json");
    }
}
