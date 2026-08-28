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

public class RedDewRingItem extends Item implements ICurioItem {
    public static final String TAG_LV = "lv";
    private static final UUID HEALTH_MODIFIER_UUID = UUID.fromString("6ba1f6a6-8e3d-4e7c-a5b2-9c8d7f1e3a2b");

    public RedDewRingItem() {
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

    /**
     * 根据等级计算 ItemProperties 的 predicate 值（0~1）
     * 公式: 1 - 1/lv，lv=1 → 0, lv=2 → 0.5, lv=3 → 0.667, lv=4 → 0.75, lv→∞ → 1.0
     * 魔改作者添加新等级贴图时，在模型 JSON 的 overrides 中使用此值作为 predicate
     * 如果不满意现在的属性加成那我建议你用kubejs curios或者hotai重写吧，我也没招了……
     */
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
            case 3 -> ModRarities.SUPERIOR;
            case 4 -> ModRarities.MASTER;
            default -> ModRarities.COMMON;
        };
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        int lv = getLv(stack);
        list.add(Component.translatable("tooltip.pasterdream.lv", lv).withStyle(ChatFormatting.GREEN));
        list.add(Component.translatable("tooltip.pasterdream.red_dew_ring.effect", lv).withStyle(ChatFormatting.BLUE));
        if (Screen.hasShiftDown()) {
            list.add(Component.translatable("tooltip.pasterdream.introduction.tooltip"));
            list.add(Component.translatable("tooltip.pasterdream.only_one.tooltip"));
        }
        else {
            list.add(Component.translatable("tooltip.pasterdream.hold_shift"));
        }
    }

    /**
     * 限制只能佩戴一枚红露滴戒指
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
            var attr = entity.getAttribute(Attributes.MAX_HEALTH);
            if (attr != null) {
                var existing = attr.getModifier(HEALTH_MODIFIER_UUID);
                if (existing == null || existing.getAmount() != lv) {
                    attr.removeModifier(HEALTH_MODIFIER_UUID);
                    attr.addPermanentModifier(
                            new AttributeModifier(HEALTH_MODIFIER_UUID, "Red dew ring health",
                                    lv, AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var attr = entity.getAttribute(Attributes.MAX_HEALTH);
            if (attr != null) {
                attr.removeModifier(HEALTH_MODIFIER_UUID);
                // 卸下后当前血量不能超过新的最大血量
                if (entity.getHealth() > entity.getMaxHealth()) {
                    entity.setHealth(entity.getMaxHealth());
                }
            }
        }
    }
}
