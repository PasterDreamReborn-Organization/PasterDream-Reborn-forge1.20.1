package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class LightButterflyCurioItem extends Item implements ICurioItem {
    public LightButterflyCurioItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.SUPERIOR));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Level level = slotContext.entity().level();
        if (level.isClientSide()) return;

        var entity = slotContext.entity();
        if (entity.tickCount % 20 != 0) return;

        if (level.getMaxLocalRawBrightness(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ())) > 7) return;

        if (entity instanceof ServerPlayer sp
                && (sp.isCreative() || MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(sp) >= 0.004)) {
            if (!sp.isCreative()) {
                MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(sp, -0.004);
            }
            sp.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 240, 0, false, false));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.light_butterfly_curio.effect"));
        list.add(Component.translatable("tooltip.pasterdream.light_butterfly_curio.energy"));
    }
}
