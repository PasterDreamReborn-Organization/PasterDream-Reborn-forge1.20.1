package com.pasterdream.pasterdreammod.advancement.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * 自定义进度触发器 —— 在指定高度以上食用星河果冻。
 * <p>
 * 对应进度条件参数：
 * <ul>
 *   <li>{@code min_y} (int) — 触发所需的最低 Y 坐标，默认 -64</li>
 * </ul>
 */
public class EatGalaxyJellyAtHeightTrigger extends SimpleCriterionTrigger<EatGalaxyJellyAtHeightTrigger.TriggerInstance> {

    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("pasterdream", "eat_galaxy_jelly_at_height");

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    @Override
    protected @NotNull TriggerInstance createInstance(@NotNull JsonObject json,
                                                       ContextAwarePredicate player,
                                                       DeserializationContext context) {
        int minY = json.has("min_y") ? json.get("min_y").getAsInt() : -64;
        return new TriggerInstance(player, minY);
    }

    /**
     * 当玩家食用星河果冻时调用。
     *
     * @param player 触发玩家
     * @param y      玩家食用时的 Y 坐标
     */
    public void trigger(ServerPlayer player, double y) {
        this.trigger(player, instance -> instance.matches(y));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        /** 触发所需的最低 Y 坐标 */
        private final int minY;

        public TriggerInstance(ContextAwarePredicate player, int minY) {
            super(ID, player);
            this.minY = minY;
        }

        /**
         * 创建默认条件实例 —— 要求 Y ≥ 256（原版建筑高度上限附近）。
         */
        public static TriggerInstance atBuildHeight() {
            return new TriggerInstance(ContextAwarePredicate.ANY, 256);
        }

        /**
         * 创建指定高度的条件实例。
         */
        public static TriggerInstance above(int minY) {
            return new TriggerInstance(ContextAwarePredicate.ANY, minY);
        }

        public boolean matches(double y) {
            return y >= minY;
        }

        @Override
        public @NotNull JsonObject serializeToJson(SerializationContext context) {
            JsonObject obj = new JsonObject();
            obj.addProperty("min_y", minY);
            return obj;
        }
    }
}
