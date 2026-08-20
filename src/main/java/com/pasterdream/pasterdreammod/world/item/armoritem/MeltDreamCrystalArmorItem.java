package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.IndestructibleItemEntity;
import com.pasterdream.pasterdreammod.world.item.ModArmorMaterials;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class MeltDreamCrystalArmorItem extends ArmorItem {

    public MeltDreamCrystalArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties.fireResistant());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(getDefaultAttributeModifiers(slot));
        if (slot == this.type.getSlot()) {
            UUID uuid = UUID.nameUUIDFromBytes(("pasterdream.melt_dream_armor.health." + slot.getName()).getBytes());
            builder.put(Attributes.MAX_HEALTH,
                    new AttributeModifier(uuid,
                            "pasterdream.melt_dream_armor.health", 2.0, AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (!level.isClientSide && slotId == EquipmentSlot.HEAD.getIndex()
                && entity instanceof ServerPlayer player) {
            MeltDreamCrystalArmorHandler.onTick(player);
        }
    }

    public static boolean hasFullSet(LivingEntity entity) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.isArmor()) {
                ItemStack stack = entity.getItemBySlot(slot);
                if (!(stack.getItem() instanceof ArmorItem armorItem
                        && armorItem.getMaterial() == ModArmorMaterials.MELT_DREAM)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_armor.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_armor.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_armor.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_armor.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_armor.5"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_armor.6"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_armor.7"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_armor.8"));
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
