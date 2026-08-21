package com.pasterdream.pasterdreammod.world.item.grasstool;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class KusanagiItem extends SwordItem {

    public KusanagiItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean retval = super.hurtEnemy(stack, target, attacker);
        if (!target.level().isClientSide()) {
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0));
        }
        return retval;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (selected && stack.getOrCreateTag().getDouble("killed") >= Config.TheNumberofKillEnemytoEvolve) {
            ItemStack evolved = new ItemStack(ModItems.MURAKUMO_KUSANAGI.get());
            evolved.setTag(stack.getOrCreateTag().copy());
            stack.shrink(1);
            if (entity instanceof Player player) {
                if (!player.getInventory().add(evolved)) {
                    player.drop(evolved, false);
                }
                if (!level.isClientSide()) {
                    player.displayClientMessage(Component.translatable("tooltip.pasterdreammod.kusanagi.evolve"), true);
                }
            }
            if (!level.isClientSide()) {
                level.playSound(null, entity.blockPosition(), ModSounds.SWORD_AWAKEN.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
            } else {
                level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), ModSounds.SWORD_AWAKEN.get(), SoundSource.NEUTRAL, 1.0f, 1.0f, false);
            }
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.END_ROD, entity.getX(), entity.getY(), entity.getZ(), 5, 1, 1, 1, 0.5);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        int killed = (int) stack.getOrCreateTag().getDouble("killed");
        tooltip.add(Component.translatable("tooltip.pasterdreammod.kusanagi.1", killed,Config.TheNumberofKillEnemytoEvolve));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.kusanagi.2"));
    }
}
