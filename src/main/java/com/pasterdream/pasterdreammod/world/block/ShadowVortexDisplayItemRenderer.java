package com.pasterdream.pasterdreammod.world.block;

import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ShadowVortexDisplayItemRenderer extends GeoItemRenderer<ShadowVortexDisplayItem> {
    public ShadowVortexDisplayItemRenderer() {
        super(new ShadowVortexDisplayModel());
    }
}
