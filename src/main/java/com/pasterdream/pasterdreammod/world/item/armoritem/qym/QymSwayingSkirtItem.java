package com.pasterdream.pasterdreammod.world.item.armoritem.qym;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.IndestructibleItemEntity;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

public class QymSwayingSkirtItem extends ArmorItem {

    public QymSwayingSkirtItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties.fireResistant());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(getDefaultAttributeModifiers(slot));
        if (slot == this.type.getSlot()) {
            builder.put(ModAttributes.SAN_VARIABILITY.get(),
                    new AttributeModifier(UUID.nameUUIDFromBytes(("pasterdream.qym_swaying_skirt." + slot.getName()).getBytes()),
                            "pasterdream.qym_swaying_skirt.san_variability", -1, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        return builder.build();
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(ModRarities.qualityTooltip(ModRarities.MIRACLE));
        list.add(Component.translatable("tooltip.pasterdream.qym_swaying_skirt.energy"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.set_bonus"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.damage_reduce"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.void_damage"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.dream_evasion"));
        super.appendHoverText(stack, level, list, flag);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(Level level, Entity location, ItemStack stack) {
        var entity = new IndestructibleItemEntity(level, location.getX(), location.getY(), location.getZ(), stack);
        entity.setDefaultPickUpDelay();
        entity.setDeltaMovement(location.getDeltaMovement());
        return entity;
    }
}
