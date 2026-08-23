package com.pasterdream.pasterdreammod.world.dimension;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.AdvancementHelper;
import com.pasterdream.pasterdreammod.helper.GameModeHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class AaroncosArenaTeleporter {
    private AaroncosArenaTeleporter() {}

    private static final ResourceKey<Level> ARENA_WORLD = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_arena_world"));

    // 原作 achievement_shadow_d_0（灯与影——做出选择），竞技场入口门控
    private static final ResourceLocation SHADOW_CHOICE_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/shadow_choice");

    // 独立入口冷却：不用原版 portalCooldown（走过下界传送门会把它设为 300t，导致竞技场传送门 15 秒内失效）
    private static final Map<UUID, Integer> ENTRY_COOLDOWNS = new ConcurrentHashMap<>();
    private static final int ENTRY_COOLDOWN_TICKS = 20;

    /** 传送门 / 创建物品共用：门控（眼方块 + 场内玩家）后传送到竞技场维度，给缓落 + 冒险模式 */
    public static void teleportToArena(Level world, Entity entity) {
        if (!(entity instanceof ServerPlayer player)) return;
        if (player.level().dimension() == ARENA_WORLD) return;
        Integer cd = ENTRY_COOLDOWNS.get(player.getUUID());
        if (cd != null && cd > 0) return;
        if (!AdvancementHelper.isDone(player, SHADOW_CHOICE_ADV) && !player.isCreative()) {
            player.displayClientMessage(Component.translatable("message.pasterdream.aaroncos_arena.need_progress"), true);
            return;
        }
        ServerLevel destination = player.server.getLevel(ARENA_WORLD);
        if (destination == null) return;

        // 参照染梦裂隙：传送前检查目标侧状态。
        // 眼在场 = 未开战（放行）；眼缺失 + 有玩家 = 战斗进行中（禁止进入）；
        // 眼缺失 + 无玩家 = 竞技场结构未生成（重新生成结构后放行）
        boolean eyePresent = AaroncosArenaWorldDimension.isEyePresent(destination);
        if (!eyePresent && !destination.players().isEmpty()) {
            player.displayClientMessage(Component.translatable("message.pasterdream.aaroncos_arena.battle_in_progress"), true);
            setEntryCooldown(player);
            return;
        }

        if (!eyePresent) {
            // 结构未生成：玩家尚未传送、仍在服务端线程，强制加载全部覆盖区块后同步放置即可
            AaroncosArenaWorldDimension.placeArenaStructure(destination);
        }

        teleportPlayer(player, destination);
        setEntryCooldown(player);
    }

    /** 传送到竞技场（结构内眼方块位于 (0,44,-1)，传至其上方几格）+ 缓落 + 冒险模式 */
    private static void teleportPlayer(ServerPlayer player, ServerLevel destination) {
        player.teleportTo(destination, 0.5, 47, -0.5, player.getYRot(), player.getXRot());
        // 实测：同 tick 连发的重生包+位置包，客户端处理重生时位置包会被忽略/覆盖，
        // 客户端停留在传送门坐标并继续上报移动，服务端确认窗口超时后接受旧坐标，把玩家拖回门口。
        // 因此改为下一 tick 起连续 3 tick 强制同步（客户端重生处理完毕后位置包必定生效）。
        for (int delay = 1; delay <= 3; delay++) {
            player.server.tell(new TickTask(delay, () -> forcePosition(player, destination)));
        }
        player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 120, 0));
        GameModeHelper.saveAndSetAdventure(player);
    }

    /** 强制把玩家锁回眼睛上方：服务端 setPos + 客户端位置包（设置 awaiting 位置，期间拒绝旧坐标上报） */
    private static void forcePosition(ServerPlayer player, ServerLevel destination) {
        if (player == null || player.level() != destination)
            return;
        player.setPos(0.5, 47, -0.5);
        player.connection.teleport(0.5, 47, -0.5, player.getYRot(), player.getXRot());
    }

    private static void setEntryCooldown(ServerPlayer player) {
        ENTRY_COOLDOWNS.put(player.getUUID(), ENTRY_COOLDOWN_TICKS);
    }

    /** 服务端 tick 递减入口冷却（由 PasterDreamMod 的 ServerTickEvent 调用） */
    public static void tickEntryCooldowns() {
        ENTRY_COOLDOWNS.replaceAll((uuid, ticks) -> ticks > 1 ? ticks - 1 : 0);
        ENTRY_COOLDOWNS.values().removeIf(ticks -> ticks <= 0);
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
        if (!GameModeHelper.restorePreDreamGameMode(player))
            player.setGameMode(GameType.SURVIVAL);
    }
}
