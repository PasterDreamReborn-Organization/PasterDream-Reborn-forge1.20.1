package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.armoritem.ForsakensWingItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ForsakensWingModel extends GeoModel<ForsakensWingItem> {

    @Override
    public ResourceLocation getAnimationResource(ForsakensWingItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/forsakens_wing.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(ForsakensWingItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/forsakens_wing.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ForsakensWingItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/models/armor/forsakens_wing.png");
    }
}
