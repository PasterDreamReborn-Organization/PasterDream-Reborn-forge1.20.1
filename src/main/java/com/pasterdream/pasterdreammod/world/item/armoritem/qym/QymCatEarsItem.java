package com.pasterdream.pasterdreammod.world.item.armoritem.qym;

import com.pasterdream.pasterdreammod.capability.san.ISanModifier;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.helper.DreamDimensionHelper;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.world.item.IndestructibleItemEntity;
import com.pasterdream.pasterdreammod.world.item.ModArmorMaterials;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
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

public class QymCatEarsItem extends ArmorItem implements ISanModifier {

    public QymCatEarsItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties.fireResistant());
    }

    @Override
    public boolean freezesSan() {
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(getDefaultAttributeModifiers(slot));
        if (slot == this.type.getSlot()) {
            builder.put(ModAttributes.SAN_VARIABILITY.get(),
                    new AttributeModifier(UUID.nameUUIDFromBytes(("pasterdream.qym_cat_ears." + slot.getName()).getBytes()),
                            "pasterdream.qym_cat_ears.san_variability", -1, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        return builder.build();
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(ModRarities.qualityTooltip(ModRarities.MIRACLE));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.san"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.set_bonus"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.damage_reduce"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.magic_damage"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.dream_evasion"));
        super.appendHoverText(stack, level, list, flag);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean selected) {
        super.inventoryTick(stack, level, entity, slotId, selected);
        if (!level.isClientSide && entity instanceof ServerPlayer sp
                && sp.getItemBySlot(EquipmentSlot.HEAD) == stack) {
            SanHelper.setPlayerSanAndSync(sp, SanHelper.getPlayerMaxSan(sp));
            if (hasFullSet(sp) && DreamDimensionHelper.isDreamDimension(sp.level())) {
                sp.addEffect(new MobEffectInstance(ModEffects.CECILIA_BLESSING_BUFF.get(), 20, 0, false, false));
            }
        }
    }

    public static boolean hasFullSet(LivingEntity entity) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.isArmor()) {
                ItemStack s = entity.getItemBySlot(slot);
                if (!(s.getItem() instanceof ArmorItem armorItem
                        && armorItem.getMaterial() == ModArmorMaterials.QYM)) {
                    return false;
                }
            }
        }
        return true;
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
