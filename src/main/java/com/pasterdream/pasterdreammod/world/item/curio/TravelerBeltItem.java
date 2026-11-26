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
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class TravelerBeltItem extends Item implements ICurioItem {
    private static final UUID BLINK_CONSUME_UUID = UUID.fromString("e54ab0c4-1bb8-476e-bb61-e7cb6830c4d2");

    public TravelerBeltItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.COMMON));
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance attr = slotContext.entity().getAttribute(ModAttributes.BLINK_CONSUME.get());
            if (attr != null && attr.getModifier(BLINK_CONSUME_UUID) == null) {
                attr.addPermanentModifier(new AttributeModifier(BLINK_CONSUME_UUID,
                        "Traveler belt blink consume", -0.5, AttributeModifier.Operation.ADDITION));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance attr = slotContext.entity().getAttribute(ModAttributes.BLINK_CONSUME.get());
            if (attr != null) {
                attr.removeModifier(BLINK_CONSUME_UUID);
            }
        }
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        // 读档时 Curios 会把槽里那一件重新校验一遍，判重必须放行自身槽位；详见 CurioEquipHelper。
        return CurioEquipHelper.allowsSingleCopy(slotContext, stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.traveler_belt.effect"));
    }
}
