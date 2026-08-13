package com.pasterdream.pasterdreammod.world.item.shadowerosiontool;

import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class ShadowErosionDaggerItem extends SwordItem implements ShadowErosionTool {

    public ShadowErosionDaggerItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties.fireResistant().rarity(Rarity.RARE));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return ShadowErosionToolHelper.withSanVariability(slot, super.getAttributeModifiers(slot, stack));
    }
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        ShadowErosionToolHelper.appendHoverText(stack, level, tooltip, flag);
    }
}
