package com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.shadowchest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShadowChestBlockModel extends GeoModel<ShadowChestBlockEntity>
{
    @Override
    public ResourceLocation getModelResource(ShadowChestBlockEntity animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/shadow_chest.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShadowChestBlockEntity animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/shadow_chest.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShadowChestBlockEntity animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/shadow_chest.animation.json");
    }
}
