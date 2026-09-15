package com.pasterdream.pasterdreammod.advancement.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * 自定义进度触发器 —— 用纷争预言卡标记悲泣尖啸怨魂。
 * 对应进度："是……我杀了我？！"。
 */
public class MarkWailingShadowGhostTrigger extends SimpleCriterionTrigger<MarkWailingShadowGhostTrigger.TriggerInstance> {

    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("pasterdream", "mark_wailing_shadow_ghost");

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

    /**
     * 当玩家用纷争预言卡标记悲泣尖啸怨魂时调用。
     */
    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(ContextAwarePredicate player) {
            super(ID, player);
        }

        /**
         * 创建条件实例 —— 用于"是……我杀了我？！"进度。
         */
        public static TriggerInstance marked() {
            return new TriggerInstance(ContextAwarePredicate.ANY);
        }

        @Override
        public @NotNull JsonObject serializeToJson(SerializationContext context) {
            return new JsonObject();
        }
    }
}
