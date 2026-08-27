package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
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

public class FeatherNecklaceItem extends Item implements ICurioItem {
    private static final UUID CONSUME_MODIFIER_UUID = UUID.fromString("c017c046-643b-4b28-a9a0-7e8134b54900");
    private static final UUID RANGE_MODIFIER_UUID = UUID.fromString("53c0c497-af95-43c7-9d82-46a5916dc442");

    public FeatherNecklaceItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.EXCELLENT));
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance consumeAttr = slotContext.entity().getAttribute(ModAttributes.BLINK_CONSUME.get());
            if (consumeAttr != null && consumeAttr.getModifier(CONSUME_MODIFIER_UUID) == null) {
                consumeAttr.addPermanentModifier(new AttributeModifier(CONSUME_MODIFIER_UUID,
                        "Feather necklace consume", -0.05, AttributeModifier.Operation.ADDITION));
            }
            AttributeInstance rangeAttr = slotContext.entity().getAttribute(ModAttributes.BLINK_RANGE.get());
            if (rangeAttr != null && rangeAttr.getModifier(RANGE_MODIFIER_UUID) == null) {
                rangeAttr.addPermanentModifier(new AttributeModifier(RANGE_MODIFIER_UUID,
                        "Feather necklace range", 0.2, AttributeModifier.Operation.ADDITION));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance consumeAttr = slotContext.entity().getAttribute(ModAttributes.BLINK_CONSUME.get());
            if (consumeAttr != null) {
                consumeAttr.removeModifier(CONSUME_MODIFIER_UUID);
            }
            AttributeInstance rangeAttr = slotContext.entity().getAttribute(ModAttributes.BLINK_RANGE.get());
            if (rangeAttr != null) {
                rangeAttr.removeModifier(RANGE_MODIFIER_UUID);
            }
        }
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
        list.add(Component.translatable("tooltip.pasterdream.feather_necklace.effect1"));
        list.add(Component.translatable("tooltip.pasterdream.feather_necklace.effect2"));
    }
}
