package com.pasterdream.pasterdreammod.world.item.curio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class CarapaxCharmItem extends Item implements ICurioItem {
    private static final UUID MOVEMENT_SPEED_UUID = UUID.fromString("2c5819a9-794c-4b46-803e-966ae56c55c9");
    private static final UUID ARMOR_UUID = UUID.fromString("9d414944-d593-4e51-b772-cfe163f26f17");
    private static final UUID ARMOR_TOUGHNESS_UUID = UUID.fromString("73ad191c-4d7e-4852-9016-7d33e523e3f5");

    public CarapaxCharmItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.EXCELLENT));
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
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext,
                                                                         UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(Attributes.MOVEMENT_SPEED,
                new AttributeModifier(MOVEMENT_SPEED_UUID, "Carapax charm movement speed", -0.08F, AttributeModifier.Operation.MULTIPLY_BASE));
        modifiers.put(Attributes.ARMOR,
                new AttributeModifier(ARMOR_UUID, "Carapax charm armor", 2.0F, AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.ARMOR_TOUGHNESS,
                new AttributeModifier(ARMOR_TOUGHNESS_UUID, "Carapax charm armor toughness", 1.0F, AttributeModifier.Operation.ADDITION));
        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
    }
}
