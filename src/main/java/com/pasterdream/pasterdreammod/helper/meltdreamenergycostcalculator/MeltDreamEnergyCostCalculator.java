package com.pasterdream.pasterdreammod.helper.meltdreamenergycostcalculator;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class MeltDreamEnergyCostCalculator
{
    public static double calculate(ListTag listTag)
    {
        double totalQualityLevelXWeight = 0;
        double totalWeight = 0;

        for(int i = 0; i < listTag.size(); i++)
        {
            CompoundTag compoundTag = listTag.getCompound(i);
            int weight = compoundTag.getInt("weight");

            double qualityMultiply = switch (i + 1)
            {
                case 1  -> 20;
                case 2  -> 45;
                case 3  -> 70;
                default -> 2147483647;
            };

            totalQualityLevelXWeight += weight * qualityMultiply;
            totalWeight += weight;
        }

        return totalQualityLevelXWeight / totalWeight;
    }
}
