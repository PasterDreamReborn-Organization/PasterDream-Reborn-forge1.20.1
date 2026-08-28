package com.pasterdream.pasterdreammod.world.item.curio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
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

public class AllkindsRingItem extends Item implements ICurioItem {
    private static final UUID HEALTH_UUID = UUID.fromString("0673e4be-d230-11ed-8917-325096b39f47");
    private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("0673e536-d230-11ed-8130-325096b39f47");
    private static final UUID ATTACK_SPEED_UUID = UUID.fromString("0673e5a4-d230-11ed-8bb0-325096b39f47");
    private static final UUID ENTITY_REACH_UUID = UUID.fromString("0673e608-d230-11ed-8948-325096b39f47");
    private static final UUID BLOCK_REACH_UUID = UUID.fromString("0673e66c-d230-11ed-aa81-325096b39f47");
    private static final UUID MOVEMENT_SPEED_UUID = UUID.fromString("0673e6c6-d230-11ed-9244-325096b39f47");
    private static final UUID SKILL_CD_UUID = UUID.fromString("0673e734-d230-11ed-8007-325096b39f47");
    private static final UUID SKILL_MULTIPLIER_UUID = UUID.fromString("0673e798-d230-11ed-b8cb-325096b39f47");
    private static final UUID BLINK_CD_UUID = UUID.fromString("54d5eebc-06cc-4ada-87a5-c04247da2506");

    public AllkindsRingItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.LEGENDARY));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(Attributes.MAX_HEALTH,
                new AttributeModifier(HEALTH_UUID, "Allkinds ring health", 4.0, AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(ATTACK_DAMAGE_UUID, "Allkinds ring attack damage", 2.0, AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.ATTACK_SPEED,
                new AttributeModifier(ATTACK_SPEED_UUID, "Allkinds ring attack speed", 0.1, AttributeModifier.Operation.ADDITION));
        modifiers.put(ForgeMod.ENTITY_REACH.get(),
                new AttributeModifier(ENTITY_REACH_UUID, "Allkinds ring entity reach", 0.2, AttributeModifier.Operation.ADDITION));
        modifiers.put(ForgeMod.BLOCK_REACH.get(),
                new AttributeModifier(BLOCK_REACH_UUID, "Allkinds ring block reach", 0.5, AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.MOVEMENT_SPEED,
                new AttributeModifier(MOVEMENT_SPEED_UUID, "Allkinds ring movement speed", 0.05, AttributeModifier.Operation.MULTIPLY_BASE));
        return modifiers;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance skillCd = slotContext.entity().getAttribute(ModAttributes.SKILL_COOLDOWN_RATE.get());
            if (skillCd != null && skillCd.getModifier(SKILL_CD_UUID) == null) {
                skillCd.addPermanentModifier(new AttributeModifier(SKILL_CD_UUID,
                        "Allkinds ring skill cooldown", -0.05, AttributeModifier.Operation.ADDITION));
            }
            AttributeInstance skillDmg = slotContext.entity().getAttribute(ModAttributes.SKILL_DAMAGE_RATE.get());
            if (skillDmg != null && skillDmg.getModifier(SKILL_MULTIPLIER_UUID) == null) {
                skillDmg.addPermanentModifier(new AttributeModifier(SKILL_MULTIPLIER_UUID,
                        "Allkinds ring skill damage", 0.05, AttributeModifier.Operation.ADDITION));
            }
            AttributeInstance blinkCd = slotContext.entity().getAttribute(ModAttributes.BLINK_CD.get());
            if (blinkCd != null && blinkCd.getModifier(BLINK_CD_UUID) == null) {
                blinkCd.addPermanentModifier(new AttributeModifier(BLINK_CD_UUID,
                        "Allkinds ring blink cooldown", -0.05, AttributeModifier.Operation.ADDITION));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance skillCd = slotContext.entity().getAttribute(ModAttributes.SKILL_COOLDOWN_RATE.get());
            if (skillCd != null) skillCd.removeModifier(SKILL_CD_UUID);
            AttributeInstance skillDmg = slotContext.entity().getAttribute(ModAttributes.SKILL_DAMAGE_RATE.get());
            if (skillDmg != null) skillDmg.removeModifier(SKILL_MULTIPLIER_UUID);
            AttributeInstance blinkCd = slotContext.entity().getAttribute(ModAttributes.BLINK_CD.get());
            if (blinkCd != null) blinkCd.removeModifier(BLINK_CD_UUID);
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
        list.add(Component.translatable("tooltip.pasterdream.allkinds_ring.flavor"));
        list.add(Component.translatable("tooltip.pasterdream.allkinds_ring.lore"));
        list.add(Component.translatable("tooltip.pasterdream.allkinds_ring.skill_cd"));
        list.add(Component.translatable("tooltip.pasterdream.allkinds_ring.skill_dmg"));
        list.add(Component.translatable("tooltip.pasterdream.allkinds_ring.blink_cd"));
    }
}
