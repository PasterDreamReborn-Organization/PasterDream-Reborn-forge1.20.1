package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
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

public class DukeCoinCurioItem extends Item implements ICurioItem {
    private static final UUID LUCK_UUID = UUID.fromString("da309091-9a17-45e1-8366-2b1a0bd55625");
    private static final UUID BLINK_CD_UUID = UUID.fromString("da309091-9a17-45e1-8366-2b1a0bd55626");

    public DukeCoinCurioItem() {
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
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var luckAttr = entity.getAttribute(Attributes.LUCK);
            if (luckAttr != null && luckAttr.getModifier(LUCK_UUID) == null) {
                luckAttr.addPermanentModifier(new AttributeModifier(LUCK_UUID, "Duke coin luck", 7.0, AttributeModifier.Operation.ADDITION));
            }
            var blinkCdAttr = entity.getAttribute(ModAttributes.BLINK_CD.get());
            if (blinkCdAttr != null && blinkCdAttr.getModifier(BLINK_CD_UUID) == null) {
                blinkCdAttr.addPermanentModifier(new AttributeModifier(BLINK_CD_UUID, "Duke coin blink cooldown", 7.0, AttributeModifier.Operation.ADDITION));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var luckAttr = entity.getAttribute(Attributes.LUCK);
            if (luckAttr != null) {
                luckAttr.removeModifier(LUCK_UUID);
            }
            var blinkCdAttr = entity.getAttribute(ModAttributes.BLINK_CD.get());
            if (blinkCdAttr != null) {
                blinkCdAttr.removeModifier(BLINK_CD_UUID);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.duke_coin_curio.effect.luck"));
        list.add(Component.translatable("tooltip.pasterdream.duke_coin_curio.effect.blink_cd"));
        list.add(Component.translatable("tooltip.pasterdream.duke_coin_curio.flavor"));
    }
}
