package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RecipeUnlockHandler {

    private static final ResourceLocation SHADOW_CLAY_POT_ADVANCEMENT =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                    "recipes/building_blocks/shadow_clay_pot_from_stonecutting");

    /**
     * 当玩家破坏陶罐时，解锁阴影陶罐的切石机配方。
     * 无论是否精准采集，只要破坏了陶罐方块就会触发。
     */
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() != ModBlocks.CLAY_POT.get()) return;
        if (!(event.getPlayer() instanceof ServerPlayer serverPlayer)) return;

        Advancement advancement = serverPlayer.server.getAdvancements()
                .getAdvancement(SHADOW_CLAY_POT_ADVANCEMENT);
        if (advancement == null) return;
        if (serverPlayer.getAdvancements().getOrStartProgress(advancement).isDone()) return;

        serverPlayer.getAdvancements().award(advancement, "has_shadow_stone_tiles");
    }
}
