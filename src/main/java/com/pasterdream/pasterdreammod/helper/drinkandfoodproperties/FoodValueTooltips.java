package com.pasterdream.pasterdreammod.helper.drinkandfoodproperties;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

/**
 * 食物/饮品的 SAN 值与融梦能量 ToolTip 共享格式化工具，
 * 供本模组食物物品与 KubeJS 外部食物属性共用，保证显示格式一致。
 */
public final class FoodValueTooltips
{
    private FoodValueTooltips()
    {
    }

    /**
     * 追加 SAN 值 ToolTip 行（0 时不追加）。
     */
    public static void appendSanTooltip(List<Component> tooltip, double sanAdd)
    {
        if (sanAdd == 0)
        {
            return;
        }
        tooltip.add(Component.translatable("tooltip.pasterdreammod.san_value").withStyle(ChatFormatting.BLUE)
                .append(Component.literal(formatValue(sanAdd)).withStyle(sanAdd < 0 ? ChatFormatting.RED : ChatFormatting.BLUE)));
    }

    /**
     * 追加融梦能量 ToolTip 行（0 时不追加）。
     */
    public static void appendMeltDreamEnergyTooltip(List<Component> tooltip, double meltDreamEnergyAdd)
    {
        if (meltDreamEnergyAdd == 0)
        {
            return;
        }
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_energy").withStyle(ChatFormatting.BLUE)
                .append(Component.literal(formatValue(meltDreamEnergyAdd)).withStyle(ChatFormatting.BLUE)));
    }

    /**
     * 追加料理 buff（COOK）ToolTip 行：时长与等级的显示走 {@link MobEffectInstance}，
     * 格式与本模组食物效果行一致。
     */
    public static void appendCookTooltip(List<Component> tooltip, int cookTicks, int cookAmplifier)
    {
        MobEffectInstance instance = new MobEffectInstance(ModEffects.COOK.get(), cookTicks, cookAmplifier);
        Component text = Component.translatable(instance.getDescriptionId());
        if (instance.getAmplifier() > 0)
        {
            text = Component.translatable("potion.withAmplifier", text,
                    Component.translatable("potion.potency." + instance.getAmplifier()));
        }
        if (!instance.endsWithin(20))
        {
            text = Component.translatable("potion.withDuration", text,
                    Component.literal(formatTickDuration(instance.getDuration())));
        }
        tooltip.add(text.copy().withStyle(instance.getEffect().getCategory().getTooltipFormatting()));
    }

    private static String formatTickDuration(int ticks)
    {
        int seconds = ticks / 20;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return minutes > 0 ? String.format("%d:%02d", minutes, seconds) : String.format("0:%02d", seconds);
    }

    public static String formatValue(double value)
    {
        if (value == (long) value)
        {
            return String.format("%+d", (long) value);
        }
        return String.format("%+.1f", value);
    }
}