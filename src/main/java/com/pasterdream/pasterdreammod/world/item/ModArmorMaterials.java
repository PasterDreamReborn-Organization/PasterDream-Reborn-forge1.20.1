package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {

    // 铜
    COPPER("copper", 14, new int[]{2, 5, 3, 1}, 8,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F,
            () -> Ingredient.of(Items.COPPER_INGOT)),

    // 钛金
    TITANIUM("titanium", 25, new int[]{3, 8, 6, 3}, 17,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 3.0F, 0.1F,
            () -> Ingredient.of(ModItems.TITANIUM_INGOT.get())),

    // 染梦合金
    DYEDREAM("dyedream", 40, new int[]{3, 8, 6, 3}, 22,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 3.0F, 0.1F,
            () -> Ingredient.of(ModItems.DYEDREAM_ALLOY_INGOT.get())),

    // SCULK("sculk", 42, new int[]{3, 8, 6, 3}, 9,
    //         SoundEvents.ARMOR_EQUIP_NETHERITE, 3.5F, 0.15F,
    //         () -> Ingredient.of(ModItems.SCULK_HEART.get())),  // TODO: sculk_heart 未注册

    // QYM("qym", 0, new int[]{2, 10, 10, 10}, 99,
    //         SoundEvents.ARMOR_EQUIP_NETHERITE, 10.0F, 1.0F,
    //         () -> Ingredient.of(ModItems.MEMORY_GEM_0.get())),  // TODO: memory_gem_0 未注册

    // 天使之翼
    ANGEL_WING("angel_wing", 30, new int[]{2, 6, 5, 2}, 9,
            SoundEvents.ARMOR_EQUIP_ELYTRA, 2.0F, 0.0F,
            () -> Ingredient.of(Items.FEATHER)),

    // FORSAKENS_WING("forsakens_wing", 30, new int[]{2, 7, 5, 2}, 7,
    //         SoundEvents.ARMOR_EQUIP_ELYTRA, 3.0F, 0.0F,
    //         () -> Ingredient.of(ModItems.NIGHTMARE_FUEL.get())),  // TODO: nightmare_fuel 未注册

    // 机械光翼
    MACHINE_WING("machine_wing", 34, new int[]{2, 7, 5, 2}, 9,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0F, 0.0F,
            Ingredient::of);

    private static final int[] BASE_DURABILITY = {11, 16, 15, 13};

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protection;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protection, int enchantability,
                      SoundEvent equipSound, float toughness, float knockbackResistance,
                      Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protection = protection;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.protection[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return PasterDreamMod.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
