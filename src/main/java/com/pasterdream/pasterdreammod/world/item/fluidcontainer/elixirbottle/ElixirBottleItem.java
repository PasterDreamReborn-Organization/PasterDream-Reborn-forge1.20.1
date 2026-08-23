package com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class ElixirBottleItem extends Item
{
    private int capacity = 1000;

    public ElixirBottleItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(stack, level, tooltip, flag);

        stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler ->
        {
            FluidStack fluid = handler.getFluidInTank(0);
            if (fluid.isEmpty())
            {
                tooltip.add(Component.translatable("tooltip.pasterdream.空").withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.translatable("tooltip.pasterdream.总容量:").append("1000 mB").withStyle(ChatFormatting.GRAY));
            }
                else
                {
                    tooltip.add(Component.translatable(fluid.getDisplayName().getString()));
                    tooltip.add(Component.literal(fluid.getAmount() + " mB"));
                }
        });
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack itemStack, @Nullable CompoundTag nbt)
    {
        return new FluidHandlerItemStack(itemStack, capacity);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer)
    {
        consumer.accept(new IClientItemExtensions()
        {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                return ElixirBottleRenderer.getInstance();
            }
        });
    }
}
