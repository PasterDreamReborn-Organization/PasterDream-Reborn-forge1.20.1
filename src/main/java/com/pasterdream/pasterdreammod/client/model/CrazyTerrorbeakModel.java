package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.CrazyTerrorbeakEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CrazyTerrorbeakModel extends GeoModel<CrazyTerrorbeakEntity> {
    @Override
    public ResourceLocation getAnimationResource(CrazyTerrorbeakEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/crazy_terrorbeak.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(CrazyTerrorbeakEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/crazy_terrorbeak.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CrazyTerrorbeakEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + entity.getTexture() + ".png");
    }
}
