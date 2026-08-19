package com.pasterdream.pasterdreammod.world.dimension;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.AdvancementHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

public final class AaroncosArenaTeleporter {
    private AaroncosArenaTeleporter() {}

    private static final ResourceKey<Level> ARENA_WORLD = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_arena_world"));

    // 原作 achievement_shadow_d_0（灯与影——做出选择），竞技场入口门控
    private static final ResourceLocation SHADOW_CHOICE_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/shadow_choice");

    /** 传送门 / 创建物品共用：检查前置进度后传送到竞技场维度，给缓落 + 冒险模式 */
    public static void teleportToArena(Level world, Entity entity) {
        if (!(entity instanceof ServerPlayer player)) return;
        if (player.level().dimension() == ARENA_WORLD) return;
        if (!AdvancementHelper.isDone(player, SHADOW_CHOICE_ADV) && !player.isCreative()) {
            player.displayClientMessage(Component.translatable("message.pasterdream.aaroncos_arena.need_progress"), true);
            return;
        }
        ServerLevel destination = player.server.getLevel(ARENA_WORLD);
        if (destination == null) return;
        // 结构内亚伦柯斯之眼位于 (0,44,-1)，传送至其上方几格
        player.teleportTo(destination, 0.5, 47, -0.5, player.getYRot(), player.getXRot());
        player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 120, 0));
        player.setGameMode(GameType.ADVENTURE);
    }

    /** 战斗结束：传回主世界重生点（或世界出生点），改生存模式 */
    public static void teleportToOverworldSpawn(ServerPlayer player) {
        ServerLevel overworld = player.server.getLevel(Level.OVERWORLD);
        if (overworld == null) return;
        double x, y, z;
        if (player.getRespawnDimension() == Level.OVERWORLD && player.getRespawnPosition() != null) {
            x = player.getRespawnPosition().getX() + 0.5;
            y = player.getRespawnPosition().getY();
            z = player.getRespawnPosition().getZ() + 0.5;
        } else {
            x = overworld.getLevelData().getXSpawn() + 0.5;
            y = overworld.getLevelData().getYSpawn();
            z = overworld.getLevelData().getZSpawn() + 0.5;
        }
        player.teleportTo(overworld, x, y, z, player.getYRot(), player.getXRot());
        player.setGameMode(GameType.SURVIVAL);
    }
}
