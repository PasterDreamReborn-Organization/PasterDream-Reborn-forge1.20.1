package com.pasterdream.pasterdreammod.helper.tooltipadder;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

public class AddToolTip
{
    public static void addTooltip(ItemTooltipEvent event)
    {
        //用于向其他已有物品添加tooltip
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

        if(stack.is(ModItems.MAGIC_STONE.get())){
            tooltip.add(Component.translatable("tooltip.pasterdreammod.magic_stone"));
        }
        if(stack.is(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get())){
            tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_crystal_fragment"));
        }
        if(stack.is(ModItems.TITANIUM_UPGRADE.get()) || stack.is(ModItems.SCULK_UPGRADE.get()) || stack.is(ModItems.DYEDREAM_UPGRADE.get())
                || stack.is(ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get())){
            tooltip.add(Component.translatable("tooltip.pasterdreammod.upgrade_kit"));
        }
        if(stack.is(ModItems.TITANIUM_HELMET.get()) || stack.is(ModItems.TITANIUM_CHESTPLATE.get())
                || stack.is(ModItems.TITANIUM_LEGGINGS.get()) || stack.is(ModItems.TITANIUM_BOOTS.get())){
            tooltip.add(Component.translatable("tooltip.pasterdreammod.titanium_armor.1"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.titanium_armor.2"));
        }
        if(stack.is(ModItems.SCULK_HELMET.get()) || stack.is(ModItems.SCULK_CHESTPLATE.get())
                || stack.is(ModItems.SCULK_LEGGINGS.get()) || stack.is(ModItems.SCULK_BOOTS.get())){
            tooltip.add(Component.translatable("tooltip.pasterdreammod.sculk_armor.1"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.sculk_armor.2"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.sculk_armor.3"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.sculk_armor.4"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.sculk_armor.5"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.sculk_armor.6"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.sculk_armor.7"));
        }
        if(stack.is(ModItems.DYEDREAM_HELMET.get()) || stack.is(ModItems.DYEDREAM_CHESTPLATE.get())
                || stack.is(ModItems.DYEDREAM_LEGGINGS.get()) || stack.is(ModItems.DYEDREAM_BOOTS.get())){
            tooltip.add(Component.translatable("tooltip.pasterdreammod.dyedream_armor.1"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.dyedream_armor.2"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.dyedream_armor.3"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.dyedream_armor.4"));
        }
        if(stack.is(ModItems.BROKEN_NOTE.get())){
            tooltip.add(Component.translatable("tooltip.pasterdream.broken_note"));
        }
        if(stack.is(ModItems.UNKNOWN_NOTE.get())){
            tooltip.add(Component.translatable("tooltip.pasterdream.unknown_note.1"));
            tooltip.add(Component.translatable("tooltip.pasterdream.unknown_note.2"));
        }
        if(stack.is(ModItems.MELT_DREAM_AURORIAN_STEEL.get())){
            tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_aurorian_steel"));
        }
        if(stack.is(ModItems.GLASS_JAR_OF_DREAM_JUICE.get())){
            tooltip.add(Component.translatable("tooltip.pasterdreammod.glass_jar_of_dream_juice"));
        }
        if(stack.is(ModItems.GLASS_CUP_OF_HONEY_JUICE.get())){
            tooltip.add(Component.translatable("tooltip.pasterdreammod.glass_cup_of_honey_juice"));
        }
        if(stack.is(ModItems.LEGENDARY_DRAGON_HORN_ICE_CREAM.get())){
            tooltip.add(Component.translatable("item.pasterdream.legendary_dragon_horn_ice_cream.describe.0"));
            tooltip.add(Component.translatable("item.pasterdream.legendary_dragon_horn_ice_cream.describe.1"));
        }
        if(stack.is(ModItems.RICE_CAKE.get())){
            tooltip.add(Component.translatable("item.pasterdream.rice_cake.describe"));
        }
        if(stack.is(ModItems.WHITE_CRYSTAL.get())){
            tooltip.add(Component.translatable("tooltip.pasterdream.white_crystal"));
        }
        if(stack.is(ModItems.SHADOW_DUNGEON_KEY.get())){
            tooltip.add(Component.translatable("tooltip.pasterdream.shadow_dungeon_key"));
        }
        if(stack.is(ModItems.SHADOW_HILT.get())){
            tooltip.add(Component.translatable("tooltip.pasterdream.shadow_hilt"));
        }
        if(stack.is(ModItems.PURE_HORROR.get())){
            tooltip.add(Component.translatable("tooltip.pasterdream.pure_horror"));
        }
    }
}
