package com.pasterdream.pasterdreammod.capability.san;

import net.minecraft.server.level.ServerPlayer;

public final class SanDisabledHealthCost {

    private SanDisabledHealthCost() {
    }

    public static void drain(ServerPlayer player, float amount) {
        if (amount <= 0.0F || player.isDeadOrDying() || player.isCreative()) return;
        float health = player.getHealth() - amount;
        if (health <= 0.0F) {
            player.setHealth(0.0F);
            player.die(player.damageSources().magic());
        } else {
            player.setHealth(health);
        }
    }
}
