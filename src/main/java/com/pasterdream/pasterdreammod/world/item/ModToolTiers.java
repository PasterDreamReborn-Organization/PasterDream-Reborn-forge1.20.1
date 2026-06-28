package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModToolTiers implements Tier {

    // ==== 成套工具 ====

    COPPER(225, 4.0F, 1.5F, 1, 12,
            () -> Ingredient.of(Items.COPPER_INGOT)),

    // 炙焰金
    MOLTEN_GOLD(251, 14.0F, 2.0F, 2, 23,
            () -> Ingredient.of(ModItems.MOLTEN_GOLD_INGOT.get())),
    // 钛金
    TITANIUM(1721, 9.0F, 3.5F, 4, 17,
            () -> Ingredient.of(ModItems.TITANIUM_INGOT.get())),

    // SHADOW_EROSION(1725, 13.0F, 4.0F, 4, 16,
    //         () -> Ingredient.of(ModItems.BLACKMETAL_INGOT.get())),  // TODO: blackmetal_ingot 未注册

    // 染梦合金
    DYEDREAM(1314, 11.0F, 4.0F, 4, 22,
            () -> Ingredient.of(ModItems.DYEDREAM_ALLOY_INGOT.get())),

    // 融梦水晶
    MELT_DREAM(1728, 14.0F, 4.5F, 5, 12,
            () -> Ingredient.of(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get())),

    // 狱炎
    HELLFIRE(1255, 16.0F, 3.0F, 4, 23,
            () -> Ingredient.of(ModItems.MOLTEN_GOLD_INGOT.get())),

    // ==== 独立武器 ====

    // 草薙
    // GRASS_SWORD(874, 1.0F, 3.0F, 1, 16,
    //         () -> Ingredient.of(Items.JUNGLE_SPORE)),  // TODO: jungle_spore 不存在

    // 引潮
    TIDE_SWORD(1561, 4.0F, 4.5F, 1, 11,
            () -> Ingredient.of(Items.PRISMARINE_CRYSTALS)),

    // 朔漠
    DESERT_SWORD(1561, 1.0F, 7.0F, 1, 8,
            Ingredient::of),

    // 影刃
    // SHADOW_SWORD(1771, 0.0F, 8.0F, 1, 10,
    //         () -> Ingredient.of(ModItems.NIGHTMARE_FUEL.get())),  // TODO: nightmare_fuel 未注册

    // 白厄
    // WHITE_SWORD(1771, 0.0F, 5.0F, 1, 10,
    //         () -> Ingredient.of(ModBlocks.SHADOW_LIGHT_0.get())),  // TODO: shadow_light_0 未注册

    // 大地之刃
    TERRA_SWORD(2011, 4.0F, 4.0F, 1, 2,
            Ingredient::of),

    // 冰影战锤
    ICE_SHADOW_HAMMER(835, 4.0F, 9.0F, 1, 2,
            Ingredient::of),

    // 热能匕首
    THERMAL_DAGGER(1721, 1.0F, 2.5F, 0, 2,
            () -> Ingredient.of(ModItems.TITANIUM_NUGGET.get())),

    // 断裂英雄之剑
    BROKEN_HERO_SWORD(100, 1.0F, 3.0F, 1, 0,
            Ingredient::of);

    // TRUEST_MOLTENGOLD_SWORD(1255, 1.0F, 3.0F, 1, 23,
    //         () -> Ingredient.of(ModItems.MOLTEN_GOLD_INGOT.get())),  // 与 TRUE_MOLTENGOLD 同级，可用后者替代

    // TRUE_GRASS_SWORD(1311, 1.0F, 3.5F, 1, 16,
    //         () -> Ingredient.of(Items.JUNGLE_SPORE)),  // TODO: jungle_spore 不存在

    // TRUE_TIDE_SWORD(1561, 4.0F, 5.0F, 1, 11,
    //         () -> Ingredient.of(Items.PRISMARINE_CRYSTALS)),  // 与 TIDE_SWORD 同级，可用后者替代

    // TRUE_DESERT_SWORD(1561, 1.0F, 8.0F, 1, 8,
    //         () -> Ingredient.of()),  // 与 DESERT_SWORD 同级，可用后者替代

    // CREATIVE_SWORD(100, 4.0F, 6.0F, 1, 2,
    //         () -> Ingredient.of()),  // 调试武器，暂不搬运

    // DREAM_WAND(0, 4.0F, 2.0F, 1, 2,
    //         () -> Ingredient.of());  // 万能工具，暂不搬运

    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int level;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    ModToolTiers(int uses, float speed, float attackDamageBonus, int level, int enchantmentValue,
                 Supplier<Ingredient> repairIngredient) {
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.level = level;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamageBonus;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
