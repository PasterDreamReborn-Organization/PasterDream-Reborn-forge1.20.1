package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class SnowVowHeadItem extends Item implements ICurioItem {

    public SnowVowHeadItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.SUPERIOR).fireResistant());
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Level level = slotContext.entity().level();
        if (level.isClientSide()) return;
        AABB area = new AABB(slotContext.entity().blockPosition()).inflate(3.5);
        List<Player> players = level.getEntitiesOfClass(Player.class, area);
        for (Player player : players) {
            player.addEffect(new MobEffectInstance(ModEffects.SNOW_VOW.get(), 20, 0, false, false));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(ModRarities.qualityTooltip(ModRarities.SUPERIOR));
        list.add(Component.translatable("tooltip.pasterdream.snow_vow_head.effect.area"));
        list.add(Component.translatable("tooltip.pasterdream.snow_vow_head.effect.bonus"));
    }
}
