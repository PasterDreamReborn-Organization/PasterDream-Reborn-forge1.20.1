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

public class CalaisSpiceBottleItem extends Item implements ICurioItem {

    public CalaisSpiceBottleItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.MASTER));
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() == null || slotContext.entity().level().isClientSide()) return;
        // 初次佩戴时清除枯竭标记，给予 Ⅹ 级增益
        slotContext.entity().getPersistentData().putBoolean("pasterdream.calais_depleted", false);
        if (!slotContext.entity().hasEffect(ModEffects.CALAIS_SPICE_BOTTLE.get())) {
            slotContext.entity().addEffect(new MobEffectInstance(ModEffects.CALAIS_SPICE_BOTTLE.get(),
                    -1, 9, false, false, true));
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity == null || entity.level().isClientSide()) return;

        // 仅在从未被攻击砍光过的情况下，自动维持 Ⅹ 级（用于处理死亡/牛奶后恢复）
        if (!entity.hasEffect(ModEffects.CALAIS_SPICE_BOTTLE.get())) {
            boolean depleted = entity.getPersistentData().getBoolean("pasterdream.calais_depleted");
            if (!depleted) {
                entity.addEffect(new MobEffectInstance(ModEffects.CALAIS_SPICE_BOTTLE.get(),
                        -1, 9, false, false, true));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            slotContext.entity().removeEffect(ModEffects.CALAIS_SPICE_BOTTLE.get());
            slotContext.entity().getPersistentData().remove("pasterdream.calais_depleted");
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.calais_spice_bottle.effect1"));
        list.add(Component.translatable("tooltip.pasterdream.calais_spice_bottle.effect2"));
        list.add(Component.translatable("tooltip.pasterdream.calais_spice_bottle.effect3"));
    }
}
