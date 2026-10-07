package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.item.armoritem.ShadowAlloyArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.ShadowAlloyArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ShadowAlloyArmorRenderer extends GeoArmorRenderer<ShadowAlloyArmorItem> {

    public ShadowAlloyArmorRenderer() {
        super(new ShadowAlloyArmorModel());
    }
}
