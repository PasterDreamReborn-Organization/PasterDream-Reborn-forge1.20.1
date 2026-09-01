package com.pasterdream.pasterdreammod.world.block.windknightaltar;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WindKnightAltarItemModel extends GeoModel<WindKnightAltarItem> {
    @Override
    public ResourceLocation getModelResource(WindKnightAltarItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/break_wind_knight_altar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WindKnightAltarItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/break_wind_knight_altar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WindKnightAltarItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/break_wind_knight_altar.animation.json");
    }
}
