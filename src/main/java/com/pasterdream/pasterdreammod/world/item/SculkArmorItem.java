package com.pasterdream.pasterdreammod.world.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class SculkArmorItem extends ArmorItem {

    public SculkArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties.fireResistant());
    }

    public static boolean hasFullSet(LivingEntity entity) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.isArmor()) {
                ItemStack stack = entity.getItemBySlot(slot);
                if (!(stack.getItem() instanceof SculkArmorItem)) {
                    return false;
                }
            }
        }
        return true;
    }
}
