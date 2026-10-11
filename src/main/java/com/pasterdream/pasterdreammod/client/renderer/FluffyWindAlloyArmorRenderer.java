package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.item.armoritem.FluffyWindAlloyArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.FluffyWindAlloyArmorModel;
import net.minecraft.world.entity.EquipmentSlot;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class FluffyWindAlloyArmorRenderer extends GeoArmorRenderer<FluffyWindAlloyArmorItem> {

    public FluffyWindAlloyArmorRenderer() {
        super(new FluffyWindAlloyArmorModel());
    }

    @Override
    protected void applyBoneVisibilityBySlot(EquipmentSlot currentSlot) {
        super.applyBoneVisibilityBySlot(currentSlot);
        GeoBone waist = getGeoModel().getBone("waist").orElse(null);
        if (waist != null) {
            waist.setHidden(currentSlot != EquipmentSlot.LEGS);
        }
    }
}
