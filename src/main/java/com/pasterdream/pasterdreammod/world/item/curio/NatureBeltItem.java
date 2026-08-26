package com.pasterdream.pasterdreammod.world.item.curio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class NatureBeltItem extends Item implements ICurioItem {
    private static final UUID SAN_VARIABILITY_UUID = UUID.fromString("3e8f07a2-b5d1-4b29-9c6e-7f143e2d9a10");

    public NatureBeltItem() {
        super(new Item.Properties().stacksTo(1).durability(480).rarity(ModRarities.COMMON));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(ModAttributes.SAN_VARIABILITY.get(),
                new AttributeModifier(SAN_VARIABILITY_UUID, "Nature belt sanity variability", 0.48, AttributeModifier.Operation.ADDITION));
        return modifiers;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (Mth.nextDouble(slotContext.entity().getRandom(), 0, 1) < 0.004) {
            if (stack.hurt(1, RandomSource.create(), null)) {
                stack.shrink(1);
                stack.setDamageValue(0);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
    }
}
