package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class OriginDreamDictionaryItem extends Item {
    public OriginDreamDictionaryItem() {
        super(new Item.Properties().stacksTo(64).rarity(ModRarities.MIRACLE));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("tooltip.pasterdream.origin_dream_dictionary.1"));
        list.add(Component.translatable("tooltip.pasterdream.origin_dream_dictionary.2"));
        list.add(Component.translatable("tooltip.pasterdream.origin_dream_dictionary.3"));
        list.add(Component.translatable("tooltip.pasterdream.origin_dream_dictionary.4"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        ItemStack itemstack = ar.getObject();

        if (!world.isClientSide()) {
            if (entity instanceof ServerPlayer sp) {
                SanHelper.addPlayerSanAndSync(sp, 10);
                MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(sp, 10);
            }

            entity.addEffect(new MobEffectInstance(ModEffects.MEMENTO.get(), 3600, 0));
            world.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()),
                    ModSounds.DING.get(), SoundSource.PLAYERS, 1, 1);
        }

        if (!entity.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return ar;
    }
}