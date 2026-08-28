package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
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

public class DreamTravelerBeltItem extends Item implements ICurioItem {
    private static final UUID MOVEMENT_SPEED_UUID = UUID.fromString("90b8eab4-a72e-4182-9129-690bbd6495f9");
    private static final UUID BLINK_CD_UUID = UUID.fromString("54fe729a-ac7d-4985-8c91-9bf70562e890");

    public DreamTravelerBeltItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.SUPERIOR));
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance speedAttr = slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null && speedAttr.getModifier(MOVEMENT_SPEED_UUID) == null) {
                speedAttr.addPermanentModifier(new AttributeModifier(MOVEMENT_SPEED_UUID,
                        "Dream traveler belt movement speed", 0.03, AttributeModifier.Operation.MULTIPLY_BASE));
            }
            AttributeInstance cdAttr = slotContext.entity().getAttribute(ModAttributes.BLINK_CD.get());
            if (cdAttr != null && cdAttr.getModifier(BLINK_CD_UUID) == null) {
                cdAttr.addPermanentModifier(new AttributeModifier(BLINK_CD_UUID,
                        "Dream traveler belt blink cd", -0.1, AttributeModifier.Operation.ADDITION));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance speedAttr = slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null) {
                speedAttr.removeModifier(MOVEMENT_SPEED_UUID);
            }
            AttributeInstance cdAttr = slotContext.entity().getAttribute(ModAttributes.BLINK_CD.get());
            if (cdAttr != null) {
                cdAttr.removeModifier(BLINK_CD_UUID);
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
        list.add(Component.translatable("tooltip.pasterdream.dream_traveler_belt.effect1"));
        list.add(Component.translatable("tooltip.pasterdream.dream_traveler_belt.effect2"));
    }
}
