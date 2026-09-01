package com.pasterdream.pasterdreammod.advancement.critereon;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.pasterdream.pasterdreammod.helper.AdvancementHelper;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.NotNull;

/**
 * 自定义进度触发器 —— 玩家已完成指定进度（用作其他进度的前置进度检查）。
 */
public class HasAdvancementTrigger extends SimpleCriterionTrigger<HasAdvancementTrigger.TriggerInstance> {

    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("pasterdream", "has_advancement");

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    @Override
    protected @NotNull TriggerInstance createInstance(@NotNull JsonObject json,
                                                       ContextAwarePredicate player,
                                                       DeserializationContext context) {
        ResourceLocation required = ResourceLocation.tryParse(GsonHelper.getAsString(json, "advancement"));
        if (required == null) {
            throw new JsonSyntaxException("Invalid advancement id");
        }
        return new TriggerInstance(player, required);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> AdvancementHelper.isDone(player, instance.requiredAdvancement));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        private final ResourceLocation requiredAdvancement;

        public TriggerInstance(ContextAwarePredicate player, ResourceLocation requiredAdvancement) {
            super(ID, player);
            this.requiredAdvancement = requiredAdvancement;
        }

        public static TriggerInstance hasAdvancement(ResourceLocation requiredAdvancement) {
            return new TriggerInstance(ContextAwarePredicate.ANY, requiredAdvancement);
        }

        @Override
        public @NotNull JsonObject serializeToJson(SerializationContext context) {
            JsonObject json = super.serializeToJson(context);
            json.addProperty("advancement", requiredAdvancement.toString());
            return json;
        }
    }
}