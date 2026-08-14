package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.WindKnightEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WindKnightModel extends GeoModel<WindKnightEntity> {
    @Override
    public ResourceLocation getModelResource(WindKnightEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/wind_knight.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WindKnightEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + animatable.getTexture() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(WindKnightEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/wind_knight.animation.json");
    }
}
