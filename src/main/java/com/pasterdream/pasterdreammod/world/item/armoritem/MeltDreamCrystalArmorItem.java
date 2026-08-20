package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.IndestructibleItemEntity;
import com.pasterdream.pasterdreammod.world.item.ModArmorMaterials;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
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

    private static final UUID HEAD_HEALTH_MODIFIER_UUID = UUID.fromString("178dddc9-e85b-4a89-913c-c63f87f5c8c7");
    private static final UUID CHEST_HEALTH_MODIFIER_UUID = UUID.fromString("c9fb089f-7a55-4452-be81-9f9a5ae3ef00");
    private static final UUID LEGS_HEALTH_MODIFIER_UUID = UUID.fromString("e74483bc-bff9-40a4-a2f2-30548172d097");
    private static final UUID FEET_HEALTH_MODIFIER_UUID = UUID.fromString("cd729cb4-7057-4844-98b9-f7aa6a0c422f");

    private static final UUID HEAD_ENERGY_MODIFIER_UUID = UUID.fromString("3b6c5d1e-8f2a-4c3b-9d1e-2f3a4b5c6d7e");
    private static final UUID CHEST_ENERGY_MODIFIER_UUID = UUID.fromString("4c7d6e2f-9a3b-5d4c-ae2f-3a4b5c6d7e8f");
    private static final UUID LEGS_ENERGY_MODIFIER_UUID = UUID.fromString("5d8e7f3a-ab4c-6e5d-bf3a-4b5c6d7e8f9a");
    private static final UUID FEET_ENERGY_MODIFIER_UUID = UUID.fromString("6e9f8a4b-bc5d-7f6e-ca4b-5c6d7e8f9ab0");

    private static final UUID HEAD_REGENERATION_MODIFIER_UUID = UUID.fromString("7a0b1c2d-3e4f-5a6b-7c8d-9e0f1a2b3c4d");
    private static final UUID CHEST_REGENERATION_MODIFIER_UUID = UUID.fromString("8b1c2d3e-4f5a-6b7c-8d9e-0f1a2b3c4d5e");
    private static final UUID LEGS_REGENERATION_MODIFIER_UUID = UUID.fromString("9c2d3e4f-5a6b-7c8d-9e0f-1a2b3c4d5e6f");
    private static final UUID FEET_REGENERATION_MODIFIER_UUID = UUID.fromString("ad3e4f5a-6b7c-8d9e-0f1a-2b3c4d5e6f7a");

    /** 每件融梦能量上限加成 */
    private static final double MAX_ENERGY_PER_PIECE = 5.0;
    /** 每件每分钟融梦能量回复量（融梦光环） */
    private static final double ENERGY_PER_MIN_PER_PIECE = 0.2;

    public MeltDreamCrystalArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties.fireResistant());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(getDefaultAttributeModifiers(slot));
        if (slot == this.type.getSlot()) {
            builder.put(Attributes.MAX_HEALTH,
                    new AttributeModifier(getHealthModifierUuid(slot),
                            "pasterdream.melt_dream_armor.health", 2.0, AttributeModifier.Operation.ADDITION));
            builder.put(ModAttributes.MAX_MELT_DREAM_ENERGY_EXTRA.get(),
                    new AttributeModifier(getEnergyModifierUuid(slot),
                            "pasterdream.melt_dream_armor.max_energy", MAX_ENERGY_PER_PIECE,
                            AttributeModifier.Operation.ADDITION));
            builder.put(ModAttributes.MELT_DREAM_VARIABILITY.get(),
                    new AttributeModifier(getRegenerationModifierUuid(slot),
                            "pasterdream.melt_dream_armor.melt_dream_variability", ENERGY_PER_MIN_PER_PIECE,
                            AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    private static UUID getHealthModifierUuid(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> HEAD_HEALTH_MODIFIER_UUID;
            case CHEST -> CHEST_HEALTH_MODIFIER_UUID;
            case LEGS -> LEGS_HEALTH_MODIFIER_UUID;
            case FEET -> FEET_HEALTH_MODIFIER_UUID;
            default -> throw new IllegalArgumentException("Unexpected armor slot: " + slot);
        };
    }

    private static UUID getEnergyModifierUuid(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> HEAD_ENERGY_MODIFIER_UUID;
            case CHEST -> CHEST_ENERGY_MODIFIER_UUID;
            case LEGS -> LEGS_ENERGY_MODIFIER_UUID;
            case FEET -> FEET_ENERGY_MODIFIER_UUID;
            default -> throw new IllegalArgumentException("Unexpected armor slot: " + slot);
        };
    }

    private static UUID getRegenerationModifierUuid(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> HEAD_REGENERATION_MODIFIER_UUID;
            case CHEST -> CHEST_REGENERATION_MODIFIER_UUID;
            case LEGS -> LEGS_REGENERATION_MODIFIER_UUID;
            case FEET -> FEET_REGENERATION_MODIFIER_UUID;
            default -> throw new IllegalArgumentException("Unexpected armor slot: " + slot);
        };
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
