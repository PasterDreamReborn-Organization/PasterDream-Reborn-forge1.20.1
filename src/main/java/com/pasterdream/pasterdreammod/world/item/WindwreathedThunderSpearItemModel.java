package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WindwreathedThunderSpearItemModel extends GeoModel<WindwreathedThunderSpearItem> {
    @Override
    public ResourceLocation getAnimationResource(WindwreathedThunderSpearItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/windwreathed_thunder_spear.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(WindwreathedThunderSpearItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/windwreathed_thunder_spear.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WindwreathedThunderSpearItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/item/windwreathed_thunder_spear.png");
    }
}
