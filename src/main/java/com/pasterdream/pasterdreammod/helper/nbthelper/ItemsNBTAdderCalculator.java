package com.pasterdream.pasterdreammod.helper.nbthelper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ItemsNBTAdderCalculator
{
    public static CompoundTag calculate(List<CompoundTag> itemsNBT)
    {
        CompoundTag calculatedNBT = new CompoundTag();

        List<ListTag> ListEnchantementList = new ArrayList<>();
        boolean isHaveEnchantment = false;

        double attackBoostValue = 0;
        double luckBoostValue = 0;

        for(CompoundTag itemNBT : itemsNBT)
        {
            if(itemNBT == null)
            {
                continue;
            }

            if(itemNBT.contains("Enchantments"))
            {
                isHaveEnchantment = true;
                ListTag Enchantments = itemNBT.getList("Enchantments", Tag.TAG_COMPOUND);
                ListEnchantementList.add(Enchantments);
            }

            if(itemNBT.contains("AttackBoost"))
            {
                attackBoostValue += itemNBT.getDouble("AttackBoost");
            }

            if(itemNBT.contains("LuckBoost"))
            {
                luckBoostValue += itemNBT.getDouble("LuckBoost");
            }
        }

        if(isHaveEnchantment)
        {
            ListTag calculatedEnchantmentList = enchantmentAdderCalculate(ListEnchantementList);
            calculatedNBT.put("Enchantments", calculatedEnchantmentList);
        }

        if(attackBoostValue != 0)
        {
            calculatedNBT.putDouble("AttackBoost", attackBoostValue);
        }

        if(luckBoostValue != 0)
        {
            calculatedNBT.putDouble("LuckBoost", luckBoostValue);
        }

        return calculatedNBT;
    }

    public static ListTag enchantmentAdderCalculate(List<ListTag> ListEnchantementList)
    {
        List<ListTag> copyListEnchantment = new ArrayList<>(ListEnchantementList);
        List<CompoundTag> AllEnchantments = new ArrayList<>();
        ListTag enchantments = new ListTag();

        for(ListTag singleItemEnchantments : copyListEnchantment)
        {
            for(int i = 0; i < singleItemEnchantments.size(); i++)
            {
                CompoundTag singleEnchantment = singleItemEnchantments.getCompound(i);
                String enchantmentId = singleEnchantment.getString("id");
                short level = singleEnchantment.getShort("lvl");
                BigInteger currentEnchantmentValue = BigInteger.ONE.shiftLeft(level);
                singleEnchantment.putString("currentEnchantmentValue", currentEnchantmentValue.toString());
                singleEnchantment.remove("lvl");

                Boolean isAddedEnchantmentsHaveThisEnchantment = false;
                int index = 0;
                for(CompoundTag addedEnchantment : AllEnchantments)
                {
                    if(addedEnchantment.getString("id").equals(enchantmentId))
                    {
                        isAddedEnchantmentsHaveThisEnchantment = true;
                        break;
                    }
                    index++;
                }

                if(isAddedEnchantmentsHaveThisEnchantment)
                {
                    CompoundTag addedEnchantment = AllEnchantments.get(index);
                    BigInteger addedEnchantmentValue = new BigInteger(addedEnchantment.getString("currentEnchantmentValue"));
                    BigInteger calculatedEnchantmentValue = addedEnchantmentValue.add(currentEnchantmentValue);
                    addedEnchantment.putString("currentEnchantmentValue", calculatedEnchantmentValue.toString());
                }
                    else
                    {
                        AllEnchantments.add(singleEnchantment);
                    }
            }
        }

        for(CompoundTag enchantment : AllEnchantments)
        {
            BigInteger enchantmentValue = new BigInteger(enchantment.getString("currentEnchantmentValue"));
            short enchantmentLevel = (short)(enchantmentValue.bitLength() - 1);
            enchantment.remove("currentEnchantmentValue");
            enchantment.putShort("lvl", enchantmentLevel);
            enchantments.add(enchantment);
        }

        return enchantments;
    }
}
