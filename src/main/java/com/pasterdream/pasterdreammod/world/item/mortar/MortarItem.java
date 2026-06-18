package com.pasterdream.pasterdreammod.world.item.mortar;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class MortarItem extends Item
{
    public MortarItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide)
        {
            CompoundTag tag = stack.getOrCreateTag();
            if (!tag.contains("InputFluids"))
            {
                ListTag inputFluids = new ListTag();
                for (int i = 0; i < 4; i++) inputFluids.add(new CompoundTag());
                tag.put("InputFluids", inputFluids);
            }
            if (!tag.contains("OutputFluids"))
            {
                ListTag outputFluids = new ListTag();
                for (int i = 0; i < 2; i++) outputFluids.add(new CompoundTag());
                tag.put("OutputFluids", outputFluids);
            }

            NetworkHooks.openScreen((ServerPlayer) player, new MenuProvider()
            {
                @Override
                public Component getDisplayName()
                {
                    return Component.translatable("gui." + PasterDreamMod.MOD_ID + ".mortar");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inv, Player p)
                {
                    return new MortarMenu(id, inv, stack, hand);
                }
            }, buf ->
            {
                buf.writeBoolean(hand == InteractionHand.MAIN_HAND);
            });
        }
        return InteractionResultHolder.success(stack);
    }
}
