package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class StrikeRingItem extends Item implements ICurioItem {
    public static final String TAG_LV = "lv";
    private static final UUID ATTACK_DAMAGE_MODIFIER_UUID = UUID.fromString("c83e6d68-2a1e-4155-9126-bcdead70e732");

    public StrikeRingItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    public static int getLv(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(TAG_LV, Tag.TAG_INT)) {
            return tag.getInt(TAG_LV);
        }
        return 1;
    }

    public static void setLv(ItemStack stack, int lv) {
        stack.getOrCreateTag().putInt(TAG_LV, Math.max(lv, 1));
    }

    public static float getPredicateValue(int lv) {
        if (lv <= 1) return 0.0f;
        return 1.0f - 1.0f / lv;
    }

    public static ItemStack createWithLv(Item item, int lv) {
        ItemStack stack = new ItemStack(item);
        setLv(stack, lv);
        return stack;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        int lv = getLv(stack);
        return switch (lv) {
            case 2 -> ModRarities.EXCELLENT;
            default -> ModRarities.COMMON;
        };
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        int lv = getLv(stack);
        list.add(Component.translatable("tooltip.pasterdream.lv", lv).withStyle(ChatFormatting.GREEN));
        list.add(Component.translatable("tooltip.pasterdream.strike_ring.effect", (lv+1)).withStyle(ChatFormatting.BLUE));
        if (Screen.hasShiftDown()) {
            list.add(Component.translatable("tooltip.pasterdream.introduction.tooltip"));
            list.add(Component.translatable("tooltip.pasterdream.only_one.tooltip"));
        }
        else {
            list.add(Component.translatable("tooltip.pasterdream.hold_shift"));
        }
    }

    /**
     * 限制只能佩戴一枚强击戒指
     */
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() != null) {
            return CuriosApi.getCuriosInventory(slotContext.entity()).map(handler ->
                            handler.findFirstCurio(this).isEmpty())
                    .orElse(true);
        }
        return true;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        int lv = getLv(stack);
        if (entity != null && !entity.level().isClientSide()) {
            var attr = entity.getAttribute(Attributes.ATTACK_DAMAGE);
            if (attr != null) {
                var existing = attr.getModifier(ATTACK_DAMAGE_MODIFIER_UUID);
                if (existing == null || existing.getAmount() != lv) {
                    attr.removeModifier(ATTACK_DAMAGE_MODIFIER_UUID);
                    attr.addPermanentModifier(
                            new AttributeModifier(ATTACK_DAMAGE_MODIFIER_UUID, "Strike ring attack damage",
                                    (lv+1), AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var attr = entity.getAttribute(Attributes.ATTACK_DAMAGE);
            if (attr != null) {
                attr.removeModifier(ATTACK_DAMAGE_MODIFIER_UUID);
                }
            }
        }
    }
