package com.pasterdream.pasterdreammod.world.item.armoritem;

import net.minecraft.resources.ResourceLocation;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import software.bernie.geckolib.model.GeoModel;

public class ShadowAlloyArmorModel extends GeoModel<ShadowAlloyArmorItem> {

    @Override
    public ResourceLocation getModelResource(ShadowAlloyArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/shadow_alloy_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShadowAlloyArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/item/armor/shadow_alloy_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShadowAlloyArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/shadow_alloy_armor.animation.json");
    }
}
