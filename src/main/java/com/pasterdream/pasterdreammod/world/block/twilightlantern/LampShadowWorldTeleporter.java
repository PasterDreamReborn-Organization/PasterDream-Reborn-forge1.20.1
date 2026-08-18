package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

public class LampShadowWorldTeleporter {

    private static final ResourceKey<Level> LAMP_SHADOW_WORLD =
            ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"));

    public static ResourceKey<Level> getLampShadowWorld() {
        return LAMP_SHADOW_WORLD;
    }

    /**
     * Teleports the entity to the lamp_shadow_world dimension.
     * Target is below the twilight_lantern in the shadow_world_spawn structure.
     * Height logic must match placeShadowWorldSpawn in ModWorldGenEvents.
     */
    public static void execute(Level world, Entity entity) {
        if (!(entity instanceof ServerPlayer player)) return;
        if (player.level().dimension() == LAMP_SHADOW_WORLD) return;

        ServerLevel destination = player.server.getLevel(LAMP_SHADOW_WORLD);
        if (destination == null) return;

        // Match the structure placement: height at (-9, -9) <= 100 → low spawn, else → high spawn
        boolean low = destination.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, -9, -9) <= 100;
        double targetY = low ? 104 : 154;

        player.teleportTo(destination, 0.5, targetY, 0.5, player.getYRot(), player.getXRot());
    }

    /**
     * 传送玩家回主世界（重生点或世界出生点）。
     * 用 teleportTo 单步跨维度传送，它会发送 RespawnPacket 并使用 addDuringCommandTeleport
     * （命令传送变体，不会像 changeDimension 的 addDuringPortalTeleport 那样破坏区块追踪）。
     *
     * 传送延迟到下一 tick 执行：在方块 use() 交互中途直接跨维度会与旧维度的方块更新包交错，
     * 使客户端在主世界同坐标残留幽灵方块。延迟后额外对源方块坐标重发一次方块状态，兜底清除残留。
     */
    public static void teleportToOverworld(ServerPlayer player, BlockPos sourcePos) {
        ServerLevel overworld = player.server.getLevel(Level.OVERWORLD);
        if (overworld == null) return;

        player.server.execute(() -> {
            double destX, destY, destZ;
            if (player.getRespawnDimension() == Level.OVERWORLD && player.getRespawnPosition() != null) {
                BlockPos respawn = player.getRespawnPosition();
                destX = respawn.getX() + 0.5;
                destY = respawn.getY();
                destZ = respawn.getZ() + 0.5;
            } else {
                destX = overworld.getLevelData().getXSpawn() + 0.5;
                destY = overworld.getLevelData().getYSpawn();
                destZ = overworld.getLevelData().getZSpawn() + 0.5;
            }
            player.teleportTo(overworld, destX, destY, destZ, player.getYRot(), player.getXRot());
            player.fallDistance = 0;
            player.connection.send(new ClientboundBlockUpdatePacket(overworld, sourcePos));
        });
    }
}
