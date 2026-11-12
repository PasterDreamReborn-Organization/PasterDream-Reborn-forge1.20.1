package com.pasterdream.pasterdreammod.world.item.shadowalloytool;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;

public class ShadowAlloyAxeItem extends AxeItem implements ShadowAlloyTool {

    public ShadowAlloyAxeItem(Tier tier, float damage, float speed, Properties properties) {
        super(tier, damage, speed, properties.fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return ShadowAlloyToolHelper.withSanVariability(slot, super.getAttributeModifiers(slot, stack));
    }
}
