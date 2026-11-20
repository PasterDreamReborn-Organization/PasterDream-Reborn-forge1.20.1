package com.pasterdream.pasterdreammod.advancement.critereon;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.armoritem.FluffyWindAlloyArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.MeltDreamCrystalArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.ShadowAlloyArmorItem;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * 自定义进度触发器 —— 玩家同时穿戴某一套护甲的全部四件。
 * <p>
 * 与 {@code inventory_changed} 的区别：后者的判据是逐条 latch 的，
 * {@code requirements(AND)} 只表示「四件在任意时刻各拿到过」，
 * 并不要求同时持有/同时穿戴。本触发器复用各护甲类的 {@code hasFullSet} 判定，
 * 语义为「此刻正穿着整套」。
 * <p>
 * 由 {@code ArmorSetAdvancementHandler} 在换装/登录/重生时触发。
 */
public class WearingArmorSetTrigger extends SimpleCriterionTrigger<WearingArmorSetTrigger.TriggerInstance> {

    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wearing_armor_set");

    /** 可判定的护甲套装。id 为进度 JSON 中 {@code set} 字段的取值。 */
    public enum ArmorSet {
        MELT_DREAM("melt_dream", MeltDreamCrystalArmorItem::hasFullSet),
        FLUFFY_WIND_ALLOY("fluffy_wind_alloy", FluffyWindAlloyArmorItem::hasFullSet),
        SHADOW_ALLOY("shadow_alloy", ShadowAlloyArmorItem::hasFullSet);

        private final String id;
        private final Predicate<LivingEntity> wearing;

        ArmorSet(String id, Predicate<LivingEntity> wearing) {
            this.id = id;
            this.wearing = wearing;
        }

        public String id() {
            return this.id;
        }

        public boolean isWearing(LivingEntity entity) {
            return this.wearing.test(entity);
        }

        private static ArmorSet byId(String id) {
            for (ArmorSet set : values()) {
                if (set.id.equals(id)) {
                    return set;
                }
            }
            return null;
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
        String setId = GsonHelper.getAsString(json, "set");
        ArmorSet set = ArmorSet.byId(setId);
        if (set == null) {
            throw new JsonSyntaxException("Unknown armor set: " + setId);
        }
        return new TriggerInstance(player, set);
    }

    public void trigger(ServerPlayer player, ArmorSet set) {
        this.trigger(player, instance -> instance.set == set);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        private final ArmorSet set;

        public TriggerInstance(ContextAwarePredicate player, ArmorSet set) {
            super(ID, player);
            this.set = set;
        }

        public static TriggerInstance wearing(ArmorSet set) {
            return new TriggerInstance(ContextAwarePredicate.ANY, set);
        }

        @Override
        public @NotNull JsonObject serializeToJson(SerializationContext context) {
            JsonObject json = super.serializeToJson(context);
            json.addProperty("set", this.set.id());
            return json;
        }
    }
}
