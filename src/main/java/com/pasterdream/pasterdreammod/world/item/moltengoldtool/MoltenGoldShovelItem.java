package com.pasterdream.pasterdreammod.world.item.moltengoldtool;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class MoltenGoldShovelItem extends ShovelItem implements MoltenGoldTool {

    public MoltenGoldShovelItem(Tier tier, float damage, float speed, Properties properties) {
        super(tier, damage, speed, properties.fireResistant());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return MoltenGoldToolHelper.use(this, level, player, hand);
    }
}
