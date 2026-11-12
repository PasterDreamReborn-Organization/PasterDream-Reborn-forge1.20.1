package com.pasterdream.pasterdreammod.world.item.meltdreamtool;

import net.minecraft.world.item.*;

public class MeltDreamHoeItem extends HoeItem implements MeltDreamTool {

    public MeltDreamHoeItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties.fireResistant());
    }
}
