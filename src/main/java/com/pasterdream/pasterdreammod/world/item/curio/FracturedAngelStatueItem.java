package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

/**
 * 折翼天使雕像：每次受到伤害时按配置概率获得短暂无敌（概率/时长由 Config 控制，Tooltip 自动同步）；
 * 免疫摔落伤害与鞘翅飞行撞击动能伤害。
 */
public class FracturedAngelStatueItem extends Item implements ICurioItem {

    public FracturedAngelStatueItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.EPIC));
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() != null) {
            return CuriosApi.getCuriosInventory(slotContext.entity()).map(handler ->
                    handler.findFirstCurio(stack.getItem()).isEmpty())
                    .orElse(true);
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.fractured_angel_statue.effect1",
                formatPercent(Config.fracturedAngelStatueInvulnerableChance),
                formatSeconds(Config.fracturedAngelStatueInvulnerableTicks)));
        list.add(Component.translatable("tooltip.pasterdream.fractured_angel_statue.effect2"));
        list.add(Component.translatable("tooltip.pasterdream.fractured_angel_statue.effect3"));
        list.add(Component.translatable("tooltip.pasterdream.fractured_angel_statue.lore"));
    }

    /** 概率转百分比字符串（去除多余的尾零），如 0.1 -> "10" */
    private static String formatPercent(double chance) {
        double pct = chance * 100.0;
        return pct == Math.floor(pct) && !Double.isInfinite(pct)
                ? String.valueOf((long) pct)
                : String.valueOf(pct);
    }

    /** tick 转秒字符串（整秒省略小数），如 20 -> "1"，30 -> "1.5" */
    private static String formatSeconds(int ticks) {
        double seconds = ticks / 20.0;
        return seconds == Math.floor(seconds) && !Double.isInfinite(seconds)
                ? String.valueOf((long) seconds)
                : String.valueOf(seconds);
    }
}