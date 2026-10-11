package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FluffyWindAlloyArmorModel extends GeoModel<FluffyWindAlloyArmorItem> {

    @Override
    public ResourceLocation getModelResource(FluffyWindAlloyArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/fluffy_wind_alloy_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FluffyWindAlloyArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/item/armor/fluffy_wind_alloy_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FluffyWindAlloyArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/fluffy_wind_alloy_armor.animation.json");
    }
}
