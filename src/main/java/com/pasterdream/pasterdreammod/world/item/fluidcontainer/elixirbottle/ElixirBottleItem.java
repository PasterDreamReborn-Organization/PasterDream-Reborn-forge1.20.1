package com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.FluidDrinkPropertiesRegistry;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.FoodValueTooltips;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.GenericFluidDrinkProperties;
import com.pasterdream.pasterdreammod.helper.potionhelper.GenericMobEffect;
import com.pasterdream.pasterdreammod.helper.potionhelper.PotionHelper;
import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
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
        FluidStack fluidStack = getElixirBottleFluidStack(itemStack);

        if (!fluidStack.isEmpty())
        {
            // 成品药水：固定粉色「灵药瓶」，不再拼接容量/流体名（对齐法术工厂药水提示）
            if (isPotionFluid(fluidStack))
            {
                return Component.translatable("item.pasterdream.elixir_bottle").withStyle(ChatFormatting.LIGHT_PURPLE);
            }
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

        FluidStack fluidStack = getElixirBottleFluidStack(itemStack);

        if (fluidStack.isEmpty())
        {
            tooltip.add(Component.translatable("tooltip.pasterdream.空").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("tooltip.pasterdream.总容量:").append("1000 mB").withStyle(ChatFormatting.GRAY));
            return;
        }

        // 成品药水：每条效果一行「·名称 [等级] (时长)」，按效果类别着色，末行灰色容量
        if (isPotionFluid(fluidStack))
        {
            for (GenericMobEffect effect : PotionHelper.getEffectType(fluidStack))
            {
                MobEffectInstance instance = GenericMobEffect.fromGenericMobEffectToEffectInstance(effect);
                MutableComponent line = Component.literal("· ");
                line.append(Component.translatable(instance.getDescriptionId()));
                if (instance.getAmplifier() > 0)
                {
                    line.append(" ").append(Component.literal(toRoman(instance.getAmplifier() + 1)));
                }
                if (instance.getDuration() > 20)
                {
                    line.append(" (").append(MobEffectUtil.formatDuration(instance, 1.0F)).append(")");
                }
                line.withStyle(instance.getEffect().getCategory().getTooltipFormatting());
                tooltip.add(line);
            }
            tooltip.add(Component.translatable("pasterdream.tooltip.elixir_capacity", fluidStack.getAmount())
                    .withStyle(ChatFormatting.GRAY));
            return;
        }

        if (FluidDrinkPropertiesRegistry.getProperties(fluidStack) != null)
        {
            GenericFluidDrinkProperties drinkProperties = FluidDrinkPropertiesRegistry.getProperties(fluidStack);

            tooltip.add(Component.translatable("tooltip.pasterdream.每次饮用:").append(drinkProperties.getDrinkAmount() + "mB").withStyle(ChatFormatting.BLUE));
            FoodValueTooltips.appendSanTooltip(tooltip, drinkProperties.getSanAdd());
            FoodValueTooltips.appendMeltDreamEnergyTooltip(tooltip, drinkProperties.getMeltDreamEnergyAdd());
            if(fluidStack.getFluid() == ModFluids.POTION.get())
            {
                List<GenericMobEffect> effectList = PotionHelper.getEffectType(fluidStack);
                for (GenericMobEffect effect : effectList)
                {
                    MutableComponent result = Component.empty();
                    result.append(PotionHelper.formatTime(effect.time()));
                    result.append(Component.translatable(effect.effectType().getDescriptionId()));
                    if(effect.level() != 0)
                    {
                        result.append(Component.translatable("enchantment.level." + (effect.level() + 1)));
                    }

                    tooltip.add(result.withStyle(effect.effectType().isBeneficial() ? ChatFormatting.BLUE : ChatFormatting.RED));
                }
            }
        }
            else
            {
                tooltip.add(Component.translatable("tooltip.pasterdream.不可饮用").withStyle(ChatFormatting.RED));
            }
    }

    /** 成品药水流体判定：原模组 potion 流体且 EffectList 非空 */
    private static boolean isPotionFluid(FluidStack fluidStack)
    {
        return !fluidStack.isEmpty() && fluidStack.getFluid() == ModFluids.POTION.get()
                && !PotionHelper.getEffectType(fluidStack).isEmpty();
    }

    /** 等级 → 罗马数字（原版 potency 语言键只覆盖到 V，更高等级需自行生成） */
    private static final int[] ROMAN_VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] ROMAN_SYMBOLS =
            {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    private static String toRoman(int level)
    {
        if (level <= 0)
        {
            return String.valueOf(level);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ROMAN_VALUES.length; i++)
        {
            while (level >= ROMAN_VALUES[i])
            {
                sb.append(ROMAN_SYMBOLS[i]);
                level -= ROMAN_VALUES[i];
            }
        }
        return sb.toString();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        FluidStack fluidStack = getElixirBottleFluidStack(itemStack);

        if (!fluidStack.isEmpty() && FluidDrinkPropertiesRegistry.getProperties(fluidStack) != null)
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
        FluidStack fluidStack = getElixirBottleFluidStack(itemStack);
        GenericFluidDrinkProperties drinkProperties = FluidDrinkPropertiesRegistry.getProperties(fluidStack);

        int time = drinkProperties != null ? drinkProperties.getUseDuration() : 32;
        int drinkAmount = drinkProperties != null ? drinkProperties.getDrinkAmount() : 250;

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
            FluidStack fluidStack = getElixirBottleFluidStack(itemStack);
            GenericFluidDrinkProperties drinkProperties = FluidDrinkPropertiesRegistry.getProperties(fluidStack);
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

    public static FluidStack getElixirBottleFluidStack(ItemStack itemStack)
    {
        AtomicReference<FluidStack> atomicReferenceFluidStack = new AtomicReference<>(FluidStack.EMPTY);
        itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> atomicReferenceFluidStack.set(handler.getFluidInTank(0)));
        return atomicReferenceFluidStack.get();
    }
}
