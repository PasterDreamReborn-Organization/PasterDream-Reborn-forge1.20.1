package com.pasterdream.pasterdreammod.world.block.itemcontainer.openedmeltdreamcrystalchest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class OpenedMeltDreamCrystalChestBlockModel extends GeoModel<OpenedMeltDreamCrystalChestBlockEntity>
{
    @Override
    public ResourceLocation getModelResource(OpenedMeltDreamCrystalChestBlockEntity animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/opened_melt_dream_crystal_chest.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OpenedMeltDreamCrystalChestBlockEntity animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/melt_dream_crystal_chest.png");
    }

    @Override
    public ResourceLocation getAnimationResource(OpenedMeltDreamCrystalChestBlockEntity animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/opened_melt_dream_crystal_chest.animation.json");
    }
}
