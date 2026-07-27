package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.TerrorbeakEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TerrorbeakModel extends GeoModel<TerrorbeakEntity> {
    @Override
    public ResourceLocation getAnimationResource(TerrorbeakEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/terrorbeak.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(TerrorbeakEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/terrorbeak.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TerrorbeakEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + entity.getTexture() + ".png");
    }
}
