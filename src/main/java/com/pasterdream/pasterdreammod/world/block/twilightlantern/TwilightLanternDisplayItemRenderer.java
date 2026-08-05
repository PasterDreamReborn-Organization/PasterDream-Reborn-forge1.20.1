package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import software.bernie.geckolib.renderer.GeoItemRenderer;

public class TwilightLanternDisplayItemRenderer extends GeoItemRenderer<TwilightLanternDisplayItem> {
    public TwilightLanternDisplayItemRenderer() {
        super(new TwilightLanternDisplayModel());
    }
}
