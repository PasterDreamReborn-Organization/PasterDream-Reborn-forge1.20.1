package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.world.dimension.AaroncosArenaTeleporter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class AaroncosArenaCreateItem extends Item {
    public AaroncosArenaCreateItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        list.add(Component.translatable("tooltip.pasterdream.aaroncos_arena_create"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, player, hand);
        AaroncosArenaTeleporter.teleportToArena(world, player);
        return ar;
    }
}
