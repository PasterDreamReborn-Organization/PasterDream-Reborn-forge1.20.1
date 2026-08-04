package com.pasterdream.pasterdreammod.world.block.lifecrystal;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class LifeCrystalBlockModel extends GeoModel<LifeCrystalBlockEntity> {
    @Override
    public ResourceLocation getModelResource(LifeCrystalBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/life_crystal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LifeCrystalBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/life_crystal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(LifeCrystalBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/life_crystal.animation.json");
    }
}
