package com.pasterdream.pasterdreammod.helper;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class AdvancementHelper {

    private AdvancementHelper() {
    }

    public static boolean isDone(ServerPlayer player, ResourceLocation id) {
        Advancement adv = player.server.getAdvancements().getAdvancement(id);
        return adv != null && player.getAdvancements().getOrStartProgress(adv).isDone();
    }

    public static void grant(ServerPlayer player, ResourceLocation id, String criterion) {
        Advancement adv = player.server.getAdvancements().getAdvancement(id);
        if (adv != null && !player.getAdvancements().getOrStartProgress(adv).isDone()) {
            player.getAdvancements().award(adv, criterion);
        }
    }
}
