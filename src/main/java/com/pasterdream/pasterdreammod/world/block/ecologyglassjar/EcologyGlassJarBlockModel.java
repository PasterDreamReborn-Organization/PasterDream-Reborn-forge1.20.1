package com.pasterdream.pasterdreammod.world.block.ecologyglassjar;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class EcologyGlassJarBlockModel extends GeoModel<EcologyGlassJarBlockEntity> {
    @Override
    public ResourceLocation getModelResource(EcologyGlassJarBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/ecology_glass_jar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EcologyGlassJarBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/ecology_glass_jar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EcologyGlassJarBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/ecology_glass_jar.animation.json");
    }
}
