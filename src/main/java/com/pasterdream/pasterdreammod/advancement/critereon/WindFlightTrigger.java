package com.pasterdream.pasterdreammod.advancement.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.NotNull;

/**
 * 自定义进度触发器 —— 风之旅途维度中带顺风/逆风效果累计飞行距离。
 * <p>
 * 飞行以鞘翅滑翔（isFallFlying）为准，距离为累计值，由
 * {@code WindDirectionHandler} 每 tick 结算并调用 {@link #trigger}。
 */
public class WindFlightTrigger extends SimpleCriterionTrigger<WindFlightTrigger.TriggerInstance> {

    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("pasterdream", "wind_flight");

    /** 飞行时携带的风向效果类型 */
    public enum FlightType {
        TAILWIND("tailwind"),
        DEADWIND("deadwind");

        private final String id;

        FlightType(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public static FlightType byId(String id) {
            for (FlightType type : values()) {
                if (type.id.equals(id)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown wind flight type: " + id);
        }
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    @Override
    protected @NotNull TriggerInstance createInstance(@NotNull JsonObject json,
                                                       ContextAwarePredicate player,
                                                       DeserializationContext context) {
        FlightType type = FlightType.byId(GsonHelper.getAsString(json, "type"));
        MinMaxBounds.Doubles distance = MinMaxBounds.Doubles.fromJson(json.get("distance"));
        return new TriggerInstance(player, type, distance);
    }

    /**
     * 玩家带指定风向效果累计飞行距离更新时调用。
     *
     * @param player   触发玩家
     * @param type     生效的风向效果
     * @param distance 累计飞行距离（格）
     */
    public void trigger(ServerPlayer player, FlightType type, double distance) {
        this.trigger(player, instance -> instance.matches(type, distance));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        private final FlightType type;
        private final MinMaxBounds.Doubles distance;

        public TriggerInstance(ContextAwarePredicate player, FlightType type, MinMaxBounds.Doubles distance) {
            super(ID, player);
            this.type = type;
            this.distance = distance;
        }

        /**
         * 带指定风向效果累计飞行达到给定距离时触发。
         */
        public static TriggerInstance flown(FlightType type, MinMaxBounds.Doubles distance) {
            return new TriggerInstance(ContextAwarePredicate.ANY, type, distance);
        }

        public boolean matches(FlightType type, double distance) {
            return this.type == type && this.distance.matches(distance);
        }

        @Override
        public @NotNull JsonObject serializeToJson(SerializationContext context) {
            JsonObject obj = new JsonObject();
            obj.addProperty("type", this.type.id);
            obj.add("distance", this.distance.serializeToJson());
            return obj;
        }
    }
}