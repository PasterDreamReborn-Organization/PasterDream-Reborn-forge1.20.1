package com.pasterdream.pasterdreammod.advancement.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * 自定义进度触发器 —— 穿过破风幕帐。
 * 由 {@code BreakWindCurtainBlock#entityInside} 在玩家飞行穿过幕帐时调用。
 */
public class BreakWindCurtainTrigger extends SimpleCriterionTrigger<BreakWindCurtainTrigger.TriggerInstance> {

    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("pasterdream", "break_wind_curtain");

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    @Override
    protected @NotNull TriggerInstance createInstance(@NotNull JsonObject json,
                                                       ContextAwarePredicate player,
                                                       DeserializationContext context) {
        MinMaxBounds.Ints count = json.has("count")
                ? MinMaxBounds.Ints.fromJson(json.get("count"))
                : MinMaxBounds.Ints.atLeast(1);
        return new TriggerInstance(player, count);
    }

    public void trigger(ServerPlayer player, int count) {
        this.trigger(player, instance -> instance.matches(count));
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, 1);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        private final MinMaxBounds.Ints count;

        public TriggerInstance(ContextAwarePredicate player, MinMaxBounds.Ints count) {
            super(ID, player);
            this.count = count;
        }

        public static TriggerInstance passed() {
            return new TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.atLeast(1));
        }

        public static TriggerInstance passedTimes(int times) {
            return new TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.atLeast(times));
        }

        public boolean matches(int count) {
            return this.count.matches(count);
        }

        @Override
        public @NotNull JsonObject serializeToJson(SerializationContext context) {
            JsonObject obj = new JsonObject();
            obj.add("count", this.count.serializeToJson());
            return obj;
        }
    }
}