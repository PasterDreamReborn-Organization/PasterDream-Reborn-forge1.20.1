package com.pasterdream.pasterdreammod.world.block.shadowbrazier;

import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ShadowBrazierDisplayItemRenderer extends GeoItemRenderer<ShadowBrazierDisplayItem> {
    public ShadowBrazierDisplayItemRenderer() {
        super(new ShadowBrazierDisplayModel());
    }
}
