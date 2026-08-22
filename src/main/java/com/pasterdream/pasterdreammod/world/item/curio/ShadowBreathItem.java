package com.pasterdream.pasterdreammod.world.item.curio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class ShadowBreathItem extends Item implements ICurioItem {
    private static final String TAG_LEVEL = "pasterdream.shadowbreath.level";

    public ShadowBreathItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(ModRarities.qualityTooltip(ModRarities.SUPERIOR));
        tooltip.add(Component.translatable("tooltip.pasterdream.shadow_breath.0").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("tooltip.pasterdream.shadow_breath.1").withStyle(ChatFormatting.BLUE));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        var entity = slotContext.entity();
        if (entity.level().isClientSide()) return;

        int level = 0;
        if (entity instanceof ServerPlayer sp) {
            var san = sp.getCapability(ModCapabilities.SAN).resolve();
            if (san.isPresent()) {
                double maxSan = san.get().getMaxSanValue();
                if (maxSan > 0) {
                    double sanRatio = san.get().getSanValue() / maxSan;
                    if (sanRatio <= 0.2) {
                        level = 3;
                    } else if (sanRatio <= 0.4) {
                        level = 2;
                    } else if (sanRatio <= 0.6) {
                        level = 1;
                    }
                }
            }
        }
        if (level > 0) {
            stack.getOrCreateTag().putInt(TAG_LEVEL, level);
        } else {
            stack.getOrCreateTag().remove(TAG_LEVEL);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        int num = stack.getTag() != null ? stack.getTag().getInt(TAG_LEVEL) : 0;
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        if (num > 0) {
            map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid,
                    "pasterdream.shadow_breath", 1 << (num - 1), AttributeModifier.Operation.ADDITION));
        }
        return map;
    }
}