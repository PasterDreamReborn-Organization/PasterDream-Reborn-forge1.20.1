package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
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
        if (world.isClientSide()) return;

        // Swing main hand
        entity.swing(InteractionHand.MAIN_HAND, true);

        // 只检查玩家是否完成了据点守卫事件，不要求上方有暮影之笼
        if (!(entity instanceof ServerPlayer sp) || !hasBastionGuard(sp)) {
            return;
        }

        // Drain 10 San and teleport to lamp_shadow_world
        SanHelper.addPlayerSanAndSync(sp, -10);

        // Defer teleport by 1 tick to avoid modifying the level during block interaction
        ServerPlayer player = sp;
        sp.server.execute(() -> LampShadowWorldTeleporter.execute(player.level(), player));
    }

    private static boolean hasBastionGuard(ServerPlayer player) {
        Advancement adv = player.server.getAdvancements().getAdvancement(BASTION_GUARD_ADV);
        return adv != null && player.getAdvancements().getOrStartProgress(adv).isDone();
    }
}
