package com.pasterdream.pasterdreammod.world.item.drinkandfooditem;

import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.PasterDreamDrinkAndFoodProperties;
import com.pasterdream.pasterdreammod.network.meltdreamenergy.MeltDreamEnergySyncPacket;
import com.pasterdream.pasterdreammod.network.san.SanSyncPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PasterDreamFoodItem extends Item
{
    private final int useDuration;
    private final double sanAdd;
    private final double meltDreamEnergyAdd;

    public PasterDreamFoodItem(PasterDreamDrinkAndFoodProperties properties)
    {
        super(properties);
        this.useDuration = properties.getUseDuration();
        this.sanAdd = properties.getSanAdd();
        this.meltDreamEnergyAdd = properties.getMeltDreamEnergyAdd();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(stack, level, tooltip, flag);

        if(sanAdd != 0)
        {
            tooltip.add(Component.translatable("tooltip.pasterdreammod.san_value").withStyle(ChatFormatting.BLUE).append(Component.literal(formatValue(sanAdd)).withStyle(sanAdd < 0 ? ChatFormatting.RED : ChatFormatting.BLUE)));
        }

        if(meltDreamEnergyAdd != 0)
        {
            tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_energy").withStyle(ChatFormatting.BLUE).append(Component.literal(formatValue(meltDreamEnergyAdd))).withStyle(ChatFormatting.BLUE));
        }
    }

    private static String formatValue(double value)
    {
        if (value == (long) value)
        {
            return String.format("%+d", (long) value);
        }
        return String.format("%+.1f", value);
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return useDuration;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack)
    {
        return UseAnim.EAT;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity)
    {
        FoodProperties food = stack.getFoodProperties(entity);
        if (food != null && entity instanceof Player player)
        {
            player.getFoodData().eat(food.getNutrition(), food.getSaturationModifier());
            for (var effectPair : food.getEffects())
            {
                if (level.random.nextFloat() < effectPair.getSecond())
                {
                    entity.addEffect(new MobEffectInstance(effectPair.getFirst()));
                }
            }
        }

        if (!level.isClientSide && entity instanceof Player player)
        {
            if (sanAdd != 0)
            {
                player.getCapability(ModCapabilities.SAN).ifPresent(capability ->
                {
                    capability.addSanValue(sanAdd);
                    SanSyncPacket.sendToPlayer(player, capability);
                });
            }

            if (meltDreamEnergyAdd != 0)
            {
                player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability ->
                {
                    capability.addMeltDreamEnergy(meltDreamEnergyAdd);
                    MeltDreamEnergySyncPacket.sendToPlayer(player, capability);
                });
            }
        }

        if (!level.isClientSide && entity instanceof Player player)
        {
            onFoodSpecial(player, level);
        }

        if (entity instanceof Player player && player.isCreative())
        {
            return stack;
        }

        stack.shrink(1);
        if (stack.isEmpty())
        {
            return ItemStack.EMPTY;
        }
        return stack;
    }

    protected void onFoodSpecial(Player player, Level level)
    {
        //默认无操作
    }
}
