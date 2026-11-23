package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.world.dimension.DyedreamDimension;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * 让染梦世界像主世界一样自然生成原版流浪商人。
 *
 * <p>原版 {@code MinecraftServer#createLevels} 只给主世界的 {@code ServerLevel} 注入
 * {@link WanderingTraderSpawner}，其他维度的 {@code customSpawners} 是空列表。这里不改动
 * 原版字段，而是在染梦世界的维度 tick 末尾手动驱动一个该维度专属的 spawner 实例，从而完整
 * 复用原版的计时、POI 选址、羊驼与消失逻辑。</p>
 *
 * <p>spawner 的计时状态只保存在内存中：染梦世界拿到的 {@code LevelData} 是
 * {@code DerivedLevelData}，本身不持久化游商计时（读取恒为 0、写入为空操作），
 * 维度重载或服务器重启后会重新等待一轮。</p>
 */
public final class DyedreamWanderingTraderSpawner {

    /** 每个染梦世界维度一个 spawner，保存自身的生成计时状态。 */
    private static final Map<ResourceKey<Level>, WanderingTraderSpawner> SPAWNERS = new HashMap<>();

    private DyedreamWanderingTraderSpawner() {}

    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END
                || !(event.level instanceof ServerLevel level)
                || !level.dimension().equals(DyedreamDimension.DYEDREAM_WORLD)
                || !Config.dyedreamWanderingTraderEnabled) {
            return;
        }

        WanderingTraderSpawner spawner = SPAWNERS.computeIfAbsent(
                level.dimension(),
                key -> new WanderingTraderSpawner((ServerLevelData) level.getLevelData()));
        spawner.tick(level, true, true);
    }

    public static void onLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel() instanceof ServerLevel level
                && level.dimension().equals(DyedreamDimension.DYEDREAM_WORLD)) {
            SPAWNERS.remove(level.dimension());
        }
    }
}
