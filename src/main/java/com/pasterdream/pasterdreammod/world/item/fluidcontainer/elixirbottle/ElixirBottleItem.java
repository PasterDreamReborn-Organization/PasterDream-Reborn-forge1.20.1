package com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.FluidDrinkPropertiesRegistry;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.GenericFluidDrinkProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class ElixirBottleItem extends Item
{
    private int capacity = 1000;

    public ElixirBottleItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public Component getName(ItemStack itemStack)
    {
        FluidStack fluidStack = getFluidStack(itemStack);

        if (!fluidStack.isEmpty())
        {
            return Component.translatable("item.pasterdream.elixir_bottle").append(" - " + fluidStack.getAmount() + "mB ").append(fluidStack.getDisplayName());
        }
            else
            {
                return Component.translatable("item.pasterdream.elixir_bottle");
            }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(itemStack, level, tooltip, flag);

        FluidStack fluidStack = getFluidStack(itemStack);

        if (fluidStack.isEmpty())
        {
            tooltip.add(Component.translatable("tooltip.pasterdream.空").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("tooltip.pasterdream.总容量:").append("1000 mB").withStyle(ChatFormatting.GRAY));
        }
            else
            {
                tooltip.add(Component.translatable(fluidStack.getDisplayName().getString()));
                tooltip.add(Component.literal(fluidStack.getAmount() + " mB"));
            }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        FluidStack fluidStack = getFluidStack(itemStack);

        if (!fluidStack.isEmpty() && FluidDrinkPropertiesRegistry.getProperties(fluidStack.getFluid()) != null)
        {
            player.startUsingItem(interactionHand);
            return InteractionResultHolder.consume(itemStack);
        }
            else
            {
                return InteractionResultHolder.fail(itemStack);
            }
    }

    @Override
    public int getUseDuration(ItemStack itemStack)
    {
        FluidStack fluidStack = getFluidStack(itemStack);
        GenericFluidDrinkProperties drinkProperties = FluidDrinkPropertiesRegistry.getProperties(fluidStack.getFluid());

        int time = drinkProperties.getUseDuration();
        int drinkAmount = drinkProperties.getDrinkAmount();

        if(fluidStack.getAmount() >= drinkAmount)
        {
            return time;
        }
            else
            {
                return Math.max(1,  time * fluidStack.getAmount() / drinkAmount);
            }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack)
    {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity)
    {
        if (!level.isClientSide && entity instanceof Player player)
        {
            FluidStack fluidStack = getFluidStack(itemStack);
            GenericFluidDrinkProperties drinkProperties = FluidDrinkPropertiesRegistry.getProperties(fluidStack.getFluid());
            if (drinkProperties != null)
            {
                int drinkAmount = drinkProperties.getDrinkAmount();
                int fluidAmountInElixirBottle = fluidStack.getAmount();

                if (!player.isCreative())
                {
                    int drainAmount = Math.min(fluidAmountInElixirBottle, drinkAmount);
                    if (drainAmount > 0)
                    {
                        itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE));
                    }
                }

                FoodProperties food = drinkProperties.getFoodProperties();
                if (food != null)
                {
                    if (fluidAmountInElixirBottle >= drinkAmount)
                    {
                        player.getFoodData().eat(food.getNutrition(), food.getSaturationModifier());
                    }
                        else
                        {
                            player.getFoodData().eat(food.getNutrition() * fluidAmountInElixirBottle / drinkAmount, food.getSaturationModifier());
                        }
                }

                for (MobEffectInstance effect : drinkProperties.getEffects())
                {
                    if (fluidAmountInElixirBottle >= drinkAmount)
                    {
                        entity.addEffect(new MobEffectInstance(effect));
                    }
                        else
                        {
                            entity.addEffect(new MobEffectInstance(effect.getEffect(), effect.getDuration() * fluidAmountInElixirBottle / drinkAmount, effect.getAmplifier()));
                        }
                }

                if (player instanceof ServerPlayer serverPlayer)
                {
                    if (drinkProperties.getSanAdd() != 0)
                    {
                        if (fluidAmountInElixirBottle >= drinkAmount)
                        {
                            SanHelper.addPlayerSanAndSync(serverPlayer, drinkProperties.getSanAdd());
                        }
                            else
                            {
                                SanHelper.addPlayerSanAndSync(serverPlayer, drinkProperties.getSanAdd() * fluidAmountInElixirBottle / drinkAmount);
                            }
                    }

                    if (drinkProperties.getMeltDreamEnergyAdd() != 0)
                    {
                        if (fluidAmountInElixirBottle >= drinkAmount)
                        {
                            MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(serverPlayer, drinkProperties.getMeltDreamEnergyAdd());
                        }
                            else
                            {
                                MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(serverPlayer, drinkProperties.getMeltDreamEnergyAdd() * fluidAmountInElixirBottle / drinkAmount);
                            }
                    }
                }

                drinkProperties.getDrinkSpecial().accept(entity, level);
            }
        }
        return itemStack;
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

    private FluidStack getFluidStack(ItemStack itemStack)
    {
        AtomicReference<FluidStack> atomicReferenceFluidStack = new AtomicReference<>(FluidStack.EMPTY);
        itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> atomicReferenceFluidStack.set(handler.getFluidInTank(0)));
        return atomicReferenceFluidStack.get();
    }
}
