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
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class SeaCharmItem extends Item implements ICurioItem {
    private static final UUID ATTRIBUTE_UUID = UUID.fromString("51284211-4ad4-497a-b588-cea181a65e15");

    public SeaCharmItem() {
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
        modifiers.put(ForgeMod.SWIM_SPEED.get(),
                new AttributeModifier(ATTRIBUTE_UUID, "Sea charm swim speed", 0.3F, AttributeModifier.Operation.ADDITION));
        modifiers.put(ModAttributes.SAN_VARIABILITY.get(),
                new AttributeModifier(ATTRIBUTE_UUID, "Sea charm sanity variability", 0.96, AttributeModifier.Operation.ADDITION));
        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
    }
}
