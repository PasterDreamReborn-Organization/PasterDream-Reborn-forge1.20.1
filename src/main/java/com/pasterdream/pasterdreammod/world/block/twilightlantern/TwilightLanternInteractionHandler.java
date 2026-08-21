package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class TwilightLanternInteractionHandler {

    private static final ResourceKey<Level> LAMP_SHADOW_WORLD =
            ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"));

    private static final ResourceLocation LAMP_SHADOW_ROOT_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/lamp_shadow_root");

    public static void execute(Level world, BlockPos pos, Player entity) {
        if (entity == null) return;
        if (world.isClientSide()) return;

        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof TwilightLanternBlockEntity lantern)) return;

        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        // Shift+right-click: reset lantern state (debug tool)
        if (entity.isShiftKeyDown()) {
            handleReset(world, pos, lantern, entity);
            return;
        }

        // If in lamp_shadow_world, right-click teleports back to overworld
        if (world.dimension() == LAMP_SHADOW_WORLD) {
            handleReturnToOverworld(world, pos, entity, x, y, z);
            return;
        }

        // Main event trigger logic (overworld / nether)
        // Check if player has read the "侵染教堂-黑面" note (lamp_shadow_root advancement)
        if (!hasReadInfestedChurch(entity)) {
            entity.displayClientMessage(Component.translatable("message.pasterdream.twilight_lantern.activate_fail_no_knowledge"), true);
            return;
        }

        // Check if holding melt_dream_crystal_fragment
        ItemStack heldItem = entity.getItemInHand(InteractionHand.MAIN_HAND);
        if (heldItem.getItem() != ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()) {
            entity.displayClientMessage(Component.translatable("message.pasterdream.twilight_lantern.activate_fail_no_crystal"), true);
            return;
        }

        // Check switch is false (event not already running)
        if (lantern.isEventSwitch()) {
            return;
        }

        // Grant Darkness effect for 7 seconds
        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 140, 0, false, false));

        // Activate the event
        lantern.setEventSwitch(true);
        lantern.setEventTick(0);
        lantern.setNumber(0);

        // Consume one crystal from player's inventory
        if (!entity.isCreative()) {
            heldItem.shrink(1);
        }

        // Play lantern place sound
        if (!world.isClientSide()) {
            world.playSound(null, pos, SoundEvents.LANTERN_PLACE, SoundSource.NEUTRAL, 1, 1);
        } else {
            world.playLocalSound(x, y, z, SoundEvents.LANTERN_PLACE, SoundSource.NEUTRAL, 1, 1, false);
        }

        // Spawn END_ROD particles
        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.END_ROD, x + 0.5, y - 0.5, z + 0.5,
                    32, 1, 1, 1, 0.05);
            // Reschedule tick to fire quickly now that the event has started
            serverLevel.scheduleTick(pos, serverLevel.getBlockState(pos).getBlock(), 1);
        }
    }

    private static void handleReturnToOverworld(Level world, BlockPos pos, Player entity, double x, double y, double z) {
        // Play lantern place sound
        if (!world.isClientSide()) {
            world.playSound(null, pos, SoundEvents.LANTERN_PLACE, SoundSource.NEUTRAL, 1, 1);
        } else {
            world.playLocalSound(x, y, z, SoundEvents.LANTERN_PLACE, SoundSource.NEUTRAL, 1, 1, false);
        }

        if (entity instanceof ServerPlayer serverPlayer) {
            LampShadowWorldTeleporter.teleportToOverworld(serverPlayer, pos);
        }
    }

    private static boolean hasReadInfestedChurch(Player entity) {
        if (!(entity instanceof ServerPlayer serverPlayer)) return false;
        Advancement adv = serverPlayer.server.getAdvancements().getAdvancement(LAMP_SHADOW_ROOT_ADV);
        return adv != null && serverPlayer.getAdvancements().getOrStartProgress(adv).isDone();
    }

    private static void handleReset(Level world, BlockPos pos, TwilightLanternBlockEntity lantern, Player entity) {
        if (!world.isClientSide()) {
            world.playSound(null, pos, ModSounds.DING.get(), SoundSource.NEUTRAL, 1, 1);
        } else {
            world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(),
                    ModSounds.DING.get(), SoundSource.NEUTRAL, 1, 1, false);
        }
        entity.displayClientMessage(Component.translatable("message.pasterdream.twilight_lantern.data_reset"), false);
        lantern.setEventSwitch(false);
        lantern.setEventTick(0);
        lantern.setNumber(0);
        lantern.setKey(false);
    }
}
