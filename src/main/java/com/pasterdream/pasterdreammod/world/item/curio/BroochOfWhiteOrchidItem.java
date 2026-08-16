package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.capability.san.ISanModifier;
import com.pasterdream.pasterdreammod.world.item.IndestructibleItemEntity;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class BroochOfWhiteOrchidItem extends Item implements ICurioItem, ISanModifier {

    public BroochOfWhiteOrchidItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.EPIC));
    }

    @Override
    public double modifyEnvRate(double envRate) {
        return Math.max(envRate, 0.0);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        // TODO: Check for advancement achievement_talent_light once advancement system is ported
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(ModRarities.qualityTooltip(ModRarities.EPIC));
        list.add(Component.translatable("tooltip.pasterdream.brooch_of_white_orchid.effect"));
        list.add(Component.translatable("tooltip.pasterdream.brooch_of_white_orchid.effect2"));
        list.add(Component.translatable("tooltip.pasterdream.brooch_of_white_orchid.effect3"));
        list.add(Component.translatable("tooltip.pasterdream.brooch_of_white_orchid.flavor"));
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
