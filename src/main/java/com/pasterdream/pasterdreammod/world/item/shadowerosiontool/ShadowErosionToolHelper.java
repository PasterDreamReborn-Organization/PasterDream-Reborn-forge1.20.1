package com.pasterdream.pasterdreammod.world.item.shadowerosiontool;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

import java.util.List;
import java.util.UUID;

/**
 * 影蚀工具共享逻辑 —— 主手时附加 SAN 波动属性修正（原版为 -1.2）。
 */
final class ShadowErosionToolHelper {

    private static final UUID SAN_VARIABILITY_UUID = UUID.fromString("daf6c47c-454a-48e4-a05d-4d7fb0deb673");
    private static final String SAN_VARIABILITY_NAME = "pasterdream.shadow_erosion.san_variability";
    private static final double SAN_VARIABILITY_AMOUNT = -1.2;
    private static final UUID BLOCK_REACH_UUID = UUID.fromString("4828780d-fdf1-4ed4-b6ce-71925026f828");
    private static final String BLOCK_REACH_NAME = "pasterdream.shadow_erosion.block_reach";
    private static final double BLOCK_REACH_AMOUNT = 1;

    static Multimap<Attribute, AttributeModifier> withSanVariability(
            EquipmentSlot slot, Multimap<Attribute, AttributeModifier> defaults) {
        if (slot != EquipmentSlot.MAINHAND) {
            return defaults;
        }
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(defaults);
        builder.put(ModAttributes.SAN_VARIABILITY.get(),
                new AttributeModifier(SAN_VARIABILITY_UUID, SAN_VARIABILITY_NAME,
                        SAN_VARIABILITY_AMOUNT, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.BLOCK_REACH.get(),
                new AttributeModifier(BLOCK_REACH_UUID, BLOCK_REACH_NAME,
                        BLOCK_REACH_AMOUNT, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }
    static void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_erosion_tool.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_erosion_tool.2"));
    }
}
