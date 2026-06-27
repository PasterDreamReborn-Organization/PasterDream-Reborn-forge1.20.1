package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DyedreamArmorItem extends ArmorItem {

    public DyedreamArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide && slotId == EquipmentSlot.HEAD.getIndex()
                && entity instanceof Player player && hasFullSet(player)) {
            player.addEffect(new MobEffectInstance(ModEffects.DYEDREAM_ARMOR_BUFF.get(), 25, 0,
                    true, false, false));
            player.addEffect(new MobEffectInstance(ModEffects.DYEDREAM_UP_BUFF.get(), 25, 0,
                    true, false, false));
        }
    }

    public static boolean hasFullSet(LivingEntity entity) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.isArmor()) {
                ItemStack stack = entity.getItemBySlot(slot);
                if (!(stack.getItem() instanceof ArmorItem armorItem
                        && armorItem.getMaterial() == ModArmorMaterials.DYEDREAM)) {
                    return false;
                }
            }
        }
        return true;
    }
}
