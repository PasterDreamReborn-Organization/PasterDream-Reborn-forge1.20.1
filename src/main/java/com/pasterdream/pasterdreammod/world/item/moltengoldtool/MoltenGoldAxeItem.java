package com.pasterdream.pasterdreammod.world.item.moltengoldtool;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class MoltenGoldAxeItem extends AxeItem {

    public MoltenGoldAxeItem(Tier tier, float damage, float speed, Properties properties) {
        super(tier, damage, speed, properties.fireResistant());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return MoltenGoldToolHelper.use(this, level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        MoltenGoldToolHelper.appendHoverText(stack, level, tooltip, flag);
    }
}
