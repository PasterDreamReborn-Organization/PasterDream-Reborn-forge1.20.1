package com.pasterdream.pasterdreammod.advancement.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * 自定义进度触发器 —— 在染梦维度准星指向粉色羊。
 */
public class LookAtPinkSheepTrigger extends SimpleCriterionTrigger<LookAtPinkSheepTrigger.TriggerInstance> {

    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("pasterdream", "look_at_pink_sheep");

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    @Override
    protected @NotNull TriggerInstance createInstance(@NotNull JsonObject json,
                                                       ContextAwarePredicate player,
                                                       DeserializationContext context) {
        return new TriggerInstance(player);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(ContextAwarePredicate player) {
            super(ID, player);
        }

        public static TriggerInstance look() {
            return new TriggerInstance(ContextAwarePredicate.ANY);
        }

        @Override
        public @NotNull JsonObject serializeToJson(SerializationContext context) {
            return new JsonObject();
        }
    }
}
