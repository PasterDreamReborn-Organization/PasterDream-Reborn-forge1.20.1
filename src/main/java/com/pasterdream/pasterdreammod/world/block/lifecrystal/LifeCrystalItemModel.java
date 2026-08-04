package com.pasterdream.pasterdreammod.world.block.lifecrystal;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class LifeCrystalItemModel extends GeoModel<LifeCrystalItem> {
    @Override
    public ResourceLocation getModelResource(LifeCrystalItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/life_crystal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LifeCrystalItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/life_crystal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(LifeCrystalItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/life_crystal.animation.json");
    }
}
