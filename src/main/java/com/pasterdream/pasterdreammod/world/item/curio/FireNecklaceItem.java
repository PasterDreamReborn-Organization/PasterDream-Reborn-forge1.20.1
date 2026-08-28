package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class FireNecklaceItem extends Item implements ICurioItem {
    public FireNecklaceItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.EXCELLENT));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity == null || entity.level().isClientSide()) return;

        BlockPos pos = entity.blockPosition();
        if (entity.level().getBlockState(pos).isAir()) {
            entity.level().setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
        }
        entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2, 0, false, false));
        if (entity.isOnFire()) {
            entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 2, 1, false, false));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.fire_necklace.effect1"));
        list.add(Component.translatable("tooltip.pasterdream.fire_necklace.effect2"));
        list.add(Component.translatable("tooltip.pasterdream.fire_necklace.effect3"));
    }
}
