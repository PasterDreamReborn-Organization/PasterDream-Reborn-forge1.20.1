package com.pasterdream.pasterdreammod.world.block.ecologyglassjar;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class EcologyGlassJarItemModel extends GeoModel<EcologyGlassJarItem> {
    @Override
    public ResourceLocation getModelResource(EcologyGlassJarItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/ecology_glass_jar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EcologyGlassJarItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/ecology_glass_jar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EcologyGlassJarItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/ecology_glass_jar.animation.json");
    }
}
