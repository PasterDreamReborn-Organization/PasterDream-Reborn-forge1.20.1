package com.pasterdream.pasterdreammod.world.item.curio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.init.ModAttributes;
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

public class HiyoriButterflyHairpinItem extends Item implements ICurioItem {
    private static final UUID MAX_HEALTH_UUID = UUID.fromString("7db7368f-03bf-46cb-b7ae-ca8b2777f4f7");
    private static final UUID LUCK_UUID = UUID.fromString("8ec8479e-14c0-57dc-c8bf-db9c3888e5f8");
    private static final UUID SAN_VARIABILITY_UUID = UUID.fromString("9fd958af-25d1-68ed-d9c0-ec0d4999f6a9");

    public HiyoriButterflyHairpinItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.EPIC));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(Attributes.MAX_HEALTH,
                new AttributeModifier(MAX_HEALTH_UUID, "Hiyori butterfly hairpin health", 2.0, AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.LUCK,
                new AttributeModifier(LUCK_UUID, "Hiyori butterfly hairpin luck", 15.0, AttributeModifier.Operation.ADDITION));
        modifiers.put(ModAttributes.SAN_VARIABILITY.get(),
                new AttributeModifier(SAN_VARIABILITY_UUID, "Hiyori butterfly hairpin SAN variability", 0.96, AttributeModifier.Operation.ADDITION));
        return modifiers;
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
        list.add(Component.translatable("tooltip.pasterdream.hiyori_butterfly_hairpin.lore"));
    }
}
