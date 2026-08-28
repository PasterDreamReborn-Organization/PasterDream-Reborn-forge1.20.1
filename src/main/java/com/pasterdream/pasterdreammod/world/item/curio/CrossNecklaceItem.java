package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class CrossNecklaceItem extends Item implements ICurioItem {
    public CrossNecklaceItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.SUPERIOR));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity == null || entity.level().isClientSide()) return;

        var effect = entity.getEffect(ModEffects.EVASION.get());
        if (effect != null && effect.getDuration() <= 6) {
            entity.addEffect(new MobEffectInstance(ModEffects.EVASION.get(), 10, 0,
                    false, false));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.cross_necklace.effect"));
    }
}
