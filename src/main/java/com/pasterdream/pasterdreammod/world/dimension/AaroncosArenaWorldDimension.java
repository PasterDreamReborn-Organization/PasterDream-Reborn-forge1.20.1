package com.pasterdream.pasterdreammod.world.dimension;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.AdvancementHelper;
import com.pasterdream.pasterdreammod.helper.GameModeHelper;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.effect.ShadowSpyonEffect;
import com.pasterdream.pasterdreammod.world.entity.AaroncosLeftHandEntity;
import com.pasterdream.pasterdreammod.world.entity.AaroncosRightHandEntity;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
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

    // ===== 竞技场几何常量 =====
    /** 竞技场结构放置原点 */
    public static final BlockPos STRUCTURE_ORIGIN = new BlockPos(-35, 0, -35);
    /** 结构内亚伦柯斯之眼所在位置 */
    public static final BlockPos ARENA_EYE_POS = new BlockPos(0, 44, -1);
    private static final Vec3 ARENA_CENTER = new Vec3(0, 50, 0);

    // 原作 achievement_shadow_e_0（吹影镂尘），击败亚伦柯斯时授予
    private static final ResourceLocation DEFEAT_AARONCOS_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/defeat_aaroncos");

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

    // ===== 竞技场战斗会话：眼方块召唤 BOSS 后销毁自身，战斗时间轴移交本类按 tick 驱动 =====

    private record BattleSession(BlockPos eyePos, int elapsed) {}

    private static final Map<ServerLevel, BattleSession> BATTLES = new HashMap<>();
    private static final int BATTLE_TICK_INTERVAL = 20;
    private static final int MUSIC_LOOP_ELAPSED = 150 * BATTLE_TICK_INTERVAL;
    private static final int[] TERRORBEAK_ELAPSED =
            {50 * BATTLE_TICK_INTERVAL, 100 * BATTLE_TICK_INTERVAL, 150 * BATTLE_TICK_INTERVAL};

    /** 眼方块被激活（召唤 BOSS）时启动战斗会话，随后眼销毁自身 */
    public static void startBattle(ServerLevel arena, BlockPos eyePos) {
        BATTLES.put(arena, new BattleSession(eyePos, 0));
    }

    /** 检测竞技场是否已生成：强制加载眼所在区块后检查结构内眼方块是否在场 */
    public static boolean isEyePresent(ServerLevel arena) {
        arena.getChunkSource().getChunk(ARENA_EYE_POS.getX() >> 4, ARENA_EYE_POS.getZ() >> 4, true);
        return arena.getBlockState(ARENA_EYE_POS).is(ModBlocks.AARONCOS_EYE.get());
    }

    /** 放置竞技场结构（强制加载结构覆盖的全部区块 → placeInWorld → 清理场内非玩家实体），并清除旧会话 */
    public static void placeArenaStructure(ServerLevel arena) {
        StructureTemplate template = arena.getStructureManager().getOrCreate(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_arena"));
        // placeInWorld 逐方块 setBlock，对未加载区块会静默失败，必须先加载结构覆盖的全部区块
        Vec3i size = template.getSize();
        int minCX = STRUCTURE_ORIGIN.getX() >> 4;
        int maxCX = (STRUCTURE_ORIGIN.getX() + size.getX() - 1) >> 4;
        int minCZ = STRUCTURE_ORIGIN.getZ() >> 4;
        int maxCZ = (STRUCTURE_ORIGIN.getZ() + size.getZ() - 1) >> 4;
        for (int cx = minCX; cx <= maxCX; cx++)
            for (int cz = minCZ; cz <= maxCZ; cz++)
                arena.getChunkSource().getChunk(cx, cz, true);
        template.placeInWorld(arena, STRUCTURE_ORIGIN, STRUCTURE_ORIGIN,
                new StructurePlaceSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false),
                arena.random, 3);
        for (Entity e : arena.getEntitiesOfClass(Entity.class, new AABB(ARENA_CENTER, ARENA_CENTER).inflate(49.5),
                e -> !(e instanceof Player))) {
            e.discard();
        }
        EXIT_SESSIONS.remove(arena);
        BATTLES.remove(arena);
    }

    @SubscribeEvent
    public static void onArenaLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.level instanceof ServerLevel arena)) return;
        if (!arena.dimension().equals(AARONCOS_ARENA_WORLD)) return;

        ExitSession session = EXIT_SESSIONS.get(arena);
        if (session != null) {
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

        // 战斗会话：时间轴推进 + Terrorbeak 波次 + 双手死亡后放奖励箱
        BattleSession battle = BATTLES.get(arena);
        if (battle != null) {
            int elapsed = battle.elapsed() + 1;
            for (int t : TERRORBEAK_ELAPSED) {
                if (elapsed == t) {
                    spawnTerrorbeak(arena, battle.eyePos().offset(0, 0, 12));
                    spawnTerrorbeak(arena, battle.eyePos().offset(0, 0, -12));
                }
            }
            if (elapsed >= MUSIC_LOOP_ELAPSED) {
                arena.playSound(null, battle.eyePos(), ModSounds.AARONCOS_MUSIC.get(), SoundSource.WEATHER, 1, 1);
                elapsed = 0;
            }
            // 双手死亡（99 格范围内空）→ 放奖励箱 + 授予进度 + 移除暗影窥视（传送逻辑移至开箱后）
            AABB box = AABB.ofSize(Vec3.atCenterOf(battle.eyePos()), 99, 99, 99);
            boolean leftAlive = !arena.getEntitiesOfClass(AaroncosLeftHandEntity.class, box, e -> true).isEmpty();
            boolean rightAlive = !arena.getEntitiesOfClass(AaroncosRightHandEntity.class, box, e -> true).isEmpty();
            if (!leftAlive && !rightAlive) {
                BATTLES.remove(arena);
                arena.setBlock(battle.eyePos().offset(0, -1, 0), ModBlocks.AARONCOS_HAND_CHEST.get().defaultBlockState(), 3);
                for (Player p : arena.players()) {
                    if (p instanceof ServerPlayer sp)
                        AdvancementHelper.grant(sp, DEFEAT_AARONCOS_ADV, "defeat_aaroncos");
                    ShadowSpyonEffect.allowRemoval(p);
                    p.removeEffect(ModEffects.SHADOW_SPYON.get());
                }
            } else {
                BATTLES.put(arena, new BattleSession(battle.eyePos(), elapsed));
            }
        }
    }

    private static void spawnTerrorbeak(ServerLevel arena, BlockPos pos) {
        Entity beak = ModEntities.TERRORBEAK.get().spawn(arena, pos, MobSpawnType.MOB_SUMMONED);
        if (beak != null)
            beak.setYRot(arena.random.nextFloat() * 360F);
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
        }
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
