package com.pasterdream.pasterdreammod.world.item.curio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class IridescentFairyJadeItem extends Item implements ICurioItem {
    private static final UUID ARROW_VELOCITY_UUID = UUID.fromString("7f3b4c5d-6e7f-8a0b-1c2d-3e4f5a6b7c8d");

    public IridescentFairyJadeItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.MIRACLE));
    }
    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() != null) {
            return CuriosApi.getCuriosInventory(slotContext.entity()).map(handler ->
                            handler.findFirstCurio(this).isEmpty())
                    .orElse(true);
        }
        return true;
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(ModAttributes.ARROW_VELOCITY.get(),
                new AttributeModifier(ARROW_VELOCITY_UUID,
                        "pasterdream.iridescent_fairy_jade.arrow_velocity",
                        0.5, AttributeModifier.Operation.ADDITION));
        return modifiers;
    }
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.iridescent_fairy_jade.1"));
        list.add(Component.translatable("tooltip.pasterdream.iridescent_fairy_jade.2"));
        list.add(Component.translatable("tooltip.pasterdream.iridescent_fairy_jade.3"));
        list.add(Component.translatable("tooltip.pasterdream.iridescent_fairy_jade.4"));
        list.add(Component.translatable("tooltip.pasterdream.iridescent_fairy_jade.5"));
        list.add(Component.translatable("tooltip.pasterdream.iridescent_fairy_jade.6"));
        list.add(Component.translatable("tooltip.pasterdream.iridescent_fairy_jade.7"));
        list.add(Component.translatable("tooltip.pasterdream.iridescent_fairy_jade.8"));
    }
}
