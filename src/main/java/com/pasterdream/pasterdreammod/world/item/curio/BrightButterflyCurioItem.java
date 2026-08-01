package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class BrightButterflyCurioItem extends Item implements ICurioItem {
    public BrightButterflyCurioItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.ANCIENT));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        var entity = slotContext.entity();
        if (entity.level().isClientSide()) return;
        if (entity.tickCount % 20 != 0) return;

        entity.removeEffect(MobEffects.DARKNESS);
        entity.removeEffect(MobEffects.BLINDNESS);
        entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 240, 0, false, false));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(ModRarities.qualityTooltip(ModRarities.ANCIENT));
        list.add(Component.translatable("tooltip.pasterdream.bright_butterfly_curio.effect.gamma"));
        list.add(Component.translatable("tooltip.pasterdream.bright_butterfly_curio.effect.darkness_immune"));
    }
}
