package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.armoritem.WindRunnerCloakItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WindRunnerCloakModel extends GeoModel<WindRunnerCloakItem> {

    @Override
    public ResourceLocation getModelResource(WindRunnerCloakItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/wind_runner_cloak.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WindRunnerCloakItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/item/armor/wind_runner_cloak.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WindRunnerCloakItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/wind_runner_cloak.animation.json");
    }
}
