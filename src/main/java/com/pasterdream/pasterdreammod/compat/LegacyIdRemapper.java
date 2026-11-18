package com.pasterdream.pasterdreammod.compat;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 旧 ID → 当前 ID 的兼容映射，用于读取「外部字符串」里的注册名（配置文件等）。
 *
 * <p>实体 ID 规范化（修正拼写错误与冗余前缀）之后，旧配置文件里的旧 ID 会在解析阶段
 * 自动被换成当前 ID，玩家不需要手动改配置：
 * <ul>
 *   <li>{@code pasterdream:weakeness_terrorbeak} → {@code pasterdream:weakness_terrorbeak}</li>
 *   <li>{@code pasterdream:projectile_bone_wing_fire_ball_projectile} →
 *       {@code pasterdream:bone_wing_fire_ball_projectile}</li>
 *   <li>{@code pasterdream:weakeness_terrorbeak_spawn_egg} →
 *       {@code pasterdream:weakness_terrorbeak_spawn_egg}（刷怪蛋物品 ID）</li>
 * </ul>
 *
 * <p>范围说明：只处理配置等外部输入的字符串 ID。存档里已经写下的实体 ID 不做迁移
 * （旧实体直接消失即可），结构 NBT 内的 ID 也不在这里处理（当前随模组分发的结构
 * 文件中不含这些旧 ID）。
 */
public final class LegacyIdRemapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(LegacyIdRemapper.class);

    private static final Map<ResourceLocation, ResourceLocation> ENTITY_TYPES = Map.of(
            id("weakeness_terrorbeak"), id("weakness_terrorbeak"),
            id("projectile_bone_wing_fire_ball_projectile"), id("bone_wing_fire_ball_projectile"));

    private static final Map<ResourceLocation, ResourceLocation> ITEMS = Map.of(
            id("weakeness_terrorbeak_spawn_egg"), id("weakness_terrorbeak_spawn_egg"));

    private LegacyIdRemapper() {
    }

    /**
     * 把旧实体 ID 换成当前实体 ID；不认识（或已是当前 ID）时原样返回。
     *
     * @param id 从配置里解析出的实体 ID，可为 {@code null}
     * @return 当前实体 ID，{@code null} 输入返回 {@code null}
     */
    public static ResourceLocation entityType(ResourceLocation id) {
        return remap(ENTITY_TYPES, id);
    }

    /**
     * 把旧物品 ID 换成当前物品 ID；不认识（或已是当前 ID）时原样返回。
     *
     * @param id 从配置里解析出的物品 ID，可为 {@code null}
     * @return 当前物品 ID，{@code null} 输入返回 {@code null}
     */
    public static ResourceLocation item(ResourceLocation id) {
        return remap(ITEMS, id);
    }

    private static ResourceLocation remap(Map<ResourceLocation, ResourceLocation> table, ResourceLocation id) {
        if (id == null) {
            return null;
        }
        ResourceLocation current = table.get(id);
        if (current == null) {
            return id;
        }
        LOGGER.info("Legacy id '{}' in config/input remapped to '{}'", id, current);
        return current;
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path);
    }
}
