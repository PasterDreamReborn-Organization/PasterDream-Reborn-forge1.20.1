package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;

import java.util.List;

public class EmptyVesselItem extends Item {
    public EmptyVesselItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("tooltip.pasterdream.empty_vessel.1"));
        list.add(Component.translatable("tooltip.pasterdream.empty_vessel.2"));
        list.add(Component.translatable("tooltip.pasterdream.empty_vessel.3"));
        list.add(Component.translatable("tooltip.pasterdream.empty_vessel.4"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        ItemStack itemstack = ar.getObject();

        if (!world.isClientSide()) {
            if (entity instanceof ServerPlayer sp) {
                if (Mth.nextDouble(sp.getRandom(), 0, 1) <= 0.01) {
                    SanHelper.setPlayerSanAndSync(sp, 0);
                } else {
                    double maxSan = SanHelper.getPlayerMaxSan(sp);
                    SanHelper.addPlayerSanAndSync(sp, maxSan * 0.2);
                }
            }

            entity.addEffect(new MobEffectInstance(ModEffects.MEMENTO.get(), 3600, 0));
            world.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()),
                    ModSounds.DING_0.get(), SoundSource.PLAYERS, 1, 1);
        }

        if (!entity.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return ar;
    }
}
