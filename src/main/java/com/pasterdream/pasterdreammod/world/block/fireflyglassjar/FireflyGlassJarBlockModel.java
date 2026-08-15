package com.pasterdream.pasterdreammod.world.block.fireflyglassjar;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FireflyGlassJarBlockModel extends GeoModel<FireflyGlassJarBlockEntity> {
    @Override
    public ResourceLocation getModelResource(FireflyGlassJarBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/ecology_glass_jar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FireflyGlassJarBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/ecology_glass_jar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FireflyGlassJarBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/ecology_glass_jar.animation.json");
    }
}
