package com.pasterdream.pasterdreammod.world.block.shadowvortex;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShadowVortexBlockModel extends GeoModel<ShadowVortexTileEntity> {
    @Override
    public ResourceLocation getModelResource(ShadowVortexTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/shadow_vortex.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShadowVortexTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/shadow_vortex.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShadowVortexTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/shadow_vortex.animation.json");
    }
}
