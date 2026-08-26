package com.pasterdream.pasterdreammod.world.item.curio;

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

public class FourleafCloverCurioItem extends Item implements ICurioItem {
    private static final UUID HEALTH_UUID = UUID.fromString("055dac74-49cf-474c-9078-f658a61f7047");
    private static final UUID LUCK_UUID = UUID.fromString("f723cde2-ecbf-45d1-b985-8670b2f00fd2");

    public FourleafCloverCurioItem() {
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
            var healthAttr = entity.getAttribute(Attributes.MAX_HEALTH);
            if (healthAttr != null && healthAttr.getModifier(HEALTH_UUID) == null) {
                healthAttr.addPermanentModifier(new AttributeModifier(HEALTH_UUID, "Fourleaf clover health", 1.0, AttributeModifier.Operation.ADDITION));
            }
            var luckAttr = entity.getAttribute(Attributes.LUCK);
            if (luckAttr != null && luckAttr.getModifier(LUCK_UUID) == null) {
                luckAttr.addPermanentModifier(new AttributeModifier(LUCK_UUID, "Fourleaf clover luck", 6.0, AttributeModifier.Operation.ADDITION));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var healthAttr = entity.getAttribute(Attributes.MAX_HEALTH);
            if (healthAttr != null) {
                healthAttr.removeModifier(HEALTH_UUID);
            }
            var luckAttr = entity.getAttribute(Attributes.LUCK);
            if (luckAttr != null) {
                luckAttr.removeModifier(LUCK_UUID);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.fourleaf_clover_curio.effect.health"));
        list.add(Component.translatable("tooltip.pasterdream.fourleaf_clover_curio.effect.luck"));
        list.add(Component.translatable("tooltip.pasterdream.fourleaf_clover_curio.flavor"));
    }
}
