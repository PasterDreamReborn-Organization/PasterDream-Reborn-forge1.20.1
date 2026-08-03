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
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class SealOfTheCorruptedItem extends Item implements ICurioItem {

    private static final UUID ENTITY_REACH_UUID = UUID.fromString("b84e7f10-74e3-43f5-95f9-968877248549");
    private static final UUID BLOCK_REACH_UUID = UUID.fromString("f55792ba-17c1-43bb-86ac-ff805d06ce3c");
    private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("4b798e40-aac3-43a2-b93b-c927ec3a2c59");

    public SealOfTheCorruptedItem() {
        super(new Properties().stacksTo(1).rarity(ModRarities.EPIC));
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() != null) {
            return CuriosApi.getCuriosInventory(slotContext.entity())
                    .map(handler -> handler.findFirstCurio(stack.getItem()).isEmpty())
                    .orElse(true);
        }
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext,
                                                                         UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(ForgeMod.ENTITY_REACH.get(),
                new AttributeModifier(ENTITY_REACH_UUID, "seal_of_the_corrupted.entity_reach",
                        1.0, AttributeModifier.Operation.ADDITION));
        modifiers.put(ForgeMod.BLOCK_REACH.get(),
                new AttributeModifier(BLOCK_REACH_UUID, "seal_of_the_corrupted.block_reach",
                        1.0, AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(ATTACK_DAMAGE_UUID, "seal_of_the_corrupted.attack_damage",
                        2.0, AttributeModifier.Operation.ADDITION));
        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(ModRarities.qualityTooltip(ModRarities.EPIC));
        list.add(Component.translatable("tooltip.pasterdream.seal_of_the_corrupted.effect1"));
        list.add(Component.translatable("tooltip.pasterdream.seal_of_the_corrupted.effect2"));
        list.add(Component.translatable("tooltip.pasterdream.seal_of_the_corrupted.effect3"));
        list.add(Component.translatable("tooltip.pasterdream.seal_of_the_corrupted.flavor"));
    }
}
