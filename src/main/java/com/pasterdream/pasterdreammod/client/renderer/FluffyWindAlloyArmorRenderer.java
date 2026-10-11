package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.item.armoritem.FluffyWindAlloyArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.FluffyWindAlloyArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class FluffyWindAlloyArmorRenderer extends GeoArmorRenderer<FluffyWindAlloyArmorItem> {

    public FluffyWindAlloyArmorRenderer() {
        super(new FluffyWindAlloyArmorModel());
    }
}
