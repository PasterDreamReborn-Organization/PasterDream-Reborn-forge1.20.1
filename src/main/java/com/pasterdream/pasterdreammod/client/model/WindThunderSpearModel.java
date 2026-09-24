package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.WindThunderSpearEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WindThunderSpearModel extends GeoModel<WindThunderSpearEntity> {
    @Override
    public ResourceLocation getModelResource(WindThunderSpearEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/windwreathed_thunder_spear.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WindThunderSpearEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/item/windwreathed_thunder_spear.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WindThunderSpearEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/windwreathed_thunder_spear.animation.json");
    }
}
