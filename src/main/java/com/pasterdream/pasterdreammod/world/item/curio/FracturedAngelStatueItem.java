package com.pasterdream.pasterdreammod.world.item.curio;

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
 * 折翼天使雕像：每次受到伤害时有 10% 概率获得 1 秒无敌；
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
        list.add(Component.translatable("tooltip.pasterdream.fractured_angel_statue.effect1"));
        list.add(Component.translatable("tooltip.pasterdream.fractured_angel_statue.effect2"));
        list.add(Component.translatable("tooltip.pasterdream.fractured_angel_statue.effect3"));
        list.add(Component.translatable("tooltip.pasterdream.fractured_angel_statue.lore"));
    }
}