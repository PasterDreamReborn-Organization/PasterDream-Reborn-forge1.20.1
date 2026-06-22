package com.pasterdream.pasterdreammod.world.item.mortar;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MortarItem extends Item
{
    public MortarItem(Properties properties)
    {
        super(properties.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.右键打开GUI"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide)
        {
            NetworkHooks.openScreen((ServerPlayer) player, new MenuProvider()
            {
                @Override
                public Component getDisplayName()
                {
                    return Component.translatable("gui." + PasterDreamMod.MOD_ID + ".mortar");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
                {
                    return new MortarMenu(id, inventory, stack, hand);
                }
            }, buf ->
            {
                buf.writeBoolean(hand == InteractionHand.MAIN_HAND);
            });
        }
        return InteractionResultHolder.success(stack);
    }
}
