package com.pasterdream.pasterdreammod.helper.drinkandfoodproperties;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class PasterDreamDrinkAndFoodProperties extends Item.Properties
{
    private int useDuration = 32;
    private double sanAdd = 0;
    private double meltDreamEnergyAdd = 0;

    @Override
    public PasterDreamDrinkAndFoodProperties food(FoodProperties food)
    {
        super.food(food);
        return this;
    }

    @Override
    public PasterDreamDrinkAndFoodProperties stacksTo(int size)
    {
        super.stacksTo(size);
        return this;
    }

    @Override
    public PasterDreamDrinkAndFoodProperties rarity(Rarity rarity)
    {
        super.rarity(rarity);
        return this;
    }

    public PasterDreamDrinkAndFoodProperties useDuration(int ticks)
    {
        this.useDuration = ticks;
        return this;
    }

    public PasterDreamDrinkAndFoodProperties sanAdd(double amount)
    {
        this.sanAdd = amount;
        return this;
    }

    public PasterDreamDrinkAndFoodProperties meltDreamEnergyAdd(double amount)
    {
        this.meltDreamEnergyAdd = amount;
        return this;
    }

    public int getUseDuration()
    {
        return useDuration;
    }

    public double getSanAdd()
    {
        return sanAdd;
    }

    public double getMeltDreamEnergyAdd()
    {
        return meltDreamEnergyAdd;
    }
}
