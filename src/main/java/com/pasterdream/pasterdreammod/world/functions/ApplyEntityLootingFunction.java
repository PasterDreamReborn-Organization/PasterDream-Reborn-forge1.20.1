package com.pasterdream.pasterdreammod.world.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.pasterdream.pasterdreammod.init.ModLootTables;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 实体战利品抢夺函数。与 {@code ApplyBonusCount} 不同，此函数不声明 {@code tool} 参数依赖，
 * 因此可在 ENTITY 战利品上下文中通过 datagen 验证。运行时从实体击杀者手中获取工具并计算抢夺等级。
 */
public class ApplyEntityLootingFunction extends LootItemConditionalFunction {
    private final Enchantment enchantment;
    private final int bonusPerLevel;

    ApplyEntityLootingFunction(LootItemCondition[] conditions, Enchantment enchantment, int bonusPerLevel) {
        super(conditions);
        this.enchantment = enchantment;
        this.bonusPerLevel = bonusPerLevel;
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext ctx) {
        ItemStack tool = ctx.getParamOrNull(LootContextParams.TOOL);
        int looting = 0;
        if (tool != null) {
            looting = EnchantmentHelper.getItemEnchantmentLevel(enchantment, tool);
        }
        if (looting > 0) {
            RandomSource random = ctx.getRandom();
            int bonus = 0;
            for (int i = 0; i < looting; i++) {
                bonus += random.nextInt(bonusPerLevel + 1);
            }
            stack.grow(bonus);
        }
        return stack;
    }

    @Override
    public LootItemFunctionType getType() {
        return ModLootTables.APPLY_ENTITY_LOOTING_FUNCTION.get();
    }

    public static class Builder extends LootItemConditionalFunction.Builder<Builder> {
        private final Enchantment enchantment;
        private final int bonusPerLevel;

        public Builder(Enchantment enchantment, int bonusPerLevel) {
            this.enchantment = enchantment;
            this.bonusPerLevel = bonusPerLevel;
        }

        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public ApplyEntityLootingFunction build() {
            return new ApplyEntityLootingFunction(getConditions(), enchantment, bonusPerLevel);
        }
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<ApplyEntityLootingFunction> {
        @Override
        public void serialize(JsonObject json, ApplyEntityLootingFunction func, JsonSerializationContext ctx) {
            super.serialize(json, func, ctx);
            json.addProperty("enchantment", ForgeRegistries.ENCHANTMENTS.getKey(func.enchantment).toString());
            json.addProperty("bonus_per_level", func.bonusPerLevel);
        }

        @Override
        public ApplyEntityLootingFunction deserialize(JsonObject json, JsonDeserializationContext ctx, LootItemCondition[] conditions) {
            String enchStr = GsonHelper.getAsString(json, "enchantment");
            Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(net.minecraft.resources.ResourceLocation.parse(enchStr));
            int bonusPerLevel = GsonHelper.getAsInt(json, "bonus_per_level", 1);
            return new ApplyEntityLootingFunction(conditions, enchantment, bonusPerLevel);
        }
    }
}
