package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.armoritem.AngelWingItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AngelWingModel extends GeoModel<AngelWingItem> {

    @Override
    public ResourceLocation getAnimationResource(AngelWingItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/angel_wing.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(AngelWingItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/angel_wing.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AngelWingItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/models/armor/angel_wing.png");
    }
}
