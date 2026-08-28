package com.pasterdream.pasterdreammod.helper.drinkandfoodproperties;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class GenericFluidDrinkProperties
{
    private int useDuration = 32;
    private int drinkAmount = 100;
    private FoodProperties foodProperties = null;
    private double sanAdd = 0;
    private double meltDreamEnergyAdd = 0;
    private List<MobEffectInstance> effects = new ArrayList<>();
    private BiConsumer<LivingEntity, Level> drinkSpecial = (entity, level) -> {};

    public GenericFluidDrinkProperties useDuration(int ticks)
    {
        this.useDuration = ticks;
        return this;
    }

    public GenericFluidDrinkProperties drinkAmount(int mb)
    {
        this.drinkAmount = mb;
        return this;
    }

    public GenericFluidDrinkProperties food(FoodProperties food)
    {
        this.foodProperties = food;
        return this;
    }

    public GenericFluidDrinkProperties sanAdd(double amount)
    {
        this.sanAdd = amount;
        return this;
    }

    public GenericFluidDrinkProperties meltDreamEnergyAdd(double amount)
    {
        this.meltDreamEnergyAdd = amount;
        return this;
    }

    public GenericFluidDrinkProperties effect(MobEffectInstance effect)
    {
        this.effects.add(effect);
        return this;
    }

    public GenericFluidDrinkProperties onDrinkSpecial(BiConsumer<LivingEntity, Level> callback)
    {
        this.drinkSpecial = callback;
        return this;
    }

    public int getUseDuration()
    {
        return useDuration;
    }

    public int getDrinkAmount()
    {
        return drinkAmount;
    }

    public FoodProperties getFoodProperties()
    {
        return foodProperties;
    }

    public double getSanAdd()
    {
        return sanAdd;
    }

    public double getMeltDreamEnergyAdd()
    {
        return meltDreamEnergyAdd;
    }

    public List<MobEffectInstance> getEffects()
    {
        return effects;
    }

    public BiConsumer<LivingEntity, Level> getDrinkSpecial()
    {
        return drinkSpecial;
    }

    public static GenericFluidDrinkProperties copy(GenericFluidDrinkProperties properties)
    {
        return new GenericFluidDrinkProperties().drinkAmount(properties.getDrinkAmount()).useDuration(properties.getUseDuration()).food(properties.getFoodProperties()).sanAdd(properties.getSanAdd()).meltDreamEnergyAdd(properties.getMeltDreamEnergyAdd()).onDrinkSpecial(properties.getDrinkSpecial());
    }
}
