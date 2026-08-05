package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class TrueShadowBedInteractionHandler {

    private static final ResourceLocation BASTION_GUARD_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/bastion_guard");

    public static void execute(Level world, BlockPos pos, Player entity) {
        if (entity == null) return;

        // Swing main hand
        entity.swing(InteractionHand.MAIN_HAND, true);

        // Check: is there a twilight_lantern directly above the bed (y+2)?
        BlockPos lanternPos = pos.above(2);
        if (!world.getBlockState(lanternPos).is(ModBlocks.TWILIGHT_LANTERN.get())) {
            return;
        }

        // Check: player has completed the bastion guard event
        if (entity instanceof ServerPlayer sp && !hasBastionGuard(sp)) {
            return;
        }

        // Drain 10 San and teleport to lamp_shadow_world
        if (entity instanceof ServerPlayer player) {
            SanHelper.addPlayerSanAndSync(player, -10);
        }
        LampShadowWorldTeleporter.execute(world, entity);
    }

    private static boolean hasBastionGuard(ServerPlayer player) {
        Advancement adv = player.server.getAdvancements().getAdvancement(BASTION_GUARD_ADV);
        return adv != null && player.getAdvancements().getOrStartProgress(adv).isDone();
    }
}
