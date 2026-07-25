package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.armoritem.MachineLightWingItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MachineLightWingModel extends GeoModel<MachineLightWingItem> {

    @Override
    public ResourceLocation getAnimationResource(MachineLightWingItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/machine_light_wing.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(MachineLightWingItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/machine_light_wing.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MachineLightWingItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/models/armor/machine_light_wing.png");
    }
}
