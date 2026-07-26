package com.pasterdream.pasterdreammod.world.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.util.FakePlayer;

/**
 * 战利品条件：破坏者是真实玩家（非 FakePlayer）。
 * 用于区分玩家手动破坏与自动化收割（机械动力动力犁等）。
 */
public class RealPlayerCondition implements LootItemCondition
{
    public static final RealPlayerCondition INSTANCE = new RealPlayerCondition();

    /** 由 ModLootTables 在注册时赋值 */
    public static LootItemConditionType TYPE;

    private RealPlayerCondition() {}

    @Override
    public boolean test(LootContext context)
    {
        Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        return entity instanceof ServerPlayer && !(entity instanceof FakePlayer);
    }

    @Override
    public LootItemConditionType getType()
    {
        return TYPE;
    }

    public static LootItemCondition.Builder builder()
    {
        return () -> INSTANCE;
    }

    public static class ConditionSerializer implements Serializer<RealPlayerCondition>
    {
        @Override
        public void serialize(JsonObject json, RealPlayerCondition condition, JsonSerializationContext ctx)
        {
        }

        @Override
        public RealPlayerCondition deserialize(JsonObject json, JsonDeserializationContext ctx)
        {
            return INSTANCE;
        }
    }
}
