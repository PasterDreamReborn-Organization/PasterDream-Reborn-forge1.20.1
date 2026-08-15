package com.pasterdream.pasterdreammod.world.block.fireflyglassjar;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FireflyGlassJarItemModel extends GeoModel<FireflyGlassJarItem> {
    @Override
    public ResourceLocation getModelResource(FireflyGlassJarItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/ecology_glass_jar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FireflyGlassJarItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/ecology_glass_jar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FireflyGlassJarItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/ecology_glass_jar.animation.json");
    }
}
