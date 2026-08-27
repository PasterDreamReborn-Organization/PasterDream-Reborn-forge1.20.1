package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class LightMossPhantomMembraneItem extends Item implements ICurioItem {
    /** 恢复耐久的时间间隔（tick）：60 tick = 3 秒 */
    private static final int REPAIR_INTERVAL_TICKS = 60;
    /** 亮度阈值：环境亮度达到 7 及以上时视为"光亮环境" */
    private static final int BRIGHTNESS_THRESHOLD = 7;
    /** 每次恢复的鞘翅耐久值 */
    private static final int REPAIR_AMOUNT = 2;
    /** 光亮环境下恢复速度的倍率 */
    private static final int BRIGHT_REPAIR_MULTIPLIER = 2;

    public LightMossPhantomMembraneItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.EXCELLENT));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Level level = slotContext.entity().level();
        if (level.isClientSide()) return;

        LivingEntity entity = slotContext.entity();
        if (entity.tickCount % REPAIR_INTERVAL_TICKS != 0) return;

        ItemStack chestStack = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (chestStack.is(Items.ELYTRA) && chestStack.isDamaged()) {
            int repair = REPAIR_AMOUNT;
            if (level.getMaxLocalRawBrightness(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ())) >= BRIGHTNESS_THRESHOLD) {
                repair *= BRIGHT_REPAIR_MULTIPLIER;
            }
            chestStack.setDamageValue(chestStack.getDamageValue() - repair);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.light_moss_phantom_membrane.effect"));
        list.add(Component.translatable("tooltip.pasterdream.light_moss_phantom_membrane.effect.dark"));
    }
}
