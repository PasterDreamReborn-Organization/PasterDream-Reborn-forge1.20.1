package com.pasterdream.pasterdreammod.world.item.meltdreamtool;

import net.minecraft.world.item.*;

public class MeltDreamShovelItem extends ShovelItem implements MeltDreamTool {

    public MeltDreamShovelItem(Tier tier, float damage, float speed, Properties properties) {
        super(tier, damage, speed, properties.fireResistant());
    }
}
