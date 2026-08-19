package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.helper.DreamDimensionHelper;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class MeltDreamEnergyRingItem extends Item implements ICurioItem {

    /** 梦境内每秒回复量 (0.3/min) */
    private static final double ENERGY_PER_SEC_IN_DREAM = 0.3 / 60.0;
    /** 梦境外每秒回复量 (0.15/min) */
    private static final double ENERGY_PER_SEC_OUT_DREAM = 0.15 / 60.0;

    public MeltDreamEnergyRingItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.SUPERIOR));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof ServerPlayer player)) return;
        if (player.tickCount % 20 != 0) return;

        double amount = DreamDimensionHelper.isDreamDimension(player.level())
                ? ENERGY_PER_SEC_IN_DREAM
                : ENERGY_PER_SEC_OUT_DREAM;

        MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(player, amount);
    }

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
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(ModRarities.qualityTooltip(ModRarities.SUPERIOR));
        list.add(Component.translatable("tooltip.pasterdream.melt_dream_energy_ring.effect1"));
        list.add(Component.translatable("tooltip.pasterdream.melt_dream_energy_ring.effect2"));
        if (Screen.hasShiftDown()) {
            list.add(Component.translatable("tooltip.pasterdream.introduction.tooltip"));
            list.add(Component.translatable("tooltip.pasterdream.only_one.tooltip"));
        } else {
            list.add(Component.translatable("tooltip.pasterdream.hold_shift"));
        }
    }
}
