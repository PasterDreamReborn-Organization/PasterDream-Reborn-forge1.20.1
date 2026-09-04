package com.pasterdream.pasterdreammod.world.item.curio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

/**
 * 风精灵：佩戴后，玩家发射的弹射物速度提高 50%。
 */
public class WindSpiritItem extends Item implements ICurioItem {

    private static final UUID ARROW_VELOCITY_UUID = UUID.fromString("5d2a7f3e-9b1c-4d6e-8a0f-2c3b4d5e6f7a");

    public WindSpiritItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.SUPERIOR));
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
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(ModAttributes.ARROW_VELOCITY.get(),
                new AttributeModifier(ARROW_VELOCITY_UUID,
                        "pasterdream.wind_spirit.arrow_velocity",
                        0.5, AttributeModifier.Operation.ADDITION));
        return modifiers;
    }
}