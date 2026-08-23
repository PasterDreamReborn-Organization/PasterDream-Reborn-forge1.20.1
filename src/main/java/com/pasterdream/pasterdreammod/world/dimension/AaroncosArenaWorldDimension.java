package com.pasterdream.pasterdreammod.world.dimension;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.GameModeHelper;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public final class AaroncosArenaWorldDimension {
    private AaroncosArenaWorldDimension() {}

    public static final ResourceKey<Level> AARONCOS_ARENA_WORLD = ResourceKey.create(
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("minecraft", "dimension")),
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_arena_world"));

    // ===== 竞技场离场会话：手箱开启后 410 tick 内倒计时并传送所有玩家回主世界 =====

    private record ExitSession(BlockPos chestPos, int elapsed) {}

    private static final Map<ServerLevel, ExitSession> EXIT_SESSIONS = new HashMap<>();
    private static final int[] COUNTDOWN_ELAPSED = {10, 210, 310, 350, 400};
    private static final String[] COUNTDOWN_MSG =
            {"离开倒计时 20秒", "离开倒计时 10秒", "离开倒计时 5秒", "离开倒计时 3秒", "离开倒计时 1秒"};
    private static final int EXIT_TOTAL_TICKS = 410;

    /** 手箱开启时启动离场会话（倒计时提示 + 传回主世界 + 清理竞技场内非玩家实体） */
    public static void startExitSession(ServerLevel arena, BlockPos chestPos) {
        EXIT_SESSIONS.put(arena, new ExitSession(chestPos, 0));
    }

    @SubscribeEvent
    public static void onArenaLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.level instanceof ServerLevel arena)) return;
        if (!arena.dimension().equals(AARONCOS_ARENA_WORLD)) return;

        ExitSession session = EXIT_SESSIONS.get(arena);
        if (session == null) return;

        int elapsed = session.elapsed() + 1;
        for (int i = 0; i < COUNTDOWN_ELAPSED.length; i++) {
            if (elapsed == COUNTDOWN_ELAPSED[i]) {
                for (Player p : arena.players()) {
                    if (p instanceof ServerPlayer sp)
                        sp.displayClientMessage(Component.literal(COUNTDOWN_MSG[i]), true);
                }
            }
        }

        if (elapsed >= EXIT_TOTAL_TICKS) {
            EXIT_SESSIONS.remove(arena);
            for (Player p : new ArrayList<>(arena.players())) {
                if (p instanceof ServerPlayer sp)
                    AaroncosArenaTeleporter.teleportToOverworldSpawn(sp);
            }
            Vec3 center = Vec3.atCenterOf(session.chestPos());
            for (Entity e : arena.getEntitiesOfClass(Entity.class, new AABB(center, center).inflate(37.5),
                    e -> !(e instanceof Player))) {
                e.discard();
            }
        } else {
            EXIT_SESSIONS.put(arena, new ExitSession(session.chestPos(), elapsed));
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class DimensionSpecialEffectsHandler {
        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
            DimensionSpecialEffects customEffect = new DimensionSpecialEffects(
                    Float.NaN,                          // cloudLevel: 无云
                    true,                               // hasGround
                    DimensionSpecialEffects.SkyType.NONE, // 无天空
                    false,                              // forceBrightLightmap
                    false                               // constantAmbientLight
            ) {
                @Override
                public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
                    return new Vec3(0.2, 0.2, 0.2);
                }

                @Override
                public boolean isFoggyAt(int x, int y) {
                    return true;
                }
            };
            event.register(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_arena_world"), customEffect);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer sp))
            return;

        // 离开竞技场维度（含 /tp 等任意逃逸方式）：恢复进入前的游戏模式，防止卡在冒险模式
        if (event.getFrom().equals(AARONCOS_ARENA_WORLD)
                && !event.getTo().equals(AARONCOS_ARENA_WORLD)) {
            GameModeHelper.restorePreDreamGameMode(sp);
            return;
        }

        if (!event.getTo().equals(AARONCOS_ARENA_WORLD))
            return;

        // 延迟到下一 tick 放置竞技场结构：跨维度传送期间区块尚未就绪，立即 placeInWorld 会静默失败
        PasterDreamMod.queueServerWork(1, () -> {
            ServerLevel arena = sp.server.getLevel(AARONCOS_ARENA_WORLD);
            if (arena == null)
                return;
            if (arena.players().size() < 2) {
                // 强制加载结构所在区块
                arena.getChunkSource().getChunk(-35 >> 4, -35 >> 4, true);
                arena.getChunkSource().getChunk(35 >> 4, 35 >> 4, true);
                StructureTemplate template = arena.getStructureManager().getOrCreate(
                        ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_arena"));
                BlockPos origin = new BlockPos(-35, 0, -35);
                template.placeInWorld(arena, origin, origin,
                        new StructurePlaceSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false),
                        arena.random, 3);
                Vec3 center = new Vec3(0, 50, 0);
                for (Entity e : arena.getEntitiesOfClass(Entity.class, new AABB(center, center).inflate(49.5),
                        e -> !(e instanceof Player))) {
                    e.discard();
                }
            }
            sp.teleportTo(arena, 0.5, 47, -0.5, sp.getYRot(), sp.getXRot());
        });
    }

    /** 玩家死亡重生后恢复进入前的游戏模式（防止 kill 等原地重生卡在冒险模式；幂等，无记录则不动作） */
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer sp))
            return;
        GameModeHelper.restorePreDreamGameMode(sp);
    }

    /**
     * 死亡重生会创建全新实体，persistentData 不会自动继承，但游戏模式会被 restoreFrom 继承（仍是冒险）。
     * 这里在克隆时把记录转移到新实体并恢复，兜住 kill/被生物击杀等死亡路径。
     */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath())
            return;
        GameModeHelper.handlePlayerDeathClone(event.getOriginal(), event.getEntity());
    }
}
