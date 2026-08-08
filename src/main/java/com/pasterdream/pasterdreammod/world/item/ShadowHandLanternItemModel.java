package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShadowHandLanternItemModel extends GeoModel<ShadowHandLanternItem> {
    @Override
    public ResourceLocation getAnimationResource(ShadowHandLanternItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/shadow_hand_lantern.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(ShadowHandLanternItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/shadow_hand_lantern.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShadowHandLanternItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/item/shadow_hand_lantern.png");
    }
}
