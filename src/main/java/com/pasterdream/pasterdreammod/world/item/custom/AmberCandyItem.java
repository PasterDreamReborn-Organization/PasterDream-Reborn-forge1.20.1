package com.pasterdream.pasterdreammod.world.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AmberCandyItem extends Item {

    private static final FoodProperties FOOD = new FoodProperties.Builder()
            .nutrition(1).saturationMod(0.1f).alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60, 0), 1.0f)
            .build();

    public AmberCandyItem() {
        super(new Item.Properties().food(FOOD));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 16;
    }
}
