package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
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

public class HealthNecklaceItem extends Item implements ICurioItem {
    private static final UUID HEALTH_UUID = UUID.fromString("997f84d7-f1c0-46ec-9337-5db4733b30ed");

    public HealthNecklaceItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.EXCELLENT));
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance health = slotContext.entity().getAttribute(Attributes.MAX_HEALTH);
            if (health != null && health.getModifier(HEALTH_UUID) == null) {
                health.addPermanentModifier(new AttributeModifier(HEALTH_UUID,
                        "Health necklace max health", 2.0, AttributeModifier.Operation.ADDITION));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance health = slotContext.entity().getAttribute(Attributes.MAX_HEALTH);
            if (health != null) {
                health.removeModifier(HEALTH_UUID);
            }
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity == null || entity.level().isClientSide()) return;

        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 2, 0, false, false));
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        // 读档时 Curios 会把槽里那一件重新校验一遍，判重必须放行自身槽位；详见 CurioEquipHelper。
        return CurioEquipHelper.allowsSingleCopy(slotContext, stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.health_necklace.effect1"));
        list.add(Component.translatable("tooltip.pasterdream.health_necklace.effect2"));
    }
}
