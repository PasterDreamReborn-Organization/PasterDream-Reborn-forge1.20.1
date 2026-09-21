package com.pasterdream.pasterdreammod.world.conditions;

import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

/**
 * 配方条件：仅在「染梦世界跃迁石」配置项开启时加载对应配方。
 * 该内容为 0.2.0 的测试性内容，默认关闭，后续版本可能重置。
 */
public class DyedreamWorldLeapstoneEnabledCondition implements ICondition
{
    public static final DyedreamWorldLeapstoneEnabledCondition INSTANCE = new DyedreamWorldLeapstoneEnabledCondition();

    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world_leapstone_enabled");

    private DyedreamWorldLeapstoneEnabledCondition() {}

    @Override
    public ResourceLocation getID()
    {
        return ID;
    }

    @Override
    public boolean test(IContext context)
    {
        return Config.dyedreamWorldLeapstoneEnabled;
    }

    public static class Serializer implements IConditionSerializer<DyedreamWorldLeapstoneEnabledCondition>
    {
        @Override
        public void write(JsonObject json, DyedreamWorldLeapstoneEnabledCondition value)
        {
        }

        @Override
        public DyedreamWorldLeapstoneEnabledCondition read(JsonObject json)
        {
            return INSTANCE;
        }

        @Override
        public ResourceLocation getID()
        {
            return ID;
        }
    }
}
